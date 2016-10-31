package Labs;

import DocumentClasses.DocumentCollection;
import DocumentClasses.*;

import java.io.*;
import java.util.*;

/**
 * Created by Ryan on 10/18/2016.
 */
public class Lab3 {
    private static double computeMAP(HashMap<Integer, ArrayList<Integer>> relevant, HashMap<Integer, ArrayList<Integer>> returned) {
        int returnedCount = 0;
        int relevantCount = 0;
        double precisionTotal = 0.0;
        int qCount = 0;
        for (Map.Entry<Integer, ArrayList<Integer>> entry : returned.entrySet()) {
            if (qCount == 20)
                break;

            int qNum = entry.getKey();
            ArrayList<Integer> curReturned = entry.getValue();
            ArrayList<Integer> curRelevant = relevant.get(qNum);

            for (int returnedDoc : curReturned) {
                returnedCount++;
                if (curRelevant.contains(returnedDoc)) {
                    relevantCount++;
                    double precision = ((double)relevantCount/returnedCount);
                    precisionTotal += precision;
                }
            }

            qCount++;
        }

        return precisionTotal/relevantCount;
    }

    private static DocumentCollection documents;
    private static DocumentCollection queries;

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        /*
        documents = new DocumentCollection("./src/files/documents.txt", "document");
        try(ObjectOutputStream os = new ObjectOutputStream(new
                FileOutputStream(new File("./src/files/docvector")))){
            os.writeObject(documents);
        } catch(Exception e){
            System.out.println(e);
        }
*/
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(new File("./src/files/docvector")));
        documents = (DocumentCollection)is.readObject();
        queries = new DocumentCollection("./src/files/queries.txt.qry", "queries");

        // Calculate Okapi results and store top 20
        OkapiDistance okapi = new OkapiDistance();
        TreeMap<Double, ArrayList<Integer>> curTopOkapi = new TreeMap<>();
        HashMap<Integer, ArrayList<Integer>> okapiResults = new HashMap<>();
        for (Map.Entry<Integer, TextVector> entry : queries.getEntrySet()) {
            int qNum = entry.getKey();
            System.out.println("Query " + Integer.toString(qNum));
            QueryVector curQVec = (QueryVector)entry.getValue();
            for (Map.Entry<Integer, TextVector> docEntry : documents.getEntrySet()) {
                int docNum = docEntry.getKey();
                DocumentVector curDocVec = (DocumentVector)docEntry.getValue();

                double okapiScore = okapi.findDistance(curQVec, curDocVec, documents, 0);

                ArrayList<Integer> curDocList;
                if (curTopOkapi.containsKey(okapiScore))
                    curDocList = curTopOkapi.get(okapiScore);
                else
                    curDocList = new ArrayList<>();
                curDocList.add(docNum);
                curTopOkapi.put(okapiScore, curDocList);
            }

            // Store top 20 for every query
            ArrayList<Integer> curTop20 = new ArrayList<>();
            int count = 0;
            boolean done = false;

            for (Double curScoreKey : curTopOkapi.descendingKeySet()) {
                ArrayList<Integer> curDocs = curTopOkapi.get(curScoreKey);
                if (!Double.isNaN(curScoreKey)) {
                    for (int curDoc : curDocs) {
                        curTop20.add(curDoc);
                        count++;
                        if (count == 20) {
                            done = true;
                            break;
                        }
                    }

                    if (done)
                        break;
                }
            }

            okapiResults.put(qNum, curTop20);
        }

        // Read in human judgement text file
        HashMap<Integer, ArrayList<Integer>> humanJudgement = new HashMap<>();
        Scanner scanner = new Scanner(new File("./src/files/human_judgement.txt"));
        while(scanner.hasNextInt()) {
            int qNum = scanner.nextInt();
            int docNum = scanner.nextInt();
            int degRel = scanner.nextInt();

            if (degRel > 0 && degRel < 4) {
                ArrayList<Integer> curDocList;
                if (humanJudgement.containsKey(qNum))
                    curDocList = humanJudgement.get(qNum);
                else
                    curDocList = new ArrayList<>();

                curDocList.add(docNum);
                humanJudgement.put(qNum, curDocList);
            }
        }

        System.out.println(computeMAP(humanJudgement, okapiResults));
    }
}

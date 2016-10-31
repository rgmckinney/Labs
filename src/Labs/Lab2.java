package Labs;

import DocumentClasses.*;
import org.jcp.xml.dsig.internal.dom.Utils;

import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Ryan on 10/3/2016.
 */
public class Lab2 {
    public static DocumentCollection documents;
    public static DocumentCollection queries;

    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        /*
        DocumentCollection docs = new DocumentCollection("./src/files/documents.txt", "document");
        try(ObjectOutputStream os = new ObjectOutputStream(new
                FileOutputStream(new File("./src/files/docvector")))){
            os.writeObject(docs);
        } catch(Exception e){
            System.out.println(e);
        }
*/

        ObjectInputStream is = new ObjectInputStream(new FileInputStream(new File("./src/files/docvector")));
        documents = (DocumentCollection)is.readObject();
        queries = new DocumentCollection("./src/files/queries.txt.qry", "queries");
        documents.normalize(documents, "document");
        queries.normalize(documents, "query");

        CosineDistance cd = new CosineDistance();
        for (Map.Entry<Integer, TextVector> qEntry : queries.getEntrySet()) {
            int qNum = qEntry.getKey();
            QueryVector curQVec = (QueryVector)qEntry.getValue();

            TreeMap<Double, ArrayList<Integer>> docScores = new TreeMap<>();
            for (Map.Entry<Integer, TextVector> docEntry : documents.getEntrySet()) {
                int dNum = docEntry.getKey();
                DocumentVector curDVec = (DocumentVector)docEntry.getValue();
                double curScore = cd.findDistance(curQVec, curDVec, documents, dNum);

                ArrayList<Integer> allDocs;
                if (docScores.containsKey(curScore)) {
                    allDocs = docScores.get(curScore);
                    allDocs.add(dNum);
                    docScores.put(curScore, allDocs);
                }
                else {
                    allDocs = new ArrayList<Integer>();
                    allDocs.add(dNum);
                    docScores.put(curScore, allDocs);
                }
            }

            int count = 0;
            boolean done = false;
            for (Double curScoreKey : docScores.descendingKeySet()) {
                ArrayList<Integer> curDocs = docScores.get(curScoreKey);
                if (!Double.isNaN(curScoreKey)) {
                    for (int curDoc : curDocs) {
                        //System.out.println(curDoc);
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

            for (Double curScoreKey : docScores.descendingKeySet()) {
                ArrayList<Integer> docs = docScores.get(curScoreKey);
                for (int curDoc : docs) {
                    if (curDoc == 51)
                        System.out.println("51 SCORE: " + curScoreKey);
                }
            }
            System.exit(0);
        }

    }
/*
    public static void main2 (String[] args) throws IOException {
        DocumentCollection test = new DocumentCollection("C:\\Users\\Ryan\\IdeaProjects\\CSC46-Lab1\\src\\documents.txt");

        String maxWord = "";
        int maxFreq = 0;
        int totalDWords = 0;
        int totalWords = 0;
        int maxId = 0;

        for (Map.Entry<Integer, TextVector> entry : test.getEntrySet()) {
            if (entry.getValue().getHighestRawFrequency() > maxFreq) {
                maxWord = entry.getValue().getMostFrequentWord();
                maxFreq = entry.getValue().getHighestRawFrequency();
                maxId = entry.getKey();
            }
            totalDWords += entry.getValue().getDistinctWordCount();
            totalWords += entry.getValue().getTotalWordCount();
        }

        System.out.println("Word = " + maxWord);
        System.out.println("Frequency = " + maxFreq);
        System.out.println("Sum of Distinct Number of Words = " + totalDWords);
        System.out.println("Total word count = " + totalWords);

        try(ObjectOutputStream os = new ObjectOutputStream(new
                FileOutputStream(new File("./docvector")))){
            os.writeObject(test);
        } catch(Exception e){
            System.out.println(e);
        }
    } */
}

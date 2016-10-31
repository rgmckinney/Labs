package DocumentClasses;

import java.io.*;
import java.util.*;


/**
 * Created by Ryan on 9/22/2016.
 */
public class DocumentCollection implements Serializable {
    HashMap<Integer, TextVector> hashmap = new HashMap<Integer, TextVector>();

    public TextVector getDocumentById(int id) {
        return hashmap.get(id);
    }

    private boolean isNumber(String word) {
        try {
            Double.parseDouble(word);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public double getAverageDocumentLength() {
        double total = 0;
        int count = 0;

        Iterator it = hashmap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            int curNum = ((TextVector)pair.getValue()).getTotalWordCount();
            total += curNum;
            count++;
        }

        return total/count;
    }

    public int getSize() {
        return hashmap.values().size();
    }

    public Collection<TextVector> getDocuments() {
        return hashmap.values();
    }

    public Set<Map.Entry<Integer, TextVector>> getEntrySet() {
        return hashmap.entrySet();
    }

    public int getDocumentFrequency(String word) {
        int total = 0;

        Iterator it = hashmap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if (((TextVector)pair.getValue()).contains(word)) {
                total++;
            }
        }

        return total;
    }

    private boolean isNoiseWord(String word) {
        return Arrays.asList(noiseWordArray).contains(word);
    }

    private boolean isLetter(char c) {
        return Character.isLetter(c);
    }

    public void normalize(DocumentCollection dc, String type) {
        if (type.equals("document")) {
            for (Map.Entry<Integer, TextVector> entry : hashmap.entrySet()) {
                entry.getValue().normalize(this);
            }
        }
        else {
            for (Map.Entry<Integer, TextVector> entry : hashmap.entrySet()) {
                entry.getValue().normalize(dc);
            }
        }
    }

    public DocumentCollection(String filename, String type)
            throws FileNotFoundException, IOException, ClassNotFoundException {

        Scanner scanner = new Scanner(new File(filename));

        if (type.equals("document")) {
            scanner.useDelimiter(".I");
            while (scanner.hasNext()) {
                String curDoc = scanner.next();
                int curDocNum = Integer.parseInt(curDoc.split(" ")[1].split("\n")[0]);
                String curDocText = curDoc.split(".W")[1];

                TextVector curVec = new DocumentVector();

                for (String curWord : curDocText.split("[^a-zA-Z]")) {
                    if (curWord.length() >= 2 && !Arrays.asList(noiseWordArray).contains(curWord) && !curWord.matches(".\\d+.*")) {
                        curVec.add(curWord.toLowerCase());
                    }
                }

                hashmap.put(curDocNum, curVec);
            }

            for (Map.Entry<Integer, TextVector> entry : hashmap.entrySet()) {
                TextVector curVec = entry.getValue();
                curVec.normalize(this);
            }
        }
        else {
            scanner.useDelimiter(".I");
            int curDocNum = 1;
            while (scanner.hasNext()) {
                String curDoc = scanner.next();
                String curDocText = curDoc.split(".W")[1];

                TextVector curVec = new QueryVector();
                for (String curWord : curDocText.split("[^a-zA-Z0-9]")) {
                    curVec.add(curWord.toLowerCase());
                }

                hashmap.put(curDocNum, curVec);
                curDocNum++;
            }

            ObjectInputStream is = new ObjectInputStream(new FileInputStream(new File("./src/files/docvector")));
            DocumentCollection documents = (DocumentCollection)is.readObject();

            for (Map.Entry<Integer, TextVector> entry : hashmap.entrySet()) {
                TextVector curVec = entry.getValue();
                curVec.normalize(documents);
            }
        }
    }

    public static  String noiseWordArray[] = {"a", "about", "above", "all", "along",
            "also", "although", "am", "an", "and", "any", "are", "aren't", "as", "at",
            "be", "because", "been", "but", "by", "can", "cannot", "could", "couldn't",
            "did", "didn't", "do", "does", "doesn't", "e.g.", "either", "etc", "etc.",
            "even", "ever", "enough", "for", "from", "further", "get", "gets", "got", "had", "have",
            "hardly", "has", "hasn't", "having", "he", "hence", "her", "here",
            "hereby", "herein", "hereof", "hereon", "hereto", "herewith", "him",
            "his", "how", "however", "i", "i.e.", "if", "in", "into", "it", "it's", "its",
            "me", "more", "most", "mr", "my", "near", "nor", "now", "no", "not", "or", "on", "of", "onto",
            "other", "our", "out", "over", "really", "said", "same", "she",
            "should", "shouldn't", "since", "so", "some", "such",
            "than", "that", "the", "their", "them", "then", "there", "thereby",
            "therefore", "therefrom", "therein", "thereof", "thereon", "thereto",
            "therewith", "these", "they", "this", "those", "through", "thus", "to",
            "too", "under", "until", "unto", "upon", "us", "very", "was", "wasn't",
            "we", "were", "what", "when", "where", "whereby", "wherein", "whether",
            "which", "while", "who", "whom", "whose", "why", "with", "without",
            "would", "you", "your", "yours", "yes"};
}


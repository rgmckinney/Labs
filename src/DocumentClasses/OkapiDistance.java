package DocumentClasses;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ryan on 10/15/2016.
 */
public class OkapiDistance implements DocumentDistance {
    private static HashMap<String, Integer> totalWordCounts = new HashMap<>();

    public double findDistance(TextVector query, TextVector document, DocumentCollection documents, int docNum) {
        double okapiTotal = 0;
        int docLength = document.getTotalWordCount();
        int numDocs = documents.getSize();
        double k1=1.2, b=0.75, k2=100;

        double total = 0.0;
        int count = 0;

        for (Map.Entry<Integer, TextVector> subentry : documents.getEntrySet()) {
            DocumentVector curDocVec = (DocumentVector) subentry.getValue();
            total += curDocVec.getTotalWordCount();
            count++;
        }

        double avgDocLength = total / count;

        for (Map.Entry<String, Integer> entry : query.getRawVectorEntrySet()) {
            String curWord = entry.getKey();

            if (curWord != null && !curWord.isEmpty()) {
                int numDocOccurrences = document.getRawFrequency(curWord);
                int numQOccurrences = query.getRawFrequency(curWord);

                int numContainWord;
                if (totalWordCounts.containsKey(curWord)) {
                    numContainWord = totalWordCounts.get(curWord);
                }
                else {
                    numContainWord = 0;
                    for (Map.Entry<Integer, TextVector> subentry : documents.getEntrySet()) {
                        DocumentVector curDocVec = (DocumentVector) subentry.getValue();
                        if (curDocVec.contains(curWord))
                            numContainWord++;
                    }
                    totalWordCounts.put(curWord, numContainWord);
                }

                double part1 = Math.log((numDocs - numContainWord + 0.5) / (numContainWord + 0.5));
                double part2 = ((k1 + 1) * numDocOccurrences) / (k1 * (1 - b + b * (docLength / avgDocLength)) + numDocOccurrences);
                double part3 = ((k2 + 1) * numQOccurrences) / (k2 + numQOccurrences);
                double okapiScore = part1 * part2 * part3;
                okapiTotal += okapiScore;
            }
        }

        return okapiTotal;
    }
}

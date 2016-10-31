package DocumentClasses;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Ryan on 10/7/2016.
 */
public class QueryVector extends TextVector {
    private HashMap<String, Double> normalizedVector = new HashMap<String, Double>();

    public Set<Map.Entry<String, Double>> getNormalizedVectorEntrySet() {
        return normalizedVector.entrySet();
    }

    public void normalize(DocumentCollection dc) {
        int numDocuments = dc.getSize();

        for (Map.Entry<String, Integer> curWordEntry: rawVector.entrySet()) {
            String curWord = curWordEntry.getKey();
            double curWordCount = curWordEntry.getValue();

            double numOccurrences = 0;
            for (Map.Entry<Integer, TextVector> curDocEntry: dc.getEntrySet()) {
                DocumentVector docVector = (DocumentVector)curDocEntry.getValue();
                if (docVector.contains(curWord))
                    numOccurrences++;
            }

            int curMaxWord = 0;
            for (Map.Entry<String, Integer> entry : rawVector.entrySet()) {
                if (entry.getValue() > curMaxWord)
                    curMaxWord = entry.getValue();
            }

            double tf = 0.5 + 0.5*curWordCount*curMaxWord;
            double idf = Math.log(numDocuments/numOccurrences)/Math.log(2);

            if (numOccurrences != 0)
                normalizedVector.put(curWord, tf*idf);
            else
                normalizedVector.put(curWord, 0.0);
        }
    }

    public double getNormalizedFrequency(String word) {
        if (!normalizedVector.containsKey(word))
            return 0.0;

        return normalizedVector.get(word);
    }
}

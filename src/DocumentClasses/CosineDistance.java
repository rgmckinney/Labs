package DocumentClasses;

import java.util.Map;

/**
 * Created by Ryan on 10/8/2016.
 */
public class CosineDistance implements DocumentDistance {
    public double findDistance(TextVector query, TextVector document, DocumentCollection documents, int docNum) {
        double numeratorTotal = 0;
        double docTotal = 0;
        double qTotal = 0;

        // DEBUGGING
        if (docNum == 51) {
            System.out.println("QUERY");
            for (Map.Entry<String, Double> qEntry : query.getNormalizedVectorEntrySet()) {
                System.out.println(qEntry.getKey() +": "+qEntry.getValue());
            }

            System.out.println("\n\n\n");
            System.out.println("DOCUMENT");
            for (Map.Entry<String, Double> dEntry : document.getNormalizedVectorEntrySet()) {
                System.out.println(dEntry.getKey() +": "+dEntry.getValue());
            }
        }

        for (Map.Entry<String, Double> curEntry: query.getNormalizedVectorEntrySet()) {
            String curWord = curEntry.getKey();
            if (!curWord.equals("")) {
                double qValue = curEntry.getValue();
                double dValue = document.getNormalizedFrequency(curWord);

                numeratorTotal += qValue * dValue;
                docTotal += dValue * dValue;
                qTotal += qValue * qValue;
            }
        }

        double denominator = Math.sqrt(docTotal)*Math.sqrt(qTotal);
        double finalScore = numeratorTotal/denominator;

        //System.out.println(Double.toString(docTotal)+"     "+Double.toString(qTotal));
        return finalScore;
    }
}

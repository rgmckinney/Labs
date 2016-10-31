package DocumentClasses;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Ryan on 9/27/2016.
 */
public abstract class TextVector implements Serializable {
    public abstract Set<Map.Entry<String, Double>> getNormalizedVectorEntrySet();
    public abstract void normalize(DocumentCollection dc);
    public abstract double getNormalizedFrequency(String word);

    public double getL2Norm() {
        double total = 0;
        for (String curWord : rawVector.keySet()) {
            total += Math.pow(getNormalizedFrequency(curWord), 2);
        }

        return Math.sqrt(total);
    }

    public ArrayList<Integer> findClosestDocuments() {
        return new ArrayList<Integer>();
    }

    HashMap<String, Integer> rawVector = new HashMap<String, Integer>();

    public Set<Map.Entry<String, Integer>> getRawVectorEntrySet() {
        return rawVector.entrySet();
    }

    public void add(String toAdd) {
        try {
            rawVector.containsKey(toAdd);
            int curVal = rawVector.get(toAdd);
            curVal++;
            rawVector.put(toAdd, curVal);
        } catch (NullPointerException e) {
            rawVector.put(toAdd, 1);
        }
    }

    public boolean contains(String toCheck) {
        return rawVector.containsKey(toCheck);
    }

    public int getRawFrequency(String toGet) {
        if (rawVector.containsKey(toGet))
            return rawVector.get(toGet);

        return 0;
    }

    public int getTotalWordCount() {
        int total = 0;

        Iterator it = rawVector.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            total += (int)pair.getValue();
        }

        return total;
    }

    public int getDistinctWordCount() {
        return rawVector.values().size();
    }

    public int getHighestRawFrequency() {
        int max = 0;

        Iterator it = rawVector.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if ((int)pair.getValue() > max) {
                max = (int)pair.getValue();
            }
        }

        return max;
    }

    public String getMostFrequentWord() {
        String maxWord = "";
        int max = 0;

        Iterator it = rawVector.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if ((int)pair.getValue() > max) {
                max = (int)pair.getValue();
                maxWord = (String)pair.getKey();
            }
        }

        return maxWord;
    }
}

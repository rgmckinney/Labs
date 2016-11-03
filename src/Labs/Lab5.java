package Labs;

import TransactionClasses.ItemSet;

import java.util.ArrayList;
import java.util.*;
import java.io.*;


public class Lab5 {
    private static ArrayList<ItemSet> transactions = new ArrayList<>();
    private static ArrayList<Integer> items = new ArrayList<>();
    private static HashMap<Integer, ArrayList<ItemSet>> frequentItemSet = new HashMap<>();
    private static double minsup = 0.01;

    public static void main(String[] args) throws FileNotFoundException {
        process("./src/files/shopping_data.csv");
        findFrequentSingleItemSets();
        ArrayList<ItemSet> curFrequentItemSets = new ArrayList<>();
        int k = 2;
        do {
            ArrayList<ItemSet> possibleFrequentSets = joinFrequentItemSets(k++);
            curFrequentItemSets = findFrequentItemSets(possibleFrequentSets);
        } while (!curFrequentItemSets.isEmpty());

        System.out.println(frequentItemSet.toString());
    }

    public static void process(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        while (scanner.hasNextLine()) {
            String[] curInputs = scanner.nextLine().split(", ");
            int transNum = Integer.parseInt(curInputs[0]);

            ItemSet curItemSet = new ItemSet();
            for (int i=1; i < curInputs.length; i++) {
                curItemSet.addItem(Integer.parseInt(curInputs[i]));
                items.add(Integer.parseInt(curInputs[i]));
            }
            transactions.add(curItemSet);
        }
    }

    public static boolean findFrequentItemSets(int k) {
        int numRequired = (int)Math.ceil(transactions.size()*minsup);
        HashMap<ItemSet, Integer> itemSetCounts = new HashMap<>();
        HashSet<ItemSet> frequentItemSets = new HashSet<>();
        int count;

        return false;
    }

    public static boolean isFrequent(ItemSet itemSet) {
        return false;
    }

    public static void findFrequentSingleItemSets() {
        int numRequired = (int)Math.ceil(transactions.size()*minsup);
        HashMap<Integer, Integer> itemCounts = new HashMap<>();
        HashSet<ItemSet> frequentSingleItemSets = new HashSet<>();
        int count;

        for (ItemSet curItemSet : transactions) {
            Iterator<Integer> iter = curItemSet.iterator();
            while (iter.hasNext()) {
                int curItem = iter.next();

                if (itemCounts.containsKey(curItem)) {
                    count = itemCounts.get(curItem) + 1;
                }
                else {
                    count = 1;
                }

                itemCounts.put(curItem, count);
            }
        }

        // Add to frequent single itemsets if item occurs enough to be considered frequent
        for (Map.Entry<Integer, Integer> entry : itemCounts.entrySet()) {
            if (entry.getValue() >= numRequired) {
                ItemSet curItemSet = new ItemSet();
                curItemSet.addItem(entry.getKey());
                frequentSingleItemSets.add(curItemSet);
            }
        }

        frequentItemSet.put(1, new ArrayList<>(frequentSingleItemSets));
    }

    private static ArrayList<ItemSet> joinFrequentItemSets(int k) {
        ArrayList<ItemSet> frequentItemSets = frequentItemSet.get(k-1);
        HashMap<Set, Integer> itemSetOccurences = new HashMap<>();
        ItemSet curItemSet;

        // Join all combinations of frequent sets of size k
        for (int i=0; i < frequentItemSets.size(); i++) {
            for (int j=i+1; j < frequentItemSets.size(); j++) {
                // Combine sets i and j
                curItemSet = new ItemSet(frequentItemSets.get(i));
                curItemSet.join(frequentItemSets.get(j));

                // Record joined set in occurrences map if set is size k
                if (curItemSet.size() == k) {
                    int count;
                    if (itemSetOccurences.containsKey(curItemSet.getSet())) {
                        count = itemSetOccurences.get(curItemSet.getSet()) + 1;
                    } else {
                        count = 1;
                    }
                    itemSetOccurences.put(curItemSet.getSet(), count);
                }
            }
        }

        ArrayList<ItemSet> possibleFrequentItemSets = new ArrayList<>();
        int numRequired = (int)Math.ceil((Math.pow(k-1, 2) + (k-1))/2.0);
        // Iterate through all size k joined sets, checking counts
        for (Map.Entry<Set, Integer> entry : itemSetOccurences.entrySet()) {
            // If this sets count is the expected for a frequent set of size k
            if (entry.getValue() >= numRequired) {
                possibleFrequentItemSets.add(new ItemSet(entry.getKey()));
            }
        }

        return possibleFrequentItemSets;
    }

    private static ArrayList<ItemSet> findFrequentItemSets(ArrayList<ItemSet> possibleItemSets) {
        // Initialize hashmap to counts occurrences of all the possible item sets
        HashMap<Set, Integer> possibleItemCounts = new HashMap<>();
        int count, k;

        // Get size k of next level of item sets or end if no possible sets
        if (!possibleItemSets.isEmpty()) {
            k = possibleItemSets.get(0).size();
        }
        else {
            return new ArrayList<>();
        }

        for (ItemSet curItemSet : transactions) {
            for (ItemSet curPossibleItemSet : possibleItemSets) {
                // If the current item set contains the possible item set, inc its count
                if (curItemSet.contains(curPossibleItemSet)) {
                    if (possibleItemCounts.containsKey(curPossibleItemSet.getSet())) {
                        count = possibleItemCounts.get(curPossibleItemSet.getSet()) + 1;
                    }
                    else {
                        count = 1;
                    }
                    possibleItemCounts.put(curPossibleItemSet.getSet(), count);
                }
            }
        }

        // Number of occurrences an item set needs to be considered frequent
        int frequentCount = (int)Math.ceil(minsup*transactions.size());
        ArrayList<ItemSet> frequentItemSets = new ArrayList<>();
        for (Map.Entry<Set, Integer> entry : possibleItemCounts.entrySet()) {
            if (entry.getValue() >= frequentCount) {
                frequentItemSets.add(new ItemSet(entry.getKey()));
            }
        }

        frequentItemSet.put(k, frequentItemSets);
        return frequentItemSets;
    }
}

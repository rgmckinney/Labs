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
        System.out.println(frequentItemSet.get(1).size());
        System.out.println(joinFrequentItemSets(2).toString());
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

                if (itemCounts.containsKey(curItem))
                    count = itemCounts.get(curItem);
                else
                    count = 0;

                if (++count >= numRequired)
                    frequentSingleItemSets.add(curItemSet);

                itemCounts.put(curItem, count);
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
        // Iterate through all size k joined sets, checking counts
        for (Map.Entry<Set, Integer> entry : itemSetOccurences.entrySet()) {
            // If this sets count is the expected for a frequent set of size k
            if (entry.getValue() >= (k-1)*2) {
                possibleFrequentItemSets.add(new ItemSet(entry.getKey()));
            }
        }

        for (ItemSet set : possibleFrequentItemSets)
            System.out.println(set.toString());

        return possibleFrequentItemSets;
    }

    private static ArrayList<ItemSet> findFrequentItemSets(ArrayList<ItemSet> possibleItemSets, int k) {
        for (ItemSet curItemSet : possibleItemSets) {
            isFrequent(curItemSet);
        }
        return null;
    }
/*
    public static ArrayList<ItemSet> possibleCombinations(int k) {
        // get all possible individual items from the k-1 frequent item set
        HashSet<Integer> possibleItemsSet = new HashSet<>();
        for (ItemSet curItemSet : frequentItemSet.get(k-1)) {
            Iterator iter = curItemSet.iterator();
            while (iter.hasNext()) {
                possibleItemsSet.add((Integer)iter.next());
            }
        }

        // generate all possible combinations of size k from the possible items set
        ArrayList<ItemSet> possibleCombinations = new ArrayList<>();
        for (Set curSet : powerSet(possibleItemsSet, k)) {
            if (curSet.size() == k) {
                possibleCombinations.add(new ItemSet(curSet));
            }
        }

        return possibleCombinations;
    }

    private static Set<Set<Integer>> powerSet(Set<Integer> possibleItems, int k) {
        Set<Set<Integer>> sets = new HashSet<>();

        if (possibleItems.isEmpty()) {
            sets.add(new HashSet<>());
            return sets;
        }

        List<Integer> list = new ArrayList<Integer>(possibleItems);
        int head = list.get(0);
        Set<Integer> rest = new HashSet<Integer>(list.subList(1, list.size()));
        for (Set<Integer> curSet : powerSet(rest, k)) {
            Set<Integer> newSet = new HashSet<>();
            newSet.add(head);
            newSet.addAll(curSet);
            sets.add(newSet);
            sets.add(curSet);
        }

        return sets;
    } */
}

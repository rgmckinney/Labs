package Labs;

import java.util.*;
import TransactionClasses.*;

public class Lab6 extends Lab5 {
    public static void main(String[] args) {
        HashSet<Integer> test = new HashSet<>();
        test.add(4);
        test.add(6);
        test.add(8);
        test.add(10);
        test.add(2);
        System.out.println(combinations(test, 4).toString());
    }

    public static ArrayList<Rule> split(ItemSet itemSet) {

        //for (int leftSize = 1; leftSize < itemSet.size(); leftSize++) {



        return null;
    }

    public static void generateRules() {

    }

    public static boolean isMinConfidenceMet(Rule r) {
        return false;
    }

    private static ArrayList<ItemSet> getCombinations(ItemSet itemSet, int k) {
        ArrayList<Integer> itemSetArray = new ArrayList<>(itemSet.getSet());
        HashSet<Integer> curSet;



        return null;
    }

    private static HashSet<HashSet<Integer>> combinations(HashSet<Integer> itemChoices, int k) {
        if (k==0) {
            HashSet<HashSet<Integer>> baseChoice = new HashSet<>();
            for (int curItem : itemChoices) {
                baseChoice.add(new HashSet<>(curItem));
            }
            System.out.println(itemChoices.toString());
            return baseChoice;
        }

        HashSet<HashSet<Integer>> newItemSets = new HashSet<>();
        ArrayList<HashSet<Integer>> curItemSets;
        HashSet<Integer> tempItemSet, tempItemChoices;
        for (int curItemChoice : itemChoices) {
            tempItemChoices = (HashSet)itemChoices.clone();
            tempItemChoices.remove(curItemChoice);
            for (HashSet curSet : combinations(tempItemChoices, k-1)) {
                tempItemSet = (HashSet)curSet.clone();
                tempItemSet.add(curItemChoice);
                newItemSets.add(tempItemSet);
            }
            /*
            for (HashSet<Integer> curItemSet : combinations(tempItemChoices, k-1)) {
                    tempItemSet = (HashSet) curItemSet.clone();
                    tempItemSet.add(curItemChoice);
                    newItemSets.add(tempItemSet);
                    //System.out.println(newItemSets.toString());
            } */
        }

        return newItemSets;
    }
/*
    private static ArrayList<HashSet<Integer>> combinations(ArrayList<HashSet<Integer>> itemSets, int k) {
        ArrayList<HashSet<Integer>> comboItemSets = new ArrayList<>();
        if (k > 1) {
            ArrayList<HashSet<Integer>> smallerItemSets = combinations(itemSets, k - 1);
            HashSet<Integer> tempItemSet;
            // Iterate through smaller item set, adding another item to the set
            for (HashSet<Integer> curSet : smallerItemSets) {
                for ()
                tempItemSet = (HashSet)curSet.clone();
                tempItemSet.add()
            }
        }
        else {
            // Find all base item sets of size 1 that are in itemSets
            HashSet<Integer> allItems = new HashSet<>();
            for (HashSet<Integer> curSet : itemSets) {
                for (int curItem : curSet) {
                    allItems.add(curItem);
                }
            }
        }

        for (HashSet<Integer> curItemSet : itemSets) {
            for (int curItem : curItemSet) {
                tempItemSet = (HashSet<Integer>)curItemSet.clone();
                tempItemSet.add(curItem);
                if (tempItemSet.size() == k) {
                    comboItemSets.add(tempItemSet);
                }
            }
        }

        combinations(???, k-1)
    } */
}

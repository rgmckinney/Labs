package GraphClasses;

import java.util.*;
import java.io.*;

/**
 * Created by Ryan on 10/24/2016.
 */
public class Graph {
    private HashSet<Integer> nodes = new HashSet<>();
    private HashMap<Integer, ArrayList<Integer>> adj = new HashMap<>();
    private HashMap<Integer, Integer> out = new HashMap<>();

    public void printVars(String input) {
        if (input.equals("adj")) {
            System.out.println("Adjacency Input Size: " + adj.size());
            System.out.println("Printing...");
            for (Map.Entry<Integer, ArrayList<Integer>> entry : adj.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }

    public boolean checkNorm(HashMap<Integer, Double> pageRank) {
        double total = 0.0;

        for (Map.Entry<Integer, Double> entry : pageRank.entrySet()) {
            total += entry.getValue();
        }

        return Math.abs(total - 1.0) < 0.000001;
    }

    public void addEdge (int startNode, int endNode) {
        nodes.add(startNode);
        nodes.add(endNode);

        ArrayList<Integer> inputNodes;
        if (adj.containsKey(endNode))
            inputNodes = adj.get(endNode);
        else
            inputNodes = new ArrayList<>();
        inputNodes.add(startNode);
        adj.put(endNode, inputNodes);

        int outCount = 1;
        if (out.containsKey(startNode))
            outCount = out.get(startNode) + 1;
        out.put(startNode, outCount);
    }
// 821 -> 820 DEBUG
    public HashMap<Integer, Double> initializePageRank() {
        HashMap<Integer, Double> pageRank = new HashMap<>();
        for (int curNode : nodes) {
            pageRank.put(curNode, 1.0/nodes.size());
        }

        return pageRank;
    }

    public HashMap<Integer, Double> iteratePageRank (HashMap<Integer, Double> pageRank, double d) {
        int numNodes = nodes.size();
        HashMap<Integer, Double> newPageRank = new HashMap<>();
        System.out.println(out.get(963));
        for (int curNode : pageRank.keySet()) {
            // If there are nodes that input into curNode
            if (adj.containsKey(curNode)) {
                double newScore = (1.0 - d) * (1.0 / numNodes) + (d * calculateIncPrestige(curNode, pageRank));
                newPageRank.put(curNode, newScore);
            }
            // If curNode has no input nodes
            else {
                newPageRank.put(curNode, pageRank.get(curNode));
            }
        }

        return newPageRank;
    }

    private double calculateIncPrestige(int curNode, HashMap<Integer, Double> pageRank) {
        double incPrestige = 0.0;
        for (int curAdjNode : adj.get(curNode)) {
            incPrestige += (1.0 / out.get(curAdjNode)) * pageRank.get(curAdjNode);
            if (curNode == 820 && curAdjNode == 821) System.out.println(curAdjNode+"  "+((1.0 / out.get(curAdjNode)) * pageRank.get(curAdjNode)));
        }
        //if (curNode == 963) System.exit(0);
        return incPrestige;
    }

    public HashMap<Integer, Double> normalize(HashMap<Integer, Double> pageRank) {
        // Calculate magnitude
        double magnitude = 0.0;
        for (Map.Entry<Integer, Double> entry : pageRank.entrySet()) {
            magnitude += Math.abs(entry.getValue());
        }
        //magnitude = Math.pow(magnitude, 1.0/pageRank.size());

        for (Map.Entry<Integer, Double> entry : pageRank.entrySet()) {
            pageRank.put(entry.getKey(), entry.getValue()/magnitude);
        }

        return pageRank;
    }

    public static double findDistance(HashMap<Integer, Double> oldPageRank, HashMap<Integer, Double> newPageRank) {
        double total = 0;

        for (Map.Entry<Integer, Double> entry : newPageRank.entrySet()) {
            double newScore = entry.getValue();
            double oldScore = oldPageRank.get(entry.getKey());
            total += Math.abs(newScore - oldScore);
        }

        return total;
    }
}

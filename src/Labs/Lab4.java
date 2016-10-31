package Labs;

import GraphClasses.Graph;

import java.util.*;
import java.io.*;

/**
 * Created by Ryan on 10/24/2016.
 */

/**
 * Bug: pretty sure in calculate incoming prestige from all nodes
 */

public class Lab4 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("./src/files/graph.csv"));
        Graph graph = new Graph();
        while (scanner.hasNext()) {
            String[] curInput = scanner.nextLine().split(",");
            int startNode = Integer.parseInt(curInput[0]);
            int endNode = Integer.parseInt(curInput[2]);
            graph.addEdge(startNode, endNode);
        }

        HashMap<Integer, Double> oldPageRank = graph.initializePageRank();
        HashMap<Integer, Double> newPageRank = graph.iteratePageRank(oldPageRank, 0.9);
        newPageRank = graph.normalize(newPageRank);

        while (Graph.findDistance(oldPageRank, newPageRank) >= 0.001) {
            oldPageRank = (HashMap<Integer, Double>) newPageRank.clone();
            newPageRank = graph.iteratePageRank(oldPageRank, 0.9);
            newPageRank = graph.normalize(newPageRank);
            assert(graph.checkNorm(newPageRank));
        }

        LinkedList<Map.Entry<Integer, Double>> sortedScores = sort(newPageRank);
        int count = 0;
        System.out.print('[');
        for (Map.Entry<Integer, Double> entry : sortedScores) {
            count++;
            if (count == 20) {
                System.out.println(entry.getKey() + "]");
                break;
            }
            else {
                System.out.print(entry.getKey() + ", ");
                System.out.println(entry.getValue());
            }
        }
    }

    public static LinkedList sort(HashMap<Integer, Double> pageRank) {
        LinkedList pageRankList = new LinkedList(pageRank.entrySet());

        Collections.sort(pageRankList,
                new Comparator() {
                    public int compare (Object entry1, Object entry2) {
                        Double value1 = ((Map.Entry<Integer, Double>)entry1).getValue();
                        Double value2 = ((Map.Entry<Integer, Double>)entry2).getValue();

                        return value2.compareTo(value1);
                    }
        });

        return pageRankList;
    }
}

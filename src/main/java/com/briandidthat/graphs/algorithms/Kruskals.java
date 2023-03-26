package com.briandidthat.graphs.algorithms;

import com.briandidthat.graphs.model.EdgeInfo;
import com.briandidthat.graphs.model.Graph;

import java.util.*;

/**
 * This is an implementation of kruskals minimum spanning tree algorithm. It is a greedy algorithm as it finds a
 * minimum spanning tree for a connected weighted graph adding increasing cost arcs at each step. However, this
 * algorithm can be used on a forest as well (un-connected graph). An edge will be represented in string format, "01"
 * representing that an edge between node 0 and node 1 exist.
 */

public class Kruskals {
    public void spanningTree(Graph graph) {
        // This priority queue will allow us to store and access edges on the basis of their weights.
        PriorityQueue<EdgeInfo> queue = new PriorityQueue<>(Comparator.comparing(EdgeInfo::getWeight));

        // Add all edges into the priority queue
        for(int i = 0; i < graph.getNumVertices(); i++) {
            for (int neighbor : graph.getAdjacentVertices(i)) {
                queue.add(new EdgeInfo(i, neighbor, graph.getWeightedEdge(i, neighbor)));
            }
        }
        // HashSet to track what vertices we've visited.
        Set<Integer> visited = new HashSet<>();
        Set<EdgeInfo> spanningTree = new HashSet<>();
        Map<Integer, Set<Integer>> edgeMap = new HashMap<>();
        // This edge map will track the edges added to the spanning to see if a cycle exists.
        for (int v = 0; v < graph.getNumVertices(); v++) {
            edgeMap.put(v, new HashSet<>());
        }
        // The spanning tree should have (numVertices - 1) Edges
        while (!queue.isEmpty() && spanningTree.size() < graph.getNumVertices() - 1) {
            EdgeInfo currentEdge = queue.poll();
            // Add the new edge to the edge map and check if it ends up with a cycle. IF so, discard this edge and
            // get the next edge from the priority queue.
            edgeMap.get(currentEdge.getVertex1()).add(currentEdge.getVertex2());
            if (hasCycle(edgeMap)) {
                edgeMap.get(currentEdge.getVertex1()).remove(currentEdge.getVertex2());
                continue;
            }

            spanningTree.add(currentEdge);

            // Add both vertices to the visited list. The set will ensure that only one copy of the vertex exists.
            visited.add(currentEdge.getVertex1());
            visited.add(currentEdge.getVertex2());
        }

        // Ensure that all vertices have been covered by the spanning tree.
        if (visited.size() != graph.getNumVertices()) {
            System.out.println("Minimum spanning tree is not possible.");
        } else {
            System.out.println("Minimum spanning tree using Kruskals Algorithm:");
            for (EdgeInfo edgeInfo : spanningTree) {
                System.out.print(edgeInfo);
                System.out.print(" -> ");
            }
        }
    }

    private boolean hasCycle(Map<Integer, Set<Integer>> edgeMap) {
        // Iterate over every vertex in the edgeMap and explore all the vertices in the spanning tree.
        for (Integer source : edgeMap.keySet()) {
            LinkedList<Integer> queue = new LinkedList<>();
            queue.add(source);
            Set<Integer> visited = new HashSet<>();

            while (!queue.isEmpty()) {
                int currentVertex = queue.pollFirst();
                // IF we ever revisit a vertex, that means there is a cycle present in the spanning tree.
                if (visited.contains(currentVertex)) {
                    return true;
                }
                // Mark the current vertex as visited.
                visited.add(currentVertex);
                queue.addAll(edgeMap.get(currentVertex)); // Add the adjacent vertices of current to queue.
            }
        }

        return false;
    }
}

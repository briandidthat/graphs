package com.briandidthat.graphs.algorithms;

import com.briandidthat.graphs.model.DistanceEdgeInfo;
import com.briandidthat.graphs.model.Graph;
import com.briandidthat.graphs.model.VertexInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * This is an implementation of the the shortest distance with a slight difference. Here we are to find the shortest
 * path from a source to a destination node, considering the num of edges as well. Explanation: IF two paths have the
 * same accumulated weight of edges between them, the one with the least amount of edges takes precedence.
 */

public class ShortestDistance {
    public Map<Integer, DistanceEdgeInfo> buildDistanceTable(Graph graph, int source) {
        // This Priority queue checks both the distance and number of edges for a vertex. IF the distance is the same,
        // only then the number of edges is checked.
        Map<Integer, DistanceEdgeInfo> distanceTable = new HashMap<>();
        PriorityQueue<VertexInfo> queue = new PriorityQueue<>((v1, v2) -> {
            if (v1.getDistance() != v2.getDistance()) {
                return ((Integer) v1.getDistance()).compareTo(v2.getDistance());
            }
            return ((Integer) v1.getNumEdges()).compareTo(v2.getNumEdges());
        });
        // Add an entry to distance table for each vertex in the graph
        for (int i = 0; i < graph.getNumVertices(); i++) {
            distanceTable.put(i, new DistanceEdgeInfo());
        }

        distanceTable.get(source).setInfo(source, 0, 0);

        VertexInfo sourceVertex = new VertexInfo(source, 0, 0);
        queue.add(sourceVertex);

        Map<Integer, VertexInfo> vertexInfoMap = new HashMap<>();
        vertexInfoMap.put(source, sourceVertex);

        while (!queue.isEmpty()) {
            // Remove the highest priority element form the queue
            VertexInfo currentVertexInfo = queue.poll();

            for (Integer neighbor : graph.getAdjacentVertices(currentVertexInfo.getVertexId())) {
                // Get the distance and number of edges from the current vertex to the neighbor
                int distance = distanceTable.get(currentVertexInfo.getVertexId()).getDistance() +
                        graph.getWeightedEdge(currentVertexInfo.getVertexId(), neighbor);
                int edges = distanceTable.get(currentVertexInfo.getVertexId()).getNumEdges() + 1;

                int neighborDistance = distanceTable.get(neighbor).getDistance();
                if (neighborDistance > distance || (neighborDistance == distance) &&
                        (distanceTable.get(neighbor).getNumEdges() > edges)) {

                    // Update the distance table for the neighbor with the new information
                    distanceTable.get(neighbor).setInfo(currentVertexInfo.getVertexId(), distance, edges);

                    VertexInfo neighborVertexInfo = vertexInfoMap.get(neighbor);
                    if (neighborVertexInfo != null) {
                        // Remove vertex info from the queue since we are updating it
                        queue.remove(neighborVertexInfo);
                    }

                    // Reassign the vertex info values to the updated values
                    neighborVertexInfo = new VertexInfo(neighbor, distance, edges);
                    queue.add(neighborVertexInfo);
                    vertexInfoMap.put(neighbor, neighborVertexInfo);
                }
            }
        }

        return distanceTable;
    }

    public void findShortestPath(Graph graph, Integer source, Integer destination) {
        Map<Integer, DistanceEdgeInfo> distanceTable = buildDistanceTable(graph, source);
        // Since we will be backtracking, we will be using a stack. Start
        Stack<Integer> stack = new Stack<>();
        stack.push(destination);

        int previousVertex = distanceTable.get(destination).getLastVertex();
        while (previousVertex != -1 && previousVertex != source) {
            stack.push(previousVertex);
            previousVertex = distanceTable.get(previousVertex).getLastVertex();
        }
        // Because we've initialized the previous vertex to -1, if it remains the same there is no path from source to
        // the destination.
        if (previousVertex == -1) {
            System.out.println("There is no path from node: " + source + " to node: " + destination);
        } else {
            System.out.print("Shortest path considering the number of edges is: " + source);
            while (!stack.isEmpty()) {
                System.out.print(" -> " + stack.pop());
            }
            System.out.println(" Djikstra done!");
        }

    }
}

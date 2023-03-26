package com.briandidthat.graphs.algorithms;

import com.briandidthat.graphs.model.DistanceInfo;
import com.briandidthat.graphs.model.Graph;
import com.briandidthat.graphs.model.VertexInfo;

import java.util.*;

/**
 * This is an implementation of Djikstra's path finding algorithm using a distance table. This is an example of a
 * "Greedy Algorithm".
 */
public class Djikstras {
    public void findShortestPath(Graph graph, Integer source, Integer destination) {
        Map<Integer, DistanceInfo> distanceTable = buildDistanceTable(graph, source);
        // Since this will require backtracking, we will use a stack.
        Stack<Integer> stack = new Stack<>();
        stack.push(destination);

        int previousVertex = distanceTable.get(destination).getLastVertex();
        while (previousVertex != -1 && previousVertex != source) {
            // Add the last vertex of each node and add it to the stack
            stack.push(previousVertex);
            previousVertex = distanceTable.get(previousVertex).getLastVertex();
        }
        // IF there was no valid last vertex in the distance table, no path exists from source to destination.
        if (previousVertex == -1) {
            System.out.println("There is no path from source: " + source + " to destination: " + destination);
        } else {
            System.out.print("Shortest path is: " + source);
            while(!stack.isEmpty()) {
                System.out.print(" -> " + stack.pop());
            }
            System.out.println(" We have completed Djikstras Algorithm.");
        }
    }


    private Map<Integer, DistanceInfo> buildDistanceTable(Graph graph, int source) {
        Map<Integer, DistanceInfo> distanceTable = new HashMap<>();
        // This Priority Queue will return nodes in order of the shortest distance from the source. "Greedy Solution"
        PriorityQueue<VertexInfo> queue = new PriorityQueue<>(Comparator.comparingInt(VertexInfo::getDistance));
        Map<Integer, VertexInfo> vertexInfoMap = new HashMap<>();
        // For each vertex we will initialize a distance info entry.
        for (int i = 0; i < graph.getNumVertices(); i++) {
            distanceTable.put(i, new DistanceInfo(true));
        }

        distanceTable.get(source).setDistance(0);
        distanceTable.get(source).setLastVertex(source);
        // Initialize and add the source to the priority queue since we will explore it first.
        VertexInfo sourceVertex = new VertexInfo(source, 0);
        queue.add(sourceVertex);
        vertexInfoMap.put(source, sourceVertex);

        while (!queue.isEmpty()) {
            // Access the priority queue to find the closest vertex.
            VertexInfo vertexInfo = queue.poll();
            int currentVertex = vertexInfo.getVertexId();

            for (Integer neighbor : graph.getAdjacentVertices(currentVertex)) {
                // Get the new distance and account for the weighted edge.
                int distance = distanceTable.get(currentVertex).getDistance() +
                        graph.getWeightedEdge(currentVertex, neighbor);
                // Check if we have found a shorter path to the neighbor. If so, update the existing distance and vertex.
                if (distance < distanceTable.get(neighbor).getDistance()) {
                    distanceTable.get(neighbor).setDistance(distance);
                    distanceTable.get(neighbor).setLastVertex(currentVertex);
                    // Since we've found a new shortest path to the neighbor, remove the old one from the queue.
                    VertexInfo neighborVertexInfo = vertexInfoMap.get(neighbor);
                    if (neighborVertexInfo != null) {
                        queue.remove(neighborVertexInfo);
                    }
                    // Enter the neighbor with the updated values.
                    neighborVertexInfo = new VertexInfo(neighbor, distance);
                    queue.add(neighborVertexInfo);
                    vertexInfoMap.put(neighbor, neighborVertexInfo);
                }
            }
        }
        return distanceTable;
    }
}

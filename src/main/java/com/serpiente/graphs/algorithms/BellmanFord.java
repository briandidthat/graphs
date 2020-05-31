package com.serpiente.graphs.algorithms;

import com.serpiente.graphs.model.Graph;

import java.util.*;

/**
 * This class is an implementation of the BellmanFord Algorithm used to find the shortest path on a weighted graph with
 * possible negative weights. To avoid integer overflow, we will use a large integer value in our distance info class
 * rather than Integer.MaxValue.
 */
public class BellmanFord {
    private static class DistanceInfo {
        private int distance;
        private int lastVertex;

        private DistanceInfo() {
            this.distance = 100000;
            this.lastVertex = -1;
        }
    }

    public void findShortestPath(Graph graph, Integer source, Integer destination) {
        Map<Integer, DistanceInfo> distanceTable = buildDistanceTable(graph,source);
        // Since we'll be backtracking, we'll use a stack
        Stack<Integer> stack = new Stack<>();
        stack.push(destination);

        int previousVertex = distanceTable.get(destination).lastVertex;
        while (previousVertex != -1 && previousVertex != source) {
            // Add the last vertex of every node to the stack
            stack.push(previousVertex);
            previousVertex = distanceTable.get(previousVertex).lastVertex;
        }

        // IF there is no valid last vertex in the distance table, no path exists from source to destination.
        if (previousVertex == -1) {
            System.out.println("There is no path from source: " + source + " to destination: " + destination);
        } else {
            System.out.print("Shortest path is: " + source);
            while (!stack.isEmpty()) {
                System.out.print(" -> " + stack.pop());
            }
            System.out.println(" We have now completed BellmanFord Algorithm.");
        }
    }


    private Map<Integer, DistanceInfo> buildDistanceTable(Graph graph, int source) {
        Map<Integer, DistanceInfo> distanceTable = new HashMap<>();
        for (int i = 0; i < graph.getNumVertices(); i++) {
            distanceTable.put(i, new DistanceInfo());
        }

        // Set up the distance of the specified source.
        distanceTable.get(source).distance = 0;
        distanceTable.get(source).lastVertex = source;

        LinkedList<Integer> queue = new LinkedList<>();

        // (Relaxing) Processing all the edges in numVertices - 1 times.
        for (int numIterations = 0; numIterations < graph.getNumVertices(); numIterations++) {
            // Add Every Vertex to the queue so we're sure to access all the vertices in the graph.
            for (int v = 0; v < graph.getNumVertices(); v++) {
                queue.add(v);
            }

            // Keep track of all we have visited using a set.
            Set<String> visitedEdges = new HashSet<>();
            while(!queue.isEmpty()) {
                int currentVertex = queue.pollFirst();

                for (int neighbor: graph.getAdjacentVertices(currentVertex)) {
                    String edge = String.valueOf(currentVertex) + neighbor;
                    // Avoid visiting edges more than once on each iteration.
                    if (visitedEdges.contains(edge)) {
                        continue;
                    }
                    visitedEdges.add(edge);
                    // Calculate the new distance for comparison with the old distance.
                    int distance = distanceTable.get(currentVertex).distance +
                            graph.getWeightedEdge(currentVertex, neighbor);
                    // IF we find a shorter path to the neighbor, update the distance and last vertex.
                    if (distance < distanceTable.get(neighbor).distance) {
                        distanceTable.get(neighbor).distance = distance;
                        distanceTable.get(neighbor).lastVertex = currentVertex;
                    }
                }
            }
        }

        // Add all the vertices to the queue one more time to check for negative cycles.
        for (int v = 0; v < graph.getNumVertices(); v++) {
            queue.add(v);
        }

        // (Relaxing) processing all the edges one last time to check for a negative cycle.
        while(!queue.isEmpty()) {
            int currentVertex = queue.pollFirst();
            for (int neighbor : graph.getAdjacentVertices(currentVertex)) {
                int distance = distanceTable.get(currentVertex).distance +
                        graph.getWeightedEdge(currentVertex, neighbor);
                // IF the distance table can be updated after we have performed numVertices - 1 iterations,
                // there is a negative cycle in the graph. Throw an Exception since we cant find path in graph with
                // negative cycles.
                if (distance < distanceTable.get(neighbor).distance) {
                    throw new IllegalArgumentException("The graph has a negative cycle.");
                }
            }
        }

        return distanceTable;
    }
}

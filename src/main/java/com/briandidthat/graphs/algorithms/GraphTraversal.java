package com.briandidthat.graphs.algorithms;

import com.briandidthat.graphs.model.DistanceInfo;
import com.briandidthat.graphs.model.Graph;

import java.util.*;

public class GraphTraversal {

    // This is a traditional breadth first traversal using a queue.
    public void breadthFirstTraversal(Graph graph, int[] visited, int current) {
        LinkedList<Integer> queue = new LinkedList<>();
        queue.push(current);

        while (!queue.isEmpty()) {
            int vertex = queue.peekFirst();
            if (visited[vertex] == 1) {
                continue;
            }

            System.out.print(vertex + "->");
            visited[vertex] = 1;

            List<Integer> list = graph.getAdjacentVertices(vertex);
            for (int v : list) {
                if (visited[v] != 1) {
                    queue.push(v);
                }
            }
        }
    }

    // This is a post order traversal, where all the children will be processed before the parent node
    public void depthFirstTraversal(Graph graph, int[] visited, int current) {
        if (visited[current] == 1) {
            return;
        }

        visited[current] = 1;
        List<Integer> list = graph.getAdjacentVertices(current);
        for (int v : list) {
            depthFirstTraversal(graph, visited, v);
        }

        System.out.print(current + "->");
    }

    // The topological sort method will perform a linear ordering of the vertices for a Directed Acyclic Graph.
    public List<Integer> topologicalSort(Graph graph) {
        LinkedList<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> indegreeMap = new HashMap<>();

        for (int vertex = 0; vertex < graph.getNumVertices(); vertex++) {
            int inDegree = graph.getIndegree(vertex);
            indegreeMap.put(vertex, inDegree);

            if (inDegree == 0) {
                // Add all the vertices with a indegree of 0 to the queue of vertices to explore
                queue.add(vertex);
            }
        }

        List<Integer> sortedList = new ArrayList<>();
        while (!queue.isEmpty()) {
            // Dequeue the nodes from the list if there are more than one. IF more than one element exists then it means
            // that the graph has more than one topological sort solution.
            int vertex = queue.pollLast();
            sortedList.add(vertex);

            List<Integer> adjacentVertices = graph.getAdjacentVertices(vertex);
            for (int adjacentVertex : adjacentVertices) {
                int updatedIndegree = indegreeMap.get(adjacentVertex) - 1;
                indegreeMap.remove(adjacentVertex);
                indegreeMap.put(adjacentVertex, updatedIndegree);

                if (updatedIndegree == 0) {
                    queue.add(adjacentVertex);
                }
            }
        }

        // If the following is true, the graph has a cycle and cannot be topologically sorted.
        if (sortedList.size() != graph.getNumVertices()) {
            throw new RuntimeException("The graph has a cycle.");
        }

        return sortedList;
    }

    // This method will find the shortest path from the source to a destination for an unweighted graph
    public void findShortestPath(Graph graph, int source, int destination) {
        // Build the distance table for the whole graph starting from the source
        Map <Integer, DistanceInfo> distanceTable = buildDistanceTable(graph, source);
        // Since this will involve backtracking, we will use a stack
        Stack<Integer> stack = new Stack<>();
        stack.push(destination);

        int previousVertex = distanceTable.get(destination).getLastVertex();
        while (previousVertex != -1 && previousVertex != source) {
            // Backtrack by getting the last vertex of every node and adding it to the stack
            stack.push(previousVertex);
            previousVertex = distanceTable.get(previousVertex).getLastVertex();
        }

        // IF no valid last vertex was found in the distance table, there exists no path from source to destination
        if (previousVertex == -1) {
            System.out.print("There is no path from node:" + source + "to the destination " + destination);
        } else {
            System.out.print("The shortest path is: " + source);
            while (!stack.isEmpty()) {
                System.out.print(" -> " + stack.pop());
            }
            System.out.println();
        }
    }



    // Helper method to build a distance table for shortest path calculation
    private Map<Integer, DistanceInfo> buildDistanceTable(Graph graph, int source) {
        Map<Integer, DistanceInfo> distanceTable = new HashMap<>();
        for (int i = 0; i < graph.getNumVertices(); i++) {
            // Set an entry in the distance table for every vertex in the graph
            distanceTable.put(i, new DistanceInfo(false));
        }

        // Initialize the distance to the source and the last vertex in the path to the source.
        distanceTable.get(source).setDistance(0);
        distanceTable.get(source).setLastVertex(source);
        // Initialize a queue, and add the source
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(source);

        while(!queue.isEmpty()) {
            int currentVertex = queue.pollFirst();
            for (int v : graph.getAdjacentVertices(currentVertex)) {
                int currentDistance = distanceTable.get(v).getDistance();
                // IF the vertex is seen for the first time, then update it's entry in the distance table
                 if (currentDistance == -1) {
                     currentDistance = 1 + distanceTable.get(currentVertex).getDistance();
                     distanceTable.get(v).setDistance(currentDistance);
                     distanceTable.get(v).setLastVertex(currentVertex);
                     // Enqueue the neighbor only if it has other adjacent vertices to explore
                     if (!graph.getAdjacentVertices(v).isEmpty()) {
                         queue.add(v);
                     }
                 }
            }
        }

        return distanceTable;
    }

}

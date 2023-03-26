package com.briandidthat.graphs.algorithms;

import com.briandidthat.graphs.model.DistanceInfo;
import com.briandidthat.graphs.model.Graph;
import com.briandidthat.graphs.model.VertexInfo;

import java.util.*;

/**
 * This class is an implementation of the Prims Algorithm for minimum spanning tree. This algorithm is useful for
 * connected, weighted undirected graphs. The running time for this algorithm if using a binary heap for the priority
 * queue is O(E log V). I'll use the DistanceInfo & VertexInfo classes i've created.
 */
public class Prims {
    public void spanningTree(Graph graph, int source) {
        Map<Integer, DistanceInfo> distanceTable = new HashMap<>();
        // This Priority Queue will return nodes in the order of shortest distance from the source using a comparator.
        PriorityQueue<VertexInfo> queue = new PriorityQueue<>(Comparator.comparingInt(VertexInfo::getDistance));
        // Add a distance table entry for each node in the graph
        for (int i = 0; i < graph.getNumVertices(); i++) {
            distanceTable.put(i, new DistanceInfo(true));
        }

        distanceTable.get(source).setDistance(0);
        distanceTable.get(source).setLastVertex(source);

        Map<Integer, VertexInfo> vertexInfoMap = new HashMap<>();
        // Add the source to the priority queue and set up the mapping to the vertex info for every vertex in the queue.
        VertexInfo sourceVertexInfo = new VertexInfo(source, 0);
        queue.add(sourceVertexInfo);
        vertexInfoMap.put(source, sourceVertexInfo);

        // The spanning tree Set will contain the edges connecting all the nodes of the graph. An Edge is represented
        // by "01" if it connects vertices 0 and 1.
        Set<String> spanningTree = new HashSet<>();
        // This set will keep track of the vertices we've visited as we traverse through the graph.
        Set<Integer> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            VertexInfo vertexInfo = queue.poll();
            int currentVertex = vertexInfo.getVertexId();

            // Avoid visiting nodes we've already visited
            if (visited.contains(currentVertex)) {
                continue;
            }
            visited.add(currentVertex);

            // IF the vertex is a source, we do not have an edge yet.
            if (currentVertex != source) {
                String edge = String.valueOf(currentVertex) +
                        distanceTable.get(currentVertex).getLastVertex();
                // Add the edge to the spanning tree if it does not already exist.
                if (!spanningTree.contains(edge)) {
                    spanningTree.add(edge);
                }
            }
            // Explore all of the adjacent vertices and check what the weight is
            for (Integer neighbor : graph.getAdjacentVertices(currentVertex)) {
                // We only consider the weight of the edge in assigning the distance to a node, not the current distance
                // from the source to that node since we do not care about cumulative distance for this algorithm.
                int distance = graph.getWeightedEdge(currentVertex, neighbor);

                //IF we find the new shortest path, update the distance and the last vertex.
                if (distanceTable.get(neighbor).getDistance() > distance) {
                    distanceTable.get(neighbor).setDistance(distance);
                    distanceTable.get(neighbor).setLastVertex(currentVertex);

                    VertexInfo neighborVertex = vertexInfoMap.get(neighbor);
                    if (neighborVertex != null) {
                        // Remove the old distance from the queue.
                        queue.remove(neighborVertex);
                    }
                    // Reassign the values of the neighbor node and add to queue.
                    neighborVertex = new VertexInfo(neighbor, distance);
                    vertexInfoMap.put(neighbor, neighborVertex);
                    queue.add(neighborVertex);
                }
            }
        }

        for (String edge : spanningTree) {
            System.out.println(edge);
        }
    }
}

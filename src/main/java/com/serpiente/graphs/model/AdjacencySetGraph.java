package com.serpiente.graphs.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This graph implementation will use an adjacency set to track adjacent nodes. You would use an adjacency set or list
 * when graph is sparse (has few connections between nodes) and using O(V^2) space is not worth it.
 *
 * space-complexity: O(E + V)
 * time-complexity: isEdgePresent: O(Log Degree of V), Iteration of Edges of on a vertex O(Degree of V)
 */

public class AdjacencySetGraph implements Graph {
    private int vertices;
    private GraphType graphType;
    private List<Node> vertexList;

    public AdjacencySetGraph(int vertices, GraphType graphType) {
        this.vertices = vertices;
        this.graphType = graphType;
        this.vertexList = new ArrayList<>();

        for (int i = 0; i < vertices; i++) {
            vertexList.add(new Node(i));
        }
    }

    @Override
    public GraphType graphType() {
        return graphType;
    }

    @Override
    public void addEdge(int v1, int v2) {
        if (v1 >= vertices || v1 < 0 || v2 >= vertices || v2 < 0) {
            throw new IllegalArgumentException("Invalid vertex number.");
        }

        vertexList.get(v1).addEdge(v2);
        if (graphType == GraphType.UNDIRECTED) {
            vertexList.get(v2).addEdge(v1);
        }
    }

    @Override
    public void addEdge(int v1, int v2, int weight) {
        throw new IllegalArgumentException("Weight not implemented in Adjacency Set.");
    }

    @Override
    public int getWeightedEdge(int v1, int v2) {
        throw new IllegalArgumentException("Weight not implemented in Adjacency Set.");
    }

    @Override
    public int getNumVertices() {
        return vertices;
    }


    @Override
    public int getIndegree(int v) {
        if (v >= vertices || v < 0) {
            throw new IllegalArgumentException("Invalid vertex number.");
        }

        int inDegree = 0;
        for (int i = 0; i < vertices; i++) {
            if (getAdjacentVertices(i).contains(v)) {
                inDegree++;
            }
        }

        return inDegree;
    }

    @Override
    public List<Integer> getAdjacentVertices(int v) {
        if (v >= vertices || v < 0) {
            throw new IllegalArgumentException("Invalid vertex number.");
        }

        return vertexList.get(v).getAdjacentVertices();
    }
}

package com.briandidthat.graphs.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Adjacency-Matrix-Graph
 * This graph implementation uses an adjacency matrix to track adjacent nodes. You would use an adjacency matrix when
 * your graph is well connected. When the connections are large, the O(V^2) space is worth it.
 *
 * space-complexity: O(V^2)
 * time-complexity: isEdgePresent: O(1), Iteration of Edges of on a vertex O(V)
 */
public class AdjacencyMatrixGraph implements Graph {
    private int[][] adjacencyMatrix;
    private int vertices;
    private boolean isWeighted;
    private GraphType graphType;

    public AdjacencyMatrixGraph(int vertices, boolean isWeighted, GraphType graphType) {
        this.vertices = vertices;
        this.isWeighted = isWeighted;
        this.graphType = graphType;
        this.adjacencyMatrix = new int[vertices][vertices];

        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                adjacencyMatrix[i][j] = 0;
            }
        }
    }

    @Override
    public GraphType graphType() {
        return graphType;
    }

    @Override
    public void addEdge(int v1, int v2) {
        if (isWeighted) {
            throw new IllegalArgumentException("This graph is weighted.");
        }

        if (validateVertices(vertices, v1, v2)) {
            throw new IllegalArgumentException("Invalid vertex number.");
        }

        adjacencyMatrix[v1][v2] = 1;
        if (graphType == GraphType.UNDIRECTED) {
            adjacencyMatrix[v2][v1] = 1;
        }
    }

    @Override
    public void addEdge(int v1, int v2, int weight) {
        if (!isWeighted) {
            throw new IllegalArgumentException("This graph is not weighted");
        }

        if (validateVertices(vertices, v1, v2)) {
            throw new IllegalArgumentException("Invalid vertex number.");
        }

        adjacencyMatrix[v1][v2] = weight;
        if (graphType == GraphType.UNDIRECTED) {
            adjacencyMatrix[v2][v1] = weight;
        }
    }

    @Override
    public int getWeightedEdge(int v1, int v2) {
        if (!isWeighted) {
            throw new IllegalArgumentException("This is not a weighted graph.");
        }

        return adjacencyMatrix[v1][v2];
    }

    @Override
    public int getNumVertices() {
        return vertices;
    }

    @Override
    public int getIndegree(int v) {
        if (validateVertices(vertices, v)) {
            throw new IllegalArgumentException("Invalid vertex number.");
        }

        int inDegree = 0;
        for (int i = 0; i < vertices; i++) {
            if (adjacencyMatrix[i][v] != 0) {
                inDegree++;
            }
        }

        return inDegree;
    }

    @Override
    public List<Integer> getAdjacentVertices(int v) {
        if (validateVertices(vertices, v)) {
            throw new IllegalArgumentException("Invalid vertex number.");
        }

        List<Integer> adjacentList = new ArrayList<>();

        for (int i = 0; i < vertices; i++) {
            if (adjacencyMatrix[v][i] != 0) {
                adjacentList.add(i);
            }
        }

        Collections.sort(adjacentList);

        return adjacentList;
    }

    private boolean validateVertices(int numVertices, int... vertices) {
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i] >= numVertices || vertices[i] < 0) {
                return true;
            }
        }

        return false;
    }
}

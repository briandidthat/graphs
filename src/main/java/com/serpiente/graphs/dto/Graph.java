package com.serpiente.graphs.dto;

import java.util.List;

public interface Graph {
    enum GraphType {
        DIRECTED,
        UNDIRECTED
    }

    GraphType graphType();

    void addEdge(int v1, int v2);

    void addEdge(int v1, int v2, int weight);

    int getWeightedEdge(int v1, int v2);

    int getNumVertices();

    int getIndegree(int v);

    List<Integer> getAdjacentVertices(int v);

}

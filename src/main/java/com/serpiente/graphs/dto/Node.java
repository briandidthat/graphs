package com.serpiente.graphs.dto;

import java.util.*;

public class Node {
    private int id;
    private Set<Integer> adjacencySet;

    public Node(int id) {
        this.id = id;
        adjacencySet = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void addEdge(int vertex) {
        adjacencySet.add(vertex);
    }

    public List<Integer> getAdjacentVertices() {
        List<Integer> sortedList = new ArrayList<>(adjacencySet);

        Collections.sort(sortedList);

        return sortedList;
    }
}

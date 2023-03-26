package com.briandidthat.graphs.model;

public class VertexInfo {
    private int vertexId;
    private int distance;
    private int numEdges;

    public VertexInfo(int vertexId, int distance) {
        this.vertexId = vertexId;
        this.distance = distance;
    }

    public VertexInfo(int vertexId, int distance, int numEdges) {
        this.vertexId = vertexId;
        this.distance = distance;
        this.numEdges = numEdges;
    }

    public int getVertexId() {
        return vertexId;
    }

    public int getDistance() {
        return distance;
    }

    public int getNumEdges() {
        return numEdges;
    }
}


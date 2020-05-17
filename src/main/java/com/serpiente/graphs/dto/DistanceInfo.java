package com.serpiente.graphs.dto;

public class DistanceInfo {
    private int distance;
    private int lastVertex;

    public DistanceInfo(boolean weighted) {
        this.distance = weighted ? Integer.MAX_VALUE : -1;
        this.lastVertex = -1;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getLastVertex() {
        return lastVertex;
    }

    public void setLastVertex(int lastVertex) {
        this.lastVertex = lastVertex;
    }
}

package com.serpiente.graphs.model;

public class DistanceEdgeInfo {
    private Integer distance;
    private Integer numEdges;
    private Integer lastVertex;

    public DistanceEdgeInfo() {
        this.distance = Integer.MAX_VALUE;
        this.numEdges = Integer.MAX_VALUE;
        this.lastVertex = -1;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public void setNumEdges(Integer numEdges) {
        this.numEdges = numEdges;
    }
    public void setLastVertex(Integer lastVertex) {
        this.lastVertex = lastVertex;
    }

    public void setInfo(Integer distance, int numEdges, Integer lastVertex) {
        this.distance = distance;
        this.numEdges = numEdges;
        this.lastVertex = lastVertex;
    }

    public Integer getDistance() {
        return distance;
    }

    public Integer getNumEdges() {
        return numEdges;
    }

    public Integer getLastVertex() {
        return lastVertex;
    }

}

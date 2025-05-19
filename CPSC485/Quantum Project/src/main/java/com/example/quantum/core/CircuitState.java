package com.example.quantum.core;

import java.io.Serializable;

public class CircuitState implements Serializable {

    private static final long serialVersionUID = 1L;

    private QuantumMatrix matrix;
    private int gateCount;
    private String circuit;
    private double error;

    public CircuitState(QuantumMatrix matrix, int gateCount, String circuit, double error) {
        this.matrix = matrix;
        this.gateCount = gateCount;
        this.circuit = circuit;
        this.error = error;
    }

    public QuantumMatrix getMatrix() {
        return matrix;
    }

    public void setMatrix(QuantumMatrix matrix) {
        this.matrix = matrix;
    }

    public int getGateCount() {
        return gateCount;
    }

    public void setGateCount(int gateCount) {
        this.gateCount = gateCount;
    }

    public String getCircuit() {
        return circuit;
    }

    public void setCircuit(String circuit) {
        this.circuit = circuit;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "CircuitState{" +
               "gates=" + gateCount +
               ", error=" + error +
               ", circuit='" + circuit + "'" +
               '}';
    }
}

package com.example.quantum.core;

import org.apache.commons.math3.complex.Complex;

import java.io.*;
import java.util.*;

public class QuantumGatesModel {

    private final QuantumMatrix goalMatrix;
    private final Queue<CircuitState> currentStates = new LinkedList<>();
    private final Queue<CircuitState> nextStates = new LinkedList<>();
    private List<CircuitState> allCircuits = new ArrayList<>();

    private int iterationsLeft;
    private int bits;

    private List<QuantumMatrix> gateSet;

    public QuantumGatesModel(QuantumMatrix goalMatrix, int iterations) {
        this.goalMatrix = goalMatrix;
        this.iterationsLeft = iterations;
        this.bits = goalMatrix.getBits();
        QuantumMatrix identity = QuantumMatrix.identity(bits);
        CircuitState initial = new CircuitState(identity, 0, "", 0);
        currentStates.add(initial);
    }

    public void findGoal() {
        loadGates(bits);
        while (iterationsLeft-- > 0) {
            while (!currentStates.isEmpty()) {
                expand(currentStates.poll());
            }
            calculateErrors();
        }
    }

    private void loadGates(int bits) {
        String filename = bits + "BitGates.dat";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                gateSet = (List<QuantumMatrix>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading gates: " + e.getMessage());
            gateSet = new ArrayList<>();
        }
    }

    private void expand(CircuitState cs) {
        for (QuantumMatrix gate : gateSet) {
            QuantumMatrix product = gate.multiply(cs.getMatrix());
            String newCircuit = cs.getCircuit() + " " + gate.getName();
            CircuitState next = new CircuitState(product, cs.getGateCount() + 1, newCircuit, 0);
            nextStates.add(next);
        }
    }

    private void calculateErrors() {
        while (!nextStates.isEmpty()) {
            CircuitState cs = nextStates.poll();

            QuantumMatrix actual = cs.getMatrix();
            QuantumMatrix diff = goalMatrix.multiply(actual.transposeConjugate());

            Complex normalizer = diff.get(0, 0);
            if (!normalizer.equals(Complex.ZERO)) {
                for (int i = 0; i < diff.getSize(); i++) {
                    for (int j = 0; j < diff.getSize(); j++) {
                        diff.set(i, j, diff.get(i, j).divide(normalizer));
                    }
                }
                diff = diff.subtract(QuantumMatrix.identity(bits));
            }

            double error = 0;
            for (int i = 0; i < diff.getSize(); i++) {
                for (int j = 0; j < diff.getSize(); j++) {
                    Complex value = diff.get(i, j);
                    error += value.multiply(value.conjugate()).getReal();
                }
            }

            cs.setError(Math.round(error * 1e6) / 1e6);
            allCircuits.add(cs);
            currentStates.add(cs);
        }
    }

    public void sortCircuitsByError() {
        allCircuits.sort(Comparator.comparingDouble(CircuitState::getError));
    }

    public List<CircuitState> getAllCircuits() {
        return allCircuits;
    }
}

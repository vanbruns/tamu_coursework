package com.example.quantum.core;

import org.apache.commons.math3.complex.Complex;

import java.io.Serializable;
import java.util.Arrays;

public class QuantumMatrix implements Serializable {

    private final int bits;
    private final int size;
    private final Complex[][] data;
    private String name = "";

    public QuantumMatrix(int bits) {
        this.bits = bits;
        this.size = (int) Math.pow(2, bits);
        this.data = new Complex[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(data[i], Complex.ZERO);
        }
    }

    public int getBits() {
        return bits;
    }

    public int getSize() {
        return size;
    }

    public Complex get(int row, int col) {
        return data[row][col];
    }

    public void set(int row, int col, Complex value) {
        data[row][col] = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public QuantumMatrix add(QuantumMatrix other) {
        QuantumMatrix result = new QuantumMatrix(bits);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result.set(i, j, data[i][j].add(other.get(i, j)));
            }
        }
        return result;
    }

    public QuantumMatrix subtract(QuantumMatrix other) {
        QuantumMatrix result = new QuantumMatrix(bits);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result.set(i, j, data[i][j].subtract(other.get(i, j)));
            }
        }
        return result;
    }

    public QuantumMatrix multiply(QuantumMatrix other) {
        QuantumMatrix result = new QuantumMatrix(bits);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Complex sum = Complex.ZERO;
                for (int k = 0; k < size; k++) {
                    sum = sum.add(data[i][k].multiply(other.get(k, j)));
                }
                result.set(i, j, sum);
            }
        }
        return result;
    }

    public QuantumMatrix transposeConjugate() {
        QuantumMatrix result = new QuantumMatrix(bits);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result.set(i, j, data[j][i].conjugate());
            }
        }
        return result;
    }

    public static QuantumMatrix identity(int bits) {
        QuantumMatrix identity = new QuantumMatrix(bits);
        for (int i = 0; i < identity.size; i++) {
            identity.set(i, i, Complex.ONE);
        }
        return identity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(data[i][j].toString()).append("   ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

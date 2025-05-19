package com.example.quantum.core;

import org.apache.commons.math3.complex.Complex;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AddGate {

    public static <T> void main(String[] args) {
        int bits = 1;
        List<QuantumMatrix> outputList = new ArrayList<>();

        QuantumMatrix gate = new QuantumMatrix(bits);
        Complex phase = new Complex(0, Math.PI / 4).exp();

        gate.setName("S");
        gate.set(0, 0, Complex.ONE);
        gate.set(0, 1, Complex.ZERO);
        gate.set(1, 0, Complex.ZERO);
        gate.set(1, 1, Complex.I);

        String filename = bits + "BitGates.dat";

        try (ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(filename))) {
            Object obj = oiStream.readObject();
            if (obj instanceof List<?>) {
                outputList = (List<QuantumMatrix>) obj;
            }
        } catch (Exception e) {
            System.out.println("No existing file, creating new gate list.");
        }

        outputList.add(gate);
        outputList.sort(new Comparator<QuantumMatrix>() {

            @Override
            public int compare(QuantumMatrix mat1, QuantumMatrix mat2) {
                return mat1.getName().compareTo(mat2.getName());
            }
            
        });

        try (ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            ooStream.writeObject(outputList);
            System.out.println("Gate saved.");
        } catch (IOException e) {
            System.out.println("Error saving gate: " + e.getMessage());
        }

        for (QuantumMatrix g : outputList) {
            System.out.println(g);
        }
    }
}

package com.example.quantum.ui;

import com.example.quantum.core.QuantumMatrix;
import com.example.quantum.core.QuantumGatesModel;

import org.apache.commons.math3.complex.Complex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnterGoalMatrixJDialog extends JDialog implements ActionListener {

    private final MainJPanel theGUI;
    private final int bits;
    private final int size;
    private final int iterations;
    private final JTextField[][] matrixTextFields;
    private final JButton enterButton = new JButton("Enter");
    private QuantumMatrix matrix;

    public EnterGoalMatrixJDialog(MainJPanel gui, int bits, int iterations) {
        super((Frame) null, "Enter Goal Matrix", true);
        this.theGUI = gui;
        this.bits = bits;
        this.iterations = iterations;
        this.size = (int) Math.pow(2, bits);

        matrix = new QuantumMatrix(bits);
        matrixTextFields = new JTextField[size][size];
        setupUI();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setupUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = size;
        add(new JLabel("Enter the goal matrix:"), gbc);

        for (int i = 0; i < size; i++) {
            gbc.gridy++;
            gbc.gridwidth = 1;
            for (int j = 0; j < size; j++) {
                matrixTextFields[i][j] = new JTextField(7);
                gbc.gridx = j;
                add(matrixTextFields[i][j], gbc);
            }
        }

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = size;
        gbc.anchor = GridBagConstraints.CENTER;
        add(enterButton, gbc);

        enterButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        QuantumMatrix inputMatrix = new QuantumMatrix(bits);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                String input = matrixTextFields[i][j].getText();
                Complex value = parseComplex(input);
                inputMatrix.set(i, j, value);
            }
        }

        this.matrix = inputMatrix;

        QuantumGatesModel model = new QuantumGatesModel(matrix, iterations);
        model.findGoal();
        model.sortCircuitsByError();
        this.dispose();

        theGUI.setList(model.getAllCircuits());
    }

    private Complex parseComplex(String text) {
        try {
            text = text.trim().replace("−", "-").replace(" ", "");

            if (text.endsWith("i")) {
                String imagPart = text.substring(0, text.length() - 1);
                if (imagPart.equals("") || imagPart.equals("+")) imagPart = "1";
                if (imagPart.equals("-")) imagPart = "-1";
                return new Complex(0, Double.parseDouble(imagPart));
            } else if (text.contains("+") || (text.lastIndexOf('-') > 0)) {
                int plusIdx = text.lastIndexOf('+');
                int minusIdx = text.lastIndexOf('-');
                int splitIdx = Math.max(plusIdx, minusIdx);
                String realStr = text.substring(0, splitIdx);
                String imagStr = text.substring(splitIdx, text.length() - 1); // drop trailing 'i'
                return new Complex(Double.parseDouble(realStr), Double.parseDouble(imagStr));
            } else {
                return new Complex(Double.parseDouble(text), 0);
            }
        } catch (Exception ex) {
            return Complex.ZERO;
        }
    }

    public QuantumMatrix getMatrix() {
        return matrix;
    }
}

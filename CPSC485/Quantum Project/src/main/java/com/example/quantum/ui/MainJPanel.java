package com.example.quantum.ui;

import com.example.quantum.core.CircuitState;
import com.example.quantum.util.TableSorter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainJPanel extends JPanel implements ActionListener {

    private List<CircuitState> circuitList = new ArrayList<>();

    private QuantumTableModel tableModel;
    private TableSorter tableSorter;
    private JLabel numberOfBitsLabel;
    private JTextField numberOfBitsTextField;
    private JLabel numberOfIterationsLabel;
    private JTextField numberOfIterationsTextField;
    private JButton enterGoalMatrixButton;
    private JTable scrollingTable;
    private JScrollPane pane;

    public MainJPanel() {
        tableModel = new QuantumTableModel("Quantum Gates", 15);
        tableSorter = new TableSorter(tableModel);

        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();

        numberOfBitsLabel = new JLabel("# of Bits");
        numberOfBitsTextField = new JTextField("", 2);
        numberOfIterationsLabel = new JLabel("# of Max Gates");
        numberOfIterationsTextField = new JTextField("", 2);
        enterGoalMatrixButton = new JButton("Enter Goal Matrix");
        scrollingTable = new JTable(tableSorter);
        pane = new JScrollPane(scrollingTable);

        scrollingTable.setPreferredScrollableViewportSize(new Dimension(320, 240));
        scrollingTable.getTableHeader().setReorderingAllowed(false);
        scrollingTable.getTableHeader().setResizingAllowed(false);
        scrollingTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollingTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        scrollingTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        scrollingTable.getColumnModel().getColumn(2).setPreferredWidth(100);

        constraints.insets = new Insets(2, 2, 2, 2);
        constraints.gridy = 0;

        constraints.gridx = 0;
        constraints.anchor = GridBagConstraints.EAST;
        layout.setConstraints(numberOfBitsLabel, constraints);
        add(numberOfBitsLabel);

        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.WEST;
        layout.setConstraints(numberOfBitsTextField, constraints);
        add(numberOfBitsTextField);

        constraints.gridx = 2;
        constraints.anchor = GridBagConstraints.EAST;
        layout.setConstraints(numberOfIterationsLabel, constraints);
        add(numberOfIterationsLabel);

        constraints.gridx = 3;
        constraints.anchor = GridBagConstraints.WEST;
        layout.setConstraints(numberOfIterationsTextField, constraints);
        add(numberOfIterationsTextField);

        constraints.gridx = 4;
        constraints.anchor = GridBagConstraints.EAST;
        layout.setConstraints(enterGoalMatrixButton, constraints);
        add(enterGoalMatrixButton);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 5;
        constraints.anchor = GridBagConstraints.WEST;
        layout.setConstraints(pane, constraints);
        add(pane);

        enterGoalMatrixButton.addActionListener(this);
    }

    public void setList(List<CircuitState> list) {
        this.circuitList = list;
        tableModel = new QuantumTableModel("Quantum Gates", Math.max(list.size(), 15));
        tableSorter.setModel(tableModel);
        refreshScrollingTable();
    }

    public List<CircuitState> getList() {
        return circuitList;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enterGoalMatrixButton) {
            try {
                int bits = Integer.parseInt(numberOfBitsTextField.getText());
                int iterations = Integer.parseInt(numberOfIterationsTextField.getText());
                new EnterGoalMatrixJDialog(this, bits, iterations);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid integer values for bits and iterations.");
            }
        }
    }

    public void refreshScrollingTable() {
        for (int i = 0; i < circuitList.size(); i++) {
            CircuitState cs = circuitList.get(i);
            scrollingTable.setValueAt(String.valueOf(cs.getGateCount()), i, 0);
            scrollingTable.setValueAt(cs.getCircuit(), i, 1);
            scrollingTable.setValueAt(String.valueOf(cs.getError()), i, 2);
        }
        for (int i = circuitList.size(); i < scrollingTable.getRowCount(); i++) {
            scrollingTable.setValueAt("", i, 0);
            scrollingTable.setValueAt("", i, 1);
            scrollingTable.setValueAt("", i, 2);
        }
    }
}

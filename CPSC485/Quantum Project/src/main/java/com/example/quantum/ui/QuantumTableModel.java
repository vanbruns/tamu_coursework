package com.example.quantum.ui;

import javax.swing.table.AbstractTableModel;


public class QuantumTableModel extends AbstractTableModel {

  private Object[][] data; // the data for the table
  private String[] columnNames; // the column names for the table

  //
  // Constructor
  //
  public QuantumTableModel(String type, int rows) {
    if (type.equals("Quantum Gates")) {
      columnNames = new String[3];
      columnNames[0] = "Number of Gates";
      columnNames[1] = "Circuit";
      columnNames[2] = "Error";
      data = new Object[rows][3];
    }
  }

  //
  // getColumnCount returns the number of columns
  //
  public int getColumnCount() {
    return columnNames.length;
  }

  //
  // getRowCount returns the number of rows
  //
  public int getRowCount() {
    return data.length;
  }

  //
  // getColumnName returns the column name
  //
  public String getColumnName(int col) {
    return columnNames[col];
  }

  //
  // getValueAt return the data value at the given column and row
  //
  public Object getValueAt(int row, int col) {
    return data[row][col];
  }

  //
  // setValueAt sets the data value at the given column and row
  //
  public void setValueAt(Object value, int row, int col) {
    if (data[0][col] instanceof Integer && !(value instanceof Integer)) {
      try {
        data[row][col] = Integer.valueOf(value.toString());
        fireTableCellUpdated(row, col);
      }
      catch (NumberFormatException e) {}
    }
    else {
      data[row][col] = value;
      fireTableCellUpdated(row, col);
    }
  }
}

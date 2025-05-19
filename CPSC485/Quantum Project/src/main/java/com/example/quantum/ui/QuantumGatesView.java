package com.example.quantum.ui;

import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class QuantumGatesView extends JFrame implements WindowListener {

  private static JFrame frm; // the frame

  private MainJPanel mainPane; // the main panel

  //
  // Constructor
  //
  public QuantumGatesView () {
    //
    // Initialize variables
    //
    mainPane = new MainJPanel();

    //
    // Define layout and add components using constraints
    //
    GridLayout layout = new GridLayout(1, 1);
    getContentPane().setLayout(layout);
    getContentPane().add(mainPane);

    setTitle("Quantum Gates");
    addWindowListener(this);
  }

  public static void main(String[] args) {
    frm = new QuantumGatesView();
    frm.setSize(425,375);
    frm.setVisible(true);
  }

  @Override
  public void windowActivated(WindowEvent e) {
    
  }

  @Override
  public void windowClosed(WindowEvent e) {
    
  }

  @Override
  public void windowClosing(WindowEvent e) {
    System.exit(0);
  }

  @Override
  public void windowDeactivated(WindowEvent e) {
    
  }

  @Override
  public void windowDeiconified(WindowEvent e) {
   
  }

  @Override
  public void windowIconified(WindowEvent e) {
    
  }

  @Override
  public void windowOpened(WindowEvent e) {
    
  }
}

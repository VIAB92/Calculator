package gui;

import com.sun.swing.internal.plaf.synth.resources.synth_sv;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;

/**
 * Created by Виктор on 14.12.13.
 */
public class MainFrame extends JFrame {
    private JLabel displayPanel;
    private JPanel buttonPanel;
    private JButton dot;
    private JButton sum;
    private JButton mult;
    private JButton sub;
    private JButton divide;
    private JButton eq;
    private JButton clear;
    private ButtonGroup btnGroup;
    private JRadioButton metal;
    private JRadioButton motif;
    private JRadioButton window;
    private int numberCounter;
    private boolean isOperationSet;
    private boolean isDotSet;
    private String operation;

    private double firstOperand;
    private double rez;

    public MainFrame(int width, int height)
    {
        setTitle("SuperCalc");
        setSize(width, height);
        setResizable(false);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        displayPanel = new JLabel(" ");
        displayPanel.setFont(new Font("Times New Roman", 0, 20));
        add(displayPanel, BorderLayout.PAGE_START);
        buttonPanel = new JPanel(new GridLayout(5,4));
        add(buttonPanel);
        NumberListener inputNumberListener = new NumberListener();
        OperationListener inputOperationListener = new OperationListener();
        for (int i=1; i<10; i++)
        {
            addNumberButton(String.valueOf(i), inputNumberListener);
            if (i==3) addOperationButtons('/', inputOperationListener);
            if (i==6) addOperationButtons('*', inputOperationListener);
            if (i==9) addOperationButtons('-', inputOperationListener);
        }
        addNumberButton("0", inputNumberListener);
        addNumberButton(".", inputNumberListener);
        addOperationButtons('+', inputOperationListener);
        //addOperationButtons(inputOperationListener);
        eq = new JButton("=");
        eq.addActionListener(new CalculateListener());
        buttonPanel.add(eq);
        addStylers();
        addOperationButtons('C', inputOperationListener);
        setVisible(true);
    }

    private void addStylers()
    {
        btnGroup = new ButtonGroup();
        metal = new JRadioButton("metal");
        buttonPanel.add(metal);
        metal.addActionListener(new StylerListener());
        motif = new JRadioButton("motif");
        buttonPanel.add(motif);
        motif.addActionListener(new StylerListener());
        window = new JRadioButton("window");
        buttonPanel.add(window);
        window.addActionListener(new StylerListener());
        metal.setSelected(true);

        btnGroup.add(metal);
        btnGroup.add(motif);
        btnGroup.add(window);

    }

    private void addOperationButtons(char op, OperationListener listener)
    {
        switch (op)
        {
            case 'C': clear = new JButton("C");
                clear.addActionListener(listener);
                buttonPanel.add(clear);
                break;
            case '+':
                sum = new JButton("+");
                sum.addActionListener(listener);
                buttonPanel.add(sum);
                break;
            case '-':
                sub = new JButton("-");
                sub.addActionListener(listener);
                buttonPanel.add(sub);
                break;
            case '*':
                mult = new JButton("*");
                mult.addActionListener(listener);
                buttonPanel.add(mult);
                break;
            case '/':
                divide = new JButton("/");
                divide.addActionListener(listener);
                buttonPanel.add(divide);
                break;
        }
    }

    private void addNumberButton(String name, ActionListener listener)
    {
        JButton btnNumber = new JButton(name);
        btnNumber.addActionListener(listener);
        buttonPanel.add(btnNumber);
    }

    public void printReport()
    {
        System.out.println("IsDotSet: "+isDotSet);
        System.out.println("IsOperationSet: "+isOperationSet);
        System.out.println("FirstOperand: "+firstOperand);
        System.out.println("Result: "+rez);
    }

    private void setLAF(int number)
    {
        String laf;
        switch (number)
        {
            case 0: laf = "javax.swing.plaf.metal.MetalLookAndFeel";
                break;
            case 1: laf = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
                break;
            case 2: laf = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
                break;
            default:
                laf = UIManager.getSystemLookAndFeelClassName();
                break;
        }

        try {
            UIManager.setLookAndFeel(laf);
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        } catch (InstantiationException e) {

            e.printStackTrace();
        } catch (IllegalAccessException e) {

            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {

            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(getContentPane());
    }
    private class NumberListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isOperationSet==false && numberCounter==0)
            {
                displayPanel.setText(" ");
            }
            String numberEntered = e.getActionCommand();
            displayPanel.setText(displayPanel.getText()+numberEntered);
            numberCounter++;

            if (e.getSource().equals(dot))
            {
                isDotSet = true;
            }
        }
    }

    private class OperationListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isOperationSet)
            {
                firstOperand=firstOperand;
            }
            else
            {
                try
                {
                firstOperand= Double.valueOf(displayPanel.getText());
                }
                catch (NumberFormatException ex)
                {
                    displayPanel.setText(" ");
                }
            }

            if (e.getSource().equals(sum))
            {
                operation = "+";
            }

            if (e.getSource().equals(sub))
            {
                operation = "-";
            }

            if (e.getSource().equals(mult))
            {
                operation = "*";
            }

            if (e.getSource().equals(divide))
            {
                operation = "/";
            }

            if (e.getSource().equals(clear))
            {
                displayPanel.setText(" ");
                firstOperand=0;
                isOperationSet=false;
                rez=0;
              //printReport();
                return;
            }

            displayPanel.setText(" ");
            isOperationSet=true;
           // System.out.println(firstOperand);
        }
    }

    private class CalculateListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            try
            {
                if (operation.equals("+"))
                {
                    rez = firstOperand+Double.valueOf(displayPanel.getText());
                    displayPanel.setText(String.valueOf(rez));

                }
                if (operation.equals("-"))
                {
                    rez = firstOperand-Double.valueOf(displayPanel.getText());
                    displayPanel.setText(String.valueOf(rez));
                }
                if (operation.equals("*"))
                {
                    rez = firstOperand*Double.valueOf(displayPanel.getText());
                    displayPanel.setText(String.valueOf(rez));
                }
                if (operation.equals("/"))
                {
                    rez = firstOperand/Double.valueOf(displayPanel.getText());
                    displayPanel.setText(String.valueOf(rez));
                }
                isOperationSet=false;
                numberCounter=0;
                rez=0;

               // printReport();
            }
            catch (NullPointerException ex)
            {
                displayPanel.setText(" ");
            }
        }

    }

    private class StylerListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (metal.isSelected())
                setLAF(0);
            else if (motif.isSelected())
                setLAF(1);
            else if(window.isSelected())
                setLAF(2);
        }
    }
}

package com.example.myapp;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class App implements ActionListener, FocusListener 
{
    JFrame frame;
    JPanel panel;
    JTextField t1, t2, tresult;
    JTextField activeTextField; // Tracks currently active input for DEL / NEG operations
    JButton addBtn, subBtn, mulBtn, divBtn;
    JButton modBtn, powBtn, sqrtBtn, percentBtn;
    JButton negBtn, clearBtn, delBtn, exitBtn;
    JTextArea historyArea;

    public App() 
    {
        frame = new JFrame("Advanced Scientific Calculator - SRM Full Stack Practical");
        frame.setSize(620, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 247, 250));

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Font textFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font btnFont = new Font("Segoe UI", Font.BOLD, 12);

        // Header Title
        JLabel titleLabel = new JLabel("STANDARD & SCIENTIFIC CALCULATOR", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setForeground(new Color(25, 42, 86));
        titleLabel.setBounds(30, 15, 540, 25);
        panel.add(titleLabel);

        // Input 1
        JLabel l1 = new JLabel("First Number:");
        l1.setFont(labelFont);
        l1.setBounds(40, 55, 120, 25);
        t1 = new JTextField(10);
        t1.setFont(textFont);
        t1.setBounds(170, 55, 160, 28);
        t1.addFocusListener(this);
        activeTextField = t1;

        // Input 2
        JLabel l2 = new JLabel("Second Number:");
        l2.setFont(labelFont);
        l2.setBounds(40, 95, 120, 25);
        t2 = new JTextField(10);
        t2.setFont(textFont);
        t2.setBounds(170, 95, 160, 28);
        t2.addFocusListener(this);

        // Result Field
        JLabel l3 = new JLabel("Result:");
        l3.setFont(labelFont);
        l3.setBounds(40, 135, 120, 25);
        tresult = new JTextField(10);
        tresult.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tresult.setForeground(new Color(12, 100, 40));
        tresult.setEditable(false);
        tresult.setBackground(Color.WHITE);
        tresult.setBounds(170, 135, 160, 28);

        // History Log Section
        JLabel historyLabel = new JLabel("Calculation Log / History:");
        historyLabel.setFont(labelFont);
        historyLabel.setBounds(350, 40, 220, 20);
        panel.add(historyLabel);

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        historyArea.setBackground(Color.WHITE);
        historyArea.setBorder(BorderFactory.createLineBorder(new Color(200, 205, 215)));
        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setBounds(350, 65, 220, 98);
        panel.add(scrollPane);

        // Buttons - Row 1 (Basic Operations)
        addBtn = createButton("ADD (+)", btnFont, new Color(220, 235, 252));
        addBtn.setBounds(40, 185, 115, 32);

        subBtn = createButton("SUBTRACT (-)", btnFont, new Color(220, 235, 252));
        subBtn.setBounds(170, 185, 125, 32);

        mulBtn = createButton("MULTIPLY (*)", btnFont, new Color(220, 235, 252));
        mulBtn.setBounds(310, 185, 125, 32);

        divBtn = createButton("DIV (/)", btnFont, new Color(220, 235, 252));
        divBtn.setBounds(450, 185, 120, 32);

        // Buttons - Row 2 (Advanced Scientific Operations)
        modBtn = createButton("MOD (%)", btnFont, new Color(230, 245, 235));
        modBtn.setBounds(40, 230, 115, 32);

        powBtn = createButton("POW (x^y)", btnFont, new Color(230, 245, 235));
        powBtn.setBounds(170, 230, 125, 32);

        sqrtBtn = createButton("SQRT (\u221Ax)", btnFont, new Color(230, 245, 235));
        sqrtBtn.setBounds(310, 230, 125, 32);

        percentBtn = createButton("PERCENT (%)", btnFont, new Color(230, 245, 235));
        percentBtn.setBounds(450, 230, 120, 32);

        // Buttons - Row 3 (Editing & Controls)
        negBtn = createButton("+/- (NEG)", btnFont, new Color(254, 243, 199));
        negBtn.setBounds(40, 275, 115, 32);

        delBtn = createButton("DEL (\u232B)", btnFont, new Color(254, 243, 199));
        delBtn.setBounds(170, 275, 125, 32);

        clearBtn = createButton("CLEAR (C)", btnFont, new Color(254, 226, 226));
        clearBtn.setBounds(310, 275, 125, 32);

        exitBtn = createButton("EXIT", btnFont, new Color(254, 202, 202));
        exitBtn.setBounds(450, 275, 120, 32);

        // Add components to panel
        panel.add(l1); panel.add(t1);
        panel.add(l2); panel.add(t2);
        panel.add(l3); panel.add(tresult);

        panel.add(addBtn); panel.add(subBtn); panel.add(mulBtn); panel.add(divBtn);
        panel.add(modBtn); panel.add(powBtn); panel.add(sqrtBtn); panel.add(percentBtn);
        panel.add(negBtn); panel.add(delBtn); panel.add(clearBtn); panel.add(exitBtn);

        // Footer Note
        JLabel footerLabel = new JLabel("Java Full Stack Practical \u2022 Maven Executable JAR Project", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        footerLabel.setForeground(Color.GRAY);
        footerLabel.setBounds(40, 420, 530, 20);
        panel.add(footerLabel);

        frame.add(panel);
        frame.setVisible(true);
    }

    private JButton createButton(String text, Font font, Color bgColor) 
    {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.addActionListener(this);
        return button;
    }

    private String formatNumber(double val) 
    {
        if (val == (long) val) 
        {
            return String.format("%d", (long) val);
        }
        return String.valueOf(val);
    }

    private void logHistory(String record) 
    {
        historyArea.append(record + "\n");
        historyArea.setCaretPosition(historyArea.getDocument().getLength());
    }

    @Override
    public void focusGained(FocusEvent e) 
    {
        if (e.getSource() == t1) 
        {
            activeTextField = t1;
        } 
        else if (e.getSource() == t2) 
        {
            activeTextField = t2;
        }
    }

    @Override
    public void focusLost(FocusEvent e) 
    {
        // No action required
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Object src = e.getSource();

        if (src == exitBtn) 
        {
            System.exit(0);
        }

        if (src == clearBtn) 
        {
            t1.setText("");
            t2.setText("");
            tresult.setText("");
            t1.requestFocus();
            return;
        }

        if (src == delBtn) 
        {
            if (activeTextField != null) 
            {
                String text = activeTextField.getText();
                if (text != null && text.length() > 0) 
                {
                    activeTextField.setText(text.substring(0, text.length() - 1));
                }
            }
            return;
        }

        if (src == negBtn) 
        {
            if (activeTextField != null) 
            {
                String text = activeTextField.getText().trim();
                if (!text.isEmpty()) 
                {
                    if (text.startsWith("-")) 
                    {
                        activeTextField.setText(text.substring(1));
                    } 
                    else 
                    {
                        activeTextField.setText("-" + text);
                    }
                }
            }
            return;
        }

        // Single operand operation: Square Root
        if (src == sqrtBtn) 
        {
            try 
            {
                String s1 = t1.getText().trim();
                if (s1.isEmpty()) 
                {
                    tresult.setText("Input 1 required");
                    return;
                }
                double num1 = Double.parseDouble(s1);
                if (num1 < 0) 
                {
                    tresult.setText("Error: Negative SQRT");
                    return;
                }
                double res = Math.sqrt(num1);
                String formatted = formatNumber(res);
                tresult.setText(formatted);
                logHistory("\u221A(" + formatNumber(num1) + ") = " + formatted);
            } 
            catch (NumberFormatException ex) 
            {
                tresult.setText("Invalid Input");
            }
            return;
        }

        // Two operand operations
        try 
        {
            String s1 = t1.getText().trim();
            String s2 = t2.getText().trim();

            if (s1.isEmpty() || s2.isEmpty()) 
            {
                tresult.setText("Enter both numbers");
                return;
            }

            double num1 = Double.parseDouble(s1);
            double num2 = Double.parseDouble(s2);
            double result = 0;
            String symbol = "";

            if (src == addBtn) 
            {
                result = num1 + num2;
                symbol = "+";
            } 
            else if (src == subBtn) 
            {
                result = num1 - num2;
                symbol = "-";
            } 
            else if (src == mulBtn) 
            {
                result = num1 * num2;
                symbol = "\u00D7";
            } 
            else if (src == divBtn) 
            {
                if (num2 != 0) 
                {
                    result = num1 / num2;
                    symbol = "/";
                } 
                else 
                {
                    tresult.setText("Error: Divide by 0");
                    return;
                }
            } 
            else if (src == modBtn) 
            {
                if (num2 != 0) 
                {
                    result = num1 % num2;
                    symbol = "MOD";
                } 
                else 
                {
                    tresult.setText("Error: Divide by 0");
                    return;
                }
            } 
            else if (src == powBtn) 
            {
                result = Math.pow(num1, num2);
                symbol = "^";
            } 
            else if (src == percentBtn) 
            {
                result = (num1 * num2) / 100.0;
                symbol = "% of";
            }

            String formattedResult = formatNumber(result);
            tresult.setText(formattedResult);
            logHistory(formatNumber(num1) + " " + symbol + " " + formatNumber(num2) + " = " + formattedResult);
        } 
        catch (NumberFormatException ex) 
        {
            tresult.setText("Invalid Input");
        }
    }

    public static void main(String[] args) 
    {
        new App();
    }
}

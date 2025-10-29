package SRM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculatorApp extends JFrame implements ActionListener, KeyListener {
    private JTextField display;
    private double num1 = 0, num2 = 0, result = 0;
    private char operator = '\0';
    private boolean isOperatorPressed = false;

    public CalculatorApp() {
        setTitle("Calculator ");
        setSize(350, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(new Color(240, 248, 255));

        display = new JTextField();
        display.setBounds(30, 40, 270, 50);
        display.setEditable(false);
        display.setFont(new Font("Consolas", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display);
        display.addKeyListener(this);

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C", "⌫"
        };

        JPanel panel = new JPanel();
        panel.setBounds(30, 110, 270, 350);
        panel.setLayout(new GridLayout(5, 4, 10, 10));
        panel.setBackground(new Color(240, 248, 255));

        for (String text : buttonLabels) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 20));
            btn.setFocusPainted(false);
            btn.setBackground(new Color(230, 230, 250));
            btn.addActionListener(this);
            panel.add(btn);
        }

        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        handleInput(e.getActionCommand());
    }

    private void handleInput(String command) {
        if (command.matches("[0-9]") || command.equals(".")) {
            if (isOperatorPressed) {
                display.setText("");
                isOperatorPressed = false;
            }
            display.setText(display.getText() + command);
        } else if (command.equals("C")) {
            clear();
        } else if (command.equals("⌫")) {
            String text = display.getText();
            if (text.length() > 0)
                display.setText(text.substring(0, text.length() - 1));
        } else if (command.equals("=")) {
            calculate();
        } else { // Operators
            try {
                if (!display.getText().isEmpty()) {
                    num1 = Double.parseDouble(display.getText());
                    operator = command.charAt(0);
                    isOperatorPressed = true;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        }
    }

    private void calculate() {
        try {
            num2 = Double.parseDouble(display.getText());
            switch (operator) {
                case '+': result = num1 + num2; break;
                case '-': result = num1 - num2; break;
                case '*': result = num1 * num2; break;
                case '/':
                    if (num2 == 0) {
                        JOptionPane.showMessageDialog(this, "Cannot divide by zero!");
                        return;
                    }
                    result = num1 / num2;
                    break;
                default:
                    return;
            }
            display.setText(String.valueOf(result));
            num1 = result;
            operator = '\0';
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid calculation!");
        }
    }

    private void clear() {
        display.setText("");
        num1 = num2 = result = 0;
        operator = '\0';
    }

    // Keyboard input support
    @Override
    public void keyTyped(KeyEvent e) {
        char key = e.getKeyChar();
        if (Character.isDigit(key) || key == '.') {
            display.setText(display.getText() + key);
        } else if ("+-*/".indexOf(key) != -1) {
            operator = key;
            try {
                num1 = Double.parseDouble(display.getText());
                display.setText("");
            } catch (Exception ignored) {}
        } else if (key == KeyEvent.VK_ENTER) {
            calculate();
        } else if (key == KeyEvent.VK_BACK_SPACE) {
            String text = display.getText();
            if (text.length() > 0)
                display.setText(text.substring(0, text.length() - 1));
        } else if (key == KeyEvent.VK_ESCAPE) {
            clear();
        }
    }

    @Override public void keyPressed(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorApp calc = new CalculatorApp();
            calc.setVisible(true);
        });
    }
}

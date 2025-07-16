import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private String currentInput = "";
    private String operator = "";
    private double firstNumber = 0;

    public Calculator() {
        setTitle("Professional Calculator");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== Display =====
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 32));
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        // ===== Buttons =====
        String[] buttonLabels = {
            "C", "←", "/", "*",
            "7", "8", "9", "-",
            "4", "5", "6", "+",
            "1", "2", "3", "=",
            "0", ".", "", ""
        };

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        for (String label : buttonLabels) {
            if (label.equals("")) {
                buttonPanel.add(new JLabel());
            } else {
                JButton button = new JButton(label);
                button.setFont(new Font("Arial", Font.BOLD, 22));
                button.addActionListener(this);
                buttonPanel.add(button);
            }
        }

        add(buttonPanel, BorderLayout.CENTER);
        addKeyBindings();
        setVisible(true);
    }

    private void addKeyBindings() {
        display.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char ch = e.getKeyChar();
                if ((ch >= '0' && ch <= '9') || ch == '.') {
                    currentInput += ch;
                    display.setText(currentInput);
                } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                    setOperator(String.valueOf(ch));
                } else if (ch == '\n' || ch == '=') {
                    calculate();
                } else if (ch == '\b') {
                    backspace();
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.matches("[0-9]")) {
            currentInput += command;
            display.setText(currentInput);
        } else if (command.equals(".")) {
            if (!currentInput.contains(".")) {
                currentInput += ".";
                display.setText(currentInput);
            }
        } else if (command.equals("C")) {
            currentInput = "";
            operator = "";
            firstNumber = 0;
            display.setText("");
        } else if (command.equals("←")) {
            backspace();
        } else if (command.equals("=")) {
            calculate();
        } else if (command.matches("[+\\-*/]")) {
            setOperator(command);
        }
    }

    private void setOperator(String op) {
        if (!currentInput.isEmpty()) {
            firstNumber = Double.parseDouble(currentInput);
            operator = op;
            currentInput = "";
        }
    }

    private void calculate() {
        if (!currentInput.isEmpty() && !operator.isEmpty()) {
            double secondNumber = Double.parseDouble(currentInput);
            double result = 0;

            switch (operator) {
                case "+": result = firstNumber + secondNumber; break;
                case "-": result = firstNumber - secondNumber; break;
                case "*": result = firstNumber * secondNumber; break;
                case "/": 
                    if (secondNumber != 0) {
                        result = firstNumber / secondNumber; 
                    } else {
                        display.setText("Error: /0");
                        return;
                    }
                    break;
            }

            display.setText(String.valueOf(result));
            currentInput = String.valueOf(result);
            operator = "";
        }
    }

    private void backspace() {
        if (!currentInput.isEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            display.setText(currentInput);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator());
    }
}


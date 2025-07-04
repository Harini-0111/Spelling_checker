import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class SpellingCheckerGUI extends JFrame {
    private HashSet<String> dictionary;
    private JTextField inputField;
    private JLabel resultLabel;
    private JPanel mainPanel;
    private JLabel statusBar;

    public SpellingCheckerGUI() {
        dictionary = new HashSet<>();
        try {
            loadDictionary("dictionary.txt");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading dictionary: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // UI Enhancements for a modern, web-like style
        setTitle("Modern Spelling Checker");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window on the screen

        // Main Panel with background color and layout
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);  // Flat white background
        mainPanel.setLayout(new BorderLayout(20, 20));
        add(mainPanel);

        // Title Label
        JLabel titleLabel = new JLabel("Spelling Checker");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(33, 150, 243));  // Web-inspired blue
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Center Panel for input and buttons with padding
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);  // Flat background
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));  // Padding
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Input Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        inputField = new JTextField(20);
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        inputField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputField.setBackground(new Color(245, 245, 245));  // Light gray
        inputField.setForeground(Color.BLACK);
        inputField.setToolTipText("Enter a word to check its spelling");
        centerPanel.add(inputField, gbc);

        // Button Styles
        JButton checkButton = new JButton("Check Spelling");
        checkButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        checkButton.setBackground(new Color(76, 175, 80));  // Green button
        checkButton.setForeground(Color.WHITE);
        checkButton.setFocusPainted(false);
        checkButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        checkButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        checkButton.setToolTipText("Click to check the spelling");
        checkButton.setOpaque(true);
        checkButton.addActionListener(new CheckButtonListener());

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        clearButton.setBackground(new Color(244, 67, 54));  // Red button
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearButton.setToolTipText("Click to clear the input");
        clearButton.setOpaque(true);
        clearButton.addActionListener(new ClearButtonListener());

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        centerPanel.add(checkButton, gbc);
        gbc.gridx = 1;
        centerPanel.add(clearButton, gbc);

        // Result Label
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        resultLabel = new JLabel("");
        resultLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setForeground(new Color(100, 100, 100));  // Neutral color
        centerPanel.add(resultLabel, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Status Bar at the bottom
        statusBar = new JLabel("Ready");
        statusBar.setFont(new Font("SansSerif", Font.ITALIC, 12));
        statusBar.setForeground(Color.GRAY);
        statusBar.setHorizontalAlignment(SwingConstants.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(statusBar, BorderLayout.SOUTH);
    }

    private void loadDictionary(String dictionaryFile) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(dictionaryFile))) {
            String word;
            while ((word = br.readLine()) != null) {
                dictionary.add(word.trim().toLowerCase());
            }
        }
    }

    private boolean checkSpelling(String word) {
        return dictionary.contains(word.toLowerCase());
    }

    private class CheckButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String word = inputField.getText().trim();
            if (word.isEmpty()) {
                resultLabel.setText("Please enter a word.");
                statusBar.setText("Status: No word entered.");
            } else if (checkSpelling(word)) {
                resultLabel.setText("✔️ Correct spelling!");
                resultLabel.setForeground(new Color(76, 175, 80));  // Green for correct
                statusBar.setText("Status: Word is spelled correctly.");
            } else {
                resultLabel.setText("❌ Incorrect spelling!");
                resultLabel.setForeground(new Color(244, 67, 54));  // Red for incorrect
                statusBar.setText("Status: Word is spelled incorrectly.");
            }
        }
    }

    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            inputField.setText("");
            resultLabel.setText("");
            statusBar.setText("Status: Ready");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SpellingCheckerGUI checkerGUI = new SpellingCheckerGUI();
            checkerGUI.setVisible(true);
        });
    }
}

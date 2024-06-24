import java.awt.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tic Tac Toe");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set frame fullscreen

            JPanel mainPanel = new JPanel(new BorderLayout());
            frame.add(mainPanel);

            CardLayout cardLayout = new CardLayout();
            mainPanel.setLayout(cardLayout);

            MainMenuPanel mainMenu = new MainMenuPanel(cardLayout, mainPanel);
            mainPanel.add(mainMenu, "home");

            frame.setVisible(true);
        });
    }
}

class MainMenuPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainMenuPanel(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        setBackground(Color.decode("#111111")); // Background color

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel label = new JLabel("Tictactoe Game By Maulana Eryk Saputra");
        label.setFont(new Font("Poppins", Font.BOLD, 30));
        label.setForeground(Color.decode("#D7FC70")); // Font color
        label.setHorizontalAlignment(SwingConstants.CENTER); // Center align text
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(label, gbc);

        JButton playWithComputerButton = createStyledButton("Vs Komputer", 30);
        playWithComputerButton.addActionListener(e -> {
            String playerName = showInputDialog("Masukkan nama Anda:");
            if (playerName != null) { // Check if playerName is not null
                int difficulty = chooseDifficulty();
                TicTacToeGame gamePanel = new TicTacToeGame(true, difficulty, playerName, null, cardLayout, mainPanel);
                mainPanel.add(gamePanel, "game");
                cardLayout.show(mainPanel, "game");
            }
        });
        gbc.gridy = 1;
        add(playWithComputerButton, gbc);

        JButton playWithFriendButton = createStyledButton("Vs Teman", 30);
        playWithFriendButton.addActionListener(e -> {
            String player1Name = showInputDialog("Masukkan nama Pemain 1:");
            if (player1Name != null) { // Check if player1Name is not null
                String player2Name = showInputDialog("Masukkan nama Pemain 2:");
                if (player2Name != null) { // Check if player2Name is not null
                    TicTacToeGame gamePanel = new TicTacToeGame(false, -1, player1Name, player2Name, cardLayout, mainPanel);
                    mainPanel.add(gamePanel, "game");
                    cardLayout.show(mainPanel, "game");
                }
            }
        });
        gbc.gridy = 2;
        add(playWithFriendButton, gbc);
    }

    private JButton createStyledButton(String text, int fontSize) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, fontSize));
        button.setBackground(Color.decode("#D7FC70")); // Button background color
        button.setForeground(Color.BLACK); // Button font color
        button.setFocusPainted(false); // Remove focus border
        button.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50)); // Padding
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand on hover
        return button;
    }

    private static int chooseDifficulty() {
        Object[] options = {"Mudah", "Sulit"};
        int choice = JOptionPane.showOptionDialog(null,
                "Pilih tingkat kesulitan:",
                "Tingkat Kesulitan",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        return (choice == JOptionPane.YES_OPTION) ? TicTacToeGame.EASY : TicTacToeGame.HARD;
    }

    private String showInputDialog(String message) {
        return JOptionPane.showInputDialog(null, message, "Input", JOptionPane.PLAIN_MESSAGE);
    }
}


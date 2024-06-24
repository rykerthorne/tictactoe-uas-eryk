import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

class TicTacToeGame extends JPanel implements ActionListener {
    private JButton[] buttons = new JButton[9];
    private char currentPlayer;
    private boolean playerTurn;
    private boolean playWithComputer;
    private int difficulty;
    private String player1Name;
    private String player2Name;
    private int player1Wins;
    private int player2Wins;
    private JLabel nameLabel;
    private JLabel scoreLabel;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public static final int EASY = 0;
    public static final int HARD = 1;

    public TicTacToeGame(boolean playWithComputer, int difficulty, String player1Name, String player2Name, CardLayout cardLayout, JPanel mainPanel) {
        this.playWithComputer = playWithComputer;
        this.difficulty = difficulty;
        this.player1Name = player1Name;
        this.player2Name = (player2Name == null || player2Name.isEmpty()) ? "Komputer" : player2Name;
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.player1Wins = 0;
        this.player2Wins = 0;
        this.currentPlayer = 'X';
        this.playerTurn = true;

        setLayout(new BorderLayout());
        setBackground(Color.decode("#2c3e50")); // Background color using Hex code

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(Color.decode("#111111")); // Top panel background color
        nameLabel = new JLabel(player1Name + " vs " + this.player2Name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 30));
        nameLabel.setForeground(Color.WHITE); // Font color
        nameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        topPanel.add(nameLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(3, 3, 10, 10)); // Grid layout with gaps
        centerPanel.setBackground(Color.decode("#111111")); // Center panel background color
        for (int i = 0; i < 9; i++) {
            buttons[i] = createStyledButton("", 60);
            buttons[i].addActionListener(this);
            centerPanel.add(buttons[i]);
        }
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.decode("#111111")); // Bottom panel background color
        scoreLabel = new JLabel("Skor - " + player1Name + ": " + player1Wins + ", " + this.player2Name + ": " + player2Wins);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        scoreLabel.setForeground(Color.WHITE); // Font color
        bottomPanel.add(scoreLabel);

        JButton backButton = createStyledButton("Kembali", 20);
        backButton.addActionListener(e -> backToHome());
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, int fontSize) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, fontSize));
        button.setBackground(Color.decode("#D7FC70")); // Button background color
        button.setForeground(Color.WHITE); // Button font color
        button.setFocusPainted(false); // Remove focus border
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand on hover
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonClicked = (JButton) e.getSource();
        if (buttonClicked.getText().isEmpty() && playerTurn) {
            buttonClicked.setText(String.valueOf(currentPlayer));
            if (checkForWin()) {
                JOptionPane.showMessageDialog(null, player1Name + " menang!");
                player1Wins++;
                updateScoreLabel();
                resetBoard();
            } else if (isBoardFull()) {
                JOptionPane.showMessageDialog(null, "Permainan seri!");
                resetBoard();
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                playerTurn = false;
                if (currentPlayer == 'O' && playWithComputer) {
                    aiMove();
                } else {
                    playerTurn = true;
                }
            }
        }
    }

    private void aiMove() {
        Timer timer = new Timer(1000, e -> {
            int move = getBestMove();
            if (move != -1) {
                buttons[move].setText("O");
                if (checkForWin()) {
                    JOptionPane.showMessageDialog(null, player2Name + " menang!");
                    player2Wins++;
                    updateScoreLabel();
                    resetBoard();
                } else if (isBoardFull()) {
                    JOptionPane.showMessageDialog(null, "Permainan seri!");
                    resetBoard();
                } else {
                    currentPlayer = 'X';
                    playerTurn = true;
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private int getBestMove() {
        // AI logic to determine the best move
        // This is a placeholder implementation. Replace it with actual AI logic.
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i].getText().isEmpty()) {
                return i;
            }
        }
        return -1;
    }

    private boolean checkForWin() {
        // Winning conditions
        String[][] board = new String[3][3];
        for (int i = 0; i < 9; i++) {
            board[i / 3][i % 3] = buttons[i].getText();
        }

        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if (!board[i][0].isEmpty() && board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2])) {
                return true;
            }
            if (!board[0][i].isEmpty() && board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i])) {
                return true;
            }
        }
        if (!board[0][0].isEmpty() && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2])) {
            return true;
        }
        if (!board[0][2].isEmpty() && board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0])) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (JButton button : buttons) {
            if (button.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Skor - " + player1Name + ": " + player1Wins + ", " + player2Name + ": " + player2Wins);
    }

    private void resetBoard() {
        for (JButton button : buttons) {
            button.setText("");
        }
        currentPlayer = 'X';
        playerTurn = true;
    }

    private void backToHome() {
        cardLayout.show(mainPanel, "home");
    }
}

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class HomePanel extends JPanel {
    public HomePanel(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(null);

        JLabel titleLabel = new JLabel("Tic Tac Toe");
        titleLabel.setBounds(150, 50, 100, 30);
        add(titleLabel);

        JButton startButton = new JButton("Start");
        startButton.setBounds(150, 100, 100, 30);
        add(startButton);

        JButton easyButton = new JButton("Easy");
        easyButton.setBounds(150, 140, 100, 30);
        add(easyButton);

        JButton hardButton = new JButton("Hard");
        hardButton.setBounds(150, 180, 100, 30);
        add(hardButton);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Game");
            }
        });

        easyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TicTacToeGame.setDifficulty(TicTacToeGame.EASY);
                cardLayout.show(mainPanel, "Game");
            }
        });

        hardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TicTacToeGame.setDifficulty(TicTacToeGame.HARD);
                cardLayout.show(mainPanel, "Game");
            }
        });
    }
}

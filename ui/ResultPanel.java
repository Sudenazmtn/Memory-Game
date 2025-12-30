package ui;

import util.ScoreManager;
import util.TimeManager;
import javax.swing.*;
import java.awt.*;

public class ResultPanel extends JPanel {
    public ResultPanel(String gameType, ScoreManager score, TimeManager time, Runnable onRestart) {
        setLayout(new GridLayout(6, 1, 10, 10));
        setBackground(new Color(30, 30, 30));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel title = new JLabel("OYUN BİTTİ - " + gameType, SwingConstants.CENTER);
        title.setForeground(Color.YELLOW);
        title.setFont(new Font("Arial", Font.BOLD, 28));

        String stats = String.format("<html><div style='text-align: center; color: white;'>" +
                        "Toplam Puan: %d<br>Toplam Süre: %d sn<br>Hatalı Hamle: %d</div></html>",
                score.getScore(), time.getElapsedSeconds(), score.getMistakes());

        JLabel statsLabel = new JLabel(stats, SwingConstants.CENTER);
        statsLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        JButton restartBtn = new JButton("Ana Menüye Dön");
        restartBtn.addActionListener(e -> onRestart.run());
        score.saveToFile(gameType, time.getElapsedSeconds());

        add(title);
        add(statsLabel);
        add(new JLabel(""));
        add(restartBtn);
    }
}
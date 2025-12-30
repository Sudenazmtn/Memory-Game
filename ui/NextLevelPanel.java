package ui;

import javax.swing.*;
import java.awt.*;

public class NextLevelPanel extends JPanel {
    public NextLevelPanel(int finishedLevel, Runnable onNext) {
        setLayout(new BorderLayout());
        setBackground(new Color(45, 45, 45));

        JLabel label = new JLabel("Seviye " + finishedLevel + " Tamamlandı!", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 26));
        label.setForeground(Color.WHITE);

        JButton nextBtn = new JButton(finishedLevel == 4 ? "Sonuçları Gör" : "Sıradaki Seviyeye Geç ➔");
        nextBtn.setFont(new Font("Arial", Font.PLAIN, 20));
        nextBtn.setFocusPainted(false);
        nextBtn.addActionListener(e -> onNext.run());

        add(label, BorderLayout.CENTER);
        add(nextBtn, BorderLayout.SOUTH);
    }
}
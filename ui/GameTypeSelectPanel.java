package ui;

import util.GameType;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class GameTypeSelectPanel extends JPanel {

    public GameTypeSelectPanel(Consumer<GameType> onSelect) {
        setLayout(new GridLayout(4, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Oyun Türünü Seç", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JButton colorBtn = new JButton("Renk Eşleştirme");
        JButton wordBtn  = new JButton("Anlam Eşleştirme");
        JButton mathBtn  = new JButton("Matematik Eşleştirme");

        colorBtn.addActionListener(e -> onSelect.accept(GameType.COLOR));
        wordBtn.addActionListener(e -> onSelect.accept(GameType.WORD));
        mathBtn.addActionListener(e -> onSelect.accept(GameType.MATH));

        add(title);
        add(colorBtn);
        add(wordBtn);
        add(mathBtn);
    }
}
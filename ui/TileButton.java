package ui;

import model.TileData;
import javax.swing.*;
import java.awt.*;

public class TileButton extends JButton {
    private final TileData data;

    public TileButton(TileData data) {
        this.data = data;
        setBackground(Color.GRAY);
        setFont(new Font("Arial", Font.BOLD, 16));
        setFocusPainted(false);
        setText("");
    }

    public void reveal() {
        if (data.getColor() != null) {
            setBackground(data.getColor());
        } else {
            setBackground(Color.WHITE);
            setText(data.getText());
        }
    }

    public void hideTile() {
        if (!data.isMatched()) {
            setBackground(Color.GRAY);
            setText("");
        }
    }

    public void markMatched() {
        data.setMatched(true);
        setBackground(Color.GREEN);
        if (data.getColor() == null) setText(data.getText());
        setEnabled(false);
    }

    public TileData getData() { return data; }
    public boolean isMatched() { return data.isMatched(); }
}
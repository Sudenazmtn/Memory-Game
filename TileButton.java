import javax.swing.*;
import java.awt.*;

public class TileButton extends JButton {
    private final int index;
    private Color colorValue;
    private String textValue;
    private int matchId = -1;
    private boolean questionCard = false;
    private boolean matched;
    private boolean active;

    public TileButton(int index) {
        this.index = index;
        setFocusPainted(false);
        setOpaque(true);
        setFont(getFont().deriveFont(Font.BOLD, 14f));
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        hideValue();
    }

    public int getIndex() { return index; }

    public void setColorValue(Color c) { this.colorValue = c; }
    public Color getColorValue() { return colorValue; }

    public void setTextValue(String s) { this.textValue = s; }
    public String getTextValue() { return textValue; }

    public void setMatchId(int id) { this.matchId = id; }
    public int getMatchId() { return matchId; }

    public void setQuestionCard(boolean q) { this.questionCard = q; }
    public boolean isQuestionCard() { return questionCard; }

    public boolean isMatched() { return matched; }
    public void setMatched(boolean matched) { this.matched = matched; }

    public boolean isActiveTile() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public void clearPayload() {
        colorValue = null;
        textValue = null;
        matchId = -1;
        questionCard = false;
    }

    public void hideValue() {
        setBackground(new Color(160,160,160));
        setText("");
    }

    public void showValue() {
        if (textValue != null) {
            setBackground(new Color(235,235,235));
            setText(textValue);
            return;
        }
        if (colorValue != null) {
            setBackground(colorValue);
            setText("");
        }
    }

    public void setGreenDone() {
        setBackground(new Color(60, 200, 90));
        if (textValue != null) setText("✓ " + textValue);
        else setText("✓");
    }

    public void setRedWrong() {
        setBackground(new Color(220, 70, 70));
    }
}
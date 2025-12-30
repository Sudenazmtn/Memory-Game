package model;
import java.awt.Color;

public class TileData {
    private String text;
    private Color color;
    private String matchValue;
    private boolean matched = false;

    public TileData(String text, Color color, String matchValue) {
        this.text = text;
        this.color = color;
        this.matchValue = matchValue;
    }

    public String getText() { return text; }
    public Color getColor() { return color; }
    public String getMatchValue() { return matchValue; }
    public boolean isMatched() { return matched; }
    public void setMatched(boolean matched) { this.matched = matched; }
}
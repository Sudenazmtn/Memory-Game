package controller.math;

import controller.AbstractLevelController;
import model.TileData;
import ui.TileButton;
import util.ScoreManager;
import util.TimeManager;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class MathBaseController extends AbstractLevelController {
    protected int rows, cols, showTimeMs;
    protected Map<String, String> questions = new LinkedHashMap<>();
    protected List<TileButton> buttons = new ArrayList<>();
    protected TileButton firstSelected = null;
    protected TileButton secondSelected = null;
    private boolean isProcessing = false;

    public MathBaseController(ScoreManager s, TimeManager t, int rows, int cols, int showTimeMs, Runnable onComplete) {
        super(s, t, (rows * cols) / 2, onComplete);
        this.rows = rows;
        this.cols = cols;
        this.showTimeMs = showTimeMs;
    }

    @Override
    public JPanel startLevel() {
        JPanel board = new JPanel(new GridLayout(rows, cols, 10, 10));
        board.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        List<TileData> tileList = new ArrayList<>();

        for (Map.Entry<String, String> entry : questions.entrySet()) {
            tileList.add(new TileData(entry.getKey(), null, entry.getValue()));
            tileList.add(new TileData(entry.getValue(), null, entry.getValue()));
        }
        Collections.shuffle(tileList);

        for (TileData data : tileList) {
            TileButton btn = new TileButton(data);
            btn.addActionListener(e -> onTileClicked(btn));
            buttons.add(btn);
            board.add(btn);
        }

        showTemporarily();
        timeManager.start();
        return board;
    }

    private void onTileClicked(TileButton btn) {
        if (isProcessing || btn.isMatched() || btn == firstSelected) return;

        btn.reveal();

        if (firstSelected == null) {
            firstSelected = btn;
        } else {
            secondSelected = btn;
            isProcessing = true;
            checkMatch();
        }
    }

    private void checkMatch() {
        javax.swing.Timer t = new javax.swing.Timer(500, e -> {
            if (firstSelected != null && secondSelected != null) {
                String val1 = firstSelected.getData().getMatchValue();
                String val2 = secondSelected.getData().getMatchValue();
                String text1 = firstSelected.getData().getText();
                String text2 = secondSelected.getData().getText();

                if (val1.equals(val2) && !text1.equals(text2)) {
                    firstSelected.markMatched();
                    secondSelected.markMatched();
                    correctMatch();
                    resetTurn();
                } else {
                    javax.swing.Timer failTimer = new javax.swing.Timer(500, ex -> {
                        if (firstSelected != null && secondSelected != null) {
                            firstSelected.hideTile();
                            secondSelected.hideTile();
                            wrongMatch();
                        }
                        resetTurn();
                    });
                    failTimer.setRepeats(false);
                    failTimer.start();
                }
            }
        });
        t.setRepeats(false);
        t.start();
    }

    private void resetTurn() {
        firstSelected = null;
        secondSelected = null;
        isProcessing = false;
    }

    private void showTemporarily() {
        isProcessing = true;
        for (TileButton b : buttons) b.reveal();
        new javax.swing.Timer(showTimeMs, e -> {
            for (TileButton b : buttons) b.hideTile();
            isProcessing = false;
        }).start();
    }
}
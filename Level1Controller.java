import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Level1Controller implements LevelController {

    private static final int ROWS = 2;
    private static final int COLS = 5;

    private static final int[] TOP = {0,1,2,3,4};
    private static final int[] BOTTOM = {5,6,7,8,9};

    private static final int REVEAL_MS = 4000;
    private static final int WRONG_FLASH_MS = 700;

    private static final int SCORE_RIGHT = 5;
    private static final int SCORE_WRONG = -2;

    private static final Color[] PALETTE = {
            new Color(255, 90, 90),
            new Color(90, 140, 255),
            new Color(255, 220, 80),
            new Color(200, 90, 255),
            new Color(90, 220, 170)
    };

    private final TileButton[] tiles;
    private final JLabel infoLabel;
    private final JLabel scoreLabel;
    private final Runnable onLevelCompleted;

    private boolean playing = false;
    private TileButton selectedTop = null;
    private TileButton selectedBottom = null;

    public Level1Controller(TileButton[] tiles, JLabel infoLabel, JLabel scoreLabel, Runnable onLevelCompleted) {
        this.tiles = tiles;
        this.infoLabel = infoLabel;
        this.scoreLabel = scoreLabel;
        this.onLevelCompleted = onLevelCompleted;

        resetBoard();
        updateScoreLabel();
    }

    @Override public int getRows() { return ROWS; }
    @Override public int getCols() { return COLS; }

    @Override
    public void startLevel() {
        resetBoard();
        assignRandomColors();
        revealSequence();
    }

    @Override
    public void onTileClick(TileButton t) {
        if (!playing) return;
        if (!t.isActiveTile() || t.isMatched()) return;

        boolean isTop = contains(TOP, t.getIndex());
        boolean isBottom = contains(BOTTOM, t.getIndex());
        if (!isTop && !isBottom) return;

        if (t == selectedTop || t == selectedBottom) return;

        if (isTop) {
            if (selectedTop != null && !selectedTop.isMatched()) selectedTop.hideValue();
            selectedTop = t;
            selectedTop.showValue();
            infoLabel.setText("Üst seçildi, şimdi alttan seç.");
        }

        if (isBottom) {
            if (selectedBottom != null && !selectedBottom.isMatched()) selectedBottom.hideValue();
            selectedBottom = t;
            selectedBottom.showValue();
            infoLabel.setText("Alt seçildi. (Üst seçiliyse kontrol...)");
        }

        if (selectedTop != null && selectedBottom != null) checkMatch();
    }

    private void resetBoard() {
        playing = false;
        selectedTop = null;
        selectedBottom = null;

        for (TileButton t : tiles) {
            t.setMatched(false);
            t.setActive(true);
            t.setEnabled(false);
            t.clearPayload();
            t.hideValue();
        }

        infoLabel.setText("Level 1: Başlat'a bas. Üst 4 sn, alt 4 sn gösterilecek.");
    }

    private void assignRandomColors() {
        ArrayList<Color> top = new ArrayList<>(Arrays.asList(PALETTE));
        ArrayList<Color> bottom = new ArrayList<>(Arrays.asList(PALETTE));
        Collections.shuffle(top);
        Collections.shuffle(bottom);

        for (int i = 0; i < TOP.length; i++) tiles[TOP[i]].setColorValue(top.get(i));
        for (int i = 0; i < BOTTOM.length; i++) tiles[BOTTOM[i]].setColorValue(bottom.get(i));
    }

    private void revealSequence() {
        setClickable(false);
        hideAllUnmatched();

        infoLabel.setText("Level 1: Üst satır 4 saniye gösteriliyor...");
        showGroup(TOP, true);

        Timer t1 = new Timer(REVEAL_MS, e -> {
            showGroup(TOP, false);

            infoLabel.setText("Level 1: Alt satır 4 saniye gösteriliyor...");
            showGroup(BOTTOM, true);

            Timer t2 = new Timer(REVEAL_MS, e2 -> {
                showGroup(BOTTOM, false);
                infoLabel.setText("Eşleştir: üstten bir, alttan bir seç (seçimler açık kalır).");
                playing = true;
                setClickable(true);
            });
            t2.setRepeats(false);
            t2.start();
        });
        t1.setRepeats(false);
        t1.start();
    }

    private void checkMatch() {
        setClickable(false);

        Color c1 = selectedTop.getColorValue();
        Color c2 = selectedBottom.getColorValue();

        if (c1 != null && c1.equals(c2)) {
            selectedTop.setMatched(true);
            selectedBottom.setMatched(true);
            selectedTop.setGreenDone();
            selectedBottom.setGreenDone();
            selectedTop.setEnabled(false);
            selectedBottom.setEnabled(false);

            ScoreManager.add(SCORE_RIGHT);
            updateScoreLabel();

            infoLabel.setText("Doğru! +" + SCORE_RIGHT + " puan");
            selectedTop = null;
            selectedBottom = null;
            setClickable(true);

            if (isCompleted()) {
                playing = false;
                infoLabel.setText("Level 1 tamamlandı ✅ Level 2'ye geçiliyor...");
                Timer go = new Timer(800, e -> onLevelCompleted.run());
                go.setRepeats(false);
                go.start();
            }
        } else {
            selectedTop.setRedWrong();
            selectedBottom.setRedWrong();

            ScoreManager.add(SCORE_WRONG);
            updateScoreLabel();

            infoLabel.setText("Yanlış! " + SCORE_WRONG + " puan");

            Timer back = new Timer(WRONG_FLASH_MS, e -> {
                selectedTop.hideValue();
                selectedBottom.hideValue();
                selectedTop = null;
                selectedBottom = null;
                setClickable(true);
            });
            back.setRepeats(false);
            back.start();
        }
    }

    private boolean isCompleted() {
        for (int idx : TOP) if (!tiles[idx].isMatched()) return false;
        for (int idx : BOTTOM) if (!tiles[idx].isMatched()) return false;
        return true;
    }

    private void showGroup(int[] group, boolean show) {
        for (int idx : group) {
            if (tiles[idx].isMatched()) continue;
            if (show) tiles[idx].showValue();
            else tiles[idx].hideValue();
        }
    }

    private void hideAllUnmatched() {
        for (TileButton t : tiles) if (!t.isMatched()) t.hideValue();
    }

    private void setClickable(boolean enable) {
        for (TileButton t : tiles) {
            if (!t.isActiveTile() || t.isMatched()) t.setEnabled(false);
            else t.setEnabled(enable);
        }
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Toplam Puan: " + ScoreManager.getTotalScore());
    }

    private boolean contains(int[] arr, int val) {
        for (int x : arr) if (x == val) return true;
        return false;
    }
}
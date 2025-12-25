import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Level2Controller implements LevelController {

    private static final int ROWS = 2;
    private static final int COLS = 8;

    private static final int[] TOP =    {0,1,2,3,4,5,6,7};
    private static final int[] BOTTOM = {8,9,10,11,12,13,14,15};

    private static final int REVEAL_MS = 6000;
    private static final int WRONG_FLASH_MS = 700;

    private static final int SCORE_RIGHT = 5;
    private static final int SCORE_WRONG = -2;

    private final TileButton[] tiles;
    private final JLabel infoLabel;
    private final JLabel scoreLabel;
    private final JButton hintButton;
    private final Runnable onLevelCompleted;

    private final Map<String, String> pairs = new LinkedHashMap<>();

    private boolean playing = false;
    private boolean hintUsed = false;

    private TileButton selectedTop = null;
    private TileButton selectedBottom = null;

    public Level2Controller(TileButton[] tiles, JLabel infoLabel, JLabel scoreLabel, JButton hintButton, Runnable onLevelCompleted) {
        this.tiles = tiles;
        this.infoLabel = infoLabel;
        this.scoreLabel = scoreLabel;
        this.hintButton = hintButton;
        this.onLevelCompleted = onLevelCompleted;

        loadPairs();
        resetBoard();
        updateScoreLabel();
        updateHintButton();
    }

    @Override public int getRows() { return ROWS; }
    @Override public int getCols() { return COLS; }

    @Override
    public void startLevel() {
        resetBoard();
        assignWordsRandom();
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

    @Override public boolean hasHint() { return true; }

    @Override
    public void useHint() {
        if (!playing) return;
        if (hintUsed) return;

        hintUsed = true;
        updateHintButton();

        clearSelectionAndHide();
        setClickable(false);

        infoLabel.setText("Yeniden Göster (1 hak): üst 6 sn, alt 6 sn...");
        showGroup(TOP, true);

        Timer t1 = new Timer(REVEAL_MS, e -> {
            showGroup(TOP, false);
            showGroup(BOTTOM, true);

            Timer t2 = new Timer(REVEAL_MS, e2 -> {
                showGroup(BOTTOM, false);
                infoLabel.setText("Devam: üstten kelime, alttan eş anlam seç.");
                setClickable(true);
            });
            t2.setRepeats(false);
            t2.start();
        });
        t1.setRepeats(false);
        t1.start();
    }

    private void loadPairs() {
        pairs.put("Hızlı", "Süratli");
        pairs.put("Mutlu", "Neşeli");
        pairs.put("Zor", "Güç");
        pairs.put("Büyük", "Kocaman");
        pairs.put("Küçük", "Minik");
        pairs.put("Akıllı", "Zeki");
        pairs.put("Yorgun", "Bitkin");
        pairs.put("Güzel", "Hoş");
    }

    private void resetBoard() {
        playing = false;
        hintUsed = false;
        clearSelectionAndHide();

        for (TileButton t : tiles) {
            t.setMatched(false);
            t.setActive(true);
            t.setEnabled(false);
            t.clearPayload();
            t.hideValue();
        }

        infoLabel.setText("Level 2: Başlat'a bas. Üst 6 sn, alt 6 sn gösterilecek.");
        updateHintButton();
    }

    private void assignWordsRandom() {
        ArrayList<String> topWords = new ArrayList<>(pairs.keySet());
        ArrayList<String> bottomSyn = new ArrayList<>(pairs.values());
        Collections.shuffle(topWords);
        Collections.shuffle(bottomSyn);

        for (int i = 0; i < TOP.length; i++) tiles[TOP[i]].setTextValue(topWords.get(i));
        for (int i = 0; i < BOTTOM.length; i++) tiles[BOTTOM[i]].setTextValue(bottomSyn.get(i));
    }

    private void revealSequence() {
        setClickable(false);
        hideAllUnmatched();

        infoLabel.setText("Level 2: Üst satır 6 saniye gösteriliyor...");
        showGroup(TOP, true);

        Timer t1 = new Timer(REVEAL_MS, e -> {
            showGroup(TOP, false);

            infoLabel.setText("Level 2: Alt satır 6 saniye gösteriliyor...");
            showGroup(BOTTOM, true);

            Timer t2 = new Timer(REVEAL_MS, e2 -> {
                showGroup(BOTTOM, false);
                infoLabel.setText("Eşleştir: üstten kelime, alttan eş anlam seç.");
                playing = true;
                setClickable(true);
                updateHintButton();
            });
            t2.setRepeats(false);
            t2.start();
        });
        t1.setRepeats(false);
        t1.start();
    }

    private void checkMatch() {
        setClickable(false);

        String w = selectedTop.getTextValue();
        String s = selectedBottom.getTextValue();
        boolean correct = (w != null && s != null && s.equals(pairs.get(w)));

        if (correct) {
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
                infoLabel.setText("Level 2 tamamlandı ✅ Level 3'e geçiliyor...");
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

    private void clearSelectionAndHide() {
        if (selectedTop != null && !selectedTop.isMatched()) selectedTop.hideValue();
        if (selectedBottom != null && !selectedBottom.isMatched()) selectedBottom.hideValue();
        selectedTop = null;
        selectedBottom = null;
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Toplam Puan: " + ScoreManager.getTotalScore());
    }

    private void updateHintButton() {
        if (hintButton == null) return;
        if (!playing) {
            hintButton.setText("Yeniden Göster (1/1)");
            hintButton.setEnabled(false);
            return;
        }
        if (hintUsed) {
            hintButton.setText("Yeniden Göster (0/1)");
            hintButton.setEnabled(false);
        } else {
            hintButton.setText("Yeniden Göster (1/1)");
            hintButton.setEnabled(true);
        }
    }

    private boolean contains(int[] arr, int val) {
        for (int x : arr) if (x == val) return true;
        return false;
    }
}
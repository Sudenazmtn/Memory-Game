import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Level3Controller implements LevelController {

    private static final int ROWS = 4;
    private static final int COLS = 4;

    private static final int START_REVEAL_MS = 10000;
    private static final int AFTER_CORRECT_REVEAL_MS = 5000;
    private static final int WRONG_FLASH_MS = 700;

    private static final int SCORE_RIGHT = 5;
    private static final int SCORE_WRONG = -2;

    private final TileButton[] tiles;
    private final JLabel infoLabel;
    private final JLabel scoreLabel;

    private boolean playing = false;

    private TileButton first = null;
    private TileButton second = null;

    public Level3Controller(TileButton[] tiles, JLabel infoLabel, JLabel scoreLabel) {
        this.tiles = tiles;
        this.infoLabel = infoLabel;
        this.scoreLabel = scoreLabel;

        resetBoard();
        updateScoreLabel();
    }

    @Override public int getRows() { return ROWS; }
    @Override public int getCols() { return COLS; }

    @Override
    public void startLevel() {
        resetBoard();
        assignMathPairs();
        revealAllThenHide(START_REVEAL_MS, "Level 3: TÃ¼m kartlar 10 saniye gÃ¶steriliyor...");
    }

    @Override
    public void onTileClick(TileButton t) {
        if (!playing) return;
        if (!t.isActiveTile() || t.isMatched()) return;
        if (t == first) return;

        t.showValue(); // seÃ§ince aÃ§Ä±k kalsÄ±n

        if (first == null) {
            first = t;
            infoLabel.setText("Bir kart daha seÃ§ (iÅŸlem â†” sonuÃ§).");
            return;
        }

        second = t;
        checkMatch();
    }

    private void resetBoard() {
        playing = false;
        first = null;
        second = null;

        for (TileButton t : tiles) {
            t.setMatched(false);
            t.setActive(true);
            t.setEnabled(false);
            t.clearPayload();
            t.hideValue();
        }

        infoLabel.setText("Level 3: BaÅŸlat'a bas. (4x4 Matematik EÅŸleÅŸtirme)");
    }

    private void assignMathPairs() {
        ArrayList<MathPair> pairs = new ArrayList<>();
        Random r = new Random();

        while (pairs.size() < 8) {
            int a = 1 + r.nextInt(9);
            int b = 1 + r.nextInt(9);
            int op = r.nextInt(4); // 0:+ 1:- 2:* 3:/

            String expr;
            int ans;

            if (op == 0) {
                ans = a + b;
                expr = a + " + " + b;
            } else if (op == 1) {
                int big = Math.max(a, b);
                int small = Math.min(a, b);
                ans = big - small;
                expr = big + " - " + small;
            } else if (op == 2) {
                ans = a * b;
                expr = a + " Ã— " + b;
            } else {
                int x = 1 + r.nextInt(9);
                int y = 1 + r.nextInt(9);
                ans = y;
                expr = (x * y) + " Ã· " + x;
            }

            pairs.add(new MathPair(expr, String.valueOf(ans)));
        }

        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < pairs.size(); i++) {
            MathPair p = pairs.get(i);
            cards.add(new Card(i, true, p.expression));  // iÅŸlem
            cards.add(new Card(i, false, p.answer));     // sonuÃ§
        }
        Collections.shuffle(cards);

        for (int i = 0; i < tiles.length; i++) {
            TileButton t = tiles[i];
            Card c = cards.get(i);

            t.setMatchId(c.matchId);
            t.setQuestionCard(c.isQuestion);
            t.setTextValue(c.displayText);
        }
    }

    private void checkMatch() {
        setClickable(false);

        boolean correct =
                first.getMatchId() == second.getMatchId()
                        && first.isQuestionCard() != second.isQuestionCard();

        if (correct) {
            first.setMatched(true);
            second.setMatched(true);
            first.setGreenDone();
            second.setGreenDone();
            first.setEnabled(false);
            second.setEnabled(false);

            ScoreManager.add(SCORE_RIGHT);
            updateScoreLabel();

            infoLabel.setText("DoÄŸru! +" + SCORE_RIGHT + " puan. (5 sn tÃ¼m kartlar gÃ¶steriliyor)");
            first = null;
            second = null;

            revealAllThenHide(AFTER_CORRECT_REVEAL_MS, null);

            if (isCompleted()) {
                playing = false;
                infoLabel.setText("Level 3 tamamlandÄ± âœ…");
                setClickable(false);
            }
        } else {
            first.setRedWrong();
            second.setRedWrong();

            ScoreManager.add(SCORE_WRONG);
            updateScoreLabel();

            infoLabel.setText("YanlÄ±ÅŸ! " + SCORE_WRONG + " puan");

            Timer back = new Timer(WRONG_FLASH_MS, e -> {
                first.hideValue();
                second.hideValue();
                first = null;
                second = null;
                setClickable(true);
            });
            back.setRepeats(false);
            back.start();
        }
    }

    private void revealAllThenHide(int ms, String message) {
        playing = false;
        setClickable(false);

        if (message != null) infoLabel.setText(message);

        // âœ… hepsi aynÄ± anda gÃ¶sterilir
        for (TileButton t : tiles) {
            if (!t.isMatched()) t.showValue();
        }

        Timer hide = new Timer(ms, e -> {
            for (TileButton t : tiles) {
                if (!t.isMatched()) t.hideValue();
            }
            first = null;
            second = null;

            playing = true;
            setClickable(true);

            if (message != null) infoLabel.setText("Level 3: EÅŸleÅŸtir (iÅŸlem â†” sonuÃ§).");
        });
        hide.setRepeats(false);
        hide.start();
    }

    private boolean isCompleted() {
        for (TileButton t : tiles) if (!t.isMatched()) return false;
        return true;
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

    private static class MathPair {
        String expression, answer;
        MathPair(String e, String a) { expression = e; answer = a; }
    }

    private static class Card {
        int matchId; boolean isQuestion; String displayText;
        Card(int id, boolean q, String text) { matchId = id; isQuestion = q; displayText = text; }
    }
}

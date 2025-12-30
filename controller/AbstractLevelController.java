package controller;

import util.ScoreManager;
import util.TimeManager;
import javax.swing.JPanel;

public abstract class AbstractLevelController {
    protected int totalPairs;
    protected int foundPairs = 0;
    protected ScoreManager scoreManager;
    protected TimeManager timeManager;
    protected Runnable onLevelComplete;

    public AbstractLevelController(ScoreManager s, TimeManager t, int totalPairs, Runnable onLevelComplete) {
        this.scoreManager = s;
        this.timeManager = t;
        this.totalPairs = totalPairs;
        this.onLevelComplete = onLevelComplete;
    }

    public void correctMatch() {
        foundPairs++;
        scoreManager.addScore(10);
        if (isLevelFinished()) {
            onLevelComplete.run();
        }
    }

    public void wrongMatch() {
        scoreManager.addScore(-5);
    }

    public boolean isLevelFinished() {
        return foundPairs == totalPairs;
    }

    public abstract JPanel startLevel();
}
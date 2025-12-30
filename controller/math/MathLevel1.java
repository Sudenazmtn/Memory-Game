package controller.math;

import controller.math.MathBaseController;
import util.ScoreManager;
import util.TimeManager;

public class MathLevel1 extends MathBaseController {
    public MathLevel1(ScoreManager s, TimeManager t, Runnable onComplete) {
        super(s, t, 2, 4, 6000, onComplete);
        questions.put("2 + 3", "5");
        questions.put("4 + 1", "5");
        questions.put("6 + 2", "8");
        questions.put("7 + 3", "10");
    }
}
package controller.math;

import util.ScoreManager;
import util.TimeManager;

public class MathLevel3 extends MathBaseController {
    public MathLevel3(ScoreManager s, TimeManager t, Runnable onComplete) {
        super(s, t, 4, 4, 4000, onComplete);
        questions.put("2 x 4", "8");
        questions.put("8 x 1", "8");
        questions.put("5 x 3", "15");
        questions.put("3 x 4", "12");
        questions.put("6 x 2", "12");
        questions.put("10 x 2", "20");
        questions.put("4 x 5", "20");
        questions.put("7 x 2", "14");
    }
}
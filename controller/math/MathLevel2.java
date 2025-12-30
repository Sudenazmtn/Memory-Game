package controller.math;

import util.ScoreManager;
import util.TimeManager;

public class MathLevel2 extends MathBaseController {
    public MathLevel2(ScoreManager s, TimeManager t, Runnable onComplete) {
        super(s, t, 3, 4, 5000, onComplete);
        questions.put("10 - 4", "6");
        questions.put("9 - 3", "6");
        questions.put("15 - 5", "10");
        questions.put("20 - 8", "12");
        questions.put("8 - 5", "3");
        questions.put("12 - 9", "3");
    }
}
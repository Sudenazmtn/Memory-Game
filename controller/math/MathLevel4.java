package controller.math;

import util.ScoreManager;
import util.TimeManager;

public class MathLevel4 extends MathBaseController {
    public MathLevel4(ScoreManager s, TimeManager t, Runnable onComplete) {
        super(s, t, 4, 5, 4000, onComplete);
        questions.put("20 / 2", "10");
        questions.put("30 / 3", "10");
        questions.put("12 / 4", "3");
        questions.put("15 / 5", "3");
        questions.put("8 x 3", "24");
        questions.put("40 - 16", "24");
        questions.put("100 / 4", "25");
        questions.put("20 + 5", "25");
        questions.put("9 x 2", "18");
        questions.put("36 / 2", "18");
    }
}
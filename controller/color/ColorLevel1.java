package controller.color;
import util.ScoreManager;
import util.TimeManager;

public class ColorLevel1 extends ColorBaseController {
    public ColorLevel1(ScoreManager s, TimeManager t, Runnable onComplete) {
        super(s, t, 2, 4, 3000, onComplete);
    }
}
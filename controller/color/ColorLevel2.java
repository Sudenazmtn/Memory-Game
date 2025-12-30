package controller.color;
import util.ScoreManager;
import util.TimeManager;

public class ColorLevel2 extends ColorBaseController {
    public ColorLevel2(ScoreManager s, TimeManager t, Runnable onComplete) {
        super(s, t, 3, 4, 3000, onComplete);
    }
}
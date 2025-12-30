package controller.color;
import util.ScoreManager;
import util.TimeManager;

public class ColorLevel3 extends ColorBaseController {
    public ColorLevel3(ScoreManager s, TimeManager t, Runnable onComplete) {
        super(s, t, 4, 4, 2500, onComplete);
    }
}
package controller.color;
import util.ScoreManager;
import util.TimeManager;

public class ColorLevel4 extends ColorBaseController {
    public ColorLevel4(ScoreManager s, TimeManager t, Runnable onComplete) {
        super(s, t, 4, 5, 2500, onComplete);
    }
}
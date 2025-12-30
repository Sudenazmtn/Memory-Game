package controller.word;
import util.ScoreManager;
import util.TimeManager;

public class WordLevel3 extends WordBaseController {
    public WordLevel3(ScoreManager s, TimeManager t, Runnable onComplete) {
        super(s, t, 4, 4, 3000, onComplete);
        wordPairs.put("Doğa", "Tabiat");
        wordPairs.put("Kalp", "Yürek");
        wordPairs.put("Özlem", "Hasret");
        wordPairs.put("Hediye", "Armağan");
        wordPairs.put("Vatan", "Yurt");
        wordPairs.put("Savaş", "Harp");
        wordPairs.put("Barış", "Sulh");
        wordPairs.put("Öykü", "Hikaye");
    }
}
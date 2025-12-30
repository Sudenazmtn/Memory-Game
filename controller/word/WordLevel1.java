package controller.word;
import util.*;

public class WordLevel1 extends WordBaseController {
    public WordLevel1(ScoreManager s, TimeManager t, Runnable onComplete) {
        super(s, t, 2, 4, 5000, onComplete);
        wordPairs.put("Siyah", "Kara");
        wordPairs.put("Beyaz", "Ak");
        wordPairs.put("Cevap", "Yanıt");
        wordPairs.put("Okul", "Mektep");
    }
}
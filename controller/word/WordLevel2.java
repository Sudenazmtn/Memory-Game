package controller.word;
import util.ScoreManager;
import util.TimeManager;

public class WordLevel2 extends WordBaseController {
    public WordLevel2(ScoreManager s, TimeManager t, Runnable onComplete) {
        super(s, t, 3, 4, 4000, onComplete);
        wordPairs.put("Hızlı", "Çabuk");
        wordPairs.put("Zengin", "Varlıklı");
        wordPairs.put("Yoksul", "Fakir");
        wordPairs.put("Büyük", "İri");
        wordPairs.put("Küçük", "Ufak");
        wordPairs.put("Islak", "Yaş");
    }
}
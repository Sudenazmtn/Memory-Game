package controller.word;
import util.ScoreManager;
import util.TimeManager;

public class WordLevel4 extends WordBaseController {
    public WordLevel4(ScoreManager s, TimeManager t, Runnable onComplete) {
        super(s, t, 4, 5, 3000, onComplete);
        wordPairs.put("Nasihat", "Öğüt");
        wordPairs.put("Uzak", "Irak");
        wordPairs.put("Zaman", "Vakit");
        wordPairs.put("Kelime", "Sözcük");
        wordPairs.put("Cümle", "Tümce");
        wordPairs.put("Esir", "Tutsak");
        wordPairs.put("Misafir", "Konuk");
        wordPairs.put("Sınav", "İmtihan");
        wordPairs.put("Deprem", "Zelzele");
        wordPairs.put("Doktor", "Hekim");
    }
}
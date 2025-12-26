public class ScoreManager {
    private static int totalScore = 0;

    public static int getTotalScore() {
        return totalScore;
    }

    public static void add(int delta) {
        totalScore += delta;
    }

    public static void reset() {
        totalScore = 0;
    }
}
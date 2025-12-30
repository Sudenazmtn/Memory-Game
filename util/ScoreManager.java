package util;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScoreManager {
    private int score = 0;
    private int mistakes = 0;

    public void addScore(int value) {
        score += value;
        if (value < 0) mistakes++;
    }

    public int getScore() { return score; }
    public int getMistakes() { return mistakes; }

    public void saveToFile(String gameType, int totalSeconds) {
        try (PrintWriter out = new PrintWriter(new FileWriter(gameType + "_Kayıtlar.txt", true))) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            out.println("--- " + dtf.format(LocalDateTime.now()) + " ---");
            out.println("Puan: " + score);
            out.println("Süre: " + totalSeconds + " saniye");
            out.println("Hata Sayısı: " + mistakes);
            out.println("-------------------------");
        } catch (IOException e) {
            System.err.println("Dosya kaydedilemedi!");
        }
    }
}
package util;

public class TimeManager {
    private long startTime;

    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    public int getElapsedSeconds() {
        return (int) ((System.currentTimeMillis() - startTime) / 1000);
    }
}
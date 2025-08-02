package io.onebrick.interview.config;

public class RateLimiter {
    private long lastRequestTime = 0;
    private final long delayMs;

    public RateLimiter(int delaySeconds) {
        this.delayMs = delaySeconds * 1000L;
    }

    public void waitIfNeeded() throws InterruptedException {
        long currentTime = System.currentTimeMillis();
        long timeSinceLastRequest = currentTime - lastRequestTime;

        if (timeSinceLastRequest < delayMs) {
            long waitTime = delayMs - timeSinceLastRequest;
            Thread.sleep(waitTime);
        }

        lastRequestTime = System.currentTimeMillis();
    }
}

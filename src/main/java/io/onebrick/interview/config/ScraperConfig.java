package io.onebrick.interview.config;

import lombok.Getter;

@Getter
public class ScraperConfig {
    private int delaySeconds = 2;
    private int timeoutSeconds = 30;
    private boolean respectRobots = true;
    private String fileStorage = "~/";

    // Constructor, getters, setters
    public ScraperConfig(int delaySeconds, int timeoutSeconds, boolean respectRobots) {
        this.delaySeconds = delaySeconds;
        this.timeoutSeconds = timeoutSeconds;
        this.respectRobots = respectRobots;
    }
}
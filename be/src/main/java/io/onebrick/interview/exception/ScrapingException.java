package io.onebrick.interview.exception;

public class ScrapingException extends Exception{
    public ScrapingException(String message) {
        super(message);
    }

    public ScrapingException(String message, Throwable cause) {
        super(message, cause);
    }
}
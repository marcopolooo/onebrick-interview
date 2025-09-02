package io.onebrick.interview;

import com.opencsv.CSVWriter;
import io.onebrick.interview.config.RateLimiter;
import io.onebrick.interview.config.RobotsChecker;
import io.onebrick.interview.config.ScraperConfig;
import io.onebrick.interview.config.UserAgentRotator;
import io.onebrick.interview.domain.Product;
import io.onebrick.interview.domain.ScrapedData;
import io.onebrick.interview.exception.ScrapingException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public abstract class WebScraper<T extends ScrapedData> {
    protected ScraperConfig config;
    protected RateLimiter rateLimiter;
    protected UserAgentRotator userAgentRotator;
    protected RobotsChecker robotsChecker;
    protected abstract List<T> parseContent(WebDriver htmlContent, String url) throws InterruptedException;
    protected abstract String getCSVFileName();
    protected abstract String[] getCSVHeaders();
    protected abstract boolean isValidData(ScrapedData data);

    public WebScraper(ScraperConfig config) {
        this.config = config;
        this.rateLimiter = new RateLimiter(config.getDelaySeconds());
        this.userAgentRotator = new UserAgentRotator();
        this.robotsChecker = new RobotsChecker();
    }

    public final List<T> scrape(String url) throws ScrapingException {
        try {
            // Step 1: Check robots.txt -> tokopedia.com/robots.txt
            if (!robotsChecker.isAllowed(url)) {
                throw new ScrapingException("Scraping not allowed by robots.txt for: " + url);
            }

            // Step 2: Rate limiting
            rateLimiter.waitIfNeeded();

            // Step 3: Hit website
            WebDriver webDriver = makeHttpRequest(url);

            // Step 4: Parsing content
            List<T> rawData = parseContent(webDriver, url);

            // Step 5: Validate data
            List<T> validatedData = validateData(rawData);

            // Step 6: Save to CSV
            saveToCSV(rawData);

            return validatedData;
        } catch (Exception e) {
            handleError(e, url);
            throw new ScrapingException("Failed to scrape: " + url, e);
        }
    }

    protected WebDriver makeHttpRequest(String url) throws IOException, InterruptedException, ScrapingException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-blink-features=AutomationControlled");

        WebDriver driver = new ChromeDriver(options);

        System.out.println("Opening Tokopedia...");
        driver.get(url);

        Thread.sleep(10000);

        return driver;
    }

    protected List<T> validateData(List<T> data) {
        return data.stream()
            .filter(Objects::nonNull)
            .filter(this::isValidData)
            .collect(java.util.stream.Collectors.toList());
    }

    protected void saveToCSV(List<T> data) {
        try {
            String fileName = getCSVFileName();
            FileWriter fileWriter = new FileWriter(fileName, StandardCharsets.UTF_8);
            CSVWriter writer = new CSVWriter(
                fileWriter,
                CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END
            );

            writer.writeNext(getCSVHeaders());

            for (Object d : data) {
                if (d instanceof Product product) {
                    String[] productData = {
                        sanitizeField(product.getName()),
                        sanitizeField(product.getDescription()),
                        sanitizeField(product.getImageLink()),
                        sanitizeField(product.getPrice()),
                        sanitizeField(product.getRating()),
                        sanitizeField(product.getMerchantStore())
                    };

                    writer.writeNext(productData);
                }
            }
            writer.close();
            System.out.println("Data saved to: " + fileName);
            System.out.printf("Total data: %d, csv filename: %s%n", data.size(), getCSVFileName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void handleError(Exception e, String url) {
        System.err.println("Error scraping " + url + ": " + e.getMessage());
    }

    private String sanitizeField(String field) {
        if (field == null) {
            return "";
        }
        return field.trim().replaceAll("\\r\\n|\\r|\\n", " ");
    }
}
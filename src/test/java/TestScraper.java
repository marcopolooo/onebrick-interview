import io.onebrick.interview.TokopediaScraper;
import io.onebrick.interview.config.ScraperConfig;
import io.onebrick.interview.domain.Product;
import io.onebrick.interview.exception.ScrapingException;
import org.junit.Test;

import java.util.List;

public class TestScraper {
    @Test
    public void tokopediaScraper() {
        try {
            // Configuration
            ScraperConfig config = new ScraperConfig(2, 30, true);

            // Ecommerce scraping
            TokopediaScraper tokopediaScraper = new TokopediaScraper(config);
            List<Product> products = tokopediaScraper.getProducts("https://www.tokopedia.com/p/handphone-tablet");
            System.out.println("Found " + products.size() + " products");
        } catch (Exception e) {
            System.err.println("Scraping failed: " + e.getMessage());
        }
    }
}
package io.onebrick.interview;

import io.onebrick.interview.config.ScraperConfig;
import io.onebrick.interview.domain.Product;
import io.onebrick.interview.domain.ScrapedData;
import io.onebrick.interview.exception.ScrapingException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TokopediaScraper extends WebScraper<Product> {
    public TokopediaScraper(ScraperConfig scraperConfig) {
        super(scraperConfig);
    }

    public List<Product> getProducts(String categoryUrl) throws ScrapingException {
        return scrape(categoryUrl);
    }

    @Override
    protected List<Product> parseContent(WebDriver driver, String url) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        List<Product> products = new ArrayList<>();
        int targetCount = 100;
        int noNewProductsCount = 0;

        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        while (products.size() < targetCount) {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

            Thread.sleep(2000);

            int oldCount = products.size();

//            List<WebElement> productElements = driver.findElements(By.cssSelector("a.Ui5-B4CDAk4Cv-cjLm4o0g\\=\\=.XeGJAOdlJaxl4\\+UD3zEJLg\\=\\="));
            List<WebElement> productElements = driver.findElement(By.id("divComp#67")).findElements(By.tagName("a"));
            for (WebElement productLink : productElements) {
                String merchantUrl = "https://www.tokopedia.com/" + productLink.getAttribute("href").replace("https://www.tokopedia.com/","").split("/")[0];

                // 1. Image src dengan class "wSt-NCwsL186UdS6D-IpAg=="
                WebElement imageElement = productLink.findElement(By.cssSelector("img.wSt-NCwsL186UdS6D-IpAg\\=\\="));
                String imageLink = imageElement != null ? imageElement.getAttribute("src") : "";

                // 2. Product name dengan span class "+tnoqZhn89+NHUA43BpiJg=="
                WebElement nameElement = productLink.findElement(By.cssSelector("span.\\+tnoqZhn89\\+NHUA43BpiJg\\=\\="));
                String productName = nameElement != null ? nameElement.getText() : "";

                // 3. Price dengan span class "urMOIDHH7I0Iy1Dv2oFaNw== "
                WebElement priceElement = productLink.findElement(By.cssSelector("div.urMOIDHH7I0Iy1Dv2oFaNw\\=\\="));
                String price = priceElement != null ? priceElement.getText() : "";

                // 4. Rating dengan span class "_2NfJxPu4JC-55aCJ8bEsyw=="
                WebElement ratingElement = productLink.findElement(By.cssSelector("span._2NfJxPu4JC-55aCJ8bEsyw\\=\\="));
                String rating = ratingElement != null ? ratingElement.getText() : "";

                Product product = new Product();
                product.setName(productName);
                product.setDescription("");
                product.setImageLink(imageLink);
                product.setPrice(price);
                product.setRating(rating);
                product.setMerchantStore(merchantUrl);
                products.add(product);
            }

            if (products.size() == oldCount) {
                noNewProductsCount++;
                if (noNewProductsCount >= 5) {
                    System.out.println("Tidak ada product lg");
                    break;
                }
            } else {
                noNewProductsCount = 0;
            }
        }

        return products;
    }

    @Override
    protected String getCSVFileName() {
        Properties properties = new Properties();
        loadProperties(properties);
        String filestorage = properties.getProperty("onebrick.interview.filestorage");

        return filestorage + "/tokopedia_top10product_mobile_and_tech_" + LocalDateTime.now().toLocalDate() + ".csv";
    }

    void loadProperties(Properties props) {
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("application.properties")) {
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String[] getCSVHeaders() {
        return new String[]{
            "Name",
            "Description",
            "Image Link",
            "Price",
            "Rating",
            "Merchant Store"
        };
    }

    @Override
    protected boolean isValidData(ScrapedData data) {
        if (!(data instanceof Product product)) {
            return false;
        }

        return product.getName() != null
            && !product.getName().trim().isEmpty()
            && product.getDescription() != null
            && !product.getDescription().trim().isEmpty()
            && product.getImageLink() != null
            && !product.getImageLink().trim().isEmpty()
            && product.getPrice() != null
            && !product.getPrice().trim().isEmpty()
            && product.getRating() != null
            && !product.getRating().trim().isEmpty()
            && product.getMerchantStore() != null
            && !product.getMerchantStore().trim().isEmpty();
    }
}
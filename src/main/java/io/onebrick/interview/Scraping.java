package io.onebrick.interview;

import io.onebrick.interview.domain.Product;

import java.io.IOException;
import java.util.List;

public interface Scraping {
    void checkRobotsTxt(String baseUrl);
    List<Product> scrapeProducts(List<String> urls) throws IOException, InterruptedException;
}
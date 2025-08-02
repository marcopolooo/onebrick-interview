package io.onebrick.interview.domain;

import lombok.Data;

@Data
public class Product extends ScrapedData{
    private String name;
    private String description;
    private String imageLink;
    private String price;
    private String rating;
    private String merchantStore;
}
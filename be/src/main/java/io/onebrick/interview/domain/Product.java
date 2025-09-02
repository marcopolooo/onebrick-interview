package io.onebrick.interview.domain;

import lombok.Data;

import java.util.Objects;

@Data
public class Product extends ScrapedData{
    private String name;
    private String description;
    private String imageLink;
    private String price;
    private String rating;
    private String merchantStore;
    private String linkDetail;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(name, product.name) && Objects.equals(imageLink, product.imageLink) && Objects.equals(price, product.price) && Objects.equals(rating, product.rating) && Objects.equals(merchantStore, product.merchantStore) && Objects.equals(linkDetail, product.linkDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, imageLink, price, rating, merchantStore, linkDetail);
    }
}
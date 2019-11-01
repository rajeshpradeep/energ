package com.miniproject.energ.data.model;

import java.io.Serializable;

/**
 * Created by Rajesh Pradeep G on 31-10-2019
 */
public class ProductModel implements Serializable {

    private String productName;
    private String productDescription;
    private String productDosage;
    private String productRecommendedCrops;
    private String productCompositions;
    private String productMRP;

    public ProductModel(String productName, String productDescription, String productDosage, String productRecommendedCrops, String productCompositions, String productMRP) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productDosage = productDosage;
        this.productRecommendedCrops = productRecommendedCrops;
        this.productCompositions = productCompositions;
        this.productMRP = productMRP;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductDosage() {
        return productDosage;
    }

    public void setProductDosage(String productDosage) {
        this.productDosage = productDosage;
    }

    public String getProductRecommendedCrops() {
        return productRecommendedCrops;
    }

    public void setProductRecommendedCrops(String productRecommendedCrops) {
        this.productRecommendedCrops = productRecommendedCrops;
    }

    public String getProductCompositions() {
        return productCompositions;
    }

    public void setProductCompositions(String productCompositions) {
        this.productCompositions = productCompositions;
    }

    public String getProductMRP() {
        return productMRP;
    }

    public void setProductMRP(String productMRP) {
        this.productMRP = productMRP;
    }
}

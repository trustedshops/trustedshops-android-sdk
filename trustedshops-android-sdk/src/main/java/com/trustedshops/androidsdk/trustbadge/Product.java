package com.trustedshops.androidsdk.trustbadge;


public class Product {

    /**
     * @required
     */
    protected String tsCheckoutProductUrl;
    protected String tsCheckoutProductImageUrl;
    /**
     * @required
     */
    protected String tsCheckoutProductName;
    /**
     * @required
     */
    protected String tsCheckoutProductSKU;
    protected String tsCheckoutProductGTIN;
    protected String tsCheckoutProductMPN;
    protected String tsCheckoutProductBrand;

    public String getTsCheckoutProductUrl() {
        return tsCheckoutProductUrl;
    }

    public void setTsCheckoutProductUrl(String tsCheckoutProductUrl) {
        this.tsCheckoutProductUrl = tsCheckoutProductUrl;
    }

    public String getTsCheckoutProductImageUrl() {
        return tsCheckoutProductImageUrl;
    }

    public void setTsCheckoutProductImageUrl(String tsCheckoutProductImageUrl) {
        this.tsCheckoutProductImageUrl = tsCheckoutProductImageUrl;
    }

    public String getTsCheckoutProductName() {
        return tsCheckoutProductName;
    }

    public void setTsCheckoutProductName(String tsCheckoutProductName) {
        this.tsCheckoutProductName = tsCheckoutProductName;
    }

    public String getTsCheckoutProductSKU() {
        return tsCheckoutProductSKU;
    }

    public void setTsCheckoutProductSKU(String tsCheckoutProductSKU) {
        this.tsCheckoutProductSKU = tsCheckoutProductSKU;
    }

    public String getTsCheckoutProductGTIN() {
        return tsCheckoutProductGTIN;
    }

    public void setTsCheckoutProductGTIN(String tsCheckoutProductGTIN) {
        this.tsCheckoutProductGTIN = tsCheckoutProductGTIN;
    }

    public String getTsCheckoutProductMPN() {
        return tsCheckoutProductMPN;
    }

    public void setTsCheckoutProductMPN(String tsCheckoutProductMPN) {
        this.tsCheckoutProductMPN = tsCheckoutProductMPN;
    }

    public String getTsCheckoutProductBrand() {
        return tsCheckoutProductBrand;
    }

    public void setTsCheckoutProductBrand(String tsCheckoutProductBrand) {
        this.tsCheckoutProductBrand = tsCheckoutProductBrand;
    }
}

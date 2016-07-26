package com.trustedshops.androidsdk.trustbadge;


import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

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

    /*Optional*/
    protected ReviewIndicator reviewIndicator;
    protected String uuid;
    protected ArrayList<ProductReview> productReviewArrayList = new ArrayList<ProductReview>();


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

    public ReviewIndicator getReviewIndicator() {
        return reviewIndicator;
    }

    public void setReviewIndicator(ReviewIndicator reviewIndicator) {
        this.reviewIndicator = reviewIndicator;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ArrayList<ProductReview> getProductReviewArrayList() {
        return productReviewArrayList;
    }

    public void setProductReviewArrayList(ArrayList<ProductReview> productReviewArrayList) {
        this.productReviewArrayList = productReviewArrayList;
    }

    public void addProductReview(ProductReview review) {
        this.productReviewArrayList.add(review);
    }


    public static Product createFromSummaryApi(String jsonData) throws Exception {
        Product parsedProduct = new Product();

        JSONObject Jobject = new JSONObject(jsonData);

        JSONObject responseJsonObject = Jobject.getJSONObject("response");
        JSONObject dataJsonObject = responseJsonObject.getJSONObject("data");
        JSONObject productJsonObject = dataJsonObject.getJSONObject("product");

        parsedProduct.setTsCheckoutProductSKU(productJsonObject.getString("sku"));
        parsedProduct.setTsCheckoutProductName(productJsonObject.getString("name"));
        parsedProduct.setUuid(productJsonObject.getString("uuid"));


        ReviewIndicator _responseReviewIndicator = new ReviewIndicator();

        if (productJsonObject.has("qualityIndicators")) {
            JSONObject qualityIndicatorsObject = productJsonObject.getJSONObject("qualityIndicators");
            if (qualityIndicatorsObject.has("reviewIndicator")) {
                JSONObject reviewIndicatorObject = qualityIndicatorsObject.getJSONObject("reviewIndicator");

                if (reviewIndicatorObject.has("overallMark")) {
                    _responseReviewIndicator.setOverallMark((float) reviewIndicatorObject.getDouble("overallMark"));
                }

                if (reviewIndicatorObject.has("totalReviewCount")) {
                    _responseReviewIndicator.setTotalReviewCount(reviewIndicatorObject.getInt("totalReviewCount"));
                }
            }
            parsedProduct.setReviewIndicator(_responseReviewIndicator);
        }
        return parsedProduct;
    }


    public static Product createFromReviewsListApi(String jsonData) throws Exception {

        Product parsedProduct = new Product();

        JSONObject Jobject = new JSONObject(jsonData);

        JSONObject responseJsonObject = Jobject.getJSONObject("response");
        JSONObject dataJsonObject = responseJsonObject.getJSONObject("data");
        JSONObject productJsonObject = dataJsonObject.getJSONObject("product");

        parsedProduct.setTsCheckoutProductSKU(productJsonObject.getString("sku"));
        parsedProduct.setTsCheckoutProductName(productJsonObject.getString("name"));
        parsedProduct.setUuid(productJsonObject.getString("uuid"));

        if (productJsonObject.has("reviews")) {

            JSONArray productReviewsArray = productJsonObject.getJSONArray("reviews");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


            for (int i = 0; i < productReviewsArray.length(); i++) {
                JSONObject _reviewObject = productReviewsArray.getJSONObject(i);

                ProductReview productReview = new ProductReview();
                productReview.setComment(_reviewObject.getString("comment"));
                productReview.setMark(_reviewObject.getInt("mark"));
                productReview.setUID(_reviewObject.getString("UID"));

                if (_reviewObject.has("creationDate")) {
                    productReview.setCreationDate(format.parse(_reviewObject.getString("creationDate")));
                }

                if (_reviewObject.has("criteria")) {
                    JSONArray _productReviewCriteriaArray = _reviewObject.getJSONArray("criteria");

                    for (int j = 0; j < _productReviewCriteriaArray.length(); j++) {
                        Criteria productReviewCriteria = new Criteria();
                        productReviewCriteria.setMark(_productReviewCriteriaArray.getJSONObject(j).getInt("mark"));

                        if (_productReviewCriteriaArray.getJSONObject(j).getString("type").equals("Total")) {
                            productReviewCriteria.setType(Criteriatype.TOTAL);
                        }
                        productReview.addCritera(productReviewCriteria);
                    }
                }
                parsedProduct.addProductReview(productReview);
            }

        }
        return parsedProduct;
    }

}

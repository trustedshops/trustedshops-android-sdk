package com.trustedshops.androidsdk.trustbadge;

import java.util.ArrayList;

public class TrustbadgeOrder {

    /**
     * @required
     */
    protected String tsCheckoutOrderNr;
    /**
     * @required
     */
    protected String tsCheckoutBuyerEmail;
    /**
     * @required
     */
    protected String tsCheckoutOrderAmount;
    /**
     * @required
     */
    protected String tsCheckoutOrderCurrency;
    /**
     * @required
     */
    protected String tsCheckoutOrderPaymentType;
    protected String tsCheckoutOrderEstDeliveryDate;
    protected ArrayList<Product> tsCheckoutProductItems = new ArrayList<Product>();


    /**
     * @required
     */
    protected String tsId;


    public String getTsCheckoutOrderNr() {
        return tsCheckoutOrderNr;
    }

    public void setTsCheckoutOrderNr(String checkoutNr) {
        this.tsCheckoutOrderNr = checkoutNr;
    }

    public String getTsCheckoutBuyerEmail() {
        return tsCheckoutBuyerEmail;
    }

    public void setTsCheckoutBuyerEmail(String buyerEmail) {
        this.tsCheckoutBuyerEmail = buyerEmail;
    }

    public String getTsCheckoutOrderAmount() {
        return tsCheckoutOrderAmount;
    }

    public void setTsCheckoutOrderAmount(String orderAmount) {
        this.tsCheckoutOrderAmount = orderAmount;
    }

    public String getTsCheckoutOrderPaymentType() {
        return tsCheckoutOrderPaymentType;
    }

    public void setTsCheckoutOrderPaymentType(String paymentType) {
        this.tsCheckoutOrderPaymentType = paymentType;
    }

    public String getTsCheckoutOrderCurrency() {
        return tsCheckoutOrderPaymentType;
    }

    public void setTsCheckoutOrderCurrency(String orderCurrency) {
        this.tsCheckoutOrderCurrency = orderCurrency;
    }

    public String getTsCheckoutOrderEstDeliveryDate() {
        return tsCheckoutOrderEstDeliveryDate;
    }

    public void setTsCheckoutOrderEstDeliveryDate(String orderEstDeliveryDate) {
        this.tsCheckoutOrderEstDeliveryDate = orderEstDeliveryDate;
    }

    public ArrayList<Product> getTsCheckoutProductItems() {
        return this.tsCheckoutProductItems;
    }

    public void addCheckoutProductItem(Product checkoutProductItem) {
        this.tsCheckoutProductItems.add(checkoutProductItem);
    }

    public String getTsId() {
        return tsId;
    }

    public void setTsId(String tsId) {
        this.tsId = tsId;
    }
}

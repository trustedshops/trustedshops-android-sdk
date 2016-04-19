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
    protected ArrayList<Product> tsCheckoutProductItems;

}

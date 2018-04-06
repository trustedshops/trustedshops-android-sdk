# Trusted Shops SDK for Android #

[![CI Status](https://travis-ci.org/trustedshops/trustedshops-android-sdk.svg?branch=master)](https://travis-ci.org/trustedshops/trustedshops-android-sdk)
[![Bintray](https://img.shields.io/bintray/v/trustedshops/maven/trustedshops-android-sdk.svg?maxAge=3600)]()

By integrating our SDK into your shopping app you can leverage the following features included in your Trusted Shops membership: 
* **Trustbadge**: Show the Trustbadge in any view and size and provide additional information along with a link to your certificate
* **Customer Reviews**: Show your shop's customer rating and reviews
* **Product Reviews**: Show your product ratings and reviews
* **Buyer Protection**: Integrate the Trusted Shops buyer protection and review collecting services

![TrustedShopsAndroidSDK](https://raw.githubusercontent.com/trustedshops/trustedshops-android-sdk/master/trustbadgeexample/screenshots/Android-SDK-woText.png?raw=true "Boost your conversion with Trustbadge and buyer protection")

Our SDK supports the following languages: DE, EN, FR, ES, IT, NL, PL.

1. [Installation](#1-installation)
2. [Display Trustbadge](#2-display-trustbadge)
3. [Display Customer Reviews](#3-display-customer-reviews)
4. [Display Product Reviews](#4-display-product-reviews)
5. [Integration of Buyer Protection after Checkout](#5-integration-of-buyer-protection-after-checkout)
6. [Audit and Authorization](#6-audit-and-authorization)
7. [About this SDK](#7-about-this-sdk)

- - - -

## 1. Installation ##
To install the current version add this to your Module Build File (build.gradle):
```Java
repositories {
    jcenter()
    maven { url "https://jitpack.io" }
}

dependencies {
    compile 'com.trustedshops.androidsdk:trustedshops-android-sdk:1.9.3'
}
```

- - - -

## 2. Display Trustbadge ##

To display the trustbadge you have to create a view container (here with the ID "trustbadgeTestImageView") in your layout.xml and add the following code in the respective java file: 
```Java
/* Set your Trusted Shops ID here */
Trustbadge trustbadge = new Trustbadge("YOUR-TRUSTED-SHOPS-ID");
ImageView imageView = (ImageView) findViewById(R.id.trustbadgeTestImageView);
  try {
    trustbadge.getTrustbadge(imageView, this);
    /* Optional parameters */
    trustbadge.setIconColor("#27AE60"); // Sets the icon color in HEX Format
    trustbadge.setLoggingActive(true); // Enables DEBUG-Logging
  } catch(IllegalArgumentException exception) {
      Log.d("TSDEBUG", exception.getMessage());
  } catch (TrustbadgeException exception) {
      Log.d("TSDEBUG", exception.getMessage());
}
```

#### Mandatory Parameters ####
```Java
new Trustbadge("YOUR-TRUSTED-SHOPS-ID");
```
This is your app's TS-ID which will be provided by Trusted Shops. <br>In order to get your TS-ID authorized please see the "Authorization" section below.

#### Optional Paramters ####

 ~~`.setIconColor("#F98222");`~~
 
 ~~Sets the icon color in the lightbox in HEX Format.~~ 
 As of version 1.7.0 custom colors are deprecated. Setting a color won't change the displayed trustcard.
 Note that, however, the trustcard is now the same that is displayed in your webshop, so the look is more consistent over the various media that may show your certificate. 
 This also means that the trustcard includes more interactive behavior for your customer, depending on what services you booked at Trusted Shops. 
 For example, the trustcard may show not just a link to your certificate, but also display sample reviews given by your customers. 
 It also explains the buyer protection (depending on services) inline, so customers won't leave your app while checking up on that. 
 
 ```Java
 .setLoggingActive(true);
  ```
  Enables DEBUG-Logging.

- - - -

## 3. Display Customer Reviews ##

You can display your Trusted Shops Customer Reviews as follows:

First you initialize the trustbadge with your TSID (mandatory). 
```Java
/* Set your Trusted Shops ID here */
Trustbadge trustbadge = new Trustbadge("YOUR-TRUSTED-SHOPS-ID");
```

Then reference all the views, in which customer reviews information shall be shown (optional).
```Java
/* Reference the views where information shall be displayed */
RatingBar reviewStarsBar = (RatingBar) findViewById(R.id.trustedShopReviewStars);
TextView trustedShopReviewStarsMarkDescription = (TextView) findViewById(R.id.trustedShopReviewStarsMarkDescription);
TextView trustedShopReviewMark = (TextView) findViewById(R.id.trustedShopReviewMark);
```

Then, ```trustbadge.getTsCustomerReviews(this, ID_1, ID_2, ID_3, ID_4, ID_5);``` populates the customer reviews information into the respective views. If you don't want to show an element, set the view ID to ```null```.

```Java
try {
  trustbadge.setLoggingActive(true);
  /* List the viewIDs in following order: stars, review mark, reviw mark description, review count, review count long format */
  trustbadge.getTsCustomerReviews(this, reviewStarsBar, trustedShopReviewMark, trustedShopReviewStarsMarkDescription, null, null);
} catch (IllegalArgumentException exception) {
    Log.d("TSDEBUG", exception.getMessage());
} catch (TrustbadgeException exception) {
    Log.d("TSDEBUG", exception.getMessage());
}
```

Insert your views' IDs according to the following table:

| Position | Description | Type          | Example EN              | Example DE              |
| -------- | ----------- | ------------- | ----------------------- | ---------------------- |
| ID_1 | stars | RatingBar |![reviewStarsBar](trustbadgeexample/screenshots/reviewStarsBar.png "reviewStarsBar") |![reviewStarsBar](trustbadgeexample/screenshots/reviewStarsBar.png "reviewStarsBar") |
| ID_2 | review mark | TextView                   | "4.89/5.00" |  "4.89/5.00" | 
| ID_3 | review mark description | TextView       | "VERY GOOD"| "SEHR GUT |
| ID_4 | review count | TextView                  | "384" | "384" |
| ID_5 | review count in long format | TextView   | "384 reviews" | "384 Bewertungen" |

For example 

```Java
trustbadge.getTsCustomerReviews(this, reviewStarsBar, trustedShopReviewMark, trustedShopReviewStarsMarkDescription, null, null);
``` 
loads and displays 
- the stars in the RatingBar object with the ID ```reviewStarsBar```,
- the review mark in a TextView with ID ```trustedShopReviewMark``` and 
- the review mark description in a TextView with ID ```trustedShopReviewStarsMarkDescription```. 

Review count and review count in long format are not used in this example.

In order to examine the correct integration, just have a look at the demo app in this repository.

If you want to implement completly different handling, you can provide yor own callback where you have an access to all the review data.

```Java

OnTsCustomerReviewsFetchCompleted tsCallBack = new OnTsCustomerReviewsFetchCompleted() {
            @Override
            public void onCustomerReviewsFetchCompleted(Shop shopObject) {
                //do your stuff on successfull call
            }
             @Override
            public void onCustomerReviewsFetchFailed(Message errorMessage) {
                //do stuff if call failed
            }

}

trustbadge.getTsCustomerReviews(this, tsCallBack);
```

- - - -
## 4. Display Product Reviews ##

There are two functions to integrate product reviews in your application.

First - **getProductReviewsSummary** retrieves the product summary without reviews list

[See Example](trustbadgeexample/src/main/java/com/trustedshops/trustbadgeexample/ProductReviewsActivity.java#L93-L127)

Second - **getProductReviewsList** retrieves the product reviews list

[See Example](trustbadgeexample/src/main/java/com/trustedshops/trustbadgeexample/ProductReviewsActivity.java#L61-L90)

- - - -

## 5. Integration of Buyer Protection after Checkout ##

In order to allow your customers to benefit from our buyer protection the following code has to be added. By this you provide the necessary checkout parameters for guarantee handling. You can decide if you want to show the guarantee dialogue automatically after your checkout is finished or if you want the user to tab on a button for that.
```Java
TrustbadgeOrder tsCheckoutTrustbadgeOrder = new TrustbadgeOrder();

/* Set your Trusted Shops ID here */
tsCheckoutTrustbadgeOrder.setTsId("YOUR-TRUSTED-SHOPS-ID");

/* Mandatory shopping cart parameter*/
tsCheckoutTrustbadgeOrder.setTsCheckoutOrderNr("checkoutOrderNumber"); 
tsCheckoutTrustbadgeOrder.setTsCheckoutOrderAmount("150,00"); 
tsCheckoutTrustbadgeOrder.setTsCheckoutOrderCurrency("EUR"); 
tsCheckoutTrustbadgeOrder.setTsCheckoutOrderPaymentType("PAYPAL");

/* Optional shopping cart parameter */
tsCheckoutTrustbadgeOrder.setTsCheckoutBuyerEmail("tester@example.com");
tsCheckoutTrustbadgeOrder.setTsCheckoutOrderEstDeliveryDate("2016-06-30");

/* Add callback for dialog dismiss */
    Handler.Callback dialogClosedCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message callBackResponse) {
            switch (callBackResponse.what) {
                case TrustedShopsCheckout._dismissCallNumber:
                //Card closed
                Log.d("TSDEBUG","Case 1 called");
                break;
                case TrustedShopsCheckout._errorCallNumber:
                //Failure
                Log.d("TSDEBUG","Case 2 called");
                break;
             }
             return true;
         }
    };

try {
    TrustedShopsCheckout tsCheckout = new TrustedShopsCheckout(tsCheckoutTrustbadgeOrder);
    tsCheckout.init(this, dialogClosedCallback);
} catch (TrustbadgeException e) {
    Log.d("TSDEBUG", "Something went wrong " + e);
}
```
#### Mandatory Parameters ####
```Java
.setTsId("YOUR-TRUSTED-SHOPS-ID");
```
This is your app's TS-ID which will be provided by Trusted Shops. <br>In order to get your TS-ID authorized please see the "Authorization" section below.  There you also find a TS-ID for testing.

```Java
.setTsCheckoutOrderNr("0123456789"); 
.setTsCheckoutOrderAmount("150,00"); 
.setTsCheckoutOrderCurrency("EUR"); 
```
ISO 4217 Currency code: https://en.wikipedia.org/wiki/ISO_4217
```Java
.setTsCheckoutOrderPaymentType("CREDIT_CARD");
```
Allowed payment type values are:<br>
`"DIRECT_DEBIT"` ,
`"CASH_ON_PICKUP"` ,
`"CLICKANDBUY"` ,
`"FINANCING"` ,
`"GIROPAY"` ,
`"GOOGLE_CHECKOUT"` ,
`"CREDIT_CARD"` ,
`"LEASING"` ,
`"MONEYBOOKERS"` ,
`"CASH_ON_DELIVERY"` ,
`"PAYBOX"` ,
`"PAYPAL"` ,
`"INVOICE"` ,
`"CHEQUE"` ,
`"SHOP_CARD"` ,
`"DIRECT_E_BANKING"` ,
`"T_PAY"` ,
`"PREPAYMENT"` ,
`"AMAZON_PAYMENTS"`

#### Optional Parameters ####
```Java
.setTsCheckoutBuyerEmail("testbuyer@testprovider.com"); 
```
Sets the email address of the customer. Only leave this out (i.e. leave it as null or provide "") if you do not have it! The trustbadge needs the customer's email to process the purchase of the buyer protection. If you don't specify it, the card will open a new page in a browser where the customer can fill it in manually. If you already to know the email of your customer and provide it, the trustcard won't have to switch to a browser, all necessary user interaction is done in the card, and thus in your app.
```Java
.setTsCheckoutOrderEstDeliveryDate("Y-m-d");
```
Sets the estimated delivery date for your order
```Java
.enableDebugmode();
```
Enables DEBUG-Logging and uses different endpoint. If set, data are loaded from the Trusted Shops development API instead of the production API (example TS-ID works for debug and normal mode).

#### Product Reviews (optional) ####
If you are also collecting product reviews via Trusted Shops, you have to provide the product list as following
```Java
Product checkoutProduct1 = new Product();
checkoutProduct1.setTsCheckoutProductName("Brother TN-241C");
checkoutProduct1.setTsCheckoutProductSKU("4123123");
checkoutProduct1.setTsCheckoutProductUrl("http://www.brother.de/verbrauchsmaterial/laser/toner/tn/tn241c");
checkoutProduct1.setTsCheckoutProductBrand("Brother");
checkoutProduct1.setTsCheckoutProductGTIN("4977766718400");
checkoutProduct1.setTsCheckoutProductImageUrl("http://www.brother.de/~/media/Product%20Images/Supplies/Laser/Toner/TN/TN241C/TN241C_main.png");
checkoutProduct1.setTsCheckoutProductMPN("TN241C");
tsCheckoutTrustbadgeOrder.addCheckoutProductItem(checkoutProduct1);
```      

- - - -

## 6. Audit and Authorization ##
To use this SDK in your own mobile app Trusted Shops needs to audit and authorize your app.<br>

Please contact your customer success manager directly or via [members@trustedshops.com](mailto:members@trustedshops.com) to start the process.

- - - -

## 7. About this SDK ##

#### Requirements & Dependencies ####
 * We support Android API 15 and above.
 * Trustedshops Android SDK depends on:
 * Square's popular [OkHTTP](http://square.github.io/okhttp/) library
 * afollestad [material dialogs](https://github.com/afollestad/material-dialogs) library

#### Data Privacy ####
Our SDK does not send or collect any user related data without prior permission from the buyer. Only if the buyer opt-in after checkout or opted-in to take advantage of the Trusted Shops guarantee in general, order information are stored for guarantee handling. Before opt-in e-mail addresses are transmitted in irreversible hashed encryption.

#### License ####
Trusted Shops Android SDK is available under the MIT license. See the LICENSE file for more info.

#### About Trusted Shops ####
Today more than 20,000 online sellers are using Trusted Shops to collect, show, and manage genuine feedback from their customers. A large community of online buyers has already contributed over 6 million reviews.
Whether you are a start-up entrepreneur, a professional seller or an international retail brand, consumer trust is a key ingredient for your business. Trusted Shops offers services that will give you the ability to highlight your trustworthiness, improve your service, and, consequently, increase your conversion rate. 

#### Questions and Feedback ####
Your feedback helps us to improve this library. 
If you have any questions concerning this product or the implementation, please contact [productfeedback@trustedshops.com](mailto:productfeedback@trustedshops.com)

#### Looking for iOS? ####
https://github.com/trustedshops/trustbadge_iOS

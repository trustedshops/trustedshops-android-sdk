# Trusted Shops SDK for Android #

[![CI Status](https://travis-ci.org/trustedshops/trustedshops-android-sdk.svg?branch=master)](https://travis-ci.org/trustedshops/trustedshops-android-sdk)
[![Bintray](https://img.shields.io/bintray/v/trustedshops/maven/trustedshops-android-sdk.svg?maxAge=2592000)]()

Integrate our SDK in your shopping app and boost your conversion with **your Trustbadge** and **our buyer protection**:
* Show the Trustbadge in any view and size and provide additional information along with a link to your certificate. 
* Integrate the Trusted Shops buyer protection and review collecting services into your app.

![TrustedShopsAndroidSDK](https://raw.githubusercontent.com/trustedshops/trustedshops-android-sdk/master/trustbadgeexample/screenshots/Android-SDK-woText.png?raw=true "Boost your conversion with Trustbadge and money-back guarantee")

Our SDK supports the following languages: DE, EN, FR, ES, IT, NL, PL.

- - - -

## Installation ##
To install the current version add this to your Module Build File (build.gradle):
```Java
repositories {
    jcenter()
    maven { url "https://jitpack.io" }
}

dependencies {
    compile 'com.trustedshops.androidsdk:trustedshops-android-sdk:1.5.1'
}
```

- - - -

## Display the Trustbadge ##

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
This is your app's TS-ID which will be provided by Trusted Shops. <br>In order to get your TS-ID authorized please see the "Authorization" section below. There you also find a TS-ID for testing.

#### Optional Paramters ####
 ```Java
 .setIconColor("#F98222");
 ```
 Sets the icon color in the lightbox in HEX Format.
 
 ```Java
 .setLoggingActive(true);
  ```
  Enables DEBUG-Logging.

- - - -

## Buyer protection after checkout ##

In order to allow your customers to benefit from our buyer protection the following code has to be added. By this you provide the necessary checkout parameters for guarantee handling. You can decide if you want to show the guarantee dialogue automatically after your checkout is finished or if you want the user to tab on a button for that.
```Java
TrustbadgeOrder tsCheckoutTrustbadgeOrder = new TrustbadgeOrder();

/* Set your Trusted Shops ID here */
tsCheckoutTrustbadgeOrder.setTsId("YOUR-TRUSTED-SHOPS-ID");

/* Mandatory shopping cart parameter*/
tsCheckoutTrustbadgeOrder.setTsCheckoutOrderNr("checkoutOrderNumber"); 
tsCheckoutTrustbadgeOrder.setTsCheckoutBuyerEmail("tester@example.com"); 
tsCheckoutTrustbadgeOrder.setTsCheckoutOrderAmount("150,00"); 
tsCheckoutTrustbadgeOrder.setTsCheckoutOrderCurrency("EUR"); 
tsCheckoutTrustbadgeOrder.setTsCheckoutOrderPaymentType("PAYPAL");

/* Optional shopping cart parameter */
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
.setTsCheckoutBuyerEmail("testbuyer@testprovider.com"); 
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

## About this SDK ##

#### Authorization ####
To use this SDK in your own mobile app Trusted Shops needs to authorize your app.<br>
Please contact us via [productfeedback@trustedshops.com](mailto:productfeedback@trustedshops.com) to get your apps authorized.  

For testing and certification purposes the following TS-ID can be used: ```X330A2E7D449E31E467D2F53A55DDD070```

#### Requirements & Dependencies ####
* We support Android API 16 and above.
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

![TrustedShopsLogo](https://raw.githubusercontent.com/trustedshops/trustedshops-android-sdk/master/trustbadgeexample/screenshots/e_trusted_shops-rgb.png "Trusted Shops - Cologne")

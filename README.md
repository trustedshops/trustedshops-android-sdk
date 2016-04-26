# Trusted Shops SDK for Android

Integrate your Trustbadge in your shopping app and show the Trusted Shops trustmark to your users and lift your conversion rate. Our SDK
- checks the validity of your certificate in the background, 
- shows the trustbadge whereever you want in your app and 
- allows the user to get more information of the certificate's advantages by tapping on the trustbadge (a lightbox appears from which the user can access your detailled review profile)
- currently supports the following languages: DE, EN, FR, ES, IT, NL, PL

<table>
    <tr>
        <td>
            Show the Trustbadge in any view and size in your app.
        </td>
        <td>
            Additional information and link to certificate.
        </td>
        <td>
            Certificate is opened in external browser.
        </td>
        <td>
            After purchase transaction to offer guarantee and collect reviews
        </td>
    </tr>
    <tr>
        <td>
            <img src="https://github.com/trustedshops/trustedshops-android-sdk/blob/master/trustbadgeexample/screenshots/trustbadge.png" alt="Trustbadge in test app" width="240" border="1">
        </td>
        <td>
            <img src="https://github.com/trustedshops/trustedshops-android-sdk/blob/master/trustbadgeexample/screenshots/lightbox.png" alt="Additional information after tap" width="240">
        </td>
        <td>
            <img src="https://github.com/trustedshops/trustedshops-android-sdk/blob/master/trustbadgeexample/screenshots/certificate2.png" alt="Certificate in Browser" width="240">
        </td>
        <td>
            <img src="https://github.com/trustedshops/trustedshops-android-sdk/blob/checkout_card/trustbadgeexample/screenshots/checkout.png" alt="Certificate in Browser" width="240">
        </td>
    </tr>

</table>

## Usage to display trustmark 

To install the current version add this to your Module Build File (build.gradle):
```
repositories {
    jcenter()
    maven { url "https://jitpack.io" }
}

dependencies {
    compile 'com.trustedshops.androidsdk:trustedshops-android-sdk:1.5'
}
```

To display the trustbadge you have to create a view container (here with the ID "trustbadgeTestImageView") in your layout.xml and add the following code in the respective java file: 
```
Trustbadge trustbadge = new Trustbadge("YOUR-TRUSTED-SHOPS-ID");
ImageView imageView = (ImageView) findViewById(R.id.trustbadgeTestImageView);
  try {
    trustbadge.getTrustbadge(imageView, this);
    // trustbadge.setIconColor("#27AE60");
    // trustbadge.setLoggingActive(true);
  } catch(IllegalArgumentException exception) {
      Log.d("TSDEBUG", exception.getMessage());
  } catch (TrustbadgeException exception) {
      Log.d("TSDEBUG", exception.getMessage());
}
```


When you’ve finished entering the above code, you will probably see some red warning text from Android Studio. This is because some of the classes we’re referencing are not imported yet. You can automatically import these classes by pressing Alt + Enter (Alt + Return on Mac). Alternatively, manually add the following to the top of your java file:
```
import com.trustedshops.androidsdk.trustbadge.Trustbadge;
import com.trustedshops.androidsdk.trustbadge.TrustbadgeException;
```

#### Mandatory Parameters

```
new Trustbadge("YOUR-TRUSTED-SHOPS-ID");
```
This is your app's TS-ID which will be provided by Trusted Shops. In order to get your TS-ID authorized please see the "Authorization" section below. For testing purposes the following TS-ID can be used:
```
new Trustbadge("X330A2E7D449E31E467D2F53A55DDD070");
```

#### Optional Parameters

```
.setIconColor("#27AE60");
```
Sets the icon color in HEX Format

```
.setLoggingActive(true);
```
Enables DEBUG-Logging


## Usage to initialize after purchase trustbadge 

In oder to display the trustbadge after purchase to offer guarantee and collect reviews you have to provide following checkout parameters:


```     
TrustbadgeOrder tsCheckoutTrustbadgeOrder = new TrustbadgeOrder();
/* Set your Trusted Shops ID here */
tsCheckoutTrustbadgeOrder.setTsId("X330A2E7D449E31E467D2F53A55DDD070");
  
tsCheckoutTrustbadgeOrder.setTsCheckoutOrderNr(checkoutOrderNumber);
tsCheckoutTrustbadgeOrder.setTsCheckoutBuyerEmail("tester@example.com");
tsCheckoutTrustbadgeOrder.setTsCheckoutOrderAmount("150");
tsCheckoutTrustbadgeOrder.setTsCheckoutOrderCurrency("EUR");
tsCheckoutTrustbadgeOrder.setTsCheckoutOrderPaymentType("PAYPAL");

/* optional */
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
If you are also collection product reviews, you have to provide the product list
```
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

#### Mandatory Parameters

```
setTsId
setTsCheckoutOrderNr
setTsCheckoutBuyerEmail
setTsCheckoutOrderAmount
setTsCheckoutOrderCurrency
setTsCheckoutOrderPaymentType

```

#### Optional Parameters

```
.setTsCheckoutOrderEstDeliveryDate("Y-m-d");
```
Sets the estimated delivery date for your order

```
.enableDebugmode();
```
Enables DEBUG-Logging and usess different endpoint


## Authorization

To use this SDK in your own mobile app Trusted Shops needs to authorize your app.<br>
Please contact us via [productfeedback@trustedshops.com](mailto:productfeedback@trustedshops.com) to get your apps authorized.  

## Requirements
We support Android API 16 and above.

## Dependencies
Trustedshops Android SDK depends on:
* Square's popular [OkHTTP](http://square.github.io/okhttp/) library
* afollestad [material dialogs](https://github.com/afollestad/material-dialogs) library*

## About Trusted Shops

Today more than 20,000 online sellers are using Trusted Shops to collect, show, and manage genuine feedback from their customers. A large community of online buyers has already contributed over 6 million reviews.
Whether you are a start-up entrepreneur, a professional seller or an international retail brand, consumer trust is a key ingredient for your business. Trusted Shops offers services that will give you the ability to highlight your trustworthiness, improve your service, and, consequently, increase your conversion rate. 

## Questions and Feedback

Your feedback helps us to improve this library. 
If you have any questions concerning this product or the implementation, please contact [productfeedback@trustedshops.com](mailto:productfeedback@trustedshops.com)

## License

Trusted Shops Android SDK is available under the MIT license. See the LICENSE file for more info.

## Looking for iOS SDK?
https://github.com/trustedshops/trustbadge_iOS

# Trusted Shops SDK for Android

Integrate your Trustbadge in your shopping app and show the Trusted Shops trustmark to your user and lift your conversion rate.

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
    </tr>

</table>


The SDK currently supports the following languages: DE, EN, FR, ES, IT, NL, PL.

## Usage

Add the following to your build.gradle:
```
repositories {
    jcenter()
}

dependencies {
    compile 'com.trustedshops.androidsdk:trustedshops-android-sdk:1.0'
}
```

See Example Project for how to use Trusted Shops Android SDK

```
Trustbadge trustbadge = new Trustbadge("YOUR-TRUSTED-SHOPS-ID");
ImageView imageView = (ImageView) findViewById(R.id.trustbadgeTestImageView);
  try {
    trustbadge.setClientToken("YOUR-TRUSTED-SHOPS-CLIENT-TOKEN");    
    trustbadge.getTrustbadge(imageView, activity);        
  } catch(IllegalArgumentException exception) {
      Log.d("TSDEBUG", exception.getMessage());
  } catch (TrustbadgeException exception) {
      Log.d("TSDEBUG", exception.getMessage());
}
```    

#### Mandatory Parameters

```
.setClientToken("YOUR-TRUSTED-SHOPS-CLIENT-TOKEN");
```
The client token will be provided by Trusted Shops upon request. It is used for authorization purposes.

#### Optional Parameters

```
.setIconColor("#F98222");
```
Sets the icon color in HEX Format

```
.setLoggingActive(true);
```
Enables DEBUG-Logging

## Requirements
We support Android API 16 and above.

## Dependencies
Trustedshops Android SDK depends on:
* Square's popular [OkHTTP](http://square.github.io/okhttp/) library

## About Trusted Shops

Today more than 20,000 online sellers are using Trusted Shops to collect, show, and manage genuine feedback from their customers. A large community of online buyers has already contributed over 6 million reviews.
Whether you are a start-up entrepreneur, a professional seller or an international retail brand, consumer trust is a key ingredient for your business. Trusted Shops offers services that will give you the ability to highlight your trustworthiness, improve your service, and, consequently, increase your conversion rate. 

## Questions and Feedback

Your feedback helps us to improve this library. 
If you have any questions concerning this product or the implementation, please contact productfeedback@trustedshops.com

## License

Trusted Shops Android SDK is available under the MIT license. See the LICENSE file for more info.

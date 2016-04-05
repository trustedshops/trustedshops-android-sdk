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

The SDK 
- checks the validity of your certificate in the background, 
- shows the trustbadge whereever you want in your app and 
- allows the user to get more information of the certificate's advantages by tapping on the trustbadge (a lightbox appears from which the user can access your detailled review profile)
- currently supports the following languages: DE, EN, FR, ES, IT, NL, PL.

## Usage

To install the current version add this to your Module Build File (build.gradle):
```
repositories {
    jcenter()
    maven { url "https://jitpack.io" }
}

dependencies {
    compile 'com.trustedshops.androidsdk:trustedshops-android-sdk:1.1'
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
When you’ve finished entering the above code, you will probably see some red warning text from Android Studio. This is because we haven’t yet imported some of the classes we’re referencing. You can automatically import these classes by pressing Alt + Enter (Alt + Return on Mac). Alternatively, manually add the following to the top of your java file:
```
import com.trustedshops.androidsdk.trustbadge.Trustbadge;
import com.trustedshops.androidsdk.trustbadge.TrustbadgeException;
```

#### Mandatory Parameters

```
new Trustbadge("YOUR-TRUSTED-SHOPS-ID");
```
This is your app's TS-ID which will be provided by Trusted Shops.
For testing purposes the following TS-ID can be used:
```
new Trustbadge("X330A2E7D449E31E467D2F53A55DDD070");
```

#### Optional Parameters

```
.setIconColor("#F98222");
```
Sets the icon color in HEX Format

```
.setLoggingActive(true);
```
Enables DEBUG-Logging

## Authorization

To use this SDK in your own mobile app Trusted Shops needs to authorize your app. 
Please contact us via productfeedback@trustedshops.com to get your apps authorized.  

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

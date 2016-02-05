# Trusted Shops SDK for Android

Integrate your Trustbadge within your shopping app. Show the Trusted Shops trustmark together with your seller rating and stars to new and existing customers when they use your app.

## Usage

Add the following to your build.gradle:
```
repositories {
    maven {
        url  "http://dl.bintray.com/trustedshops/maven"
    }
}

dependencies {
    compile 'com.trustedshops.androidsdk:trustedshops-android-sdk:0.0.2'
}
```

See Example Project for how to use Trusted Shops Android SDK

```
Trustbadge trustbadge = new Trustbadge("YOUR-TRUSTED-SHOPS-ID");
  try {
    trustbadge.getTrustbadge(imageView);
  } catch(IllegalArgumentException exception) {
      Log.d("TSDEBUG", exception.getMessage());
  } catch (TrustbadgeException exception) {
      Log.d("TSDEBUG", exception.getMessage());
}
```    

## About Trusted Shops

Today more than 20,000 online sellers are using Trusted Shops to collect, show, and manage genuine feedback from their customers. A large community of online buyers has already contributed over 6 million reviews.
Whether you are a start-up entrepreneur, a professional seller or an international retail brand, consumer trust is a key ingredient for your business. Trusted Shops offers services that will give you the ability to highlight your trustworthiness, improve your service, and, consequently, increase your conversion rate.

## Questions and Feedback

Your feedback helps us make this library better. If you have any questions concerning this product or the implementation, please contact productfeedback@trustedshops.com

## License

Trusted Shops Android SDK is available under the MIT license. See the LICENSE file for more info.
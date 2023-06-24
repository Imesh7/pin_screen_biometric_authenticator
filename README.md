<!--
This README describes the package. If you publish this package to pub.dev,
this README's contents appear on the landing page for your package.

For information about how to write a good package README, see the guide for
[writing package pages](https://dart.dev/guides/libraries/writing-package-pages).

For general information about developing packages, see the Dart guide for
[creating packages](https://dart.dev/guides/libraries/create-library-packages)
and the Flutter guide for
[developing packages and plugins](https://flutter.dev/developing-packages).
-->
<div align="center">  
 <h1 align="center" style="font-size: 70px;">Flutter Pin screen with Biometric Authenticator</h1>
  </div>


Flutter package to create easily on screen pin input with biometric authentication.for Biometric its use Native SDK



<p>Disclaimer : As of Now Only support for <strong>Android</strong> Platform</p>

| Android | iOS | MacOS | Web | Linux | Windows |
| :-----: | :-: | :---: | :-: | :---: | :-----: |
|   ✅    | ❌  |  ❌   | ❌  |  ❌   |   ❌    |


## Feature

- Pin input
- Biometric Authentication
- Latest Native sdk support

## Demo

In the below gif you can see the Black screen , it's happens when I tap the biometric icon on the screen.
screen recorders cannot get catch due to underlaying OS restrictions.

<img src="https://i.ibb.co/1MbyZxN/pin-input-biometric-authenticator.gif"  width="250" />



## Usage


first add the biometric permission to AndroidManifest.xml file
``` xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
          package="com.example.app">
  <uses-permission android:name="android.permission.USE_BIOMETRIC"/>
<manifest> 

```


<p><code>PinScreenWithBiometric</code> class to implement.</p>

```dart
            //implementation
              PinScreenWithBiometric(
                controller: textEditingController,
                textFieldLength: 4,
                authResultCallBack: (bool? result) {
                  debugPrint("BIOMETRIC RESULT IS `$result`");
                },
              ),
           
```

## Additional information

Welcome for Contribution

## License
This package is licensed under the MIT license. See [LICENSE](https://github.com/Imesh7/pin_screen_biometric_authenticator/blob/main/LICENSE) for details.

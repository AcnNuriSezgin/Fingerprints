# Fingerprints
This library provides a fingerprint dialog and its implementation. The FingerprintManager
integration is encapsulated by FingerprintAdapter, that object communicate with system manager
and notify current state over callbacks.

The Dialog's ui much more configurable, you should give a layout id and cancel button id. The layout id that should be shown on dialog UI.

## Prerequisites
First, dependency must be added to build.gradle file.
```groovy
    implementation 'nurisezgin.com.fingerprint:fingerprint-dialog:1.0.0'
```

## How To Use

#### Dialog

```java
    DialogUIProperties uiProp = new DialogUIProperties(R.layout.dialog_fingerprint, R.id.cancel);
    KeystoreProperties keystoreProp = new KeystoreProperties(ANDROID_KEY_STORE, "sampleKey");
    // on Activity
    FingerprintDialog dialog = FingerprintDialog.newInstance(uiProp, keystoreProp);
    // on Fragment
    // FingerprintDialog dialog = FingerprintDialog.newInstance(targetFragment, uiProp, keystoreProp);
    FingerprintDialog.showDialog(this, dialog);
```

#### Error Messages

All error messages are coming from FingerprintDialogPlugin, that class keeps given
error messages and provide when any of them need.

```java
    FingerprintDialogPlugin.setErrorFingerprintAuth("Auth Error");
    FingerprintDialogPlugin.setErrorFingerprintEnrollment("Enrollment Error");
    FingerprintDialogPlugin.setErrorFingerprintUnrecognized("Unrecognized Error");
    FingerprintDialogPlugin.setErrorKeyguardSecurity("Security Error");
    FingerprintDialogPlugin.setErrorUnsupportedDevice("Unsupported Error");
```

## Authors
* **Nuri SEZGIN**-[Email](acnnurisezgin@gmail.com)

## Licence

```
Copyright 2018 Nuri SEZGİN

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


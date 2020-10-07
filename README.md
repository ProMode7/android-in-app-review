# android-in-app-review
A sample demonstration of Android's new In-App Review API which lets you prompt users to submit Play Store ratings and reviews without the inconvenience of leaving your app or game. 

# HOW TO USE

1. Import the Play Core Library into your project: Add the following in your app’s build.gradle file:


```gradle
dependencies {
    // This dependency is downloaded from the Google’s Maven repository.
    // So, make sure you also include that repository in your project's build.gradle file.
    implementation 'com.google.android.play:core:1.8.2'

    // For Kotlin users also import the Kotlin extensions library for Play Core:
    implementation 'com.google.android.play:core-ktx:1.8.1'
}
```

2. Create the ReviewManager instance

```kotlin
// If using kotlin: 
val manager = ReviewManagerFactory.create(context)

// If using Java:
ReviewManager manager = ReviewManagerFactory.create(context)
```

3. Request a ReviewInfo object

```kotlin
// If using kotlin: 
val request = manager.requestReviewFlow()
request.addOnCompleteListener { request ->
    if (request.isSuccessful) {
        // We got the ReviewInfo object
        val reviewInfo = request.result
        val flow = manager.launchReviewFlow(activity, reviewInfo)
        flow.addOnCompleteListener { _ ->
            // The flow has finished. The API does not indicate whether the user
            // reviewed or not, or even whether the review dialog was shown. Thus, no
            // matter the result, we continue our app flow.
        }
    } else {
        // There was some problem, continue regardless of the result.
        // you can show your own rate dialog alert and redirect user to your app page
        // on play store.
    }
}

// If using Java
ReviewManager manager = ReviewManagerFactory.create(this);
Task<ReviewInfo> request = manager.requestReviewFlow();
request.addOnCompleteListener(task -> {
    if (task.isSuccessful()) {
        // We can get the ReviewInfo object
        ReviewInfo reviewInfo = task.getResult();
        Task<Void> flow = manager.launchReviewFlow(activity, reviewInfo);
        flow.addOnCompleteListener(task -> {
            // The flow has finished. The API does not indicate whether the user
            // reviewed or not, or even whether the review dialog was shown. Thus, no
            // matter the result, we continue our app flow.
        });
    } else {
        // There was some problem, continue regardless of the result.
        // you can show your own rate dialog alert and redirect user to your app page
        // on play store.
    }
});
```

For more detailed information please follow this link: https://developer.android.com/guide/playcore/in-app-review

![Sample Screenshot](https://developer.android.com/images/google/play/in-app-review/iar-flow.jpg "Sample Screenshot from android dev documentation")

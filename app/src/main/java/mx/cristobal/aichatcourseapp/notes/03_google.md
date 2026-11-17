# Firebase Authentication & Google Sign-In Guide

This document summarizes the steps required to integrate Firebase Authentication with Google Sign-In in an Android Jetpack Compose app, with a detailed breakdown of the repository pattern and authentication state listeners.

---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

## 1. Summary of Firebase Setup Steps

1. **Firebase Console & CLI Setup**:
   - Create a Firebase project in the [Firebase Console](https://console.firebase.google.com/).
   - Enable **Firebase Authentication** and enable the **Google** sign-in provider.
   - Obtain or fetch `google-services.json` programmatically via the Firebase CLI (`npx -y firebase-tools@latest apps:sdkconfig ANDROID <APP_ID>`) and place it in your app module (`app/google-services.json`).

2. **Gradle & Dependencies Configuration**:
   - Add the Google Services plugin to the root and app-level `build.gradle.kts`:
     ```kotlin
     id("com.google.gms.google-services")
     ```
   - Add the Firebase BoM and authentication/credentials libraries in `app/build.gradle.kts`:
     ```kotlin
     implementation(platform("com.google.firebase:firebase-bom:35.2.0"))
     implementation("com.google.firebase:firebase-auth")
     implementation("androidx.credentials:credentials:1.6.0")
     implementation("androidx.credentials:credentials-play-services-auth:1.6.0")
     implementation("com.google.android.libraries.identity.googleid:googleid:1.2.1")
     implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.11.0")
     ```

3. **Secure Credentials (`local.properties`)**:
   - Store your Google Web Client ID securely in `local.properties`:
     ```properties
     WEB_CLIENT_ID=your_web_client_id_here.apps.googleusercontent.com
     ```
   - Expose it to Kotlin code safely via `BuildConfig` in `app/build.gradle.kts`:
     ```kotlin
     buildConfigField("String", "WEB_CLIENT_ID", "\"${localProperties.getProperty("WEB_CLIENT_ID") ?: ""}\"")
     ```

---

## 2. Deep Dive: `AuthRepository` & Auth State Listener

The `AuthRepository` encapsulates all Firebase Auth and Credential Manager logic. It provides reactive state updates using Kotlin Coroutines `callbackFlow`.

### Why `callback` and `AuthStateListener`?
Firebase Authentication operates asynchronously on the server and local token cache. When a user signs in, signs out, or when the app restarts with an existing session, Firebase triggers its `AuthStateListener`.

```kotlin
val currentUser: StateFlow<FirebaseUser?> = callbackFlow {
    val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        // Whenever Firebase auth state changes, send the current user to the flow
        trySend(firebaseAuth.currentUser)
    }
    auth.addAuthStateListener(listener)
    
    // Immediately emit current user upon subscription
    trySend(auth.currentUser)

    // Clean up listener when collector is cancelled
    awaitClose {
        auth.removeAuthStateListener(listener)
    }
}.stateIn(
    scope = CoroutineScope(Dispatchers.Main),
    started = SharingStarted.Eagerly,
    initialValue = null
)
```

### How Google Sign-In works with Credential Manager & Firebase:
1. **Credential Manager Request**:
   Using Android's modern Credential Manager API (`GetGoogleIdOption`), we request Google ID credentials using our server client ID (`BuildConfig.WEB_CLIENT_ID`).
2. **Obtaining ID Token**:
   The user selects their Google account via the system sheet, returning a `GoogleIdTokenCredential` containing the Google ID token (`idToken`).
3. **Firebase Authentication**:
   We exchange the Google ID token for Firebase credentials (`GoogleAuthProvider.getCredential(idToken, null)`) and sign in asynchronously using `auth.signInWithCredential(authCredential).await()`.
4. **Reactive State Emission**:
   Once Firebase confirms successful authentication, `FirebaseAuth.AuthStateListener` automatically fires, emitting the authenticated `FirebaseUser` through `currentUser` `StateFlow` across the app.

# StackOverflow App

A simple app to display the top 20 users on StackOverflow.

## Design

The app has a simple MVP pattern. The presenter does the bulk of the work, but is abstracted away from the Android SDK, and unit testable.

We have a separate domain package, with POJOs that we convert from our api responses.

Retrofit, OkHttp and Gson are used in the API client.

Retrolambda and Streams are used for cleaner, more concise code.

Note: you need a Java 8 JDK to compile this (for retrolambda)


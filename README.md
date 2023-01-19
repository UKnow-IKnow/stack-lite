# Stack Lite

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?&style=for-the-badge&logo=kotlin&logoColor=white)

## Features:
* Shows a list of questions from stack exchange Api
* Can search for the desires question.
* Can add tags to your search.
* Can open the question on Chrome Custom tab.  

## Tech Stack used 👨‍💻 :

- [MVVM](https://developer.android.com/jetpack/guide) - MVVM is one of the architectural patterns which enhances separation of concerns, it allows separating the user interface logic from the business (or the back-end) logic. Its target (with other MVC patterns goal) is to achieve the following principle “Keeping UI code simple and free of app logic in order to make it easier to manage”.
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development. Our app is totally written in kotlin.
- [Different Layouts](https://developer.android.com/guide/topics/ui/declaring-layout) -  In this app we have used difrenet layouts to make the app UI responsive. The used layouts are LinearLayout, ConstraintLayout and FrameLayout .
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more. Speacially used at the time of networking calls and using database .
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [Data Binding](https://developer.android.com/topic/libraries/data-binding?authuser=2) - The Data Binding Library is a support library that allows you to bind UI components in your layouts to data sources in your app using a declarative format rather than programmatically. Used two data binding in each fragments.
  - [Navigation](https://developer.android.com/guide/navigation#:~:text=Navigation%20refers%20to%20the%20interactions,bars%20and%20the%20navigation%20drawer.) - Navigation refers to the interactions that allow users to navigate across, into, and back out from the different pieces of content within your app. In our app we followed single app architecture using navigation and also implemented an unique nav nav drawer.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - LiveData is an observable data holder class. Unlike a regular observable, LiveData is lifecycle-aware, meaning it respects the lifecycle of other app components, such as activities, fragments, or services. 
- [Retrofit](https://github.com/square/retrofit) - A type-safe HTTP client for Android and Java.
- [RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview?authuser=2) - RecyclerView makes it easy to efficiently display large sets of data. To show large lists.
- [Glide](https://github.com/bumptech/glide.git) - Glide is a fast and efficient open-source media management and image loading framework for Android developed by bumptech. In this tutorial, we will use this awesome library to show an image from the internet on our apps screen.
- [Shimmer](https://github.com/facebook/shimmer-android.git) - Shimmer is an Android library that provides an easy way to add a shimmer effect to any view in your Android app, It is useful as an unobtrusive loading indicator, and was originally developed for Facebook Home.
- [Chrome Custom Tabs](https://developer.chrome.com/docs/android/custom-tabs/integration-guide/) - Custom Tabs is a browser feature, introduced by Chrome, that is now supported by most major browsers on Android. It give apps more control over their web experience, and make transitions between native and web content more seamless without having to resort to a WebView.

## API used:
[Stack Exchange API](https://api.stackexchange.com/)

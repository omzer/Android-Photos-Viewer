# Photos Viewer App 
##### Android App where you can view Photos (coming from an [API](https://picsum.photos/v2/list)) and mark the ones you like as favorite.
##### Here's a [VIDEO](https://youtu.be/QQeFFim8O58) on how to use it.

| Main Screen   | Favorite screen    | Photo view Screen | App in action |
| ---   | ---   | --- | --- |
| <img src="https://github.com/omzer/Android-Photos-Viewer/blob/master/app/src/main/res/drawable/screen_main.png?raw=true" width="300"> | <img src="https://github.com/omzer/Android-Photos-Viewer/blob/master/app/src/main/res/drawable/screen_favorite.png?raw=true" width="300">| <img src="https://github.com/omzer/Android-Photos-Viewer/blob/master/app/src/main/res/drawable/screen_view.png?raw=true" width="300">| <img src="https://github.com/omzer/Android-Photos-Viewer/blob/master/app/src/main/res/drawable/gif.gif?raw=true" width="300">

## Technical details about libraries/techniques used
- [Retrofit](https://github.com/square/retrofit) for the API calls.
- [Room](https://developer.android.com/topic/libraries/architecture/room) for local storage.
- MVVM architecture.
- RxJava for observing Retrofit calls.
- Kotlin coroutines for waiting on local DB calls.

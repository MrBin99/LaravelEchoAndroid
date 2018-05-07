[![](https://jitpack.io/v/MrBin99/LaravelEchoAndroid.svg)](https://jitpack.io/#MrBin99/LaravelEchoAndroid)

# LaravelEchoAndroid

Laravel Echo Android client.


###Installation
In your ``build.gradle`` paste the code bellow :
```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

And in ``dependencies`` :
`````gradle
dependencies {
    compile 'com.github.MrBin99:LaravelEchoAndroid:0.1pre'
}
`````

Thanks for this library [https://github.com/socketio/socket.io-client-java](https://github.com/socketio/socket.io-client-java) which is a wrapper of Socket.IO in Java.
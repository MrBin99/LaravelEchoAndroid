[![](https://jitpack.io/v/MrBin99/LaravelEchoAndroid.svg)](https://jitpack.io/#MrBin99/LaravelEchoAndroid)

# LaravelEchoAndroid

Laravel Echo Android client.


## Installation
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
    compile 'com.github.MrBin99:LaravelEchoAndroid:1.0'
}
`````

## Basic usage

### Creation

```java
// Setup options
EchoOptions options = new EchoOptions();

// Setup host of your Laravel Echo Server
options.host = "http://{my-host}:{my-port}";

/*
 * Add headers for authorizing your users (private and presence channels).
 * This line can change matching how you have configured 
 * your guards on your Laravel application
 */
options.headers.put("Authorization", "Bearer {token}");

// Create the client
Echo echo = new Echo(options);
echo.connect(new EchoCallback() {
    @Override
    public void call(Object... args) {
        // Success connect
    }
}, new EchoCallback() {
    @Override
    public void call(Object... args) {
        // Error connect
    }
});
```

Don't forget to close your Echo client when you've done with it or when
the user kill the app.
Warning: closing the socket means that you wil unsubscribe to all channels, and you will quit all presence channel you're currently in.

```java
@Override
public void onDestroy() {
    super.onDestroy();

    echo.disconnect();
}
```

### Public channels

For subscribing on public channel (which means no authentication is needed) and listening for an event :

```java
echo.channel("channel-name")
    .listen("EventName", new EchoCallback() {
        @Override
        public void call(Object... args) {
            // Event thrown.
        }
    });
```

### Private channels

For subscribing on private channel, this means that you need to be authenticated with headers and have the right to 
enter this channel :

```java
SocketIOPrivateChannel privateChannel = echo.privateChannel("channel-name");
privateChannel.listen("EventName", new EchoCallback() {
    @Override
    public void call(Object... args) {
        // Event thrown.
    }
});
```

If you want to send a private message to only one member of this private channel :

```java
try {
    JSONObject data = new JSONObject();
    data.put("name", "john-doe");
    privateChannel.whisper("hello", data, new EchoCallback() {
        @Override
        public void call(Object... args) {
            // Whisper received
        }
    });
} catch (Exception e) {
    e.printStackTrace();
}
```

And on the other side :

```java
privateChannel.listenForWhisper("hello", new EchoCallback() {
    @Override
    public void call(Object... args) {
        // Received !     
    }
});
```

### Presence channels

To enter a presence, like a private channel, you must be authenticated and have the rights.

```java 
SocketIOPresenceChannel presenceChannel = echo.presenceChannel("presence-channel");
```

You can now listen to particular events on presence channels :

```java
presenceChannel.here(new EchoCallback() {
    @Override
    public void call(Object... args) {
        // Gets users present in this channel.
        // Called just after connecting to it.
    }
});

presenceChannel.joining(new EchoCallback() {
    @Override
    public void call(Object... args) {
        // Called when new user join the channel.
    }
});

presenceChannel.leaving(new EchoCallback() {
    @Override
    public void call(Object... args) {
        // Called when a user leave the channel
    }
});
```

### Leaving

For leaving a channel and don't receive events related to them anymore :

```java
echo.leave("channel-name");
```

For presence channel, this action make Laravel Echo Server send a "leaving" event to all other clients connected to this channel.

### Listening for general Socket.io events

You can also listen for generic Socket.io events :

```java
echo.on(Socket.EVENT_ERROR, new EchoCallback() {
    @Override
    public void call(Object... args) {
        // Callback
    }
});
```

You can check all available predefined events on the `io.socket.client.Socket` class or on [https://stackoverflow.com/questions/24224287/list-of-socket-io-events](https://stackoverflow.com/questions/24224287/list-of-socket-io-events).

If you want to delete the callback :

```java
echo.off(Socket.EVENT_ERROR);
```

### More information

For more information, please visit the [Laravel Broadcasting documentation](https://laravel.com/docs/5.6/broadcasting).

## Thanks

Thanks for this library [https://github.com/socketio/socket.io-client-java](https://github.com/socketio/socket.io-client-java) which is a wrapper of Socket.IO in Java.

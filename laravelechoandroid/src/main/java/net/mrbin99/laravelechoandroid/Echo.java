/*
 * Echo.java
 * MrBin99 © 2018
 */
package net.mrbin99.laravelechoandroid;

import net.mrbin99.laravelechoandroid.channel.SocketIOChannel;
import net.mrbin99.laravelechoandroid.channel.SocketIOPresenceChannel;
import net.mrbin99.laravelechoandroid.channel.SocketIOPrivateChannel;
import net.mrbin99.laravelechoandroid.connector.SocketIOConnector;

/**
 * This class is the primary API for interacting with broadcasting.
 */
public final class Echo {

    /**
     * The broadcasting connector.
     */
    private SocketIOConnector connector;

    /**
     * Creates a new Echo instance with default options.
     */
    public Echo() {
        this(new EchoOptions());
    }

    /**
     * Create a new Echo instance.
     *
     * @param options options
     */
    public Echo(EchoOptions options) {
        this.connector = new SocketIOConnector(options);
    }

    /**
     * Connect to the Echo server.
     *
     * @param success success callback
     * @param error   error callback
     */
    public void connect(EchoCallback success, EchoCallback error) {
        connector.connect(success, error);
    }

    /**
     * Listen for general event on the socket.
     *
     * @param eventName event name
     * @param callback  callback
     * @see io.socket.client.Socket list of event types to listen to
     */
    public void on(String eventName, EchoCallback callback) {
        connector.on(eventName, callback);
    }

    /**
     * Remove all listeners for a general event.
     *
     * @param eventName event name
     */
    public void off(String eventName) {
        connector.off(eventName);
    }

    /**
     * Listen for an event on a channel instance.
     *
     * @param channel  channel name
     * @param event    event name
     * @param callback callback when event is triggered
     * @return the channel
     */
    public SocketIOChannel listen(String channel, String event, EchoCallback callback) {
        return connector.listen(channel, event, callback);
    }

    /**
     * Get a channel by name.
     *
     * @param channel channel name
     * @return the channel
     */
    public SocketIOChannel channel(String channel) {
        return (SocketIOChannel) connector.channel(channel);
    }

    /**
     * Get a private channel by name.
     *
     * @param channel channel name
     * @return the channel
     */
    public SocketIOPrivateChannel privateChannel(String channel) {
        return (SocketIOPrivateChannel) connector.privateChannel(channel);
    }

    /**
     * Get a presence channel by name.
     *
     * @param channel channel name
     * @return the channel
     */
    public SocketIOPresenceChannel presenceChannel(String channel) {
        return (SocketIOPresenceChannel) connector.presenceChannel(channel);
    }

    /**
     * Leave the given channel.
     *
     * @param channel channel name
     */
    public void leave(String channel) {
        connector.leave(channel);
    }

    /**
     * @return if currently connected to server.
     */
    public boolean isConnected() {
        return connector.isConnected();
    }

    /**
     * Disconnect from the Echo server.
     */
    public void disconnect() {
        connector.disconnect();
    }
}

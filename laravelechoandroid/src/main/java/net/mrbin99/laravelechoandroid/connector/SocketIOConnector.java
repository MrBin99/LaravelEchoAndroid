/*
 * SocketIOConnector.java
 * MrBin99 © 2018
 */
package net.mrbin99.laravelechoandroid.connector;

import io.socket.client.IO;
import io.socket.client.Socket;
import net.mrbin99.laravelechoandroid.EchoCallback;
import net.mrbin99.laravelechoandroid.EchoException;
import net.mrbin99.laravelechoandroid.EchoOptions;
import net.mrbin99.laravelechoandroid.channel.AbstractChannel;
import net.mrbin99.laravelechoandroid.channel.SocketIOChannel;
import net.mrbin99.laravelechoandroid.channel.SocketIOPresenceChannel;
import net.mrbin99.laravelechoandroid.channel.SocketIOPrivateChannel;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class creates a connector to a Socket.io server.
 */
public class SocketIOConnector extends AbstractConnector {

    /**
     * The socket.
     */
    private Socket socket;

    /**
     * All of the subscribed channel names.
     */
    private Map<String, SocketIOChannel> channels;

    /**
     * Create a new Socket.IO connector.
     *
     * @param options options
     */
    public SocketIOConnector(EchoOptions options) {
        super(options);

        channels = new HashMap<>();
    }

    @Override
    public void connect(EchoCallback success, EchoCallback error) {
        try {
            socket = IO.socket(this.options.host);
            socket.connect();

            if (success != null) {
                socket.on(Socket.EVENT_CONNECT, success);
            }

            if (error != null) {
                socket.on(Socket.EVENT_CONNECT_ERROR, error);
            }
        } catch (URISyntaxException e) {
            if (error != null) {
                error.call();
            }
        }
    }

    /**
     * Listen for general event on the socket.
     *
     * @param eventName event name
     * @param callback  callback
     * @see io.socket.client.Socket list of event types to listen to
     */
    public void on(String eventName, EchoCallback callback) {
        socket.on(eventName, callback);
    }

    /**
     * Remove all listeners for a general event.
     *
     * @param eventName event name
     */
    public void off(String eventName) {
        socket.off(eventName);
    }

    /**
     * Listen for an event on a channel.
     *
     * @param channel  channel name
     * @param event    event name
     * @param callback callback
     * @return the channel
     */
    public SocketIOChannel listen(String channel, String event, EchoCallback callback) {
        return (SocketIOChannel) this.channel(channel).listen(event, callback);
    }

    @Override
    public AbstractChannel channel(String channel) {
        if (!channels.containsKey(channel)) {
            channels.put(channel, new SocketIOChannel(socket, channel, options));
        }
        return channels.get(channel);
    }

    @Override
    public AbstractChannel privateChannel(String channel) {
        String name = "private-" + channel;

        if (!channels.containsKey(name)) {
            channels.put(name, new SocketIOPrivateChannel(socket, name, options));
        }
        return channels.get(name);
    }

    @Override
    public AbstractChannel presenceChannel(String channel) {
        String name = "presence-" + channel;

        if (!channels.containsKey(name)) {
            channels.put(name, new SocketIOPresenceChannel(socket, name, options));
        }
        return channels.get(name);
    }

    @Override
    public void leave(String channel) {
        String privateChannel = "private-" + channel;
        String presenceChannel = "presence-" + channel;

        Iterator<Map.Entry<String, SocketIOChannel>> iterator = channels.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, SocketIOChannel> entry = iterator.next();
            SocketIOChannel socketIOChannel = entry.getValue();
            String subscribed = entry.getKey();

            if (subscribed.equals(channel) || subscribed.equals(privateChannel) || subscribed.equals(presenceChannel)) {
                try {
                    socketIOChannel.unsubscribe(null);
                } catch (EchoException e) {
                    e.printStackTrace();
                }

                iterator.remove();
            }
        }
    }

    @Override
    public boolean isConnected() {
        return socket != null && socket.connected();
    }

    @Override
    public void disconnect() {
        for (String subscribed : channels.keySet()) {
            try {
                channels.get(subscribed).unsubscribe(null);
            } catch (EchoException e) {
                e.printStackTrace();
            }
        }

        channels.clear();
        socket.disconnect();
    }
}

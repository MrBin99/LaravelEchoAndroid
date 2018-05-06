/*
 * EchoOptions.java
 * MrBin99 © 2018
 */
package net.mrbin99.laravelechoandroid;

import android.util.ArrayMap;
import org.json.JSONObject;

import java.util.Map;

/**
 * Echo options.
 */
public final class EchoOptions {

    /**
     * Host of the Echo server.
     */
    public String host;

    /**
     * Host endpoint.
     */
    public String authEndpoint;

    /**
     * Event namespace.
     */
    public String eventNamespace;

    /**
     * Request headers.
     */
    public Map<String, String> headers;

    /**
     * Create default object of options.
     */
    public EchoOptions() {
        headers = new ArrayMap<>();
        host = "http://localhost:6001";
        authEndpoint = "/broadcasting/auth";
        eventNamespace = "App.Events";
    }

    /**
     * @return the auth JSON object.
     * @throws Exception if error creating the JSON.
     */
    public JSONObject getAuth() throws Exception {
        JSONObject auth = new JSONObject();
        JSONObject headers = new JSONObject();

        for (String header : this.headers.keySet()) {
            headers.put(header, this.headers.get(header));
        }

        auth.put("headers", headers);

        return auth;
    }
}

/*
 * EchoOptions.java
 * MrBin99 © 2018
 */
package net.mrbin99.laravelechoandroid;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Echo options.
 */
public final class EchoOptions {

    /**
     * Host of the Echo server.<br>
     * <b>Default : </b>http://localhost:6001
     */
    public String host;

    /**
     * Host endpoint.<br>
     * <b>Default : </b>/broadcasting/auth
     */
    public String authEndpoint;

    /**
     * Event namespace.<br>
     * <b>Default : </b>App.Events
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
        headers = new HashMap<>();
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

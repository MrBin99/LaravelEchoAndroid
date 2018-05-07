/*
 * EventFormatter.java
 * MrBin99 © 2018
 */
package net.mrbin99.laravelechoandroid.util;

/**
 * Event name formatter.
 */
public final class EventFormatter {

    /**
     * Event namespace.
     */
    private String namespace;

    /**
     * Creates a new event formatter with an empty namespace.
     */
    public EventFormatter() {
    }

    /**
     * Creates a new event formatter.
     *
     * @param namespace namespace of events
     */
    public EventFormatter(String namespace) {
        this.namespace = namespace;
    }

    /**
     * Format the given event name.
     *
     * @param event event name
     * @return event name formatted
     */
    public String format(String event) {
        String result = event;

        if (result.charAt(0) == '.' || result.charAt(0) == '\\') {
            return result.substring(1);
        } else if (!namespace.isEmpty()) {
            result = namespace + "." + event;
        }

        return result.replace('.', '\\');
    }

    /**
     * Sets the namespace.
     *
     * @param namespace namespace of events
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}

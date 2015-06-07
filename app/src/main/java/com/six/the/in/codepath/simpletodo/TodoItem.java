package com.six.the.in.codepath.simpletodo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shoabe on 15-06-07.
 */
public class TodoItem {
    private int id;
    private String body;
    private int priority;

    public static final String PRIO_LOW = "LOW";
    public static final String PRIO_NORMAL = "NORMAL";
    public static final String PRIO_HIGH = "HIGH";
    public static final String PRIO_URGENT = "URGENT";

    private static final Map<Integer, String> priorityMap = new HashMap<Integer, String>() {{
        put(1, PRIO_LOW);
        put(2, PRIO_NORMAL);
        put(3, PRIO_HIGH);
        put(4, PRIO_URGENT);
    }};

    private static final int DEFAULT_PRIORITY = 1;

    public TodoItem(String body, int priority) {
        super();
        this.body = body;
        this.priority = priority;
    }

    public TodoItem(String body) {
        super();
        this.body = body;
        this.priority = DEFAULT_PRIORITY;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPriorityString() {
        return priorityMap.get(this.priority);
    }
}

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
    private boolean checked;

    public static final int PRIO_LOW_INT = 1;
    public static final int PRIO_NORMAL_INT = 2;
    public static final int PRIO_HIGH_INT = 3;
    public static final int PRIO_URGENT_INT = 4;

    public static final String PRIO_LOW_STRING = "LOW";
    public static final String PRIO_NORMAL_STRING = "NORMAL";
    public static final String PRIO_HIGH_STRING = "HIGH";
    public static final String PRIO_URGENT_STRING = "URGENT";

    private static final Map<Integer, String> priorityMap = new HashMap<Integer, String>() {{
        put(PRIO_LOW_INT, PRIO_LOW_STRING);
        put(PRIO_NORMAL_INT, PRIO_NORMAL_STRING);
        put(PRIO_HIGH_INT, PRIO_HIGH_STRING);
        put(PRIO_URGENT_INT, PRIO_URGENT_STRING);
    }};

    public TodoItem(String body, int priority) {
        super();
        this.body = body;
        this.priority = priority;
        this.checked = false;
    }

    public TodoItem(String body) {
        super();
        this.body = body;
        this.priority = PRIO_LOW_INT;
        this.checked = false;
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

    public String getPriorityString() {
        return priorityMap.get(this.priority);
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

    public int isChecked() {
        return checked ? 1 : 0;
    }

    public void setChecked(int checkedVal) {
        this.checked = checkedVal != 0;
    }

    public void toggleChecked() {
        this.checked = !checked;
    }
}

package com.six.the.in.codepath.simpletodo;

/**
 * Created by shoabe on 15-06-07.
 */
public class TodoItem {
    private int id;
    private String body;
    private int priority;

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
}

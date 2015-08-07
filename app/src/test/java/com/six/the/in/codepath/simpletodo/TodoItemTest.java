package com.six.the.in.codepath.simpletodo;

/**
 * Created by shoabe on 15-08-06.
 */
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class TodoItemTest {
    static final int PRIO_LOW_INT = 1;
    static final int PRIO_NORMAL_INT = 2;
    static final int PRIO_HIGH_INT = 3;
    static final int PRIO_URGENT_INT = 4;

    @Test
    public void todoItemInst() {
        String task = "Get groceries";
        TodoItem todoItem = new TodoItem(task, PRIO_HIGH_INT);
        assertEquals(task, todoItem.getBody());
        assertEquals(PRIO_HIGH_INT, todoItem.getPriority());
    }

    @Test
    public void todoItemInstDefaultPriority() {
        String task = "Get groceries";
        TodoItem todoItem = new TodoItem(task);
        assertEquals(task, todoItem.getBody());
        assertEquals(PRIO_LOW_INT, todoItem.getPriority());
    }

}
package com.six.the.in.codepath.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by shoabe on 15-06-07.
 */
public class TodoItemDatabase extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "todoListDatabase";

    // Todo table name
    private static final String TABLE_TODO = "todo_items";

    // Todo Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_BODY = "body";
    private static final String KEY_PRIORITY = "priority";
    private static final String KEY_CHECKED = "checked";

    public TodoItemDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
     * Creating our initial tables
     * These is where we need to write create table statements.
     * This is called when database is created.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Construct a table for todo items
        String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_TODO + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_BODY + " TEXT,"
                + KEY_PRIORITY + " INTEGER,"
                + KEY_CHECKED + " INTEGER" + ")";
        db.execSQL(CREATE_TODO_TABLE);
    }

    /*
     * Upgrading the database between versions
     *
     * This method is called when database is upgraded like modifying the table structure,
     * adding constraints to database, etc
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 1) {
            // Wipe older tables if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
            // Create tables again
            onCreate(db);
        }
    }

    /*
     * Returns a single item by id
     */
    public TodoItem getTodoItem(int id) {
        // Open database for reading
        SQLiteDatabase db = this.getReadableDatabase();
        // Construct and execute query
        Cursor cursor = db.query(TABLE_TODO,  // TABLE
                new String[]{KEY_ID, KEY_BODY, KEY_PRIORITY, KEY_CHECKED}, // SELECT
                KEY_ID + "= ?", new String[]{String.valueOf(id)},  // WHERE, ARGS
                null, null, "id ASC", "100"); // GROUP BY, HAVING, ORDER BY, LIMIT
        if (cursor != null)
            cursor.moveToFirst();
        // Load result into model object
        TodoItem item = new TodoItem(cursor.getString(1), cursor.getInt(2));
        item.setId(cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)));
        // Close the cursor
        if (cursor != null)
            cursor.close();
        // return todo item
        return item;
    }

    /*
     * Get all items in the database
     */
    public ArrayList<TodoItem> getAllTodoItems() {
        ArrayList<TodoItem> todoItems = new ArrayList<TodoItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TODO;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery + " ORDER BY " + KEY_PRIORITY + " DESC", null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TodoItem item = new TodoItem(cursor.getString(1), cursor.getInt(2));
                item.setId(cursor.getInt(0));
                item.setChecked(cursor.getInt(3));
                // Adding todo item to list
                todoItems.add(item);
            } while (cursor.moveToNext());
        }
        // Close the cursor
        if (cursor != null)
            cursor.close();

        // return todo list
        return todoItems;
    }

    /*
     * Insert record into the database
     */
    public void addTodoItem(TodoItem item) {
        // Open database connection
        SQLiteDatabase db = this.getWritableDatabase();
        // Define values for each field
        ContentValues values = new ContentValues();
        values.put(KEY_BODY, item.getBody());
        values.put(KEY_PRIORITY, item.getPriority());
        values.put(KEY_CHECKED, item.isChecked());
        // Insert Row and get generated id
        long rowId = db.insertOrThrow(TABLE_TODO, null, values);
        // Closing database connection
        db.close();
    }

    /*
     * Update a record in the database
     */
    public int updateTodoItem(TodoItem item) {
        // Open database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // Setup fields to update
        ContentValues values = new ContentValues();
        values.put(KEY_BODY, item.getBody());
        values.put(KEY_PRIORITY, item.getPriority());
        values.put(KEY_CHECKED, item.isChecked());
        // Updating row
        String itemId = String.valueOf(item.getId());
        int result = db.update(TABLE_TODO, values, KEY_ID + " = " + itemId,
                null);
        // Close the database
        db.close();
        return result;
    }

    /*
     * Delete a record from the database
     */
    public void deleteTodoItem(TodoItem item) {
        // Open database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete the record with the specified id
        db.delete(TABLE_TODO, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getId()) });
        // Close the database
        db.close();
    }
}

package com.six.the.in.codepath.simpletodo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements EditItemDialog.EditItemDialogListener {
    ArrayList<TodoItem> items;
    TodoListAdapter itemsAdapter;
    ListView lvItems;
    TodoItemDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create our sqlite database object
        db = new TodoItemDatabase(this);

        lvItems = (ListView) findViewById(R.id.lvItems);
//        items = new ArrayList<TodoItem>();
        refreshItems();
        itemsAdapter = new TodoListAdapter (
                this,
                items
        );
        lvItems.setAdapter(itemsAdapter);
        if (items.isEmpty()) {
            db.addTodoItem(new TodoItem("First item"));
            db.addTodoItem(new TodoItem("Second item"));
            refreshItems();
        }
        setupListViewListener();
        // Ensure soft keyboard is hidden
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        db.deleteTodoItem(items.get(pos));
                        refreshItems();
                        return true;
                    }
                }
        );

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter,
                                            View item, int pos, long id) {

                        showEditDialog(pos);
                    }
                }
        );
    }

    private void showEditDialog(int itemPosition) {
        FragmentManager fm = getSupportFragmentManager();
        EditItemDialog editNameDialog = EditItemDialog.newInstance(
                "Edit Item",
                items.get(itemPosition),
                itemPosition
        );
        editNameDialog.show(fm, "fragment_edit_name");
    }

    @Override
    public void onFinishEditDialog(int itemId, String inputText, int itemPriority, int itemPos) {
        TodoItem itemToUpdate = db.getTodoItem(itemId);
        itemToUpdate.setBody(inputText);
        itemToUpdate.setPriority(itemPriority);
        db.updateTodoItem(itemToUpdate);
        refreshItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        TodoItem itemToAdd = new TodoItem(itemText);
        db.addTodoItem(itemToAdd);
        refreshItems();
        etNewItem.setText("");
    }

    private void refreshItems() {
        // Querying all to-do items
        items = db.getAllTodoItems();
        // Resetting the adapter
        itemsAdapter = new TodoListAdapter (
                this,
                items
        );
        lvItems.setAdapter(itemsAdapter);
    }
}

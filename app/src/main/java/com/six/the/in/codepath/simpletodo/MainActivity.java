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

    private static final int DEFAULT_PRIORITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<TodoItem>();
//        readItems();
        itemsAdapter = new TodoListAdapter (
                this,
                items
        );
        lvItems.setAdapter(itemsAdapter);
        if (items.isEmpty()) {
            items.add(new TodoItem("First item", DEFAULT_PRIORITY));
            items.add(new TodoItem("Second item", DEFAULT_PRIORITY));
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
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
//                        writeItems();
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
        EditItemDialog editNameDialog = EditItemDialog.newInstance("Edit Item", items.get(itemPosition), itemPosition);
        editNameDialog.show(fm, "fragment_edit_name");
    }

    @Override
    public void onFinishEditDialog(String inputText, int itemPriority, int itemPosition) {
        items.set(itemPosition, new TodoItem(inputText, itemPriority));
        itemsAdapter.notifyDataSetChanged();
//        writeItems();
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
        itemsAdapter.add(new TodoItem(itemText, DEFAULT_PRIORITY));
        etNewItem.setText("");
//        writeItems();
    }

//    private void readItems() {
//        File filesDir = getFilesDir();
//        File todoFile = new File(filesDir, "todo.txt");
//        try {
//            items = new <String>(FileUtils.readLines(todoFile));
//        } catch (IOException ioe) {
//            items = new ArrayList<String>();
//        }
//    }
//
//    private void writeItems() {
//        File filesDir = getFilesDir();
//        File todoFile = new File(filesDir, "todo.txt");
//        try {
//            FileUtils.writeLines(todoFile, items);
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
//    }
}

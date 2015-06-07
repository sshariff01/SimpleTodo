package com.six.the.in.codepath.simpletodo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shoabe on 15-06-07.
 */
public class TodoListAdapter extends ArrayAdapter<TodoItem> {
    public TodoListAdapter(Context context, ArrayList<TodoItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TodoItem item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        // Lookup view for data population
        TextView txtListItem = (TextView) convertView.findViewById(R.id.txtListItem);
        TextView txtItemPriority = (TextView) convertView.findViewById(R.id.txtItemPrio);

        // Populate the data into the template view using the data object
        txtListItem.setText(item.getBody());
        txtItemPriority.setText(item.getPriorityString());
        switch (item.getPriorityString()) {
            case TodoItem.PRIO_LOW:
                txtItemPriority.setTextColor(Color.BLUE);
                break;
            case TodoItem.PRIO_NORMAL:
                txtItemPriority.setTextColor(Color.GREEN);
                break;
            case TodoItem.PRIO_HIGH:
                txtItemPriority.setTextColor(Color.MAGENTA);
                break;
            case TodoItem.PRIO_URGENT:
                txtItemPriority.setTextColor(Color.RED);
                break;
        }

        // Return the completed view to render on screen
        return convertView;
    }
}


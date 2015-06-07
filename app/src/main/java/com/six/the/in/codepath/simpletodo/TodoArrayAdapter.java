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
public class TodoArrayAdapter extends ArrayAdapter<TodoItem> {
    public TodoArrayAdapter(Context context, ArrayList<TodoItem> items) {
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
        switch (item.getPriority()) {
            case TodoItem.PRIO_LOW_INT:
                txtItemPriority.setTextColor(Color.BLUE);
                break;
            case TodoItem.PRIO_NORMAL_INT:
                txtItemPriority.setTextColor(Color.GREEN);
                break;
            case TodoItem.PRIO_HIGH_INT:
                txtItemPriority.setTextColor(Color.MAGENTA);
                break;
            case TodoItem.PRIO_URGENT_INT:
                txtItemPriority.setTextColor(Color.RED);
                break;
        }

        // Return the completed view to render on screen
        return convertView;
    }
}


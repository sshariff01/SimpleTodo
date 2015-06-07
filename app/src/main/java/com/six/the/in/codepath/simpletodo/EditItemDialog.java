package com.six.the.in.codepath.simpletodo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by shoabe on 15-06-04.
 */
public class EditItemDialog extends DialogFragment implements TextView.OnEditorActionListener, View.OnClickListener {

    private EditText mEditText;
    private Button saveBtn;
    private RadioButton prio_LowBtn, prio_MedBtn, prio_HighBtn, prio_UrgentBtn;
    private int itemPriority;

    public interface EditItemDialogListener {
        void onFinishEditDialog(int itemId, String inputText, int itemPriority);
    }

    public EditItemDialog() {
        // Empty constructor required for DialogFragment
    }

    public static EditItemDialog newInstance(String title, TodoItem item) {
        EditItemDialog frag = new EditItemDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
//        todoItem = item;
        args.putInt("itemId", item.getId());
        args.putString("itemName", item.getBody());
        args.putInt("itemPriority", item.getPriority());
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_fragment, container);
        saveBtn = (Button) view.findViewById(R.id.etSaveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });
        mEditText = (EditText) view.findViewById(R.id.etItemEtText);
        mEditText.setText(getArguments().getString("itemName", ""));
        mEditText.setSelection(mEditText.getText().length());

        getDialog().setTitle(getArguments().getString("title", "Title"));
        // Show soft keyboard automatically
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        mEditText.setOnEditorActionListener(this);
        itemPriority = getArguments().getInt("itemPriority");
        prio_LowBtn = (RadioButton) view.findViewById(R.id.priority_low);
        prio_LowBtn.setOnClickListener(this);
        prio_MedBtn = (RadioButton) view.findViewById(R.id.priority_med);
        prio_MedBtn.setOnClickListener(this);
        prio_HighBtn = (RadioButton) view.findViewById(R.id.priority_high);
        prio_HighBtn.setOnClickListener(this);
        prio_UrgentBtn = (RadioButton) view.findViewById(R.id.priority_urgent);
        prio_UrgentBtn.setOnClickListener(this);

        setPriorityChecked();

        return view;
    }

    private void setPriorityChecked() {
        switch(itemPriority) {
            case TodoItem.PRIO_LOW_INT:
                prio_LowBtn.setChecked(true);
                break;
            case TodoItem.PRIO_NORMAL_INT:
                prio_MedBtn.setChecked(true);
                break;
            case TodoItem.PRIO_HIGH_INT:
                prio_HighBtn.setChecked(true);
                break;
            case TodoItem.PRIO_URGENT_INT:
                prio_UrgentBtn.setChecked(true);
                break;
        }
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.priority_low:
                itemPriority = TodoItem.PRIO_LOW_INT;
                prio_LowBtn.setChecked(true);
                break;
            case R.id.priority_med:
                itemPriority = TodoItem.PRIO_NORMAL_INT;
                prio_MedBtn.setChecked(true);
                break;
            case R.id.priority_high:
                itemPriority = TodoItem.PRIO_HIGH_INT;
                prio_HighBtn.setChecked(true);
                break;
            case R.id.priority_urgent:
                itemPriority = TodoItem.PRIO_URGENT_INT;
                prio_UrgentBtn.setChecked(true);
                break;
        }
    }

    // Fires whenever the textfield has an action performed
    // In this case, when the "Done" button is pressed
    // REQUIRES a 'soft keyboard' (virtual keyboard)
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            closeDialog();
            return true;
        }
        return false;
    }

    private void closeDialog() {
        EditItemDialogListener listener = (EditItemDialogListener) getActivity();
        listener.onFinishEditDialog(
                getArguments().getInt("itemId"),
                mEditText.getText().toString(),
                itemPriority
        );
        // Hide soft keyboard
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        dismiss();
    }
}
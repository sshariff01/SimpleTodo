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
import android.widget.TextView;

/**
 * Created by shoabe on 15-06-04.
 */
public class EditItemDialog extends DialogFragment implements TextView.OnEditorActionListener {

    private EditText mEditText;
    private Button saveBtn;

    public interface EditItemDialogListener {
        void onFinishEditDialog(String inputText, int itemPriority, int itemPosition);
    }

    public EditItemDialog() {
        // Empty constructor required for DialogFragment
    }

    public static EditItemDialog newInstance(String title, TodoItem item, int itemPosition) {
        EditItemDialog frag = new EditItemDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("itemName", item.getBody());
        args.putInt("itemPriority", item.getPriority());
        args.putInt("itemPosition", itemPosition);
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
        return view;
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
                mEditText.getText().toString(),
                getArguments().getInt("itemPriority"),
                getArguments().getInt("itemPosition")
        );
        // Hide soft keyboard
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        dismiss();
    }
}
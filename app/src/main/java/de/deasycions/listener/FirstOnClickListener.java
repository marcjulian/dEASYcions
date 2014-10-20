package de.deasycions.listener;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import de.deasycions.utilities.ListenerUtility;

/**
 * {@link FirstOnClickListener} reacts on the first click on {@link android.widget.EditText}.
 * It enables the focus and start the keyboard.
 *
 * @author Marc Stammerjohann
 */
public class FirstOnClickListener implements View.OnClickListener {

    private InputMethodManager imm;
    private EditText[] editText;

    public FirstOnClickListener(Activity currentActivity, EditText[] editText) {
        this.editText = editText;
        imm = (InputMethodManager) currentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
    }


    @Override
    public void onClick(View view) {
        int position = ListenerUtility.getEditTextPosition(view, editText);
        ListenerUtility.editTextPosition = position;
        EditText current = editText[position];
        //enable focus
        current.setFocusableInTouchMode(true);
        //resize text
        current.setTextSize(20);
        current.requestFocus();
        //shows keyboard
        imm.showSoftInput(current, InputMethodManager.SHOW_IMPLICIT);
    }
}

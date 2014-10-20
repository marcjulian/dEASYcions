package de.deasycions.listener;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import de.deasycions.CategoryPage;
import de.deasycions.data.Category;
import de.deasycions.utilities.ListenerUtility;

/**
 * {@link LongHoldClickListener} reacts on long click on {@link android.widget.EditText}.
 * It enables editing the text of {@link android.widget.EditText}.
 *
 * @author Gary Grossgarten
 */
public class LongHoldClickListener implements View.OnLongClickListener {

    private final TextView.OnEditorActionListener onEditorActionListener;
    private EditText[] editText;
    private InputMethodManager imm;

    public LongHoldClickListener(Activity currentActivity, EditText[] editText, TextView.OnEditorActionListener onEditorActionListener) {
        this.editText = editText;
        this.onEditorActionListener = onEditorActionListener;
        imm = (InputMethodManager) currentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
    }


    @Override
    public boolean onLongClick(View view) {
        view.performHapticFeedback(1);
        int position = ListenerUtility.getEditTextPosition(view, editText);
        ListenerUtility.editTextPosition = position;
        EditText current = editText[position];
        current.setFocusableInTouchMode(true);
        current.requestFocus();
        imm.showSoftInput(current, InputMethodManager.SHOW_IMPLICIT);
        if (onEditorActionListener instanceof CategoryDoneEditorListener) {
            CategoryDoneEditorListener categoryDoneEditorListener = (CategoryDoneEditorListener) onEditorActionListener;
            categoryDoneEditorListener.setCurrentName(current.getText().toString());
            current.setOnEditorActionListener(categoryDoneEditorListener);
        } else if (onEditorActionListener instanceof EntryDoneEditorListener) {
            EntryDoneEditorListener entryDoneEditorListener = (EntryDoneEditorListener) onEditorActionListener;
            entryDoneEditorListener.setCurrentName(current.getText().toString());
            current.setOnEditorActionListener(entryDoneEditorListener);
        }
        return false;
    }
}

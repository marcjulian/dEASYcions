package de.deasycions.listener;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import de.deasycions.EditablePage;
import de.deasycions.utilities.ListenerUtility;

/**
 * {@link LongHoldClickListener} reacts on long click on {@link android.widget.EditText}.
 * It enables editing the text of {@link android.widget.EditText}.
 *
 * @author Gary Grossgarten
 */
public class LongHoldClickListener implements View.OnLongClickListener {

    private EditablePage contentPage;
    private final TextView.OnEditorActionListener onEditorActionListener;
    private EditText[] editText;

    public LongHoldClickListener(EditablePage contentPage, EditText[] editText, TextView.OnEditorActionListener onEditorActionListener) {
        this.contentPage = contentPage;
        this.editText = editText;
        this.onEditorActionListener = onEditorActionListener;
    }


    @Override
    public boolean onLongClick(View view) {
        if(!ListenerUtility.isViewMoving(view)) {
            view.performHapticFeedback(1);
            int position = ListenerUtility.getEditTextPosition(view, editText);
            ListenerUtility.editTextPosition = position;
            EditText currentEditText = editText[position];
            currentEditText.setFocusableInTouchMode(true);
            currentEditText.requestFocus();
            contentPage.showKeyboard(currentEditText);
            //TODO set cursor behind the text
            ContentDoneEditorListener contentDoneEditorListener = (ContentDoneEditorListener) onEditorActionListener;
            contentDoneEditorListener.setCurrentName(currentEditText.getText().toString());
            currentEditText.setOnEditorActionListener(contentDoneEditorListener);
       }
        return false;
    }



}

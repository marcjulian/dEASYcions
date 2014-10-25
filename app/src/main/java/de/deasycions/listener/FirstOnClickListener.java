package de.deasycions.listener;

import android.view.View;
import android.widget.EditText;

import de.deasycions.interfaces.IdEASYcionsContent;
import de.deasycions.utilities.ListenerUtility;

/**
 * {@link FirstOnClickListener} reacts on the first click on {@link android.widget.EditText}.
 * It enables the focus and start the keyboard.
 *
 * @author Marc Stammerjohann
 */
public class FirstOnClickListener implements View.OnClickListener {

    private IdEASYcionsContent contentPage;
    private EditText[] editText;

    public FirstOnClickListener(IdEASYcionsContent contentPage, EditText[] editText) {
        this.contentPage = contentPage;
        this.editText = editText;
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
        contentPage.showKeyboard(current);
    }
}

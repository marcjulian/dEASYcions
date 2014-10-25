package de.deasycions.listener;

import android.view.View;
import android.widget.EditText;

import de.deasycions.interfaces.IdEASYcionsContent;
import de.deasycions.utilities.ListenerUtility;

/**
 * {@link FirstOnClickListener} reacts on the second click on {@link android.widget.EditText}.
 * It starts a new Intent to call the {@link de.deasycions.CategoryPage}-Activity.
 *
 * @author Marc Stammerjohann
 */
public class SecondOnClickListener implements View.OnClickListener {

    private IdEASYcionsContent contentPage;
    private EditText[] editText;

    public SecondOnClickListener(IdEASYcionsContent contentPage, EditText[] editText) {
        this.contentPage = contentPage;
        this.editText = editText;
    }

    @Override
    public void onClick(View view) {
        int position = ListenerUtility.getEditTextPosition(view, editText);
        ListenerUtility.editTextPosition = position;
        contentPage.startNextActivity(editText[position].getText().toString());
    }
}

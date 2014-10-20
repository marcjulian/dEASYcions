package de.deasycions.listener;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import de.deasycions.StartPage;
import de.deasycions.utilities.ListenerUtility;

/**
 * {@link FirstOnClickListener} reacts on the second click on {@link android.widget.EditText}.
 * It starts a new Intent to call the {@link de.deasycions.CategoryPage}-Activity.
 *
 * @author Marc Stammerjohann
 */
public class SecondOnClickListener implements View.OnClickListener {

    private Activity currentActivity;
    private EditText[] editText;

    public SecondOnClickListener(Activity currentActivity, EditText[] editText) {
        this.currentActivity = currentActivity;
        this.editText = editText;
    }

    @Override
    public void onClick(View view) {
        int position = ListenerUtility.getEditTextPosition(view, editText);
        ListenerUtility.editTextPosition = position;
        if (currentActivity instanceof StartPage) {
            ((StartPage) currentActivity).startCategoryPageActivity(editText[position].getText().toString());
        }
    }
}

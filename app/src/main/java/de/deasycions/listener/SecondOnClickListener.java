package de.deasycions.listener;

import android.view.View;
import android.widget.EditText;

import de.deasycions.EditablePage;
import de.deasycions.Page;
import de.deasycions.utilities.ListenerUtility;

/**
 * {@link FirstOnClickListener} reacts on the second click on {@link android.widget.EditText}.
 * It starts a new Intent to call the {@link de.deasycions.CategoryPage}-Activity.
 *
 * @author Marc Stammerjohann
 */
public class SecondOnClickListener implements View.OnClickListener {

    private EditablePage contentPage;
    private EditText[] editText;

    public SecondOnClickListener(EditablePage contentPage, EditText[] editText) {
        this.contentPage = contentPage;
        this.editText = editText;
    }

    @Override
    public void onClick(View view) {
        if(!ListenerUtility.isViewMoving(view)) {
            int position = ListenerUtility.getEditTextPosition(view, editText);
            ListenerUtility.editTextPosition = position;
            contentPage.startNextActivity(editText[position].getText().toString(), Page.CATEGORY_PAGE);
        }
    }
}

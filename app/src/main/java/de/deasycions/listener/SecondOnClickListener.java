package de.deasycions.listener;

import android.view.View;
import android.widget.EditText;

import de.deasycions.EditablePage;
import de.deasycions.Page;
import de.deasycions.customText.EasyText;
import de.deasycions.customText.MovingText;
import de.deasycions.utilities.ListenerUtility;

/**
 * {@link FirstOnClickListener} reacts on the second click on {@link android.widget.EditText}.
 * It starts a new Intent to call the {@link de.deasycions.CategoryPage}-Activity.
 *
 * @author Marc Stammerjohann
 */
public class SecondOnClickListener implements View.OnClickListener {

    private EditablePage contentPage;

    public SecondOnClickListener(EditablePage contentPage) {
        this.contentPage = contentPage;
    }

    @Override
    public void onClick(View view) {
        if (view instanceof MovingText) {
            MovingText currentMovingText = (MovingText) view;
            if (!ListenerUtility.isViewMoving(currentMovingText)) {
                contentPage.startNextActivity(currentMovingText, Page.CATEGORY_PAGE);
            }
        }
    }
}

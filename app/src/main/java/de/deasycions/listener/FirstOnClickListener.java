package de.deasycions.listener;

import android.view.View;

import de.deasycions.EditablePage;
import de.deasycions.customText.EasyText;

/**
 * {@link FirstOnClickListener} reacts on the first click on {@link android.widget.EditText}.
 * It enables the focus and start the keyboard.
 *
 * @author Marc Stammerjohann
 */
public class FirstOnClickListener implements View.OnClickListener {

    private EditablePage contentPage;

    public FirstOnClickListener(EditablePage contentPage) {
        this.contentPage = contentPage;
    }


    @Override
    public void onClick(View view) {
        if (view instanceof EasyText) {
            EasyText currentEasyText = (EasyText) view;
            //enable focus
            currentEasyText.setFocusableInTouchMode(true);
            //resize text
            currentEasyText.setTextSize(20);
            currentEasyText.requestFocus();
            //shows keyboard
            contentPage.showKeyboard(currentEasyText);
        }
    }
}

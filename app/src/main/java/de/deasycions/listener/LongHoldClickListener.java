package de.deasycions.listener;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import de.deasycions.EditablePage;
import de.deasycions.customText.EasyText;
import de.deasycions.utilities.ListenerUtility;

/**
 * {@link LongHoldClickListener} reacts on long click on {@link android.widget.EditText}.
 * It enables editing the text of {@link android.widget.EditText}.
 *
 * @author Gary Grossgarten
 */
public class LongHoldClickListener implements View.OnLongClickListener {

    private EditablePage contentPage;

    public LongHoldClickListener(EditablePage contentPage) {
        this.contentPage = contentPage;
    }

    @Override
    public boolean onLongClick(View view) {
        boolean handled = false;
        if (view instanceof EasyText) {
                EasyText currentEasyText = (EasyText) view;
            if (!ListenerUtility.isViewMoving(currentEasyText)) {
                view.performHapticFeedback(1);
                currentEasyText.setFocusableInTouchMode(true);
                currentEasyText.requestFocus();
                currentEasyText.setSelection(currentEasyText.getText().length());
                contentPage.showKeyboard(currentEasyText);
                handled = true;
            }
        }
        return handled;
    }


}

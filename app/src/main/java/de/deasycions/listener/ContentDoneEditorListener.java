package de.deasycions.listener;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import de.deasycions.EditablePage;
import de.deasycions.customText.EasyText;
import de.deasycions.utilities.ActivityUtility;

/**
 * {@link ContentDoneEditorListener} reacts on the DONE click on a keyboard.
 * It creates a new content, depending on the input if it is allowed.
 *
 * @author Marc Stammerjohann
 */
public class ContentDoneEditorListener implements TextView.OnEditorActionListener {


    private final EditablePage contentPage;

    public ContentDoneEditorListener(EditablePage contentPage) {
        this.contentPage = contentPage;
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {

        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            if(textView instanceof EasyText) {
                EasyText currentEasyText = (EasyText) textView;
                ActivityUtility.saveAtDoneClick(contentPage, currentEasyText);
                handled = true;
            }
        }
        return handled;
    }

}

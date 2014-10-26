package de.deasycions.listener;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import de.deasycions.EditablePage;
import de.deasycions.data.CategoryStorage;

/**
 * {@link ContentDoneEditorListener} reacts on the DONE click on a keyboard.
 * It creates a new content, depending on the input if it is allowed.
 *
 * @author Marc Stammerjohann
 */
public class ContentDoneEditorListener implements TextView.OnEditorActionListener {


    private final EditablePage contentPage;

    private String infoMessage;
    private String currentName;
    private CategoryStorage categoryStorage;

    public ContentDoneEditorListener(EditablePage contentPage) {
        this.contentPage = contentPage;
        this.categoryStorage = CategoryStorage.getInstance();
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            String newContentName = textView.getText().toString();
            // hiding the keyboard
            contentPage.hideKeyboard(textView.getWindowToken());

            if (!verifyNewCategoryName(newContentName)) {
                contentPage.displayInfoMessage(infoMessage, textView, currentName);
            } else {
                if (currentName == null) {
                    contentPage.createContent(newContentName);
                } else {
                    contentPage.setNewContentName(currentName, newContentName);
                }
            }
            //disabled editing of the text
            textView.setFocusableInTouchMode(false);
            textView.clearFocus();
            handled = true;
        }
        return handled;
    }

    /**
     * Verifies if the text is allowed for a new {@link de.deasycions.data.Category}.
     * Also specifies the info message.
     *
     * @param input to verify for new category
     * @return boolean whether the text is allowed or not
     */
    private boolean verifyNewCategoryName(String input) {
        if (input.equals("")) {
            infoMessage = String.format("The %s name must not be empty!", contentPage.getDescription());
            return false;
        }
        if (currentName != null) {
            if (currentName.toLowerCase().equals(input.toLowerCase())) {
                infoMessage = "";
                return false;
            }
        }
        if (contentPage.containsContent(input)) {
            infoMessage = String.format("The %s %s already exists!" , contentPage.getDescription(), input);
            return false;
        }
        return true;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }
}

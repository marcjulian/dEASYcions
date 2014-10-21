package de.deasycions.listener;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import de.deasycions.StartPage;
import de.deasycions.data.CategoryStorage;
import de.deasycions.utilities.ListenerUtility;

/**
 * {@link CategoryDoneEditorListener} reacts on the DONE click on a keyboard.
 * It creates a new {@link de.deasycions.data.Category}, depending if the input was allowed.
 *
 * @author Marc Stammerjohann
 */
public class CategoryDoneEditorListener implements TextView.OnEditorActionListener {

    private InputMethodManager imm;

    /**
     * currentName is used for changing the category name, if it is null category is created
     */
    private String currentName;
    private Activity currentActivity;
    private String infoMessage;
    private CategoryStorage categoryStorage;

    private TextView message;

    public CategoryDoneEditorListener() {
        //do nothing
    }

    public CategoryDoneEditorListener(Activity currentActivity, TextView message) {
        this.currentActivity = currentActivity;
        this.message = message;
        imm = (InputMethodManager) currentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        this.categoryStorage = CategoryStorage.getInstance();
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            String newCategoryName = textView.getText().toString();
            // hiding the keyboard
            imm.hideSoftInputFromWindow(textView.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);

            if (!verifyNewCategoryName(newCategoryName)) {
                ListenerUtility.setInfoTextMessage(message, textView, infoMessage, currentName);
            } else {
                if (currentName == null) {
                    ((StartPage) currentActivity).createCategory(newCategoryName);
                } else {
                    categoryStorage.setNewCategoryName(currentName, newCategoryName);
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
            infoMessage = "The category name must not be empty!";
            return false;
        }
        if(currentName != null){
            if(currentName.toLowerCase().equals(input.toLowerCase())) {
                infoMessage = "";
                return false;
            }
        }
        if (categoryStorage.containsCategory(input)) {
            infoMessage = "The category " + input + " already exists";
            return false;
        }
        return true;
    }
}




package de.deasycions.listener;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import de.deasycions.CategoryPage;
import de.deasycions.data.Category;
import de.deasycions.utilities.ListenerUtility;

/**
 * @author Marc Stammerjohann
 */
public class EntryDoneEditorListener implements TextView.OnEditorActionListener {

    private TextView message;
    private Activity currentActivity;
    private String infoMessage;

    private InputMethodManager imm;
    private Category currentCategory;

    private String currentName;

    public EntryDoneEditorListener(Activity currentActivity, TextView message, Category currentCategory) {
        this.currentActivity = currentActivity;
        this.message = message;
        this.currentCategory = currentCategory;
        imm = (InputMethodManager) currentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public void setCurrentName(String currentName){
        this.currentName = currentName;
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            String newEntryName = textView.getText().toString();
            // hiding the keyboard
            imm.hideSoftInputFromWindow(textView.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
            if(!verifyNewEntryName(newEntryName)){
                ListenerUtility.setInfoTextMessage(message, textView, infoMessage, currentName);
            }else{
                if(currentName == null){
                    ((CategoryPage)currentActivity).addEntry(newEntryName);
                }else{
                    currentCategory.changeEntryName(currentName, newEntryName);
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
    private boolean verifyNewEntryName(String input) {
        if (input.equals("")) {
            infoMessage = "The entry name must not be empty!";
            return false;
        }
        if(currentName != null){
            if(currentName.toLowerCase().equals(input.toLowerCase())) {
                infoMessage = "";
                return false;
            }
        }
        if (currentCategory.containsEntry(input)) {
            infoMessage = "The entry " + input + " already exists";
            return false;
        }
        return true;
    }
}

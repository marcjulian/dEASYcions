package de.deasycions;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import de.deasycions.customText.EasyText;
import de.deasycions.data.CategoryStorage;
import de.deasycions.interfaces.IStartActivity;
import de.deasycions.listener.ContentDoneEditorListener;
import de.deasycions.listener.FirstOnClickListener;
import de.deasycions.listener.LongHoldClickListener;
import de.deasycions.utilities.ActivityUtility;
import de.deasycions.utilities.ListenerUtility;

/**
 * Initializes and defines same methods for editable pages (e.g startpage and categorypage).
 *
 * @author Marc Stammerjohann
 */
public abstract class EditablePage extends Activity {

    //Data
    protected CategoryStorage categoryStorage;
    //View
    private TextView message;
    protected EasyText[] easyTexts;
    protected ImageView trashView;
    //Listener
    protected View.OnLongClickListener longHoldClickListener;
    protected View.OnClickListener firstOnClickListener;
    //Keyboard
    private InputMethodManager inputMethodManager;
    // Variables
    private String description;
    protected float height;
    protected float width;
    protected float density;

    /**
     * Reacts when the background is touched. It reacts like on DoneClick, it tries to save the newcategory text.
     *
     * @param event
     * @return
     */
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View currentFocus = getWindow().getCurrentFocus();
                if (currentFocus instanceof EasyText) {
                    EasyText currentEasyText = (EasyText) currentFocus;
                    saveAtDoneClick(currentEasyText);
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    protected void initialize() {
        //Category-Section
        categoryStorage = CategoryStorage.getInstance();

        //Keyboard-Section
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        //Widget-Section
        easyTexts = ActivityUtility.createEasyText(this);
        message = (TextView) findViewById(R.id.ContainsMessage);
        trashView = (ImageView) findViewById(R.id.trash);

        //Display-Section
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        density = getResources().getDisplayMetrics().density;
        height = outMetrics.heightPixels;
        width = outMetrics.widthPixels;

        //Listener-Section
        firstOnClickListener = new FirstOnClickListener(this);
        ContentDoneEditorListener contentDoneEditorListener = new ContentDoneEditorListener(this);
        longHoldClickListener = new LongHoldClickListener(this);
        ActivityUtility.addListenerToEditText(easyTexts, firstOnClickListener, contentDoneEditorListener);
    }

    public abstract boolean containsContent(String content);

    public abstract void setNewContentName(String currentName, String newContentName);

    public abstract void deleteContent(View currentView);

    public abstract void createContent(EasyText currentEasyText);

    public abstract void displayContent();


    public void showKeyboard(EditText currentEditText) {
        inputMethodManager.showSoftInput(currentEditText, InputMethodManager.SHOW_IMPLICIT);
    }

    public void hideKeyboard(IBinder windowToken) {
        inputMethodManager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void displayInfoMessage(String infoMessage) {
        ListenerUtility.setInfoTextMessage(message, infoMessage);
    }

    public void startNextActivity(EasyText currentEasyText, Page page) {
        //do nothing, sub-class must override
    }

    public void resetEditText(EasyText currentEasyText) {
        currentEasyText.setText("");
        currentEasyText.setTextSize(50);
        currentEasyText.setOnClickListener(firstOnClickListener);
        currentEasyText.clearFocus();
        currentEasyText.setFocusableInTouchMode(false);
        //TouchListener and LongClick listener are not used, when the edittext is empty!
        currentEasyText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Do nothing
                return false;
            }
        });
        currentEasyText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Do nothing
                return false;
            }
        });
        currentEasyText.resetCurrentName();
    }

    public void refreshDisplay(EasyText currentEasyText, int size) {
        EasyText before = currentEasyText.getBefore();
        EasyText behind = currentEasyText.getBehind();

        if (size == 5) {
            resetEditText(currentEasyText);
        } else if (before.getText().toString().equals("")) {
            resetEditText(currentEasyText);
            currentEasyText.setVisibility(View.INVISIBLE);
        } else if (!before.getText().toString().equals("")) {
            while (!behind.getText().toString().equals("")) {
                currentEasyText.setTextSize(20);
                currentEasyText.setText(behind.getText().toString());
                currentEasyText = behind;
                behind = currentEasyText.getBehind();
            }
            resetEditText(currentEasyText);
            behind.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * It whether saves the new entered name or displays an info message.
     * It is used in the {@link de.deasycions.listener.ContentDoneEditorListener} and in the onTouch-Method in StartPage and CategoryPage.
     * It also is used when the keyboard is closed via toolbar
     *
     * @param currentEasyText
     */
    //TODO call this when the keyboard is closed via toolbar
    public void saveAtDoneClick(EasyText currentEasyText) {
        // hiding the keyboard
        hideKeyboard(currentEasyText.getWindowToken());
        String currentName = currentEasyText.getCurrentName();
        String newContentName = currentEasyText.getNewName();
        if (!verifyNewCategoryName(currentEasyText)) {
            if (currentName == null) {
                currentEasyText.setText("");
                currentEasyText.setTextSize(50);
            } else {
                currentEasyText.setText(currentName);
            }
        } else {
            currentEasyText.setCurrentName(newContentName);
            if (currentName == null) {
                createContent(currentEasyText);
            } else {
                setNewContentName(currentName, newContentName);
            }
        }
        //disabled editing of the text
        currentEasyText.setFocusableInTouchMode(false);
        currentEasyText.clearFocus();
    }

    /**
     * Verifies if the text is allowed for a new {@link de.deasycions.data.Category}.
     * Also specifies the info message.
     *
     * @return boolean whether the text is allowed or not
     */
    public boolean verifyNewCategoryName(EasyText currentEasyText) {
        String currentName = currentEasyText.getCurrentName();
        String newContentName = currentEasyText.getNewName();
        String infoMessage = "";
        boolean verified = true;
        if (newContentName.equals("")) {
            infoMessage = String.format("Please enter a %s name!", getDescription());
            verified = false;
        } else if (currentName != null) {
            if (currentName.toLowerCase().equals(newContentName.toLowerCase())) {
                verified = false;
            }
        } else if (containsContent(newContentName)) {
            infoMessage = String.format("The %s %s already exists!", getDescription(), newContentName);
            verified = false;
        }
        displayInfoMessage(infoMessage);
        return verified;
    }
}

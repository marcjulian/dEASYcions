package de.deasycions;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
public abstract class EditablePage extends Activity implements IStartActivity {

    //Data
    protected CategoryStorage categoryStorage;
    //View
    private TextView message;
    protected EditText[] editText;
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

    protected void initialize() {
        //Category-Section
       categoryStorage = CategoryStorage.getInstance();

        //Keyboard-Section
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        //Widget-Section
        editText = ActivityUtility.createEditText(this);
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
        firstOnClickListener = new FirstOnClickListener(this, editText);
        ContentDoneEditorListener contentDoneEditorListener = new ContentDoneEditorListener(this);
        longHoldClickListener = new LongHoldClickListener(this, editText, contentDoneEditorListener);
        ActivityUtility.addListenerToEditText(editText, firstOnClickListener, contentDoneEditorListener);
    }

    public abstract boolean containsContent(String content);

    public abstract void setNewContentName(String currentName, String newContentName);

    public abstract void deleteContent(View currentView);

    public abstract void createContent(String newContentName);

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

    public void startNextActivity(String contentName, Page page) {
        //do nothing, sub-class must override
    }

    public void resetEditText(EditText currentEditText){
        currentEditText.setText("");
        currentEditText.setTextSize(50);
        currentEditText.setOnClickListener(firstOnClickListener);
        currentEditText.setFocusableInTouchMode(false);
        currentEditText.clearFocus();
    }

    public void refreshDisplay(EditText currentEditText, int size) {
        int position = ListenerUtility.getEditTextPosition(currentEditText, editText);
        int positionBefore = (position - 1 + editText.length) % editText.length;
        int positionBehind = (position + 1) % editText.length;
        EditText before = editText[positionBefore];
        EditText behind = editText[positionBehind];

        if(size == 5){
            resetEditText(currentEditText);
        }else if(before.getText().toString().equals("")){
            resetEditText(currentEditText);
            currentEditText.setVisibility(View.INVISIBLE);
        }else if(!before.getText().toString().equals("")){
            while(!behind.getText().toString().equals("")){
                currentEditText.setTextSize(20);
                currentEditText.setText(behind.getText().toString());
                currentEditText = behind;
                position++;
                behind = editText[(position + 1) % editText.length];
            }
            resetEditText(currentEditText);
            behind.setVisibility(View.INVISIBLE);
        }
    }

}

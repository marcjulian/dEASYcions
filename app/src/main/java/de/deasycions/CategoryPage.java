package de.deasycions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import de.deasycions.data.Category;
import de.deasycions.data.CategoryStorage;
import de.deasycions.data.SharedData;
import de.deasycions.interfaces.IdEASYcionsContent;
import de.deasycions.listener.ContentDoneEditorListener;
import de.deasycions.listener.EditTextOnTouchListener;
import de.deasycions.listener.EmptyOnClickListener;
import de.deasycions.listener.FirstOnClickListener;
import de.deasycions.listener.LongHoldClickListener;
import de.deasycions.listener.OnClickCategoryListener;
import de.deasycions.utilities.ActivityUtility;
import de.deasycions.utilities.ListenerUtility;

/**
 * Category age of dEASYcions app.
 * Category page is displaying and managing the entries of a category (add, edit, deleting).
 *
 * @author Marc Stammerjohann
 */
public class CategoryPage extends Activity implements IdEASYcionsContent {

    private CategoryStorage categoryStorage;
    private Category newCategory;
    private EditText[] editText;
    private TextView message;
    private Button categoryButton;
    private ImageView trashView;
    private View.OnClickListener emptyOnClickListener;
    private View.OnLongClickListener longHoldClickListener;
    private View.OnTouchListener editTextOnTouchListener;
    //swaping color of the button to the selected category
    private int currentCategoryPosition;
    private InputMethodManager inputMethodManager;
    private String description;
    private float height;
    private float width;
    private float density;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);

        initialize();
        displayContent();
        ActivityUtility.swapBackgroundColor(categoryButton, editText, currentCategoryPosition);

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedData sharedData = new SharedData(this);
        sharedData.clearSharedPreferences();
        sharedData.saveData();
    }


    private void initialize() {
        //String-Section
        description = getString(R.string.entry);
        //Intent-Section
        Intent intent = getIntent();
        String categoryName = intent.getStringExtra(ActivityUtility.CATEGORY_NAME);
        currentCategoryPosition = intent.getIntExtra(ActivityUtility.CATEGORY_POSITION, -1);
        //Category-Section
        categoryStorage = CategoryStorage.getInstance();
        newCategory = categoryStorage.getCategory(categoryName);
        //Widget-Section
        message = (TextView) findViewById(R.id.ContainsMessage);
        categoryButton = (Button) findViewById(R.id.categoryName);
        categoryButton.setText(newCategory.getName());
        editText = ActivityUtility.createEditText(this);
        trashView = (ImageView) findViewById(R.id.trash);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);
        density = getResources().getDisplayMetrics().density;
        height = outMetrics.heightPixels;
        width  = outMetrics.widthPixels;
        density = getResources().getDisplayMetrics().density;

        //Keyboard-Section
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //Listener-Section
        FirstOnClickListener firstOnClickListener = new FirstOnClickListener(this, editText);
        ContentDoneEditorListener entryDoneEditorListener = new ContentDoneEditorListener(this);
        longHoldClickListener = new LongHoldClickListener(this, editText, entryDoneEditorListener);
        emptyOnClickListener = new EmptyOnClickListener();
        editTextOnTouchListener = new EditTextOnTouchListener(null, null, trashView, height, width, density);
        ActivityUtility.addListenerToEditText(editText, firstOnClickListener, entryDoneEditorListener);
        categoryButton.setOnClickListener(new OnClickCategoryListener(this, newCategory.getName()));
    }

    private void createEntry(String newEntryName) {
        EditText currentEditText = editText[ListenerUtility.editTextPosition];
        editText[(ListenerUtility.editTextPosition + 1) % editText.length].setVisibility(View.VISIBLE);
        newCategory.addEntry(newEntryName);
        ActivityUtility.addListenerToEditText(currentEditText, emptyOnClickListener, longHoldClickListener, editTextOnTouchListener);
    }

    public void displayContent() {
        int size = newCategory.size();
        for (int i = 0; i < size; i++) {
            EditText currentEditText = editText[i];
            currentEditText.setTextSize(20);
            currentEditText.setVisibility(View.VISIBLE);
            currentEditText.setText(newCategory.getEntry(i).getName());
            ActivityUtility.addListenerToEditText(currentEditText, emptyOnClickListener, longHoldClickListener, editTextOnTouchListener);
        }
        editText[size % editText.length].setVisibility(View.VISIBLE);
    }

    private void startCategoryPageRandomizeActivity(String categoryName) {
        if (!newCategory.isEmpty()) {
            Intent intent = new Intent(this, CategoryPageRandomize.class);
            intent.putExtra(ActivityUtility.CATEGORY_NAME, categoryName);
            intent.putExtra(ActivityUtility.CATEGORY_POSITION, currentCategoryPosition);
            startActivity(intent);
        }
    }

    public void createContent(String newContentName) {
        createEntry(newContentName);
    }


    public void deleteContent(String contentName) {
        //TODO delete Entry
    }


    public void startNextActivity(String contentName) {
        startCategoryPageRandomizeActivity(contentName);
    }


    public void showKeyboard(EditText currentEditText) {
        inputMethodManager.showSoftInput(currentEditText, InputMethodManager.SHOW_IMPLICIT);
    }


    public void hideKeyboard(IBinder windowToken) {
        inputMethodManager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    public String getDescription() {
        return description;
    }

    public void displayInfoMessage(String infoMessage, TextView textView, String currentName) {
        ListenerUtility.setInfoTextMessage(message, textView, infoMessage, currentName);
    }

    public boolean containsContent(String content){
        return newCategory.containsEntry(content);
    }

    public void setNewContentName(String currentName, String newContentName){
        newCategory.changeEntryName(currentName, newContentName);
    }
}

package de.deasycions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collection;

import de.deasycions.data.Category;
import de.deasycions.data.CategoryStorage;
import de.deasycions.data.SharedData;
import de.deasycions.interfaces.IdEASYcionsContent;
import de.deasycions.listener.ContentDoneEditorListener;
import de.deasycions.listener.EditTextOnTouchListener;
import de.deasycions.listener.FirstOnClickListener;
import de.deasycions.listener.LongHoldClickListener;
import de.deasycions.listener.SecondOnClickListener;
import de.deasycions.utilities.ActivityUtility;
import de.deasycions.utilities.ListenerUtility;

/**
 * Start Page of dEASYcions app.
 * It is the home page of the app, first loaded after the splash screen.
 * <p/>
 * Start page is displaying and managing the categories (add, edit, deleting).
 *
 * @author Gary Grossgarten
 * @author Marc Stammerjohann
 */
public class StartPage extends Activity implements IdEASYcionsContent {
    private EditText[] editText;
    private TextView message;
    private CategoryStorage categoryStorage;
    private View.OnLongClickListener longHoldClickListener;
    private View.OnClickListener secondOnClickListener;
    private View.OnTouchListener editTextOnTouchListener;
    private Button randomButton;
    private ImageView trashView;
    private InputMethodManager inputMethodManager;
    private String description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        initialize();
        displayContent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedData sharedData = new SharedData(this);
        sharedData.clearSharedPreferences();
        sharedData.saveData();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
        //TODO if an edittext is focused and the background is touched, lose focus of the edittext! Should save text?!
    }

    private void initialize() {
        //String-Section
        description = getString(R.string.category);
        //Category-Section
        categoryStorage = CategoryStorage.getInstance();
        //Widget-Section
        message = (TextView) findViewById(R.id.ContainsMessage);
        editText = ActivityUtility.createEditText(this);
        randomButton = (Button) findViewById(R.id.random);
        trashView = (ImageView) findViewById(R.id.trash);
        //Keyboard-Section
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //Listener-Section
        FirstOnClickListener firstOnClickListener = new FirstOnClickListener(this, editText);
        ContentDoneEditorListener categoryDoneEditorListener = new ContentDoneEditorListener(this);
        longHoldClickListener = new LongHoldClickListener(this, editText, categoryDoneEditorListener);
        secondOnClickListener = new SecondOnClickListener(this, editText);

        editTextOnTouchListener = new EditTextOnTouchListener(randomButton, getString(R.string.random), trashView);
        ActivityUtility.addListenerToEditText(editText, firstOnClickListener, categoryDoneEditorListener);
    }

    /**
     * @param categoryName
     */
    public void createCategory(String categoryName) {
        EditText currentEditText = editText[ListenerUtility.editTextPosition];
        editText[(ListenerUtility.editTextPosition + 1) % editText.length].setVisibility(View.VISIBLE);
        currentEditText.performHapticFeedback(1);
        categoryStorage.createCategory(categoryName);
        ActivityUtility.addListenerToEditText(currentEditText, secondOnClickListener, longHoldClickListener, editTextOnTouchListener);
        currentEditText.setOnLongClickListener(longHoldClickListener);
        currentEditText.setOnClickListener(secondOnClickListener);
        startCategoryPageActivity(categoryName);
    }

    public void displayContent() {
        int position = 0;
        if (!categoryStorage.isEmpty()) {
            Collection<Category> categoryValues = categoryStorage.getCategoryValues();
            for (Category currentCategory : categoryValues) {
                EditText currentEditText = editText[position];
                currentEditText.setTextSize(20);
                currentEditText.setText(currentCategory.getName());
                ActivityUtility.addListenerToEditText(currentEditText, secondOnClickListener, longHoldClickListener, editTextOnTouchListener);
                editText[position + 1].setVisibility(View.VISIBLE);
                position++;
            }
        }
    }

    /**
     * Starting the {@link de.deasycions.CategoryPage}-Activity.
     *
     * @param categoryName of the category which will be displayed.
     */
    private void startCategoryPageActivity(String categoryName) {
        Intent intent = new Intent(this, CategoryPage.class);
        intent.putExtra(ActivityUtility.CATEGORY_NAME, categoryName);
        intent.putExtra(ActivityUtility.CATEGORY_POSITION, ListenerUtility.editTextPosition);
        startActivity(intent);
    }


    public void createContent(String newContentName) {
        createCategory(newContentName);
    }


    public void deleteContent(String contentName) {
        //TODO delete category
    }


    public void startNextActivity(String contentName) {
        startCategoryPageActivity(contentName);
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

    public boolean containsContent(String content) {
        return categoryStorage.containsCategory(content);
    }

    public void setNewContentName(String currentName, String newContentName){
        categoryStorage.setNewCategoryName(currentName, newContentName);
    }

    //TODO delete Category
    //private void deleteCategory(String categoryName) {
    //    categoryStorage.deleteCategory(categoryName);
    //    editText[position].setOnClickListener(new SecondOnClickListener(this, editText));
    //    startCategoryPageActivity(categoryName);
    // }


}
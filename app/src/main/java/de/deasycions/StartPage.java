package de.deasycions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import de.deasycions.data.Category;
import de.deasycions.data.CategoryStorage;
import de.deasycions.data.SharedData;
import de.deasycions.listener.CategoryDoneEditorListener;
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
public class StartPage extends Activity {
    private EditText[] editText;
    private TextView message;
    private CategoryStorage categoryStorage;
    private View.OnLongClickListener longHoldClickListener;
    private View.OnClickListener secondOnClickListener;
    private View.OnTouchListener editTextOnTouchListener;
    private Button randomButton;
    private ImageView trashView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        initialize();
        displayCategories();
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
        //Category-Section
        categoryStorage = CategoryStorage.getInstance();
        //Widget-Section
        message = (TextView) findViewById(R.id.ContainsMessage);
        editText = ActivityUtility.createEditText(this);
        randomButton = (Button) findViewById(R.id.random);
        trashView = (ImageView) findViewById(R.id.trash);
        //Listener-Section
        FirstOnClickListener firstOnClickListener = new FirstOnClickListener(this, editText);
        CategoryDoneEditorListener categoryDoneEditorListener = new CategoryDoneEditorListener(this, message);
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

    private void displayCategories() {
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
    public void startCategoryPageActivity(String categoryName) {
        Intent intent = new Intent(this, CategoryPage.class);
        intent.putExtra(ActivityUtility.CATEGORY_NAME, categoryName);
        intent.putExtra(ActivityUtility.CATEGORY_POSITION, ListenerUtility.editTextPosition);
        startActivity(intent);
    }

    //TODO delete Category
    //private void deleteCategory(String categoryName) {
    //    categoryStorage.deleteCategory(categoryName);
    //    editText[position].setOnClickListener(new SecondOnClickListener(this, editText));
    //    startCategoryPageActivity(categoryName);
    // }


}
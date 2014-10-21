package de.deasycions;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.deasycions.data.Category;
import de.deasycions.data.CategoryStorage;
import de.deasycions.data.SharedData;
import de.deasycions.listener.CategoryDoneEditorListener;
import de.deasycions.listener.EntryDoneEditorListener;
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
public class CategoryPage extends Activity {

    private CategoryStorage categoryStorage;
    private Category newCategory;
    private EditText[] editText;
    private TextView message;
    private Button categoryButton;
    //swaping color of the button to the selected category
    private int currentCategoryPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);
        initialize();
        displayEntries();
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

    private void displayEntries() {
        int size = newCategory.size();
        for (int i = 0; i < size; i++) {
            EditText currentEditText = editText[i];
            currentEditText.setTextSize(20);
            currentEditText.setVisibility(View.VISIBLE);
            editText[i].setText(newCategory.getEntry(i).getName());
            editText[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //do nothing
                }
            });
        }
        editText[size % editText.length].setVisibility(View.VISIBLE);
    }

    private void initialize() {
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
        //Listener-Section
        FirstOnClickListener firstOnClickListener = new FirstOnClickListener(this, editText);
        EntryDoneEditorListener entryDoneEditorListener = new EntryDoneEditorListener(this, message, newCategory);
        LongHoldClickListener longHoldClickListener = new LongHoldClickListener(this, editText, entryDoneEditorListener);
        ActivityUtility.addListenerToEditText(editText, firstOnClickListener, entryDoneEditorListener, longHoldClickListener);
        categoryButton.setOnClickListener(new OnClickCategoryListener(this, newCategory.getName()));
    }

    public void addEntry(String newEntryName) {
        editText[(ListenerUtility.editTextPosition + 1) % editText.length].setVisibility(View.VISIBLE);
        newCategory.addEntry(newEntryName);
        editText[ListenerUtility.editTextPosition].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do nothing
            }
        });
    }

    public void startCategoryPageRandomizeActivity(String categoryName) {
        if (!newCategory.isEmpty()) {
            Intent intent = new Intent(this, CategoryPageRandomize.class);
            intent.putExtra(ActivityUtility.CATEGORY_NAME, categoryName);
            intent.putExtra(ActivityUtility.CATEGORY_POSITION, currentCategoryPosition);
            startActivity(intent);
        }
    }
}

package de.deasycions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.deasycions.data.Category;
import de.deasycions.data.CategoryStorage;
import de.deasycions.listener.OnClickRandomListener;
import de.deasycions.utilities.ActivityUtility;

/**
 * @author Gary         //TODO auskommentieren!!
 */
public class CategoryPageRandomize extends Activity {

    private EditText[] editText;
    private CategoryStorage categoryStorage;
    private Category currentCategory;
    private Button randomize;
    private String categoryName;
    private TextView categoryTextView;
    //swaping color of the button to the selected category
    private int currentCategoryPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page_random);
        initialize();
        displayEntries();
        ActivityUtility.swapBackgroundColor(randomize, editText, currentCategoryPosition);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void initialize() {
        //Intent-Section
        Intent intent = getIntent();
        categoryName = intent.getStringExtra(ActivityUtility.CATEGORY_NAME);
        currentCategoryPosition = intent.getIntExtra(ActivityUtility.CATEGORY_POSITION, -1);
        //Category-Section
        categoryStorage = CategoryStorage.getInstance();
        currentCategory = categoryStorage.getCategory(categoryName);
        //Widget-Section
        randomize = (Button) findViewById(R.id.categoryName);
        categoryTextView = (TextView) findViewById(R.id.displayCategoryName);
        categoryTextView.setText(categoryName);
        editText = ActivityUtility.createEditText(this);
        //Listener-Section
        randomize.setOnClickListener(new OnClickRandomListener(this, currentCategory));
    }

    private void displayEntries() {
        int size = currentCategory.size();
        for (int i = 0; i < size; i++) {
            EditText currentEditText = editText[i];
            currentEditText.setVisibility(View.VISIBLE);
            editText[i].setText(currentCategory.getEntry(i).getName());
        }
    }

    public void startResultPageActivity(String result) {
        Intent intent = new Intent(this, ResultPage.class);
        intent.putExtra(ActivityUtility.RESULT, result);
        startActivity(intent);
    }
}

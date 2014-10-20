package de.deasycions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;
import java.util.Set;

import de.deasycions.data.Category;
import de.deasycions.data.CategoryStorage;
import de.deasycions.data.SharedData;
import de.deasycions.listener.CategoryDoneEditorListener;
import de.deasycions.listener.FirstOnClickListener;
import de.deasycions.listener.LongHoldClickListener;
import de.deasycions.listener.SecondOnClickListener;
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
    /**
     * Constant for recieving the category name in the intent.
     */
    public final static String CATEGORY_NAME = "de.deasycions.MESSAGE";
    // private Button randomize;
    private EditText[] editText;
    private TextView message;
    private CategoryStorage categoryStorage;

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

    private void initialize() {
        categoryStorage = CategoryStorage.getInstance();
        //randomize = (Button) findViewById(R.id.random);
        message = (TextView) findViewById(R.id.ContainsMessage);
        CategoryDoneEditorListener categoryDoneEditorListener = new CategoryDoneEditorListener(this, message);

        editText = new EditText[6];
        editText[0] = (EditText) findViewById(R.id.etLU);
        editText[1] = (EditText) findViewById(R.id.etRU);
        editText[2] = (EditText) findViewById(R.id.etR);
        editText[3] = (EditText) findViewById(R.id.etRD);
        editText[4] = (EditText) findViewById(R.id.etLD);
        editText[5] = (EditText) findViewById(R.id.etL);

        for (int i = 0; i < editText.length; i++) {
            editText[i].setOnClickListener(new FirstOnClickListener(this, editText));
            editText[i].setOnEditorActionListener(categoryDoneEditorListener);
            editText[i].setOnLongClickListener(new LongHoldClickListener(this, editText, categoryDoneEditorListener));
        }
    }

    /**
     *
     * @param categoryName
     */
    public void createCategory(String categoryName) {
        editText[(ListenerUtility.editTextPosition + 1) % editText.length].setVisibility(View.VISIBLE);
        editText[ListenerUtility.editTextPosition].performHapticFeedback(1);
        categoryStorage.createCategory(categoryName);
        editText[ListenerUtility.editTextPosition].setOnClickListener(new SecondOnClickListener(this, editText));
        startCategoryPageActivity(categoryName);
    }

    private void displayCategories() {
        int position = 0;
        if (!categoryStorage.isEmpty()) {
            Set<Map.Entry<String, Category>> categorySet = categoryStorage.getCategorySet();
            for (Map.Entry<String, Category> entry : categorySet) {
                Category currentCategory = entry.getValue();
                editText[position].setTextSize(20);
                editText[position].setText(currentCategory.getName());
                editText[position].setOnClickListener(new SecondOnClickListener(this, editText));
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
        intent.putExtra(CATEGORY_NAME, categoryName);
        startActivity(intent);
    }

    //public void startRandom(View view) {
    //}

    //private void deleteCategory(String categoryName) {
    //    categoryStorage.deleteCategory(categoryName);
    //    editText[position].setOnClickListener(new SecondOnClickListener(this, editText));
    //    startCategoryPageActivity(categoryName);
    // }
    //TODO delete old Category, create Category with edited name (if edited) with same entries


}
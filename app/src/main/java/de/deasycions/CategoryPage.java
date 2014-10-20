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
import de.deasycions.data.SharedData;
import de.deasycions.listener.CategoryDoneEditorListener;
import de.deasycions.listener.EntryDoneEditorListener;
import de.deasycions.listener.FirstOnClickListener;
import de.deasycions.listener.LongHoldClickListener;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);
        Intent intent = getIntent();
        String categoryName = intent.getStringExtra(StartPage.CATEGORY_NAME);
        ((Button) findViewById(R.id.categoryName)).setText(categoryName);
        categoryStorage = CategoryStorage.getInstance();
        newCategory = categoryStorage.getCategory(categoryName);

        initialize();
        displayEntries();
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
        message = (TextView) findViewById(R.id.ContainsMessage);
        EntryDoneEditorListener entryDoneEditorListener = new EntryDoneEditorListener(this, message, newCategory);

        editText = new EditText[6];
        editText[0] = (EditText) findViewById(R.id.cetLU);
        editText[1] = (EditText) findViewById(R.id.cetRU);
        editText[2] = (EditText) findViewById(R.id.cetR);
        editText[3] = (EditText) findViewById(R.id.cetRD);
        editText[4] = (EditText) findViewById(R.id.cetLD);
        editText[5] = (EditText) findViewById(R.id.cetL);

        for (int i = 0; i < editText.length; i++) {
            editText[i].setOnClickListener(new FirstOnClickListener(this, editText));
            editText[i].setOnEditorActionListener(entryDoneEditorListener);
            editText[i].setOnLongClickListener(new LongHoldClickListener(this, editText, entryDoneEditorListener));
        }
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
}

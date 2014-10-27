package de.deasycions;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import de.deasycions.data.Category;
import de.deasycions.listener.EditTextOnTouchListener;
import de.deasycions.listener.EmptyOnClickListener;
import de.deasycions.utilities.ActivityUtility;
import de.deasycions.utilities.ListenerUtility;

/**
 * Category age of dEASYcions app.
 * Category page is displaying and managing the entries of a category (add, edit, deleting).
 *
 * @author Marc Stammerjohann
 */
public class CategoryPage extends EditablePage {

    private Category newCategory;
    private Button categoryButton;
    private View.OnClickListener emptyOnClickListener;
    private View.OnTouchListener editTextOnTouchListener;
    //swaping color of the button to the selected category
    private int currentCategoryPosition;

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

    protected void initialize() {
        super.initialize();
        //String-Section
        setDescription(getString(R.string.entry));
        //Intent-Section
        Intent intent = getIntent();
        String categoryName = intent.getStringExtra(ActivityUtility.CATEGORY_NAME);
        currentCategoryPosition = intent.getIntExtra(ActivityUtility.CATEGORY_POSITION, -1);
        //Category-Section
        newCategory = categoryStorage.getCategory(categoryName);
        //Widget-Section
        categoryButton = (Button) findViewById(R.id.categoryName);
        categoryButton.setText(newCategory.getName());
        //Listener-Section
        emptyOnClickListener = new EmptyOnClickListener();
        editTextOnTouchListener = new EditTextOnTouchListener(this, null, null, trashView, height, width, density);
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

    public void createContent(String newEntryName) {
        EditText currentEditText = editText[ListenerUtility.editTextPosition];
        editText[(ListenerUtility.editTextPosition + 1) % editText.length].setVisibility(View.VISIBLE);
        newCategory.addEntry(newEntryName);
        ActivityUtility.addListenerToEditText(currentEditText, emptyOnClickListener, longHoldClickListener, editTextOnTouchListener);
    }

    public void deleteContent(View currentView) {
        if(currentView instanceof EditText){
            EditText currentEditText = (EditText) currentView;
            newCategory.removeEntry(currentEditText.getText().toString());
            refreshDisplay(currentEditText);
        }
    }

    public boolean containsContent(String content) {
        return newCategory.containsEntry(content);
    }

    public void setNewContentName(String currentName, String newContentName) {
        newCategory.changeEntryName(currentName, newContentName);
    }
}

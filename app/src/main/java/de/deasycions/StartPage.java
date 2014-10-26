package de.deasycions;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Collection;

import de.deasycions.data.Category;
import de.deasycions.data.SharedData;
import de.deasycions.listener.EditTextOnTouchListener;
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
public class StartPage extends EditablePage {

    private View.OnClickListener secondOnClickListener;
    private View.OnTouchListener editTextOnTouchListener;
    private Button randomButton;

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

    protected void initialize() {
        super.initialize();
        //String-Section
        setDescription(getString(R.string.category));
        //Widget-Section
        randomButton = (Button) findViewById(R.id.random);
        //Listener-Section
        secondOnClickListener = new SecondOnClickListener(this, editText);
        editTextOnTouchListener = new EditTextOnTouchListener(this, randomButton, getString(R.string.random), trashView, height, width, density);
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
                editText[(position + 1) % editText.length].setVisibility(View.VISIBLE);
                position++;
            }
        }
    }

    public void createContent(String newCategoryName) {
        EditText currentEditText = editText[ListenerUtility.editTextPosition];
        editText[(ListenerUtility.editTextPosition + 1) % editText.length].setVisibility(View.VISIBLE);
        currentEditText.performHapticFeedback(1);
        categoryStorage.createCategory(newCategoryName);
        ActivityUtility.addListenerToEditText(currentEditText, secondOnClickListener, longHoldClickListener, editTextOnTouchListener);
        currentEditText.setOnLongClickListener(longHoldClickListener);
        currentEditText.setOnClickListener(secondOnClickListener);
        startNextActivity(newCategoryName, Page.CATEGORY_PAGE);
    }

    public void deleteContent(View currentView) {
        if (currentView instanceof EditText) {
            EditText currentEditText = (EditText) currentView;
            categoryStorage.deleteCategory(currentEditText.getText().toString());
            currentEditText.setText("");
            currentEditText.setTextSize(50);
            currentEditText.setOnClickListener(firstOnClickListener);
            currentEditText.clearFocus();
            currentEditText.setFocusableInTouchMode(false);
            refreshDisplay(currentEditText);
        }
    }

    private void refreshDisplay(EditText currentEditText) {
        if (categoryStorage.size() < 5) {
            //TODO set invisible or rearrange edittext, no gaps are allowed
        }
    }

    /**
     * Starting the {@link de.deasycions.CategoryPage}-Activity.
     *
     * @param contentName of the category which will be displayed.
     */
    public void startNextActivity(String contentName, Page page) {
        Intent intent = null;
        switch (page) {
            case CATEGORY_PAGE:
                intent = new Intent(this, CategoryPage.class);
                break;
            case RANDOMIZE_PAGE:
                if (!categoryStorage.getCategory(contentName).isEmpty()) {
                    intent = new Intent(this, CategoryPageRandomize.class);
                }
                break;
            default:
                break;
        }
        if (intent != null) {
            intent.putExtra(ActivityUtility.CATEGORY_NAME, contentName);
            intent.putExtra(ActivityUtility.CATEGORY_POSITION, ListenerUtility.editTextPosition);
            startActivity(intent);
        }
    }

    public boolean containsContent(String content) {
        return categoryStorage.containsCategory(content);
    }

    public void setNewContentName(String currentName, String newContentName) {
        categoryStorage.setNewCategoryName(currentName, newContentName);
    }

}
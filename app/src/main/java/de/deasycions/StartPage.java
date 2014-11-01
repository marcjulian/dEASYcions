package de.deasycions;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Collection;

import de.deasycions.customText.EasyText;
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
    protected void onResume() {
        super.onResume();
        // When returning to StartPage, the contentPage in easytext must be reset.
        EasyText.setContentPage(this);
    }

    protected void initialize() {
        super.initialize();
        //String-Section
        setDescription(getString(R.string.category));
        //Widget-Section
        randomButton = (Button) findViewById(R.id.random);
        //Listener-Section
        secondOnClickListener = new SecondOnClickListener(this);
        editTextOnTouchListener = new EditTextOnTouchListener(this, randomButton, getString(R.string.random), trashView, height, width, density);
    }

    public void displayContent() {
        int position = 0;
        if (!categoryStorage.isEmpty()) {
            Collection<Category> categoryValues = categoryStorage.getCategoryValues();
            for (Category currentCategory : categoryValues) {
                EasyText currentEasyText = easyTexts[position];
                currentEasyText.setTextSize(20);
                String name = currentCategory.getName();
                currentEasyText.setCurrentName(name);
                currentEasyText.setText(name);
                ActivityUtility.addListenerToEditText(currentEasyText, secondOnClickListener, longHoldClickListener, editTextOnTouchListener);
                currentEasyText.getBehind().setVisibility(View.VISIBLE);
                position++;
            }
        }
    }

    public void createContent(EasyText currentEasyText) {
        String newCategoryName = currentEasyText.getText().toString();
        currentEasyText.getBehind().setVisibility(View.VISIBLE);
        currentEasyText.performHapticFeedback(1);
        categoryStorage.createCategory(newCategoryName);
        currentEasyText.clearFocus();
        ActivityUtility.addListenerToEditText(currentEasyText, secondOnClickListener, longHoldClickListener, editTextOnTouchListener);
        startNextActivity(currentEasyText, Page.CATEGORY_PAGE);
    }

    public void deleteContent(View currentView) {
        if (currentView instanceof EasyText) {
            EasyText currentEditText = (EasyText) currentView;
            categoryStorage.deleteCategory(currentEditText.getText().toString());
            refreshDisplay(currentEditText, categoryStorage.size());
        }
    }

    /**
     * Starting the {@link de.deasycions.CategoryPage}-Activity.
     *
     */
    public void startNextActivity(EasyText currentEasyText, Page page) {
        Intent intent = null;
        String contentName = currentEasyText.getText().toString();
        switch (page) {
            case CATEGORY_PAGE:
                intent = new Intent(this, CategoryPage.class);
                break;
            case RANDOMIZE_PAGE:
                if (!categoryStorage.getCategory(contentName).isEmpty()) {
                    intent = new Intent(this, CategoryPageRandomize.class);
                }else{
                    displayInfoMessage("Category must not be empty!");
                }
                break;
            default:
                break;
        }
        if (intent != null) {
            intent.putExtra(ActivityUtility.CATEGORY_NAME, contentName);
            intent.putExtra(ActivityUtility.CATEGORY_POSITION, currentEasyText.getPosition());
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
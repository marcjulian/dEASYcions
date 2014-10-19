package de.deasycions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;
import java.util.Set;

import de.deasycions.data.Category;
import de.deasycions.data.CategoryStorage;
import de.deasycions.data.SharedData;


public class StartPage extends Activity {
   public final static String CATEGORY_NAME = "de.deasycions.MESSAGE";
   private Button randomize;
   private EditText[] editText;
   private CategoryStorage categoryStorage;
   private  InputMethodManager imm;
   private int position;

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
        sharedData.saveData();
    }

    private void initialize() {
        position = 0;
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        categoryStorage = CategoryStorage.getInstance();
        randomize = (Button) findViewById(R.id.random);
        editText = new EditText[6];
        editText[0] = (EditText) findViewById(R.id.etLU);
        editText[1] = (EditText) findViewById(R.id.etRU);
        editText[2] = (EditText) findViewById(R.id.etR);
        editText[3]  = (EditText) findViewById(R.id.etRD);
        editText[4]  = (EditText) findViewById(R.id.etLD);
        editText[5]  = (EditText) findViewById(R.id.etL);

        //the last editText gets another OnClickListener
        for(int i = 0; i < editText.length; i++){
            editText[i].setOnClickListener(new FirstOnClickListener());
            editText[i].setOnEditorActionListener(new DoneEditorListener());
            if(i == 5){
                editText[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO do the action only, if the categoryName is not null
                        editText[5].setTextSize(20);
                    }
                });
            }
        }
    }

    private void createCategory(String categoryName){
        editText[position + 1].setVisibility(View.VISIBLE);
        editText[position].performHapticFeedback(1);
        //TODO use exist method vom Category Storage, if doesn't exist call addCategory, startCategoryPageAcitivity and add new Listener
        categoryStorage.createCategory(categoryName);
        editText[position].setOnClickListener(new SecondOnClickListener());
        startCategoryPageActivity(categoryName);

    }

    private void displayCategories() {
        if(!categoryStorage.isEmpty()){
            Set<Map.Entry<String, Category>> categorySet = categoryStorage.getCategorySet();
            for(Map.Entry<String, Category> entry : categorySet){
                Category currentCategory = entry.getValue();
                editText[position].setTextSize(20);
                editText[position].setText(currentCategory.getName());
                editText[position].setOnClickListener(new SecondOnClickListener());
                editText[position + 1].setVisibility(View.VISIBLE);
                position++;
            }
        }
    }

    private void startCategoryPageActivity(String categoryName) {
        Intent intent = new Intent(this, CategoryPage.class);
        intent.putExtra(CATEGORY_NAME, categoryName);
        startActivity(intent);
    }

    private int getEditTextPosition(View view) {
        int id =  view.getId();
        int counter = 0;
        while(editText[counter].getId() != id){
            counter++;
        }
        return counter;
    }

    public void startRandom(View view){

    }

    private class FirstOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
           position = getEditTextPosition(view);
           EditText current = editText[position];
           current.setFocusableInTouchMode(true);
           current.setTextSize(20);
           current.requestFocus();
           imm.showSoftInput(current, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private class SecondOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            position = getEditTextPosition(view);
            startCategoryPageActivity(editText[position].getText().toString());
        }
    }

    private class DoneEditorListener implements TextView.OnEditorActionListener{

        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String categoryName = textView.getText().toString();
                imm.hideSoftInputFromWindow(textView.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
                //TODO call the method createCategory only, if the categoryName is not null, if it is null set the text size back to 50
                createCategory(categoryName);
                //disabled editing of the text
                textView.setFocusableInTouchMode(false);
                textView.clearFocus();
                handled = true;
            }
            return handled;
        }
    }

    /** private class OnHoldClickListener implements View.OnLongClickListener{

    @Override
    public boolean onLongClick(View view) {
    view.performHapticFeedback(1);
    position = getEditTextPosition(view);
    EditText current = editText[position];
    current.setFocusableInTouchMode(true);
    current.setTextSize(20);
    current.requestFocus();
    imm.showSoftInput(current, InputMethodManager.SHOW_IMPLICIT);

    return false;
    }
    }
    //TODO Disable Editing of TextViews without LongClick
     */
}
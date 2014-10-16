package de.deasycions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;

import de.deasycions.input.Category;
import de.deasycions.input.CategoryStorage;


public class StartPage extends Activity {
   public final static String CATEGORY_NAME = "de.deasycions.MESSAGE";
   private Button randomize;
   private EditText[] editText;
   private CategoryStorage categoryStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        initialize();
    }

    private void initialize() {
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

    private void setNextETVisible(final EditText et, final EditText etNext){
        //TODO do the action only, if the categoryName is not null
        String categoryName = et.getText().toString();

         et.setTextSize(20);
         etNext.setVisibility(View.VISIBLE);
         et.performHapticFeedback(1);
         //et.setEnabled(false);

        Category category = new Category(categoryName);
        //TODO use exist method vom Category Storage, if doesn't exist addCategory,  startCategoryPageAcitivity and add new Listener
        categoryStorage.addCategory(category);
        et.setOnClickListener(new SecondOnClickListener());
        startCategoryPageActivity(categoryName);

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
           int counter = getEditTextPosition(view);
           setNextETVisible(editText[counter], editText[counter+1]);
        }
    }

    private class SecondOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            int counter = getEditTextPosition(view);
            startCategoryPageActivity(editText[counter].getText().toString());
        }
    }

}


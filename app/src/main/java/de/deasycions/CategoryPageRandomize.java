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
import de.deasycions.listener.FirstOnClickListener;
import de.deasycions.listener.LongHoldClickListener;
import de.deasycions.listener.OnClickRandomListener;
import de.deasycions.utilities.ListenerUtility;

/**
 * @author Gary         //TODO auskommentieren!!
 */
public class CategoryPageRandomize extends Activity {
    private EditText[] editText;
    private CategoryStorage categoryStorage;
    private Category newCategory;
    private Button randomize;
    String categoryName;
    TextView categoryTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page_random);
        initialize();
        Intent intent = getIntent();
        categoryName = intent.getStringExtra("CategoryNameExtra");
        newCategory = categoryStorage.getCategory(categoryName);
        ((TextView) findViewById(R.id.displayCategoryName)).setText(categoryName);
        displayEntries();
    }



    private void initialize() {
        categoryStorage = CategoryStorage.getInstance();
        randomize = (Button) findViewById(R.id.categoryName);

        categoryTextView = (TextView) findViewById(R.id.displayCategoryName);

        editText = new EditText[6];
        editText[0] = (EditText) findViewById(R.id.cetLU);
        editText[1] = (EditText) findViewById(R.id.cetRU);
        editText[2] = (EditText) findViewById(R.id.cetR);
        editText[3] = (EditText) findViewById(R.id.cetRD);
        editText[4] = (EditText) findViewById(R.id.cetLD);
        editText[5] = (EditText) findViewById(R.id.cetL);

        randomize.setOnClickListener(new OnClickRandomListener(this, randomize, editText));
    }

    public void setNextETVisible(final EditText et, final EditText etNext){
        et.setTextSize(20);
        etNext.setVisibility(View.VISIBLE);
        et.performHapticFeedback(1);
        et.setEnabled(false);

        String entryName = et.getText().toString();
        newCategory.addEntry(entryName);
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

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void startResultPageActivity(String result) {
        Intent intent = new Intent(this, ResultPage.class);
        intent.putExtra("Result", result);
        startActivity(intent);
    }
}

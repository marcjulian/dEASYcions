package de.deasycions;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Map;
import java.util.Set;

import de.deasycions.data.Category;
import de.deasycions.data.CategoryStorage;


public class CategoryPage extends Activity {
    private CategoryStorage categoryStorage;
    private Category newCategory;

    private static final String filename = "CategoryStorage";
    private SharedPreferences savedData;
    private static final String CATEGORY = "CATEGORY";

    //TODO change to one edittext array, see StartPage class
    private EditText clu,cru,cr,cl,cld,crd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);
        initialize();

        Intent intent = getIntent();
        String categoryName = intent.getStringExtra(StartPage.CATEGORY_NAME);
        ((Button) findViewById(R.id.categoryName)).setText(categoryName);

        newCategory = categoryStorage.getCategory(categoryName);
        if(!newCategory.isEmpty()){
            showEntries();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveData();
    }

    // TODO use the array index (for loop?), maybe then we won't need the switch case anymore
    private void showEntries() {
        int size = newCategory.size();
        switch (size){
            case 1: setNextETVisible(clu,cru);
                clu.setText(newCategory.getEntry(0).getName());
                break;
            case 2:
                setNextETVisible(clu, cru);
                setNextETVisible(cru, cr);
                clu.setText(newCategory.getEntry(0).getName());
                cru.setText(newCategory.getEntry(1).getName());
                break;
            //TODO more cases or for loop?
            default:break;

        }
    }

    private void initialize() {
       categoryStorage = CategoryStorage.getInstance();
       savedData = getSharedPreferences(filename, MODE_PRIVATE);

        //TODO initialize the array of EditText, add the EditText in the correct order (clu, cru, cr, crd, cld, cl), see StartPage Class
        clu = (EditText) findViewById(R.id.cetLU);
        clu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNextETVisible(clu,cru);
            }
        });
        cru = (EditText) findViewById(R.id.cetRU);
        cru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNextETVisible(cru, cr);
            }
        });
        cr = (EditText) findViewById(R.id.cetR);
        cr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNextETVisible(cr, crd);
            }
        });
        crd = (EditText) findViewById(R.id.cetRD);
        crd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNextETVisible(crd, cld);
            }
        });
        cld = (EditText) findViewById(R.id.cetLD);
        cld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNextETVisible(cld, cl);
            }
        });
        cl = (EditText) findViewById(R.id.cetL);
        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl.setTextSize(20);
            }
        });

    }

    public void setNextETVisible(final EditText et, final EditText etNext){
        et.setTextSize(20);
        etNext.setVisibility(View.VISIBLE);
        et.performHapticFeedback(1);
        et.setEnabled(false);

        String entryName = et.getText().toString();
        newCategory.addEntry(entryName);
      }

    public void saveData(){
        int counter = 0;
        SharedPreferences.Editor editor = savedData.edit();
        Set<Map.Entry<String, Category>> categorySet = categoryStorage.getCategorySet();
        for(Map.Entry<String, Category> entry : categorySet){
            Category currentCategory = entry.getValue();
            String categoryName = currentCategory.getName();
            editor.putString(CATEGORY + counter, categoryName);
            for (int i = 0; i < currentCategory.size(); i++){
                editor.putString(categoryName + i, currentCategory.getEntry(i).getName());
            }
            counter++;
        }
        editor.commit();
    }
}

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
   private EditText lu,ru,r,l,ld,rd;
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
        lu = (EditText) findViewById(R.id.etLU);
        lu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNextETVisible(lu, ru);
            }
        });
        ru = (EditText) findViewById(R.id.etRU);
        ru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNextETVisible(ru, r);
            }
        });
        r = (EditText) findViewById(R.id.etR);
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNextETVisible(r, rd);
            }
        });
        rd = (EditText) findViewById(R.id.etRD);
        rd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNextETVisible(rd, ld);
            }
        });
        ld = (EditText) findViewById(R.id.etLD);
        ld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNextETVisible(ld, l);
            }
        });
        l = (EditText) findViewById(R.id.etL);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l.setTextSize(20);
            }
        });

    }

    public void setNextETVisible(final EditText et, final EditText etNext){
         et.setTextSize(20);
         etNext.setVisibility(View.VISIBLE);
         et.performHapticFeedback(1);
         //et.setEnabled(false);

         String categoryName = et.getText().toString();
         Category category = new Category(categoryName);
         categoryStorage.addCategory(category);

         Intent intent = new Intent(this, CategoryPage.class);
         intent.putExtra(CATEGORY_NAME, categoryName);
         startActivity(intent);
    }


    public void startRandom(View view){

    }

}


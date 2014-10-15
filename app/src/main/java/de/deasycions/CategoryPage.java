package de.deasycions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import de.deasycions.input.Category;
import de.deasycions.input.CategoryStorage;


public class CategoryPage extends Activity {
    private CategoryStorage categoryStorage;
    private Category newCategory;

    private EditText clu,cru,cr,cl,cld,crd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);
        initialize();

        Intent intent = getIntent();
        String categoryName = intent.getStringExtra(StartPage.CATEGORY_NAME);
        ((Button) findViewById(R.id.crandom)).setText(categoryName);

        newCategory = categoryStorage.getCategory(categoryName);

    }

    private void initialize() {
       categoryStorage = CategoryStorage.getInstance();
        
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
      }

    public void startRandom(View view){

    }

}

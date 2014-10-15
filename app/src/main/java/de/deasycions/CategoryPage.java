package de.deasycions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class CategoryPage extends Activity {
private    EditText clu,cru,cr,cl,cld,crd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);

        Intent intent = getIntent();
        String catName = intent.getStringExtra(StartPage.CATEGORYNAME);
        ((Button) findViewById(R.id.crandom)).setText(catName);

        initialize();
    }

    private void initialize() {

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




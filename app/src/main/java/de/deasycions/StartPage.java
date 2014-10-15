package de.deasycions;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class StartPage extends Activity {
   private Button randomize;
   private EditText lu,ru,r,l,ld,rd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        initialize();
    }

    private void initialize() {
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.start_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startRandom(View view){

    }

}


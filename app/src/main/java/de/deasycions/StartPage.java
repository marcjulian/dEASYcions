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
        ru = (EditText) findViewById(R.id.etRU);
        r = (EditText) findViewById(R.id.etR);
        l = (EditText) findViewById(R.id.etL);
        ld = (EditText) findViewById(R.id.etLD);
        rd = (EditText) findViewById(R.id.etRD);
    }

    public void setNextETVisible(final EditText et, final EditText etNext){
         et.setTextSize(20);
         et.setSelected(true);
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

    public void newCategory(View view){
       int i = view.getId();
        switch (i){
            case R.id.etLU:
                setNextETVisible(lu, ru);
            case R.id.etRU:
                setNextETVisible(ru, r);
            case R.id.etR:
                setNextETVisible(r, rd);
            case R.id.etRD:
                setNextETVisible(rd, ld);
            case R.id.etLD:
                setNextETVisible(ld, l);
            case R.id.etL:
               l.setTextSize(20);
               l.setSelected(true);
        }
    }
}


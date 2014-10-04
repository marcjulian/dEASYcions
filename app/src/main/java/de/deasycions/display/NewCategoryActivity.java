package de.deasycions.display;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import de.deasycions.R;
import de.deasycions.StartPage;
import de.deasycions.input.Category;

public class NewCategoryActivity extends Activity {
    public final static String CATEGORY_NAME = "de.deasycions.display.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_category, menu);
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

    // TODO new Activity for the entries
    public void saveCategory(View view){
       // Intent intent = new Intent(this, AddEntriesActivity.class);
       //EditText editCategory = (EditText) findViewById(R.id.new_category);
       //String name = editCategory.getText().toString();
       //intent.putExtra(CATEGORY_NAME, name);
      // startActivity(intent);
    }

    public void cancelActivity(View view){
        Intent intent = new Intent(this, StartPage.class );
        startActivity(intent);
    }
}

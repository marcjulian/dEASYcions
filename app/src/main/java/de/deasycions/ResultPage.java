package de.deasycions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import de.deasycions.utilities.ActivityUtility;

/**
 * @author Gary             //TODO auskommentieren!!
 */
public class ResultPage extends Activity {
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);
        initialize();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void initialize() {
        //Intent-Section
        Intent intent = getIntent();
        String resultString = intent.getStringExtra(ActivityUtility.RESULT);
        //Widget-Section
        result = (TextView) findViewById(R.id.resultTextView);
        result.setText(resultString);
    }
}

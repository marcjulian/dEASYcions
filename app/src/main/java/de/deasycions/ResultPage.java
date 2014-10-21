package de.deasycions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @author Gary             //TODO auskommentieren!!
 */
public class ResultPage extends Activity{
    TextView result;
    String resultString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);
        result = (TextView) findViewById(R.id.resultTextView);
        Intent intent = getIntent();
        resultString = intent.getStringExtra("Result");
        ((TextView) findViewById(R.id.resultTextView)).setText(resultString);


    }
}

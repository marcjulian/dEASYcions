package de.deasycions.listener;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

import de.deasycions.CategoryPage;
import de.deasycions.CategoryPageRandomize;
import de.deasycions.data.Category;
import de.deasycions.data.CategoryStorage;
import de.deasycions.data.EmptyCategoryException;
import de.deasycions.data.SharedData;
import de.deasycions.random.Deasycions;

/**
 * @author Gary                 //TODO auskommentieren!!
 */
public class OnClickRandomListener implements View.OnClickListener {
    Activity currentActivity;
    Button category_button;
    String resultString;
    EditText[] entries;

    public OnClickRandomListener(Activity currentActivity, Button button, EditText[] editText){
        this.currentActivity = currentActivity;
        category_button = button;
        entries = new EditText[editText.length];
        for (int i = 0; i <editText.length; i++){
            entries[i] = editText[i];
        }
     }

    @Override
    public void onClick(View view) {
        if (currentActivity instanceof CategoryPageRandomize) {
            if (getLastNotEmpty(entries) >= 0) {
                resultString = entries[(int) ((Math.random()*getLastNotEmpty(entries)))].getText().toString();
                ((CategoryPageRandomize) currentActivity).startResultPageActivity(resultString);
            }
        }
    }

    public int getLastNotEmpty(EditText[] editTexts){
        int position=-1;
        for (int i = 0; i < editTexts.length; i++) {
            if(!editTexts[i].getText().toString().equals("")){
                position++;
            }
        }
        return position;
    }
}

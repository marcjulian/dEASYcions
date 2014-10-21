package de.deasycions.listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import de.deasycions.CategoryPage;
import de.deasycions.StartPage;

/**
 * @author Gary                 //TODO auskommentieren!!
 */
public class OnClickCategoryListener implements View.OnClickListener{
    private Activity currentActivity;
    Intent openCategoryPageRandomize;
    String buttonText;
    String categoryName;

    public OnClickCategoryListener(Activity currentActivity, Button category_button, String categoryName){
        buttonText = category_button.getText().toString();
        this.currentActivity = currentActivity;
        this.categoryName = categoryName;

    }

    @Override
    public void onClick(View view) {
        if (currentActivity instanceof CategoryPage) {
            ((CategoryPage) currentActivity).startCategoryPageRandomizeActivity(buttonText);
        }
    }


}
package de.deasycions.listener;

import android.app.Activity;
import android.view.View;

import de.deasycions.CategoryPageRandomize;
import de.deasycions.data.Category;

/**
 * @author Gary                 //TODO auskommentieren!!
 */
public class OnClickRandomListener implements View.OnClickListener {
    private Category currentCategory;
    private Activity currentActivity;

    public OnClickRandomListener(Activity currentActivity, Category currentCategory) {
        this.currentActivity = currentActivity;
        this.currentCategory = currentCategory;
    }

    @Override
    public void onClick(View view) {
        if (currentActivity instanceof CategoryPageRandomize) {
            int randomPosition = (int) (Math.random() * currentCategory.size());
            ((CategoryPageRandomize) currentActivity).startResultPageActivity(currentCategory.getEntry(randomPosition).getName());
        }
    }
}

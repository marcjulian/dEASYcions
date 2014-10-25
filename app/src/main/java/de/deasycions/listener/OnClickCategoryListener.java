package de.deasycions.listener;

import android.view.View;

import de.deasycions.interfaces.IdEASYcionsContent;

/**
 * @author Gary                 //TODO auskommentieren!!
 */
public class OnClickCategoryListener implements View.OnClickListener {
    private IdEASYcionsContent contentPage;
    private String categoryName;

    public OnClickCategoryListener(IdEASYcionsContent contentPage, String categoryName) {
        this.contentPage = contentPage;
        this.categoryName = categoryName;
    }

    @Override
    public void onClick(View view) {
        contentPage.startNextActivity(categoryName);
    }

}
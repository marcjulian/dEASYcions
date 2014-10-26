package de.deasycions.listener;

import android.view.View;

import de.deasycions.Page;
import de.deasycions.data.Category;
import de.deasycions.interfaces.IStartActivity;

/**
 * @author Gary                 //TODO auskommentieren!!
 */
public class OnClickRandomListener implements View.OnClickListener {
    private Category currentCategory;
    private IStartActivity contentPage;

    public OnClickRandomListener(IStartActivity contentPage, Category currentCategory) {
        this.contentPage = contentPage;
        this.currentCategory = currentCategory;
    }

    @Override
    public void onClick(View view) {
        int randomPosition = (int) (Math.random() * currentCategory.size());
        contentPage.startNextActivity(currentCategory.getEntry(randomPosition).getName(), Page.RANDOMIZE_PAGE);
    }
}

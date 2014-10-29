package de.deasycions.listener;

import android.view.View;

import de.deasycions.CategoryPageRandomize;
import de.deasycions.Page;
import de.deasycions.data.Category;
import de.deasycions.interfaces.IStartActivity;

/**
 * @author Gary                 //TODO auskommentieren!!
 */
public class OnClickRandomListener implements View.OnClickListener {

    private IStartActivity contentPage;

    public OnClickRandomListener(IStartActivity contentPage) {
        this.contentPage = contentPage;

    }

    @Override
    public void onClick(View view) {
        int randomPosition = (int) (Math.random() * getResult().size());
        contentPage.startNextActivity(getResult().getEntry(randomPosition).getName(), Page.RANDOMIZE_PAGE);
    }

    public Category getResult(){
        return CategoryPageRandomize.getResultAfterVoting();
    }
}

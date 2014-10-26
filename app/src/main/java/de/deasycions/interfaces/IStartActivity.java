package de.deasycions.interfaces;

import android.view.View;

import de.deasycions.Page;

/**
 * Define method to start the next activity.
 *
 * @author Marc Stammerjohann
 */
public interface IStartActivity {

    public void displayContent();

    public void startNextActivity(String contentName, Page page);

    public void deleteContent(View currentView);
}

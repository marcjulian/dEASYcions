package de.deasycions.interfaces;

/**
 * Define method to start the next activity.
 *
 * @author Marc Stammerjohann
 */
public interface IStartActivity {

    public void displayContent();

    public void startNextActivity(String contentName);

    public void deleteContent(String categoryName);
}

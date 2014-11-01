package de.deasycions;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import de.deasycions.customText.EasyText;
import de.deasycions.customText.MovingText;
import de.deasycions.data.CategoryStorage;

/**
 * Initializes and defines same methods for content pages only (e.g randomizepage)
 *
 * @author Marc Stammerjohann
 */
public abstract class ContentPage extends Activity {

    //Data
    protected CategoryStorage categoryStorage;
    //View
    protected TextView messageText;
    //Display
    protected float height;
    protected float width;
    protected float density;
    //Listener
    protected View.OnTouchListener editTextOnTouchListener;

    protected void initialize() {
        //Category-Section
        categoryStorage = CategoryStorage.getInstance();

        //Widget-Section
        messageText = (TextView) findViewById(R.id.ContainsMessage);

        //Display-Section
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        density = getResources().getDisplayMetrics().density;
        height = outMetrics.heightPixels;
        width = outMetrics.widthPixels;
    }

    public abstract void deleteContent(View currentView);

    public abstract void createContent(MovingText currentEasyText);

    public void startNextActivity(String result, Page page) {
        //do nothing, sub-class must override
    }

    public void startNextActivity(MovingText currentEasyText, Page page) {
        //do nothing, sub-class must override
    }

}

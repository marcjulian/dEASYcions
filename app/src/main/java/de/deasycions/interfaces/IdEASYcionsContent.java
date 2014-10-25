package de.deasycions.interfaces;

import android.os.IBinder;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Define method for the content pages of dEASYcions app, which uses the EditTexts around a Button.
 *
 * @author Marc Stammerjohann
 */
public interface IdEASYcionsContent extends IStartActivity {

    public void createContent(String newContentName);

    public void deleteContent(String contentName);

    public void showKeyboard(EditText currentEditText);

    public void hideKeyboard(IBinder windowToken);

    public String getDescription();

    public void displayInfoMessage(String infoMessage, TextView textView, String currentName);

    public boolean containsContent(String content);

    public void setNewContentName(String currentName, String newContentName);

}

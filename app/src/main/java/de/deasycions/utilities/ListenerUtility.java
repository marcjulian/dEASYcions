package de.deasycions.utilities;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Utility class for Listeners.
 *
 * @author Marc Stammerjohann
 */
public final class ListenerUtility {

    public static int editTextPosition;

    private ListenerUtility(){
        //this class has only static methods
    }

    /**
     * Returns the  position of the {@link android.widget.EditText} which was clicked.
     *
     * @param view
     * @return position
     */
    public static int getEditTextPosition(View view, EditText[] editText) {
        int id = view.getId();
        int counter = 0;
        while (editText[counter].getId() != id) {
            counter++;
        }
        return counter;
    }

    public static void setInfoTextMessage(TextView message, TextView textView, String infoMessage, String currentName){
        message.setText(infoMessage);
        message.setVisibility(View.VISIBLE);
        if (currentName == null) {
            textView.setText("");
            textView.setTextSize(50);
        } else {
            textView.setText(currentName);
        }
        //TODO hide message text after few seconds
        //message.setVisibility(View.INVISIBLE);
    }
}

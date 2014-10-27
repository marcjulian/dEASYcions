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

    //don't change the values of initial
    public static float initialXAxis;
    public static float initialYAxis;

    private ListenerUtility() {
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

    /**
     * Displays an info messages to the user.
     *
     * @param message     where the info message is displayed
     * @param infoMessage which is to be displayed
     */
    public static void setInfoTextMessage(final TextView message, String infoMessage) {
        message.setText(infoMessage);
        message.setVisibility(View.VISIBLE);
        message.postDelayed(new Runnable() {
            public void run() {
                message.setVisibility(View.INVISIBLE);
            }
        }, 3000);
    }

    /**
     * Checks if the given view is moving compared to its initial position.
     *
     * @param view
     * @return
     */
    public static boolean isViewMoving(View view) {
        float viewX = view.getX();
        float viewY = view.getY();
        //if they are same as the initialPosition, the view isn't moving
        if (viewX == ListenerUtility.initialXAxis && viewY == ListenerUtility.initialYAxis) {
            return false;
        }
        return true;
    }

}

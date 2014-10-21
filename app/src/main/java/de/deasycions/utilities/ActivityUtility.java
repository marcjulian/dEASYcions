package de.deasycions.utilities;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import de.deasycions.R;

/**
 * Utility class for Activities.
 *
 * @author Marc Stammerjohann
 */
public final class ActivityUtility {

    private ActivityUtility() {
        //this class has only static methods
    }

    /**
     * Initialize EditText-Array for the given activity class.
     *
     * @param currentActivity
     * @return
     */
    public static EditText[] createEditText(Activity currentActivity) {
        EditText[] editText = new EditText[6];
        editText[0] = (EditText) currentActivity.findViewById(R.id.etLU);
        editText[1] = (EditText) currentActivity.findViewById(R.id.etRU);
        editText[2] = (EditText) currentActivity.findViewById(R.id.etR);
        editText[3] = (EditText) currentActivity.findViewById(R.id.etRD);
        editText[4] = (EditText) currentActivity.findViewById(R.id.etLD);
        editText[5] = (EditText) currentActivity.findViewById(R.id.etL);
        return editText;
    }

    /**
     * Adding the given listeners to each editText field.
     *
     *
     * @param editText
     * @param onClickListener
     * @param onEditorActionListener
     * @param onLongClickListener
     */
    public static void addListenerToEditText(EditText[] editText, View.OnClickListener onClickListener, TextView.OnEditorActionListener onEditorActionListener, View.OnLongClickListener onLongClickListener) {
        for (int i = 0; i < editText.length; i++) {
            if (onClickListener != null) {
                editText[i].setOnClickListener(onClickListener);
            }
            if (onEditorActionListener != null) {
                editText[i].setOnEditorActionListener(onEditorActionListener);
            }
            if (onLongClickListener != null) {
                editText[i].setOnLongClickListener(onLongClickListener);
            }
        }
    }

}

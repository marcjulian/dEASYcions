package de.deasycions.utilities;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.deasycions.EditablePage;
import de.deasycions.R;
import de.deasycions.customText.EasyText;
import de.deasycions.listener.EditTextOnTouchListener;

/**
 * Utility class for Activities.
 *
 * @author Marc Stammerjohann
 */
public final class ActivityUtility {
    /**
     * Intent constant.
     */
    public final static String RESULT = "RESULT";

    /**
     * Intent constant.
     */
    public final static String CATEGORY_NAME = "CATEGORY.NAME";

    /**
     * Intent constant.
     */
    public final static String CATEGORY_POSITION = "CATEGORY.POSITION";

    private ActivityUtility() {
        //this class has only static methods
    }

    /**
     * Initialize EditText-Array for the given activity class.
     *
     * @param currentActivity
     * @return
     */
    public static EasyText[] createEasyText(Activity currentActivity) {
        EasyText.resetID();
        EasyText[] easyTexts = new EasyText[6];
        easyTexts[0] = (EasyText) currentActivity.findViewById(R.id.etLU);
        easyTexts[1] = (EasyText) currentActivity.findViewById(R.id.etRU);
        easyTexts[2] = (EasyText) currentActivity.findViewById(R.id.etR);
        easyTexts[3] = (EasyText) currentActivity.findViewById(R.id.etRD);
        easyTexts[4] = (EasyText) currentActivity.findViewById(R.id.etLD);
        easyTexts[5] = (EasyText) currentActivity.findViewById(R.id.etL);
        int length = easyTexts.length;
        for (int i = 0; i < length; i++) {
            easyTexts[i].setBefore(easyTexts[((i - 1) + length) % length]);
            easyTexts[i].setBehind(easyTexts[(i + 1) % length]);
        }
        return easyTexts;
    }

    /**
     * Adding the given listeners to each editText field.
     * EditTexts only need onclick and Editor Listener, when they are initialized.
     *
     * @param easyTexts
     * @param onClickListener
     * @param onEditorActionListener
     */
    public static void addListenerToEditText(EasyText[] easyTexts, View.OnClickListener onClickListener, TextView.OnEditorActionListener onEditorActionListener) {
        for (int i = 0; i < easyTexts.length; i++) {
            EditText currentEditText = easyTexts[i];
            if (onClickListener != null) {
                currentEditText.setOnClickListener(onClickListener);
            }
            if (onEditorActionListener != null) {
                currentEditText.setOnEditorActionListener(onEditorActionListener);
            }
        }
    }

    /**
     * Adding the given listeners to one editText field.
     * After initialized, when text is entered, new listeners are added.
     *
     * @param currentEasyText
     * @param secondOnClickListener
     * @param longHoldClickListener
     */
    public static void addListenerToEditText(EasyText currentEasyText, View.OnClickListener secondOnClickListener, View.OnLongClickListener longHoldClickListener, View.OnTouchListener onTouchListener) {
        if (secondOnClickListener != null) {
            currentEasyText.setOnClickListener(secondOnClickListener);
        }
        if (longHoldClickListener != null) {
            currentEasyText.setOnLongClickListener(longHoldClickListener);
        }
        if (onTouchListener != null) {
            currentEasyText.setOnTouchListener(onTouchListener);
        }
    }

    /**
     * Swaps the button and the selected category background color.
     *
     * @param button
     * @param easyTexts
     * @param currentCategoryPosition
     */
    public static void swapBackgroundColor(Button button, EasyText[] easyTexts, int currentCategoryPosition) {
        Drawable buttonBackground = button.getBackground();
        if (currentCategoryPosition == -1) {
            return;
        }
        Drawable editTextBackground = easyTexts[currentCategoryPosition].getBackground();

        //swap background
        button.setBackground(editTextBackground);
        easyTexts[currentCategoryPosition].setBackground(buttonBackground);
    }

    /**
     * It whether saves the new entered name or displays an info message.
     * It is used in the {@link de.deasycions.listener.ContentDoneEditorListener} and in the onTouch-Method in StartPage and CategoryPage.
     * It also is used when the keyboard is closed via toolbar
     *
     * @param contentPage
     * @param currentEasyText
     */
    //TODO call this when the keyboard is closed via toolbar
    public static void saveAtDoneClick(EditablePage contentPage, EasyText currentEasyText) {
        // hiding the keyboard
        contentPage.hideKeyboard(currentEasyText.getWindowToken());
        String currentName = currentEasyText.getCurrentName();
        String newContentName = currentEasyText.getNewName();
        if (!currentEasyText.verifyNewCategoryName(contentPage)) {
            if (currentName == null) {
                currentEasyText.setText("");
                currentEasyText.setTextSize(50);
            } else {
                currentEasyText.setText(currentName);
            }
        } else {
            currentEasyText.setCurrentName(newContentName);
            if (currentName == null) {
                contentPage.createContent(currentEasyText);
            } else {
                contentPage.setNewContentName(currentName, newContentName);
            }
        }
        //disabled editing of the text
        currentEasyText.setFocusableInTouchMode(false);
        currentEasyText.clearFocus();

    }
}

package de.deasycions.utilities;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.deasycions.ContentPage;
import de.deasycions.EditablePage;
import de.deasycions.R;
import de.deasycions.customText.EasyText;
import de.deasycions.customText.MovingText;

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
     * @param contentPage
     * @return
     */
    public static EasyText[] createEasyText(EditablePage contentPage) {
        EasyText[] easyTexts = new EasyText[6];
        easyTexts[0] = (EasyText) contentPage.findViewById(R.id.etLU);
        easyTexts[1] = (EasyText) contentPage.findViewById(R.id.etRU);
        easyTexts[2] = (EasyText) contentPage.findViewById(R.id.etR);
        easyTexts[3] = (EasyText) contentPage.findViewById(R.id.etRD);
        easyTexts[4] = (EasyText) contentPage.findViewById(R.id.etLD);
        easyTexts[5] = (EasyText) contentPage.findViewById(R.id.etL);
        int length = easyTexts.length;
        for (int i = 0; i < length; i++) {
            easyTexts[i].setPosition(i);
            easyTexts[i].setBefore(easyTexts[((i - 1) + length) % length]);
            easyTexts[i].setBehind(easyTexts[(i + 1) % length]);
        }
        return easyTexts;
    }

    /**
     * Initialize EditText-Array for the given activity class.
     *
     * @param contentPage
     * @return
     */
    public static MovingText[] createMovingText(ContentPage contentPage) {
        MovingText[] movingTexts = new MovingText[6];
        movingTexts[0] = (MovingText) contentPage.findViewById(R.id.etLU);
        movingTexts[1] = (MovingText) contentPage.findViewById(R.id.etRU);
        movingTexts[2] = (MovingText) contentPage.findViewById(R.id.etR);
        movingTexts[3] = (MovingText) contentPage.findViewById(R.id.etRD);
        movingTexts[4] = (MovingText) contentPage.findViewById(R.id.etLD);
        movingTexts[5] = (MovingText) contentPage.findViewById(R.id.etL);
        int length = movingTexts.length;
        for (int i = 0; i < length; i++) {
            movingTexts[i].setPosition(i);
            movingTexts[i].setBefore(movingTexts[((i - 1) + length) % length]);
            movingTexts[i].setBehind(movingTexts[(i + 1) % length]);
        }
        return movingTexts;
    }

    /**
     * Adding the given listeners to each editText field.
     * EditTexts only need onclick and Editor Listener, when they are initialized.
     *
     * @param editTexts
     * @param onClickListener
     * @param onEditorActionListener
     */
    public static void addListenerToEditText(EditText[] editTexts, View.OnClickListener onClickListener, TextView.OnEditorActionListener onEditorActionListener) {
        for (int i = 0; i < editTexts.length; i++) {
            EditText currentEasyText = editTexts[i];
            if (onClickListener != null) {
                currentEasyText.setOnClickListener(onClickListener);
            }
            if (onEditorActionListener != null) {
                currentEasyText.setOnEditorActionListener(onEditorActionListener);
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
    public static void addListenerToEditText(EditText currentEasyText, View.OnClickListener secondOnClickListener, View.OnLongClickListener longHoldClickListener, View.OnTouchListener onTouchListener) {
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
     * @param editTexts
     * @param currentCategoryPosition
     */
    public static void swapBackgroundColor(Button button, EditText[] editTexts, int currentCategoryPosition) {
        Drawable buttonBackground = button.getBackground();
        if (currentCategoryPosition == -1) {
            return;
        }
        Drawable editTextBackground = editTexts[currentCategoryPosition].getBackground();

        //swap background
        button.setBackground(editTextBackground);
        editTexts[currentCategoryPosition].setBackground(buttonBackground);
    }
}

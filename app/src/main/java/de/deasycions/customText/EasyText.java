package de.deasycions.customText;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

import de.deasycions.EditablePage;

/**
 * Custom view extends EditText.
 *
 * @author Marc Stammerjohann
 */
public class EasyText extends MovingText {

    private static EditablePage contentPage;
    private String currentName;

    public EasyText(Context context) {
        super(context);
    }

    public EasyText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EasyText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK &&
                event.getAction() == KeyEvent.ACTION_UP) {
            contentPage.saveAtDoneClick(this);
        }
        return super.onKeyPreIme(keyCode, event);
    }


    public static void setContentPage(EditablePage contentPage) {
        EasyText.contentPage = contentPage;
    }

    public void resetCurrentName() {
        this.currentName = null;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }

    public String getCurrentName() {
        return currentName;
    }

    public String getNewName() {
        return getText().toString();
    }
}

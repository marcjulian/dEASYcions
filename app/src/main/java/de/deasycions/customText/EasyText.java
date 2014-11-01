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
public class EasyText extends EditText {

    private static EditablePage contentPage;
    private String currentName;
    private int position;

    private EasyText before;
    private EasyText behind;

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

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
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

    public void setBefore(EasyText before) {
        this.before = before;
    }

    public EasyText getBefore() {
        return before;
    }

    public void setBehind(EasyText behind) {
        this.behind = behind;
    }

    public EasyText getBehind() {
        return behind;
    }
}

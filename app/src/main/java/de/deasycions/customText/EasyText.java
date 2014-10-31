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

    public static void setContentPage(EditablePage contentPage) {
        EasyText.contentPage = contentPage;
    }

    public static void resetContentPage() {
        EasyText.contentPage = null;
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

    public int getPosition() {
        return position;
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


    /**
     * Verifies if the text is allowed for a new {@link de.deasycions.data.Category}.
     * Also specifies the info message.
     *
     * @return boolean whether the text is allowed or not
     */
    public boolean verifyNewCategoryName(EditablePage contentPage) {
        String newContentName = getNewName();
        String infoMessage = "";
        boolean verified = true;
        if (newContentName.equals("")) {
            infoMessage = String.format("Please enter a %s name!", contentPage.getDescription());
            verified = false;
        } else if (currentName != null) {
            if (currentName.toLowerCase().equals(newContentName.toLowerCase())) {
                verified = false;
            }
        } else if (contentPage.containsContent(newContentName)) {
            infoMessage = String.format("The %s %s already exists!", contentPage.getDescription(), newContentName);
            verified = false;
        }
        contentPage.displayInfoMessage(infoMessage);
        return verified;
    }

}

package de.deasycions.customText;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * @author Marc Stammerjohann
 */
public class MovingText extends EditText{

    //TODO add radius, alpha, initial x and y

    private int position;

    private EditText before;
    private EditText behind;

    public MovingText(Context context) {
        super(context);
    }

    public MovingText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MovingText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setBefore(EditText before) {
        this.before = before;
    }

    public EditText getBefore() {
        return before;
    }

    public void setBehind(EditText behind) {
        this.behind = behind;
    }

    public EditText getBehind() {
        return behind;
    }
}

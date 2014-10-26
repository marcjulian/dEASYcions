package de.deasycions.listener;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import de.deasycions.StartPage;
import de.deasycions.interfaces.IStartActivity;
import de.deasycions.utilities.ListenerUtility;

/**
 * Reacts on the touch gesture on the screen. It recognizes when it touch down, move and up.
 * It draws the editText at a new position.
 * Also it has to check, if it either close to trash or center button.
 *
 * @author Marc Stammerjohann
 */
public class EditTextOnTouchListener implements View.OnTouchListener {
    private final Button button;
    private String currentButtonName;
    private final String tempButtonName;
    private final ImageView trashView;

    private float lastXAxis;
    private float lastYAxis;

    private float currentXAxis;
    private float currentYAxis;

    private float Height;
    private float Width;
    private float density;

    private IStartActivity contentPage;


    public EditTextOnTouchListener(Button button, String tempButtonName, ImageView trashView, float Height, float Width, float density,IStartActivity contentPage ) {
        this.button = button;
        if(button != null) {
            currentButtonName = button.getText().toString();
        }
        this.tempButtonName = tempButtonName;
        this.trashView = trashView;
        this.Width = Width;
        this.Height=Height;
        this.density = density;
        this.contentPage = contentPage;



    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        boolean handled = false;
        int performedAction = event.getAction();
        float viewX = view.getX();
        float viewY = view.getY();
        float mouseX = event.getX();
        float mouseY = event.getY();

        switch (performedAction) {
            case MotionEvent.ACTION_DOWN:
                ListenerUtility.initialXAxis = viewX;
                ListenerUtility.initialYAxis = viewY;
                lastXAxis = mouseX;
                lastYAxis = mouseY;
                currentXAxis = viewX;
                currentYAxis = viewY;


                //handled = true; if return false onclick and longclick is activated
                break;
            case MotionEvent.ACTION_MOVE:
                currentXAxis += mouseX - lastXAxis;
                currentYAxis += mouseY - lastYAxis;
//TODO ersetze 170 mit höhe Textview + Schriftgröße im Textview + PaddingTop
                if (currentYAxis < 170) {
                    currentYAxis = 170;
                }
//TODO ersetze 100 durch Höhe editTextkugeln
                if (currentYAxis > Height-(100*density+5)) {
                    currentYAxis = Height-(100*density+5);
                }

                if (currentXAxis < 5) {
                    currentXAxis = 5;
                }

                if (currentXAxis > Width-(100*density+5)) {
                    currentXAxis = Width-(100*density+5);
                }
                view.setX(currentXAxis);
                view.setY(currentYAxis);

                if (button != null) {
                    button.setText(tempButtonName);
                }
                if (trashView != null) {
                    trashView.setVisibility(View.VISIBLE);

                }
                // the other listeners won't react
                handled = true;
                break;
            case MotionEvent.ACTION_UP:
                //if it the edit text didn't end up on the trash or randomize button, reset it to the initial position
                //TODO listen for hovering over trash or middle button
                //middle: start Activity CategoryPageRandomize
                //trash: delete hovering category
                view.setX(ListenerUtility.initialXAxis);
                view.setY(ListenerUtility.initialYAxis);
                //TODO deleteContent does not get called
                if(Math.abs(currentXAxis-trashView.getX())<100  && Math.abs(currentYAxis-trashView.getY())<100){
                contentPage.deleteContent(((EditText) view).getText().toString());
                }

                if (button != null) {
                    button.setText(currentButtonName);
                }
                if (trashView != null) {
                    trashView.setVisibility(View.INVISIBLE);
                }
                break;
            default:

                break;
        }
        // if return true the other listeners are not called
        return handled;
    }
}
package de.deasycions.listener;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import de.deasycions.utilities.ActivityUtility;
import de.deasycions.utilities.ListenerUtility;

/**
 * @author Marc Stammerjohann
 */
public class EditTextOnTouchListener implements View.OnTouchListener {
    private final Button button;
    private String currentButtonName;
    private final String tempButtonName;
    private final ImageView trashView;

    //don't change the values of initial
    private float initialXAxis;
    private float initialYAxis;

    private float lastXAxis;
    private float lastYAxis;

    private float currentXAxis;
    private float currentYAxis;

    public  EditTextOnTouchListener (Button button, String tempButtonName, ImageView trashView){
        this.button = button;
        this.tempButtonName = tempButtonName;
        this.trashView = trashView;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int performedAction = event.getAction();
        float viewX = view.getX();
        float viewY = view.getY();
        float mouseX = event.getX();
        float mouseY = event.getY();
        switch (performedAction) {
            case MotionEvent.ACTION_DOWN:
                initialXAxis = viewX;
                initialYAxis = viewY;
                lastXAxis = mouseX;
                lastYAxis = mouseY;
                currentXAxis = viewX;
                currentYAxis = viewY;

                if(button != null) {
                    currentButtonName = button.getText().toString();
                    button.setText(tempButtonName);
                }
                if(trashView != null){
                    trashView.setVisibility(View.VISIBLE);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                currentXAxis += mouseX - lastXAxis;
                currentYAxis += mouseY - lastYAxis;
                //TODO can not move to the info message text field, calculate the width of the info message text and the circle radius
                if (currentYAxis < 100) {
                    currentYAxis = 100;
                }
                // TODO can not move to the bottom edge of the screen, calculate radius of the circle and the width of the screen
                if(currentYAxis > 980){
                    currentYAxis = 980;
                }
                // TODO can not move to the right edge of the screen, calculate radius of the circle and the width of the screen
                if(currentXAxis < 5){
                    currentXAxis = 5;
                }
                // TODO can not move to the left edge of the screen, calculate radius of the circle and the width of the screen
                if(currentXAxis > 560){
                    currentXAxis = 560;
                }
                view.setX(currentXAxis);
                view.setY(currentYAxis);
                break;
            case MotionEvent.ACTION_UP:
                //if it the edit text didn't end up on the trash or randomize button, reset it to the initial position
                view.setX(initialXAxis);
                view.setY(initialYAxis);

                if(button != null) {
                    button.setText(currentButtonName);
                }
                if(trashView != null){
                    trashView.setVisibility(View.INVISIBLE);
                }
                break;
            default:

                break;
        }
        // if return true the other listeners are not called
        return false;
    }
}
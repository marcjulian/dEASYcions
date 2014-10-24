package de.deasycions.listener;

import android.view.MotionEvent;
import android.view.View;

/**
 * @author Marc Stammerjohann
 */
public class EditTextOnTouchListener implements View.OnTouchListener {

    private float firstXAxis = 0;
    private float firstYAxis = 0;

    private float lastXAxis;
    private float lastYAxis;

    private float currentXAxis;
    private float currentYAxis;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int performedAction = event.getAction();
        switch (performedAction) {
            case MotionEvent.ACTION_DOWN:
                if(firstXAxis == 0){
                    firstXAxis = view.getX();
                }
                if(firstYAxis == 0){
                    firstYAxis = view.getY();
                }
                lastXAxis = currentXAxis = event.getX();
                lastYAxis = currentYAxis = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                currentXAxis += event.getX() - lastXAxis;
                currentYAxis += event.getY() - lastYAxis;

                view.setX(currentXAxis);
                view.setY(currentYAxis);
                break;
            case MotionEvent.ACTION_UP:
                view.setX(firstXAxis);
                view.setY(firstYAxis);
                break;
            default:

                break;
        }
        return true;
    }
}

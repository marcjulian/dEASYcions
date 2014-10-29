package de.deasycions.listener;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import de.deasycions.EditablePage;
import de.deasycions.Page;
import de.deasycions.utilities.ListenerUtility;

/**
 * Reacts on the touch gesture on the screen. It recognizes when it touch down, move and up.
 * It draws the editText at a new position.
 * Also it has to check, if it either close to trash or center button.
 *
 * @author Marc Stammerjohann
 */
public class EditTextOnTouchListener implements View.OnTouchListener {
    private EditablePage contentPage;
    private final Button button;
    private String currentButtonName;
    private final String tempButtonName;
    private final ImageView trashView;

    private float lastXAxis;
    private float lastYAxis;

    private float currentXAxis;
    private float currentYAxis;

    private float height;
    private float width;
    private float density;

    public EditTextOnTouchListener(EditablePage contentPage, Button button, String tempButtonName, ImageView trashView, float height, float width, float density) {
        this.contentPage = contentPage;
        this.button = button;
        if (button != null) {
            currentButtonName = button.getText().toString();
        }
        this.tempButtonName = tempButtonName;
        this.trashView = trashView;
        this.width = width;
        this.height = height;
        this.density = density;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        boolean handled = false;
        int performedAction = event.getAction();
        //view
        float viewX = view.getX();
        float viewY = view.getY();
        float viewRadius = view.getHeight() / 2;

        //mouse
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
                //if (currentYAxis > height - (viewRadius * density + 5)) {
                if (currentYAxis > height - (100 * density + 5)) {
                    currentYAxis = height - (100 * density + 5);
                }

                if (currentXAxis < 5) {
                    currentXAxis = 5;
                }

                //if (currentXAxis > width - (viewRadius * density + 5)) {
                if (currentXAxis > width - (100 * density + 5)) {
                    currentXAxis = width - (100 * density + 5);
                }
                view.setX(currentXAxis);
                view.setY(currentYAxis);

                if (button != null) {
                    button.setText(tempButtonName);
                    if (isViewAboveButton(view)) {
                            view.performHapticFeedback(1);
                    }
                }
                if (trashView != null) {
                    trashView.setVisibility(View.VISIBLE);
                    if (isViewAboveTrash(view)) {
                        view.performHapticFeedback(1);
                    }
                }
                // the other listeners won't react
                handled = true;
                break;
            case MotionEvent.ACTION_UP:
                if (button != null) {
                    if (isViewAboveButton(view)) {
                        if (view instanceof EditText) {
                            view.performHapticFeedback(1);
                            contentPage.startNextActivity(((EditText) view).getText().toString(), Page.RANDOMIZE_PAGE);
                            handled = true;
                        }
                    }
                    button.setText(currentButtonName);
                }
                if (trashView != null) {
                    if (isViewAboveTrash(view)) {
                        if (view instanceof EditText) {
                            contentPage.deleteContent(view);
                            handled = true;
                        }
                    }
                    trashView.setVisibility(View.INVISIBLE);
                }
                //reset view to its initial position
                view.setX(ListenerUtility.initialXAxis);
                view.setY(ListenerUtility.initialYAxis);
                break;
            default:
                break;
        }
        // if return true the other listeners are not called
        return handled;
    }

    private boolean isViewAboveButton(View view) {
        final Rect buttonRect = createButtonRect();

        float viewX = view.getX();
        float viewY = view.getY();
        return buttonRect.contains((int) viewX, (int) viewY);

    }


    public boolean isViewAboveTrash(View view) {
        final Rect imageTrash = createTrashRect();

        float viewX = view.getX();
        float viewY = view.getY();
        return imageTrash.contains((int) viewX, (int) viewY);
    }

    private Rect createButtonRect() {
        float buttonX = button.getX();
        float buttonY = button.getY();
        float buttonRange = button.getHeight()/4;

        return new Rect((int) (buttonX - buttonRange), (int)
                (buttonY - buttonRange), (int) (buttonX + buttonRange), (int) (buttonY + buttonRange));
    }

    private Rect createTrashRect() {
        float trashX = trashView.getX();
        float trashY = trashView.getY();
        float buttonHalfHeight = trashView.getHeight();
        float buttonHalfWidth = trashView.getWidth();

        return new Rect((int) (trashX - buttonHalfWidth), (int) (trashY - buttonHalfHeight), (int) (trashX + buttonHalfWidth), (int) (trashY + buttonHalfHeight));
    }
}
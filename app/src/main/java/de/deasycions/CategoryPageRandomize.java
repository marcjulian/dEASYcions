package de.deasycions;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import de.deasycions.customText.EasyText;
import de.deasycions.customText.MovingText;
import de.deasycions.data.Category;
import de.deasycions.listener.EditTextOnTouchListener;
import de.deasycions.listener.OnClickRandomListener;
import de.deasycions.utilities.ActivityUtility;
import de.deasycions.utilities.ListenerUtility;

import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;


/**
 * @author Gary         //TODO auskommentieren!!
 */
public class CategoryPageRandomize extends ContentPage {

    private MovingText[] movingText;
    private String[] entries;
    private Category currentCategory;
    private Button randomize;
    private String categoryName;
    private ImageView countdown;
    private ImageView downvote;
    private ImageView upvote;
    private static Category resultAfterVoting;
    //swaping color of the button to the selected category
    private int currentCategoryPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page_random);
        initialize();
        displayContent();
        ActivityUtility.swapBackgroundColor(randomize, movingText, currentCategoryPosition);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    protected void initialize() {
        super.initialize();
        //Intent-Section
        Intent intent = getIntent();
        categoryName = intent.getStringExtra(ActivityUtility.CATEGORY_NAME);
        currentCategoryPosition = intent.getIntExtra(ActivityUtility.CATEGORY_POSITION, -1);
        //Category-Section
        currentCategory = categoryStorage.getCategory(categoryName);
        entries = new String[currentCategory.size()];
        for (int i = 0; i < currentCategory.size(); i++) {
            entries[i] = currentCategory.getEntry(i).getName();
        }
        //Widget-Section
        randomize = (Button) findViewById(R.id.random);
        movingText = ActivityUtility.createMovingText(this);
        //TODO up- and downvote image
        downvote = (ImageView) findViewById(R.id.downvote);
        upvote = (ImageView) findViewById(R.id.upvote);

        //Listener-Section
        randomize.setOnClickListener(new OnClickRandomListener(this));
        editTextOnTouchListener = new EditTextOnTouchListener(this, null, categoryName, downvote,upvote, height, width, density);

        //Result-Section
        resultAfterVoting = createResultCategoryFromEntries(entries);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        resultAfterVoting = null;
    }

    public void displayContent() {
        int size = currentCategory.size();
        for (int i = 0; i < size; i++) {
            EditText currentEditText = movingText[i];
            currentEditText.setVisibility(View.VISIBLE);
            movingText[i].setText(currentCategory.getEntry(i).getName());
            ActivityUtility.addListenerToEditText(movingText[i], null, null, editTextOnTouchListener);
        }
    }

    public void startNextActivity(String result, Page page) {
        setContentView(R.layout.countdown_animation);
        countdown = (ImageView) findViewById(R.id.countdownImageView);
        countdown.setBackgroundResource(R.drawable.animation_countdown_drawable);
        final AnimationDrawable animationDrawable = (AnimationDrawable) countdown.getBackground();

        final Intent intent = new Intent(this, ResultPage.class);
        intent.putExtra(ActivityUtility.RESULT, result);

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {

            public void run() {
                startActivity(intent);
            }
        };
        animationDrawable.start();
        handler.postDelayed(runnable, 2250);
    }

    /**
     * Downvote
     *
     * @param currentView
     */
    public void deleteContent(View currentView) {
        if (currentView instanceof MovingText) {
            MovingText currentMovingText = (MovingText) currentView;
            if (lastPositionEntry(currentMovingText.getNewName()) >= 0) {
                entries = deleteFromEntries(lastPositionEntry(currentMovingText.getNewName()));
            }
        }
        resultAfterVoting = createResultCategoryFromEntries(entries);
    }


    /**
     * Upvote
     *
     * @param currentEasyText
     */
    public void createContent(MovingText currentEasyText) {
        String newContentName = currentEasyText.getNewName();
        String[] temp = new String[entries.length + 1];
        for (int i = 0; i < entries.length; i++) {
            temp[i] = entries[i];
        }
        temp[temp.length - 1] = newContentName;
        entries = temp;

        resultAfterVoting = createResultCategoryFromEntries(entries);
    }

    private int lastPositionEntry(String entryName) {
        int position = -1;
        for (int i = 0; i < entries.length; i++) {
            if (entryName.equals(entries[i])) {
                position = i;
            }
        }
        return position;
    }

    private String[] deleteFromEntries(int position) {

        if (entries.length <= 1) {
            ListenerUtility.setInfoTextMessage(messageText, getString(R.string.random_page_error_downvote));
            return entries;
        } else {
            String[] temp = new String[entries.length - 1];
            for (int i = 0; i < position; i++) {
                temp[i] = entries[i];
            }
            for (int j = position; j < temp.length; j++) {
                temp[j] = entries[j + 1];
            }
            return temp;
        }
    }

    private Category createResultCategoryFromEntries(String[] entries) {
        Category result = new Category(ActivityUtility.RESULT);
        for (int i = 0; i < entries.length; i++) {
            result.addEntry(entries[i]);
        }
        return result;
    }

    public static Category getResultAfterVoting() {
        return resultAfterVoting;
    }

}

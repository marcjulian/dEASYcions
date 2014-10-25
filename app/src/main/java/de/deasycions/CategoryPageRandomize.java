package de.deasycions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.deasycions.data.Category;
import de.deasycions.data.CategoryStorage;
import de.deasycions.interfaces.IStartActivity;
import de.deasycions.listener.OnClickRandomListener;
import de.deasycions.utilities.ActivityUtility;

import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;


/**
 * @author Gary         //TODO auskommentieren!!
 */
public class CategoryPageRandomize extends Activity implements IStartActivity {

    private EditText[] editText;
    private CategoryStorage categoryStorage;
    private Category currentCategory;
    private Button randomize;
    private String categoryName;
    private TextView categoryTextView;
    private ImageView countdown;
    //swaping color of the button to the selected category
    private int currentCategoryPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page_random);
        initialize();
        displayContent();
        ActivityUtility.swapBackgroundColor(randomize, editText, currentCategoryPosition);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void initialize() {
        //Intent-Section
        Intent intent = getIntent();
        categoryName = intent.getStringExtra(ActivityUtility.CATEGORY_NAME);
        currentCategoryPosition = intent.getIntExtra(ActivityUtility.CATEGORY_POSITION, -1);
        //Category-Section
        categoryStorage = CategoryStorage.getInstance();
        currentCategory = categoryStorage.getCategory(categoryName);
        //Widget-Section
        randomize = (Button) findViewById(R.id.random);
        categoryTextView = (TextView) findViewById(R.id.displayCategoryName);
        categoryTextView.setText(categoryName);
        editText = ActivityUtility.createEditText(this);
        //Listener-Section
        randomize.setOnClickListener(new OnClickRandomListener(this, currentCategory));
    }

    public void displayContent() {
        int size = currentCategory.size();
        for (int i = 0; i < size; i++) {
            EditText currentEditText = editText[i];
            currentEditText.setVisibility(View.VISIBLE);
            editText[i].setText(currentCategory.getEntry(i).getName());
        }
    }

    private void startResultPageActivity(String result) {
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

    public void startNextActivity(String contentName) {
        startResultPageActivity(contentName);
    }

}

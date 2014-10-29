package de.deasycions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.deasycions.data.Category;
import de.deasycions.data.CategoryStorage;
import de.deasycions.interfaces.IStartActivity;
import de.deasycions.listener.EditTextOnTouchListener;
import de.deasycions.listener.OnClickRandomListener;
import de.deasycions.utilities.ActivityUtility;

import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;


/**
 * @author Gary         //TODO auskommentieren!!
 */
public class CategoryPageRandomize extends EditablePage implements IStartActivity {

    private EditText[] editText;
    private CategoryStorage categoryStorage;
    private String[] entries;
    private Category currentCategory;
    private Button randomize;
    private String categoryName;
    private TextView categoryTextView;
    private ImageView countdown;
    private ImageView trashView;
    private static Category resultAfterVoting;
    //swaping color of the button to the selected category
    private int currentCategoryPosition;
    private View.OnTouchListener editTextOnTouchListener;



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

    protected void initialize() {

        //Intent-Section
        Intent intent = getIntent();
        categoryName = intent.getStringExtra(ActivityUtility.CATEGORY_NAME);
        currentCategoryPosition = intent.getIntExtra(ActivityUtility.CATEGORY_POSITION, -1);
        //Category-Section
        categoryStorage = CategoryStorage.getInstance();
        currentCategory = categoryStorage.getCategory(categoryName);
        entries = new String[currentCategory.size()];

        for(int i = 0; i < currentCategory.size();i++){
            entries[i] = currentCategory.getEntry(i).getName();
        }


        //Widget-Section
        randomize = (Button) findViewById(R.id.random);
        categoryTextView = (TextView) findViewById(R.id.displayCategoryName);
        categoryTextView.setText(categoryName);
        editText = ActivityUtility.createEditText(this);
        this.trashView = (ImageView) findViewById(R.id.trash);

        //Display-Section
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        density = getResources().getDisplayMetrics().density;
        height = outMetrics.heightPixels;
        width = outMetrics.widthPixels;

        //Listener-Section
        randomize.setOnClickListener(new OnClickRandomListener(this));
        editTextOnTouchListener = new EditTextOnTouchListener(this,randomize,"Upvote", trashView, height, width, density);

    }

    @Override
    public boolean containsContent(String content) {
        return false;
    }

    @Override
    public void setNewContentName(String currentName, String newContentName) {

    }

    public void displayContent() {
        int size = currentCategory.size();
        for (int i = 0; i < size; i++) {
            EditText currentEditText = editText[i];
            currentEditText.setVisibility(View.VISIBLE);
            editText[i].setText(currentCategory.getEntry(i).getName());
            ActivityUtility.addListenerToEditText(editText[i],null,null,editTextOnTouchListener);
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

    public void deleteContent(View currentView) {     //Downvote
        if(currentView instanceof EditText){
            EditText currentEditText = (EditText) currentView;
            if(lastPositionEntry(currentEditText.getText().toString())>=0){
                entries = deleteFromEntries(lastPositionEntry(currentEditText.getText().toString()));
            }
        }resultAfterVoting = createResultCategoryFromEntries(entries);
    }

    @Override
    public void createContent(String newContentName) { //Upvote
        String[] temp = new String[entries.length+1];
        for(int i=0;i<entries.length;i++){
            temp[i]= entries[i];
        }
        temp[temp.length-1] = newContentName;
        entries = temp;

        resultAfterVoting = createResultCategoryFromEntries(entries);
    }

    private int lastPositionEntry(String entryname){
        int position = -1;
        for(int i = 0; i < entries.length;i++){
            if(entryname.equals(entries[i])){
                position = i;

            }
        }
        return position;
    }

    private String[] deleteFromEntries(int position){
        String[] temp = new String[entries.length-1];
        for (int i = 0;i < position;i++){
            temp[i] = entries[i];
        }
        for(int j = position;j<temp.length;j++){
            temp[j] = entries[j+1];
        }

        return temp;
    }

    private Category createResultCategoryFromEntries(String[] entries){
        Category result = new Category("Result");

        for(int i = 0; i< entries.length;i++){
            result.addEntry(entries[i]);
        }

        return result;
    }

    public static Category getResultAfterVoting() {
        return resultAfterVoting;
    }

}

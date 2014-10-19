package de.deasycions;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import de.deasycions.data.CategoryStorage;

/**
 * @author Gary Grossgarten
 */
public class SplashScreen extends Activity {

    private CategoryStorage categoryStorage;

    private static final String filename = "CategoryStorage";
    private SharedPreferences savedData;
    private static final String CATEGORY = "CATEGORY";
    private static final String CATEGORY_NOT_EXIST = "CATEGORY NOT EXIST";
    private static final String ENTRY_NOT_EXIST = "ENTRY NOT EXIST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        categoryStorage = CategoryStorage.getInstance();
        savedData = getSharedPreferences(filename, MODE_PRIVATE);
        loadData();
        Thread timer = new Thread(){
            public void run(){
                try{
                   sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally{

                    Intent intent = new Intent("de.deasycions.StartPage");
                    startActivity(intent);
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    public void loadData(){
        int counterCategory = 0;
        savedData = getSharedPreferences(filename, MODE_PRIVATE);
        String categoryName = savedData.getString(CATEGORY + counterCategory, CATEGORY_NOT_EXIST);
        while(!categoryName.equals(CATEGORY_NOT_EXIST)){
            categoryStorage.createCategory(categoryName);
            int counterEntry = 0;
            String entryName = savedData.getString(categoryName + counterEntry, ENTRY_NOT_EXIST);
            while (!entryName.equals(ENTRY_NOT_EXIST)){
                categoryStorage.getCategory(categoryName).addEntry(entryName);
                counterEntry++;
                entryName = savedData.getString(categoryName + counterEntry, ENTRY_NOT_EXIST);
            }
            counterCategory++;
            categoryName = savedData.getString(CATEGORY + counterCategory, CATEGORY_NOT_EXIST);
        }
    }


}

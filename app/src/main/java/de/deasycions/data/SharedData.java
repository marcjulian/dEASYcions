package de.deasycions.data;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

/**
 * @author Marc Stammerjohann
 */
public class SharedData {

    private CategoryStorage categoryStorage;

    private static final String filename = "CategoryStorage";
    private SharedPreferences savedData;
    private static final String CATEGORY = "CATEGORY";
    private static final String CATEGORY_NOT_EXIST = "CATEGORY NOT EXIST";
    private static final String ENTRY_NOT_EXIST = "ENTRY NOT EXIST";

    private Activity currentActivity;

    public SharedData(Activity currentActivity){
        this.currentActivity = currentActivity;
        this.categoryStorage = CategoryStorage.getInstance();
        savedData = currentActivity.getSharedPreferences(filename, currentActivity.MODE_PRIVATE);
    }

    public void loadData(){
        int counterCategory = 0;
        savedData = currentActivity.getSharedPreferences(filename, currentActivity.MODE_PRIVATE);
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

    public void saveData(){
        int counter = 0;
        SharedPreferences.Editor editor = savedData.edit();
        Set<Map.Entry<String, Category>> categorySet = categoryStorage.getCategorySet();
        for(Map.Entry<String, Category> entry : categorySet){
            Category currentCategory = entry.getValue();
            String categoryName = currentCategory.getName();
            editor.putString(CATEGORY + counter, categoryName);
            for (int i = 0; i < currentCategory.size(); i++){
                editor.putString(categoryName + i, currentCategory.getEntry(i).getName());
            }
            counter++;
        }
        editor.commit();
    }

    public void clearSharedPreferences(){
        SharedPreferences.Editor editor = savedData.edit();
        editor.clear();
        editor.commit();
    }

}

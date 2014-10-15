package de.deasycions.input;

import java.util.HashMap;

/**
 * Created by marc on 15.10.2014.
 */
public class CategoryStorage {

    private HashMap<String, Category> savedCategories;

    private static CategoryStorage instance;

    private CategoryStorage() {
        savedCategories = new HashMap<String, Category>();
    }

    public static CategoryStorage getInstance(){
        if(CategoryStorage.instance == null){
            CategoryStorage.instance = new CategoryStorage();
        }
        return CategoryStorage.instance;
    }

    public void addCategory(Category category){
        savedCategories.put(category.getName(),category);
    }

    public Category getCategory(String name){
        return savedCategories.get(name);
    }
}

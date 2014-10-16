package de.deasycions.input;

import java.util.HashMap;

/**
 * @author Marc Stammerjohann
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
        //TODO use the new method if the categoryName already exists
        if(getCategory(category.getName()) == null) {
            savedCategories.put(category.getName(), category);
        }
    }

    public Category getCategory(String name){
        return savedCategories.get(name);
    }

    //TODO new method which can tell, if a categoryName already exist or not
}

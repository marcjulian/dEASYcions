package de.deasycions.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Marc Stammerjohann
 */
public class CategoryStorage {

    private HashMap<String, Category> savedCategories;

    private static CategoryStorage instance;

    private CategoryStorage() {
        savedCategories = new HashMap<String, Category>();    }

    public static CategoryStorage getInstance(){
        if(CategoryStorage.instance == null){
            CategoryStorage.instance = new CategoryStorage();
        }
        return CategoryStorage.instance;
    }

    public void createCategory(String categoryName){
        Category category = new Category(categoryName);
        //TODO use the new method if the categoryName already exists
        if(getCategory(categoryName) == null) {
            savedCategories.put(category.getName(), category);
        }
    }

    public boolean isEmpty(){
        return savedCategories.isEmpty();
    }

    public Category getCategory(String name){
        return savedCategories.get(name);
    }


    public Set<Map.Entry<String, Category>> getCategorySet(){
       return savedCategories.entrySet();
    }

    //TODO new method which can tell, if a categoryName already exist or not

}

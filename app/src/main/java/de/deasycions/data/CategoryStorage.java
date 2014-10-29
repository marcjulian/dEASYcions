package de.deasycions.data;

import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * {@link de.deasycions.data.CategoryStorage} is a singleton, which stores the categories in a map.
 * Every class can only get access to the same map.
 *
 * @author Marc Stammerjohann
 */
public class CategoryStorage {

    private LinkedHashMap<String, Category> savedCategories;

    private static CategoryStorage instance;

    private CategoryStorage() {
        savedCategories = new LinkedHashMap<String, Category>();
    }

    public static CategoryStorage getInstance() {
        if (CategoryStorage.instance == null) {
            CategoryStorage.instance = new CategoryStorage();
        }
        return CategoryStorage.instance;
    }

    /**
     * It is Case-insensitivity.
     *
     * @param categoryName
     */
    public void createCategory(String categoryName) {
        Category category = new Category(categoryName);
        addCategory(category);
    }

    private void addCategory(Category category){
        savedCategories.put(category.getName().toLowerCase(), category);
    }

    public void deleteCategory(String categoryName) {
        if (containsCategory(categoryName)) {
            savedCategories.remove(categoryName.toLowerCase());
        }
    }

    public boolean isEmpty() {
        return savedCategories.isEmpty();
    }

    public int size(){
        return savedCategories.size();
    }

    /**
     * It is Case-insensitivity.
     *
     * @param name
     * @return
     */
    public Category getCategory(String name) {
        return savedCategories.get(name.toLowerCase());
    }

    public Collection<Category> getCategoryValues() {
        return savedCategories.values();
    }

    /**
     * It is Case-insensitivity.
     *
     * @param categoryName
     * @return
     */
    public boolean containsCategory(String categoryName) {
        return savedCategories.containsKey(categoryName.toLowerCase());
    }

    public void setNewCategoryName(String currentName, String newCategoryName) {
        Category category = getCategory(currentName);
        category.changeCategoryName(newCategoryName);
        addCategory(category);
        deleteCategory(currentName);
    }

}

package de.deasycions.data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
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
     * @param categoryName
     */
    public void createCategory(String categoryName) {
        Category category = new Category(categoryName);
        savedCategories.put(category.getName().toLowerCase(), category);
    }

    public void deleteCategory(String categoryName) {
        Category category = new Category(categoryName);
        if (getCategory(categoryName) != null) {
            savedCategories.remove(category);
        }
    }

    public boolean isEmpty() {
        return savedCategories.isEmpty();
    }

    /**
     * It is Case-insensitivity.
     * @param name
     * @return
     */
    public Category getCategory(String name) {
        return savedCategories.get(name.toLowerCase());
    }


    public Set<Map.Entry<String, Category>> getCategorySet() {
        return savedCategories.entrySet();
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
    }
}

package de.deasycions.random;

import de.deasycions.input.Category;

/**
 *
 *
 * @author Marc Stammerjohann
 */
public class Deasycions {

    public Category.Entry random(Category category){
        int random = (int)(Math.random() * category.size());
        return category.getEntry(random);
    }



}

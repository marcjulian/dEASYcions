package de.deasycions.random;

import de.deasycions.input.Category;
import de.deasycions.input.EmptyCategoryException;

/**
 *
 *
 * @author Marc Stammerjohann
 */
public class Deasycions {

    public Category.Entry random(Category category) throws EmptyCategoryException {
        if (category.isEmpty()) {
            throw new EmptyCategoryException();
        }
        int random = (int) (Math.random() * category.size());
        return category.getEntry(random);
    }
}

package de.deasycions.random;

import de.deasycions.data.Category;
import de.deasycions.data.EmptyCategoryException;

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

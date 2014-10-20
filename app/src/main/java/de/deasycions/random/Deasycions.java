package de.deasycions.random;

import java.util.Random;

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

        Random random = new Random();
        return category.getEntry( random.nextInt(category.size()));
    }
}

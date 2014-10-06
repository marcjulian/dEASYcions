package de.deasycions.input;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import de.deasycions.random.Deasycions;

/**
 * @author Marc Stammerjohann
 */
public class CategoryTest {

    @Test
    public void testCategory() {
        String foodName = "Food";
        Category food = new Category(foodName);
        assertEquals(foodName, food.getName());
        assertEquals(true, food.isEmpty());
        assertEquals(0, food.size());

        try {
            food.getEntry(2);
        } catch (IndexOutOfBoundsException e) {
            e = null;
        }

        String grieche = "Grieche";
        food.addEntry(grieche);

        assertEquals(false, food.isEmpty());
        assertEquals(1, food.size());

        assertEquals(grieche, food.getEntry(0).getName());

        String thai = "Thai";
        food.addEntry(thai);

        String bk = "BK";
        food.addEntry(bk);

        assertEquals(3, food.size());
    }

    @Test
    public void testRandom() {
        Deasycions easy = new Deasycions();
        String foodName = "Food";
        Category food = new Category(foodName);

        try {
            System.out.println(easy.random(food).getName());
        } catch (EmptyCategoryException e) {
            e = null;
        }

        String grieche = "Grieche";
        food.addEntry(grieche);
        String thai = "Thai";
        food.addEntry(thai);
        String bk = "BK";
        food.addEntry(bk);
        String mecces = "Mecces";
        food.addEntry(mecces);
        String sultan = "Sultan";
        food.addEntry(sultan);

        try {
            System.out.println(easy.random(food).getName());
        } catch (EmptyCategoryException e) {
            e = null;
        }
    }

    @Test
    public void testChangeCategory() {
        String foodName = "Food";
        Category food = new Category(foodName);
        String lunch = "Lunch";
        food.changeName(lunch);
        assertEquals(lunch, food.getName());

        String grieche = "Grieche";
        food.addEntry(grieche);
        String thai = "Thai";
        food.addEntry(thai);
        String bk = "BK";
        food.addEntry(bk);
        String mecces = "Mecces";
        food.addEntry(mecces);

        String bk2 = "Burger King";
        food.changeEntryName(bk2, 2);
        assertEquals(bk2, food.getEntry(2).getName());
    }

}

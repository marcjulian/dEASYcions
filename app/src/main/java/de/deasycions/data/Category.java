package de.deasycions.data;

/**
 * Category is a data structure saving a category name and entries objects.
 *
 * @author Marc Stammerjohann
 */
public class Category {

    private String name;

    private Entry first = null;
    private Entry last = null;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isEmpty() {
        if (first == null) {
            return true;
        }
        return false;
    }

    public int size() {
        int size = 0;
        Entry current = first;
        while (current != null) {
            current = current.next;
            size++;
        }
        return size;
    }


    /**
     * returns an entry at the given position.
     *
     * @param position starts at 0
     * @return entry at the give position
     * @throws IndexOutOfBoundsException
     */
    public Entry getEntry(int position) throws IndexOutOfBoundsException {
        if (isEmpty() || position > size()) {
            throw new IndexOutOfBoundsException();
        }

        Entry current = first;
        for (int i = 0; i < position; i++) {
            current = current.next;
        }
        return current;
    }

    /**
     * Add entry last.
     *
     * @param name
     */
    public void addEntry(String name) {
        Entry newEntry = new Entry(name);
        if (last == null) {
            first = last = newEntry;
        } else {
            last.next = newEntry;
            newEntry.prev = last;
            last = newEntry;
        }
    }

    /**
     * removes an entry at the given position.
     *
     * @param position starts at 0
     * @throws IndexOutOfBoundsException
     */
    public void removeEntry(int position) throws IndexOutOfBoundsException {
        if (isEmpty() || position > size()) {
            throw new IndexOutOfBoundsException();
        }

        Entry current = first;
        for (int i = 0; i < position; i++) {
            current = current.next;
        }
        Entry prev = current.prev;
        Entry next = current.next;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }
    }

    /**
     * removes an entry with the given name.
     *
     * @param name
     * @throws IndexOutOfBoundsException
     */
    public void removeEntry(String name) {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException();
        }

        Entry current = first;
        while (!current.getName().equals(name)) {
            current = current.next;
        }
        Entry prev = current.prev;
        Entry next = current.next;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }

    }

    /**
     * changes the category name.
     *
     * @param name to change the category name to
     */
    public void changeCategoryName(String name) {
        this.name = name;
    }

    /**
     * changes the entry name at the given position.
     *
     * @param currentName of the entry
     * @param newName     to change the entry name to
     */
    public void changeEntryName(String currentName, String newName) {
        Entry current = first;
        while (current != null) {
            if (current.getName().equals(currentName)) {
                current.setName(newName);
                return;
            }
            current = current.next;
        }

    }

    /**
     * Returns whether an entry of the same name exists already or not.
     * It is Case-insensitivity.
     *
     * @param name for a new entry
     * @return
     */
    public boolean containsEntry(String name) {
        Entry current = first;
        while (current != null) {
            if (current.getName().toLowerCase().equals(name.toLowerCase())) {
                return true;
            }
            current = current.next;
        }
        return false;
    }


    /**
     * Entry is saving the entry name and positions.
     *
     * @author Marc Stammerjohann
     */
    public class Entry {

        private String name;

        private Entry next;

        private Entry prev;

        public Entry(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}

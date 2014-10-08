package de.deasycions.input;

/**
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

    public void changeName(String name) {
        this.name = name;
    }

    public void changeEntryName(String name, int position)
            throws IndexOutOfBoundsException {
        if (isEmpty() || position > size()) {
            throw new IndexOutOfBoundsException();
        }

        Entry current = first;
        for (int i = 0; i < position; i++) {
            current = current.next;
        }

        current.setName(name);
    }

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

package de.deasycions.input;

/**
 * @author Marc Stammerjohann
 */
public class Category {

    private String name;

    private Entry first = null;

    public Category(String name){
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isEmpty(){
        if(first == null){
            return true;
        }
        return false;
    }

    public int size(){
        int size = 0;
        Entry tmp = first;
        while(tmp != null){
            size++;
        }
        return size;
    }

    public Entry getEntry(int random) {
        Entry tmp = first;
        while(random - 1 > 0){
            tmp = tmp.next;
            random--;
        }
        return tmp;
    }

    public class Entry{

        private String name;

        public Entry next;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}


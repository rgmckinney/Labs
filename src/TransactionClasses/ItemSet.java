package TransactionClasses;

import java.util.*;
import java.io.*;


public class ItemSet {
    private HashSet<Integer> items = new HashSet<>();

    public HashSet<Integer> getSet() {
        return (HashSet)items.clone();
    }

    public void addItem(int item) {
        items.add(item);
    }

    public ItemSet() {}

    public ItemSet(Set itemSet) {
        Iterator iter = itemSet.iterator();
        while (iter.hasNext()) {
            items.add((Integer)iter.next());
        }
    }

    public ItemSet(ItemSet itemSet) {
        Iterator iter = itemSet.iterator();
        while (iter.hasNext()) {
            items.add((Integer)iter.next());
        }
    }

    public void join(ItemSet itemSet) {
        Iterator iter = itemSet.iterator();
        while (iter.hasNext()) {
            items.add((Integer)iter.next());
        }
    }

    public boolean contains(int item) {
        return items.contains(item);
    }

    public boolean contains(ItemSet itemSet) {
        Iterator<Integer> iter = itemSet.iterator();
        while (iter.hasNext()) {
            if (!items.contains(iter.next()))
                return false;
        }

        return true;
    }


    public Iterator<Integer> iterator() {
        return items.iterator();
    }
    public int size() {
        return items.size();
    }

    @Override
    public String toString() {
        return items.toString();
    }
}

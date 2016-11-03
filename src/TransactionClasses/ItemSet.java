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

    public ItemSet minusItemSet (ItemSet itemSet) {
        ItemSet newItemSet = new ItemSet();
        for (int curItem : items) {
            if (!itemSet.contains(curItem)) {
                newItemSet.addItem(curItem);
            }
        }
        return newItemSet;
    }

    public void removeItemSet(ItemSet itemSet) {
        for (int curItem : itemSet.getSet()) {
            if (items.contains(curItem)) {
                items.remove(curItem);
            }
        }
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

    @Override
    public boolean equals(Object other) {
        return this.getSet().equals(((ItemSet)other).getSet());
    }
}

package Behavioral.IteratorDP;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

// Concrete Aggregate
class MyArrayList<T> implements Iterable<T> {  //T represents the type of collection(lists, sets, etc)
    private List<T> list; //T : generic class , now can work with any data type

    public MyArrayList() {
        list = new ArrayList<>();
    }

    public void add(T element) {
        list.add(element);
    }

    public int size() {
        return list.size();
    }

    @Override
    public Iterator<T> iterator() {
        return new MyArrayListIterator<T>(list);
    }

    // Inner Iterator class
    private class MyArrayListIterator<T> implements Iterator<T> {
        private int currentIndex = 0;
        private final List<T> listRef;

        public MyArrayListIterator(List<T> list) {
            this.listRef = list;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < listRef.size();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return listRef.get(currentIndex++);
        }
    }
}


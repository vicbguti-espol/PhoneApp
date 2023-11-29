package collections;

import java.util.Iterator;

public interface CustomIterator<E> extends Iterator<E> {
    public E previous();
}

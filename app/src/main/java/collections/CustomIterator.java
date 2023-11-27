package collections;

import java.util.Iterator;

public interface CustomIterator<E> extends Iterator<E> {
    E previous();
}

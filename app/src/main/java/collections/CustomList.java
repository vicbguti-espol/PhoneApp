package collections;

import java.util.List;

public interface CustomList<E> extends List<E> {
    public CustomIterator<E> customIterator();
    public E getFirst();
}

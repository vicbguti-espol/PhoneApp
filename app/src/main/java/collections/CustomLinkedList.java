package collections;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class CustomLinkedList<E> implements CustomList<E>, Iterable<E>, Serializable {
    Node<E> last;
    int n;
    
    public CustomLinkedList(){
        last = null;
        n = 0;
    }
    
    public CustomLinkedList(List<E> list){
        addAll(list);
    }


    private class Node<E> implements Serializable {
        E content;
        Node<E> next;
        Node<E> previous;

        public Node(E e) {
            content = e;
            next = previous =  this;
        }
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public boolean isEmpty() {
        return last == null;
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Iterator<E> iterator() {
        return new CustomLinkedListIterator<E>(this);
    }
    
    private class CustomLinkedListIterator<E> implements Iterator<E>{
        private Node<E> it;
        private int i = 0;
        
        CustomLinkedListIterator(CustomLinkedList customLinkedList){
            it = customLinkedList.last.next;
        }
        
        @Override
        public boolean hasNext() {
            return i < size();
        }

        @Override
        public E next() {
            E content = it.content;
            it = it.next;
            i++;
            return content;
        }
    
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean add(E e) {
        Node<E> node = new Node(e);
        if ( isEmpty() ){
            last = node;
        }
        else {
            node.previous = last;
            node.next = last.next;
            
            last.next.previous =  node;
            last.next = node;
            last = node; 
        }
        n++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean containsAll(Collection<?> clctn) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean addAll(Collection<? extends E> clctn) {
        clctn.forEach(e -> this.add(e));
        return true;
    }

    @Override
    public boolean addAll(int i, Collection<? extends E> clctn) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean removeAll(Collection<?> clctn) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public boolean retainAll(Collection<?> clctn) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void clear() {
        last = null;
        n = 0;
    }
    
    @Override
    public E getFirst(){
        return last.next.content;
    }

    @Override
    public E get(int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E set(int i, E e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void add(int i, E e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E remove(int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public List<E> findAll(Comparator<E> cmp, E e2) {
        List<E> found = new CustomLinkedList<>();
        for ( E e1: this ) {
            if ( cmp.compare(e1, e2) == 0 ) {
                found.add(e1);
            }
        }
        return found;
    }

    @Override
    public ListIterator<E> listIterator(int i) {
        return new CustomListIterator<>(this, i);
    }
    
    private class CustomListIterator<E> implements ListIterator<E>{
        private Node<E> it;
        private int j = 0;
        
        CustomListIterator(CustomLinkedList customLinkedList, int i){
            it = customLinkedList.last.next;
            while (j < i){
                it = it.next;
                j++;
            }
        }
        
        @Override
        public boolean hasNext() {
            return j < size();
        }

        @Override
        public E next() {
            E content = it.content;
            it = it.next;
            j++;
            return content;
        }

        @Override
        public boolean hasPrevious() {
            return j > 0;
        }

        @Override
        public E previous() {
            E content = it.content;
            it = it.previous;
            j--;
            return content;
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
        
    }

    @Override
    public List<E> subList(int i, int i1) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public String toString(){
        String s = "[";
        if (last != null){
            Node tmp = last.next;
            for (int i = 0; i < n; i++){
                s += tmp.content + ", ";
                tmp = tmp.next;
            }
        }
        return s + "]";
    }
    
    @Override
    public CustomIterator<E> customIterator(){
        return new CircularIterator<>(this);
    }
    
    private class CircularIterator<E> implements CustomIterator<E> {
        private Node<E> it;
        private boolean nextStatus;
        private boolean previousStatus;
        
        CircularIterator(CustomLinkedList<E> list){
            if (list.last != null) {
                it = (Node<E>) list.last.next; 
                nextStatus = true;
            }   
            else it = null;
        }
        
        @Override
        public boolean hasNext() {
            return  it != null;
        }
        
        @Override
        public E next() {
            if (!nextStatus){
                nextStatus = true; previousStatus=false;
                it = it.next.next;
            }
            E content = it.content;
            it = it.next;
            return content;
        }
        
        @Override
        public E previous() {
            if (!previousStatus){
                previousStatus = true; nextStatus=false;
                it = it.previous.previous;
            }
            E content = it.content;
            it = it.previous;
            return content;
        }
    }
}

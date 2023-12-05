package collections;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

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
        Iterator<E> it = this.iterator();
        int i = 0;
        while (it.hasNext()){
            ts[i] = (T) it.next();
            i++;
        }
        return ts;
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
        if (isEmpty()) return false;

        Node<E> current = last.next;
        while (current != last) {
            if (current.content.equals(o)) {
                Node<E> prev = current.previous;
                Node<E> next = current.next;
                prev.next = next;
                next.previous = prev;
                if (current == last) {
                    last = prev;
                }
                n--;
                return true;
            }
            current = current.next;
        }
        if (last.content.equals(o)) {
            Node<E> prev = current.previous;
            Node<E> next = current.next;
            prev.next = next;
            next.previous = prev;
            last = prev;
            n--;
            return true;
        }
        return false;
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
        if (i < 0 || i >= size()) throw new IndexOutOfBoundsException(i);
        if (i != (size() - 1)){
            Node<E> tmp = last.next;
            for (int c = 0; c < i; c++){
                tmp = tmp.next;
            }
            return tmp.content;
        }
        return getLast();
    }
    
    public E getLast(){
        if (isEmpty()){
            return null;
        }
        return last.content;
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
    public E remove(int index) {
        if (index <= 0 || index >= size()) throw new IndexOutOfBoundsException(index);
        if (index != size() - 1) {
            Node<E> tmp = last.previous;
            for (int i = 0; i < (index - 1); i++){
                tmp = tmp.next;
            }
            E removed = tmp.next.content;
            tmp.next = tmp.next.next;
            tmp.next.previous = tmp;
            n--;
            return removed;
        }
        return removeLast();
    }
    
    public E removeLast(){
        E removed = last.content;
        last.previous.next = last.previous;
        last = last.previous;
        //primero.anterior = last;
        n--;
        return removed;
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
        return new CustomListIterator<>(this, 0);
        // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public CustomList<E> findAll(Comparator<E> cmp, E e2) {
        CustomList<E> found = new CustomLinkedList<>();
        for ( E e1: this ) {
            if ( cmp.compare(e1, e2) == 0 ) {
                found.add(e1);
            }
        }
        return found;
    }

    @Override
    public ListIterator<E> listIterator(int i) {
        if (i < 0 || i >= size()) throw new IndexOutOfBoundsException(i);
        return new CustomListIterator<>(this, i);
        // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private class CustomListIterator<T> implements ListIterator<E>{
        private Node<E> it;
        private int j = 0;
        private boolean canRemove;
        
        CustomListIterator(CustomLinkedList customLinkedList, int i){
            it = customLinkedList.last.next;
            while (j < i){
                it = it.next;
                j++;
            }
            canRemove = false;
        }
        
        @Override
        public boolean hasNext() {
            return j < size();
        }

        @Override
        public E next() {
            if(!hasNext()) throw new NoSuchElementException();
            E content = it.content;
            it = it.next;
            j++;
            canRemove = true;
            return content;
        }

        @Override
        public boolean hasPrevious() {
            return j > 0;
        }

        @Override
        public E previous() {
            if(!hasPrevious()) throw new NoSuchElementException();
            E content = it.content;
            it = it.previous;
            j--;
            canRemove = true;
            return content;
        }

        @Override
        public int nextIndex() {
            return j;
        }

        @Override
        public int previousIndex() {
            return j - 1;
        }

        @Override
        public void remove() {
            if(!canRemove) throw new IllegalStateException();
            Node<E> prevNode = it.previous;
            Node<E> nextNode = it.next;

            if (prevNode == null) {last.previous = it;}
            else prevNode.next = it;
            if (nextNode == null) last = prevNode;
            else nextNode.previous = prevNode;
            
            
            prevNode.next = nextNode;
            nextNode.previous = prevNode;

            if (it == last) it = prevNode;
            j--;
            n--;
            canRemove = false;
            //throw new UnsupportedOperationException();
        }

        @Override
        public void set(E e) {
            if(!canRemove) throw new IllegalStateException();
            it.content = e;
            //throw new UnsupportedOperationException();
        }

        @Override
        public void add(E e) {
            if (it == null) {
                Node<E> l = (Node<E>) last;
                Node<E> newNode = new Node<>(e);
                newNode.previous = l;
                newNode.next = (Node<E>) last.previous;
                last = newNode;
                if (l == null) last.previous = newNode;
                else l.next = newNode;
            } else {
                Node<E> pred = it.previous;
                Node<E> newNode = new Node<>(e);
                newNode.previous = pred;
                newNode.next = it;
                it.previous = newNode;
                if (pred == null) last.previous = newNode;
                else pred.next = newNode;
            }
            j++;
            n++;
            canRemove = false;
            //throw new UnsupportedOperationException();
        }
        
    }
    
    public static void main(String[] args) {
        CustomLinkedList<Integer> list = new CustomLinkedList<>();
        list.add(8);
        list.add(4);
        list.add(3);
        list.add(1);
        list.add(5);
        System.out.println(list);
        ListIterator<Integer> listIt = list.listIterator();
        
        while (listIt.hasNext()) {
            int i = listIt.next();
            System.out.println(i+" ");
            
            if(i%2==0){
                listIt.set(0);
                listIt.add(i);
            }
        }
        
        System.out.println(list);
        
        /*while (listIt.hasPrevious()) {
            int i = listIt.previous();
            System.out.println(i+" ");
        }
        
        System.out.println(list);*/
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

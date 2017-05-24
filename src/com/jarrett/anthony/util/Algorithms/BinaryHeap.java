package com.jarrett.anthony.util.Algorithms;

import java.util.Arrays;
import java.util.Comparator;
/**
 * Binary Heap data structure to support A* algorithm
 */
public class BinaryHeap<T> {

    //ATTRIBUTES
    private Comparator<T> comparator;
    private Object[] array = new Object[8];
    private int size =0;

    //CONSTRUCTOR
    protected BinaryHeap(Comparator<T> c){
        this.comparator = c;
    }

    //OTHER METHODS

    //clear the heap
    protected void clear(){
        Arrays.fill(array, 0, array.length, null);
        this.size = 0;
    }

    //size of heap
    public int size(){
        return this.size;
    }

    //increase size when necessary; effective use of Arrays.copyOf
    private void ensureCapacity(){
        if(array.length <= size){
            int newSize = array.length;
            while((newSize = newSize*2) <=this.size());
            array = Arrays.copyOf(array, newSize);
        }
    }
    //add element to heap
    public void add(T t){
        size++;
        ensureCapacity();
        array[size] = t;
        bubbleUp(size);
    }
    //remove top element from heap and put last element to top
    @SuppressWarnings("unchecked")
    public T remove(){
        if (size > 0){
            Object obj = array[1];
            array[1] = array[size];
            size--;
            siftDown(1);
            return (T) obj;
        }
        return null;
    }

    //remove a given element by replacing it with the last element
    public void remove(T t){
        int index = indexOf(t);
        if(index != -1){
            if(index == size){
                array[size] = null;
                size--;
            }
            else{
                array[index] = array[size];
                size--;
                siftDown(index);
            }
        }
    }
    //check for valid index
    protected boolean contains(T t){
        return indexOf(t) != -1;
    }

    //return the index of an element by checking for equality
    private int indexOf(T t){
        for(int i = 0; i < array.length; i++){
            if(t == array[i]){
                return i;
            }
        }
        return -1;
    }

    /**
     *
     * @param i
     */
    @SuppressWarnings("unchecked")
    private void bubbleUp(int i){
        while(i > 1){
            if(comparator.compare((T)array[i], (T)array[i/2]) >=0)
                break;
            Object obj = array[i];
            array[i] = array[i/2];
            array[i/2] = obj;
            i /= 2;
        }
    }

    /**
     * @param i
     *
     */
    @SuppressWarnings("unchecked")
    private void siftDown(int i){
        int i1 = i;
        while (true){
            int i2 = i1;
            if(i2*2+1 <= size){
                if(comparator.compare((T)array[i2], (T)array[i2*2]) > 0){
                    i1 = i2*2;
                }
                if(comparator.compare((T)array[i1], (T)array[i2*2+1]) > 0){
                    i1 = i2*2+1;
                }
            }
            else if(i2*2 <=size){
                if(comparator.compare((T)array[i1], (T)array[i2*2]) > 0){
                    i1 = i2*2;
                }
            }
            if(i2 != i1){
                Object obj = array[i2];
                array[i2] = array[i1];
                array[i1] = obj;
            }
            else
                break;
        }
    }
}

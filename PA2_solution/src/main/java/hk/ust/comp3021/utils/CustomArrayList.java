package hk.ust.comp3021.utils;

public class CustomArrayList<E> {
    private Object[] elements;
    private int size;
    private int capacity;

    /**
     * TODO `CustomArrayList` constructor with default `capacity`(5) 
     * PS: `size` is set to the initial value 0
     */
    public CustomArrayList() {
    	size = 0;
    	capacity = 5;
        elements = new Object[capacity];
    }
    
    /**
     * TODO `CustomArrayList` constructor with given `capacity`
     * PS: `size` is set to the initial value 0
     */
    public CustomArrayList(int initialCapacity) {
    	size = 0;
    	capacity = initialCapacity;
        elements = new Object[capacity];
    }

    /**
     * TODO `add` appends new element into `elements`. Once `size` is equal to `capacity`, 
     * 		we need to resize `elements` to twice its original size.
     * @param element to be added into `elements`
     * @return null
     */
    public void add(E element) {
        if (size == capacity) {
            resize(2 * capacity);
        }
        elements[size++] = element;
    }
    
    /**
     * TODO `resize` modifies the size of `elements`
     * @param newCapacity to indicate the new capacity of `elements`
     * @return null
     */
    private void resize(int newCapacity) {
        Object[] newElements = new Object[newCapacity];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
        capacity = newCapacity;
    }

    /**
     * TODO `get` obtains target element based on the given index. Once the index is not within [0, size), 
     * 		we need to return null.
     * @param index to indicate the element position
     * @return element whose index is `index`
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        E element = (E) this.elements[index];
        return element;
    }

    /**
     * TODO `size` obtains the size of `elements`
     * @param null
     * @return `size`
     */
    public int size() {
        return size;
    }
    
    /**
     * TODO `isEmpty` determine whether the list is empty
     * @param null
     * @return boolean variable that indicates the list status
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * TODO `contains` determine whether the input is in `elements`
     * @param obj to be determined
     * @return boolean variable that indicates the existence of `obj`
     */
    public boolean contains(E obj) {
    	if (obj == null) {
    		for (int i = 0; i < size; i++) {
                if (elements[i] == obj) {
                    return true;
                }
            }
    	} else {
    		for (int i = 0; i < size; i++) {
                if (obj.equals(elements[i])) {
                    return true;
                }
            }
    	}
        return false;
    }
}
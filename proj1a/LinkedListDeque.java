public class LinkedListDeque<T> {
    private class Node<T> {
        T data;
        Node<T> next = null;
        Node<T> prev = null;

        Node(T data) {
            this.data = data;
            next = null;
            prev = null;
        }
    }

    private Node<T> front, rear;
    private int count;

    public LinkedListDeque() {
        // dummy node
        this.front = new Node<T>(null);
        this.rear = new Node<T>(null);
        front.next = rear;
        front.prev = null;
        rear.next = null;
        rear.prev = front;
        this.count = 0;
    }


    public boolean isEmpty() {
        return count == 0;
    }


    public void addFirst(T add) {
        Node<T> rest = this.front.next;
        this.front.next = new Node(add);
        this.front.next.next = rest;
        this.front.next.prev = this.front;
        rest.prev = this.front.next;
        count++;
    }


    public int size() {
        return count;
    }


    public void addLast(T add) {
        Node<T> rest = this.rear.prev;
        this.rear.prev = new Node(add);
        this.rear.prev.prev = rest;
        this.rear.prev.next = this.rear;
        rest.next = this.rear.prev;
        count++;
    }


    public void printDeque() {
    }


    public T removeFirst() {
        if (size() == 0) {
            return null;
        }
        T tmp = this.front.next.data;
        this.front.next = this.front.next.next;
        this.front.next.prev = this.front;
        count--;
        return tmp;
    }


    public T removeLast() {
        if (size() == 0) {
            return null;
        }
        T tmp = this.rear.prev.data;
        this.rear.prev.prev.next = this.rear;
        this.rear.prev = this.rear.prev.prev;
        count--;
        return tmp;
    }


    public T get(int index) {
        Node<T> tra = front.next;
        while (tra != null && index > 0) {
            index--;
            tra = tra.next;
        }
        return (tra != null) ? tra.data : null;
    }

    public T getRecursive(int index) {
        if (index >= size()) {
            return null;
        }
        return getHelper(index, this.front.next);
    }

    private T getHelper(int index, Node<T> root) {
        if (index == 0) {
            return root.data;
        } else {
            return getHelper(index - 1, root.next);
        }
    }
}

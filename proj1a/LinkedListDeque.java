public class LinkedListDeque<T> implements Deque<T> {
    private class Node<T> {
        public T data;
        public Node<T> next = null;
        public Node<T> prev = null;

        public Node(T data) {
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

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public void addFirst(T add) {
        Node<T> rest = this.front.next;
        this.front.next = new Node(add);
        this.front.next.next = rest;
        this.front.next.prev = this.front;
        rest.prev = this.front.next;
        count++;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public void addLast(T add) {
        Node<T> rest = this.rear.prev;
        this.rear.prev = new Node(add);
        this.rear.prev.prev = rest;
        this.rear.prev.next = this.rear;
        rest.next = this.rear.prev;
        count++;
    }

    @Override
    public void printDeque() {
    }

    @Override
    public T removeFirst() {
        if (size() == 0) return null;
        T tmp = this.front.next.data;
        this.front.next = this.front.next.next;
        this.front.next.prev = this.front;
        count--;
        return tmp;
    }

    @Override
    public T removeLast() {
        if (size() == 0) return null;
        T tmp = this.rear.prev.data;
        this.rear.prev.prev.next = this.rear;
        this.rear.prev = this.rear.prev.prev;
        count--;
        return tmp;
    }

    @Override
    public T get(int index) {
        Node<T> tra = front.next;
        while (tra != null) {
            index--;
            tra = tra.next;
        }
        return (index == 0) ? tra.data : null;
    }
}

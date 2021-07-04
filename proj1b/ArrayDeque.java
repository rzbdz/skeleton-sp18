public class ArrayDeque<T> implements Deque {
    private Object[] items;
    private int capacity;
    private int front, rear;
    private static final int INIT_CAP = 8;

    public ArrayDeque() {
        this.capacity = INIT_CAP;
        this.items = new Object[INIT_CAP];
    }

    private boolean isFull() {
        return (rear + 1) % capacity == front;
    }

    private void resizeSpread() {
        int oldCapacity = this.capacity;
        this.capacity = this.capacity * 2;
        Object[] re = new Object[capacity];
        int j = 0;
        if (front < rear) {
            for (int i = front; i < rear; i++) {
                re[j] = items[i];
                j++;
            }
        } else {
            for (int i = 0; i < rear; i++) {
                re[i] = items[i];
            }
            int oldFront = front;
            front = this.capacity - (oldCapacity - oldFront);
            j = front;
            for (int i = oldFront; i < oldCapacity; i++) {
                re[j] = items[i];
                j++;
            }
        }
        items = re;
    }

    private void resizeShrink() {
        int copyCnt = size();
        int newCap = capacity / 2;
        Object[] re = new Object[newCap];
        for (int i = 0; i < copyCnt; i++) {
            re[i] = get(i);
        }
        items = re;
        capacity = newCap;
        front = 0;
        rear = copyCnt;
    }


    public boolean isEmpty() {
        return front == rear;
    }


    public int size() {
        return (rear - front + capacity) % capacity;
    }


    public void addFirst(Object add) {
        if (isFull()) {
            resizeSpread();
        }
        front = (front - 1 + capacity) % capacity;
        items[front] = add;
    }


    public void addLast(Object add) {
        if (isFull()) {
            resizeSpread();
        }
        items[rear] = add;
        rear = (rear + 1) % capacity;
    }


    public T removeFirst() {
        if (size() < capacity / 2) {
            resizeShrink();
        }
        T tmp = (T) items[front];
        items[front] = null;
        front = (front + 1) % capacity;
        return tmp;
    }


    public T removeLast() {
        if (size() < capacity / 2) {
            resizeShrink();
        }
        rear = (rear - 1 + capacity) % capacity;
        T tmp = (T) items[rear];
        items[rear] = null;
        return tmp;
    }


    public T get(int index) {
        if (index >= size()) {
            return null;
        }
        return (T) items[(index + front) % capacity];
    }


    public void printDeque() {
        int i = front;
        while (i != rear) {
            System.out.print(items[i] + ", ");
            i = (i + 1) % capacity;
        }
        System.out.println();
    }

    private void printDeque(String hint) {
        int i = front;
        System.out.println(hint);
        int cnt = 0;
        while (cnt < size()) {
            System.out.print(items[i] + ", ");
            i = (i + 1) % capacity;
            cnt++;
        }
        System.out.println();
    }
}

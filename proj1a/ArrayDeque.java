public class ArrayDeque<T> implements Deque<T> {
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

    private void resize() {
        int old_capacity = this.capacity;
        this.capacity = this.capacity * 2;
        Object[] re = new Object[capacity];
        int j = 0;
        System.out.println("front: "+front + " rear: "+rear);
        if(front<rear){
            for(int i = front;i<rear;i++){
                re[j] = items[i];
                j++;
            }
        }else{
            for(int i = 0;i<rear;i++){
                re[i] = items[i];
            }
            int old_front = front;
            front = this.capacity - (old_capacity - old_front);
            j = front;
            for(int i = old_front;i<old_capacity;i++){
                re[j] = items[i];
                j++;
            }
            System.out.println("front updated: "+front);
        }
        items = re;
        printDeque("rsz");
    }

    @Override
    public boolean isEmpty() {
        return front == rear;
    }

    @Override
    public int size() {
        return (rear - front + capacity) % capacity;
    }

    @Override
    public void addFirst(T add) {
        if (isFull()) {
            resize();
        }
        front = (front - 1 + capacity) % capacity;
        items[front] = add;
        printDeque("in add first:");
    }

    @Override
    public void addLast(T add) {
        if (isFull()) {
            resize();
        }
        items[rear] = add;
        rear = (rear + 1) % capacity;
    }

    @Override
    public T removeFirst() {
        T tmp = (T) items[front];
        items[front] = null;
        front = (front + 1) % capacity;
        return tmp;
    }

    @Override
    public T removeLast() {
        T tmp = (T) items[rear - 1];
        items[rear - 1] = null;
        rear = (rear - 1 + capacity) % capacity;
        return tmp;
    }

    @Override
    public T get(int index) {
        if (index >= size()) return null;
        return (T) items[(index + front) % capacity];
    }

    @Override
    public void printDeque() {
        int i = front;
        while (i != rear) {
            System.out.print(items[i] + ", ");
            i = (i + 1) % capacity;
        }
        System.out.println();
    }
    public void printDeque(String hint) {
        int i = front;
        System.out.println(hint);
        int cnt = 0;
        System.out.print("size: "+size()+"; ");
        while (cnt<size()) {
            System.out.print(items[i] + ", ");
            i = (i + 1) % capacity;
            cnt++;
        }
        System.out.println();
    }
}

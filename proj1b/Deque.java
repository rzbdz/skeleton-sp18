public interface Deque<T> {
    boolean isEmpty();

    void addFirst(T add);

    int size();

    void addLast(T add);

    void printDeque();

    T removeFirst();

    T removeLast();

    T get(int index);
}

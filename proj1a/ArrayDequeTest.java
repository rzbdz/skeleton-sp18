public class ArrayDequeTest {
    public static boolean testConsistency() {
        ArrayDeque<Integer> dequeAf = new ArrayDeque<Integer>();
        ArrayDeque<Integer> dequeAl = new ArrayDeque<Integer>();

        boolean acc = true;
        int[] testArr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i = 0; i < 10; i++) {
            dequeAl.addLast(testArr[i]);
            dequeAf.addFirst(testArr[i]);
        }
        for (int i = 0; i < 10; i++) {
            acc &= dequeAf.get(i) == testArr[9 - i];
            acc &= dequeAl.get(i) == testArr[i];
        }
        acc &= (dequeAf.size() == 10) && (dequeAl.size() == 10);
        for (int i = 0; i < 10; i++) {
            acc &= dequeAf.removeFirst() == testArr[9 - i];
            acc &= dequeAf.size() == 10 - i - 1;
            acc &= dequeAl.removeFirst() == testArr[i];
            acc &= dequeAl.size() == 10 - i - 1;
        }
        return acc;
    }

    public static boolean testInterleave() {
        ArrayDeque<Integer> deque = new ArrayDeque<Integer>();
        boolean acc = true;
        int[] testAdfirst = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] testAdlast = {11, 22, 33, 44, 55, 66, 77, 88, 99, 1010};
        for (int i = 0; i < 10; i++) {
            deque.addLast(testAdlast[i]);
            deque.addFirst(testAdfirst[i]);
        }
        int j = 0;
        for (int i = 0; i < 10; i++) {
            if (deque.get(j) != testAdfirst[9 - i]) {
                return false;
            }
            if (deque.get(j + 10) != testAdlast[i]) {
                return false;
            }
            j++;
        }
        return true;
    }


    public static void main(String[] args) {
        System.out.println(testConsistency());
    }
}

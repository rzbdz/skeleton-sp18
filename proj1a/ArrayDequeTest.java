public class ArrayDequeTest {
    public static boolean testConsistency() {
        ArrayDeque<Integer> deque_af = new ArrayDeque<Integer>();
        ArrayDeque<Integer> deque_al = new ArrayDeque<Integer>();

        boolean acc = true;
        int[] testArr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i = 0; i < 10; i++) {
            deque_al.addLast(testArr[i]);
            deque_af.addFirst(testArr[i]);
        }
        deque_af.printDeque("af: before check");
        deque_al.printDeque("al: before check");
        for (int i = 0; i < 10; i++) {
            acc &= deque_af.get(i) == testArr[9 - i];
            acc &= deque_al.get(i) == testArr[i];
        }
        acc &= (deque_af.size() == 10) && (deque_al.size() == 10);
        for (int i = 0; i < 10; i++) {
            acc &= deque_af.removeFirst() == testArr[9 - i];
            acc &= deque_af.size() == 10 - i - 1;
            acc &= deque_al.removeFirst() == testArr[i];
            acc &= deque_al.size() == 10 - i - 1;
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

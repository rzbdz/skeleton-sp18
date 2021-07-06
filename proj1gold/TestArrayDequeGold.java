import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestArrayDequeGold {
    private static final int ADD_FIRST = 1;
    private static final int ADD_LAST = 2;
    private static final int REMOVE_FIRST = 3;
    private static final int REMOVE_LAST = 4;

    @Test
    public void testAddRandomly() {
        double randDouble;
        StudentArrayDeque<Integer> sad1;
        ArrayDequeSolution<Integer> ads1;
        StringBuilder msg;
        for(int times = 4;times<1000;times*=2){
            sad1 = new StudentArrayDeque<>();
            ads1 = new ArrayDequeSolution<>();
            msg = new StringBuilder();
            for (int i = 0; i < times; i++) {
                randDouble = StdRandom.uniform();
                if (randDouble > 0.5) {
                    sad1.addFirst(i);
                    ads1.addFirst(i);
                    msg.append("addFirst(").append(i).append(")\n");
                } else {
                    sad1.addLast(i);
                    ads1.addLast(i);
                    msg.append("addLast(").append(i).append(")\n");
                }
            }
            validateSameSeq(sad1, ads1, msg);
        }

    }

    private boolean validateSameSeq(StudentArrayDeque<Integer> sad,
                                    ArrayDequeSolution<Integer> ads,
                                    StringBuilder msg) {
        if (sad.size() != ads.size()) {
            return false;
        }
        double randDouble;
        Integer student, correct;
        while (sad.size() > 0 && ads.size() > 0) {
            randDouble = StdRandom.uniform();
            StringBuilder bd = new StringBuilder("expected");
            if (randDouble > 0.5) {
                student = sad.removeFirst();
                correct = ads.removeFirst();
                msg.append("removeFirst()\n");
            } else {
                student = sad.removeLast();
                correct = ads.removeLast();
                msg.append("removeLast()\n");
            }
            Assert.assertEquals(msg.toString(),correct,student);
        }
        return true;
    }
}

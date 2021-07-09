package synthesizer;

import org.junit.Assert;
import org.junit.Test;


/**
 * Tests the ArrayRingBuffer class.
 *
 * @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        StringBuilder bd = new StringBuilder("\n");
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(10);
        // test consistency
        for (int i = 0; i < 5; i++) {
            arb.enqueue(i);
            bd.append("enqueue(").append(i).append(")\n");
            Integer test;
            Assert.assertTrue(bd.toString(), (test = arb.peek()) != null);
            bd.append("peek()\n");
            Assert.assertEquals(bd.toString(), 0, test.intValue());
        }
        for (int i = 0; i < 5; i++) {
            bd.append("peek(").append(")\n");
            Integer test;
            test = arb.peek();
            Assert.assertEquals(bd.toString(), i, test.intValue());
            bd.append("dequeue(").append(")\n");
            test = arb.dequeue();
            Assert.assertEquals(bd.toString(), i, test.intValue());
        }
        // test Exception
        arb = new ArrayRingBuffer<>(4);
        try {
            for (int i = 0; i < 10; i++) {
                arb.enqueue(i);
            }
        } catch (RuntimeException e) {
            Assert.assertEquals("Didn't throw correspond exception",
                    "Ring buffer overflow", e.getMessage());
        }
    }

    /**
     * Calls tests for ArrayRingBuffer.
     */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 

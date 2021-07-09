package synthesizer;
//package <package name>;

//Make sure this class is public
public class GuitarString {
    /**
     * Constants. Do not change. In case you're curious, the keyword final means
     * the values cannot be changed at runtime. We'll discuss this and other topics
     * in lecture on Friday.
     */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor
    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        buffer = new ArrayRingBuffer<Double>((int) Math.round((SR / frequency)));
    }

    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        //       Make sure that your random numbers are different from each other.

        while (!buffer.isEmpty()) {
            buffer.dequeue();
        }
        double en;
        while (!buffer.isFull()) {
            en = 0.5 * (Math.random() - 0.5);
            buffer.enqueue(en);
        }
    }


    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double de = buffer.dequeue();
        double newSoundPoint = DECAY * (0.5 * (de + buffer.peek()));
        buffer.enqueue(newSoundPoint);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        if (buffer.isEmpty()) {
            return 0;
        }
        return buffer.peek();
    }
}

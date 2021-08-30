package byog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RectangleAllocator {
    private List<Rectangle> freelist;
    private Random random;

    public RectangleAllocator(int worldWidth, int worldHeight, Random r) {
        this.freelist = new ArrayList<>();
        this.random = r;
        freelist.add(new Rectangle(0, worldWidth, 0, worldHeight));
    }

    public Rectangle randomRecAlloc() {
        int i = random.nextInt(freelist.size());
        Rectangle big = freelist.get(i);
        int left = nextBoundInt(big.left, big.right);
        int bottom = nextBoundInt(big.bottom, big.top);
        int width = random.nextInt(big.right - left + 1);
        int height = random.nextInt(big.top - bottom + 1);
        int right = left + width - 1;
        int top = bottom + height - 1;
        if (left == big.left && right == big.right && bottom == big.bottom && top == big.top) {
            freelist.remove(big);
        }
        Rectangle allocated = new Rectangle(left, right, bottom, top);
        return allocated;
    }

    private int nextBoundInt(int down, int up) {
        return random.nextInt(down + up + 1) + down;
    }
}

package byog.lab5;

import byog.Point;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int size = 60;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(size, size);
        TETile[][] world = new TETile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        HexWorld.addHexagon(world, Tileset.GRASS, 5, new Point(20, 20));
        addHexLine(world, Tileset.WALL, 4, new Point(3, 0), 3);
        addHHexagon(world, Tileset.FLOWER, 3, 5, new Point(0, 0));
        ter.renderFrame(world);
    }

    public static void addHHexagon(TETile[][] world, TETile tile, int n, int bigN, Point recLB) {
        Point oriLeftBottom = recLB.add(n - 1, ntoLength(n));
        Point traverse;
        for (int i = 0; i < bigN; i++) {
            traverse = new Point(oriLeftBottom.getX(), oriLeftBottom.getY() + 2 * i * n);
            for (int j = 0; j < 2 * bigN - 1 - i; j++) {
                traverse.add(bypassOffsetX(n, 1), bypassOffsetY(n, 1));
                addHexagon(world, tile, n, traverse);
            }
        }
    }

    private static int bypassOffsetY(int n, int count) {
        return n * count;
    }

    private static int bypassOffsetX(int n, int count) {
        return bypassOffsetXHelper(n, count, 0);
    }

    private static int bypassOffsetXHelper(int n, int count, int acc) {
        if (count == 0) {
            return acc + n - 1;
        } else {
            return bypassOffsetXHelper(n, count - 1, acc + 2 * n - 1);
        }
    }


    private static void addHexLine(TETile[][] world, TETile tile, int n, Point lb, int count) {
        int offset = ntoLength(n) + 2 * n - 1;
        for (int i = 0; i < count; i++) {
            addHexagon(world, tile, n, lb.add(offset, 0));
        }
    }

    public static int ntoLength(int n) {
        return 3 * n - 2;
    }

    public static void addHexagon(TETile[][] world, TETile tile, int n, Point leftBottom) {
        addHexagonAtRec(world, tile, n, leftBottom.add(1 - n, 0));
    }

    public static void addHexagonAtRec(TETile[][] world, TETile tile, int n, Point recLeftBottom) {
        int recLeftBottomX = recLeftBottom.getX(), recLeftBottomY = recLeftBottom.getY();
        if (n < 2) {
            throw new IllegalArgumentException("Hexagon n must > 2");
        }
        int length = 3 * n - 2;
        for (int i = 0; i < n; i++) {
            addCenteredLine(world, tile, recLeftBottomX, recLeftBottomY + i, length, n - 1 - i);
        }
        for (int i = 0; i < n; i++) {
            addCenteredLine(world, tile, recLeftBottomX, recLeftBottomY + n + i, length, i);
        }
    }

    private static void addCenteredLine(TETile[][] world, TETile tile,
                                        int x, int y, int length, int noCnt) {
        for (int i = 0; i < length - 2 * noCnt; i++) {
            if (x + noCnt + i == -1 || y == -1) {
                System.out.println("fuckyou");
            }
            world[x + noCnt + i][y] = tile;
        }
    }

}

package byog;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayDeque;
import java.util.Calendar;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Random;


/**
 * A generator that generate a Tile based cave world.
 * <p>
 * #########################################
 * ##########          ####     ############
 * ########        ###########         #####
 * ###      ###         #######   #  #######
 * #####      #####    ####           ######
 * ###         ##### ###########       #####
 * ###############   #######################
 * #########################################
 */
public class CaveWorldGenerator extends WorldGenerator {
    private static final double FLOOR_PORTION = 0.4;
    static final int[] STEP_X = {0, 0, 1, -1, 1, 1, -1, -1};
    static final int[] STEP_Y = {-1, 1, 0, 0, -1, 1, 1, -1};

    public CaveWorldGenerator(long seed, int width, int height) {
        super(seed, width, height, true);
    }

    public static void main(String[] args) {
        int width = 105, height = 45;
        TERenderer ter = new TERenderer();
        CaveWorldGenerator wg = new CaveWorldGenerator(1717, width, height);
        ter.initialize(width, height);
        wg.generate();
        TETile[][] randomTiles = wg.world;
        ter.renderFrame(randomTiles);
    }


    /**
     * Return a random Tileset.FLOOR or WALL tile based on the FLOOR_PORTION possibility;
     */
    private TETile randomTile() {
        double r = random.nextDouble();
        if (r > FLOOR_PORTION) {
            return Tileset.GREEN_WALL();
        }
        return Tileset.FLOOR;
    }


    private void addCanGoNeighbors(int x, int y, Deque<Point> queue) {
        int nextI, nextJ;
        for (int i = 0; i < 4; i++) {
            nextI = x + STEP_X[i];
            nextJ = y + STEP_Y[i];
            if (nextI == 0 || nextJ == 0 || nextI == world.length - 1 || nextJ == world[0].length - 1) {
                continue;
            }
            if (world[nextI][nextJ] == Tileset.FLOOR) {
                queue.addLast(new Point(nextI, nextJ));
            }
        }
    }

    private void recursiveBreakThrough(int[][] visit, Point source, Point destination) {
        if ((source.getX() == destination.getX() &&
                source.getY() == destination.getY()) ||
                CA.isVonNeighbor(source, destination)) {
            return;
        }
        int breakX = 0, breakY = 0;
        int stepI = destination.getX() >= source.getX() ? 1 : -1;
        int stepJ = destination.getY() >= source.getY() ? 1 : -1;
        boolean isXMoved;
        while (breakX == 0 || breakY == 0 ||
                breakX == world.length - 1 || breakY == world[0].length - 1) {
            isXMoved = random.nextBoolean();
            breakX = source.getX() + (isXMoved ? stepI : 0);
            breakY = source.getY() + (isXMoved ? 0 : stepJ);
        }
        world[breakX][breakY] = Tileset.FLOOR;
        visit[breakX][breakY] = 2;
        recursiveBreakThrough(visit, new Point(breakX, breakY), destination);
    }

    private void breakThrough() {
        int VISIT_1 = 1;
        int[][] vis = new int[world.length][world[0].length];
        Deque<Point> exploreQueue = new ArrayDeque<>();
        PriorityQueue<Point> areaSpeaker =
                new PriorityQueue<>((o1, o2) -> o1.getX() + o1.getY() - o2.getX() - o2.getY());
        // traverse the floors to add area speakers.
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                if (vis[i][j] > 0 || world[i][j].description().equals("wall")) {
                    continue;
                }
                areaSpeaker.add(new Point(i, j));
                // use queue to BFS visit all floors of the same area
                exploreQueue.addLast(new Point(i, j));
                Point pass;
                int xx, yy;
                // BFS
                while (!exploreQueue.isEmpty()) {
                    pass = exploreQueue.pop();
                    xx = pass.getX();
                    yy = pass.getY();
                    if (vis[xx][yy] == VISIT_1) {
                        continue;
                    }
                    addCanGoNeighbors(xx, yy, exploreQueue);
                    vis[xx][yy] = VISIT_1;
                }
            }
        }
        // connect all speakers
        while (areaSpeaker.size() > 1) {
            Point b1 = areaSpeaker.poll(), b2 = areaSpeaker.poll();
            recursiveBreakThrough(vis, b1, b2);
            areaSpeaker.offer(b2);
        }
    }

    private void buildDoorWay(Point newFloor) {
        int VISIT_1 = 1;
        int[][] vis = new int[world.length][world[0].length];
        Point b1 = new Point(0, 0);
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                if (vis[i][j] > 0 || world[i][j].description().equals("wall")) {
                    continue;
                }
                b1.setX(i);
                b1.setY(j);
                break;
            }
        }
        world[newFloor.getX()][newFloor.getY()] = Tileset.FLOOR;
        System.out.println(b1);
        recursiveBreakThrough(vis, b1, newFloor);
    }

    private boolean canBeDoor(int x, int y, int[] xStep, int[] yStep) {
        int floorSum = 0;
        for (int i = 0; i < 6; i++) {
            floorSum += (world[x + xStep[i]][y + yStep[i]] == Tileset.FLOOR ? 1 : 0);
        }
        return floorSum == 6;
    }

    private void findAWallPlaceLockedDoor(boolean isRandomized) {
        Random doorRandom = random;
        if (isRandomized) {
            doorRandom = new Random(Calendar.getInstance().getTimeInMillis());
        }
        int topBottomLeftRight = doorRandom.nextInt(4);
        int doorX, doorY;
        boolean canBeDoor = false;
        int[] stepX, stepY;
        int loopCount = 0;
        if (topBottomLeftRight < 2) { // 0 top, 1 bottom
            doorX = doorRandom.nextInt(world.length - 1) + 1;
            doorY = (topBottomLeftRight == 0) ? (world[0].length - 1) : 0;
            stepX = new int[]{-1, 0, 1, -1, 0, 1};
            stepY = (topBottomLeftRight == 0) ? (new int[]{-1, -1, -1, -2, -2, -2}) : (new int[]{1, 1, 1, 2, 2, 2});
            while (!canBeDoor && loopCount < 10) {
                doorX = ((doorX + 1) % (world.length - 2)) + 1;
                canBeDoor = canBeDoor(doorX, doorY, stepX, stepY);
                loopCount++;
            }
        } else { // 2 left, 3 right
            doorY = doorRandom.nextInt(world[0].length - 1) + 1;
            doorX = (topBottomLeftRight == 2) ? 0 : (world.length - 1);
            stepY = new int[]{-1, 0, 1, -1, 0, 1};
            stepX = (topBottomLeftRight == 2) ? (new int[]{1, 1, 1, 2, 2, 2}) : (new int[]{-1, -1, -1, -2, -2, -2});
            while (!canBeDoor && loopCount < 10) {
                doorY = ((doorY + 1) % (world[0].length - 2)) + 1;
                canBeDoor = canBeDoor(doorX, doorY, stepX, stepY);
                loopCount++;
            }
        }
        if (loopCount >= 10 && !canBeDoor) {
            Point pointBeforeDoor = new Point(doorX, doorY);
            int offX = 0, offY = 0;
            switch (topBottomLeftRight) {
                case 0://top door
                    offY = -1;
                    break;
                case 1://bottom
                    offY = 1;
                    break;
                case 2://left
                    offX = 1;
                    break;
                case 3: //right
                    offX = -1;
                    break;
            }
            buildDoorWay(pointBeforeDoor);
        }
        world[doorX][doorY] = Tileset.LOCKED_DOOR;
    }

    public TETile[][] generate() {
        CA moorCA = new MoorCA(), vonCA = new VonCA();
        TETile[][] currentWorld = new TETile[width][height];
        // initialize the world
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                currentWorld[i][j] = randomTile();
            }
        }
        // cellular automata: enlarge floor area and wall area
        currentWorld = CA.updateWorld(4, moorCA, currentWorld);
        // cellular automata : eliminate single floor
        currentWorld = CA.updateWorld(1, vonCA, currentWorld);
        // make boundary on top and bottom
        for (int i = 0; i < height; i++) {
            currentWorld[0][i] = Tileset.GREEN_WALL();
            currentWorld[width - 1][i] = Tileset.GREEN_WALL();
        }
        // make boundary on left and right
        for (int i = 0; i < width; i++) {
            currentWorld[i][0] = Tileset.GREEN_WALL();
            currentWorld[i][height - 1] = Tileset.GREEN_WALL();
        }
        world = currentWorld;
        // make sure all floor are connected
        breakThrough();
        // make the door
        findAWallPlaceLockedDoor(false);
        return this.world;
    }

    private interface CA {
        void updateCell(TETile[][] oldWorld, TETile[][] newWorld, int x, int y);

        static TETile[][] updateWorld(int time, CA rule, TETile[][] oldWorld) {
            TETile[][] tmp = null;
            TETile[][] newWorld = new TETile[oldWorld.length][oldWorld[0].length];
            for (int i = 0; i < oldWorld.length; i++) {
                for (int j = 0; j < oldWorld[0].length; j++) {
                    newWorld[i][j] = Tileset.NOTHING;
                }
            }
            for (int t = 0; t < time; t++) {
                for (int i = 1; i < oldWorld.length - 1; i++) {
                    for (int j = 1; j < oldWorld[0].length - 1; j++) {
                        rule.updateCell(oldWorld, newWorld, i, j);
                    }
                }
                tmp = newWorld;
                newWorld = oldWorld;
                oldWorld = tmp;
            }
            return tmp;
        }

        private static boolean isVonNeighbor(Point p1, Point p2) {
            return Math.abs(p2.getX() - p1.getX()) + Math.abs(p2.getY() - p1.getY()) == 1;
        }
    }

    private class VonCA implements CA {
        /**
         * Von-Neumann cell updateCell method;
         *
         * @param oldWorld the world as last state of the automata;
         * @param newWorld the updated world array;
         */
        public void updateCell(TETile[][] oldWorld, TETile[][] newWorld, int x, int y) {
            int vonWallSum = 0;
            for (int i = 0; i < 4; i++) {
                vonWallSum += oldWorld[x + STEP_X[i]][y + STEP_Y[i]].description().equals("wall") ? 1 : 0;
            }
            if (oldWorld[x][y] == Tileset.FLOOR) {
                if (vonWallSum >= 4) {
                    newWorld[x][y] = Tileset.GREEN_WALL();
                } else {
                    newWorld[x][y] = Tileset.FLOOR;

                }
            }
            if (oldWorld[x][y].description().equals("wall")) {
                if (vonWallSum < 2) {
                    newWorld[x][y] = Tileset.FLOOR;
                } else {
                    newWorld[x][y] = Tileset.GREEN_WALL();
                }
            }
        }
    }

    private static class MoorCA implements CA {
        /**
         * Moor cell updateCell method;
         *
         * @param oldWorld the world as last state of the automata;
         * @param newWorld the updated world array;
         */
        public void updateCell(TETile[][] oldWorld, TETile[][] newWorld, int x, int y) {
            int moorWallSum = 0;
            for (int k = 0; k < 8; k++) {
                moorWallSum += oldWorld[x + STEP_X[k]][y + STEP_Y[k]].description().equals("wall") ? 1 : 0;
            }
            if (oldWorld[x][y].description().equals("wall")) {
                newWorld[x][y] = moorWallSum < 4 ? Tileset.FLOOR : Tileset.GREEN_WALL();
            }
            if (oldWorld[x][y] == Tileset.FLOOR) {
                newWorld[x][y] = moorWallSum > 5 ? Tileset.GREEN_WALL() : Tileset.FLOOR;
            }
        }
    }

}

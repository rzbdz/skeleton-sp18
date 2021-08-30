package byog;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

/**
 * inherited from WorldGenerator:
 * protected TETile[][] world;
 * protected int width, height;
 * protected long seed;
 * protected Random random;
 */
public class RectangleWorldGenerator extends WorldGenerator {

    public RectangleWorldGenerator(int seed, int width, int height) {
        super(seed, width, height, true);
    }

    @Override
    public TETile[][] generate() {
        makeManyRooms(40);
        makeRoomsConnected();
        placeALockedDoor();
        return this.world;
    }

    private ArrayList<Rectangle> existingRooms;

    public static void main(String[] args) {
        TERenderer rd;
        rd = new TERenderer();
        TETile[][] wo = new TETile[0][];
        int wd = 105, ht = 45;
        rd.initialize(wd, ht);
        int ans = 0;
        RectangleWorldGenerator rwg;
        rwg = new RectangleWorldGenerator(17171, wd, ht);
        rd.renderFrame(rwg.generate());
        System.out.println(ans);
    }

    private void makeManyRooms(int count) {
        int w, h, left, bottom, right, top;
        int th = 0;
        existingRooms = new ArrayList<>();
        for (int i = 0; i < count; ) {
            if (th < 2 * i) {
                w = random.nextInt(Math.min(15, width / 5)) + 2;
                h = random.nextInt(Math.min(20, height / 2)) + 2;
            } else if (th <= 1000) {
                w = random.nextInt(Math.min(12, width / 7)) + 2;
                h = random.nextInt(Math.min(18, height / 2)) + 2;
            } else if (th <= 2000) {
                w = random.nextInt(Math.min(5, width / 10)) + 2;
                h = random.nextInt(Math.min(5, height / 5)) + 2;
            } else {
                System.out.println("WARNING: too narrow to place more rooms, Please down the requesting room count!");
                break;
            }
            left = random.nextInt(width - w - 3) + 1;
            bottom = random.nextInt(height - h - 3) + 1;
            right = left + w + 1;
            top = bottom + h + 1;
            Rectangle cur = new Rectangle(left, right, bottom, top);
            if (canRoomBePlaced(cur, false)) {
                i++;
                makeRectangleRoom(cur);
                existingRooms.add(cur);
            } else {
                th++;
            }
        }
    }

    private void makeRoomsConnected() {
        Rectangle rr = existingRooms.remove(existingRooms.size() - 1);
        existingRooms.sort(Comparator.comparingInt(o -> (o.left * o.left + o.bottom * o.bottom)));
        int lx = rr.getCenterX(), ly = rr.getCenterY();
        for (var r : existingRooms) {
            int x = random.nextInt(r.getWidth() - 2) + r.left + 1;
            int y = random.nextInt(r.getHeight() - 2) + r.bottom + 1;
            makePathBetween2Floors(lx, ly, x, y);
            lx = x;
            ly = y;
        }
    }

    private boolean canRoomBePlaced(Rectangle rec, boolean canAdjacent) {
        /*
          rectangle overlapping detection algorithm
          more general, and have same performance in this scenario.
        */
        for (var room : existingRooms) {
            if (rec.isRectangleOverlap(room, canAdjacent)) {
                return false;
            }
        }
        /* NOTHING detection based algorithm
        if (!rec.insideRange(new Rectangle(0, width - 1, 0, height - 1))) return false;
        int offset = 0;
        if (!canAdjacent) {
            offset = 1;
        }
        for (int i = rec.left - offset; i <= rec.right + 2 * offset; i++) {
            if (i >= width) continue;
            for (int j = rec.bottom - offset; j < rec.top + 2 * offset; j++) {
                if (j >= height) continue;
                if (world[i][j] != Tileset.NOTHING) {
                    return false;
                }
            }
        }*/
        return true;
    }

    private void makeRectangleRoom(Rectangle room) {
        for (int i = room.left + 1; i <= room.right - 1; i++) {
            for (int j = room.bottom + 1; j <= room.top - 1; j++) {
                if (world[i][j] == Tileset.NOTHING) {
                    world[i][j] = Tileset.FLOOR;
                }
            }
        }
        for (int i = room.left; i <= room.right; i++) {
            if (world[i][room.top] == Tileset.NOTHING) {
                world[i][room.top] = Tileset.GREEN_WALL();
            }
            if (world[i][room.bottom] == Tileset.NOTHING) {
                world[i][room.bottom] = Tileset.GREEN_WALL();
            }
        }
        for (int i = room.bottom; i <= room.top; i++) {
            if (world[room.right][i] == Tileset.NOTHING) {
                world[room.right][i] = Tileset.GREEN_WALL();
            }
            if (world[room.left][i] == Tileset.NOTHING) {
                world[room.left][i] = Tileset.GREEN_WALL();
            }
        }
    }

    private void makeWallSurroundedFloor(int x, int y) {
        world[x][y] = Tileset.FLOOR;
        makeWallSurroundings(x, y, true);
    }


    private void makeWallSurroundings(int x, int y, boolean wallOnlyOnNothing) {
        int[] offX = new int[]{-1, 0, 1, -1, 0, 1, -1, 1};
        int[] offY = new int[]{1, 1, 1, -1, -1, -1, 0, 0};
        int wallX, wallY;
        for (int i = 0; i < 8; i++) {
            wallX = x + offX[i];
            wallY = y + offY[i];
            if (wallX < 0 || wallX >= world.length || wallY < 0 || wallY >= world[0].length) {
                throw new IllegalArgumentException("cannot build wall outside the world");
            }
            world[wallX][wallY] = wallOnlyOnNothing ? (world[wallX][wallY] == Tileset.NOTHING ? Tileset.GREEN_WALL() : world[wallX][wallY]) : Tileset.GREEN_WALL();
        }
    }

    private void buildPathIfNotExist(int x, int y) {
        makeWallSurroundedFloor(x, y);
    }

    private void makePathBetween2Floors(int srcX, int srcY, int dstX, int dstY) {
        if (srcX == dstX && srcY == dstY) {
            return;
        }
        int ampX = (srcX < dstX) ? 1 : -1, ampY = (srcY < dstY) ? 1 : -1;
        // one axis is aligned ,simply finish the rest with a straight hallway
        if (srcX == dstX || srcY == dstY) {
            while (srcY != dstY || srcX != dstX) {
                buildPathIfNotExist(srcX, srcY);
                srcY += (srcY == dstY) ? 0 : ampY;
                srcX += (srcX == dstX) ? 0 : ampX;
            }
        } else {
            if (random.nextBoolean()) {
                //
                while (srcY != dstY) {
                    buildPathIfNotExist(srcX, srcY);
                    srcY += ampY;
                }
                while (srcX != dstX) {
                    buildPathIfNotExist(srcX, srcY);
                    srcX += ampX;
                }
            } else {
                while (srcX != dstX) {
                    buildPathIfNotExist(srcX, srcY);
                    srcX += ampX;
                }
                while (srcY != dstY) {
                    buildPathIfNotExist(srcX, srcY);
                    srcY += ampY;
                }
            }
        }
    }

    private void placeALockedDoor() {
        int lockedDoorRoomIndex = random.nextInt(existingRooms.size());
        Rectangle doorRec = existingRooms.get(lockedDoorRoomIndex);
        int notAvailable;
        int x, y;
        do {
            notAvailable = 0;
            int whichSideLRTB = random.nextInt(4);
            switch (whichSideLRTB) {
                case 0:
                    x = doorRec.left;
                    do {
                        y = random.nextInt(doorRec.getHeight() - 2) + doorRec.bottom + 1;
                        notAvailable++;
                        if (notAvailable >= 10) {
                            break;
                        }
                    } while (!world[x][y].description().equals("wall"));
                    break;
                case 1:
                    x = doorRec.right;
                    do {
                        y = random.nextInt(doorRec.getHeight() - 2) + doorRec.bottom + 1;
                        notAvailable++;
                        if (notAvailable >= 10) {
                            break;
                        }
                    } while (!world[x][y].description().equals("wall"));
                    break;
                case 2:
                    y = doorRec.top;
                    do {
                        x = random.nextInt(doorRec.getWidth() - 2) + doorRec.left + 1;
                        notAvailable++;
                        if (notAvailable >= 10) {
                            break;
                        }
                    } while (!world[x][y].description().equals("wall"));
                    break;
                case 3:
                    y = doorRec.bottom;
                    do {
                        x = random.nextInt(doorRec.getWidth() - 2) + doorRec.left + 1;
                        notAvailable++;
                        if (notAvailable >= 10) {
                            break;
                        }
                    } while (!world[x][y].description().equals("wall"));
                    break;
                default:
                    x = 0;
                    y = 0;
                    break;
            }
            if (notAvailable < 10) {
                world[x][y] = Tileset.LOCKED_DOOR;
            } else {
                notAvailable = -1;
            }
        } while (notAvailable == -1);
    }
}




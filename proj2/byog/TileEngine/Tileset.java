package byog.TileEngine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 * <p>
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 * <p>
 * Ex:
 * world[x][y] = Tileset.FLOOR;
 * <p>
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static final TETile PLAYER = new TETile('@', Color.white, Color.black, "player");
    //default darkgray
    public static final TETile WALL = new TETile('#', new Color(231, 143, 143), Color.darkGray, "wall");

    public static final TETile GREEN_WALL0 = new TETile('#', new Color(46, 106, 100), new Color(90, 173, 175),
            "wall");
    private static final TETile GREEN_WALL1 = new TETile('#', new Color(60, 121, 119), new Color(101, 170, 169),
            "wall");
    private static final TETile GREEN_WALL2 = new TETile('#', new Color(65, 127, 134), new Color(81, 156, 158),
            "wall");
    private static final TETile GREEN_WALL3 = new TETile('#', new Color(81, 156, 158), new Color(65, 127, 134),
            "wall");
    private static final TETile GREEN_WALL4 = new TETile('#', new Color(101, 170, 169), new Color(60, 121, 119),
            "wall");
    private static final TETile GREEN_WALL5 = new TETile('#', new Color(90, 173, 175), new Color(46, 106, 100),
            "wall");
    private static final TETile[] GREEN_COLOR_WALL = {
            GREEN_WALL0,
            GREEN_WALL1,
            GREEN_WALL2,
            GREEN_WALL3,
            GREEN_WALL4,
            GREEN_WALL5,
    };
    private static final Color[] BLUE_COLOR_SET = {
            new Color(0, 0, 205),
            new Color(65, 105, 225),
            new Color(0, 0, 255),
            new Color(30, 144, 255),
            new Color(0, 191, 255),
            new Color(135, 206, 235),
    };
    public static final TETile BLUE_WALL0 = new TETile('#', BLUE_COLOR_SET[0], BLUE_COLOR_SET[5],
            "wall");
    private static final TETile BLUE_WALL1 = new TETile('#', BLUE_COLOR_SET[1], BLUE_COLOR_SET[4],
            "wall");
    private static final TETile BLUE_WALL2 = new TETile('#', BLUE_COLOR_SET[2], BLUE_COLOR_SET[3],
            "wall");
    private static final TETile BLUE_WALL3 = new TETile('#', BLUE_COLOR_SET[3], BLUE_COLOR_SET[2],
            "wall");
    private static final TETile BLUE_WALL4 = new TETile('#', BLUE_COLOR_SET[4], BLUE_COLOR_SET[1],
            "wall");
    private static final TETile BLUE_WALL5 = new TETile('#', BLUE_COLOR_SET[5], BLUE_COLOR_SET[0],
            "wall");
    private static final TETile[] BLUE_COLOR_WALL = {
            BLUE_WALL0,
            BLUE_WALL1,
            BLUE_WALL2,
            BLUE_WALL3,
            BLUE_WALL4,
            BLUE_WALL5,
    };

    public static TETile BLUE_WALL() {
        TETile ret = BLUE_COLOR_WALL[cWallIndex];
        cWallIndex = (cWallIndex + 1) % GREEN_COLOR_WALL.length;
        return ret;
    }

    public static int cWallIndex = 0;

    public static TETile GREEN_WALL() {
        TETile ret = GREEN_COLOR_WALL[cWallIndex];
        cWallIndex = (cWallIndex + 1) % GREEN_COLOR_WALL.length;
        return ret;
    }

    public static final TETile FLOOR = new TETile('·', new Color(128, 192, 128), Color.black,
            "floor");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
}



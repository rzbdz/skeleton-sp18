package byog;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * An abstract class that provide essential preliminary for a WorldGenerator instance;
 */
public abstract class WorldGenerator {
    protected TETile[][] world;
    protected int width, height;
    protected long seed;
    protected Random random;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getSeed() {
        return seed;
    }

    /**
     * Constructor of a world generator with pseudo random seed;
     *
     * @param seed          the seed for pseudo random number generator;
     * @param height        must be larger than 2 or IllegalArgumentException;
     * @param width         must be larger than 2 or IllegalArgumentException;
     * @param isInitNothing determine whether the world would be initialized filling Tileset.NOTHING;
     */
    public WorldGenerator(long seed, int width, int height, boolean isInitNothing) {
        if (width <= 2 || height <= 2) {
            throw new IllegalArgumentException("At lease 3x3 for the boundary wall");
        }
        this.seed = seed;
        random = new Random(seed);
        this.width = width;
        this.height = height;
        this.world = new TETile[width][height];
        if (isInitNothing) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    this.world[i][j] = Tileset.NOTHING;
                }
            }
        }
    }

    /**
     * Basic method that generate a TETile world;
     *
     * @return a world based on the seed of the generator;
     */
    public abstract TETile[][] generate();
}

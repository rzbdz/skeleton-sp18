package byog;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.util.Calendar;

public class MazeGenerator extends WorldGenerator {

    public MazeGenerator(long seed, int width, int height) {
        super(seed, width, height, true);
    }


    public static void main(String[] args) {
        int wd = 80, ht = 30;
        TERenderer rd = new TERenderer();
        MazeGenerator mz = new MazeGenerator(Calendar.getInstance().getTimeInMillis(), 80, 30);
        rd.initialize(wd, ht);
        rd.renderFrame(mz.world);
    }

    @Override
    public TETile[][] generate() {
        return new TETile[0][];
    }
}

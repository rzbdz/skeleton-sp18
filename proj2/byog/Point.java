package byog;

public class Point {
    public int getX() {
        return x;
    }

    public Point add(int offX, int offY) {
        return new Point(x + offX, y + offY);
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "(" + x +
                ", " + y +
                ')';
    }

    private int x;
    private int y;
}

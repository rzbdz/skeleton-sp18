package byog;

public class Rectangle {
    int left, right, bottom, top;

    public Rectangle(int left, int right, int bottom, int top) {
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
    }

    public int getCenterX() {
        return (right - left) / 2 + left;
    }

    public boolean insideRange(Rectangle r) {
        return this.left > r.left && this.right < r.right && this.bottom > r.bottom && this.top < r.top;
    }

    public int getCenterY() {
        return (top - bottom) / 2 + bottom;
    }

    public int getWidth() {
        return right - left + 1;
    }

    public int getHeight() {
        return top - bottom + 1;
    }

    public boolean isRectangleOverlap(Rectangle r, boolean canAdjacent) {
        return isLineInXIntersect(r.left - 1, r.right + 1, this.left - 1, this.right + 1) &&
                isLineInXIntersect(r.bottom - 1, r.top + 1, this.bottom - 1, this.top + 1);
    }

    private boolean isLineInXIntersect(int x11, int x12, int x21, int x22) {
        return Math.max(x11, x21) < Math.min(x12, x22);
    }
}

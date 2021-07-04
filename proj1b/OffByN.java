public class OffByN implements CharacterComparator {
    private final int n;

    public OffByN() {
        n = 1;
    }

    public OffByN(int N) {
        n = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == n;
    }
}

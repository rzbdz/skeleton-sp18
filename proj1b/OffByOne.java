public class OffByOne implements CharacterComparator {
    public OffByOne() {

    }

    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == 1;
    }
}

public class OffByN implements CharacterComparator{
    private final int n;
    OffByN(){
        n = 1;
    }
    OffByN(int N){
        n = N;
    }
    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x-y) == n;
    }
}

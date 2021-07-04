import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    //Uncomment this class once you've created your CharacterComparator interface and OffByOne class. **/
    @Test
    public void test(){
        String[] offByOneChars = {"ab", "ba", "cd", "ef", "&%", "%&", "xy"};
        String[] notOffByOneChars = {"ac", "da", "dd", "ez", "..", "  ", "19"};
        String str;
        for (String offByOneChar : offByOneChars) {
            str = offByOneChar;
            assertTrue(offByOne.equalChars(str.charAt(0), str.charAt(1)));
        }
        for (String  notOffByOneChar :  notOffByOneChars) {
            str =  notOffByOneChar;
            assertFalse(offByOne.equalChars(str.charAt(0), str.charAt(1)));
        }
    }
}

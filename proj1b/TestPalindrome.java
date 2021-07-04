import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator obo = new OffByOne();
    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        StringBuilder actual = new StringBuilder();
        for (int i = 0; i < "persiflage".length(); i++) {
            actual.append(d.removeFirst());
        }
        assertEquals("persiflage", actual.toString());
    }

    @Test
    public void testIsPalindrome() {
        String[] notPalindrome = {"abc", "aA", "cat", "not", "palindrome", "is"};
        String[] isPalindrome = {"a", "d", "diminimid", "palindnilap", ""};
        for (int i = 0; i < notPalindrome.length; i++) {
            assertFalse(palindrome.isPalindrome(notPalindrome[i]));
        }
        for (int i = 0; i < isPalindrome.length; i++) {
            assertTrue(palindrome.isPalindrome(isPalindrome[i]));
        }
    }

    @Test
    public void testInsPalindromeOffByOne(){
        String[] notPalindrome = {"abc", "aA", "cat", "not", "palindrome", "is"};
        String[] isPalindrome = {"a", "d", "dabcdabe", "pabccbo", "", "flake"};
        for (int i = 0; i < notPalindrome.length; i++) {
            assertFalse(palindrome.isPalindrome(notPalindrome[i], obo));
        }
        for (int i = 0; i < isPalindrome.length; i++) {
            assertTrue(palindrome.isPalindrome(isPalindrome[i], obo));
        }
    }
}

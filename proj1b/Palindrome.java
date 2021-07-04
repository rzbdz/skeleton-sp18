import java.util.Objects;

public class Palindrome {
    public Palindrome() {
    }

    /* string to deque in order */
    public Deque<Character> wordToDeque(String word) {
        ArrayDeque<Character> deque = new ArrayDeque<Character>();
        int length = word.length();
        for (int i = 0; i < length; i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        return isPalindromeSimpleLoop(word, Objects::equals);
        //return isRecursive ? isPalindromeRecursive(word) : isPalindromeWithDeque(word);
    }
    public boolean isPalindrome(String word, CharacterComparator cc) {
        return isPalindromeSimpleLoop(word, cc);
        //return isRecursive ? isPalindromeRecursive(word) : isPalindromeWithDeque(word);
    }

    private boolean isPalindromeWithDeque(String word) {
        Deque<Character> deque = wordToDeque(word);
        while (deque.size() > 1) {
            char front = deque.removeFirst();
            char rear = deque.removeLast();
            if (front != rear) {
                return false;
            }
        }
        return true;
    }

    private boolean isPalindromeSimpleLoop(String word, CharacterComparator cc){
        int length = word.length();
        if (length <= 1) {
            return true;
        }
        for (int i = 0; i < length / 2; i++) {
            char c1 = word.charAt(i);
            char c2 = word.charAt(length-i-1);
            if (!cc.equalChars(c1, c2)){
                return false;
            }
        }
        return true;
    }

    private boolean isPalindromeRecursive(String word) {
        if (word.length() < 1) {
            return true;
        } else {
            return helper(word, 0, word.length());
        }
    }

    private boolean helper(String word, int left, int right) {
        if (left >= right - 1) {
            return true;
        } else {
            return helper(word, left + 1, right - 1) &&
                    word.charAt(left) == word.charAt(right - 1);
        }
    }

}

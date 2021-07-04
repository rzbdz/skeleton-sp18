public class Palindrome {
    /* string to deque in order */
    public Deque<Character> wordToDeque(String word) {
        ArrayDeque<Character> deque = new ArrayDeque<Character>();
        int length = word.length();
        for (int i = 0; i < length; i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public Palindrome() {

    }

    private boolean isRecursive;

    public Palindrome(boolean isRecursive) {
        this.isRecursive = isRecursive;
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

    public boolean isPalindrome(String word) {
        return isPalindromeSimpleLoop(word);
        //return isRecursive ? isPalindromeRecursive(word) : isPalindromeWithDeque(word);
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

    private boolean isPalindromeSimpleLoop(String word) {
        int length = word.length();
        if (length <= 1) {
            return true;
        }
        for (int i = 0; i < length / 2; i++) {
            if (word.charAt(i) != word.charAt(length-i-1)){
                return false;
            }
        }
        return true;
    }
}

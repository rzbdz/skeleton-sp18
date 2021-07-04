import java.io.*;

/** This class outputs all palindromes in the words file in the current directory. */
public class PalindromeFinder {

    public static void main(String[] args) throws IOException {
        int minLength = 4;
        Reader in = new FileReader("../library-sp18/data/words.txt");
        Palindrome palindrome = new Palindrome();
        BufferedReader rd = new BufferedReader(in);
        String word;
        while ((word = rd.readLine())!=null) {
            if (word.length() >= minLength && palindrome.isPalindrome(word)) {
                System.out.println(word);
            }
        }
    }
}

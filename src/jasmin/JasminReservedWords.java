/**
 * A hashtable which contains all the reserved words in Jasmin.
 */

package jasmin;

public class JasminReservedWords {
    static java.util.Hashtable reservedWords;

    public static boolean reservedWord(String word) {
        return reservedWords.containsKey(word);
    }

    static {
        reservedWords = new java.util.Hashtable();

        // Add the reserved words in Jasmin
    }
}

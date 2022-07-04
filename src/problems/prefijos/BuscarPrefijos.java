package problems.prefijos;

/**
 *
 *
 * Given a array of X number of strings, find the longest common prefix among all strings present in the array.<p>
 *
 * Input:<p>
 * An array of different items. For example: ['pea', 'pear', 'apple', 'for', 'april', 'apendix', 'peace', 1]<p>
 *
 * Output:<p>
 * Print the longest common prefix as a string in the given array. If no such prefix exists print "-1"(without quotes).<p>
 *
 * Example:<p>
 * Input:<p>
 * ['pea', 'pear', 'apple', 'for', 'april', 'apendix', 'peace', 1]<p>
 *
 * Output:<p>
 * ['pea', 'ap']<p>
 *
 * Explanation:<p>
 * Longest common prefix in all the given strings is 'pea'.<p>
 *
 * @see <a href="https://github.com/zero-to-mastery/coding_challenge-21">Fuente</a>
 */
public class BuscarPrefijos {
    /**
     * Ejecuta el problema.<p>
     * La complejidad de esta resolución se estima de <code>O(nln(n))</code>.<p>
     * Este dato es puramente especulativo.
     * @param palabras Vector de palabras sobre las que se busca los prefijos más largos.
     * @return Vector de prefijos más largos.
     */
    public static String[] longestPrefix(String[] palabras){
        PrefijoNode padre = new PrefijoNode();
        PrefijoNode actual;
        for(String s: palabras) {
            actual = padre;
            for (int c = 0; c < s.length(); c++) {
                actual = actual.addSon(s.charAt(c));
            }
        }
        return padre.toArray();
    }
}

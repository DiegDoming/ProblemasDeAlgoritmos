package problems.subcadenas;

import java.util.Arrays;

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
public class BuscarSubcadenas {
    public static void main(String[] args){

        //TODO Estas dos llamadas se pueden hacer en paralelo
        longestSubstring(new String[]{"aaaaac","bcbaac","aaa", "aacdbcdd","ddadbjaa"});

        System.out.println("Ejecutando al revés");
        longestSubstring(new String[]{"caaaaa","caabcb","aaa","ddcbdcaa","aajbdadd"});
    }
    /**
     * Ejecuta el problema.<p>
     * La complejidad de esta resolución se estima de <code>O(n^2ln(n))</code>.<p>
     * Este dato es puramente especulativo.
     * @param palabras Vector de palabras sobre las que se busca los prefijos más largos.
     * @return Vector de prefijos más largos.
     */
    public static String[] longestSubstring(String[] palabras){
        SubcadenaNode padre = new SubcadenaNode();
        SubcadenaNode actual;
        for(String s: palabras) {
            actual = padre;
            for (int c = 0; c < s.length(); c++) {
                actual = actual.addSon(s.charAt(c));
            }
        }
        SubcadenaNode nuevoArbol = new SubcadenaNode();
        do {
            String[] a = padre.nextTree(nuevoArbol);
            if (a.length == 0 || a[0].isEmpty())
                break;
            System.out.println(Arrays.toString(a));

            padre = nuevoArbol;
            nuevoArbol = new SubcadenaNode();
        } while (true);


        return null;
    }

}

package problems.subcadenas;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Archivos muy grandes requieren aumentar el tamaño de swap memory:
 * -Xss2g
 */


public class BuscarSubcadenas {

    private static final int MIN_SIZE = 5;

    public static void main(String[] args) throws IOException {
        //TODO Estas dos llamadas se pueden hacer en paralelo
        /*longestSubstring(new String[]{"aaaaac","bcbaac","aaa", "aacdbcdd","ddadbjaa"});

        System.out.println("Ejecutando al revés");
        longestSubstring(new String[]{"caaaaa","caabcb","aaa","ddcbdcaa","aajbdadd"});*/

        String s = readFile("C:\\Users\\diego\\OneDrive\\Desktop\\Comprimir\\rew.pdf", StandardCharsets.UTF_8);
        //String s = "aacdbaadefaedbeeefsdsadasdasvdbvasdasdnasvdnasvdnbvsandbvasnbdvnasbvdnbasvdnasvdmnasvdnasvdbvasmdbvasmdbvasmdvasamndvasmdnbvasmnda";

        s = s.replaceAll("[\n\r\f]", "");

        String salida;
        HashSet<String> set = new HashSet<>();
        ArrayList<String> list = new ArrayList<>();
        //2 --> 100%
        //4 --> 50%
        //8 --> 25%
        //int limiteSuperior = s.length()/16;
        int limiteSuperior = 50;
        //La palabra mas larga del castellano son 23 letras
        for (int i = limiteSuperior - 1; i >= MIN_SIZE; i--) {
            for (int j = 0; j < (s.length() % i); j++) {
                System.out.printf("%.2f%%\r", 100 * (((i - 2) / (float) (limiteSuperior - 1)) + j / (s.length() % i)));
                longestSubstring(s.substring(j).split("(?<=\\G.{" + i + "})"), set);
            }

        }
        System.out.printf("%.2f%%\r", 100f);

        list.addAll(set);
        list.sort((i1, i2) -> Integer.compare(i2.length(), i1.length()));
        char currentChar = 250;
        FileWriter myWriter = new FileWriter("C:\\Users\\diego\\OneDrive\\Desktop\\Comprimir\\diccionario.txt");

        StringBuilder result = new StringBuilder();

        System.out.println("Writing\r");
        String elem;
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%.2f%%\r", i / (float) list.size() * 100);
            elem = list.get(i);
            if (elem.length() < MIN_SIZE)
                break;
            if (!s.contains(elem))
                continue;
            //myWriter.write(currentChar + ": " + list.get(i) + "\n");
            result.append(currentChar)/*.append(": ")*/.append(elem).append("\n");
            s = s.replace(elem, String.valueOf(currentChar));
            currentChar++;
        }

        myWriter.write(result.toString());
        myWriter.close();
        try {
            myWriter = new FileWriter("C:\\Users\\diego\\OneDrive\\Desktop\\Comprimir\\salida.txt");
            myWriter.write(s);
        } catch (IOException e) {
            System.out.println("ERROR" + e);
        }

        System.out.println("Writed\r");
        myWriter.close();
    }

    /**
     * Ejecuta el problema.<p>
     * La complejidad de esta resolución se estima de <code>O(n^2ln(n))</code>.<p>
     * Este dato es puramente especulativo.
     *
     * @param palabras Vector de palabras sobre las que se busca los prefijos más largos.
     * @return Vector de prefijos más largos.
     */
    public static void longestSubstring(String[] palabras, Set<String> set) {
        SubcadenaNode padre = new SubcadenaNode();
        SubcadenaNode actual;
        for (String s : palabras) {
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
            set.addAll(Arrays.asList(a));

            padre = nuevoArbol;
            nuevoArbol = new SubcadenaNode();
        } while (true);
    }

    static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}

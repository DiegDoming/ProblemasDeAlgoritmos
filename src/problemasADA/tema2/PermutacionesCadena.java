package problemasADA.tema2;

public class PermutacionesCadena {
    /**
     * Programa que lea una cadena de caracteres, compruebe no existe en ella ningún carácter repetido, y<p>
     * en ese caso escriba todas las cadenas que se pueden formar por permutaciones de los caracteres de la cadena<p>
     * original.<p>
     * @param cadena Cadena a permutar
     */
    public static void permutacionesDeCadenaIni(String cadena){
        permutacionesDeCadena("", cadena);
    }
    private static void permutacionesDeCadena(String cabeza, String cola) {
        if(cola.isEmpty()) {
            System.out.println(cabeza + cola);
            return;
        }
        for(int i = 0; i < cola.length(); i++) {
            permutacionesDeCadena(cabeza + cola.substring(i, i+1), cola.substring(0, i).concat(cola.substring(i + 1)));
        }
    }

}
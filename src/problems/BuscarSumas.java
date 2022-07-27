package problems;

import java.util.HashMap;

/**
 * @author Diego Fernandez de Valderrama
 */
public class BuscarSumas {
    /**
     * Dada una lista de numeros y un valor. Encuentra todas las sumas de dos valores de la lista, que den ese valor inicial.<p>
     * Tiene una complejidad de <code>O(n)</code>.<p>
     * <p>
     * Suponemos que siempre podemos encontrar la suma en la Lista.<p>
     * <p>
     * La funcion imprime dos veces la misma combinacion, cada vez en una direccion.<p>
     * Ej. (x,y) y luego (y,x).<p>
     *
     * @param vector   Lista de elementos entre los que vamos a buscar una suma.
     * @param objetivo Valor que debemos alcanzar sumando dos valores del Vector.
     */
    public static void findSumas(int[] vector, int objetivo) {
        //Cada elemento lo añadimos al HashMap, con una Key igual al valor que necesita para alcanzar el objetivo.
        //Si el valor ya esta, lo notificamos
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        int pareja;
        int valorVectorI;
        for (int j : vector) {
            //Sacamos el acceso a vector a una variable para reducir accesos a vector.
            valorVectorI = j;
            //Key, Value
            hashMap.put(objetivo - valorVectorI, valorVectorI);
        }

        for (int j : vector) {
            //Sacamos el acceso a vector a una variable para reducir accesos a vector.
            valorVectorI = j;
            try {
                pareja = hashMap.get(valorVectorI);
                System.out.println("(" + valorVectorI + "," + pareja + ")");
            } catch (Exception ignored) {
            }
        }

        //Tanto añadir como eliminar elementos de un HashMap es de O(1).
        //Recorremos dos veces el vector luego 2n --> 0(n).
    }
}

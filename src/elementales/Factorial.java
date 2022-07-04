package elementales;

/**
 * @author Diego Fernandez de Valderrama
 */
public class Factorial {
    /**
     * Implementación <code>O(n)</code> de la operación Factorial.
     * @param val Valor del cual calcular el factorial.
     * @return Factorial del valor calculado.
     *
     * @throws IllegalArgumentException Si el argumento es negativo.
     */
    public static int factorial(int val){
        if(val < 0)
            throw new IllegalArgumentException("val debe ser positivo");
        if(val < 2)
            return 1;
        int salida = 1;
        for(int i = 2; i <= val; i++)
            salida *= i;
        return salida;
    }
}

package problems;

import exceptions.RunOutOfBoundsException;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Diego Fernandez de Valderrama
 */
public class CribaErastotenes {
    /**
     * Dado un valor, encuentra todos los numeros primos menores o iguales a este.<p>
     * Devuelve primos, empezando desde 0.<p>
     * Para ello se utliza el metodo de la criba de Erastotenes.<p>
     *
     * Este metodo se basa en usar un vector binario Booleano por lo tanto, su complejidad espacial es de: <code>O(n)</code>.<p>
     * Y tiene una complejidad temporal "teorica" de <code>O(nlog(n))</code>.<p>
     * @param limiteSuperior Limite Superior de los numeros primos a encontrar.
     * @return Una lista Formada por todos los numero primos menores o iguales a <code>limiteSuperior</code>.
     * @throws RunOutOfBoundsException No deberia producirse nunca, de darse --- Revisar codigo.
     */
    public static List<Integer> cribaErastotenesBinaria(int limiteSuperior) throws RunOutOfBoundsException {
        //Incrementamos el limte en uno, pues empezamos en 0 y asi alcanzamos el limite deseado.
        limiteSuperior++;
        boolean[] vectorBinario = new boolean[limiteSuperior];
        //Si el vector esta en false --> Esa casilla es explorable
        int m = 2;
        while(2*m < limiteSuperior){
            for(int i = 2*m; i<limiteSuperior;i+=m) {
                vectorBinario[i] = true;
            }
            m = siguienteCandidato(m,vectorBinario);
        }

        ArrayList<Integer> salida = new ArrayList<>();
        //Seleccionamos Solo los numeros primos
        for(int i = 0; i < vectorBinario.length; i++)
            if(!vectorBinario[i])
                salida.add(i);
        return salida;
    }

    /**
     * Encuentra el siguiente elemento del vector, que vale false. <p>
     * @param m Indice actual.
     * @param vectorBinario Vector sobre el que buscar.
     * @return Indice del siguiente elemento con valor false.
     * @throws RunOutOfBoundsException No deberia producirse nunca, de darse --- Revisar codigo.
     */
    private static int siguienteCandidato(int m, boolean[] vectorBinario) throws RunOutOfBoundsException {
        for(int i = m+1; i < vectorBinario.length; i++)
            if(!vectorBinario[i])
                return i;
        throw new RunOutOfBoundsException("No se ha encontrado la siguiente posicion");
    }
}

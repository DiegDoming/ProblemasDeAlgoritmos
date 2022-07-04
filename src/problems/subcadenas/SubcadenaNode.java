package problems.subcadenas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Nodo que contiene un valor booleano, y apunta a otros nodos mediante un <code>char</code>.<p>
 * Para almacenar los otros nodos se basa en <code>HashMap</code>.
 *
 * @author Diego Fernandez de Valderrama
 */
public class SubcadenaNode {
    private boolean check = false;
    private final HashMap<Character, SubcadenaNode> hijos;

    public SubcadenaNode(){
        this.hijos = new HashMap<>();
    }

    /**
     * Añade un hijo al Nodo, actual. De este ya estar añadido cambia su estado a <code>true</code>.<p>
     * Implicando que tiene más de una aparición, y por lo tanto es una entrada válida.
     * @param c Character a añadir como hijo del Nodo.
     * @return El nodo hijo, ya existente o creado.
     */
    public SubcadenaNode addSon(char c){
        SubcadenaNode son;
        if(!hijos.containsKey(c)) {
            son = new SubcadenaNode();
            hijos.put(c, son);
        }else {
            son = hijos.get(c);
            son.check = true;
        }

        return son;
    }

    private void addSonRecIni(char c, SubcadenaNode nextTree){
        hijos.get(c).addSonRec(nextTree.addSon(c));
    }

    private void addSonRec(SubcadenaNode nextTree){
        for (Map.Entry<Character, SubcadenaNode> entry : hijos.entrySet()) {
            Character key = entry.getKey();
            hijos.get(key).addSonRec(nextTree.addSon(key));
        }
    }
    private boolean hasValidSon(){
        for (Map.Entry<Character, SubcadenaNode> entry : hijos.entrySet()) {
            if (entry.getValue().check)
                return true;
        }
        return false;
    }

    /**
     * Dado un Nodo, obtiene todos sus posibles combinaciones de hijos.<p>
     * Todas estas combinaciones cumplen la condición de que aparece más de una vez su prefijo.
     * @return Vector de Prefijos.
     */
    public String[] nextTree(SubcadenaNode nuevoArbol){
        String tmp = "";
        return toArrayComplex(tmp, nuevoArbol).toArray(new String[0]);
    }

    /**
     * Recorre el Arbol, hasta llegar a un Nodo sin hijos, o en el que todos los elementos son  <code>False</code>.
     * @param tmp String acumulado del paso anterior
     * @return Lista con los elementos encontrados.
     */
    private List<String> toArrayComplex(String tmp,SubcadenaNode nuevoArbol){
        List<String> salidas = new ArrayList<>();
        boolean valid = false;
        if(hijos.isEmpty()){
            salidas.add(tmp);
            return salidas;
        }
        for (Map.Entry<Character, SubcadenaNode> entry : hijos.entrySet()) {
            Character key = entry.getKey();
            SubcadenaNode value = entry.getValue();
            if (value.check) {
                valid = true;
                salidas.addAll(value.toArrayComplex(tmp + key,nuevoArbol));
            }else{
                if(tmp.length() == 1 && !hasValidSon())
                    addSonRecIni(key, nuevoArbol.addSon(tmp.charAt(0)));
                else
                    addSonRecIni(key,nuevoArbol);
            }
        }
        if(!valid && tmp.length() != 1) {
                salidas.add(tmp);
        }
        return salidas;
    }
}

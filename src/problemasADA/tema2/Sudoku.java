package problemasADA.tema2;

public class Sudoku {
    /**
     * Para una matriz dada 9x9, siendo las casillas vacias 0<p>
     * completa el sudoku.
     *
     * @param tablero Tablero 9x9 de Sudoku.
     */
    public static void solve(int[][] tablero) {
        if(tablero == null)
            throw new NullPointerException();
        if(tablero.length != 9 || tablero[0].length != 9)
            throw new IllegalArgumentException("Matrix bust be 9x9");

        if(!validBoard(tablero))
            throw new IllegalArgumentException("Values must be in range");


        int candidato;
        for (int fila = 0; fila < tablero.length; fila++) {
            for (int columna = 0; columna < tablero[fila].length; columna++) {
                candidato = calcularPosibilidades(tablero, fila, columna);
                if (candidato != -1)
                    llenarCasilla(tablero, fila, columna, candidato);
            }
        }
    }

    private static boolean validBoard(int[][] tablero){
        for(int[] i: tablero)
            for(int j: i)
                if(j<0 || j>9)
                    return false;
        return true;
    }

    /**
     * Para una casilla dada del tablero de Sudoku, introduce el valor y vuelve toda<p>
     * casilla que se ha podido ver influenciada por la introduccion de este valor.<p>
     * <p>
     * Una vez resuelto, mediante recursividad va llamando a las casillas influenciadas por las<p>
     * casillas ya cambiadas, asi asta resolver el Sudoku.<p>
     *
     * @param tablero Tablero 9x9 de Sudoku.
     * @param fila    Fila en la que se encuentra la casilla a llenar.
     * @param columna Columna en la que se encuentra la casilla a llenar.
     * @param valor   Valor a introducir en la casilla.
     */
    private static void llenarCasilla(int[][] tablero, int fila, int columna, int valor) {
        tablero[fila][columna] = valor;
        int candidato;
        for (int f = 0; f < tablero.length; f++) {
            candidato = calcularPosibilidades(tablero, f, columna);
            if (candidato != -1)
                llenarCasilla(tablero, f, columna, candidato);
        }
        for (int c = 0; c < tablero.length; c++) {
            candidato = calcularPosibilidades(tablero, fila, c);
            if (candidato != -1)
                llenarCasilla(tablero, fila, c, candidato);
        }

        for (int f = fila - fila % 3; f < fila - fila % 3 + 3; f++)
            for (int c = columna - columna % 3; c < columna - columna % 3 + 3; c++) {
                candidato = calcularPosibilidades(tablero, f, c);
                if (candidato != -1)
                    llenarCasilla(tablero, f, c, candidato);
            }
    }

    /**
     * Para una casilla dada en un tablero de Sudoku, comprueba si esta tiene un solo valor<p>
     * posible, y de ser asi lo devuelve.<p>
     * <p>
     * Si hay mas de un valor posible la ejeuccion se detiene y devuelve un <code>-1</code>.<p>
     *
     * @param tablero Tablero 9x9 de Sudoku.
     * @param fila    Fila en la que se encuentra la casilla a comprobar.
     * @param columna Columna en la que se encuentra la casilla a comprobar.
     * @return Valor a introducir en la casilla, en caso de haber mas de un candidato, devuevle <code>-1</code>
     */
    private static int calcularPosibilidades(int[][] tablero, int fila, int columna) {
        if (tablero[fila][columna] != 0)
            return -1;
        int candidato = 0;

        for (int i = 1; i <= 9; i++) {
            boolean esta = false;
            for (int[] ints : tablero)
                if (ints[columna] == i) {
                    esta = true;
                    break;
                }

            if (!esta)
                for (int c = 0; c < tablero.length; c++)
                    if (tablero[fila][c] == i) {
                        esta = true;
                        break;
                    }

            if (!esta)
                for (int f = fila - fila % 3; f < fila - fila % 3 + 3; f++)
                    for (int c = columna - columna % 3; c < columna - columna % 3 + 3; c++) {
                        if (tablero[f][c] == i) {
                            esta = true;
                            break;
                        }
                    }

            if (!esta) {
                if (candidato != 0)
                    return -1;
                candidato = i;
            }
        }
        return candidato;
    }
}

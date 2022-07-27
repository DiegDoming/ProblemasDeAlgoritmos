package problemasADA.tema2;

public class Sudoku {
    public static void main(String[] args) {
        int[][] tablero = new int[9][9];
        tablero[0][0] = 5;
        tablero[0][1] = 3;
        tablero[0][4] = 7;

        tablero[1][0] = 6;
        tablero[1][3] = 1;
        tablero[1][4] = 9;
        tablero[1][5] = 5;

        tablero[2][1] = 9;
        tablero[2][2] = 8;
        tablero[2][7] = 6;

        tablero[3][0] = 8;
        tablero[3][4] = 6;
        tablero[3][8] = 3;

        tablero[4][0] = 4;
        tablero[4][3] = 8;
        tablero[4][5] = 3;
        tablero[4][8] = 1;

        tablero[5][0] = 7;
        tablero[5][4] = 2;
        tablero[5][8] = 6;

        tablero[6][1] = 6;
        tablero[6][6] = 2;
        tablero[6][7] = 8;

        tablero[7][3] = 4;
        tablero[7][4] = 1;
        tablero[7][5] = 9;
        tablero[7][8] = 5;

        tablero[8][4] = 8;
        tablero[8][7] = 7;
        tablero[8][8] = 9;

        printMatrix(tablero);
        solveSudoku(tablero);
        System.out.println();
        printMatrix(tablero);
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }

    /**
     * Para una matriz dada 9x9, siendo las casillas vacias 0<p>
     * completa el sudoku.
     *
     * @param tablero Tablero 9x9 de Sudoku.
     */
    public static void solveSudoku(int[][] tablero) {
        int candidato;
        for (int fila = 0; fila < tablero.length; fila++) {
            for (int columna = 0; columna < tablero[fila].length; columna++) {
                candidato = calcularPosibilidades(tablero, fila, columna);
                if (candidato != -1)
                    llenarCasilla(tablero, fila, columna, candidato);
            }
        }
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

package objects.laberinto;

import java.util.Random;

public class GeneradorLaberinto {

    private static final Random rand = new Random();

    private static final int WALL = 0;
    private static final int BORDER = 1;
    private final int row;
    private final int col;
    private final int[][] maze;


    /**
     * Dado una matriz booleana, en la cual<p>
     * <code>false</code> implica que es una pared del laberinto.<p>
     * <code>true</code> implica que no es una pared del laberinto.<p>
     * <p>
     * Genera los datos necesarios para ramificar este laberinto.
     * Las coordenadas de la matriz booleana dada, nunca se conectarán entre sí mediante pasillos generados.
     *
     * @param laberinto Navegabilidad de las casillas del laberinto.
     */
    public GeneradorLaberinto(boolean[][] laberinto) {
        row = laberinto.length;
        col = laberinto[0].length;
        maze = new int[row][col];
        int current = -1;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (laberinto[i][j])
                    maze[i][j] = current--;
                else
                    maze[i][j] = WALL;
            }
        }
    }

    /**
     * Obtiene el laberinto en su estado actual como matriz booleana.
     *
     * @return LaberintoBinario
     */
    public boolean[][] getLaberinto() {
        boolean[][] laberinto = new boolean[row][col];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                laberinto[i][j] = (maze[i][j] != WALL);

        return laberinto;
    }

    /**
     * Ramifica el laberinto para una profundidad maxima de <code>maxDeep</code><p>
     *
     * @param maxDeep Profundidad maxima a la que el laberinto ramificara.
     */
    public void generateLabyrinth(int maxDeep) {
        if (BORDER < 1)
            return;

        if (maxDeep > row * col)
            maxDeep = row * col;

        for (int i = 0; i < maxDeep; i++)
            generateLabyrinthStep();
    }

    /**
     * Recorre la matriz y da un paso desde cada casilla que sea pasillo
     */
    private void generateLabyrinthStep() {
        int b = BORDER + 1;

        int[][] mazeCpy = cpyMaze();

        for (int i = b; i < row - b; i++)
            for (int j = b; j < col - b; j++)
                if (mazeCpy[i][j] != WALL)
                    processPoint(i, j);
    }

    /**
     * Genera una copia de la matriz del laberinto
     *
     * @return Copia de la matriz
     */
    private int[][] cpyMaze() {
        int[][] mazeCpy = new int[maze.length][];
        for (int i = 0; i < maze.length; i++)
            mazeCpy[i] = maze[i].clone();

        return mazeCpy;
    }

    private void processPoint(int x, int y) {
        int current = Math.abs(maze[x][y]);
        switch (ran(3)) {
            case 0 -> {
                if (validPoint(current, x + 1, y))
                    maze[x + 1][y] = current;
            }
            case 1 -> {
                if (validPoint(current, x - 1, y))
                    maze[x - 1][y] = current;
            }
            case 2 -> {
                if (validPoint(current, x, y + 1))
                    maze[x][y + 1] = current;
            }
            case 3 -> {
                if (validPoint(current, x, y - 1))
                    maze[x][y - 1] = current;
            }
            default -> throw new IllegalStateException("Unexpected value: " + ran(3));
        }
    }

    /**
     * Comprueba si un punto es válido para la generación
     *
     * @param current Valor del pasillo actual
     * @param x       Coordenada X
     * @param y       Coordenada Y
     * @return Resultado de la comprobación
     */
    private boolean validPoint(int current, int x, int y) {

        //Check if the current point is part of the main path
        if (maze[x][y] < 0)
            return false;

        //Check if it is surrounded by walls
        if (checkWalls(x, y))
            return true;

        //Checks if there is a neighbor from another branch
        if (checkNeighbor(current, x, y))
            return false;

        //Checks if the generation will generate a 2x2 area
        return !checkNoBigBox(current, x, y);
    }

    /**
     * Comprueba si la casilla actual está rodeada por 3 muros
     *
     * @param x Coordenada X
     * @param y Coordenada Y
     * @return Resultado de la comprobación
     */
    private boolean checkWalls(int x, int y) {
        return checkAround(x, y, WALL) == 3;
    }

    /**
     * Comprueba si la casilla actual, esa rodeada solo por elementos iguales a <code>current</code>
     *
     * @param current Elemento al que igualarse para la comprobación
     * @param x       Coordenada X
     * @param y       Coordenada Y
     * @return Resultado de la comprobación
     */
    private boolean checkNeighbor(int current, int x, int y) {
        return checkAround(x, y, current) != 4;
    }

    /**
     * Dada unas coordenadas cuenta cuantas de las casillas contiguas son iguales a <code>value</code>
     *
     * @param x     Coordenada X
     * @param y     Coordenada Y
     * @param value Elemento al que igualarse en la comprobación
     * @return Número de casillas iguales a <code>value</code>
     */
    private int checkAround(int x, int y, int value) {
        int count = 0;
        if (maze[x + 1][y] == value)
            count++;
        if (maze[x - 1][y] == value)
            count++;
        if (maze[x][y + 1] == value)
            count++;
        if (maze[x][y - 1] == value)
            count++;

        return count;
    }

    /**
     * Comprueba si la conversión de esta casilla a pasillo, produciría un área de 2x2.
     *
     * @param current Valor del pasillo actual
     * @param x       Coordenada X
     * @param y       Coordenada Y
     * @return Resultado de la comprobación
     */
    private boolean checkNoBigBox(int current, int x, int y) {
        if (maze[x + 1][y] == current && maze[x][y + 1] == current && maze[x + 1][y + 1] == current)
            return true;

        if (maze[x + 1][y] == current && maze[x][y - 1] == current && maze[x + 1][y - 1] == current)
            return true;

        if (maze[x - 1][y] == current && maze[x][y + 1] == current && maze[x - 1][y + 1] == current)
            return true;

        return maze[x - 1][y] == current && maze[x][y - 1] == current && maze[x - 1][y - 1] == current;
    }

    /**
     * Genera un valor aleatorio [0,<code>max</code>]
     *
     * @param max Limite superior del valor aleatorio
     * @return Valor aleatorio
     */
    private static int ran(int max) {
        max++;
        return rand.nextInt(max);
    }

    /**
     * Convierte la matriz de booleanos a string <p>
     * ◼ : Navegable <p>
     * ◻ :Pared
     *
     * @return String del laberinto
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (boolean[] booleans : getLaberinto()) {
            for (boolean aBoolean : booleans) {
                if (aBoolean)
                    sb.append("◼");
                else
                    sb.append("◻");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

}

package objects.laberinto;

import java.awt.*;
import java.util.Random;

public class GeneradorLaberinto {


    private static final Random rand = new Random();

    private static final int WALL = 0;
    private static final int BORDER = 1;
    private final int row;
    private final int col;
    private final Point ini;
    private final Point end;
    private final int[][] maze;


    public GeneradorLaberinto(int row, int col, Point ini, Point end) {

        if(row <= 0)
            throw new IllegalArgumentException("row must be positive");
        if(col <= 0)
            throw new IllegalArgumentException("col must be positive");

        this.row = row;
        this.col = col;

        if(ini == null)
            throw new NullPointerException();

        if(end == null)
            throw new NullPointerException();

        if(checkPoint(ini))
            throw new IllegalArgumentException("Ini must be contained in row and col");
        if(checkPoint(end))
            throw new IllegalArgumentException("End must be contained in row and col");
        if(checkCorner(ini))
            throw new IllegalArgumentException("Ini cannot be a corner");
        if(checkCorner(end))
            throw new IllegalArgumentException("End cannot be a corner");

        maze = new int[row][col];

        this.ini = ini;
        this.end = end;

        generatePath(parsePoint(ini), parsePoint(end));

        maze[ini.x][ini.y] = -1;
        maze[end.x][end.y] = -1;
    }

    private boolean checkPoint(Point point) {
        return point.x < 0 || point.x >= row || point.y < 0 || point.y >= col;
    }

    private boolean checkCorner(Point point){
        return (point.x == 0 && point.y == 0) || (point.x == row-1 && point.y == 0) || (point.x == 0 && point.y == col -1) || (point.x == row-1 && point.y == col-1);
    }

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
    public GeneradorLaberinto(boolean[][] laberinto, Point ini, Point end) {

        if(laberinto == null)
            throw new NullPointerException();

        if(ini == null)
            throw new NullPointerException();

        if(end == null)
            throw new NullPointerException();

        row = laberinto.length;
        col = laberinto[0].length;

        if(checkPoint(ini))
            throw new IllegalArgumentException("Ini must be contained in row and col");
        if(checkPoint(end))
            throw new IllegalArgumentException("End must be contained in row and col");
        if(checkCorner(ini))
            throw new IllegalArgumentException("Ini cannot be a corner");
        if(checkCorner(end))
            throw new IllegalArgumentException("End cannot be a corner");

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

        this.end = end;
        this.ini = ini;

        maze[ini.x][ini.y] = -1;
        maze[end.x][end.y] = -1;
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
     * Ramifica el laberinto para una profundidad maxima de <code>maxDepth</code><p>
     *
     * @param maxDepth Profundidad maxima a la que el laberinto ramificara.
     */
    public void generateLabyrinth(int maxDepth) {
        if (BORDER < 1)
            return;

        if (maxDepth > row * col)
            maxDepth = row * col;

        for (int i = 0; i < maxDepth; i++)
            generateLabyrinthStep();
    }

    private Point parsePoint(Point point) {
        Point p = new Point(point.x, point.y);
        if (p.x == 0) {
            p.x++;
        }
        if (p.y == 0) {
            p.y++;
        }
        if (p.x == row - 1) {
            p.x--;
        }
        if (p.y == col - 1) {
            p.y--;
        }

        return p;
    }

    /**
     * Recorre la matriz y da un paso desde cada casilla que sea pasillo
     */
    private void generateLabyrinthStep() {
        int[][] mazeCpy = cpyMaze();

        for (int i = BORDER; i < row - BORDER; i++)
            for (int j = BORDER; j < col - BORDER; j++)
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

    private void generatePath(Point ini, Point end) {

        Point p;
        Point p2;
        int count = 0;
        int current;
        while (maze[end.x][end.y] == WALL) {
            cleanMaze();
            maze[ini.x][ini.y] = -1;
            current = -1;
            p = new Point(ini.x, ini.y);
            p2 = new Point(ini.x, ini.y);
            while (maze[end.x][end.y] == WALL) {
                p = processPoint(p.x, p.y);
                if (p2.equals(p)) {
                    count++;
                    if (count == 4)
                        break;
                } else {
                    count = 0;
                }

                maze[p.x][p.y] = current--;
                p2 = p;
            }
        }

    }

    private void cleanMaze() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                maze[i][j] = WALL;
            }
        }
    }

    private Point processPoint(int x, int y) {
        int current = Math.abs(maze[x][y]);
        switch (ran(3)) {
            case 0 -> {
                if (x + 1 < row && validPoint(current, x + 1, y)) {
                    maze[x + 1][y] = current;
                    return new Point(x + 1, y);
                }
            }
            case 1 -> {
                if (x - 1 >= 0 && validPoint(current, x - 1, y)) {
                    maze[x - 1][y] = current;
                    return new Point(x - 1, y);
                }
            }
            case 2 -> {
                if (y + 1 < col && validPoint(current, x, y + 1)) {
                    maze[x][y + 1] = current;
                    return new Point(x, y + 1);
                }
            }
            case 3 -> {
                if (y - 1 >= 0 && validPoint(current, x, y - 1)) {
                    maze[x][y - 1] = current;
                    return new Point(x, y - 1);
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + ran(3));
        }

        return new Point(x, y);
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
        if ((x + 1) < row && maze[x + 1][y] == value)
            count++;
        if ((x - 1) >= 0 && maze[x - 1][y] == value)
            count++;
        if ((y + 1) < col && maze[x][y + 1] == value)
            count++;
        if ((y - 1) >= 0 && maze[x][y - 1] == value)
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
     * ⚫ : Entrada <p>
     * ✪ : Salida <p>
     * ◻ :Pared
     *
     * @return String del laberinto
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                sb.append(pointToChar(i,j));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private char pointToChar(int x, int y){
        if (x == ini.x && y == ini.y)
            return '⚫';
        if (x == end.x && y == end.y)
            return '✪';
        if (maze[x][y] == WALL)
            return '◻';
        else
            return'◼';
    }

}

package objects.laberinto;

import exceptions.NotPathFromWallException;

import java.awt.*;

/**
 * @author Diego Fernandez de Valderrama
 */
public class LaberintoBinario {

    private enum EstadoLaberinto {
        NO_RESUELTO,
        RESUELTO
    }

    private static final int NO_PARENT = -1;


    private final boolean[][] pared;
    private final int[][] adjacencyMatrix;
    private int[] parents;
    private static int[] shortestDistances;
    private EstadoLaberinto estado;

    /**
     * Dado una matriz booleana, en la cual<p>
     * <code>false</code> implica que es una pared del laberinto.<p>
     * <code>true</code> implica que no es una pared del laberinto.<p>
     * <p>
     * Genera los datos necesarios para resolver este laberinto.
     *
     * @param laberinto Navegabilidad de las casillas del laberinto.
     */
    public LaberintoBinario(boolean[][] laberinto) {
        this.pared = laberinto;
        this.estado = EstadoLaberinto.NO_RESUELTO;

        this.adjacencyMatrix = new int[laberinto.length * laberinto[0].length][laberinto.length * laberinto[0].length];
        for (int i = 0; i < laberinto.length * laberinto[0].length; i++) {
            for (int j = 0; j < laberinto.length * laberinto[0].length; j++)
                adjacencyMatrix[i][j] = 0;
        }

        for (int i = 0; i < laberinto.length; i++) {
            for (int j = 0; j < laberinto[0].length; j++) {
                procesaPunto(i, j);
            }
        }
    }

    /**
     * Obtiene todos los caminos mas cortos para llegar a la salida aplicando el algoritmo de dijkstra.<p>
     * Las coordenadas deben pertenecer a un elemento del laberinto que no sea una pared.<p>
     *
     * @param x Coordenada X de la salida del laberinto
     * @param y Coordenada Y de la salida del laberinto
     * @return El propio objeto, por conveniencia operacional
     * @throws NotPathFromWallException Si las coordenadas introducidas pertenecen a una casilla que es una pared.
     */
    public LaberintoBinario resuelve(int x, int y) throws NotPathFromWallException {
        if (!pared[x][y])
            throw new NotPathFromWallException("La coordenada " + x + ", " + y + " se trata de una pared.");
        this.parents = dijkstra(adjacencyMatrix, coordGlobal(x, y));
        this.estado = EstadoLaberinto.RESUELTO;

        return this;
    }

    /**
     * Obtiene todos los caminos mas cortos para llegar a la salida aplicando el algoritmo de dijkstra.<p>
     * Las coordenadas deben pertenecer a un elemento del laberinto que no sea una pared.<p>
     *
     * @param p Coordenadas de la salida del laberinto
     * @return El propio objeto, por conveniencia operacional
     * @throws NotPathFromWallException Si las coordenadas introducidas pertenecen a una casilla que es una pared.
     */
    public LaberintoBinario resuelve(Point p) throws NotPathFromWallException {
        if (!pared[p.x][p.y])
            throw new NotPathFromWallException("La coordenada " + p.x + ", " + p.y + " se trata de una pared.");
        this.parents = dijkstra(adjacencyMatrix, coordGlobal(p.x, p.y));
        this.estado = EstadoLaberinto.RESUELTO;

        return this;
    }

    /**
     * Obtiene el camino mas corto a la salida, desde una posición dada<p>
     * Para esto requiere, que se haya establecido previamente la salida con <code>resuelve()</code><p>
     * El formato de la salida es un vector, con la serie de pasos a tomar.
     *
     * @param x Coordenada X desde la cual queremos llegar a la salida.
     * @param y Coordenada Y desde la cual queremos llegar a la salida.
     * @return Vector con el camino mas corto
     * @throws NotPathFromWallException Si las coordenadas introducidas pertenecen a una casilla que es una pared.
     */
    public Point[] camino(int x, int y) throws NotPathFromWallException {
        if (!pared[x][y])
            throw new NotPathFromWallException("La coordenada " + x + ", " + y + " se trata de una pared.");
        Point[] resultado = null;
        if (this.estado == EstadoLaberinto.RESUELTO) {
            int curr = coordGlobal(x, y);
            resultado = new Point[shortestDistances[coordGlobal(x, y)] + 1];
            resultado[0] = new Point(x, y);
            for (int i = 1; i < resultado.length; i++) {
                resultado[i] = traduceCoord(parents[curr]);
                curr = parents[curr];
            }
        }
        return resultado;
    }

    /**
     * Obtiene el camino mas corto a la salida, desde una posición dada<p>
     * Para esto requiere, que se haya establecido previamente la salida con <code>resuelve()</code><p>
     * El formato de la salida es un vector, con la serie de pasos a tomar.
     *
     * @param p Coordenadas desde la cual queremos llegar a la salida.
     * @return Vector con el camino mas corto
     * @throws NotPathFromWallException Si las coordenadas introducidas pertenecen a una casilla que es una pared.
     */
    public Point[] camino(Point p) throws NotPathFromWallException {
        if (!pared[p.x][p.y])
            throw new NotPathFromWallException("La coordenada " + p.x + ", " + p.y + " se trata de una pared.");
        Point[] resultado = null;
        if (this.estado == EstadoLaberinto.RESUELTO) {
            int curr = coordGlobal(p.x, p.y);
            resultado = new Point[shortestDistances[coordGlobal(p.x, p.y)] + 1];
            resultado[0] = new Point(p.x, p.y);
            for (int i = 1; i < resultado.length; i++) {
                resultado[i] = traduceCoord(parents[curr]);
                curr = parents[curr];
            }
        }
        return resultado;
    }

    /**
     * Conectamos con el vecino de la derecha y el de abajo
     *
     * @param x Coordenada X
     * @param y Coordenada Y
     */
    private void procesaPunto(int x, int y) {
        if (pared[x][y]) {
            if (y < pared[0].length - 1 && pared[x][y + 1]) {
                adjacencyMatrix[coordGlobal(x, y)][coordGlobal(x, y + 1)] = 1;
                adjacencyMatrix[coordGlobal(x, y + 1)][coordGlobal(x, y)] = 1;
            }
            if (x < pared.length - 1 && pared[x + 1][y]) {
                adjacencyMatrix[coordGlobal(x, y)][coordGlobal(x + 1, y)] = 1;
                adjacencyMatrix[coordGlobal(x + 1, y)][coordGlobal(x, y)] = 1;
            }
        }
    }

    private int coordGlobal(int x, int y) {
        return x * pared.length + y;
    }

    private Point traduceCoord(int coordGlobal) {
        return new Point((coordGlobal / pared.length), coordGlobal % pared.length);
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
        for (boolean[] booleans : pared) {
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

    /**
     * Funcion que haya el camino mas corto a un punto, mediante el algoritmo de dijkstra<p>
     * Esta version se basa en la matriz de adyacencia.
     *
     * @param adjacencyMatrix Matriz de adyacencia
     * @param startVertex     Punto al que se le buscan los caminos mas cortos
     * @return Vector con todos los caminos mas cortos hasta <code>startVertex</code>
     */
    private static int[] dijkstra(int[][] adjacencyMatrix, int startVertex) {
        int nVertices = adjacencyMatrix[0].length;

        // shortestDistances[i] will hold the
        // shortest distance from src to i
        shortestDistances = new int[nVertices];

        // added[i] will true if vertex i is
        // included / in shortest path tree
        // or shortest distance from src to
        // i is finalized
        boolean[] added = new boolean[nVertices];

        // Initialize all distances as
        // INFINITE and added[] as false
        for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }

        // Distance of source vertex from
        // itself is always 0
        shortestDistances[startVertex] = 0;

        // Parent array to store shortest
        // path tree
        int[] parents = new int[nVertices];

        // The starting vertex does not
        // have a parent
        parents[startVertex] = NO_PARENT;

        // Find shortest path for all
        // vertices
        for (int i = 1; i < nVertices; i++) {

            // Pick the minimum distance vertex
            // from the set of vertices not yet
            // processed. nearestVertex is
            // always equal to startNode in
            // first iteration.
            int nearestVertex = -1;
            int shortestDistance = Integer.MAX_VALUE;
            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }

            // Mark the picked vertex as
            // processed

            if (nearestVertex == -1)
                continue;
            added[nearestVertex] = true;

            // Update dist value of the
            // adjacent vertices of the
            // picked vertex.
            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

                if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex])) {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
                }
            }
        }

        return parents;
    }
}

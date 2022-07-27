package exceptions;

public class NotPathFromWallException extends Exception {
    /**
     * Se alcanza cuando se intenta operar desde una casilla que es una pared del laberinto
     *
     * @param s Mensaje a mostrar
     */
    public NotPathFromWallException(String s) {
    }
}

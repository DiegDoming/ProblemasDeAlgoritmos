package com.example.javafx;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import static javafx.scene.paint.Color.LIGHTGRAY;

public class HelloApplication extends Application {
    public static int TABLERO_SIZE = 6;
    public static int WINNING_LENGTH = 4;
    public boolean turno = true;
    public static Casilla[][] TABLERO = new Casilla[TABLERO_SIZE][TABLERO_SIZE];

    @Override
    public void start(Stage stage) {

        BorderPane root = new BorderPane();
        // Create the Pane
        GridPane gridTablero = new GridPane();
        Button button = new Button("Reset");
        button.setOnAction(actionEvent ->  {
            //reset(gridTablero);
            clearCasilla(gridTablero,2,2);
        });
        root.setRight(button);
        root.setCenter(gridTablero);
        // Add the Children to the Pane
        drawTablero(gridTablero);
        gridTablero.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int x = (int) Math.floor(event.getSceneX() / gridTablero.getWidth() * TABLERO_SIZE);
                int y = (int) Math.floor(event.getSceneY() / gridTablero.getHeight() * TABLERO_SIZE);
                System.out.println(x);
                System.out.println(y);
                if (TABLERO[x][y] == Casilla.Vacio) {
                    Circle c = new Circle(0, 0, 35);
                    if (turno) {
                        c.setFill(LIGHTGRAY);
                        TABLERO[x][y] = Casilla.Uno;
                        //Move(x,y,Casilla.Uno);
                    } else {
                        TABLERO[x][y] = Casilla.Dos;
                        //Move(x,y,Casilla.Dos);
                    }
                    turno = !turno;
                    gridTablero.add(c, x, y, 1, 1);
                    GridPane.setHalignment(c, HPos.CENTER);
                    GridPane.setValignment(c, VPos.CENTER);
                    checkWin();

                }
            }
        });

        // Create the Scene
        Scene scene = new Scene(root);
        // Add the Scene to the Stage
        stage.setScene(scene);
        stage.setResizable(false);
        // Set the Title of the Stage
        stage.setTitle("A JavaFX Rectangle Example");
        // Display the Stage
        stage.show();
    }

    public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }
    public void clearCasilla(GridPane gridTablero, int row, int column){
        gridTablero.getChildren().remove(getNodeByRowColumnIndex(row,column,gridTablero));
        Rectangle r = new Rectangle(100, 100);
        r.setFill(Color.KHAKI);
        r.setStroke(Color.BLACK);
        gridTablero.add(r, row, column, 1, 1);

        TABLERO[row][column] = Casilla.Vacio;
    }
    public void reset(GridPane gridTablero){
        gridTablero.getChildren().clear();
        drawTablero(gridTablero);
        cleanMatrix();
        turno = true;
    }
    public void drawTablero(GridPane gridTablero){
        for (int i = 0; i < TABLERO_SIZE; i++) {
            for (int j = 0; j < TABLERO_SIZE; j++) {
                Rectangle r = new Rectangle(100, 100);
                r.setFill(Color.KHAKI);
                r.setStroke(Color.BLACK);
                gridTablero.add(r, i, j, 1, 1);
            }
        }
    }
    public static void cleanMatrix(){
        for (int i = 0; i < TABLERO_SIZE; i++)
            for (int j = 0; j < TABLERO_SIZE; j++)
                TABLERO[i][j] = Casilla.Vacio;
    }
    public static void main(String[] args) {
        cleanMatrix();
        launch();
    }

    public static void checkWin() {
        checkHorizontal();
        checkVertical();
        checkDiagonal();
        checkAntiDiagonal();
    }
    public static void win(Casilla ganador){
        System.out.println("El ganador es el jugador " + ganador);
    }
    private static void checkHorizontal(){
        int racha;
        //Check Horizontal
        for (int i = 0; i < TABLERO_SIZE - 1; i++) {
            for (int j = 0; j < TABLERO_SIZE; j++) {
                racha = 1;
                if (TABLERO[i][j] != Casilla.Vacio) {
                    while (i < TABLERO_SIZE - 1 && TABLERO[i][j] == TABLERO[i + 1][j]) {
                        racha++;
                        i++;
                        if (racha >= WINNING_LENGTH)
                            win(TABLERO[i][j]);
                    }
                }
            }
        }

    }
    private static void checkVertical(){
        int racha;
        //Check Vertical
        for (int i = 0; i < TABLERO_SIZE; i++) {
            for (int j = 0; j < TABLERO_SIZE - 1; j++) {
                racha = 1;
                if (TABLERO[i][j] != Casilla.Vacio) {
                    while (j < TABLERO_SIZE - 1 && TABLERO[i][j] == TABLERO[i][j + 1]) {
                        racha++;
                        j++;
                        if (racha >= WINNING_LENGTH) {
                            win(TABLERO[i][j]);
                            return;
                        }
                    }
                }
            }
        }


    }
    private static void checkDiagonal() {
        int racha;
        //Check Diagonal
        for (int i = 1 - TABLERO_SIZE; i < TABLERO_SIZE; i++) {
            racha = 1;
            for (int x = -Math.min(0, i), y = Math.max(0, i); x < TABLERO_SIZE - 1 && y < TABLERO_SIZE - 1; x++, y++) {
                racha = 1;
                if (TABLERO[y][x] != Casilla.Vacio) {
                    while (x < TABLERO_SIZE - 1 && y < TABLERO_SIZE - 1 && TABLERO[y][x] == TABLERO[y + 1][x + 1]) {
                        racha++;
                        x++;
                        y++;
                        if (racha >= WINNING_LENGTH) {
                            win(TABLERO[y][x]);
                            return;
                        }
                    }
                }
            }
        }


    }
    private static void checkAntiDiagonal() {
        //Check AntiDiagonal;
        int racha;
        for(int b = 1; b < TABLERO_SIZE+1; b++) {
            int a = b;
            Casilla previous = null;
            racha = 1;
            for (int i = 0; i < TABLERO_SIZE; i++) {
                if (a > 0) {
                    if (previous != null)
                        if (TABLERO[i][a - 1] != Casilla.Vacio)
                            if (previous == TABLERO[i][a - 1]) {
                                racha++;
                                if (racha >= WINNING_LENGTH) {
                                    win(TABLERO[i][a - 1]);
                                    return;
                                }
                            } else
                                racha = 1;
                    previous = TABLERO[i][a - 1];
                    a--;
                }
            }
        }

        for(int b = TABLERO_SIZE-1; b > 0; b--) {
            int m = TABLERO_SIZE - 1;
            Casilla previous = null;
            racha = 1;
            for (int i = b; i > 0; i--) {
                int x = m;
                int y = (Math.abs(TABLERO_SIZE - m) + TABLERO_SIZE - b - 1);
                if (previous != null)
                    if (TABLERO[x][y] != Casilla.Vacio)
                        if (previous == TABLERO[x][y]) {
                            racha++;
                            if (racha >= WINNING_LENGTH) {
                                win(TABLERO[x][y]);
                                return;
                            }
                        } else
                            racha = 1;
                previous = TABLERO[x][y];
                m--;
            }
        }
    }

}
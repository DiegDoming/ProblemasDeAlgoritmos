package com.example.javafx;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.paint.Color.LIGHTGRAY;

public class HelloApplication extends Application {
    public static int TABLERO_SIZE = 5;
    public static int WINNING_LENGTH = 3;
    public boolean turno = true;
    public static Casilla[][] TABLERO = new Casilla[TABLERO_SIZE][TABLERO_SIZE];

    @Override
    public void start(Stage stage) {
        // Create the Pane
        GridPane root = new GridPane();
        // Add the Children to the Pane
        for (int i = 0; i < TABLERO_SIZE; i++) {
            for (int j = 0; j < TABLERO_SIZE; j++) {
                Rectangle r = new Rectangle(100, 100);
                r.setFill(Color.KHAKI);
                r.setStroke(Color.BLACK);
                root.add(r, i, j, 1, 1);
            }
        }
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int x = (int) Math.floor(event.getSceneX() / root.getWidth() * TABLERO_SIZE);
                int y = (int) Math.floor(event.getSceneY() / root.getHeight() * TABLERO_SIZE);
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
                    root.add(c, x, y, 1, 1);
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

    public static void main(String[] args) {
        for (int i = 0; i < TABLERO_SIZE; i++)
            for (int j = 0; j < TABLERO_SIZE; j++)
                TABLERO[i][j] = Casilla.Vacio;
        launch();
    }

    public static void checkWin() {
        int racha;
        //Check Vertical
        for (int i = 0; i < TABLERO_SIZE; i++) {
            for (int j = 0; j < TABLERO_SIZE - 1; j++) {
                racha = 1;
                if (TABLERO[i][j] != Casilla.Vacio) {
                    while (j < TABLERO_SIZE - 1 && TABLERO[i][j] == TABLERO[i][j + 1]) {
                        racha++;
                        j++;
                        if (racha >= WINNING_LENGTH)
                            System.out.println("Winner");
                    }
                }
            }
        }

        //Check Horizontal
        for (int i = 0; i < TABLERO_SIZE - 1; i++) {
            for (int j = 0; j < TABLERO_SIZE; j++) {
                racha = 1;
                if (TABLERO[i][j] != Casilla.Vacio) {
                    while (i < TABLERO_SIZE - 1 && TABLERO[i][j] == TABLERO[i + 1][j]) {
                        racha++;
                        i++;
                        if (racha >= WINNING_LENGTH)
                            System.out.println("Winner");
                    }
                }
            }
        }
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
                        if (racha >= WINNING_LENGTH)
                            System.out.println("Winner");
                    }
                }
            }
        }

        //Check AntiDiagonal;
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
                                if (racha >= WINNING_LENGTH)
                                    System.out.println("Winner");
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
                            if (racha >= WINNING_LENGTH)
                                System.out.println("Winner");
                        } else
                            racha = 1;
                previous = TABLERO[x][y];
                m--;
            }
        }
    }

}
package com.champlain.soft.game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.Random;

public class Gameboard extends Application {

    private static final int ROWS = 10;
    private static final int COLS = 10;
    private static final int SCENE_WIDTH = 800;
    private static final int SCENE_HEIGHT = 800;

    enum CellType {
        GRASS, PLAYER, PRINCESS, BOMB, WALL
    }

    private CellType[][] matrix = new CellType[ROWS][COLS];

    @Override
    public void start(Stage stage) {
        initMatrix();

        GridPane grid = new GridPane();
        grid.setPrefSize(SCENE_WIDTH, SCENE_HEIGHT);
        drawBoard(grid);

        BorderPane root = new BorderPane();
        root.setCenter(grid);

        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        stage.setTitle("Rescue the Princess");
        stage.setScene(scene);
        stage.show();
    }

    private void initMatrix() {
        Random rand = new Random();

        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLS; c++)
                matrix[r][c] = CellType.GRASS;

        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLS; c++)
                if (r == 0 || r == ROWS - 1 || c == 0 || c == COLS - 1)
                    matrix[r][c] = CellType.WALL;

        matrix[1][1] = CellType.PLAYER;

        int pr, pc;
        do {
            pr = rand.nextInt(ROWS - 2) + 1;
            pc = rand.nextInt(COLS - 2) + 1;
        } while (pr == 1 && pc == 1);
        matrix[pr][pc] = CellType.PRINCESS;

        int placed = 0;
        while (placed < 4) {
            int br = rand.nextInt(ROWS - 2) + 1;
            int bc = rand.nextInt(COLS - 2) + 1;
            if (matrix[br][bc] == CellType.GRASS) {
                matrix[br][bc] = CellType.BOMB;
                placed++;
            }
        }
    }

    private void drawBoard(GridPane grid) {
        grid.getChildren().clear();

        double cellSize = SCENE_WIDTH / (double) COLS;

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {

                StackPane cell = new StackPane();
                cell.setPrefSize(cellSize, cellSize);

                ImageView bg = new ImageView(new Image(getClass().getResourceAsStream("/com/champlain/soft/game/images/grass.png")));
                bg.setFitWidth(cellSize);
                bg.setFitHeight(cellSize);
                cell.getChildren().add(bg);

                if (matrix[row][col] == CellType.WALL) {
                    ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/com/champlain/soft/game/images/wall.png")));
                    img.setFitWidth(cellSize);
                    img.setFitHeight(cellSize);
                    cell.getChildren().add(img);
                } else if (matrix[row][col] == CellType.PLAYER) {
                    ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/com/champlain/soft/game/images/player.png")));
                    img.setFitWidth(cellSize);
                    img.setFitHeight(cellSize);
                    cell.getChildren().add(img);
                } else if (matrix[row][col] == CellType.PRINCESS) {
                    ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/com/champlain/soft/game/images/princess.png")));
                    img.setFitWidth(cellSize);
                    img.setFitHeight(cellSize);
                    cell.getChildren().add(img);
                } else if (matrix[row][col] == CellType.BOMB) {
                    ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/com/champlain/soft/game/images/bomb.png")));
                    img.setFitWidth(cellSize);
                    img.setFitHeight(cellSize);
                    cell.getChildren().add(img);
                }

                grid.add(cell, col, row);
            }
        }
    }
}
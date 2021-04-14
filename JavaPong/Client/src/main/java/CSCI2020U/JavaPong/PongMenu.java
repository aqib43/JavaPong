package CSCI2020U.JavaPong;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

public class PongMenu extends Application 
{
    PongGameTest testScene = new PongGameTest();

    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        //grid.setHgap(10);
        grid.setVgap(40);

        Canvas canvas = new Canvas();

        //Inits the borderpane root
        BorderPane root = new BorderPane();
        SetBackgroundColor(root, Color.DARKGREY);

        //Set center value to grid
        root.setCenter(grid);
        root.setTop(canvas);

        //Sets up our primary stage
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(root, 600, 400));

        

        //Sets the Center value to the menu
        Button playButton = new Button("Play");
        grid.add(playButton, 0, 0);

        playButton.setOnAction(value ->  {
            testScene.start(primaryStage); 
        });

        primaryStage.show();

        Button exitButton =  new Button("Exit");
        grid.add(exitButton, 0, 1);

        exitButton.setOnAction(value -> {
            testScene.start(primaryStage);
        });

        primaryStage.show();

        Button creditsButton =  new Button("Credits");
        grid.add(creditsButton, 0, 2);
        
        creditsButton.setOnAction(value -> {
            testScene.start(primaryStage);
        });

        primaryStage.show();
    }

    public void SetBackgroundColor(Pane node, Paint fill)
    {
        node.setBackground(new Background(new BackgroundFill(fill, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}

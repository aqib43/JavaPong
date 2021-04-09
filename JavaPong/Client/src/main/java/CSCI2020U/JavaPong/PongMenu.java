package CSCI2020U.JavaPong;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

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
        //Inits the borderpane root
        BorderPane root = new BorderPane();
        SetBackgroundColor(root, Color.DARKGREY);

        //Sets up our primary stage
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(root, 600, 400));

        //Sets the top value to the menu
        Button playButton = new Button("Play Button");
        root.setCenter(playButton);
        root.setAlignment(playButton, Pos.CENTER);

        playButton.setOnAction(value ->  {
            testScene.start(primaryStage);
        });

        primaryStage.show();
    }

    public void SetBackgroundColor(Pane node, Paint fill)
    {
        node.setBackground(new Background(new BackgroundFill(fill, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}

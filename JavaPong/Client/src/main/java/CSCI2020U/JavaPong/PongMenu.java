package CSCI2020U.JavaPong;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

public class PongMenu extends Application 
{
    PongGameTest testScene = new PongGameTest();

    //Store menu
    GridPane menu;
    //Store credits
    GridPane credits;

    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        //Inits the borderpane root
        BorderPane root = new BorderPane();
        SetBackgroundColor(root, Color.BLACK);

        //Grid creation
        menu = new GridPane();

        //Set center value to grid
        root.setCenter(menu);

        //Sets up the menu buttons
        SetupPongTitle(root);
        SetupMenuButtons(menu, root, primaryStage);
        SetUpCredits(root);

        //Sets up our primary stage
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(root, 600, 400));

        primaryStage.show();
    }

    public void SetupPongTitle(BorderPane root)
    {
        Text pongTitle = new Text("PONG");
        pongTitle.setTextAlignment(TextAlignment.CENTER);
        pongTitle.setFill(Color.WHITE);
        Font pongFont = Font.font("Impact", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 80);
        pongTitle.setFont(pongFont);

        root.setTop(pongTitle);
        root.setAlignment(pongTitle, Pos.CENTER);
    }

    public void SetupMenuButtons(GridPane menu, BorderPane root, Stage primaryStage)
    {
        //Alignment
        menu.setAlignment(Pos.CENTER);
        menu.setVgap(30);

         //Sets the Center value to the menu
         Button playButton = new Button("Play");
         menu.add(playButton, 0, 0);
         GridPane.setHalignment(playButton, HPos.CENTER);
         playButton.setMaxSize(800, 650);
         playButton.setMinSize(150, 80);
 
         playButton.setOnAction(value ->  {
             testScene.start(primaryStage); 
         });
 
         Button creditsButton =  new Button("Credits");
         menu.add(creditsButton, 0, 1);
         GridPane.setHalignment(creditsButton, HPos.CENTER);
         creditsButton.setMaxSize(800, 650);
         creditsButton.setMinSize(150, 80);

         creditsButton.setOnAction(value -> {
            root.setCenter(credits);
            root.setAlignment(credits, Pos.CENTER);
         });

         Button exitButton =  new Button("Exit");
         menu.add(exitButton, 0, 2);
         GridPane.setHalignment(exitButton, HPos.CENTER);
         exitButton.setMaxSize(800, 650);
         exitButton.setMinSize(150, 80);
         
         exitButton.setOnAction(value -> {
            Runtime.getRuntime().exit(0);
         });
    }

    public void SetUpCredits(BorderPane root)
    {
        //Create new gridpane
        credits = new GridPane();
        credits.setHgap(30);
        credits.setVgap(15);


        //Add the stuff for credits
        Text name1 = new Text("Nicholas Juniper");
        name1.setTextAlignment(TextAlignment.CENTER);
        name1.setFill(Color.WHITE);
        name1.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 30));
        credits.add(name1, 0, 0);
        GridPane.setHalignment(name1, HPos.CENTER);

        //Add student number
        Text studentNum1 = new Text("100659791");
        studentNum1.setTextAlignment(TextAlignment.CENTER);
        studentNum1.setFill(Color.WHITE);
        studentNum1.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 30));
        credits.add(studentNum1, 1, 0);
        GridPane.setHalignment(studentNum1, HPos.CENTER);

        Button returnToMenu = new Button("Return To Menu");
        credits.add(returnToMenu, 0, credits.getRowCount());

        returnToMenu.setOnAction(value -> {
            root.setCenter(menu);
        });
    }

    public void SetBackgroundColor(Pane node, Paint fill)
    {
        node.setBackground(new Background(new BackgroundFill(fill, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}

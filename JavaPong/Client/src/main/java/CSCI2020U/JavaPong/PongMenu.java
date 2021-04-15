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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PongMenu extends Application {
    //Stores game
    PongGameTest testScene;

    //Pong font
    Font pongFont = Font.font("Impact", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 80);
    Font pongFont2 = Font.font("Impact", FontWeight.LIGHT, FontPosture.REGULAR, 45);

    // Store menu
    GridPane menu;
    // Store credits
    GridPane credits;

    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        // Inits the borderpane root
        BorderPane root = new BorderPane();
        SetBackgroundColor(root, Color.BLACK);

        // Grid creation
        menu = new GridPane();

        // Set center value to grid
        root.setCenter(menu);

        // Sets up the menu buttons
        SetupPongTitle(root);
        SetupMenuButtons(menu, root, primaryStage);
        SetUpCredits(root);

        // Sets up our primary stage
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(root, 600, 400));

        primaryStage.show();
    }

    public void SetupPongTitle(BorderPane root) 
    {
        Text pongTitle = new Text("PONG");
        pongTitle.setTextAlignment(TextAlignment.CENTER);
        pongTitle.setFill(Color.WHITE);
        
        pongTitle.setFont(pongFont);

        root.setTop(pongTitle);
        root.setAlignment(pongTitle, Pos.CENTER);
    }

    public void SetupMenuButtons(GridPane menu, BorderPane root, Stage primaryStage) 
    {
        // Alignment
        menu.setAlignment(Pos.CENTER);
        menu.setVgap(30);

        Button playButton = new Button("Play");
        playButton.setFont(pongFont2);
        playButton.setStyle("-fx-text-fill: white; -fx-background-color: black;");
        playButton.setMaxSize(800, 650);
        playButton.setMinSize(150, 80);
        menu.add(playButton, 0, 0);
        GridPane.setHalignment(playButton, HPos.CENTER);

        playButton.setOnAction(value -> {
            testScene = new PongGameTest();
            testScene.start(primaryStage);
        });

        Button creditsButton = new Button("Credits");
        creditsButton.setFont(pongFont2);
        creditsButton.setStyle("-fx-text-fill: white; -fx-background-color: black;");
        creditsButton.setMaxSize(800, 650);
        creditsButton.setMinSize(150, 80);
        menu.add(creditsButton, 0, 1);
        GridPane.setHalignment(creditsButton, HPos.CENTER);

        creditsButton.setOnAction(value -> {
            root.setCenter(credits);
            root.setAlignment(credits, Pos.CENTER);
        });

        Button exitButton = new Button("Exit");
        exitButton.setFont(pongFont2);
        exitButton.setStyle("-fx-text-fill: white; -fx-background-color: black;");
        exitButton.setMaxSize(800, 650);
        exitButton.setMinSize(150, 80);
        menu.add(exitButton, 0, 2);
        GridPane.setHalignment(exitButton, HPos.CENTER);

        exitButton.setOnAction(value -> {
            Runtime.getRuntime().exit(0);
        });
    }

    public void SetUpCredits(BorderPane root) 
    {
        // Create new gridpane
        credits = new GridPane();
        credits.setHgap(30);
        credits.setVgap(15);

        // Add the stuff for credits
        Text name1 = new Text("Nicholas Juniper");
        name1.setTextAlignment(TextAlignment.CENTER);
        name1.setFill(Color.WHITE);
        name1.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 30));
        credits.add(name1, 0, 0);
        GridPane.setHalignment(name1, HPos.CENTER);

        Text name2 = new Text("Tiseagan Ketharasingam");
        name2.setTextAlignment(TextAlignment.CENTER);
        name2.setFill(Color.WHITE);
        name2.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 30));
        credits.add(name2, 0, 1);
        GridPane.setHalignment(name2, HPos.CENTER);

        Text name3 = new Text("Kevin Lounsbury");
        name3.setTextAlignment(TextAlignment.CENTER);
        name3.setFill(Color.WHITE);
        name3.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 30));
        credits.add(name3, 0, 2);
        GridPane.setHalignment(name3, HPos.CENTER);

        Text name4 = new Text("Aqib Alam");
        name4.setTextAlignment(TextAlignment.CENTER);
        name4.setFill(Color.WHITE);
        name4.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 30));
        credits.add(name4, 0, 3);
        GridPane.setHalignment(name4, HPos.CENTER);

        // Add student number
        Text studentNum1 = new Text("100659791");
        studentNum1.setTextAlignment(TextAlignment.CENTER);
        studentNum1.setFill(Color.WHITE);
        studentNum1.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 30));
        credits.add(studentNum1, 1, 0);
        GridPane.setHalignment(studentNum1, HPos.CENTER);

        Text studentNum2 = new Text("100748047");
        studentNum2.setTextAlignment(TextAlignment.CENTER);
        studentNum2.setFill(Color.WHITE);
        studentNum2.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 30));
        credits.add(studentNum2, 1, 1);
        GridPane.setHalignment(studentNum2, HPos.CENTER);

        Text studentNum3 = new Text("100654226");
        studentNum3.setTextAlignment(TextAlignment.CENTER);
        studentNum3.setFill(Color.WHITE);
        studentNum3.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 30));
        credits.add(studentNum3, 1, 2);
        GridPane.setHalignment(studentNum3, HPos.CENTER);

        Text studentNum4 = new Text("100754170 ");
        studentNum4.setTextAlignment(TextAlignment.CENTER);
        studentNum4.setFill(Color.WHITE);
        studentNum4.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 30));
        credits.add(studentNum4, 1, 3);
        GridPane.setHalignment(studentNum4, HPos.CENTER);

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

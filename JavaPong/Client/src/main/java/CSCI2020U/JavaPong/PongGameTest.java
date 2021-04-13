package CSCI2020U.JavaPong;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
//import javafx.scene.input;
import java.lang.Object;
import java.lang.Enum;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

public class PongGameTest 
{
    public Parent _mainMenuRoot = null;
    public String _mainMenuName = "";
    public Parent _sceneRoot = null;

    public List<GameObject> _gameObjects = new ArrayList();

    public PongClient _client = new PongClient("localhost", 8080, null);

    private int playerNum = 0;

    private boolean recv = false;

    private boolean _up = false;
    private boolean _down = false;
    private boolean _left = false;
    private boolean _right = false;

    PongGameTest()
    {

    }
 
    public void start(Stage primaryStage)
    {
        if (_sceneRoot == null) 
        {
            // Inits the borderpane root
            BorderPane root = new BorderPane();
            _sceneRoot = root;

            // Stores main menu root
            _mainMenuRoot = primaryStage.getScene().getRoot();
            _mainMenuName = primaryStage.getTitle();

            // Sets the top value to the menu
            GridPane topMenu = new GridPane();
            root.setTop(topMenu);
            root.setAlignment(topMenu, Pos.CENTER);
            SetBackgroundColor(topMenu, Color.DARKGREY);

            // Set up the menu at the top
            SetupConnectedText(topMenu);
            SetupConnectionButtons(topMenu);
            SetupReturnButton(topMenu, primaryStage);

            SetupKeyListener(primaryStage);

            // Creates our game loop
            TestUpdateInit(root);
        }

        // Changes stage title
        primaryStage.setTitle("Pong Tester");
        //Sets to this scene's root
        primaryStage.getScene().setRoot(_sceneRoot);

        primaryStage.show();
    }

    public void SetupKeyListener(Stage primaryStage)
    {
        //Scene scene = _sceneRoot.getScene();

        primaryStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event)
            {
                switch (event.getCode()) {
                    case W:
                        _up = true;
                        break;
                    case S:
                        _down = true;
                        break;
                    case A:
                        _left = true;
                        break;
                    case D:
                        _right = true;
                        break;
                
                    default:
                        break;
                }
            }
        });

        

        primaryStage.getScene().setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event)
            {
                switch (event.getCode()) {
                    case W:
                        _up = false;
                        break;
                    case S:
                        _down = false;
                        break;
                    case A:
                        _left = false;
                        break;
                    case D:
                        _right = false;
                        break;
                
                    default:
                        break;
                }
            }
        });
    }

    public void SetupConnectedText(GridPane topMenu)
    {
        //Creates a sub menu
        GridPane topSubMenu = new GridPane();
        topSubMenu.setAlignment(Pos.CENTER);

        //Sets to resize with top menu
        topSubMenu.prefWidthProperty().bind(topMenu.widthProperty());

        //Add column constraints
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHgrow(Priority.ALWAYS);
        column1.setHalignment(HPos.CENTER);
        topSubMenu.getColumnConstraints().addAll(column1);

        //Add to parent
        topMenu.add(topSubMenu, 0, 0);
        
        //Sets up text
        Text connected = new Text("Are you Connected?");
        _client.SetConnectedText(connected);

        //Adds to submenu
        topSubMenu.add(connected, 0, 0);
    }

    public void SetupConnectionButtons(GridPane topMenu)
    {
        //Creates sub menu
        GridPane middleSubMenu = new GridPane();
        middleSubMenu.setAlignment(Pos.CENTER);

        //Sets to resize with top menu
        middleSubMenu.prefWidthProperty().bind(topMenu.widthProperty());

        //Add column constraints
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHgrow(Priority.ALWAYS);
        column1.setHalignment(HPos.RIGHT);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHgrow(Priority.ALWAYS);
        column2.setHalignment(HPos.LEFT);
        middleSubMenu.getColumnConstraints().addAll(column1, column2);

        //Add to root
        topMenu.add(middleSubMenu, 0, 1);

        Button connectToServer = new Button("Connect");
        connectToServer.setOnAction(value ->  {
            _client.ConnectToServer();
        });
        
        Button disconnectFromServer = new Button("Disconnect");
        disconnectFromServer.setOnAction(value ->  {
            _client.DisconnectFromServer();
        });
        
        //Add the buttons to the menu
        middleSubMenu.add(connectToServer, 0, 0);
        middleSubMenu.add(disconnectFromServer, 1, 0);
    }

    public void SetupReturnButton(GridPane topMenu, Stage primaryStage)
    {
        //Creates sub menu
        GridPane bottomSubMenu = new GridPane();
        bottomSubMenu.setAlignment(Pos.CENTER);

        //Sets to resize with top menu
        bottomSubMenu.prefWidthProperty().bind(topMenu.widthProperty());

        //Add column constraints
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHgrow(Priority.ALWAYS);
        column1.setHalignment(HPos.CENTER);
        bottomSubMenu.getColumnConstraints().addAll(column1);

        //Add to root
        topMenu.add(bottomSubMenu, 0, 2);

        Button returnToMenu = new Button("Return to Main Menu");
        returnToMenu.setOnAction(value ->  {
            primaryStage.setTitle(_mainMenuName);
            primaryStage.getScene().setRoot(_mainMenuRoot);

            if (_client.GetConnected())
            {
                _client.DisconnectFromServer();
            }
        });

        bottomSubMenu.add(returnToMenu, 0, 0);
    }

    public void TestUpdateInit(BorderPane root)
    {
        //Get the start time (this should be moved into some sort of timer class)
        final long startNanoTime = System.nanoTime();
        long lastNanoTime = 10;

        //Wrapper pane so game canvas resizes properly
        Pane wrapperPane = new Pane();
        root.setCenter(wrapperPane);
        root.setAlignment(wrapperPane, Pos.CENTER);

        //Sets center to the game canvas
        Canvas gameCanvas = new Canvas();
        wrapperPane.getChildren().add(gameCanvas);
        //Binds width and height of wrapper pane to gamecanvas width and height
        gameCanvas.widthProperty().bind(wrapperPane.widthProperty());
        gameCanvas.heightProperty().bind(wrapperPane.heightProperty());

        //Create game objects
        //RectGameObject ball = new RectGameObject(new Vec2(), new Vec2(10.0f, 10.0f), Color.PINK, Color.BLUE);
        OvalGameObject ball = new OvalGameObject(new Vec2(), new Vec2(10.0f, 10.0f), Color.PURPLE, Color.BLUE);
        RectGameObject paddle1 = new RectGameObject(new Vec2(), new Vec2(10.0f, 35.0f), Color.RED, Color.BLUE);
        RectGameObject paddle2 = new RectGameObject(new Vec2(), new Vec2(10.0f, 35.0f), Color.GREEN, Color.BLUE);
        //OvalGameObject testOval = new OvalGameObject(new Vec2(-20.0f, 0.0f), new Vec2(10.0f, 10.0f), Color.PURPLE, Color.BLUE);
        //SpriteGameObject testSprite = new SpriteGameObject(new Vec2(10.0f, 0.0f), new Vec2(20.0f, 20.0f), new Image("file:res/missingTexture.jpg"));

        //Adds all our gameobjects
        _gameObjects.add(ball);
        _gameObjects.add(paddle1);
        _gameObjects.add(paddle2);
        //_gameObjects.add(testOval);
        //_gameObjects.add(testSprite);
        ball.SetVelocity(new Vec2(10,20));

        //Get 2d graphics context
        GraphicsContext context = gameCanvas.getGraphicsContext2D();

        //Set the rendering "units"
        GameObject.SetWorldWidth(200);
        GameObject.SetWorldHeight(200);
        paddle1.SetPosition(-(GameObject.GetWorldWidth() / 2.0f) + 5.0f, 0);
        paddle2.SetPosition((GameObject.GetWorldWidth() / 2.0f) - 10.0f, 0);
        //Example anonymous loop
        new AnimationTimer() 
        {   
            @Override
            public void handle(long currentNanoTime) 
            {
                if(_client.GetConnected() && _client.ready)
                {
                        
                   System.out.println("Connected to and ready");

                    //Gets different time values
                    //SHOULD JUST MAKE TIMER CLASS THAT CONVERTS NANOTIME
                    double totalTime = (currentNanoTime - startNanoTime) / 1000000000.0;
                    
                    //Get window width and height
                    int windowWidth = (int)gameCanvas.getWidth();
                    int windowHeight = (int)gameCanvas.getHeight();

                    //Update the static window height and window width every frame
                    GameObject.SetWindowWidth(windowWidth);
                    GameObject.SetWindowHeight(windowHeight);

                    //Clear screen
                    context.clearRect(0, 0, windowWidth, windowHeight);

                    //Updates our objects' position
                    //ball.SetPosition((float)Math.sin(totalTime) * 50.0f, 0.0f);
                    ball.PhysicsUpdate();

                    if(ball.Collision(paddle1))
                    {
                        ball.SetVelocityX(Math.abs(ball.GetVelocity()._x));
                    }
                    else if(ball.Collision(paddle2))
                    {
                        ball.SetVelocityX(-(Math.abs(ball.GetVelocity()._x)));
                    }

                    

                    if(_client.playerNum == 1)
                    {
                        //paddle1.SetPosition(-(GameObject.GetWorldWidth() / 2.0f) + 5.0f, (float)Math.sin(totalTime) * 50.0f);
                        if(_up)
                        {
                            paddle1.SetPosition(new Vec2(paddle1.GetPositionX(), 
                            paddle1.GetPositionY() - 2));
                        }
                        if(_down)
                        {
                            paddle1.SetPosition(new Vec2(paddle1.GetPositionX(), 
                            paddle1.GetPositionY() + 2));
                        }
                        //if(_left)
                        //{
                        //    paddle1.SetPosition(new Vec2(paddle1.GetPositionX() - 2, 
                        //    paddle1.GetPositionY()));
                        //}
                        //if(_right)
                        //{
                        //    paddle1.SetPosition(new Vec2(paddle1.GetPositionX() + 2, 
                        //    paddle1.GetPositionY()));
                        //}
                    }
                    if(_client.playerNum == 2)
                    {
                        if(_up)
                        {
                            paddle2.SetPosition(new Vec2(paddle2.GetPositionX(), 
                            paddle2.GetPositionY() - 2));
                        }
                        if(_down)
                        {
                            paddle2.SetPosition(new Vec2(paddle2.GetPositionX(), 
                            paddle2.GetPositionY() + 2));
                        }
                        //if(_left)
                        //{
                        //    paddle2.SetPosition(new Vec2(paddle2.GetPositionX() - 2, 
                        //    paddle2.GetPositionY()));
                        //}
                        //if(_right)
                        //{
                        //    paddle2.SetPosition(new Vec2(paddle2.GetPositionX() + 2, 
                        //    paddle2.GetPositionY()));
                        //}
                    }

                    //Sends data to server (only if client is connected)
                    for (int i = 0; i < _gameObjects.size(); i++)
                    {
                        _gameObjects.get(i).Draw(context);
                        //_gameObjects.get(i).SendPositionData(_client);
                    }

                    switch (_client.playerNum) {
                        case 1:
                            paddle1.SendPositionData(_client);
                            paddle2.SetPositionY(_client.ReadPosition());
                            break;
                        case 2:
                            paddle2.SendPositionData(_client);
                            paddle1.SetPositionY(_client.ReadPosition());
                            break;
                        default:
                            break;
                    }
                    //System.out.println("At the bottom");
                    //float temp = _client.ReadPosition();
                }
            }
        }.start();
    }

    public void SetBackgroundColor(Pane node, Paint fill)
    {
        node.setBackground(new Background(new BackgroundFill(fill, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}



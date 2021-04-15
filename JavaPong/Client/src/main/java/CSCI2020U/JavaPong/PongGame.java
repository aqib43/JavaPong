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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

public class PongGame 
{
    public Parent _mainMenuRoot = null;
    public String _mainMenuName = "";
    public Parent _sceneRoot = null;

    public List<GameObject> _gameObjects = new ArrayList<>();

    public PongClient _client = new PongClient("localhost", 8081, null);

    private int _player1Score = 0;
    private int _player2Score = 0;

    private boolean _gameOver = false;

    private boolean _up = false;
    private boolean _down = false;
 
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
            BorderPane.setAlignment(topMenu, Pos.CENTER);
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
        primaryStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event)
            {
                switch (event.getCode()) 
                {
                    case W:
                        _up = true;
                        break;
                    case S:
                        _down = true;
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
        //Wrapper pane so game canvas resizes properly
        Pane wrapperPane = new Pane();
        root.setCenter(wrapperPane);
        BorderPane.setAlignment(wrapperPane, Pos.CENTER);
        SetBackgroundColor(wrapperPane, Color.BLACK);

        //Sets center to the game canvas
        Canvas gameCanvas = new Canvas();
        wrapperPane.getChildren().add(gameCanvas);
        //Binds width and height of wrapper pane to gamecanvas width and height
        gameCanvas.widthProperty().bind(wrapperPane.widthProperty());
        gameCanvas.heightProperty().bind(wrapperPane.heightProperty());

        //Create game objects
        OvalGameObject ball = new OvalGameObject(new Vec2(), new Vec2(10.0f, 10.0f), Color.WHITE, Color.BLACK);
        RectGameObject paddle1 = new RectGameObject(new Vec2(), new Vec2(10.0f, 35.0f), Color.WHITE, Color.BLACK);
        RectGameObject paddle2 = new RectGameObject(new Vec2(), new Vec2(10.0f, 35.0f), Color.WHITE, Color.BLUE);

        //UI game Objects
        //Score
        TextGameObject scoreOne = new TextGameObject(Integer.toString(_player1Score), 
        Font.font("arial", 40), new Vec2(), new Vec2(), Color.WHITE, Color.BLACK);
        TextGameObject scoreTwo = new TextGameObject(Integer.toString(_player2Score), 
        Font.font("arial", 40), new Vec2(), new Vec2(), Color.WHITE, Color.BLACK);

        //Win/Lose
        TextGameObject winner = new TextGameObject("You win!", 
        Font.font("impact", 80), new Vec2(-30.f, 0.f), new Vec2(), Color.WHITE, Color.BLACK);
        TextGameObject loser = new TextGameObject("You Lose!!!", 
        Font.font("impact", 80), new Vec2(-35.f, 0.f), new Vec2(), Color.WHITE, Color.BLACK);

        //Adds all our gameobjects
        _gameObjects.add(ball);
        _gameObjects.add(paddle1);
        _gameObjects.add(paddle2);
        _gameObjects.add(scoreOne);
        _gameObjects.add(scoreTwo);

        //Set starting velocity
        ball.SetVelocity(new Vec2(10, 20));

        //Get 2d graphics context
        GraphicsContext context = gameCanvas.getGraphicsContext2D();

        //Set the rendering "units"
        GameObject.SetWorldWidth(200);
        GameObject.SetWorldHeight(200);

        //Set the position based on the world "rendering units"
        paddle1.SetPosition(-(GameObject.GetWorldWidth() / 2.0f) + 5.0f, 0);
        paddle2.SetPosition((GameObject.GetWorldWidth() / 2.0f) - 10.0f, 0);
        scoreOne.SetPosition(-(GameObject.GetWorldWidth() / 2.0f) + 5.0f, (float)(GameObject.GetWorldHeight() / 2.0));
        scoreTwo.SetPosition((GameObject.GetWorldWidth() / 2.0f) - 10.0f, (float)(GameObject.GetWorldHeight() / 2.0));
        
        new AnimationTimer() 
        {   
            @Override
            public void handle(long currentNanoTime) 
            {
                if(_client.GetConnected() && _client._ready && !_gameOver)
                {
                    // Update window data and clear our screen
                    UpdateScreenData(gameCanvas, context);
                    // Highlight controlled paddle
                    HighlightControlledPaddle(paddle1, paddle2);

                    // Update ball physics
                    UpdateBallPhysics(ball, paddle1, paddle2);
                    // Check paddle movement
                    UpdatePaddleMovement(paddle1, paddle2);

                    // Check for scored point
                    CheckScorePoint(ball);
                    // Check win/loss
                    CheckWinLose(ball, winner, loser);

                    // Update score display
                    UpdateScoreDisplay(scoreOne, scoreTwo);
                    // Update Networked Positions
                    UpdateNetworkedPositions(paddle1, paddle2);

                    // Draw after everything is done updating
                    Draw(context);
                }
            }
        }.start();
    }

    public void UpdateScreenData(Canvas gameCanvas, GraphicsContext context)
    {
        //Get window width and height
        int windowWidth = (int)gameCanvas.getWidth();
        int windowHeight = (int)gameCanvas.getHeight();

        //Update the static window height and window width every frame
        GameObject.SetWindowWidth(windowWidth);
        GameObject.SetWindowHeight(windowHeight);

        //Clear screen
        context.clearRect(0, 0, windowWidth, windowHeight);
    }

    public void HighlightControlledPaddle(RectGameObject paddle1, RectGameObject paddle2)
    {
        // Highlight the paddle you control
        if (_client._playerNum == 1) 
        {
            paddle1.SetFillColor(Color.AQUA);
        } 
        else 
        {
            paddle2.SetFillColor(Color.AQUA);
        }
    }

    public void UpdateBallPhysics(GameObject ball, GameObject paddle1, GameObject paddle2)
    {
        //Updates our balls' position
        ball.PhysicsUpdate();

        if(ball.Collision(paddle1))
        {
            ball.SetVelocityX(Math.abs(ball.GetVelocity()._x));
        }
        else if(ball.Collision(paddle2))
        {
            ball.SetVelocityX(-(Math.abs(ball.GetVelocity()._x)));
        }
    }

    public void UpdatePaddleMovement(GameObject paddle1, GameObject paddle2)
    {
        if (_client._playerNum == 1) 
        {
            if (_up && (paddle1.GetPositionY() >= -(GameObject.GetWorldHeight() / 2.0))) 
            {
                paddle1.SetPosition(new Vec2(paddle1.GetPositionX(), paddle1.GetPositionY() - 2));
            }
            if (_down && (paddle1.GetPositionY() <= (GameObject.GetWorldHeight() / 2.0) - 40)) 
            {
                paddle1.SetPosition(new Vec2(paddle1.GetPositionX(), paddle1.GetPositionY() + 2));
            }
        } 
        else if (_client._playerNum == 2) 
        {
            if (_up && (paddle2.GetPositionY() >= -(GameObject.GetWorldHeight() / 2.0))) 
            {
                paddle2.SetPosition(new Vec2(paddle2.GetPositionX(), paddle2.GetPositionY() - 2));
            }
            if (_down && (paddle2.GetPositionY() <= (GameObject.GetWorldHeight() / 2.0) - 40)) 
            {
                paddle2.SetPosition(new Vec2(paddle2.GetPositionX(), paddle2.GetPositionY() + 2));
            }
        }
    }

    public void CheckScorePoint(GameObject ball)
    {
        //Handle score
        if (ball._position._x <= -(GameObject.GetWorldWidth() / 2.0))
        {
            //Increase player 2 score
            _player2Score++;

            ResetBall(ball);
        }
        else if (ball._position._x >= (GameObject.GetWorldWidth() / 2.0))
        {
            //Increase player 1 score
            _player1Score++;

            ResetBall(ball);
        }
    }

    public void CheckWinLose(GameObject ball, GameObject winner, GameObject loser)
    {
        if (_player1Score >= 10 && !_gameOver) 
        {
            if (_client._playerNum == 1) 
            {
                _gameObjects.add(winner);
            } 
            else 
            {
                _gameObjects.add(loser);
            }

            // Stop ball
            StopBall(ball);
            // Set game to over
            _gameOver = true;
            // Disconnect client
            _client.DisconnectFromServer();
        } 
        else if (_player2Score >= 10 && !_gameOver) 
        {
            if (_client._playerNum == 1) 
            {
                _gameObjects.add(loser);
            } 
            else 
            {
                _gameObjects.add(winner);
            }

            // Stop ball
            StopBall(ball);
            // Set game to over
            _gameOver = true;
            // Disconnect client
            _client.DisconnectFromServer();
        }
    }

    public void UpdateScoreDisplay(TextGameObject scoreOne, TextGameObject scoreTwo)
    {
        scoreOne.SetText(Integer.toString(_player1Score));
        scoreTwo.SetText(Integer.toString(_player2Score));
    }
    
    public void Draw(GraphicsContext context)
    {
        for (int i = 0; i < _gameObjects.size(); i++)
        {
            _gameObjects.get(i).Draw(context);
        }
    }

    public void UpdateNetworkedPositions(GameObject paddle1, GameObject paddle2)
    {
        switch (_client._playerNum) 
        {
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
    }

    public void ResetBall(GameObject ball)
    {  
        //Reset position to center
        ball.SetPosition(new Vec2());
        //Random left or right and up or down
        ball.SetVelocity(new Vec2(10, 20));
    }

    public void StopBall(GameObject ball)
    {
        //Set position to center
        ball.SetPosition(new Vec2());
        //Set velocity to zero
        ball.SetVelocity(new Vec2());
    }

    public void SetBackgroundColor(Pane node, Paint fill)
    {
        node.setBackground(new Background(new BackgroundFill(fill, CornerRadii.EMPTY, Insets.EMPTY)));
    }
}



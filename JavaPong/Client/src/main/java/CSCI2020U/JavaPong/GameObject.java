package CSCI2020U.JavaPong;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject 
{
    protected static int _worldWidth = 500;
    protected static int _worldHeight = 500;

    protected static int _windowWidth = 600;
    protected static int _windowHeight = 400;

    protected static int idGenerator = 0;
    protected int _personalID = 0;

    protected Vec2 _position;
    protected Vec2 _size;
    //physics variables
    protected Vec2 _velocity = new Vec2();
    protected Vec2 _accelleration = new Vec2();

    protected GameObject()
    {
        //Stores the personal id and then incrememnts the generator
        _personalID = idGenerator++;
        //Stores position
        SetPosition(new Vec2());
        //Stores size
        SetSize(new Vec2(10.0f, 10.0f));
    }

    protected GameObject(Vec2 position, Vec2 size)
    {
        _personalID = idGenerator++;
        //Stores position
        SetPosition(position);
        //Stores size value
        SetSize(size);
    }

    protected void SetVelocity(Vec2 force)
    {
        //_accelleration = force;
        _velocity = force;
    }
    protected void SetVelocityX(float x)
    {
        _velocity._x = x;
    }
    protected Vec2 GetVelocity() 
    {
        return _velocity;
    }
    protected void PhysicsUpdate()
    {
        float dt = 0.016f;
        _position = _position.Add(_velocity.Multiply(dt)).Add
        (_accelleration.Multiply(dt * dt).Multiply(0.5f));

        WallConstraints();

        
    }

    private void WallConstraints()
    {
    //keeps ball in on screen (up and down wall bounce)
        if (_position._y > GetWorldHeight()/2 - GetSizeY()/2)
        {
            _velocity._y = Math.abs(_velocity._y) * (-1);
        }
        else if (_position._y < -GetWorldHeight()/2 + GetSizeY()/2)
        {
            _velocity._y = Math.abs(_velocity._y);
        }
        
    }

    private boolean PointBoxCol(Vec2 point, Vec2 pos, Vec2 size)
    {
        if((point._x < (pos._x + size._x/2.0f))&& (point._x > (pos._x - size._x/2.0f)))
        {
            if((point._y < pos._y + size._y/2.0f) && (point._y > pos._y - size._y/2.0f))
            {
                return true;
            }
            
            
        }
        return false;
    }

    private boolean CircleCol(Vec2 point1, float r1, Vec2 point2, float r2)
    {
        if ((point2.Subtract(point1).Length() < r1 + r2))
        {
            return true;
        }
        
        return false;
        
    }

    protected boolean Collision(GameObject paddle) 
    {

        //Vec2 topLeft = new Vec2(paddle.GetPositionX()-paddle.GetSizeX()/2,
        //                        paddle.GetPositionY()+paddle.GetSizeY()/2);
        //Vec2 topRight = new Vec2(paddle.GetPositionX()+paddle.GetSizeX()/2,
        //                        paddle.GetPositionY()+paddle.GetSizeY()/2);
        //Vec2 bottomLeft = new Vec2(paddle.GetPositionX()-paddle.GetSizeX()/2,
        //                        paddle.GetPositionY()-paddle.GetSizeY()/2);
        //Vec2 bottomRight = new Vec2(paddle.GetPositionX()+paddle.GetSizeX()/2,
        //                        paddle.GetPositionY()-paddle.GetSizeY()/2);

        Vec2 topLeft = paddle.GetPosition();
        Vec2 topRight = new Vec2(paddle.GetPositionX()+paddle.GetSizeX(),
                                paddle.GetPositionY());
        Vec2 bottomLeft = new Vec2(paddle.GetPositionX(), 
                                    paddle.GetPositionY() + paddle.GetSizeY());
        Vec2 bottomRight = new Vec2(paddle.GetPositionX() + paddle.GetSizeX(), 
                                    paddle.GetPositionY() + paddle.GetSizeY());

        float diameter = GetSizeX();

        //rectangle 1
        float r1W = paddle.GetSizeX() + diameter;
        float r1H = paddle.GetSizeY();

        //rectangle 2
        float r2W = paddle.GetSizeX();
        float r2H = paddle.GetSizeY() + diameter;

        Vec2[] points = new Vec2[]{topLeft, topRight, bottomLeft, bottomRight};
        Vec2 ballCenter = _position.Add(_size.Multiply(0.5f));

        Vec2 paddleCenter = paddle.GetPosition().Add(paddle.GetSize().Multiply(0.5f));

        for (Vec2 point:points)
        {
            if (CircleCol(point, diameter/2, ballCenter, diameter/2))
            {
                return true;
            }
        }
            
        if(PointBoxCol(ballCenter, paddleCenter, new Vec2(r1W,r1H)))
        {
            return true;
        }
        if(PointBoxCol(ballCenter, paddleCenter, new Vec2(r2W,r2H)))
        {
            return true;
        }

        return false;
    }


    protected void Draw(GraphicsContext context)
    {
        //Doesn't do anything because abstract
        System.out.println("You didn't override Draw... Make sure to do that.");
    }

    //DO NOT MODIFY POSITION AND SIZE
    protected List<Vec2> ConvertToWorld(Vec2 position, Vec2 size)
    {        
        List<Vec2> multiReturn = new ArrayList();
        multiReturn.add(new Vec2());
        multiReturn.add(new Vec2());

        //Prevents divide by zero
        if (_windowWidth != 0)
        {
            float aspectRatio = ((float)_windowWidth / (float)_windowHeight);

            // Aspect ratio modified world width
            float aspectWorldWidth = (float) _worldWidth * aspectRatio;

            // Percent of the world that the position is at
            float percentPosX = (position._x + (_worldWidth / 2.0f)) / _worldWidth;
            float percentPosY = (position._y + (_worldHeight / 2.0f)) / (float) _worldHeight;

            // Position in window space
            float windowPosX = percentPosX * (float) _windowWidth;
            float windowPosY = percentPosY * (float) _windowHeight;

            // Adds this to return list
            multiReturn.get(0).Set(windowPosX, windowPosY);

            // Percent of the world that the size takes up
            float percentSizeX = (size._x) / aspectWorldWidth;
            float percentSizeY = (size._y)/ (float) _worldHeight;

            // Size in window space
            float windowSizeX = percentSizeX * (float) _windowWidth;
            float windowSizeY = percentSizeY * (float) _windowHeight;

            // Adds this to return list
            multiReturn.get(1).Set(windowSizeX, windowSizeY);
        }

        //Returns our list
        return multiReturn;
    }

    protected void SendPositionData(PongClient client)
    {
        if (client.GetConnected())
        {
            client.SendRequest("POSITION " + _personalID + " " + _position._x + " " + _position._y);
        }
    }

    protected static void SetWorldWidth(int width)
    {
        _worldWidth = width;
    }

    protected static void SetWorldHeight(int height)
    {
        _worldHeight = height;
    }

    protected static void SetWindowWidth(int width)
    {
        _windowWidth = width;
    }

    protected static void SetWindowHeight(int height)
    {
        _windowHeight = height;
    }

    protected void SetPosition(Vec2 position)
    {
        _position = position;
    }

    protected void SetPosition(float x, float y)
    {
        _position._x = x;
        _position._y = y;
    }

    protected void SetPositionX(float x)
    {
        _position._x = x;
    }

    protected void SetPositionY(float y)
    {
        _position._y = y;
    }

    public void SetSize(Vec2 size)
    {
        _size = size;
    }

    public void SetSize(float x, float y)
    {
        _size._x = x;
        _size._y = y;
    }

    public void SetSizeX(float x)
    {
        _size._x = x;
    }

    public void SetSizeY(float y)
    {
        _size._y = y;
    }

    protected static float GetWorldWidth()
    {
        return _worldWidth;
    }

    protected static float GetWorldHeight()
    {
        return _worldHeight;
    }

    protected static float GetWindowWidth()
    {
        return _windowWidth;
    }

    protected static float GetWindowHeight()
    {
        return _windowHeight;
    }

    protected int GetPersonalID()
    {
        return _personalID;
    }

    protected Vec2 GetPosition()
    {
        return _position;
    }

    protected float GetPositionX()
    {
        return _position._x;
    }

    protected float GetPositionY()
    {
        return _position._y;
    }

    public Vec2 GetSize()
    {
        return _size;
    }

    public float GetSizeX()
    {
        return _size._x;
    }

    public float GetSizeY()
    {
        return _size._y;
    }

}

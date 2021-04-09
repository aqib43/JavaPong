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

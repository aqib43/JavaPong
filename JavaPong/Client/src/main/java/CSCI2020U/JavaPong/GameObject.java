package CSCI2020U.JavaPong;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public abstract class GameObject 
{
    protected static int idGenerator = 0;
    protected int _personalID = 0;

    protected Vec2 _position;

    protected Paint _fillColor;
    protected Paint _strokeColor;

    protected GameObject()
    {
        //Stores the personal id and then incrememnts the generator
        _personalID = idGenerator++;
        //Stores position
        SetPosition(new Vec2());
    }

    protected GameObject(Vec2 position, Paint fill, Paint stroke)
    {
        _personalID = idGenerator++;
        //Stores position
        SetPosition(position);
        //Stores fill
        SetFillColor(fill);
        //Stores stroke
        SetStrokeColor(stroke);
    }

    protected void Draw(GraphicsContext context)
    {
        //Doesn't do anything because abstract
    }

    protected void SendDataToServer(PongClient client)
    {
        if (client.GetConnected())
        {
            client.SendRequest("POSITION " + _personalID + " " + _position._x + " " + _position._y);
        }
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

    protected void SetFillColor(Paint fill)
    {
        _fillColor = fill;
    }

    protected void SetStrokeColor(Paint stroke)
    {
        _strokeColor = stroke;
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

    protected Paint GetFillColor()
    {
        return _fillColor;
    }

    protected Paint GetStrokeColor()
    {
        return _strokeColor;
    }

}

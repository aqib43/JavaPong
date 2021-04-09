package CSCI2020U.JavaPong;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class RectGameObject extends GameObject
{
    private Vec2 _size;

    public RectGameObject()
    {
        //Just calls base class constructor
        super();
        //Stores size value
        SetSize(new Vec2());
    }

    public RectGameObject(Vec2 position, Paint fill, Paint stroke, Vec2 size)
    {
        //Call base class for position
        super(position, fill, stroke);
        //Stores size value
        SetSize(size);
    }

    @Override
    public void Draw(GraphicsContext context)
    {
        //Overrides base class

        //Sets fill and stroke before draw
        context.setFill(_fillColor);
        context.setStroke(_strokeColor);

        //Draws the filled rect at position with size
        context.fillRect(_position._x, _position._y, _size._x, _size._y);
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

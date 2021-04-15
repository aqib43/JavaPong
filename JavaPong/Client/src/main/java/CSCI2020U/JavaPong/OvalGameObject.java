package CSCI2020U.JavaPong;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class OvalGameObject extends GameObject 
{
    private Paint _fillColor;
    private Paint _strokeColor;

    public OvalGameObject()
    {
        //Just calls base class constructor
        super();

        //Set fill
        SetFillColor(Color.BLACK);
        //Set stroke
        SetStrokeColor(Color.WHITE);
    }

    public OvalGameObject(Vec2 position, Vec2 size, Paint fill, Paint stroke)
    {
        //Call base class for position
        super(position, size);
        //Stores fill
        SetFillColor(fill);
        //Stores stroke
        SetStrokeColor(stroke);
    }

    @Override
    public void Draw(GraphicsContext context)
    {
        //Overrides base class

        //Sets fill and stroke before draw
        context.setFill(_fillColor);
        context.setStroke(_strokeColor);
        
        //Gets in world space
        List<Vec2> posSize = ConvertToWorld(_position, _size);

        //Draws the filled rect at position with size
        context.fillOval(posSize.get(0)._x, posSize.get(0)._y, posSize.get(1)._x, posSize.get(1)._y);
    }

    protected void SetFillColor(Paint fill)
    {
        _fillColor = fill;
    }

    protected void SetStrokeColor(Paint stroke)
    {
        _strokeColor = stroke;
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

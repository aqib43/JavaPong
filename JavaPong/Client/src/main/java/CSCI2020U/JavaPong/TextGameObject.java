package CSCI2020U.JavaPong;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class TextGameObject extends GameObject
{
    private String _text;
    private Font _font;

    private Paint _fillColor;
    private Paint _strokeColor;

    public TextGameObject()
    {
        super();
    }

    public TextGameObject(String text, Font font, Vec2 pos, Vec2 size, Paint fill, Paint stroke)
    {
        super(pos, size);

        SetText(text);
        SetFont(font);

        SetFillColor(fill);
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

        context.setFont(_font);
        //Draws the filled rect at position with size
        context.fillText(_text, posSize.get(0)._x, posSize.get(0)._y);
    }

    protected void SetText(String text)
    {
        _text = text;
    }

    protected void SetFont(Font font)
    {
        _font = font;
    }

    protected void SetFillColor(Paint fill)
    {
        _fillColor = fill;
    }

    protected void SetStrokeColor(Paint stroke)
    {
        _strokeColor = stroke;
    }

    protected String GetText()
    {
        return _text;
    }

    protected Font GetFont()
    {
        return _font;
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

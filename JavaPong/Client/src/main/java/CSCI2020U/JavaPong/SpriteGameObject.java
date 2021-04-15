package CSCI2020U.JavaPong;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SpriteGameObject extends GameObject 
{
    private Image _sprite;

    public SpriteGameObject()
    {
        //Just calls base class constructor
        super();
        //Stores default image
        SetSprite(new Image("file:res/missingTexture.jpg"));
    }

    public SpriteGameObject(Vec2 position, Vec2 size, Image sprite)
    {
        //Call base class for position
        super(position, size);
        //Stores sprite
        SetSprite(sprite);
    }

    @Override
    public void Draw(GraphicsContext context)
    {
        //Gets in world space
        List<Vec2> posSize = ConvertToWorld(_position, _size);

        //Overrides base class
        context.drawImage(_sprite, posSize.get(0)._x, posSize.get(0)._y, posSize.get(1)._x, posSize.get(1)._y);
    }

    public void SetSprite(Image sprite)
    {
        _sprite = sprite;
    }

    public Image GetSprite()
    {
        return _sprite;
    }
}

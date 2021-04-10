package CSCI2020U.JavaPong;

//import java.lang;

public class Vec2 
{
    float _x = 0.0f;
    float _y = 0.0f;

    public Vec2()
    {
        _x = 0.0f;
        _y = 0.0f;
    }

    public Vec2(float x)
    {
        _x = x;
        _y = 0.0f;
    }

    public Vec2(float x, float y)
    {
        _x = x;
        _y = y;
    }

    public Vec2(Vec2 copy)
    {
        _x = copy._x;
        _y = copy._y;
    }

    public float Length()
    {
        float result = _x * _x + _y * _y;

        result = (float)Math.sqrt(result);
        return result;

    }

    public Vec2 Add(Vec2 v2)
    {
        Vec2 temp = new Vec2(v2);

        temp._x += _x;
        temp._y += _y;

        return temp;
    }

    public Vec2 Subtract(Vec2 v2)
    {
        Vec2 temp = new Vec2(v2);

        temp._x -= _x;
        temp._y -= _y;

        return temp;
    }

    public Vec2 Multiply(float scalar)
    {
        Vec2 temp = new Vec2(this);

        temp._x *= scalar;
        temp._y *= scalar;

        return temp;
    }

    public Vec2 Divide(float scalar)
    {
        Vec2 temp = new Vec2(this);

        temp._x /= scalar;
        temp._y /= scalar;

        return temp;
    }

    public float Dot(Vec2 v2)
    {
        return (_x * v2._x) + (_y * v2._y);
    }

    
    public float Cross(Vec2 v2)
    {
        //Gets the z component of 2D vector laying on xy plane
        return (_x * v2._y) - (_y * v2._x);
    }

    public void Set(float x, float y)
    {
        _x = x;
        _y = y;
    }

    public void SetX(float x)
    {
        _x = x;
    }

    public float GetX()
    {
        return _x;
    }
    
    public void SetY(float y)
    {
        _y = y;
    }

    public float GetY()
    {
        return _y;
    }
}

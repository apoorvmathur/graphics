
import java.awt.Graphics;

public abstract class Line extends Shape {

	Point p1, p2;

	@Override
	public abstract void paint(Graphics g);

	@Override
	public abstract void transform(Transform transform) throws InvalidTransformException;

}

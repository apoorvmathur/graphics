
import java.awt.Color;
import java.awt.Graphics;

public abstract class Shape {

	protected Color color;
	protected boolean solid;

	@Override
	public abstract Shape clone();

	public Color getColor() {
		return color;
	}

	public boolean isSolid() {
		return solid;
	}

	public abstract void paint(Graphics g);

	public void setColor(Color color) {
		this.color = color;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public abstract String stringify();

	public abstract void transform(Transform transform) throws InvalidTransformException;

}

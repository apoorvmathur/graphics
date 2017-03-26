
import java.awt.Color;
import java.awt.Graphics;

public class Circle extends Shape {

	public static Circle restore(String str) {
		String[] s = str.split(" ");
		HPoint2D center = new HPoint2D(s[1], s[2], s[3]);
		double radX = Double.parseDouble(s[4]);
		double radY = Double.parseDouble(s[5]);
		boolean solid = Boolean.parseBoolean(s[6]);
		int r = Integer.parseInt(s[7]);
		int g = Integer.parseInt(s[8]);
		int b = Integer.parseInt(s[9]);
		Color color = new Color(r, g, b);
		Circle circle = new Circle(center, radX, radY);
		circle.setColor(color);
		circle.setSolid(solid);
		return circle;
	}

	HPoint2D center, upLeft, upRight, downRight;
	double radX, radY;

	public Circle(HPoint2D center, double radius) {
		this.center = center;
		this.radX = radius;
		this.radY = radius;
		center.normalize();
		upLeft = new HPoint2D(center.getX() - radX, center.getY() - radY, 1);
		upRight = new HPoint2D(center.getX() + radX, center.getY() - radY, 1);
		downRight = new HPoint2D(center.getX() + radX, center.getY() + radY, 1);
		solid = false;
		setColor(Color.BLACK);
	}

	public Circle(HPoint2D center, double radX, double radY) {
		this.center = center;
		this.radX = radX;
		this.radY = radY;
		center.normalize();
		upLeft = new HPoint2D(center.getX() - radX, center.getY() - radY, 1);
		upRight = new HPoint2D(center.getX() + radX, center.getY() - radY, 1);
		downRight = new HPoint2D(center.getX() + radX, center.getY() + radY, 1);
		solid = false;
		setColor(Color.BLACK);
	}

	@Override
	public Circle clone() {
		String g = stringify();
		Circle circle = restore(g);
		return circle;
	}

	@Override
	public void paint(Graphics g) {

		if (solid) {
			if (getColor() != null) {
				Color c = g.getColor();
				g.setColor(getColor());
				g.fillOval((int) (upLeft.getX()), (int) (upLeft.getY()), (int) (2 * radX), (int) (2 * radY));
				g.setColor(c);
			} else {
				g.fillOval((int) (upLeft.getX()), (int) (upLeft.getY()), (int) (2 * radX), (int) (2 * radY));
			}
		} else {
			if (getColor() != null) {
				Color c = g.getColor();
				g.setColor(getColor());
				g.drawOval((int) (upLeft.getX()), (int) (upLeft.getY()), (int) (2 * radX), (int) (2 * radY));
				g.setColor(c);
			} else {
				g.drawOval((int) (upLeft.getX()), (int) (upLeft.getY()), (int) (2 * radX), (int) (2 * radY));
			}
		}
	}

	@Override
	public String stringify() {
		String g = "Circle" + ' ' + center.getX() + ' ' + center.getY() + ' ' + center.getH() + ' ' + radX + ' ' + radY
				+ ' ' + solid + ' ' + getColor().getRed() + ' ' + getColor().getGreen() + ' ' + getColor().getBlue();
		return g;
	}

	@Override
	public void transform(Transform transform) throws InvalidTransformException {
		upLeft.transform(transform);
		upRight.transform(transform);
		downRight.transform(transform);
		center.transform(transform);
		if ((int) upLeft.getY() != (int) upRight.getY()) {
			upLeft = new HPoint2D(center.getX() - radX, center.getY() - radY, 1);
			upRight = new HPoint2D(center.getX() + radX, center.getY() - radY, 1);
			downRight = new HPoint2D(center.getX() + radX, center.getY() + radY, 1);
		} else {
			radX = Math.abs(center.getX() - upLeft.getX());
			radY = Math.abs(downRight.getY() - center.getY());
			upLeft = new HPoint2D(center.getX() - radX, center.getY() - radY, 1);
			upRight = new HPoint2D(center.getX() + radX, center.getY() - radY, 1);
			downRight = new HPoint2D(center.getX() + radX, center.getY() + radY, 1);
		}
	}

}

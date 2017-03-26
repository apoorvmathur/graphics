
import java.awt.Color;
import java.awt.Graphics;
import java.math.BigDecimal;
import java.util.ArrayList;

public class FreeShape extends Shape {

	public static FreeShape restore(String str) {
		FreeShape shape = new FreeShape();
		String[] props = str.split(";");
		String[] poly = props[0].split(" ");
		int r = Integer.parseInt(poly[2]);
		int g = Integer.parseInt(poly[3]);
		int b = Integer.parseInt(poly[4]);
		Color color = new Color(r, g, b);
		shape.setSolid(Boolean.parseBoolean(poly[1]));
		shape.setColor(color);
		for (int i = 1; i < props.length; i++) {
			String[] point = props[i].split(",");
			HPoint2D p = new HPoint2D(new BigDecimal(point[0]), new BigDecimal(point[1]), BigDecimal.ONE);
			shape.addPoint(p);
		}
		return shape;
	}

	ArrayList<HPoint2D> points;
	ArrayList<HLine2D> lines;

	public FreeShape() {
		points = new ArrayList<HPoint2D>();
		lines = new ArrayList<HLine2D>();
	}

	public void addPoint(HPoint2D point) {
		points.add(point);
		if (points.size() > 1) {
			HLine2D line = new HLine2D(points.get(points.size() - 2), point);
			line.setColor(getColor());
			lines.add(line);
		}
	}

	@Override
	public FreeShape clone() {
		String str = stringify();
		return restore(str);
	}

	@Override
	public void paint(Graphics g) {
		for (int i = 0; i < lines.size(); i++) {
			lines.get(i).paint(g);
		}
	}

	@Override
	public String stringify() {
		String str = "FreeShape" + ' ' + solid + ' ' + color.getRed() + ' ' + color.getGreen() + ' ' + color.getBlue();
		for (int i = 0; i < points.size(); i++) {
			str = str + ';' + points.get(i).toString();
		}
		return str;
	}

	@Override
	public void transform(Transform transform) throws InvalidTransformException {
		for (int i = 0; i < points.size(); i++) {
			points.get(i).transform(transform);
		}
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
		for (int i = 0; i < lines.size(); i++) {
			lines.get(i).setColor(color);
		}
	}

}

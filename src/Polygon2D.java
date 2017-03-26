
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Polygon2D extends Shape {

	public static Polygon2D restore(String str) {
		Polygon2D polygon = new Polygon2D();
		String[] props = str.split(";");
		String[] poly = props[0].split(" ");
		int r = Integer.parseInt(poly[2]);
		int g = Integer.parseInt(poly[3]);
		int b = Integer.parseInt(poly[4]);
		Color color = new Color(r, g, b);
		polygon.setSolid(Boolean.parseBoolean(poly[1]));
		polygon.setColor(color);
		for (int i = 1; i < props.length; i++) {
			String[] point = props[i].split(",");
			HPoint2D p = new HPoint2D(new BigDecimal(point[0]), new BigDecimal(point[1]), BigDecimal.ONE);
			polygon.addPoint(p);
		}
		return polygon;
	}

	ArrayList<HPoint2D> points;

	ArrayList<HLine2D> lines;

	public Polygon2D() {
		points = new ArrayList<HPoint2D>();
		lines = new ArrayList<HLine2D>();
		color = Color.BLACK;
	}

	public void addPoint(HPoint2D point) {
		points.add(point);
		if (points.size() > 3) {
			lines.remove(lines.size() - 1);
		}
		if (points.size() > 2) {
			HLine2D line = new HLine2D(points.get(points.size() - 2), point);
			line.setColor(getColor());
			lines.add(line);
		}
		if (points.size() > 1) {
			HLine2D line = new HLine2D(points.get(0), point);
			line.setColor(getColor());
			lines.add(line);
		}
	}

	@Override
	public Polygon2D clone() {
		String str = stringify();
		return restore(str);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(color);
		if (solid && points.size() > 2) {
			Polygon p = new Polygon();
			for (int i = 0; i < points.size(); i++) {
				p.addPoint((int) points.get(i).getX(), (int) points.get(i).getY());
			}
			g.fillPolygon(p);
		} else {
			for (int i = 0; i < lines.size(); i++) {
				lines.get(i).paint(g);
			}
		}
	}

	public void removeLastPoint() {
		points.remove(points.size() - 1);
		lines.remove(lines.size() - 1);
		if (points.size() > 1) {
			lines.remove(lines.size() - 1);
		}
		if (points.size() > 2) {
			lines.add(new HLine2D(points.get(points.size() - 1), points.get(0)));
		}
	}

	@Override
	public String stringify() {
		String str = "Polygon2D" + ' ' + solid + ' ' + color.getRed() + ' ' + color.getGreen() + ' ' + color.getBlue();
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

	public void setColor(Color color) {
		this.color = color;
		for (int i = 0; i < lines.size(); i++) {
			lines.get(i).setColor(color);
		}
	}

}

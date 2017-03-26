
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.math.BigDecimal;

public class Rectangle extends Shape {

	public static Rectangle restore(String str) {
		String[] s = str.split(" ");
		HPoint2D p1 = new HPoint2D(s[1], s[2], s[3]);
		HPoint2D p2 = new HPoint2D(s[4], s[5], s[6]);
		HPoint2D p3 = new HPoint2D(s[7], s[8], s[9]);
		HPoint2D p4 = new HPoint2D(s[10], s[11], s[12]);
		int r = Integer.parseInt(s[14]);
		int g = Integer.parseInt(s[15]);
		int b = Integer.parseInt(s[16]);
		Color color = new Color(r, g, b);
		boolean solid = Boolean.parseBoolean(s[13]);
		Rectangle rect = new Rectangle(p1, p2);
		rect.p1 = p1;
		rect.p2 = p2;
		rect.p3 = p3;
		rect.p4 = p4;
		rect.setColor(color);
		rect.calcLines();
		rect.solid = solid;
		return rect;
	}

	HPoint2D p1, p2, p3, p4;
	HLine2D line1, line2, line3, line4;

	public Rectangle(HPoint2D p1, HPoint2D p2) {
		this.p1 = p1;
		this.p3 = p2;
		this.p2 = new HPoint2D(p3.x.add(BigDecimal.ZERO), p1.y.add(BigDecimal.ZERO), BigDecimal.ONE);
		this.p4 = new HPoint2D(p1.x.add(BigDecimal.ZERO), p3.y.add(BigDecimal.ZERO), BigDecimal.ONE);
		calcLines();
		solid = false;
		setColor(Color.BLACK);
	}

	private void calcLines() {
		line1 = new HLine2D(p1, p2);
		line2 = new HLine2D(p2, p3);
		line3 = new HLine2D(p3, p4);
		line4 = new HLine2D(p4, p1);
		line1.setColor(getColor());
		line2.setColor(getColor());
		line3.setColor(getColor());
		line4.setColor(getColor());
	}

	@Override
	public Shape clone() {
		String str = stringify();
		return restore(str);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(color);
		Polygon p = new Polygon();
		p.addPoint((int) p1.getX(), (int) p1.getY());
		p.addPoint((int) p2.getX(), (int) p2.getY());
		p.addPoint((int) p3.getX(), (int) p3.getY());
		p.addPoint((int) p4.getX(), (int) p4.getY());
		if (solid) {	
			g.fillPolygon(p);
		} else {
			g.drawPolygon(p);
		}
	}

	@Override
	public String stringify() {
		String s = "Rectangle" + ' ' + p1.x + ' ' + p1.y + ' ' + p1.h + ' ' + p2.x + ' ' + p2.y + ' ' + p2.h + ' '
				+ p3.x + ' ' + p3.y + ' ' + p3.h + ' ' + p4.x + ' ' + p4.y + ' ' + p4.h + ' ' + solid + ' '
				+ getColor().getRed() + ' ' + getColor().getGreen() + ' ' + getColor().getBlue();
		return s;
	}

	@Override
	public void transform(Transform transform) throws InvalidTransformException {
		p1.transform(transform);
		p2.transform(transform);
		p3.transform(transform);
		p4.transform(transform);
	}

}

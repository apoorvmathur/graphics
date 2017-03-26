
import java.awt.Color;
import java.awt.Graphics;

public class HLine2D extends Line {

	public static HLine2D restore(String str) {
		String[] s = str.split(" ");
		HPoint2D p1 = new HPoint2D(s[1], s[2], s[3]);
		HPoint2D p2 = new HPoint2D(s[4], s[5], s[6]);
		int r = Integer.parseInt(s[7]);
		int g = Integer.parseInt(s[8]);
		int b = Integer.parseInt(s[9]);
		Color color = new Color(r, g, b);
		HLine2D line = new HLine2D(p1, p2);
		line.setColor(color);
		return line;
	}

	private HPoint2D p1, p2;

	HLine2D(HPoint2D p1, HPoint2D p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	@Override
	public HLine2D clone() {
		String g = stringify();
		HLine2D line = restore(g);
		return line;
	}

	public HPoint2D getP1() {
		return p1;
	}

	public HPoint2D getP2() {
		return p2;
	}

	@Override
	public void paint(Graphics g) {
		if (getColor() != null) {
			Color c = g.getColor();
			g.setColor(getColor());
			g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
			g.setColor(c);
		} else {
			g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
			g.setClip(100, 100, 400, 400);
		}
	}

	public void setP1(HPoint2D p1) {
		this.p1 = p1;
	}

	public void setP2(HPoint2D p2) {
		this.p2 = p2;
	}

	@Override
	public String stringify() {
		String s = "HLine2D" + ' ' + p1.x + ' ' + p1.y + ' ' + p1.h + ' ' + p2.x + ' ' + p2.y + ' ' + p2.h + ' '
				+ getColor().getRed() + ' ' + getColor().getGreen() + ' ' + getColor().getBlue();
		return s;
	}

	@Override
	public void transform(Transform transform) throws InvalidTransformException {
		p1.transform(transform);
		p2.transform(transform);
	}

}

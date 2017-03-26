
import java.math.BigDecimal;
import java.math.MathContext;

public class HPoint2D extends Point {

	static MathContext context = new MathContext(100);
	BigDecimal x, y, h;

	HPoint2D(BigDecimal x, BigDecimal y, BigDecimal h) {
		this.x = x;
		this.y = y;
		this.h = h;
		normalize();
	}

	HPoint2D(double x, double y, double h) {
		this.x = new BigDecimal(x, context);
		this.y = new BigDecimal(y, context);
		this.h = new BigDecimal(h, context);
		normalize();
	}

	HPoint2D(String x, String y, String h) {
		this.x = new BigDecimal(x, context);
		this.y = new BigDecimal(y, context);
		this.h = new BigDecimal(h, context);
		normalize();
	}

	public double getDist(HPoint2D P) {
		double dist = Math.sqrt(Math.pow(P.getX() - x.doubleValue(), 2) + Math.pow(P.getY() - y.doubleValue(), 2));
		return dist;
	}

	public double getH() {
		return h.doubleValue();
	}

	public double getX() {
		return x.doubleValue();
	}

	public double getY() {
		return y.doubleValue();
	}

	public void normalize() {
		x = x.divide(h, context);
		y = y.divide(h, context);
		h = h.divide(h, context);
	}

	public void setH(double h) {
		this.h = new BigDecimal(h);
	}

	public void setX(double x) {
		this.x = new BigDecimal(x);
	}

	public void setY(double y) {
		this.y = new BigDecimal(y);
	}

	@Override
	public String toString() {
		return x + "," + y;
	}

	public void transform(Transform transform) throws InvalidTransformException {
		if (!transform.getClass().getSuperclass().equals(Transform2D.class)) {
			throw new InvalidTransformException();
		}
		BigDecimal[] p = new BigDecimal[] { x, y, h };
		transform.perform(p);
		x = p[0];
		y = p[1];
		h = p[2];
		normalize();
	}
}

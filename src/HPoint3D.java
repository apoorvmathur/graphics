
import java.math.BigDecimal;

public class HPoint3D extends Point {

	BigDecimal x, y, z, h;

	HPoint3D(double x, double y, double z, double h) {
		this.x = new BigDecimal(x);
		this.y = new BigDecimal(y);
		this.z = new BigDecimal(z);
		this.h = new BigDecimal(h);
		normalize();
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

	public double getZ() {
		return z.doubleValue();
	}

	public void normalize() {
		x = x.divide(h);
		y = y.divide(h);
		z = z.divide(h);
		h = h.divide(h);
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

	public void setZ(double y) {
		this.z = new BigDecimal(y);
	}

	public void transform(Transform3D transform) {
		BigDecimal[] p = new BigDecimal[] { x, y, z, h };
		transform.perform(p);
		normalize();
	}
}

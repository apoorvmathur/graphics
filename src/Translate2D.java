
public class Translate2D extends Transform2D {

	public Translate2D(double x, double y) {
		matrix = new double[][] { { 1, 0, 0 }, { 0, 1, 0 }, { x, y, 1 } };
	}

	public Translate2D(double x, double y, boolean flag) {
		matrix = new double[][] { { 1, 0, 0 }, { 0, 1, 0 }, { -x, -y, 1 } };
	}

	public void reverseTransform() {
		matrix[2][0] = -matrix[2][0];
		matrix[2][1] = -matrix[2][1];
	}

	public void setTranspose(double x, double y) {
		matrix[2][0] = x;
		matrix[2][1] = y;
	}

}

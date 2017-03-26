
public class Rotate2D extends Transform2D {

	public Rotate2D(double x) {
		matrix = new double[][] { { Math.cos(x), Math.sin(x), 0 }, { -Math.sin(x), Math.cos(x), 0 }, { 0, 0, 1 } };
	}

}

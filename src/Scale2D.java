
public class Scale2D extends Transform2D {

	public Scale2D(double scale) {
		matrix = new double[][] { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 / scale } };
	}

	public Scale2D(double scaleX, double scaleY) {
		matrix = new double[][] { { scaleX, 0, 0 }, { 0, scaleY, 0 }, { 0, 0, 1 } };
	}

}

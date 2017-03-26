
public class Reflect2D extends Transform {

	public static final int REFLECT_X = 0, REFLECT_Y = 1;

	public Reflect2D(int reflect) {
		if (reflect == REFLECT_X) {
			matrix = new double[][] { { 1, 0, 0 }, { 0, -1, 0 }, { 0, 0, 1 } };
		} else {
			matrix = new double[][] { { -1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } };
		}
	}
}

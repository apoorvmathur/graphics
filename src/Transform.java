
import java.math.BigDecimal;

public abstract class Transform {

	double[][] matrix;

	public void perform(BigDecimal[] x) {
		BigDecimal[] x1 = x.clone();
		for (int i = 0; i < matrix.length; i++) {
			x[i] = BigDecimal.ZERO;
			for (int j = 0; j < matrix[i].length; j++) {
				x[i] = x[i].add(x1[j].multiply(new BigDecimal(matrix[j][i]), HPoint2D.context));
			}
		}
	}
}

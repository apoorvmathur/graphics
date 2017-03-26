import java.awt.Color;
import java.awt.Graphics;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Curve extends Shape {
	
	ArrayList<HPoint2D> points;
	
	public Curve() {
		points = new ArrayList<HPoint2D>();
		color = Color.BLACK;
	}

	@Override
	public Shape clone() {
		String g = stringify();
		return restore(g);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(color);
		double[] val = new double[2];
		if (points.size() > 1) {
			double w = points.get(points.size()-1).getX()-points.get(0).getX();
			double inc = Math.abs(1/(2*w));
			for (double i = 0.00; i < 1.00; i=i+inc) {
				val = curveVal(0, points.size()-1, i);
				plot(g, (int)val[0], (int)val[1] );
			}
		}
		
		for(int i=0;i<points.size();i++) {
			plot(g, (int)points.get(i).getX(), (int)points.get(i).getY());
		}
	}
	
	public double[] curveVal(int startnum, int endnum, double t) {
		double[] val = new double[2];
		if (endnum - startnum == 0) {
			val[0] = points.get(endnum).getX();
			val[1] = points.get(endnum).getY();
			return val;
		}
		double[] val1 = new double[2], val2 = new double[2];
		val1[0] = (1 - t) * curveVal(startnum, endnum - 1, t)[0];
		val1[1] = (1 - t) * curveVal(startnum, endnum - 1, t)[1];
		val2[0] = t * curveVal(startnum + 1, endnum, t)[0];
		val2[1] = t * curveVal(startnum + 1, endnum, t)[1];
		val[0] = val1[0]+val2[0];
		val[1] = val1[1]+val2[1];
		return  val;
	}
	
	public void plot(Graphics g, int x, int y) {
		g.drawLine(x, y, x, y);
	}
	
	public static Curve restore(String str) {
		Curve curve = new Curve();
		String[] props = str.split(";");
		String[] poly = props[0].split(" ");
		int r = Integer.parseInt(poly[2]);
		int g = Integer.parseInt(poly[3]);
		int b = Integer.parseInt(poly[4]);
		Color color = new Color(r, g, b);
		curve.setSolid(Boolean.parseBoolean(poly[1]));
		curve.setColor(color);
		for (int i = 1; i < props.length; i++) {
			String[] point = props[i].split(",");
			HPoint2D p = new HPoint2D(new BigDecimal(point[0]), new BigDecimal(point[1]), BigDecimal.ONE);
			curve.addPoint(p);
		}
		return curve;
	}

	@Override
	public String stringify() {
		String str = "Curve" + ' ' + solid + ' ' + color.getRed() + ' ' + color.getGreen() + ' ' + color.getBlue();
		for (int i = 0; i < points.size(); i++) {
			str = str + ';' + points.get(i).toString();
		}
		return str;
	}

	@Override
	public void transform(Transform transform) throws InvalidTransformException {
		for(int i=0;i<points.size();i++) {
			points.get(i).transform(transform);
		}
	}
	
	public void addPoint(HPoint2D point) {
		int index = 0;
		if(points.size()>1) {
			index = points.size()-1;
			System.out.println(index);
		}
		points.add(index, point);
	}
	
	public void removeLastPoint() {
		points.remove(points.size()-1);
	}

}

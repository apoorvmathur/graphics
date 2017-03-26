
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JPanel;

public class PaintPanel extends JPanel {

	private static final long serialVersionUID = -6872174779428801314L;

	ArrayList<Shape> objects;
	boolean showAxes;

	public PaintPanel() {
		super();
		objects = new ArrayList<Shape>();
		showAxes = false;
	}

	public void addShape(Shape s) {
		objects.add(s);
		repaint();
	}

	@Override
	public PaintPanel clone() {
		PaintPanel canvas = new PaintPanel();
		for (int i = 0; i < objects.size(); i++) {
			canvas.addShape(objects.get(i).clone());
		}
		canvas.showAxes = showAxes;
		return canvas;
	}

	public ArrayList<Shape> getAllShapes() {
		return objects;
	}

	public Shape getShape(int i) {
		return objects.get(i);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.clearRect(0, 0, getWidth(), getHeight());
		if (g != null) {
			for (int i = 0; i < objects.size(); i++) {
				objects.get(i).paint(g);
			}
			
			if (showAxes) {
				g.setColor(Color.GRAY);
				g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
				g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
			}
		}
	}

	public void removeAllShapes() {
		objects.clear();
	}

	public void removeShape(int i) {
		objects.remove(i);
	}

	public void removeShape(Shape shape) {
		objects.remove(shape);
	}

	public void restore(File file) throws IOException {
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		while (br.ready()) {
			String g = br.readLine();
			if (g.split(" ")[0].equals("HLine2D")) {
				objects.add(HLine2D.restore(g));
			} else if (g.split(" ")[0].equals("Circle")) {
				objects.add(Circle.restore(g));
			} else if (g.split(" ")[0].equals("Rectangle")) {
				objects.add(Rectangle.restore(g));
			} else if (g.split(" ")[0].equals("Polygon2D")) {
				objects.add(Polygon2D.restore(g));
			} else if (g.split(" ")[0].equals("FreeShape")) {
				objects.add(FreeShape.restore(g));
			}
		}
		br.close();
		fr.close();
	}

	public void save(File file) throws IOException {
		FileWriter fw = new FileWriter(file);
		PrintWriter pw = new PrintWriter(fw);
		for (int i = 0; i < objects.size(); i++) {
			pw.println(objects.get(i).stringify());
		}
		pw.close();
		fw.close();
	}

	public void transform(Transform transform) throws InvalidTransformException {
		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).transform(transform);
		}
	}
	
}

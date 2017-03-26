
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyBoundsAdapter;
import java.awt.event.HierarchyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class GUI {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JFrame frame;

	private JPanel panel, panel_1;
	private JPanel panel_3;
	private JLabel lblMode;
	private JRadioButton rdbtnLine, rdbtnRectangle, rdbtnCircle;
	private ButtonGroup group;
	private HPoint2D point;
	private HLine2D line;
	private Circle circle;
	private Polygon2D polygon;
	private Rectangle rectangle;
	private FreeShape freeShape;
	private boolean shadowAct, lockSliders;
	private JSlider slider;
	private JSlider slider_1, slider_2;
	private double sliderLastVal, slider1LastVal, slider2LastVal;
	private int x, y, midX, midY;
	private JLabel lblRotate;
	private JCheckBox chckbxSolid, chckbxLock;
	private JPanel button;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmSave;
	private JMenuItem mntmLoad;
	private JMenuItem mntmExit;

	private static final int UNDO_STACK_SIZE = 50;

	private ArrayList<PaintPanel> list;
	PaintPanel panel_2;
	Color color;
	private JMenu mnEditor;
	private JCheckBoxMenuItem chckbxmntmShowAxes;
	private JMenuItem mntmUndo;
	private JRadioButton rdbtnPolygon;

	private JRadioButton rdbtnFreehand;

	/**
	 * Create the application.
	 */
	public GUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		x = -1;
		y = -1;
		lockSliders = true;
		color = Color.BLACK;
		panel_2 = new PaintPanel();
		initialize();
		group.add(rdbtnLine);
		group.add(rdbtnCircle);
		group.add(rdbtnRectangle);
		group.add(rdbtnPolygon);
		group.add(rdbtnFreehand);
		midX = panel_2.getWidth() / 2;
		midY = panel_2.getHeight() / 2;
		list = new ArrayList<PaintPanel>();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 625);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		panel_1 = new JPanel();
		panel_2.addHierarchyBoundsListener(new HierarchyBoundsAdapter() {
			@Override
			public void ancestorResized(HierarchyEvent arg0) {
				int x = panel_2.getWidth() / 2;
				int y = panel_2.getHeight() / 2;
				if (x != midX || y != midY) {
					try {
						panel_2.transform(new Translate2D(x - midX, y - midY));
					} catch (InvalidTransformException e) {
						e.printStackTrace();
					}
				}
				midX = x;
				midY = y;
			}
		});
		panel_2.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				double tr = 0;
				if (arg0.getWheelRotation() < 0) {
					tr = 1.2;
				} else {
					tr = 0.8;
				}
				try {
					panel_2.transform(new Translate2D(panel_2.getWidth() / 2, panel_2.getHeight() / 2, true));
					panel_2.transform(new Scale2D(tr));
					panel_2.transform(new Translate2D(panel_2.getWidth() / 2, panel_2.getHeight() / 2));
				} catch (InvalidTransformException e) {
					e.printStackTrace();
				}
				panel_2.repaint();
			}
		});
		panel_2.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				if (x == -1) {
					x = arg0.getX();
					y = arg0.getY();
				} else {
					if (rdbtnFreehand.isSelected()) {
						if (arg0.getX() != x || arg0.getY() != y) {
							HPoint2D point1 = new HPoint2D(arg0.getX(), arg0.getY(), 1);
							freeShape.addPoint(point1);
						}
					} else {
						try {
							panel_2.transform(new Translate2D(x - arg0.getX(), y - arg0.getY(), true));
						} catch (InvalidTransformException e) {
							e.printStackTrace();
						}
					}
					x = arg0.getX();
					y = arg0.getY();
				}
				panel_2.repaint();
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				panel_2.repaint();
				if (shadowAct) {
					if (rdbtnLine.isSelected()) {
						if (line != null) {
							panel_2.removeShape(line);
						}
						HPoint2D point1 = new HPoint2D(arg0.getX(), arg0.getY(), 1);
						line = new HLine2D(point, point1);
						line.setColor(Color.GRAY);
						panel_2.addShape(line);
					} else if (rdbtnCircle.isSelected()) {
						if (circle != null) {
							panel_2.removeShape(circle);
						}
						double l = Math.sqrt(
								Math.pow(point.getX() - arg0.getX(), 2) + Math.pow(point.getY() - arg0.getY(), 2));
						circle = new Circle(point, l);
						circle.setColor(Color.LIGHT_GRAY);
						panel_2.addShape(circle);
					} else if (rdbtnRectangle.isSelected()) {
						if (rectangle != null) {
							panel_2.removeShape(rectangle);
						}
						HPoint2D point1 = new HPoint2D(arg0.getX(), arg0.getY(), 1);
						rectangle = new Rectangle(point, point1);
						rectangle.setColor(Color.LIGHT_GRAY);
						panel_2.addShape(rectangle);
					} else if (rdbtnPolygon.isSelected()) {
						if (polygon.points.size() > 1) {
							polygon.removeLastPoint();
						}
						HPoint2D point1 = new HPoint2D(arg0.getX(), arg0.getY(), 1);
						polygon.addPoint(point1);
					}
				}
			}
		});
		panel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (rdbtnLine.isSelected()) {
					if (point == null) {
						point = new HPoint2D(arg0.getX(), arg0.getY(), 1);
						shadowAct = true;
					} else {
						int x = arg0.getX();
						int y = arg0.getY();
						HPoint2D point2 = new HPoint2D(x, y, 1);
						HLine2D line2 = new HLine2D(point, point2);
						line2.setColor(color);
						panel_2.removeShape(line);
						saveState();
						panel_2.addShape(line2);
						point = null;
						shadowAct = false;
					}
				} else if (rdbtnCircle.isSelected()) {
					if (point == null) {
						point = new HPoint2D(arg0.getX(), arg0.getY(), 1);
						shadowAct = true;
					} else {
						double l = point.getDist(new HPoint2D(arg0.getX(), arg0.getY(), 1));
						Circle circle1 = new Circle(point, l);
						circle1.setSolid(chckbxSolid.isSelected());
						circle1.setColor(color);
						panel_2.removeShape(circle);
						saveState();
						panel_2.addShape(circle1);
						point = null;
						shadowAct = false;
					}
				} else if (rdbtnRectangle.isSelected()) {
					if (point == null) {
						point = new HPoint2D(arg0.getX(), arg0.getY(), 1);
						shadowAct = true;
					} else {
						int x = arg0.getX();
						int y = arg0.getY();
						HPoint2D point2 = new HPoint2D(x, y, 1);
						Rectangle rect2 = new Rectangle(point, point2);
						rect2.setColor(color);
						rect2.setSolid(chckbxSolid.isSelected());
						panel_2.removeShape(rectangle);
						saveState();
						panel_2.addShape(rect2);
						point = null;
						shadowAct = false;
					}
				} else if (rdbtnPolygon.isSelected()) {
					if (polygon == null) {
						saveState();
						polygon = new Polygon2D();
						panel_2.addShape(polygon);
						polygon.setColor(color);
						polygon.setSolid(chckbxSolid.isSelected());
						point = new HPoint2D(arg0.getX(), arg0.getY(), 1);
						polygon.addPoint(point);
						shadowAct = true;
					} else {
						int x = arg0.getX();
						int y = arg0.getY();
						HPoint2D point1 = new HPoint2D(x, y, 1);
						polygon.addPoint(point1);
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				if (rdbtnLine.isSelected()) {
					panel_2.removeShape(line);
				} else if (rdbtnCircle.isSelected()) {
					panel_2.removeShape(circle);
				} else if (rdbtnRectangle.isSelected()) {
					panel_2.removeShape(rectangle);
				} else if (rdbtnPolygon.isSelected()) {
					if (shadowAct) {
						polygon.removeLastPoint();
						polygon.setColor(color);
					}
				}
				polygon = null;
				point = null;
				line = null;
				circle = null;
				rectangle = null;
				shadowAct = false;
				panel_2.repaint();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				x = -1;
				y = -1;
				freeShape = null;
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				if (rdbtnFreehand.isSelected()) {
					saveState();
					freeShape = new FreeShape();
					freeShape.setColor(color);
					freeShape.addPoint(new HPoint2D(arg0.getX(), arg0.getY(), 1));
					panel_2.addShape(freeShape);
				}
			}
		});
		panel_2.setBackground(Color.WHITE);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap())
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel_2,
								GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE))
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)));

		slider_1 = new JSlider();
		slider_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				slider_1.setValue(50);
				slider1LastVal = 50;
				if (lockSliders) {
					slider.setValue(50);
					sliderLastVal = 50;
				}
			}
		});
		slider_1.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				double val = slider_1.getValue();
				if (val != slider1LastVal) {
					double tr = 1;
					if ((slider1LastVal - val) > 0) {
						tr = 0.8;
					} else {
						tr = 1.2;
					}
					if (lockSliders) {
						slider.setValue(slider_1.getValue());
						try {
							panel_2.transform(new Translate2D(panel_2.getWidth() / 2, panel_2.getHeight() / 2, true));
							panel_2.transform(new Scale2D(tr));
							panel_2.transform(new Translate2D(panel_2.getWidth() / 2, panel_2.getHeight() / 2));
						} catch (InvalidTransformException e) {
							e.printStackTrace();
						}
					} else {
						try {
							panel_2.transform(new Translate2D(panel_2.getWidth() / 2, panel_2.getHeight() / 2, true));
							panel_2.transform(new Scale2D(1, tr));
							panel_2.transform(new Translate2D(panel_2.getWidth() / 2, panel_2.getHeight() / 2));
						} catch (InvalidTransformException e) {
							e.printStackTrace();
						}
					}
					panel_2.repaint();
					slider1LastVal = slider_1.getValue();
				}
			}
		});
		slider_1.setFocusable(false);
		slider_1.setOrientation(SwingConstants.VERTICAL);

		panel_3 = new JPanel();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
						.addComponent(slider_1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 489,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap())
								.addComponent(slider_1, GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE))));

		lblMode = new JLabel("Mode");
		lblMode.setFont(new Font("Segoe UI", Font.PLAIN, 14));

		rdbtnLine = new JRadioButton("Line");
		rdbtnLine.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isControlDown() && e.getKeyCode() == 90) {
					restoreState();
				}
			}
		});
		rdbtnLine.setSelected(true);
		rdbtnLine.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		rdbtnCircle = new JRadioButton("Circle");
		rdbtnCircle.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isControlDown() && e.getKeyCode() == 90) {
					restoreState();
				}
			}
		});
		rdbtnCircle.setFont(new Font("Segoe UI", Font.PLAIN, 11));

		group = new ButtonGroup();

		JLabel lblReflect = new JLabel("Reflect");
		lblReflect.setFont(new Font("Segoe UI", Font.PLAIN, 14));

		JButton btnXAxis = new JButton("X Axis");
		btnXAxis.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnXAxis.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isControlDown() && e.getKeyCode() == 90) {
					restoreState();
				}
			}
		});
		btnXAxis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveState();
				try {
					panel_2.transform(new Translate2D(panel_2.getWidth() / 2, panel_2.getHeight() / 2, true));
					panel_2.transform(new Scale2D(1, -1));
					panel_2.transform(new Translate2D(panel_2.getWidth() / 2, panel_2.getHeight() / 2));
					panel_2.repaint();
				} catch (InvalidTransformException e) {
					e.printStackTrace();
				}
			}
		});

		JButton btnYAxis = new JButton("Y Axis");
		btnYAxis.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnYAxis.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isControlDown() && e.getKeyCode() == 90) {
					restoreState();
				}
			}
		});
		btnYAxis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveState();
				try {
					panel_2.transform(new Translate2D(panel_2.getWidth() / 2, panel_2.getHeight() / 2, true));
					panel_2.transform(new Scale2D(-1, 1));
					panel_2.transform(new Translate2D(panel_2.getWidth() / 2, panel_2.getHeight() / 2));
					panel_2.repaint();
				} catch (InvalidTransformException e) {
					e.printStackTrace();
				}
			}
		});

		slider_2 = new JSlider();
		slider_2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isControlDown() && e.getKeyCode() == 90) {
					restoreState();
				}
			}
		});
		slider_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				saveState();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				slider_2.setValue(0);
				slider2LastVal = 0;
			}
		});
		slider_2.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				int val = slider_2.getValue();
				double diff = val - slider2LastVal;
				if (slider2LastVal != val) {
					if (diff > 0) {
						try {
							panel_2.transform(new Translate2D(panel_2.getWidth() / 2, panel_2.getHeight() / 2, true));
							panel_2.transform(new Rotate2D(Math.PI / 30));
							panel_2.transform(new Translate2D(panel_2.getWidth() / 2, panel_2.getHeight() / 2));
						} catch (InvalidTransformException e) {
							e.printStackTrace();
						}
					} else {
						try {
							panel_2.transform(new Translate2D(panel_2.getWidth() / 2, panel_2.getHeight() / 2, true));
							panel_2.transform(new Rotate2D(-Math.PI / 30));
							panel_2.transform(new Translate2D(panel_2.getWidth() / 2, panel_2.getHeight() / 2));
						} catch (InvalidTransformException e) {
							e.printStackTrace();
						}
					}
					slider2LastVal = val;
					panel_2.repaint();
				}
			}
		});
		slider_2.setMinorTickSpacing(1);
		slider_2.setMajorTickSpacing(3);
		slider_2.setMaximum(15);
		slider_2.setMinimum(-15);
		slider_2.setValue(0);
		slider_2.setSnapToTicks(true);
		slider_2.setPaintTicks(true);
		slider_2.setOrientation(SwingConstants.VERTICAL);

		lblRotate = new JLabel("Rotate");
		lblRotate.setFont(new Font("Segoe UI", Font.PLAIN, 14));

		JButton btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		btnClear.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isControlDown() && e.getKeyCode() == 90) {
					restoreState();
				}
			}
		});
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel_2.removeAllShapes();
				panel_2.repaint();
			}
		});

		chckbxSolid = new JCheckBox("Solid");
		chckbxSolid.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		chckbxSolid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isControlDown() && e.getKeyCode() == 90) {
					restoreState();
				}
			}
		});

		rdbtnRectangle = new JRadioButton("Rectangle");
		rdbtnRectangle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		rdbtnRectangle.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isControlDown() && e.getKeyCode() == 90) {
					restoreState();
				}
			}
		});

		rdbtnPolygon = new JRadioButton("Polygon");
		rdbtnPolygon.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		rdbtnPolygon.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isControlDown() && e.getKeyCode() == 90) {
					restoreState();
				}
			}
		});

		rdbtnFreehand = new JRadioButton("Freehand");
		rdbtnFreehand.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		rdbtnFreehand.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isControlDown() && e.getKeyCode() == 90) {
					restoreState();
				}
			}
		});

		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup().addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_3.createSequentialGroup().addGap(13)
								.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING).addComponent(rdbtnRectangle)
										.addComponent(rdbtnCircle).addComponent(rdbtnLine).addComponent(rdbtnPolygon)
										.addComponent(rdbtnFreehand)))
						.addGroup(gl_panel_3.createSequentialGroup().addGap(33).addComponent(lblMode))
						.addGroup(gl_panel_3.createSequentialGroup().addGap(28)
								.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING).addComponent(btnClear)
										.addComponent(chckbxSolid)))
						.addGroup(gl_panel_3.createSequentialGroup().addGap(5).addComponent(slider_2,
								GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_3.createSequentialGroup().addGap(31).addComponent(lblRotate))
						.addGroup(gl_panel_3.createSequentialGroup().addGap(21)
								.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING).addComponent(btnXAxis)
										.addComponent(btnYAxis)))
						.addGroup(gl_panel_3.createSequentialGroup().addGap(31).addComponent(lblReflect)))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_panel_3.setVerticalGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup().addContainerGap(20, Short.MAX_VALUE).addComponent(lblMode)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(rdbtnLine)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(rdbtnCircle)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(rdbtnRectangle)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(rdbtnPolygon)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(rdbtnFreehand).addGap(9)
						.addComponent(lblReflect).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnXAxis)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnYAxis)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblRotate)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(slider_2, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(chckbxSolid)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnClear)));
		panel_3.setLayout(gl_panel_3);
		panel.setLayout(gl_panel);

		slider = new JSlider();
		slider.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isControlDown() && e.getKeyCode() == 90) {
					restoreState();
				}
			}
		});
		slider.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				slider.setValue(50);
				sliderLastVal = 50;
				if (lockSliders) {
					slider_1.setValue(50);
					slider1LastVal = 50;
				}
			}
		});
		slider.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				double val = slider.getValue();
				if (val != sliderLastVal) {
					double tr = 1;
					if ((sliderLastVal - val) > 0) {
						tr = 0.8;
					} else {
						tr = 1.2;
					}
					if (lockSliders) {
						slider_1.setValue(slider.getValue());
						try {
							panel_2.transform(new Translate2D(panel_2.getWidth() / 2, panel_2.getHeight() / 2, true));
							panel_2.transform(new Scale2D(tr));
							panel_2.transform(new Translate2D(panel_2.getWidth() / 2, panel_2.getHeight() / 2));
						} catch (InvalidTransformException e) {
							e.printStackTrace();
						}
					} else {
						try {
							panel_2.transform(new Translate2D(panel_2.getWidth() / 2, panel_2.getHeight() / 2, true));
							panel_2.transform(new Scale2D(tr, 1));
							panel_2.transform(new Translate2D(panel_2.getWidth() / 2, panel_2.getHeight() / 2));
						} catch (InvalidTransformException e) {
							e.printStackTrace();
						}
					}
					panel_2.repaint();
					sliderLastVal = slider.getValue();
				}
			}
		});
		slider.setFocusable(false);

		chckbxLock = new JCheckBox("Scale Uniform");
		chckbxLock.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isControlDown() && e.getKeyCode() == 90) {
					restoreState();
				}
			}
		});
		chckbxLock.setSelected(true);
		chckbxLock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lockSliders = chckbxLock.isSelected();
			}
		});

		button = new JPanel();
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				popup();
			}
		});
		button.setBackground(Color.BLACK);
		button.setBorder(null);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1
				.setHorizontalGroup(
						gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup().addContainerGap()
										.addComponent(slider, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(chckbxLock)
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(button,
												GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE).addComponent(chckbxLock)
								.addComponent(button, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
						.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addContainerGap(19, Short.MAX_VALUE)));
		panel_1.setLayout(gl_panel_1);
		frame.getContentPane().setLayout(groupLayout);

		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				save();
			}
		});
		mnFile.add(mntmSave);

		mntmLoad = new JMenuItem("Load");
		mntmLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				load();
			}
		});
		mnFile.add(mntmLoad);

		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		mnFile.add(mntmExit);

		mnEditor = new JMenu("Editor");
		menuBar.add(mnEditor);

		chckbxmntmShowAxes = new JCheckBoxMenuItem("Show Axes");
		chckbxmntmShowAxes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel_2.showAxes = chckbxmntmShowAxes.isSelected();
				panel_2.repaint();
			}
		});
		mnEditor.add(chckbxmntmShowAxes);

		mntmUndo = new JMenuItem("Undo");
		mntmUndo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				restoreState();
			}
		});
		mnEditor.add(mntmUndo);
	}

	protected void load() {
		saveState();
		LoadWindow window = new LoadWindow(this);
		window.setVisible(true);
	}

	protected void popup() {
		ColorPopup popup = new ColorPopup(this);
		popup.setVisible(true);
	}

	public void refreshColor() {
		button.setBackground(color);
	}

	protected void restoreState() {
		if (list.size() > 0) {
			panel_2.objects = list.get(list.size() - 1).objects;
			panel_2.showAxes = list.get(list.size() - 1).showAxes;
			list.remove(list.size() - 1);
		}
		panel_2.repaint();
	}

	protected void save() {
		SaveWindow window = new SaveWindow(this);
		window.setVisible(true);
	}

	protected void saveState() {
		if (list.size() > UNDO_STACK_SIZE) {
			list.remove(0);
		}
		list.add(panel_2.clone());
	}
}

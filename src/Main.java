
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

public class Main {

	class Task extends TimerTask {

		@Override
		public void run() {
			try {
				ticks0++;
				if (ticks0 == 100) {
					rotate(line0);
					ticks1++;
					ticks0 = 0;
				}
				if (ticks1 == 60) {
					paintPanel.removeShape(line0);
					line0 = new HLine2D(new HPoint2D(250, 200, 1), new HPoint2D(250, 150, 1));
					paintPanel.addShape(line0);
					ticks1 = 0;
					rotate(line1);
					ticks2++;
				}
				if (ticks2 == 60) {
					paintPanel.removeShape(line1);
					line1 = new HLine2D(new HPoint2D(250, 200, 1), new HPoint2D(250, 150, 1));
					paintPanel.addShape(line1);
					ticks2 = 0;
					rotate(line2);
				}
				paintPanel.repaint();
				if (state) {
					timer.schedule(new Task(), 10);
				}

			} catch (InvalidTransformException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JFrame frame;
	Timer timer;
	int ticks0, ticks1, ticks2;
	Rotate2D transform;
	Translate2D transform1, transform2;

	HLine2D line0, line1, line2;

	boolean state;
	JButton btnReset, btnStart;

	private PaintPanel paintPanel;

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
		frame.setVisible(true);
		ticks0 = 0;
		ticks1 = 0;
		ticks2 = 0;

		initLines();

		timer = new Timer();

		transform = new Rotate2D(Math.PI / 30);
		transform1 = new Translate2D(250, 200, true);
		transform2 = new Translate2D(250, 200);

		Circle cir = new Circle(new HPoint2D(250, 200, 1), 60);
		Circle cir1 = new Circle(new HPoint2D(250, 200, 1), 3);

		cir.setColor(Color.BLUE);
		cir1.setColor(Color.BLUE);
		cir1.setSolid(true);

		paintPanel.addShape(cir);
		paintPanel.addShape(cir1);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 516, 439);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		paintPanel = new PaintPanel();
		paintPanel.setBackground(Color.WHITE);
		frame.getContentPane().add(paintPanel, BorderLayout.CENTER);

		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (state) {
					btnStart.setText("Start");
					timer.cancel();
					btnReset.setEnabled(true);
					state = !state;
				} else {
					btnStart.setText("Stop");
					timer = new Timer();
					timer.schedule(new Task(), 10);
					state = !state;
				}
			}
		});

		btnReset = new JButton("Reset");
		btnReset.setEnabled(false);
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ticks0 = 0;
				ticks1 = 0;
				ticks2 = 0;
				initLines();
				btnReset.setEnabled(false);
			}
		});
		GroupLayout gl_paintCanvas = new GroupLayout(paintPanel);
		gl_paintCanvas.setHorizontalGroup(gl_paintCanvas.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_paintCanvas
						.createSequentialGroup().addGap(220).addGroup(gl_paintCanvas
								.createParallelGroup(Alignment.LEADING).addComponent(btnReset).addComponent(btnStart))
				.addContainerGap(219, Short.MAX_VALUE)));
		gl_paintCanvas
				.setVerticalGroup(gl_paintCanvas.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
						gl_paintCanvas.createSequentialGroup().addContainerGap(330, Short.MAX_VALUE)
								.addComponent(btnStart).addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnReset).addGap(18)));
		gl_paintCanvas.linkSize(SwingConstants.HORIZONTAL, new Component[] { btnReset, btnStart });
		paintPanel.setLayout(gl_paintCanvas);
	}

	protected void initLines() {
		paintPanel.removeShape(line0);
		paintPanel.removeShape(line1);
		paintPanel.removeShape(line2);
		line0 = new HLine2D(new HPoint2D(250, 200, 1), new HPoint2D(250, 150, 1));
		line1 = new HLine2D(new HPoint2D(250, 200, 1), new HPoint2D(250, 160, 1));
		line2 = new HLine2D(new HPoint2D(250, 200, 1), new HPoint2D(250, 170, 1));
		paintPanel.addShape(line0);
		paintPanel.addShape(line1);
		paintPanel.addShape(line2);
	}

	public void rotate(Line line) throws InvalidTransformException {
		line.transform(transform1);
		line.transform(transform);
		line.transform(transform2);
	}
}

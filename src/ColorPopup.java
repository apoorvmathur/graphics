
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorPopup extends JDialog {

	class MouseAdapt extends MouseAdapter {

		JPanel panel;

		public MouseAdapt(JPanel panel) {
			this.panel = panel;
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			okButton.setEnabled(true);
			Color color1 = panel.getBackground();
			spinner.setValue(color1.getRed());
			spinner_1.setValue(color1.getGreen());
			spinner_2.setValue(color1.getBlue());
			color = color1;
			panel_4.setBackground(color);
		}
	}

	private static final long serialVersionUID = -4572030571208918598L;
	private final JPanel contentPanel = new JPanel();
	private Color color;
	private JPanel panel_4;
	JSpinner spinner, spinner_1, spinner_2;

	private JPanel colorPane1, colorPane2, colorPane3, colorPane4, colorPane5, colorPane6, colorPane7, colorPane8,
			colorPane9, colorPane10, colorPane11, colorPane12;
	GUI gui;
	private JButton okButton;

	private boolean enabled;

	/**
	 * Create the dialog.
	 */
	public ColorPopup(GUI window) {
		this.gui = window;

		color = gui.color;

		setBounds(100, 100, 325, 180);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setVisible(true);

		getContentPane().add(contentPanel, BorderLayout.CENTER);

		colorPane1 = new JPanel();
		colorPane1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		colorPane1.setBackground(Color.BLACK);
		colorPane2 = new JPanel();
		colorPane2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		colorPane2.setBackground(Color.GRAY);
		colorPane3 = new JPanel();
		colorPane3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		colorPane3.setBackground(Color.RED);
		colorPane5 = new JPanel();
		colorPane5.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		colorPane5.setBackground(Color.DARK_GRAY);
		colorPane4 = new JPanel();
		colorPane4.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		colorPane4.setBackground(Color.YELLOW);
		colorPane6 = new JPanel();
		colorPane6.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		colorPane6.setBackground(Color.LIGHT_GRAY);
		colorPane7 = new JPanel();
		colorPane7.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		colorPane7.setBackground(Color.ORANGE);
		colorPane8 = new JPanel();
		colorPane8.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		colorPane8.setBackground(Color.GREEN);
		colorPane9 = new JPanel();
		colorPane9.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		colorPane9.setBackground(Color.WHITE);
		colorPane10 = new JPanel();
		colorPane10.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		colorPane10.setBackground(Color.BLUE);
		colorPane11 = new JPanel();
		colorPane11.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		colorPane11.setBackground(Color.MAGENTA);
		colorPane12 = new JPanel();
		colorPane12.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		colorPane12.setBackground(Color.CYAN);

		addAdapters();

		JLabel lblR = new JLabel("R:");

		JLabel lblG = new JLabel("G:");

		JLabel lblB = new JLabel("B:");

		spinner = new JSpinner();
		spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (enabled) {
					color = new Color((int) spinner.getValue(), (int) spinner_1.getValue(), (int) spinner_2.getValue());
					panel_4.setBackground(color);
				}
			}
		});
		spinner.setModel(new SpinnerNumberModel(0, 0, 255, 1));

		spinner_1 = new JSpinner();
		spinner_1.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (enabled) {
					color = new Color((int) spinner.getValue(), (int) spinner_1.getValue(), (int) spinner_2.getValue());
					panel_4.setBackground(color);
				}
			}
		});
		spinner_1.setModel(new SpinnerNumberModel(0, 0, 255, 1));

		spinner_2 = new JSpinner();
		spinner_2.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (enabled) {
					color = new Color((int) spinner.getValue(), (int) spinner_1.getValue(), (int) spinner_2.getValue());
					panel_4.setBackground(color);
				}
			}
		});
		spinner_2.setModel(new SpinnerNumberModel(0, 0, 255, 1));

		panel_4 = new JPanel();
		panel_4.setBackground(color);

		spinner.setValue(color.getRed());
		spinner_1.setValue(color.getGreen());
		spinner_2.setValue(color.getBlue());

		enabled = true;

		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel
				.setHorizontalGroup(
						gl_contentPanel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPanel.createSequentialGroup().addContainerGap()
										.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_contentPanel.createSequentialGroup()
														.addComponent(colorPane1, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(colorPane2, GroupLayout.PREFERRED_SIZE, 33,
																GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(colorPane3, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(colorPane4, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addGroup(
								gl_contentPanel.createSequentialGroup()
										.addComponent(colorPane5, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(colorPane6, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(colorPane7, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(colorPane8,
												GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addGroup(
								gl_contentPanel.createSequentialGroup()
										.addComponent(colorPane9, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(colorPane10, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(colorPane11, GroupLayout.PREFERRED_SIZE, 33,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(colorPane12,
												GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)))
						.addGap(18)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addComponent(lblR)
								.addComponent(lblG).addComponent(lblB))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 44, Short.MAX_VALUE)
								.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)).addGap(18)
						.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE).addGap(18)));
		gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPanel
				.createSequentialGroup()
				.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPanel
						.createSequentialGroup().addGap(20)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPanel.createSequentialGroup()
										.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblR).addComponent(spinner, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblG).addComponent(spinner_1, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblB)
								.addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPanel.createSequentialGroup().addContainerGap().addGroup(gl_contentPanel
								.createParallelGroup(Alignment.LEADING)
								.addComponent(colorPane1, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(colorPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(colorPane3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(colorPane4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(colorPane8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(colorPane7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(colorPane6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(colorPane5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
										.addComponent(colorPane9, GroupLayout.PREFERRED_SIZE, 23,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(colorPane10, GroupLayout.PREFERRED_SIZE, 23,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(colorPane11, GroupLayout.PREFERRED_SIZE, 23,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(colorPane12, GroupLayout.PREFERRED_SIZE, 23,
												GroupLayout.PREFERRED_SIZE))))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_contentPanel.linkSize(SwingConstants.VERTICAL,
				new Component[] { colorPane3, colorPane5, colorPane4, colorPane6, colorPane7, colorPane8, colorPane9,
						colorPane10, colorPane11, colorPane12, colorPane1, colorPane2 });
		gl_contentPanel.linkSize(SwingConstants.HORIZONTAL,
				new Component[] { colorPane3, colorPane5, colorPane4, colorPane6, colorPane7, colorPane8, colorPane9,
						colorPane10, colorPane11, colorPane12, colorPane1, colorPane2 });
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						enabled = false;
						gui.color = color;
						gui.refreshColor();
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						cancel();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	protected void addAdapters() {
		colorPane1.addMouseListener(new MouseAdapt(colorPane1));
		colorPane2.addMouseListener(new MouseAdapt(colorPane2));
		colorPane3.addMouseListener(new MouseAdapt(colorPane3));
		colorPane4.addMouseListener(new MouseAdapt(colorPane4));
		colorPane5.addMouseListener(new MouseAdapt(colorPane5));
		colorPane6.addMouseListener(new MouseAdapt(colorPane6));
		colorPane7.addMouseListener(new MouseAdapt(colorPane7));
		colorPane8.addMouseListener(new MouseAdapt(colorPane8));
		colorPane9.addMouseListener(new MouseAdapt(colorPane9));
		colorPane10.addMouseListener(new MouseAdapt(colorPane10));
		colorPane11.addMouseListener(new MouseAdapt(colorPane11));
		colorPane12.addMouseListener(new MouseAdapt(colorPane12));
	}

	protected void cancel() {
		enabled = false;
		this.dispose();
	}
}

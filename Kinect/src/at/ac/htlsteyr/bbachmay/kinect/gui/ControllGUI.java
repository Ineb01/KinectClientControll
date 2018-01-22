package at.ac.htlsteyr.bbachmay.kinect.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;

public class ControllGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton freeze_btn;
	private JButton exitprog_btn;
	private JProgressBar bar_left;
	private JProgressBar bar_right;
	private JLabel skeleton_indicator;
	private JLabel motor0;
	private JLabel motor1;
	private JLabel motor2;
	private JLabel motor3;
	private boolean frozen;
	private boolean exit;
	private JLabel Baseplate;
	private JLabel warning;
	private JPanel ButtonWrapper;
	private JPanel BaseplateWrapper;
	private MotorWrapper left;
	private MotorWrapper right;

	private Image motor_fwd;
	private Image motor_rev;
	private Image motor_stp;
	private Image motor_fwd_m;
	private Image motor_rev_m;
	private Image motor_stp_m;
	private Image baseplate_icon;

	/**
	 * Create the frame.
	 */
	public ControllGUI() {
		try {
			motor_fwd = ImageIO.read(ControllGUI.class.getResource("/at/restental/andreas/icons/motor_fwd.png"));
			motor_rev = ImageIO.read(ControllGUI.class.getResource("/at/restental/andreas/icons/motor_rev.png"));
			motor_stp = ImageIO.read(ControllGUI.class.getResource("/at/restental/andreas/icons/motor_stop.png"));
			motor_fwd_m = ImageIO
					.read(ControllGUI.class.getResource("/at/restental/andreas/icons/motor_fwd_mirror.png"));
			motor_rev_m = ImageIO
					.read(ControllGUI.class.getResource("/at/restental/andreas/icons/motor_rev_mirror.png"));
			motor_stp_m = ImageIO
					.read(ControllGUI.class.getResource("/at/restental/andreas/icons/motor_stop_mirror.png"));
			baseplate_icon = ImageIO.read(ControllGUI.class.getResource("/at/restental/andreas/icons/base.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frozen = false;
		exit = false;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 733, 524);
		setTitle("Robo-Controll");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		ButtonWrapper = new JPanel();
		contentPane.add(ButtonWrapper, BorderLayout.SOUTH);
		ButtonWrapper.setLayout(new GridLayout(1, 0, 0, 0));

		freeze_btn = new JButton("Freeze");
		ButtonWrapper.add(freeze_btn);
		freeze_btn.addActionListener(this);
		freeze_btn.setFont(new Font("Tahoma", Font.BOLD, 11));
		freeze_btn.setActionCommand("freeze");

		bar_right = new JProgressBar(-360, 360);
		ButtonWrapper.add(bar_right);
		bar_right.setStringPainted(true);

		bar_left = new JProgressBar(-360, 360);
		ButtonWrapper.add(bar_left);
		bar_left.setStringPainted(true);

		exitprog_btn = new JButton("Exit");
		ButtonWrapper.add(exitprog_btn);
		exitprog_btn.addActionListener(this);
		exitprog_btn.setActionCommand("exit");

		skeleton_indicator = new JLabel("");
		skeleton_indicator.setHorizontalAlignment(SwingConstants.CENTER);
		skeleton_indicator.setPreferredSize(new Dimension(16, 16));
		ButtonWrapper.add(skeleton_indicator);
		skeleton_indicator.setIcon(
				new ImageIcon(ControllGUI.class.getResource("/at/restental/andreas/icons/conection_unknown.png")));

		JPanel DisplayWrapper = new JPanel();
		contentPane.add(DisplayWrapper, BorderLayout.CENTER);
		DisplayWrapper.setLayout(new GridLayout(0, 3, 0, 0));

		motor0 = new JLabel("");
		motor0.setIcon(new ImageIcon(motor_stp));

		motor1 = new JLabel("");
		motor1.setIcon(new ImageIcon(motor_stp));

		left = new MotorWrapper(BorderLayout.WEST, motor0, motor1);
		DisplayWrapper.add(left);

		BaseplateWrapper = new JPanel();
		DisplayWrapper.add(BaseplateWrapper);
		BaseplateWrapper.setLayout(null);

		warning = new JLabel("");
		warning.setBounds(0, 0, 200, 200);
		BaseplateWrapper.add(warning);
		warning.setIcon(new ImageIcon(ControllGUI.class.getResource("/at/restental/andreas/icons/dist.png")));
		warning.setVisible(false);

		Baseplate = new JLabel("");
		Baseplate.setHorizontalAlignment(SwingConstants.CENTER);
		Baseplate.setBounds(0, 0, 210, 390);
		BaseplateWrapper.add(Baseplate);
		Baseplate.setIcon(new ImageIcon(baseplate_icon));

		motor3 = new JLabel("");
		motor3.setIcon(new ImageIcon(motor_stp_m));

		motor2 = new JLabel("");
		motor2.setIcon(new ImageIcon(motor_stp_m));

		right = new MotorWrapper(BorderLayout.EAST, motor2, motor3);
		DisplayWrapper.add(right);

		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("freeze"))
			frozen = !frozen;
		if (e.getActionCommand().equals("exit"))
			exit = true;
	}

	public void setValueLeft(int value) {
		bar_left.setValue(value);
	}

	public void setValueRight(int value) {
		bar_right.setValue(value);
	}

	public boolean frozen() {
		return frozen;
	}

	public boolean is_exit() {
		Baseplate.setIcon(new ImageIcon(baseplate_icon.getScaledInstance(BaseplateWrapper.getWidth(),
				BaseplateWrapper.getHeight(), Image.SCALE_SMOOTH)));
		Baseplate.setBounds(0, 0, BaseplateWrapper.getWidth(), BaseplateWrapper.getHeight());
		
		
		
		return exit;
	}

	public void updateVisuals(String s) {
		if (s.contains("left")) {
			if (s.contains("fwd")) {
				motor0.setIcon(new ImageIcon(motor_fwd));
				motor1.setIcon(new ImageIcon(motor_fwd));
			} else if (s.contains("rev")) {
				motor0.setIcon(new ImageIcon(motor_rev));
				motor1.setIcon(new ImageIcon(motor_rev));
			} else if (s.contains("off")) {
				motor0.setIcon(new ImageIcon(motor_stp));
				motor1.setIcon(new ImageIcon(motor_stp));
			}
		} else if (s.contains("right")) {
			if (s.contains("fwd")) {
				motor2.setIcon(new ImageIcon(motor_fwd_m));
				motor3.setIcon(new ImageIcon(motor_fwd_m));
			} else if (s.contains("rev")) {
				motor2.setIcon(new ImageIcon(motor_rev_m));
				motor3.setIcon(new ImageIcon(motor_rev_m));
			} else if (s.contains("off")) {
				motor2.setIcon(new ImageIcon(motor_stp_m));
				motor3.setIcon(new ImageIcon(motor_stp_m));
			}
		} else if (s.contains("dist")) {
			if (s.contains("distend")) {
				warning.setVisible(false);
			} else {
				warning.setVisible(true);
			}
		}
	}

	public void setSkeletonIndicator(boolean set) {
		if (set == true) {
			skeleton_indicator.setIcon(
					new ImageIcon(ControllGUI.class.getResource("/at/restental/andreas/icons/conection_good.png")));
		} else {
			skeleton_indicator.setIcon(
					new ImageIcon(ControllGUI.class.getResource("/at/restental/andreas/icons/conection_bad.png")));
		}
	}
}

package at.ac.htlsteyr.bbachmay.kinect.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private JLabel Basaeplate;

	/**
	 * Create the frame.
	 */
	public ControllGUI() {
		frozen = false;
		exit = false;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 500);
		setTitle("Robo-Controll");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		freeze_btn = new JButton("Freeze");
		freeze_btn.setBounds(10, 427, 100, 23);
		freeze_btn.addActionListener(this);
		contentPane.setLayout(null);
		freeze_btn.setFont(new Font("Tahoma", Font.BOLD, 11));
		freeze_btn.setActionCommand("freeze");
		contentPane.add(freeze_btn);

		bar_right = new JProgressBar(-360, 360);
		bar_right.setBounds(120, 431, 146, 17);
		bar_right.setStringPainted(true);
		contentPane.add(bar_right);

		bar_left = new JProgressBar(-360, 360);
		bar_left.setBounds(343, 431, 146, 17);
		bar_left.setStringPainted(true);
		contentPane.add(bar_left);

		exitprog_btn = new JButton("Exit");
		exitprog_btn.setBounds(499, 427, 92, 23);
		exitprog_btn.addActionListener(this);
		exitprog_btn.setActionCommand("exit");
		contentPane.add(exitprog_btn);

		skeleton_indicator = new JLabel("");
		skeleton_indicator.setIcon(new ImageIcon(ControllGUI.class.getResource("/at/restental/andreas/icons/conection_unknown.png")));
		skeleton_indicator.setBounds(601, 427, 23, 23);
		contentPane.add(skeleton_indicator);
		
		motor0 = new JLabel("");
		motor0.setIcon(new ImageIcon(ControllGUI.class.getResource("/at/restental/andreas/icons/motor_stop.png")));
		motor0.setBounds(120, 10, 80, 100);
		contentPane.add(motor0);
		
		motor1 = new JLabel("");
		motor1.setIcon(new ImageIcon(ControllGUI.class.getResource("/at/restental/andreas/icons/motor_stop.png")));
		motor1.setBounds(120, 320, 80, 100);
		contentPane.add(motor1);
		
		motor2 = new JLabel("");
		motor2.setIcon(new ImageIcon(ControllGUI.class.getResource("/at/restental/andreas/icons/motor_stop_mirror.png")));
		motor2.setBounds(410, 10, 80, 100);
		contentPane.add(motor2);
		
		motor3 = new JLabel("");
		motor3.setIcon(new ImageIcon(ControllGUI.class.getResource("/at/restental/andreas/icons/motor_stop_mirror.png")));
		motor3.setBounds(410, 320, 80, 100);
		contentPane.add(motor3);
		
		Basaeplate = new JLabel("");
		Basaeplate.setIcon(new ImageIcon(ControllGUI.class.getResource("/at/restental/andreas/icons/base.png")));
		Basaeplate.setBounds(200, 21, 210, 390);
		contentPane.add(Basaeplate);

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
		return exit;
	}
	
	public void updateVisuals(String s) {
		if(s.contains("left")) {
			if(s.contains("fwd")) {
				motor0.setIcon(new ImageIcon(ControllGUI.class.getResource("/at/restental/andreas/icons/motor_fwd.png")));
				motor1.setIcon(new ImageIcon(ControllGUI.class.getResource("/at/restental/andreas/icons/motor_fwd.png")));
			}else if(s.contains("rev")) {
				motor0.setIcon(new ImageIcon(ControllGUI.class.getResource("/at/restental/andreas/icons/motor_rev.png")));
				motor1.setIcon(new ImageIcon(ControllGUI.class.getResource("/at/restental/andreas/icons/motor_rev.png")));
			}else if(s.contains("off")) {
				motor0.setIcon(new ImageIcon(ControllGUI.class.getResource("/at/restental/andreas/icons/motor_stop.png")));
				motor1.setIcon(new ImageIcon(ControllGUI.class.getResource("/at/restental/andreas/icons/motor_stop.png")));
			}
		}else if(s.contains("right")) {
			if(s.contains("fwd")) {
				motor2.setIcon(new ImageIcon(ControllGUI.class.getResource("/at/restental/andreas/icons/motor_fwd_mirror.png")));
				motor3.setIcon(new ImageIcon(ControllGUI.class.getResource("/at/restental/andreas/icons/motor_fwd_mirror.png")));
			}else if(s.contains("rev")) {
				motor2.setIcon(new ImageIcon(ControllGUI.class.getResource("/at/restental/andreas/icons/motor_rev_mirror.png")));
				motor3.setIcon(new ImageIcon(ControllGUI.class.getResource("/at/restental/andreas/icons/motor_rev_mirror.png")));
			}else if(s.contains("off")) {
				motor2.setIcon(new ImageIcon(ControllGUI.class.getResource("/at/restental/andreas/icons/motor_stop_mirror.png")));
				motor3.setIcon(new ImageIcon(ControllGUI.class.getResource("/at/restental/andreas/icons/motor_stop_mirror.png")));
			}
		}
	}

	public void setSkeletonIndicator(boolean set) {
		if (set == true) {
			skeleton_indicator.setIcon(new ImageIcon(ControllGUI.class.getResource("/at/restental/andreas/icons/conection_good.png")));
		} else {
			skeleton_indicator.setIcon(new ImageIcon(ControllGUI.class.getResource("/at/restental/andreas/icons/conection_bad.png")));
		}
	}
}

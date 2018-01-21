package at.ac.htlsteyr.bbachmay.kinect.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class MotorWrapper extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel WrapperTop;
	private JPanel WrapperBot;
	
	public MotorWrapper(String constraint, JLabel top, JLabel bot) {
		setLayout(new BorderLayout(0, 0));
		
		JPanel top_panel = new JPanel();
		add(top_panel, BorderLayout.NORTH);
		top_panel.setLayout(new BorderLayout(0, 0));
		
		WrapperTop = new JPanel();
		WrapperTop.add(top);
		top_panel.add(WrapperTop, BorderLayout.NORTH);
		
		JPanel bot_panel = new JPanel();
		add(bot_panel, BorderLayout.SOUTH);
		bot_panel.setLayout(new BorderLayout(0, 0));
		
		WrapperBot = new JPanel();
		WrapperBot.add(bot);
		bot_panel.add(WrapperBot, BorderLayout.NORTH);
		
		if(constraint.equals(BorderLayout.EAST)) {
			top_panel.add(WrapperTop, BorderLayout.WEST);
			bot_panel.add(WrapperBot, BorderLayout.WEST);
		}else {
			top_panel.add(WrapperTop, BorderLayout.EAST);
			bot_panel.add(WrapperBot, BorderLayout.EAST);
		}
		
	}
	public Dimension getWrapperSizeTop() {
		return WrapperTop.getSize();
	}
	public Dimension getWrapperSizeBot() {
		return WrapperBot.getSize();
	}


}

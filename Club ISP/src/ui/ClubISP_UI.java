package ui;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import utility.LookAndFeel;
import utility.PaddedJComponent;

public class ClubISP_UI extends JFrame {
	
	public static final JPanel pan;
	
	static {
		pan = new JPanel();
		pan.setLayout(new BorderLayout());
	}
	
	public ClubISP_UI() {
		final int scrwidth=Toolkit.getDefaultToolkit().getScreenSize().width;
        final int scrheight=Toolkit.getDefaultToolkit().getScreenSize().height;
		
        int framewidth = 600+scrwidth/10;
        int frameheight = 400+scrheight/10;
		
        setTitle("Club ISPs");
        
        setSize(framewidth, frameheight);
		setLocation((scrwidth-framewidth)/2, (scrheight-frameheight)/2);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		pan.add(new PaddedJComponent(new HomeScreen(framewidth, frameheight), 20));
		add(pan, BorderLayout.CENTER);
		
	}
	
	public static void main(String[] args) {
		LookAndFeel.setSystemLookAndFeel();
		
		new ClubISP_UI().setVisible(true);
	}
}

package utility;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class PaddedJComponent extends JPanel {
	final JComponent component;
	
	public PaddedJComponent(JComponent component, int gap) {
		this.component = component;
		setLayout(new BorderLayout());
		add(Box.createVerticalStrut(gap), BorderLayout.NORTH);
		add(Box.createHorizontalStrut(gap), BorderLayout.EAST);
		add(Box.createVerticalStrut(gap), BorderLayout.SOUTH);
		add(Box.createHorizontalStrut(gap), BorderLayout.WEST);
		add(component);
	}
	
	public PaddedJComponent(JComponent component, int topGap, int rightGap, int bottomGap, int leftGap) {
		this.component = component;
		setLayout(new BorderLayout());
		add(Box.createVerticalStrut(topGap), BorderLayout.NORTH);
		add(Box.createHorizontalStrut(rightGap), BorderLayout.EAST);
		add(Box.createVerticalStrut(bottomGap), BorderLayout.SOUTH);
		add(Box.createHorizontalStrut(leftGap), BorderLayout.WEST);
		add(component);
	}
}

package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utility.PaddedJComponent;

public class IspPanel extends JPanel implements MouseListener {
	
	public JLabel label;
	public JLabel imgLabel;
	
	private static ImageIcon activeIcon;
	private static ImageIcon inactiveIcon;
	
	public IspPanel(String title, int iconLength)
	{
		setLayout(new BorderLayout());
		setOpaque(false);
		
		activeIcon = new ImageIcon(getClass().getResource("../resources/images/active_isp.png"));
		activeIcon.setImage(activeIcon.getImage().getScaledInstance(iconLength, iconLength, Image.SCALE_SMOOTH));
		
		inactiveIcon = new ImageIcon(getClass().getResource("../resources/images/inactive_isp.png"));
		inactiveIcon.setImage(inactiveIcon.getImage().getScaledInstance(iconLength, iconLength, Image.SCALE_SMOOTH));
		
		imgLabel = new JLabel(activeIcon);
		imgLabel.addMouseListener(this);
		imgLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		JPanel paddedPanW = new PaddedJComponent(imgLabel, 0);
		paddedPanW.setOpaque(false);
		add(paddedPanW, BorderLayout.WEST);

		label = new JLabel(title);
		label.setFont(new Font("New Courier", 0, 14));
		add(label, BorderLayout.CENTER);
	}
	
	
	private void activate() {
		imgLabel.setIcon(activeIcon);
	}
	
	private void deActivate() {
		imgLabel.setIcon(inactiveIcon);
	}
	
	private boolean isActive() {
		return imgLabel.getIcon() == activeIcon;
	}


	@Override
	public void mouseClicked(MouseEvent event) {
		if(event.getSource()==imgLabel)
		{
			if(isActive()) {
				deActivate();
			}
			else {
				activate();
			}
		}
		else
		{
			
		}
	}

	public void disableInterface()
	{
		deActivate();
	}
	
	public void enableInterface()
	{
		activate();
	}
	
	@Override
	public void mouseEntered(MouseEvent event) {
	}

	@Override
	public void mouseExited(MouseEvent event) {
	}

	@Override
	public void mousePressed(MouseEvent event) {
	}

	@Override
	public void mouseReleased(MouseEvent event) {
	}
	
	public JLabel getImgLabel()
	{
		return imgLabel;
	}
	

	@Override
	public synchronized void addMouseListener(MouseListener l) {
		imgLabel.addMouseListener(l);
		super.addMouseListener(l);
	}
	
	
}

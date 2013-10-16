package ui;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Isp;

public class IspDetailPanel extends JPanel {
	
	public JLabel getLabel(String text, int style, int alignment)
	{
		JLabel label = new JLabel(text);
		label.setHorizontalAlignment(alignment);
		label.setFont(new Font("New Courier", style, 15));
		return label;
	}
	
	public IspDetailPanel(Isp isp)
	{
		setLayout(new GridLayout(6, 2, 5, 5));
		setOpaque(false);
		
		add(getLabel("Interface Name :", 0, JLabel.RIGHT));
		add(getLabel(isp.getIface(), 1, JLabel.LEFT));
		add(getLabel("IP Address :", 0, JLabel.RIGHT));
		add(getLabel(isp.getIp_address(), 1, JLabel.LEFT));
		add(getLabel("Network Address :", 0, JLabel.RIGHT));
		add(getLabel(isp.getNetwork(), 1, JLabel.LEFT));
		add(getLabel("Default Route :", 0, JLabel.RIGHT));
		add(getLabel(isp.getGateway(), 1, JLabel.LEFT));
		add(getLabel("DNS :", 0, JLabel.RIGHT));
		add(getLabel("8.8.8.8", 1, JLabel.LEFT));
		add(getLabel("Weight :", 0, JLabel.RIGHT));
		add(getLabel(isp.getWeight()+"", 1, JLabel.LEFT));
	}

}

package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import model.Isp;

import utility.ConfigGenerator;
import utility.LoadBalancer;
import utility.PaddedJComponent;

public class HomeScreen extends JPanel implements ActionListener, MouseListener {
	
	private static final long serialVersionUID = 1L;
	private static ImageIcon startIcon;
	private static ImageIcon stopIcon;
	private static ImageIcon refreshIcon;
	
	private ConfigGenerator configGenerator;
	
	private boolean isStarted;
	
	private JButton startButton;
	private JButton refreshButton;
	private int selectedPanel=0, width;
	private IspPanel[] ispArr;
	private JPanel panC, panW, panWN;
	
	private LoadBalancer loadBalancer;
	
	public HomeScreen(int width, int height) {
		
		configGenerator = new ConfigGenerator();
		configGenerator.createConfigList();
		
		setLayout(new BorderLayout());
		
		final JPanel panN = new JPanel();
		panN.setLayout(new BorderLayout());
		
		startIcon = new ImageIcon(getClass().getResource("../resources/images/start_button.png"));
		startIcon.setImage(startIcon.getImage().getScaledInstance(50+height/10, 50+height/10, Image.SCALE_SMOOTH));
		
		stopIcon = new ImageIcon(getClass().getResource("../resources/images/stop_button.png"));
		stopIcon.setImage(stopIcon.getImage().getScaledInstance(50+height/10, 50+height/10, Image.SCALE_SMOOTH));
		
		refreshIcon = new ImageIcon(getClass().getResource("../resources/images/refresh.png"));
		refreshIcon.setImage(refreshIcon.getImage().getScaledInstance(10+height/20, 10+height/20, Image.SCALE_SMOOTH));
		
		
		isStarted = false;
		startButton = new JButton(startIcon);
		startButton.setBorder(BorderFactory.createEmptyBorder());
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);
		startButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		startButton.setToolTipText("Start");
		startButton.addActionListener(this);
		
		panN.add(startButton, BorderLayout.WEST);
		
		JPanel panNC = new JPanel();
		panNC.setLayout(new BorderLayout());
		panNC.setOpaque(true);
		panNC.setBackground(Color.WHITE);
		
		refreshButton = new JButton(refreshIcon);
		refreshButton.setBorder(BorderFactory.createEmptyBorder());
		refreshButton.setContentAreaFilled(false);
		refreshButton.setFocusPainted(false);
		refreshButton.setToolTipText("Refresh");
		refreshButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		refreshButton.addActionListener(this);
		
		
		panN.add(new PaddedJComponent(panNC, 0, 0, 0, 20), BorderLayout.CENTER);
		
		add(panN, BorderLayout.NORTH);
		
		panW = new JPanel();
		panW.setLayout(new BorderLayout());
		panW.setOpaque(true);
		panW.setBackground(Color.WHITE);
		panWN = new JPanel();
		panWN.setLayout(new BoxLayout(panWN, BoxLayout.Y_AXIS));
		panWN.setOpaque(false);
		panWN.add(Box.createHorizontalStrut(50+width/5));
		this.width=width;
		for(JPanel ispPanel:getIspPanels())
			panWN.add(ispPanel);
		
		panW.add(panWN, BorderLayout.NORTH);
		add(new PaddedJComponent(panW, 10, 0, 0, 0), BorderLayout.WEST);
		
		panW.add(refreshButton, BorderLayout.SOUTH);
		
		panC = new JPanel();
		panC.setOpaque(true);
		panC.setBackground(Color.WHITE);
		
		Iterator<Isp> it = configGenerator.getArraylist().iterator();
		if(it.hasNext())
			panC.add(new IspDetailPanel(it.next()));
		add(new PaddedJComponent(panC, 10, 0, 0, 10), BorderLayout.CENTER);
	}
	
	private IspPanel[] getIspPanels() {
		
		ispArr = new IspPanel[configGenerator.getArraylist().size()];
		int i=0;
		for(Isp isp: configGenerator.getArraylist())
		{
			ispArr[i] = new IspPanel(isp.getIface(), 40);
			ispArr[i].setOpaque(true);
			ispArr[i].setBackground(Color.WHITE);
			ispArr[i++].addMouseListener(this);
		}
		
		if(ispArr!=null && ispArr.length>=1)
		{
			ispArr[selectedPanel].setBackground(Color.GREEN);
		}
			
		return ispArr;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		JButton button = (JButton)event.getSource();
		if(button == startButton) {
			if(isStarted) {
				isStarted = false;
				startButton.setToolTipText("Start");
				loadBalancer.balance(configGenerator.getIspAtIndex(selectedPanel));
				for(int i=0; i<ispArr.length; i++)
					if(i!=selectedPanel)
						ispArr[i].disableInterface();
				startButton.setIcon(startIcon);
			}
			else {
				isStarted = true;
				startButton.setToolTipText("Stop");
				for(int i=0; i<ispArr.length; i++)
						ispArr[i].enableInterface();
				loadBalancer = new LoadBalancer(configGenerator.getArraylist());
				startButton.setIcon(stopIcon);
			}
		}
		else if(button == refreshButton)
		{
			configGenerator.createConfigList();
			panWN.setVisible(false);
			panWN.removeAll();
			panWN.setLayout(new BoxLayout(panWN, BoxLayout.Y_AXIS));
			panWN.setOpaque(false);
			panWN.add(Box.createHorizontalStrut(50+width/5));
			for(JPanel ispPanel:getIspPanels())
				panWN.add(ispPanel);
			panWN.setVisible(true);
		}
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		
		for(int i=0; i<ispArr.length; i++)
		{
			if((event.getSource()==ispArr[i] || event.getSource()==ispArr[i].getImgLabel()) && i!=selectedPanel)
			{
				ispArr[selectedPanel].setBackground(Color.WHITE);
				ispArr[i].setBackground(Color.GREEN);
				panC.setVisible(false);
				panC.removeAll();
				panC.add(new IspDetailPanel(configGenerator.getIspAtIndex(i)));
				panC.setVisible(true);
				selectedPanel=i;
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
	
}

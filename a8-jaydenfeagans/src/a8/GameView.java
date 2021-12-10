package a8;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GameView extends JPanel implements ActionListener, SpotListener, ChangeListener {
	
	private JSpotBoard board;
	private JPanel buttonPanel;
	private List <GameListener> listenerList;
	private JLabel label;
	private JPanel varPanel;
	private JSlider scrollWid;
	private JSlider scrollHei;
	private JSlider scrollIt;
	
	
	public GameView() {
		setLayout( new BorderLayout());
		
		
		board = new JSpotBoard(50, 50);
		board.addSpotListener(this);
		add(board, BorderLayout.CENTER);
		
		label = new JLabel();
		add(board, BorderLayout.NORTH);
		
		varPanel = new JPanel();
		varPanel.setLayout(new BorderLayout());
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3,5));
		
		JPanel sub = new JPanel();
		sub.setLayout(new BorderLayout());
		
		buttonPanel.add(new JButton("Reset"));
		buttonPanel.add(new JButton("Randomize"));
		buttonPanel.add(new JButton("Next Move"));
		buttonPanel.add(new JButton("Play"));
		buttonPanel.add(new JButton("Pause"));
		
		buttonPanel.add(new JButton("Survival Upper Inc."));
		buttonPanel.add(new JButton("Survival Lower Inc."));
		buttonPanel.add(new JButton("Survival Upper Dec."));
		buttonPanel.add(new JButton("Survival Lower Dec."));
		
		buttonPanel.add(new JButton("Birth Upper Dec."));
		buttonPanel.add(new JButton("Birth Lower Dec."));
		buttonPanel.add(new JButton("Birth Upper Inc."));
		buttonPanel.add(new JButton("Birth Lower Inc."));
		
		buttonPanel.add(new JButton("Torus Toggle"));
		
		varPanel.add(buttonPanel, BorderLayout.CENTER);
		
		JPanel scrollP = new JPanel();
		JPanel scrollC = new JPanel();
		JPanel scrollW = new JPanel();
		JPanel scrollE = new JPanel();
		
		JLabel scrollWLabel = new JLabel();
		scrollWLabel.setText("Width");
		
		scrollW.add(scrollWLabel, BorderLayout.NORTH);
		
		scrollWid = new JSlider(JSlider.HORIZONTAL, 10,500,50);
		scrollWid.setMajorTickSpacing(100);
		scrollWid.setMinorTickSpacing(10);
		scrollWid.setPaintTicks(true);
		scrollWid.setPaintLabels(true);
		scrollWid.setSnapToTicks(true);
		scrollWid.addChangeListener(this);
		
		scrollW.add(scrollWid, BorderLayout.CENTER);
		
		JLabel scrollHLabel = new JLabel();
		scrollHLabel.setText("Height");
		
		scrollC.add(scrollHLabel, BorderLayout.NORTH);
		
		scrollHei = new JSlider(JSlider.HORIZONTAL, 10, 500, 50);
		scrollHei.setMajorTickSpacing(100);
		scrollHei.setMinorTickSpacing(10);
		scrollHei.setPaintTicks(true);
		scrollHei.setPaintLabels(true);
		scrollHei.setSnapToTicks(true);
		scrollHei.addChangeListener(this);
		
		scrollC.add(scrollHei, BorderLayout.CENTER);
		
		JLabel scrollItLabel = new JLabel();
		scrollItLabel.setText("Iteration Time");
		
		
		scrollIt = new JSlider(JSlider.HORIZONTAL,10, 1000, 10);
		scrollIt.setMajorTickSpacing(500);
		scrollIt.setMinorTickSpacing(20);
		scrollIt.setPaintTicks(true);
		scrollIt.setPaintLabels(true);
		scrollIt.setSnapToTicks(true);
		scrollIt.addChangeListener(this);
		
		scrollE.add(scrollItLabel, BorderLayout.NORTH);
		scrollE.add(scrollIt, BorderLayout.CENTER);
		
		scrollP.add(scrollW, BorderLayout.WEST);
		scrollP.add(scrollC, BorderLayout.CENTER);
		scrollP.add(scrollE, BorderLayout.EAST);
		
		varPanel.add(scrollP, BorderLayout.SOUTH);
		
		add(varPanel,BorderLayout.SOUTH);
		
		
		
		
		
		for(Component a: buttonPanel.getComponents()) {
			JButton b = (JButton) a;
			b.addActionListener(this);
		}
		
		listenerList = new ArrayList<GameListener>();
		
		this.setFocusable(true);
		this.grabFocus();
		
	}
	
	public void appendToTape(String a) {
		if(a.equals("")) {
			return;
		}
		label.setText(a);
	}

	@Override
	public void spotClicked(Spot spot) {
		// TODO Auto-generated method stub
		if(spot.isEmpty()) {
			spot.setSpotColor(Color.BLACK);
			spot.toggleSpot();
		}
		else {
			spot.clearSpot();
		}
	}

	@Override
	public void spotEntered(Spot spot) {
		// TODO Auto-generated method stub
		spot.highlightSpot();
	}

	@Override
	public void spotExited(Spot spot) {
		// TODO Auto-generated method stub
		spot.unhighlightSpot();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton defaultB = (JButton) e.getSource();
		String defaultS = defaultB.getText();
		
		dispatchEvent(defaultS);
	}
	
	public void resetGame() {
		for(int n=0; n<board.getSpotWidth();n++) {
			for(int c=0; c<board.getSpotHeight();c++) {
				board.getSpotAt(n, c).clearSpot();
			}
		}
	}
	
	public void randomize() {
		resetGame();
		for(int n=0; n<board.getSpotWidth();n++) {
			for(int c=0; c<board.getSpotHeight();c++) {
				if(Math.random()>.5) {
				board.getSpotAt(n, c).toggleSpot();
				}
			}
		}
		appendToTape("Randomize");
	}
	
	public void fireEvent(GameViewEvent a) {
		for(GameListener n: listenerList) {
			n.handleGameViewEvent(a);
		}
	}

	private void dispatchEvent(String defaultS) {
		fireEvent(new SimpEvent(defaultS));
	}
	
	public void addGameListener(GameListener a) {
		listenerList.add(a);
	}
	
	public void remGameListener(GameListener a) {
		listenerList.remove(a);
	}
	
	

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider slide = (JSlider) e.getSource();
		if(slide.getValueIsAdjusting()) {
			if(slide == scrollWid)
				fireEvent(new BoardSize('w',slide.getValue()));
			else if(slide == scrollHei)
				fireEvent(new BoardSize('h',slide.getValue()));
			else if(slide == scrollIt)
				fireEvent(new TimeE(slide.getValue()));
		}
	}
	
	public JSpotBoard getBoard() {
		return board;
	}
	public void setBoard(int width, int height) {
		removeAll();
		repaint();
		revalidate();
		
		add(varPanel,BorderLayout.SOUTH);
		board = new JSpotBoard(width, height);
		board.addSpotListener(this);
		add(board,BorderLayout.CENTER);
		
		repaint();
		revalidate();
		
	}

}

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameWidget extends JPanel implements ActionListener, SpotListener{
	
	private JSpotBoard board;
	private JLabel text;
	private boolean isFinished;

	public GameWidget() {
		
		board = new JSpotBoard(10, 10);
		
		
		text = new JLabel();
		text.setText("Welcome to Conway's Game of Life");
		
		setLayout(new BorderLayout());
		add(board, BorderLayout.CENTER);
		
		JPanel resetText = new JPanel();
		resetText.setLayout(new BorderLayout());
		
		JButton resetButton = new JButton("reset");
		resetButton.addActionListener(this);
		resetText.add(resetButton, BorderLayout.EAST);
		resetText.add(text, BorderLayout.CENTER);
		add(resetText, BorderLayout.SOUTH);
		
		resetGame();
	}
	
	
	private void resetGame() {
		for(Spot a: board){
			a.clearSpot();
			a.unhighlightSpot();
		}
		
	}
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void spotClicked(Spot spot) {
		// TODO Auto-generated method stub
		
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
	
	

}

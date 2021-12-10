package a8;

import javax.swing.JFrame;

public class GameOfLife {
	
	public static void main(String[] args) {
		GameModel mod = new GameModel();
		GameView view = new GameView();
		GameController control = new GameController(mod, view);
		
		JFrame mainF = new JFrame();
		mainF.setTitle("Jon Conway's Game of Life!");
		mainF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainF.setContentPane(view);
		
		mainF.pack();
		mainF.setVisible(true);
	}
}

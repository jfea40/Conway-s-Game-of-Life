package a8;

public class GameController implements GameObserver, GameListener {

	

	GameModel model;

	GameView view;

	

	private boolean play;

	private int wait_time = 1000;

	

	public GameController(GameModel m, GameView v) {

		model = m;

		view = v;

		

		view.addGameListener(this);

		

		model.addObserver(this);

	}



	@Override

	public void handleGameViewEvent(GameViewEvent e) {

		if (e.isSimpEvent()) {

			SimpEvent event = (SimpEvent) e;

			

			if (event.getString().equals("Reset")){

				view.resetGame();

				model.reset();

			} else if (event.getString().equals("Randomize")) {

				view.randomize();

			} else if (event.getString().equals("Next Move")) {

				model.lifeCycle(view.getBoard());

			} else if (event.getString().equals("Survival Upper Inc.")) {


				model.increaseUpperSurvive();

			} else if (event.getString().equals("Survival Lower Inc.")) {

				model.increaseLowerSurvive();

			} else if (event.getString().equals("Birth Upper Inc.")) {

				model.increaseUpperBirth();

			} else if (event.getString().equals("Birth Lower Inc.")) {

				model.increaseLowerBirth();

			} else if (event.getString().equals("Survival Upper Dec.")) {

				model.decreaseUpperSurvive();

			} else if (event.getString().equals("Survival Lower Dec.")) {

				model.decreaseLowerSurvive();

			} else if (event.getString().equals("Birth Upper Dec.")) {

				model.decreaseUpperBirth();

			} else if (event.getString().equals("Birth Lower Dec.")) {

				model.decreaseLowerBirth();

			} else if (event.getString().equals("Play")) {

				play = true;

				view.appendToTape("Playing");

				life();

			} else if (event.getString().equals("Pause")) {

				play = false;

				view.appendToTape("Paused");

			} else if (event.getString().equals("Torus Toggle")) {

				model.setTorus();

			} else if (event.getString().equals("Decrease Iteration time")) {

				if (wait_time > 10) 

					wait_time -= 10;

				view.appendToTape("Time Between Iterations is now " + wait_time + "milliseconds");

			}

		}

		else if (e.isBoardSize()) {

			BoardSize event = (BoardSize) e;

			

			if (event.getSort() == 'w') {

				view.appendToTape("The new Width of the Board is " + event.getValue());

				view.setBoard(event.getValue(), view.getBoard().getSpotHeight());



			} else if (event.getSort() == 'h') {

				view.appendToTape("The new Height of the Board is " + event.getValue());

				view.setBoard(view.getBoard().getSpotWidth(),event.getValue());

			}

		}

		else if (e.isTime()) {

			TimeE event = (TimeE) e;

			wait_time = event.getValue();

			view.appendToTape("The new time between iterations is " + wait_time + " milliseconds");

		}

		

	}



	@Override

	public void update(GameModel con, String m) {

		view.appendToTape(m);

	}

	

	public void life() {

		if (play) {

			model.lifeCycle(view.getBoard());

			new Thread(new Runnable() {

				public void run() {

					try {

						Thread.sleep(wait_time);

					} catch (InterruptedException e) {

					}

					life();

				}

			}).start();

		}

	}

}


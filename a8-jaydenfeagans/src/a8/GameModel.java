package a8;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameModel {

	private boolean isTorus;
	private boolean play;
	private int surviveLowThresh;
	private int surviveupThresh;
	private int birthLowThresh;
	private int birthUpThresh;
	private Date date;
	private long time;
	private String text;

	private List<GameObserver> observers;

	public GameModel() {
		isTorus = false;
		play = false;
		surviveLowThresh = 2;
		surviveupThresh = 3;
		birthLowThresh = 3;
		birthUpThresh = 3;
		date = new Date();
		text = "Welcome to Conway's Game of Life!";
		observers = new ArrayList<GameObserver>();
	}

	public void reset() {
		isTorus = false;
		play = false;
		surviveLowThresh = 2;
		surviveupThresh = 3;
		birthLowThresh = 3;
		birthUpThresh = 3;
		date = new Date();
		text = "reset";
		notifyObservers();
	}

	public void addObserver(GameObserver o) {
		observers.add(o);
		notifyObservers();
	}

	public void removeObserver(GameObserver o) {
		observers.remove(o);
	}

	private void notifyObservers() {
		for (GameObserver o : observers) {
			o.update(this, text);
		}
		text = "";
	}

	private boolean isTorus() {
		return isTorus;
	}

	public boolean isPlay() {
		return play;
	}

	private int getsurviveLowThresh() {
		return surviveLowThresh;
	}

	private int getsurviveupThresh() {
		return surviveupThresh;
	}

	private int getbirthLowThresh() {
		return birthLowThresh;
	}

	private int getbirthUpThresh() {
		return birthUpThresh;
	}

	public long getTime() {
		return time;
	}

	public void setTorus() {
		isTorus = !isTorus;

		if (isTorus) {
			text = "Torus mode is now on";
		} else {
			text = "Torus mode is now off";
		}

		notifyObservers();
	}

	public void setPlay(boolean play) {
		this.play = play;

		if (play) {
			text = "Playing";
		} else {
			text = "Paused";
		}

		notifyObservers();
	}

	private void setsurviveLowThresh(int surviveLowThresh) {
		this.surviveLowThresh = surviveLowThresh;

		text = "The Lower Survive limit is now " + surviveLowThresh;

		notifyObservers();
	}

	private void setsurviveupThresh(int surviveupThresh) {
		this.surviveupThresh = surviveupThresh;

		text = "The Upper Survive limit is now " + surviveupThresh;

		notifyObservers();
	}

	private void setbirthLowThresh(int birthLowThresh) {
		this.birthLowThresh = birthLowThresh;

		text = "The Lower Birth limit is now " + birthLowThresh;

		notifyObservers();
	}

	private void setbirthUpThresh(int birthUpThresh) {
		this.birthUpThresh = birthUpThresh;

		text = "The Upper Birth limit is now " + birthUpThresh;

		notifyObservers();
	}

	public void setTime() {
		;
		time = date.getTime();
	}

	public void lifeCycle(JSpotBoard board) {
		int[][] current = new int[board.getSpotWidth()][board.getSpotHeight()];
		for (int i = 0; i < board.getSpotWidth(); i++) {
			for (int j = 0; j < board.getSpotHeight(); j++) {
				current[i][j] = board.getSpotAt(i, j).isEmpty() ? 0 : 1;
			}
		}

		if (!isTorus()) {
			for (int i = 0; i < board.getSpotWidth(); i++) {
				for (int j = 0; j < board.getSpotHeight(); j++) {
					int around = 0; // track number of lives squares surrounding it

					if (i > 0) {
						around += current[i - 1][j];
					}

					if (i > 0 && j > 0) {
						around += current[i - 1][j - 1];
					}

					if (i > 0 && j < board.getSpotHeight() - 1) {
						around += current[i - 1][j + 1];
					}

					if (j > 0) {
						around += current[i][j - 1];
					}

					if (j > 0 && i < board.getSpotWidth() - 1) {
						around += current[i + 1][j - 1];
					}

					if (i < board.getSpotWidth() - 1) {
						around += current[i + 1][j];
					}

					if (i < board.getSpotWidth() - 1 && j < board.getSpotHeight() - 1) {
						around += current[i + 1][j + 1];
					}

					if (j < board.getSpotHeight() - 1) {
						around += current[i][j + 1];
					}

					if (current[i][j] == 0) {
						if (around >= getbirthLowThresh() && around <= getbirthUpThresh()) {
							board.getSpotAt(i, j).toggleSpot();
						}
					} else {
						if (around < getsurviveLowThresh() || around > getsurviveupThresh()) {
							board.getSpotAt(i, j).toggleSpot();
						}
					}
				}
			}
		} else {
			for (int i = 0; i < board.getSpotWidth(); i++) {
				for (int j = 0; j < board.getSpotHeight(); j++) {
					System.out.println("s");
					int around = 0; // track number of lives squares surrounding it
					int tempX = 0;
					int tempY = 0;

					if (i > 0) {
						around += current[i - 1][j];
					} else {
						around += current[board.getSpotWidth() - 1][j];
					}

					if (i > 0 && j > 0) {
						around += current[i - 1][j - 1];
					} else {
						if (i > 0)
							tempX = i - 1;
						else
							tempX = board.getSpotWidth() - 1;
						if (j > 0)
							tempY = j - 1;
						else
							tempY = board.getSpotHeight() - 1;
						around += current[tempX][tempY];
					}

					if (i > 0 && j < board.getSpotHeight() - 1) {
						around += current[i - 1][j + 1];
					} else {
						if (i > 0)
							tempX = i - 1;
						else
							tempX = board.getSpotWidth() - 1;
						if (j < board.getSpotHeight() - 1)
							tempY = j + 1;
						else
							tempY = 0;
						around += current[tempX][tempY];
					}

					if (j > 0) {
						around += current[i][j - 1];
					} else {
						around += current[i][board.getSpotHeight() - 1];
					}

					if (j > 0 && i < board.getSpotWidth() - 1) {
						around += current[i + 1][j - 1];
					} else {
						if (j > 0)
							tempY = j - 1;
						else
							tempY = board.getSpotHeight() - 1;
						if (i < board.getSpotWidth() - 1)
							tempX = i + 1;
						else
							tempX = 0;
						around += current[tempX][tempY];
					}

					if (i < board.getSpotWidth() - 1) {
						around += current[i + 1][j];
					} else {
						around += current[0][j];
					}

					if (i < board.getSpotWidth() - 1 && j < board.getSpotHeight() - 1) {
						around += current[i + 1][j + 1];
					} else {
						if (i < board.getSpotWidth() - 1)
							tempX = i + 1;
						else
							tempX = 0;
						if (j < board.getSpotHeight() - 1)
							tempY = j + 1;
						else
							tempY = 0;
						around += current[tempX][tempY];
					}

					if (j < board.getSpotHeight() - 1) {
						around += current[i][j + 1];
					} else {
						around += current[i][0];
					}

					if (current[i][j] == 0) {
						if (around >= getbirthLowThresh() && around <= getbirthUpThresh()) {
							board.getSpotAt(i, j).toggleSpot();
						}
					} else {
						if (around < getsurviveLowThresh() || around > getsurviveupThresh()) {
							board.getSpotAt(i, j).toggleSpot();
						}
					}
				}
			}
		}
	}

	public void increaseUpperSurvive() {
		if (surviveupThresh == 8) {
			text = "The Upper Limit of Survive can not be increased";
			notifyObservers();
		} else {
			setsurviveupThresh(surviveupThresh + 1);
		}
	}

	public void decreaseUpperSurvive() {
		if (surviveupThresh == surviveLowThresh) {
			text = "The Upper Limit of Survive can not be decreased";
			notifyObservers();
		} else {
			setsurviveupThresh(surviveupThresh - 1);
		}
	}

	public void increaseUpperBirth() {
		if (birthUpThresh == 8) {
			text = "The Upper Limit of Birth can not be increased";
			notifyObservers();
		} else {
			setbirthUpThresh(birthUpThresh + 1);
		}
	}

	public void decreaseUpperBirth() {
		if (birthUpThresh == birthLowThresh) {
			text = "The Upper Limit of Birth can not be decreased";
			notifyObservers();
		} else {
			setbirthUpThresh(birthUpThresh - 1);
		}
	}

	public void increaseLowerSurvive() {
		if (surviveLowThresh == surviveupThresh) {
			text = "The Lower Limit of Survive can not be increased";
			notifyObservers();
		} else {
			setsurviveLowThresh(surviveLowThresh + 1);
		}
	}

	public void decreaseLowerSurvive() {
		if (surviveLowThresh == 1) {
			text = "The Lower Limit of Survive can not be decreased";
			notifyObservers();
		} else {
			setsurviveLowThresh(surviveLowThresh - 1);
		}
	}

	public void increaseLowerBirth() {
		if (birthLowThresh == birthUpThresh) {
			text = "The Lower Limit of Birth can not be increased";
			notifyObservers();
		} else {
			setbirthLowThresh(birthLowThresh + 1);
		}
	}

	public void decreaseLowerBirth() {
		if (birthLowThresh == 1) {
			text = "The Lower Limit of Birth can not be decreased";
			notifyObservers();
		} else {
			setbirthLowThresh(birthLowThresh - 1);
		}
	}
}
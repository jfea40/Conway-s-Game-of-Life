package a8;

abstract public class GameViewEvent {

	public boolean isSimpEvent() {
		return false;
	}
	public boolean isBoardSize() {
		return false;
	}
	public boolean isTime() {
		return false;
	}

}

class SimpEvent extends GameViewEvent{
	private String demand;
	
	public SimpEvent(String a) {
		demand = a;
	}
	public String getString() {
		return demand;
	}
	public boolean isSimpEvent() {
		return true;
	}
}

class BoardSize extends GameViewEvent{
	private int valueUp;
	private char sort;
	
	public BoardSize(char a, int value) {
		sort = a;
		valueUp = value;
	}
	public char getSort() {
		return sort;
	}
	public int getValue() {
		return valueUp;
	}
	public boolean isBoardSize() {
		return true;
	}
}
class TimeE extends GameViewEvent{
	private int valueUp;
	
	public TimeE(int number) {
		valueUp = number;
	}
	public int getValue() {
		return valueUp;
	}
	public boolean isTime() {
		return true;
	}
}

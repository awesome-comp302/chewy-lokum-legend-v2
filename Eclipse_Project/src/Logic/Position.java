package Logic;

public class Position {

	private int x,y;
	public Position(int x, int y) {
		setX(x);
		setY(y);
	}
	public int getX() {
		return x;
	}
	public void setX(int x) throws IllegalArgumentException{
		if(x<0) throw new IllegalArgumentException("Invalid position -x: position should be nonnegative.");
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) throws IllegalArgumentException {
		if(y<0) throw new IllegalArgumentException("Invalid position -y: position should be nonnegative.");	
		this.y = y;
	}
	
	public boolean isSamePlace(Position p) {
		if (p == null ) {
			return false;
		}
		return p.x == x &&
				p.y == y;
	}
	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}
	
	

}

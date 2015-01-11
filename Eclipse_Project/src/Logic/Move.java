package Logic;

public class Move {
	
	private boolean special;
	
	private String type1;
	private String specialType1;
	private Position position1;
	
	private String type2;
	private String specialType2;
	private Position position2;
	
	

	public Move(int x1, int y1, int x2, int y2, GamePlay gp, boolean special) {
		this.special = special;
		ChewyObject o1 = gp.getLevel().getBoard().cellAt(x1, y1).getCurrentObject();
		ChewyObject o2 = gp.getLevel().getBoard().cellAt(x2, y2).getCurrentObject();
		type1 = o1.getType();
		type2 = o2.getType();
		if (o1 instanceof Lokum) {
			specialType1 = ((Lokum)o1).getSpecialType();
		}
		if (o2 instanceof Lokum) {
			specialType2 = ((Lokum)o2).getSpecialType();
		}
		position1 = new Position(x1, y1);
		position2 = new Position(x2, y2);
		
	}
	
	


	public Position getPosition1() {
		return position1;
	}

	public String getType1() {
		return type1;
	}


	public String getSpecialType1() {
		return specialType1;
	}

	public String getType2() {
		return type2;
	}

	public String getSpecialType2() {
		return specialType2;
	}

	public Position getPosition2() {
		return position2;
	}


	public boolean isSpecial() {
		return special;
	}


	
}

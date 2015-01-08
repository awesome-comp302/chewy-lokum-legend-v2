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
		
		/*Lokum l1 = (Lokum) gp.getLevel().getBoard().cellAt(x1, y1)
				.getCurrentObject();
		Lokum l2 = (Lokum) gp.getLevel().getBoard().cellAt(x2, y2)
				.getCurrentObject();

		String type1 = l1.getSpecialType();
		String type2 = l2.getSpecialType();
		
		if (type1.equalsIgnoreCase(type2)) {// Combos and Regular
			if (type1.equals("Regular")) {
				type = NORMAL;
			} else if (type1.equals("Wrapped")) {
				type = COMBO_WRAPPED;
			} else if (type2.equalsIgnoreCase("Color Bomb")) {
				type = COMBO_BOMB;
			}
		} else if (type1.endsWith("Striped") && type2.endsWith("Striped")) { //Striped-Striped
			type = COMBO_STRIPED;
		} else if (type1.equals("Color Bomb") && type2.endsWith("Striped")
				|| type1.endsWith("Striped") && type2.equals("Color Bomb")) {//Color Bomb-Striped
			type = BOMB_STRIPED;
		} else if (type1.equals("Wrapped") && type2.endsWith("Striped")
				|| type1.endsWith("Striped") && type2.equals("Wrapped")) {//Striped-Wrapped
			type = WRAPPED_STRIPED;
		} else if (type1.equals("Wrapped") && type2.equals("Color Bomb")
				|| type1.equals("Color Bomb") && type2.equals("Wrapped")) {//Color Bomb - Wrapped
			type = BOMB_WRAPPED;
		}*/
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


	/*
	private int type;
	public static final int NORMAL = 0;
	public static final int ONE_BOMB = 1;
	public static final int ONE_WRAPPED = 2;
	public static final int ONE_HSTRIPED = 3;
	public static final int ONE_VSTRIPED = 10;
	public static final int COMBO_BOMB = 4;
	public static final int COMBO_WRAPPED = 5;
	public static final int COMBO_VSTRIPED = 6;
	public static final int COMBO_HSTRIPED = 11;
	
	public static final int BOMB_WRAPPED = 7;
	public static final int BOMB_HSTRIPED = 8;
	public static final int BOMB_VSTRIPED = 12;
	public static final int WRAPPED_HSTRIPED = 9;
	public static final int WRAPPED_VSTRIPED = 13;*/
	
}

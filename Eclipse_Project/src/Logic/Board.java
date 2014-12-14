package Logic;
//oldu
public class Board {
	private int width;
	private int height;
	private Cell[][] cells;
	
	/**
	 * 
	 * @param width: Board width
	 * @param height: Board height
	 * @throws IllegalArgumentException :for too large and negative coordinate values
	 */
	public Board(int width, int height) throws IllegalArgumentException{
		
		if (width < 1 || height < 1) {
			throw new IllegalArgumentException("Board width and height should be positive");
		}
		this.width = width;
		this.height = height;
		cells = new Cell[height][width];
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				cells[i][j] = new Cell(new Nothing());
			}
		}
	}
	
	public boolean repOk() {
		//inspects whether the constructor did its job
		if (width < 1 || height < 1) {
			return false;
		}
		
		if (cells == null) {
			return false;
		}
		return true;
	}
	
	
	public boolean inBoard(int x, int y) 
	{
		return x>=0 && y>= 0 &&
				x< width && y < height;
	}
	
	/**
	
	 * @param x: x coordinate
	 * @param y: y coordinate
	 * @throws IllegalArgumentException: for too big or negative indexes
	 * @postcondition: Wanted cell should be returned
	 */
	public Cell cellAt(int x, int y) throws IllegalArgumentException{
		if (inBoard(x, y)) {
			return cells[y][x].clone();
		} else {
			throw new IllegalArgumentException("Oha! x:" + x + " y:" + y);
		}
	}
	
	/**
	
	 * @param x: x coordinate
	 * @param y: y coordinate
	 * @throws IllegalArgumentException: for too big or negative indexes
	 * @postcondition: Wanted cell should be filled with the given object
	 */
	public void fillCellAt(int x, int y, ChewyObject co) throws IllegalArgumentException{
		if (inBoard(x, y)) {
			cells[y][x].setCurrentObject(co);
		} else {
			throw new IllegalArgumentException("Oha! x:" + x + " y:" + y);
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	
	@Override
	public String toString() {
		String result = "\n";
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				result += String.format("%-20s", cellAt(x, y));
				
			}
			result += "\n";
			
		}
		return result;
	}
	
	public String status() {
		return  "\nBOARD STATUS \n"+
				"Board width: " +width+ "\n"+
				"Board height: " +height+ "\n"+
				"Cells array's width: " + cells.length+ "\n"+
				"Cells array's height: "  +  cells[0].length;

	}
}

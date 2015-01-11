package Logic;

public class Board {
	private int width;
	private int height;
	private Cell[][] cells;
	
	/**
	 * The constructor of the Board class.
	 * @param width : Board width
	 * @param height : Board height
	 * @requires There are classes Cell and Nothing exist and can be accessed by the Board class. Cell has a constructor that takes a single ChewyObject argument.
	 * 
	 *  
	 * @ensures Cells matrix is initialized. Each cell in the cells is filled by a Nothing object. Width and height fields are set. IllegalArgumentException is thrown with message "Board width and height should be positive" if width<1 or height<1. 
	 * 
	 * @throws IllegalArgumentException, if width < 1 or height <1
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
	
	
	/**
	 * A testing method returns false if width or height is illegally set to a value smaller than 1
	 * ,cells field is null, any cell object is null or any cell object was filled by null.
	 * <br>Returns true, otherwise.
	 */
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
	
	
	/**
	 *@requires width and height is initialized successfully.
	 *@ensures Returns true if x is in range [0...width] and y is in range [0...height], false o/w.
	 */
	public boolean inBoard(int x, int y) 
	{
		return x>=0 && y>= 0 &&
				x< width && y < height;
	}
	
	/**
	
	 * @requires
	 * 
	 * inBoard method is implemented correctly with two int arguments represents x and y, respectively.</li>
	 * Fields cells matrix and each element of this matrix are non-null.
	 * Cell class has an instance method with no arguments named clone for copying itself.
	 * 
	 * 
	 * @ensures
	 *A clone of the cell at (x, y) is returned if coordinates are in the board, 
	 *An Illegal argument exception should be thrown for illegal x and y indexes.  
	 *@throws IllegalArgumentException: for too big or negative indexes
	 */
	public Cell cellAt(int x, int y) throws IllegalArgumentException{
		if (inBoard(x, y)) {
			return cells[y][x].clone();
		} else {
			throw new IllegalArgumentException("Oha! x:" + x + " y:" + y);
		}
	}
	
	/**
	 * Fills the cell at (x, y) with ChewyObject co.
		@requires repOk.
		@modifies cells
		@ensures Cell of the board at (x,y) is filled if x and y are in the board, an IllegalArgumentException is thrown o/w.
		
	 */
	public void fillCellAt(int x, int y, ChewyObject co) throws IllegalArgumentException{
		if (inBoard(x, y)) {
			cells[y][x].setCurrentObject(co);
		} else {
			throw new IllegalArgumentException("Oha! x:" + x + " y:" + y);
		}
	}

	

	/**
	 * 
	 * @requires width is initializes
	 * @ensures width value is returned,
	 */
	public int getWidth() {
		return width;
	}

	
	/**
	 * 
	 * @requires height is initializes
	 * @ensures height value is returned,
	 */
	public int getHeight() {
		return height;
	}
	
	
	/**
	 * All cells are returned as a formatted String.
	 */
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
	
	/**
	 * Returns sizes of the Board and cells data for comparison and testing purposes.
	 * 
	 */
	public String status() {
		return  "\nBOARD STATUS \n"+
				"Board width: " +width+ "\n"+
				"Board height: " +height+ "\n"+
				"Cells array's width: " + cells.length+ "\n"+
				"Cells array's height: "  +  cells[0].length;

	}
}

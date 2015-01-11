package Logic;

public class Cell implements Clonable{
	
	private ChewyObject currentObject;
	
	
	
	public Cell(ChewyObject chewyObject) {
		currentObject = chewyObject;
	}
	
	public Cell clone() {
		return new Cell(currentObject);
	}

	public boolean repOk() {
		// Checks if the cell has a chewy object component.
		if(!(currentObject == null)) return false;
		return true;
	}
	
	/**
	 * Exchangable means object can match with any other
	 * @return if object is matchable
	 */
	public boolean isExchangable() {
		return currentObject instanceof Matchable;
	}
	
	
	
	/**
	 * 
	 * @return Current object of cell
	 */
	public ChewyObject getCurrentObject() {
		return currentObject;
	}

	/**
	 * 
	 * @return Previous object of cell.
	 * 
	 * -- This is required for swapping action. --
	 */
	
	
	/**
	 * @modifies currentObject
	 * @param object : object for filling the cell
	 */
	public void setCurrentObject(ChewyObject object) {
		
		this.currentObject = object;
		
	}

	@Override
	public String toString() {
		if (currentObject instanceof Lokum) {
			Lokum cur = (Lokum)currentObject;
			if (cur.isSpecial()) {
				return currentObject.getType() + "->"+  cur.getSpecialType();
			}
		}
		return  currentObject.getType();
	}
	
	public String status() {
		return currentObject.getType();
	}
	
	

}

package Logic;

public class Nothing extends ChewyObject {

	public Nothing() {
		type = "Empty";
	}

	@Override
	public ChewyObject clone() {
		return new Nothing();
	}

}

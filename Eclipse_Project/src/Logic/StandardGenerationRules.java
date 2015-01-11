package Logic;

public class StandardGenerationRules implements GenerationRules {

	private static StandardGenerationRules instance;

	private StandardGenerationRules() {
	}
	

	public static StandardGenerationRules getInstance() {
		if (instance == null) {
			instance = new StandardGenerationRules();
		}
		return instance;
	}

	@Override
	//lastMove stores allready waps adresses
	public ChewyObject getObject(String lastType, MatchingScaleInformer msi) {
		
		if (msi.horizontalMatchTotalScale() == 5 || msi.verticalMatchTotalScale() == 5) {
			return new Lokum(lastType, "Color Bomb");
		}
		
		if (msi.horizontalMatchTotalScale() >= 3 && msi.verticalMatchTotalScale() >= 3) {
			return new Lokum(lastType, "Wrapped");
		}
		
		if (msi.horizontalMatchTotalScale() == 4) {
			return new Lokum(lastType, "Vertical Striped");
		}
		if (msi.verticalMatchTotalScale() == 4) {
			return new Lokum(lastType, "Horizontal Striped");
		}
		
		
		
		return new Nothing();
	}
	

}

package Logic;

public class MatchingScaleInformerFactory {
	
	public static MatchingScaleInformerFactory instance;
	
	private MatchingScaleInformerFactory() {}
	
	public static MatchingScaleInformerFactory getInstance() {
		if(instance == null) instance= new MatchingScaleInformerFactory();
		return instance;
	}
	
	public MatchingScaleInformer getMatchingScaleInformer(Board board, int x1,
			int y1, ChewyObject object) {

		MatchingScaleInformer info = new MatchingScaleInformer();
		if (!(object instanceof Matchable)) {
			return info;
		}

		Matchable m = (Matchable) object;

		int up = countTop(board, x1, y1, m);
		int down = countBottom(board, x1, y1, m);
		int right = countRigth(board, x1, y1, m);
		int left = countLeft(board, x1, y1, m);


		info.setUpScale(up);
		info.setDownScale(down);
		info.setRightScale(right);
		info.setLeftScale(left);

		return info;
	}
	
	private int countBottom(Board board, int x1, int y1, Matchable candidate) {
		int sum = 0;

		int y_count = y1 + 1;
		while (board.inBoard(x1, y_count)) {
			ChewyObject current = board.cellAt(x1, y_count).getCurrentObject();
			if ((current instanceof Matchable)
					&& (candidate.isMatched((Matchable) current))) {
				y_count++;
				sum++;
			} else {
				break;
			}
		}
		return sum;

	}

	private int countTop(Board board, int x1, int y1, Matchable candidate) {
		int sum = 0;
		int y_count = y1 - 1;
		while (board.inBoard(x1, y_count)) {
			ChewyObject current = board.cellAt(x1, y_count).getCurrentObject();
			if ((current instanceof Matchable)
					&& (candidate.isMatched((Matchable) current))) {
				y_count--;
				sum++;
			} else {
				break;
			}
		}
		return sum;
	}
	
	private int countRigth(Board board, int x1, int y1, Matchable candidate) {
		int sum = 0;

		int x_count = x1 + 1;
		while (board.inBoard(x_count, y1)) {
			ChewyObject current = board.cellAt(x_count, y1).getCurrentObject();
			if ((current instanceof Matchable)
					&& (candidate.isMatched((Matchable) current))) {
				x_count++;
				sum++;
			} else {
				break;
			}
		}

		return sum;
	}

	private int countLeft(Board board, int x1, int y1, Matchable candidate) {
		int sum = 0;
		int x_count = x1 - 1;
		while (board.inBoard(x_count, y1)) {
			ChewyObject current = board.cellAt(x_count, y1).getCurrentObject();
			if ((current instanceof Matchable)
					&& (candidate.isMatched((Matchable) current))) {
				x_count--;
				sum++;
			} else {
				break;
			}
		}
		return sum;
	}

	public MatchingScaleInformer getMatchingScaleInformer(Board board, int x,
			int y) {
		return getMatchingScaleInformer(board, x, y, board.cellAt(x, y).getCurrentObject());
	}
	
}

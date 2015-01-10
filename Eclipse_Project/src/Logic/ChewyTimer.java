package Logic;

public class ChewyTimer implements Runnable {

	private GamePlay gp;
	private boolean paused;

	public ChewyTimer(GamePlay gp) {
		this.gp = gp;
		paused = false;
	}

	@Override
	public void run() {


		while (!gp.isGameOver()) {
			while (!paused) {
				waitGame(1000);
				gp.countDownTimer();
				if (gp.anyListeners()) {
					gp.publishGame(UpdateType.timeLabel);
				}
				
			}
		}

	}

	private void waitGame(int milliseconds) {

		int refTime = (int) System.currentTimeMillis();
		int curr = (int) System.currentTimeMillis();

		while ((curr - refTime) < milliseconds) {
			curr = (int) System.currentTimeMillis();
		}

	}
	
	public boolean isPaused() {
		return paused;
	}
	
	public void setPaused(boolean b) {
		this.paused = b;
	}

}

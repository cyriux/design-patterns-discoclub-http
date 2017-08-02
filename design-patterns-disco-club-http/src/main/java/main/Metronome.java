package main;

import java.util.Timer;
import java.util.TimerTask;

@Deprecated
public class Metronome {
	private final Timer timer = new Timer(false);

	public void start() {
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

			}
		}, 0, 1000);
	}
}

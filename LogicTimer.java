package team;

import java.util.Timer;
import java.util.TimerTask;

public class LogicTimer {
	Timer timer = new Timer();
	TimerTask timertask = new TimerTask() {
		public void run() {
			
		}
	}
	timer.schedule(timertask,1000);
}

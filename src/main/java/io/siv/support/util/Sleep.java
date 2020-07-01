package io.siv.support.util;

/**
 * STOP - in most cases you should be using the Waiter class.
 * One reason to use raw sleep methods if when working with iFrames
 * since they take WebDriver extended time to load.
 */
public class Sleep {

	/**
	 * Sleep for 1 second
	 */
	public static void aLittle() {
		forTime(1000L);
	}

	/**
	 * Sleep for specified time in milliseconds
	 * 
	 * @param time long
	 */
	public static void forTime(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sleep before and after the Task for time in milliseconds
	 * 
	 * @param t Task
	 * @param time long
	 */
	public static void aroundForTime(Task t, long time) {
		try {
			Thread.sleep(time);
			t.complete();
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

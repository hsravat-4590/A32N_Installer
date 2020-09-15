package com.ravat.hanzalah.a32n_installer.util.activity;



/**
 * An Activity is a task which can be completed concurrently to one being executed on the primary thread.
 * @author Hanzalah Ravat
 * @since 1.0.0
 * @version 1.0.0
 *
 *@see Runnable
 *
 */
public interface Activity extends Runnable{
	

	/**
	 * Gets the unique tag for the activity
	 * @return Unique tag for the activity
	 */
	public String getTAG();

	
	/**
	 * Activity side pause event. This will pause the activity
	 * A simple implementation would be:
	 * <pre>
	 *     Thread mThread = new Thread(Runnable,TAG);
	 *     public void onPause(){
	 *         mThread.wait();
	 *     }
	 * </pre>
	 */
	public void onPause();
	
	/**
	 * Checks if the activity has been paused
	 * A simple implementation would be to get the {@link Thread.State} and check if it is in the WAITING State
	 * @return true if the activity has been soft-paused
	 */
	public boolean isPaused();

	
	/**
	 * Resumes a paused activity
	 * If thread is waiting, it can be resumed using the <code>Thread.notify()</code> method
	 */
	public void onResume();
	

	
	/**
	 * Terminates the activity
	 */
	public void terminateActivity();
	

}

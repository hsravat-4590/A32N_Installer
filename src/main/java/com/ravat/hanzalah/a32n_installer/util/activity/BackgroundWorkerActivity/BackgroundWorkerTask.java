package com.ravat.hanzalah.a32n_installer.util.activity.BackgroundWorkerActivity;

/**
 * This interface is for classes which want to use the BackgroundWorker with it's looped stages
 * @author Hanzalah Ravat
 * @since 1.0.0
 * @version 1.0.0
 */

public interface BackgroundWorkerTask {
    /**
     * Runs the task
     */
    public void runTask();

    /**
     * Boolean to check if the task is complete
     * @return True if the task is completed (constraints have been met) or otherwise false
     */
    public boolean isComplete();

    /**
     * If the thread is terminated, this action is triggered before terminating the activity
     */
    public void intentTerminated();

    /**
     * Method to start the activity
     */
    public void beginActivity();
}

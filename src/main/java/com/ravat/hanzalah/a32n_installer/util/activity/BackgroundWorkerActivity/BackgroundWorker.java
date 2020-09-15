package com.ravat.hanzalah.a32n_installer.util.activity.BackgroundWorkerActivity;


import com.ravat.hanzalah.a32n_installer.util.activity.Activity;

/**
 * Background Worker Activity to run a continuous background task (looped task).
 * @author Hanzalah Ravat
 * @since 1.0.0
 * @version 2.1.0
 */
public final class BackgroundWorker implements Activity {

    private static final String TAG = "BACKGROUND_WORKER";
    private Thread mThread;
    private BackgroundWorkerTask backgroundWorkerTask;
    private BackgroundWorkerState state;
    private volatile boolean exit = false;

    /**
     *
     * @param TAG the Tag of the
     * @param task a class which implements {@link BackgroundWorkerTask}
     */
    public BackgroundWorker(String TAG, BackgroundWorkerTask task){
        this.backgroundWorkerTask = task;
        mThread = new Thread((Runnable)this,getTAG());
        state = BackgroundWorkerState.READY;
    }

    @Override
    public String getTAG() {
        return TAG;
    }


    @Override
    public void onPause() { state = BackgroundWorkerState.PAUSED; }

    @Override
    public boolean isPaused() { return state == BackgroundWorkerState.PAUSED;}

    @Override
    public void onResume() {
        if(isPaused()){
           state = BackgroundWorkerState.RUNNING;
        }
    }


    @Override
    public void terminateActivity() {
        if(mThread != null) {
            state = BackgroundWorkerState.TERMINATED;
            exit = true;
        } else {
            throw new ThreadNotFoundException();
        }

    }


    public void onStart() {
        mThread.start();
        state = BackgroundWorkerState.RUNNING;
    }

    /**
     * Change the state of the BackgroundWorker
     * @param newState The new state for the BackgroundWorker Instance
     */
    public void switchState(BackgroundWorkerState newState) { state = newState; }

    @Override
    public void run() {
        worker: while((state != BackgroundWorkerState.TERMINATED) || !exit && (backgroundWorkerTask.isComplete())){
             switch(state){
                 case RUNNING:
                     Thread.State mThreadState =  mThread.getState();
                     if(mThreadState == Thread.State.RUNNABLE)
                         backgroundWorkerTask.runTask();
                     break;
                 case TERMINATED:
                     backgroundWorkerTask.intentTerminated();
                     break worker;
                 default:
                     break;
             }
         }
    }
}

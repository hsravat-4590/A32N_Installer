package com.ravat.hanzalah.a32n_installer.downloader;


/**
 * Downloader Interface
 */
public abstract class Downloader implements Runnable {
    public abstract void download();
    boolean isComplete;

    @Override
    public void run(){
        isComplete = false;
    }

    public void onRunComplete(){
        isComplete = true;
    }
}

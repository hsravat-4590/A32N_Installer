package com.ravat.hanzalah.a32n_installer.util.activity.BackgroundWorkerActivity;

class ThreadNotFoundException extends RuntimeException {
    ThreadNotFoundException(){
        super("There is no thread assigned to this Activity");
    }
}

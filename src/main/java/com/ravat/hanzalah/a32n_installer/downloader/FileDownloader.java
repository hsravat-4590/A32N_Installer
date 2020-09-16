package com.ravat.hanzalah.a32n_installer.downloader;


import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.LinkedList;
import java.util.List;

/**
 * Downloads a file from a  source using Background Worker threaded tasks.
 */
public class FileDownloader implements Runnable {


    private static final String TAG = "FILE_DOWNLOADER";
    private final Thread mThread;
    private final String URL,destination;
    private boolean isComplete;
    private final List<TaskCompleteListener> listenerList;

    public FileDownloader(String destination, String URL){
        this.URL = URL;
        this.destination = destination;
        mThread = new Thread(this,TAG);
        listenerList = new LinkedList<>();

    }

    @Override
    public void run() {
        System.out.print("Download Starting...");
        try {
            java.net.URL mURL = new URL(URL);
            ReadableByteChannel readableByteChannel = Channels.newChannel(mURL.openStream());
            System.out.println(destination);
            FileOutputStream fileOutputStream = new FileOutputStream(destination);
            FileChannel fileChannel = fileOutputStream.getChannel();
            fileOutputStream.getChannel()
                    .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            System.out.print("done \n");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("Error: Exception Caught \n");
        }
        isComplete = true;
        System.out.println("Broadcasting to Listeners");
        for (TaskCompleteListener listener: listenerList
             ) {
            listener.taskComplete();

        }

    }

    /**
     * Runs the downloader using another thread
     */
    public void runWithThread(){
        mThread.start();
    }

    public void addListener(TaskCompleteListener listener){
        listenerList.add(listener);
    }
    public interface TaskCompleteListener{

        void taskComplete();
    }
}

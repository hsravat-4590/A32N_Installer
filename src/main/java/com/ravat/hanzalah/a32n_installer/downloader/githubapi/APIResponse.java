package com.ravat.hanzalah.a32n_installer.downloader.githubapi;

import java.io.*;
import java.util.List;

/**
 * Class to hold Github API Response for latest release.
 */
public class APIResponse implements Serializable {

    public  String tag_name;
    public  boolean prerelease;
    public  String published_at;
    public  List<Assets> assets;
    public  String body;

    public class Assets implements Serializable {
        public final int size;
        public final String browser_download_url;

        public Assets(int size, String browser_download_url) {
            this.size = size;
            this.browser_download_url = browser_download_url;
        }
    }

    /**
     * Attempts to serialise the API response for later use
     * @param path The location to store the file along with filename
     * @param apiResponse The API response instance
     * @return True if write is completed successfully
     */
    public boolean writeToDisk(String path, APIResponse apiResponse){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(apiResponse);
            out.close();
            fileOut.close();
            return true;
        } catch (IOException i) {
            i.printStackTrace();
        }
        return false;
    }

}

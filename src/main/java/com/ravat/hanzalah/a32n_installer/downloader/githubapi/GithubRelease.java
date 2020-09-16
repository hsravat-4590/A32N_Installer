package com.ravat.hanzalah.a32n_installer.downloader.githubapi;

import com.google.gson.Gson;
import com.ravat.hanzalah.a32n_installer.ApplicationManifest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import com.ravat.hanzalah.a32n_installer.downloader.Downloader;
import com.ravat.hanzalah.a32n_installer.downloader.FileDownloader;

/**
 * Downloads release artifacts off github using Github API
 */
public class GithubRelease extends Downloader implements FileDownloader.TaskCompleteListener {
    private final String primitiveResponse;
    private String workingPath;
    private final APIResponse response;
    private  boolean isComplete;
    private boolean status;
    private FileDownloader downloader;
    public GithubRelease() throws Exception {
        super();
        String url = String.format("https://api.%s/repos/%s/%s/releases/latest", ApplicationManifest.REMOTE, ApplicationManifest.OWNER, ApplicationManifest.PROJECT);
        System.out.println(url);
        URL api = new URL(url);
        URLConnection connection = api.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()
                )
        );
        String apiOutput;
        String JSON = "";
        while ((apiOutput = in.readLine()) != null)
            JSON = JSON + apiOutput;
        in.close();
        primitiveResponse = JSON;
        Gson gson = new Gson();
        response = gson.fromJson(primitiveResponse,APIResponse.class);

    }
    /**
     * Downloads the latest release
     */
    @Override
     public void download() {
        isComplete = false;
    }

    public String getDownloadZipPath(){
        return this.workingPath;
    }
    public APIResponse getAPIResponse(){return this.response;}


    public void runTask() {

        try {
            URL file = new URL(response.assets.get(0).browser_download_url);
            File workingDir = File.createTempFile("a32n","");

            String workingPath = workingDir.getCanonicalPath();
            int index;
            FileOutputStream fos;
            if (( index= workingPath.lastIndexOf('/')) > 0){
                workingPath = workingPath.substring(0, index).concat("/");
                workingDir = new File(workingPath.concat("/Flybywiresim/"));
                workingPath = workingDir.getCanonicalPath();
                fos = new FileOutputStream(workingPath.concat("/".concat(response.tag_name.concat(".zip"))));
            } else if((index= workingPath.lastIndexOf('\\')) > 0){
                workingPath = workingPath.substring(0,index).concat("\\");
                workingDir = new File(workingPath.concat("\\Flybywiresim\\"));
                workingPath = workingDir.getCanonicalPath();
                this.workingPath = workingPath.concat("\\".concat(response.tag_name.concat(".zip")));
                workingDir.mkdirs();
                fos = new FileOutputStream(workingPath.concat("\\".concat(response.tag_name.concat(".zip"))));
            } else {
                fos = new FileOutputStream(response.tag_name.concat(".zip"));
            }
            ReadableByteChannel rbc = Channels.newChannel(file.openStream());
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            isComplete = true;
        } catch (Exception e) {
            e.printStackTrace();
            isComplete =  true;
        }
    }

    public void download2() throws Exception{
        String URL = response.assets.get(0).browser_download_url;
        File workingDir = File.createTempFile("a32n","");
        String workingPath = workingDir.getCanonicalPath();
        int index;
        if (( index= workingPath.lastIndexOf('/')) > 0){
            workingPath = workingPath.substring(0, index).concat("/");
            workingDir = new File(workingPath.concat("/Flybywiresim/"));
            workingPath = workingDir.getCanonicalPath();
        } else if((index= workingPath.lastIndexOf('\\')) > 0){
            workingPath = workingPath.substring(0,index).concat("\\");
            workingDir = new File(workingPath.concat("\\Flybywiresim\\"));
            workingPath = workingDir.getCanonicalPath();
            this.workingPath = workingPath.concat("\\".concat(response.tag_name.concat(".zip")));
            workingDir.mkdirs();
        }
        downloader = new FileDownloader(this.workingPath,URL);
        downloader.addListener(this);
        downloader.runWithThread();
    }

    public boolean isComplete(){ return isComplete;}

    @Override
    public void taskComplete() {
        isComplete = true;
        System.out.println("Broadcast Received... isComplete set to True");

    }
}

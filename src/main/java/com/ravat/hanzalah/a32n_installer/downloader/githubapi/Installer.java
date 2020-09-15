package com.ravat.hanzalah.a32n_installer.downloader.githubapi;

import com.ravat.hanzalah.a32n_installer.downloader.ZipInstaller;

import java.io.IOException;

/**
 * Github API Focused installer. ALso saves the API Response instance so that it can be used for update purposes
 */
public class Installer extends ZipInstaller {

    private APIResponse apiResponse;
    public Installer(String zipPath,String communityPath, APIResponse apiResponse){
        super(zipPath,communityPath);
        this.apiResponse = apiResponse;
    }

    public Installer(String zipPath, APIResponse apiResponse){
        super(zipPath);
        this.apiResponse = apiResponse;
    }

    public Installer(GithubRelease githubRelease){
        super(githubRelease.getDownloadZipPath());
        this.apiResponse = githubRelease.getAPIResponse();
    }

    public Installer(GithubRelease githubRelease, String communityPath){
        super(githubRelease.getDownloadZipPath(),communityPath);
        this.apiResponse = githubRelease.getAPIResponse();
    }

    @Override
    public boolean install() throws IOException{
        if(super.install()) {
            apiResponse.writeToDisk(super.communityPath.concat("A32NX").concat("installation.bin"), apiResponse);
            return true;
        } else {
            return false;
        }

    }
}

package com.ravat.hanzalah.a32n_installer.ui;

import com.ravat.hanzalah.a32n_installer.downloader.Installer;
import com.ravat.hanzalah.a32n_installer.downloader.githubapi.GithubRelease;
import com.ravat.hanzalah.a32n_installer.fs.DirectoryDetect;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.eclipse.jgit.api.Git;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class InstallDialog {

    @FXML private Button installButton,browseButton;
    @FXML private ProgressBar progressLine;
    @FXML private TextField directoryField;
    @FXML private Label logLabel;
    private Stage stage;
    private Scene scene;
    private GithubRelease release;

    public InstallDialog(Stage stage){
        this.stage = stage;
        stage.initStyle(StageStyle.UNDECORATED);
        try {
            release = new GithubRelease();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void initialize(){
        scene = stage.getScene();
        String communityPath = DirectoryDetect.detectCommunityDirectory();
        if(communityPath == null){
            logLabel.setText("Community Directory could not be found. Please find manually to proceed with the installation");
        } else {
            logLabel.setText("Community Directory Found");
             installButton.setDisable(false);
            directoryField.setText(communityPath);
            directoryField.setDisable(true);
            browseButton.setDisable(true);
        }
            progressLine.setProgress(0);
    }

    @FXML
    public void installClicked(){
        try {
            GithubRelease release = new GithubRelease();
            logLabel.setText("Starting Installation");
            logLabel.setText("Downloading");
            progressLine.setProgress(-1);
            release.download2();
            int timer = 0 ;
            do{
                System.out.println(timer++);
                Thread.sleep(10); //Sleep the thread for 10millis to prevent it from hanging the UI
            }while (release.isComplete() == false);
                logLabel.setText("Complete");
                System.out.println("Download is complete YAY");
                System.out.println("Timer: " + timer);
                logLabel.setText("Installing");
                String communityPath = directoryField.getText();
                Installer installer = new com.ravat.hanzalah.a32n_installer.downloader.githubapi.Installer(release,communityPath.concat("\\"));
            installer.install();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void browseClicked(){
        Stage chooserStage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(chooserStage);
        directoryField.setText(selectedDirectory.getAbsolutePath());
        installButton.setDisable(false);
    }
}

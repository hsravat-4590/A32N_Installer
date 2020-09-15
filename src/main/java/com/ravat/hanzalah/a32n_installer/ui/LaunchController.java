package com.ravat.hanzalah.a32n_installer.ui;


import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
public class LaunchController {

    @FXML private ChoiceBox availBranchesCheckBox;
    @FXML private TextField installPathField;
    @FXML private Button browseDirectory,installButton;
    @FXML private ProgressBar progressBar;
    private Stage stage;

    public LaunchController(Stage stage){this.stage = stage;}

    @FXML
    public void installPressed(){
        System.out.println("Install has been pressed!");
    }
    @FXML
    public void browseClicked(){
        System.out.println("Browse has been clicked!");
    }
 }

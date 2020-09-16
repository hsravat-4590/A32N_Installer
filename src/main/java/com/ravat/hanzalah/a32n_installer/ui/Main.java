package com.ravat.hanzalah.a32n_installer.ui;


import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{


    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/layout/main.fxml"));
            loader.setController(new InstallDialog(stage));
            Parent content = (Parent) loader.load();
            stage.setResizable(false);
            Scene scene = new Scene(content);
            stage.setScene(scene);
            List styles = new LinkedList();
            styles.add(getClass().getResource("/drawable/Material/IndeterminateLoader.css").toExternalForm());
            styles.add(getClass().getResource("/drawable/Material/MaterialFX-0-3.css").toExternalForm());
            scene.getStylesheets().addAll(styles);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

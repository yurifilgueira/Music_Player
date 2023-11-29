package br.ufrn.imd.services;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicReference;

public class WindowService {
    public static void closeWindow(ActionEvent event, Stage stage){
        stage = ((Stage) ((Button) event.getSource()).getScene().getWindow());
        stage.close();
    }

    public static void minimizeWindow(ActionEvent event, Stage stage){
        stage = ((Stage) ((Button) event.getSource()).getScene().getWindow());
        stage.setIconified(true);
    }

    public static void moveWindow(Stage stage, Parent root){
        AtomicReference<Double> x = new AtomicReference<>((double) 0);
        AtomicReference<Double> y = new AtomicReference<>((double) 0);

        root.setOnMousePressed( mouseEvent -> {
            x.set(mouseEvent.getSceneX());
            y.set(mouseEvent.getSceneY());
        });

        root.setOnMouseDragged( mouseEvent -> {
            if(y.get() < 27){
                stage.setX(mouseEvent.getScreenX() - x.get());
                stage.setY(mouseEvent.getScreenY() - y.get());
            }
        });
    }
}

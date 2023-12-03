package br.ufrn.imd.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public abstract class MainController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    protected Stage getStage() {
        return stage;
    }

    protected void setStage(Stage stage) {
        this.stage = stage;
    }

    protected Scene getScene() {
        return scene;
    }

    protected void setScene(Scene scene) {
        this.scene = scene;
    }

    protected Parent getRoot() {
        return root;
    }

    protected void setRoot(Parent root) {
        this.root = root;
    }

    public abstract void onCloseButton(ActionEvent event);

    public abstract void onMinimizeButton(ActionEvent event);
}

package com.example.aps.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    public Button autoButton;

    @FXML
    protected void stepByStepClicked() {

    }

    @FXML
    private void autoClicked() {
        try {
            Stage stage = (Stage) autoButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aps/auto-screen.fxml"));
            VBox autoScreen = loader.load();

            Scene autoScene = new Scene(autoScreen);

            stage.setScene(autoScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
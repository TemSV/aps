package com.example.aps;

import com.example.aps.logic.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Mode mode = Mode.STEP;
        Application application = new Application(4, 3, 6);
        application.setMode(mode);
        Generator generator = new Generator(application, 15, 0.5);
        generator.run();
        application.getStatistics().printStatistics();
    }
}

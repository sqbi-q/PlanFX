package com.sqbiq.planfx;

import com.sqbiq.planfx.elements.SessionAddDialog;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(new Pane(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        SessionAddDialog dialog = new SessionAddDialog();
        dialog.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
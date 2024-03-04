package com.sqbiq.planfx;

import com.sqbiq.planfx.elements.SessionAddDialog;
import com.sqbiq.planfx.session.Session;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(new Pane(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        SessionAddDialog dialog = new SessionAddDialog();
        Optional<Session> dialogResult = dialog.showAndWait();
        dialogResult.ifPresent(session -> {
            System.out.println(session.getTitle());
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
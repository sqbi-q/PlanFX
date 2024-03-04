package com.sqbiq.planfx;

import com.sqbiq.planfx.elements.SessionAddDialog;
import com.sqbiq.planfx.session.Session;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Pane root = new Pane();
        root.getChildren().add(getApplicationToolbar(stage));

        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    private ToolBar getApplicationToolbar(Stage stage) {
        ToolBar toolbar = new ToolBar();
        toolbar.prefWidthProperty().bind(stage.widthProperty());

        Button addSessionButton = new Button("Add session");

        addSessionButton.setOnAction(event -> {
            SessionAddDialog dialog = new SessionAddDialog();
            Optional<Session> dialogResult = dialog.showAndWait();
            dialogResult.ifPresent(session -> {
                System.out.println(session.getTitle());
            });
        });

        toolbar.getItems().add(addSessionButton);

        return toolbar;
    }

    public static void main(String[] args) {
        launch();
    }
}
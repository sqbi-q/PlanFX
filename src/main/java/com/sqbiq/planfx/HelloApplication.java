package com.sqbiq.planfx;

import com.sqbiq.planfx.elements.SessionAddDialog;
import com.sqbiq.planfx.elements.SessionView;
import com.sqbiq.planfx.session.Session;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        VBox root = new VBox();

        HBox sessionsContainer = new HBox();
        ScrollPane sessionsPane = new ScrollPane();
        sessionsPane.setContent(sessionsContainer);

        Button addSessionButton = new Button("Add session");

        addSessionButton.setOnAction(event -> {
            SessionAddDialog dialog = new SessionAddDialog();
            Optional<Session> dialogResult = dialog.showAndWait();
            dialogResult.ifPresent(session -> {
                SessionView sv = new SessionView(session);
                sessionsContainer.getChildren().add(sv);
            });
        });

        root.getChildren().add(getApplicationToolbar(stage, addSessionButton));
        root.getChildren().add(sessionsPane);

        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    private ToolBar getApplicationToolbar(Stage stage, Node... es) {
        ToolBar toolbar = new ToolBar();
        toolbar.prefWidthProperty().bind(stage.widthProperty());

        toolbar.getItems().addAll(es);

        return toolbar;
    }

    public static void main(String[] args) {
        launch();
    }
}
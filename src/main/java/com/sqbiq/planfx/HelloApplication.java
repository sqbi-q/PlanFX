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
import java.util.ArrayList;
import java.util.Optional;

public class HelloApplication extends Application {
    private ArrayList<Session> sessions = new ArrayList<>();

    private HBox sessionsContainer = new HBox();

    @Override
    public void start(Stage stage) throws IOException {
        VBox root = new VBox();

        ScrollPane sessionsPane = new ScrollPane();
        sessionsPane.setContent(sessionsContainer);

        Button addSessionButton = new Button("Add session");

        addSessionButton.setOnAction(event -> {
            SessionAddDialog dialog = new SessionAddDialog();
            Optional<Session> dialogResult = dialog.showAndWait();
            dialogResult.ifPresent(this::onSessionAdd);
        });

        root.getChildren().add(getApplicationToolbar(stage, addSessionButton));
        root.getChildren().add(sessionsPane);

        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    private void onSessionAdd(Session session) {
        sessions.add(session);
        SessionView sv = new SessionView(session);
        sv.setPrefWidth(200);

        sv.addSessionEventListener(new SessionView.SessionViewEventListener() {
            @Override
            public void deleted(SessionView sessionView) {
                // todo confirm if user really wants to remove session

                // remove session view and associated session
                sessions.remove(sessionView.getSession());
                sessionsContainer.getChildren().remove(sessionView);
            }

            @Override
            public void edited(SessionView sessionView) {
                System.out.println("Edit");
            }
        });

        sessionsContainer.getChildren().add(sv);
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
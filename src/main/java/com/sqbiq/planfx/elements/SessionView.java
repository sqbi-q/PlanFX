package com.sqbiq.planfx.elements;

import com.sqbiq.planfx.session.Session;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SessionView extends VBox {
    private final Session session;

    public SessionView(Session session) {
        this.session = session;

        Label title = new Label(session.getTitle());
        title.fontProperty().set(new Font(20));
        getChildren().add(title);

        Label description = new Label(session.getDescription());
        getChildren().add(description);
    }
}

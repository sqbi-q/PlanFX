package com.sqbiq.planfx.elements;

import com.sqbiq.planfx.session.Session;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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

        borderProperty().set(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        paddingProperty().set(new Insets(10, 10, 10, 10));
    }
}

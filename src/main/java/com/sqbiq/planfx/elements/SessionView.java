package com.sqbiq.planfx.elements;

import com.sqbiq.planfx.session.Session;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SessionView extends VBox {
    private final Session session;

    public SessionView(Session session) {
        this.session = session;

        Label title = new Label();
        title.textProperty().bind(session.getTitleProperty());
        title.fontProperty().set(new Font(20));
        getChildren().add(title);

        Label description = new Label();
        description.textProperty().bind(session.getDescriptionProperty());
        getChildren().add(description);

        borderProperty().set(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        paddingProperty().set(new Insets(10, 10, 10, 10));

        ContextMenu contextMenu = getContextMenu();
        setOnContextMenuRequested(e ->
                contextMenu.show(this, e.getScreenX(), e.getScreenY()));
    }

    private ContextMenu getContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem deleteItem = new MenuItem("Delete session");
        deleteItem.setOnAction(event -> {
            System.out.println("Delete");
        });
        contextMenu.getItems().add(deleteItem);

        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(event -> {
            System.out.println("Edit");
        });
        contextMenu.getItems().add(editItem);

        return contextMenu;
    }
}

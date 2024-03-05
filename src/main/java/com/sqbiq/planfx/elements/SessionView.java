package com.sqbiq.planfx.elements;

import com.sqbiq.planfx.session.Session;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class SessionView extends VBox {
    private final Session session;

    private final List<SessionViewEventListener> sessionViewEventListeners =
            new ArrayList<>();

    public interface SessionViewEventListener extends EventListener {
        void deleted(SessionView sessionView);
        void edited(SessionView sessionView);
    }

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

    public Session getSession() {
        return session;
    }

    public void addSessionEventListener(SessionViewEventListener onSessionViewEvent) {
        sessionViewEventListeners.add(onSessionViewEvent);
    }

    public void removeSessionEventListener(SessionViewEventListener onSessionViewEvent) {
        sessionViewEventListeners.remove(onSessionViewEvent);
    }

    private ContextMenu getContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem deleteItem = new MenuItem("Delete session");
        deleteItem.setOnAction(event -> {
            // notify on delete to arrays holding SessionView
            sessionViewEventListeners.forEach(listener -> listener.deleted(this));
        });
        contextMenu.getItems().add(deleteItem);

        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(event -> {
            // notify on edit to pop-up edit menu
            sessionViewEventListeners.forEach(listener -> listener.edited(this));
        });
        contextMenu.getItems().add(editItem);

        return contextMenu;
    }
}

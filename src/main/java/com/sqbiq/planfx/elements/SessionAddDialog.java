package com.sqbiq.planfx.elements;

import com.sqbiq.planfx.session.Session;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class SessionAddDialog extends Dialog<Session> {
    public SessionAddDialog() {
        setTitle("Add Session");

        GridPane fieldsPane = new GridPane();

        TextField title = new TextField();
        Label titleLabel = new Label("Title:");
        fieldsPane.add(titleLabel, 0, 0, 2, 1);
        fieldsPane.add(title, 0, 1, 2, 1);

        TextArea description = new TextArea();
        Label descriptionLabel = new Label("Description:");
        fieldsPane.add(descriptionLabel, 0, 2, 2, 1);
        fieldsPane.add(description, 0, 3, 2, 1);

        DatePicker dateBegin = new DatePicker();
        Label dateBeginLabel = new Label("Date begin:");
        fieldsPane.add(dateBeginLabel, 0, 4, 1, 1);
        fieldsPane.add(dateBegin, 0, 5, 1, 1);

        DatePicker dateEnd = new DatePicker();
        Label dateEndLabel = new Label("Date end:");
        fieldsPane.add(dateEndLabel, 1, 4, 1, 1);
        fieldsPane.add(dateEnd, 1, 5, 1, 1);

        getDialogPane().setContent(fieldsPane);

        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(cancelButtonType);
        getDialogPane().getButtonTypes().add(addButtonType);
        boolean addDisabled = false;
        getDialogPane().lookupButton(addButtonType).setDisable(addDisabled);

        setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                Session session = new Session(title.getText());
                session.setDescription(description.getText());
                return session;
            }
            return null;
        });
    }
}

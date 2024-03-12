package com.sqbiq.planfx.elements;

import com.sqbiq.planfx.session.Session;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class SessionMenuDialog extends Dialog {

    private GridPane fieldsPane = new GridPane();

    private TextField title = new TextField();
    private TextArea description = new TextArea();

    private DatePicker dateBegin = new DatePicker();
    private TimeSpinner timeBegin = new TimeSpinner(LocalTime.now());

    private DatePicker dateEnd = new DatePicker();
    private TimeSpinner timeEnd = new TimeSpinner(LocalTime.now().plusMinutes(45));

    private ButtonType deleteButton = new ButtonType("Delete",
            ButtonBar.ButtonData.OK_DONE);
    private ButtonType cancelButton = new ButtonType("Cancel",
            ButtonBar.ButtonData.CANCEL_CLOSE);
    private ButtonType addButton = new ButtonType("Add",
            ButtonBar.ButtonData.OK_DONE);
    private ButtonType editButton = new ButtonType("Apply changes",
            ButtonBar.ButtonData.OK_DONE);

    private SessionMenuDialog() {
        Label titleLabel = new Label("Title:");
        fieldsPane.add(titleLabel, 0, 0, 2, 1);
        fieldsPane.add(title, 0, 1, 2, 1);

        Label descriptionLabel = new Label("Description:");
        fieldsPane.add(descriptionLabel, 0, 2, 2, 1);
        fieldsPane.add(description, 0, 3, 2, 1);

        Label dateBeginLabel = new Label("Date begin:");
        fieldsPane.add(dateBeginLabel, 0, 4, 1, 1);
        fieldsPane.add(dateBegin, 0, 5, 1, 1);
        fieldsPane.add(timeBegin, 0, 6, 1, 1);

        Label dateEndLabel = new Label("Date end:");
        fieldsPane.add(dateEndLabel, 1, 4, 1, 1);
        fieldsPane.add(dateEnd, 1, 5, 1, 1);
        fieldsPane.add(timeEnd, 1, 6, 1, 1);

        getDialogPane().setContent(fieldsPane);

        getDialogPane().getButtonTypes().add(cancelButton);
    }

    public interface AddSessionEventListener extends EventListener {
        void added(Session session);
    }

    public interface EditSessionEventListener extends EventListener {
        void deleted();
        void edited(Session session);
    }

    public static SessionMenuDialog Add(AddSessionEventListener onAdd) {
        SessionMenuDialog dialog = new SessionMenuDialog();
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().add(dialog.addButton);

        ((Button) dialogPane.lookupButton(dialog.addButton)).setOnAction(e -> {
            Session session = new Session(dialog.getSessionTitle());
            session.setDescription(dialog.getDescription());

            session.setDateBegin(LocalDateTime.of(
                    dialog.getDateBegin().getValue(), dialog.getTimeBegin().getValue()));

            session.setDateEnd(LocalDateTime.of(
                    dialog.getDateEnd().getValue(), dialog.getTimeEnd().getValue()));

            onAdd.added(session);
        });

        return dialog;
    }

    public static SessionMenuDialog Edit(Session baseSession, EditSessionEventListener onEdit) {
        SessionMenuDialog dialog = new SessionMenuDialog();

        // set fields to match properties of baseSession
        dialog.setSessionTitle(baseSession.getTitle());
        dialog.setDescription(baseSession.getDescription());

        dialog.setDateBeginValue(baseSession.getDateBegin().toLocalDate());
        dialog.setTimeBeginValue(baseSession.getDateBegin().toLocalTime());

        dialog.setDateEndValue(baseSession.getDateEnd().toLocalDate());
        dialog.setTimeEndValue(baseSession.getDateEnd().toLocalTime());

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().add(dialog.deleteButton);
        dialogPane.getButtonTypes().add(dialog.editButton);

        ((Button) dialogPane.lookupButton(dialog.deleteButton)).setOnAction(e -> {
            onEdit.deleted();
        });

        ((Button) dialogPane.lookupButton(dialog.editButton)).setOnAction(e -> {
            Session editedSession = new Session(dialog.getSessionTitle());
            editedSession.setDescription(dialog.getDescription());
            editedSession.setDateBegin(LocalDateTime.of(
                    dialog.getDateBegin().getValue(), dialog.getTimeBegin().getValue()));

            editedSession.setDateEnd(LocalDateTime.of(
                    dialog.getDateEnd().getValue(), dialog.getTimeEnd().getValue()));

            onEdit.edited(editedSession);
        });

        return dialog;
    }

    public String getSessionTitle() {
        return title.getText();
    }

    public void setSessionTitle(String title) {
        this.title.setText(title);
    }

    public String getDescription() {
        return description.getText();
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public DatePicker getDateBegin() {
        return dateBegin;
    }

    public void setDateBeginValue(LocalDate value) {
        this.dateBegin.setValue(value);
    }

    public TimeSpinner getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBeginValue(LocalTime value) {
        this.timeBegin.setValue(value);
    }

    public DatePicker getDateEnd() {
        return dateEnd;
    }

    public void setDateEndValue(LocalDate value) {
        this.dateEnd.setValue(value);
    }

    public TimeSpinner getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEndValue(LocalTime value) {
        this.timeEnd.setValue(value);
    }
}

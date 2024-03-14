package com.sqbiq.planfx;

import com.sqbiq.calendarviewfx.Calendar;
import com.sqbiq.planfx.elements.SessionAddDialog;
import com.sqbiq.planfx.elements.SessionMenuDialog;
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
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HelloApplication extends Application {
    private ArrayList<Session> sessions = new ArrayList<>();

    private HBox sessionsContainer = new HBox();

    private Calendar calendarView = new Calendar();

    @Override
    public void start(Stage stage) throws IOException {
        VBox root = new VBox();

        ScrollPane sessionsPane = new ScrollPane();
        sessionsPane.setContent(sessionsContainer);
        VBox.setVgrow(sessionsPane, Priority.ALWAYS);
        sessionsPane.setFitToHeight(true);

        Button addSessionButton = new Button("Add session");

        addSessionButton.setOnAction(event -> {
            SessionMenuDialog dialog = SessionMenuDialog.Add(this::onSessionAdd);
            dialog.showAndWait();
        });

        root.getChildren().add(getApplicationToolbar(stage, addSessionButton));

        root.getChildren().add(calendarView);
        calendarView.addChangeDateListener(this::onCalendarUpdate);

        root.getChildren().add(sessionsPane);

        Scene scene = new Scene(root, 500, 400);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    private void onSessionAdd(Session session) {
        sessions.add(session);
        SessionView sv = new SessionView(session);
        sv.setPrefWidth(200);
        sv.prefHeightProperty().bind(sessionsContainer.heightProperty());

        sv.addSessionEventListener(new SessionView.SessionViewEventListener() {
            @Override
            public void deleted(SessionView sessionView) {
                // todo confirm if user really wants to remove session

                // remove session view and associated session
                onSessionDelete(sessionView);
                onCalendarUpdate();
            }

            @Override
            public void edited(SessionView sessionView) {
                SessionMenuDialog dialog = SessionMenuDialog.Edit(session,
                    new SessionMenuDialog.EditSessionEventListener() {
                        @Override
                        public void deleted() {
                            onSessionDelete(sessionView);
                        }

                        @Override
                        public void edited(Session editedSession) {
                            session.setTitle(editedSession.getTitle());
                            session.setDescription(editedSession.getDescription());
                            session.setDateBegin(editedSession.getDateBegin());
                            session.setDateEnd(editedSession.getDateEnd());
                        }
                    });
                dialog.showAndWait();
                onCalendarUpdate();
            }
        });

        sessionsContainer.getChildren().add(sv);
        onCalendarUpdate();
    }

    private void onSessionDelete(SessionView sv) {
        sessions.remove(sv.getSession());
        sessionsContainer.getChildren().remove(sv);
    }

    private void onCalendarUpdate() {
        YearMonth selectedDate = calendarView.getDate();

        List<Button> dateCells = calendarView.getCalendarGrid().getDateCells();
        dateCells.forEach(cell -> cell.setStyle(null));

        sessions.stream()
                .filter(session -> {
                    YearMonth sessionBegin = YearMonth.from(session.getDateBegin());
                    YearMonth sessionEnd = YearMonth.from(session.getDateEnd());

                    return (selectedDate.isAfter(sessionBegin) || selectedDate.equals(sessionBegin))
                        && (selectedDate.isBefore(sessionEnd) || selectedDate.equals(sessionEnd));
                })
                .forEach(session -> {
                    LocalDate sessionBegin = LocalDate.from(session.getDateBegin());
                    LocalDate sessionEnd = LocalDate.from(session.getDateEnd());

                    int dayBegin = selectedDate.atDay(1).isAfter(sessionBegin) ? 1
                            : sessionBegin.getDayOfMonth();
                    int dayEnd = selectedDate.atEndOfMonth().isBefore(sessionEnd) ? selectedDate.lengthOfMonth()
                            : sessionEnd.getDayOfMonth();

                    for (int day = dayBegin; day <= dayEnd; day++) {
                        dateCells.get(day-1).setStyle("-fx-background-color: #b0b0b0;");
                    }
                });
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
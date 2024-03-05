package com.sqbiq.planfx.session;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

public class Session {
    private StringProperty title = new SimpleStringProperty(this, "title");
    private StringProperty description = new SimpleStringProperty(this, "description");
    private LocalDateTime dateBegin;
    private LocalDateTime dateEnd;

    public Session(String title) {
        this.title.set(title);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty getTitleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty getDescriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public LocalDateTime getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(LocalDateTime dateBegin) {
        this.dateBegin = dateBegin;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }
}

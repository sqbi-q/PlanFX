package com.sqbiq.planfx.elements;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeSpinner extends Spinner<LocalTime> {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    private StringConverter<LocalTime> localTimeConverter = new StringConverter<>() {
        @Override
        public String toString(LocalTime localTime) {
            return formatter.format(localTime);
        }

        @Override
        public LocalTime fromString(String s) {
            String[] fields = s.split(":");
            int hours = Integer.parseInt(fields[0]);
            int minutes = Integer.parseInt(fields[1]);

            return LocalTime.of(hours, minutes);
        }
    };

    private SpinnerValueFactory<LocalTime> timeSpinnerValueFactory = new SpinnerValueFactory<>() {
        {
            setConverter(localTimeConverter);
            setValue(LocalTime.MIDNIGHT);
        }

        @Override
        public void decrement(int i) {
            setValue(getValue().minusMinutes(i));
        }

        @Override
        public void increment(int i) {
            setValue(getValue().plusMinutes(i));
        }
    };

    private TextFormatter<LocalTime> textFormatter = new TextFormatter<>(localTimeConverter, LocalTime.MIDNIGHT,
        c -> {
            String newText = c.getControlNewText();
            // check if new text is valid
            if (newText.matches("[0-9]{0,2}:[0-9]{0,2}"))
                return c;
            return null;
        }
    );

    public TimeSpinner(LocalTime time) {
        super();

        setEditable(true);
        setValueFactory(timeSpinnerValueFactory);
        getEditor().setTextFormatter(textFormatter);
        timeSpinnerValueFactory.setValue(time);
    }

    public void setValue(LocalTime time) {
        timeSpinnerValueFactory.setValue(time);
    }
}

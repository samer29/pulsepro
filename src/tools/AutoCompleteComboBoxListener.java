package tools;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class AutoCompleteComboBoxListener<T> implements EventHandler<KeyEvent> {

    private final JFXComboBox<T> comboBox;
    private final ObservableList<T> data;

    public AutoCompleteComboBoxListener(final JFXComboBox<T> comboBox) {
        this.comboBox = comboBox;
        this.data = comboBox.getItems();

        comboBox.setEditable(true);
        comboBox.setOnKeyPressed(e -> handle(e));
        comboBox.setOnKeyReleased(e -> handle(e));
    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
            return; // Ignore UP and DOWN key events
        }

        ObservableList<T> filteredItems = FXCollections.observableArrayList();
        String inputText = comboBox.getEditor().getText().toLowerCase();

        for (T item : data) {
            if (item.toString().toLowerCase().startsWith(inputText)) {
                filteredItems.add(item);
            }
        }

        comboBox.setItems(filteredItems);

        if (!filteredItems.isEmpty()) {
            comboBox.show();
        }
    }
}

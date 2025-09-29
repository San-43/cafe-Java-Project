package org.cerveza.cafe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {

    @FXML
    private Label databaseLocationLabel;

    @FXML
    private Label statusLabel;

    @FXML
    public void initialize() {
        databaseLocationLabel.setText(DatabaseManager.getDatabasePath().toString());
        statusLabel.setText("La base de datos se inicializó correctamente.");
    }
}

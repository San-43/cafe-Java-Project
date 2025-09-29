package org.cerveza.cafe;

import jakarta.persistence.EntityManager;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.cerveza.cafe.model.DumpRecord;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DumpListController {

    @FXML
    private TableView<DumpRecord> dumpTable;

    @FXML
    private TableColumn<DumpRecord, Long> idColumn;

    @FXML
    private TableColumn<DumpRecord, String> fileNameColumn;

    @FXML
    private TableColumn<DumpRecord, String> pathColumn;

    @FXML
    private TableColumn<DumpRecord, LocalDateTime> createdColumn;

    @FXML
    private Label databaseLocationLabel;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getId()));
        fileNameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getNombreArchivo()));
        pathColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getRutaArchivo()));
        createdColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getFechaCreacion()));
        createdColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(dateFormatter.format(item));
                }
            }
        });

        databaseLocationLabel.setText(DatabaseManager.getDatabasePath().toString());

        loadDumpRecords();
    }

    private void loadDumpRecords() {
        EntityManager entityManager = DatabaseManager.getEntityManagerFactory().createEntityManager();
        try {
            List<DumpRecord> records = entityManager
                    .createQuery("SELECT d FROM DumpRecord d ORDER BY d.fechaCreacion DESC", DumpRecord.class)
                    .getResultList();
            dumpTable.getItems().setAll(records);
        } finally {
            entityManager.close();
        }
    }
}

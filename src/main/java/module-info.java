module org.cerveza.cafe {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires jakarta.persistence;

    opens org.cerveza.cafe to javafx.fxml;
    exports org.cerveza.cafe;
    opens org.cerveza.cafe.model;
    exports org.cerveza.cafe.model;
}

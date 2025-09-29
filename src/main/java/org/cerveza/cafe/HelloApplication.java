package org.cerveza.cafe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        DatabaseManager.initialize();
    }

    @Override
    public void stop() {
        DatabaseManager.shutdown();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dump-list-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 480);
        stage.setTitle("Historial de dumps de base de datos");
        stage.setScene(scene);
        stage.show();
    }
}

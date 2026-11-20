package uam.edu.ni.servicios_web;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Tab usuarios = new Tab("Usuarios", FXMLLoader.load(getClass().getResource("usuario-view.fxml")));
        Tab clientes = new Tab("Clientes", FXMLLoader.load(getClass().getResource("cliente-view.fxml")));
        TabPane pestañas = new TabPane(usuarios, clientes);
        pestañas.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        Scene scene = new Scene(pestañas, 920, 480);
        stage.setTitle("Servicios Web");
        stage.setScene(scene);
        stage.show();
    }
}

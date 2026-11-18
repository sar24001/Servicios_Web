package uam.edu.ni.servicios_web.controller;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import uam.edu.ni.servicios_web.model.Usuario;
import uam.edu.ni.servicios_web.service.UsuarioService;

import java.io.IOException;
import java.util.List;


public class UsuarioController {

    @FXML
    private TableView<Usuario> tablaUsuarios;
    @FXML
    private TableColumn<Usuario, Integer> colId;
    @FXML
    private TableColumn<Usuario, String> colNombre;
    @FXML
    private TableColumn<Usuario, String> colUsername;
    @FXML
    private TableColumn<Usuario, String> colCorreo;
    @FXML
    private TableColumn<Usuario, String> colTelefono;
    @FXML
    private TableColumn<Usuario, String> colCiudad;
    @FXML
    private TableColumn<Usuario, String> colEmpresa;
    @FXML
    private Button btnConsultar;
    @FXML
    private Label lblEstado;

    private final UsuarioService service = new UsuarioService();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );
        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );
        colUsername.setCellValueFactory(
                new PropertyValueFactory<>("username")
        );
        colCorreo.setCellValueFactory(
                new PropertyValueFactory<>("email")
        );
        colTelefono.setCellValueFactory(
                new PropertyValueFactory<>("telefono")
        );
        colCiudad.setCellValueFactory(
                new PropertyValueFactory<>("ciudad")
        );
        colEmpresa.setCellValueFactory(
                new PropertyValueFactory<>("empresa")
        );
    }

    @FXML
    private void consultarUsuarios(){
        Task<List<Usuario>> task = new Task<List<Usuario>>() {
            @Override
            protected List<Usuario> call() throws Exception {
                return service.obtenerUsuarios();
            }
        };

        btnConsultar.disableProperty().bind(
                task.runningProperty()
        );

        task.setOnSucceeded(e -> {
            List<Usuario> usuarios = task.getValue();
            tablaUsuarios.setItems(FXCollections.observableArrayList(usuarios));
            lblEstado.setText("Usuarios cargados: " + usuarios.size());
        });

        task.setOnFailed(e -> {
            Throwable error = task.getException();
            lblEstado.setText("Error: " + error.getMessage());
        });
        Thread hilo = new Thread(task);
        hilo.setDaemon(true);
        hilo.start();
    }
}
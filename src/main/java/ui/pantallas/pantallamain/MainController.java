package ui.pantallas.pantallamain;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.log4j.Log4j2;
import modelo.Usuario;
import servicios.ServiciosConfig;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;
import ui.pantallas.common.Pantallas;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

@Log4j2
public class MainController extends BasePantallaController implements Initializable {

    Instance<Object> instance;

    private final ServiciosConfig serviciosConfig;

    @Inject
    public MainController(Instance<Object> instance, ServiciosConfig serviciosConfig) {
        this.instance = instance;
        this.serviciosConfig = serviciosConfig;
    }

    @FXML
    private Stage primaryStage;

    @FXML
    private Menu menuOpciones;

    @FXML
    private BorderPane root;

    public Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return serviciosConfig.getNombre();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuario = serviciosConfig.getUsuario();
        menuOpciones.setVisible(false);
    }

    private void closeWindowEvent(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().add(ButtonType.CANCEL);
        alert.getButtonTypes().add(ButtonType.YES);
        alert.setTitle(ConstantesPantallas.SALIR_DE_LA_APLICACIÓN);
        alert.setContentText(ConstantesPantallas.ESTÁ_SEGURO_QUE_DESEA_SALIR_DE_LA_APLICACIÓN_PODRÍAN_PERDERSE_LOS_DATOS_DE_ESTA_SESIÓN);
        alert.initOwner(primaryStage.getOwner());
        Optional<ButtonType> res = alert.showAndWait();

        res.ifPresent(buttonType -> {
            if (buttonType == ButtonType.CANCEL) {
                event.consume();
            }
        });
    }

    public void setStage(Stage stage) {
        primaryStage = stage;
        primaryStage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
        if (Objects.equals(getNombre(), "")) {
            cargarPantalla(Pantallas.PANTALLALOGIN);
        } else {
            cargarPantalla(Pantallas.PANTALLAINICIO);
        }
    }

    private void cargarPantalla(Pantallas pantalla) {
        Pane panePantalla;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(controller -> instance.select(controller).get());
            panePantalla = fxmlLoader.load(getClass().getResourceAsStream(pantalla.getRuta()));
            BasePantallaController pantallaController = fxmlLoader.getController();
            pantallaController.setMainController(this);
            pantallaController.principalCargado();
            root.setCenter(panePantalla);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void cargarPantallaBanners(ActionEvent actionEvent) {
        cargarPantalla(Pantallas.PANTALLABANNERS);
    }

    public void cargarTienda(ActionEvent actionEvent) {
        cargarPantalla(Pantallas.PANTALLATIENDA);
    }

    public void cargarFarmeo(ActionEvent actionEvent) {
        cargarPantalla(Pantallas.PANTALLAFARMEO);
    }

    public void cargarInicio() {
        serviciosConfig.saveUsuario(usuario);
        cargarPantalla(Pantallas.PANTALLAINICIO);
    }

    public void cargarInventario(ActionEvent actionEvent) {
        cargarPantalla(Pantallas.PANTALLAINVENTARIO);
    }

    public void saveUsuario() {
        serviciosConfig.saveUsuario(usuario);
    }

    public void salir() {
        serviciosConfig.saveUsuario(usuario);
        Platform.exit();
    }
}

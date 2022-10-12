package ui.pantallas.pantallalogin;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import servicios.ServiciosConfig;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends BasePantallaController implements Initializable {

    private final ServiciosConfig serviciosConfig;

    @Inject
    public LoginController(ServiciosConfig serviciosConfig) {
        this.serviciosConfig = serviciosConfig;
    }

    @FXML
    private MFXButton botonInicio;

    @FXML
    private MFXTextField textoNombre;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void login() {
        if (serviciosConfig.saveNombre(textoNombre.getText())) {
            this.getMainController().getUsuario().setNombreUsuario(textoNombre.getText());
            this.getMainController().cargarInicio();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ConstantesPantallas.ERROR);
            alert.setHeaderText(ConstantesPantallas.ERROR);
            alert.setContentText(ConstantesPantallas.NOMBRE_DE_USUARIO_NO_VÁLIDO);
            alert.showAndWait();
        }
    }
}
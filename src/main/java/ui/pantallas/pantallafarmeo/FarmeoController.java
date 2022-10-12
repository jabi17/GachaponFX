package ui.pantallas.pantallafarmeo;

import io.github.palexdev.materialfx.controls.MFXButton;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import modelo.Usuario;
import servicios.ServiciosFarmeo;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;

import java.net.URL;
import java.util.ResourceBundle;

public class FarmeoController extends BasePantallaController implements Initializable {

    private final ServiciosFarmeo serviciosFarmeo;

    @Inject
    public FarmeoController(ServiciosFarmeo serviciosFarmeo) {
        this.serviciosFarmeo = serviciosFarmeo;
    }

    @FXML
    private MFXButton testButton;

    @FXML
    private Label labelMonedas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void principalCargado() {
        labelMonedas.setText(ConstantesPantallas.MONEDAS_LABEL + this.getMainController().getUsuario().getCantidadMonedas() + ConstantesPantallas.CARACTER_VACIO);
    }

    public void addMonedas(MouseEvent actionEvent) {
        serviciosFarmeo.addMonedas(1000);
        Usuario usu = this.getMainController().getUsuario();
        usu.setCantidadMonedas(this.getMainController().getUsuario().getCantidadMonedas() + 1000);
        this.getMainController().setUsuario(usu);
        labelMonedas.setText(ConstantesPantallas.MONEDAS_LABEL + this.getMainController().getUsuario().getCantidadMonedas() + ConstantesPantallas.CARACTER_VACIO);
    }

    public void volverAtras(MouseEvent mouseEvent) {
        this.getMainController().cargarInicio();
    }
}
package ui.pantallas.pantallainicio;

import io.github.palexdev.materialfx.controls.MFXButton;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.log4j.Log4j2;
import modelo.Usuario;
import servicios.ServiciosConfig;
import servicios.ServiciosUsuario;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

@Log4j2
public class InicioController extends BasePantallaController implements Initializable {

    private final ServiciosConfig serviciosConfig;
    private final ServiciosUsuario serviciosUsuario;

    @Inject
    public InicioController(ServiciosConfig serviciosConfig, ServiciosUsuario serviciosUsuario) {
        this.serviciosConfig = serviciosConfig;
        this.serviciosUsuario = serviciosUsuario;
    }

    @FXML
    private MFXButton botonBanners;

    @FXML
    private MFXButton botonFarmeo;

    @FXML
    private MFXButton botonTienda;

    @FXML
    private MFXButton botonSalir;

    @FXML
    private ImageView fondoPantalla;

    @FXML
    private ImageView logo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try (var inputStream = getClass().getResourceAsStream(ConstantesPantallas.RUTA_GIF_FONDO)) {
            assert inputStream != null;
            Image logoImage = new Image(inputStream);
            fondoPantalla.setImage(logoImage);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        try (var inputStream = getClass().getResourceAsStream(ConstantesPantallas.RUTA_LOGO)) {
            assert inputStream != null;
            Image logoImage = new Image(inputStream);
            logo.setImage(logoImage);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void principalCargado() {
        LocalDate ultimaConexion = this.getMainController().getUsuario().getUltimaConexion();
        if (ultimaConexion.isBefore(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(ConstantesPantallas.INFORMACIÓN);
            alert.setHeaderText(ConstantesPantallas.HOLA + this.getMainController().getUsuario().getNombreUsuario() + ConstantesPantallas.EXCLAMACION);
            alert.setContentText(ConstantesPantallas.RECOMPENSA_DIARIA);
            alert.showAndWait();
            Usuario usu = this.getMainController().getUsuario();
            usu.setCantidadMonedas(this.getMainController().getUsuario().getCantidadMonedas() + 60);
            usu.setUltimaConexion(LocalDate.now());
            this.getMainController().setUsuario(usu);
            this.getMainController().saveUsuario();
        }
    }

    public void cargarPantallaBanners(ActionEvent actionEvent) {
        this.getMainController().cargarPantallaBanners(actionEvent);
    }

    public void cargarPantallaFarmeo(ActionEvent actionEvent) {
        this.getMainController().cargarFarmeo(actionEvent);
    }

    public void cargarPantallaTienda(ActionEvent actionEvent) {
        this.getMainController().cargarTienda(actionEvent);
    }

    public void cargarPantallaInventario(ActionEvent actionEvent) {
        this.getMainController().cargarInventario(actionEvent);
    }

    public void salir(ActionEvent actionEvent) {
        this.getMainController().salir();
    }

    public void reiniciarCuenta(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(ConstantesPantallas.CONFIRMACIÓN);
        alert.setHeaderText(ConstantesPantallas.REINICIO1);
        alert.setContentText(ConstantesPantallas.REINICIO2 +
                ConstantesPantallas.MONEDAS_A_CONSEGUIR + serviciosUsuario.getValorInventario());
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK && serviciosUsuario.getValorInventario() > 0) {
            serviciosConfig.reiniciarCuenta();
            Platform.exit();
        } else {
            alert.close();
            if (serviciosUsuario.getValorInventario() == 0) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle(ConstantesPantallas.INFORMACIÓN);
                alert2.setHeaderText(ConstantesPantallas.NO_SE_PUDO_REINICIAR_LA_CUENTA);
                alert2.setContentText(ConstantesPantallas.NO_TIENES_SUFICIENTES_MONEDAS_PARA_REINICIAR_LA_CUENTA);
                alert2.showAndWait();
            }
        }
    }
}
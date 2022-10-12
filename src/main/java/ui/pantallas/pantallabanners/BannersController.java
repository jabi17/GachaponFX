package ui.pantallas.pantallabanners;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.extern.log4j.Log4j2;
import modelo.Banner;
import modelo.Personaje;
import modelo.Usuario;
import servicios.ServiciosBanner;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Log4j2
public class BannersController extends BasePantallaController implements Initializable {

    private final ServiciosBanner serviciosBanner;

    @Inject
    public BannersController(ServiciosBanner serviciosBanner) {
        this.serviciosBanner = serviciosBanner;
    }

    @FXML
    private MFXButton tiradaSingle;

    @FXML
    private MFXButton tiradaMulti;

    @FXML
    private Label precioSingle;

    @FXML
    private Label precioMulti;

    @FXML
    private MFXButton botonPool;

    @FXML
    private MFXButton botonAyuda;

    @FXML
    private MFXComboBox<String> comboBox;

    @FXML
    private Label labelMonedas;

    @FXML
    private ImageView imagenBanner;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox.getItems().addAll(serviciosBanner.verBanners().stream().map(Banner::getNombre).toArray(String[]::new));
        comboBox.getSelectionModel().selectFirst();
        try (var inputStream = getClass().getResourceAsStream(ConstantesPantallas.BANNER_VENTI_PNG)) {
            assert inputStream != null;
            Image logoImage = new Image(inputStream);
            imagenBanner.setImage(logoImage);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void principalCargado() {
        labelMonedas.setText(ConstantesPantallas.MONEDAS_LABEL + this.getMainController().getUsuario().getCantidadMonedas() + ConstantesPantallas.CARACTER_VACIO);
    }

    public void tiradaSingle(ActionEvent actionEvent) {
        if (this.getMainController().getUsuario().getCantidadMonedas() >= 160) {
            String nombreBanner = comboBox.getValue();
            Personaje p = serviciosBanner.tiradaSingle(nombreBanner);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(ConstantesPantallas.TIRADA_DE_BANNER);
            alert.setHeaderText(ConstantesPantallas.TIRADA_DE_BANNER_EXCLAMACION);
            alert.setContentText(ConstantesPantallas.HAS_OBTENIDO_EL_PERSONAJE + p.getNombre() + ConstantesPantallas.PARENTESIS + p.getRareza() + ConstantesPantallas.ESTRELLA + ConstantesPantallas.EXCLAMACION);
            alert.showAndWait();
            Usuario usu = this.getMainController().getUsuario();
            usu.getInventario().add(p);
            usu.setCantidadMonedas(this.getMainController().getUsuario().getCantidadMonedas() - 160);
            this.getMainController().setUsuario(usu);
            labelMonedas.setText(ConstantesPantallas.MONEDAS_LABEL + this.getMainController().getUsuario().getCantidadMonedas() + ConstantesPantallas.CARACTER_VACIO);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(ConstantesPantallas.TIRADA_DE_BANNER);
            alert.setHeaderText(ConstantesPantallas.NO_TIENES_SUFICIENTES_MONEDAS);
            alert.setContentText(ConstantesPantallas.NECESITAS_160_MONEDAS_PARA_TIRAR_UN_BANNER);
            alert.showAndWait();
        }
    }

    public void tiradaMulti(ActionEvent actionEvent) {
        if (this.getMainController().getUsuario().getCantidadMonedas() >= 1600) {
            String nombreBanner = comboBox.getValue();
            List<Personaje> personajes = serviciosBanner.tiradaMulti(nombreBanner);
            StringBuilder sb = new StringBuilder();
            for (Personaje personaje : personajes) {
                sb.append(personaje.getNombre()).append(ConstantesPantallas.PARENTESIS).append(personaje.getRareza()).append(ConstantesPantallas.ESTRELLA).append(ConstantesPantallas.BARRA_N);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(ConstantesPantallas.TIRADA_DE_BANNER);
            alert.setHeaderText(ConstantesPantallas.TIRADA_DE_BANNER_EXCLAMACION);
            alert.setContentText(ConstantesPantallas.HAS_OBTENIDO_LOS_PERSONAJES + sb);
            alert.showAndWait();
            Usuario usu = this.getMainController().getUsuario();
            usu.getInventario().addAll(personajes);
            usu.setCantidadMonedas(this.getMainController().getUsuario().getCantidadMonedas() - 1600);
            this.getMainController().setUsuario(usu);
            labelMonedas.setText(ConstantesPantallas.MONEDAS_LABEL + this.getMainController().getUsuario().getCantidadMonedas() + ConstantesPantallas.CARACTER_VACIO);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(ConstantesPantallas.TIRADA_DE_BANNER);
            alert.setHeaderText(ConstantesPantallas.NO_TIENES_SUFICIENTES_MONEDAS);
            alert.setContentText(ConstantesPantallas.NECESITAS_1600_MONEDAS_PARA_HACER_UNA_TIRADA_MULTI);
            alert.showAndWait();
        }
    }

    public void volverAtras(MouseEvent mouseEvent) {
        this.getMainController().saveUsuario();
        this.getMainController().cargarInicio();
    }

    public void cambiarBanner(ActionEvent actionEvent) {
        String nombreBanner = comboBox.getValue();
        Banner b = serviciosBanner.verBanners().stream().filter(banner -> banner.getNombre().equals(nombreBanner)).findFirst().get();
        String nombrePersonaje = b.getPersonajeDestacado().getNombre().replace(ConstantesPantallas.ESPACIO, ConstantesPantallas.CARACTER_VACIO);
        String rutaImagen = ConstantesPantallas.RUTA_GENERICA_BANNER + nombrePersonaje + ConstantesPantallas.PNG;
        try (var inputStream = getClass().getResourceAsStream(rutaImagen)) {
            assert inputStream != null;
            Image logoImage = new Image(inputStream);
            imagenBanner.setImage(logoImage);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void mostrarAyuda(ActionEvent actionEvent) {
        String nombreBanner = comboBox.getValue();
        Banner b = serviciosBanner.verBanners().stream().filter(banner -> banner.getNombre().equals(nombreBanner)).findFirst().get();
        double probabilidadDestacado;
        if (b.getPity() == 89) {
            probabilidadDestacado = 100;
        } else if (b.getPity() > 72) {
            probabilidadDestacado = (b.getPity() - 72) + 0.6;
        } else {
            probabilidadDestacado = 0.6;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(ConstantesPantallas.AYUDA);
        alert.setHeaderText(ConstantesPantallas.INFORMACIÓN_ACERCA_DEL_BANNER);
        alert.setContentText(ConstantesPantallas.PERSONAJE_DESTACADO + b.getPersonajeDestacado().getNombre() + ConstantesPantallas.BARRA_N +
                ConstantesPantallas.PROBABILIDADES_DE_APARICIÓN +
                ConstantesPantallas.PERSONAJES_DE_5_ESTRELLAS + probabilidadDestacado + ConstantesPantallas.PORCENTAJE_BARRA_N +
                ConstantesPantallas.PERSONAJES_DE_4_ESTRELLAS + (100 - probabilidadDestacado) + ConstantesPantallas.PORCENTAJE_BARRA_N +
                ConstantesPantallas.CADA_BANNER_GARANTIZA_UN_PERSONAJE_DE_5_CADA_90_TIRADAS +
                ConstantesPantallas.NÚMERO_DE_TIRADAS_DESDE_EL_ÚLTIMO_PERSONAJE_DESTACADO + b.getPity() + ConstantesPantallas.BARRA_N +
                ConstantesPantallas.TODAS_LAS_ESTADÍSTICAS_DE_ESTA_PESTAÑA_PERTENECEN_ÚNICAMENTE_AL_BANNER_SELECCIONADO_ACTUALMENTE +
                ConstantesPantallas.PARA_CONSEGUIR_MÁS_MONEDAS_VE_A_LA_PANTALLA_FARMEO_EN_EL_MENÚ_PRINCIPAL);
        alert.showAndWait();
    }
}
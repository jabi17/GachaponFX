package ui.pantallas.pantallatienda;

import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.extern.log4j.Log4j2;
import modelo.Personaje;
import modelo.Usuario;
import servicios.ServiciosTienda;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Log4j2
public class TiendaController extends BasePantallaController implements Initializable {

    private final ServiciosTienda serviciosTienda;

    @Inject
    public TiendaController(ServiciosTienda serviciosTienda) {
        this.serviciosTienda = serviciosTienda;
    }

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label4;

    @FXML
    private Label label5;

    @FXML
    private Label label6;

    @FXML
    private ImageView icon1;

    @FXML
    private ImageView icon2;

    @FXML
    private ImageView icon3;

    @FXML
    private ImageView icon4;

    @FXML
    private ImageView icon5;

    @FXML
    private ImageView icon6;

    @FXML
    private Label labelMonedas;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void principalCargado() {
        LocalDate tiendaRefresh = this.getMainController().getUsuario().getTiendaRefresh();
        List<Personaje> tienda;
        if (tiendaRefresh.isBefore(LocalDate.now())) {
            tienda = serviciosTienda.generarTienda();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(ConstantesPantallas.TIENDA);
            alert.setHeaderText(ConstantesPantallas.TIENDA_ACTUALIZADA);
            alert.setContentText(ConstantesPantallas.LA_TIENDA_HA_SIDO_ACTUALIZADA);
            alert.showAndWait();
            Usuario usu = this.getMainController().getUsuario();
            usu.setTienda(tienda);
            usu.setTiendaRefresh(LocalDate.now());
            this.getMainController().setUsuario(usu);
            this.getMainController().saveUsuario();
        } else {
            tienda = this.getMainController().getUsuario().getTienda();
        }
        label1.setText(tienda.get(0).getNombre());
        label2.setText(tienda.get(1).getNombre());
        label3.setText(tienda.get(2).getNombre());
        label4.setText(tienda.get(3).getNombre());
        label5.setText(tienda.get(4).getNombre());
        label6.setText(tienda.get(5).getNombre());

        generarIcono(tienda, label1.getText(), icon1);
        generarIcono(tienda, label2.getText(), icon2);
        generarIcono(tienda, label3.getText(), icon3);
        generarIcono(tienda, label4.getText(), icon4);
        generarIcono(tienda, label5.getText(), icon5);
        generarIcono(tienda, label6.getText(), icon6);

        labelMonedas.setText(ConstantesPantallas.MONEDAS_LABEL + this.getMainController().getUsuario().getCantidadMonedas() + ConstantesPantallas.CARACTER_VACIO);
    }

    private void generarIcono(List<Personaje> tienda, String nombre, ImageView icon) {
        String rutaImagen;
        rutaImagen = tienda.stream().filter(p -> p.getNombre().equals(nombre)).findFirst().get().getIcono();
        try (var inputStream = getClass().getResourceAsStream(rutaImagen)) {
            assert inputStream != null;
            Image logoImage = new Image(inputStream);
            icon.setImage(logoImage);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void volverAtras(MouseEvent mouseEvent) {
        this.getMainController().cargarInicio();
    }

    public void comprarElemento1(ActionEvent actionEvent) {
        List<Personaje> tienda = this.getMainController().getUsuario().getTienda();
        Personaje personaje = tienda.get(0);
        comprarPersonaje(personaje);
    }

    public void comprarElemento2(ActionEvent actionEvent) {
        List<Personaje> tienda = this.getMainController().getUsuario().getTienda();
        Personaje personaje = tienda.get(1);
        comprarPersonaje(personaje);
    }

    public void comprarElemento3(ActionEvent actionEvent) {
        List<Personaje> tienda = this.getMainController().getUsuario().getTienda();
        Personaje personaje = tienda.get(2);
        comprarPersonaje(personaje);
    }

    public void comprarElemento4(ActionEvent actionEvent) {
        List<Personaje> tienda = this.getMainController().getUsuario().getTienda();
        Personaje personaje = tienda.get(3);
        comprarPersonaje(personaje);
    }

    public void comprarElemento5(ActionEvent actionEvent) {
        List<Personaje> tienda = this.getMainController().getUsuario().getTienda();
        Personaje personaje = tienda.get(4);
        comprarPersonaje(personaje);
    }

    public void comprarElemento6(ActionEvent actionEvent) {
        List<Personaje> tienda = this.getMainController().getUsuario().getTienda();
        Personaje personaje = tienda.get(5);
        comprarPersonaje(personaje);
    }

    private void comprarPersonaje(Personaje personaje) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle(ConstantesPantallas.COMPRAR);
        a.setHeaderText(ConstantesPantallas.DESEA_COMPRAR_EL_PERSONAJE + personaje.getNombre() + ConstantesPantallas.INTERROGACION);
        a.setContentText(ConstantesPantallas.EL_PERSONAJE + personaje.getNombre() + ConstantesPantallas.CUESTA + personaje.getPrecio() + ConstantesPantallas.MONEDAS_PUNTO);
        Optional<ButtonType> result = a.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (personaje.getPrecio() <= this.getMainController().getUsuario().getCantidadMonedas()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(ConstantesPantallas.TIENDA);
                alert.setHeaderText(ConstantesPantallas.ELEMENTO_COMPRADO);
                alert.setContentText(ConstantesPantallas.HAS_COMPRADO_EL_PERSONAJE + personaje.getNombre());
                alert.showAndWait();
                Usuario usu = this.getMainController().getUsuario();
                List<Personaje> personajes = usu.getInventario();
                personajes.add(personaje);
                usu.setInventario(personajes);
                usu.setCantidadMonedas(usu.getCantidadMonedas() - personaje.getPrecio());
                this.getMainController().setUsuario(usu);
                this.getMainController().saveUsuario();
                labelMonedas.setText(ConstantesPantallas.MONEDAS_LABEL + this.getMainController().getUsuario().getCantidadMonedas() + ConstantesPantallas.ESPACIO);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(ConstantesPantallas.TIENDA);
                alert.setHeaderText(ConstantesPantallas.NO_TIENES_SUFICIENTES_MONEDAS);
                alert.setContentText(ConstantesPantallas.NO_TIENES_SUFICIENTES_MONEDAS_PARA_COMPRAR_ESTE_PERSONAJE);
                alert.showAndWait();
            }
        }
    }
}
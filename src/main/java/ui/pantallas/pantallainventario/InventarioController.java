package ui.pantallas.pantallainventario;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import modelo.PersonajeInventario;
import servicios.ServiciosUsuario;
import ui.pantallas.common.BasePantallaController;
import ui.pantallas.common.ConstantesPantallas;

import java.net.URL;
import java.util.ResourceBundle;

public class InventarioController extends BasePantallaController implements Initializable {
    private final ServiciosUsuario serviciosUsuario;

    @Inject
    public InventarioController(ServiciosUsuario serviciosUsuario) {
        this.serviciosUsuario = serviciosUsuario;
    }

    @FXML
    private TableView<PersonajeInventario> tablaInventario;

    @FXML
    private TableColumn<PersonajeInventario, String> columnaNombre;

    @FXML
    private TableColumn<PersonajeInventario, String> columnaRareza;

    @FXML
    private TableColumn<PersonajeInventario, Integer> columnaCantidad;

    @FXML
    private Label valorInventario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<PersonajeInventario> personajes = FXCollections.observableArrayList(serviciosUsuario.verInventario());
        tablaInventario.setItems(personajes);
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.NOMBRE));
        columnaRareza.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.RAREZA));
        columnaCantidad.setCellValueFactory(new PropertyValueFactory<>(ConstantesPantallas.CANTIDAD));
        valorInventario.setText(ConstantesPantallas.VALOR_ACTUAL_DEL_INVENTARIO + serviciosUsuario.getValorInventario());
    }

    public void volverAtras(MouseEvent mouseEvent) {
        this.getMainController().cargarInicio();
    }
}
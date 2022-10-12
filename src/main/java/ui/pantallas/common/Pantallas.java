package ui.pantallas.common;

public enum Pantallas {
    PANTALLAMAIN("/fxml/PantallaMain.fxml"),
    PANTALLALOGIN("/fxml/Login.fxml"),
    PANTALLABANNERS("/fxml/Banners.fxml"),
    PANTALLATIENDA("/fxml/Tienda.fxml"),
    PANTALLAINICIO("/fxml/Inicio.fxml"),
    PANTALLAFARMEO("/fxml/Farmeo.fxml"),
    PANTALLAINVENTARIO("/fxml/Inventario.fxml");

    private final String ruta;

    Pantallas(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }
}

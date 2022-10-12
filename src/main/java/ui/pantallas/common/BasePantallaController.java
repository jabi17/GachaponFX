package ui.pantallas.common;

import ui.pantallas.pantallamain.MainController;

public class BasePantallaController {
    private MainController mainController;

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void principalCargado() {

    }
}

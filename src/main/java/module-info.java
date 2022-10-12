module GachaponFX {
    requires lombok;
    requires jakarta.jakartaee.web.api;
    requires com.fasterxml.jackson.dataformat.yaml;
    requires com.fasterxml.jackson.databind;
    requires com.google.gson;
    requires org.apache.logging.log4j;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires MaterialFX;

    exports ui to javafx.graphics;
    exports modelo;
    exports data;
    exports ui.pantallas.pantallabanners;
    exports servicios;
    exports di;
    exports config;
    exports ui.pantallas.pantallafarmeo;
    exports ui.pantallas.pantallalogin;
    exports ui.pantallas.pantallatienda;
    exports ui.pantallas.pantallamain;
    exports ui.pantallas.common;
    exports ui.pantallas.pantallainicio;
    exports ui.pantallas.pantallainventario;

    opens ui;
    opens config;
    opens modelo;
    opens ui.pantallas.common;
    opens ui.pantallas.pantallamain;
    opens ui.pantallas.pantallalogin;
    opens ui.pantallas.pantallainicio;
    opens ui.pantallas.pantallabanners;
    opens ui.pantallas.pantallafarmeo;
    opens ui.pantallas.pantallatienda;
    opens ui.pantallas.pantallainventario;
}
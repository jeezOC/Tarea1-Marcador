module cr.ac.una.marcador {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphicsEmpty;
    requires java.logging;

    opens cr.ac.una.marcador.controller to javafx.fxml;
    exports cr.ac.una.marcador;
//    exports cr.ac.una.marcador.controller;

    requires com.jfoenix;
    requires javaee.web.api;
}

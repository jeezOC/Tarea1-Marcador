module cr.ac.una.marcador {
    
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphicsEmpty;
    requires java.logging;
    requires com.jfoenix;
    requires javaee.web.api;
    exports cr.ac.una.wstarea;
    
    opens cr.ac.una.marcador.controller to javafx.fxml;
    exports cr.ac.una.marcador;
//    exports cr.ac.una.marcador.controller;
    requires java.xml.ws;
    requires java.jws;
  
}

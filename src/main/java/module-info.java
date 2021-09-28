module cr.ac.una.marcador {
    
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphicsEmpty;
    requires java.logging;
    requires com.jfoenix;
    requires javaee.web.api;
    requires java.xml.ws;
    requires java.jws;
    requires webcam.capture;
    requires java.desktop;
     
    opens cr.ac.una.relojunaws;
    opens cr.ac.una.marcador to javafx.fxml, javafx.graphics;
    opens cr.ac.una.marcador.controller to javafx.fxml, javafx.controls, com.jfoenix;
    exports cr.ac.una.marcador;
    requires java.xml.bind;


}

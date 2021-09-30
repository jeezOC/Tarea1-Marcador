module cr.ac.una.marcador {
    
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphicsEmpty;
    requires java.logging;
    requires com.jfoenix;
    requires webcam.capture;
    requires java.desktop;
     
    //ws
    requires javax.jws;
    requires java.xml.ws;
    requires java.xml.bind;
    
    opens cr.ac.una.relojunaws;
    opens cr.ac.una.marcador to javafx.fxml, javafx.graphics;
    opens cr.ac.una.marcador.controller to javafx.fxml, javafx.controls, com.jfoenix;
    exports cr.ac.una.marcador;


}

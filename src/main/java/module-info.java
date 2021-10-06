module cr.ac.una.marcador {
    
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphicsEmpty;
    requires java.logging;
    requires com.jfoenix;
    requires webcam.capture;
    requires java.desktop;
    requires AnimateFX;
    requires poi.ooxml;
    //ws
    requires javax.jws;
    requires java.xml.ws;
    requires java.xml.bind;
    requires java.base;
    requires poi;
    
    requires jasperreports;
    
    
    opens cr.ac.una.relojunaws;
    opens cr.ac.una.marcador to javafx.fxml, javafx.graphics;
    opens cr.ac.una.marcador.controller to javafx.fxml, javafx.controls,com.jfoenix,poi.ooxml,jasperreports;
    
    exports cr.ac.una.marcador;


}

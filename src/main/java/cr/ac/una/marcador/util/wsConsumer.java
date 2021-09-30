package cr.ac.una.marcador.util;

import cr.ac.una.relojunaws.Respuesta;

public class wsConsumer {
    private static wsConsumer INSTANCE = null;

    cr.ac.una.relojunaws.WS service = new cr.ac.una.relojunaws.WS();
    cr.ac.una.relojunaws.Relojunaws port = service.getRelojunawsPort();
    cr.ac.una.relojunaws.Respuesta respuesta = new cr.ac.una.relojunaws.Respuesta();
    private wsConsumer() {
    }

    private static void createInstance() {
        if (INSTANCE == null) {
            synchronized (FlowController.class) {
                if (INSTANCE == null) {
                    INSTANCE = new wsConsumer();
                }
            }
        }
    }

    public static wsConsumer getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public cr.ac.una.relojunaws.Relojunaws getPort(){
        return port;
    };

    // TODO initialize WS operation arguments here

    // TODO process result here
    public Boolean login(String folio,String psswrd){
        respuesta = port.login(folio, psswrd);
        return respuesta.isEstado();
    }
}

package cr.ac.una.marcador.util;

import cr.ac.una.marcador.model.EmpleadoDto;
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

    public Respuesta getRespuesta(){
        return respuesta;
    }
    public cr.ac.una.relojunaws.Relojunaws getPort(){
        return port;
    };

    public Boolean validarFolioContrasena(String folio,String psswrd){
        respuesta = port.login(folio, psswrd);
        return respuesta.isEstado();
    }
    public Respuesta existeEmpleado(String folio){
        respuesta = port.existeEmpleado(folio);
        return respuesta;
    }
    public Boolean crearMarca(String folio ){
//        respuesta = port.login(folio);
        return respuesta.isEstado();
    }

    
    public EmpleadoDto buscarEmpleadoFolio(String folio){
        cr.ac.una.relojunaws.EmpleadoDto empleadoWebService =  port.buscarEmpleadoFolio(folio);
        EmpleadoDto empleado = new EmpleadoDto();
        empleado.cargarDatos(empleadoWebService);
        return empleado; 
    }
    
    
}

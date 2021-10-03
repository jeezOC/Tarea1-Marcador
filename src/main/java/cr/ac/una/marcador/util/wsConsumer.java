package cr.ac.una.marcador.util;

import cr.ac.una.marcador.model.EmpleadoDto;
import cr.ac.una.relojunaws.Respuesta;
import java.time.ZoneId;
import java.util.Base64;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

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
    
    public cr.ac.una.marcador.util.Respuesta guardarEmpleado(EmpleadoDto empleado){
        
        
        try {
            cr.ac.una.relojunaws.EmpleadoDto emp = new cr.ac.una.relojunaws.EmpleadoDto();
            emp = empDtoClientToServer(empleado);
            cr.ac.una.relojunaws.EmpleadoDto r = port.guardarEmpleado(emp);
            empleado.cargarDatos((cr.ac.una.relojunaws.EmpleadoDto) r);
            if(r==null){
            return new cr.ac.una.marcador.util.Respuesta(false, CodigoRespuesta.ERROR_INTERNO,"Error guardando el empleado","Excepcion null");
            }
            return new cr.ac.una.marcador.util.Respuesta(true, CodigoRespuesta.CORRECTO,"Empleado guardado correctamente","Ok","Emp",empleado);
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(wsConsumer.class.getName()).log(Level.SEVERE, null, ex);
            return new cr.ac.una.marcador.util.Respuesta(false, CodigoRespuesta.ERROR_INTERNO, "Ocurrio un error al guardar el empleado.", "DataObjectTransfer " + ex.getMessage());
        }
    
    }
    
    private cr.ac.una.relojunaws.EmpleadoDto empDtoClientToServer(EmpleadoDto empleado) throws DatatypeConfigurationException{
        cr.ac.una.relojunaws.EmpleadoDto empleadoDtoServidor = new cr.ac.una.relojunaws.EmpleadoDto();
        empleadoDtoServidor.setId(empleado.id);
        empleadoDtoServidor.setAdmin(empleado.admin.getValue());
        empleadoDtoServidor.setApellido(empleado.lastname.getValue());
        empleadoDtoServidor.setCedula(empleado.cedula.getValue());
        empleadoDtoServidor.setFolio(empleado.folio.getValue());
        empleadoDtoServidor.setFoto(Base64.getEncoder().encodeToString(empleado.getFoto()));
        
        //Conversion de fecha---
        GregorianCalendar gcal = GregorianCalendar.from(empleado.getNacimiento().atStartOfDay(ZoneId.systemDefault()));
        XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
        //----
        empleadoDtoServidor.setNacimiento(xcal);
        empleadoDtoServidor.setNombre(empleado.name.getValue());
        empleadoDtoServidor.setPsswr(empleado.psswr.getValue());
        
        return empleadoDtoServidor;
    }
}

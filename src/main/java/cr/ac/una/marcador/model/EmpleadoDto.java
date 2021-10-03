/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.marcador.model;

import java.time.LocalDate;
import java.time.ZoneId;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Farlen
 */
public class EmpleadoDto {
    public SimpleStringProperty folio;
    public SimpleStringProperty name;
    public SimpleStringProperty lastname;
    public SimpleStringProperty cedula;
    public SimpleBooleanProperty admin;
    public ObjectProperty<LocalDate> nacimiento;
    public SimpleStringProperty psswr;
    public ObjectProperty<byte[]> foto;
    public Long id;
    
    public Boolean modificado = false;
    
    public EmpleadoDto(){
        this.modificado = false;
        this.id = null;
        this.name = new SimpleStringProperty();
        this.psswr = new SimpleStringProperty();
        this.lastname = new SimpleStringProperty();
        this.cedula = new SimpleStringProperty();
        this.folio = new SimpleStringProperty();
        this.admin = new SimpleBooleanProperty(false);
        this.nacimiento = new SimpleObjectProperty();
        this.foto = new SimpleObjectProperty();
        
    }
    public void cargarDatos(cr.ac.una.relojunaws.EmpleadoDto empleado){
        this.folio.set(empleado.getFolio());
        this.id = empleado.getId();
        this.name.set(empleado.getNombre());
        this.cedula.set(empleado.getCedula());
        this.lastname.set(empleado.getApellido());
        this.admin.setValue(empleado.isAdmin());
        this.nacimiento.set(empleado.getNacimiento().toGregorianCalendar().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        this.psswr.set(empleado.getPsswr());
        this.foto.setValue(empleado.getFoto().getBytes());
//        this.marc = (List<MarcaDto>) new MarcaDto ((Marca) empleado.getMarca());
    }    
    
    public String getPsswr() {
        return psswr.get();
    }

    public void setPsswr(String psswr) {
        this.psswr.set(psswr);
    }
        
    public String getFolio() {
        return folio.get();
    }

    public void setFolio(String folio) {
        this.folio.set(folio);
    }

    public String getNombre() {
        return name.get();
    }

    public void setNombre(String name) {
        this.name.set(name);
    }

    public String getApellido() {
        return lastname.get();
    }

    public void setApellido(String lastname) {
        this.lastname.set(lastname);
    }

    public String getCedula() {
        return cedula.get();
    }

    public void setCedula(String cedula) {
        this.cedula.set(cedula);
    }

    public String getIsAdmin() {
        return admin.getValue() ? "S" : "N";
    }

    public void setIsAdmin(String isAdmin) {
        this.admin.setValue(isAdmin.equalsIgnoreCase("S"));
    }
    
    
/*
    public List<MarcaDto> getMarc() {
        return marc;
    }

    public void setMarc(List<MarcaDto> marc) {
        this.marc = marc;
    }
     */

    public LocalDate getNacimiento() {
        return nacimiento.get();
    }
    public void setNacimiento(LocalDate fechaNac) {
        this.nacimiento.set(fechaNac);
    }
    public byte[] getFoto() {
        return foto.get();
    }
    public void setFoto(byte[] fotoByte) {
        this.foto.set(fotoByte);
    }
    
        public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }
    
}
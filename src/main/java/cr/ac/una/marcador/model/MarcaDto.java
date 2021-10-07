/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.marcador.model;

import cr.ac.una.relojunaws.Empleado;
import cr.ac.una.relojunaws.Marca;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author Kendall
 */
public class MarcaDto {
    private Long marcaid;
    private LocalDate  marcahoraEntrada;
    private LocalDate  marcahoraSalida;
    private LocalDate marcajornada;
    private Empleado empleadoid;

//    public MarcaDto(Marca marc) {
//        this.marcaid = marc.getMarcaId();
//        this.marcahoraEntrada = marc.getMarcaHoraEntrada().toGregorianCalendar().getTime();
//        this.marcahoraSalida = marc.getMarcaHoraEntrada().toGregorianCalendar().getTime().getHours();
//        this.marcajornada = marc.getMarcaJornada().toGregorianCalendar().getTime();
//    }

    public MarcaDto(){}
    public MarcaDto(cr.ac.una.relojunaws.Marca marca) {
        this.marcaid = marca.getMarcaId();
        this.marcahoraEntrada = marca.getMarcaHoraEntrada().toGregorianCalendar().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.marcahoraSalida = marca.getMarcaHoraSalida().toGregorianCalendar().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.marcajornada = marca.getMarcaJornada().toGregorianCalendar().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.empleadoid = marca.getEmpleadoId();
    }

    public void crearMarca(LocalDate fecha, Boolean isEntrada) {
//          this.empleadoid = emp;
          if(isEntrada) this.marcahoraEntrada=fecha;
          else this.marcahoraSalida= fecha;
          
          this.marcajornada=fecha;
          
    }

    public  LocalDate  getMarcahoraEntrada() {
        return marcahoraEntrada;
    }

    public void setMarcahoraEntrada(LocalDate marcahoraEntrada) {
        this.marcahoraEntrada= marcahoraEntrada;
    }

    public  LocalDate  getMarcahoraSalida() {
        return marcahoraSalida;
    }

    public void setMarcahoraSalida(LocalDate marcahoraSalida) {
        this.marcahoraSalida= marcahoraSalida;
    }

    public LocalDate  getMarcajornada() {
        return marcajornada;
    }

    public void setMarcajornada(LocalDate marcajornada) {
        this.marcajornada= marcajornada;
    }

    public Long getMarcaid() {
        return marcaid;
    }

    public void setMarcaid(Long marcaid) {
        this.marcaid = marcaid;
    }

    public Empleado getEmpleadoid() {
        return empleadoid;
    }

    public void setEmpleadoid(Empleado empleadoid) {
        this.empleadoid = empleadoid;
    }  
    
}

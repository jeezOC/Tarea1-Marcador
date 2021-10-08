/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.marcador.model;

import cr.ac.una.relojunaws.Empleado;
import cr.ac.una.relojunaws.Marca;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private LocalDateTime  marcahoraEntrada;
    private LocalDateTime  marcahoraSalida;
    public LocalDate marcajornada;
    private Empleado empleadoid;

//    public MarcaDto(Marca marc) {
//        this.marcaid = marc.getMarcaId();
//        this.marcahoraEntrada = marc.getMarcaHoraEntrada().toGregorianCalendar().getTime();
//        this.marcahoraSalida = marc.getMarcaHoraEntrada().toGregorianCalendar().getTime().getHours();
//        this.marcajornada = marc.getMarcaJornada().toGregorianCalendar().getTime();
//    }

    public MarcaDto(){}
    public MarcaDto(cr.ac.una.relojunaws.MarcaDto marcaDto) {
        this.marcaid = marcaDto.getMarcaid();
        if(marcaDto.getMarcahoraEntrada()!=null)this.marcahoraEntrada = marcaDto.getMarcahoraEntrada().toGregorianCalendar().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        if(marcaDto.getMarcahoraSalida()!=null)this.marcahoraSalida = marcaDto.getMarcahoraSalida().toGregorianCalendar().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.marcajornada = marcaDto.getMarcajornada().toGregorianCalendar().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.empleadoid = marcaDto.getEmpleadoid();
    }

    public void crearMarca(LocalDateTime fecha, Boolean isEntrada) {
//          this.empleadoid = emp;
        if(isEntrada) {
            this.marcahoraEntrada=fecha;
        }
        else{
            this.marcahoraSalida= fecha;
        }
        this.marcajornada=fecha.toLocalDate();
          
    }

    public  LocalDateTime  getMarcahoraEntrada() {
        return marcahoraEntrada;
    }

    public void setMarcahoraEntrada(LocalDateTime marcahoraEntrada) {
        this.marcahoraEntrada= marcahoraEntrada;
    }

    public  LocalDateTime  getMarcahoraSalida() {
        return marcahoraSalida;
    }

    public void setMarcahoraSalida(LocalDateTime marcahoraSalida) {
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

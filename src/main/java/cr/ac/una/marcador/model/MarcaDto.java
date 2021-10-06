/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.marcador.model;

import cr.ac.una.relojunaws.Marca;
import java.util.Date;

/**
 *
 * @author Kendall
 */
public class MarcaDto {
    private Date horaEntrada;
    private Date horaSalida;
    private Date jornada;
    private Long id;
     private Long pkMarca;

    public MarcaDto(Marca marc) {
        this.id = marc.getMarcaId();
        if(marc.getMarcaHoraEntrada() != null && marc.getMarcaJornada() != null && marc.getMarcaHoraSalida() != null ){
        this.horaEntrada = marc.getMarcaHoraEntrada().toGregorianCalendar().getTime();
        this.horaSalida = marc.getMarcaHoraEntrada().toGregorianCalendar().getTime();
        this.jornada = marc.getMarcaJornada().toGregorianCalendar().getTime();
        }
    }

    public MarcaDto(Date fecha, Long id_emp, Boolean salida) {
          this.id = id_emp;
          if(salida) this.horaSalida = fecha;
          else this.horaEntrada = fecha;
    }

    public Long getPkMarca() {
        return pkMarca;
    }

    public void setPkMarca(Long pkMarca) {
        this.pkMarca = pkMarca;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Date horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Date getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Date horaSalida) {
        this.horaSalida = horaSalida;
    }

    public Date getJornada() {
        return jornada;
    }

    public void setJornada(Date jornada) {
        this.jornada = jornada;
    }   
    
}

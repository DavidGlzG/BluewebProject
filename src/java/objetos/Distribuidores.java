/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import java.io.Serializable;

/**
 *
 * @author admin
 */
public class Distribuidores {
    
    private int idDistribuidor;
    private String claveDistribuidor;
    private String nombre;
    private int activo;
    private String fechaAlta;
    private String fechaBaja;
    private String fechaServidor;
    private int idUsuarioModifica;

    //<editor-fold defaultstate="collapsed" desc="getset">
    public int getIdDistribuidor() {
        return idDistribuidor;
    }
    
    public void setIdDistribuidor(int idDistribuidor) {
        this.idDistribuidor = idDistribuidor;
    }
    
    public String getClaveDistribuidor() {
        return claveDistribuidor;
    }
    
    public void setClaveDistribuidor(String claveDistribuidor) {
        this.claveDistribuidor = claveDistribuidor;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int getActivo() {
        return activo;
    }
    
    public void setActivo(int activo) {
        this.activo = activo;
    }
    
    public String getFechaAlta() {
        return fechaAlta;
    }
    
    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    
    public String getFechaBaja() {
        return fechaBaja;
    }
    
    public void setFechaBaja(String fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
    
    public String getFechaServidor() {
        return fechaServidor;
    }
    
    public void setFechaServidor(String fechaServidor) {
        this.fechaServidor = fechaServidor;
    }
    
    public int getIdUsuarioModifica() {
        return idUsuarioModifica;
    }
    
    public void setIdUsuarioModifica(int idUsuarioModifica) {
        this.idUsuarioModifica = idUsuarioModifica;
    }
//</editor-fold>
    
}

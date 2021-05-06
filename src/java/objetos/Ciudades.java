/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

/**
 *
 * @author David
 */
public class Ciudades {
    private int idCiudad;
    private String descripcion;
    private String codigo;
    private int lada;
    private int activo;
    private String fecha_alta;
    private String fecha_baja;
    private String fecha_servidor;

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getLada() {
        return lada;
    }

    public void setLada(int lada) {
        this.lada = lada;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public String getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(String fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public String getFecha_baja() {
        return fecha_baja;
    }

    public void setFecha_baja(String fecha_baja) {
        this.fecha_baja = fecha_baja;
    }

    public String getFecha_servidor() {
        return fecha_servidor;
    }

    public void setFecha_servidor(String fecha_servidor) {
        this.fecha_servidor = fecha_servidor;
    }
    
    
}

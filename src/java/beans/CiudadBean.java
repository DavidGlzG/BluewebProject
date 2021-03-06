/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelos.CiudadesModelo;
import objetos.Ciudades;
import org.primefaces.model.DualListModel;
import respuestas.RespuestaCiudades;

/**
 *
 * @author David
 */
@ManagedBean
@ViewScoped
public class CiudadBean implements Serializable {

    private Ciudades ciudad;
    private List<Ciudades> listaCiudades = new ArrayList<Ciudades>();

    /**
     * Metodo constructor donde se inicia la lista de las ciudades.
     */
    public CiudadBean() {
        listarCiudades();
    }

    /**
     * Metodo para traer todas las ciudades de la base de datos y almacenarlos en un lista.
     */
    public void listarCiudades() {
        try {
            CiudadesModelo modelo = new CiudadesModelo();
            RespuestaCiudades rs = modelo.listarCiudades();
            if (rs.getRespuesta().getIdRespuesta() == 0) {
                listaCiudades = rs.getListaCiudades();
            } else {
                System.out.println(rs.getRespuesta().getMsgRespuesta());
            }
        } catch (Exception e) {
            Logger.getLogger(CiudadBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

//<editor-fold defaultstate="collapsed" desc="Get Set">
    public Ciudades getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudades ciudad) {
        this.ciudad = ciudad;
    }

    public List<Ciudades> getListaCiudades() {
        return listaCiudades;
    }

    public void setListaCiudades(List<Ciudades> listaCiudades) {
        this.listaCiudades = listaCiudades;
    }

//</editor-fold>
   
}

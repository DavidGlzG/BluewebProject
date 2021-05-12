/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utils.TraeDatoSesion;

/**
 *
 * @author David Gonzalez
 */
@ManagedBean
@ViewScoped
public class InicioBean implements Serializable {

    private String nombreUsuario;

    /**
     * Metodo constructor donde se asigna el valor del nombre del usuario almacenado en las
     * variables de sesión.
     */
    public InicioBean() {
        nombreUsuario = TraeDatoSesion.traerNombreUsuario();
    }

//<editor-fold defaultstate="collapsed" desc="Get Set">
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
//</editor-fold>

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;


import entities.SAplicaciones;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import objetos.Usuarios;


/**
 *
 * @author antuan.yanez
 */

public class TraeDatoSesion {

    public static int traerIdUsuario() {
        int usuario = 0;
        usuario = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idUsuario");
        return usuario;
    }
    
    public static int traerIdUsuarioPerfil() {
        int usuario = 0;
        usuario = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idPerfil");
        return usuario;
    }

    public static String traerUsuario() {
        String usuario = "";
        usuario = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return usuario;
    }

    public static String traerNombreUsuario() {
        String usuario = "";
        usuario = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("nombreUsuario");
        return usuario;
    }
    
    public static List<SAplicaciones> traerListaMenu(){
        List<SAplicaciones> listaAplicaciones = new ArrayList<>();
        listaAplicaciones = (List<SAplicaciones>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listaAplicaciones");
        return listaAplicaciones;
    }
    
    public static Usuarios traerUsuarioObjeto() {
        Usuarios usuario;
        usuario = (Usuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("UsuarioObjeto");
        return usuario;
    }
    

}

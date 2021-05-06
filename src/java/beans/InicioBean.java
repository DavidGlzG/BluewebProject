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
    private static int indice;

    public InicioBean() {
        nombreUsuario = TraeDatoSesion.traerNombreUsuario();
        enlaceCatalogos();
    }

    public void almacenarIndice(int indice){
        this.indice = indice;
    }
    
    public String enlaceCatalogos() {
        
        String ruta;
        switch(indice){
            case 1:
                ruta = "/inicio/imagenes.xhtml";
                break;
            case 2:
                ruta = "/catalogo/clientes.xhtml";
                break;
            case 3:
                ruta = "/catalogo/distribuidor.xhtml";
                break;
            default:
                ruta = "/inicio/prueba.xhtml";
                        
        }
        return ruta;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public static int getIndice() {
        return indice;
    }

    public static void setIndice(int indice) {
        InicioBean.indice = indice;
    }

}

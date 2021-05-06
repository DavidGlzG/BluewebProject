/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import objetos.Usuarios;

/**
 *
 * @author David Gonzalez
 */
@ManagedBean
@SessionScoped
public class IndexBean {
    
    private Usuarios usuario;

    public IndexBean() {
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
    
    
    
}

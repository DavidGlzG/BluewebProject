/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controllers.SAplicacionesJpaController;
import controllers.SUsuariosJpaController;
import entities.SAplicaciones;
import entities.SUsuarios;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import modelos.ClientesModelo;
import modelos.UsuariosModelo;
import objetos.Usuarios;
import respuestas.RespuestaUsuarios;
import utils.TraeDatoSesion;

/**
 *
 * @author David Gonzalez
 */
@ManagedBean
@SessionScoped
public class UsuariosBean implements Serializable {

    private Usuarios usuario = new Usuarios();
    private List<SUsuarios> listaUsuarios;

    @PostConstruct
    public void init() {
        listarUsuarios();
    }

    public void listarUsuarios() {
        SUsuariosJpaController modelo = new SUsuariosJpaController();
        listaUsuarios = modelo.findSUsuariosEntities();
    }

    /**
     * Valida el usuario en el login. En caso de ser correcto almacena los datos
     * del usuario en variables de sesion, de lo contrario muestra mensaje de
     * error.
     */
    public void validarUsuario() {

        try {
            UsuariosModelo modelo = new UsuariosModelo();
            RespuestaUsuarios rs = modelo.validarUsuarios(usuario);
            SAplicacionesJpaController modeloAplicaciones = new SAplicacionesJpaController();
            List<SAplicaciones> listaAplicaciones = new ArrayList<>();

            FacesContext context = FacesContext.getCurrentInstance();
            if (rs.getRespuesta().getIdRespuesta() == 0) {
                usuario = rs.getUsuario();
                listaAplicaciones = modeloAplicaciones.traerListaAplicaciones(usuario.getUsuario());

                context.getExternalContext().getSessionMap().put("idUsuario", usuario.getIdUsuario());
                context.getExternalContext().getSessionMap().put("idPerfil", usuario.getIdPerfil());
                context.getExternalContext().getSessionMap().put("usuario", usuario.getUsuario());
                context.getExternalContext().getSessionMap().put("nombreUsuario", usuario.getNombreUsuarios());
                context.getExternalContext().getSessionMap().put("UsuarioObjeto", usuario);
                context.getExternalContext().getSessionMap().put("listaAplicaciones", listaAplicaciones);
                usuario = new Usuarios();
                String ctxPath = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getContextPath();
                context.getExternalContext().redirect(ctxPath + "/faces/inicio/inicio.xhtml");

            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "El usuario o la contraseña es incorrecta", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                System.out.println(rs.getRespuesta().getMsgRespuesta());
            }
        } catch (Exception e) {
            Logger.getLogger(ClientesModelo.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Verifica que una sesion se encuentre activa. Si el usuario es igual a
     * nullo entonces redirige a una pagina de acceso invalido.
     */
    public void verificarSesion() {
        try {
            usuario = TraeDatoSesion.traerUsuarioObjeto();
            FacesContext context = FacesContext.getCurrentInstance();
            if (usuario == null) {
                context.getExternalContext().redirect("./../invalido.xhtml");
            }
        } catch (Exception e) {
            Logger.getLogger(ClientesModelo.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Metodo para invalidar las variables de sesion y redirige a la pagina
     * donde esta el login
     */
    public void salirSesion() {
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
        try {
            ((HttpSession) ctx.getSession(false)).invalidate();
            ctx.redirect(ctxPath + "/faces/index.xhtml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

//<editor-fold defaultstate="collapsed" desc="Get Set">
    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public List<SUsuarios> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<SUsuarios> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
//</editor-fold>
}

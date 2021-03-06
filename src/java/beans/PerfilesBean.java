/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controllers.SAccesosJpaController;
import controllers.SPerfilesAccesosJpaController;
import controllers.SPerfilesJpaController;
import controllers.exceptions.IllegalOrphanException;
import controllers.exceptions.NonexistentEntityException;
import entities.SAccesos;
import entities.SPerfiles;
import entities.SPerfilesAccesos;
import entities.SPerfilesAccesosPK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DualListModel;
import utils.TraeDatoSesion;

/**
 *
 * @author David Gonzalez
 */
@ManagedBean
@ViewScoped
public class PerfilesBean implements Serializable {

    private SPerfiles perfiles = new SPerfiles();
    private Integer idPerfil;
    private List<SPerfiles> listaPerfiles;
    private List<SAccesos> listaAccesosDisponibles;
    private List<SAccesos> listaAccesosAsignados = new ArrayList<>();
    private DualListModel<SAccesos> dualListAccesos;

    /**
     * Metodo constructor donde se inicia la lista de perfiles y accesos además 
     * de inicializar la dualListAccesos.
     */
    public PerfilesBean() {
        listarPerfiles();
        listarAccesos();
        dualListAccesos = new DualListModel<>(listaAccesosDisponibles, listaAccesosAsignados);
    }

    /**
     * Metodo para traer todos los perfiles de la base de datos y alamcenarlos en una lista.
     */
    public void listarPerfiles() {
        SPerfilesJpaController modelo = new SPerfilesJpaController();
        listaPerfiles = modelo.findSPerfilesEntities();
    }

    /**
     * Metodo para traer todos los accesos de la base de datos y alamcenarlos en una lista.
     */
    public void listarAccesos() {
        SAccesosJpaController modelo = new SAccesosJpaController();
        listaAccesosDisponibles = modelo.findSAccesosEntities();
    }

    /**
     * Metodo para registar un perfil nuevo y también registrar sus accesos asigandos.
     */
    public void registrarPerfil() {
        List<SAccesos> listaIdAccesosString = new ArrayList<>();
        SPerfilesAccesos perfilesAccesos = new SPerfilesAccesos();

        SPerfilesJpaController modeloPerfiles = new SPerfilesJpaController();
        SPerfilesAccesosJpaController modeloPerfilesAccesos = new SPerfilesAccesosJpaController();

        perfiles.setActivo(true);
        perfiles.setFechaAlta(new Date());
        perfiles.setFechaServidor(new Date());
        perfiles.setIdUsuarioModifica(TraeDatoSesion.traerIdUsuario());
        modeloPerfiles.create(perfiles);
        listaIdAccesosString = dualListAccesos.getTarget();

        SAccesos objAccesos = new SAccesos();
        try {
            for (Object listaId : listaIdAccesosString) {

                int id = Integer.parseInt(listaId.toString());
                objAccesos.setIdAcceso(id);
                perfilesAccesos.setSPerfiles(perfiles);
                perfilesAccesos.setSAccesos(objAccesos);
                perfilesAccesos.setFechaServidor(new Date());
                perfilesAccesos.setIdUsuarioModifica(TraeDatoSesion.traerIdUsuario());
                modeloPerfilesAccesos.create(perfilesAccesos);
            }
            FacesMessage msg = new FacesMessage("Se registro correctamente.", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            Logger.getLogger(PerfilesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        perfiles = new SPerfiles();
        listarPerfiles();
        listarAccesos();
        listaAccesosAsignados = new ArrayList<>();
        dualListAccesos = new DualListModel<>(listaAccesosDisponibles, listaAccesosAsignados);
    }

    /**
     * Metodo para eliminar un perfil y sus accesos asignados.
     * @param perfil Es el perfil de donde se obtiene el id a eliminar.
     */
    public void eliminarPerfil(SPerfiles perfil) {
        SPerfilesJpaController modeloPerfiles = new SPerfilesJpaController();
        SPerfilesAccesosJpaController modeloPerfilesAccesos = new SPerfilesAccesosJpaController();
        List<SPerfilesAccesos> listaPerfilesAccesos = new ArrayList<>();

        listaPerfilesAccesos = modeloPerfilesAccesos.traerAccesosByPerfil(perfil);
        try {
            for (SPerfilesAccesos lista : listaPerfilesAccesos) {
                modeloPerfilesAccesos.destroy(lista.getSPerfilesAccesosPK());
            }
            try {
                modeloPerfiles.destroy(perfil.getIdPerfil());
                FacesMessage msg = new FacesMessage("Registro eliminado correctamente", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);

            } catch (IllegalOrphanException ex) {
                Logger.getLogger(PerfilesBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PerfilesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        listarPerfiles();
    }

    /**
     * Metodo para modificar un perfil y reasignarle los accesos seleccionados.
     */
    public void modificarPerfil() {
        List<SAccesos> listaIdAccesosAsignadosString = new ArrayList<>();
        SPerfilesAccesos perfilesAccesos = new SPerfilesAccesos();
        List<SPerfilesAccesos> listaPerfilesAccesos = new ArrayList<>();

        SPerfilesJpaController modeloPerfiles = new SPerfilesJpaController();
        SPerfilesAccesosJpaController modeloPerfilesAccesos = new SPerfilesAccesosJpaController();

        perfiles.setIdUsuarioModifica(TraeDatoSesion.traerIdUsuario());

        listaIdAccesosAsignadosString = dualListAccesos.getTarget();

        SAccesos objAccesos = new SAccesos();

        listaPerfilesAccesos = modeloPerfilesAccesos.traerAccesosByPerfil(perfiles);
        try {
            for (SPerfilesAccesos lista : listaPerfilesAccesos) {
                modeloPerfilesAccesos.destroy(lista.getSPerfilesAccesosPK());
            }
            for (Object listaId : listaIdAccesosAsignadosString) {

                int id = Integer.parseInt(listaId.toString());
                objAccesos.setIdAcceso(id);
                perfilesAccesos.setSPerfiles(perfiles);
                perfilesAccesos.setSAccesos(objAccesos);
                perfilesAccesos.setFechaServidor(new Date());
                perfilesAccesos.setIdUsuarioModifica(TraeDatoSesion.traerIdUsuario());
                try {
                    modeloPerfilesAccesos.create(perfilesAccesos);
                } catch (Exception ex) {
                    Logger.getLogger(PerfilesBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                modeloPerfiles.edit(perfiles);
                FacesMessage msg = new FacesMessage("Registro modificado correctamente", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (Exception ex) {
                Logger.getLogger(PerfilesBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PerfilesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        listarPerfiles();
    }

    /**
     * Metodo para traer todos los accesos disponibles y asignados de un perfil para luego inicializar 
     * una dualList con esos valores.
     * @param perfil Es el perfil de donde se obtiene el id para obtener los accesos disponibles y asignados. 
     */
    public void traerAccesosPerfil(SPerfiles perfil) {
        perfiles = perfil;
        dualListAccesos = new DualListModel<>();
        listaAccesosDisponibles = new ArrayList<>();
        listaAccesosAsignados = new ArrayList<>();
        SPerfilesAccesosJpaController modelo = new SPerfilesAccesosJpaController();

        listaAccesosDisponibles = modelo.traerAccesosDisponibles(perfil);
        listaAccesosAsignados = modelo.traerAccesosAsignados(perfil);
        dualListAccesos = new DualListModel<>(listaAccesosDisponibles, listaAccesosAsignados);

    }

//<editor-fold defaultstate="collapsed" desc="Get Set">
    public SPerfiles getPerfiles() {
        return perfiles;
    }
    
    public void setPerfiles(SPerfiles perfiles) {
        this.perfiles = perfiles;
    }
    
    public List<SPerfiles> getListaPerfiles() {
        return listaPerfiles;
    }
    
    public void setListaPerfiles(List<SPerfiles> listaPerfiles) {
        this.listaPerfiles = listaPerfiles;
    }
    
    public List<SAccesos> getListaAccesosDisponibles() {
        return listaAccesosDisponibles;
    }
    
    public void setListaAccesosDisponibles(List<SAccesos> listaAccesosDisponibles) {
        this.listaAccesosDisponibles = listaAccesosDisponibles;
    }
    
    public List<SAccesos> getListaAccesosAsignados() {
        return listaAccesosAsignados;
    }
    
    public void setListaAccesosAsignados(List<SAccesos> listaAccesosAsignados) {
        this.listaAccesosAsignados = listaAccesosAsignados;
    }
    
    public DualListModel<SAccesos> getDualListAccesos() {
        return dualListAccesos;
    }
    
    public void setDualListAccesos(DualListModel<SAccesos> dualListAccesos) {
        this.dualListAccesos = dualListAccesos;
    }
    
    public Integer getIdPerfil() {
        return idPerfil;
    }
    
    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }
//</editor-fold>

}

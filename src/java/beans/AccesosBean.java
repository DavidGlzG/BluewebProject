/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import controllers.SAccesosJpaController;
import controllers.SAccesosJpaController;
import controllers.exceptions.IllegalOrphanException;
import controllers.exceptions.NonexistentEntityException;
import entities.SAccesos;
import java.io.Serializable;
import static java.lang.Math.log;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author David Gonzalez
 */
@ManagedBean
@ViewScoped
public class AccesosBean implements Serializable {

    private SAccesos accesos = new SAccesos();
    private List<SAccesos> listaAccesos;
    private Integer idAcceso;
    private SAccesos modificaAccesos = new SAccesos();

    /**
     * Metodo constructor en donde se inica la lista de los accesos.
     */
    public AccesosBean() {
        listarAccesos();
    }

    /**
     * Metodo para traer todos los accesos de la base de datos y almacenarlos en una lista.
     */
    public void listarAccesos() {
        SAccesosJpaController modelo = new SAccesosJpaController();
        listaAccesos = modelo.findSAccesosEntities();
    }

    /**
     * Metodo para registar un acceso nuevo.
     */
    public void registrarAccesos() {
        try {
            SAccesosJpaController modelo = new SAccesosJpaController();
            accesos.setActivo(true);
            accesos.setFechaServidor(new Date());
            modelo.create(accesos);
            accesos = new SAccesos();
            listarAccesos();
            FacesMessage msg = new FacesMessage("Se registro correctamente.", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            Logger.getLogger(AccesosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo para eliminar un acceso de la base de datos.
     */
    public void eliminarAccesos() {
        try {
            SAccesosJpaController modelo = new SAccesosJpaController();
            modelo.destroy(idAcceso);
            listarAccesos();
            FacesMessage msg = new FacesMessage("Registro eliminado correctamente.", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(AccesosBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AccesosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metodo para modificar un acceso.
     */
    public void modificarAccesos() {
        try {
            SAccesosJpaController modelo = new SAccesosJpaController();
            modelo.edit(modificaAccesos);
            listarAccesos();
            FacesMessage msg = new FacesMessage("Registro modificado correctamente.", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(AccesosBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AccesosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo para asignar un objeto acceso seleccionado desde la vista al objeto acceso
     * declarado al principio de este bean.
     * @param seleccionAccesos Es el objeto tipo acceso seleccionado en la vista. 
     */
    public void seleccionAcceso(SAccesos seleccionAccesos) {
        modificaAccesos = seleccionAccesos;
    }

//<editor-fold defaultstate="collapsed" desc="Get Set">
    public SAccesos getAccesos() {
        return accesos;
    }
    
    public void setAccesos(SAccesos accesos) {
        this.accesos = accesos;
    }
    
    public List<SAccesos> getListaAccesos() {
        return listaAccesos;
    }
    
    public void setListaAccesos(List<SAccesos> listaAccesos) {
        this.listaAccesos = listaAccesos;
    }
    
    public Integer getIdAcceso() {
        return idAcceso;
    }
    
    public void setIdAcceso(Integer idAcceso) {
        this.idAcceso = idAcceso;
    }
    
    public SAccesos getModificaAccesos() {
        return modificaAccesos;
    }
    
    public void setModificaAccesos(SAccesos modificaAccesos) {
        this.modificaAccesos = modificaAccesos;
    }
//</editor-fold>

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controllers.SPerfilesAccesosJpaController;
import entities.SPerfilesAccesos;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DualListModel;

/**
 *
 * @author David Gonzalez
 */
@ManagedBean
@ViewScoped
public class PerfilesAccesos {
    private List<SPerfilesAccesos> listaPerfilesAccesos;
    private List<SPerfilesAccesos> listaPerfilesAccesso2;
    private DualListModel<SPerfilesAccesos> dualListaPerfilesAccesos = new DualListModel<>(listaPerfilesAccesos, listaPerfilesAccesso2);

    public PerfilesAccesos() {
        listarPerfilesAccesos();
    }
    
    public void listarPerfilesAccesos(){
        SPerfilesAccesosJpaController modelo = new SPerfilesAccesosJpaController();
        listaPerfilesAccesos = modelo.findSPerfilesAccesosEntities();
    }

    public List<SPerfilesAccesos> getListaPerfilesAccesos() {
        return listaPerfilesAccesos;
    }

    public void setListaPerfilesAccesos(List<SPerfilesAccesos> listaPerfilesAccesos) {
        this.listaPerfilesAccesos = listaPerfilesAccesos;
    }

    public List<SPerfilesAccesos> getListaPerfilesAccesso2() {
        return listaPerfilesAccesso2;
    }

    public void setListaPerfilesAccesso2(List<SPerfilesAccesos> listaPerfilesAccesso2) {
        this.listaPerfilesAccesso2 = listaPerfilesAccesso2;
    }

    public DualListModel<SPerfilesAccesos> getDualListaPerfilesAccesos() {
        return dualListaPerfilesAccesos;
    }

    public void setDualListaPerfilesAccesos(DualListModel<SPerfilesAccesos> dualListaPerfilesAccesos) {
        this.dualListaPerfilesAccesos = dualListaPerfilesAccesos;
    }

    
    
    
}

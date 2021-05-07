/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controllers.SPerfilesJpaController;
import entities.SPerfiles;
import java.util.ArrayList;
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
public class PerfilesBean {
    private SPerfiles perfiles = new SPerfiles();
    private List<SPerfiles> listaPerfiles;
    private List<SPerfiles> listaPerfiles2 = new ArrayList<>();
    private DualListModel<SPerfiles> dualListaPerfiles;

    public PerfilesBean() {
        listarPerfoles();
    }
    
    public void listarPerfoles(){
        SPerfilesJpaController modelo = new SPerfilesJpaController();
        listaPerfiles = modelo.findSPerfilesEntities();
        dualListaPerfiles = new DualListModel<>(listaPerfiles, listaPerfiles2);
    }

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

    public List<SPerfiles> getListaPerfiles2() {
        return listaPerfiles2;
    }

    public void setListaPerfiles2(List<SPerfiles> listaPerfiles2) {
        this.listaPerfiles2 = listaPerfiles2;
    }

    public DualListModel<SPerfiles> getDualListaPerfiles() {
        return dualListaPerfiles;
    }

    public void setDualListaPerfiles(DualListModel<SPerfiles> dualListaPerfiles) {
        this.dualListaPerfiles = dualListaPerfiles;
    }
    
    
}

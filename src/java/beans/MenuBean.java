/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import controllers.SAplicacionesJpaController;
import entities.SAccesos;
import entities.SAplicaciones;
import entities.SPerfiles;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import utils.TraeDatoSesion;

/**
 *
 * @author David Gonzalez
 */
@ManagedBean
@ViewScoped
public class MenuBean implements Serializable {

    private MenuModel modeloMenu;

    /**
     * Metodo constructor en donde se incia la lista del menú dinámico.
     */
    @PostConstruct
    public void init() {
        listarMenu();
    }

    /**
     * Metodo que lista un menú dinamicamente segun el usuario activo.
     */
    public void listarMenu() {
        SAplicacionesJpaController modeloAplicaciones = new SAplicacionesJpaController();
        List<SAplicaciones> listaAplicaciones = new ArrayList<>();
        listaAplicaciones = modeloAplicaciones.traerListaAplicaciones(TraeDatoSesion.traerUsuario());

        DefaultMenuItem menuItem = new DefaultMenuItem();
        modeloMenu = new DefaultMenuModel();
        DefaultSubMenu inicioSubMenu = DefaultSubMenu.builder().label("Inicio").build();
        menuItem.setValue("Imagenes");
        menuItem.setUrl("/../DavidProject/faces/inicio/imagenes.xhtml");

        DefaultSubMenu menuDinamico = new DefaultSubMenu();
        DefaultMenuItem menuDinamicoItem = new DefaultMenuItem();

        inicioSubMenu.getElements().add(menuItem);
        modeloMenu.getElements().add(inicioSubMenu);

        for (SAplicaciones listaMenu : listaAplicaciones) {
            if (listaMenu.getIdAplicacion() == 0) {

                menuDinamico.setLabel(listaMenu.getNombreAplicacion());
                menuDinamico.setIcon(listaMenu.getIcono());

                String nombreMenu = listaMenu.getNombreAplicacion();
                String nombreMenuConfirmacion = "";

                for (SAplicaciones listaItem : listaAplicaciones) {

                    if (listaItem.getIdAplicacion() == 0) {
                        nombreMenuConfirmacion = listaItem.getNombreAplicacion();
                    }
                    if (nombreMenu == nombreMenuConfirmacion &&
                            listaItem.getIdAplicacion() != 0) {
                        
                            menuDinamicoItem.setValue(listaItem.getNombreAplicacion());
                            menuDinamicoItem.setUrl("/../DavidProject/faces" + listaItem.getUrl());
                            menuDinamicoItem.setIcon(listaItem.getIcono());
                            menuDinamico.getElements().add(menuDinamicoItem);
                            menuDinamicoItem = new DefaultMenuItem();
                    }
                }
                modeloMenu.getElements().add(menuDinamico);
                menuDinamico = new DefaultSubMenu();
            }
        }
    }

//<editor-fold defaultstate="collapsed" desc="Get Set">
    public MenuModel getModeloMenu() {
        return modeloMenu;
    }
    
    public void setModeloMenu(MenuModel modeloMenu) {
        this.modeloMenu = modeloMenu;
    }
//</editor-fold>

}

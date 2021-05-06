/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import objetos.Distribuidores;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelos.DistribuidoresModelo;
import respuestas.RespuestaDistribuidores;

/**
 *
 * @author admin
 */

@ManagedBean
@ViewScoped
public class DistribuidoresBean implements Serializable{
    
    private Distribuidores distribuidorRegistrar = new Distribuidores();
    private Distribuidores distribuidor = new Distribuidores();
    private List<Distribuidores> listaDistribuidor = new ArrayList<Distribuidores>();
    private List<Distribuidores> listaDistribuidorFiltro = new ArrayList<Distribuidores>();
    private static int idDistribuidor;

    public DistribuidoresBean() {
        listarDistribuidores();
    }
    
    /**
     * Llama al modelo de distribuidores para agregarlos al 
     * objeto de lista de distribuidores.
     */
    public void listarDistribuidores(){
        
        RespuestaDistribuidores rs = DistribuidoresModelo.listarDistribuidores();
        if(rs.getRespuesta().getIdRespuesta() == 0){
            listaDistribuidor = rs.getListaDistribuidores();
        }else{
            System.out.println(rs.getRespuesta().getMsgRespuesta());
        }
        
    }
    
    
    public void listarDistribuidoresFiltro(){
        
        RespuestaDistribuidores rs = DistribuidoresModelo.listarDistribuidoresFiltro(distribuidor);
        if(rs.getRespuesta().getIdRespuesta() == 0){
            listaDistribuidorFiltro = rs.getListaDistribuidores();
        }else{
            System.out.println(rs.getRespuesta().getMsgRespuesta());
        }
        
    }
    
    
    /**
     * Llama al modelo de distribuidores que registra al
     * objeto distribuidor.
     */
    public void registrarDistribuidores(){
        DistribuidoresModelo modelo = new DistribuidoresModelo();
        RespuestaDistribuidores rs = modelo.registroDistribuidores(distribuidorRegistrar);
        if(rs.getRespuesta().getIdRespuesta() == 0){
            listaDistribuidorFiltro = rs.getListaDistribuidores();
            listarDistribuidoresFiltro();
            FacesMessage msg = new FacesMessage("Se registro correctamente.", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            distribuidorRegistrar = new Distribuidores();
        }else{
            System.out.println(rs.getRespuesta().getMsgRespuesta());
        }
    }
    
    /**
     * Llama al modelo de distribuidores que elimina al 
     * objeto distribuidor.
     * @param distribuidor se optiene de la vista de distribuidor 
     * para obtener el id del objeto distribuidor a eliminar.
     */
    public void eliminarDistribuidores(){
        DistribuidoresModelo modelo = new DistribuidoresModelo();
        RespuestaDistribuidores rs = modelo.eliminarDistribuidores(idDistribuidor);
        if(rs.getRespuesta().getIdRespuesta() == 0){
            listaDistribuidorFiltro = rs.getListaDistribuidores();
            listarDistribuidoresFiltro();
            FacesMessage msg = new FacesMessage("Registro eliminado correctamente.", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }else{
            System.out.println(rs.getRespuesta().getMsgRespuesta());
        }
    }
    
    /**
     * Llama al modelo de distribuidores que modifica al 
     * objeto distribuidor.
     * @param distribuidor se optiene de la vista de distribuidor 
     * para obtener los valores del objeto distribuidor a modificar.
     */
    public void modificarDistribuidores(Distribuidores distribuidor){
        DistribuidoresModelo modelo = new DistribuidoresModelo();
        RespuestaDistribuidores rs = modelo.modificarDistribuidores(distribuidor);
        if(rs.getRespuesta().getIdRespuesta() == 0){
            listaDistribuidorFiltro = rs.getListaDistribuidores();
            listarDistribuidoresFiltro();
            FacesMessage msg = new FacesMessage("Registro actualizado correctamente.", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }else{
            System.out.println(rs.getRespuesta().getMsgRespuesta());
        }
    }
    
    public void almacenarId(Distribuidores distribuidor){
        
        idDistribuidor = distribuidor.getIdDistribuidor();
        
    }

    public Distribuidores getDistribuidor() {
        return distribuidor;
    }

    public void setDistribuidor(Distribuidores distribuidor) {
        this.distribuidor = distribuidor;
    }

    public List<Distribuidores> getListaDistribuidor() {
        return listaDistribuidor;
    }

    public void setListaDistribuidor(List<Distribuidores> listaDistribuidor) {
        this.listaDistribuidor = listaDistribuidor;
    }

    public static int getIdDistribuidor() {
        return idDistribuidor;
    }

    public static void setIdDistribuidor(int idDistribuidor) {
        DistribuidoresBean.idDistribuidor = idDistribuidor;
    }

    public List<Distribuidores> getListaDistribuidorFiltro() {
        return listaDistribuidorFiltro;
    }

    public void setListaDistribuidorFiltro(List<Distribuidores> listaDistribuidorFiltro) {
        this.listaDistribuidorFiltro = listaDistribuidorFiltro;
    }

    public Distribuidores getDistribuidorRegistrar() {
        return distribuidorRegistrar;
    }

    public void setDistribuidorRegistrar(Distribuidores distribuidorRegistrar) {
        this.distribuidorRegistrar = distribuidorRegistrar;
    }
    
}

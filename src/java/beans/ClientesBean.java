/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.ArrayList;
import objetos.Clientes;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelos.ClientesModelo;
import respuestas.Respuesta;
import respuestas.RespuestaClientes;

/**
 *
 * @author admin
 */
@ManagedBean
@ViewScoped
public class ClientesBean implements Serializable {

    private Clientes cliente = new Clientes();
    private Clientes clienteRegistro = new Clientes();
    private List<Clientes> listaCliente = new ArrayList<Clientes>();
    private static int idCliente;
    

    public ClientesBean() {
        
    }

    /**
     * Llama al modelo de clientes para agregarlos al objeto de lista de
     * clientes.
     */
    public void listarClientes() {

        try {
            RespuestaClientes rs = ClientesModelo.listarClientes(cliente);
            if (rs.getRespuesta().getIdRespuesta() == 0
                    || rs.getRespuesta().getIdRespuesta() == 1) {
                listaCliente = rs.getListaCliente();
            } else {
                System.out.println(rs.getRespuesta().getMsgRespuesta());
                
            }
        } catch (Exception e) {
            Logger.getLogger(ClientesBean.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    /**
     * Llama al modelo de clientes que registra al objeto clientes.
     */
    public void registrarClientes() {

        try {
            ClientesModelo modelo = new ClientesModelo();
            Respuesta rs = modelo.registroClientes(clienteRegistro);
            if (rs.getIdRespuesta() == 0) {
                listarClientes();
                FacesMessage msg = new FacesMessage("Se registro correctamente.", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                clienteRegistro = new Clientes();
            } else {
                System.out.println(rs.getMsgRespuesta());
            }
        } catch (Exception e) {
            Logger.getLogger(ClientesBean.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    /**
     * Llama al modelo de clientes que elimina al objeto cliente.
     *
     * @param cliente se optiene de la vista de clientes para obtener el id del
     * objeto clietete a eliminar.
     */
    public void eliminarClientes() {

        try {
            ClientesModelo modelo = new ClientesModelo();
            Respuesta rs = modelo.eliminarClientes(idCliente);
            if (rs.getIdRespuesta() == 0) {
                listarClientes();
                FacesMessage msg = new FacesMessage("Registro eliminado correctamente.", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                System.out.println(rs.getMsgRespuesta());
            }
        } catch (Exception e) {
            Logger.getLogger(ClientesBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Llama al modelo de clientes que modifica al objeto cliente.
     *
     * @param cliente se optiene de la vista clientes para optener los valores
     * del cliete a modificar.
     */
    public void modificarClientes(Clientes cliente) {

        try {
            ClientesModelo modelo = new ClientesModelo();
            Respuesta rs = modelo.modificarClientes(cliente);
            if (rs.getIdRespuesta() == 0) {
                listarClientes();
                FacesMessage msg = new FacesMessage("Registro modificado correctamente.", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                System.out.println(rs.getMsgRespuesta());
            }
        } catch (Exception ex) {
            Logger.getLogger(ClientesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Este metodo almacena el atributo idCliente en una variable
     * estatica llamada idCliente
     * @param cliente Es el objeto cliente del cual se obtiene el
     * atributo IdCliente
     */
    public void almacenarId(Clientes cliente) {
        idCliente = cliente.getIdCliente();
    }

//<editor-fold defaultstate="collapsed" desc="Get Set">
    public Clientes getCliente() {
        return cliente;
    }
    
    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }
    
    public List<Clientes> getListaCliente() {
        return listaCliente;
    }
    
    public void setListaCliente(List<Clientes> listaCliente) {
        this.listaCliente = listaCliente;
    }
    
    public int getIdCliente() {
        return idCliente;
    }
    
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
    public Clientes getClienteRegistro() {
        return clienteRegistro;
    }
    
    public void setClienteRegistro(Clientes clienteRegistro) {
        this.clienteRegistro = clienteRegistro;
    }
//</editor-fold>
}

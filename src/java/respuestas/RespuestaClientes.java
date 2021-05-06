/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package respuestas;

import objetos.Clientes;
import java.util.ArrayList;
import java.util.List;
import objetos.Clientes;

/**
 *
 * @author admin
 */
public class RespuestaClientes {
    
    private Respuesta respuesta;
    private Clientes cliente;
    private List<Clientes> listaCliente;

    public Respuesta getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Respuesta respuesta) {
        this.respuesta = respuesta;
    }

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
    
    
}

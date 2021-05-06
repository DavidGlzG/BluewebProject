/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package respuestas;

import objetos.Distribuidores;
import java.util.ArrayList;
import java.util.List;
import objetos.Distribuidores;

/**
 *
 * @author admin
 */
public class RespuestaDistribuidores {

    private Respuesta respuesta;
    private Distribuidores distribuidor;
    private List<Distribuidores> listaDistribuidores;

    public Respuesta getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Respuesta respuesta) {
        this.respuesta = respuesta;
    }

    public Distribuidores getDistribuidor() {
        return distribuidor;
    }

    public void setDistribuidor(Distribuidores distribuidor) {
        this.distribuidor = distribuidor;
    }

    public List<Distribuidores> getListaDistribuidores() {
        return listaDistribuidores;
    }

    public void setListaDistribuidores(List<Distribuidores> listaDistribuidores) {
        this.listaDistribuidores = listaDistribuidores;
    }

}

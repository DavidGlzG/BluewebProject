/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import dbutils.PoolDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import objetos.Ciudades;
import respuestas.Respuesta;
import respuestas.RespuestaCiudades;

/**
 *
 * @author David
 */
public class CiudadesModelo {
    
    public RespuestaCiudades listarCiudades(){
        List<Ciudades> lista = new ArrayList<Ciudades>();
        Connection con = null;
        Ciudades obj;
        RespuestaCiudades rs = new RespuestaCiudades();
        Respuesta respuesta = new Respuesta();
        boolean hayRegistro = false;
        String sql = "SELECT ID_CIUDAD, DESCRIPCION, CODIGO, LADA, ACTIVO, "
                + "FECHA_ALTA, FECHA_BAJA, FECHA_SERVIDOR FROM C_CIUDAD";
        
        try {
            con = PoolDB.getConnection("ACTIVACION");
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rsBuscar = ps.executeQuery();
            
            while(rsBuscar.next()){
                hayRegistro = true;
                obj = new Ciudades();
                obj.setIdCiudad(rsBuscar.getInt("ID_CIUDAD"));
                obj.setDescripcion(rsBuscar.getString("DESCRIPCION"));
                obj.setCodigo(rsBuscar.getString("CODIGO"));
                obj.setLada(rsBuscar.getInt("LADA"));
                obj.setActivo(rsBuscar.getInt("ACTIVO"));
                obj.setFecha_alta(rsBuscar.getString("FECHA_ALTA"));
                obj.setFecha_baja(rsBuscar.getString("FECHA_BAJA"));
                obj.setFecha_servidor(rsBuscar.getString("FECHA_SERVIDOR"));
                lista.add(obj);
            }
            
            if (hayRegistro) {
                respuesta.setIdRespuesta(0);
                respuesta.setMsgRespuesta("Si hay registros");
            } else {
                respuesta.setIdRespuesta(1);
                respuesta.setMsgRespuesta("No hay registros");
            }

        } catch (NamingException ex) {
            respuesta.setIdRespuesta(-1);
            respuesta.setMsgRespuesta("Error de conexion");
            Logger.getLogger(ClientesModelo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            respuesta.setIdRespuesta(-2);
            respuesta.setMsgRespuesta("Error de base de datos");
            Logger.getLogger(ClientesModelo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            respuesta.setIdRespuesta(-3);
            respuesta.setMsgRespuesta("Error de aplicacion");
            Logger.getLogger(ClientesModelo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                respuesta.setIdRespuesta(-4);
                respuesta.setMsgRespuesta("Error al cerrar conexion");
                Logger.getLogger(CiudadesModelo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        rs.setListaCiudades(lista);
        rs.setRespuesta(respuesta);
        return rs;
        
    }
    
}

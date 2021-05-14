/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import dbutils.PoolDB;
import java.sql.Connection;
import objetos.Usuarios;
import respuestas.Respuesta;
import respuestas.RespuestaUsuarios;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import utils.HexDigest;

/**
 *
 * @author David Gonzalez
 */
public class UsuariosModelo {

    public static RespuestaUsuarios validarUsuarios(Usuarios usuario){
        Connection con = null;
        Respuesta respuesta = new Respuesta();
        RespuestaUsuarios rs = new RespuestaUsuarios();
        Usuarios obj = new Usuarios();
        List<Usuarios> lista = new ArrayList<>();
        boolean hayRegistro = false;
        
        String password = HexDigest.hexDigest(usuario.getPassword());
        
        try {
            con = PoolDB.getConnection("ACTIVACION");
            PreparedStatement ps = con.prepareStatement("SELECT ID_USUARIO, ID_PERFIL, USUARIO, NOMBRE_USUARIO, PASSWORD, ULTIMA_SESION"
                    + " FROM S_USUARIOS WHERE USUARIO = ? AND PASSWORD = ?");
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, password);
            ResultSet rsBuscar = ps.executeQuery();
            
            while(rsBuscar.next()){
                hayRegistro = true;
                obj = new Usuarios();
                obj.setIdUsuario(rsBuscar.getInt("ID_USUARIO"));
                obj.setIdPerfil(rsBuscar.getInt("ID_PERFIL"));
                obj.setUsuario(rsBuscar.getString("USUARIO"));
                obj.setNombreUsuarios(rsBuscar.getString("NOMBRE_USUARIO"));
                obj.setUltimaSesion(rsBuscar.getString("ULTIMA_SESION"));
                
                ps = con.prepareStatement("UPDATE S_USUARIOS SET ULTIMA_SESION = GETDATE() WHERE ID_USUARIO = ?");
                ps.setInt(1, obj.getIdUsuario());
                ps.executeUpdate();
            }
            
            ps.close();
            
            if (hayRegistro) {
                respuesta.setIdRespuesta(0);
                respuesta.setMsgRespuesta("Resgistro exitoso");
                
            } else {
                respuesta.setIdRespuesta(1);
                respuesta.setMsgRespuesta("No se pudo crear el registro");
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
                Logger.getLogger(ClientesModelo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        rs.setUsuario(obj);
        rs.setRespuesta(respuesta);
        return rs;
    }
    
    
}

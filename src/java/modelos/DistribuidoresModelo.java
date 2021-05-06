/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import dbutils.PoolDB;
import java.util.ArrayList;
import java.util.List;
import objetos.Distribuidores;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import respuestas.RespuestaDistribuidores;
import java.sql.ResultSet;
import respuestas.Respuesta;
import utils.TraeDatoSesion;

/**
 *
 * @author David Gonzalez
 */
public class DistribuidoresModelo {

    public static RespuestaDistribuidores listarDistribuidores() {
        List<Distribuidores> lista = new ArrayList<Distribuidores>();
        Connection con = null;
        Distribuidores obj;
        RespuestaDistribuidores rs = new RespuestaDistribuidores();
        Respuesta respuesta = new Respuesta();
        boolean hayRegistro = false;
        String sql = "SELECT ID_DISTRIBUIDOR, "
                + "CLAVE_DISTRIBUIDOR, NOMBRE, ACTIVO, FECHA_ALTA, FECHA_BAJA, FECHA_SERVIDOR, ID_USUARIO_MODIFICA FROM "
                + "C_DISTRIBUIDOR";
        try {
            con = PoolDB.getConnection("ACTIVACION");
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rsBuscar = ps.executeQuery();

            while (rsBuscar.next()) {
                hayRegistro = true;
                obj = new Distribuidores();
                obj.setIdDistribuidor(rsBuscar.getInt("ID_DISTRIBUIDOR"));
                obj.setClaveDistribuidor(rsBuscar.getString("CLAVE_DISTRIBUIDOR"));
                obj.setNombre(rsBuscar.getString("NOMBRE"));
                obj.setActivo(rsBuscar.getInt("ACTIVO"));
                obj.setFechaAlta(rsBuscar.getString("FECHA_ALTA"));
                obj.setFechaBaja(rsBuscar.getString("FECHA_BAJA"));
                obj.setFechaServidor(rsBuscar.getString("FECHA_SERVIDOR"));
                obj.setIdUsuarioModifica(rsBuscar.getInt("ID_USUARIO_MODIFICA"));
                lista.add(obj);

            }
            rsBuscar.close();
            ps.close();

            if (hayRegistro) {
                respuesta.setIdRespuesta(0);
                respuesta.setMsgRespuesta("Si hay registros");
            } else {
                respuesta.setIdRespuesta(1);
                respuesta.setMsgRespuesta("No hay resgistros");
            }

        } catch (NamingException ex) {
            respuesta.setIdRespuesta(-1);
            respuesta.setMsgRespuesta("Error de conexion");
            Logger.getLogger(DistribuidoresModelo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            respuesta.setIdRespuesta(-2);
            respuesta.setMsgRespuesta("Error de base de datos");
            Logger.getLogger(DistribuidoresModelo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            respuesta.setIdRespuesta(-3);
            respuesta.setMsgRespuesta("Error de aplicacion");
            Logger.getLogger(DistribuidoresModelo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                respuesta.setIdRespuesta(-4);
                respuesta.setMsgRespuesta("Error al cerrar conexion");
                Logger.getLogger(DistribuidoresModelo.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        rs.setListaDistribuidores(lista);
        rs.setRespuesta(respuesta);
        return rs;
    }

    public static RespuestaDistribuidores listarDistribuidoresFiltro(Distribuidores distribuidor) {
        List<Distribuidores> lista = new ArrayList<Distribuidores>();
        Connection con = null;
        Distribuidores obj;
        RespuestaDistribuidores rs = new RespuestaDistribuidores();
        Respuesta respuesta = new Respuesta();
        boolean hayRegistro = false;
        String sql = "SELECT ID_DISTRIBUIDOR, "
                + "CLAVE_DISTRIBUIDOR, NOMBRE, ACTIVO, FECHA_ALTA, FECHA_BAJA, FECHA_SERVIDOR, ID_USUARIO_MODIFICA FROM "
                + "C_DISTRIBUIDOR";
        String claveDistribuidor = "'" + distribuidor.getClaveDistribuidor() + "%'";
        String nombre = "'" + distribuidor.getNombre() + "%'";
        sql = sql + " WHERE NOMBRE LIKE " + nombre + " AND CLAVE_DISTRIBUIDOR LIKE " + claveDistribuidor;
        try {
            con = PoolDB.getConnection("ACTIVACION");
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rsBuscar = ps.executeQuery();

            while (rsBuscar.next()) {
                hayRegistro = true;
                obj = new Distribuidores();
                obj.setIdDistribuidor(rsBuscar.getInt("ID_DISTRIBUIDOR"));
                obj.setClaveDistribuidor(rsBuscar.getString("CLAVE_DISTRIBUIDOR"));
                obj.setNombre(rsBuscar.getString("NOMBRE"));
                obj.setActivo(rsBuscar.getInt("ACTIVO"));
                obj.setFechaAlta(rsBuscar.getString("FECHA_ALTA"));
                obj.setFechaBaja(rsBuscar.getString("FECHA_BAJA"));
                obj.setFechaServidor(rsBuscar.getString("FECHA_SERVIDOR"));
                obj.setIdUsuarioModifica(rsBuscar.getInt("ID_USUARIO_MODIFICA"));
                lista.add(obj);

            }
            rsBuscar.close();
            ps.close();

            if (hayRegistro) {
                respuesta.setIdRespuesta(0);
                respuesta.setMsgRespuesta("Si hay registros");
            } else {
                respuesta.setIdRespuesta(1);
                respuesta.setMsgRespuesta("No hay resgistros");
            }

        } catch (NamingException ex) {
            respuesta.setIdRespuesta(-1);
            respuesta.setMsgRespuesta("Error de conexion");
            Logger.getLogger(DistribuidoresModelo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            respuesta.setIdRespuesta(-2);
            respuesta.setMsgRespuesta("Error de base de datos");
            Logger.getLogger(DistribuidoresModelo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            respuesta.setIdRespuesta(-3);
            respuesta.setMsgRespuesta("Error de aplicacion");
            Logger.getLogger(DistribuidoresModelo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                respuesta.setIdRespuesta(-4);
                respuesta.setMsgRespuesta("Error al cerrar conexion");
                Logger.getLogger(DistribuidoresModelo.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        rs.setListaDistribuidores(lista);
        rs.setRespuesta(respuesta);
        return rs;
    }

    public RespuestaDistribuidores registroDistribuidores(Distribuidores distribuidor) {

        RespuestaDistribuidores rs = new RespuestaDistribuidores();
        Respuesta respuesta = new Respuesta();
        Connection con = null;
        int registro = 0;
        int idUsuarioModifica = TraeDatoSesion.traerIdUsuario();
        try {
            con = PoolDB.getConnection("ACTIVACION");
            PreparedStatement ps = con.prepareStatement("INSERT INTO C_DISTRIBUIDOR (CLAVE_DISTRIBUIDOR,"
                    + "NOMBRE, ACTIVO, FECHA_ALTA, ID_USUARIO_MODIFICA) VALUES (?,?,1, GETDATE(), ?)");
            ps.setString(1, distribuidor.getClaveDistribuidor());
            ps.setString(2, distribuidor.getNombre());
            ps.setInt(3, idUsuarioModifica);
            registro = ps.executeUpdate();
            ps.close();

            if (registro > 0) {
                respuesta.setIdRespuesta(0);
                respuesta.setMsgRespuesta("Resgistro exitoso");
            } else {
                respuesta.setIdRespuesta(1);
                respuesta.setMsgRespuesta("No se pudo crear el registro");
            }

        } catch (NamingException ex) {
            respuesta.setIdRespuesta(-1);
            respuesta.setMsgRespuesta("Error de conexion");
            Logger.getLogger(DistribuidoresModelo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            respuesta.setIdRespuesta(-2);
            respuesta.setMsgRespuesta("Error de base de datos");
            Logger.getLogger(DistribuidoresModelo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            respuesta.setIdRespuesta(-3);
            respuesta.setMsgRespuesta("Error de aplicacion");
            Logger.getLogger(DistribuidoresModelo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                respuesta.setIdRespuesta(-4);
                respuesta.setMsgRespuesta("Error al cerrar conexion");
                Logger.getLogger(DistribuidoresModelo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        rs.setRespuesta(respuesta);
        return rs;
    }

    public RespuestaDistribuidores eliminarDistribuidores(int idDistribuidor) {

        RespuestaDistribuidores rs = new RespuestaDistribuidores();
        Respuesta respuesta = new Respuesta();
        Connection con = null;
        int registro = 0;
        try {
            con = PoolDB.getConnection("ACTIVACION");
            PreparedStatement ps = con.prepareStatement("DELETE FROM C_DISTRIBUIDOR WHERE ID_DISTRIBUIDOR = ? ");
            ps.setInt(1, idDistribuidor);
            registro = ps.executeUpdate();
            ps.close();

            if (registro > 0) {
                respuesta.setIdRespuesta(0);
                respuesta.setMsgRespuesta("Resgistro exitoso");
            } else {
                respuesta.setIdRespuesta(1);
                respuesta.setMsgRespuesta("No se pudo crear el registro");
            }

        } catch (NamingException ex) {
            respuesta.setIdRespuesta(-1);
            respuesta.setMsgRespuesta("Error de conexion");
            Logger.getLogger(DistribuidoresModelo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            respuesta.setIdRespuesta(-2);
            respuesta.setMsgRespuesta("Error de base de datos");
            Logger.getLogger(DistribuidoresModelo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            respuesta.setIdRespuesta(-3);
            respuesta.setMsgRespuesta("Error de aplicacion");
            Logger.getLogger(DistribuidoresModelo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                respuesta.setIdRespuesta(-4);
                respuesta.setMsgRespuesta("Error al cerrar conexion");
                Logger.getLogger(DistribuidoresModelo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        rs.setRespuesta(respuesta);
        return rs;
    }

    public RespuestaDistribuidores modificarDistribuidores(Distribuidores distribuidor) {

        RespuestaDistribuidores rs = new RespuestaDistribuidores();
        Respuesta respuesta = new Respuesta();
        Connection con = null;
        int registro = 0;
        int idUsuarioModifica = TraeDatoSesion.traerIdUsuario();
        try {
            con = PoolDB.getConnection("ACTIVACION");
            PreparedStatement ps = con.prepareStatement("UPDATE C_DISTRIBUIDOR SET CLAVE_DISTRIBUIDOR = ?,"
                    + "NOMBRE = ?, ACTIVO = ?, ID_USUARIO_MODIFICA = ? WHERE ID_DISTRIBUIDOR = ?");
            ps.setString(1, distribuidor.getClaveDistribuidor());
            ps.setString(2, distribuidor.getNombre());
            ps.setInt(3, distribuidor.getActivo());
            ps.setInt(4, idUsuarioModifica);
            ps.setInt(5, distribuidor.getIdDistribuidor());
            registro = ps.executeUpdate();
            ps.close();

            if (registro > 0) {
                respuesta.setIdRespuesta(0);
                respuesta.setMsgRespuesta("Resgistro exitoso");
            } else {
                respuesta.setIdRespuesta(1);
                respuesta.setMsgRespuesta("No se pudo crear el registro");
            }

        } catch (NamingException ex) {
            respuesta.setIdRespuesta(-1);
            respuesta.setMsgRespuesta("Error de conexion");
            Logger.getLogger(DistribuidoresModelo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            respuesta.setIdRespuesta(-2);
            respuesta.setMsgRespuesta("Error de base de datos");
            Logger.getLogger(DistribuidoresModelo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            respuesta.setIdRespuesta(-3);
            respuesta.setMsgRespuesta("Error de aplicacion");
            Logger.getLogger(DistribuidoresModelo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                respuesta.setIdRespuesta(-4);
                respuesta.setMsgRespuesta("Error al cerrar conexion");
                Logger.getLogger(DistribuidoresModelo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        rs.setRespuesta(respuesta);
        return rs;
    }
}

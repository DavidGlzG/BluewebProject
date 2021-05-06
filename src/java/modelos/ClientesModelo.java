/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import dbutils.PoolDB;
import java.util.ArrayList;
import java.util.List;
import objetos.Clientes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import respuestas.Respuesta;
import respuestas.RespuestaClientes;
import utils.TraeDatoSesion;

/**
 *
 * @author David Gonzalez
 */
public class ClientesModelo {

    /**
     * Funcion que se conecta con la base de datos y extrair el contenido de la
     * tabla C_CLIENTES para luego almacenarlos en una lista de objetos
     * clientes.
     *
     * @return Regresa el objeto lista de clientes y una respuesta segun la
     * Exception o error.
     */
    public static RespuestaClientes listarClientes(Clientes cliente) {
        List<Clientes> lista = new ArrayList<>();
        Connection con = null;
        Clientes obj;
        RespuestaClientes rs = new RespuestaClientes();
        Respuesta respuesta = new Respuesta();
        boolean hayRegistro = false;
        String sql = ("SELECT CL.ID_CLIENTE, CL.NUM_CLIENTE, CL.NOMBRE_CLIENTE, \n"
                + "CL.ID_DISTRIBUIDOR, DI.NOMBRE, CL.ID_CIUDAD, \n"
                + "CI.DESCRIPCION, CL.TEL_CONTACTO, CL.RFC, CL.CALLE, CL.NUM_EXT, CL.COLONIA,\n"
                + "CL.CP, CL.ACTIVO, CL.FECHA_ALTA, CL.FECHA_BAJA, CL.FECHA_SERVIDOR, CL.ID_USUARIO_MODIFICA\n"
                + "FROM C_CLIENTES CL LEFT JOIN C_DISTRIBUIDOR DI ON CL.ID_DISTRIBUIDOR=DI.ID_DISTRIBUIDOR\n"
                + "LEFT JOIN C_CIUDAD CI ON CL.ID_CIUDAD=CI.ID_CIUDAD");
        String nombreCliente = "'%'";
        if (cliente.getNombreCliente() != null) {
            nombreCliente = "'" + cliente.getNombreCliente() + "%'";
            sql = sql + " WHERE CL.NOMBRE_CLIENTE LIKE " + nombreCliente;
            if (cliente.getIdDistribuidor() > 0) {
                sql = sql + " AND CL.ID_DISTRIBUIDOR=" + cliente.getIdDistribuidor();
                if (cliente.getIdCiudad() > 0) {
                    sql = sql + " AND CL.ID_CIUDAD=" + cliente.getIdCiudad();
                }
            } else if (cliente.getIdCiudad() > 0) {
                sql = sql + " AND CL.ID_CIUDAD=" + cliente.getIdCiudad();
            }
        }

        try {
            con = PoolDB.getConnection("ACTIVACION");
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rsBuscar = ps.executeQuery();

            while (rsBuscar.next()) {
                hayRegistro = true;
                obj = new Clientes();
                obj.setIdCliente(rsBuscar.getInt("ID_CLIENTE"));
                obj.setNumCliente(rsBuscar.getString("NUM_CLIENTE"));
                obj.setNombreCliente(rsBuscar.getString("NOMBRE_CLIENTE"));
                obj.setIdDistribuidor(rsBuscar.getInt("ID_DISTRIBUIDOR"));
                obj.setIdCiudad(rsBuscar.getInt("ID_CIUDAD"));
                obj.setTelContacto(rsBuscar.getString("TEL_CONTACTO"));
                obj.setRfc(rsBuscar.getString("RFC"));
                obj.setCalle(rsBuscar.getString("CALLE"));
                obj.setNumExt(rsBuscar.getString("NUM_EXT"));
                obj.setColonia(rsBuscar.getString("COLONIA"));
                obj.setCp(rsBuscar.getString("CP"));
                obj.setActivo(rsBuscar.getInt("ACTIVO"));
                obj.setFechaAlta(rsBuscar.getString("FECHA_ALTA"));
                obj.setFechaBaja(rsBuscar.getString("FECHA_BAJA"));
                obj.setFechaServidor(rsBuscar.getString("FECHA_SERVIDOR"));
                obj.setIdUsuarioModifica(rsBuscar.getInt("ID_USUARIO_MODIFICA"));
                obj.setNombreDistribuidor(rsBuscar.getString("NOMBRE"));
                obj.setNombreCiudad(rsBuscar.getString("DESCRIPCION"));
                lista.add(obj);
            }
            rsBuscar.close();
            ps.close();

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
                Logger.getLogger(ClientesModelo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        rs.setListaCliente(lista);
        rs.setRespuesta(respuesta);
        return rs;
    }

    /**
     *
     *
     * @param cliente
     * @return
     */
    public Respuesta registroClientes(Clientes cliente) {

        Respuesta respuesta = new Respuesta();
        Connection con = null;
        int registro = 0;
        int idUsuarioModifica = TraeDatoSesion.traerIdUsuario();
        try {
            con = PoolDB.getConnection("ACTIVACION");
            PreparedStatement ps = con.prepareStatement("INSERT INTO C_CLIENTES (NUM_CLIENTE,"
                    + "NOMBRE_CLIENTE, ID_DISTRIBUIDOR, ID_CIUDAD, TEL_CONTACTO, RFC, CALLE, NUM_EXT, "
                    + "COLONIA, CP, ACTIVO, ID_USUARIO_MODIFICA) VALUES (?,?,?,?,?,?,?,?,?,?,1,?)");
            ps.setString(1, cliente.getNumCliente());
            ps.setString(2, cliente.getNombreCliente());
            ps.setInt(3, cliente.getIdDistribuidor());
            ps.setInt(4, cliente.getIdCiudad());
            ps.setString(5, cliente.getTelContacto());
            ps.setString(6, cliente.getRfc());
            ps.setString(7, cliente.getCalle());
            ps.setString(8, cliente.getNumExt());
            ps.setString(9, cliente.getColonia());
            ps.setString(10, cliente.getCp());
            ps.setInt(11, idUsuarioModifica);
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

        return respuesta;
    }

    public Respuesta eliminarClientes(int idCliente) {

        Respuesta respuesta = new Respuesta();
        Connection con = null;
        int registro = 0;
        try {
            con = PoolDB.getConnection("ACTIVACION");
            PreparedStatement ps = con.prepareStatement("DELETE FROM C_CLIENTES WHERE ID_CLIENTE = ? ");
            ps.setInt(1, idCliente);
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

        return respuesta;
    }

    public Respuesta modificarClientes(Clientes cliente) {

        Respuesta respuesta = new Respuesta();
        Connection con = null;
        int registro = 0;
        int idUsuarioModifica = TraeDatoSesion.traerIdUsuario();

        try {
            con = PoolDB.getConnection("ACTIVACION");
            PreparedStatement ps = con.prepareStatement("UPDATE C_CLIENTES SET NUM_CLIENTE = ?, NOMBRE_CLIENTE = ?,"
                    + "ID_DISTRIBUIDOR = ?, ID_CIUDAD = ?, TEL_CONTACTO = ?, RFC = ?, CALLE = ?, NUM_EXT = ?,"
                    + "COLONIA = ?, CP = ?, ACTIVO = ?, ID_USUARIO_MODIFICA = ? WHERE ID_CLIENTE = ?");
            ps.setString(1, cliente.getNumCliente());
            ps.setString(2, cliente.getNombreCliente());
            ps.setInt(3, cliente.getIdDistribuidor());
            ps.setInt(4, cliente.getIdCiudad());
            ps.setString(5, cliente.getTelContacto());
            ps.setString(6, cliente.getRfc());
            ps.setString(7, cliente.getCalle());
            ps.setString(8, cliente.getNumExt());
            ps.setString(9, cliente.getColonia());
            ps.setString(10, cliente.getCp());
            ps.setInt(11, cliente.getActivo());
            ps.setInt(12, idUsuarioModifica);
            ps.setInt(13, cliente.getIdCliente());
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

        return respuesta;
    }
}

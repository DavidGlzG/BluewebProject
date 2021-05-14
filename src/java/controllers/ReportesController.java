package controllers;

import entities.SAplicaciones;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import objetos.ReportesCClientes;
import static sun.jvm.hotspot.HelloWorld.e;
import utils.LocalEntityManagerFactory;

/**
 *
 * @author David Gonzalez
 */
public class ReportesController implements Serializable {

    public ReportesController() {
        this.emf = LocalEntityManagerFactory.getEntityManagerFactory();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<ReportesCClientes> traerListaReportesCClientes(int idUsuario) {
        List<ReportesCClientes> listaReportesCClientes = new ArrayList<>();
        ReportesCClientes reportesCClientes = new ReportesCClientes();
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("stp_SelectCClientesDavid");
            storedProcedure.registerStoredProcedureParameter("idUsuario", Integer.class, ParameterMode.IN);
            storedProcedure.setParameter("idUsuario", idUsuario);
            storedProcedure.execute();
            List<Object[]> listaResultados = storedProcedure.getResultList();

            for (Object[] obj : listaResultados) {
                reportesCClientes.setNumCliente(obj[0].toString());
                reportesCClientes.setNombreCliente(obj[1].toString());
                reportesCClientes.setTelContacto(obj[2].toString());
                reportesCClientes.setNombreDistribuidor(obj[3].toString());
                reportesCClientes.setDescripcionCiudad(obj[4].toString());
                listaReportesCClientes.add(reportesCClientes);
                reportesCClientes = new ReportesCClientes();
            }

        } catch (Exception e) {
            Logger.getLogger(ReportesController.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return listaReportesCClientes;
    }

}

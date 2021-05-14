
package beans;

import controllers.ReportesController;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import objetos.ReportesCClientes;

/**
 *
 * @author David Gonzalez
 */
@ManagedBean
@ViewScoped
public class ReportesBean implements Serializable{
    private List<ReportesCClientes> listaResportesCClientes;
    private int idUsuario;
    
    /**
     * Metodo que lista todos los datos de un reporte de cliente.
     */
    public void listarReportesCClientes(){
        ReportesController modelo = new ReportesController();
        try {
            listaResportesCClientes = modelo.traerListaReportesCClientes(idUsuario);
        } catch (Exception e) {
            Logger.getLogger(ReportesBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

//<editor-fold defaultstate="collapsed" desc="Get Set">
    public List<ReportesCClientes> getListaResportesCClientes() {
        return listaResportesCClientes;
    }
    
    public void setListaResportesCClientes(List<ReportesCClientes> listaResportesCClientes) {
        this.listaResportesCClientes = listaResportesCClientes;
    }
    
    public int getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
//</editor-fold>
    
}

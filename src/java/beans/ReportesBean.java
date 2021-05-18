package beans;

import controllers.HActivacionJpaController;
import controllers.ReportesController;
import entities.HActivacion;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import objetos.ReportesCClientes;

/**
 *
 * @author David Gonzalez
 */
@ManagedBean
@ViewScoped
public class ReportesBean implements Serializable {

    private List<ReportesCClientes> listaResportesCClientes;
    private List<HActivacion> listaHActivacion;
    private int idUsuario;
    private Date fechaInicio;
    private Date fechaFinal;

    /**
     * Metodo que lista todos los datos de un reporte de cliente toamdo como
     * valor un id de usuario.
     */
    public void listarReportesCClientes() {
        ReportesController modelo = new ReportesController();
        try {
            listaResportesCClientes = modelo.traerListaReportesCClientes(idUsuario);
        } catch (Exception e) {
            Logger.getLogger(ReportesBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Metodo que lista todos los datos de un reporte de activaciones tomando
     * cono valores un rango de fechas.
     */
    public void rangoFechas() {
        HActivacionJpaController modelo = new HActivacionJpaController();
        Calendar c = Calendar.getInstance();
        if (fechaInicio.before(fechaFinal)) {
            Date fechaMasDia = fechaFinal;
            c.setTime(fechaMasDia);
            c.add(Calendar.DATE, 1);
            fechaMasDia = c.getTime();
            try {
                listaHActivacion = modelo.trarReporteHActivacion(fechaInicio, fechaMasDia);
            } catch (Exception e) {
                Logger.getLogger(ReportesBean.class.getName()).log(Level.SEVERE, null, e);
            }
        } else {
            FacesMessage msg = new FacesMessage("Fecha Final debe ser mayor que Fecha Inicial", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public List<HActivacion> getListaHActivacion() {
        return listaHActivacion;
    }

    public void setListaHActivacion(List<HActivacion> listaHActivacion) {
        this.listaHActivacion = listaHActivacion;
    }
//</editor-fold>
}

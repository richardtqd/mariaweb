package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class EstudianteAlergiasRepBean implements Serializable {

    private String tipoAlergia;
    private String alergia;
    private JRBeanCollectionDataSource listaNacimiento;
    private JRBeanCollectionDataSource listaVacunas;
    private JRBeanCollectionDataSource listaMedicamentos;
    private JRBeanCollectionDataSource listaEnfermedades;
    private JRBeanCollectionDataSource listaTraumas;
    private JRBeanCollectionDataSource listaEnfermedadPadres;
   
    
    public String getTipoAlergia() {
        return tipoAlergia;
    }

    public void setTipoAlergia(String tipoAlergia) {
        this.tipoAlergia = tipoAlergia;
    }

    public String getAlergia() {
        return alergia;
    }

    public void setAlergia(String alergia) {
        this.alergia = alergia;
    }

    public JRBeanCollectionDataSource getListaNacimiento() {
        return listaNacimiento;
    }

    public void setListaNacimiento(List<EstudianteNacimientoRepBean> listaNacimiento) {
        this.listaNacimiento = new JRBeanCollectionDataSource(listaNacimiento);
    }

    public JRBeanCollectionDataSource getListaVacunas() {
        return listaVacunas;
    }

    public void setListaVacunas(List<EstudianteVacunasRepBean> listaVacunas) {
        this.listaVacunas = new JRBeanCollectionDataSource(listaVacunas);
    }

    public JRBeanCollectionDataSource getListaMedicamentos() {
        return listaMedicamentos;
    } 
    
    public void setListaMedicamentos(List<EstudianteMedicamentosRepBean> listaMedicamentos) {
        this.listaMedicamentos = new JRBeanCollectionDataSource(listaMedicamentos);
    } 
    
    
    public JRBeanCollectionDataSource getListaEnfermedades() {
        return listaEnfermedades;
    } 
    
    public void setListaEnfermedades(List<EstudianteEnfermedadRepBean> listaEnfermedades) {
        this.listaEnfermedades = new JRBeanCollectionDataSource(listaEnfermedades);
    }
    
    public JRBeanCollectionDataSource getListaTraumas() {
        return listaTraumas;
    } 
    
    public void setListaTraumas(List<EstudianteTraumaRepBean> listaTraumas) {
        this.listaTraumas = new JRBeanCollectionDataSource(listaTraumas);
    }

    public JRBeanCollectionDataSource getListaEnfermedadPadres() {
        return listaEnfermedadPadres;
    }

    public void setListaEnfermedadPadres(List<EstudianteEnfermedadPadresRepBean> listaEnfermedadPadres) {
        this.listaEnfermedadPadres = new JRBeanCollectionDataSource(listaEnfermedadPadres);
    }
}

package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class EstadoRecibosRepBean implements Serializable {
    private Integer nroAlumnos;
    private Integer anio;
    private Integer nroRecibosImpresos;
    private Integer nroRecibosNoImpresos;
    private String mes;
    private Integer cantPagados;
    private Integer cantSinServicio;
    private Integer cantDeben;
    private String nombreUniNeg;
    private JRBeanCollectionDataSource listaDetalle;
    private String uniNeg;

    
    private JRBeanCollectionDataSource listaVerificacionPlanilla;
    private JRBeanCollectionDataSource listaCTS;
    
    public Integer getNroAlumnos() {
        return nroAlumnos;
    }

    public void setNroAlumnos(Integer nroAlumnos) {
        this.nroAlumnos = nroAlumnos;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getNroRecibosImpresos() {
        return nroRecibosImpresos;
    }

    public void setNroRecibosImpresos(Integer nroRecibosImpresos) {
        this.nroRecibosImpresos = nroRecibosImpresos;
    }

    public Integer getNroRecibosNoImpresos() {
        return nroRecibosNoImpresos;
    }

    public void setNroRecibosNoImpresos(Integer nroRecibosNoImpresos) {
        this.nroRecibosNoImpresos = nroRecibosNoImpresos;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Integer getCantPagados() {
        return cantPagados;
    }

    public void setCantPagados(Integer cantPagados) {
        this.cantPagados = cantPagados;
    }

    public Integer getCantSinServicio() {
        return cantSinServicio;
    }

    public void setCantSinServicio(Integer cantSinServicio) {
        this.cantSinServicio = cantSinServicio;
    }

    public Integer getCantDeben() {
        return cantDeben;
    }

    public void setCantDeben(Integer cantDeben) {
        this.cantDeben = cantDeben;
    }

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public JRBeanCollectionDataSource getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<EstadoRecibosRepBean> listaDetalle) {
        this.listaDetalle = new JRBeanCollectionDataSource(listaDetalle);
    } 

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public JRBeanCollectionDataSource getListaVerificacionPlanilla() {
        return listaVerificacionPlanilla;
    }

    public void setListaVerificacionPlanilla(List<VerificacionIngresoPlanillaRepBean> listaVerificacionPlanilla) {
        this.listaVerificacionPlanilla = new JRBeanCollectionDataSource(listaVerificacionPlanilla);
    }

    public JRBeanCollectionDataSource getListaCTS() {
        return listaCTS;
    }

    public void setListaCTS(List<VerificacionIngresoPlanillaRepBean> listaCTS) {
        this.listaCTS = new JRBeanCollectionDataSource(listaCTS);
    }
}

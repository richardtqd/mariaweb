package pe.marista.sigma.bean.reporte;

import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class PlanillaPersonalCRRepBean {
    private String nombreUniNeg;
    private String titulo;
    private String titulo2;
    private String rucColegio;
    private String nombreCompleto;
    private String cr1;
    private String cr2;
    private String cr3;
    private String cr4;
    private String cr5; 
    private String cr1Porc;
    private String cr2Porc;
    private String cr3Porc;
    private String cr4Porc;
    private String cr5Porc;
    private Integer sumaPorc;
    private JRBeanCollectionDataSource listaDetPlanilla;
    private JRBeanCollectionDataSource listaSubDetalle;
    private JRBeanCollectionDataSource listaSinNada;
    private Integer id;
    private Integer idPersonal;
    private String tipoNivelesIns;
    private String codPer;
    private Integer cantidad;
    private JRBeanCollectionDataSource listaTotales;

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getRucColegio() {
        return rucColegio;
    }

    public void setRucColegio(String rucColegio) {
        this.rucColegio = rucColegio;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCr1() {
        return cr1;
    }

    public void setCr1(String cr1) {
        this.cr1 = cr1;
    }

    public String getCr2() {
        return cr2;
    }

    public void setCr2(String cr2) {
        this.cr2 = cr2;
    }

    public String getCr3() {
        return cr3;
    }

    public void setCr3(String cr3) {
        this.cr3 = cr3;
    }

    public String getCr4() {
        return cr4;
    }

    public void setCr4(String cr4) {
        this.cr4 = cr4;
    }

    public String getCr5() {
        return cr5;
    }

    public void setCr5(String cr5) {
        this.cr5 = cr5;
    }

    public String getCr1Porc() {
        return cr1Porc;
    }

    public void setCr1Porc(String cr1Porc) {
        this.cr1Porc = cr1Porc;
    }

    public String getCr2Porc() {
        return cr2Porc;
    }

    public void setCr2Porc(String cr2Porc) {
        this.cr2Porc = cr2Porc;
    }

    public String getCr3Porc() {
        return cr3Porc;
    }

    public void setCr3Porc(String cr3Porc) {
        this.cr3Porc = cr3Porc;
    }

    public String getCr4Porc() {
        return cr4Porc;
    }

    public void setCr4Porc(String cr4Porc) {
        this.cr4Porc = cr4Porc;
    }

    public String getCr5Porc() {
        return cr5Porc;
    }

    public void setCr5Porc(String cr5Porc) {
        this.cr5Porc = cr5Porc;
    }

    public Integer getSumaPorc() {
        return sumaPorc;
    }

    public void setSumaPorc(Integer sumaPorc) {
        this.sumaPorc = sumaPorc;
    }

    public JRBeanCollectionDataSource getListaDetPlanilla() {
        return listaDetPlanilla;
    }

    public void setListaDetPlanilla(List<PlanillaPersonalCRRepBean> listaDetPlanilla) {
        this.listaDetPlanilla = new JRBeanCollectionDataSource(listaDetPlanilla);
    } 

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(Integer idPersonal) {
        this.idPersonal = idPersonal;
    }

    public String getTipoNivelesIns() {
        return tipoNivelesIns;
    }

    public void setTipoNivelesIns(String tipoNivelesIns) {
        this.tipoNivelesIns = tipoNivelesIns;
    }

    public JRBeanCollectionDataSource getListaSubDetalle() {
        return listaSubDetalle;
    }

    public void setListaSubDetalle(List<PlanillaPersonalCRRepBean> listaSubDetalle) {
        this.listaSubDetalle = new JRBeanCollectionDataSource(listaSubDetalle);
    }

    public String getCodPer() {
        return codPer;
    }

    public void setCodPer(String codPer) {
        this.codPer = codPer;
    }

    public JRBeanCollectionDataSource getListaSinNada() {
        return listaSinNada;
    }

    public void setListaSinNada(List<PlanillaPersonalCRRepBean> listaSinNada) {
        this.listaSinNada = new JRBeanCollectionDataSource(listaSinNada);
    }

    public String getTitulo2() {
        return titulo2;
    }

    public void setTitulo2(String titulo2) {
        this.titulo2 = titulo2;
    }
    
    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public JRBeanCollectionDataSource getListaTotales() {
        return listaTotales;
    }

    public void setListaTotales(List<PlanillaPersonalCRRepBean> listaTotales) {
        this.listaTotales = new JRBeanCollectionDataSource(listaTotales);
    }
    
}

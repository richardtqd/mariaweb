package pe.marista.sigma.bean.reporte;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import java.util.List;

public class PlanillaCtsReapBean {

    private String nombreUniNeg;
    private String ruc;
    private String codigo;
    private Integer idCodigo;
    private String empleado;
    private Integer idPlanillaCts;
    private String cr;
    private String monto;
    private JRBeanCollectionDataSource listaSubDetalle;
    private JRBeanCollectionDataSource listaDetalle;
    private String total;
    private String anioPeriodo;

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getIdCodigo() {
        return idCodigo;
    }

    public void setIdCodigo(Integer idCodigo) {
        this.idCodigo = idCodigo;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public Integer getIdPlanillaCts() {
        return idPlanillaCts;
    }

    public void setIdPlanillaCts(Integer idPlanillaCts) {
        this.idPlanillaCts = idPlanillaCts;
    }

    public String getCr() {
        return cr;
    }

    public void setCr(String cr) {
        this.cr = cr;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public JRBeanCollectionDataSource getListaSubDetalle() {
        return listaSubDetalle;
    }

    public void setListaSubDetalle(List<PlanillaCtsReapBean> listaSubDetalle) {
        this.listaSubDetalle = new JRBeanCollectionDataSource(listaSubDetalle);
    }

    public JRBeanCollectionDataSource getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<PlanillaCtsReapBean> listaDetalle) {
        this.listaDetalle = new JRBeanCollectionDataSource(listaDetalle);
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAnioPeriodo() {
        return anioPeriodo;
    }

    public void setAnioPeriodo(String anioPeriodo) {
        this.anioPeriodo = anioPeriodo;
    }

}

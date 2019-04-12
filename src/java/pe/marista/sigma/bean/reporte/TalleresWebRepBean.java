package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class TalleresWebRepBean implements Serializable{
    private String nrodoc;
    private String codigo;
    private String servicio;
    private String fecha;
    private String discente;
    private String glosa1;
    private String glosa2;
    private String montoPagado;
    private String montoPagadoLetras;
    private String banco;
    private String simbolo;
    private String nombreUniNeg;
    private String ruc;
    private String direccion;
    private String correo;
    private String nombreRecaudo;
    private String codRecaudo;
    private JRBeanCollectionDataSource listaDetalle;
    private String cantidad;
    private String ref;
    private String monto;
    private String dsctoRef;
    private String dscto; 
    public String getNrodoc() {
        return nrodoc;
    }

    public void setNrodoc(String nrodoc) {
        this.nrodoc = nrodoc;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDiscente() {
        return discente;
    }

    public void setDiscente(String discente) {
        this.discente = discente;
    }

    public String getGlosa1() {
        return glosa1;
    }

    public void setGlosa1(String glosa1) {
        this.glosa1 = glosa1;
    }

    public String getGlosa2() {
        return glosa2;
    }

    public void setGlosa2(String glosa2) {
        this.glosa2 = glosa2;
    }

    public String getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(String montoPagado) {
        this.montoPagado = montoPagado;
    }

    public String getMontoPagadoLetras() {
        return montoPagadoLetras;
    }

    public void setMontoPagadoLetras(String montoPagadoLetras) {
        this.montoPagadoLetras = montoPagadoLetras;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreRecaudo() {
        return nombreRecaudo;
    }

    public void setNombreRecaudo(String nombreRecaudo) {
        this.nombreRecaudo = nombreRecaudo;
    }

    public String getCodRecaudo() {
        return codRecaudo;
    }

    public void setCodRecaudo(String codRecaudo) {
        this.codRecaudo = codRecaudo;
    }

    public JRBeanCollectionDataSource getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<TalleresWebRepBean> listaDetalle) {
        this.listaDetalle = new JRBeanCollectionDataSource(listaDetalle);
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getDsctoRef() {
        return dsctoRef;
    }

    public void setDsctoRef(String dsctoRef) {
        this.dsctoRef = dsctoRef;
    }

    public String getDscto() {
        return dscto;
    }

    public void setDscto(String dscto) {
        this.dscto = dscto;
    }
  

    
}

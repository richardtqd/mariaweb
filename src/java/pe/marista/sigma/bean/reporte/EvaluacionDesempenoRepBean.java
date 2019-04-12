package pe.marista.sigma.bean.reporte;

import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class EvaluacionDesempenoRepBean {

    private String nombreUniNeg;
    private String titulo;
    private String hecho;
    private String nombreEvaluador;
    private String nombreEvaluado;
    private String cargoEvaluador;
    private String cargoEvaluado;
    private String grupoOcupacionalUNivelEvaluador;
    private String grupoOcupacionalUNivelEvaluado;
    private String cabecera;

    private Integer nroEva;
    private Integer aplicado;
    private Integer pendiente;
    private String usuario;
    private String estado;

    //Reporte Sin evaluacion de desempeño
    private String nombreCompleto;
    private Integer anio;
    private String condicion;

    private JRBeanCollectionDataSource listaGeneral;

    private String codigoEvaluado;
    private String codigoEvaluador;

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

    public String getHecho() {
        return hecho;
    }

    public void setHecho(String hecho) {
        this.hecho = hecho;
    }

    public String getNombreEvaluador() {
        return nombreEvaluador;
    }

    public void setNombreEvaluador(String nombreEvaluador) {
        this.nombreEvaluador = nombreEvaluador;
    }

    public String getNombreEvaluado() {
        return nombreEvaluado;
    }

    public void setNombreEvaluado(String nombreEvaluado) {
        this.nombreEvaluado = nombreEvaluado;
    }

    public String getCargoEvaluador() {
        return cargoEvaluador;
    }

    public void setCargoEvaluador(String cargoEvaluador) {
        this.cargoEvaluador = cargoEvaluador;
    }

    public String getCargoEvaluado() {
        return cargoEvaluado;
    }

    public void setCargoEvaluado(String cargoEvaluado) {
        this.cargoEvaluado = cargoEvaluado;
    }

    public String getGrupoOcupacionalUNivelEvaluador() {
        return grupoOcupacionalUNivelEvaluador;
    }

    public void setGrupoOcupacionalUNivelEvaluador(String grupoOcupacionalUNivelEvaluador) {
        this.grupoOcupacionalUNivelEvaluador = grupoOcupacionalUNivelEvaluador;
    }

    public String getGrupoOcupacionalUNivelEvaluado() {
        return grupoOcupacionalUNivelEvaluado;
    }

    public void setGrupoOcupacionalUNivelEvaluado(String grupoOcupacionalUNivelEvaluado) {
        this.grupoOcupacionalUNivelEvaluado = grupoOcupacionalUNivelEvaluado;
    }

    public String getCabecera() {
        return cabecera;
    }

    public void setCabecera(String cabecera) {
        this.cabecera = cabecera;
    }

    public Integer getNroEva() {
        return nroEva;
    }

    public void setNroEva(Integer nroEva) {
        this.nroEva = nroEva;
    }

    public Integer getAplicado() {
        return aplicado;
    }

    public void setAplicado(Integer aplicado) {
        this.aplicado = aplicado;
    }

    public Integer getPendiente() {
        return pendiente;
    }

    public void setPendiente(Integer pendiente) {
        this.pendiente = pendiente;
    }

    public JRBeanCollectionDataSource getListaGeneral() {
        return listaGeneral;
    }

    public void setListaGeneral(List<SeguimientoEDRepBean> listaGeneral) {
        this.listaGeneral = new JRBeanCollectionDataSource(listaGeneral);
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getCodigoEvaluado() {
        return codigoEvaluado;
    }

    public void setCodigoEvaluado(String codigoEvaluado) {
        this.codigoEvaluado = codigoEvaluado;
    }

    public String getCodigoEvaluador() {
        return codigoEvaluador;
    }

    public void setCodigoEvaluador(String codigoEvaluador) {
        this.codigoEvaluador = codigoEvaluador;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author MS002
 */
public class DocIngresoRepBean implements Serializable {

    private String nombre;
    private String ruc;
    private String direccion;
    private String nomDistrito;
    private String telefono;
    private String correo;
    private Integer idDocIngreso;
    private String codigo;
    private String serie;
    private String nroDoc;
    private String idDiscente;
    private String discente;
    private String rucDiscente;
    private String codEstudiante;
    private String seccion;
    private Integer idGradoAcademico;
    private String nombreGrado;
    private String nombreNivel;
    private String idResPago;
    private String resPago;
    private String dni;
    private String rucDoc;
    private Integer anio;
    private String fechaPago;
    private String lugarPago;
    private String modoPago;
    private Integer idEstudianteBeca;
    private String caja;
    private String textoMonto;
    private JRBeanCollectionDataSource listaDetalle;

    //ayuda
    private String concepto;
    private String moneda;
    private String serieNroDoc;
    private String fechaStr;
    private String fecha;
    private Double montoPagado;
    private String correspondientea;
    private String fechaVenc;
    private String tipoBeca;
    private String tipoDoc;
    private String pagante;
    private String becado;
    private String colegio;
    private String banco;
    private String montoPagadoVista;
    private String montoPensionVista;
    private String moraVista;
    private String nomConcepto;

    //para los union all
    private Integer mora;
    private Integer dscto;
    private Integer beca;
    private Integer flgDscto;

    private String infoRecibo;
    private Integer infoMonto;

    private String numOperacion;
    private String qr;

    //Ayuda Recibos
    private Integer idRecibosMora;
    private Integer mes;
    private String idEstudiante;
    private String serieNroDocMora;
    
    private String referencial;
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getNomDistrito() {
        return nomDistrito;
    }

    public void setNomDistrito(String nomDistrito) {
        this.nomDistrito = nomDistrito;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getIdDocIngreso() {
        return idDocIngreso;
    }

    public void setIdDocIngreso(Integer idDocIngreso) {
        this.idDocIngreso = idDocIngreso;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public String getIdDiscente() {
        return idDiscente;
    }

    public void setIdDiscente(String idDiscente) {
        this.idDiscente = idDiscente;
    }

    public String getDiscente() {
        return discente;
    }

    public void setDiscente(String discente) {
        this.discente = discente;
    }

    public String getRucDiscente() {
        return rucDiscente;
    }

    public void setRucDiscente(String rucDiscente) {
        this.rucDiscente = rucDiscente;
    }

    public String getCodEstudiante() {
        return codEstudiante;
    }

    public void setCodEstudiante(String codEstudiante) {
        this.codEstudiante = codEstudiante;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public Integer getIdGradoAcademico() {
        return idGradoAcademico;
    }

    public void setIdGradoAcademico(Integer idGradoAcademico) {
        this.idGradoAcademico = idGradoAcademico;
    }

    public String getNombreGrado() {
        return nombreGrado;
    }

    public void setNombreGrado(String nombreGrado) {
        this.nombreGrado = nombreGrado;
    }

    public String getNombreNivel() {
        return nombreNivel;
    }

    public void setNombreNivel(String nombreNivel) {
        this.nombreNivel = nombreNivel;
    }

    public String getIdResPago() {
        return idResPago;
    }

    public void setIdResPago(String idResPago) {
        this.idResPago = idResPago;
    }

    public String getResPago() {
        return resPago;
    }

    public void setResPago(String resPago) {
        this.resPago = resPago;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getRucDoc() {
        return rucDoc;
    }

    public void setRucDoc(String rucDoc) {
        this.rucDoc = rucDoc;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getLugarPago() {
        return lugarPago;
    }

    public void setLugarPago(String lugarPago) {
        this.lugarPago = lugarPago;
    }

    public String getModoPago() {
        return modoPago;
    }

    public void setModoPago(String modoPago) {
        this.modoPago = modoPago;
    }

    public Integer getIdEstudianteBeca() {
        return idEstudianteBeca;
    }

    public void setIdEstudianteBeca(Integer idEstudianteBeca) {
        this.idEstudianteBeca = idEstudianteBeca;
    }

    public JRBeanCollectionDataSource getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<DetDocIngresoRepBean> listaDetalle) {
        this.listaDetalle = new JRBeanCollectionDataSource(listaDetalle);
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }

    public String getTextoMonto() {
        return textoMonto;
    }

    public void setTextoMonto(String textoMonto) {
        this.textoMonto = textoMonto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getSerieNroDoc() {
        return serieNroDoc;
    }

    public void setSerieNroDoc(String serieNroDoc) {
        this.serieNroDoc = serieNroDoc;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(Double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getCorrespondientea() {
        return correspondientea;
    }

    public void setCorrespondientea(String correspondientea) {
        this.correspondientea = correspondientea;
    }

    public String getFechaVenc() {
        return fechaVenc;
    }

    public void setFechaVenc(String fechaVenc) {
        this.fechaVenc = fechaVenc;
    }

    public String getTipoBeca() {
        return tipoBeca;
    }

    public void setTipoBeca(String tipoBeca) {
        this.tipoBeca = tipoBeca;
    }

    public Integer getMora() {
        return mora;
    }

    public void setMora(Integer mora) {
        this.mora = mora;
    }

    public Integer getDscto() {
        return dscto;
    }

    public void setDscto(Integer dscto) {
        this.dscto = dscto;
    }

    public Integer getBeca() {
        return beca;
    }

    public void setBeca(Integer beca) {
        this.beca = beca;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getPagante() {
        return pagante;
    }

    public void setPagante(String pagante) {
        this.pagante = pagante;
    }

    public String getBecado() {
        return becado;
    }

    public void setBecado(String becado) {
        this.becado = becado;
    }

    public String getColegio() {
        return colegio;
    }

    public void setColegio(String colegio) {
        this.colegio = colegio;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getMontoPagadoVista() {
        return montoPagadoVista;
    }

    public void setMontoPagadoVista(String montoPagadoVista) {
        this.montoPagadoVista = montoPagadoVista;
    }

    public String getNomConcepto() {
        return nomConcepto;
    }

    public void setNomConcepto(String nomConcepto) {
        this.nomConcepto = nomConcepto;
    }

    public Integer getFlgDscto() {
        return flgDscto;
    }

    public void setFlgDscto(Integer flgDscto) {
        this.flgDscto = flgDscto;
    }

    public String getInfoRecibo() {
        return infoRecibo;
    }

    public void setInfoRecibo(String infoRecibo) {
        this.infoRecibo = infoRecibo;
    }

    public Integer getInfoMonto() {
        return infoMonto;
    }

    public void setInfoMonto(Integer infoMonto) {
        this.infoMonto = infoMonto;
    }

    public String getNumOperacion() {
        return numOperacion;
    }

    public void setNumOperacion(String numOperacion) {
        this.numOperacion = numOperacion;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public String getFechaStr() {
        return fechaStr;
    }

    public void setFechaStr(String fechaStr) {
        this.fechaStr = fechaStr;
    }

    public String getMontoPensionVista() {
        return montoPensionVista;
    }

    public void setMontoPensionVista(String montoPensionVista) {
        this.montoPensionVista = montoPensionVista;
    }

    public String getMoraVista() {
        return moraVista;
    }

    public void setMoraVista(String moraVista) {
        this.moraVista = moraVista;
    }

    public Integer getIdRecibosMora() {
        return idRecibosMora;
    }

    public void setIdRecibosMora(Integer idRecibosMora) {
        this.idRecibosMora = idRecibosMora;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getSerieNroDocMora() {
        return serieNroDocMora;
    }

    public void setSerieNroDocMora(String serieNroDocMora) {
        this.serieNroDocMora = serieNroDocMora;
    }

    public String getReferencial() {
        return referencial;
    }

    public void setReferencial(String referencial) {
        this.referencial = referencial;
    }

}

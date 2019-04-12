/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;

/**
 *
 * @author MS002
 */
public class DocIngresoABRepBean implements Serializable {

    private String nombre;
    private String ruc;
    private String direccion;
    private String nomDistrito;
    private String telefono;
    private String correo;
    private String formato;

    //LADO A
    private Integer idDocIngresoA;
    private String codigoA;
    private String serieA;
    private String nroDocA;
    private String idDiscenteA;
    private String discenteA;
    private String rucDiscenteA;
    private String codEstudianteA;
    private String seccionA;
    private Integer idGradoAcademicoA;
    private String nombreGradoA;
    private String nombreNivelA;
    private String idResPagoA;
    private String resPagoA;
    private String dniA;
    private String rucDocA;
    private Integer anioA;
    private String fechaPagoA;
    private String lugarPagoA;
    private String modoPagoA;
    private Integer idEstudianteBecaA;
    private String cajaA;
    private String textoMontoA;
    //ayuda
    private String conceptoA;
    private String monedaA;
    private String serieNroDocA;
    private String fechaStrA;
    private String fechaA;
    private Double montoPagadoA;
    private String correspondienteaA;
    private String fechaVencA;
    private String tipoBecaA;
    private String tipoDocA;
    private String paganteA;
    private String becadoA;
    private String colegioA;
    private String bancoA;
    private String montoPagadoVistaA;
    private String montoPensionVistaA;
    private String moraVistaA;
    private String nomConceptoA;
    //para los union all
    private Integer moraA;
    private Integer dsctoA;
    private Integer becaA;
    private Integer flgDsctoA;
    private String infoReciboA;
    private Integer infoMontoA;
    private String numOperacionA;
    private String qrA;

    //LADO B
    private Integer idDocIngresoB;
    private String codigoB;
    private String serieB;
    private String nroDocB;
    private String idDiscenteB;
    private String discenteB;
    private String rucDiscenteB;
    private String codEstudianteB;
    private String seccionB;
    private Integer idGradoAcademicoB;
    private String nombreGradoB;
    private String nombreNivelB;
    private String idResPagoB;
    private String resPagoB;
    private String dniB;
    private String rucDocB;
    private Integer anioB;
    private String fechaPagoB;
    private String lugarPagoB;
    private String modoPagoB;
    private Integer idEstudianteBecaB;
    private String cajaB;
    private String textoMontoB;
    //ayuda
    private String conceptoB;
    private String monedaB;
    private String serieNroDocB;
    private String fechaStrB;
    private String fechaB;
    private Double montoPagadoB;
    private String correspondienteaB;
    private String fechaVencB;
    private String tipoBecaB;
    private String tipoDocB;
    private String paganteB;
    private String becadoB;
    private String colegioB;
    private String bancoB;
    private String montoPagadoVistaB;
    private String montoPensionVistaB;
    private String moraVistaB;
    private String nomConceptoB;
    //para los union all
    private Integer moraB;
    private Integer dsctoB;
    private Integer becaB;
    private Integer flgDsctoB;
    private String infoReciboB;
    private Integer infoMontoB;
    private String numOperacionB;
    private String qrB;

    //detalle
    private Integer cuentaDA;
    private String montoVistaA;
    private String cuentaDsctoBecaA;
    private String labelDsctoBecaA;
    private String dsctobecaA;

    private Integer cuentaDB;
    private String montoVistaB;
    private String cuentaDsctoBecaB;
    private String labelDsctoBecaB;
    private String dsctobecaB;
    private String fechaPago;

    //reciboMora
    private Integer idRecibosMoraA;
    private Integer idRecibosMoraB;
    
    private String serieNroDocMoraA;
    private String serieNroDocMoraB;
    private String referencialA;
    private String referencialB;
    
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

    ///////////////////////INICIO LADO A//////////////////////////
    public Integer getIdDocIngresoA() {
        return idDocIngresoA;
    }

    public void setIdDocIngresoA(Integer idDocIngresoA) {
        this.idDocIngresoA = idDocIngresoA;
    }

    public String getCodigoA() {
        return codigoA;
    }

    public void setCodigoA(String codigoA) {
        this.codigoA = codigoA;
    }

    public String getSerieA() {
        return serieA;
    }

    public void setSerieA(String serieA) {
        this.serieA = serieA;
    }

    public String getNroDocA() {
        return nroDocA;
    }

    public void setNroDocA(String nroDocA) {
        this.nroDocA = nroDocA;
    }

    public String getIdDiscenteA() {
        return idDiscenteA;
    }

    public void setIdDiscenteA(String idDiscenteA) {
        this.idDiscenteA = idDiscenteA;
    }

    public String getDiscenteA() {
        return discenteA;
    }

    public void setDiscenteA(String discenteA) {
        this.discenteA = discenteA;
    }

    public String getRucDiscenteA() {
        return rucDiscenteA;
    }

    public void setRucDiscenteA(String rucDiscenteA) {
        this.rucDiscenteA = rucDiscenteA;
    }

    public String getCodEstudianteA() {
        return codEstudianteA;
    }

    public void setCodEstudianteA(String codEstudianteA) {
        this.codEstudianteA = codEstudianteA;
    }

    public String getSeccionA() {
        return seccionA;
    }

    public void setSeccionA(String seccionA) {
        this.seccionA = seccionA;
    }

    public Integer getIdGradoAcademicoA() {
        return idGradoAcademicoA;
    }

    public void setIdGradoAcademicoA(Integer idGradoAcademicoA) {
        this.idGradoAcademicoA = idGradoAcademicoA;
    }

    public String getNombreGradoA() {
        return nombreGradoA;
    }

    public void setNombreGradoA(String nombreGradoA) {
        this.nombreGradoA = nombreGradoA;
    }

    public String getNombreNivelA() {
        return nombreNivelA;
    }

    public void setNombreNivelA(String nombreNivelA) {
        this.nombreNivelA = nombreNivelA;
    }

    public String getIdResPagoA() {
        return idResPagoA;
    }

    public void setIdResPagoA(String idResPagoA) {
        this.idResPagoA = idResPagoA;
    }

    public String getResPagoA() {
        return resPagoA;
    }

    public void setResPagoA(String resPagoA) {
        this.resPagoA = resPagoA;
    }

    public String getDniA() {
        return dniA;
    }

    public void setDniA(String dniA) {
        this.dniA = dniA;
    }

    public String getRucDocA() {
        return rucDocA;
    }

    public void setRucDocA(String rucDocA) {
        this.rucDocA = rucDocA;
    }

    public Integer getAnioA() {
        return anioA;
    }

    public void setAnioA(Integer anioA) {
        this.anioA = anioA;
    }

    public String getFechaPagoA() {
        return fechaPagoA;
    }

    public void setFechaPagoA(String fechaPagoA) {
        this.fechaPagoA = fechaPagoA;
    }

    public String getLugarPagoA() {
        return lugarPagoA;
    }

    public void setLugarPagoA(String lugarPagoA) {
        this.lugarPagoA = lugarPagoA;
    }

    public String getModoPagoA() {
        return modoPagoA;
    }

    public void setModoPagoA(String modoPagoA) {
        this.modoPagoA = modoPagoA;
    }

    public Integer getIdEstudianteBecaA() {
        return idEstudianteBecaA;
    }

    public void setIdEstudianteBecaA(Integer idEstudianteBecaA) {
        this.idEstudianteBecaA = idEstudianteBecaA;
    }

    public String getCajaA() {
        return cajaA;
    }

    public void setCajaA(String cajaA) {
        this.cajaA = cajaA;
    }

    public String getTextoMontoA() {
        return textoMontoA;
    }

    public void setTextoMontoA(String textoMontoA) {
        this.textoMontoA = textoMontoA;
    }

    public String getConceptoA() {
        return conceptoA;
    }

    public void setConceptoA(String conceptoA) {
        this.conceptoA = conceptoA;
    }

    public String getMonedaA() {
        return monedaA;
    }

    public void setMonedaA(String monedaA) {
        this.monedaA = monedaA;
    }

    public String getSerieNroDocA() {
        return serieNroDocA;
    }

    public void setSerieNroDocA(String serieNroDocA) {
        this.serieNroDocA = serieNroDocA;
    }

    public String getFechaStrA() {
        return fechaStrA;
    }

    public void setFechaStrA(String fechaStrA) {
        this.fechaStrA = fechaStrA;
    }

    public String getFechaA() {
        return fechaA;
    }

    public void setFechaA(String fechaA) {
        this.fechaA = fechaA;
    }

    public Double getMontoPagadoA() {
        return montoPagadoA;
    }

    public void setMontoPagadoA(Double montoPagadoA) {
        this.montoPagadoA = montoPagadoA;
    }

    public String getCorrespondienteaA() {
        return correspondienteaA;
    }

    public void setCorrespondienteaA(String correspondienteaA) {
        this.correspondienteaA = correspondienteaA;
    }

    public String getFechaVencA() {
        return fechaVencA;
    }

    public void setFechaVencA(String fechaVencA) {
        this.fechaVencA = fechaVencA;
    }

    public String getTipoBecaA() {
        return tipoBecaA;
    }

    public void setTipoBecaA(String tipoBecaA) {
        this.tipoBecaA = tipoBecaA;
    }

    public String getTipoDocA() {
        return tipoDocA;
    }

    public void setTipoDocA(String tipoDocA) {
        this.tipoDocA = tipoDocA;
    }

    public String getPaganteA() {
        return paganteA;
    }

    public void setPaganteA(String paganteA) {
        this.paganteA = paganteA;
    }

    public String getBecadoA() {
        return becadoA;
    }

    public void setBecadoA(String becadoA) {
        this.becadoA = becadoA;
    }

    public String getColegioA() {
        return colegioA;
    }

    public void setColegioA(String colegioA) {
        this.colegioA = colegioA;
    }

    public String getBancoA() {
        return bancoA;
    }

    public void setBancoA(String bancoA) {
        this.bancoA = bancoA;
    }

    public String getMontoPagadoVistaA() {
        return montoPagadoVistaA;
    }

    public void setMontoPagadoVistaA(String montoPagadoVistaA) {
        this.montoPagadoVistaA = montoPagadoVistaA;
    }

    public String getMontoPensionVistaA() {
        return montoPensionVistaA;
    }

    public void setMontoPensionVistaA(String montoPensionVistaA) {
        this.montoPensionVistaA = montoPensionVistaA;
    }

    public String getMoraVistaA() {
        return moraVistaA;
    }

    public void setMoraVistaA(String moraVistaA) {
        this.moraVistaA = moraVistaA;
    }

    public String getNomConceptoA() {
        return nomConceptoA;
    }

    public void setNomConceptoA(String nomConceptoA) {
        this.nomConceptoA = nomConceptoA;
    }

    public Integer getMoraA() {
        return moraA;
    }

    public void setMoraA(Integer moraA) {
        this.moraA = moraA;
    }

    public Integer getDsctoA() {
        return dsctoA;
    }

    public void setDsctoA(Integer dsctoA) {
        this.dsctoA = dsctoA;
    }

    public Integer getBecaA() {
        return becaA;
    }

    public void setBecaA(Integer becaA) {
        this.becaA = becaA;
    }

    public Integer getFlgDsctoA() {
        return flgDsctoA;
    }

    public void setFlgDsctoA(Integer flgDsctoA) {
        this.flgDsctoA = flgDsctoA;
    }

    public String getInfoReciboA() {
        return infoReciboA;
    }

    public void setInfoReciboA(String infoReciboA) {
        this.infoReciboA = infoReciboA;
    }

    public Integer getInfoMontoA() {
        return infoMontoA;
    }

    public void setInfoMontoA(Integer infoMontoA) {
        this.infoMontoA = infoMontoA;
    }

    public String getNumOperacionA() {
        return numOperacionA;
    }

    public void setNumOperacionA(String numOperacionA) {
        this.numOperacionA = numOperacionA;
    }

    public String getQrA() {
        return qrA;
    }

    public void setQrA(String qrA) {
        this.qrA = qrA;
    }
    ///////////////////////FIN LADO A/////////////////////////////

    ///////////////////////INICIO LADO B//////////////////////////
    public Integer getIdDocIngresoB() {
        return idDocIngresoB;
    }

    public void setIdDocIngresoB(Integer idDocIngresoB) {
        this.idDocIngresoB = idDocIngresoB;
    }

    public String getCodigoB() {
        return codigoB;
    }

    public void setCodigoB(String codigoB) {
        this.codigoB = codigoB;
    }

    public String getSerieB() {
        return serieB;
    }

    public void setSerieB(String serieB) {
        this.serieB = serieB;
    }

    public String getNroDocB() {
        return nroDocB;
    }

    public void setNroDocB(String nroDocB) {
        this.nroDocB = nroDocB;
    }

    public String getIdDiscenteB() {
        return idDiscenteB;
    }

    public void setIdDiscenteB(String idDiscenteB) {
        this.idDiscenteB = idDiscenteB;
    }

    public String getDiscenteB() {
        return discenteB;
    }

    public void setDiscenteB(String discenteB) {
        this.discenteB = discenteB;
    }

    public String getRucDiscenteB() {
        return rucDiscenteB;
    }

    public void setRucDiscenteB(String rucDiscenteB) {
        this.rucDiscenteB = rucDiscenteB;
    }

    public String getCodEstudianteB() {
        return codEstudianteB;
    }

    public void setCodEstudianteB(String codEstudianteB) {
        this.codEstudianteB = codEstudianteB;
    }

    public String getSeccionB() {
        return seccionB;
    }

    public void setSeccionB(String seccionB) {
        this.seccionB = seccionB;
    }

    public Integer getIdGradoAcademicoB() {
        return idGradoAcademicoB;
    }

    public void setIdGradoAcademicoB(Integer idGradoAcademicoB) {
        this.idGradoAcademicoB = idGradoAcademicoB;
    }

    public String getNombreGradoB() {
        return nombreGradoB;
    }

    public void setNombreGradoB(String nombreGradoB) {
        this.nombreGradoB = nombreGradoB;
    }

    public String getNombreNivelB() {
        return nombreNivelB;
    }

    public void setNombreNivelB(String nombreNivelB) {
        this.nombreNivelB = nombreNivelB;
    }

    public String getIdResPagoB() {
        return idResPagoB;
    }

    public void setIdResPagoB(String idResPagoB) {
        this.idResPagoB = idResPagoB;
    }

    public String getResPagoB() {
        return resPagoB;
    }

    public void setResPagoB(String resPagoB) {
        this.resPagoB = resPagoB;
    }

    public String getDniB() {
        return dniB;
    }

    public void setDniB(String dniB) {
        this.dniB = dniB;
    }

    public String getRucDocB() {
        return rucDocB;
    }

    public void setRucDocB(String rucDocB) {
        this.rucDocB = rucDocB;
    }

    public Integer getAnioB() {
        return anioB;
    }

    public void setAnioB(Integer anioB) {
        this.anioB = anioB;
    }

    public String getFechaPagoB() {
        return fechaPagoB;
    }

    public void setFechaPagoB(String fechaPagoB) {
        this.fechaPagoB = fechaPagoB;
    }

    public String getLugarPagoB() {
        return lugarPagoB;
    }

    public void setLugarPagoB(String lugarPagoB) {
        this.lugarPagoB = lugarPagoB;
    }

    public String getModoPagoB() {
        return modoPagoB;
    }

    public void setModoPagoB(String modoPagoB) {
        this.modoPagoB = modoPagoB;
    }

    public Integer getIdEstudianteBecaB() {
        return idEstudianteBecaB;
    }

    public void setIdEstudianteBecaB(Integer idEstudianteBecaB) {
        this.idEstudianteBecaB = idEstudianteBecaB;
    }

    public String getCajaB() {
        return cajaB;
    }

    public void setCajaB(String cajaB) {
        this.cajaB = cajaB;
    }

    public String getTextoMontoB() {
        return textoMontoB;
    }

    public void setTextoMontoB(String textoMontoB) {
        this.textoMontoB = textoMontoB;
    }

    public String getConceptoB() {
        return conceptoB;
    }

    public void setConceptoB(String conceptoB) {
        this.conceptoB = conceptoB;
    }

    public String getMonedaB() {
        return monedaB;
    }

    public void setMonedaB(String monedaB) {
        this.monedaB = monedaB;
    }

    public String getSerieNroDocB() {
        return serieNroDocB;
    }

    public void setSerieNroDocB(String serieNroDocB) {
        this.serieNroDocB = serieNroDocB;
    }

    public String getFechaStrB() {
        return fechaStrB;
    }

    public void setFechaStrB(String fechaStrB) {
        this.fechaStrB = fechaStrB;
    }

    public String getFechaB() {
        return fechaB;
    }

    public void setFechaB(String fechaB) {
        this.fechaB = fechaB;
    }

    public Double getMontoPagadoB() {
        return montoPagadoB;
    }

    public void setMontoPagadoB(Double montoPagadoB) {
        this.montoPagadoB = montoPagadoB;
    }

    public String getCorrespondienteaB() {
        return correspondienteaB;
    }

    public void setCorrespondienteaB(String correspondienteaB) {
        this.correspondienteaB = correspondienteaB;
    }

    public String getFechaVencB() {
        return fechaVencB;
    }

    public void setFechaVencB(String fechaVencB) {
        this.fechaVencB = fechaVencB;
    }

    public String getTipoBecaB() {
        return tipoBecaB;
    }

    public void setTipoBecaB(String tipoBecaB) {
        this.tipoBecaB = tipoBecaB;
    }

    public String getTipoDocB() {
        return tipoDocB;
    }

    public void setTipoDocB(String tipoDocB) {
        this.tipoDocB = tipoDocB;
    }

    public String getPaganteB() {
        return paganteB;
    }

    public void setPaganteB(String paganteB) {
        this.paganteB = paganteB;
    }

    public String getBecadoB() {
        return becadoB;
    }

    public void setBecadoB(String becadoB) {
        this.becadoB = becadoB;
    }

    public String getColegioB() {
        return colegioB;
    }

    public void setColegioB(String colegioB) {
        this.colegioB = colegioB;
    }

    public String getBancoB() {
        return bancoB;
    }

    public void setBancoB(String bancoB) {
        this.bancoB = bancoB;
    }

    public String getMontoPagadoVistaB() {
        return montoPagadoVistaB;
    }

    public void setMontoPagadoVistaB(String montoPagadoVistaB) {
        this.montoPagadoVistaB = montoPagadoVistaB;
    }

    public String getMontoPensionVistaB() {
        return montoPensionVistaB;
    }

    public void setMontoPensionVistaB(String montoPensionVistaB) {
        this.montoPensionVistaB = montoPensionVistaB;
    }

    public String getMoraVistaB() {
        return moraVistaB;
    }

    public void setMoraVistaB(String moraVistaB) {
        this.moraVistaB = moraVistaB;
    }

    public String getNomConceptoB() {
        return nomConceptoB;
    }

    public void setNomConceptoB(String nomConceptoB) {
        this.nomConceptoB = nomConceptoB;
    }

    public Integer getMoraB() {
        return moraB;
    }

    public void setMoraB(Integer moraB) {
        this.moraB = moraB;
    }

    public Integer getDsctoB() {
        return dsctoB;
    }

    public void setDsctoB(Integer dsctoB) {
        this.dsctoB = dsctoB;
    }

    public Integer getBecaB() {
        return becaB;
    }

    public void setBecaB(Integer becaB) {
        this.becaB = becaB;
    }

    public Integer getFlgDsctoB() {
        return flgDsctoB;
    }

    public void setFlgDsctoB(Integer flgDsctoB) {
        this.flgDsctoB = flgDsctoB;
    }

    public String getInfoReciboB() {
        return infoReciboB;
    }

    public void setInfoReciboB(String infoReciboB) {
        this.infoReciboB = infoReciboB;
    }

    public Integer getInfoMontoB() {
        return infoMontoB;
    }

    public void setInfoMontoB(Integer infoMontoB) {
        this.infoMontoB = infoMontoB;
    }

    public String getNumOperacionB() {
        return numOperacionB;
    }

    public void setNumOperacionB(String numOperacionB) {
        this.numOperacionB = numOperacionB;
    }

    public String getQrB() {
        return qrB;
    }

    public void setQrB(String qrB) {
        this.qrB = qrB;
    }

    ///////////////////////FIN LADO B//////////////////////////

    public Integer getCuentaDA() {
        return cuentaDA;
    }

    public void setCuentaDA(Integer cuentaDA) {
        this.cuentaDA = cuentaDA;
    }

    public String getMontoVistaA() {
        return montoVistaA;
    }

    public void setMontoVistaA(String montoVistaA) {
        this.montoVistaA = montoVistaA;
    }

    public String getCuentaDsctoBecaA() {
        return cuentaDsctoBecaA;
    }

    public void setCuentaDsctoBecaA(String cuentaDsctoBecaA) {
        this.cuentaDsctoBecaA = cuentaDsctoBecaA;
    }

    public String getLabelDsctoBecaA() {
        return labelDsctoBecaA;
    }

    public void setLabelDsctoBecaA(String labelDsctoBecaA) {
        this.labelDsctoBecaA = labelDsctoBecaA;
    }

    public String getDsctobecaA() {
        return dsctobecaA;
    }

    public void setDsctobecaA(String dsctobecaA) {
        this.dsctobecaA = dsctobecaA;
    }

    public Integer getCuentaDB() {
        return cuentaDB;
    }

    public void setCuentaDB(Integer cuentaDB) {
        this.cuentaDB = cuentaDB;
    }

    public String getMontoVistaB() {
        return montoVistaB;
    }

    public void setMontoVistaB(String montoVistaB) {
        this.montoVistaB = montoVistaB;
    }

    public String getCuentaDsctoBecaB() {
        return cuentaDsctoBecaB;
    }

    public void setCuentaDsctoBecaB(String cuentaDsctoBecaB) {
        this.cuentaDsctoBecaB = cuentaDsctoBecaB;
    }

    public String getLabelDsctoBecaB() {
        return labelDsctoBecaB;
    }

    public void setLabelDsctoBecaB(String labelDsctoBecaB) {
        this.labelDsctoBecaB = labelDsctoBecaB;
    }

    public String getDsctobecaB() {
        return dsctobecaB;
    }

    public void setDsctobecaB(String dsctobecaB) {
        this.dsctobecaB = dsctobecaB;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Integer getIdRecibosMoraA() {
        return idRecibosMoraA;
    }

    public void setIdRecibosMoraA(Integer idRecibosMoraA) {
        this.idRecibosMoraA = idRecibosMoraA;
    }

    public Integer getIdRecibosMoraB() {
        return idRecibosMoraB;
    }

    public void setIdRecibosMoraB(Integer idRecibosMoraB) {
        this.idRecibosMoraB = idRecibosMoraB;
    }

    public String getSerieNroDocMoraA() {
        return serieNroDocMoraA;
    }

    public void setSerieNroDocMoraA(String serieNroDocMoraA) {
        this.serieNroDocMoraA = serieNroDocMoraA;
    }

    public String getSerieNroDocMoraB() {
        return serieNroDocMoraB;
    }

    public void setSerieNroDocMoraB(String serieNroDocMoraB) {
        this.serieNroDocMoraB = serieNroDocMoraB;
    } 

    public String getReferencialA() {
        return referencialA;
    }

    public void setReferencialA(String referencialA) {
        this.referencialA = referencialA;
    }

    public String getReferencialB() {
        return referencialB;
    }

    public void setReferencialB(String referencialB) {
        this.referencialB = referencialB;
    }

    
    
    
}

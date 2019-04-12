/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author MS001
 */
public class PersonalRepBean implements Serializable {

    private String nombreCompleto;
    private String uniOrg;
    private String nomUniNeg;
    private String celular1;
    private String celular2;
    private String correoCor;
    private String correoPer;
    //me sirven 
    private String codPer;
    private String estado;
    //////////////
    private String uniNeg;
    private String nombreUniNeg;
    private String rucUniNeg;
    private String nombrePersonal;
    private String nroDoc;
    private String nombreUniOrg;
    private Integer idUniOrg;

    private String cargo;
    private Integer idCargo;

    private String fechaIng;
    private String fechaBaja;
    private Date fechaIngIniDate;
    private Date fechaIngFinDate;
    private Date fechaBajaIniDate;
    private Date fechaBajaFinDate;

    private String documento;
    private String sexo;
    private String nacionalidad;
    private Integer idPaisNacionalidad;

    private String paisNacimiento;
    private Integer idPaisNacimiento;

    private Boolean flgDistritoDomi;
    private String distritoDomi;
    private Integer idDepartamento;
    private Integer idProvincia;
    private Integer idDistritoDomi;

    private String estadoCivil;
    private String textoFiltro;
    private Boolean flgPadres;
    private Boolean flgMadres;
    private Boolean flgPadresConHijosH;//hijos hombres
    private Boolean flgMadresConHijosH;//hijos hombres
    private Boolean flgStatus;
    private Boolean flgStatusFiltro=false;
    
    public PersonalRepBean() {
        this.idDepartamento = MaristaConstantes.DEP_LIMA;
        this.idProvincia = MaristaConstantes.PROV_LIMA;
    }

    public String getCodPer() {
        return codPer;
    }

    public void setCodPer(String codPer) {
        this.codPer = codPer;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getUniOrg() {
        return uniOrg;
    }

    public void setUniOrg(String uniOrg) {
        this.uniOrg = uniOrg;
    }

    public String getNomUniNeg() {
        return nomUniNeg;
    }

    public void setNomUniNeg(String nomUniNeg) {
        this.nomUniNeg = nomUniNeg;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public String getRucUniNeg() {
        return rucUniNeg;
    }

    public void setRucUniNeg(String rucUniNeg) {
        this.rucUniNeg = rucUniNeg;
    }

    public String getNombrePersonal() {
        return nombrePersonal;
    }

    public void setNombrePersonal(String nombrePersonal) {
        this.nombrePersonal = nombrePersonal;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public String getNombreUniOrg() {
        return nombreUniOrg;
    }

    public void setNombreUniOrg(String nombreUniOrg) {
        this.nombreUniOrg = nombreUniOrg;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getFechaIng() {
        return fechaIng;
    }

    public void setFechaIng(String fechaIng) {
        this.fechaIng = fechaIng;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getPaisNacimiento() {
        return paisNacimiento;
    }

    public void setPaisNacimiento(String paisNacimiento) {
        this.paisNacimiento = paisNacimiento;
    }

    public String getDistritoDomi() {
        return distritoDomi;
    }

    public void setDistritoDomi(String distritoDomi) {
        this.distritoDomi = distritoDomi;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(String fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public Integer getIdUniOrg() {
        return idUniOrg;
    }

    public void setIdUniOrg(Integer idUniOrg) {
        this.idUniOrg = idUniOrg;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public Integer getIdPaisNacionalidad() {
        return idPaisNacionalidad;
    }

    public void setIdPaisNacionalidad(Integer idPaisNacionalidad) {
        this.idPaisNacionalidad = idPaisNacionalidad;
    }

    public Integer getIdPaisNacimiento() {
        return idPaisNacimiento;
    }

    public void setIdPaisNacimiento(Integer idPaisNacimiento) {
        this.idPaisNacimiento = idPaisNacimiento;
    }

    public Integer getIdDistritoDomi() {
        return idDistritoDomi;
    }

    public void setIdDistritoDomi(Integer idDistritoDomi) {
        this.idDistritoDomi = idDistritoDomi;
    }

    public Date getFechaIngIniDate() {
        return fechaIngIniDate;
    }

    public void setFechaIngIniDate(Date fechaIngIniDate) {
        this.fechaIngIniDate = fechaIngIniDate;
    }

    public Date getFechaIngFinDate() {
        return fechaIngFinDate;
    }

    public void setFechaIngFinDate(Date fechaIngFinDate) {
        this.fechaIngFinDate = fechaIngFinDate;
    }

    public Date getFechaBajaIniDate() {
        return fechaBajaIniDate;
    }

    public void setFechaBajaIniDate(Date fechaBajaIniDate) {
        this.fechaBajaIniDate = fechaBajaIniDate;
    }

    public Date getFechaBajaFinDate() {
        return fechaBajaFinDate;
    }

    public void setFechaBajaFinDate(Date fechaBajaFinDate) {
        this.fechaBajaFinDate = fechaBajaFinDate;
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Integer getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(Integer idProvincia) {
        this.idProvincia = idProvincia;
    }

    public String getTextoFiltro() {
        return textoFiltro;
    }

    public void setTextoFiltro(String textoFiltro) {
        this.textoFiltro = textoFiltro;
    }

    public Boolean getFlgPadres() {
        return flgPadres;
    }

    public void setFlgPadres(Boolean flgPadres) {
        this.flgPadres = flgPadres;
    }

    public Boolean getFlgMadres() {
        return flgMadres;
    }

    public void setFlgMadres(Boolean flgMadres) {
        this.flgMadres = flgMadres;
    }

    public Boolean getFlgPadresConHijosH() {
        return flgPadresConHijosH;
    }

    public void setFlgPadresConHijosH(Boolean flgPadresConHijosH) {
        this.flgPadresConHijosH = flgPadresConHijosH;
    }

    public Boolean getFlgMadresConHijosH() {
        return flgMadresConHijosH;
    }

    public void setFlgMadresConHijosH(Boolean flgMadresConHijosH) {
        this.flgMadresConHijosH = flgMadresConHijosH;
    }

    public Boolean getFlgDistritoDomi() {
        return flgDistritoDomi;
    }

    public void setFlgDistritoDomi(Boolean flgDistritoDomi) {
        this.flgDistritoDomi = flgDistritoDomi;
    }

    public String getCelular1() {
        return celular1;
    }

    public void setCelular1(String celular1) {
        this.celular1 = celular1;
    }

    public String getCelular2() {
        return celular2;
    }

    public void setCelular2(String celular2) {
        this.celular2 = celular2;
    }

    public String getCorreoCor() {
        return correoCor;
    }

    public void setCorreoCor(String correoCor) {
        this.correoCor = correoCor;
    }

    public String getCorreoPer() {
        return correoPer;
    }

    public void setCorreoPer(String correoPer) {
        this.correoPer = correoPer;
    }

    public Boolean getFlgStatus() {
        return flgStatus;
    }

    public void setFlgStatus(Boolean flgStatus) {
        this.flgStatus = flgStatus;
    }

    public Boolean getFlgStatusFiltro() {
        return flgStatusFiltro;
    }

    public void setFlgStatusFiltro(Boolean flgStatusFiltro) {
        this.flgStatusFiltro = flgStatusFiltro;
    }

}

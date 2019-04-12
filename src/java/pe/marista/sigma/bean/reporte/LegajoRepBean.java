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
 * @author MS001
 */
public class LegajoRepBean implements Serializable {

    private String nombreUniNeg;
    private String rucUniNeg;
    private String codPer;
    private String nombre;
    private String apePat;
    private String apeMat;
    private String tipoDoc;
    private String nroDoc;
    private String ruc;
    private String estadoCiv;
    private String sexo;
    private String celular1;
    private String celular2;
    private String teleFijo;
    private String correoCor;
    private String correoPer;
    private String fecNac;
    private String paisNac;
    private String nacionalidad;
    private String nombreDep;
    private String nombreProv;
    private String nombreDis;
    private String domicilio;
    private String nombreDepDomi;
    private String nombreProvDomi;
    private String nombreDisDomi;
    private String tipoViv;
    private String condicionPropiedad;
    private String estadoConservacion;
    private String tipoSegPer;
    private String nombreEntEPS;
    private String numeroEPS;
    private String nombreEntPension;
    private String numeroPension;
    private String nombreUniOrg;

    private JRBeanCollectionDataSource listaPersonalDependientes;
    private JRBeanCollectionDataSource listaPersonalFormacionBasica;
    private JRBeanCollectionDataSource listaPersonalFormacionSuperior;
    private JRBeanCollectionDataSource listaPersonalOtrosEstudios;
    private JRBeanCollectionDataSource listaPersonalIdioma;
    private JRBeanCollectionDataSource listaPersonalContrato; //benPersonalContrato
    private JRBeanCollectionDataSource listaPersonalVacaciones;//benPersonalContrato
    private JRBeanCollectionDataSource listaPersonalCargo;
    private JRBeanCollectionDataSource listaPersonalExperiencia;
    private JRBeanCollectionDataSource listaPersonalProcesoJudicial;
    private JRBeanCollectionDataSource listaPersonalDocumento;
    

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

    public String getCodPer() {
        return codPer;
    }

    public void setCodPer(String codPer) {
        this.codPer = codPer;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApePat() {
        return apePat;
    }

    public void setApePat(String apePat) {
        this.apePat = apePat;
    }

    public String getApeMat() {
        return apeMat;
    }

    public void setApeMat(String apeMat) {
        this.apeMat = apeMat;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getEstadoCiv() {
        return estadoCiv;
    }

    public void setEstadoCiv(String estadoCiv) {
        this.estadoCiv = estadoCiv;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
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

    public String getTeleFijo() {
        return teleFijo;
    }

    public void setTeleFijo(String teleFijo) {
        this.teleFijo = teleFijo;
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

    public String getFecNac() {
        return fecNac;
    }

    public void setFecNac(String fecNac) {
        this.fecNac = fecNac;
    }

    public String getPaisNac() {
        return paisNac;
    }

    public void setPaisNac(String paisNac) {
        this.paisNac = paisNac;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getNombreDep() {
        return nombreDep;
    }

    public void setNombreDep(String nombreDep) {
        this.nombreDep = nombreDep;
    }

    public String getNombreProv() {
        return nombreProv;
    }

    public void setNombreProv(String nombreProv) {
        this.nombreProv = nombreProv;
    }

    public String getNombreDis() {
        return nombreDis;
    }

    public void setNombreDis(String nombreDis) {
        this.nombreDis = nombreDis;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getNombreDepDomi() {
        return nombreDepDomi;
    }

    public void setNombreDepDomi(String nombreDepDomi) {
        this.nombreDepDomi = nombreDepDomi;
    }

    public String getNombreProvDomi() {
        return nombreProvDomi;
    }

    public void setNombreProvDomi(String nombreProvDomi) {
        this.nombreProvDomi = nombreProvDomi;
    }

    public String getNombreDisDomi() {
        return nombreDisDomi;
    }

    public void setNombreDisDomi(String nombreDisDomi) {
        this.nombreDisDomi = nombreDisDomi;
    }

    public String getTipoViv() {
        return tipoViv;
    }

    public void setTipoViv(String tipoViv) {
        this.tipoViv = tipoViv;
    }

    public String getCondicionPropiedad() {
        return condicionPropiedad;
    }

    public void setCondicionPropiedad(String condicionPropiedad) {
        this.condicionPropiedad = condicionPropiedad;
    }

    public String getEstadoConservacion() {
        return estadoConservacion;
    }

    public void setEstadoConservacion(String estadoConservacion) {
        this.estadoConservacion = estadoConservacion;
    }

    public String getTipoSegPer() {
        return tipoSegPer;
    }

    public void setTipoSegPer(String tipoSegPer) {
        this.tipoSegPer = tipoSegPer;
    }

    public String getNombreEntEPS() {
        return nombreEntEPS;
    }

    public void setNombreEntEPS(String nombreEntEPS) {
        this.nombreEntEPS = nombreEntEPS;
    }

    public String getNumeroEPS() {
        return numeroEPS;
    }

    public void setNumeroEPS(String numeroEPS) {
        this.numeroEPS = numeroEPS;
    }

    public String getNombreEntPension() {
        return nombreEntPension;
    }

    public void setNombreEntPension(String nombreEntPension) {
        this.nombreEntPension = nombreEntPension;
    }

    public String getNumeroPension() {
        return numeroPension;
    }

    public void setNumeroPension(String numeroPension) {
        this.numeroPension = numeroPension;
    }

    public String getNombreUniOrg() {
        return nombreUniOrg;
    }

    public void setNombreUniOrg(String nombreUniOrg) {
        this.nombreUniOrg = nombreUniOrg;
    }

    public JRBeanCollectionDataSource getListaPersonalDependientes() {
        return listaPersonalDependientes;
    }

    public void setListaPersonalDependientes(List<PersonalDependienteRepBean> listaPersonalDependientes) {
        this.listaPersonalDependientes = new JRBeanCollectionDataSource(listaPersonalDependientes);
    }

    public JRBeanCollectionDataSource getListaPersonalFormacionBasica() {
        return listaPersonalFormacionBasica;
    }

    public void setListaPersonalFormacionBasica(List<PersonalFormacionRepBean> listaPersonalFormacionBasica) {
        this.listaPersonalFormacionBasica = new JRBeanCollectionDataSource(listaPersonalFormacionBasica);
    }

    public JRBeanCollectionDataSource getListaPersonalFormacionSuperior() {
        return listaPersonalFormacionSuperior;
    }

    public void setListaPersonalFormacionSuperior(List<PersonalFormacionRepBean> listaPersonalFormacionSuperior) {
        this.listaPersonalFormacionSuperior = new JRBeanCollectionDataSource(listaPersonalFormacionSuperior);;
    }

    public JRBeanCollectionDataSource getListaPersonalOtrosEstudios() {
        return listaPersonalOtrosEstudios;
    }

    public void setListaPersonalOtrosEstudios(List<PersonalOtrosEstudiosRepBean> listaPersonalOtrosEstudios) {
        this.listaPersonalOtrosEstudios = new JRBeanCollectionDataSource(listaPersonalOtrosEstudios);
    }

    public JRBeanCollectionDataSource getListaPersonalIdioma() {
        return listaPersonalIdioma;
    }

    public void setListaPersonalIdioma(List<PersonalIdiomaRepBean> listaPersonalIdioma) {
        this.listaPersonalIdioma = new JRBeanCollectionDataSource(listaPersonalIdioma);
    }

    public JRBeanCollectionDataSource getListaPersonalContrato() {
        return listaPersonalContrato;
    }

    public void setListaPersonalContrato(List<PersonalContratoRepBean> listaPersonalContrato) {
        this.listaPersonalContrato = new JRBeanCollectionDataSource(listaPersonalContrato);
    }

    public JRBeanCollectionDataSource getListaPersonalVacaciones() {
        return listaPersonalVacaciones;
    }

    public void setListaPersonalVacaciones(List<PersonalContratoRepBean> listaPersonalVacaciones) {
        this.listaPersonalVacaciones = new JRBeanCollectionDataSource(listaPersonalVacaciones);
    }

    public JRBeanCollectionDataSource getListaPersonalCargo() {
        return listaPersonalCargo;
    }

    public void setListaPersonalCargo(List<PersonalCargoRepBean> listaPersonalCargo) {
        this.listaPersonalCargo = new JRBeanCollectionDataSource(listaPersonalCargo);
    }

    public JRBeanCollectionDataSource getListaPersonalExperiencia() {
        return listaPersonalExperiencia;
    }

    public void setListaPersonalExperiencia(List<PersonalExperienciaRepBean> listaPersonalExperiencia) { 
         this.listaPersonalExperiencia = new JRBeanCollectionDataSource(listaPersonalExperiencia);
    }

    public JRBeanCollectionDataSource getListaPersonalProcesoJudicial() {
        return listaPersonalProcesoJudicial;
    }

    public void setListaPersonalProcesoJudicial(List<PersonalProcesoJudicialRepBean> listaPersonalProcesoJudicial) {
        this.listaPersonalProcesoJudicial = new JRBeanCollectionDataSource(listaPersonalProcesoJudicial);
    }

    public JRBeanCollectionDataSource getListaPersonalDocumento() {
        return listaPersonalDocumento;
    }

    public void setListaPersonalDocumento(List<PersonalDocumentoRepBean> listaPersonalDocumento) { 
        this.listaPersonalDocumento = new JRBeanCollectionDataSource(listaPersonalDocumento);
    }

    
    
}

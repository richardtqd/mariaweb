/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author Administrador
 */
public class PersonalBean implements Serializable {

    private Integer idPersonal;
    private String codPer;
    private String nombre;
    private String apepat;
    private String apemat;
    private Date fecNac;
    private CodigoBean tipoDocPerBean;//idTipoDocPer
    private String nroDoc;
    private PaisBean paisNacionalidadBean;//idNacionalidad 
    private String ruc;
    private String correoPer;
    private String correoCor;
    private int sexo = 1;
    private String foto;
    private CodigoBean tipoEstadoCivilBean;//idTipoEstadoCivil
    private PaisBean paisNacimientoBean;//idPaisNacimiento
    private String obsNacimiento;
    private DistritoBean distritoNacBean;//idDistritoNac
    private DistritoBean distritoDomBean;//idDistritoDom
    private String domicilio;
    private CodigoBean tipoViviendaBean;//idTipoVivienda
    private CodigoBean tipoCasaBean;//idTipoCasa;
    private CodigoBean tipoStatusCasaBean;//idTipoStatusCasa
    private String observacionCasa;
    private EntidadBean entidadEpsBean;//idEntidadEps
    private String numeroEps;
    private EntidadBean entidadPensionBean;//identidadPension
    private String numeroPension;
//    private CodigoBean tipoAseguradoBean;//idTipoAsegurado----------
    private CodigoBean tipoSeguroPersonalBean;//idTipoSeguroPersonal 
    private Date fecIngreso;
    private Date fecBaja;
    private boolean status = true;
    private CodigoBean tipoOcupacionBean;//idTipoOcupacion
    private CodigoBean tipoNivelInsBean;
//    private CodigoBean tipoProfesionBean;//idTipoProfesion-------------
    private UnidadNegocioBean unidadNegocioBean;//uniNeg
    private UnidadOrganicaBean unidadOrganicaBean; //idUnidadOrganica
    private GradoAcademicoBean gradoAcademicoBean; //idGradoAcademico
    private String grupoSanguineo;
    private String factorSanguineo;
    private String telefonoFijo;
    private String celular1;
    private String celular2;
    private BigDecimal basico;
    private Integer nroDias;
    private Integer nroHoras;
    private String creaPor;
    private String modiPor;
    private Date creaFecha;
//    private CarreraBean carreraBean;//id

    private CentroResponsabilidadBean cr1;
    private CentroResponsabilidadBean cr2;
    private CentroResponsabilidadBean cr3;
    private CentroResponsabilidadBean cr4;
    private CentroResponsabilidadBean cr5;

    //Ayuda
    private String nombreCompleto;
    private Integer cantidadUsuarios;
    private String usuario;
    private int estadoInt;
    private boolean collapsed = false;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer diaFecNac;
    private Integer MesFecNac;
    private Integer idTipoVacaciones;//ayuda 
    private Integer mes;//ayuda
    private Boolean statusVista;

    private boolean flgSuperAdmin;

    private Integer cr1Porc = 0;
    private Integer cr2Porc = 0;
    private Integer cr3Porc = 0;
    private Integer cr4Porc = 0;
    private Integer cr5Porc = 0;

    private Integer contadorDuplicados;
    private Boolean flgSeleccion = false;
    private String estadoAyuda;

    private CodigoBean tipoViaBean;
    private CodigoBean tipoZonaBean;
    private String nombreZona;
    private String interior;
    private String numero;
    private int flgDiscapacitado;
    private Integer nroHijosDepen;
    private String nombreContacto;
    private String telefonoContacto;
    private PaisBean paisEmisorDoc;
    private CodigoBean tipoPensionPersonal;
    private Date fechaIniSeguro;
    private Date fechaIniPension;
    
    //
    private String participacionActParro;
    private Boolean flgBautismo=false;
    private Boolean flgConfirmacion=false;
    private Boolean flgComunion=false;
    private Boolean flgMatrimonio=false;
    private int flgDonadorSangre;
    private int flgDonadorOrganos;

    //poliza sctr
    private String nroPolizaSctr;
    private String nombreCompaniaSctr;
    private String beneficiariosSctr;
    private Boolean flgTrabajoAltoRiesgo=false;
    private BigDecimal basicoEstatal;
    private CodigoBean tipoPlanillaColegio;
    private BigDecimal boniCargoTotal;
    
    public Integer getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(Integer idPersonal) {
        this.idPersonal = idPersonal;
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

    public String getApepat() {
        return apepat;
    }

    public void setApepat(String apepat) {
        this.apepat = apepat;
    }

    public String getApemat() {
        return apemat;
    }

    public void setApemat(String apemat) {
        this.apemat = apemat;
    }

    public Date getFecNac() {
        return fecNac;
    }

    public void setFecNac(Date fecNac) {
        this.fecNac = fecNac;
    }

    public CodigoBean getTipoDocPerBean() {
        if (tipoDocPerBean == null) {
            tipoDocPerBean = new CodigoBean();
        }
        return tipoDocPerBean;
    }

    public void setTipoDocPerBean(CodigoBean tipoDocPerBean) {
        this.tipoDocPerBean = tipoDocPerBean;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public void setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
    }

    public PaisBean getPaisNacionalidadBean() {
        if (paisNacionalidadBean == null) {
            paisNacionalidadBean = new PaisBean();
        }
        return paisNacionalidadBean;
    }

    public void setPaisNacionalidadBean(PaisBean paisNacionalidadBean) {
        this.paisNacionalidadBean = paisNacionalidadBean;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getCorreoPer() {
        return correoPer;
    }

    public void setCorreoPer(String correoPer) {
        this.correoPer = correoPer;
    }

    public String getCorreoCor() {
        return correoCor;
    }

    public void setCorreoCor(String correoCor) {
        this.correoCor = correoCor;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public CodigoBean getTipoEstadoCivilBean() {
        if (tipoEstadoCivilBean == null) {
            tipoEstadoCivilBean = new CodigoBean();
        }
        return tipoEstadoCivilBean;
    }

    public void setTipoEstadoCivilBean(CodigoBean tipoEstadoCivilBean) {
        this.tipoEstadoCivilBean = tipoEstadoCivilBean;
    }

    public PaisBean getPaisNacimientoBean() {
        if (paisNacimientoBean == null) {
            paisNacimientoBean = new PaisBean();
        }
        return paisNacimientoBean;
    }

    public void setPaisNacimientoBean(PaisBean paisNacimientoBean) {
        this.paisNacimientoBean = paisNacimientoBean;
    }

    public String getObsNacimiento() {
        return obsNacimiento;
    }

    public void setObsNacimiento(String obsNacimiento) {
        this.obsNacimiento = obsNacimiento;
    }

    public DistritoBean getDistritoNacBean() {
        if (distritoNacBean == null) {
            distritoNacBean = new DistritoBean();
        }
        return distritoNacBean;
    }

    public void setDistritoNacBean(DistritoBean distritoNacBean) {
        this.distritoNacBean = distritoNacBean;
    }

    public DistritoBean getDistritoDomBean() {
        if (distritoDomBean == null) {
            distritoDomBean = new DistritoBean();
        }
        return distritoDomBean;
    }

    public void setDistritoDomBean(DistritoBean distritoDomBean) {
        this.distritoDomBean = distritoDomBean;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getObservacionCasa() {
        return observacionCasa;
    }

    public void setObservacionCasa(String observacionCasa) {
        this.observacionCasa = observacionCasa;
    }

    public EntidadBean getEntidadEpsBean() {
        if (entidadEpsBean == null) {
            entidadEpsBean = new EntidadBean();
        }
        return entidadEpsBean;
    }

    public void setEntidadEpsBean(EntidadBean entidadEpsBean) {
        this.entidadEpsBean = entidadEpsBean;
    }

    public String getNumeroEps() {
        return numeroEps;
    }

    public void setNumeroEps(String numeroEps) {
        this.numeroEps = numeroEps;
    }

    public EntidadBean getEntidadPensionBean() {
        if (entidadPensionBean == null) {
            entidadPensionBean = new EntidadBean();
        }
        return entidadPensionBean;
    }

    public void setEntidadPensionBean(EntidadBean entidadPensionBean) {
        this.entidadPensionBean = entidadPensionBean;
    }

    public String getNumeroPension() {
        return numeroPension;
    }

    public void setNumeroPension(String numeroPension) {
        this.numeroPension = numeroPension;
    }

    public Date getFecIngreso() {
        return fecIngreso;
    }

    public void setFecIngreso(Date fecIngreso) {
        this.fecIngreso = fecIngreso;
    }

    public Date getFecBaja() {
        return fecBaja;
    }

    public void setFecBaja(Date fecBaja) {
        this.fecBaja = fecBaja;
    }

    public CodigoBean getTipoOcupacionBean() {
        if (tipoOcupacionBean == null) {
            tipoOcupacionBean = new CodigoBean();
        }
        return tipoOcupacionBean;
    }

    public void setTipoOcupacionBean(CodigoBean tipoOcupacionBean) {
        this.tipoOcupacionBean = tipoOcupacionBean;
    }

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public UnidadOrganicaBean getUnidadOrganicaBean() {
        if (unidadOrganicaBean == null) {
            unidadOrganicaBean = new UnidadOrganicaBean();
        }
        return unidadOrganicaBean;
    }

    public void setUnidadOrganicaBean(UnidadOrganicaBean unidadOrganicaBean) {
        this.unidadOrganicaBean = unidadOrganicaBean;
    }

    public GradoAcademicoBean getGradoAcademicoBean() {
        if (gradoAcademicoBean == null) {
            gradoAcademicoBean = new GradoAcademicoBean();
        }
        return gradoAcademicoBean;
    }

    public void setGradoAcademicoBean(GradoAcademicoBean gradoAcademicoBean) {
        this.gradoAcademicoBean = gradoAcademicoBean;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
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

    public BigDecimal getBasico() {
        return basico;
    }

    public void setBasico(BigDecimal basico) {
        this.basico = basico;
    }

    public Integer getNroDias() {
        return nroDias;
    }

    public void setNroDias(Integer nroDias) {
        this.nroDias = nroDias;
    }

    public Integer getNroHoras() {
        return nroHoras;
    }

    public void setNroHoras(Integer nroHoras) {
        this.nroHoras = nroHoras;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public boolean getEstadoActual() {
        return status;
    }

    public String getNombreCompleto() {
        StringBuilder sb = new StringBuilder();
        if (apepat != null) {
            sb.append(apepat).append(" ");
        }
        if (apemat != null) {
            sb.append(apemat).append(", ");
        }
        if (nombre != null) {
            sb.append(nombre);
        }
        nombreCompleto = sb.toString();
        return nombreCompleto;
    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public CodigoBean getTipoViviendaBean() {
        if (tipoViviendaBean == null) {
            tipoViviendaBean = new CodigoBean();
        }
        return tipoViviendaBean;
    }

    public void setTipoViviendaBean(CodigoBean tipoViviendaBean) {
        this.tipoViviendaBean = tipoViviendaBean;
    }

    public CodigoBean getTipoCasaBean() {
        if (tipoCasaBean == null) {
            tipoCasaBean = new CodigoBean();
        }
        return tipoCasaBean;
    }

    public void setTipoCasaBean(CodigoBean tipoCasaBean) {
        this.tipoCasaBean = tipoCasaBean;
    }

    public CodigoBean getTipoStatusCasaBean() {
        if (tipoStatusCasaBean == null) {
            tipoStatusCasaBean = new CodigoBean();
        }
        return tipoStatusCasaBean;
    }

    public void setTipoStatusCasaBean(CodigoBean tipoStatusCasaBean) {
        this.tipoStatusCasaBean = tipoStatusCasaBean;
    }
//
//    public CarreraBean getCarreraBean() {
//        if (carreraBean == null) {
//            carreraBean = new CarreraBean();
//        }
//        return carreraBean;
//    }
//
//    public void setCarreraBean(CarreraBean carreraBean) {
//        this.carreraBean = carreraBean;
//    }

    public boolean isStatus() {
        return status;
    }

    public int getEstadoInt() {
        if (status) {
            return 1;
        } else {
            return 0;
        }

    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setEstadoInt(int estadoInt) {
        this.estadoInt = estadoInt;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public boolean isFlgSuperAdmin() {
        return flgSuperAdmin;
    }

    public void setFlgSuperAdmin(boolean flgSuperAdmin) {
        this.flgSuperAdmin = flgSuperAdmin;
    }

    public CodigoBean getTipoSeguroPersonalBean() {
        if (tipoSeguroPersonalBean == null) {
            tipoSeguroPersonalBean = new CodigoBean();
        }
        return tipoSeguroPersonalBean;
    }

    public void setTipoSeguroPersonalBean(CodigoBean tipoSeguroPersonalBean) {
        this.tipoSeguroPersonalBean = tipoSeguroPersonalBean;
    }

    public String getFactorSanguineo() {
        return factorSanguineo;
    }

    public void setFactorSanguineo(String factorSanguineo) {
        this.factorSanguineo = factorSanguineo;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getDiaFecNac() {
        return diaFecNac;
    }

    public void setDiaFecNac(Integer diaFecNac) {
        this.diaFecNac = diaFecNac;
    }

    public Integer getMesFecNac() {
        return MesFecNac;
    }

    public void setMesFecNac(Integer MesFecNac) {
        this.MesFecNac = MesFecNac;
    }

    public CentroResponsabilidadBean getCr1() {
        if (cr1 == null) {
            cr1 = new CentroResponsabilidadBean();
        }
        return cr1;
    }

    public void setCr1(CentroResponsabilidadBean cr1) {
        this.cr1 = cr1;
    }

    public CentroResponsabilidadBean getCr2() {
        if (cr2 == null) {
            cr2 = new CentroResponsabilidadBean();
        }
        return cr2;
    }

    public void setCr2(CentroResponsabilidadBean cr2) {
        this.cr2 = cr2;
    }

    public CentroResponsabilidadBean getCr3() {
        if (cr3 == null) {
            cr3 = new CentroResponsabilidadBean();
        }
        return cr3;
    }

    public void setCr3(CentroResponsabilidadBean cr3) {
        this.cr3 = cr3;
    }

    public CentroResponsabilidadBean getCr4() {
        if (cr4 == null) {
            cr4 = new CentroResponsabilidadBean();
        }
        return cr4;
    }

    public void setCr4(CentroResponsabilidadBean cr4) {
        this.cr4 = cr4;
    }

    public Integer getIdTipoVacaciones() {
        return idTipoVacaciones;
    }

    public void setIdTipoVacaciones(Integer idTipoVacaciones) {
        this.idTipoVacaciones = idTipoVacaciones;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Boolean getStatusVista() {
        return statusVista;
    }

    public void setStatusVista(Boolean statusVista) {
        this.statusVista = statusVista;
    }

    public Integer getCr1Porc() {
        return cr1Porc;
    }

    public void setCr1Porc(Integer cr1Porc) {
        this.cr1Porc = cr1Porc;
    }

    public Integer getCr2Porc() {
        return cr2Porc;
    }

    public void setCr2Porc(Integer cr2Porc) {
        this.cr2Porc = cr2Porc;
    }

    public Integer getCr3Porc() {
        return cr3Porc;
    }

    public void setCr3Porc(Integer cr3Porc) {
        this.cr3Porc = cr3Porc;
    }

    public Integer getCr4Porc() {
        return cr4Porc;
    }

    public void setCr4Porc(Integer cr4Porc) {
        this.cr4Porc = cr4Porc;
    }

    public CentroResponsabilidadBean getCr5() {
        if (cr5 == null) {
            cr5 = new CentroResponsabilidadBean();
        }
        return cr5;
    }

    public void setCr5(CentroResponsabilidadBean cr5) {
        this.cr5 = cr5;
    }

    public Integer getCr5Porc() {
        return cr5Porc;
    }

    public void setCr5Porc(Integer cr5Porc) {
        this.cr5Porc = cr5Porc;
    }

    public CodigoBean getTipoNivelInsBean() {
        if (tipoNivelInsBean == null) {
            tipoNivelInsBean = new CodigoBean();
        }
        return tipoNivelInsBean;
    }

    public void setTipoNivelInsBean(CodigoBean tipoNivelInsBean) {
        this.tipoNivelInsBean = tipoNivelInsBean;
    }

    public Integer getContadorDuplicados() {
        return contadorDuplicados;
    }

    public void setContadorDuplicados(Integer contadorDuplicados) {
        this.contadorDuplicados = contadorDuplicados;
    }

    public Integer getCantidadUsuarios() {
        return cantidadUsuarios;
    }

    public void setCantidadUsuarios(Integer cantidadUsuarios) {
        this.cantidadUsuarios = cantidadUsuarios;
    }

    public Boolean getFlgSeleccion() {
        return flgSeleccion;
    }

    public void setFlgSeleccion(Boolean flgSeleccion) {
        this.flgSeleccion = flgSeleccion;
    }

    public String getEstadoAyuda() {
        if (status == true) {
            estadoAyuda = MaristaConstantes.ESTADO_ACTIVO_DES;
        }
        if (status == false) {
            estadoAyuda = MaristaConstantes.ESTADO_INACTIVO_DES;
        }
        return estadoAyuda;
    }

    public void setEstadoAyuda(String estadoAyuda) {
        this.estadoAyuda = estadoAyuda;
    }

    public CodigoBean getTipoViaBean() {
        if (tipoViaBean == null) {
            tipoViaBean = new CodigoBean();
        }
        return tipoViaBean;
    }

    public void setTipoViaBean(CodigoBean tipoViaBean) {
        this.tipoViaBean = tipoViaBean;
    }

    public CodigoBean getTipoZonaBean() {
        if (tipoZonaBean == null) {
            tipoZonaBean = new CodigoBean();
        }
        return tipoZonaBean;
    }

    public void setTipoZonaBean(CodigoBean tipoZonaBean) {
        this.tipoZonaBean = tipoZonaBean;
    }

    public String getNombreZona() {
        return nombreZona;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    public String getInterior() {
        return interior;
    }

    public void setInterior(String interior) {
        this.interior = interior;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getFlgDiscapacitado() {
        return flgDiscapacitado;
    }

    public void setFlgDiscapacitado(int flgDiscapacitado) {
        this.flgDiscapacitado = flgDiscapacitado;
    }

    public Integer getNroHijosDepen() {
        return nroHijosDepen;
    }

    public void setNroHijosDepen(Integer nroHijosDepen) {
        this.nroHijosDepen = nroHijosDepen;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public PaisBean getPaisEmisorDoc() {
        if (paisEmisorDoc==null) {
            paisEmisorDoc= new PaisBean();
        }
        return paisEmisorDoc;
    }

    public void setPaisEmisorDoc(PaisBean paisEmisorDoc) {
        this.paisEmisorDoc = paisEmisorDoc;
    }

    public CodigoBean getTipoPensionPersonal() {
        if (tipoPensionPersonal==null) {
            tipoPensionPersonal= new CodigoBean();
        }
        return tipoPensionPersonal;
    }

    public void setTipoPensionPersonal(CodigoBean tipoPensionPersonal) {
        this.tipoPensionPersonal = tipoPensionPersonal;
    }

    public Date getFechaIniSeguro() {
        return fechaIniSeguro;
    }

    public void setFechaIniSeguro(Date fechaIniSeguro) {
        this.fechaIniSeguro = fechaIniSeguro;
    }

    public Date getFechaIniPension() {
        return fechaIniPension;
    }

    public void setFechaIniPension(Date fechaIniPension) {
        this.fechaIniPension = fechaIniPension;
    }

    public String getParticipacionActParro() {
        return participacionActParro;
    }

    public void setParticipacionActParro(String participacionActParro) {
        this.participacionActParro = participacionActParro;
    }

    public Boolean getFlgBautismo() {
        return flgBautismo;
    }

    public void setFlgBautismo(Boolean flgBautismo) {
        this.flgBautismo = flgBautismo;
    }

    public Boolean getFlgConfirmacion() {
        return flgConfirmacion;
    }

    public void setFlgConfirmacion(Boolean flgConfirmacion) {
        this.flgConfirmacion = flgConfirmacion;
    }

    public Boolean getFlgComunion() {
        return flgComunion;
    }

    public void setFlgComunion(Boolean flgComunion) {
        this.flgComunion = flgComunion;
    }

    public Boolean getFlgMatrimonio() {
        return flgMatrimonio;
    }

    public void setFlgMatrimonio(Boolean flgMatrimonio) {
        this.flgMatrimonio = flgMatrimonio;
    }

    public int getFlgDonadorSangre() {
        return flgDonadorSangre;
    }

    public void setFlgDonadorSangre(int flgDonadorSangre) {
        this.flgDonadorSangre = flgDonadorSangre;
    }

    public int getFlgDonadorOrganos() {
        return flgDonadorOrganos;
    }

    public void setFlgDonadorOrganos(int flgDonadorOrganos) {
        this.flgDonadorOrganos = flgDonadorOrganos;
    }

    public String getNroPolizaSctr() {
        return nroPolizaSctr;
    }

    public void setNroPolizaSctr(String nroPolizaSctr) {
        this.nroPolizaSctr = nroPolizaSctr;
    }

    public String getNombreCompaniaSctr() {
        return nombreCompaniaSctr;
    }

    public void setNombreCompaniaSctr(String nombreCompaniaSctr) {
        this.nombreCompaniaSctr = nombreCompaniaSctr;
    }

    public String getBeneficiariosSctr() {
        return beneficiariosSctr;
    }

    public void setBeneficiariosSctr(String beneficiariosSctr) {
        this.beneficiariosSctr = beneficiariosSctr;
    }

    public Boolean getFlgTrabajoAltoRiesgo() {
        return flgTrabajoAltoRiesgo;
    }

    public void setFlgTrabajoAltoRiesgo(Boolean flgTrabajoAltoRiesgo) {
        this.flgTrabajoAltoRiesgo = flgTrabajoAltoRiesgo;
    }

    public BigDecimal getBasicoEstatal() {
        return basicoEstatal;
    }

    public void setBasicoEstatal(BigDecimal basicoEstatal) {
        this.basicoEstatal = basicoEstatal;
    }
    
     public CodigoBean getTipoPlanillaColegio() {
        if (tipoPlanillaColegio==null) {
            tipoPlanillaColegio= new CodigoBean();
        }
        return tipoPlanillaColegio;
    }

    public void setTipoPlanillaColegio(CodigoBean tipoPlanillaColegio) {
        this.tipoPlanillaColegio = tipoPlanillaColegio;
    }

    public BigDecimal getBoniCargoTotal() {
        return boniCargoTotal;
    }

    public void setBoniCargoTotal(BigDecimal boniCargoTotal) {
        this.boniCargoTotal = boniCargoTotal;
    }
}

package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author Administrador
 */
public class ConceptoBean implements Serializable {

    private Integer idConcepto;
    private String nombre;
    private String descrip;
    private TipoConceptoBean tipoConceptoBean;//idConceptoCategoria
    private PlanContableBean planContableCuentaDBean;
    private PlanContableBean planContableCuentaHBean;
//    private Integer cuentad;
//    private Integer cuentah;
    private CentroResponsabilidadBean cr;
    private Boolean flgShowEstudiante;
    private Boolean flgShowExAlumno;
    private Boolean flgShowExterno;
    private Boolean flgShowHijoExAlumno;
    private Boolean flgShowHijoEmpleado;
    private Boolean flgProgramacion;
    private Boolean flgPrecio;
    private Boolean status;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;

    //ayuda
    private String vistaFlgShowEstudiante;
    private String vistaFlgShowExAlumno;
    private String vistaFlgShowExterno;
    private String vistaFlgShowHijoExAlumno;
    private String vistaFlgShowHijoEmpleado;
    private String vistaFlgProgramacion;
    private String vistaFlgPrecio;
    private String vistaStatus;
    private Boolean flgTieneCr;
    private String flgTieneCrVista;

    //
    private String nombreConceptoVista;

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public Integer getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
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

    public Boolean isFlgShowEstudiante() {
        return flgShowEstudiante;
    }

    public void setFlgShowEstudiante(Boolean flgShowEstudiante) {
        this.flgShowEstudiante = flgShowEstudiante;
    }

    public Boolean isFlgShowExAlumno() {
        return flgShowExAlumno;
    }

    public void setFlgShowExAlumno(Boolean flgShowExAlumno) {
        this.flgShowExAlumno = flgShowExAlumno;
    }

    public Boolean isFlgShowExterno() {
        return flgShowExterno;
    }

    public void setFlgShowExterno(Boolean flgShowExterno) {
        this.flgShowExterno = flgShowExterno;
    }

    public Boolean isFlgProgramacion() {
        return flgProgramacion;
    }

    public void setFlgProgramacion(Boolean flgProgramacion) {
        this.flgProgramacion = flgProgramacion;
    }

    public Boolean isFlgPrecio() {
        return flgPrecio;
    }

    public void setFlgPrecio(Boolean flgPrecio) {
        this.flgPrecio = flgPrecio;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getFlgShowEstudiante() {
        return flgShowEstudiante;
    }

    public Boolean getFlgShowExAlumno() {
        return flgShowExAlumno;
    }

    public Boolean getFlgShowExterno() {
        return flgShowExterno;
    }

    public Boolean getFlgProgramacion() {
        return flgProgramacion;
    }

    public Boolean getFlgPrecio() {
        return flgPrecio;
    }

//    public Integer getCuentad() {
//        return cuentad;
//    }
//
//    public void setCuentad(Integer cuentad) {
//        this.cuentad = cuentad;
//    }
//
//    public Integer getCuentah() {
//        return cuentah;
//    }
//
//    public void setCuentah(Integer cuentah) {
//        this.cuentah = cuentah;
//    }
    public PlanContableBean getPlanContableCuentaDBean() {
        if (planContableCuentaDBean == null) {
            planContableCuentaDBean = new PlanContableBean();
        }
        return planContableCuentaDBean;
    }

    public void setPlanContableCuentaDBean(PlanContableBean planContableCuentaDBean) {
        this.planContableCuentaDBean = planContableCuentaDBean;
    }

    public PlanContableBean getPlanContableCuentaHBean() {
        if (planContableCuentaHBean == null) {
            planContableCuentaHBean = new PlanContableBean();
        }
        return planContableCuentaHBean;
    }

    public void setPlanContableCuentaHBean(PlanContableBean planContableCuentaHBean) {
        this.planContableCuentaHBean = planContableCuentaHBean;
    }

    public TipoConceptoBean getTipoConceptoBean() {
        if (tipoConceptoBean == null) {
            tipoConceptoBean = new TipoConceptoBean();
        }
        return tipoConceptoBean;
    }

    public void setTipoConceptoBean(TipoConceptoBean tipoConceptoBean) {
        this.tipoConceptoBean = tipoConceptoBean;
    }

    public Boolean getFlgShowHijoExAlumno() {
        return flgShowHijoExAlumno;
    }

    public void setFlgShowHijoExAlumno(Boolean flgShowHijoExAlumno) {
        this.flgShowHijoExAlumno = flgShowHijoExAlumno;
    }

    public Boolean getFlgShowHijoEmpleado() {
        return flgShowHijoEmpleado;
    }

    public void setFlgShowHijoEmpleado(Boolean flgShowHijoEmpleado) {
        this.flgShowHijoEmpleado = flgShowHijoEmpleado;
    }

    public String getNombreConceptoVista() {
        if (idConcepto == null) {
            nombreConceptoVista = MaristaConstantes.SIN_CONCEPTO;
            return nombreConceptoVista;
        }
        if (!idConcepto.equals(0)) {
            nombreConceptoVista = nombre;
            return nombreConceptoVista;
        }
        return nombreConceptoVista;
    }

    public void setNombreConceptoVista(String nombreConceptoVista) {
        this.nombreConceptoVista = nombreConceptoVista;
    }

    public CentroResponsabilidadBean getCr() {
        if (cr == null) {
            cr = new CentroResponsabilidadBean();
        }
        return cr;
    }

    public void setCr(CentroResponsabilidadBean cr) {
        this.cr = cr;
    }

    public String getVistaFlgShowEstudiante() {
        if (flgShowEstudiante != null) {
            if (flgShowEstudiante == true) {
                vistaFlgShowEstudiante = MaristaConstantes.SI;
            } else {
                vistaFlgShowEstudiante = MaristaConstantes.NO;
            }
        } else {
            vistaFlgShowEstudiante = MaristaConstantes.GUION;
        }
        return vistaFlgShowEstudiante;
    }

    public void setVistaFlgShowEstudiante(String vistaFlgShowEstudiante) {
        this.vistaFlgShowEstudiante = vistaFlgShowEstudiante;
    }

    public String getVistaFlgShowExAlumno() {
        if (flgShowExAlumno != null) {
            if (flgShowExAlumno == true) {
                vistaFlgShowExAlumno = MaristaConstantes.SI;
            } else {
                vistaFlgShowExAlumno = MaristaConstantes.NO;
            }
        } else {
            vistaFlgShowExAlumno = MaristaConstantes.GUION;
        }
        return vistaFlgShowExAlumno;
    }

    public void setVistaFlgShowExAlumno(String vistaFlgShowExAlumno) {
        this.vistaFlgShowExAlumno = vistaFlgShowExAlumno;
    }

    public String getVistaFlgShowExterno() {
        if (flgShowExterno != null) {
            if (flgShowExterno == true) {
                vistaFlgShowExterno = MaristaConstantes.SI;
            } else {
                vistaFlgShowExterno = MaristaConstantes.NO;
            }
        } else {
            vistaFlgShowExterno = MaristaConstantes.GUION;
        }
        return vistaFlgShowExterno;
    }

    public void setVistaFlgShowExterno(String vistaFlgShowExterno) {
        this.vistaFlgShowExterno = vistaFlgShowExterno;
    }

    public String getVistaFlgShowHijoExAlumno() {
        if (flgShowHijoExAlumno != null) {
            if (flgShowHijoExAlumno == true) {
                vistaFlgShowHijoExAlumno = MaristaConstantes.SI;
            } else {
                vistaFlgShowHijoExAlumno = MaristaConstantes.NO;
            }
        } else {
            vistaFlgShowHijoExAlumno = MaristaConstantes.GUION;
        }
        return vistaFlgShowHijoExAlumno;
    }

    public void setVistaFlgShowHijoExAlumno(String vistaFlgShowHijoExAlumno) {
        this.vistaFlgShowHijoExAlumno = vistaFlgShowHijoExAlumno;
    }

    public String getVistaFlgShowHijoEmpleado() {
        if (flgShowHijoEmpleado != null) {
            if (flgShowHijoEmpleado == true) {
                vistaFlgShowHijoEmpleado = MaristaConstantes.SI;
            } else {
                vistaFlgShowHijoEmpleado = MaristaConstantes.NO;
            }
        } else {
            vistaFlgShowHijoEmpleado = MaristaConstantes.GUION;
        }
        return vistaFlgShowHijoEmpleado;
    }

    public void setVistaFlgShowHijoEmpleado(String vistaFlgShowHijoEmpleado) {
        this.vistaFlgShowHijoEmpleado = vistaFlgShowHijoEmpleado;
    }

    public String getVistaFlgProgramacion() {
        if (flgProgramacion != null) {
            if (flgProgramacion == true) {
                vistaFlgProgramacion = MaristaConstantes.SI;
            } else {
                vistaFlgProgramacion = MaristaConstantes.NO;
            }
        } else {
            vistaFlgProgramacion = MaristaConstantes.GUION;
        }
        return vistaFlgProgramacion;
    }

    public void setVistaFlgProgramacion(String vistaFlgProgramacion) {
        this.vistaFlgProgramacion = vistaFlgProgramacion;
    }

    public String getVistaFlgPrecio() {
        if (flgPrecio != null) {
            if (flgPrecio == true) {
                vistaFlgPrecio = MaristaConstantes.SI;
            } else {
                vistaFlgPrecio = MaristaConstantes.NO;
            }
        } else {
            vistaFlgPrecio = MaristaConstantes.GUION;
        }
        return vistaFlgPrecio;
    }

    public void setVistaFlgPrecio(String vistaFlgPrecio) {
        this.vistaFlgPrecio = vistaFlgPrecio;
    }

    public String getVistaStatus() {
        if (status != null) {
            if (status == true) {
                vistaStatus = MaristaConstantes.ACTIVO;
            } else {
                vistaStatus = MaristaConstantes.Inactivo;
            }
        } else {
            vistaStatus = MaristaConstantes.GUION;
        }
        return vistaStatus;
    }

    public void setVistaStatus(String vistaStatus) {
        this.vistaStatus = vistaStatus;
    }

    public Boolean getFlgTieneCr() {
        return flgTieneCr;
    }

    public void setFlgTieneCr(Boolean flgTieneCr) {
        this.flgTieneCr = flgTieneCr;
    }

    public String getFlgTieneCrVista() {
        return flgTieneCrVista;
    }

    public void setFlgTieneCrVista(String flgTieneCrVista) {
        this.flgTieneCrVista = flgTieneCrVista;
    }

}

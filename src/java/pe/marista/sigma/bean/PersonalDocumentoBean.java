/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author Administrador
 */
public class PersonalDocumentoBean implements Serializable {

    private Integer idPersonalDocumento;
    private UnidadNegocioBean unidadNegocioBean;
    private PersonalBean personalBean;//id
    private DocumentoBean documentoBean;//id
    private Boolean status;
    private Boolean flgCaduca;
    private Date fechaCaduca;
    private CodigoBean tipoCopiaBean;
    private Boolean flgObligatorio;
    private Boolean flgPresentacion;
    private Date fechaPresentacion;
    private String creaPor;
    private Date creaFecha;
    private Boolean collapsed = true;
    private String modiPor;
    //vista
    private String flgCaducaVista;
    private String statusVista;
    private String flgObligatorioVista;
    private String fechaCaducaVista;
    private String flgPresentacionVista;
    private String fechaPresentacionVista;

    public Integer getIdPersonalDocumento() {
        return idPersonalDocumento;
    }

    public void setIdPersonalDocumento(Integer idPersonalDocumento) {
        this.idPersonalDocumento = idPersonalDocumento;
    }

    public PersonalBean getPersonalBean() {
        if (personalBean == null) {
            personalBean = new PersonalBean();
        }
        return personalBean;
    }

    public void setPersonalBean(PersonalBean personalBean) {
        this.personalBean = personalBean;
    }

    public DocumentoBean getDocumentoBean() {
        if (documentoBean == null) {
            documentoBean = new DocumentoBean();
        }
        return documentoBean;
    }

    public void setDocumentoBean(DocumentoBean documentoBean) {
        this.documentoBean = documentoBean;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean isFlgCaduca() {
        return flgCaduca;
    }

    public void setFlgCaduca(Boolean flgCaduca) {
        this.flgCaduca = flgCaduca;
    }

    public Date getFechaCaduca() {
        return fechaCaduca;
    }

    public void setFechaCaduca(Date fechaCaduca) {
        this.fechaCaduca = fechaCaduca;
    }

    public Date getFechaPresentacion() {
        return fechaPresentacion;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        this.fechaPresentacion = fechaPresentacion;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public CodigoBean getTipoCopiaBean() {
        if (tipoCopiaBean == null) {
            tipoCopiaBean = new CodigoBean();
        }
        return tipoCopiaBean;
    }

    public void setTipoCopiaBean(CodigoBean tipoCopiaBean) {
        this.tipoCopiaBean = tipoCopiaBean;
    }

    public String getFlgCaducaVista() {
        if (flgCaduca != null) {
            if (flgCaduca == true) {
                flgCaducaVista = MaristaConstantes.SI;
            }
            if (flgCaduca == false) {
                flgCaducaVista = MaristaConstantes.NO;
            }
        }

        return flgCaducaVista;
    }

    public void setFlgCaducaVista(String flgCaducaVista) {
        this.flgCaducaVista = flgCaducaVista;
    }

    public String getStatusVista() {
        if (status != null) {
            if (status == true) {
                statusVista = MaristaConstantes.ESTADO_ENTREGADO;
            }
            if (status == false) {
                statusVista = MaristaConstantes.ESTADO_PENDIENTE;
            }
        }

        return statusVista;
    }

    public void setStatusVista(String statusVista) {
        this.statusVista = statusVista;
    }

    public String getFlgObligatorioVista() {
        if (flgObligatorio != null) {
            if (flgObligatorio == true) {
                flgObligatorioVista = "SI";
            }
            if (flgObligatorio == false) {
                flgObligatorioVista = "NO";
            }
        }
        return flgObligatorioVista;
    }

    public void setFlgObligatorioVista(String flgObligatorioVista) {
        this.flgObligatorioVista = flgObligatorioVista;
    }

    public String getFechaCaducaVista() {
        if (flgCaduca != null) {
            if (flgCaduca == false) {
                fechaCaducaVista = MaristaConstantes.SIN_FEC_CADUCA;
                fechaCaduca = null;
            }
            if (flgCaduca == true) {

                if (fechaCaduca != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String date = sdf.format(fechaCaduca);
                    fechaCaducaVista = date;
                }
                if (fechaCaduca == null) {
                    fechaCaducaVista = MaristaConstantes.CON_FEC_CADUCA;
                }
            }
        }
        return fechaCaducaVista;
    }

    public void setFechaCaducaVista(String fechaCaducaVista) {
        this.fechaCaducaVista = fechaCaducaVista;
    }

    public String getFlgPresentacionVista() {
        if (flgPresentacion != null) {
            if (flgPresentacion == true) {
                flgPresentacionVista = MaristaConstantes.SI;
            }
            if (flgPresentacion == false) {
                flgPresentacionVista = MaristaConstantes.NO;
            }
        }
        return flgPresentacionVista;
    }

    public void setFlgPresentacionVista(String flgPresentacionVista) {
        this.flgPresentacionVista = flgPresentacionVista;
    }

    public String getFechaPresentacionVista() {
        if (flgPresentacion != null) {
            if (flgPresentacion == false) {
                fechaPresentacionVista = MaristaConstantes.SIN_FEC_CADUCA;
                fechaPresentacion = null;
            }
            if (flgPresentacion == true) {
                if (fechaPresentacion != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String date = sdf.format(fechaPresentacion);
                    fechaPresentacionVista = date;
                }
                if (fechaPresentacion == null) {
                    fechaPresentacionVista = MaristaConstantes.CON_FEC_PRESENTACION;
                }
            }
        }

        return fechaPresentacionVista;
    }

    public void setFechaPresentacionVista(String fechaPresentacionVista) {
        this.fechaPresentacionVista = fechaPresentacionVista;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
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

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public Boolean getFlgObligatorio() {
        return flgObligatorio;
    }

    public void setFlgObligatorio(Boolean flgObligatorio) {
        this.flgObligatorio = flgObligatorio;
    }

    public Boolean getFlgPresentacion() {
        return flgPresentacion;
    }

    public void setFlgPresentacion(Boolean flgPresentacion) {
        this.flgPresentacion = flgPresentacion;
    }

    public Boolean getCollapsed() {
        return collapsed;
    }

    public void setCollapsed(Boolean collapsed) {
        this.collapsed = collapsed;
    }
    
    

}

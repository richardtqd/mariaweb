/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author Administrador
 */
public class TipoSolicitudBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;//uniNeg 
    private Integer idTipoSolicitud;
    private String nombre;
    private CodigoBean tipoAmbitoSolBean;
    //Autoriza1
//    private CodigoBean tipoAutoriza1Bean;
    private PersonalBean idAutorizaPer1Bean;
    private UniNegUniOrgBean idAutorizaUO1Bean;
    //Autoriza2
//    private CodigoBean tipoAutoriza2Bean;
    private PersonalBean idAutorizaPer2Bean;
    private UniNegUniOrgBean idAutorizaUO2Bean;
    //Autoriza3
//    private CodigoBean tipoAutoriza3Bean;
    private PersonalBean idAutorizaPer3Bean;
    private UniNegUniOrgBean idAutorizaUO3Bean;
    private Integer ans1;//acuerdo nivel servicio Autoriza1 
    private Integer ans2;//acuerdo nivel servicio Autoriza2
    private Boolean flgAutoEscala;
    private Boolean status=true;

    private String creaPor;
    private Date creaFecha;
    private String modiPor;

    //ayuda
    private String tipoAutoriza1Vista;
    private String tipoAutoriza2Vista;
    private String tipoAutoriza3Vista;
    private String idAutoriza1Vista;
    private String idAutoriza2Vista;
    private String idAutoriza3Vista;
    private String flgAutoEscalaVista;
    private String statusVista;
    private String ans1Vista;
    private String ans2Vista;

    private String idTipoAutoriza1;
    private String idTipoAutoriza2;
    private String idTipoAutoriza3;

    public TipoSolicitudBean() {
        this.status=true;
    }

    public TipoSolicitudBean(UnidadNegocioBean unidadNegocioBean, String codigo) {
        this.unidadNegocioBean = unidadNegocioBean;
        CodigoBean cod = new CodigoBean();
        cod.setCodigo(codigo); 
        this.tipoAmbitoSolBean=cod; 
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

    public Integer getIdTipoSolicitud() {
        return idTipoSolicitud;
    }

    public void setIdTipoSolicitud(Integer idTipoSolicitud) {
        this.idTipoSolicitud = idTipoSolicitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public CodigoBean getTipoAmbitoSolBean() {
        if (tipoAmbitoSolBean == null) {
            tipoAmbitoSolBean = new CodigoBean();
        }
        return tipoAmbitoSolBean;
    }

    public void setTipoAmbitoSolBean(CodigoBean tipoAmbitoSolBean) {
        this.tipoAmbitoSolBean = tipoAmbitoSolBean;
    }

//    public CodigoBean getTipoAutoriza1Bean() {
//        if (tipoAutoriza1Bean == null) {
//            tipoAutoriza1Bean = new CodigoBean();
//        }
//        return tipoAutoriza1Bean;
//    }
//
//    public void setTipoAutoriza1Bean(CodigoBean tipoAutoriza1Bean) {
//        this.tipoAutoriza1Bean = tipoAutoriza1Bean;
//    }
    public PersonalBean getIdAutorizaPer1Bean() {
        if (idAutorizaPer1Bean == null) {
            idAutorizaPer1Bean = new PersonalBean();
        }
        return idAutorizaPer1Bean;
    }

    public void setIdAutorizaPer1Bean(PersonalBean idAutorizaPer1Bean) {
        this.idAutorizaPer1Bean = idAutorizaPer1Bean;
    }

    public UniNegUniOrgBean getIdAutorizaUO1Bean() {
        if (idAutorizaUO1Bean == null) {
            idAutorizaUO1Bean = new UniNegUniOrgBean();
        }
        return idAutorizaUO1Bean;
    }

    public void setIdAutorizaUO1Bean(UniNegUniOrgBean idAutorizaUO1Bean) {
        this.idAutorizaUO1Bean = idAutorizaUO1Bean;
    }

//    public CodigoBean getTipoAutoriza2Bean() {
//        if (tipoAutoriza2Bean == null) {
//            tipoAutoriza2Bean = new CodigoBean();
//        }
//        return tipoAutoriza2Bean;
//    }
//
//    public void setTipoAutoriza2Bean(CodigoBean tipoAutoriza2Bean) {
//        this.tipoAutoriza2Bean = tipoAutoriza2Bean;
//    }
    public PersonalBean getIdAutorizaPer2Bean() {
        if (idAutorizaPer2Bean == null) {
            idAutorizaPer2Bean = new PersonalBean();
        }
        return idAutorizaPer2Bean;
    }

    public void setIdAutorizaPer2Bean(PersonalBean idAutorizaPer2Bean) {
        this.idAutorizaPer2Bean = idAutorizaPer2Bean;
    }

    public UniNegUniOrgBean getIdAutorizaUO2Bean() {
        if (idAutorizaUO2Bean == null) {
            idAutorizaUO2Bean = new UniNegUniOrgBean();
        }
        return idAutorizaUO2Bean;
    }

    public void setIdAutorizaUO2Bean(UniNegUniOrgBean idAutorizaUO2Bean) {
        this.idAutorizaUO2Bean = idAutorizaUO2Bean;
    }
//
//    public CodigoBean getTipoAutoriza3Bean() {
//        if (tipoAutoriza3Bean == null) {
//            tipoAutoriza3Bean = new CodigoBean();
//        }
//        return tipoAutoriza3Bean;
//    }
//
//    public void setTipoAutoriza3Bean(CodigoBean tipoAutoriza3Bean) {
//        this.tipoAutoriza3Bean = tipoAutoriza3Bean;
//    }

    public PersonalBean getIdAutorizaPer3Bean() {
        if (idAutorizaPer3Bean == null) {
            idAutorizaPer3Bean = new PersonalBean();
        }
        return idAutorizaPer3Bean;
    }

    public void setIdAutorizaPer3Bean(PersonalBean idAutorizaPer3Bean) {
        this.idAutorizaPer3Bean = idAutorizaPer3Bean;
    }

    public UniNegUniOrgBean getIdAutorizaUO3Bean() {
        if (idAutorizaUO3Bean == null) {
            idAutorizaUO3Bean = new UniNegUniOrgBean();
        }
        return idAutorizaUO3Bean;
    }

    public void setIdAutorizaUO3Bean(UniNegUniOrgBean idAutorizaUO3Bean) {
        this.idAutorizaUO3Bean = idAutorizaUO3Bean;
    }

    public Integer getAns1() {
        return ans1;
    }

    public void setAns1(Integer ans1) {
        this.ans1 = ans1;
    }

    public Integer getAns2() {
        return ans2;
    }

    public void setAns2(Integer ans2) {
        this.ans2 = ans2;
    }

    public Boolean getFlgAutoEscala() {
        return flgAutoEscala;
    }

    public void setFlgAutoEscala(Boolean flgAutoEscala) {
        this.flgAutoEscala = flgAutoEscala;
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

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public String getTipoAutoriza1Vista() {
        if (idTipoAutoriza1 != null) {
            if (idTipoAutoriza1.equals("P")) {
                tipoAutoriza1Vista = MaristaConstantes.COD_PERSONAL;
            }
            if (idTipoAutoriza1.equals("U")) {
                tipoAutoriza1Vista = MaristaConstantes.COD_UNIORG;
            }
        }
        if (idTipoAutoriza1 == null) {
            tipoAutoriza1Vista = MaristaConstantes.SIN_AUTORIZADOR;
        }
        return tipoAutoriza1Vista;
    }

    public void setTipoAutoriza1Vista(String tipoAutoriza1Vista) {
        this.tipoAutoriza1Vista = tipoAutoriza1Vista;
    }

    public String getIdAutoriza1Vista() {
        if (idAutoriza1Vista == null) {
            idAutoriza1Vista = MaristaConstantes.SIN_AUTORIZADOR;
        }
        return idAutoriza1Vista;
    }

    public void setIdAutoriza1Vista(String idAutoriza1Vista) {
        this.idAutoriza1Vista = idAutoriza1Vista;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getIdAutoriza2Vista() {
        if (idAutoriza2Vista == null) {
            idAutoriza2Vista = MaristaConstantes.SIN_AUTORIZADOR;
        }
        return idAutoriza2Vista;
    }

    public void setIdAutoriza2Vista(String idAutoriza2Vista) {
        this.idAutoriza2Vista = idAutoriza2Vista;
    }

    public String getIdAutoriza3Vista() {
        if (idAutoriza3Vista == null) {
            idAutoriza3Vista = MaristaConstantes.SIN_AUTORIZADOR;
        }
        return idAutoriza3Vista;
    }

    public void setIdAutoriza3Vista(String idAutoriza3Vista) {
        this.idAutoriza3Vista = idAutoriza3Vista;
    }

    public String getTipoAutoriza2Vista() {
        if (idTipoAutoriza2 != null) {
            if (idTipoAutoriza2.equals("P")) {
                tipoAutoriza2Vista = MaristaConstantes.COD_PERSONAL;
            }
            if (idTipoAutoriza2.equals("U")) {
                tipoAutoriza2Vista = MaristaConstantes.COD_UNIORG;
            }
        }
        if (idTipoAutoriza2 == null) {
            tipoAutoriza2Vista = MaristaConstantes.SIN_AUTORIZADOR;
        }
        return tipoAutoriza2Vista;
    }

    public void setTipoAutoriza2Vista(String tipoAutoriza2Vista) {
        this.tipoAutoriza2Vista = tipoAutoriza2Vista;
    }

    public String getTipoAutoriza3Vista() {
        if (idTipoAutoriza3 != null) {
            if (idTipoAutoriza3.equals("P")) {
                tipoAutoriza3Vista = MaristaConstantes.COD_PERSONAL;
            }
            if (idTipoAutoriza3.equals("U")) {
                tipoAutoriza3Vista = MaristaConstantes.COD_UNIORG;
            }
        }
        if (idTipoAutoriza3 == null) {
            tipoAutoriza3Vista = MaristaConstantes.SIN_AUTORIZADOR;
        }
        return tipoAutoriza3Vista;
    }

    public void setTipoAutoriza3Vista(String tipoAutoriza3Vista) {
        this.tipoAutoriza3Vista = tipoAutoriza3Vista;
    }

    public String getFlgAutoEscalaVista() {
        if (flgAutoEscala != null) {
            if (flgAutoEscala == true) {
                flgAutoEscalaVista = MaristaConstantes.SI;
            }
            if (flgAutoEscala == false) {
                flgAutoEscalaVista = MaristaConstantes.NO;
            }
        }
        return flgAutoEscalaVista;
    }

    public void setFlgAutoEscalaVista(String flgAutoEscalaVista) {
        this.flgAutoEscalaVista = flgAutoEscalaVista;
    }

    public String getStatusVista() {
        if (status != null) {
            if (status == true) {
                statusVista = MaristaConstantes.Activo;
            }
            if (status == false) {
                statusVista = MaristaConstantes.Inactivo;
            }
        }
        return statusVista;
    }

    public void setStatusVista(String statusVista) {
        this.statusVista = statusVista;
    }

    public String getAns1Vista() {
        if (ans1 != null) {
            if (ans1 != null) {
                ans1Vista = ans1.toString();
            }
        }
        if (ans1 == null) {
            ans1Vista = MaristaConstantes.SIN_ANS;
        }
        return ans1Vista;
    }

    public void setAns1Vista(String ans1Vista) {
        this.ans1Vista = ans1Vista;
    }

    public String getAns2Vista() {
        if (ans2 != null) {
            if (ans2 != null) {
                ans2Vista = ans2.toString();
            }
        }
        if (ans2 == null) {
            ans2Vista = MaristaConstantes.SIN_ANS;
        }
        return ans2Vista;
    }

    public void setAns2Vista(String ans2Vista) {
        this.ans2Vista = ans2Vista;
    }

    public String getIdTipoAutoriza1() {
        return idTipoAutoriza1;
    }

    public void setIdTipoAutoriza1(String idTipoAutoriza1) {
        this.idTipoAutoriza1 = idTipoAutoriza1;
    }

    public String getIdTipoAutoriza2() {
        return idTipoAutoriza2;
    }

    public void setIdTipoAutoriza2(String idTipoAutoriza2) {
        this.idTipoAutoriza2 = idTipoAutoriza2;
    }

    public String getIdTipoAutoriza3() {
        return idTipoAutoriza3;
    }

    public void setIdTipoAutoriza3(String idTipoAutoriza3) {
        this.idTipoAutoriza3 = idTipoAutoriza3;
    }

}

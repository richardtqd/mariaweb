package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;
import pe.marista.sigma.MaristaConstantes;

/**
 *
 * @author Administrador
 */
public class EstudianteDocumentoBean implements Serializable {

    private Integer idEstudianteDocumento;
    private EstudianteBean estudianteBean;//idEstudiante
    private ProcesoBean procesoBean;
    private CheckListBean checkListBean;
    private boolean status;
    private Boolean flgOriginal;
//    private CodigoBean codigoBean;//idTipoCopia 
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    
    private String statusVista;
    

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }
 

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }
    
    public Integer getIdEstudianteDocumento() {
        return idEstudianteDocumento;
    }

    public void setIdEstudianteDocumento(Integer idEstudianteDocumento) {
        this.idEstudianteDocumento = idEstudianteDocumento;
    }

    public EstudianteBean getEstudianteBean() {
        if(estudianteBean==null){
            estudianteBean = new EstudianteBean();
        }
        return estudianteBean;
    }

    public void setEstudianteBean(EstudianteBean estudianteBean) {
        this.estudianteBean = estudianteBean;
    }
 
    public Boolean isFlgOriginal() {
        return flgOriginal;
    }

    public void setFlgOriginal(Boolean flgOriginal) {
        this.flgOriginal = flgOriginal;
    }
//
//    public CodigoBean getCodigoBean() {
//        if(codigoBean==null){
//            codigoBean = new CodigoBean();
//        }
//        return codigoBean;
//    }
//
//    public void setCodigoBean(CodigoBean codigoBean) {
//        this.codigoBean = codigoBean;
//    }

    public ProcesoBean getProcesoBean() {
        if(procesoBean == null)
        {
         procesoBean= new ProcesoBean();
        }
        return procesoBean;
    }

    public void setProcesoBean(ProcesoBean procesoBean) {
        this.procesoBean = procesoBean;
    }

    public CheckListBean getCheckListBean() {
        if (checkListBean== null){
            checkListBean = new CheckListBean();
        }
        return checkListBean;
    }

    public void setCheckListBean(CheckListBean checkListBean) {
        this.checkListBean = checkListBean;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

   public String getStatusVista() {
        if (status == true) {
            statusVista = MaristaConstantes.ESTADO_ENTREGADO;
        }
        if (status == false) {
            statusVista = MaristaConstantes.ESTADO_PENDIENTE;
        }
        return statusVista;
    }

    public void setStatusVista(String statusVista) {
        this.statusVista = statusVista;
    }
  
}

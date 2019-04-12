
package pe.marista.sigma.bean;
import java.io.Serializable;
import java.util.Date;

public class MovimientoActivoBean implements Serializable{
    
    private UnidadNegocioBean unidadNegocioBean;
    private InventarioActivoBean inventarioActivoBean;
//    private Integer idInventarioActivo;
    private Integer idMovimientoActivo;
    private Integer anio;
    private String objReferencia;
    private Integer objid;
    private Date fechaMov;
    private Date fechaRetorno;
    private CodigoBean tipoMovActivoBean;
    private MotivoMovimientoBean motivoMovimientoBean;
//    private Integer idMovimientoMotivo;
    private CodigoBean tipoDuracionBean;
    private UnidadOrganicaBean uniOrgOrigenBean;
    private UnidadOrganicaBean uniOrgDestinoBean;
    private PersonalBean respOrigenBean;
    private PersonalBean resoDestinoBean;
    private String observacion;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private Integer idRespOrigen;
    private Integer idRespDestino;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer nroMovAct;
    
    
    public MovimientoActivoBean(){
    this.fechaMov = new Date();
}
    
    
    public Integer getIdMovimientoActivo() {
        return idMovimientoActivo;
    }

    public void setIdMovimientoActivo(Integer idMovimientoActivo) {
        this.idMovimientoActivo = idMovimientoActivo;
    }

    public InventarioActivoBean getInventarioActivoBean() {
        if(inventarioActivoBean == null){
            inventarioActivoBean = new InventarioActivoBean();
        }
        return inventarioActivoBean;
    }

    public void setInventarioActivoBean(InventarioActivoBean inventarioActivoBean) {
        this.inventarioActivoBean = inventarioActivoBean;
    }

    public UnidadNegocioBean getUnidadNegocioBean() {
        if(unidadNegocioBean == null){
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public String getObjReferencia() {
        return objReferencia;
    }

    public void setObjReferencia(String objReferencia) {
        this.objReferencia = objReferencia;
    }

    public Integer getObjid() {
        return objid;
    }

    public void setObjid(Integer objid) {
        this.objid = objid;
    }

    public Date getFechaMov() {
        return fechaMov;
    }

    public void setFechaMov(Date fechaMov) {
        this.fechaMov = fechaMov;
    }

    public Date getFechaRetorno() {
        return fechaRetorno;
    }

    public void setFechaRetorno(Date fechaRetorno) {
        this.fechaRetorno = fechaRetorno;
    }
 
    
    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public MotivoMovimientoBean getMotivoMovimientoBean() {
        if( motivoMovimientoBean == null ){
            motivoMovimientoBean = new MotivoMovimientoBean();
        }
        return motivoMovimientoBean;
    }

    public void setMotivoMovimientoBean(MotivoMovimientoBean motivoMovimientoBean) {
        this.motivoMovimientoBean = motivoMovimientoBean;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public CodigoBean getTipoMovActivoBean() {
        if(tipoMovActivoBean==null)
        {
            tipoMovActivoBean = new CodigoBean();
        }
        return tipoMovActivoBean;
    }

    public void setTipoMovActivoBean(CodigoBean tipoMovActivoBean) {
        this.tipoMovActivoBean = tipoMovActivoBean;
    }

    public CodigoBean getTipoDuracionBean() {
        if(tipoDuracionBean==null)
        {
            tipoDuracionBean= new CodigoBean();
        }
        return tipoDuracionBean;
    }

    public void setTipoDuracionBean(CodigoBean tipoDuracionBean) {
        this.tipoDuracionBean = tipoDuracionBean;
    }

    public UnidadOrganicaBean getUniOrgOrigenBean() {
        if(uniOrgOrigenBean==null)
        {
            uniOrgOrigenBean = new UnidadOrganicaBean();
        }
        return uniOrgOrigenBean;
    }

    public void setUniOrgOrigenBean(UnidadOrganicaBean uniOrgOrigenBean) {
        this.uniOrgOrigenBean = uniOrgOrigenBean;
    }

    public UnidadOrganicaBean getUniOrgDestinoBean() {
        if(uniOrgDestinoBean==null)
        {
            uniOrgDestinoBean= new UnidadOrganicaBean();
        }
        return uniOrgDestinoBean;
    }

    public void setUniOrgDestinoBean(UnidadOrganicaBean uniOrgDestinoBean) {
        this.uniOrgDestinoBean = uniOrgDestinoBean;
    }

    public Integer getIdRespOrigen() {
        return idRespOrigen;
    }

    public void setIdRespOrigen(Integer idRespOrigen) {
        this.idRespOrigen = idRespOrigen;
    }

    public Integer getIdRespDestino() {
        return idRespDestino;
    }

    public void setIdRespDestino(Integer idRespDestino) {
        this.idRespDestino = idRespDestino;
    }

    public PersonalBean getRespOrigenBean() {
        if (respOrigenBean==null) {
            respOrigenBean= new PersonalBean();
            
        }
        return respOrigenBean;
    }

    public void setRespOrigenBean(PersonalBean respOrigenBean) {
        this.respOrigenBean = respOrigenBean;
    }

    public PersonalBean getResoDestinoBean() {
        if (resoDestinoBean==null) {
            resoDestinoBean= new PersonalBean();
            
        }
        return resoDestinoBean;
    }

    public void setResoDestinoBean(PersonalBean resoDestinoBean) {
        this.resoDestinoBean = resoDestinoBean;
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

    public Integer getNroMovAct() {
        return nroMovAct;
    }

    public void setNroMovAct(Integer nroMovAct) {
        this.nroMovAct = nroMovAct;
    }
    
}

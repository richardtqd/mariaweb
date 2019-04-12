/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.CronogramaPagoBean;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.DetDocIngresoBean;
import pe.marista.sigma.bean.DocIngresoBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.ProcesoRecuperacionBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.BecaService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.CuentasPorCobrarService;
import pe.marista.sigma.service.DocIngresoService;
import pe.marista.sigma.service.MatriculaService;
import pe.marista.sigma.service.ProcesoEnvioService;
import pe.marista.sigma.service.ProcesoRecuperacionService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author MS002
 */
public class ResolveCtaCteMB implements Serializable {

    /**
     * Creates a new instance of ResolveCtaCteMB
     */
    @PostConstruct
    public void ResolveCtaCteMB() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            //Filtro  Recuperacion
            getProcesoRecuperacionFiltroBean().getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            //Filtro  Matricula            
            getMatriculafiltroBean().getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            getCuentasPorCobrar();
            getCuentasPorCobrar().getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getCuentasPorCobrarDes();
            getCuentasPorCobrarDes().getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            cargarAnio();
//            MatriculaService matriculaService = BeanFactory.getMatriculaService();
//            getListaMatriculaFiltroBean();
//            listaMatriculaFiltroBean = matriculaService.obtenerMatricula();

            BecaService becaService = BeanFactory.getBecaService();
            getListaCronograma();
            listaCronograma = becaService.obtenerCronograma();

            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaTipoEstadoCuenta();
            listaTipoEstadoCuenta = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_CTA_CTE));

            getCuentaOrigen();
            getCuentaDestino();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    private MatriculaBean matriculaBean;
    private MatriculaBean matriculafiltroBean;

    private ProcesoRecuperacionBean procesoRecuperacionBean;
    private ProcesoRecuperacionBean procesoRecuperacionFiltroBean;
    private List<ProcesoRecuperacionBean> listaProcesoRecuperacionBean;

    private List<MatriculaBean> listaMatriculaBean;
    private List<MatriculaBean> listaMatriculaFiltroBean;
    private List<CodigoBean> listaTipoEstadoCuenta;

    private CuentasPorCobrarBean cuentasPorCobrarBean;
    private CuentasPorCobrarBean cuentasDestinoBean;
    private CuentasPorCobrarBean cuentaResultadoBean;
    private List<CuentasPorCobrarBean> listaCuentasPorCobrarBean;
    private List<CuentasPorCobrarBean> listaCuentasDestinoBean;

    private EstudianteBean estudianteBean;
    private CuentasPorCobrarBean cuentasPorCobrar;
    private CuentasPorCobrarBean cuentasPorCobrarDes;
    private List<CuentasPorCobrarBean> listaCuentasPorCobrar;
    private List<CuentasPorCobrarBean> listaCuentasPorCobrarDes;
    private List<Integer> listaAnios;
    private List<CuentasPorCobrarBean> listaCuentasConfirm;
    private List<CronogramaPagoBean> listaCronograma;
    private CuentasPorCobrarBean cuentaOrigen;
    private CuentasPorCobrarBean cuentaDestino;

    //Ayuda
    private Boolean renderDestino = false;
    private Boolean renderOrigen = false;
    private Boolean renderChange = true;

    private Integer opcion;

    public MatriculaBean getMatriculaBean() {
        if (matriculaBean == null) {
            matriculaBean = new MatriculaBean();
        }
        return matriculaBean;
    }

    public void setMatriculaBean(MatriculaBean matriculaBean) {
        this.matriculaBean = matriculaBean;
    }

    public MatriculaBean getMatriculafiltroBean() {
        if (matriculafiltroBean == null) {
            matriculafiltroBean = new MatriculaBean();
        }
        return matriculafiltroBean;
    }

    public void setMatriculafiltroBean(MatriculaBean matriculafiltroBean) {
        this.matriculafiltroBean = matriculafiltroBean;
    }

    public List<MatriculaBean> getListaMatriculaBean() {
        if (listaMatriculaBean == null) {
            listaMatriculaBean = new ArrayList<>();
        }
        return listaMatriculaBean;
    }

    public void setListaMatriculaBean(List<MatriculaBean> listaMatriculaBean) {
        this.listaMatriculaBean = listaMatriculaBean;
    }

    public List<MatriculaBean> getListaMatriculaFiltroBean() {
        if (listaMatriculaFiltroBean == null) {
            listaMatriculaFiltroBean = new ArrayList<>();
        }
        return listaMatriculaFiltroBean;
    }

    public void setListaMatriculaFiltroBean(List<MatriculaBean> listaMatriculaFiltroBean) {
        this.listaMatriculaFiltroBean = listaMatriculaFiltroBean;
    }

    public ProcesoRecuperacionBean getProcesoRecuperacionBean() {
        if (procesoRecuperacionBean == null) {
            procesoRecuperacionBean = new ProcesoRecuperacionBean();
        }
        return procesoRecuperacionBean;
    }

    public void setProcesoRecuperacionBean(ProcesoRecuperacionBean procesoRecuperacionBean) {
        this.procesoRecuperacionBean = procesoRecuperacionBean;
    }

    public ProcesoRecuperacionBean getProcesoRecuperacionFiltroBean() {
        if (procesoRecuperacionFiltroBean == null) {
            procesoRecuperacionFiltroBean = new ProcesoRecuperacionBean();
        }
        return procesoRecuperacionFiltroBean;
    }

    public void setProcesoRecuperacionFiltroBean(ProcesoRecuperacionBean procesoRecuperacionFiltroBean) {
        this.procesoRecuperacionFiltroBean = procesoRecuperacionFiltroBean;
    }

    public List<ProcesoRecuperacionBean> getListaProcesoRecuperacionBean() {
        if (listaProcesoRecuperacionBean == null) {
            listaProcesoRecuperacionBean = new ArrayList<>();
        }
        return listaProcesoRecuperacionBean;
    }

    public void setListaProcesoRecuperacionBean(List<ProcesoRecuperacionBean> listaProcesoRecuperacionBean) {
        this.listaProcesoRecuperacionBean = listaProcesoRecuperacionBean;
    }

    public List<CuentasPorCobrarBean> getListaCuentasPorCobrarBean() {
        if (listaCuentasPorCobrarBean == null) {
            listaCuentasPorCobrarBean = new ArrayList<>();
        }
        return listaCuentasPorCobrarBean;
    }

    public void setListaCuentasPorCobrarBean(List<CuentasPorCobrarBean> listaCuentasPorCobrarBean) {
        this.listaCuentasPorCobrarBean = listaCuentasPorCobrarBean;
    }

    public CuentasPorCobrarBean getCuentasPorCobrarBean() {
        if (cuentasPorCobrarBean == null) {
            cuentasPorCobrarBean = new CuentasPorCobrarBean();
        }
        return cuentasPorCobrarBean;
    }

    public void setCuentasPorCobrarBean(CuentasPorCobrarBean cuentasPorCobrarBean) {
        this.cuentasPorCobrarBean = cuentasPorCobrarBean;
    }

    public List<CuentasPorCobrarBean> getListaCuentasDestinoBean() {
        if (listaCuentasDestinoBean == null) {
            listaCuentasDestinoBean = new ArrayList<>();
        }
        return listaCuentasDestinoBean;
    }

    public void setListaCuentasDestinoBean(List<CuentasPorCobrarBean> listaCuentasDestinoBean) {
        this.listaCuentasDestinoBean = listaCuentasDestinoBean;
    }

    public CuentasPorCobrarBean getCuentasDestinoBean() {
        if (cuentasDestinoBean == null) {
            cuentasDestinoBean = new CuentasPorCobrarBean();
        }
        return cuentasDestinoBean;
    }

    public void setCuentasDestinoBean(CuentasPorCobrarBean cuentasDestinoBean) {
        this.cuentasDestinoBean = cuentasDestinoBean;
    }

    public Boolean getRenderDestino() {
        return renderDestino;
    }

    public void setRenderDestino(Boolean renderDestino) {
        this.renderDestino = renderDestino;
    }

    public Boolean getRenderOrigen() {
        return renderOrigen;
    }

    public void setRenderOrigen(Boolean renderOrigen) {
        this.renderOrigen = renderOrigen;
    }

    public Boolean getRenderChange() {
        return renderChange;
    }

    public void setRenderChange(Boolean renderChange) {
        this.renderChange = renderChange;
    }

    //Metodos ================================================================================================================
    public void obtenerFiltroRecuperacion() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoRecuperacionService procesoRecuperacionService = BeanFactory.getProcesoRecuperacionService();
            if (procesoRecuperacionFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona() != null && !procesoRecuperacionFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona().equals("")) {
                procesoRecuperacionFiltroBean.getEstudianteBean().getPersonaBean().setIdPersona(procesoRecuperacionFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona());
            }
            if (procesoRecuperacionFiltroBean.getEstudianteBean().getPersonaBean().getNombre() != null && !procesoRecuperacionFiltroBean.getEstudianteBean().getPersonaBean().getNombre().equals("")) {
                procesoRecuperacionFiltroBean.getEstudianteBean().getPersonaBean().setNombre(procesoRecuperacionFiltroBean.getEstudianteBean().getPersonaBean().getNombre().toUpperCase());
            }
            if (procesoRecuperacionFiltroBean.getEstudianteBean().getPersonaBean().getApepat() != null && !procesoRecuperacionFiltroBean.getEstudianteBean().getPersonaBean().getApepat().equals("")) {
                procesoRecuperacionFiltroBean.getEstudianteBean().getPersonaBean().setApepat(procesoRecuperacionFiltroBean.getEstudianteBean().getPersonaBean().getApepat());
            }
            if (procesoRecuperacionFiltroBean.getEstudianteBean().getPersonaBean().getApemat() != null && !procesoRecuperacionFiltroBean.getEstudianteBean().getPersonaBean().getApemat().equals("")) {
                procesoRecuperacionFiltroBean.getEstudianteBean().getPersonaBean().setApemat(procesoRecuperacionFiltroBean.getEstudianteBean().getPersonaBean().getApemat());
            }
            if (procesoRecuperacionFiltroBean.getDia() != null && !procesoRecuperacionFiltroBean.getDia().equals("")) {
                procesoRecuperacionFiltroBean.setDia(procesoRecuperacionFiltroBean.getDia());
            }
            if (procesoRecuperacionFiltroBean.getMes() != null && !procesoRecuperacionFiltroBean.getMes().equals("")) {
                procesoRecuperacionFiltroBean.setMes(procesoRecuperacionFiltroBean.getMes());
            }
            if (procesoRecuperacionFiltroBean.getAnio() != null && !procesoRecuperacionFiltroBean.getAnio().equals("")) {
                procesoRecuperacionFiltroBean.setAnio(procesoRecuperacionFiltroBean.getAnio());
            }
            if (procesoRecuperacionFiltroBean.getNumOperacion() != null && !procesoRecuperacionFiltroBean.getNumOperacion().equals("")) {
                procesoRecuperacionFiltroBean.setNumOperacion(procesoRecuperacionFiltroBean.getNumOperacion());
            }
            listaProcesoRecuperacionBean = procesoRecuperacionService.obtenerFiltroRecuperacion(procesoRecuperacionFiltroBean);
            if (listaProcesoRecuperacionBean.isEmpty()) {
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeAlert", null), MensajesBackEnd.getValueOfKey("mensajeConsPer", null)));
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerEstudiante(Integer dato) {
        try {
            setOpcion(dato);
            limpiarFiltroCtaDes();
            System.out.println(">>>>" + dato);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerEstadoCuenta(Integer opp) {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            Integer res = 0;
            if (opp.equals(1)) {
                if (getCuentasPorCobrar().getEstudianteBean().getIdEstudiante() != null
                        && !getCuentasPorCobrar().getEstudianteBean().getIdEstudiante().equals("")) {
                    getCuentasPorCobrar().getEstudianteBean().setIdEstudiante(getCuentasPorCobrar().getEstudianteBean().getIdEstudiante());
                    res = 1;
                }
                if (getCuentasPorCobrar().getEstudianteBean().getCodigo() != null
                        && !getCuentasPorCobrar().getEstudianteBean().getCodigo().equals("")) {
                    getCuentasPorCobrar().getEstudianteBean().setCodigo(getCuentasPorCobrar().getEstudianteBean().getCodigo());
                    res = 1;
                }
                if (getCuentasPorCobrar().getEstudianteBean().getPersonaBean().getNombre() != null
                        && !getCuentasPorCobrar().getEstudianteBean().getPersonaBean().getNombre().equals("")) {
                    getCuentasPorCobrar().getEstudianteBean().getPersonaBean().setNombre(getCuentasPorCobrar().getEstudianteBean().getPersonaBean().getNombre());
                    res = 1;
                }
                if (getCuentasPorCobrar().getEstudianteBean().getPersonaBean().getApepat() != null
                        && !getCuentasPorCobrar().getEstudianteBean().getPersonaBean().getApepat().equals("")) {
                    getCuentasPorCobrar().getEstudianteBean().getPersonaBean().setApepat(getCuentasPorCobrar().getEstudianteBean().getPersonaBean().getApepat());
                    res = 1;
                }
                if (getCuentasPorCobrar().getEstudianteBean().getPersonaBean().getApemat() != null
                        && !getCuentasPorCobrar().getEstudianteBean().getPersonaBean().getApemat().equals("")) {
                    getCuentasPorCobrar().getEstudianteBean().getPersonaBean().setApemat(getCuentasPorCobrar().getEstudianteBean().getPersonaBean().getApemat());
                    res = 1;
                }
                if (res.equals(1)) {
                    listaCuentasPorCobrar = procesoEnvioService.obtenerEnvioCuentaRes(getCuentasPorCobrar());
                    if (listaCuentasPorCobrar.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    }
                }
            } else {
                if (opp.equals(2)) {
                    if (getCuentasPorCobrarDes().getEstudianteBean().getIdEstudiante() != null
                            && !getCuentasPorCobrarDes().getEstudianteBean().getIdEstudiante().equals("")) {
                        getCuentasPorCobrarDes().getEstudianteBean().setIdEstudiante(getCuentasPorCobrar().getEstudianteBean().getIdEstudiante());
                        res = 1;
                    }
                    if (getCuentasPorCobrarDes().getEstudianteBean().getCodigo() != null
                            && !getCuentasPorCobrarDes().getEstudianteBean().getCodigo().equals("")) {
                        getCuentasPorCobrarDes().getEstudianteBean().setCodigo(getCuentasPorCobrar().getEstudianteBean().getCodigo());
                        res = 1;
                    }
                    if (getCuentasPorCobrarDes().getEstudianteBean().getPersonaBean().getNombre() != null
                            && !getCuentasPorCobrarDes().getEstudianteBean().getPersonaBean().getNombre().equals("")) {
                        getCuentasPorCobrarDes().getEstudianteBean().getPersonaBean().setNombre(getCuentasPorCobrar().getEstudianteBean().getPersonaBean().getNombre());
                        res = 1;
                    }
                    if (getCuentasPorCobrarDes().getEstudianteBean().getPersonaBean().getApepat() != null
                            && !getCuentasPorCobrarDes().getEstudianteBean().getPersonaBean().getApepat().equals("")) {
                        getCuentasPorCobrarDes().getEstudianteBean().getPersonaBean().setApepat(getCuentasPorCobrar().getEstudianteBean().getPersonaBean().getApepat());
                        res = 1;
                    }
                    if (getCuentasPorCobrarDes().getEstudianteBean().getPersonaBean().getApemat() != null
                            && !getCuentasPorCobrarDes().getEstudianteBean().getPersonaBean().getApemat().equals("")) {
                        getCuentasPorCobrarDes().getEstudianteBean().getPersonaBean().setApemat(getCuentasPorCobrar().getEstudianteBean().getPersonaBean().getApemat());
                        res = 1;
                    }
                    if (res.equals(1)) {
                        listaCuentasPorCobrar = procesoEnvioService.obtenerEnvioCuentaRes(getCuentasPorCobrarDes());
                        if (listaCuentasPorCobrar.isEmpty()) {
                            new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public void obtenerFiltroEstCta(Integer op) {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            MatriculaBean matriculaBean = new MatriculaBean();
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            System.out.println(">>>" + opcion);
            if (op.equals(1)) {
                if (cuentasPorCobrarDes.getEstudianteBean().getIdEstudiante() != null && !cuentasPorCobrarDes.getEstudianteBean().getIdEstudiante().equals("")) {
                    cuentasPorCobrarDes.getEstudianteBean().setIdEstudiante(cuentasPorCobrarDes.getEstudianteBean().getIdEstudiante());
                }
                if (cuentasPorCobrarDes.getEstudianteBean().getPersonaBean().getNombre() != null && !cuentasPorCobrarDes.getEstudianteBean().getPersonaBean().getNombre().equals("")) {
                    cuentasPorCobrarDes.getEstudianteBean().getPersonaBean().setNombre(cuentasPorCobrarDes.getEstudianteBean().getPersonaBean().getNombre().toUpperCase());
                }
                if (cuentasPorCobrarDes.getEstudianteBean().getPersonaBean().getApepat() != null && !cuentasPorCobrarDes.getEstudianteBean().getPersonaBean().getApepat().equals("")) {
                    cuentasPorCobrarDes.getEstudianteBean().getPersonaBean().setApepat(cuentasPorCobrarDes.getEstudianteBean().getPersonaBean().getApepat());
                }
                if (cuentasPorCobrarDes.getEstudianteBean().getPersonaBean().getApemat() != null && !cuentasPorCobrarDes.getEstudianteBean().getPersonaBean().getApemat().equals("")) {
                    cuentasPorCobrarDes.getEstudianteBean().getPersonaBean().setApemat(cuentasPorCobrarDes.getEstudianteBean().getPersonaBean().getApemat());
                }
                if (cuentasPorCobrarDes.getAnios() != null && !cuentasPorCobrar.getAnios().equals("")) {
                    cuentasPorCobrarDes.setAnios(cuentasPorCobrar.getAnios());
                }
                if (cuentasPorCobrarDes.getAnio() != null) {
                    cuentasPorCobrarDes.setAnio(cuentasPorCobrarDes.getAnio());
                }
                if (cuentasPorCobrarDes.getMes() != null) {
                    cuentasPorCobrarDes.setMes(cuentasPorCobrarDes.getMes());
                }
                listaCuentasPorCobrar = procesoEnvioService.obtenerEnvioCuentaRes(cuentasPorCobrar);
                if (listaCuentasPorCobrar.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            } else {
                if (op.equals(2)) {
                    if (cuentasPorCobrarDes.getEstudianteBean().getIdEstudiante() != null && !cuentasPorCobrarDes.getEstudianteBean().getIdEstudiante().equals("")) {
                        cuentasPorCobrarDes.getEstudianteBean().setIdEstudiante(cuentasPorCobrarDes.getEstudianteBean().getIdEstudiante());
                    }
                    if (cuentasPorCobrarDes.getEstudianteBean().getPersonaBean().getNombre() != null && !cuentasPorCobrarDes.getEstudianteBean().getPersonaBean().getNombre().equals("")) {
                        cuentasPorCobrarDes.getEstudianteBean().getPersonaBean().setNombre(cuentasPorCobrarDes.getEstudianteBean().getPersonaBean().getNombre().toUpperCase());
                    }
                    if (cuentasPorCobrarDes.getEstudianteBean().getPersonaBean().getApepat() != null && !cuentasPorCobrarDes.getEstudianteBean().getPersonaBean().getApepat().equals("")) {
                        cuentasPorCobrarDes.getEstudianteBean().getPersonaBean().setApepat(cuentasPorCobrarDes.getEstudianteBean().getPersonaBean().getApepat());
                    }
                    if (cuentasPorCobrarDes.getEstudianteBean().getPersonaBean().getApemat() != null && !cuentasPorCobrarDes.getEstudianteBean().getPersonaBean().getApemat().equals("")) {
                        cuentasPorCobrarDes.getEstudianteBean().getPersonaBean().setApemat(cuentasPorCobrarDes.getEstudianteBean().getPersonaBean().getApemat());
                    }
                    if (cuentasPorCobrarDes.getAnios() != null && !cuentasPorCobrarDes.getAnios().equals("")) {
                        cuentasPorCobrarDes.setAnios(cuentasPorCobrarDes.getAnios());
                    }
                    if (cuentasPorCobrarDes.getAnio() != null) {
                        cuentasPorCobrarDes.setAnio(cuentasPorCobrarDes.getAnio());
                    }
                    if (cuentasPorCobrarDes.getMes() != null) {
                        cuentasPorCobrarDes.setMes(cuentasPorCobrarDes.getMes());
                    }
                    listaCuentasPorCobrarDes = procesoEnvioService.obtenerEnvioCuentaResDes(cuentasPorCobrarDes);
                    if (listaCuentasPorCobrarDes.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    }
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void filtrarEstudianteCuenta() {
        try {
            Integer res = 0;
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            if (getCuentasPorCobrarDes().getEstudianteBean().getIdEstudiante() != null
                    && getCuentasPorCobrarDes().getEstudianteBean().getIdEstudiante().equals("")) {
                getCuentasPorCobrarDes().getEstudianteBean().setIdEstudiante(getCuentasPorCobrarDes().getEstudianteBean().getIdEstudiante());
                res = 1;
            }
            if (getCuentasPorCobrarDes().getEstudianteBean().getPersonaBean().getApepat() != null
                    && getCuentasPorCobrarDes().getEstudianteBean().getPersonaBean().getApepat().equals("")) {
                getCuentasPorCobrarDes().getEstudianteBean().getPersonaBean().setApepat(getCuentasPorCobrarDes().getEstudianteBean().getPersonaBean().getApepat());
                res = 1;
            }
            if (getCuentasPorCobrarDes().getEstudianteBean().getPersonaBean().getApemat() != null
                    && getCuentasPorCobrarDes().getEstudianteBean().getPersonaBean().getApemat().equals("")) {
                getCuentasPorCobrarDes().getEstudianteBean().getPersonaBean().setApemat(getCuentasPorCobrarDes().getEstudianteBean().getPersonaBean().getApemat());
                res = 1;
            }
            if (getCuentasPorCobrarDes().getEstudianteBean().getPersonaBean().getNombre() != null
                    && getCuentasPorCobrarDes().getEstudianteBean().getPersonaBean().getNombre().equals("")) {
                getCuentasPorCobrarDes().getEstudianteBean().getPersonaBean().setNombre(getCuentasPorCobrarDes().getEstudianteBean().getPersonaBean().getNombre());
                res = 1;
            }
            if (getCuentasPorCobrarDes().getMes() != null) {
                getCuentasPorCobrarDes().setMes(getCuentasPorCobrarDes().getMes());
                res = 1;
            }
            if (getCuentasPorCobrarDes().getAnio() != null) {
                getCuentasPorCobrarDes().getAnio();
                res = 1;
            }
            if (getCuentasPorCobrarDes().getIdTipoStatusCtaCte().getIdCodigo() != null) {
                getCuentasPorCobrarDes().getIdTipoStatusCtaCte().setIdCodigo(getCuentasPorCobrarDes().getIdTipoStatusCtaCte().getIdCodigo());
                res = 1;
            }
            if (res == 1) {
                listaCuentasPorCobrarDes = procesoEnvioService.obtenerEnvioCuentaResDes(getCuentasPorCobrarDes());
                if (listaCuentasPorCobrarDes.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaCuentasPorCobrarDes = new ArrayList<>();
                }
            } else if (res == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaCuentasPorCobrarDes = new ArrayList<>();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarMovimientoCuenta() {
        try {
            cuentasDestinoBean = new CuentasPorCobrarBean();
            cuentasPorCobrarBean = new CuentasPorCobrarBean();
            listaCuentasConfirm = new ArrayList<>();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerFiltroMatriculados() {
        try {
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            if (matriculafiltroBean.getEstudianteBean().getPersonaBean().getIdPersona() != null && !matriculafiltroBean.getEstudianteBean().getPersonaBean().getIdPersona().equals("")) {
                matriculafiltroBean.getEstudianteBean().getPersonaBean().setIdPersona(matriculafiltroBean.getEstudianteBean().getPersonaBean().getIdPersona());
            }
            if (matriculafiltroBean.getEstudianteBean().getPersonaBean().getNombre() != null && !matriculafiltroBean.getEstudianteBean().getPersonaBean().getNombre().equals("")) {
                matriculafiltroBean.getEstudianteBean().getPersonaBean().setNombre(matriculafiltroBean.getEstudianteBean().getPersonaBean().getNombre());
            }
            if (matriculafiltroBean.getEstudianteBean().getPersonaBean().getApepat() != null && !matriculafiltroBean.getEstudianteBean().getPersonaBean().getApepat().equals("")) {
                matriculafiltroBean.getEstudianteBean().getPersonaBean().setApepat(matriculafiltroBean.getEstudianteBean().getPersonaBean().getApepat());
            }
            if (matriculafiltroBean.getEstudianteBean().getPersonaBean().getApemat() != null && !matriculafiltroBean.getEstudianteBean().getPersonaBean().getApemat().equals("")) {
                matriculafiltroBean.getEstudianteBean().getPersonaBean().setApemat(matriculafiltroBean.getEstudianteBean().getPersonaBean().getApemat());
            }
            listaMatriculaFiltroBean = matriculaService.obtenerFiltroMatriculados(matriculafiltroBean);
            if (listaMatriculaFiltroBean.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, MensajesBackEnd.getValueOfKey("mensajeAlert", null), MensajesBackEnd.getValueOfKey("mensajeConsPer", null)));
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerRecuperacionCta(Object object, Integer op) {
        try {
            /*
             1 => origen
             2 => destino
             */
            CuentasPorCobrarBean cta = (CuentasPorCobrarBean) object;
            CuentasPorCobrarBean cuentas = new CuentasPorCobrarBean();
            cuentas = (CuentasPorCobrarBean) object;
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            if (op.equals(1)) {
                cuentasPorCobrarBean = procesoEnvioService.obtenerCtaEstudiante(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cuentas.getEstudianteBean().getIdEstudiante(), cuentas.getAnio(), cuentas.getMes());
                cuentaOrigen = cuentasPorCobrarBean;
            } else {
                if (op.equals(2)) {
                    cuentasDestinoBean = procesoEnvioService.obtenerCtaEstudiante(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cuentas.getEstudianteBean().getIdEstudiante(), cuentas.getAnio(), cuentas.getMes());
                    cuentaDestino = cuentasDestinoBean;
                    renderChange = false;
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void nuevoFiltroRecuperacion() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
//            procesoRecuperacionFiltroBean = new ProcesoRecuperacionBean();
//            getProcesoRecuperacionFiltroBean().getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            cuentasPorCobrar = new CuentasPorCobrarBean();
            getCuentasPorCobrar().getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarFiltroMatriculados() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaMatriculaFiltroBean = new ArrayList<>();
            listaCuentasDestinoBean = new ArrayList<>();
            matriculafiltroBean = new MatriculaBean();
            getMatriculafiltroBean().getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarFiltroCtaDes() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            cuentasPorCobrarDes = new CuentasPorCobrarBean();
            cuentasPorCobrarDes.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaCuentasPorCobrarDes = new ArrayList<>();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarFiltroEst() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            cuentasPorCobrarDes = new CuentasPorCobrarBean();
            cuentasPorCobrarDes.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaCuentasPorCobrarDes = new ArrayList<>();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerRecuperacion(Object object) {
        try {
            ProcesoRecuperacionBean recup = (ProcesoRecuperacionBean) object;
            procesoRecuperacionBean = new ProcesoRecuperacionBean();
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaCuentasPorCobrarBean = procesoEnvioService.obtenerCuentaEstudiante(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), recup.getEstudianteBean().getIdEstudiante());
            ProcesoRecuperacionService procesoRecuperacionService = BeanFactory.getProcesoRecuperacionService();
            procesoRecuperacionBean = procesoRecuperacionService.obtenerRecupPorId(recup.getIdProcesoRecup());
            procesoRecuperacionBean.getEstudianteBean().getPersonaBean().setNombreCompleto(recup.getEstudianteBean().getPersonaBean().getNombreCompleto());
            System.out.println(">>>>" + recup.getEstudianteBean().getPersonaBean().getNombreCompleto());
            System.out.println(">>>>" + procesoRecuperacionBean.getEstudianteBean().getPersonaBean().getNombreCompleto());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerCuentaOrigen(Object object) {
        try {
            CuentasPorCobrarBean cuentaOrigen = (CuentasPorCobrarBean) object;
            getCuentasPorCobrarBean().setIdCtasXCobrar(cuentaOrigen.getIdCtasXCobrar());
            getCuentasPorCobrarBean().getEstudianteBean().getPersonaBean().setIdPersona(cuentaOrigen.getEstudianteBean().getPersonaBean().getIdPersona());
            getCuentasPorCobrarBean().getEstudianteBean().getPersonaBean().setNombre(cuentaOrigen.getEstudianteBean().getPersonaBean().getNombre());
            getCuentasPorCobrarBean().getEstudianteBean().getPersonaBean().setApepat(cuentaOrigen.getEstudianteBean().getPersonaBean().getApepat());
            getCuentasPorCobrarBean().getEstudianteBean().getPersonaBean().setApemat(cuentaOrigen.getEstudianteBean().getPersonaBean().getApemat());
            getCuentasPorCobrarBean().getConceptoBean().setNombre(cuentaOrigen.getConceptoBean().getNombre());
            getCuentasPorCobrarBean().setMontoPagado(cuentaOrigen.getMontoPagado());
            getCuentasPorCobrarBean().setMeses(cuentaOrigen.getMeses());
            renderDestino = true;
            renderOrigen = true;
//            renderChange = false;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerCuentafinal(Object object) {
        try {
            CuentasPorCobrarBean cuentaDestino = (CuentasPorCobrarBean) object;
            getCuentasDestinoBean().setIdCtasXCobrar(cuentaDestino.getIdCtasXCobrar());
            getCuentasDestinoBean().getEstudianteBean().getPersonaBean().setIdPersona(cuentaDestino.getEstudianteBean().getPersonaBean().getIdPersona());
            getCuentasDestinoBean().getEstudianteBean().getPersonaBean().setNombre(cuentaDestino.getEstudianteBean().getPersonaBean().getNombre());
            getCuentasDestinoBean().getEstudianteBean().getPersonaBean().setApepat(cuentaDestino.getEstudianteBean().getPersonaBean().getApepat());
            getCuentasDestinoBean().getEstudianteBean().getPersonaBean().setApemat(cuentaDestino.getEstudianteBean().getPersonaBean().getApemat());
            getCuentasDestinoBean().getConceptoBean().setNombre(cuentaDestino.getConceptoBean().getNombre());
            getCuentasDestinoBean().setMontoPagado(cuentaDestino.getMontoPagado());
            getCuentasDestinoBean().setMeses(cuentaDestino.getMeses());
            renderChange = false;
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerCuentaDestino(SelectEvent event) {
        try {
            MatriculaBean matricula = (MatriculaBean) event.getObject();
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaCuentasDestinoBean = procesoEnvioService.obtenerCuentaEstudiante(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), matricula.getEstudianteBean().getPersonaBean().getIdPersona());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void confirmarCambio() {
        try {
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            listaCuentasConfirm = procesoEnvioService.obtenerResTrans(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getCuentasPorCobrarBean().getIdCtasXCobrar(), getCuentasDestinoBean().getIdCtasXCobrar());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void modificarCambio() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            //DESTINO
            System.out.println("cta destino: "+ cuentaDestino.getIdCtasXCobrar());
            System.out.println("cta origen: "+ cuentaOrigen.getIdCtasXCobrar());
            if(cuentaDestino.getIdCtasXCobrar()!=null && cuentaOrigen.getIdCtasXCobrar()!=null){
            cuentaDestino.setIdCtasXCobrar(cuentaDestino.getIdCtasXCobrar());
            cuentaDestino.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            cuentaDestino.getIdTipoStatusCtaCte().setIdCodigo(MaristaConstantes.COD_STA_CTA_EST_PAG);
            cuentaDestino.setMontoPagado(cuentaOrigen.getMontoPagado());
            cuentaDestino.setFechaPago(cuentaOrigen.getFechaPago());
            cuentaDestino.setModiPor(beanUsuarioSesion.getUsuario());
            cuentaDestino.setMora(cuentaOrigen.getMora());
            procesoEnvioService.modificarCuentaCambio(cuentaDestino);

            //ORIGEN
            cuentaOrigen.setIdCtasXCobrar(cuentaOrigen.getIdCtasXCobrar());
            cuentaOrigen.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            cuentaOrigen.getIdTipoStatusCtaCte().setIdCodigo(MaristaConstantes.COD_STA_CTA_EST_PEN);
            cuentaOrigen.setMontoPagado(null);
            cuentaOrigen.setFechaPago(null);
            cuentaOrigen.setModiPor(beanUsuarioSesion.getUsuario());
            cuentaOrigen.setMora(cuentaDestino.getMora());
            procesoEnvioService.modificarCuentaCambio(cuentaOrigen);
            
            //DocIngreso Destino
            DocIngresoBean docIngreso = new DocIngresoBean();
            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
            docIngreso.getCuentasPorCobrarBean().setIdCtasXCobrar(cuentaDestino.getIdCtasXCobrar());
            docIngreso.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            docIngreso.getIdTipoLugarPago().setIdCodigo(MaristaConstantes.COD_LUGAR_BANCO);
            docIngreso.getIdTipoModoPago().setIdCodigo(MaristaConstantes.COD_MODO_PAGO_BANCO);
            docIngreso.setFechaPago(cuentaOrigen.getFechaPago());
            docIngreso.setMontoEfectivoSol(cuentaOrigen.getMontoPagado().doubleValue());
            docIngreso.getTipoStatusDocIng().setIdCodigo(MaristaConstantes.COD_PAGADO);
            docIngreso.setModiPor(beanUsuarioSesion.getUsuario());
            docIngresoService.modificarDocIngresoRetificacion(docIngreso);
            //Origen
            docIngreso.getCuentasPorCobrarBean().setIdCtasXCobrar(cuentaOrigen.getIdCtasXCobrar());
            docIngreso.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            docIngreso.getIdTipoLugarPago().setIdCodigo(null);
            docIngreso.getIdTipoModoPago().setIdCodigo(null);
            docIngreso.setFechaPago(cuentaDestino.getFechaPago());
            docIngreso.setMontoEfectivoSol(cuentaDestino.getMontoPagado().doubleValue());
            docIngreso.getTipoStatusDocIng().setIdCodigo(MaristaConstantes.COD_PENDIENTE);
            docIngreso.setModiPor(beanUsuarioSesion.getUsuario());
            docIngresoService.modificarDocIngresoRetificacion(docIngreso);

            //DetDocIngreso Destino
            DetDocIngresoBean detDocIngresoBean = new DetDocIngresoBean();
            detDocIngresoBean.getCuentasPorCobrarBean().setIdCtasXCobrar(cuentaDestino.getIdCtasXCobrar());
            detDocIngresoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            detDocIngresoBean.setMontoPagado(cuentaDestino.getMontoPagado());
            detDocIngresoBean.setMontoSoles(cuentaDestino.getMontoPagado());
            detDocIngresoBean.setModiPor(beanUsuarioSesion.getUsuario());
            detDocIngresoBean.setMora(cuentaDestino.getMora());
            docIngresoService.modificarDetDocIngresoRetificacion(detDocIngresoBean);
            //Origen 
            
            detDocIngresoBean.getCuentasPorCobrarBean().setIdCtasXCobrar(cuentaOrigen.getIdCtasXCobrar());
            detDocIngresoBean.getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            detDocIngresoBean.setMontoPagado(getCuentasPorCobrarBean().getMontoPagado());
            detDocIngresoBean.setMontoSoles(getCuentasPorCobrarBean().getMontoPagado());
            detDocIngresoBean.setModiPor(beanUsuarioSesion.getUsuario());
            detDocIngresoBean.setMora(cuentaOrigen.getMora());
            docIngresoService.modificarDetDocIngresoRetificacion(detDocIngresoBean);

            listaCuentasConfirm = new ArrayList<>();
            cuentasDestinoBean = new CuentasPorCobrarBean();
            cuentasPorCobrarBean = new CuentasPorCobrarBean();
            nuevoFiltroRecuperacion();
            limpiarMovimientoCuenta();
            limpiarFiltroEst();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            limpiarCambio();}
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cambiarEstadoCuenta() {
        try {
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProcesoEnvioService procesoEnvioService = BeanFactory.getProcesoEnvioService();
            CuentasPorCobrarBean cuentas = new CuentasPorCobrarBean();
            cuentas.setIdCtasXCobrar(getCuentasPorCobrarBean().getIdCtasXCobrar());
            cuentas.setMontoPagado(getCuentasPorCobrarBean().getMontoPagado());
            cuentas.setMora(getCuentasPorCobrarBean().getMora());
            cuentas.setFechaPago(null);
            cuentas.getIdTipoStatusCtaCte().setIdCodigo(MaristaConstantes.COD_STA_CTA_EST_PEN);
            cuentas.setModiPor(beanUsuarioSesion.getPersonalBean().getUsuario());
            procesoEnvioService.modificarCuentaCambio(cuentas);

            cuentas.setIdCtasXCobrar(getCuentasDestinoBean().getIdCtasXCobrar());
            cuentas.setMontoPagado(getCuentasDestinoBean().getMontoPagado());
            cuentas.setMora(getCuentasDestinoBean().getMora());
            cuentas.setFechaPago(getCuentasPorCobrarBean().getFechaPago());
            cuentas.setModiPor(beanUsuarioSesion.getPersonalBean().getUsuario());
            cuentas.getIdTipoStatusCtaCte().setIdCodigo(MaristaConstantes.COD_STA_CTA_EST_PAG);
            procesoEnvioService.modificarCuentaCambio(cuentas);

            //DocIngreso
//            DocIngresoBean docIngreso = new DocIngresoBean();
//            DocIngresoService docIngresoService = BeanFactory.getDocIngresoService();
//            docIngreso.getIdTipoLugarPago().setIdCodigo(null);
//            docIngreso.getIdTipoModoPago().setIdCodigo(null);
//            docIngreso.setFechaPago(null);
//            docIngreso.setMontoEfectivoSol(getCuentasPorCobrarBean().getMontoPagado().doubleValue());
//            docIngreso.getTipoStatusDocIng().setIdCodigo(MaristaConstantes.COD_PENDIENTE);
//            docIngreso.setModiPor(beanUsuarioSesion.getPersonalBean().getUsuario());
//            docIngresoService.modificarDocIngresoRetificacion(docIngreso);
//
//            docIngreso.getIdTipoLugarPago().setIdCodigo(MaristaConstantes.COD_LUGAR_BANCO);
//            docIngreso.getIdTipoModoPago().setIdCodigo(MaristaConstantes.COD_MODO_PAGO_BANCO);
//            docIngreso.setFechaPago(null);
//            docIngreso.setMontoEfectivoSol(getCuentasDestinoBean().getMontoPagado().doubleValue());
//            docIngreso.getTipoStatusDocIng().setIdCodigo(MaristaConstantes.COD_PAGADO);
//            docIngreso.setModiPor(beanUsuarioSesion.getPersonalBean().getUsuario());
//            docIngresoService.modificarDocIngresoRetificacion(docIngreso);
//
//            //DetDocIngreso
//            DetDocIngresoBean detDocIngresoBean = new DetDocIngresoBean();
//            detDocIngresoBean.setMontoPagado(getCuentasPorCobrarBean().getMontoPagado());
//            detDocIngresoBean.setMontoSoles(getCuentasPorCobrarBean().getMontoPagado());
//            detDocIngresoBean.setModiPor(beanUsuarioSesion.getPersonalBean().getUsuario());
//            docIngresoService.modificarDetDocIngresoRetificacion(docIngreso);
//
//            detDocIngresoBean.setMontoPagado(getCuentasDestinoBean().getMontoPagado());
//            detDocIngresoBean.setMontoSoles(getCuentasDestinoBean().getMontoPagado());
//            detDocIngresoBean.setModiPor(beanUsuarioSesion.getPersonalBean().getUsuario());
//            docIngresoService.modificarDetDocIngresoRetificacion(docIngreso);

            listaCuentasConfirm = new ArrayList<>();
            cuentasDestinoBean = new CuentasPorCobrarBean();
            cuentasPorCobrarBean = new CuentasPorCobrarBean();
            nuevoFiltroRecuperacion();
            limpiarMovimientoCuenta();
            limpiarFiltroEst();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            limpiarCambio();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarCambio() {
        try {
            cuentasDestinoBean = new CuentasPorCobrarBean();
            cuentasPorCobrarBean = new CuentasPorCobrarBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargarAnio() {
        try {
            Calendar miCalendario = Calendar.getInstance();
            int inicio = MaristaConstantes.ANO_INI_DEFAULT_COLE;
            int fin = miCalendario.get(Calendar.YEAR) + 5;
            listaAnios = new ArrayList<>();
            for (Integer i = inicio; i <= fin; i++) {
                listaAnios.add(i);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public CuentasPorCobrarBean getCuentaResultadoBean() {
        if (cuentaResultadoBean == null) {
            cuentaResultadoBean = new CuentasPorCobrarBean();
        }
        return cuentaResultadoBean;
    }

    public void setCuentaResultadoBean(CuentasPorCobrarBean cuentaResultadoBean) {
        this.cuentaResultadoBean = cuentaResultadoBean;
    }

    public EstudianteBean getEstudianteBean() {
        if (estudianteBean == null) {
            estudianteBean = new EstudianteBean();
        }
        return estudianteBean;
    }

    public void setEstudianteBean(EstudianteBean estudianteBean) {
        this.estudianteBean = estudianteBean;
    }

    public CuentasPorCobrarBean getCuentasPorCobrar() {
        if (cuentasPorCobrar == null) {
            cuentasPorCobrar = new CuentasPorCobrarBean();
        }
        return cuentasPorCobrar;
    }

    public void setCuentasPorCobrar(CuentasPorCobrarBean cuentasPorCobrar) {
        this.cuentasPorCobrar = cuentasPorCobrar;
    }

    public List<CuentasPorCobrarBean> getListaCuentasPorCobrar() {
        return listaCuentasPorCobrar;
    }

    public void setListaCuentasPorCobrar(List<CuentasPorCobrarBean> listaCuentasPorCobrar) {
        this.listaCuentasPorCobrar = listaCuentasPorCobrar;
    }

    public List<Integer> getListaAnios() {
        if (listaAnios == null) {
            listaAnios = new ArrayList<>();
        }
        return listaAnios;
    }

    public void setListaAnios(List<Integer> listaAnios) {
        this.listaAnios = listaAnios;
    }

    public CuentasPorCobrarBean getCuentasPorCobrarDes() {
        if (cuentasPorCobrarDes == null) {
            cuentasPorCobrarDes = new CuentasPorCobrarBean();
        }
        return cuentasPorCobrarDes;
    }

    public void setCuentasPorCobrarDes(CuentasPorCobrarBean cuentasPorCobrarDes) {
        this.cuentasPorCobrarDes = cuentasPorCobrarDes;
    }

    public List<CuentasPorCobrarBean> getListaCuentasPorCobrarDes() {
        if (listaCuentasPorCobrarDes == null) {
            listaCuentasPorCobrarDes = new ArrayList<>();
        }
        return listaCuentasPorCobrarDes;
    }

    public void setListaCuentasPorCobrarDes(List<CuentasPorCobrarBean> listaCuentasPorCobrarDes) {
        this.listaCuentasPorCobrarDes = listaCuentasPorCobrarDes;
    }

    public List<CuentasPorCobrarBean> getListaCuentasConfirm() {
        if (listaCuentasConfirm == null) {
            listaCuentasConfirm = new ArrayList<>();
        }
        return listaCuentasConfirm;
    }

    public void setListaCuentasConfirm(List<CuentasPorCobrarBean> listaCuentasConfirm) {
        this.listaCuentasConfirm = listaCuentasConfirm;
    }

    public Integer getOpcion() {
        return opcion;
    }

    public void setOpcion(Integer opcion) {
        this.opcion = opcion;
    }

    public List<CronogramaPagoBean> getListaCronograma() {
        if (listaCronograma == null) {
            listaCronograma = new ArrayList<>();
        }
        return listaCronograma;
    }

    public void setListaCronograma(List<CronogramaPagoBean> listaCronograma) {
        this.listaCronograma = listaCronograma;
    }

    public List<CodigoBean> getListaTipoEstadoCuenta() {
        if (listaTipoEstadoCuenta == null) {
            listaTipoEstadoCuenta = new ArrayList<>();
        }
        return listaTipoEstadoCuenta;
    }

    public void setListaTipoEstadoCuenta(List<CodigoBean> listaTipoEstadoCuenta) {
        this.listaTipoEstadoCuenta = listaTipoEstadoCuenta;
    }

    public CuentasPorCobrarBean getCuentaOrigen() {
        if (cuentaOrigen == null) {
            cuentaOrigen = new CuentasPorCobrarBean();
        }
        return cuentaOrigen;
    }

    public void setCuentaOrigen(CuentasPorCobrarBean cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public CuentasPorCobrarBean getCuentaDestino() {
        if (cuentaDestino == null) {
            cuentaDestino = new CuentasPorCobrarBean();
        }
        return cuentaDestino;
    }

    public void setCuentaDestino(CuentasPorCobrarBean cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

}

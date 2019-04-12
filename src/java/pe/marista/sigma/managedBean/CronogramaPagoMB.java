/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CronogramaPagoBean;
import pe.marista.sigma.bean.TipoConceptoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CronogramaPagoService;
import pe.marista.sigma.service.TipoConceptoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author MS002
 */
public class CronogramaPagoMB implements Serializable {

    /**
     * Creates a new instance of CronogramaPagoMB
     */
    @PostConstruct
    public void CronogramaPagoMB() {
        try {
            //Usuario en Sesion
            beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            obtenerCRonogramnaPago();
            cargarAnio();
            cargarMeses();
            cargaMes();
            //Lista de Tipos de Concepto
            TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
            listaTipoConceptoBean = tipoConceptoService.obtenerPorTipoCronograma(MaristaConstantes.ID_TIPO_CONCEPTOUNO, MaristaConstantes.ID_TIPO_CONCEPTODOS);
            getListaTipoConceptoBean();
            listaMesesForSup();
            cronogramaPagoBean = new CronogramaPagoBean();
            Calendar miCalendario = Calendar.getInstance();
            cronogramaPagoBean.setAnio(miCalendario.get(Calendar.YEAR));

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    private UsuarioBean beanUsuarioSesion;
    private List<CronogramaPagoBean> listaCronogramaPagoBean;
    private CronogramaPagoBean cronogramaPagoBean;
    private List<TipoConceptoBean> listaTipoConceptoBean;

    private List<Integer> listaAnios;
    private List<Integer> listaAniosRender;
    private Map<String, Integer> listaMesesExpMap;
    private Map<Integer, String> listaMeses;
    private Integer idTipoConcepto, mes = 0, anio = 0;

    public void obtenerCRonogramnaPago() {
        try {
            CronogramaPagoService cronogramaPagoService = BeanFactory.getCronogramaPagoService();
            listaCronogramaPagoBean = cronogramaPagoService.obtenerCronogramaPago(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), null);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public final void cargarAnio() {
        try {
            int a = 2040;
            int b = 2014;
            listaAnios = new ArrayList<>();
            for (int i = b; i <= a; i++) {
                listaAnios.add(i);
            }
            listaAniosRender = new ArrayList<>();
            for (int i = b; i <= a; i++) {
                listaAniosRender.add(i);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargarMeses() {
        try {
            listaMesesExpMap = new LinkedHashMap<>();
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaEnero", null), 1);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaFebrero", null), 2);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaMarzo", null), 3);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaAbril", null), 4);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaMayo", null), 5);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaJunio", null), 6);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaJulio", null), 7);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaAgosto", null), 8);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaSetiembre", null), 9);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaOctubre", null), 10);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaNoviembre", null), 11);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaDiciembre", null), 12);
            listaMesesExpMap = Collections.unmodifiableMap(listaMesesExpMap);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargaMes() {
        try {
            listaMeses = new LinkedHashMap<>();
            listaMeses.put(1, MensajesBackEnd.getValueOfKey("etiquetaEnero", null));
            listaMeses.put(2, MensajesBackEnd.getValueOfKey("etiquetaFebrero", null));
            listaMeses.put(3, MensajesBackEnd.getValueOfKey("etiquetaMarzo", null));
            listaMeses.put(4, MensajesBackEnd.getValueOfKey("etiquetaAbril", null));
            listaMeses.put(5, MensajesBackEnd.getValueOfKey("etiquetaMayo", null));
            listaMeses.put(6, MensajesBackEnd.getValueOfKey("etiquetaJunio", null));
            listaMeses.put(7, MensajesBackEnd.getValueOfKey("etiquetaJulio", null));
            listaMeses.put(8, MensajesBackEnd.getValueOfKey("etiquetaAgosto", null));
            listaMeses.put(9, MensajesBackEnd.getValueOfKey("etiquetaSetiembre", null));
            listaMeses.put(10, MensajesBackEnd.getValueOfKey("etiquetaOctubre", null));
            listaMeses.put(11, MensajesBackEnd.getValueOfKey("etiquetaNoviembre", null));
            listaMeses.put(12, MensajesBackEnd.getValueOfKey("etiquetaDiciembre", null));
            listaMeses = Collections.unmodifiableMap(listaMeses);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void insertarCronograma() {
        try {
            CronogramaPagoService cronogramaPago = BeanFactory.getCronogramaPagoService();
            cronogramaPagoBean.setTasaInteres(cronogramaPagoBean.getTasaInteres());
            cronogramaPagoBean.setFechaVenc(cronogramaPagoBean.getFechaVenc());
            cronogramaPagoBean.setMes(cronogramaPagoBean.getMes());
            cronogramaPagoBean.setAnio(cronogramaPagoBean.getAnio());
            cronogramaPagoBean.setIdTipoCodigo(cronogramaPagoBean.getIdTipoCodigo());
            cronogramaPagoBean.setCreaPor(beanUsuarioSesion.getUsuario());
            cronogramaPagoBean.setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            cronogramaPago.insertarCronogramaPago(cronogramaPagoBean);
            limpiarCronograma();
            obtenerCRonogramnaPago();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void actualizarCronograma() {
        try {
            CronogramaPagoService cronogramaPago = BeanFactory.getCronogramaPagoService();
            CronogramaPagoBean crono = new CronogramaPagoBean();
            crono.setTasaInteres(cronogramaPagoBean.getTasaInteres());
            crono.setFechaVenc(cronogramaPagoBean.getFechaVenc());
            crono.setMes(cronogramaPagoBean.getMes());
            crono.setAnio(cronogramaPagoBean.getAnio());
            crono.setIdTipoCodigo(cronogramaPagoBean.getIdTipoCodigo());
            crono.setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            crono.setModiPor(beanUsuarioSesion.getUsuario());
            crono.setIdCronogramaPago(cronogramaPagoBean.getIdCronogramaPago());
            cronogramaPago.actualizarCronogramaPago(crono);
            limpiarCronograma();
            obtenerCRonogramnaPago();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void registrarCronograma() {
        try {
            if (cronogramaPagoBean.getIdCronogramaPago() != null) {
                actualizarCronograma();
            } else {
                if (cronogramaPagoBean.getIdCronogramaPago() == null) {
                    insertarCronograma();
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarCronograma() {
        try {
            cronogramaPagoBean = new CronogramaPagoBean();
            Calendar miCalendario = Calendar.getInstance();
            cronogramaPagoBean.setAnio(miCalendario.get(Calendar.YEAR));
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerTipo() {
        try {
            if (cronogramaPagoBean.getMes().equals(2)) {
                idTipoConcepto = 100;
                cronogramaPagoBean.getTipoConceptoBean().setIdTipoConcepto(idTipoConcepto);
            } else {
                if (!cronogramaPagoBean.getMes().equals(2)) {
                    idTipoConcepto = 101;
                    cronogramaPagoBean.getTipoConceptoBean().setIdTipoConcepto(idTipoConcepto);
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerFecha() {
        try {
            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
            if (cronogramaPagoBean.getFechaVenc().getMonth() == 1
                    || cronogramaPagoBean.getFechaVenc().getMonth() == 2) {
                idTipoConcepto = 100;
                cronogramaPagoBean.setFecha(date.format(cronogramaPagoBean.getFechaVenc()));
                cronogramaPagoBean.setAnio(cronogramaPagoBean.getFechaVenc().getYear());
                cronogramaPagoBean.setMes(cronogramaPagoBean.getFechaVenc().getMonth() + 1);
//                cronogramaPagoBean.getTipoConceptoBean().setIdTipoConcepto(idTipoConcepto);
            } else {
                idTipoConcepto = 101;
                cronogramaPagoBean.setFecha(date.format(cronogramaPagoBean.getFechaVenc()));
                cronogramaPagoBean.setAnio(cronogramaPagoBean.getFechaVenc().getYear());
                cronogramaPagoBean.setMes(cronogramaPagoBean.getFechaVenc().getMonth() + 1);
//                cronogramaPagoBean.setIdTipoCodigo(idTipoConcepto);
            }
            CronogramaPagoBean crono = new CronogramaPagoBean();
            crono = validarCronograma();
            if (crono != null) {
                RequestContext.getCurrentInstance().addCallbackParam("validacionErronea", true);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerTipoCon() {
        try {
            System.out.println(cronogramaPagoBean.getTipoConceptoBean().getIdTipoConcepto());
            cronogramaPagoBean.setIdTipoCodigo(cronogramaPagoBean.getTipoConceptoBean().getIdTipoConcepto());
            if (cronogramaPagoBean.getTipoConceptoBean().getIdTipoConcepto().equals(100)) {
                cronogramaPagoBean.setMes(2);
            } else {
                cronogramaPagoBean.setMes(null);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public CronogramaPagoBean validarCronograma() {
        CronogramaPagoBean cronograma = new CronogramaPagoBean();
        try {
            SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
            CronogramaPagoService cronogramaPagoService = BeanFactory.getCronogramaPagoService();
            cronograma = cronogramaPagoService.validarCronograma(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), null, cronogramaPagoBean.getMes(), date.format(cronogramaPagoBean.getFechaVenc()));
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return cronograma;
    }

    public void obtenerIdCronograma(Object object) {
        try {
            cronogramaPagoBean = (CronogramaPagoBean) object;
            CronogramaPagoService cronogramaPagoService = BeanFactory.getCronogramaPagoService();
            cronogramaPagoBean = cronogramaPagoService.obtenerIdCronograma(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cronogramaPagoBean.getIdCronogramaPago());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void listaMesesForSup() {
        try {
            listaMesesExpMap = new LinkedHashMap<>();
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaMatricula", null), 2);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaMarzo", null), 3);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaAbril", null), 4);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaMayo", null), 5);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaJunio", null), 6);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaJulio", null), 7);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaAgosto", null), 8);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaSetiembre", null), 9);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaOctubre", null), 10);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaNoviembre", null), 11);
            listaMesesExpMap.put(MensajesBackEnd.getValueOfKey("etiquetaDiciembre", null), 12);
            listaMesesExpMap = Collections.unmodifiableMap(listaMesesExpMap);
        } catch (Exception ettt) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ettt);
        }
    }

    public List<CronogramaPagoBean> getListaCronogramaPagoBean() {
        if (listaCronogramaPagoBean == null) {
            listaCronogramaPagoBean = new ArrayList<>();
        }
        return listaCronogramaPagoBean;
    }

    public void setListaCronogramaPagoBean(List<CronogramaPagoBean> listaCronogramaPagoBean) {
        this.listaCronogramaPagoBean = listaCronogramaPagoBean;
    }

    public CronogramaPagoBean getCronogramaPagoBean() {
        if (cronogramaPagoBean == null) {
            cronogramaPagoBean = new CronogramaPagoBean();
        }
        return cronogramaPagoBean;
    }

    public void setCronogramaPagoBean(CronogramaPagoBean cronogramaPagoBean) {
        this.cronogramaPagoBean = cronogramaPagoBean;
    }

    public UsuarioBean getBeanUsuarioSesion() {
        if (beanUsuarioSesion == null) {
            beanUsuarioSesion = new UsuarioBean();
        }
        return beanUsuarioSesion;
    }

    public void setBeanUsuarioSesion(UsuarioBean beanUsuarioSesion) {
        this.beanUsuarioSesion = beanUsuarioSesion;
    }

    public List<TipoConceptoBean> getListaTipoConceptoBean() {
        if (listaTipoConceptoBean == null) {
            listaTipoConceptoBean = new ArrayList<>();
        }
        return listaTipoConceptoBean;
    }

    public void setListaTipoConceptoBean(List<TipoConceptoBean> listaTipoConceptoBean) {
        this.listaTipoConceptoBean = listaTipoConceptoBean;
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

    public List<Integer> getListaAniosRender() {
        if (listaAniosRender == null) {
            listaAniosRender = new ArrayList<>();
        }
        return listaAniosRender;
    }

    public void setListaAniosRender(List<Integer> listaAniosRender) {
        this.listaAniosRender = listaAniosRender;
    }

    public Map<String, Integer> getListaMesesExpMap() {
        return listaMesesExpMap;
    }

    public void setListaMesesExpMap(Map<String, Integer> listaMesesExpMap) {
        this.listaMesesExpMap = listaMesesExpMap;
    }

    public Map<Integer, String> getListaMeses() {
        return listaMeses;
    }

    public void setListaMeses(Map<Integer, String> listaMeses) {
        this.listaMeses = listaMeses;
    }

    public Integer getIdTipoConcepto() {
        return idTipoConcepto;
    }

    public void setIdTipoConcepto(Integer idTipoConcepto) {
        this.idTipoConcepto = idTipoConcepto;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

}

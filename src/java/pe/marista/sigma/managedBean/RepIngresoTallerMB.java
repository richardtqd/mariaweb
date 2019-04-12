/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.primefaces.context.RequestContext;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.CobranzaValoradoRepBean;
import pe.marista.sigma.bean.reporte.DocIngresoTallerRepBean;
import pe.marista.sigma.bean.reporte.PagoBancoRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.PagoBancoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author JC
 */
public class RepIngresoTallerMB {

    @PostConstruct
    public void RepIngresoTallerMB() {
        try {
            usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getTipoFormato();
            setTipoFormato(1);
            getEstadoImpresion();
            setEstadoImpresion(Boolean.FALSE); // 0 => NO IMPRESO / 1 => IMPRESO
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    private UsuarioBean usuarioBean;
    private DocIngresoTallerRepBean docIngresoTallerRepBean;
    private DocIngresoTallerRepBean docIngresoTaller;
    private List<DocIngresoTallerRepBean> listaDocIngresoTallerRepBean;
    private List<CodigoBean> listaTipoModoPago;
    private List<CodigoBean> listaTipoLugarPago;
    private List<CodigoBean> listaTipoMoneda;
    private List<CodigoBean> listaTipoEstado;
    private List<CodigoBean> listaTipoDoc;
    private Boolean valAdmTodos;
    private Integer tipoFormato;
    private Boolean estadoImpresion;

    //VARIABLES DE AYUDA
    private BigDecimal totSoles;

    public void cargarDatos() {
        try {
            getDocIngresoTallerRepBean();
            getDocIngresoTallerRepBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            Calendar fechaActual = new GregorianCalendar();
            getDocIngresoTallerRepBean().setFechaIni(fechaActual.getTime());
            getDocIngresoTallerRepBean().setFechaFin(fechaActual.getTime());
            getDocIngresoTallerRepBean().setIdTipoDoc(MaristaConstantes.COD_DOC_RECCAJA);
            getDocIngresoTallerRepBean().setIdEstado(MaristaConstantes.COD_PAG_BANCO_PAG);
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoLugarPago = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_LUG_PAGO));
            listaTipoModoPago = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_MOD_PAGO));
            listaTipoMoneda = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_MON));
            listaTipoEstado = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_PAGO_BCO));
            listaTipoDoc = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_DOCUMENTO));
            Integer res = 0;
            Float pro = res.floatValue();
            totSoles = new BigDecimal(pro.floatValue());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void filtrarPagosTaller() {
        try {
            Integer res = 0;
            Float pro = res.floatValue();
            PagoBancoService pagoBancoService = BeanFactory.getPagoBancoService();
            if (getDocIngresoTallerRepBean().getIdEstado() != null) {
                getDocIngresoTallerRepBean().setIdEstado(getDocIngresoTallerRepBean().getIdEstado());
                res = 1;
            }
            if (getDocIngresoTallerRepBean().getIdTipoDoc() != null) {
                getDocIngresoTallerRepBean().setIdTipoDoc(getDocIngresoTallerRepBean().getIdTipoDoc());
                res = 1;
            }
            if (getDocIngresoTallerRepBean().getIdTipoLugar() != null) {
                getDocIngresoTallerRepBean().setIdTipoLugar(getDocIngresoTallerRepBean().getIdTipoLugar());
                res = 1;
            }
            if (getDocIngresoTallerRepBean().getIdTipoModo() != null) {
                getDocIngresoTallerRepBean().setIdTipoModo(getDocIngresoTallerRepBean().getIdTipoModo());
                res = 1;
            }
            if (getDocIngresoTallerRepBean().getIdTipoMoneda() != null) {
                getDocIngresoTallerRepBean().setIdTipoMoneda(getDocIngresoTallerRepBean().getIdTipoMoneda());
                res = 1;
            }
            if (getDocIngresoTallerRepBean().getIdDiscente() != null && !getDocIngresoTallerRepBean().getIdDiscente().equals("")) {
                getDocIngresoTallerRepBean().setIdDiscente(getDocIngresoTallerRepBean().getIdDiscente());
                res = 1;
            }
            if (getDocIngresoTallerRepBean().getDiscente() != null && !getDocIngresoTallerRepBean().getDiscente().equals("")) {
                getDocIngresoTallerRepBean().setDiscente(getDocIngresoTallerRepBean().getDiscente());
                res = 1;
            }
            if (getDocIngresoTallerRepBean().getReferencia() != null && !getDocIngresoTallerRepBean().getReferencia().equals("")) {
                getDocIngresoTallerRepBean().setReferencia(getDocIngresoTallerRepBean().getReferencia());
                res = 1;
            }
            if (getEstadoImpresion() != null) {
                getDocIngresoTallerRepBean().setFlgImpresion(getEstadoImpresion());
                res = 1;
            }
            if (res == 1) {
                listaDocIngresoTallerRepBean = pagoBancoService.obtenerConciliaPagoBanco(getDocIngresoTallerRepBean());
                if (listaDocIngresoTallerRepBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaDocIngresoTallerRepBean = new ArrayList<>();
                    totSoles = new BigDecimal(pro.floatValue());
                } else if (!listaDocIngresoTallerRepBean.isEmpty()) {
                    for (DocIngresoTallerRepBean ficha : listaDocIngresoTallerRepBean) {
                        pro = Float.parseFloat(ficha.getMontoPagado()) + pro;
                    }
                    totSoles = new BigDecimal(pro.floatValue());
                }
            } else if (res == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                totSoles = new BigDecimal(pro.floatValue());
                listaDocIngresoTallerRepBean = new ArrayList<>();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarPagoTaller() {
        try {
            docIngresoTallerRepBean = new DocIngresoTallerRepBean();
            cargarDatos();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void reImprimirReciboTaller(Object object) {
        ServletOutputStream out = null;
        try {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reportTalleresBco.jasper");

            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            DocIngresoTallerRepBean docIngresoTallerRepBean = (DocIngresoTallerRepBean) object;
            docIngresoTallerRepBean.setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            List<PagoBancoRepBean> listaPagoBancoRepBean = new ArrayList<>();
            PagoBancoService pagoBancoService = BeanFactory.getPagoBancoService();
            listaPagoBancoRepBean = pagoBancoService.reImprimirReciboTaller(docIngresoTallerRepBean);

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaPagoBancoRepBean);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void imprimirRecibosMasivos() {
        ServletOutputStream out = null;
        try {
            PagoBancoService pagoBancoService = BeanFactory.getPagoBancoService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = "";
            DocIngresoTallerRepBean taller = new DocIngresoTallerRepBean();
            List<Integer> lista = new ArrayList<>();
            List<PagoBancoRepBean> listaPagoBancoRepBean = new ArrayList<>();
            List<CobranzaValoradoRepBean> listaCobranzaValoradoRepBean = new ArrayList<>();
            if (getTipoFormato() != null) {
                if (getTipoFormato().equals(1)) {
                    //OBTENIENDO NUMERO DE PAGO BANCO
                    if (!listaDocIngresoTallerRepBean.isEmpty()) {
                        for (DocIngresoTallerRepBean doc : listaDocIngresoTallerRepBean) {
                            lista.add(doc.getIdPagoBanco());
                        }
                    }
                    archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reportTalleresBco.jasper");
                    taller.setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    listaPagoBancoRepBean = pagoBancoService.reImprimirReciboTallerMasivo(taller.getUniNeg(), lista);
                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    String absoluteWebPath = externalContext.getRealPath("/");
                    File file = new File(archivoJasper);
                    JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                    JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaPagoBancoRepBean);
                    Map<String, Object> parametros = new HashMap<>();
                    String ruta = absoluteWebPath + "reportes\\";
                    parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
                    parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
                    parametros.put("SUBREPORT_DIR", ruta);
                    byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
                    response.reset();
                    out = response.getOutputStream();
                    out.write(bytes);
                    out.flush();
                } else if (getTipoFormato().equals(2)) {
                    archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteCobranzaTallerWeb.jasper");
                    if (!listaDocIngresoTallerRepBean.isEmpty()) {
                        for (DocIngresoTallerRepBean doc : listaDocIngresoTallerRepBean) {
                            if (!doc.getFlgImpresion()) {
                                Integer nroDoc = pagoBancoService.obtenerMaxNroDoc(MaristaConstantes.serie_numdoc_3);
                                doc.setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                doc.setSerie(MaristaConstantes.serie_numdoc_3);
                                doc.setNroDoc(nroDoc + 1);
                                doc.setModiPor(usuarioBean.getUsuario());
                                doc.setFlgImpresion(Boolean.TRUE);
                                pagoBancoService.modificarNumRecibo(doc);
                            }
                            lista.add(doc.getIdPagoBanco());
                        }
                    }
                    taller.setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    listaCobranzaValoradoRepBean = pagoBancoService.reImprimirReciboTallerMasivoLibre(taller.getUniNeg(), lista);
                    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                    String absoluteWebPath = externalContext.getRealPath("/");
                    File file = new File(archivoJasper);
                    JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                    JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCobranzaValoradoRepBean);
                    Map<String, Object> parametros = new HashMap<>();
                    String ruta = absoluteWebPath + "reportes\\";
                    parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
                    parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
                    parametros.put("SUBREPORT_DIR", ruta);
                    byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
                    response.reset();
                    out = response.getOutputStream();
                    out.write(bytes);
                    out.flush();
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void cambiarValAdmTodos() {
        try {
            if (valAdmTodos != null) {
                if (valAdmTodos) {
                    if (!listaDocIngresoTallerRepBean.isEmpty()) {
                        for (DocIngresoTallerRepBean doc : listaDocIngresoTallerRepBean) {
                            doc.setFlgImpresion(Boolean.TRUE);
                        }
                    }
                } else if (!valAdmTodos) {
                    if (!listaDocIngresoTallerRepBean.isEmpty()) {
                        for (DocIngresoTallerRepBean doc : listaDocIngresoTallerRepBean) {
                            doc.setFlgImpresion(Boolean.FALSE);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorId(Object obj) {
        try {
            PagoBancoService pagoBancoService = BeanFactory.getPagoBancoService();
            docIngresoTaller = (DocIngresoTallerRepBean) obj;
            docIngresoTaller.setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            docIngresoTaller = pagoBancoService.obtenerPorIdPagoBanco(docIngresoTaller);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cambiarEsatdoPagoBanco() {
        try {
            PagoBancoService pagoBancoService = BeanFactory.getPagoBancoService();
            docIngresoTaller.setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            docIngresoTaller.setModiPor(usuarioBean.getUsuario());
            pagoBancoService.modificarPagoBanco(docIngresoTaller);
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFormato() {
        try {
            setTipoFormato(getTipoFormato());
            System.out.println(">>>" + getTipoFormato());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public UsuarioBean getUsuarioBean() {
        if (usuarioBean == null) {
            usuarioBean = new UsuarioBean();
        }
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public DocIngresoTallerRepBean getDocIngresoTallerRepBean() {
        if (docIngresoTallerRepBean == null) {
            docIngresoTallerRepBean = new DocIngresoTallerRepBean();
        }
        return docIngresoTallerRepBean;
    }

    public void setDocIngresoTallerRepBean(DocIngresoTallerRepBean docIngresoTallerRepBean) {
        this.docIngresoTallerRepBean = docIngresoTallerRepBean;
    }

    public List<CodigoBean> getListaTipoModoPago() {
        if (listaTipoModoPago == null) {
            listaTipoModoPago = new ArrayList<>();
        }
        return listaTipoModoPago;
    }

    public void setListaTipoModoPago(List<CodigoBean> listaTipoModoPago) {
        this.listaTipoModoPago = listaTipoModoPago;
    }

    public List<CodigoBean> getListaTipoLugarPago() {
        if (listaTipoLugarPago == null) {
            listaTipoLugarPago = new ArrayList<>();
        }
        return listaTipoLugarPago;
    }

    public void setListaTipoLugarPago(List<CodigoBean> listaTipoLugarPago) {
        this.listaTipoLugarPago = listaTipoLugarPago;
    }

    public List<CodigoBean> getListaTipoMoneda() {
        if (listaTipoMoneda == null) {
            listaTipoMoneda = new ArrayList<>();
        }
        return listaTipoMoneda;
    }

    public void setListaTipoMoneda(List<CodigoBean> listaTipoMoneda) {
        this.listaTipoMoneda = listaTipoMoneda;
    }

    public List<CodigoBean> getListaTipoEstado() {
        if (listaTipoEstado == null) {
            listaTipoEstado = new ArrayList<>();
        }
        return listaTipoEstado;
    }

    public void setListaTipoEstado(List<CodigoBean> listaTipoEstado) {
        this.listaTipoEstado = listaTipoEstado;
    }

    public List<CodigoBean> getListaTipoDoc() {
        if (listaTipoDoc == null) {
            listaTipoDoc = new ArrayList<>();
        }
        return listaTipoDoc;
    }

    public void setListaTipoDoc(List<CodigoBean> listaTipoDoc) {
        this.listaTipoDoc = listaTipoDoc;
    }

    public BigDecimal getTotSoles() {
        return totSoles;
    }

    public void setTotSoles(BigDecimal totSoles) {
        this.totSoles = totSoles;
    }

    public List<DocIngresoTallerRepBean> getListaDocIngresoTallerRepBean() {
        if (listaDocIngresoTallerRepBean == null) {
            listaDocIngresoTallerRepBean = new ArrayList<>();
        }
        return listaDocIngresoTallerRepBean;
    }

    public void setListaDocIngresoTallerRepBean(List<DocIngresoTallerRepBean> listaDocIngresoTallerRepBean) {
        this.listaDocIngresoTallerRepBean = listaDocIngresoTallerRepBean;
    }

    public DocIngresoTallerRepBean getDocIngresoTaller() {
        if (docIngresoTaller == null) {
            docIngresoTaller = new DocIngresoTallerRepBean();
        }
        return docIngresoTaller;
    }

    public void setDocIngresoTaller(DocIngresoTallerRepBean docIngresoTaller) {
        this.docIngresoTaller = docIngresoTaller;
    }

    public Boolean getValAdmTodos() {
        return valAdmTodos;
    }

    public void setValAdmTodos(Boolean valAdmTodos) {
        this.valAdmTodos = valAdmTodos;
    }

    public Integer getTipoFormato() {
        return tipoFormato;
    }

    public void setTipoFormato(Integer tipoFormato) {
        this.tipoFormato = tipoFormato;
    }

    public Boolean getEstadoImpresion() {
        return estadoImpresion;
    }

    public void setEstadoImpresion(Boolean estadoImpresion) {
        this.estadoImpresion = estadoImpresion;
    }

}

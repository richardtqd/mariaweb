package pe.marista.sigma.managedBean;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.FichaBean;
import pe.marista.sigma.bean.TipoPaganteBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.CobranzaValoradoRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.FichaService;
import pe.marista.sigma.service.TipoPaganteService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

public class RepDocIngresoEvento {

    @PostConstruct
    public void RepDocIngresoEvento() {
        try {
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            GregorianCalendar fechaActual = new GregorianCalendar();
            getFichaFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getFichaFiltroBean().setFechaIni(fechaActual.getTime());
            getFichaFiltroBean().setFechaFin(fechaActual.getTime());

            Integer pro = 0;
            totSoles = new BigDecimal(pro.floatValue());

            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaModoPago();
            listaModoPago = new ArrayList<>();
            listaModoPago = codigoService.obtenerCodigoDocIngreso();

            TipoPaganteBean tipoPaganteBean = new TipoPaganteBean();
            TipoPaganteService tipoPaganteService = BeanFactory.getTipoPaganteService();
            tipoPaganteBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaTipoPaganteBean = tipoPaganteService.obtenerTipoPagante(tipoPaganteBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //CLASES
    private FichaBean fichaFiltroBean;
    private UsuarioBean usuarioLoginBean;

    //LISTAS
    private List<FichaBean> listaFichaFamilia;
    private List<CodigoBean> listaModoPago;
    private List<TipoPaganteBean> listaTipoPaganteBean;

    //VARIABLES DE AYUDA
    private BigDecimal totSoles;

    public void filtrarFichaPagada() {
        try {
            Integer res = 0;
            Float pro = res.floatValue();
            FichaService fichaService = BeanFactory.getFichaService();
            if (fichaFiltroBean.getFechaIni() != null) {
                fichaFiltroBean.setFechaIni(fichaFiltroBean.getFechaIni());
                res = 1;
            }
            if (fichaFiltroBean.getFechaFin() != null) {
                fichaFiltroBean.setFechaFin(fichaFiltroBean.getFechaFin());
                res = 1;
            }
            if (fichaFiltroBean.getPaganteBean().getNomPagante() != null
                    && !fichaFiltroBean.getPaganteBean().getNomPagante().equals("")) {
                fichaFiltroBean.getPaganteBean().setNomPagante(fichaFiltroBean.getPaganteBean().getNomPagante());
                res = 1;
            }
            if (fichaFiltroBean.getPaganteBean().getNroDoc() != null
                    && !fichaFiltroBean.getPaganteBean().getNroDoc().equals("")) {
                fichaFiltroBean.getPaganteBean().setNroDoc(fichaFiltroBean.getPaganteBean().getNroDoc());
                res = 1;
            }
            if (fichaFiltroBean.getTipoModoPago().getIdCodigo() != null) {
                fichaFiltroBean.getTipoModoPago().setIdCodigo(fichaFiltroBean.getTipoModoPago().getIdCodigo());
                res = 1;
            }
            if (fichaFiltroBean.getPaganteBean().getTipoPaganteBean().getIdtipoPagante() != null) {
                fichaFiltroBean.getPaganteBean().getTipoPaganteBean().setIdtipoPagante(fichaFiltroBean.getPaganteBean().getTipoPaganteBean().getIdtipoPagante());
                res = 1;
            }
            if (res == 1) {
                listaFichaFamilia = fichaService.filtrarFichasPagadasObj(fichaFiltroBean);
                if (listaFichaFamilia.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaFichaFamilia = new ArrayList<>();
                    totSoles = new BigDecimal(pro.floatValue());
                } else if (!listaFichaFamilia.isEmpty()) {
                    for (FichaBean ficha : listaFichaFamilia) {
                        pro = ficha.getTotalFa().floatValue() + pro;
                    }
                    totSoles = new BigDecimal(pro.floatValue());
                }
            } else if (res == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                totSoles = new BigDecimal(pro.floatValue());
                listaFichaFamilia = new ArrayList<>();
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarFicha() {
        try {
            Integer res = 0;
            fichaFiltroBean = new FichaBean();
            totSoles = new BigDecimal(res.floatValue());
            GregorianCalendar fechaActual = new GregorianCalendar();
            getFichaFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getFichaFiltroBean().setFechaIni(fechaActual.getTime());
            getFichaFiltroBean().setFechaFin(fechaActual.getTime());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void reimpresionReciboMasivo(Object object) {
        ServletOutputStream out = null;
        try {
            FichaBean fichaBean = (FichaBean) object;
            List<CobranzaValoradoRepBean> listaCobranzaValoradoRepBean = new ArrayList<>();
            FichaService fichaService = BeanFactory.getFichaService();
            List<Integer> listaIds = new ArrayList<>();
            List<FichaBean> listaPagoFicha = new ArrayList<>();
            fichaBean.getTipoStatusFicha().setCodigo(MaristaConstantes.COD_FICHA_STATUS_PAGADO);
            fichaBean.getTipoStatusFicha().getTipoCodigoBean().setIdTipoCodigo(MaristaConstantes.TIP_STATUS_FICHA);
            listaPagoFicha = fichaService.obtenerFichaPorObjPagado(fichaBean);
            for (FichaBean ficha : listaPagoFicha) {
                listaIds.add(ficha.getIdFicha());
            }
            listaCobranzaValoradoRepBean = fichaService.generarReciboValorado(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaIds);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/RepCobranzaValorado.jasper");
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
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        FacesContext.getCurrentInstance().responseComplete();
    }

    //GET Y SET
    public FichaBean getFichaFiltroBean() {
        if (fichaFiltroBean == null) {
            fichaFiltroBean = new FichaBean();
        }
        return fichaFiltroBean;
    }

    public void setFichaFiltroBean(FichaBean fichaFiltroBean) {
        this.fichaFiltroBean = fichaFiltroBean;
    }

    public List<FichaBean> getListaFichaFamilia() {
        if (listaFichaFamilia == null) {
            listaFichaFamilia = new ArrayList<>();
        }
        return listaFichaFamilia;
    }

    public void setListaFichaFamilia(List<FichaBean> listaFichaFamilia) {
        this.listaFichaFamilia = listaFichaFamilia;
    }

    public BigDecimal getTotSoles() {
        return totSoles;
    }

    public void setTotSoles(BigDecimal totSoles) {
        this.totSoles = totSoles;
    }

    public List<CodigoBean> getListaModoPago() {
        if (listaModoPago == null) {
            listaModoPago = new ArrayList<>();
        }
        return listaModoPago;
    }

    public void setListaModoPago(List<CodigoBean> listaModoPago) {
        this.listaModoPago = listaModoPago;
    }

    public UsuarioBean getUsuarioLoginBean() {
        if (usuarioLoginBean == null) {
            usuarioLoginBean = new UsuarioBean();
        }
        return usuarioLoginBean;
    }

    public void setUsuarioLoginBean(UsuarioBean usuarioLoginBean) {
        this.usuarioLoginBean = usuarioLoginBean;
    }

    public List<TipoPaganteBean> getListaTipoPaganteBean() {
        if (listaTipoPaganteBean == null) {
            listaTipoPaganteBean = new ArrayList<>();
        }
        return listaTipoPaganteBean;
    }

    public void setListaTipoPaganteBean(List<TipoPaganteBean> listaTipoPaganteBean) {
        this.listaTipoPaganteBean = listaTipoPaganteBean;
    }

}

package pe.marista.sigma.managedBean;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
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
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.CajaGenRepBean;
import pe.marista.sigma.bean.reporte.CajaGeneralCtaRepBean;
import pe.marista.sigma.bean.reporte.CajaGeneralCtasRepBean;
import pe.marista.sigma.bean.reporte.CajaGeneralRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CajaGenService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

public class ArqueoTallerMB {

    @PostConstruct
    public void ArqueoTallerMB() {
        try {
            usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            fecActual = new GregorianCalendar();
            setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            setFecIni(fecActual.getTime());
            setFecFin(fecActual.getTime());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    private UsuarioBean usuarioBean;
    private String uniNeg;
    private Calendar fecActual;
    private Date fecIni;
    private Date fecFin;

    public void imprimirReporte() {
        ServletOutputStream out = null;
        try {
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/RepDetDocIngresoTaller.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<CajaGenRepBean> listaCajaGenRep = new ArrayList<>();
            listaCajaGenRep = cajaGenService.obtenerCajaGenPorDetalleForTaller(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getFecIni(), getFecFin());
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCajaGenRep);
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

    public void imprimirReporteCta() {
        ServletOutputStream out = null;
        try {
            String text = "";
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String date = sdf.format(getFecIni());
            text = "DEL " + sdf.format(getFecIni()) + "AL " + sdf.format(getFecFin());
            CajaGenService cajaGenService = BeanFactory.getCajaGenService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/RepDetDocIngresoPorCuentaTallerFor.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<CajaGeneralRepBean> listaCajaGenRep = new ArrayList<>();
            listaCajaGenRep = cajaGenService.obtenerCajaGenPorDetalleCtaForTaller(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getFecIni(), getFecFin());

            if (!listaCajaGenRep.isEmpty() && getFecIni() != null && getFecFin() != null) {
                for (CajaGeneralRepBean list : listaCajaGenRep) {
                    list.setTxtFiltro(text);
                }
            }
            if (!listaCajaGenRep.isEmpty()) {
                for (int i = 0; i < listaCajaGenRep.size(); i++) {
                    List<CajaGeneralCtasRepBean> listaCuentas = new ArrayList<>();
                    listaCuentas = cajaGenService.obtenerCuentasCajaGeneralForTaller(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getFecIni(), getFecFin());
                    listaCajaGenRep.get(i).setListaCuentas(listaCuentas);
                    if (!listaCuentas.isEmpty()) {
                        for (int j = 0; j < listaCajaGenRep.get(0).getListaCuentas().getData().size(); j++) {
                            List<CajaGeneralCtaRepBean> listaDetalle = new ArrayList<>();
                            System.out.println("cta-." + listaCuentas.get(j).getCuenta());
                            listaDetalle = cajaGenService.obtenerDetallePorCtaForTaller(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getFecIni(), getFecFin(), listaCuentas.get(j).getCuenta());
                            listaCuentas.get(j).setListaDetalle(listaDetalle);
                            listaCajaGenRep.get(i).setListaCuentas(listaCuentas);
                        }
                    }
                }
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCajaGenRep);
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
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
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

    public UsuarioBean getUsuarioBean() {
        if (usuarioBean == null) {
            usuarioBean = new UsuarioBean();
        }
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public Calendar getFecActual() {
        return fecActual;
    }

    public void setFecActual(Calendar fecActual) {
        this.fecActual = fecActual;
    }

    public Date getFecIni() {
        return fecIni;
    }

    public void setFecIni(Date fecIni) {
        this.fecIni = fecIni;
    }

    public Date getFecFin() {
        return fecFin;
    }

    public void setFecFin(Date fecFin) {
        this.fecFin = fecFin;
    }

}

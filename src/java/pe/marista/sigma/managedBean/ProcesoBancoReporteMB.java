/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.ProcesoBancoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.ProcesosBancosRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.ProcesoBancoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author Administrador
 */
public class ProcesoBancoReporteMB {

    @PostConstruct
    public void ProcesoBancoReporteMB() {
        try {
            beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getProcesoBancoBean();
            getProcesoBancoBean().getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getCuentasPorCobrarBean();
            getCuentasPorCobrarBean().getUnidadNegocioBean().setUniNeg(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            setearValoresMonto();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    private ProcesoBancoBean procesoBancoBean;
    private CuentasPorCobrarBean cuentasPorCobrarBean;
    private UsuarioBean beanUsuarioSesion;
    private Map<String, Object> objBanco;
    private List<ProcesosBancosRepBean> listaProcesosBancosRepBean;

    /* Montos por Meses */
    private Float totalMatricula;
    private Float totalMarzo;
    private Float totalAbril;
    private Float totalMayo;
    private Float totalJunio;
    private Float totalJulio;
    private Float totalAgosto;
    private Float totalSetiembre;
    private Float totalOctubre;
    private Float totalNoviembre;
    private Float totalDiciembre;

    /* Get y Set */
    public ProcesoBancoBean getProcesoBancoBean() {
        if (procesoBancoBean == null) {
            procesoBancoBean = new ProcesoBancoBean();
        }
        return procesoBancoBean;
    }

    public void setProcesoBancoBean(ProcesoBancoBean procesoBancoBean) {
        this.procesoBancoBean = procesoBancoBean;
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

    public UsuarioBean getBeanUsuarioSesion() {
        if (beanUsuarioSesion == null) {
            beanUsuarioSesion = new UsuarioBean();
        }
        return beanUsuarioSesion;
    }

    public void setBeanUsuarioSesion(UsuarioBean beanUsuarioSesion) {
        this.beanUsuarioSesion = beanUsuarioSesion;
    }

    public Map<String, Object> getObjBanco() {
        return objBanco;
    }

    public void setObjBanco(Map<String, Object> objBanco) {
        this.objBanco = objBanco;
    }

    public List<ProcesosBancosRepBean> getListaProcesosBancosRepBean() {
        if (listaProcesosBancosRepBean == null) {
            listaProcesosBancosRepBean = new ArrayList<>();
        }
        return listaProcesosBancosRepBean;
    }

    public void setListaProcesosBancosRepBean(List<ProcesosBancosRepBean> listaProcesosBancosRepBean) {
        this.listaProcesosBancosRepBean = listaProcesosBancosRepBean;
    }

    public Float getTotalMatricula() {
        return totalMatricula;
    }

    public void setTotalMatricula(Float totalMatricula) {
        this.totalMatricula = totalMatricula;
    }

    public Float getTotalMarzo() {
        return totalMarzo;
    }

    public void setTotalMarzo(Float totalMarzo) {
        this.totalMarzo = totalMarzo;
    }

    public Float getTotalAbril() {
        return totalAbril;
    }

    public void setTotalAbril(Float totalAbril) {
        this.totalAbril = totalAbril;
    }

    public Float getTotalMayo() {
        return totalMayo;
    }

    public void setTotalMayo(Float totalMayo) {
        this.totalMayo = totalMayo;
    }

    public Float getTotalJunio() {
        return totalJunio;
    }

    public void setTotalJunio(Float totalJunio) {
        this.totalJunio = totalJunio;
    }

    public Float getTotalJulio() {
        return totalJulio;
    }

    public void setTotalJulio(Float totalJulio) {
        this.totalJulio = totalJulio;
    }

    public Float getTotalAgosto() {
        return totalAgosto;
    }

    public void setTotalAgosto(Float totalAgosto) {
        this.totalAgosto = totalAgosto;
    }

    public Float getTotalSetiembre() {
        return totalSetiembre;
    }

    public void setTotalSetiembre(Float totalSetiembre) {
        this.totalSetiembre = totalSetiembre;
    }

    public Float getTotalOctubre() {
        return totalOctubre;
    }

    public void setTotalOctubre(Float totalOctubre) {
        this.totalOctubre = totalOctubre;
    }

    public Float getTotalNoviembre() {
        return totalNoviembre;
    }

    public void setTotalNoviembre(Float totalNoviembre) {
        this.totalNoviembre = totalNoviembre;
    }

    public Float getTotalDiciembre() {
        return totalDiciembre;
    }

    public void setTotalDiciembre(Float totalDiciembre) {
        this.totalDiciembre = totalDiciembre;
    }

    /* Metodos */
    public void setearValoresMonto() {
        try {
            Integer var = 0;
            setTotalMatricula(var.floatValue());
            setTotalMarzo(var.floatValue());
            setTotalAbril(var.floatValue());
            setTotalMayo(var.floatValue());
            setTotalJunio(var.floatValue());
            setTotalJulio(var.floatValue());
            setTotalAgosto(var.floatValue());
            setTotalSetiembre(var.floatValue());
            setTotalOctubre(var.floatValue());
            setTotalNoviembre(var.floatValue());
            setTotalDiciembre(var.floatValue());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void filtrarReporteBanco() {
        try {
            if (getCuentasPorCobrarBean().getFechaInicio() != null) {
                getCuentasPorCobrarBean().setFechaInicio(getCuentasPorCobrarBean().getFechaInicio());
            }
            if (getCuentasPorCobrarBean().getFechaFin() != null) {
                getCuentasPorCobrarBean().setFechaFin(getCuentasPorCobrarBean().getFechaFin());
            }
            if (getCuentasPorCobrarBean().getEstudianteBean().getIdEstudiante() != null
                    && !getCuentasPorCobrarBean().getEstudianteBean().getIdEstudiante().equals("")) {
                getCuentasPorCobrarBean().getEstudianteBean().setIdEstudiante(getCuentasPorCobrarBean().getEstudianteBean().getIdEstudiante());
            }
            if (getCuentasPorCobrarBean().getEstudianteBean().getCodigo() != null
                    && !getCuentasPorCobrarBean().getEstudianteBean().getCodigo().equals("")) {
                getCuentasPorCobrarBean().getEstudianteBean().setCodigo(getCuentasPorCobrarBean().getEstudianteBean().getCodigo());
            }
            if (getCuentasPorCobrarBean().getEstudianteBean().getPersonaBean().getNombreCompleto() != null
                    && !getCuentasPorCobrarBean().getEstudianteBean().getPersonaBean().getNombreCompleto().equals("")) {
                getCuentasPorCobrarBean().getEstudianteBean().getPersonaBean().setNombreCompleto(getCuentasPorCobrarBean().getEstudianteBean().getPersonaBean().getNombreCompleto());
            }
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            listaProcesosBancosRepBean = procesoBancoService.obtenerReporteBanco(getCuentasPorCobrarBean());
            if (getListaProcesosBancosRepBean().size() != 0) {
                obtenerMontos();
            } else {
                new MensajePrime().addInformativeMessage("informacionEtiqueta");
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarFiltro() {
        try {
            cuentasPorCobrarBean = new CuentasPorCobrarBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerMontos() {
        try {
            if (listaProcesosBancosRepBean.size() != 0) {
                for (ProcesosBancosRepBean reporte : listaProcesosBancosRepBean) {
                    setTotalMatricula(reporte.getTotalMatricula());
                    setTotalMarzo(reporte.getTotalMarzo());
                    setTotalAbril(reporte.getTotalAbril());
                    setTotalMayo(reporte.getTotalMayo());
                    setTotalJunio(reporte.getTotalJunio());
                    setTotalJulio(reporte.getTotalJulio());
                    setTotalAgosto(reporte.getTotalAgosto());
                    setTotalSetiembre(reporte.getTotalSetiembre());
                    setTotalOctubre(reporte.getTotalOctubre());
                    setTotalNoviembre(reporte.getTotalNoviembre());
                    setTotalDiciembre(reporte.getTotalDiciembre());
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void imprimirReporteBanco() {
        ServletOutputStream out = null;
        try {
            if (!listaProcesosBancosRepBean.isEmpty()) {
                ProcesosBancosRepBean procesoBancosRepBean = new ProcesosBancosRepBean();
                procesoBancosRepBean.setTotal(listaProcesosBancosRepBean.size());
                listaProcesosBancosRepBean.add(procesoBancosRepBean);
                ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/RepProcesosBancos.jasper");
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                String absoluteWebPath = externalContext.getRealPath("/");
                File file = new File(archivoJasper);
                JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaProcesosBancosRepBean);
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
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
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

}

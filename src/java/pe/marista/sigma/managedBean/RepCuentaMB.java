package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.CuentaRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.PresupuestoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

public class RepCuentaMB implements Serializable {

    @PostConstruct
    public void RepCuentaMB() {
        try {
            usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getCuentaRepBean();
            cuentaRepBean.setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            cuentaRepBean.setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco));
            getFlgFiltroRep();
            setFlgFiltroRep(1);
            obtenerFiltroRep();
            totalMonto = new BigDecimal(0.0);
            totalMontoPre = new BigDecimal(0.0);
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    //CLASES
    private CuentaRepBean cuentaRepBean;
    private UsuarioBean usuarioBean;

    //LISTAS
    private List<CuentaRepBean> listaCuentaRepBean;

    //VARIABLES 
    private Integer flgFiltroRep;
    private Boolean renderCuenta;
    private Boolean renderCuentaCr;
    private Boolean renderCr;
    private BigDecimal totalMonto;
    private BigDecimal totalMontoPre;
    private Integer valor = 0;

    //TOTALES
    private BigDecimal totalPres;
    private BigDecimal totalExec;
    private BigDecimal totalSaldo;

    private String pres;
    private String exec;
    private String sald;

    public void obtenerFiltroRep() {
        try {
            if (flgFiltroRep.equals(1)) {
                setRenderCuenta(true);
                setRenderCuentaCr(false);
                setRenderCr(false);
                getCuentaRepBean().setFlgFiltro(null);
                getCuentaRepBean().setCrDigit(null);
                getCuentaRepBean().setDigit(null);
            } else if (flgFiltroRep.equals(2)) {
                setRenderCuentaCr(true);
                setRenderCuenta(false);
                setRenderCr(false);
                getCuentaRepBean().setCuentaIni(null);
                getCuentaRepBean().setCuentaFin(null);
                getCuentaRepBean().setCuentaExec(null);
                getCuentaRepBean().setFlgFiltro(1);
            } else if (flgFiltroRep.equals(3)) {
                setRenderCr(true);
                setRenderCuentaCr(false);
                setRenderCuenta(false);
                getCuentaRepBean().setCuentaIni(null);
                getCuentaRepBean().setCuentaFin(null);
                getCuentaRepBean().setCuentaExec(null);
                getCuentaRepBean().setFlgFiltro(2);
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    //FILTRO POR CUENTA
    public void obtenerListaCuenta() {
        try {
            Integer res = 0;
            Double n = 0.0, m = 0.0, x = 0.0;
            PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
            if (cuentaRepBean.getCuentaIni() != null) {
                cuentaRepBean.setCuentaIni(cuentaRepBean.getCuentaIni());
                res = 1;
            }
            if (cuentaRepBean.getCuentaFin() != null) {
                cuentaRepBean.setCuentaFin(cuentaRepBean.getCuentaFin());
                res = 1;
            }
            if (cuentaRepBean.getCuentaExec() != null) {
                cuentaRepBean.setCuentaExec(cuentaRepBean.getCuentaExec());
                res = 1;
            }
            if (cuentaRepBean.getCrDigit() != null) {
                cuentaRepBean.setCrDigit(cuentaRepBean.getCrDigit());
                res = 1;
            }
            if (cuentaRepBean.getDigit() != null) {
                cuentaRepBean.setDigit(cuentaRepBean.getDigit());
                res = 1;
            }
            if (res == 1) {
                listaCuentaRepBean = presupuestoService.obtenerListaCuentaFiltro(cuentaRepBean);
                if (listaCuentaRepBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaCuentaRepBean = new ArrayList<>();
                    totalMonto = new BigDecimal(n);
                    totalMontoPre = new BigDecimal(m);

                } else if (!listaCuentaRepBean.isEmpty()) {
                    for (CuentaRepBean cuenta : listaCuentaRepBean) {
                        n = Double.parseDouble(cuenta.getMontoExecVista()) + n;
                        m = Double.parseDouble(cuenta.getMontoPresVista()) + m;
                        x = Double.parseDouble(cuenta.getMontoSaldoVista()) + x;
                    }
                    totalMonto = new BigDecimal(n);
                    totalMontoPre = new BigDecimal(m);
                    totalSaldo = new BigDecimal(x);
                    valor = listaCuentaRepBean.size();
                }
            } else if (res == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaCuentaRepBean = new ArrayList<>();
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void imprimirReporte() {
        String pagina = null;
        pagina = new MaristaUtils().validaUsuarioSesion();
        ServletOutputStream out = null;
        try {
            if (pagina == null) {
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                String archivoJasper = "";
                if (getFlgFiltroRep() != null) {
                    if (getFlgFiltroRep().equals(1)) {
                        archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reportePresupuestoCuenta.jasper");
                    } else if (getFlgFiltroRep().equals(2)) {
                        archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reportePresupuestoCuentaCr.jasper");
                    }
                }
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                String absoluteWebPath = externalContext.getRealPath("/");
                File file = new File(archivoJasper);
                JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
                Map<String, Object> parametros = new HashMap<>();
                String ruta = absoluteWebPath + "reportes\\";
                parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
                if (getFlgFiltroRep() != null) {
                    if (getFlgFiltroRep().equals(1)) {
                        for (CuentaRepBean cuenta : listaCuentaRepBean) {
                            cuenta.setTotalExec(totalMonto.setScale(2, RoundingMode.HALF_UP).toString());
                            cuenta.setTotalPres(totalMontoPre.setScale(2, RoundingMode.HALF_UP).toString());
                            cuenta.setTotalSaldo(totalSaldo.setScale(2, RoundingMode.HALF_UP).toString());
                        }
                        JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCuentaRepBean);
                        byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
                        response.reset();
                        response.setContentType("application/pdf");
                        response.setContentLength(bytes.length);
                        response.setHeader("Content-Disposition", "inline; filename=ReporteDeudas" + new SimpleDateFormat(" dd-MM-yyyy HH:mm:ss").format(new Date()) + ".pdf");
                        response.setHeader("Cache-Control", "cache, must-revalidate");
                        response.setHeader("Pragma", "public");
                        out = response.getOutputStream();
                        out.write(bytes);
                        out.flush();
                    } else if (getFlgFiltroRep().equals(2)) {
                        List<CuentaRepBean> listaCuenta = new ArrayList<>();
                        PresupuestoService presupuestoService = BeanFactory.getPresupuestoService();
                        listaCuenta = presupuestoService.obtenerListaCuentaFiltroRep(cuentaRepBean);
                        JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCuenta);
                        for (CuentaRepBean cuenta : listaCuenta) {
                            cuenta.setTotalExec(totalMonto.setScale(2, RoundingMode.HALF_UP).toString());
                            cuenta.setTotalPres(totalMontoPre.setScale(2, RoundingMode.HALF_UP).toString());
                            cuenta.setTotalSaldo(totalSaldo.setScale(2, RoundingMode.HALF_UP).toString());
                        }
                        byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
                        response.reset();
                        response.setContentType("application/pdf");
                        response.setContentLength(bytes.length);
                        response.setHeader("Content-Disposition", "inline; filename=ReporteDeudas" + new SimpleDateFormat(" dd-MM-yyyy HH:mm:ss").format(new Date()) + ".pdf");
                        response.setHeader("Cache-Control", "cache, must-revalidate");
                        response.setHeader("Pragma", "public");
                        out = response.getOutputStream();
                        out.write(bytes);
                        out.flush();
                    }
                }
            }
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

    public CuentaRepBean getCuentaRepBean() {
        if (cuentaRepBean == null) {
            cuentaRepBean = new CuentaRepBean();
        }
        return cuentaRepBean;
    }

    public void setCuentaRepBean(CuentaRepBean cuentaRepBean) {
        this.cuentaRepBean = cuentaRepBean;
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

    public List<CuentaRepBean> getListaCuentaRepBean() {
        if (listaCuentaRepBean == null) {
            listaCuentaRepBean = new ArrayList<>();
        }
        return listaCuentaRepBean;
    }

    public void setListaCuentaRepBean(List<CuentaRepBean> listaCuentaRepBean) {
        this.listaCuentaRepBean = listaCuentaRepBean;
    }

    public Integer getFlgFiltroRep() {
        return flgFiltroRep;
    }

    public void setFlgFiltroRep(Integer flgFiltroRep) {
        this.flgFiltroRep = flgFiltroRep;
    }

    public Boolean getRenderCuenta() {
        return renderCuenta;
    }

    public void setRenderCuenta(Boolean renderCuenta) {
        this.renderCuenta = renderCuenta;
    }

    public Boolean getRenderCuentaCr() {
        return renderCuentaCr;
    }

    public void setRenderCuentaCr(Boolean renderCuentaCr) {
        this.renderCuentaCr = renderCuentaCr;
    }

    public BigDecimal getTotalMonto() {
        return totalMonto;
    }

    public void setTotalMonto(BigDecimal totalMonto) {
        this.totalMonto = totalMonto;
    }

    public BigDecimal getTotalMontoPre() {
        return totalMontoPre;
    }

    public void setTotalMontoPre(BigDecimal totalMontoPre) {
        this.totalMontoPre = totalMontoPre;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public BigDecimal getTotalSaldo() {
        return totalSaldo;
    }

    public void setTotalSaldo(BigDecimal totalSaldo) {
        this.totalSaldo = totalSaldo;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getExec() {
        return exec;
    }

    public void setExec(String exec) {
        this.exec = exec;
    }

    public String getSald() {
        return sald;
    }

    public void setSald(String sald) {
        this.sald = sald;
    }

    public Boolean getRenderCr() {
        return renderCr;
    }

    public void setRenderCr(Boolean renderCr) {
        this.renderCr = renderCr;
    }

}

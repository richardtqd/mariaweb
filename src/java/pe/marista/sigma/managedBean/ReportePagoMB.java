/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.PagosUniNegRepBean;
import pe.marista.sigma.bean.reporte.ReportePagoRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.ProcesoFinalService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author JC
 */
public class ReportePagoMB implements Serializable {

    @PostConstruct
    public void ReportePagoMB() {
        try {
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getCuentasPorCobrarBean();
            getCuentasPorCobrarBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            GregorianCalendar fechaActual = new GregorianCalendar();
            getCuentasPorCobrarBean().setFechaInicio(fechaActual.getTime());
            getCuentasPorCobrarBean().setFechaFin(fechaActual.getTime());
            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoEstado = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_CUENTA));

            //Inicializando Filtro
            setSelTipFil(1);
            selFiltro();
            filtrarPagos();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    private CuentasPorCobrarBean cuentasPorCobrarBean;
    private ReportePagoRepBean reportePagoBean;
    private List<ReportePagoRepBean> listaReportePagoRepBean;
    private List<ReportePagoRepBean> listaReporteDeudaRepBean;
    private ReportePagoRepBean reportePagoRepBean;
    private UsuarioBean usuarioLoginBean;
    private List<CodigoBean> listaTipoEstado;

    private Integer selTipFil;
    private Boolean flgTipFil;

    /* Montos por pagos */
    private BigDecimal montoFebrero;
    private BigDecimal montoMarzo;
    private BigDecimal montoAbril;
    private BigDecimal montoMayo;
    private BigDecimal montoJunio;
    private BigDecimal montoJulio;
    private BigDecimal montoAgosto;
    private BigDecimal montoSetiembre;
    private BigDecimal montoOctubre;
    private BigDecimal montoNoviembre;
    private BigDecimal montoDiciembre;

    /* Montos por mora */
    private BigDecimal moraFebrero;
    private BigDecimal moraMarzo;
    private BigDecimal moraAbril;
    private BigDecimal moraMayo;
    private BigDecimal moraJunio;
    private BigDecimal moraJulio;
    private BigDecimal moraAgosto;
    private BigDecimal moraSetiembre;
    private BigDecimal moraOctubre;
    private BigDecimal moraNoviembre;
    private BigDecimal moraDiciembre;

    public CuentasPorCobrarBean getCuentasPorCobrarBean() {
        if (cuentasPorCobrarBean == null) {
            cuentasPorCobrarBean = new CuentasPorCobrarBean();
        }
        return cuentasPorCobrarBean;
    }

    public void setCuentasPorCobrarBean(CuentasPorCobrarBean cuentasPorCobrarBean) {
        this.cuentasPorCobrarBean = cuentasPorCobrarBean;
    }

    public ReportePagoRepBean getReportePagoBean() {
        if (reportePagoBean == null) {
            reportePagoBean = new ReportePagoRepBean();
        }
        return reportePagoBean;
    }

    public void setReportePagoBean(ReportePagoRepBean reportePagoBean) {
        this.reportePagoBean = reportePagoBean;
    }

    public UsuarioBean getUsuarioLoginBean() {
        if (usuarioLoginBean == null) {
            usuarioLoginBean = new UsuarioBean();
        }
        return usuarioLoginBean;
    }

    public List<ReportePagoRepBean> getListaReportePagoRepBean() {
        if (listaReportePagoRepBean == null) {
            listaReportePagoRepBean = new ArrayList<>();
        }
        return listaReportePagoRepBean;
    }

    public void setListaReportePagoRepBean(List<ReportePagoRepBean> listaReportePagoRepBean) {
        this.listaReportePagoRepBean = listaReportePagoRepBean;
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

    public void setUsuarioLoginBean(UsuarioBean usuarioLoginBean) {
        this.usuarioLoginBean = usuarioLoginBean;
    }

    public ReportePagoRepBean getReportePagoRepBean() {
        return reportePagoRepBean;
    }

    public void setReportePagoRepBean(ReportePagoRepBean reportePagoRepBean) {
        this.reportePagoRepBean = reportePagoRepBean;
    }

    public List<ReportePagoRepBean> getListaReporteDeudaRepBean() {
        if (listaReporteDeudaRepBean == null) {
            listaReporteDeudaRepBean = new ArrayList<>();
        }
        return listaReporteDeudaRepBean;
    }

    public void setListaReporteDeudaRepBean(List<ReportePagoRepBean> listaReporteDeudaRepBean) {
        this.listaReporteDeudaRepBean = listaReporteDeudaRepBean;
    }

    public Integer getSelTipFil() {
        return selTipFil;
    }

    public void setSelTipFil(Integer selTipFil) {
        this.selTipFil = selTipFil;
    }

    public Boolean getFlgTipFil() {
        return flgTipFil;
    }

    public void setFlgTipFil(Boolean flgTipFil) {
        this.flgTipFil = flgTipFil;
    }

    public BigDecimal getMontoFebrero() {
        return montoFebrero;
    }

    public void setMontoFebrero(BigDecimal montoFebrero) {
        this.montoFebrero = montoFebrero;
    }

    public BigDecimal getMontoMarzo() {
        return montoMarzo;
    }

    public void setMontoMarzo(BigDecimal montoMarzo) {
        this.montoMarzo = montoMarzo;
    }

    public BigDecimal getMontoAbril() {
        return montoAbril;
    }

    public void setMontoAbril(BigDecimal montoAbril) {
        this.montoAbril = montoAbril;
    }

    public BigDecimal getMontoMayo() {
        return montoMayo;
    }

    public void setMontoMayo(BigDecimal montoMayo) {
        this.montoMayo = montoMayo;
    }

    public BigDecimal getMontoJunio() {
        return montoJunio;
    }

    public void setMontoJunio(BigDecimal montoJunio) {
        this.montoJunio = montoJunio;
    }

    public BigDecimal getMontoJulio() {
        return montoJulio;
    }

    public void setMontoJulio(BigDecimal montoJulio) {
        this.montoJulio = montoJulio;
    }

    public BigDecimal getMontoAgosto() {
        return montoAgosto;
    }

    public void setMontoAgosto(BigDecimal montoAgosto) {
        this.montoAgosto = montoAgosto;
    }

    public BigDecimal getMontoSetiembre() {
        return montoSetiembre;
    }

    public void setMontoSetiembre(BigDecimal montoSetiembre) {
        this.montoSetiembre = montoSetiembre;
    }

    public BigDecimal getMontoOctubre() {
        return montoOctubre;
    }

    public void setMontoOctubre(BigDecimal montoOctubre) {
        this.montoOctubre = montoOctubre;
    }

    public BigDecimal getMontoNoviembre() {
        return montoNoviembre;
    }

    public void setMontoNoviembre(BigDecimal montoNoviembre) {
        this.montoNoviembre = montoNoviembre;
    }

    public BigDecimal getMontoDiciembre() {
        return montoDiciembre;
    }

    public void setMontoDiciembre(BigDecimal montoDiciembre) {
        this.montoDiciembre = montoDiciembre;
    }

    public BigDecimal getMoraFebrero() {
        return moraFebrero;
    }

    public void setMoraFebrero(BigDecimal moraFebrero) {
        this.moraFebrero = moraFebrero;
    }

    public BigDecimal getMoraMarzo() {
        return moraMarzo;
    }

    public void setMoraMarzo(BigDecimal moraMarzo) {
        this.moraMarzo = moraMarzo;
    }

    public BigDecimal getMoraAbril() {
        return moraAbril;
    }

    public void setMoraAbril(BigDecimal moraAbril) {
        this.moraAbril = moraAbril;
    }

    public BigDecimal getMoraMayo() {
        return moraMayo;
    }

    public void setMoraMayo(BigDecimal moraMayo) {
        this.moraMayo = moraMayo;
    }

    public BigDecimal getMoraJunio() {
        return moraJunio;
    }

    public void setMoraJunio(BigDecimal moraJunio) {
        this.moraJunio = moraJunio;
    }

    public BigDecimal getMoraJulio() {
        return moraJulio;
    }

    public void setMoraJulio(BigDecimal moraJulio) {
        this.moraJulio = moraJulio;
    }

    public BigDecimal getMoraAgosto() {
        return moraAgosto;
    }

    public void setMoraAgosto(BigDecimal moraAgosto) {
        this.moraAgosto = moraAgosto;
    }

    public BigDecimal getMoraSetiembre() {
        return moraSetiembre;
    }

    public void setMoraSetiembre(BigDecimal moraSetiembre) {
        this.moraSetiembre = moraSetiembre;
    }

    public BigDecimal getMoraOctubre() {
        return moraOctubre;
    }

    public void setMoraOctubre(BigDecimal moraOctubre) {
        this.moraOctubre = moraOctubre;
    }

    public BigDecimal getMoraNoviembre() {
        return moraNoviembre;
    }

    public void setMoraNoviembre(BigDecimal moraNoviembre) {
        this.moraNoviembre = moraNoviembre;
    }

    public BigDecimal getMoraDiciembre() {
        return moraDiciembre;
    }

    public void setMoraDiciembre(BigDecimal moraDiciembre) {
        this.moraDiciembre = moraDiciembre;
    }
 
    public void filtrarPagos() {
        try {
            Integer estado = 0;
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            Object obj = new Object();
            if (selTipFil.equals(1)) {
//                obj = procesoFinalService.execProGenRepPago(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getCuentasPorCobrarBean().getFechaInicio(), getCuentasPorCobrarBean().getFechaFin(), 19404);
//                obj = procesoFinalService.execProGenRepPago(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getCuentasPorCobrarBean().getFechaInicio(), getCuentasPorCobrarBean().getFechaFin(), 19401);
                listaReportePagoRepBean = procesoFinalService.obtenerTotalRepBanco(cuentasPorCobrarBean);
                listaReporteDeudaRepBean = procesoFinalService.obtenerTotalRepBancoDeuda(cuentasPorCobrarBean);
                if (listaReportePagoRepBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                } else {
                    montoFebrero = obtenerImportesMoras(2, 1);
                    montoMarzo = obtenerImportesMoras(3, 1);
                    montoAbril = obtenerImportesMoras(4, 1);
                    montoMayo = obtenerImportesMoras(5, 1);
                    montoJunio = obtenerImportesMoras(6, 1);
                    montoJulio = obtenerImportesMoras(7, 1);
                    montoAgosto = obtenerImportesMoras(8, 1);
                    montoSetiembre = obtenerImportesMoras(9, 1);
                    montoOctubre = obtenerImportesMoras(10, 1);
                    montoNoviembre = obtenerImportesMoras(11, 1);
                    montoDiciembre = obtenerImportesMoras(12, 1);
                }
                if (listaReporteDeudaRepBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                } else {
                    moraFebrero = obtenerImportesMoras(2, 0);
                    moraMarzo = obtenerImportesMoras(3, 0);
                    moraAbril = obtenerImportesMoras(4, 0);
                    moraMayo = obtenerImportesMoras(5, 0);
                    moraJunio = obtenerImportesMoras(6, 0);
                    moraJulio = obtenerImportesMoras(7, 0);
                    moraAgosto = obtenerImportesMoras(8, 0);
                    moraSetiembre = obtenerImportesMoras(9, 0);
                    moraOctubre = obtenerImportesMoras(10, 0);
                    moraNoviembre = obtenerImportesMoras(11, 0);
                    moraDiciembre = obtenerImportesMoras(12, 0);
                }
            } else if (selTipFil.equals(0)) {
                if (getCuentasPorCobrarBean().getFechaInicio() != null) {
                    getCuentasPorCobrarBean().setFechaInicio(getCuentasPorCobrarBean().getFechaInicio());
                    estado = 1;
                }
                if (getCuentasPorCobrarBean().getFechaFin() != null) {
                    getCuentasPorCobrarBean().setFechaFin(getCuentasPorCobrarBean().getFechaFin());
                    estado = 1;
                }
                if (getCuentasPorCobrarBean().getIdTipoStatusCtaCte().getIdCodigo() != null) {
                    getCuentasPorCobrarBean().getIdTipoStatusCtaCte().setIdCodigo(getCuentasPorCobrarBean().getIdTipoStatusCtaCte().getIdCodigo());
                    estado = 1;
                }
                if (estado == 1) {
//                    obj = procesoFinalService.execProGenRepPago(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getCuentasPorCobrarBean().getFechaInicio(), getCuentasPorCobrarBean().getFechaFin(), 19404);
//                    obj = procesoFinalService.execProGenRepPago(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getCuentasPorCobrarBean().getFechaInicio(), getCuentasPorCobrarBean().getFechaFin(), 19401);
                    listaReportePagoRepBean = procesoFinalService.obtenerTotalRepBanco(cuentasPorCobrarBean);
                    listaReporteDeudaRepBean = procesoFinalService.obtenerTotalRepBancoDeuda(cuentasPorCobrarBean);
                    if (listaReportePagoRepBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    } else {
                        montoFebrero = obtenerImportesMoras(2, 1);
                        montoMarzo = obtenerImportesMoras(3, 1);
                        montoAbril = obtenerImportesMoras(4, 1);
                        montoMayo = obtenerImportesMoras(5, 1);
                        montoJunio = obtenerImportesMoras(6, 1);
                        montoJulio = obtenerImportesMoras(7, 1);
                        montoAgosto = obtenerImportesMoras(8, 1);
                        montoSetiembre = obtenerImportesMoras(9, 1);
                        montoOctubre = obtenerImportesMoras(10, 1);
                        montoNoviembre = obtenerImportesMoras(11, 1);
                        montoDiciembre = obtenerImportesMoras(12, 1);
                    }
                    if (listaReporteDeudaRepBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    } else {
                        moraFebrero = obtenerImportesMoras(2, 0);
                        moraMarzo = obtenerImportesMoras(3, 0);
                        moraAbril = obtenerImportesMoras(4, 0);
                        moraMayo = obtenerImportesMoras(5, 0);
                        moraJunio = obtenerImportesMoras(6, 0);
                        moraJulio = obtenerImportesMoras(7, 0);
                        moraAgosto = obtenerImportesMoras(8, 0);
                        moraSetiembre = obtenerImportesMoras(9, 0);
                        moraOctubre = obtenerImportesMoras(10, 0);
                        moraNoviembre = obtenerImportesMoras(11, 0);
                        moraDiciembre = obtenerImportesMoras(12, 0);
                    }
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }
    
    public void limpiarPagos() {
        try {
            cuentasPorCobrarBean = new CuentasPorCobrarBean();
            GregorianCalendar fechaActual = new GregorianCalendar();
            getCuentasPorCobrarBean().setFechaInicio(fechaActual.getTime());
            getCuentasPorCobrarBean().setFechaFin(fechaActual.getTime());
            CodigoService codigoService = BeanFactory.getCodigoService();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void selFiltro() {
        try {
            if (selTipFil.equals(1)) {
                setFlgTipFil(true);
                getCuentasPorCobrarBean().setFechaInicio(null);
                getCuentasPorCobrarBean().setFechaFin(null);
            } else if (selTipFil.equals(0)) {
                setFlgTipFil(false);
                GregorianCalendar fechaActual = new GregorianCalendar();
                getCuentasPorCobrarBean().setFechaInicio(fechaActual.getTime());
                getCuentasPorCobrarBean().setFechaFin(fechaActual.getTime());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public BigDecimal obtenerImportesMoras(Integer mes, Integer opcion) {
        Float m = null;
        Integer dato = 0;
        m = dato.floatValue();
        try {
            /*
             Operacion: 
             Caso sea 1 => suma monstos pagados
             Caso sea 0 => suma monstos moras
             */
            if (opcion.equals(1)) {
                if (!listaReportePagoRepBean.isEmpty()) {
                    if (mes.equals(2)) {
                        for (ReportePagoRepBean pagos : listaReportePagoRepBean) {
                            m = pagos.getMontoFebrero().floatValue() + m;
                        }
                    }
                    if (mes.equals(3)) {
                        for (ReportePagoRepBean pagos : listaReportePagoRepBean) {
                            m = pagos.getMontoMarzo().floatValue() + m;
                        }
                    }
                    if (mes.equals(4)) {
                        for (ReportePagoRepBean pagos : listaReportePagoRepBean) {
                            m = pagos.getMontoAbril().floatValue() + m;
                        }
                    }
                    if (mes.equals(5)) {
                        for (ReportePagoRepBean pagos : listaReportePagoRepBean) {
                            m = pagos.getMontoMayo().floatValue() + m;
                        }
                    }
                    if (mes.equals(6)) {
                        for (ReportePagoRepBean pagos : listaReportePagoRepBean) {
                            m = pagos.getMontoJunio().floatValue() + m;
                        }
                    }
                    if (mes.equals(7)) {
                        for (ReportePagoRepBean pagos : listaReportePagoRepBean) {
                            m = pagos.getMontoJulio().floatValue() + m;
                        }
                    }
                    if (mes.equals(8)) {
                        for (ReportePagoRepBean pagos : listaReportePagoRepBean) {
                            m = pagos.getMontoAgosto().floatValue() + m;
                        }
                    }
                    if (mes.equals(9)) {
                        for (ReportePagoRepBean pagos : listaReportePagoRepBean) {
                            m = pagos.getMontoSetiembre().floatValue() + m;
                        }
                    }
                    if (mes.equals(10)) {
                        for (ReportePagoRepBean pagos : listaReportePagoRepBean) {
                            m = pagos.getMontoOctubre().floatValue() + m;
                        }
                    }
                    if (mes.equals(11)) {
                        for (ReportePagoRepBean pagos : listaReportePagoRepBean) {
                            m = pagos.getMontoNoviembre().floatValue() + m;
                        }
                    }
                    if (mes.equals(12)) {
                        for (ReportePagoRepBean pagos : listaReportePagoRepBean) {
                            m = pagos.getMontoDiciembre().floatValue() + m;
                        }
                    }
                }
            } else if (opcion.equals(0)) {
                if (!listaReporteDeudaRepBean.isEmpty()) {
                    if (mes.equals(2)) {
                        for (ReportePagoRepBean deuda : listaReporteDeudaRepBean) {
                            m = deuda.getMontoFebrero().floatValue() + m;
                        }
                    }
                    if (mes.equals(3)) {
                        for (ReportePagoRepBean deuda : listaReporteDeudaRepBean) {
                            m = deuda.getMontoMarzo().floatValue() + m;
                        }
                    }
                    if (mes.equals(4)) {
                        for (ReportePagoRepBean deuda : listaReporteDeudaRepBean) {
                            m = deuda.getMontoAbril().floatValue() + m;
                        }
                    }
                    if (mes.equals(5)) {
                        for (ReportePagoRepBean deuda : listaReporteDeudaRepBean) {
                            m = deuda.getMontoMayo().floatValue() + m;
                        }
                    }
                    if (mes.equals(6)) {
                        for (ReportePagoRepBean deuda : listaReporteDeudaRepBean) {
                            m = deuda.getMontoJunio().floatValue() + m;
                        }
                    }
                    if (mes.equals(7)) {
                        for (ReportePagoRepBean deuda : listaReporteDeudaRepBean) {
                            m = deuda.getMontoJulio().floatValue() + m;
                        }
                    }
                    if (mes.equals(8)) {
                        for (ReportePagoRepBean deuda : listaReporteDeudaRepBean) {
                            m = deuda.getMontoAgosto().floatValue() + m;
                        }
                    }
                    if (mes.equals(9)) {
                        for (ReportePagoRepBean deuda : listaReporteDeudaRepBean) {
                            m = deuda.getMontoSetiembre().floatValue() + m;
                        }
                    }
                    if (mes.equals(10)) {
                        for (ReportePagoRepBean deuda : listaReporteDeudaRepBean) {
                            m = deuda.getMontoOctubre().floatValue() + m;
                        }
                    }
                    if (mes.equals(11)) {
                        for (ReportePagoRepBean deuda : listaReporteDeudaRepBean) {
                            m = deuda.getMontoNoviembre().floatValue() + m;
                        }
                    }
                    if (mes.equals(12)) {
                        for (ReportePagoRepBean deuda : listaReporteDeudaRepBean) {
                            m = deuda.getMontoDiciembre().floatValue() + m;
                        }
                    }
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        BigDecimal monto = new BigDecimal(m);
        return monto;
    }

}

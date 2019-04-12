/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.ProcesoBancoBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.NotaAbonoRepBean;
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
public class ReporteNotaAbonoMB {

    @PostConstruct
    public void ReporteNotaAbonoMB() {
        try {
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getProcesoBancoBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            GregorianCalendar fechaActual = new GregorianCalendar();
            getProcesoBancoBean().setFechaInicio(fechaActual.getTime());
            getProcesoBancoBean().setFechaFin(fechaActual.getTime());
            getProcesoBancoBean().setEstado(MaristaConstantes.COD_STA_CTA_EST_PAG);

            CodigoService codigoService = BeanFactory.getCodigoService();
            listaTipoEstado = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_CUENTA));

            setSelTipFil(1);
            selFiltro();
            filtrarPagos();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    private ProcesoBancoBean procesoBancoBean;
    private Integer selTipFil;
    private Boolean flgTipFil;
    private UsuarioBean usuarioLoginBean;
    private List<NotaAbonoRepBean> listaNotaAbonoRepBean;
    private NotaAbonoRepBean notaAbonoRepBean;
    private List<CodigoBean> listaTipoEstado;

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
    private BigDecimal montoTotalMora;
    private BigDecimal montoTotalImporte;
    private BigDecimal montoTotalPagado;

    public ProcesoBancoBean getProcesoBancoBean() {
        if (procesoBancoBean == null) {
            procesoBancoBean = new ProcesoBancoBean();
        }
        return procesoBancoBean;
    }

    public void setProcesoBancoBean(ProcesoBancoBean procesoBancoBean) {
        this.procesoBancoBean = procesoBancoBean;
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

    public UsuarioBean getUsuarioLoginBean() {
        if (usuarioLoginBean == null) {
            usuarioLoginBean = new UsuarioBean();
        }
        return usuarioLoginBean;
    }

    public void setUsuarioLoginBean(UsuarioBean usuarioLoginBean) {
        this.usuarioLoginBean = usuarioLoginBean;
    }

    public List<NotaAbonoRepBean> getListaNotaAbonoRepBean() {
        return listaNotaAbonoRepBean;
    }

    public void setListaNotaAbonoRepBean(List<NotaAbonoRepBean> listaNotaAbonoRepBean) {
        this.listaNotaAbonoRepBean = listaNotaAbonoRepBean;
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

    public List<CodigoBean> getListaTipoEstado() {
        if (listaTipoEstado == null) {
            listaTipoEstado = new ArrayList<>();
        }
        return listaTipoEstado;
    }

    public void setListaTipoEstado(List<CodigoBean> listaTipoEstado) {
        this.listaTipoEstado = listaTipoEstado;
    }

    public NotaAbonoRepBean getNotaAbonoRepBean() {
        if (notaAbonoRepBean == null) {
            notaAbonoRepBean = new NotaAbonoRepBean();
        }
        return notaAbonoRepBean;
    }

    public void setNotaAbonoRepBean(NotaAbonoRepBean notaAbonoRepBean) {
        this.notaAbonoRepBean = notaAbonoRepBean;
    }

    public BigDecimal getMontoTotalMora() {
        return montoTotalMora;
    }

    public void setMontoTotalMora(BigDecimal montoTotalMora) {
        this.montoTotalMora = montoTotalMora;
    }

    public BigDecimal getMontoTotalImporte() {
        return montoTotalImporte;
    }

    public void setMontoTotalImporte(BigDecimal montoTotalImporte) {
        this.montoTotalImporte = montoTotalImporte;
    }

    public BigDecimal getMontoTotalPagado() {
        return montoTotalPagado;
    }

    public void setMontoTotalPagado(BigDecimal montoTotalPagado) {
        this.montoTotalPagado = montoTotalPagado;
    }

    public void selFiltro() {
        try {
            if (selTipFil.equals(1)) {
                setFlgTipFil(true);
                getProcesoBancoBean().setFechaInicio(null);
                getProcesoBancoBean().setFechaFin(null);
            } else if (selTipFil.equals(0)) {
                setFlgTipFil(false);
                GregorianCalendar fechaActual = new GregorianCalendar();
                getProcesoBancoBean().setFechaInicio(fechaActual.getTime());
                getProcesoBancoBean().setFechaFin(fechaActual.getTime());
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void filtrarPagos() {
        try {
            Integer estado = 0;
            ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
            Object obj = new Object();
            if (selTipFil.equals(1)) {
                listaNotaAbonoRepBean = procesoFinalService.obtenerRepNotaAbono(procesoBancoBean);
                if (listaNotaAbonoRepBean.isEmpty()) {
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
                    montoTotalMora = obtenerImportesMoras(13, 1);
                    montoTotalImporte = obtenerImportesMoras(14, 1);
                    montoTotalPagado = obtenerImportesMoras(15, 1);
                }
            } else if (selTipFil.equals(0)) {
                if (getProcesoBancoBean().getFechaInicio() != null) {
                    getProcesoBancoBean().setFechaInicio(getProcesoBancoBean().getFechaInicio());
                    estado = 1;
                }
                if (getProcesoBancoBean().getFechaFin() != null) {
                    getProcesoBancoBean().setFechaFin(getProcesoBancoBean().getFechaFin());
                    estado = 1;
                }
                if (getProcesoBancoBean().getEstado() != null) {
                    getProcesoBancoBean().setEstado(getProcesoBancoBean().getEstado());
                    estado = 1;
                }
                if (estado == 1) {
                    listaNotaAbonoRepBean = procesoFinalService.obtenerRepNotaAbono(procesoBancoBean);
                    if (listaNotaAbonoRepBean.isEmpty()) {
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
                        montoTotalMora = obtenerImportesMoras(13, 1);
                        montoTotalImporte = obtenerImportesMoras(14, 1);
                        montoTotalPagado = obtenerImportesMoras(15, 1);
                    }
                }
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
            if (opcion.equals(1)) {
                if (!listaNotaAbonoRepBean.isEmpty()) {
                    if (mes.equals(2)) {
                        for (NotaAbonoRepBean pagos : listaNotaAbonoRepBean) {
                            m = pagos.getMontoFebrero().floatValue() + m;
                        }
                    }
                    if (mes.equals(3)) {
                        for (NotaAbonoRepBean pagos : listaNotaAbonoRepBean) {
                            m = pagos.getMontoMarzo().floatValue() + m;
                        }
                    }
                    if (mes.equals(4)) {
                        for (NotaAbonoRepBean pagos : listaNotaAbonoRepBean) {
                            m = pagos.getMontoAbril().floatValue() + m;
                        }
                    }
                    if (mes.equals(5)) {
                        for (NotaAbonoRepBean pagos : listaNotaAbonoRepBean) {
                            m = pagos.getMontoMayo().floatValue() + m;
                        }
                    }
                    if (mes.equals(6)) {
                        for (NotaAbonoRepBean pagos : listaNotaAbonoRepBean) {
                            m = pagos.getMontoJunio().floatValue() + m;
                        }
                    }
                    if (mes.equals(7)) {
                        for (NotaAbonoRepBean pagos : listaNotaAbonoRepBean) {
                            m = pagos.getMontoJulio().floatValue() + m;
                        }
                    }
                    if (mes.equals(8)) {
                        for (NotaAbonoRepBean pagos : listaNotaAbonoRepBean) {
                            m = pagos.getMontoAgosto().floatValue() + m;
                        }
                    }
                    if (mes.equals(9)) {
                        for (NotaAbonoRepBean pagos : listaNotaAbonoRepBean) {
                            m = pagos.getMontoSetiembre().floatValue() + m;
                        }
                    }
                    if (mes.equals(10)) {
                        for (NotaAbonoRepBean pagos : listaNotaAbonoRepBean) {
                            m = pagos.getMontoOctubre().floatValue() + m;
                        }
                    }
                    if (mes.equals(11)) {
                        for (NotaAbonoRepBean pagos : listaNotaAbonoRepBean) {
                            m = pagos.getMontoNoviembre().floatValue() + m;
                        }
                    }
                    if (mes.equals(12)) {
                        for (NotaAbonoRepBean pagos : listaNotaAbonoRepBean) {
                            m = pagos.getMontoDiciembre().floatValue() + m;
                        }
                    }
                    if (mes.equals(13)) {
                        for (NotaAbonoRepBean pagos : listaNotaAbonoRepBean) {
                            m = pagos.getTotalMora().floatValue() + m;
                        }
                    }
                    if (mes.equals(14)) {
                        for (NotaAbonoRepBean pagos : listaNotaAbonoRepBean) {
                            m = pagos.getSubTotal().floatValue() + m;
                        }
                    }
                    if (mes.equals(15)) {
                        for (NotaAbonoRepBean pagos : listaNotaAbonoRepBean) {
                            m = pagos.getTotal().floatValue() + m;
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

    public void limpiarPagos() {
        try {
            procesoBancoBean = new ProcesoBancoBean();
            GregorianCalendar fechaActual = new GregorianCalendar();
            procesoBancoBean.setFechaInicio(fechaActual.getTime());
            procesoBancoBean.setFechaFin(fechaActual.getTime());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

}

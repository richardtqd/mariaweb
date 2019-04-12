/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CajaGenBean;
import pe.marista.sigma.bean.CajeroCajaBean;
import pe.marista.sigma.bean.ChequeBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.DocEgresoBean;
import pe.marista.sigma.bean.FacturaCompraBean;
import pe.marista.sigma.bean.SolicitudCajaCHBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.ChequesEmitidosLPMRepBean;
import pe.marista.sigma.bean.reporte.DetDetDocEgresoRepBean;
import pe.marista.sigma.bean.reporte.DetDocEgresoRepBean;
import pe.marista.sigma.bean.reporte.DocEgresoRepBean;
import pe.marista.sigma.bean.reporte.PagosEmitidosRepBean;
import pe.marista.sigma.dao.DocEgresoDAO;
import pe.marista.sigma.dao.RegistroCompraDAO;
import pe.marista.sigma.dao.SolicitudCajaCHDAO;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.managedBean.RegistroCompraMB;
import pe.marista.sigma.managedBean.SolicitudCajaCHMB;

/**
 *
 * @author Administrador
 */
public class DocEgresoService {

    private DocEgresoDAO docEgresoDAO;
    private RegistroCompraDAO registroCompraDAO;
    private SolicitudCajaCHDAO solicitudCajaCHDAO;

    public List<DocEgresoBean> obtenerDocEgresoUniNeg(DocEgresoBean docEgresoBean) throws Exception {
        return docEgresoDAO.obtenerDocEgresoUniNeg(docEgresoBean);
    }

    public List<DocEgresoBean> obtenerDocEgresoPorFiltro(DocEgresoBean docEgresoBean) throws Exception {
        return docEgresoDAO.obtenerDocEgresoPorFiltro(docEgresoBean);
    }

    public List<DocEgresoBean> obtenerDocEgresoPorFiltroARend(DocEgresoBean docEgresoBean) throws Exception {
        return docEgresoDAO.obtenerDocEgresoPorFiltroARend(docEgresoBean);
    }

    public List<DocEgresoBean> obtenerDocEgresoPorIdDistinct(DocEgresoBean docEgresoBean) throws Exception {
        return docEgresoDAO.obtenerDocEgresoPorIdDistinct(docEgresoBean);
    }

    public List<DocEgresoBean> obtenerDocEgresoRegistroCompraDistinct(DocEgresoBean docEgresoBean) throws Exception {
        return docEgresoDAO.obtenerDocEgresoRegistroCompraDistinct(docEgresoBean);
    }

    public List<DocEgresoBean> obtenerDocEgresoChequePorFiltro(DocEgresoBean docEgresoBean) throws Exception {
//        docEgresoBean.getTipoPagoBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_MOD_PAGO);
//        docEgresoBean.getTipoPagoBean().setCodigo(MaristaConstantes.COD_CHEQUE);
        return docEgresoDAO.obtenerDocEgresoChequePorFiltro(docEgresoBean);
    }

    public DocEgresoBean obtenerDocEgresoPorId(DocEgresoBean docEgresoBean) throws Exception {
        return docEgresoDAO.obtenerDocEgresoPorId(docEgresoBean);
    }

    @Transactional
    public void insertarDocEgreso(DocEgresoBean docEgresoBean, List<FacturaCompraBean> listaDocEgresoFacturaBean, UsuarioBean usuarioLoginBean, CajeroCajaBean cajeroCajaBean, Integer origen, String impresora,
            List<SolicitudCajaCHBean> listaSolictudCajaDocEgresoBean) throws Exception {
        Integer id = 0;
        docEgresoBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
        id = docEgresoDAO.obtenerUltimoDocEgreso(docEgresoBean) + 1;
        Integer flgCheque = 0;
        Integer estActChequera = 0;
        if (origen == 0) { // REGISTRO COMPRA
            for (FacturaCompraBean detalle : listaDocEgresoFacturaBean) {
                System.out.println("nota " + detalle.getDsctoNotCred());
                System.out.println("nro nota " + detalle.getNroNotaCredito());
                DocEgresoBean docEgre = new DocEgresoBean();
                Double montoCajaDol = new Double("0.00");
                Double montoCajaSol = new Double("0.00");
                docEgre.setTipoCambioBean(docEgresoBean.getTipoCambioBean());
                CodigoService codigoService = BeanFactory.getCodigoService();
                CajaGenService cajaGenService = BeanFactory.getCajaGenService();
                CajaGenBean cajaGenBean = new CajaGenBean();
                cajaGenBean.setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                cajaGenBean.setCajaBean(cajeroCajaBean.getCajaBean());
                cajaGenBean.setUsuarioBean(usuarioLoginBean);
                Calendar miCalendario = Calendar.getInstance();
                cajaGenBean.setAnio(miCalendario.get(Calendar.YEAR));
                cajaGenBean.setFecApertura(new Date());
                cajaGenBean = cajaGenService.verificarApertura(cajaGenBean);
                montoCajaSol = cajaGenBean.getEgresoSol();
                montoCajaDol = cajaGenBean.getEgresoDol();
                CodigoBean codigo = new CodigoBean();
                CodigoBean tipoPago = new CodigoBean();
                tipoPago = codigoService.obtenerPorId(docEgresoBean.getTipoPagoBean());
                Integer estado = 0;
                switch (tipoPago.getCodigo()) {
                    case "Efectivo":
                        codigo = codigoService.obtenerPorId(docEgresoBean.getTipoMonedaBean());
                        switch (codigo.getCodigo()) {
                            case "Soles":
                                if ((cajaGenBean.getEgresoSol() + detalle.getMontoPago()) <= cajaGenBean.getIngresoSol()) {
                                    if (montoCajaSol == null) {
                                        montoCajaSol = new Double("0.00");
                                        montoCajaSol = montoCajaSol + detalle.getMontoPago();
                                        estado = 1;
                                        break;
                                    } else {
                                        montoCajaSol = montoCajaSol + detalle.getMontoPago();
                                        estado = 1;
                                    }
                                    break;
                                } else {
                                    RequestContext.getCurrentInstance().addCallbackParam("montoApagarSuperior", true);
                                    estado = 0;
                                    break;
                                }
                            case "Dolares":
                                if ((cajaGenBean.getEgresoDol() + detalle.getMontoPago()) <= cajaGenBean.getIngresoDol()) {
                                    if (montoCajaDol == null) {
                                        montoCajaDol = new Double("0.00");
                                        montoCajaDol = montoCajaDol + detalle.getMontoPago();
                                        estado = 1;
                                    } else {
                                        montoCajaDol = montoCajaDol + detalle.getMontoPago();
                                        estado = 1;
                                    }
                                    break;
                                } else {
                                    RequestContext.getCurrentInstance().addCallbackParam("montoApagarSuperior", true);
                                    estado = 0;
                                    break;
                                }
                        }
                    case MaristaConstantes.COD_CHEQUE:
//                        docEgre.setNumCheque(docEgresoBean.getNumCheque());
                        estado = 2;
                        flgCheque = 1;
                        break;
                    case MaristaConstantes.COD_CARTA_ORD:
                        estado = 2;
                        break;
                    case MaristaConstantes.COD_TRANSFERENCIA:
                        estado = 2;
                        break;
                }
                if (detalle.getDsctoNotCred() == null) {
                    detalle.setDsctoNotCred(0.0);
                }
                Double mont = detalle.getMontoPago() - detalle.getDsctoNotCred();
                mont = (double) Math.round(mont * 100) / 100;
                if (estado == 1) {
                    //seteando los valores adelanto o reg compra
                    cajaGenBean.setEgresoSol(montoCajaSol);
                    cajaGenBean.setEgresoDol(montoCajaDol);
                    docEgre.setCreaPor(usuarioLoginBean.getUsuario());
                    docEgre.getCajeroCajaBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                    docEgre.setCajaGenBean(cajaGenBean);
                    docEgre.setCajeroCajaBean(cajeroCajaBean);
                    docEgre.setTipoPagoBean(docEgresoBean.getTipoPagoBean());
//                                        docEgre.setMontoPagado(BigDecimal.valueOf(detalle.getMontoPago()));
                    docEgre.setMontoPagado(BigDecimal.valueOf(mont));
                    docEgre.setGlosa(detalle.getGlosaCompra());

                    docEgre.setDsctoNotCred(detalle.getDsctoNotCred());
                    docEgre.setNroNotaCredito(detalle.getNroNotaCredito());

                    docEgre.setDetraccionBean(detalle.getDetraccionBean());
                    docEgre.setCuentaBancoBean(docEgresoBean.getCuentaBancoBean());
                    StringBuilder sb = new StringBuilder();
                    if (detalle.getSerieDoc() != null) {
                        sb.append(detalle.getSerieDoc()).append("-");
                    }
                    if (detalle.getNroDoc() != null) {
                        sb.append(detalle.getNroDoc());
                    }
                    docEgre.setNroDocRef(sb.toString());
//                    docEgre.setRegistroCompraBean(detalle.getRegistroCompraBean());
                    docEgre.getFacturaCompraBean().getRegistroCompraBean().getEntidadBean().setRuc(detalle.getRucCompra());
                    docEgre.getFacturaCompraBean().setIdFacturaCompra(detalle.getIdFacturaCompra());
                    docEgre.getFacturaCompraBean().getRegistroCompraBean().setGlosa(detalle.getGlosaCompra());
                    docEgre.setIdTipoDoc(0);
                    if (detalle.getIdFacturaCompra() != null) {
                        docEgre.setIdTipoDocEgreso("F");
                    } else if (detalle.getRegistroCompraBean().getIdRegistroCompra() != null) {
                        docEgre.setIdTipoDocEgreso("R");
                    } else {
                        docEgre.setIdTipoDocEgreso("O");
                    }
                    cajaGenService.modificarEgresoSolYDol(cajaGenBean);
                }
                if (estado == 2) {
                    docEgre.setCreaPor(usuarioLoginBean.getUsuario());
                    docEgre.getCajeroCajaBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                    docEgre.setCajaGenBean(cajaGenBean);
                    docEgre.setCajeroCajaBean(cajeroCajaBean);
                    docEgre.setTipoPagoBean(docEgresoBean.getTipoPagoBean());
//                    docEgre.setMontoPagado(BigDecimal.valueOf(detalle.getMontoPago()));
                    docEgre.setMontoPagado(BigDecimal.valueOf(mont));
                    docEgre.setGlosa(detalle.getGlosa());
                    docEgre.setDsctoNotCred(detalle.getDsctoNotCred());
                    docEgre.setNroNotaCredito(detalle.getNroNotaCredito());
                    docEgre.setDetraccionBean(detalle.getDetraccionBean());
                    docEgre.setCuentaBancoBean(docEgresoBean.getCuentaBancoBean());
                    docEgre.setNumCheque(docEgresoBean.getNumCheque());
                    if (detalle.getTipoMonedaBean().getCodigo().equals(MaristaConstantes.COD_Dolares)) {
                        docEgre.getTipoMonedaBean().setIdCodigo(MaristaConstantes.COD_DOLARES);
                    } else {
                        docEgre.getTipoMonedaBean().setIdCodigo(MaristaConstantes.COD_SOLES);
                    }
                    StringBuilder sb = new StringBuilder();
                    if (detalle.getSerieDoc() != null) {
                        sb.append(detalle.getSerieDoc()).append("-");
                    }
                    if (detalle.getNroDoc() != null) {
                        sb.append(detalle.getNroDoc());
                    }
                    docEgre.setNroDocRef(sb.toString());
                    docEgre.getFacturaCompraBean().getRegistroCompraBean().getEntidadBean().setRuc(detalle.getRucCompra());
                    docEgre.getFacturaCompraBean().setIdFacturaCompra(detalle.getIdFacturaCompra());
                    docEgre.getFacturaCompraBean().getRegistroCompraBean().setGlosa(detalle.getGlosaCompra());
                    docEgre.setIdTipoDoc(0);
                    if (detalle.getIdFacturaCompra() != null) {
                        docEgre.setIdTipoDocEgreso("F");
                    } else if (detalle.getRegistroCompraBean().getIdRegistroCompra() != null) {
                        docEgre.setIdTipoDocEgreso("R");
                    } else {
                        docEgre.setIdTipoDocEgreso("O");
                    }
                }
                docEgre.setNroDocEgreso(id);
                docEgresoDAO.insertarDocEgreso(docEgre);
                FacturaCompraBean factura = new FacturaCompraBean();
                factura.setIdFacturaCompra(detalle.getIdFacturaCompra());
                factura.getTipoStatusFacturaBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_FACT_COM);
                factura.getTipoStatusFacturaBean().setCodigo(MaristaConstantes.COD_SOL_PAGADA);
                factura.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                factura.setGlosa(detalle.getGlosa());
                factura.setDsctoNotCred(detalle.getDsctoNotCred());
                factura.setNroNotaCredito(detalle.getNroNotaCredito());
                System.out.println(detalle.getGlosa());
                registroCompraDAO.cambiarEstadoPagadoFacturaCompra(factura);
                registroCompraDAO.modificarGlosa(factura);
//                registroCompraDAO.modificarCuentaFactDsctoNotaCred(detalle.getDsctoNotCred(),
//                        detalle.getIdFacturaCompra(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

                RegistroCompraMB registroCompraMB = (RegistroCompraMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("registroCompraMB");
                registroCompraMB.distribuirCuandoHayNotaC(detalle, mont);
                docEgresoBean.setNroDocEgreso(id);
                ///asiento
                ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
                procesoFinalService.execProAsiento(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), MaristaConstantes.OBJ_SOL_FACT_COMPRA, factura.getIdFacturaCompra(), usuarioLoginBean.getUsuario(), 0);
                //fin asiento
            }
            if (flgCheque.equals(1)) {
                if (estActChequera.equals(0)) {
                    estActChequera = 1;
                    ChequeService chequeService = BeanFactory.getChequeService();
                    ChequeBean cheque = new ChequeBean();
                    cheque = chequeService.obtenerUltimoChequeMasUno(docEgresoBean.getTipoMonedaBean().getIdCodigo(), impresora, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    cheque.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                    chequeService.aumentarSecuenciaCheque(cheque);
                }
            }
        } else {
            for (SolicitudCajaCHBean detalle : listaSolictudCajaDocEgresoBean) {

                System.out.println("sol->" + detalle.getMotivo());
                Double montoCajaDol = new Double("0.00");
                Double montoCajaSol = new Double("0.00");
                CodigoService codigoService = BeanFactory.getCodigoService();
                CajaGenService cajaGenService = BeanFactory.getCajaGenService();
                CajaGenBean cajaGenBean = new CajaGenBean();
                cajaGenBean.setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                cajaGenBean.setCajaBean(cajeroCajaBean.getCajaBean());
                cajaGenBean.setUsuarioBean(usuarioLoginBean);
                Calendar miCalendario = Calendar.getInstance();
                cajaGenBean.setAnio(miCalendario.get(Calendar.YEAR));
                cajaGenBean.setFecApertura(new Date());
                cajaGenBean = cajaGenService.verificarApertura(cajaGenBean);
                montoCajaSol = cajaGenBean.getEgresoSol();
                montoCajaDol = cajaGenBean.getEgresoDol();
                CodigoBean codigo = new CodigoBean();
                CodigoBean tipoPago = new CodigoBean();
                tipoPago = codigoService.obtenerPorId(docEgresoBean.getTipoPagoBean());
                Integer estado = 0; //0=NO INSERTA, 1= INSERTA EFECTIVO, 2=INSERTA OTRO TIPO DOC
                switch (tipoPago.getCodigo()) {
                    case "Efectivo":
                        codigo = codigoService.obtenerPorId(docEgresoBean.getTipoMonedaBean());
                        switch (codigo.getCodigo()) {
                            case "Soles":
                                if ((cajaGenBean.getEgresoSol() + detalle.getMontoPagado().doubleValue()) <= cajaGenBean.getIngresoSol()) {
                                    if (montoCajaSol == null) {
                                        montoCajaSol = new Double("0.00");
                                        montoCajaSol = montoCajaSol + detalle.getMontoPagado().doubleValue();
                                        estado = 1;
                                        break;
                                    } else {
                                        montoCajaSol = montoCajaSol + detalle.getMontoPagado().doubleValue();
                                        estado = 1;
                                    }
                                    break;
                                } else {
                                    RequestContext.getCurrentInstance().addCallbackParam("montoApagarSuperior", true);
                                    estado = 0;
                                    break;
                                }
                            case "Dolares":
                                if ((cajaGenBean.getEgresoDol() + detalle.getMontoPagado().doubleValue()) <= cajaGenBean.getIngresoDol()) {
                                    if (montoCajaDol == null) {
                                        montoCajaDol = new Double("0.00");
                                        montoCajaDol = montoCajaDol + detalle.getMontoPagado().doubleValue();
                                        estado = 1;
                                    } else {
                                        montoCajaDol = montoCajaDol + detalle.getMontoPagado().doubleValue();
                                        estado = 1;
                                    }
                                    break;
                                } else {
                                    RequestContext.getCurrentInstance().addCallbackParam("montoApagarSuperior", true);
                                    estado = 0;
                                    break;
                                }
                        }
                    case MaristaConstantes.COD_CHEQUE:
                        estado = 2;
                        flgCheque = 1;
                        break;
                    case MaristaConstantes.COD_CARTA_ORD:
                        estado = 2;
                        break;
                    case MaristaConstantes.COD_TRANSFERENCIA:
                        estado = 2;
                        break;
                }
                if (detalle.getDsctoNotCred() == null) {
                    detalle.setDsctoNotCred(0.0);
                }
//                Double mont = detalle.getMontoPagado() - detalle.getDsctoNotCred();
//                mont = (double) Math.round(mont * 100) / 100;
                if (estado == 1) {
                    cajaGenBean.setEgresoSol(montoCajaSol);
                    cajaGenBean.setEgresoDol(montoCajaDol);
                    docEgresoBean.setCreaPor(usuarioLoginBean.getUsuario());
                    docEgresoBean.getCajeroCajaBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                    docEgresoBean.setCajaGenBean(cajaGenBean);
                    docEgresoBean.setCajeroCajaBean(cajeroCajaBean);
                    docEgresoBean.setCuentaBancoBean(docEgresoBean.getCuentaBancoBean());
                    docEgresoBean.setIdTipoDoc(1);
                    docEgresoBean.setIdTipoDocEgreso("A");
                    docEgresoBean.setDetraccionBean(detalle.getDetraccionBean());
                    docEgresoBean.setGarantia(detalle.getGarantia());
                    docEgresoBean.setDsctoNotCred(detalle.getDsctoNotCred());
                    docEgresoBean.setNroNotaCredito(detalle.getNroNotaCredito());
                    cajaGenService.modificarEgresoSolYDol(cajaGenBean);
                }
                if (estado == 2) {
                    docEgresoBean.setCreaPor(usuarioLoginBean.getUsuario());
                    docEgresoBean.getCajeroCajaBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                    docEgresoBean.setCajaGenBean(cajaGenBean);
                    docEgresoBean.setCajeroCajaBean(cajeroCajaBean);
                    docEgresoBean.setCuentaBancoBean(docEgresoBean.getCuentaBancoBean());
                    docEgresoBean.setIdTipoDoc(1);
                    docEgresoBean.setIdTipoDocEgreso("A");
                    docEgresoBean.setNumCheque(docEgresoBean.getNumCheque());
                    docEgresoBean.setGlosa(detalle.getGlosaDoc());
                    docEgresoBean.setNroDocRef(detalle.getDocRefAyuda());
                    docEgresoBean.setSerie(detalle.getSerie());
                    docEgresoBean.setTipoDocBean(detalle.getTipoDocBean());
                    docEgresoBean.setDetraccionBean(detalle.getDetraccionBean());
                    docEgresoBean.setGarantia(detalle.getGarantia());
//                    docEgresoBean.setMontoPagado(new BigDecimal(detalle.getMontoPagado()));
                    docEgresoBean.setMontoPagado(BigDecimal.valueOf((detalle.getMontoPagado())));
//                    docEgresoBean.setSolicitudCajaCHBean(detalle);
                    docEgresoBean.getSolicitudCajaCHBean().setIdSolicitudCajaCh(detalle.getIdSolCaja());
                    docEgresoBean.getSolicitudCajaCHBean().setIdRespCheque(detalle.getIdRespCheque());
                    docEgresoBean.getSolicitudCajaCHBean().setMotivo(detalle.getGlosaDoc());
                    docEgresoBean.setDsctoNotCred(detalle.getDsctoNotCred());
                    docEgresoBean.setNroNotaCredito(detalle.getNroNotaCredito());
                    codigo = codigoService.obtenerPorId(docEgresoBean.getTipoMonedaBean());
                    switch (codigo.getCodigo()) {
                        case "Soles":
                        case "Dolares":
                    }
                }
                docEgresoBean.setNroDocEgreso(id);
                System.out.println("sol1->" + detalle.getGlosaDoc());
                System.out.println("docE->" + docEgresoBean.getSolicitudCajaCHBean().getMotivo());
                docEgresoDAO.insertarDocEgreso(docEgresoBean);
                docEgresoBean.setNroDocEgreso(id);
                docEgresoBean.getSolicitudCajaCHBean().setIdSolicitudCajaCh(detalle.getIdSolCaja());
                docEgresoBean.getSolicitudCajaCHBean().getTipoStatusSolCajaChBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STATUS_SOL);
                docEgresoBean.getSolicitudCajaCHBean().getTipoStatusSolCajaChBean().setCodigo(MaristaConstantes.COD_SOL_PAGADO);
                docEgresoBean.getSolicitudCajaCHBean().setMotivo(detalle.getGlosaDoc());
                docEgresoBean.getSolicitudCajaCHBean().setDsctoNotCred(detalle.getDsctoNotCred());
                docEgresoBean.getSolicitudCajaCHBean().setNroNotaCredito(detalle.getNroNotaCredito());
                solicitudCajaCHDAO.cambiarEstadoPagadoSolicitudCCh(docEgresoBean.getSolicitudCajaCHBean());
                solicitudCajaCHDAO.modificarMotivo(docEgresoBean.getSolicitudCajaCHBean());
//////
                SolicitudCajaCHMB solicitudCajaCHMB = (SolicitudCajaCHMB) FacesContext.getCurrentInstance().getViewRoot().getViewMap().get("solicitudCajaCHMB");
                solicitudCajaCHMB.distribuirCuandoHayNotaC(detalle, detalle.getMontoPagado());

                ///asiento
                CodigoBean cod = new CodigoBean();
                cod = codigoService.obtenerPorCodigoDisCR(docEgresoBean.getSolicitudCajaCHBean().getIdSolicitudCajaCh(), usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                if (cod != null) {
                    if (cod.getIdCodigo() != null) {
                        docEgresoBean.getSolicitudCajaCHBean().setCodDistribucion(cod.getIdCodigo());
                    } else {
                        detalle.setFlgTieneCr(true);
                        System.out.println("null xds");
                    }

                } else {
                    detalle.setFlgTieneCr(true);
                }
                if (detalle.getFlgTieneCr() == false) {
                    ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
                    procesoFinalService.execProAsiento(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), MaristaConstantes.OBJ_SOL_CAJACH, docEgresoBean.getSolicitudCajaCHBean().getIdSolicitudCajaCh(), usuarioLoginBean.getUsuario(), 0);
                } else {
                    System.out.println("No tiene Cr :D");
                }
                //fin asiento

                if (flgCheque.equals(1)) {
                    if (estActChequera.equals(0)) {
                        estActChequera = 1;
                        ChequeService chequeService = BeanFactory.getChequeService();
                        ChequeBean cheque = new ChequeBean();
                        if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOC)) {
                            cheque = chequeService.obtenerUltimoChequeCorrelativo(docEgresoBean.getTipoMonedaBean().getIdCodigo(), impresora, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            cheque.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                        } else {
                            cheque = chequeService.obtenerUltimoChequeMasUno(docEgresoBean.getTipoMonedaBean().getIdCodigo(), impresora, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            cheque.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                        }
                        chequeService.aumentarSecuenciaCheque(cheque);
                    }
                }
            }
        }
    }

    @Transactional
    public void insertarDocEgresoBarina(DocEgresoBean docEgresoBean, List<FacturaCompraBean> listaDocEgresoFacturaBean, UsuarioBean usuarioLoginBean, CajeroCajaBean cajeroCajaBean, Integer origen, String impresora,
            List<SolicitudCajaCHBean> listaSolictudCajaDocEgresoBean) throws Exception {
        Integer id = 0;
        docEgresoBean.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
        id = docEgresoDAO.obtenerUltimoDocEgreso(docEgresoBean) + 1;
        Integer flgCheque = 0;
        Integer estActChequera = 0;
        if (origen == 1) { // SOLICITUD 
            for (SolicitudCajaCHBean detalle : listaSolictudCajaDocEgresoBean) {

                System.out.println("sol->" + detalle.getMotivo());
                Double montoCajaDol = new Double("0.00");
                Double montoCajaSol = new Double("0.00");
                CodigoService codigoService = BeanFactory.getCodigoService();
                CajaGenService cajaGenService = BeanFactory.getCajaGenService();
                CajaGenBean cajaGenBean = new CajaGenBean();
                cajaGenBean.setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                cajaGenBean.setCajaBean(cajeroCajaBean.getCajaBean());
                cajaGenBean.setUsuarioBean(usuarioLoginBean);
                Calendar miCalendario = Calendar.getInstance();
                cajaGenBean.setAnio(miCalendario.get(Calendar.YEAR));
                cajaGenBean.setFecApertura(new Date());
                cajaGenBean = cajaGenService.verificarApertura(cajaGenBean);
                montoCajaSol = cajaGenBean.getEgresoSol();
                montoCajaDol = cajaGenBean.getEgresoDol();
                CodigoBean codigo = new CodigoBean();
                CodigoBean tipoPago = new CodigoBean();
                tipoPago = codigoService.obtenerPorId(docEgresoBean.getTipoPagoBean());
                Integer estado = 0; //0=NO INSERTA, 1= INSERTA EFECTIVO, 2=INSERTA OTRO TIPO DOC
                switch (tipoPago.getCodigo()) {
                    case "Efectivo":
                        codigo = codigoService.obtenerPorId(docEgresoBean.getTipoMonedaBean());
                        switch (codigo.getCodigo()) {
                            case "Soles":
                                if ((cajaGenBean.getEgresoSol() + detalle.getMontoPagado().doubleValue()) <= cajaGenBean.getIngresoSol()) {
                                    if (montoCajaSol == null) {
                                        montoCajaSol = new Double("0.00");
                                        montoCajaSol = montoCajaSol + detalle.getMontoPagado().doubleValue();
                                        estado = 1;
                                        break;
                                    } else {
                                        montoCajaSol = montoCajaSol + detalle.getMontoPagado().doubleValue();
                                        estado = 1;
                                    }
                                    break;
                                } else {
                                    RequestContext.getCurrentInstance().addCallbackParam("montoApagarSuperior", true);
                                    estado = 0;
                                    break;
                                }
                            case "Dolares":
                                if ((cajaGenBean.getEgresoDol() + detalle.getMontoPagado().doubleValue()) <= cajaGenBean.getIngresoDol()) {
                                    if (montoCajaDol == null) {
                                        montoCajaDol = new Double("0.00");
                                        montoCajaDol = montoCajaDol + detalle.getMontoPagado().doubleValue();
                                        estado = 1;
                                    } else {
                                        montoCajaDol = montoCajaDol + detalle.getMontoPagado().doubleValue();
                                        estado = 1;
                                    }
                                    break;
                                } else {
                                    RequestContext.getCurrentInstance().addCallbackParam("montoApagarSuperior", true);
                                    estado = 0;
                                    break;
                                }
                        }
                    case MaristaConstantes.COD_CHEQUE:
                        estado = 2;
                        flgCheque = 1;
                        break;
                    case MaristaConstantes.COD_CARTA_ORD:
                        estado = 2;
                        break;
                    case MaristaConstantes.COD_TRANSFERENCIA:
                        estado = 2;
                        break;
                }
                if (estado == 1) {
                    cajaGenBean.setEgresoSol(montoCajaSol);
                    cajaGenBean.setEgresoDol(montoCajaDol);
                    docEgresoBean.setCreaPor(usuarioLoginBean.getUsuario());
                    docEgresoBean.getCajeroCajaBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                    docEgresoBean.setCajaGenBean(cajaGenBean);
                    docEgresoBean.setCajeroCajaBean(cajeroCajaBean);
                    docEgresoBean.setCuentaBancoBean(docEgresoBean.getCuentaBancoBean());
                    docEgresoBean.setIdTipoDoc(1);
                    docEgresoBean.setIdTipoDocEgreso("A");
                    docEgresoBean.setDetraccionBean(detalle.getDetraccionBean());
                    docEgresoBean.setGarantia(detalle.getGarantia());
                    cajaGenService.modificarEgresoSolYDol(cajaGenBean);
                }
                if (estado == 2) {
                    docEgresoBean.setCreaPor(usuarioLoginBean.getUsuario());
                    docEgresoBean.getCajeroCajaBean().setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                    docEgresoBean.setCajaGenBean(cajaGenBean);
                    docEgresoBean.setCajeroCajaBean(cajeroCajaBean);
                    docEgresoBean.setCuentaBancoBean(docEgresoBean.getCuentaBancoBean());
                    docEgresoBean.setIdTipoDoc(1);
                    docEgresoBean.setIdTipoDocEgreso("A");
                    docEgresoBean.setNumCheque(docEgresoBean.getNumCheque());
                    docEgresoBean.setGlosa(detalle.getGlosaDoc());
                    docEgresoBean.setNroDocRef(detalle.getDocRefAyuda());
                    docEgresoBean.setSerie(detalle.getSerie());
                    docEgresoBean.setTipoDocBean(detalle.getTipoDocBean());
                    docEgresoBean.setDetraccionBean(detalle.getDetraccionBean());
                    docEgresoBean.setGarantia(detalle.getGarantia());
                    docEgresoBean.setMontoPagado(BigDecimal.valueOf((detalle.getMontoPagado())));
                    docEgresoBean.getSolicitudCajaCHBean().setIdSolicitudCajaCh(detalle.getIdSolCaja());
                    docEgresoBean.getSolicitudCajaCHBean().setIdRespCheque(detalle.getIdRespCheque());
                    docEgresoBean.getSolicitudCajaCHBean().setMotivo(detalle.getGlosaDoc());
                    codigo = codigoService.obtenerPorId(docEgresoBean.getTipoMonedaBean());
                    switch (codigo.getCodigo()) {
                        case "Soles":
                        case "Dolares":
                    }
                }
                docEgresoBean.setNroDocEgreso(id);
                System.out.println("sol1->" + detalle.getGlosaDoc());
                System.out.println("docE->" + docEgresoBean.getSolicitudCajaCHBean().getMotivo());
                docEgresoDAO.insertarDocEgreso(docEgresoBean);
                docEgresoBean.setNroDocEgreso(id);
                docEgresoBean.getSolicitudCajaCHBean().setIdSolicitudCajaCh(detalle.getIdSolCaja());
                docEgresoBean.getSolicitudCajaCHBean().getTipoStatusSolCajaChBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STATUS_SOL);
                docEgresoBean.getSolicitudCajaCHBean().getTipoStatusSolCajaChBean().setCodigo(MaristaConstantes.COD_SOL_PAGADO);
                docEgresoBean.getSolicitudCajaCHBean().setMotivo(detalle.getGlosaDoc());
                solicitudCajaCHDAO.cambiarEstadoPagadoSolicitudCCh(docEgresoBean.getSolicitudCajaCHBean());
                solicitudCajaCHDAO.modificarMotivo(docEgresoBean.getSolicitudCajaCHBean());

                ///asiento
                if (detalle.getFlgTieneCr() == false) {
                    ProcesoFinalService procesoFinalService = BeanFactory.getProcesoFinalService();
                    procesoFinalService.execProAsiento(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), MaristaConstantes.OBJ_SOL_CAJACH, docEgresoBean.getSolicitudCajaCHBean().getIdSolicitudCajaCh(), usuarioLoginBean.getUsuario(), 0);
                } else {
                    System.out.println("No tiene Cr :D");
                }
                //fin asiento

                if (flgCheque.equals(1)) {
                    if (estActChequera.equals(0)) {
                        estActChequera = 1;
                        ChequeService chequeService = BeanFactory.getChequeService();
                        ChequeBean cheque = new ChequeBean();
                        if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOC)) {
                            cheque = chequeService.obtenerUltimoChequeCorrelativo(docEgresoBean.getTipoMonedaBean().getIdCodigo(), impresora, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            cheque.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                        } else {
                            cheque = chequeService.obtenerUltimoChequeMasUno(docEgresoBean.getTipoMonedaBean().getIdCodigo(), impresora, usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            cheque.setUnidadNegocioBean(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean());
                        }
                        chequeService.aumentarSecuenciaCheque(cheque);
                    }
                }
            }
        }
    }

    @Transactional
    public void modificarDocEgreso(DocEgresoBean docEgresoBean) throws Exception {
        docEgresoDAO.modificarDocEgreso(docEgresoBean);
    }

    @Transactional
    public void flgAnuladoDocEgreso(DocEgresoBean docEgresoBean) throws Exception {
        docEgresoDAO.flgAnuladoDocEgreso(docEgresoBean);
    }

    @Transactional
    public void cambiarEstadoRendicionDoc(DocEgresoBean docEgresoBean) throws Exception {
        docEgresoDAO.cambiarEstadoRendicionDoc(docEgresoBean);
    }

    @Transactional
    public void eliminarDocEgreso(DocEgresoBean docEgresoBean, CajaGenBean cajaGenBean) throws Exception {
//        docEgresoDAO.eliminarDocEgreso(docEgresoBean);
        CajaGenService cajaGenService = BeanFactory.getCajaGenService();
        docEgresoBean.getFacturaCompraBean().getRegistroCompraBean().getTipoStatusRegCBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_REGISTRO);
        docEgresoBean.getFacturaCompraBean().getRegistroCompraBean().getTipoStatusRegCBean().setCodigo(MaristaConstantes.COD_SOL_AUTORIZADO);
        registroCompraDAO.cambiarEstadoPagadoSolicitudRC(docEgresoBean.getFacturaCompraBean().getRegistroCompraBean());
        cajaGenService.modificarEgresoSolYDol(cajaGenBean);
    }

    public Integer obtenerUltimoDocEgreso(DocEgresoBean docEgresoBean) throws Exception {
        return docEgresoDAO.obtenerUltimoDocEgreso(docEgresoBean);
    }

    public String convertirAletras(BigDecimal monto) throws Exception {
        return docEgresoDAO.convertirAletras(monto);
    }

    public List<DocEgresoBean> obtenerDocEgresoPorFiltroDistinct(DocEgresoBean docEgresoBean) throws Exception {
        return docEgresoDAO.obtenerDocEgresoPorFiltroDistinct(docEgresoBean);
    }

    public List<DocEgresoBean> obtenerPorNroDocEgreso(DocEgresoBean docEgresoBean) throws Exception {
        return docEgresoDAO.obtenerPorNroDocEgreso(docEgresoBean);
    }

    public List<DocEgresoRepBean> obtenerDocEgreso(DocEgresoBean docEgresoBean) throws Exception {
        return docEgresoDAO.obtenerDocEgreso(docEgresoBean);
    }

    public List<PagosEmitidosRepBean> obtenerFiltrosPagosEmitidosRep(PagosEmitidosRepBean pagosEmitidosRepBean) throws Exception {
        return docEgresoDAO.obtenerFiltrosPagosEmitidosRep(pagosEmitidosRepBean);
    }

    public List<DetDocEgresoRepBean> obtenerDetalleDocEgreso(Integer nroDocEgreso, String uniNeg) throws Exception {
        return docEgresoDAO.obtenerDetalleDocEgreso(nroDocEgreso, uniNeg);
    }

    public List<DetDocEgresoRepBean> obtenerDetalleFacturaDocEgreso(Integer nroDocEgreso, String uniNeg) throws Exception {
        return docEgresoDAO.obtenerDetalleFacturaDocEgreso(nroDocEgreso, uniNeg);
    }

    public List<DocEgresoRepBean> obtenerFiltroDocEgresoCheque(DocEgresoRepBean docEgresoRepBean) throws Exception {
        return docEgresoDAO.obtenerFiltroDocEgresoCheque(docEgresoRepBean);
    }

    public List<DetDetDocEgresoRepBean> obtenerDetalleDetalleDocEgreso(Integer nroDocEgreso, String uniNeg, Integer id, Integer detra, Integer gara) throws Exception {
        return docEgresoDAO.obtenerDetalleDetalleDocEgreso(nroDocEgreso, uniNeg, id, detra, gara);
    }

    public List<DetDetDocEgresoRepBean> obtenerDetalleDetalleFacturaDocEgreso(Integer nroDocEgreso, String uniNeg, Integer id, Integer detra, String cuenta) throws Exception {
        return docEgresoDAO.obtenerDetalleDetalleFacturaDocEgreso(nroDocEgreso, uniNeg, id, detra, cuenta);
    }

    public List<DocEgresoBean> obtenerPorIdPersonal(DocEgresoBean docEgresoBean) throws Exception {
        return docEgresoDAO.obtenerPorIdPersonal(docEgresoBean);
    }

    public String obtenerActualMas1PorTipoPago(String tipoPago, String uniNeg) throws Exception {
        return docEgresoDAO.obtenerActualMas1PorTipoPago(tipoPago, uniNeg);
    }

    // 
    public DocEgresoDAO getDocEgresoDAO() {
        return docEgresoDAO;
    }

    public void setDocEgresoDAO(DocEgresoDAO docEgresoDAO) {
        this.docEgresoDAO = docEgresoDAO;
    }

    public RegistroCompraDAO getRegistroCompraDAO() {
        return registroCompraDAO;
    }

    public void setRegistroCompraDAO(RegistroCompraDAO registroCompraDAO) {
        this.registroCompraDAO = registroCompraDAO;
    }

    public SolicitudCajaCHDAO getSolicitudCajaCHDAO() {
        return solicitudCajaCHDAO;
    }

    public void setSolicitudCajaCHDAO(SolicitudCajaCHDAO solicitudCajaCHDAO) {
        this.solicitudCajaCHDAO = solicitudCajaCHDAO;
    }

    public List<DocEgresoBean> obtenerDocEgresoPorFiltroDistinctNoAnulados(DocEgresoBean docEgresoBean) throws Exception {
        return docEgresoDAO.obtenerDocEgresoPorFiltroDistinctNoAnulados(docEgresoBean);
    }

    public DocEgresoBean obtenerDocEgresoCheque(DocEgresoBean docEgresoBean) throws Exception {
        return docEgresoDAO.obtenerDocEgresoCheque(docEgresoBean);
    }

    public List<DocEgresoBean> obtenerFacturaPorNumCheq(DocEgresoBean docEgresoBean) throws Exception {
        return docEgresoDAO.obtenerFacturaPorNumCheq(docEgresoBean);
    }

    public void modificarFechaDocEgreso(DocEgresoBean docEgresoBean) throws Exception {
        docEgresoDAO.modificarFechaDocEgreso(docEgresoBean);
    }

    public List<DocEgresoRepBean> obtenerDocEgreso2(DocEgresoBean docEgresoBean) throws Exception {
        return docEgresoDAO.obtenerDocEgreso2(docEgresoBean);
    }

    public DocEgresoBean obtenerDocEgresoPorCheque(DocEgresoBean docEgresoBean) throws Exception {
        return docEgresoDAO.obtenerDocEgresoPorCheque(docEgresoBean);
    }

    public List<DocEgresoRepBean> obtenerFiltroDocEgresoChequeSolesLPM(DocEgresoRepBean DocEgresoRepBean) throws Exception {
        return docEgresoDAO.obtenerFiltroDocEgresoChequeSolesLPM(DocEgresoRepBean);
    }

    public List<DocEgresoRepBean> obtenerFiltroDocEgresoChequeDolesLPM(DocEgresoRepBean DocEgresoRepBean) throws Exception {
        return docEgresoDAO.obtenerFiltroDocEgresoChequeDolesLPM(DocEgresoRepBean);
    }

    public List<DocEgresoBean> obtenerListaDocEgresoDetalle(DocEgresoBean docEgresoBean) throws Exception {
        return docEgresoDAO.obtenerListaDocEgresoDetalle(docEgresoBean);
    }

    public List<ChequesEmitidosLPMRepBean> obtenerChequesLPMCabecera(String uniNeg, Integer anio, Integer mes, Integer tipoMoneda, Integer tipoModoPago) throws Exception {
        return docEgresoDAO.obtenerChequesLPMCabecera(uniNeg, anio, mes, tipoMoneda, tipoModoPago);
    }

    public List<ChequesEmitidosLPMRepBean> obtenerChequesLPMDetalle(String uniNeg, Integer anio, Integer mes, String numCheque) throws Exception {
        return docEgresoDAO.obtenerChequesLPMDetalle(uniNeg, anio, mes, numCheque);
    }

    public List<ChequesEmitidosLPMRepBean> obtenerChequesLPMSubDetalle(String uniNeg, Integer anio, Integer mes, String numCheque) throws Exception {
        return docEgresoDAO.obtenerChequesLPMSubDetalle(uniNeg, anio, mes, numCheque);
    }

}

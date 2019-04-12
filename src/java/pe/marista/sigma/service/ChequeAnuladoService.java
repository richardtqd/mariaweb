/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.AsientoBean;
import pe.marista.sigma.bean.ChequeAnuladoBean;
import pe.marista.sigma.bean.DocEgresoBean;
import pe.marista.sigma.bean.FacturaCompraBean;
import pe.marista.sigma.bean.SolicitudCajaCHBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.dao.AsientoDAO;
import pe.marista.sigma.dao.ChequeAnuladoDAO;
import pe.marista.sigma.dao.DocEgresoDAO;
import pe.marista.sigma.dao.RegistroCompraDAO;
import pe.marista.sigma.factory.BeanFactory;

/**
 *
 * @author MS002
 */
public class ChequeAnuladoService {

    private ChequeAnuladoDAO chequeAnuladoDAO;
    private DocEgresoDAO docEgresoDAO;
    private RegistroCompraDAO registroCompraDAO;

    @Transactional
    public void insertarChequeAnulado(ChequeAnuladoBean chequeAnuladoBean, UsuarioBean usuarioBean, List<DocEgresoBean> listaDocEgresoBean, DocEgresoBean docEgreso) throws Exception {
        chequeAnuladoDAO.insertarChequeAnulado(chequeAnuladoBean);
//        docEgresoDAO.eliminarDocEgreso(chequeAnuladoBean.getDocEgresoBean());
//        docEgresoDAO.eliminarDocEgreso(chequeAnuladoBean.getDocEgresoBean());
        docEgresoDAO.flgAnuladoDocEgreso(chequeAnuladoBean.getDocEgresoBean());
        if (chequeAnuladoBean.getDocEgresoBean().getIdTipoDocEgreso().equals("F") || chequeAnuladoBean.getDocEgresoBean().getIdTipoDocEgreso().equals("O")) {
//            FacturaCompraBean factura = new FacturaCompraBean();
            RegistroCompraService facturaService = BeanFactory.getRegistroCompraService();
            DocEgresoService doc = BeanFactory.getDocEgresoService();
            List<FacturaCompraBean> factu = new ArrayList<>();
            listaDocEgresoBean = doc.obtenerFacturaPorNumCheq(docEgreso);
            for (DocEgresoBean docE : listaDocEgresoBean) {
                FacturaCompraBean factura = new FacturaCompraBean();
                factura.getUnidadNegocioBean().setUniNeg(docEgreso.getUnidadNegocioBean().getUniNeg());
                factura.setIdFacturaCompra(docE.getFacturaCompraBean().getIdFacturaCompra());
                factu = facturaService.obtenerFactura(factura);
                for (FacturaCompraBean fact : factu) {
                    fact.setIdFacturaCompra(docE.getFacturaCompraBean().getIdFacturaCompra());
                    fact.getTipoStatusFacturaBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_FACT_COM);
                    fact.getTipoStatusFacturaBean().setCodigo(MaristaConstantes.ESTADO_AUTORIZADA_FAC_COM);
                    fact.getTipoStatusFacturaBean().setIdCodigo(MaristaConstantes.Id_ESTADO_AUTORIZADA_FAC_COM);
                    fact.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
//                    registroCompraDAO.cambiarEstadoPagadoSolicitudRC(docEgre.getRegistroCompraBean());
                    registroCompraDAO.cambiarEstadoFacturaPorReg(fact);
                }
            }
        }
        if (chequeAnuladoBean.getDocEgresoBean().getIdTipoDocEgreso().equals("A")) {
            DocEgresoService doc = BeanFactory.getDocEgresoService();
            listaDocEgresoBean = doc.obtenerFacturaPorNumCheq(docEgreso);
            for (DocEgresoBean docE : listaDocEgresoBean) {
                SolicitudCajaCHService solicitudCajaCHService = BeanFactory.getSolicitudCajaCHService();
                SolicitudCajaCHBean solicitudCajaChica = new SolicitudCajaCHBean();
                solicitudCajaChica.getTipoStatusSolCajaChBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STATUS_SOL);
                solicitudCajaChica.getTipoStatusSolCajaChBean().setCodigo(MaristaConstantes.COD_SOL_AUTORIZADO);
                solicitudCajaChica.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
                solicitudCajaChica.setIdSolicitudCajaCh(docE.getSolicitudCajaCHBean().getIdSolicitudCajaCh());
                solicitudCajaCHService.cambiarEstadoAutorizadoSolicitudCCh(solicitudCajaChica);
            }
        }
        //asiento
        AsientoDAO asientoDAO = BeanFactory.getAsientoDAO();
        AsientoBean asiento = new AsientoBean();
        asiento.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
        asiento.setStatus(Boolean.FALSE);
        DocEgresoService docEgr = BeanFactory.getDocEgresoService();
        DocEgresoBean doc = new DocEgresoBean();
        doc.getUnidadNegocioBean().setUniNeg(chequeAnuladoBean.getUnidadNegocioBean().getUniNeg());
        doc.setNumCheque(chequeAnuladoBean.getDocEgresoBean().getNumCheque());
        listaDocEgresoBean = docEgr.obtenerDocEgresoChequePorFiltro(doc);
        for (DocEgresoBean docE : listaDocEgresoBean) {
            if (docE.getIdTipoDocEgreso().equals("A")) {
                asiento.setObjeto(MaristaConstantes.OBJ_SOL_CAJACH);
                asiento.setIdObjeto(docE.getIdCompraDocE());
            }
            if (docE.getIdTipoDocEgreso().equals("F") || chequeAnuladoBean.getDocEgresoBean().getIdTipoDocEgreso().equals("O")) {
                asiento.setObjeto(MaristaConstantes.OBJ_SOL_FACT_COMPRA);
                asiento.setIdObjeto(docE.getIdCompraDocE());
            }
            asiento.setModiPor(usuarioBean.getUsuario());
            asientoDAO.cambiarEstadoAsiento(asiento);
        }
//
//        chequeAnuladoBean.getDocEgresoBean().getFacturaCompraBean().getRegistroCompraBean().getTipoStatusRegCBean().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STA_REGISTRO);
//        chequeAnuladoBean.getDocEgresoBean().getFacturaCompraBean().getRegistroCompraBean().getTipoStatusRegCBean().setCodigo(MaristaConstantes.COD_SOL_AUTORIZADO);
//        registroCompraDAO.cambiarEstadoPagadoSolicitudRC(chequeAnuladoBean.getDocEgresoBean().getFacturaCompraBean().getRegistroCompraBean());
    }

    @Transactional
    public void modificarChequeAnulado(ChequeAnuladoBean chequeAnuladoBean) throws Exception {
        chequeAnuladoDAO.modificarChequeAnulado(chequeAnuladoBean);
    }

    @Transactional
    public void eliminarChequeAnulado(ChequeAnuladoBean chequeAnuladoBean) throws Exception {
        chequeAnuladoDAO.eliminarChequeAnulado(chequeAnuladoBean);
    }

    public List<ChequeAnuladoBean> obtenerChequeAnuladoPorUnidadNeg(ChequeAnuladoBean chequeAnuladoBean) throws Exception {
        return chequeAnuladoDAO.obtenerChequeAnuladoPorFiltro(chequeAnuladoBean);
    }

    public List<ChequeAnuladoBean> obtenerChequeAnuladoPorFiltro(ChequeAnuladoBean chequeAnuladoBean) throws Exception {
        return chequeAnuladoDAO.obtenerChequeAnuladoPorFiltro(chequeAnuladoBean);
    }

    public ChequeAnuladoBean obtenerChequeAnuladoPorId(ChequeAnuladoBean chequeAnuladoBean) throws Exception {
        return chequeAnuladoDAO.obtenerChequeAnuladoPorId(chequeAnuladoBean);
    }

    public ChequeAnuladoDAO getChequeAnuladoDAO() {
        return chequeAnuladoDAO;
    }

    public void setChequeAnuladoDAO(ChequeAnuladoDAO chequeAnuladoDAO) {
        this.chequeAnuladoDAO = chequeAnuladoDAO;
    }

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

}

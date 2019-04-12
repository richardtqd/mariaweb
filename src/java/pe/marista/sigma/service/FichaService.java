package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.CajeroCajaBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.FichaBean;
import pe.marista.sigma.bean.ImpresoraBean;
import pe.marista.sigma.bean.ImpresoraCajaBean;
import pe.marista.sigma.bean.reporte.CobranzaValoradoRepBean;
import pe.marista.sigma.dao.FichaDAO;

public class FichaService {

    private FichaDAO fichaDAO;

    public List<FichaBean> obtener(FichaBean fichaBean) throws Exception {
        return fichaDAO.obtener(fichaBean);
    }

    public List<FichaBean> obtenerPorId(FichaBean fichaBean) throws Exception {
        return fichaDAO.obtenerPorId(fichaBean);
    }

    public Integer obtenerMaxNro(FichaBean fichaBean) throws Exception {
        return fichaDAO.obtenerMaxNro(fichaBean);
    }

    @Transactional
    public void insertar(FichaBean fichaBean) throws Exception {
        fichaDAO.insertar(fichaBean);
    }

    @Transactional
    public void actualizar(FichaBean fichaBean) throws Exception {
        fichaDAO.actualizar(fichaBean);
    }

    @Transactional
    public void eliminar(FichaBean fichaBean) throws Exception {
        fichaDAO.eliminar(fichaBean);
    }

    public List<FichaBean> obtenerFichaPorPaganteEst(FichaBean fichaBean) throws Exception {
        return fichaDAO.obtenerFichaPorPaganteEst(fichaBean);
    }

    public List<FichaBean> obtenerFichaPorPagantePer(FichaBean fichaBean) throws Exception {
        return fichaDAO.obtenerFichaPorPagantePer(fichaBean);
    }

    public List<FichaBean> obtenerFichaPorPaganteExt(FichaBean fichaBean) throws Exception {
        return fichaDAO.obtenerFichaPorPaganteExt(fichaBean);
    }

    public List<FichaBean> obtenerFichaPorPaganteEnt(FichaBean fichaBean) throws Exception {
        return fichaDAO.obtenerFichaPorPaganteEnt(fichaBean);
    }

    @Transactional
    public Object execProCtaCteBingo(String uniNeg, String ruc, Integer idProcesoBanco, String modiPor) throws Exception {
        return fichaDAO.execProCtaCteBingo(uniNeg, ruc, idProcesoBanco, modiPor);
    }

    public List<FichaBean> filtrarFichasPagadas(FichaBean fichaBean) throws Exception {
        return fichaDAO.filtrarFichasPagadas(fichaBean);
    }

    public Integer obtenerNroFicha(FichaBean fichaBean) throws Exception {
        return fichaDAO.obtenerNroFicha(fichaBean);
    }

    @Transactional
    public void modificarEstado(FichaBean fichaBean) throws Exception {
        fichaDAO.modificarEstado(fichaBean);
    }

    public List<FichaBean> obtenerPorTipoEstado(FichaBean fichaBean) throws Exception {
        return fichaDAO.obtenerPorTipoEstado(fichaBean);
    }

    public List<ImpresoraBean> obtenerImpresoraCajero(CajeroCajaBean cajeroCajaBean) throws Exception {
        return fichaDAO.obtenerImpresoraCajero(cajeroCajaBean);
    }

    public List<CodigoBean> obtenerTipDocumentoPorImpresora(String impresora, String usuario, String uniNeg, Integer idCaja) throws Exception {
        return fichaDAO.obtenerTipDocumentoPorImpresora(impresora, usuario, uniNeg, idCaja);
    }

    public List<CodigoBean> obtenerTipDocumentoPorImpresoraComprobante(String impresora, String usuario, String uniNeg, Integer idCaja, String tipoDoc) throws Exception {
        return fichaDAO.obtenerTipDocumentoPorImpresoraComprobante(impresora, usuario, uniNeg, idCaja, tipoDoc);
    }

    public ImpresoraCajaBean obtenerDetalleTipoDoc(String impresora, String usuario, String uniNeg, Integer idTipoDoc, Integer idCaja) throws Exception {
        return fichaDAO.obtenerDetalleTipoDoc(impresora, usuario, uniNeg, idTipoDoc, idCaja);
    }

    @Transactional
    public void modificarFichaPagada(FichaBean fichaBean) throws Exception {
        fichaDAO.modificarFichaPagada(fichaBean);
    }

    public List<CobranzaValoradoRepBean> generarReciboValorado(String uniNeg, List<Integer> ids) throws Exception {
        return fichaDAO.generarReciboValorado(uniNeg, ids);
    }

    public Integer obtenerMaxIdFicha(String uniNeg) throws Exception {
        return fichaDAO.obtenerMaxIdFicha(uniNeg);
    }

    public Integer obtenerIdNroFicha(FichaBean fichaBean) throws Exception {
        return fichaDAO.obtenerIdNroFicha(fichaBean);
    }

    @Transactional
    public void modificarImpresoraActual(ImpresoraBean impresoraBean) throws Exception {
        fichaDAO.modificarImpresoraActual(impresoraBean);
    }

    public FichaBean obtenerPorIdFicha(FichaBean fichaBean) throws Exception {
        return fichaDAO.obtenerPorIdFicha(fichaBean);
    }

    @Transactional
    public void eliminarFicha(FichaBean fichaBean) throws Exception {
        fichaDAO.eliminarFicha(fichaBean);
    }

    public List<FichaBean> obtenerHistorialCarga(FichaBean fichaBean) throws Exception {
        return fichaDAO.obtenerHistorialCarga(fichaBean);
    }

    public List<FichaBean> filtrarFichasPagadasFamilia(FichaBean fichaBean) throws Exception {
        return fichaDAO.filtrarFichasPagadasFamilia(fichaBean);
    }

    public List<FichaBean> obtenerFichaPorPagantePagado(FichaBean fichaBean) throws Exception {
        return fichaDAO.obtenerFichaPorPagantePagado(fichaBean);
    }

    public Integer obtenerNumFichaDon(FichaBean fichaBean) throws Exception {
        return fichaDAO.obtenerNumFichaDon(fichaBean);
    }

    public List<FichaBean> obtenerFichaPorPaganteObj(FichaBean fichaBean) throws Exception {
        return fichaDAO.obtenerFichaPorPaganteObj(fichaBean);
    }

    public List<FichaBean> obtenerPorNroFicha(FichaBean fichaBean) throws Exception {
        return fichaDAO.obtenerPorNroFicha(fichaBean);
    }

    public List<FichaBean> filtrarFichasPagadasObj(FichaBean fichaBean) throws Exception {
        return fichaDAO.filtrarFichasPagadasObj(fichaBean);
    }

    public List<FichaBean> obtenerFichaPorObjPagado(FichaBean fichaBean) throws Exception {
        return fichaDAO.obtenerFichaPorObjPagado(fichaBean);
    }

    public FichaBean obtenerPorNroFichaEspecial(FichaBean fichaBean) throws Exception {
        return fichaDAO.obtenerPorNroFichaEspecial(fichaBean);
    }

    public Integer obtenerNroDocFicha(FichaBean fichaBean) throws Exception {
        return fichaDAO.obtenerNroDocFicha(fichaBean);
    }

    public Integer obtenerMaxNroDocFicha(FichaBean fichaBean) throws Exception {
        return fichaDAO.obtenerMaxNroDocFicha(fichaBean);
    }

    public FichaDAO getFichaDAO() {
        return fichaDAO;
    }

    public void setFichaDAO(FichaDAO fichaDAO) {
        this.fichaDAO = fichaDAO;
    }

}

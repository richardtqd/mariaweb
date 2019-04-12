package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.AsientoBean;
import pe.marista.sigma.bean.CajaChicaBean;
import pe.marista.sigma.bean.CajaChicaMovBean;
import pe.marista.sigma.bean.CajaChicaSaldoBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.DetSolicitudCajaChCRBean;
import pe.marista.sigma.bean.SolicitudCajaCHBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.reporte.CajaChMovRepBean;
import pe.marista.sigma.bean.reporte.CajaChicaMovCRRepBean;
import pe.marista.sigma.bean.reporte.CajaChicaMovCentroRepBean;
import pe.marista.sigma.bean.reporte.CajaChicaMovRepBean;
import pe.marista.sigma.bean.reporte.CajaChicaMovSoliRepBean;
import pe.marista.sigma.bean.reporte.CajaChicaMovilidadRepBean;
import pe.marista.sigma.bean.reporte.CajaChicaMovilidadSubRepBean;
import pe.marista.sigma.bean.reporte.CrDetalladitoRepBean;
import pe.marista.sigma.dao.AsientoDAO;
import pe.marista.sigma.dao.CajaChicaDAO;
import pe.marista.sigma.dao.CajaChicaMovDAO;
import pe.marista.sigma.factory.BeanFactory;

/**
 *
 * @author Administrador
 */
public class CajaChicaMovService {

    private CajaChicaMovDAO cajaChicaMovDAO;
    private CajaChicaDAO cajaChicaDAO;
    private AsientoDAO asientoDAO;

    @Transactional
    public void insertarCajaChicaMov(CajaChicaMovBean cajaChicaMovBean) throws Exception {
        cajaChicaMovDAO.insertarCajaChicaMov(cajaChicaMovBean);

        if (cajaChicaMovBean.getIdDevolucion() != null) {
            cajaChicaMovDAO.actualizarIdDevolucion(cajaChicaMovBean);
        }
        CajaChicaSaldoBean cajaChicaSaldoBean = new CajaChicaSaldoBean();
        cajaChicaSaldoBean.setCajaChicaBean(cajaChicaMovBean.getCajaChicaBean());
        cajaChicaSaldoBean.setTipoMonedaBean(cajaChicaMovBean.getSolicitudCajaCHBean().getTipoMonedaBean());
//        cajaChicaSaldoBean = cajaChicaDAO.obtenerCajaChicaSaldoPorCCH(cajaChicaSaldoBean);
        if (cajaChicaMovBean.getSolicitudCajaCHBean().getTipoMonedaBean().getCodigo().equals(MaristaConstantes.PAGO_MON_SOL)) {
            cajaChicaSaldoBean = cajaChicaDAO.obtenerCajaChicaSaldoPorCCH(cajaChicaSaldoBean);
            cajaChicaSaldoBean.setImporte(cajaChicaMovBean.getCajaChicaBean().getSaldoSol());
            cajaChicaSaldoBean.setModiPor(cajaChicaMovBean.getCreaPor());
            if (cajaChicaMovBean.getFlgMov() == 1) {
                cajaChicaSaldoBean.setImporte(cajaChicaSaldoBean.getImporte() + cajaChicaMovBean.getMonto());
                cajaChicaMovBean.getCajaChicaBean().setSaldoSol(cajaChicaMovBean.getCajaChicaBean().getSaldoSol() + cajaChicaMovBean.getMonto());
                cajaChicaMovBean.getCajaChicaBean().setDevueltoSol(cajaChicaMovBean.getCajaChicaBean().getDevueltoSol() + cajaChicaMovBean.getMonto());
            }
            cajaChicaDAO.modificarCajaChicaSaldo(cajaChicaSaldoBean);
        }
        if (cajaChicaMovBean.getSolicitudCajaCHBean().getTipoMonedaBean().getCodigo().equals(MaristaConstantes.PAGO_MON_DOL)) {
            cajaChicaSaldoBean = cajaChicaDAO.obtenerCajaChicaSaldoPorCCH(cajaChicaSaldoBean);
            cajaChicaSaldoBean.setImporte(cajaChicaMovBean.getCajaChicaBean().getSaldoDol());
            cajaChicaSaldoBean.setModiPor(cajaChicaMovBean.getCreaPor());
            if (cajaChicaMovBean.getFlgMov() == 1) {
                cajaChicaSaldoBean.setImporte(cajaChicaSaldoBean.getImporte() + cajaChicaMovBean.getMonto());
                cajaChicaMovBean.getCajaChicaBean().setSaldoDol(cajaChicaMovBean.getCajaChicaBean().getSaldoDol() + cajaChicaMovBean.getMonto());
                cajaChicaMovBean.getCajaChicaBean().setDevueltoDol(cajaChicaMovBean.getCajaChicaBean().getDevueltoDol() + cajaChicaMovBean.getMonto());
            }
            cajaChicaDAO.modificarCajaChicaSaldo(cajaChicaSaldoBean);
        }
//        if(cajaChicaMovBean.getFlgMov() == 1){
//            cajaChicaMovBean.getCajaChicaBean().setSaldoSol(Double.NaN);
//        }
        cajaChicaDAO.modificarCajaChicaCantidadades(cajaChicaMovBean.getCajaChicaBean());
        if (cajaChicaMovBean.getFlgMov() == 0) {
            SolicitudCajaCHBean solicitudCajaCHBean = new SolicitudCajaCHBean();
            CodigoBean bean = new CodigoBean();
            bean.setCodigo(MaristaConstantes.TIP_STA_CCH);
//        bean.setTipoCodigoBean(new TipoCodigoBean(MaristaConstantes.NOM_PAG));
            bean.setTipoCodigoBean(new TipoCodigoBean(null, MaristaConstantes.NOM_PAG));
            solicitudCajaCHBean.setTipoStatusSolCajaChBean(bean);
            solicitudCajaCHBean.setIdSolicitudCajaCh(cajaChicaMovBean.getSolicitudCajaCHBean().getIdSolicitudCajaCh());
            solicitudCajaCHBean.setUnidadNegocioBean(cajaChicaMovBean.getSolicitudCajaCHBean().getUnidadNegocioBean());
            cajaChicaMovDAO.cambiarEstadoSolicitudCCh(solicitudCajaCHBean);
        }
        //3.- Cambiando estado rendicion
        if (cajaChicaMovBean.getFlgMov() == 1) {
            cajaChicaMovBean.setFlgRendicion(Boolean.TRUE);
            cajaChicaMovDAO.cambiarEstadoRendicionMov(cajaChicaMovBean);
        }
        ProcesoFinalService procesoFileService = BeanFactory.getProcesoFinalService();
        System.out.println("ProcesoFinalService procesoFileService = BeanFactory.getProcesoFinalService();");
        for (DetSolicitudCajaChCRBean lista : cajaChicaMovBean.getSolicitudCajaCHBean().getListaDetSolicitudCajaChCRBean()) {
            System.out.println("lista..."+lista.getCentroResponsabilidadBean().getCr()+"..."+lista.getValor()+"..."+lista.getValorD());
        }
        procesoFileService.execProAsiento(cajaChicaMovBean.getCajaChicaBean().getUnidadNegocioBean().getUniNeg(), "MT_CajaChicaMov", cajaChicaMovBean.getIdCajaChicaMov2(), cajaChicaMovBean.getCreaPor(), 0);
    }

    @Transactional
    public void modificarCajaChicaMov(CajaChicaMovBean cajaChicaMovBean) throws Exception {
        cajaChicaMovDAO.modificarCajaChicaMov(cajaChicaMovBean);
    }

    @Transactional
    public void cambiarEstadoRendicionMov(CajaChicaMovBean cajaChicaMovBean) throws Exception {
        cajaChicaMovDAO.cambiarEstadoRendicionMov(cajaChicaMovBean);
    }

    @Transactional
    public void eliminarCajaChicaMov(CajaChicaMovBean cajaChicaMovBean) throws Exception {
        cajaChicaMovDAO.eliminarCajaChicaMov(cajaChicaMovBean);
    }

    public List<CajaChicaMovBean> obtenerCajaChicaMovPorCCH(CajaChicaMovBean cajaChicaMovBean) throws Exception {
        return cajaChicaMovDAO.obtenerCajaChicaMovPorCCH(cajaChicaMovBean);
    }

    public List<CajaChicaMovBean> obtenerCajaChicaMovPorFiltro(CajaChicaMovBean cajaChicaMovBean) throws Exception {
        cajaChicaMovBean.getSolicitudCajaCHBean().getTipoSolicitudBean().setNombre(MaristaConstantes.A_RENDIR);
        return cajaChicaMovDAO.obtenerCajaChicaMovPorFiltro(cajaChicaMovBean);
    }

    public CajaChicaMovBean obtenerCajaChicaMovPorId(CajaChicaMovBean cajaChicaMovBean) throws Exception {
        return cajaChicaMovDAO.obtenerCajaChicaMovPorId(cajaChicaMovBean);
    }

    //Insertar Asiento ContraPago
    public void insertarAsiento(SolicitudCajaCHBean solicitudCajaCHBean, String objeto) throws Exception {
        AsientoBean asientoBean = new AsientoBean();
        asientoBean.setUnidadNegocioBean(solicitudCajaCHBean.getUnidadNegocioBean());
        asientoBean.setIdObjeto(solicitudCajaCHBean.getIdSolicitudCajaCh());
        asientoBean.setObjeto(objeto);
        asientoBean.setStatus(Boolean.TRUE);
        asientoBean.setPlanContableCuentaDBean(solicitudCajaCHBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaDBean());
        asientoBean.setPlanContableCuentaHBean(solicitudCajaCHBean.getConceptoUniNegBean().getConceptoBean().getPlanContableCuentaHBean());
        asientoBean.setNumeroComprobante(solicitudCajaCHBean.getCreaPor());
    }

    //Reportes
    public List<CajaChMovRepBean> obtenerCajaChicaMovRepNewFor(CajaChicaBean cajaChicaBean) throws Exception {
        return cajaChicaMovDAO.obtenerCajaChicaMovRepNewFor(cajaChicaBean);
    }

    public List<CajaChicaMovRepBean> obtenerCajaChicaMovRep(CajaChicaBean cajaChicaBean) throws Exception {
        return cajaChicaMovDAO.obtenerCajaChicaMovRep(cajaChicaBean);
    }

    public List<CajaChicaMovRepBean> obtenerCajaChicaMovRepPorIdDev(Integer idCajaChicaMov) throws Exception {
        return cajaChicaMovDAO.obtenerCajaChicaMovRepPorIdDev(idCajaChicaMov);
    }
    //GEtter y Setter

    public CajaChicaMovDAO getCajaChicaMovDAO() {
        return cajaChicaMovDAO;
    }

    public void setCajaChicaMovDAO(CajaChicaMovDAO cajaChicaMovDAO) {
        this.cajaChicaMovDAO = cajaChicaMovDAO;
    }

    public CajaChicaDAO getCajaChicaDAO() {
        return cajaChicaDAO;
    }

    public void setCajaChicaDAO(CajaChicaDAO cajaChicaDAO) {
        this.cajaChicaDAO = cajaChicaDAO;
    }

    public AsientoDAO getAsientoDAO() {
        return asientoDAO;
    }

    public void setAsientoDAO(AsientoDAO asientoDAO) {
        this.asientoDAO = asientoDAO;
    }

    public void modificarCajaChicaMovAnulacion(String uniNeg, Integer idSolicitudCajaCh, String modiPor,String motivo) throws Exception {
        cajaChicaMovDAO.modificarCajaChicaMovAnulacion(uniNeg, idSolicitudCajaCh, modiPor, motivo);
    }

    public List<CajaChicaMovCRRepBean> obtenerCajaChicaCentros(String uniNeg, Integer idCajaChica) throws Exception {
        return cajaChicaMovDAO.obtenerCajaChicaCentros(uniNeg, idCajaChica);
    }

    public List<CajaChicaMovSoliRepBean> obtenerCajaChicaSoliCentros(String uniNeg, Integer idCajaChica) throws Exception {
        return cajaChicaMovDAO.obtenerCajaChicaSoliCentros(uniNeg, idCajaChica);
    }

    public List<CajaChicaMovCentroRepBean> obtenerCajaChicaCRCentros(String uniNeg, Integer idSolicitudCajaCh) throws Exception {
        return cajaChicaMovDAO.obtenerCajaChicaCRCentros(uniNeg, idSolicitudCajaCh);
    } 

    public List<CrDetalladitoRepBean> obtenerCRDetalladito(String uniNeg, Integer idCajaChica) throws Exception {
        return cajaChicaMovDAO.obtenerCRDetalladito(uniNeg, idCajaChica);
    }

    public List<CajaChicaMovilidadSubRepBean> obtenerCajaChicaMovDetalle(String uniNeg, Integer idPersonal,Integer idCajaChica) throws Exception {
        return cajaChicaMovDAO.obtenerCajaChicaMovDetalle(uniNeg, idPersonal,idCajaChica);
    }

    public List<CajaChicaMovilidadRepBean> obtenerCajaChicaMovCabecera(String uniNeg,Integer idCajaChica) throws Exception {
        return cajaChicaMovDAO.obtenerCajaChicaMovCabecera(uniNeg,idCajaChica);
    }

    public List<CajaChMovRepBean> obtenerCajaChicaDetalle(String uniNeg, Integer idSolicitudCajaCh) throws Exception {
        return cajaChicaMovDAO.obtenerCajaChicaDetalle(uniNeg, idSolicitudCajaCh);
    }
    
    
}

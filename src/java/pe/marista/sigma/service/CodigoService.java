package pe.marista.sigma.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.MotivoMovimientoBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.dao.CodigoDAO;

/**
 *
 * @author Administrador
 */
public class CodigoService {

    private CodigoDAO codigoDAO;

    public List<CodigoBean> obtenerPorTipoMamaApoVive(TipoCodigoBean tipoCodigoBean) throws Exception {
        return codigoDAO.obtenerPorTipoMamaApoVive(tipoCodigoBean);
    }

    public List<CodigoBean> obtenerPorTipoPapaApoVive(TipoCodigoBean tipoCodigoBean) throws Exception {
        return codigoDAO.obtenerPorTipoPapaApoVive(tipoCodigoBean);
    }

    public List<CodigoBean> obtenerPorTipoPapaMamaVive(TipoCodigoBean tipoCodigoBean) throws Exception {
        return codigoDAO.obtenerPorTipoPapaMamaVive(tipoCodigoBean);
    }

    public List<CodigoBean> obtenerPorTipoPapaVive(TipoCodigoBean tipoCodigoBean) throws Exception {
        return codigoDAO.obtenerPorTipoPapaVive(tipoCodigoBean);
    }

    public List<CodigoBean> obtenerPorTipoMamaVive(TipoCodigoBean tipoCodigoBean) throws Exception {
        return codigoDAO.obtenerPorTipoMamaVive(tipoCodigoBean);
    }

    public List<CodigoBean> obtenerPorTipoNingunoVive(TipoCodigoBean tipoCodigoBean) throws Exception {
        return codigoDAO.obtenerPorTipoNingunoVive(tipoCodigoBean);
    }

    public List<CodigoBean> obtenerPorTipoApoVive(TipoCodigoBean tipoCodigoBean) throws Exception {
        return codigoDAO.obtenerPorTipoApoVive(tipoCodigoBean);
    }

    //Logica de Negocio
    @Transactional
    public void insertarCodigo(CodigoBean codigoBean) throws Exception {
        codigoDAO.insertar(codigoBean);
    }

    @Transactional
    public void eliminarCodigo(CodigoBean codigoBean) throws Exception {
        codigoDAO.eliminar(codigoBean);
    }

    @Transactional
    public void modificarCodigo(CodigoBean codigoBean) throws Exception {
        codigoDAO.actualizar(codigoBean);
    }

    @Transactional
    public void eliminarPorTipo(TipoCodigoBean tipoCodigoBean) throws Exception {
        codigoDAO.eliminarPorTipo(tipoCodigoBean);
    }

    public List<CodigoBean> obtenerPorTipo(TipoCodigoBean tipoCodigoBean) throws Exception {
        return codigoDAO.obtenerPorTipo(tipoCodigoBean);
    }

    public List<CodigoBean> funcionObtenerPorTipo(TipoCodigoBean tipoCodigoBean) throws Exception {
        return codigoDAO.funcionObtenerPorTipo(tipoCodigoBean);
    }

    public List<CodigoBean> obtenerPorTipoSol(TipoCodigoBean tipoCodigoBean) throws Exception {
        return codigoDAO.obtenerPorTipoSol(tipoCodigoBean);
    }

    public List<CodigoBean> obtenerPorTipoDes(TipoCodigoBean tipoCodigoBean) throws Exception {
        return codigoDAO.obtenerPorTipoDes(tipoCodigoBean);
    }

    public List<CodigoBean> obtenerPorTipoCat(TipoCodigoBean tipoCodigoBean) throws Exception {
        return codigoDAO.obtenerPorTipoCat(tipoCodigoBean);
    }

    public List<CodigoBean> obtenerPorTipoDespacho(TipoCodigoBean tipoCodigoBean) throws Exception {
        return codigoDAO.obtenerPorTipoDespacho(tipoCodigoBean);
    }

    public List<MotivoMovimientoBean> obtenerMotivoPorMov(Integer idTipoMov) throws Exception {
        return codigoDAO.obtenerMotivoPorMov(idTipoMov);
    }

    public List<CodigoBean> obtenerMotivoPorDuracion(Integer idTipoDuracion) throws Exception {
        return codigoDAO.obtenerMotivoPorDuracion(idTipoDuracion);
    }

    public List<CodigoBean> obtenerDuracion(Integer idTipoDuracion) throws Exception {
        return codigoDAO.obtenerDuracion(idTipoDuracion);
    }

    public CodigoBean obtenerPorId(CodigoBean codigoBean) throws Exception {
        return codigoDAO.obtenerPorId(codigoBean);
    }

    public CodigoBean obtenerPorCodigo(CodigoBean codigoBean) throws Exception {
        return codigoDAO.obtenerPorCodigo(codigoBean);
    }

    public CodigoBean obtenerPorCodigoDisCR(Integer id, String uniNeg) throws Exception {
        return codigoDAO.obtenerPorCodigoDisCR(id, uniNeg);
    }

    public CodigoBean obtenerPorCodigoDisCRReq(Integer id, String uniNeg) throws Exception {
        return codigoDAO.obtenerPorCodigoDisCRReq(id, uniNeg);
    }

    public MotivoMovimientoBean obtenerMotivoPorId(Integer idTipoMotivo) throws Exception {
        return codigoDAO.obtenerMotivoPorId(idTipoMotivo);
    }

    public List<CodigoBean> obtenerTodos() throws Exception {
        return codigoDAO.obtenerTodos();
    }

    public List<CodigoBean> obtenerParentescoSinPadres() throws Exception {
        Map<String, Object> parms = new HashMap<>();
        parms.put("padre", MaristaConstantes.COD_PAPA);
        parms.put("madre", MaristaConstantes.COD_MAMA);
        parms.put("tipParentesco", MaristaConstantes.TIP_PARENTESCO);
        return codigoDAO.obtenerParentescoSinPadres(parms);
    }

    public List<CodigoBean> obtenerParentescoConTodo() throws Exception {
        Map<String, Object> parms = new HashMap<>();
        parms.put("padre", MaristaConstantes.COD_PAPA);
        parms.put("madre", MaristaConstantes.COD_MAMA);
        parms.put("apoderado", MaristaConstantes.COD_Apoderado);
        parms.put("tipParentesco", MaristaConstantes.TIP_PARENTESCO);
        return codigoDAO.obtenerParentescoConTodo(parms);
    }

    public List<CodigoBean> obtenerCodigoDocIngreso() throws Exception {
        Map<String, Object> parms = new HashMap<>();
        parms.put("efectivo", MaristaConstantes.COD_EFECTIVO);
        parms.put("pos", MaristaConstantes.COD_POS);
        parms.put("ambos", MaristaConstantes.COD_EFE_POS);
        parms.put("banco", MaristaConstantes.COD_BANCO);
        parms.put("tipModoPago", MaristaConstantes.TIP_MODOPAGO);
        return codigoDAO.obtenerCodigoDocIngreso(parms);
    }

    public List<CodigoBean> obtenerCodigoDocIngresoBanco() throws Exception {
        Map<String, Object> parms = new HashMap<>();
        parms.put("banco", MaristaConstantes.COD_BANCO);
        parms.put("tipModoPago", MaristaConstantes.TIP_MODOPAGO);
        return codigoDAO.obtenerCodigoDocIngreso(parms);
    }

    public List<CodigoBean> obtenerCodigoDocEgreso() throws Exception {
        Map<String, Object> parms = new HashMap<>();
        parms.put("pos", MaristaConstantes.COD_POS);
        parms.put("ambos", MaristaConstantes.COD_EFE_POS);
        parms.put("banco", MaristaConstantes.COD_BANCO);
        parms.put("tipModoPago", MaristaConstantes.TIP_MODOPAGO);
        return codigoDAO.obtenerCodigoDocEgreso(parms);
    }

    public List<CodigoBean> obtenerCodigoStatusPost() throws Exception {
        Map<String, Object> parms = new HashMap<>();
        parms.put("post", MaristaConstantes.COD_POSTULANTE);
        parms.put("tipStatusEst", MaristaConstantes.TIP_STATUS_EST);
        return codigoDAO.obtenerCodigoStatusPost(parms);
    }
//    public List<CodigoBean> obtenerCodigoMonedas() throws Exception{    
//        Map<String, Object> parms = new HashMap<>();  
//        parms.put("ambos",  MaristaConstantes.COD_SOL_DOL); 
//        parms.put("tipMoneda",  MaristaConstantes.TIP_MON); 
//        return codigoDAO.obtenerCodigoMonedas(parms);
//    }
//    

    public List<CodigoBean> obtenerPorTipoEst(TipoCodigoBean tipoCodigoBean) throws Exception {
        return codigoDAO.obtenerPorTipoEst(tipoCodigoBean);
    }

    public List<CodigoBean> obtenerPorTipoOpe(TipoCodigoBean tipoCodigoBean) throws Exception {
        return codigoDAO.obtenerPorTipoOpe(tipoCodigoBean);
    }

    //Getter y Setter
    public CodigoDAO getCodigoDAO() {
        return codigoDAO;
    }

    public void setCodigoDAO(CodigoDAO codigoDAO) {
        this.codigoDAO = codigoDAO;
    }

    public List<CodigoBean> obtenerTemporal() throws Exception {
        return codigoDAO.obtenerTemporal();
    }

    public List<CodigoBean> obtenerPermanente() throws Exception {
        return codigoDAO.obtenerPermanente();
    }

    public List<CodigoBean> obtenerPorStatusEst(TipoCodigoBean tipoCodigoBean) throws Exception {
        return codigoDAO.obtenerPorStatusEst(tipoCodigoBean);
    }

    public List<CodigoBean> obtenerPorIdLista(String codigo) throws Exception {
        return codigoDAO.obtenerPorIdLista(codigo);
    }

    public List<CodigoBean> obtenerPorTipoDocumento(TipoCodigoBean tipoCodigoBean) throws Exception {
        return codigoDAO.obtenerPorTipoDocumento(tipoCodigoBean);
    }

    public List<CodigoBean> obtenerPorTipoSeguro(TipoCodigoBean tipoCodigoBean) throws Exception {
        return codigoDAO.obtenerPorTipoSeguro(tipoCodigoBean);
    }

    public List<CodigoBean> funcionObtenerPorTipoSoloInsc(TipoCodigoBean tipoCodigoBean) throws Exception {
        return codigoDAO.funcionObtenerPorTipoSoloInsc(tipoCodigoBean);
    }

    public List<CodigoBean> obtenerPorTipoRecibo(TipoCodigoBean tipoCodigoBean) throws Exception {
        return codigoDAO.obtenerPorTipoRecibo(tipoCodigoBean);
    }

    public List<CodigoBean> obtenerPorTipoSoles(TipoCodigoBean tipoCodigoBean) throws Exception {
        return codigoDAO.obtenerPorTipoSoles(tipoCodigoBean);
    }

    public List<CodigoBean> obtenerPorStatusEstModificaciones(TipoCodigoBean tipoCodigoBean) throws Exception {
        return codigoDAO.obtenerPorStatusEstModificaciones(tipoCodigoBean);
    }

    public List<CodigoBean> obtenerPorTipoDependiente(TipoCodigoBean tipoCodigoBean) throws Exception {
        return codigoDAO.obtenerPorTipoDependiente(tipoCodigoBean);
    }

    public List<CodigoBean> obtenerPorTipoOtrosDependiente(TipoCodigoBean tipoCodigoBean) throws Exception {
        return codigoDAO.obtenerPorTipoOtrosDependiente(tipoCodigoBean);
    }

    public List<CodigoBean> obtenerPorTipoStatusCtaCte(TipoCodigoBean tipoCodigoBean) throws Exception {
        return codigoDAO.obtenerPorTipoStatusCtaCte(tipoCodigoBean);
    }

}

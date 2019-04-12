/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.ProcesoBancoBean;
import pe.marista.sigma.bean.ProcesoRecuperacionBean;
import pe.marista.sigma.dao.ProcesoRecuperacionDAO;

/**
 *
 * @author MS-005
 */
public class ProcesoRecuperacionService {

    private ProcesoRecuperacionDAO procesoRecuperacionDAO;

    public List<ProcesoRecuperacionBean> obtenerProcesoRec() throws Exception {
        return procesoRecuperacionDAO.obtenerProcesoRec();
    }

//    public List<ProcesoRecuperacionBean> obtenerPorProcesoBanco(Integer idProcesoBanco) throws Exception {
//        return procesoRecuperacionDAO.obtenerPorProcesoBanco(idProcesoBanco);
//    }    
    public List<ProcesoRecuperacionBean> obtenerPorProcesoBanco(Integer idProcesoBanco, String uniNeg) throws Exception {
        return procesoRecuperacionDAO.obtenerPorProcesoBanco(idProcesoBanco, uniNeg);
    }

    public Integer obtenerMaxIdProcesoBanco(String uniNeg) throws Exception {
        return procesoRecuperacionDAO.obtenerMaxIdProcesoBanco(uniNeg);
    }

    public List<ProcesoRecuperacionBean> obtenerListaPorId(Integer idProcesoRecup) throws Exception {
        return procesoRecuperacionDAO.obtenerListaPorId(idProcesoRecup);
    }

    public List<ProcesoRecuperacionBean> obtenerError(String uniNeg) throws Exception {
        return procesoRecuperacionDAO.obtenerError(uniNeg);
    }

    public List<ProcesoRecuperacionBean> obtenerNoProcesados(Integer flgConcilia, String uniNeg) throws Exception {
        return procesoRecuperacionDAO.obtenerNoProcesados(flgConcilia, uniNeg);
    }

    public List<ProcesoRecuperacionBean> obtenerFiltroRecuperacion(ProcesoRecuperacionBean procesoRecuperacionBean) throws Exception {
        return procesoRecuperacionDAO.obtenerFiltroRecuperacion(procesoRecuperacionBean);
    }

    public ProcesoRecuperacionBean obtenerRecupPorId(Integer idProcesoRecup) throws Exception {
        return procesoRecuperacionDAO.obtenerRecupPorId(idProcesoRecup);
    }

    public Integer obtenerDeudaTotal(Integer idProcesoBanco, String uniNeg, Integer idEstudiante) throws Exception {
        return procesoRecuperacionDAO.obtenerDeudaTotal(idProcesoBanco, uniNeg, idEstudiante);
    }

    public void insertarrecuperacion(String file) throws Exception {
        procesoRecuperacionDAO.insertarrecuperacion(file);
    }

    public void insertarRecuperacion(ProcesoRecuperacionBean procesoRecuperacionBean) throws Exception {
        procesoRecuperacionDAO.insertarRecuperacion(procesoRecuperacionBean);
    }

    public void elimnarRecuperacionPorBanco(Integer idProcesoBanco) throws Exception {
        procesoRecuperacionDAO.elimnarRecuperacionPorBanco(idProcesoBanco);
    }

    public void modificarConcilia(ProcesoRecuperacionBean procesoRecuperacionBean) throws Exception {
        procesoRecuperacionDAO.modificarConcilia(procesoRecuperacionBean);
    }

    public List<ProcesoRecuperacionBean> obtenerProcesoRecPorUniNeg(String uniNeg) throws Exception {
        return procesoRecuperacionDAO.obtenerProcesoRecPorUniNeg(uniNeg);
    }

    public Float obtenerMontoTotal(Integer idProcesoBanco, Integer idTipoFile, Integer flgProceso, String ruc, String uniNeg) throws Exception {
        return procesoRecuperacionDAO.obtenerMontoTotal(idProcesoBanco, idTipoFile, flgProceso, ruc, uniNeg);
    }

    public Integer obtenerTotalRegistros(Integer idProcesoBanco, String ruc, String uniNeg) throws Exception {
        return procesoRecuperacionDAO.obtenerTotalRegistros(idProcesoBanco, ruc, uniNeg);
    }

    public Object execProAsiento(String uniNeg, String objeto, Integer idObjeto, String creaPor, Integer idProcesoBanco) throws Exception {
        return procesoRecuperacionDAO.execProAsiento(uniNeg, objeto, idObjeto, creaPor, idProcesoBanco);
    }

    public Integer obtenerDeudaConciliaRecup(Integer idProcesoRecup, String idDiscente) throws Exception {
        return procesoRecuperacionDAO.obtenerDeudaConciliaRecup(idProcesoRecup, idDiscente);
    }

    @Transactional
    public Object execProRecuperacionTaller(ProcesoBancoBean procesoBancoBean) throws Exception {
        return procesoRecuperacionDAO.execProRecuperacionTaller(procesoBancoBean);
    }

    public ProcesoRecuperacionDAO getProcesoRecuperacionDAO() {
        return procesoRecuperacionDAO;
    }

    public void setProcesoRecuperacionDAO(ProcesoRecuperacionDAO procesoRecuperacionDAO) {
        this.procesoRecuperacionDAO = procesoRecuperacionDAO;
    }

}

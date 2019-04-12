/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.ChequeBean;
import pe.marista.sigma.dao.ChequeDAO;

/**
 *
 * @author MS002
 */
public class ChequeService {

    private ChequeDAO chequeDAO;

    @Transactional
    public void insertarCheque(ChequeBean chequeBean) throws Exception {
        chequeDAO.insertarCheque(chequeBean);
    }

    @Transactional
    public void modificarCheque(ChequeBean chequeBean) throws Exception {
        chequeDAO.modificarCheque(chequeBean);
    }
    @Transactional
    public void aumentarSecuenciaCheque(ChequeBean chequeBean) throws Exception {
        chequeDAO.aumentarSecuenciaCheque(chequeBean);
    }

    @Transactional
    public void eliminarCheque(ChequeBean chequeBean) throws Exception {
        chequeDAO.eliminarCheque(chequeBean);
    }

    public ChequeBean obtenerChequePorId(ChequeBean chequeBean) throws Exception {
        return chequeDAO.obtenerChequePorId(chequeBean);
    }

    public ChequeBean obtenerUltimoChequeMasUno(Integer idTipoMoneda, String impresora,String uninNeg) throws Exception {
        return chequeDAO.obtenerUltimoChequeMasUno(idTipoMoneda,impresora,uninNeg);
    }

    public List<ChequeBean> obtenerChequePorFiltro(ChequeBean chequeBean) throws Exception {
        return chequeDAO.obtenerChequePorFiltro(chequeBean);
    }
    public List<ChequeBean> obtenerChequeActivos(ChequeBean chequeBean) throws Exception {
        return chequeDAO.obtenerChequeActivos(chequeBean);
    }

    public ChequeBean obtenerUltimoChequeCorrelativo(Integer idTipoMoneda, String impresora, String uniNeg) {
        return chequeDAO.obtenerUltimoChequeCorrelativo(idTipoMoneda, impresora, uniNeg);
    }

    public ChequeDAO getChequeDAO() {
        return chequeDAO;
    }

    public void setChequeDAO(ChequeDAO chequeDAO) {
        this.chequeDAO = chequeDAO;
    }

}

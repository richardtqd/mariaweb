/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.CuentaBancoBean;
import pe.marista.sigma.dao.CuentaBancoDAO;

/**
 *
 * @author Administrador
 */
public class CuentaBancoService {

    private CuentaBancoDAO cuentaBancoDAO;

    public List<CuentaBancoBean> obtenerCuentaPorUniNeg(String cuentaBancoBean) throws Exception {
        return cuentaBancoDAO.obtenerCuentaPorUniNeg(cuentaBancoBean);
    }
    public List<CuentaBancoBean> obtenerCuentaPorCongregacion(String cuentaBancoBean) throws Exception {
        return cuentaBancoDAO.obtenerCuentaPorCongregacion(cuentaBancoBean);
    }

    public List<CuentaBancoBean> obtenerBancoPorTipMonedaBcoColegio(String idTipoCodigo, String codigo, String uniNeg) throws Exception {
        return cuentaBancoDAO.obtenerBancoPorTipMonedaBcoColegio(idTipoCodigo, codigo, uniNeg);
    }

    public List<CuentaBancoBean> obtenerBancoPorTipMoneda(String idTipoCodigo, String codigo, String uniNeg) throws Exception {
        return cuentaBancoDAO.obtenerBancoPorTipMoneda(idTipoCodigo, codigo, uniNeg);
    }

    public List<CuentaBancoBean> obtenerBancoPorTipMonedaYBanco(String idTipoCodigo, String codigo, String uniNeg, String rucBanco) throws Exception {
        return cuentaBancoDAO.obtenerBancoPorTipMonedaYBanco(idTipoCodigo, codigo, uniNeg, rucBanco);
    }

    public List<CuentaBancoBean> obtenerBancoPorTipMonedaYBancoFlgCobranza(String idTipoCodigo, String codigo, String uniNeg, String rucBanco, Boolean flgCobranza) throws Exception {
        return cuentaBancoDAO.obtenerBancoPorTipMonedaYBancoFlgCobranza(idTipoCodigo, codigo, uniNeg, rucBanco, flgCobranza);
    }

    public CuentaBancoBean obtenerCuentaBancoPorId(CuentaBancoBean cuentaBancoBean) throws Exception {
        return cuentaBancoDAO.obtenerCuentaBancoPorId(cuentaBancoBean);
    }

    public CuentaBancoBean obtenerCuentaBancoPorNumCta(CuentaBancoBean cuentaBancoBean) throws Exception {
        return cuentaBancoDAO.obtenerCuentaBancoPorNumCta(cuentaBancoBean);
    }

    public List<CuentaBancoBean> obtenerPorRuc(String uniNeg, String ruc, Integer idTipoMoneda) throws Exception {
        return cuentaBancoDAO.obtenerPorRuc(uniNeg, ruc, idTipoMoneda);
    }

    public String obtenerRucEntidad(String uniNeg) throws Exception {
        return cuentaBancoDAO.obtenerRucEntidad(uniNeg);
    }

    @Transactional
    public void insertarCuentaBanco(CuentaBancoBean cuentaBancoBean) throws Exception {
        cuentaBancoDAO.insertarCuentaBanco(cuentaBancoBean);
    }

    @Transactional
    public void modificarCuentaBanco(CuentaBancoBean cuentaBancoBean) throws Exception {
        cuentaBancoDAO.modificarCuentaBanco(cuentaBancoBean);
    }

    @Transactional
    public void eliminarCuentaBanco(CuentaBancoBean cuentaBancoBean) throws Exception {
        cuentaBancoDAO.eliminarCuentaBanco(cuentaBancoBean);
    }

    public List<CuentaBancoBean> obtenerPorRucRecauda(String uniNeg, String ruc, Integer idTipoMoneda) throws Exception {
        return cuentaBancoDAO.obtenerPorRucRecauda(uniNeg, ruc, idTipoMoneda);
    }
    public CuentaBancoBean obtenerPorRucRecaudaVer2(String uniNeg, Integer idTipoMoneda) throws Exception {
        return cuentaBancoDAO.obtenerPorRucRecaudaVer2(uniNeg, idTipoMoneda);
    }

    public List<CuentaBancoBean> obtenerCuentaPorFiltro(CuentaBancoBean cuentaBancoBean) throws Exception {
        return cuentaBancoDAO.obtenerCuentaPorFiltro(cuentaBancoBean);
    }

    public List<CuentaBancoBean> obtenerCuentaPorTipo(CuentaBancoBean cuentaBancoBean) throws Exception {
        return cuentaBancoDAO.obtenerCuentaPorTipo(cuentaBancoBean);
    }
    
    

    // 
    public CuentaBancoDAO getCuentaBancoDAO() {
        return cuentaBancoDAO;
    }

    public void setCuentaBancoDAO(CuentaBancoDAO cuentaBancoDAO) {
        this.cuentaBancoDAO = cuentaBancoDAO;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.CajaBean;
import pe.marista.sigma.bean.CajeroCajaBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.dao.CajaDAO;
import pe.marista.sigma.dao.CajeroDAO;

/**
 *
 * @author Administrador
 */
public class CajeroService {

    private CajeroDAO cajeroDAO;
    private CajaDAO cajaDAO;

    //Metodos de Logica de Negocio  
   
    @Transactional
    public void insertar(List<CajaBean> listaCaja, CajeroCajaBean cajeroCaja) throws Exception{
        for (Object objecto : listaCaja) {
            CajaBean cajaBean = new CajaBean();
            cajaBean.setIdCaja(new Integer(objecto.toString())); 
            cajeroCaja.setCajaBean(cajaBean);          
            cajeroDAO.insertarCajeroCaja(cajeroCaja);
        }
    }

    //CajeroCaja
    public List<CajaBean> obtenerCajasPorCajero(CajeroCajaBean cajeroCajaBean) throws Exception {
        return cajeroDAO.obtenerCajasPorCajero(cajeroCajaBean);
    }

    @Transactional
    public void modificarCajeroCaja(CajeroCajaBean cajeroCajaBean) throws Exception {
        cajeroDAO.modificarCajeroCaja(cajeroCajaBean);
    }
    @Transactional
    public void eliminarCajaAll(CajeroCajaBean cajeroCajaBean) throws Exception {
        cajeroDAO.eliminarCajaAll(cajeroCajaBean);
    }

    public List<CajeroCajaBean> obtenerUsuarioConCaja(String uniNeg) throws Exception {
        return cajeroDAO.obtenerUsuarioConCaja(uniNeg);
    }

    public List<PersonalBean> obtenerUsarioPerfil() throws Exception {
        return cajeroDAO.obtenerUsarioPerfil();
    }
    
    public List<CajaBean> obtenerCajaSinCajero(CajeroCajaBean cajeroCajaBean) throws Exception{
        return cajeroDAO.obtenerCajaSinCajero(cajeroCajaBean);
    }

    public CajeroCajaBean obtenerUsuarioConCajaPorId(CajeroCajaBean cajeroCajaBean) throws Exception{
        return cajeroDAO.obtenerUsuarioConCajaPorId(cajeroCajaBean);
    }
    
    public CajeroCajaBean autenticarUsuarioConCaja(CajeroCajaBean cajeroCajaBean) throws Exception{
        return cajeroDAO.autenticarUsuarioConCaja(cajeroCajaBean);
    }
    //Getter and Setter

    public CajeroDAO getCajeroDAO() {
        return cajeroDAO;
    }

    public void setCajeroDAO(CajeroDAO cajeroDAO) {
        this.cajeroDAO = cajeroDAO;
    }

    public CajaDAO getCajaDAO() {
        return cajaDAO;
    }

    public void setCajaDAO(CajaDAO cajaDAO) {
        this.cajaDAO = cajaDAO;
    }

}

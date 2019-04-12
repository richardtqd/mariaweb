package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.CajaBean;
import pe.marista.sigma.bean.CajeroCajaBean;
import pe.marista.sigma.bean.PerfilBean;
import pe.marista.sigma.bean.PersonalBean;


public interface CajeroDAO {

    //Cajero CAja
    public List<CajaBean> obtenerCajasPorCajero(CajeroCajaBean cajeroCajaBean) throws Exception;
    public void insertarCajeroCaja(CajeroCajaBean cajeroCajaBean) throws Exception;
    public void modificarCajeroCaja(CajeroCajaBean cajeroCajaBean) throws Exception;
    public void eliminarCajaAll(CajeroCajaBean cajeroCajaBean) throws Exception;
    public List<CajeroCajaBean> obtenerUsuarioConCaja(String uniNeg) throws Exception;
    public CajeroCajaBean obtenerUsuarioConCajaPorId(CajeroCajaBean cajeroCajaBean) throws Exception;
    public List<CajaBean> obtenerCajaSinCajero(CajeroCajaBean cajeroCajaBean) throws Exception;
    public CajeroCajaBean autenticarUsuarioConCaja(CajeroCajaBean cajeroCajaBean) throws Exception;
    public CajeroCajaBean obtenerUsuarioCajaPorId(CajeroCajaBean cajeroCajaBean) throws Exception;
    
        
    //PersonalPerfil
    public List<PersonalBean> obtenerUsarioPerfil() throws Exception;
    
}

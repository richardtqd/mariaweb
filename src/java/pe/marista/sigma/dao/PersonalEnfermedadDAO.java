package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PersonalEnfermedadBean;

/**
 *
 * @author Administrador
 */
public interface PersonalEnfermedadDAO {
        
    public List<PersonalEnfermedadBean> obtenerPersonalEnfermedadPorPersonal(PersonalBean personalBean) throws Exception;
    
    public PersonalEnfermedadBean obtenerPersonalEnfermedadPorId(PersonalEnfermedadBean personalEnfermedadBean) throws Exception;
        
    public void insertarPersonalEnfermedad(PersonalEnfermedadBean personalEnfermedadBean) throws Exception;

    public void modificarPersonalEnfermedad(PersonalEnfermedadBean personalEnfermedadBean) throws Exception;

    public void eliminarPersonalEnfermedad(PersonalEnfermedadBean personalEnfermedadBean) throws Exception;
    
    public void cambiarEstadoEnfermedad(PersonalEnfermedadBean personalEnfermedadBean) throws Exception;
            
}

package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.EstudianteSeguroBean;

/**
 *
 * @author Administrador
 */
public interface EstudianteSeguroDAO {

    public void insertarEstudianteSeguro(EstudianteSeguroBean estudianteSeguroBean) throws Exception;
    public void insertarEstudianteSeguroRapido(EstudianteSeguroBean estudianteSeguroBean) throws Exception;

    public void modificarEstudianteSeguro(EstudianteSeguroBean estudianteSeguroBean) throws Exception;
    public void modificarEstudianteSeguroRapido(EstudianteSeguroBean estudianteSeguroBean) throws Exception;
    public void cambiarEstadoAllEstudianteSeguroRapido(EstudianteSeguroBean estudianteSeguroBean) throws Exception;

    public void eliminarEstudianteSeguro(EstudianteSeguroBean estudianteSeguroBean) throws Exception;

    public void cambiarEstadoEstudianteSeguro(EstudianteSeguroBean estudianteSeguroBean) throws Exception;

    public EstudianteSeguroBean obtenerEstudianteSeguroPorId(EstudianteSeguroBean estudianteSeguroBean) throws Exception;

    public List<EstudianteSeguroBean> obtenerEstudianteSeguro() throws Exception;
    
    public List<EstudianteSeguroBean> obtenerEstudianteSeguroPorEst(EstudianteBean estudianteBean) throws Exception;
    public List<EstudianteSeguroBean> obtenerEstudianteSeguroActivoPorEst(EstudianteBean estudianteBean) throws Exception;
    
    public EstudianteSeguroBean obtenerEstudianteSeguroSelect(EstudianteBean estudianteBean) throws Exception;
    
}

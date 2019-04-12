package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.CarreraBean;
 
public interface CarreraDAO {

    public List<CarreraBean> obtenerTodosCAR() throws Exception;
    
    public List<CarreraBean> obtenerCarreraPorNivelAcademico(Integer idNivelAcademico) throws Exception;
    
    public List<CarreraBean> obtenerCarreraPorGradoAca(Integer idGradoAcademico) throws Exception;
    
    public List<CarreraBean> obtenerCarreraPorCarreraArea(Integer idCarreraAreaBean) throws Exception;
    
    public List<CarreraBean> obtenerCarreraPorCarreraSubArea(Integer idCarreraSubArea) throws Exception;
    
    public CarreraBean obtenerCarreraPorId(Integer idCarrera) throws Exception;

    public void insertarCarrera(CarreraBean carreraBean) throws Exception;

    public void modificarCarrera(CarreraBean carreraBean) throws Exception;

    public void eliminarCarrera(Integer idCarrera) throws Exception;
   
}

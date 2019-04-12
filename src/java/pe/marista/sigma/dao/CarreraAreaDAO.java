package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.CarreraAreaBean;
import pe.marista.sigma.bean.GradoAcademicoBean;

public interface CarreraAreaDAO {

    public List<CarreraAreaBean> obtenerCarreraArea() throws Exception;

    public List<CarreraAreaBean> obtenerCarreraAreaPorNivelAcademico(Integer idNivelEstudio) throws Exception;

    public List<CarreraAreaBean> obtenerCarreraAreaPorGradoAcademico(Integer idGradoAcademico) throws Exception;
    
    public List<CarreraAreaBean> obtenerGAPorCarreraArea(GradoAcademicoBean gradoAcademicoBean) throws Exception;
    
    public void insertarCarreraArea(CarreraAreaBean carreraAreaBean) throws Exception;

    public void modificarCarreraArea(CarreraAreaBean carreraAreaBean) throws Exception;

    public void eliminarCarreraArea(Integer idCarreraArea) throws Exception;
   
    public CarreraAreaBean obtenerCarreraAreaPorId(Integer idCarreraArea) throws Exception;

}

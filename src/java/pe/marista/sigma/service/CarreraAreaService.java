package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.CarreraAreaBean;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.dao.CarreraAreaDAO;


/**
 *
 * @author Administrador
 */
public class CarreraAreaService{
    private CarreraAreaDAO carreraAreaDAO;
    
    public List<CarreraAreaBean> obtenerCarreraArea() throws Exception {
        return carreraAreaDAO.obtenerCarreraArea();
    }
 
    public List<CarreraAreaBean> obtenerCarreraAreaPorGradoAcademico(Integer idGradoAcademico) throws Exception{    
        return carreraAreaDAO.obtenerCarreraAreaPorGradoAcademico(idGradoAcademico); //Cambio
    }
  
    public List<CarreraAreaBean> obtenerCarreraAreaPorNivelAcademico(Integer idNivelEstudio) throws Exception{    
        return carreraAreaDAO.obtenerCarreraAreaPorNivelAcademico(idNivelEstudio);
    }

    public List<CarreraAreaBean> obtenerGAPorCarreraArea(GradoAcademicoBean gradoAcademicoBean) throws Exception {
        return carreraAreaDAO.obtenerGAPorCarreraArea(gradoAcademicoBean);
    }
    
    @Transactional
    public void insertarCarreraArea(CarreraAreaBean carreraAreaBean) throws Exception {
        carreraAreaDAO.insertarCarreraArea(carreraAreaBean);
    }

    @Transactional
    public void modificarCarreraArea(CarreraAreaBean carreraAreaBean) throws Exception {
        carreraAreaDAO.modificarCarreraArea(carreraAreaBean);
    }

    @Transactional
    public void eliminarCarreraArea(Integer idCarreraArea) throws Exception {
        carreraAreaDAO.eliminarCarreraArea(idCarreraArea);
    }

    public CarreraAreaBean obtenerCarreraAreaPorId(Integer idCarreraArea) throws Exception {
        return carreraAreaDAO.obtenerCarreraAreaPorId(idCarreraArea);
    }
    
    //metodos getter and setter 
    public CarreraAreaDAO getCarreraAreaDAO() {
        return carreraAreaDAO;
    }

    public void setCarreraAreaDAO(CarreraAreaDAO carreraAreaDAO) {
        this.carreraAreaDAO = carreraAreaDAO;
    }
    
  
}

package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.CarreraSubAreaBean;

public interface CarreraSubAreaDAO {

    public List<CarreraSubAreaBean> obtenerTodosSA() throws Exception;

    public List<CarreraSubAreaBean> obtenerCarreraSubAreaPorCarreraArea(Integer idCarreraSubArea) throws Exception;//obtenerCarreraSubAreaPorCarreraArea(Integer idCarreraSubArea) Cambio
   
    public List<CarreraSubAreaBean> obtenerCarreraSAreaPorArea() throws Exception;
    
    public void insertarSubArea(CarreraSubAreaBean carreraSubAreaBean) throws Exception;

    public void modificarSubArea(CarreraSubAreaBean carreraSubAreaBean) throws Exception;

    public void eliminarSubArea(Integer idCarreraSubArea) throws Exception;
   
    public CarreraSubAreaBean obtenerSubAreaPorId(Integer idCarreraSubArea) throws Exception;
    
}

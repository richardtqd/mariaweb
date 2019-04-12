package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.CargoBean;

public interface CargoDAO {
    
    public void insertarCargo(CargoBean cargoBean) throws Exception;
    public void modificarCargo(CargoBean cargoBean) throws Exception;
    public void eliminarCargo(Integer idCargo) throws Exception;
    
    public List<CargoBean> obtenerTodos() throws Exception;
    public List<CargoBean> obtenerTodosActivos() throws Exception;
    public CargoBean obtenerCargoPorId(Integer idCargo) throws Exception;
    public void cambiarEstado(CargoBean CargoBean) throws Exception;
    public String obtenerGrupoOcupacional(Integer idCodigo) throws Exception;
    
}

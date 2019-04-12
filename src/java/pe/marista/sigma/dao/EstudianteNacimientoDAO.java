package pe.marista.sigma.dao;
 
import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.EstudianteNacimientoBean;
import pe.marista.sigma.bean.reporte.EstudianteNacimientoRepBean;

public interface EstudianteNacimientoDAO {
    
    public void insertarEstudianteNacimiento(EstudianteNacimientoBean estudianteNacimientoBean) throws Exception;
    
    public void modificarEstudianteNacimiento(EstudianteNacimientoBean estudianteNacimientoBean) throws Exception;
    
    public EstudianteNacimientoBean obtenerEstNacimientoPorEst(String idEstudiante) throws Exception;
    
    //FichaClinica
    public List<EstudianteNacimientoRepBean> obtenerEstudianteNacimiento(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg) throws Exception;
}

package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.EstudianteTraumaBean;
import pe.marista.sigma.bean.reporte.EstudianteTraumaRepBean;

public interface EstudianteTraumaDAO {

    public void insertarEstTrauma(EstudianteTraumaBean estudianteTraumaBean) throws Exception;

    public void modificarEstTrauma(EstudianteTraumaBean estudianteTraumaBean) throws Exception;

    public void eliminarEstTrauma(EstudianteTraumaBean estudianteTraumaBean) throws Exception;

    public EstudianteTraumaBean obtenerEstTraumaPorId(EstudianteTraumaBean estudianteTraumaBean) throws Exception;

    public List<EstudianteTraumaBean> obtenerEstTraumaPorEst(String idEstudiante) throws Exception;
    
    //FichaClinica
    public List<EstudianteTraumaRepBean> obtenerTraumas(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg) throws Exception;
}

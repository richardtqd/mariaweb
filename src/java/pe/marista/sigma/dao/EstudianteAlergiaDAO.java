package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.EstudianteAlergiaBean;
import pe.marista.sigma.bean.reporte.EstudianteAlergiasRepBean;


/**
 *
 * @author Administrador
 */
public interface EstudianteAlergiaDAO {

    public void insertarEstAlergia(EstudianteAlergiaBean estudianteAlergiaBean) throws Exception;

    public void modificarEstAlergia(EstudianteAlergiaBean estudianteAlergiaBean) throws Exception;

    public void eliminarEstAlergia(EstudianteAlergiaBean estudianteAlergiaBean) throws Exception;

    public EstudianteAlergiaBean obtenerEstAlergiaPorId(EstudianteAlergiaBean estudianteAlergiaBean) throws Exception;

    public List<EstudianteAlergiaBean> obtenerEstAlergiaPorEst(String idEstudiante) throws Exception;
     //FichaClinica
    public List<EstudianteAlergiasRepBean> obtenerAlergias(@Param("idEstudiante") String idEstudiante, @Param("uniNeg") String uniNeg) throws Exception;
}

package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.EstudianteDocumentoBean;

public interface EstudianteDocumentoDAO {
 
//    public void modificarEstDocumento(EstudianteDocumentoBean estudianteDocumentoBean) throws Exception; 
    public void darCheckDocumentoAdmision(EstudianteDocumentoBean estudianteDocumentoBean) throws Exception;
    public EstudianteDocumentoBean eliminarEstDocumento(EstudianteDocumentoBean estudianteDocumentoBean) throws Exception;
    public List<EstudianteDocumentoBean> obtenerEstDocumentoPorEst(@Param("idEstudiante") String idEstudiante,@Param("uniNeg") String uniNeg,@Param("anio") Integer anio) throws Exception;
    public EstudianteDocumentoBean obtenerEstDocumentoPorId(EstudianteDocumentoBean estudianteDocumentoBean) throws Exception;
    
}

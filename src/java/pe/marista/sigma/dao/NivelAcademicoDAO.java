
package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.NivelAcademicoBean;
import pe.marista.sigma.bean.TipoFormacionBean;


public interface NivelAcademicoDAO {

    public void insertar(NivelAcademicoBean nivelAcademicoBean) throws Exception;

    public void actualizar(NivelAcademicoBean nivelAcademicoBean) throws Exception;

    public void eliminar(NivelAcademicoBean nivelAcademicoBean) throws Exception;

    public List<NivelAcademicoBean> obtenerTodos() throws Exception;

    public List<NivelAcademicoBean> obtenerPorFiltro(NivelAcademicoBean nivelAcademicoBean) throws Exception;
    
    public List<NivelAcademicoBean> obtenerPorTipoFormacion(Integer idTipoForm) throws Exception;
    public List<NivelAcademicoBean> obtenerPorTipoFormacionSinIniPriSec(Integer idTipoForm) throws Exception;

    public NivelAcademicoBean obtenerPorId(NivelAcademicoBean nivelAcademicoBean) throws Exception;
    
    //Carrera
    public List<NivelAcademicoBean> obtenerNivelAcaPorTipoFormacion(TipoFormacionBean tipoFormacionBean) throws Exception;
     
    
}

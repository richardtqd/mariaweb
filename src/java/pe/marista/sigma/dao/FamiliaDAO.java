package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.FamiliaBean;
import pe.marista.sigma.bean.PersonaBean;
//import pe.marista.sigma.bean.UnidadNegocioBean;

/**
 *
 * @author Administrador
 */
public interface FamiliaDAO {

    public void insertarFamilia(FamiliaBean familiaBean) throws Exception;

    public void insertarFamiliaRapido(FamiliaBean familiaBean) throws Exception;

    public void modificarFamilia(FamiliaBean familiaBean) throws Exception;

    public void modificarFamiliaRapido(FamiliaBean familiaBean) throws Exception;

    public void modificarFamiliaRapidoPapa(FamiliaBean familiaBean) throws Exception;

    public void modificarFamiliaRapidoMama(FamiliaBean familiaBean) throws Exception;

    public void eliminarFamilia(Integer idFamilia) throws Exception;

    public List<FamiliaBean> obtenerFamilia() throws Exception;

    public List<FamiliaBean> obtenerFiltroFamilia(FamiliaBean familiaBean) throws Exception;

//    public List<FamiliaBean> obtenerFamiliaPorUniNeg(UnidadNegocioBean unidadNegocioBean) throws Exception;
    public FamiliaBean obtenerFamiliaPorId(FamiliaBean familiaBean) throws Exception;

    public FamiliaBean obtenerFamiliaPorIdRapido(FamiliaBean familiaBean) throws Exception;

    public FamiliaBean obtenerFamiliaPorPaMa(FamiliaBean familiaBean) throws Exception;

    //Grupo Familiar
    public void actualizaFamiliaPorGrupo(FamiliaBean familiaBean) throws Exception;

    public Integer generaCodigoGrupoFamiliar(FamiliaBean familiaBean) throws Exception;

    public List<FamiliaBean> obtenerGrupoFamiliirPorId(FamiliaBean familiaBean) throws Exception;

    public List<FamiliaBean> obtenerGrupoFamiliirPorIdInverso(FamiliaBean familiaBean) throws Exception;

    public List<FamiliaBean> obtenerFiltroGrupoFamilia(FamiliaBean familiaBean) throws Exception;

    public List<PersonaBean> obtenerEstudiantePorGrupoFam(FamiliaBean familiaBean) throws Exception;
    
    public Boolean obtenerFamiliaPorPadresFam(@Param("idPadre") String idPadre, @Param("idMadre") String idMadre, @Param("uniNeg") String uniNeg) throws Exception;
    
    public Integer obtenerFamiliaId(@Param("idPadre") String idPadre, @Param("idMadre") String idMadre, @Param("uniNeg") String uniNeg,@Param("flgOpcion") Integer flgOpcion) throws Exception;
}

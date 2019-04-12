package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PersonalIdiomaBean;
import pe.marista.sigma.bean.reporte.PersonalIdiomaRepBean;

/**
 *
 * @author Administrador
 */
public interface PersonalIdiomaDAO {

    public List<PersonalIdiomaBean> obtenerPersonalIdiomaPorPersonal(PersonalBean personalBean) throws Exception;

    public PersonalIdiomaBean obtenerPersonalIdiomaPorId(PersonalIdiomaBean personalIdiomaBean) throws Exception;

    public void insertarPersonalIdioma(PersonalIdiomaBean personalIdiomaBean) throws Exception;

    public void modificarPersonalIdioma(PersonalIdiomaBean personalIdiomaBean) throws Exception;

    public void eliminarPersonalIdioma(PersonalIdiomaBean personalIdiomaBean) throws Exception;

    public List<PersonalIdiomaRepBean> obtenerIdiomaPorPersonalRep(@Param("idPersonal") Integer idPersonal, @Param("uniNeg") String uniNeg) throws Exception;

}

package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PersonalExperienciaBean;
import pe.marista.sigma.bean.reporte.PersonalExperienciaRepBean;

/**
 *
 * @author Administrador
 */
public interface PersonalExperienciaDAO {

    public List<PersonalExperienciaBean> obtenerPersonalExperienciaPorPersonal(PersonalBean personalBean) throws Exception;

    public List<PersonalExperienciaRepBean> obtenerExperienciaPorPersonalRep(@Param("idPersonal") Integer idPersonal, @Param("uniNeg") String uniNeg) throws Exception;

    public PersonalExperienciaBean obtenerPersonalExperienciaPorId(PersonalExperienciaBean personalExperienciaBean) throws Exception;

    public void insertarPersonalExperiencia(PersonalExperienciaBean personalExperienciaBean) throws Exception;

    public void modificarPersonalExperiencia(PersonalExperienciaBean personalExperienciaBean) throws Exception;

    public void eliminarPersonalExperiencia(PersonalExperienciaBean personalExperienciaBean) throws Exception;

}

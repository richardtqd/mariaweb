package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PersonalOtrosEstudiosBean;
import pe.marista.sigma.bean.reporte.PersonalOtrosEstudiosRepBean;

/**
 *
 * @author Administrador
 */
public interface PersonalOtrosEstudiosDAO {

    public List<PersonalOtrosEstudiosBean> obtenerPersonalOtrosEstudiosPorPersonal(PersonalBean personalBean) throws Exception;

    public PersonalOtrosEstudiosBean obtenerPersonalOtrosEstudiosPorId(PersonalOtrosEstudiosBean personalOtrosEstudiosBean) throws Exception;

    public void insertarPersonalOtrosEstudios(PersonalOtrosEstudiosBean personalOtrosEstudiosBean) throws Exception;

    public void modificarPersonalOtrosEstudios(PersonalOtrosEstudiosBean personalOtrosEstudiosBean) throws Exception;

    public void eliminarPersonalOtrosEstudios(PersonalOtrosEstudiosBean personalOtrosEstudiosBean) throws Exception;

    public List<PersonalOtrosEstudiosRepBean> obtenerOtrosEstudiosPorPersonalRep(@Param("idPersonal") Integer idPersonal, @Param("uniNeg") String uniNeg) throws Exception;

}

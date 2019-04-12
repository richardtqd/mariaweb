package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PersonalFormacionBean;
import pe.marista.sigma.bean.PersonalFormacionCarismaBean;
import pe.marista.sigma.bean.reporte.PersonalFormacionRepBean;

/**
 *
 * @author Administrador
 */
public interface PersonalFormacionDAO {

    public List<PersonalFormacionBean> obtenerPersonalFormacionSuperiorPorPersonal(PersonalBean personalBean) throws Exception;

    public List<PersonalFormacionBean> obtenerPersonalFormacionBasicaPorPersonal(PersonalBean personalBean) throws Exception;

    public List<PersonalFormacionBean> obtenerPersonalFormacionBasicaPorPersonalNew(PersonalBean personalBean) throws Exception;

    public PersonalFormacionBean obtenerPersonalFormacionSuperiorPorId(PersonalFormacionBean personalFormacionBean) throws Exception;

    public PersonalFormacionBean obtenerPersonalFormacionBasicaPorId(PersonalFormacionBean personalFormacionBean) throws Exception;

    public void insertarPersonalFormacionSuperior(PersonalFormacionBean personalFormacionBean) throws Exception;

    public void insertarPersonalFormacionBasica(PersonalFormacionBean personalFormacionBean) throws Exception;

    public void modificarPersonalFormacionSuperior(PersonalFormacionBean personalFormacionBean) throws Exception;

    public void modificarPersonalFormacionBasica(PersonalFormacionBean personalFormacionBean) throws Exception;

    public void eliminarPersonalFormacionSuperior(PersonalFormacionBean personalFormacionBean) throws Exception;

    public void eliminarPersonalFormacionBasica(PersonalFormacionBean personalFormacionBean) throws Exception;

    public List<PersonalFormacionRepBean> obtenerFormacionBasicaPorPersonalRep(@Param("idPersonal") Integer idPersonal, @Param("uniNeg") String uniNeg) throws Exception;

    public List<PersonalFormacionRepBean> obtenerFormacionSuperiorPorPersonalRep(@Param("idPersonal") Integer idPersonal, @Param("uniNeg") String uniNeg) throws Exception;

    //Formacion Carisma
    public void insertarPersonalFormacionCarisma(PersonalFormacionCarismaBean personalFormacionCarismaBean) throws Exception;

    public void modificarPersonalFormacionCarisma(PersonalFormacionCarismaBean personalFormacionCarismaBean) throws Exception;

    public void eliminarPersonalFormacionCarisma(PersonalFormacionCarismaBean personalFormacionCarismaBean) throws Exception;
    
    public PersonalFormacionCarismaBean obtenerPersonalFormacionCarismaID(PersonalFormacionCarismaBean personalFormacionCarismaBean) throws Exception;
    
    public List<PersonalFormacionCarismaBean> obtenerPersonalFormacionCarismaLista(PersonalFormacionCarismaBean personalFormacionCarismaBean) throws Exception;
}

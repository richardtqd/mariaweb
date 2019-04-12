package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PersonalCargoBean;
import pe.marista.sigma.bean.reporte.PersonalCargoRepBean;

public interface PersonalCargoDAO {

    public List<PersonalCargoBean> obtenerPersonalCargoPorPersonal(PersonalBean personalBean) throws Exception;

    public PersonalCargoBean obtenerPersonalCargoPorId(PersonalCargoBean personalCargoBean) throws Exception;

    public PersonalCargoBean obtenerCargoActivoPorPersonal(PersonalBean personalBean) throws Exception;

    public void insertarPersonalCargo(PersonalCargoBean personalCargoBean) throws Exception;

    public void modificarPersonalCargo(PersonalCargoBean personalCargoBean) throws Exception;

    public void eliminarPersonalCargo(PersonalCargoBean personalCargoBean) throws Exception;

    public void cambiarEstadoCargo(PersonalCargoBean personalCargoBean) throws Exception;

    public List<PersonalCargoRepBean> obtenerCargoPorPersonalRep(@Param("idPersonal") Integer idPersonal, @Param("uniNeg") String uniNeg) throws Exception;

}

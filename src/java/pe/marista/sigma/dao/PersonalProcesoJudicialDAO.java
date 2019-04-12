package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PersonalProcesoJudicialBean; 
import pe.marista.sigma.bean.reporte.PersonalProcesoJudicialRepBean;

public interface PersonalProcesoJudicialDAO {

    public List<PersonalProcesoJudicialBean> obtenerPersonalProcesoJudicialPorPersonal(PersonalBean personalBean) throws Exception;

    public PersonalProcesoJudicialBean obtenerPersonalProcesoJudicialPorId(PersonalProcesoJudicialBean personalProcesoJudicialBean) throws Exception;

    public void insertarPersonalProcesoJudicial(PersonalProcesoJudicialBean personalProcesoJudicialBean) throws Exception;

    public void modificarPersonalProcesoJudicial(PersonalProcesoJudicialBean personalProcesoJudicialBean) throws Exception;

    public void eliminarPersonalProcesoJudicial(PersonalProcesoJudicialBean personalProcesoJudicialBean) throws Exception;

    public void cambiarEstadoProcesoJudicial(PersonalProcesoJudicialBean personalProcesoJudicialBean) throws Exception;

    public List<PersonalProcesoJudicialRepBean> obtenerProcesoJudicialPorPersonalRep(@Param("idPersonal") Integer idPersonal, @Param("uniNeg") String uniNeg) throws Exception;

}

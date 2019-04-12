package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.PersonalAlergiaBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PersonalDescansoMedicoBean;
import pe.marista.sigma.bean.PersonalEvaPsicologicaBean;

/**
 *
 * @author Administrador
 */
public interface PersonalAlergiaDAO {

    public List<PersonalAlergiaBean> obtenerPersonalAlergiaPorPersonal(PersonalBean personalBean) throws Exception;

    public PersonalAlergiaBean obtenerPersonalAlergiaPorId(PersonalAlergiaBean personalAlergiaBean) throws Exception;

    public void insertarPersonalAlergia(PersonalAlergiaBean personalAlergiaBean) throws Exception;

    public void modificarPersonalAlergia(PersonalAlergiaBean personalAlergiaBean) throws Exception;

    public void eliminarPersonalAlergia(PersonalAlergiaBean personalAlergiaBean) throws Exception;

//    public void cambiarEstadoAlergia(PersonalAlergiaBean personalAlergiaBean) throws Exception;
    //Descanso Medico
    public List<PersonalDescansoMedicoBean> obtenerPersonalDescansoMedico(PersonalDescansoMedicoBean personalDescansoMedicoBean) throws Exception;

    public List<PersonalDescansoMedicoBean> obtenerPersonalInasistencia(PersonalDescansoMedicoBean personalDescansoMedicoBean) throws Exception;

    public List<PersonalDescansoMedicoBean> obtenerPersonalAccidente(PersonalDescansoMedicoBean personalDescansoMedicoBean) throws Exception;

    public PersonalDescansoMedicoBean obtenerPersonalDescansoMedicoPorId(PersonalDescansoMedicoBean personalDescansoMedicoBean) throws Exception;

    public void insertarPersonalDescansoMedico(PersonalDescansoMedicoBean personalDescansoMedicoBean) throws Exception;

    public void modificarPersonalDescansoMedico(PersonalDescansoMedicoBean personalDescansoMedicoBean) throws Exception;

    public void eliminarPersonalDescansoMedico(PersonalDescansoMedicoBean personalDescansoMedicoBean) throws Exception;

    //Evaluacion Psicologica
    public List<PersonalEvaPsicologicaBean> obtenerPersonalEvaPsicologica(PersonalEvaPsicologicaBean personalEvaPsicologicaBean) throws Exception;

    public PersonalEvaPsicologicaBean obtenerPersonalEvaPsicologicaPorId(PersonalEvaPsicologicaBean personalEvaPsicologicaBean) throws Exception;

    public void insertarPersonalEvaPsicologica(PersonalEvaPsicologicaBean personalEvaPsicologicaBean) throws Exception;

    public void modificarPersonalEvaPsicologica(PersonalEvaPsicologicaBean personalEvaPsicologicaBean) throws Exception;

    public void eliminarPersonalEvaPsicologica(PersonalEvaPsicologicaBean personalEvaPsicologicaBean) throws Exception;
}

package pe.marista.sigma.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PersonalDocumentoBean;
import pe.marista.sigma.bean.PersonalPDFBean;
import pe.marista.sigma.bean.reporte.PersonalDocumentoRepBean;

/**
 *
 * @author Administrador
 */
public interface PersonalDocumentoDAO {

    public List<PersonalDocumentoBean> obtenerPersonalDocumentoPorPersonal(PersonalBean personalBean) throws Exception;
     
    public PersonalDocumentoBean obtenerPersonalDocumentoPorId(PersonalDocumentoBean personalDocumentoBean) throws Exception;

    public void insertarPersonalDocumento(PersonalDocumentoBean personalDocumentoBean) throws Exception;

    public void modificarPersonalDocumento(PersonalDocumentoBean personalDocumentoBean) throws Exception;

    public void eliminarPersonalDocumento(PersonalDocumentoBean personalDocumentoBean) throws Exception;

    public List<PersonalDocumentoBean> obtenerDocPorCargo(Integer idCargo) throws Exception;

    public List<PersonalDocumentoRepBean> obtenerDocumentoPorPersonalRep(@Param("idPersonal") Integer idPersonal, @Param("uniNeg") String uniNeg) throws Exception;
    
    public void insertarPersonalPDF(PersonalPDFBean personalPDFBean) throws Exception;

    public List<PersonalPDFBean> obtenerPersonalPDFPorPersonal(PersonalBean personalBean) throws Exception;
}

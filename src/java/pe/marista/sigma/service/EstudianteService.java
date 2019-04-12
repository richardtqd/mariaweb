package pe.marista.sigma.service;

import com.google.common.io.ByteStreams;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.AdmisionBean;
import pe.marista.sigma.bean.BloqueoBean;
import pe.marista.sigma.bean.CodigoColegioBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.EstudianteInfoBean;
import pe.marista.sigma.bean.FamiliaBean;
import pe.marista.sigma.bean.FamiliarEstudianteBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.EstudianteEnfermedadPadresRepBean;
import pe.marista.sigma.bean.reporte.EstudianteMatriculaRepBean;
import pe.marista.sigma.bean.reporte.EstudianteRepBean;
import pe.marista.sigma.bean.reporte.FamiliarEstudianteRepBean;
import pe.marista.sigma.bean.reporte.FichaClinicaEstudianteRepBean;
import pe.marista.sigma.bean.reporte.FichaEstudianteRepBean;
import pe.marista.sigma.bean.reporte.ResponsableEconomicoRepBean;
import pe.marista.sigma.dao.AdmisionDAO;
import pe.marista.sigma.dao.EstudianteDAO;
import pe.marista.sigma.dao.PersonaDAO;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.util.Mailing;

/**
 *
 * @author Administrador
 */
public class EstudianteService {
    
    private EstudianteDAO estudianteDAO;
    private PersonaDAO personaDAO;
    private AdmisionDAO admisionDAO;

    //LÃ³gica de Negocio
    public List<EstudianteBean> obtenerEstudiante() throws Exception {
        return estudianteDAO.obtenerEstudiante();
    }
    
    public List<EstudianteBean> obtenerEstudiantePost() throws Exception {
        return estudianteDAO.obtenerEstudiantePost();
    }
    
    public List<EstudianteBean> obtenerEstudiantePorUniNeg(String uniNeg) throws Exception {
        return estudianteDAO.obtenerEstudiantePorUniNeg(uniNeg);
    }
    
    public List<EstudianteBean> obtenerEstudiantePostPorUniNeg(String uniNeg) throws Exception {
        return estudianteDAO.obtenerEstudiantePostPorUniNeg(uniNeg);
    }
    
    public List<EstudianteBean> obtenerFiltroEstudiante(EstudianteBean estudianteBean) throws Exception {
        estudianteBean.getTipoStatusEst().setCodigo(MaristaConstantes.COD_POSTULANTE);
        estudianteBean.getTipoStatusEst().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STATUS_EST);
        return estudianteDAO.obtenerFiltroEstudiante(estudianteBean);
    }
    
    public List<EstudianteBean> obtenerFiltroEstudianteFicha(EstudianteBean estudianteBean) throws Exception {
        estudianteBean.getTipoStatusEst().setCodigo(MaristaConstantes.COD_POSTULANTE);
        estudianteBean.getTipoStatusEst().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STATUS_EST);
        return estudianteDAO.obtenerFiltroEstudianteFicha(estudianteBean);
    }
    
    public List<EstudianteBean> obtenerFiltroEstudianteActivo(EstudianteBean estudianteBean) throws Exception {
        estudianteBean.setIdTipoStatusEst1(MaristaConstantes.COD_ESTUDIANTE_ACTIVO);
        estudianteBean.setIdTipoStatusEst2(MaristaConstantes.COD_EST_CONDI);
        return estudianteDAO.obtenerFiltroEstudianteActivo(estudianteBean);
    }
    
    public List<EstudianteBean> obtenerFiltroEstudiantePros(EstudianteBean estudianteBean) throws Exception {
        estudianteBean.getTipoStatusEst().setCodigo(MaristaConstantes.COD_POSTULANTE);
        estudianteBean.getTipoStatusEst().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STATUS_EST);
        return estudianteDAO.obtenerFiltroEstudiantePros(estudianteBean);
    }
    
    public List<EstudianteBean> obtenerFiltroEstudianteEspecial(EstudianteBean estudianteBean) throws Exception {
        estudianteBean.getTipoStatusEst().setCodigo(MaristaConstantes.ACTIVO);
        estudianteBean.getTipoStatusEst().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STATUS_EST);
        return estudianteDAO.obtenerFiltroEstudiantePros(estudianteBean);
    }
    
    public List<EstudianteBean> obtenerFiltroEstudiantePost(EstudianteBean estudianteBean) throws Exception {
        estudianteBean.getTipoStatusEst().setCodigo(MaristaConstantes.COD_POSTULANTE);
        estudianteBean.getTipoStatusEst().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STATUS_EST);
        return estudianteDAO.obtenerFiltroEstudiantePost(estudianteBean);
    }
//    public List<EstudianteBean> obtenerFiltroEstudianteMasivo(EstudianteBean estudianteBean) throws Exception {
//        return estudianteDAO.obtenerFiltroEstudianteMasivo(estudianteBean);
//    }

    public EstudianteBean obtenerEstPorId(EstudianteBean bean) throws Exception {
        EstudianteBean estudianteBean = estudianteDAO.obtenerEstPorId(bean);
        if (estudianteBean != null) {
            PersonaBean personaBean = personaDAO.obtenerPersPorId(estudianteBean.getPersonaBean());
            estudianteBean.setPersonaBean(personaBean);
        }
        return estudianteBean;
    }
    
    public EstudianteBean obtenerEstudianteGradoAca(EstudianteBean estudianteBean) throws Exception {
        return estudianteDAO.obtenerEstudianteGradoAca(estudianteBean);
    }
    
    @Transactional
    public void insertarEstudiante(EstudianteBean estudianteBean, String tipo, UsuarioBean usuario) throws Exception {
        PersonaBean bean = personaDAO.obtenerPersPorId(estudianteBean.getPersonaBean());
        if (bean == null) {
            personaDAO.insertarPersona(estudianteBean.getPersonaBean());
        }
        if (estudianteBean.getFile() != null) {
            guardarArchivo(estudianteBean);
        } else {
        }
        
        estudianteBean.getTipoStatusEst().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STATUS_EST);
        if (tipo.equals("postulante")) {
            estudianteBean.getTipoStatusEst().setCodigo(MaristaConstantes.COD_POSTULANTE);
        }        
        EntidadService entidadService = BeanFactory.getEntidadService();
        EntidadBean entidadBean = new EntidadBean();
        entidadBean = entidadService.obtenerInfoEntidad(estudianteBean.getUnidadNegocioBean().getUniNeg());
        
        
//        Se desactivo porque no pueden ser alumnos y solo queden como porstulantes y no es de nuestro interes crear a la familia.
//        if (estudianteBean.getFamiliaBean().getIdFamilia() == null) {
//            FamiliaService familiaService = BeanFactory.getFamiliaService();
//            FamiliaBean familiaBean = new FamiliaBean();
//            familiaBean.getUnidadNegocioBean().setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            familiaBean.setNombre(estudianteBean.getPersonaBean().getApepat() + " " + estudianteBean.getPersonaBean().getApemat());
//            familiaBean.setStatus(Boolean.TRUE);
//            familiaBean.setCreaPor(usuario.getUsuario());
//            familiaService.insertarFamiliaRapido(familiaBean);
//            estudianteBean.getFamiliaBean().setIdFamilia(familiaBean.getIdFamilia());
//        }
        estudianteBean.setDniEstudiante(estudianteBean.getPersonaBean().getNroDoc());
        estudianteDAO.insertarEstudiante(estudianteBean);

//        if (estudianteBean.getPersonaBean().getCorreo().trim() != null && !estudianteBean.getPersonaBean().getCorreo().trim().equals("")
//                && estudianteBean.getPersonaBean().getCorreo() != null && !estudianteBean.getPersonaBean().getCorreo().equals("")) {
//            new Mailing().enviarCorreoEstudiante(estudianteBean, entidadBean);
//        }
    }
    
    public EstudianteBean obtenerEstudiantePorCodigo(String codigo, String uniNeg) throws Exception {
        return estudianteDAO.obtenerEstudiantePorCodigo(codigo, uniNeg);
    }
    
    @Transactional
    public void insertarEstudianteEspecial(EstudianteBean estudianteBean, String tipo) throws Exception {
        PersonaBean bean = personaDAO.obtenerPersPorId(estudianteBean.getPersonaBean());
        AdmisionBean admisionBean = new AdmisionBean();
        GregorianCalendar fechaActual = new GregorianCalendar();
        
        System.out.println(">>>>" + estudianteBean.getPersonaBean().getIdPersona() + fechaActual.YEAR);
        System.out.println(">>>>" + estudianteBean.getPersonaBean().getGradoAcademicoBean().getIdGradoAcademico());
        estudianteBean.getGradoHabilitado().setIdGradoAcademico(estudianteBean.getPersonaBean().getGradoAcademicoBean().getIdGradoAcademico());
        if (bean == null) {
            personaDAO.insertarPersona(estudianteBean.getPersonaBean());
        }
        if (estudianteBean.getFile() != null) {
            guardarArchivo(estudianteBean);
        }
        
        estudianteBean.getTipoStatusEst().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STATUS_EST);
        if (tipo.equals("postulante")) {
            estudianteBean.getTipoStatusEst().setCodigo(MaristaConstantes.COD_POSTULANTE);
        } else if (tipo.equals("Activo")) {
            estudianteBean.getTipoStatusEst().setCodigo(MaristaConstantes.ACTIVO);
            admisionBean.getUnidadNegocioBean().setUniNeg(estudianteBean.getUnidadNegocioBean().getUniNeg());
            admisionBean.getEstudianteBean().getPersonaBean().setIdPersona(estudianteBean.getPersonaBean().getIdPersona());
            admisionBean.setFechaIngreso(fechaActual.getTime());
            admisionBean.getCodigoBean().setIdCodigo(MaristaConstantes.COD_ESTUDIANTE_ACTIVO);
            admisionBean.getGradoAcademicoBean().setIdGradoAcademico(estudianteBean.getPersonaBean().getGradoAcademicoBean().getIdGradoAcademico());
            admisionBean.setAnio(fechaActual.YEAR);
            admisionBean.setFecExamen(fechaActual.getTime());
            admisionBean.setFechaInscripcion(fechaActual.getTime());
            admisionBean.setHoraExamen(fechaActual.getTime());
            admisionBean.setGrupo(MaristaConstantes.Grupo_Especial);
            admisionBean.setCreaPor(estudianteBean.getCreaPor());
        }
        
        estudianteDAO.insertarEstudiante(estudianteBean);
        admisionDAO.insertarAdmisionEspecial(admisionBean);
        
        EntidadService entidadService = BeanFactory.getEntidadService();
        EntidadBean entidadBean = new EntidadBean();
        entidadBean = entidadService.obtenerInfoEntidad(estudianteBean.getUnidadNegocioBean().getUniNeg());
        
        if (estudianteBean.getPersonaBean().getCorreo().trim() != null && !estudianteBean.getPersonaBean().getCorreo().trim().equals("")
                && estudianteBean.getPersonaBean().getCorreo() != null && !estudianteBean.getPersonaBean().getCorreo().equals("")) {
            new Mailing().enviarCorreoEstudiante(estudianteBean, entidadBean);
        }
    }
    
    @Transactional
    public void actualizarGrupoSanguineo(EstudianteBean estudianteBean) throws Exception {
        estudianteDAO.actualizarGrupoSanguineo(estudianteBean);
    }
    
    @Transactional
    public void cambiarGradoAcademico(EstudianteBean estudianteBean) throws Exception {
        estudianteDAO.cambiarGradoAcademico(estudianteBean);
    }
    
    @Transactional
    public void modificarEstudiante(EstudianteBean estudianteBean, String tipo, UsuarioBean usuario) throws Exception {
        System.out.println("sex " + estudianteBean.getPersonaBean().getSexo());
        System.out.println("fec " + estudianteBean.getPersonaBean().getFecNac());
        personaDAO.modificarPersona(estudianteBean.getPersonaBean());
        if (estudianteBean.getFile() != null) {
            guardarArchivo(estudianteBean);
        } else {
        }
        estudianteBean.getTipoStatusEst().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STATUS_EST);
        if (tipo.equals("postulante")) {
            estudianteBean.getTipoStatusEst().setCodigo(MaristaConstantes.COD_POSTULANTE);
            
            estudianteBean.getTipoStatusEst().setCodigo(MaristaConstantes.COD_POSTULANTE);
        } else if (tipo.equals("Activo")) {
            estudianteBean.getTipoStatusEst().setCodigo(MaristaConstantes.ACTIVO);
            
        }        
//        FamiliaService familiaService = BeanFactory.getFamiliaService();
//        if (estudianteBean.getFamiliaBean().getIdFamilia() == null) {            
//            FamiliaBean familiaBean = new FamiliaBean();
//            familiaBean.getUnidadNegocioBean().setUniNeg(usuario.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            familiaBean.setNombre(estudianteBean.getPersonaBean().getApepat() + " " + estudianteBean.getPersonaBean().getApemat());
//            familiaBean.setStatus(Boolean.TRUE);
//            familiaBean.setCreaPor(usuario.getUsuario());
//            familiaService.insertarFamiliaRapido(familiaBean);
//            estudianteBean.getFamiliaBean().setIdFamilia(familiaBean.getIdFamilia());
//        }        
        estudianteBean.setDniEstudiante(estudianteBean.getPersonaBean().getNroDoc());
        estudianteDAO.modificarEstudiante(estudianteBean);
        FamiliarService familiarService = BeanFactory.getFamiliarService();
        FamiliarEstudianteBean familiarEstudianteBean = new FamiliarEstudianteBean();
        familiarEstudianteBean.getEstudianteBean().getPersonaBean().setIdPersona(estudianteBean.getPersonaBean().getIdPersona());
        familiarEstudianteBean.setDniEstudiante(estudianteBean.getPersonaBean().getNroDoc());
        familiarEstudianteBean.setModiPor(usuario.getUsuario());
        familiarService.modificarDniEstudianteFamEst(familiarEstudianteBean);
        EstudianteInfoBean estudianteInfoBean = new EstudianteInfoBean();
        estudianteInfoBean.getEstudianteBean().getPersonaBean().setIdPersona(estudianteBean.getPersonaBean().getIdPersona());
        estudianteInfoBean.setDniEstudiante(estudianteBean.getPersonaBean().getNroDoc());
        estudianteDAO.modificarDNIEstudianteInfo(estudianteInfoBean);
        
    }

    //en admisión paso los valores de codigo
    @Transactional
    public void cambiarDatosIngresoEstudiante(EstudianteBean estudianteBean) throws Exception {
        estudianteDAO.cambiarDatosIngresoEstudiante(estudianteBean);
    }
    
    @Transactional
    public void cambiarDatosEgresoEstudiante(EstudianteBean estudianteBean) throws Exception {
        estudianteDAO.cambiarDatosEgresoEstudiante(estudianteBean);
    }
    
    @Transactional
    public void eliminarEstudiante(String idEstudiante) throws Exception {
        estudianteDAO.eliminarEstudiante(idEstudiante);
    }
    
    public void guardarArchivo(EstudianteBean estudianteBean) throws Exception {
        //1.- Setteando el valor en base de datos
        StringBuilder rutaFoto = new StringBuilder();
        rutaFoto.append("\\").append(MaristaConstantes.RUTA_DOCU_EST).append(estudianteBean.getPersonaBean().getIdPersona()).append(".jpg");
        estudianteBean.setFoto(rutaFoto.toString());
        //2.- Creando el valor en base de datos
        StringBuilder rutaGral = new StringBuilder();
        InputStream inputStream = estudianteBean.getFile().getInputstream();
        rutaGral.append(MaristaConstantes.RUTA_DOCUMENTOS).append(rutaFoto);
        System.out.println("ruta: " + rutaGral);
        File foto = new File(rutaGral.toString());
        FileOutputStream miArchivo = new FileOutputStream(foto);
        byte[] bytes = ByteStreams.toByteArray(inputStream);
        miArchivo.write(bytes);
        miArchivo.close();

//        String cadena = MaristaUtils.obtenerRealPath().toString().substring(0, MaristaUtils.obtenerRealPath().length() - 9);
//        File foto2 = new File(cadena + "\\web\\" + MaristaConstantes.RUTA_DOCU_EST + estudianteBean.getCodigo() + ".jpg");
//        FileOutputStream miArchivo2 = new FileOutputStream(foto2);
//        miArchivo2.write(bytes);
//        miArchivo2.close();
        inputStream.close();
    }

    //EstudianteInfo
    public EstudianteInfoBean obtenerEstudianteInfoPorId(String idEstudiante) throws Exception {
        return estudianteDAO.obtenerEstudianteInfoPorId(idEstudiante);
    }
    
    @Transactional
    public void insertarEstudianteInfo(EstudianteInfoBean estudianteInfoBean, EstudianteBean estudianteBean) throws Exception {
        estudianteDAO.insertarEstudianteInfo(estudianteInfoBean);
        personaDAO.modificarResponsable(estudianteBean);
    }
    
    @Transactional
    public void modificarEstudianteInfo(EstudianteInfoBean estudianteInfoBean, EstudianteBean estudianteBean) throws Exception {
        estudianteDAO.modificarEstudianteInfo(estudianteInfoBean);
        personaDAO.modificarResponsable(estudianteBean);
    }
     
    @Transactional
    public void eliminarEstudianteInfo(String idEstudiante) throws Exception {
        estudianteDAO.eliminarEstudianteInfo(idEstudiante);
    }

    //Filtros de Admision
    public List<EstudianteBean> obtenerEstudianteAminPorUniNeg(String uniNeg) throws Exception {
        return estudianteDAO.obtenerEstudianteAminPorUniNeg(uniNeg);
    }

    //Filtros de Matricula
    public List<EstudianteBean> obtenerEstudianteMatPorUniNeg(String codAdmi, String uniNeg) throws Exception {
        Map<String, Object> parms = new HashMap<>();
        parms.put("codAdmi", codAdmi);
        parms.put("uniNeg", uniNeg);
//        parms.put("periodo", periodo);
        return estudianteDAO.obtenerEstudianteMatPorUniNeg(parms);
    }

    //Reportes
    public List<EstudianteRepBean> obtenerEstudianteRepPorId(EstudianteBean estudianteBean) throws Exception {
        return estudianteDAO.obtenerEstudianteRepPorId(estudianteBean);
    }

    //Metodos Getter y Setter
    public EstudianteDAO getEstudianteDAO() {
        return estudianteDAO;
    }
    
    public void setEstudianteDAO(EstudianteDAO estudianteDAO) {
        this.estudianteDAO = estudianteDAO;
    }
    
    public PersonaDAO getPersonaDAO() {
        return personaDAO;
    }
    
    public void setPersonaDAO(PersonaDAO personaDAO) {
        this.personaDAO = personaDAO;
    }
    
    public void modificarTipoStatusEst(EstudianteBean estudianteBean) throws Exception {
        estudianteDAO.modificarTipoStatusEst(estudianteBean);
    }

    //FichaEstudiante
    public List<FichaEstudianteRepBean> obtenerFichaEstudianteCabecera(String idEstudiante, String uniNeg) throws Exception {
        return estudianteDAO.obtenerFichaEstudianteCabecera(idEstudiante, uniNeg);
    }
    
    public List<FamiliarEstudianteRepBean> obtenerDetPadres(String idEstudiante, String uniNeg) throws Exception {
        return estudianteDAO.obtenerDetPadres(idEstudiante, uniNeg);
    }
    
    public List<ResponsableEconomicoRepBean> obtenerResponEconomico(String idEstudiante, String uniNeg) throws Exception {
        return estudianteDAO.obtenerResponEconomico(idEstudiante, uniNeg);
    }
    
    public AdmisionDAO getAdmisionDAO() {
        return admisionDAO;
    }
    
    public void setAdmisionDAO(AdmisionDAO admisionDAO) {
        this.admisionDAO = admisionDAO;
    }
    
    public void actualizarDatosNacimiento(EstudianteBean estudianteBean) throws Exception {
        estudianteDAO.actualizarDatosNacimiento(estudianteBean);
    }
    
    public List<FichaClinicaEstudianteRepBean> obtenerFichaClinicaEstudiante(String idEstudiante, String uniNeg) throws Exception {
        return estudianteDAO.obtenerFichaClinicaEstudiante(idEstudiante, uniNeg);
    }
    
    public List<EstudianteEnfermedadPadresRepBean> obtenerEstudianteInfoEnfPadres(String idEstudiante, String uniNeg) throws Exception {
        return estudianteDAO.obtenerEstudianteInfoEnfPadres(idEstudiante, uniNeg);
    }
    
    public EstudianteBean obtenerEstPorCodigo(EstudianteBean estudianteBean) throws Exception {
        return estudianteDAO.obtenerEstPorCodigo(estudianteBean);
    }
    
    @Transactional
    public Object execProBloqueo(BloqueoBean bloqueoBean) throws Exception {
        return estudianteDAO.execProBloqueo(bloqueoBean);
    }
    
    @Transactional
    public Object execProBloqueoLibera(BloqueoBean bloqueoBean) throws Exception {
        return estudianteDAO.execProBloqueoLibera(bloqueoBean);
    }
    
    @Transactional
    public EstudianteBean SP_actualizarEstadoEst(EstudianteBean estudianteBean) throws Exception {
        return estudianteDAO.SP_actualizarEstadoEst(estudianteBean);
    }
    
    public List<EstudianteBean> obtenerFiltroMatriculadosPorUsuario(EstudianteBean estudianteBean) throws Exception {
        return estudianteDAO.obtenerFiltroMatriculadosPorUsuario(estudianteBean);
    }
    
    public Object execProUnirFamilia(String uniNeg) throws Exception {
        return estudianteDAO.execProUnirFamilia(uniNeg);
    }
    
    public List<CodigoColegioBean> obtenerCodigoPorFiltro(CodigoColegioBean codigoColegioBean) throws Exception {
        return estudianteDAO.obtenerCodigoPorFiltro(codigoColegioBean);
    }
    
    public void insertarCodigoColegio(CodigoColegioBean codigoColegioBean) throws Exception {
        estudianteDAO.insertarCodigoColegio(codigoColegioBean);
    }
    
    public void modificarCodigoColegio(CodigoColegioBean codigoColegioBean) throws Exception {
        estudianteDAO.modificarCodigoColegio(codigoColegioBean);
    }
    
    public void eliminarCodigoColegio(CodigoColegioBean codigoColegioBean) throws Exception {
        estudianteDAO.eliminarCodigoColegio(codigoColegioBean);
    }
    
    public CodigoColegioBean obtenerCodigoColegioPorId(CodigoColegioBean codigoColegioBean) throws Exception {
        return estudianteDAO.obtenerCodigoColegioPorId(codigoColegioBean);
    }
    
    public CodigoColegioBean obtenerCodigoColegioPorCodigo(CodigoColegioBean codigoColegioBean) throws Exception {
        return estudianteDAO.obtenerCodigoColegioPorCodigo(codigoColegioBean);
    }
    
    public void modificarEstFam(EstudianteBean estudianteBean) throws Exception {
        estudianteDAO.modificarEstFam(estudianteBean);
    } 
    
    public void modificarEstudianteSeccion(String idEstudiante, String seccion) throws Exception {
        estudianteDAO.modificarEstudianteSeccion(idEstudiante, seccion);
    } 

    public List<EstudianteMatriculaRepBean> obtenerAlumnoMatricula(String idEstudiante, String uniNeg, Integer anio) throws Exception {
        return estudianteDAO.obtenerAlumnoMatricula(idEstudiante, uniNeg, anio);
    } 

    public void modificarEstudianteRespMat(EstudianteBean estudianteBean) throws Exception {
        estudianteDAO.modificarEstudianteRespMat(estudianteBean);
    }

    public String existeCodigo(EstudianteBean estudianteBean) throws Exception {
        return estudianteDAO.existeCodigo(estudianteBean);
    }

    public void modificarEstudianteSeccionyRetiro(String idEstudiante, String seccion, Date fechaStatusEst, String motivoStatusEst) throws Exception {
        estudianteDAO.modificarEstudianteSeccionyRetiro(idEstudiante, seccion, fechaStatusEst, motivoStatusEst);
    }

    public EstudianteBean obtenerEstPorIdMatricula(EstudianteBean estudianteBean) throws Exception {
        return estudianteDAO.obtenerEstPorIdMatricula(estudianteBean);
    }

    public CodigoColegioBean obtenerCodigoColegioPorCodigoSanjoh(CodigoColegioBean codigoColegioBean) throws Exception {
        return estudianteDAO.obtenerCodigoColegioPorCodigoSanjoh(codigoColegioBean);
    }

    public Integer obtenerIdTipoResp(String idEstudiante) throws Exception {
        return estudianteDAO.obtenerIdTipoResp(idEstudiante);
    }

    public void modificarSoloDniRespPago(EstudianteBean estudianteBean) throws Exception {
        estudianteDAO.modificarSoloDniRespPago(estudianteBean);
    }

    public String obtenerIdResPago(String idEstudiante, Integer idTipoParentesco) throws Exception {
        return estudianteDAO.obtenerIdResPago(idEstudiante, idTipoParentesco);
    }

    public Integer obtenerSiAlumnoEsMatriculado(String idEstudiante, Integer anio) throws Exception {
        return estudianteDAO.obtenerSiAlumnoEsMatriculado(idEstudiante, anio);
    } 

    public Boolean obtenerEstudianteActivoProspecto(String idEstudiante, String uniNeg) throws Exception {
        return estudianteDAO.obtenerEstudianteActivoProspecto(idEstudiante, uniNeg);
    }

    public Integer obtenerEstPorIdFamilia(String idEstudiante, String uniNeg) {
        return estudianteDAO.obtenerEstPorIdFamilia(idEstudiante, uniNeg);
    }

    public void modificarFamiliaEstudiante(EstudianteBean estudianteBean) throws Exception {
        estudianteDAO.modificarFamiliaEstudiante(estudianteBean);
    }

    public void modificarDNIEstudianteInfo(EstudianteInfoBean estudianteInfoBean) throws Exception {
        estudianteDAO.modificarDNIEstudianteInfo(estudianteInfoBean);
    }
    
}

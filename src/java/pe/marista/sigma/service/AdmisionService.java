package pe.marista.sigma.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.AdmisionBean;
import pe.marista.sigma.bean.CodigoColegioBean;
import pe.marista.sigma.bean.EntidadBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.EstudianteDocumentoBean;
import pe.marista.sigma.bean.EstudianteInfoBean;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.ProgramacionBean;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.AdmisionEstudiantesRepBean;
import pe.marista.sigma.dao.AdmisionDAO;
import pe.marista.sigma.dao.EstudianteDAO;
import pe.marista.sigma.dao.EstudianteDocumentoDAO;
import pe.marista.sigma.dao.MatriculaDAO;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.util.Mailing;

/**
 *
 * @author Administrador
 */
public class AdmisionService {

    private AdmisionDAO admisionDAO;
    private EstudianteDAO estudianteDAO;
    private EstudianteDocumentoDAO estudianteDocumentoDAO;
    private MatriculaDAO matriculaDAO;

    //Lógica de Negocio
    public List<AdmisionBean> obtenerAdmision() throws Exception {
        return admisionDAO.obtenerAdmision();
    }

    public List<AdmisionBean> obtenerAdmisionPorUniNeg(String uniNeg) throws Exception {
        return admisionDAO.obtenerAdmisionPorUniNeg(uniNeg);
    }

    public List<AdmisionBean> obtenerListaDistinctAdm(String value) throws Exception {
        return admisionDAO.obtenerListaDistinctAdm(value);
    }

    public AdmisionBean obtenerAdmisionPorId(AdmisionBean admisionBean) throws Exception {
        return admisionDAO.obtenerAdmisionPorId(admisionBean);
    }

    public AdmisionBean validarPostuEnAdmision(EstudianteBean estudianteBean) throws Exception {
        return admisionDAO.validarPostuEnAdmision(estudianteBean);
    }

    public AdmisionBean obtenerAdmisionPorPostu(AdmisionBean admisionBean) throws Exception {
        return admisionDAO.obtenerAdmisionPorPostu(admisionBean);
    }

    @Transactional
    public void insertarAdmision(AdmisionBean admisionBean) throws Exception {
        admisionDAO.insertarAdmision(admisionBean);
        admisionDAO.llamarGenerarDocPorAdmision(admisionBean);
        List<EstudianteDocumentoBean> listaEstudianteDocumentoBean = estudianteDocumentoDAO.obtenerEstDocumentoPorEst(admisionBean.getEstudianteBean().getPersonaBean().getIdPersona(), admisionBean.getEstudianteBean().getPersonaBean().getUnidadNegocioBean().getUniNeg(), admisionBean.getProgramacionBean().getProcesoBean().getAnio());
        System.out.println("lista");

        EntidadBean entidadBean = new EntidadBean();
        String nom = "", ruc = "";
        CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
        EntidadService entidadService = BeanFactory.getEntidadService();
//        ruc = cuentaBancoService.obtenerRucEntidad(admisionBean.getUnidadNegocioBean().getUniNeg());
        UnidadNegocioBean bean = new UnidadNegocioBean();
        bean.setUniNeg(admisionBean.getUnidadNegocioBean().getUniNeg());
        UnidadNegocioService uniSer = BeanFactory.getUnidadNegocioService();
        UnidadNegocioBean bean2 = new UnidadNegocioBean();
        bean2 = uniSer.obtenerPorId(bean);
        entidadBean.getUnidadNegocioBean().setUniNeg(admisionBean.getUnidadNegocioBean().getUniNeg());
        entidadBean.setRuc(bean2.getRuc());
        entidadBean = entidadService.obtenerEntidadPorId(entidadBean);
        nom = entidadBean.getNombre();
        entidadBean = entidadService.obtenerInfoEntidad(admisionBean.getUnidadNegocioBean().getUniNeg());
        if (admisionBean.getFlgEmail()) {
            if (admisionBean.getEstudianteBean().getPersonaBean().getCorreo().trim() != null && !admisionBean.getEstudianteBean().getPersonaBean().getCorreo().trim().equals("")
                    && admisionBean.getEstudianteBean().getPersonaBean().getCorreo() != null && !admisionBean.getEstudianteBean().getPersonaBean().getCorreo().equals("")) {
                new Mailing().enviarCorreoCita(admisionBean, admisionBean.getEstudianteBean(), entidadBean, nom);
            }
        } else if (!admisionBean.getFlgEmail()) {
            System.out.println("No Enviar");
        }
    }

    @Transactional
    public void llamarGenerarDocPorAdmision(AdmisionBean admisionBean) throws Exception {
        admisionDAO.llamarGenerarDocPorAdmision(admisionBean);
    }

    @Transactional
    public void modificarAdmision(AdmisionBean admisionBean) throws Exception {
        admisionDAO.modificarAdmision(admisionBean);
        EntidadBean entidadBean = new EntidadBean();
        String nom = "", ruc = "";
        CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
        EntidadService entidadService = BeanFactory.getEntidadService();
//        ruc = cuentaBancoService.obtenerRucEntidad(admisionBean.getUnidadNegocioBean().getUniNeg());
        UnidadNegocioBean bean = new UnidadNegocioBean();
        bean.setUniNeg(admisionBean.getUnidadNegocioBean().getUniNeg());
        UnidadNegocioService uniSer = BeanFactory.getUnidadNegocioService();
        UnidadNegocioBean bean2 = new UnidadNegocioBean();
        bean2 = uniSer.obtenerPorId(bean);
        entidadBean.getUnidadNegocioBean().setUniNeg(admisionBean.getUnidadNegocioBean().getUniNeg());
        entidadBean.setRuc(bean2.getRuc());
        entidadBean = entidadService.obtenerEntidadPorId(entidadBean);
        nom = entidadBean.getNombre();
        entidadBean = entidadService.obtenerInfoEntidad(admisionBean.getUnidadNegocioBean().getUniNeg());
        if (admisionBean.getFlgEmail() && admisionBean.getFlgEmail() != null) {
            if (admisionBean.getEstudianteBean().getPersonaBean().getCorreo().trim() != null && !admisionBean.getEstudianteBean().getPersonaBean().getCorreo().trim().equals("")
                    && admisionBean.getEstudianteBean().getPersonaBean().getCorreo() != null && !admisionBean.getEstudianteBean().getPersonaBean().getCorreo().equals("")) {
                System.out.println(">>>> correo");
                new Mailing().enviarCorreoAdmision(admisionBean, admisionBean.getEstudianteBean(), entidadBean, nom);
            }
        } else {
            System.out.println("No Enviar");
        }
    }

    @Transactional
    public void cambiarEstadoPostulante(AdmisionBean admisionBean) throws Exception {
        admisionDAO.cambiarEstadoPostulante(admisionBean);
    }

    @Transactional
    public void eliminarAdmision(AdmisionBean admisionBean) throws Exception {
        admisionDAO.eliminarAdmision(admisionBean);
    }

    public AdmisionBean obtenerAdmisionPorIdPeriodo(AdmisionBean admisionBean) throws Exception {
        return admisionDAO.obtenerAdmisionPorIdPeriodo(admisionBean);
    }

    //Aprobacion de Postulantes
    public List<AdmisionBean> obtenerAdmisionFiltro(AdmisionBean admisionBean) throws Exception {
        return admisionDAO.obtenerAdmisionFiltro(admisionBean);
    }

    @Transactional
    public void aprobarPostulante(List<AdmisionBean> listaAdmisionBean, AdmisionBean admision, EstudianteBean estudiante, UsuarioBean usuarioLogin, Boolean flgAll) throws Exception {
        for (AdmisionBean admisionBean : listaAdmisionBean) {
//            if (admisionBean.getEstadoAprobacion() == null) {
//                admisionBean.setEstadoAprobacion(Boolean.FALSE);
//            }
            int est = 1;
            if (admisionBean.getEstadoAprobacionData()) {
                System.out.println("adm");
                admisionBean.getCodigoBean().setCodigo(MaristaConstantes.COD_ADMITIDO);

                estudiante.getPersonaBean().setIdPersona(admisionBean.getEstudianteBean().getPersonaBean().getIdPersona());
                estudiante.setUnidadNegocioBean(admisionBean.getEstudianteBean().getPersonaBean().getUnidadNegocioBean());
                //Codigo Activo - tipostatusest
                estudiante.getTipoStatusEst().setCodigo(MaristaConstantes.COD_EST_ACTIVO);
                estudiante.getTipoStatusEst().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STATUS_EST);
                //Codigo Nuevo -tipoingresoest
                estudiante.getTipoIngresoEst().setCodigo(MaristaConstantes.COD_EST_NUEVO);
                estudiante.getTipoIngresoEst().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_INGRESO_ESTUDIANTE);
                //Grado ingreso = Grado al que postula
                estudiante.getGradoAcademicoBean().setIdGradoAcademico(admisionBean.getGradoAcademicoBean().getIdGradoAcademico());
                //Grado habilitado = Grado al que postula
                estudiante.getGradoHabilitado().setIdGradoAcademico(admisionBean.getGradoAcademicoBean().getIdGradoAcademico());
                //Año ingreso
                estudiante.setAnioIngreso(admisionBean.getAnio());
                //Fecha ingreso
                estudiante.setFechaIngreso(admisionBean.getFechaIngreso());
                estudianteDAO.cambiarDatosIngresoEstudiante(estudiante);
                //---- Generando Codigo Estudiante 
                String existe = estudianteDAO.existeCodigo(estudiante);
                if (existe == null || Objects.equals(existe, "")) {
                    CodigoColegioBean code = new CodigoColegioBean();
                    code.setAnio(admisionBean.getProgramacionBean().getProcesoBean().getAnio());
                    code.setCodigo(admisionBean.getProgramacionBean().getProcesoBean().getAnio().toString());
                    code.setUnidadNegocioBean(estudiante.getUnidadNegocioBean());
                    if (usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOH)) {
                        code = estudianteDAO.obtenerCodigoColegioPorCodigoSanjoh(code);
                    } else {
                        code = estudianteDAO.obtenerCodigoColegioPorCodigo(code);
                    }
                    estudiante.setPeriodo(admisionBean.getProgramacionBean().getProcesoBean().getAnio().toString());
                    int estado = 0;
                    String ceros = "";
                    if (code != null) {
                        estado = 1;
                        String codigoActualVista = code.getCodigoActual();
                        //Ayuda

                        if (Integer.parseInt(codigoActualVista.substring(0, 3)) == 0) {
                            ceros = codigoActualVista.substring(0, 3);
                        } else if (Integer.parseInt(codigoActualVista.substring(0, 2)) == 0) {
                            ceros = codigoActualVista.substring(0, 2);
                        } else if (Integer.parseInt(codigoActualVista.substring(0, 1)) == 0) {
                            ceros = codigoActualVista.substring(0, 1);
                        }
                        //Fin Ayuda
                        Integer codigoAcInt = Integer.parseInt(code.getCodigoActual());
                        Integer codi = codigoAcInt + 1;
                        if (estudiante.getUnidadNegocioBean().getUniNeg() != null && estudiante.getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                            estudiante.setCodigo(ceros + codi.toString());
                        } else if (estudiante.getUnidadNegocioBean().getUniNeg() != null && estudiante.getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOH)) {
                            estudiante.setCodigo(ceros + codi.toString());
                        } else if (estudiante.getUnidadNegocioBean().getUniNeg() != null) {
                            estudiante.setCodigo(ceros + codi.toString());
                        } else {
                            throw new SQLException();
                        }
                        estudianteDAO.insertarCodigo(estudiante);
                    }
                    if (estado == 1) {
                        Integer codigo = Integer.parseInt(code.getCodigoActual());
                        codigo = codigo + 1;
                        String codigoActualVista = code.getCodigoActual();
                        if (Integer.parseInt(codigoActualVista.substring(0, 3)) == 0) {
                            ceros = codigoActualVista.substring(0, 3);
                        } else if (Integer.parseInt(codigoActualVista.substring(0, 2)) == 0) {
                            ceros = codigoActualVista.substring(0, 2);
                        } else if (Integer.parseInt(codigoActualVista.substring(0, 1)) == 0) {
                            ceros = codigoActualVista.substring(0, 1);
                        }
                        code.setCodigoActual(ceros + codigo);
                        estudianteDAO.modificarCodigoColegio(code);
                    }

//                    estudianteDAO.insertarCodigo(estudiante);
                }
//                if (existe == null || Objects.equals(existe, "")) {
//                    estudiante.setPeriodo(admisionBean.getProgramacionBean().getProcesoBean().getAnio().toString());
//                    if (estudiante.getUnidadNegocioBean().getUniNeg() != null && estudiante.getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
//                        System.out.println("codigo: " + estudianteDAO.generarCodigoChamp(estudiante));
//                        estudiante.setCodigo(generaCodigoEst(estudiante.getPeriodo(), estudianteDAO.generarCodigoChamp(estudiante), MaristaConstantes.UNI_NEG_CHAMPS));
//                    } else if (estudiante.getUnidadNegocioBean().getUniNeg() != null && estudiante.getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SANJOH)) {
//                        System.out.println("codigo: " + estudianteDAO.generarCodigoSanjoH(estudiante));
//                        estudiante.setCodigo(generaCodigoEst(estudiante.getPeriodo(), estudianteDAO.generarCodigoSanjoH(estudiante), MaristaConstantes.UNI_NEG_SANJOH));
//                    } else if (estudiante.getUnidadNegocioBean().getUniNeg() != null) {
//                        System.out.println("codigo: " + estudianteDAO.generarCodigoOtros(estudiante));
//                        estudiante.setCodigo(generaCodigoEst(estudiante.getPeriodo(), estudianteDAO.generarCodigoOtros(estudiante), estudiante.getUnidadNegocioBean().getUniNeg()));
//                    } else {
//                        throw new SQLException();
//                    }
////                    estudianteDAO.insertarCodigo(estudiante);
//                }
                //----fin generando Codigo Estudiante-----
                //matricula
                MatriculaBean matricula = new MatriculaBean();
                matricula.setEstudianteBean(admisionBean.getEstudianteBean());
                matricula.setAnio(admisionBean.getAnio());
                matricula = matriculaDAO.obtenerMatriculaPorIdPeriodo(matricula);
                if (matricula == null) {
                    MatriculaBean matriculaBean = new MatriculaBean();
                    matriculaBean.setEstudianteBean(admisionBean.getEstudianteBean());
                    matriculaBean.setAnio(admisionBean.getProgramacionBean().getAnio());
//                    matriculaBean = matriculaDAO.obtenerMatriculaPorIdPeriodo(matriculaBean);
                    matriculaBean.setFechaMatricula(null);
                    matriculaBean.setFlgMatricula(false);
                    matriculaBean.getTipoMatriculaBean().setIdCodigo(11701);
                    matriculaBean.setProgramacionBean(admisionBean.getProgramacionBean());
                    matriculaBean.setAnio(admisionBean.getAnio());
                    matriculaBean.setSeccion("A");
                    matriculaBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                    matriculaBean.setCreaPor(usuarioLogin.getUsuario());
                    matriculaBean.getGradoAcademicoBean().setIdGradoAcademico(admisionBean.getGradoAcademicoBean().getIdGradoAcademico());
//                    if (estudiante.getGradoHabilitado().getIdGradoAcademico() == 25) {//canchero (:
//                        matriculaBean.getGradoAcademicoBean().setIdGradoAcademico(1);
//                    } else {
//                        matriculaBean.getGradoAcademicoBean().setIdGradoAcademico(estudiante.getGradoHabilitado().getIdGradoAcademico() + 1);
//                    }
                    matriculaDAO.insertarMatricula(matriculaBean);

                    //email admitido
                    EntidadBean entidadBean = new EntidadBean();
                    admisionBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                    String nom = "", ruc = "";
                    CuentaBancoService cuentaBancoService = BeanFactory.getCuentaBancoService();
                    ProgramacionService programacionService = BeanFactory.getProgramacionService();
                    GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
                    GradoAcademicoBean grado = new GradoAcademicoBean();
                    grado = gradoAcademicoService.obtenerPorId(admisionBean.getGradoAcademicoBean());
                    admisionBean.getEstudianteBean().getPersonaBean().setGradoAcademicoBean(grado);
                    ProgramacionBean programacion = new ProgramacionBean();
                    programacion = admisionBean.getProgramacionBean();
                    programacion.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                    programacion = programacionService.obtenerPrograPorId(programacion);
                    admisionBean.setProgramacionBean(programacion);
                    EntidadService entidadService = BeanFactory.getEntidadService();
//                    ruc = cuentaBancoService.obtenerRucEntidad(admisionBean.getUnidadNegocioBean().getUniNeg());
                    UnidadNegocioBean bean = new UnidadNegocioBean();
                    bean.setUniNeg(admisionBean.getUnidadNegocioBean().getUniNeg());
                    UnidadNegocioService uniSer = BeanFactory.getUnidadNegocioService();
                    UnidadNegocioBean bean2 = new UnidadNegocioBean();
                    bean2 = uniSer.obtenerPorId(bean);
                    entidadBean.getUnidadNegocioBean().setUniNeg(admisionBean.getUnidadNegocioBean().getUniNeg());
                    entidadBean.setRuc(bean2.getRuc());
                    entidadBean = entidadService.obtenerEntidadPorId(entidadBean);
                    nom = entidadBean.getNombre();
                    entidadBean = entidadService.obtenerInfoEntidad(admisionBean.getUnidadNegocioBean().getUniNeg());
                    if (admisionBean.getEstudianteBean().getPersonaBean().getCorreo() != null) {
                        if (admisionBean.getEstudianteBean().getPersonaBean().getCorreo().trim() != null && !admisionBean.getEstudianteBean().getPersonaBean().getCorreo().trim().equals("")
                                && admisionBean.getEstudianteBean().getPersonaBean().getCorreo() != null && !admisionBean.getEstudianteBean().getPersonaBean().getCorreo().equals("")) {
//                            new Mailing().enviarCorreoAdmision(admisionBean, admisionBean.getEstudianteBean(), entidadBean, nom);
                        }
                    }
                    //cambiar por genera matricula
                    matriculaDAO.llamarGenerarMatricula(matriculaBean);
                    System.out.println("listaAdm");
                }

                EstudianteInfoBean estudianteInfoBean = new EstudianteInfoBean();
                EstudianteService estudianteService = BeanFactory.getEstudianteService();
//                estudianteInfoBean.getEstudianteBean().getPersonaBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                estudiante.getPersonaBean().setIdPersona(admisionBean.getEstudianteBean().getPersonaBean().getIdPersona());
                estudiante.getPersonaBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                estudianteInfoBean.setDniEstudiante(admisionBean.getEstudianteBean().getDniEstudiante());
                estudianteInfoBean.setEstudianteBean(estudiante);
                estudianteInfoBean.setCreaPor(usuarioLogin.getUsuario());
                estudiante.setModiPor(usuarioLogin.getUsuario());
                System.out.println("Unineg: " + estudianteInfoBean.getEstudianteBean().getPersonaBean().getUnidadNegocioBean().getUniNeg());
                EstudianteInfoBean estVista = new EstudianteInfoBean();
                estVista = estudianteService.obtenerEstudianteInfoPorId(estudiante.getPersonaBean().getIdPersona());
                if (estVista != null) {
                    System.out.println("ya esta creado el estudiante Info");
                } else {
                    estudianteService.insertarEstudianteInfo(estudianteInfoBean, estudiante);
                }
            } else {
                System.out.println("FLG " + flgAll);
                if (flgAll.equals(Boolean.TRUE)) {
                    System.out.println("NO ADM " + est);
                    admisionBean.getCodigoBean().setCodigo(MaristaConstantes.COD_NO_ADMITIDO);
                } else {
                    est = 0;
                    System.out.println("nada " + est);
                }
            }
            if (est == 1) {
                admisionBean.setDocRefeIngreso(admision.getDocRefeIngreso());
                admisionBean.setFechaIngreso(admision.getFechaIngreso());
                admisionDAO.aprobarPostulante(admisionBean);
            }
        }
    }

    public String generaCodigoEst(String anio, Integer contador, String UniNeg) {
        StringBuilder sb = new StringBuilder();
        contador++;
        if (UniNeg.equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
            sb.append(anio);
            if (contador.toString().length() == 1) {
                sb.append("000").append(contador.toString());
            }
            if (contador.toString().length() == 2) {
                sb.append("00").append(contador.toString());
            }
            if (contador.toString().length() == 3) {
                sb.append("0").append(contador.toString());
            }
            if (contador.toString().length() == 4) {
                sb.append(contador.toString());
            }
        } else if (UniNeg.equals(MaristaConstantes.UNI_NEG_SANJOH)) {
            System.out.println("nada");
        } else {
            sb.append(anio.toString());
            if (contador.toString().length() == 1) {
                sb.append("00").append(contador.toString());
            }
            if (contador.toString().length() == 2) {
                sb.append("0").append(contador.toString());
            }
            if (contador.toString().length() == 3) {
                sb.append(contador.toString());
            }
        }
        return sb.toString();
    }

    //GEtter y Setter
    public AdmisionDAO getAdmisionDAO() {
        return admisionDAO;
    }

    public void setAdmisionDAO(AdmisionDAO admisionDAO) {
        this.admisionDAO = admisionDAO;
    }

    public EstudianteDocumentoDAO getEstudianteDocumentoDAO() {
        return estudianteDocumentoDAO;
    }

    public void setEstudianteDocumentoDAO(EstudianteDocumentoDAO estudianteDocumentoDAO) {
        this.estudianteDocumentoDAO = estudianteDocumentoDAO;
    }

    public EstudianteDAO getEstudianteDAO() {
        return estudianteDAO;
    }

    public void setEstudianteDAO(EstudianteDAO estudianteDAO) {
        this.estudianteDAO = estudianteDAO;
    }

    public MatriculaDAO getMatriculaDAO() {
        return matriculaDAO;
    }

    public void setMatriculaDAO(MatriculaDAO matriculaDAO) {
        this.matriculaDAO = matriculaDAO;
    }

    public List<AdmisionEstudiantesRepBean> obtenerAlumnosPorAnio(String uniNeg, Integer anio) throws Exception {
        return admisionDAO.obtenerAlumnosPorAnio(uniNeg, anio);
    }

}

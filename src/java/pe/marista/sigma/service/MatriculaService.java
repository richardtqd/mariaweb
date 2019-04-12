package pe.marista.sigma.service;

import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CuentasPorCobrarBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.ViewMatriculaBean;
import pe.marista.sigma.bean.reporte.AlumnosIngresantesRetiradosRepBean;
import pe.marista.sigma.bean.reporte.DeclaracionJuradaRepBean;
import pe.marista.sigma.bean.reporte.EstMatriculaGradoAcaRepBean;
import pe.marista.sigma.bean.reporte.EstMatriculaNivelRepBean;
import pe.marista.sigma.bean.reporte.EstMatriculaRepBean;
import pe.marista.sigma.bean.reporte.EstMatriculaSeccGradoAcaRepBean;
import pe.marista.sigma.bean.reporte.EstMatriculaSeccionRepBean;
import pe.marista.sigma.bean.reporte.EstudianteGeneralRepBean;
import pe.marista.sigma.bean.reporte.ListaAlumnosRepBean;
import pe.marista.sigma.dao.MatriculaDAO;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.util.MaristaUtils;

/**
 *
 * @author Administrador
 */
public class MatriculaService {

    public List<MatriculaBean> obtenerFiltroMatriculadosPorUsuario(MatriculaBean matriculaBean) throws Exception {
        return matriculaDAO.obtenerFiltroMatriculadosPorUsuario(matriculaBean);
    }

    private MatriculaDAO matriculaDAO;
//    private CuentasPorCobrarDAO cuentasPorCobrarDAO;
//    private CentroResponsabilidadDAO centroResponsabilidadDAO;
//    private ConceptoUniNegDAO conceptoUniNegDAO;
//    private CodigoDAO codigoDAO;
//    private EstudianteBecaDAO estudianteBecaDAO;

    //Lógica de Negocio
    public List<MatriculaBean> obtenerMatricula() throws Exception {
        return matriculaDAO.obtenerMatricula();
    }

    public List<MatriculaBean> obtenerFiltroMatriculaMasivo(MatriculaBean matriculaBean) throws Exception {
        return matriculaDAO.obtenerFiltroMatriculaMasivo(matriculaBean);
    }

    public List<MatriculaBean> obtenerFiltroNoMatriculados(MatriculaBean matriculaBean) throws Exception {
        return matriculaDAO.obtenerFiltroNoMatriculados(matriculaBean);
    }

    public List<MatriculaBean> obtenerFiltroMatriculados(MatriculaBean matriculaBean) throws Exception {
        return matriculaDAO.obtenerFiltroMatriculados(matriculaBean);
    }

    public List<MatriculaBean> obtenerFiltroEstudianteMasivo(MatriculaBean matriculaBean) throws Exception {
        matriculaBean.getEstudianteBean().getTipoStatusEst().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STATUS_EST);
        matriculaBean.getEstudianteBean().getTipoStatusEst().setCodigo(MaristaConstantes.COD_EST_ACTIVO);
        return matriculaDAO.obtenerFiltroEstudianteMasivo(matriculaBean);
    }

    public List<MatriculaBean> obtenerFiltroEstudianteMasivoDeudor(MatriculaBean matriculaBean) throws Exception {
        matriculaBean.getEstudianteBean().getTipoStatusEst().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STATUS_EST);
        matriculaBean.getEstudianteBean().getTipoStatusEst().setCodigo(MaristaConstantes.COD_EST_ACTIVO);
        return matriculaDAO.obtenerFiltroEstudianteMasivoDeudor(matriculaBean);
    }

    public List<MatriculaBean> obtenerFiltroEstudianteMasivoDeudorMes(MatriculaBean matriculaBean, Integer[] listaMeses) throws Exception {
        matriculaBean.getEstudianteBean().getTipoStatusEst().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STATUS_EST);
        matriculaBean.getEstudianteBean().getTipoStatusEst().setCodigo(MaristaConstantes.COD_EST_ACTIVO);
        return matriculaDAO.obtenerFiltroEstudianteMasivoDeudorMes(matriculaBean, listaMeses);
    }

    public List<MatriculaBean> obtenerFiltroEstudianteCtasPorCobrarMasivo(MatriculaBean matriculaBean) throws Exception {
        return matriculaDAO.obtenerFiltroEstudianteCtasPorCobrarMasivo(matriculaBean);
    }

    public List<MatriculaBean> obtenerFiltroEstudianteImpCompMasivo(MatriculaBean matriculaBean) throws Exception {
        return matriculaDAO.obtenerFiltroEstudianteImpCompMasivo(matriculaBean);
    }

    public List<MatriculaBean> obtenerFiltroEstudianteImpCompMasivoAfter(MatriculaBean matriculaBean) throws Exception {
        return matriculaDAO.obtenerFiltroEstudianteImpCompMasivoAfter(matriculaBean);
    }

    public List<MatriculaBean> obtenerFiltroEstudianteMasivoCorreccionGrado(MatriculaBean matriculaBean) throws Exception {
        matriculaBean.setGradoAcademicoFiltro(MaristaConstantes.PreInicial_3_anios);
        return matriculaDAO.obtenerFiltroEstudianteMasivoCorreccionGrado(matriculaBean);
    }

//    public List<ViewMatriculaBean> verEstadisticasMatriculaPorAnio(Integer anio,String uniNeg) throws Exception {
//        return matriculaDAO.verEstadisticasMatriculaPorAnio(anio,uniNeg);
//    }
    public List<ViewMatriculaBean> verEstadisticasMatriculaPorAnio(ViewMatriculaBean viewMatriculaBean) throws Exception {
        viewMatriculaBean.setNivelInicial(MaristaConstantes.NIV_ACA_INI);
        viewMatriculaBean.setNivelPrimaria(MaristaConstantes.NIV_ACA_PRI);
        viewMatriculaBean.setNivelSecundaria(MaristaConstantes.NIV_ACA_SEC);
        return matriculaDAO.verEstadisticasMatriculaPorAnio(viewMatriculaBean);
    }

    public MatriculaBean obtenerMatriculaPorId(MatriculaBean matriculaBean) throws Exception {
        return matriculaDAO.obtenerMatriculaPorId(matriculaBean);
    }

    public MatriculaBean obtenerMatriculaPorIdPeriodo(MatriculaBean matriculaBean) throws Exception {
        return matriculaDAO.obtenerMatriculaPorIdPeriodo(matriculaBean);
    }

//    public MatriculaBean obtenerBachiller() throws Exception{
//        return matriculaDAO.obtenerBachiller();
//    }
    @Transactional
    public void insertarMatricula(MatriculaBean matriculaBean) throws Exception {
        matriculaBean.setAnio(matriculaBean.getProgramacionBean().getProcesoBean().getAnio());
        matriculaDAO.insertarMatricula(matriculaBean);
        UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
        matriculaBean.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());

//        Map<String, Object> mapa = new LinkedHashMap<>();
//        mapa.put("uniNeg", usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//        mapa.put("anio", matriculaBean.getProgramacionBean().getProcesoBean().getPeriodo());
//        mapa.put("idDiscente", matriculaBean.getEstudianteBean().getPersonaBean().getIdPersona());
//        mapa.put("resultado", new Object());
//        matriculaDAO.llamarGenerarCtasxCobrar(matriculaBean);
//        CuentasPorCobrarBean cuentasPorCobrarBean = new CuentasPorCobrarBean();
//        cuentasPorCobrarBean.setEstudianteBean(matriculaBean.getEstudianteBean());//Para idEstudiante(EstudianteBean)
//        CentroResponsabilidadBean centroResponsabilidadBean = centroResponsabilidadDAO.obtenerCentroResPorNombre(MaristaConstantes.CR_ACADEMICO);
//        cuentasPorCobrarBean.setCentroResponsabilidadBean(centroResponsabilidadBean);//Para cr (CentroResponsabilidadBean)
//        CodigoBean tipoMonedaBean = codigoDAO.obtenerPorCodigo(MaristaConstantes.MON_SOLES);
//        cuentasPorCobrarBean.setIdTipoMoneda(tipoMonedaBean);//Para idTipoMoneda (CodigoBean)
//        EstudianteBecaBean estudianteBecaBean = new EstudianteBecaBean();
//        estudianteBecaBean.setEstudianteBean(matriculaBean.getEstudianteBean());
//        estudianteBecaBean.setPeriodo(matriculaBean.getProgramacionBean().getProcesoBean().getPeriodo());
//        estudianteBecaBean = estudianteBecaDAO.buscarPorIdEstudiantePeriodo(estudianteBecaBean);
//        cuentasPorCobrarBean.setMonto(BigDecimal.ZERO);
//        cuentasPorCobrarBean.setCreaPor(matriculaBean.getCreaPor());
//        cuentasPorCobrarBean.setPeriodo(matriculaBean.getProgramacionBean().getProcesoBean().getPeriodo());
//       
//        
//        for (int i = 0; i < 10; i++) {
//            cuentasPorCobrarDAO.insertarCuentaPorCobrar(cuentasPorCobrarBean);
//        }
    }

    @Transactional
    public void insertarMatriculaMasivo(MatriculaBean matriculaBean, MatriculaBean matriculaBean1, List<MatriculaBean> listaMatriculaEstudianteBean, UsuarioBean usuarioLogin, Integer crIni, Integer crPri, Integer crSec, Integer crBac) throws Exception {
//        Calendar miCalendario = Calendar.getInstance();         
        EstudianteService estudianteService = BeanFactory.getEstudianteService();
        for (MatriculaBean matriEst : listaMatriculaEstudianteBean) {
            if (matriEst.getEstudianteBean().getEstadoAprobacion() == true) {
//                System.out.println(matriEst.getEstudianteBean().getGradoHabilitado().getNombre());
                if (matriEst.getEstudianteBean().getGradoHabilitado().getNombre().equals(MaristaConstantes.Quinto_Bach_Secundaria) || matriEst.getEstudianteBean().getGradoHabilitado().getNombre().equals(MaristaConstantes.Quinto_Secundaria)) {
                    EstudianteBean est = new EstudianteBean();
                    est.setUnidadNegocioBean(matriEst.getUnidadNegocioBean());
                    est.getPersonaBean().setIdPersona(matriEst.getEstudianteBean().getPersonaBean().getIdPersona());
                    est.getTipoStatusEst().setCodigo(MaristaConstantes.EGRESADO);
                    est.getTipoStatusEst().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STATUS_EST);
                    estudianteService.cambiarDatosEgresoEstudiante(est);
                } else {
                    MatriculaBean matricula = new MatriculaBean();
                    matricula.setEstudianteBean(matriEst.getEstudianteBean());
                    matricula.setAnio(matriculaBean1.getProgramacionBean().getAnio());
                    matricula = matriculaDAO.obtenerMatriculaPorIdPeriodo(matricula);
                    if (matricula == null) {
                        matriculaBean.setEstudianteBean(matriEst.getEstudianteBean());
                        matriculaBean.setAnio(matriculaBean1.getProgramacionBean().getAnio() - 1);
                        matriculaBean = matriculaDAO.obtenerMatriculaPorIdPeriodo(matriculaBean);
                        if (matriculaBean != null) {
                            matriculaBean.setFechaMatricula(null);
                            matriculaBean.setFlgMatricula(false);
                            matriculaBean.setTipoMatriculaBean(matriculaBean1.getTipoMatriculaBean());
                            matriculaBean.setProgramacionBean(matriculaBean1.getProgramacionBean());
                            matriculaBean.setAnio(matriculaBean1.getProgramacionBean().getAnio());
                            matriculaBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                            matriculaBean.setCreaPor(usuarioLogin.getUsuario());
                            System.out.println("matri " + matriculaBean.getGradoAcademicoBean().getNombre());

                            if (matriculaBean.getGradoAcademicoBean().getNombre() == null) {
                                matriculaBean.setGradoAcademicoBean(matriEst.getEstudianteBean().getGradoHabilitado());
                            }

                            if (matriculaBean.getGradoAcademicoBean().getNombre().equals(MaristaConstantes.Inicial_5_anios)) {
                                matriculaBean.getGradoAcademicoBean().setIdGradoAcademico(1);
                            } else {
                                //  BACHILLERATO
                                switch (matriculaBean.getGradoAcademicoBean().getNombre()) {
                                    case MaristaConstantes.Cuarto_Secundaria:
                                        matriculaBean.getGradoAcademicoBean().setIdGradoAcademico(11);
                                        matriculaBean.setFlgBachillerato(false);
                                        break;
                                    case MaristaConstantes.Cuarto_Bach_Secundaria:
                                        matriculaBean.getGradoAcademicoBean().setIdGradoAcademico(13);
                                        matriculaBean.setFlgBachillerato(true);
                                        break;
                                    case MaristaConstantes.Quinto_Secundaria:
//                                     matriculaBean.getGradoAcademicoBean().setIdGradoAcademico(11);
                                        matriculaBean.setFlgBachillerato(false);
                                        break;
                                    case MaristaConstantes.Quinto_Bach_Secundaria:
//                                     matriculaBean.getGradoAcademicoBean().setIdGradoAcademico(11);
                                        matriculaBean.setFlgBachillerato(true);
                                        break;
                                    default:
                                        matriculaBean.getGradoAcademicoBean().setIdGradoAcademico(matriculaBean.getGradoAcademicoBean().getIdGradoAcademico() + 1);
//                                        matriculaBean.getGradoAcademicoBean().setIdGradoAcademico(matriEst.getEstudianteBean().getGradoHabilitado().getIdGradoAcademico() + 1);
                                        break;
                                }
                            }
//                    //FLG BACHILLERATO
//                    if (matriculaBean.getGradoAcademicoBean().getNombre().equals(MaristaConstantes.Quinto_Secundaria) || matriculaBean.getGradoAcademicoBean().getNombre().equals(MaristaConstantes.Cuarto_Secundaria)) {//canchero (:
//                        matriculaBean.setFlgBachillerato(false);
//                    }
                            matriculaDAO.insertarMatricula(matriculaBean);
                            EstudianteBean estudiante = new EstudianteBean();
                            estudiante = matriEst.getEstudianteBean();
                            estudiante.setGradoHabilitado(matriculaBean.getGradoAcademicoBean());
                            estudianteService.cambiarGradoAcademico(estudiante);
                            if (crIni != null) {
                                matriculaBean.setCrIni(crIni);
                            }
                            if (crPri != null) {
                                matriculaBean.setCrPri(crPri);
                            }
                            if (crSec != null) {
                                matriculaBean.setCrSec(crSec);
                            }
                            if (crBac != null) {
                                matriculaBean.setCrBac(crBac);
                            }
                            matriculaDAO.llamarGenerarMatricula(matriculaBean);
                            System.out.println("lista");
                        }
                    }
                }
            }
        }
    }

    @Transactional
    public void insertarCuentasPorCobrarMasivo(CuentasPorCobrarBean cuentasPorCobrarBean, List<MatriculaBean> listaMatriculaEstudiante, UsuarioBean usuarioLogin, Integer flgMatriPend, Integer flgReProceso, Integer crIni, Integer crPri, Integer crSec, Integer crBac, Integer anio, Integer mes) throws Exception {
//        Calendar miCalendario = Calendar.getInstance();         
        for (MatriculaBean matriEst : listaMatriculaEstudiante) {
            if (matriEst.getEstudianteBean().getEstadoAprobacionCtaPorCobrar() == true) {
                MatriculaBean matricula = new MatriculaBean();
                matricula.setUnidadNegocioBean(matriEst.getUnidadNegocioBean());
                matricula.setEstudianteBean(matriEst.getEstudianteBean());
                matricula.setAnio(anio);
                matricula.setMes(mes);
                matricula.setCreaPor(usuarioLogin.getUsuario());

                matricula.setFlgMatriPend(flgMatriPend);
                matricula.setFlgReProceso(flgReProceso);
                if (crIni != null) {
                    matricula.setCrIni(crIni);
                }
                if (crPri != null) {
                    matricula.setCrPri(crPri);
                }
                if (crSec != null) {
                    matricula.setCrSec(crSec);
                }
                if (crBac != null) {
                    matricula.setCrBac(crBac);
                }

                matriculaDAO.llamarGenerarCtasxCobrar(matricula);
                System.out.println("lista Cta");
            }
        }

    }

    @Transactional
    public void modificarMatricula(MatriculaBean matriculaBean) throws Exception {
        matriculaDAO.modificarMatricula(matriculaBean);
        if (matriculaBean.isFlgMatricula() == true) {
            matriculaDAO.llamarGenerarCtasxCobrar(matriculaBean);
        } else {
            matriculaDAO.eliminarCtasxcobrar(matriculaBean);
        }
    }

    @Transactional
    public void modificarMatriculaGradoAca(MatriculaBean matriculaBean, UsuarioBean usuarioLogin, GradoAcademicoBean gradoAcademicoBean) throws Exception {
        matriculaBean.setGradoAcademicoBean(gradoAcademicoBean);
        matriculaDAO.modificarMatriculaGradoAca(matriculaBean);
        EstudianteService estudianteService = BeanFactory.getEstudianteService();
        matriculaBean.getEstudianteBean().getPersonaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
        matriculaBean.getEstudianteBean().setModiPor(usuarioLogin.getUsuario());
        matriculaBean.setGradoAcademicoBean(gradoAcademicoBean);
        estudianteService.cambiarGradoAcademico(matriculaBean.getEstudianteBean());
        // CAMBIO EN CUENTAS
        CuentasPorCobrarService cueentas = BeanFactory.getCuentasPorCobrarService();
        cueentas.modificarConceptoCambioDeGrado(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), matriculaBean.getGradoAcademicoBean().getIdGradoAcademico(), matriculaBean.getAnio(), matriculaBean.getEstudianteBean().getIdEstudiante());

        List<Integer> listIdDoc = cueentas.obtenerIdDocIngreso(matriculaBean.getEstudianteBean().getIdEstudiante(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), matriculaBean.getAnio());
        for (Integer list : listIdDoc) {
            DocIngresoService doc = BeanFactory.getDocIngresoService();
            doc.modificarConceptoCambioGrado(list, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), matriculaBean.getGradoAcademicoBean().getIdGradoAcademico());
        }

        if (gradoAcademicoBean.getNombre().equals(MaristaConstantes.Quinto_Bach_Secundaria) || gradoAcademicoBean.getNombre().equals(MaristaConstantes.Quinto_Secundaria)) {
            EstudianteBean est = new EstudianteBean();
            est.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            est.getPersonaBean().setIdPersona(matriculaBean.getEstudianteBean().getPersonaBean().getIdPersona());
            est.getTipoStatusEst().setCodigo(MaristaConstantes.ACTIVO);
            est.getTipoStatusEst().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STATUS_EST);
            estudianteService.cambiarDatosEgresoEstudiante(est);
        }
    }

    @Transactional
    public void modificarMatriculaGradoAcaLista(MatriculaBean matriculaBean, UsuarioBean usuarioLogin, List<MatriculaBean> listaMatriculaEstudiantesMasivosBean) throws Exception {
        for (MatriculaBean matricula : listaMatriculaEstudiantesMasivosBean) {
            if (matricula.getEstudianteBean().getEstadoAprobacion() == true) {
                System.out.println("aa: " + matricula.getGradoAcademicoBean().getIdGradoAcademico());
                System.out.println("aa: " + matricula.getGradoAcademicoBean().getNombre());
                System.out.println("stado: " + matricula.getEstudianteBean().getEstadoAprobacion());
                System.out.println("1: " + matricula.getAnio());
                System.out.println("2: " + matricula.getAnioFin());
                System.out.println("3: " + matricula.getAnioIni());
                matricula.setModiPor(usuarioLogin.getUsuario());
                matriculaDAO.modificarMatriculaGradoAca(matricula);
                EstudianteService estudianteService = BeanFactory.getEstudianteService();
                EstudianteBean estudiante = new EstudianteBean();
                estudiante.getPersonaBean().setIdPersona(matricula.getEstudianteBean().getIdEstudiante());
                estudiante.getPersonaBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                estudiante.setModiPor(usuarioLogin.getUsuario());
                estudiante.setGradoHabilitado(matricula.getGradoAcademicoBean());
                estudiante.getGradoAcademicoBean().setIdGradoAcademico(matricula.getGradoAcademicoBean().getIdGradoAcademico());
                estudianteService.cambiarGradoAcademico(estudiante);

                // CAMBIO EN CUENTAS
                CuentasPorCobrarService cueentas = BeanFactory.getCuentasPorCobrarService();
                cueentas.modificarConceptoCambioDeGrado(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), matricula.getGradoAcademicoBean().getIdGradoAcademico(), matricula.getAnio(), matricula.getEstudianteBean().getIdEstudiante());

                List<Integer> listIdDoc = cueentas.obtenerIdDocIngreso(matricula.getEstudianteBean().getIdEstudiante(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), matricula.getAnio());
                for (Integer list : listIdDoc) {
                    DocIngresoService doc = BeanFactory.getDocIngresoService();
                    doc.modificarConceptoCambioGrado(list, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), matricula.getGradoAcademicoBean().getIdGradoAcademico());
                }

                if (matricula.getGradoAcademicoBean().getNombre().equals(MaristaConstantes.Quinto_Bach_Secundaria) || matricula.getGradoAcademicoBean().getNombre().equals(MaristaConstantes.Quinto_Secundaria)) {
                    EstudianteBean est = new EstudianteBean();
                    est.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
                    est.getPersonaBean().setIdPersona(matricula.getEstudianteBean().getPersonaBean().getIdPersona());
                    est.getTipoStatusEst().setCodigo(MaristaConstantes.ACTIVO);
                    est.getTipoStatusEst().getTipoCodigoBean().setDescripcion(MaristaConstantes.TIP_STATUS_EST);
                    est.setModiPor(usuarioLogin.getUsuario());
                    est.setIdEstudiante(matricula.getEstudianteBean().getIdEstudiante());
                    estudianteService.cambiarDatosEgresoEstudiante(est);
                }
            }
        }
    }

    public List<MatriculaBean> obtenerFiltroEstudianteMasivoLista(MatriculaBean matriculaBean) throws Exception {
        return matriculaDAO.obtenerFiltroEstudianteMasivoLista(matriculaBean);
    }

    @Transactional
    public void modificarMatriculaGradoAcaPorCta(MatriculaBean matriculaBean, String usuario, GradoAcademicoBean gradoAcademicoBean) throws Exception {
        matriculaBean.setGradoAcademicoBean(gradoAcademicoBean);
        matriculaDAO.modificarMatriculaGradoAca(matriculaBean);
        EstudianteService estudianteService = BeanFactory.getEstudianteService();
        matriculaBean.getEstudianteBean().getPersonaBean().setUnidadNegocioBean(matriculaBean.getUnidadNegocioBean());
        matriculaBean.getEstudianteBean().setModiPor(usuario);
        matriculaBean.getEstudianteBean().setGradoHabilitado(gradoAcademicoBean);
        estudianteService.cambiarGradoAcademico(matriculaBean.getEstudianteBean());
    }

    @Transactional
    public void eliminarMatricula(MatriculaBean matriculaBean) throws Exception {
        matriculaDAO.eliminarMatricula(matriculaBean.getIdMatricula());
        matriculaDAO.eliminarCtasxcobrar(matriculaBean);
    }

    public List<MatriculaBean> obtenerAlumnoEsquela(String creaFecha, Integer idEsquela, String uniNeg, Integer status) throws Exception {
        return matriculaDAO.obtenerAlumnoEsquela(creaFecha, idEsquela, uniNeg, status);
    }

    public List<MatriculaBean> obtenerMatriculaUniNegAnio(MatriculaBean matriculaBean) throws Exception {
        return matriculaDAO.obtenerMatriculaUniNegAnio(matriculaBean);
    }

    public List<MatriculaBean> obtenerFiltroEstudianteMatriculadosPorUsu(MatriculaBean matriculaBean) throws Exception {
        return matriculaDAO.obtenerFiltroEstudianteMatriculadosPorUsu(matriculaBean);
    }

    @Transactional
    public void insertarMatriculaEspecial(MatriculaBean matriculaBean) throws Exception {
        matriculaDAO.insertarMatriculaEspecial(matriculaBean);
    }

    @Transactional
    public void generaMatriculaEspecial(MatriculaBean matriculaBean) throws Exception {
        matriculaDAO.llamarGenerarMatricula(matriculaBean);
    }

    public Integer obtenerMaxId(MatriculaBean matriculaBean) throws Exception {
        return matriculaDAO.obtenerMaxId(matriculaBean);
    }

    //GEtter y Setter
    public MatriculaDAO getMatriculaDAO() {
        return matriculaDAO;
    }

    public void setMatriculaDAO(MatriculaDAO matriculaDAO) {
        this.matriculaDAO = matriculaDAO;
    }

//    public CuentasPorCobrarDAO getCuentasPorCobrarDAO() {
//        return cuentasPorCobrarDAO;
//    }
//
//    public void setCuentasPorCobrarDAO(CuentasPorCobrarDAO cuentasPorCobrarDAO) {
//        this.cuentasPorCobrarDAO = cuentasPorCobrarDAO;
//    }
//
//    public CentroResponsabilidadDAO getCentroResponsabilidadDAO() {
//        return centroResponsabilidadDAO;
//    }
//
//    public void setCentroResponsabilidadDAO(CentroResponsabilidadDAO centroResponsabilidadDAO) {
//        this.centroResponsabilidadDAO = centroResponsabilidadDAO;
//    }
//
//    public ConceptoUniNegDAO getConceptoUniNegDAO() {
//        return conceptoUniNegDAO;
//    }
//
//    public void setConceptoUniNegDAO(ConceptoUniNegDAO conceptoUniNegDAO) {
//        this.conceptoUniNegDAO = conceptoUniNegDAO;
//    }
//
//    public CodigoDAO getCodigoDAO() {
//        return codigoDAO;
//    }
//
//    public void setCodigoDAO(CodigoDAO codigoDAO) {
//        this.codigoDAO = codigoDAO;
//    }
//
//    public EstudianteBecaDAO getEstudianteBecaDAO() {
//        return estudianteBecaDAO;
//    }
//
//    public void setEstudianteBecaDAO(EstudianteBecaDAO estudianteBecaDAO) {
//        this.estudianteBecaDAO = estudianteBecaDAO;
//    }
    public List<MatriculaBean> obtenerMatriculaUniNeg(String uniNeg) throws Exception {
        return matriculaDAO.obtenerMatriculaUniNeg(uniNeg);
    }

    public MatriculaBean obtenerEstudiantePorMatricula(MatriculaBean matriculaBean) throws Exception {
        return matriculaDAO.obtenerEstudiantePorMatricula(matriculaBean);
    }

    //reprotes
    public List<EstMatriculaNivelRepBean> obtenerNiveles(String uniNeg, Integer anio, String tipFor, Integer flg, Date fechaInicio, Date fechaFin) throws Exception {
        return matriculaDAO.obtenerNiveles(uniNeg, anio, tipFor, flg, fechaInicio, fechaFin);
    }

    public List<EstMatriculaGradoAcaRepBean> obtenerGradosAcademicoPorNivel(String uniNeg, Integer anio, String tipFor, String nivel, Integer flg, Date fechaInicio, Date fechaFin) throws Exception {
        return matriculaDAO.obtenerGradosAcademicoPorNivel(uniNeg, anio, tipFor, nivel, flg, fechaInicio, fechaFin);
    }

    public List<EstMatriculaSeccGradoAcaRepBean> obtenerSeccionesPorGradoAca(String uniNeg, Integer anio, String tipFor, String nivel, Integer idGradoAcademico, Integer flg, Date fechaInicio, Date fechaFin) throws Exception {
        return matriculaDAO.obtenerSeccionesPorGradoAca(uniNeg, anio, tipFor, nivel, idGradoAcademico, flg, fechaInicio, fechaFin);
    }

    public List<EstMatriculaSeccionRepBean> obtenerDetSeccionPorGradoAca(String uniNeg, Integer anio, String tipFor, String nivel, Integer idGradoAcademico, String secc, Integer flg, Date fechaInicio, Date fechaFin) throws Exception {
        return matriculaDAO.obtenerDetSeccionPorGradoAca(uniNeg, anio, tipFor, nivel, idGradoAcademico, secc, flg, fechaInicio, fechaFin);
    }

    public List<EstMatriculaRepBean> obtenerEstadisticaMatriculaCabecera(String uniNeg, Integer anio, String tipFor, Integer flg, Date fechaInicio, Date fechaFin) throws Exception {
        return matriculaDAO.obtenerEstadisticaMatriculaCabecera(uniNeg, anio, tipFor, flg, fechaInicio, fechaFin);
    }

    //ENVIO MASIVO
    public List<MatriculaBean> obtenerFiltroEstudianteImpCompMasivoDeuda(MatriculaBean matriculaBean, Integer[] listaMeses) throws Exception {
        return matriculaDAO.obtenerFiltroEstudianteImpCompMasivoDeuda(matriculaBean, listaMeses);
    }

    public List<EstudianteGeneralRepBean> obtenerImpresionPorDeUsuarioMat(String uniNeg, Integer anio, String creaPor, Date fechaMatricula) throws Exception {
        return matriculaDAO.obtenerImpresionPorDeUsuarioMat(uniNeg, anio, creaPor, fechaMatricula);
    }

    public List<MatriculaBean> obtenerTablaParaExcel(String uniNeg, Integer anio, Date fechaInicio, Date fechaFin, Integer matricula) throws Exception {
        return matriculaDAO.obtenerTablaParaExcel(uniNeg, anio, fechaInicio, fechaFin, matricula);
    }

    public List<EstudianteGeneralRepBean> obtenerImpresionPorDeUsuarioMatPorGrado(String uniNeg, Integer anio, String creaPor, Date fechaMatriculaInicio, Date fechaMatriculaFin, MatriculaBean matriculaBean) throws Exception {
        return matriculaDAO.obtenerImpresionPorDeUsuarioMatPorGrado(uniNeg, anio, creaPor, fechaMatriculaInicio, fechaMatriculaFin, matriculaBean);
    }

    public List<MatriculaBean> obtenerFiltroEstudianteImpCompMasivoDeudaCole(MatriculaBean matriculaBean, Integer[] listaMeses) throws Exception {
        return matriculaDAO.obtenerFiltroEstudianteImpCompMasivoDeudaCole(matriculaBean, listaMeses);
    }

    public List<MatriculaBean> obtenerSeccionPorGrado(Integer idGradoAcademico, Integer anio, String uniNeg) throws Exception {
        return matriculaDAO.obtenerSeccionPorGrado(idGradoAcademico, anio, uniNeg);
    }

    public List<MatriculaBean> obtenerFiltroSaldoPensiones(String uniNeg, Integer mes, Integer anio, Integer idNivelAcademico, Integer idGradoAcademico) throws Exception {
        return matriculaDAO.obtenerFiltroSaldoPensiones(uniNeg, mes, anio, idNivelAcademico, idGradoAcademico);
    }

    public List<MatriculaBean> obtenerFiltroEstudianteMasivoSeccion(MatriculaBean matriculaBean) throws Exception {
        return matriculaDAO.obtenerFiltroEstudianteMasivoSeccion(matriculaBean);
    }

    public void modificarMatriculaSeccion(MatriculaBean matriculaBean) throws Exception {
        matriculaDAO.modificarMatriculaSeccion(matriculaBean);
    }

    public void corregirMatriculaOff(MatriculaBean matriculaBean) throws Exception {
        matriculaDAO.corregirMatriculaOff(matriculaBean);
    }

    public List<MatriculaBean> obtenerFiltroEstudianteGenerarRecibo(MatriculaBean matriculaBean) throws Exception {
        return matriculaDAO.obtenerFiltroEstudianteGenerarRecibo(matriculaBean);
    }

    public List<AlumnosIngresantesRetiradosRepBean> obtenerCabeceraIngresantesRetiradosTotal(String uniNeg, Integer anio, Date fechaInicioClases, Date fechaFinFiltro) throws Exception {
        return matriculaDAO.obtenerCabeceraIngresantesRetiradosTotal(uniNeg, anio, fechaInicioClases, fechaFinFiltro);
    }

    public List<AlumnosIngresantesRetiradosRepBean> obtenerDetalleIngresantesRetiradosTotal(String uniNeg, Integer anio, Date fechaInicioClases, Date fechaFinFiltro, Integer idNivel) throws Exception {
        return matriculaDAO.obtenerDetalleIngresantesRetiradosTotal(uniNeg, anio, fechaInicioClases, fechaFinFiltro, idNivel);
    }

    public List<AlumnosIngresantesRetiradosRepBean> obtenerSubDetalleIngresantesRetiradosTotal(String uniNeg, Integer anio, Date fechaInicioClases, Date fechaFinFiltro, Integer idGrado) throws Exception {
        return matriculaDAO.obtenerSubDetalleIngresantesRetiradosTotal(uniNeg, anio, fechaInicioClases, fechaFinFiltro, idGrado);
    }

    public List<AlumnosIngresantesRetiradosRepBean> obtenerNivelesIngresantesRetiradosTotal(String uniNeg, Integer anio, Date fechaInicioClases, Date fechaFinFiltro) throws Exception {
        return matriculaDAO.obtenerNivelesIngresantesRetiradosTotal(uniNeg, anio, fechaInicioClases, fechaFinFiltro);
    }

    public List<ListaAlumnosRepBean> obtenerListaEstudiantesMatr(String uniNeg, Integer anio, Integer flg, Integer idGrado, Integer idNivel,String seccion) throws Exception {
        return matriculaDAO.obtenerListaEstudiantesMatr(uniNeg, anio, flg, idGrado, idNivel, seccion);
    }

    public List<ListaAlumnosRepBean> obtenerListaEstudiantesMatrDetalle(String uniNeg, Integer anio, Integer flg, Integer idGrado, Integer idNivel, String seccion) throws Exception {
        return matriculaDAO.obtenerListaEstudiantesMatrDetalle(uniNeg, anio, flg, idGrado, idNivel, seccion);
    }

    public List<DeclaracionJuradaRepBean> obtenerDeclaracionJuradaPrimera(String uniNeg, Integer anio, String idEstudiante) throws Exception {
        return matriculaDAO.obtenerDeclaracionJuradaPrimera(uniNeg, anio, idEstudiante);
    }

    public List<DeclaracionJuradaRepBean> obtenerDeclaracionJuradaCabecera(String uniNeg, Integer anio) throws Exception {
        return matriculaDAO.obtenerDeclaracionJuradaCabecera(uniNeg, anio);
    }

    public List<DeclaracionJuradaRepBean> obtenerDeclaracionJuradaSegunda(String uniNeg, Integer anio, String idEstudiante) throws Exception {
        return matriculaDAO.obtenerDeclaracionJuradaSegunda(uniNeg, anio, idEstudiante);
    } 

    public List<DeclaracionJuradaRepBean> obtenerDeclaracionJuradaTercera(String uniNeg, Integer anio, String idEstudiante) throws Exception {
        return matriculaDAO.obtenerDeclaracionJuradaTercera(uniNeg, anio, idEstudiante);
    }

    public List<MatriculaBean> obtenerFiltroEstudianteGenerarSinRecibo(MatriculaBean matriculaBean) throws Exception {
        return matriculaDAO.obtenerFiltroEstudianteGenerarSinRecibo(matriculaBean);
    }
    
}

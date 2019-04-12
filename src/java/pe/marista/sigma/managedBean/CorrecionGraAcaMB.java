/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.NivelAcademicoBean;
import pe.marista.sigma.bean.ProgramacionBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.GradoAcademicoService;
import pe.marista.sigma.service.MatriculaService;
import pe.marista.sigma.service.ProgramacionService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS001
 */
public final class CorrecionGraAcaMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of CorrecionGraAcaMB
     */
    @PostConstruct
    public void CorrecionGraAcaMB() {
        try {
            usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");

            Calendar miCalendario = Calendar.getInstance();
            getMatriculaFiltroBean().setAnio(miCalendario.get(Calendar.YEAR));
            getMatriculaFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }

    }

    private MatriculaBean matriculaBean;
    private MatriculaBean matriculaFiltroBean;
    private List<MatriculaBean> listaMatriculaEstudiantesBean;
    private List<GradoAcademicoBean> listaGradoAcademicoBean;
    private GradoAcademicoBean gradoAcademicoBean;
    private UsuarioBean usuarioLogin;
    private Boolean flgMostrarLista = false;
    //Filtros
    private List<ProgramacionBean> listaProgramacionBean;
//    private List<NivelAcademicoBean> listaNivelAcademicoBean;
    private List<GradoAcademicoBean> listaGradoAcademicoFiltroBean;
    private List<Integer> listaAnioFiltroMatricula;

    public void cargarFiltro() {
        try {
            limpiarFiltroMatricula();
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            getListaGradoAcademicoBean();
            listaGradoAcademicoFiltroBean = gradoAcademicoService.obtenerTodosMatri();

            Calendar miCalendario = Calendar.getInstance();
            getListaAnioFiltroMatricula();
            for (int i = miCalendario.get(Calendar.YEAR) - 5; i <= miCalendario.get(Calendar.YEAR)+5; i++) {
                listaAnioFiltroMatricula.add(i);
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFiltroEstudianteMasivo() {
        try {
            int estado = 0;
            MatriculaService matriculaService = BeanFactory.getMatriculaService();

            estado = 1;
            if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona().equals("")) {
                matriculaFiltroBean.getEstudianteBean().getPersonaBean().setIdPersona(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getIdPersona());
                estado = 1;
            }
            if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat().equals("")) {
                matriculaFiltroBean.getEstudianteBean().getPersonaBean().setApepat(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApepat());
                estado = 1;
            }
            if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat().equals("")) {
                matriculaFiltroBean.getEstudianteBean().getPersonaBean().setApemat(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getApemat());
                estado = 1;
            }
            if (matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre() != null && !matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre().equals("")) {
                matriculaFiltroBean.getEstudianteBean().getPersonaBean().setNombre(matriculaFiltroBean.getEstudianteBean().getPersonaBean().getNombre());
                estado = 1;
            } 
            if (matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getIdGradoAcademico() != null && !matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getIdGradoAcademico().equals(0)) {
                matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().setIdGradoAcademico(matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getIdGradoAcademico());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaMatriculaEstudiantesBean = new ArrayList<>();
            }
            if (estado == 1) {
                listaMatriculaEstudiantesBean = matriculaService.obtenerFiltroEstudianteMasivoCorreccionGrado(matriculaFiltroBean);
                if (listaMatriculaEstudiantesBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaMatriculaEstudiantesBean = new ArrayList<>();
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String cambiarGradoMatricula() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                MatriculaService matriculaService = BeanFactory.getMatriculaService();
                matriculaBean.setModiPor(usuarioLogin.getUsuario());
                matriculaService.modificarMatriculaGradoAca(matriculaBean, usuarioLogin, gradoAcademicoBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void ponerEstudianteEnMatricula(Object matricula) {
        try {
            matriculaBean = (MatriculaBean) matricula;
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            matriculaBean = matriculaService.obtenerMatriculaPorId(matriculaBean);
            Integer estado = 0;
            switch (matriculaBean.getGradoAcademicoBean().getNombre()) {
                case MaristaConstantes.PreInicial_4_anios:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.PreInicial_3_a�os);
                    gradoAcademicoBean.setNombre(MaristaConstantes.PreInicial_3_anios);
                    estado = 1;
                    break;
                case MaristaConstantes.Inicial_5_anios:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.PreInicial_4_a�os);
                    gradoAcademicoBean.setNombre(MaristaConstantes.PreInicial_4_anios);
                    estado = 1;
                    break;
                case MaristaConstantes.Primero_Primaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Inicial_5_a�os);
                    gradoAcademicoBean.setNombre(MaristaConstantes.Inicial_5_anios);
                    estado = 1;
                    break;
                case MaristaConstantes.Segundo_Primaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Primero_Primaria);
                    gradoAcademicoBean.setNombre(MaristaConstantes.Primero_Primaria);
                    estado = 1;
                    break;
                case MaristaConstantes.Tercero_Primaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Segundo_Primaria);
                    gradoAcademicoBean.setNombre(MaristaConstantes.Segundo_Primaria);
                    estado = 1;
                    break;
                case MaristaConstantes.Cuarto_Primaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Tercero_Primaria);
                    gradoAcademicoBean.setNombre(MaristaConstantes.Tercero_Primaria);
                    estado = 1;
                    break;
                case MaristaConstantes.Quinto_Primaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Cuarto_Primaria);
                    gradoAcademicoBean.setNombre(MaristaConstantes.Cuarto_Primaria);
                    estado = 1;
                    break;
                case MaristaConstantes.Sexto_Primaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Quinto_Primaria);
                    gradoAcademicoBean.setNombre(MaristaConstantes.Quinto_Primaria);
                    estado = 1;
                    break;
                case MaristaConstantes.Primero_Secundaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Sexto_Primaria);
                    gradoAcademicoBean.setNombre(MaristaConstantes.Sexto_Primaria);
                    estado = 1;
                    break;
                case MaristaConstantes.Segundo_Secundaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Primero_Secundaria);
                    gradoAcademicoBean.setNombre(MaristaConstantes.Primero_Secundaria);
                    estado = 1;
                    break;
                case MaristaConstantes.Tercero_Secundaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Segundo_Secundaria);
                    gradoAcademicoBean.setNombre(MaristaConstantes.Segundo_Secundaria);
                    estado = 1;
                    break;
                case MaristaConstantes.Cuarto_Secundaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Tercero_Secundaria);
                    estado = 3;
                    this.setFlgMostrarLista(true);
                    break;
                case MaristaConstantes.Cuarto_Bach_Secundaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Tercero_Secundaria);
                    estado = 3;
                    this.setFlgMostrarLista(true);
                    break;
                case MaristaConstantes.Quinto_Secundaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Cuarto_Secundaria);
                    estado = 2;
                    this.setFlgMostrarLista(true);
                    break;
                case MaristaConstantes.Quinto_Bach_Secundaria:
//                    matriculaBean.getGradoAcademicoBean().setNombre(MaristaConstantes.Cuarto_Bach_Secundaria);
                    estado = 2;
                    this.setFlgMostrarLista(true);
                    break;
                default:
                    break;
            }
            if (estado == 1) {
                obtenerGradoAcaPorNombre();
            }
            if (estado == 2) {
                GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
                getListaGradoAcademicoBean();
                listaGradoAcademicoBean = gradoAcademicoService.obtenerQuintoCuartoBachiller();
            }
            if (estado == 3) {
                GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
                getListaGradoAcademicoBean();
                listaGradoAcademicoBean = gradoAcademicoService.obtenerCuartoBachillerTercero();
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //para el combo 
    public void obtenerNombreGradoAca() {
        try {
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            gradoAcademicoBean = gradoAcademicoService.obtenerPorId(gradoAcademicoBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //para obtener el id por nombre
    public void obtenerGradoAcaPorNombre() {
        try {
//            gradoAcademicoBean =(GradoAcademicoBean)grado;
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            gradoAcademicoBean = gradoAcademicoService.obtenerPorIdNombre(gradoAcademicoBean.getNombre());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarFiltroMatricula() {
        matriculaFiltroBean = new MatriculaBean();
        listaMatriculaEstudiantesBean = new ArrayList<>();

        Calendar miCalendario = Calendar.getInstance();
        getMatriculaFiltroBean().setAnio(miCalendario.get(Calendar.YEAR));
        getMatriculaFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
    }

    public void limpiarMatricula() {
        matriculaBean = new MatriculaBean();
        gradoAcademicoBean = new GradoAcademicoBean();
        this.setFlgMostrarLista(false);
    }

    public void validar() {
        try {
            if (gradoAcademicoBean.getIdGradoAcademico() != null && matriculaBean.getIdMatricula() != null) {
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            } else {
                new MensajePrime().addInformativeMessagePer("errorEstNoSelec");
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
//getter and setter

    public MatriculaBean getMatriculaBean() {
        if (matriculaBean == null) {
            matriculaBean = new MatriculaBean();
        }
        return matriculaBean;
    }

    public void setMatriculaBean(MatriculaBean matriculaBean) {
        this.matriculaBean = matriculaBean;
    }

    public List<GradoAcademicoBean> getListaGradoAcademicoBean() {
        if (listaGradoAcademicoBean == null) {
            listaGradoAcademicoBean = new ArrayList<>();
        }
        return listaGradoAcademicoBean;
    }

    public void setListaGradoAcademicoBean(List<GradoAcademicoBean> listaGradoAcademicoBean) {
        this.listaGradoAcademicoBean = listaGradoAcademicoBean;
    }

    public List<ProgramacionBean> getListaProgramacionBean() {
        if (listaProgramacionBean == null) {
            listaProgramacionBean = new ArrayList<>();
        }
        return listaProgramacionBean;
    }

    public void setListaProgramacionBean(List<ProgramacionBean> listaProgramacionBean) {
        this.listaProgramacionBean = listaProgramacionBean;
    }

//    public List<NivelAcademicoBean> getListaNivelAcademicoBean() {
//        if (listaNivelAcademicoBean == null) {
//            listaNivelAcademicoBean = new ArrayList<>();
//        }
//        return listaNivelAcademicoBean;
//    }
//
//    public void setListaNivelAcademicoBean(List<NivelAcademicoBean> listaNivelAcademicoBean) {
//        this.listaNivelAcademicoBean = listaNivelAcademicoBean;
//    }
    public List<GradoAcademicoBean> getListaGradoAcademicoFiltroBean() {
        if (listaGradoAcademicoFiltroBean == null) {
            listaGradoAcademicoFiltroBean = new ArrayList<>();
        }
        return listaGradoAcademicoFiltroBean;
    }

    public void setListaGradoAcademicoFiltroBean(List<GradoAcademicoBean> listaGradoAcademicoFiltroBean) {
        this.listaGradoAcademicoFiltroBean = listaGradoAcademicoFiltroBean;
    }

    public MatriculaBean getMatriculaFiltroBean() {
        if (matriculaFiltroBean == null) {
            matriculaFiltroBean = new MatriculaBean();
        }
        return matriculaFiltroBean;
    }

    public void setMatriculaFiltroBean(MatriculaBean matriculaFiltroBean) {
        this.matriculaFiltroBean = matriculaFiltroBean;
    }

    public List<MatriculaBean> getListaMatriculaEstudiantesBean() {
        if (listaMatriculaEstudiantesBean == null) {
            listaMatriculaEstudiantesBean = new ArrayList<>();
        }
        return listaMatriculaEstudiantesBean;
    }

    public void setListaMatriculaEstudiantesBean(List<MatriculaBean> listaMatriculaEstudiantesBean) {
        this.listaMatriculaEstudiantesBean = listaMatriculaEstudiantesBean;
    }

    public List<Integer> getListaAnioFiltroMatricula() {
        if (listaAnioFiltroMatricula == null) {
            listaAnioFiltroMatricula = new ArrayList<>();
        }
        return listaAnioFiltroMatricula;
    }

    public void setListaAnioFiltroMatricula(List<Integer> listaAnioFiltroMatricula) {
        this.listaAnioFiltroMatricula = listaAnioFiltroMatricula;
    }

    public GradoAcademicoBean getGradoAcademicoBean() {
        if (gradoAcademicoBean == null) {
            gradoAcademicoBean = new GradoAcademicoBean();
        }
        return gradoAcademicoBean;
    }

    public void setGradoAcademicoBean(GradoAcademicoBean gradoAcademicoBean) {
        this.gradoAcademicoBean = gradoAcademicoBean;
    }

    public Boolean getFlgMostrarLista() {
        return flgMostrarLista;
    }

    public void setFlgMostrarLista(Boolean flgMostrarLista) {
        this.flgMostrarLista = flgMostrarLista;
    }

}

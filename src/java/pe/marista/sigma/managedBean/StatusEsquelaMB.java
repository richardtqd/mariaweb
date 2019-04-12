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
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.EsquelaBean;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.NivelAcademicoBean;
import pe.marista.sigma.bean.ProgramacionBean;
import pe.marista.sigma.bean.TipoFormacionBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.ViewMatriculaBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.GradoAcademicoService;
import pe.marista.sigma.service.MatriculaService;
import pe.marista.sigma.service.NivelAcademicoService;
import pe.marista.sigma.service.ProgramacionService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS002
 */
public class StatusEsquelaMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of StatusEsquelaMB
     */
    @PostConstruct
    public void StatusEsquelaMB() {
        try {
            usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");

            NivelAcademicoService nivelAcademicoService = BeanFactory.getNivelAcademicoService();
            getListaNivelAcademico();
            listaNivelAcademico = nivelAcademicoService.obtenerNivelAcaPorTipoFormacion(new TipoFormacionBean(MaristaConstantes.TIP_FOR_BAS));

            Calendar miCalendario = Calendar.getInstance();
            getMatriculaFiltroBean().setAnioIni(miCalendario.get(Calendar.YEAR));
            getMatriculaFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getMatriculaFiltroBean().setAnio(miCalendario.get(Calendar.YEAR) + 1);
            getViewMatriculaBean().setAnio(miCalendario.get(Calendar.YEAR) + 1);

            getListaAnioFiltroMatricula();
            for (int i = miCalendario.get(Calendar.YEAR) - 1; i <= miCalendario.get(Calendar.YEAR); i++) {
                listaAnioFiltroMatricula.add(i);
            }

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    private UsuarioBean usuarioLogin;

    private MatriculaBean matriculaFiltroBean;
    private List<MatriculaBean> listaMatriculaEstudiantesMasivosBean;
    private MatriculaBean matriculaBean;
    private ViewMatriculaBean viewMatriculaBean;

    //Nivel Academico(Codigos)
    private List<NivelAcademicoBean> listaNivelAcademico;
    private List<GradoAcademicoBean> listaGradoAcademicoFiltroBean;
    private List<ProgramacionBean> listaProgramacionBean;

    //Esquela
    private EsquelaBean esquelaBean;

    //Ayuda
    private Boolean flgTodos;
    private Boolean flgEstEsp;
    private Boolean flgEstSinPro;
    private Boolean flgPorNivelGrado;
    private Boolean envio;
    private List<Integer> listaAnioFiltroMatricula;

    //Metodos Get y Set
    public MatriculaBean getMatriculaFiltroBean() {
        if (matriculaFiltroBean == null) {
            matriculaFiltroBean = new MatriculaBean();
        }
        return matriculaFiltroBean;
    }

    public void setMatriculaFiltroBean(MatriculaBean matriculaFiltroBean) {
        this.matriculaFiltroBean = matriculaFiltroBean;
    }

    public List<MatriculaBean> getListaMatriculaEstudiantesMasivosBean() {
        if (listaMatriculaEstudiantesMasivosBean == null) {
            listaMatriculaEstudiantesMasivosBean = new ArrayList<>();
        }
        return listaMatriculaEstudiantesMasivosBean;
    }

    public void setListaMatriculaEstudiantesMasivosBean(List<MatriculaBean> listaMatriculaEstudiantesMasivosBean) {
        this.listaMatriculaEstudiantesMasivosBean = listaMatriculaEstudiantesMasivosBean;
    }

    public Boolean getFlgTodos() {
        return flgTodos;
    }

    public void setFlgTodos(Boolean flgTodos) {
        this.flgTodos = flgTodos;
    }

    public Boolean getFlgEstEsp() {
        return flgEstEsp;
    }

    public void setFlgEstEsp(Boolean flgEstEsp) {
        this.flgEstEsp = flgEstEsp;
    }

    public Boolean getFlgEstSinPro() {
        return flgEstSinPro;
    }

    public void setFlgEstSinPro(Boolean flgEstSinPro) {
        this.flgEstSinPro = flgEstSinPro;
    }

    public Boolean getFlgPorNivelGrado() {
        return flgPorNivelGrado;
    }

    public void setFlgPorNivelGrado(Boolean flgPorNivelGrado) {
        this.flgPorNivelGrado = flgPorNivelGrado;
    }

    public EsquelaBean getEsquelaBean() {
        if (esquelaBean == null) {
            esquelaBean = new EsquelaBean();
        }
        return esquelaBean;
    }

    public void setEsquelaBean(EsquelaBean esquelaBean) {
        this.esquelaBean = esquelaBean;
    }

    public Boolean getEnvio() {
        return envio;
    }

    public void setEnvio(Boolean envio) {
        this.envio = envio;
    }

    public List<NivelAcademicoBean> getListaNivelAcademico() {
        return listaNivelAcademico;
    }

    public void setListaNivelAcademico(List<NivelAcademicoBean> listaNivelAcademico) {
        this.listaNivelAcademico = listaNivelAcademico;
    }

    public List<GradoAcademicoBean> getListaGradoAcademicoFiltroBean() {
        if (listaGradoAcademicoFiltroBean == null) {
            listaGradoAcademicoFiltroBean = new ArrayList<>();
        }
        return listaGradoAcademicoFiltroBean;
    }

    public void setListaGradoAcademicoFiltroBean(List<GradoAcademicoBean> listaGradoAcademicoFiltroBean) {
        this.listaGradoAcademicoFiltroBean = listaGradoAcademicoFiltroBean;
    }

    public MatriculaBean getMatriculaBean() {
        if (matriculaBean == null) {
            matriculaBean = new MatriculaBean();
        }
        return matriculaBean;
    }

    public void setMatriculaBean(MatriculaBean matriculaBean) {
        this.matriculaBean = matriculaBean;
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

    public List<ProgramacionBean> getListaProgramacionBean() {
        if (listaProgramacionBean == null) {
            listaProgramacionBean = new ArrayList<>();
        }
        return listaProgramacionBean;
    }

    public void setListaProgramacionBean(List<ProgramacionBean> listaProgramacionBean) {
        this.listaProgramacionBean = listaProgramacionBean;
    }

    public UsuarioBean getUsuarioLogin() {
        if (usuarioLogin == null) {
            usuarioLogin = new UsuarioBean();
        }
        return usuarioLogin;
    }

    public void setUsuarioLogin(UsuarioBean usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public ViewMatriculaBean getViewMatriculaBean() {
        if (viewMatriculaBean == null) {
            viewMatriculaBean = new ViewMatriculaBean();
        }
        return viewMatriculaBean;
    }

    public void setViewMatriculaBean(ViewMatriculaBean viewMatriculaBean) {
        this.viewMatriculaBean = viewMatriculaBean;
    }

    //Metodos
    public void obtenerFiltroEstudianteMasivo() {
        try {
            int estado = 0;
            UsuarioBean beanUsuarioSesion = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            matriculaFiltroBean.setUnidadNegocioBean(beanUsuarioSesion.getPersonalBean().getUnidadNegocioBean());
            MatriculaService matriculaService = BeanFactory.getMatriculaService();
            if (flgTodos == true) {
                matriculaFiltroBean.setAnioFin(null);
                listaMatriculaEstudiantesMasivosBean = matriculaService.obtenerFiltroEstudianteMasivo(matriculaFiltroBean);
            } else {
                Calendar miCalendario = Calendar.getInstance();
                matriculaFiltroBean.setAnioFin(miCalendario.get(Calendar.YEAR) + 1);
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
                if (matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getNivelAcademicoBean().getIdNivelAcademico() != null && !matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getNivelAcademicoBean().getIdNivelAcademico().equals(0)) {
                    matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getNivelAcademicoBean().setIdNivelAcademico(matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getNivelAcademicoBean().getIdNivelAcademico());
                    estado = 1;
                }
                if (matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getIdGradoAcademico() != null && !matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getIdGradoAcademico().equals(0)) {
                    matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().setIdGradoAcademico(matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getIdGradoAcademico());
                    estado = 1;
                }
                if (matriculaFiltroBean.getEstudianteBean().getSeccion() != null && !matriculaFiltroBean.getEstudianteBean().getSeccion().equals("")) {
                    matriculaFiltroBean.getEstudianteBean().setSeccion(matriculaFiltroBean.getEstudianteBean().getSeccion());
                    estado = 1;
                } else if (estado == 0 && flgTodos == false) {
                    new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                    listaMatriculaEstudiantesMasivosBean = new ArrayList<>();
                }
                if (estado == 1) {
                    listaMatriculaEstudiantesMasivosBean = matriculaService.obtenerFiltroEstudianteMasivoDeudor(matriculaFiltroBean);
                    if (listaMatriculaEstudiantesMasivosBean.isEmpty()) {
                        new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                        listaMatriculaEstudiantesMasivosBean = new ArrayList<>();
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarEstudianteMatriculaMasivo() {
        try {
            matriculaFiltroBean = new MatriculaBean();
            listaMatriculaEstudiantesMasivosBean = new ArrayList<>();
            flgEstEsp = false;
            flgEstSinPro = false;
            flgPorNivelGrado = false;
            flgTodos = false;
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void activarMeses() {
        try {
            if (esquelaBean.getTipoEsquelaBean().getIdCodigo().equals(19701)) {
                envio = false;
                matriculaFiltroBean.setDato(2);
            } else {
                if (esquelaBean.getTipoEsquelaBean().getIdCodigo().equals(19702)) {
                    envio = true;
                    matriculaFiltroBean.setDato(1);
                } else {
                    if (esquelaBean.getTipoEsquelaBean().getIdCodigo().equals(19703)) {
                        envio = false;
                        matriculaFiltroBean.setDato(2);
                    }
                }
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroTodos() {
        try {
            if (this.flgTodos == true) {
                this.flgEstSinPro = false;
                this.flgPorNivelGrado = false;
                this.flgEstEsp = false;
                matriculaFiltroBean.setEstudianteBean(null);
                matriculaFiltroBean.getEstudianteBean().setGradoHabilitado(null);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroEstSinPro() {
        try {
            if (this.flgEstSinPro == true) {
                this.flgTodos = false;
                this.flgEstEsp = false;
                matriculaFiltroBean.setEstudianteBean(null);
                matriculaFiltroBean.getEstudianteBean().setGradoHabilitado(null);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroEstEsp() {
        try {
            if (this.flgEstEsp == true) {
                this.flgTodos = false;
                this.flgEstSinPro = false;
                this.flgPorNivelGrado = false;
                matriculaFiltroBean.setEstudianteBean(null);
                matriculaFiltroBean.getEstudianteBean().setGradoHabilitado(null);
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void verificarFiltroNivelGrado() {
        try {
            if (this.flgPorNivelGrado == true) {
                this.flgTodos = false;
                this.flgEstEsp = false;
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerGradoAcaBasica() {
        try {
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            listaGradoAcademicoFiltroBean = gradoAcademicoService.obtenerGradoAcaPorNivelAca(matriculaFiltroBean.getEstudianteBean().getGradoHabilitado().getNivelAcademicoBean().getIdNivelAcademico());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void actualizarAnioFinFiltroPorProgramacion() { //obtener el anio siguiente
        try {

            ProgramacionService programacionService = BeanFactory.getProgramacionService();
            getListaProgramacionBean();
//            System.out.println(MaristaConstantes.COD_MATRICULA);
//            listaProgramacionBean = programacionService.obtenerPrograPorTipo(MaristaConstantes.COD_MATRICULA);
            listaProgramacionBean = programacionService.obtenerProgPorTipoPorAnioPorUniNeg(MaristaConstantes.TIP_COD_PROC, MaristaConstantes.COD_MATRICULA, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), matriculaFiltroBean.getAnioIni());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

}

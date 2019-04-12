/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.EsquelaBean;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.MatriculaBean;
import pe.marista.sigma.bean.NivelAcademicoBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.GradoAcademicoService;
import pe.marista.sigma.service.MatriculaService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author JC
 */
public class MailMB extends BaseMB implements Serializable {

    @PostConstruct
    public void MailMB() {
        try {
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaTipoEsquelaBean();
            listaTipoEsquelaBean = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_ESQUELA));
            listaMesesForSup();
            getMatriculaFiltroBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    private EsquelaBean esquelaBean;
    private MatriculaBean matriculaFiltroBean;
    private List<MatriculaBean> listaMatriculaEstudiantesMasivosBean;
    private List<NivelAcademicoBean> listaNivelAcademico;
    private List<GradoAcademicoBean> listaGradoAcademicoFiltroBean;
    private List<CodigoBean> listaTipoEsquelaBean;
    private MatriculaBean matriculaBean;
    private Boolean flgTodos;
    private Boolean flgEstSinPro;
    private Boolean flgPorNivelGrado;
    private Boolean flgEstEsp;
    private Boolean valAdmTodos = true;
    private Boolean flgTodosMatriculados;
    private Boolean flgEstEspMatricula;
    private Map<String, String> listaMeses;

    public EsquelaBean getEsquelaBean() {
        if (esquelaBean == null) {
            esquelaBean = new EsquelaBean();
        }
        return esquelaBean;
    }

    public void setEsquelaBean(EsquelaBean esquelaBean) {
        this.esquelaBean = esquelaBean;
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

    public List<MatriculaBean> getListaMatriculaEstudiantesMasivosBean() {
        if (listaMatriculaEstudiantesMasivosBean == null) {
            listaMatriculaEstudiantesMasivosBean = new ArrayList<>();
        }
        return listaMatriculaEstudiantesMasivosBean;
    }

    public void setListaMatriculaEstudiantesMasivosBean(List<MatriculaBean> listaMatriculaEstudiantesMasivosBean) {
        this.listaMatriculaEstudiantesMasivosBean = listaMatriculaEstudiantesMasivosBean;
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

    public Boolean getFlgTodos() {
        return flgTodos;
    }

    public void setFlgTodos(Boolean flgTodos) {
        this.flgTodos = flgTodos;
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

    public Boolean getFlgEstEsp() {
        return flgEstEsp;
    }

    public void setFlgEstEsp(Boolean flgEstEsp) {
        this.flgEstEsp = flgEstEsp;
    }

    public Boolean getValAdmTodos() {
        return valAdmTodos;
    }

    public void setValAdmTodos(Boolean valAdmTodos) {
        this.valAdmTodos = valAdmTodos;
    }

    public Boolean getFlgTodosMatriculados() {
        return flgTodosMatriculados;
    }

    public void setFlgTodosMatriculados(Boolean flgTodosMatriculados) {
        this.flgTodosMatriculados = flgTodosMatriculados;
    }

    public Boolean getFlgEstEspMatricula() {
        return flgEstEspMatricula;
    }

    public void setFlgEstEspMatricula(Boolean flgEstEspMatricula) {
        this.flgEstEspMatricula = flgEstEspMatricula;
    }

    public List<NivelAcademicoBean> getListaNivelAcademico() {
        if (listaNivelAcademico == null) {
            listaNivelAcademico = new ArrayList<>();
        }
        return listaNivelAcademico;
    }

    public void setListaNivelAcademico(List<NivelAcademicoBean> listaNivelAcademico) {
        this.listaNivelAcademico = listaNivelAcademico;
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

    public Map<String, String> getListaMeses() {
        return listaMeses;
    }

    public void setListaMeses(Map<String, String> listaMeses) {
        this.listaMeses = listaMeses;
    }

    public List<CodigoBean> getListaTipoEsquelaBean() {
        if (listaTipoEsquelaBean == null) {
            listaTipoEsquelaBean = new ArrayList<>();
        }
        return listaTipoEsquelaBean;
    }

    public void setListaTipoEsquelaBean(List<CodigoBean> listaTipoEsquelaBean) {
        this.listaTipoEsquelaBean = listaTipoEsquelaBean;
    }

    // Metodos
    public void listaMesesForSup() {
        try {
            listaMeses = new LinkedHashMap<>();
            listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaEnero", null), "1");
            listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaFebrero", null), "2");
            listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaMarzo", null), "3");
            listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaAbril", null), "4");
            listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaMayo", null), "5");
            listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaJunio", null), "6");
            listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaJulio", null), "7");
            listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaAgosto", null), "8");
            listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaSetiembre", null), "9");
            listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaOctubre", null), "10");
            listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaNoviembre", null), "11");
            listaMeses.put(MensajesBackEnd.getValueOfKey("etiquetaDiciembre", null), "12");
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        listaMeses = Collections.unmodifiableMap(listaMeses);
    }

    public void obtenerFiltroEstudianteMasivo() {
        try {
            int estado = 0;
            if (matriculaFiltroBean.getDato() != null) {
                if (matriculaFiltroBean.getDato().equals(1)) {
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
                } else {
                    if (matriculaFiltroBean.getDato().equals(2)) {
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
                                listaMatriculaEstudiantesMasivosBean = matriculaService.obtenerFiltroEstudianteMasivo(matriculaFiltroBean);
                                if (listaMatriculaEstudiantesMasivosBean.isEmpty()) {
                                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                                    listaMatriculaEstudiantesMasivosBean = new ArrayList<>();
                                }
                            }
                        }
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

}

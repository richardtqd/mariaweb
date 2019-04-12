package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.EventoBean;
import pe.marista.sigma.bean.EventoTipoPaganteBean;
import pe.marista.sigma.bean.FichaBean;
import pe.marista.sigma.bean.GradoAcademicoBean;
import pe.marista.sigma.bean.NivelAcademicoBean;
import pe.marista.sigma.bean.PaganteBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.TipoFormacionBean;
import pe.marista.sigma.bean.TipoPaganteBean;
import pe.marista.sigma.bean.UnidadOrganicaBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.EventoService;
import pe.marista.sigma.service.GradoAcademicoService;
import pe.marista.sigma.service.NivelAcademicoService;
import pe.marista.sigma.service.PaganteService;
import pe.marista.sigma.service.TipoPaganteService;
import pe.marista.sigma.service.UnidadOrganicaService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

public class GrafoEventoMB implements Serializable{

    @PostConstruct
    public void GrafoEventoMB() {
        try {
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getPaganteBean();
            getPaganteBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            cargarDatos();
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    //CLASES
    private PaganteBean paganteBean;
    private UsuarioBean usuarioLoginBean;

    //LISTAS
    private List<EventoBean> listaEventoBean;
    private List<EventoTipoPaganteBean> listaEventoTipoPaganteBean;
    private List<TipoPaganteBean> listaTipoPaganteBean;
    private List<FichaBean> listaFichaBean;
    private List<CodigoBean> listaTipoEstado;
    private List<CodigoBean> listaTipoModoPago;
    private List<NivelAcademicoBean> listaNivelAcademicoBean;
    private List<GradoAcademicoBean> listaGradoAcademicoFiltroBean;
    private List<UnidadOrganicaBean> listaUnidadOrganicaBean;
    private List<PaganteBean> listaPaganteBean;

    //VARIABLES DE AYUDA
    private Integer tipPagante;
    private Integer selFiltro;

    //FLG DE ESTUDIANTE
    private Boolean FlgEstudiante;
    private Boolean flgNivGrad;
    private Boolean flgEstEsp;

    //FLG DE PERSONAL
    private Boolean flgPersonal;
    private Boolean flgUniOrg;
    private Boolean flgPerEsp;

    //FLG EXTERNO
    private Boolean flgExterno;
    private Boolean flgExtEsp;

    //FLG ENTIDAD
    private Boolean flgEntidad;
    private Boolean flgEntEsp;

    //METODOS DE CLASE
    public void cargarDatos() {
        try {
            //OBTENIENDO LISTA DE EVENTOS
            EventoBean eventoBean = new EventoBean();
            eventoBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            EventoService eventoService = BeanFactory.getEventoService();
            getListaEventoBean();
            listaEventoBean = eventoService.obtener(eventoBean);

            //LISTA TIPO PAGANTE
            TipoPaganteBean tipoPaganteBean = new TipoPaganteBean();
            tipoPaganteBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            TipoPaganteService tipoPaganteService = BeanFactory.getTipoPaganteService();
            getListaTipoPaganteBean();
            listaTipoPaganteBean = tipoPaganteService.obtenerTipoPagante(tipoPaganteBean);

            //OBTENIENDO LISTA DE TIPOS
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaTipoEstado();
            listaTipoEstado = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_FICHA));
            getListaTipoModoPago();
            listaTipoModoPago = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_MOD_PAGO));

            //SETEANDO TIPO PAGANTE
            getPaganteBean();
            getPaganteBean().getTipoPaganteBean().setIdtipoPagante(1);
            getPaganteBean().getMatriculaBean().setSeccion(null);
            setSelFiltro(1);
            obtenerTiposFiltro();

            //OBTENER NIVEL ACADEMICO
            NivelAcademicoService nivelAcademicoService = BeanFactory.getNivelAcademicoService();
            getListaNivelAcademicoBean();
            listaNivelAcademicoBean = nivelAcademicoService.obtenerNivelAcaPorTipoFormacion(new TipoFormacionBean(MaristaConstantes.TIP_FOR_BAS));

            //OBTENIENDO UNIORG
            UnidadOrganicaService unidadOrganicaService = BeanFactory.getUnidadOrganicaService();
            getListaUnidadOrganicaBean();
            listaUnidadOrganicaBean = unidadOrganicaService.obtenerTodos();

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerGradoAcaBasica() {
        try {
            GradoAcademicoService gradoAcademicoService = BeanFactory.getGradoAcademicoService();
            listaGradoAcademicoFiltroBean = gradoAcademicoService.obtenerGradoAcaPorNivelAca(paganteBean.getMatriculaBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerTiposFiltro() {
        try {
            getPaganteBean();
            if (getPaganteBean().getTipoPaganteBean().getIdtipoPagante().equals(1)) {
                setFlgEstudiante(true);
                setFlgPersonal(false);
                setFlgExterno(false);
                setFlgEntidad(false);
                flgNivGrad = true;
                flgUniOrg = false;
                flgPerEsp = false;
                flgEstEsp = false;
                flgExtEsp = false;
                flgEntEsp = false;
                setSelFiltro(1);
            } else if (getPaganteBean().getTipoPaganteBean().getIdtipoPagante().equals(2)) {
                setFlgPersonal(true);
                setFlgEstudiante(false);
                setFlgExterno(false);
                setFlgEntidad(false);
                flgUniOrg = true;
                flgPerEsp = false;
                flgNivGrad = false;
                flgEstEsp = false;
                flgExtEsp = false;
                flgEntEsp = false;
                setSelFiltro(3);
            } else if (getPaganteBean().getTipoPaganteBean().getIdtipoPagante().equals(3)) {
                setFlgExterno(true);
                setFlgPersonal(false);
                setFlgEstudiante(false);
                setFlgEntidad(false);
                setFlgExtEsp(true);
                flgUniOrg = false;
                flgPerEsp = false;
                flgNivGrad = false;
                flgEstEsp = false;
                flgExtEsp = false;
                flgEntEsp = false;
                setSelFiltro(5);
            } else if (getPaganteBean().getTipoPaganteBean().getIdtipoPagante().equals(4)) {
                setFlgEntidad(true);
                setFlgPersonal(false);
                setFlgEstudiante(false);
                setFlgExterno(false);
                setFlgEntEsp(true);
                flgUniOrg = false;
                flgPerEsp = false;
                flgNivGrad = false;
                flgEstEsp = false;
                flgExtEsp = false;
                flgEntEsp = false;
                setSelFiltro(6);
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerTiposFiltroSub() {
        try {
            if (getSelFiltro() != null) {
                if (getSelFiltro() == 1) { //POR NIVEL Y GRADO
                    flgNivGrad = true;
                    flgEstEsp = false;
                    flgUniOrg = false;
                    flgPerEsp = false;
                    flgExtEsp = false;
                    flgEntEsp = false;
                    paganteBean.getMatriculaBean().getEstudianteBean().setPersonaBean(null);
                } else if (getSelFiltro() == 2) { //ESTUDIANTE ESPECIFICO
                    flgNivGrad = false;
                    flgEstEsp = true;
                    flgUniOrg = false;
                    flgPerEsp = false;
                    flgExtEsp = false;
                    flgEntEsp = false;
                    paganteBean.getMatriculaBean().getGradoAcademicoBean().setNivelAcademicoBean(null);
                    paganteBean.getMatriculaBean().getEstudianteBean().setGradoHabilitado(null);
                } else if (getSelFiltro() == 3) { //POR UNIDAD ORGANICA
                    flgUniOrg = true;
                    flgPerEsp = false;
                    flgNivGrad = false;
                    flgEstEsp = false;
                    flgExtEsp = false;
                    flgEntEsp = false;
                    paganteBean.getMatriculaBean().getEstudianteBean().setPersonaBean(null);
                    paganteBean.setMatriculaBean(null);
                    paganteBean.getPersonalBean().setCodPer(null);
                    paganteBean.getPersonalBean().setNroDoc(null);
                    paganteBean.getPersonalBean().setNombre(null);
                    paganteBean.getPersonalBean().setApepat(null);
                    paganteBean.getPersonalBean().setApemat(null);
                } else if (getSelFiltro() == 4) { //PERSONAL ESPECIFICO
                    flgUniOrg = false;
                    flgPerEsp = true;
                    flgNivGrad = false;
                    flgEstEsp = false;
                    flgExtEsp = false;
                    flgEntEsp = false;
                    paganteBean.getMatriculaBean().getEstudianteBean().setPersonaBean(null);
                    paganteBean.setMatriculaBean(null);
                    paganteBean.getPersonalBean().setUnidadOrganicaBean(null);
                } else if (getSelFiltro() == 5) { //EXTERNO ESPECIFICO
                    flgExtEsp = true;
                    flgUniOrg = false;
                    flgPerEsp = false;
                    flgNivGrad = false;
                    flgEstEsp = false;
                    flgExtEsp = false;
                    flgEntEsp = false;
                    paganteBean.getMatriculaBean().getEstudianteBean().setPersonaBean(null);
                    paganteBean.getMatriculaBean().getGradoAcademicoBean().setNivelAcademicoBean(null);
                    paganteBean.getMatriculaBean().getEstudianteBean().setGradoHabilitado(null);
                    paganteBean.setMatriculaBean(null);
                    paganteBean.getPersonalBean().setCodPer(null);
                    paganteBean.getPersonalBean().setNroDoc(null);
                    paganteBean.getPersonalBean().setNombre(null);
                    paganteBean.getPersonalBean().setApepat(null);
                    paganteBean.getPersonalBean().setApemat(null);
                    paganteBean.getPersonalBean().setUnidadOrganicaBean(null);
                } else if (getSelFiltro() == 6) { //ENTIDAD ESPECIFICA
                    flgEntEsp = true;
                    flgUniOrg = false;
                    flgPerEsp = false;
                    flgNivGrad = false;
                    flgEstEsp = false;
                    flgExtEsp = false;
                    flgEntEsp = false;
                    paganteBean.getMatriculaBean().getEstudianteBean().setPersonaBean(null);
                    paganteBean.getMatriculaBean().getGradoAcademicoBean().setNivelAcademicoBean(null);
                    paganteBean.getMatriculaBean().getEstudianteBean().setGradoHabilitado(null);
                    paganteBean.setMatriculaBean(null);
                    paganteBean.getPersonalBean().setCodPer(null);
                    paganteBean.getPersonalBean().setNroDoc(null);
                    paganteBean.getPersonalBean().setNombre(null);
                    paganteBean.getPersonalBean().setApepat(null);
                    paganteBean.getPersonalBean().setApemat(null);
                    paganteBean.getPersonalBean().setUnidadOrganicaBean(null);
                }
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerFiltroPagante() {
        try {
            if (paganteBean.getTipoPaganteBean().getIdtipoPagante().equals(1)) {
                obtenerGrafoEstudiante();
            } else if (paganteBean.getTipoPaganteBean().getIdtipoPagante().equals(2)) {

            } else if (paganteBean.getTipoPaganteBean().getIdtipoPagante().equals(3)) {

            } else if (paganteBean.getTipoPaganteBean().getIdtipoPagante().equals(4)) {

            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void obtenerGrafoEstudiante() {
        try {
            PaganteService paganteService = BeanFactory.getPaganteService();
            Integer estado = 0;
            if (paganteBean.getMatriculaBean().getEstudianteBean().getPersonaBean().getIdPersona() != null && !paganteBean.getMatriculaBean().getEstudianteBean().getPersonaBean().getIdPersona().equals("")) {
                paganteBean.getMatriculaBean().getEstudianteBean().getPersonaBean().setIdPersona(paganteBean.getMatriculaBean().getEstudianteBean().getPersonaBean().getIdPersona());
                estado = 1;
            }
            if (paganteBean.getMatriculaBean().getEstudianteBean().getCodigo() != null && !paganteBean.getMatriculaBean().getEstudianteBean().getCodigo().equals("")) {
                paganteBean.getMatriculaBean().getEstudianteBean().setCodigo(paganteBean.getMatriculaBean().getEstudianteBean().getCodigo());
                estado = 1;
            }
            if (paganteBean.getMatriculaBean().getEstudianteBean().getPersonaBean().getApepat() != null && !paganteBean.getMatriculaBean().getEstudianteBean().getPersonaBean().getApepat().equals("")) {
                paganteBean.getMatriculaBean().getEstudianteBean().getPersonaBean().setApepat(paganteBean.getMatriculaBean().getEstudianteBean().getPersonaBean().getApepat());
                estado = 1;
            }
            if (paganteBean.getMatriculaBean().getEstudianteBean().getPersonaBean().getApemat() != null && !paganteBean.getMatriculaBean().getEstudianteBean().getPersonaBean().getApemat().equals("")) {
                paganteBean.getMatriculaBean().getEstudianteBean().getPersonaBean().setApemat(paganteBean.getMatriculaBean().getEstudianteBean().getPersonaBean().getApemat());
                estado = 1;
            }
            if (paganteBean.getMatriculaBean().getEstudianteBean().getPersonaBean().getNombre() != null && !paganteBean.getMatriculaBean().getEstudianteBean().getPersonaBean().getNombre().equals("")) {
                paganteBean.getMatriculaBean().getEstudianteBean().getPersonaBean().setNombre(paganteBean.getMatriculaBean().getEstudianteBean().getPersonaBean().getNombre());
                estado = 1;
            }
            if (paganteBean.getMatriculaBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico() != null && !paganteBean.getMatriculaBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico().equals(0)) {
                paganteBean.getMatriculaBean().getGradoAcademicoBean().getNivelAcademicoBean().setIdNivelAcademico(paganteBean.getMatriculaBean().getGradoAcademicoBean().getNivelAcademicoBean().getIdNivelAcademico());
                estado = 1;
            }
            if (paganteBean.getMatriculaBean().getSeccion() != null && !paganteBean.getMatriculaBean().getSeccion().equals("")) {
                paganteBean.getMatriculaBean().setSeccion(paganteBean.getMatriculaBean().getSeccion());
                estado = 1;
            }
            if (paganteBean.getIdTipoEstado() != null) {
                paganteBean.setIdTipoEstado(paganteBean.getIdTipoEstado());
                estado = 1;
            }
            if (paganteBean.getIdTipoModoPago() != null) {
                paganteBean.setIdTipoModoPago(paganteBean.getIdTipoModoPago());
                estado = 1;
            }
            if (paganteBean.getMatriculaBean().getGradoAcademicoBean().getIdGradoAcademico() != null && !paganteBean.getMatriculaBean().getGradoAcademicoBean().getIdGradoAcademico().equals(0)) {
                paganteBean.getMatriculaBean().getGradoAcademicoBean().setIdGradoAcademico(paganteBean.getMatriculaBean().getGradoAcademicoBean().getIdGradoAcademico());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaPaganteBean = new ArrayList<>();
            }
            if (estado == 1) {
//                listaPaganteBean = paganteService.obtenerGrafoFichaEstudiante(paganteBean);
                if (listaPaganteBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaPaganteBean = new ArrayList<>();
                }
            }
        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void limpiarFiltroPagante() {
        try {

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public PaganteBean getPaganteBean() {
        if (paganteBean == null) {
            paganteBean = new PaganteBean();
        }
        return paganteBean;
    }

    public void setPaganteBean(PaganteBean paganteBean) {
        this.paganteBean = paganteBean;
    }

    public List<FichaBean> getListaFichaBean() {
        if (listaFichaBean == null) {
            listaFichaBean = new ArrayList<>();
        }
        return listaFichaBean;
    }

    public void setListaFichaBean(List<FichaBean> listaFichaBean) {
        this.listaFichaBean = listaFichaBean;
    }

    public Integer getTipPagante() {
        return tipPagante;
    }

    public void setTipPagante(Integer tipPagante) {
        this.tipPagante = tipPagante;
    }

    public UsuarioBean getUsuarioLoginBean() {
        if (usuarioLoginBean == null) {
            usuarioLoginBean = new UsuarioBean();
        }
        return usuarioLoginBean;
    }

    public void setUsuarioLoginBean(UsuarioBean usuarioLoginBean) {
        this.usuarioLoginBean = usuarioLoginBean;
    }

    public List<EventoBean> getListaEventoBean() {
        if (listaEventoBean == null) {
            listaEventoBean = new ArrayList<>();
        }
        return listaEventoBean;
    }

    public void setListaEventoBean(List<EventoBean> listaEventoBean) {
        this.listaEventoBean = listaEventoBean;
    }

    public List<TipoPaganteBean> getListaTipoPaganteBean() {
        if (listaTipoPaganteBean == null) {
            listaTipoPaganteBean = new ArrayList<>();
        }
        return listaTipoPaganteBean;
    }

    public void setListaTipoPaganteBean(List<TipoPaganteBean> listaTipoPaganteBean) {
        this.listaTipoPaganteBean = listaTipoPaganteBean;
    }

    public List<EventoTipoPaganteBean> getListaEventoTipoPaganteBean() {
        if (listaEventoTipoPaganteBean == null) {
            listaEventoTipoPaganteBean = new ArrayList<>();
        }
        return listaEventoTipoPaganteBean;
    }

    public void setListaEventoTipoPaganteBean(List<EventoTipoPaganteBean> listaEventoTipoPaganteBean) {
        this.listaEventoTipoPaganteBean = listaEventoTipoPaganteBean;
    }

    public List<CodigoBean> getListaTipoEstado() {
        if (listaTipoEstado == null) {
            listaTipoEstado = new ArrayList<>();
        }
        return listaTipoEstado;
    }

    public void setListaTipoEstado(List<CodigoBean> listaTipoEstado) {
        this.listaTipoEstado = listaTipoEstado;
    }

    public List<CodigoBean> getListaTipoModoPago() {
        if (listaTipoModoPago == null) {
            listaTipoModoPago = new ArrayList<>();
        }
        return listaTipoModoPago;
    }

    public void setListaTipoModoPago(List<CodigoBean> listaTipoModoPago) {
        this.listaTipoModoPago = listaTipoModoPago;
    }

    public Integer getSelFiltro() {
        return selFiltro;
    }

    public void setSelFiltro(Integer selFiltro) {
        this.selFiltro = selFiltro;
    }

    public List<NivelAcademicoBean> getListaNivelAcademicoBean() {
        if (listaNivelAcademicoBean == null) {
            listaNivelAcademicoBean = new ArrayList<>();
        }
        return listaNivelAcademicoBean;
    }

    public void setListaNivelAcademicoBean(List<NivelAcademicoBean> listaNivelAcademicoBean) {
        this.listaNivelAcademicoBean = listaNivelAcademicoBean;
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

    public Boolean getFlgNivGrad() {
        return flgNivGrad;
    }

    public void setFlgNivGrad(Boolean flgNivGrad) {
        this.flgNivGrad = flgNivGrad;
    }

    public Boolean getFlgEstEsp() {
        return flgEstEsp;
    }

    public void setFlgEstEsp(Boolean flgEstEsp) {
        this.flgEstEsp = flgEstEsp;
    }

    public Boolean getFlgUniOrg() {
        return flgUniOrg;
    }

    public void setFlgUniOrg(Boolean flgUniOrg) {
        this.flgUniOrg = flgUniOrg;
    }

    public Boolean getFlgPerEsp() {
        return flgPerEsp;
    }

    public void setFlgPerEsp(Boolean flgPerEsp) {
        this.flgPerEsp = flgPerEsp;
    }

    public Boolean getFlgExtEsp() {
        return flgExtEsp;
    }

    public void setFlgExtEsp(Boolean flgExtEsp) {
        this.flgExtEsp = flgExtEsp;
    }

    public Boolean getFlgEntEsp() {
        return flgEntEsp;
    }

    public void setFlgEntEsp(Boolean flgEntEsp) {
        this.flgEntEsp = flgEntEsp;
    }

    public Boolean getFlgEstudiante() {
        return FlgEstudiante;
    }

    public void setFlgEstudiante(Boolean FlgEstudiante) {
        this.FlgEstudiante = FlgEstudiante;
    }

    public Boolean getFlgPersonal() {
        return flgPersonal;
    }

    public void setFlgPersonal(Boolean flgPersonal) {
        this.flgPersonal = flgPersonal;
    }

    public Boolean getFlgExterno() {
        return flgExterno;
    }

    public void setFlgExterno(Boolean flgExterno) {
        this.flgExterno = flgExterno;
    }

    public Boolean getFlgEntidad() {
        return flgEntidad;
    }

    public void setFlgEntidad(Boolean flgEntidad) {
        this.flgEntidad = flgEntidad;
    }

    public List<UnidadOrganicaBean> getListaUnidadOrganicaBean() {
        if (listaUnidadOrganicaBean == null) {
            listaUnidadOrganicaBean = new ArrayList<>();
        }
        return listaUnidadOrganicaBean;
    }

    public void setListaUnidadOrganicaBean(List<UnidadOrganicaBean> listaUnidadOrganicaBean) {
        this.listaUnidadOrganicaBean = listaUnidadOrganicaBean;
    }

    public List<PaganteBean> getListaPaganteBean() {
        if (listaPaganteBean == null) {
            listaPaganteBean = new ArrayList<>();
        }
        return listaPaganteBean;
    }

    public void setListaPaganteBean(List<PaganteBean> listaPaganteBean) {
        this.listaPaganteBean = listaPaganteBean;
    }

}

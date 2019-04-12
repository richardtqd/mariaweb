package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CursoTallerBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.ProgramacionBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.CursoTallerRepBean;
import pe.marista.sigma.bean.reporte.DetCursoTallerRepBean;
import pe.marista.sigma.bean.reporte.DetDetCursoTallerRepBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.CursoTallerService;
import pe.marista.sigma.service.PersonaService;
import pe.marista.sigma.service.ProgramacionService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author Administrador
 */
public class CursoTallerMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of CursoTallerMB
     */
    @PostConstruct
    public void CursoTallerMB() {
        try {

            usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            ProgramacionService programacionService = BeanFactory.getProgramacionService();
            getListaProgramacionBean();
            listaProgramacionBean = programacionService.obtenerProgPorTipoActivos(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            cursoTallerBean.setCollapsed(true);
            Calendar miCalendario = Calendar.getInstance();
            getCursoTallerFiltroBean().setAnio(miCalendario.get(Calendar.YEAR));
            getCursoTallerFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getListaAnioBean();
            for (int i = MaristaConstantes.ANO_INI_DEFAULT_COLE; i <= miCalendario.get(Calendar.YEAR) + 3; i++) {
                listaAnioBean.add(i);
            }

            fechaActual = new GregorianCalendar();
            getCursoTallerFiltroBean().setFechaInicio(fechaActual.getTime());
            getCursoTallerFiltroBean().setFechaFin(fechaActual.getTime());
            setStatusProgramacion(1);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    private CursoTallerBean cursoTallerBean;
    private CursoTallerBean cursoTallerFiltroBean;
    private List<CursoTallerBean> listaCursoTallerBean;
    private PersonaBean personaBean;
    private ProgramacionBean programacionBean;
    private boolean comodin;
    private List<ProgramacionBean> listaProgramacionBean;
    private List<PersonaBean> listaFiltroBean;
    private PersonaBean personaFiltroBean;
    private EstudianteBean estudianteFiltroBean;
    private List<EstudianteBean> listaEstudianteBean;
    private List<Integer> listaAnioBean;
    private List<Integer> listaIdProgramacion;

    private Calendar fechaActual;
    private Boolean valAdmTodos;

    private UsuarioBean usuarioLogin;
    //REPORT
    private List<DetCursoTallerRepBean> listaCursoTallerRepBean;

    private String descripcion;
    private Integer statusProgramacion;

    //Logica de Negocio
    public void listaCursoTaller() {
        try {
            ProgramacionService programacionService = BeanFactory.getProgramacionService();
            listaProgramacionBean = programacionService.obtenerProgPorTipoActivos(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarCursoTaller() {
        cursoTallerBean = new CursoTallerBean();
        programacionBean = new ProgramacionBean();
        personaBean = new PersonaBean();
        comodin = false;
//        cursoTallerBean.setCollapsed(false); // Cambios
    }

    public void limpiarCursoTallerFiltro() {
        cursoTallerFiltroBean = new CursoTallerBean();
        Calendar miCalendario = Calendar.getInstance();
        getCursoTallerFiltroBean().setAnio(miCalendario.get(Calendar.YEAR));
        getCursoTallerFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        listaCursoTallerBean = new ArrayList<>();
    }

    public void limpiarCursoTallerRepFiltro() {
        cursoTallerFiltroBean = new CursoTallerBean();
        fechaActual = new GregorianCalendar();
        getCursoTallerFiltroBean().setFechaInicio(fechaActual.getTime());
        getCursoTallerFiltroBean().setFechaFin(fechaActual.getTime());
        listaCursoTallerRepBean = new ArrayList<>();
        listaIdProgramacion = new ArrayList<>();
        listaProgramacionBean = new ArrayList<>();
        listaCursoTaller();
    }

    public void obtenerFiltrorCursoTaller() {
        try {
            int estado = 0;
            CursoTallerService cursoTallerService = BeanFactory.getCursoTallerService();
            if (cursoTallerFiltroBean.getAnio() != null && !cursoTallerFiltroBean.getAnio().equals(0)) {
                cursoTallerFiltroBean.setAnio(cursoTallerFiltroBean.getAnio());
                estado = 1;
            }
            if (cursoTallerFiltroBean.getPersonaBean().getIdPersona() != null && !cursoTallerFiltroBean.getPersonaBean().getIdPersona().equals("")) {
                cursoTallerFiltroBean.getPersonaBean().setIdPersona(cursoTallerFiltroBean.getPersonaBean().getIdPersona().toUpperCase().trim());
                estado = 1;
            }
            if (cursoTallerFiltroBean.getPersonaBean().getApepat() != null && !cursoTallerFiltroBean.getPersonaBean().getApepat().equals("")) {
                cursoTallerFiltroBean.getPersonaBean().setApepat(cursoTallerFiltroBean.getPersonaBean().getApepat().toUpperCase().trim());
                estado = 1;
            }
            if (cursoTallerFiltroBean.getPersonaBean().getApemat() != null && !cursoTallerFiltroBean.getPersonaBean().getApemat().equals("")) {
                cursoTallerFiltroBean.getPersonaBean().setApemat(cursoTallerFiltroBean.getPersonaBean().getApemat().toUpperCase().trim());
                estado = 1;
            }
            if (cursoTallerFiltroBean.getPersonaBean().getNombre() != null && !cursoTallerFiltroBean.getPersonaBean().getNombre().equals("")) {
                cursoTallerFiltroBean.getPersonaBean().setNombre(cursoTallerFiltroBean.getPersonaBean().getNombre().toUpperCase().trim());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            }
            if (estado == 1) {
                listaCursoTallerBean = cursoTallerService.obtenerFiltroCursoTaller(cursoTallerFiltroBean);
                if (listaCursoTallerBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaCursoTallerBean = new ArrayList<>();
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerCursoTaller() {
        try {
            CursoTallerService cursoTallerService = BeanFactory.getCursoTallerService();
            listaCursoTallerBean = cursoTallerService.obtenerCursoTaller();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public String insertarCursoTaller() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CursoTallerService cursoTallerService = BeanFactory.getCursoTallerService();
                UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
                cursoTallerBean.setPersonaBean(personaBean);
                cursoTallerBean.setProgramacionBean(programacionBean);
                cursoTallerBean.setCreaPor(usuarioBean.getUsuario());
                cursoTallerBean.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
                cursoTallerService.insertarCursoTaller(cursoTallerBean);
                listaCursoTallerBean = cursoTallerService.obtenerFiltroCursoTaller(cursoTallerFiltroBean);

                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarCursoTaller();
                listaCursoTaller();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String modificarCursoTaller() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CursoTallerService cursoTallerService = BeanFactory.getCursoTallerService();
                cursoTallerBean.setPersonaBean(personaBean);
                cursoTallerBean.setProgramacionBean(programacionBean);
                cursoTallerService.modificarCursoTaller(cursoTallerBean);
                listaCursoTallerBean = cursoTallerService.obtenerFiltroCursoTaller(cursoTallerFiltroBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                limpiarCursoTaller();
                listaCursoTaller();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public String eliminarCursoTaller() {
        String pagina = null;
        try {
            pagina = new MaristaUtils().validaUsuarioSesion();
            if (pagina == null) {
                CursoTallerService cursoTallerService = BeanFactory.getCursoTallerService();
                cursoTallerService.eliminarCursoTaller(cursoTallerBean.getIdCursoTaller());
                listaCursoTallerBean = cursoTallerService.obtenerFiltroCursoTaller(cursoTallerFiltroBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
                listaCursoTaller();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return pagina;
    }

    public void guardarCursoTaller() {
        if (cursoTallerBean.getIdCursoTaller() != null) {
            modificarCursoTaller();
        } else {
            insertarCursoTaller();
        }
    }

    public void rowSelect(SelectEvent event) {
        try {
            cursoTallerBean = (CursoTallerBean) event.getObject();
            CursoTallerService cursoTallerService = BeanFactory.getCursoTallerService();
            cursoTallerBean = cursoTallerService.obtenerCursoTallerPorId(cursoTallerBean.getIdCursoTaller(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ProgramacionService programacionService = BeanFactory.getProgramacionService();
            programacionBean = programacionService.obtenerPrograPorId(cursoTallerBean.getProgramacionBean());
            PersonaService personaService = BeanFactory.getPersonaService();
            cursoTallerBean.getPersonaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
            personaBean = personaService.obtenerPersPorId(cursoTallerBean.getPersonaBean());
            cursoTallerBean.setPersonaBean(personaBean);
            comodin = true;
            listaCursoTaller();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void ponerCursoTaller(Object cursoTaller) {
        try {
            cursoTallerBean = (CursoTallerBean) cursoTaller;
            CursoTallerService cursoTallerService = BeanFactory.getCursoTallerService();
            cursoTallerBean = cursoTallerService.obtenerCursoTallerPorId(cursoTallerBean.getIdCursoTaller(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            cursoTallerBean.setPersonaBean(personaBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public List<PersonaBean> completePersona(String query) {
        try {
            PersonaService personaService = BeanFactory.getPersonaService();
            List<PersonaBean> listaPersonaTodosBean = personaService.obtenerPersona();
            List<PersonaBean> listaPersonaFiltroBean = new ArrayList<>();
            for (int i = 0; i < listaPersonaTodosBean.size(); i++) {
                PersonaBean bean = listaPersonaTodosBean.get(i);
                if (bean.getNombreCompleto().toLowerCase().contains(query.toLowerCase())) {
                    listaPersonaFiltroBean.add(bean);
                }
            }
            return listaPersonaFiltroBean;

        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return null;
    }

    public void convertirStrFechasCursoTaller() {
        try {
//            String fechaInicio = null;
//            String fechaFin = null;
//            if (cursoTallerFiltroBean.getFechaInicio() != null) {
//                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                fechaInicio = sdf.format(cursoTallerFiltroBean.getFechaInicio());
//            }
//            if (cursoTallerFiltroBean.getFechaFin() != null) {
//                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                fechaFin = sdf.format(cursoTallerFiltroBean.getFechaFin());
//            }
            String uniNeg = usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            CursoTallerService cursoTallerService = BeanFactory.getCursoTallerService();
            listaIdProgramacion = new ArrayList<>();
            for (ProgramacionBean lista : listaProgramacionBean) {
                if (lista.getFlgSelect() != null) {
                    if (lista.getFlgSelect().equals(Boolean.TRUE)) {
                        listaIdProgramacion.add(lista.getIdProgramacion());
                    }
                }
            }
            Integer flg = 1;
            if (listaIdProgramacion.isEmpty()) {
                flg = 0;
            }
            listaCursoTallerRepBean = cursoTallerService.obtenerDetalleTallerRepDesc(uniNeg, cursoTallerFiltroBean.getFechaInicio(), cursoTallerFiltroBean.getFechaFin(), listaIdProgramacion, flg, descripcion.trim());
            System.out.println("listaCursoTallerRepBean size" + listaCursoTallerRepBean.size());
            //AGREGAR FILTRO DE DESCRIPCION EN PROGRAMACIÓN
            ProgramacionService programacionService = BeanFactory.getProgramacionService();
            ProgramacionBean progra = new ProgramacionBean();
            progra.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            progra.setDescripProgramacion(descripcion);
            progra.setStatusProgramacion(statusProgramacion);
            listaProgramacionBean = programacionService.obtenerProgPorTipoActivosRef(progra); 
            //END
            if (!listaCursoTallerRepBean.isEmpty()) {
                System.out.println("if (!listaCursoTallerRepBean.isEmpty()) ");
                for (int j = 0; j < listaCursoTallerRepBean.size(); j++) {
                    List<DetDetCursoTallerRepBean> listaInscritoPorTallerRep = new ArrayList<>();
                    listaInscritoPorTallerRep = cursoTallerService.obtenerInscritosTalleresRep(uniNeg, listaCursoTallerRepBean.get(j).getIdProgramacion(), cursoTallerFiltroBean.getFechaInicio(), cursoTallerFiltroBean.getFechaFin());
                    if (!listaInscritoPorTallerRep.isEmpty()) {
                        for (int k = 0; k < listaInscritoPorTallerRep.size(); k++) {
                            listaInscritoPorTallerRep.get(k).setTaller(listaCursoTallerRepBean.get(j).getTaller());
                            listaInscritoPorTallerRep.get(k).setMontoPagadoPorTaller(listaCursoTallerRepBean.get(j).getMontoPagadoPorTaller());
                        }
                    }
                    listaCursoTallerRepBean.get(j).setListaDetalleInscritos(listaInscritoPorTallerRep);
                }
            } else {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void convertirStrFechasCursoTallerPartes() {
        try {
            System.out.println("convertirStrFechasCursoTallerPartes");
//            String fechaInicio = null;
//            String fechaFin = null;
//            if (cursoTallerFiltroBean.getFechaInicio() != null) {
//                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                fechaInicio = sdf.format(cursoTallerFiltroBean.getFechaInicio());
//            }
//            if (cursoTallerFiltroBean.getFechaFin() != null) {
//                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                fechaFin = sdf.format(cursoTallerFiltroBean.getFechaFin());
//            }
            String uniNeg = usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            CursoTallerService cursoTallerService = BeanFactory.getCursoTallerService();
            listaIdProgramacion = new ArrayList<>();
            for (ProgramacionBean lista : listaProgramacionBean) {
                System.out.println("prog..." + lista.getIdProgramacion() + " " + lista.getDescripProgramacion());
                if (lista.getFlgSelect() != null) {
                    if (lista.getFlgSelect().equals(Boolean.TRUE)) {
                        listaIdProgramacion.add(lista.getIdProgramacion());
                    }
                }
            }
            Integer flg = 1;
            if (listaIdProgramacion.isEmpty()) {
                flg = 0;
            }
            listaCursoTallerRepBean = cursoTallerService.obtenerDetalleTallerRep(uniNeg, cursoTallerFiltroBean.getFechaInicio(), cursoTallerFiltroBean.getFechaFin(), listaIdProgramacion, flg);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void convertirStrFechasCursoTaller2(Integer flgTipo) {
        try {
//            String fechaInicio = null;
//            String fechaFin = null;
//            if (cursoTallerFiltroBean.getFechaInicio() != null) {
//                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                fechaInicio = sdf.format(cursoTallerFiltroBean.getFechaInicio());
//            }
//            if (cursoTallerFiltroBean.getFechaFin() != null) {
//                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                fechaFin = sdf.format(cursoTallerFiltroBean.getFechaFin());
//            }

            listaIdProgramacion = new ArrayList<>();
            for (ProgramacionBean lista : listaProgramacionBean) {
                System.out.println("prog gggggggg..." + lista.getIdProgramacion() + " flg " + lista.getFlgSelect());
                if (lista.getFlgSelect() != null) {
                    if (lista.getFlgSelect().equals(Boolean.TRUE)) {
                        System.out.println("Integer entró..." + lista.getIdProgramacion());
                        listaIdProgramacion.add(lista.getIdProgramacion());
                    }
                }
            }
            for (Integer list : listaIdProgramacion) {
                System.out.println("Integer prog..." + list);
            }

            imprimirPdfCursosTalleres(cursoTallerFiltroBean.getFechaInicio(), cursoTallerFiltroBean.getFechaFin(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), flgTipo);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void convertirStrFechasCursoTaller3(Object object, Integer flgTipo) {
        try {
            DetCursoTallerRepBean cursoTallerBean = (DetCursoTallerRepBean) object;
//            CursoTallerService cursoTallerService = BeanFactory.getCursoTallerService();
//            String fechaInicio = null;
//            String fechaFin = null;
//            if (cursoTallerFiltroBean.getFechaInicio() != null) {
//                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                fechaInicio = sdf.format(cursoTallerFiltroBean.getFechaInicio());
//            }
//            if (cursoTallerFiltroBean.getFechaFin() != null) {
//                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                fechaFin = sdf.format(cursoTallerFiltroBean.getFechaFin());
//            }
            System.out.println(">>>>" + cursoTallerBean.getIdProgramacion());
            System.out.println(">>>>>" + cursoTallerFiltroBean.getFechaInicio() + " // " + cursoTallerFiltroBean.getFechaFin());
            imprimirPdfCursosTalleresId(cursoTallerFiltroBean.getFechaInicio(), cursoTallerFiltroBean.getFechaFin(), usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), cursoTallerBean.getIdProgramacion(), flgTipo);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cambiarComodin() {
        comodin = false;
    }

    public void cambiarValAdmTodos() {
        try {
            System.out.println(">>>>" + valAdmTodos);
            if (valAdmTodos) {
                for (ProgramacionBean lista : listaProgramacionBean) {
                    lista.setFlgSelect(Boolean.TRUE);
                }
            } else if (!valAdmTodos) {
                for (ProgramacionBean lista : listaProgramacionBean) {
                    lista.setFlgSelect(Boolean.FALSE);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //Programacion
    public void rowSelectProgramacion(SelectEvent event) {
        try {
            ProgramacionBean programacion = (ProgramacionBean) event.getObject();
//            programacionBean = (ProgramacionBean) event.getObject();
            if (programacion.getOcupados() < programacion.getMax()) {
                ProgramacionService programacionService = BeanFactory.getProgramacionService();
                programacionBean = programacionService.obtenerPrograPorId(programacion);
            } else {
                new MensajePrime().addErrorMessage(MensajesBackEnd.getValueOfKey("mensajePrograNoDisp", null));
                programacionBean = new ProgramacionBean();
            }

//            System.out.println("aaaaaaaaaa");
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    ////////////reportes
    public void imprimirPdfCursosTalleres(Date fechaInicio, Date fechaFin, String uniNeg, Integer flgTipo) {
        ServletOutputStream out = null;
        try {
            CursoTallerService cursoTallerService = BeanFactory.getCursoTallerService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteCursoTaller.jasper");
            if (flgTipo.equals(0)) {
                archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteCursoTallerSinMonto.jasper");
            }
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<CursoTallerRepBean> listaCabeceraCursoTaller = new ArrayList<>();
            Integer flg = 1;
//            listaIdProgramacion = new ArrayList<>();
//            for (ProgramacionBean lista : listaProgramacionBean) {
//                if (lista.getFlgSelect() != null) {
//                    if (lista.getFlgSelect().equals(Boolean.TRUE)) {
//                        listaIdProgramacion.add(lista.getIdProgramacion());
//                        System.out.println("prog reporte..." + lista.getIdProgramacion());
//                    }
//                }
//            }
            for (Integer list : listaIdProgramacion) {
                System.out.println("Integer prog..." + list);
            }
            if (listaIdProgramacion.isEmpty()) {
                flg = 0;
            }
            System.out.println("flg" + flg);
            listaCabeceraCursoTaller = cursoTallerService.obtenerTalleresRep(uniNeg, fechaInicio, fechaFin, listaIdProgramacion, flg);
//
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String strFechaInicio = sdf.format(fechaInicio);
            String strFechaFin = sdf.format(fechaFin);
            if (!listaCabeceraCursoTaller.isEmpty()) {
                for (int i = 0; i < listaCabeceraCursoTaller.size(); i++) {
                    listaCabeceraCursoTaller.get(0).setTextoFiltro("Filtros: " + strFechaInicio + " al " + strFechaFin);
                    List<DetCursoTallerRepBean> listaDetTalleres = new ArrayList<>();
                    listaDetTalleres = cursoTallerService.obtenerDetalleTallerRep(uniNeg, fechaInicio, fechaFin, listaIdProgramacion, flg);
                    listaCabeceraCursoTaller.get(0).setListaDetalleTalleres(listaDetTalleres);
                    if (!listaDetTalleres.isEmpty()) {
                        for (int j = 0; j < listaCabeceraCursoTaller.get(0).getListaDetalleTalleres().getData().size(); j++) {
                            List<DetDetCursoTallerRepBean> listaInscritoPorTallerRep = new ArrayList<>();
                            listaInscritoPorTallerRep = cursoTallerService.obtenerInscritosTalleresRep(uniNeg, listaDetTalleres.get(j).getIdProgramacion(), fechaInicio, fechaFin);
                            if (!listaInscritoPorTallerRep.isEmpty()) {
                                for (int k = 0; k < listaInscritoPorTallerRep.size(); k++) {
                                    listaInscritoPorTallerRep.get(k).setTaller(listaDetTalleres.get(j).getTaller());
                                    listaInscritoPorTallerRep.get(k).setMontoPagadoPorTaller(listaDetTalleres.get(j).getMontoPagadoPorTaller());
                                }
                            }
                            listaDetTalleres.get(j).setListaDetalleInscritosPorCursoTaller(listaInscritoPorTallerRep);
                            listaCabeceraCursoTaller.get(0).setListaDetalleTalleres(listaDetTalleres);
                        }
                    }
                }
            } else {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCabeceraCursoTaller);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);

            response.setHeader("Content-Disposition", "inline; filename=Talleres" + new SimpleDateFormat(" dd-MM-yyyy").format(new Date()) + ".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");

            out = response.getOutputStream();
            out.write(bytes);
            out.flush();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        FacesContext.getCurrentInstance().responseComplete();
    }

    /* REPORTE POR ID */
    public void imprimirPdfCursosTalleresId(Date fechaInicio, Date fechaFin, String uniNeg, Integer idProgramacion, Integer flgTipo) {
        ServletOutputStream out = null;
        try {
            CursoTallerService cursoTallerService = BeanFactory.getCursoTallerService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteCursoTaller.jasper");
            if (flgTipo.equals(0)) {
                archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteCursoTallerSinMonto.jasper");
            }
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<CursoTallerRepBean> listaCabeceraCursoTaller = new ArrayList<>();
            listaCabeceraCursoTaller = cursoTallerService.obtenerTalleresRepId(uniNeg, fechaInicio, fechaFin, idProgramacion);
//
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String strFechaInicio = sdf.format(fechaInicio);
            String strFechaFin = sdf.format(fechaFin);
            if (!listaCabeceraCursoTaller.isEmpty()) {
                for (int i = 0; i < listaCabeceraCursoTaller.size(); i++) {
                    listaCabeceraCursoTaller.get(0).setTextoFiltro("Filtros: " + strFechaInicio + " al " + strFechaFin);
                    List<DetCursoTallerRepBean> listaDetTalleres = new ArrayList<>();
                    listaDetTalleres = cursoTallerService.obtenerDetalleTallerRepId(uniNeg, fechaInicio, fechaFin, idProgramacion);
                    listaCabeceraCursoTaller.get(0).setListaDetalleTalleres(listaDetTalleres);
                    if (!listaDetTalleres.isEmpty()) {
                        for (int j = 0; j < listaCabeceraCursoTaller.get(0).getListaDetalleTalleres().getData().size(); j++) {
                            List<DetDetCursoTallerRepBean> listaInscritoPorTallerRep = new ArrayList<>();
                            listaInscritoPorTallerRep = cursoTallerService.obtenerInscritosTalleresRep(uniNeg, listaDetTalleres.get(j).getIdProgramacion(), fechaInicio, fechaFin);
                            if (!listaInscritoPorTallerRep.isEmpty()) {
                                for (int k = 0; k < listaInscritoPorTallerRep.size(); k++) {
                                    listaInscritoPorTallerRep.get(k).setTaller(listaDetTalleres.get(j).getTaller());
                                    listaInscritoPorTallerRep.get(k).setMontoPagadoPorTaller(listaDetTalleres.get(j).getMontoPagadoPorTaller());
                                    listaInscritoPorTallerRep.get(k).setMontoPagadoPorTaller(listaDetTalleres.get(j).getMontoPagadoPorTaller());
                                }
                            }
                            listaDetTalleres.get(j).setListaDetalleInscritosPorCursoTaller(listaInscritoPorTallerRep);
                            listaCabeceraCursoTaller.get(0).setListaDetalleTalleres(listaDetTalleres);
                        }
                    }
                }
            } else {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCabeceraCursoTaller);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        FacesContext.getCurrentInstance().responseComplete();
    }

    //METODOS REPORTE TALLERES WEB
    public void convertirStrFechasCursoTallerWeb() {
        try {
            String fechaInicio = null;
            String fechaFin = null;
            if (cursoTallerFiltroBean.getFechaInicio() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                fechaInicio = sdf.format(cursoTallerFiltroBean.getFechaInicio());
            }
            if (cursoTallerFiltroBean.getFechaFin() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                fechaFin = sdf.format(cursoTallerFiltroBean.getFechaFin());
            }
            String uniNeg = usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            CursoTallerService cursoTallerService = BeanFactory.getCursoTallerService();
            listaIdProgramacion = new ArrayList<>();
            for (ProgramacionBean lista : listaProgramacionBean) {
                if (lista.getFlgSelect() != null) {
                    if (lista.getFlgSelect().equals(Boolean.TRUE)) {
                        listaIdProgramacion.add(lista.getIdProgramacion());
                    }
                }
            }
            Integer flg = 1;
            if (listaIdProgramacion.isEmpty()) {
                flg = 0;
            }
            listaCursoTallerRepBean = cursoTallerService.obtenerDetalleTallerWebRepDesc(uniNeg, fechaInicio, fechaFin, listaIdProgramacion, flg, descripcion);
            //AGREGAR FILTRO DE DESCRIPCION EN PROGRAMACIÓN
            ProgramacionService programacionService = BeanFactory.getProgramacionService();
            ProgramacionBean progra = new ProgramacionBean();
            progra.getUnidadNegocioBean().setUniNeg(usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            progra.setDescripProgramacion(descripcion);
            progra.setStatusProgramacion(statusProgramacion);
            listaProgramacionBean = programacionService.obtenerProgPorTipoActivosRef(progra);
            //END
            if (!listaCursoTallerRepBean.isEmpty()) {
                for (int j = 0; j < listaCursoTallerRepBean.size(); j++) {

                    List<DetDetCursoTallerRepBean> listaInscritoPorTallerRep = new ArrayList<>();
                    listaInscritoPorTallerRep = cursoTallerService.obtenerInscritosTalleresRepWeb(uniNeg, listaCursoTallerRepBean.get(j).getIdProgramacion(), fechaInicio, fechaFin);
                    if (!listaInscritoPorTallerRep.isEmpty()) {
                        for (int k = 0; k < listaInscritoPorTallerRep.size(); k++) {
                            listaInscritoPorTallerRep.get(k).setTaller(listaCursoTallerRepBean.get(j).getTaller());
                            listaInscritoPorTallerRep.get(k).setMontoPagadoPorTaller(listaCursoTallerRepBean.get(j).getMontoPagadoPorTaller());
                        }
                    }
                    listaCursoTallerRepBean.get(j).setListaDetalleInscritos(listaInscritoPorTallerRep);
                }
            } else {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void convertirStrFechasCursoTallerWeb2(Integer flgTipo) {
        try {
            String fechaInicio = null;
            String fechaFin = null;
            if (cursoTallerFiltroBean.getFechaInicio() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                fechaInicio = sdf.format(cursoTallerFiltroBean.getFechaInicio());
            }
            if (cursoTallerFiltroBean.getFechaFin() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                fechaFin = sdf.format(cursoTallerFiltroBean.getFechaFin());
            }

            listaIdProgramacion = new ArrayList<>();
            for (ProgramacionBean lista : listaProgramacionBean) {
                if (lista.getFlgSelect() != null) {
                    if (lista.getFlgSelect().equals(Boolean.TRUE)) {
                        listaIdProgramacion.add(lista.getIdProgramacion());
                    }
                }
            }

            imprimirPdfCursosTalleresWeb(fechaInicio, fechaFin, usuarioLogin.getPersonalBean().getUnidadNegocioBean().getUniNeg(), flgTipo);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void imprimirPdfCursosTalleresWeb(String fechaInicio, String fechaFin, String uniNeg, Integer flgTipo) {
        ServletOutputStream out = null;
        try {
            CursoTallerService cursoTallerService = BeanFactory.getCursoTallerService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteCursoTaller.jasper");
            if (flgTipo.equals(0)) {
                archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("/reportes/reporteCursoTallerSinMonto.jasper");
            }
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<CursoTallerRepBean> listaCabeceraCursoTaller = new ArrayList<>();
            Integer flg = 1;
            if (listaIdProgramacion.isEmpty()) {
                flg = 0;
            }
            listaCabeceraCursoTaller = cursoTallerService.obtenerTalleresRepWeb(uniNeg, fechaInicio, fechaFin, listaIdProgramacion, flg);
//
            if (!listaCabeceraCursoTaller.isEmpty()) {
                for (int i = 0; i < listaCabeceraCursoTaller.size(); i++) {
                    listaCabeceraCursoTaller.get(0).setTextoFiltro("Filtros: " + fechaInicio + " al " + fechaFin);
                    List<DetCursoTallerRepBean> listaDetTalleres = new ArrayList<>();
                    listaDetTalleres = cursoTallerService.obtenerDetalleTallerRepWeb(uniNeg, fechaInicio, fechaFin, listaIdProgramacion, flg);
                    listaCabeceraCursoTaller.get(0).setListaDetalleTalleres(listaDetTalleres);
                    if (!listaDetTalleres.isEmpty()) {
                        for (int j = 0; j < listaCabeceraCursoTaller.get(0).getListaDetalleTalleres().getData().size(); j++) {
                            List<DetDetCursoTallerRepBean> listaInscritoPorTallerRep = new ArrayList<>();
                            listaInscritoPorTallerRep = cursoTallerService.obtenerInscritosTalleresRepWeb(uniNeg, listaDetTalleres.get(j).getIdProgramacion(), fechaInicio, fechaFin);
                            if (!listaInscritoPorTallerRep.isEmpty()) {
                                for (int k = 0; k < listaInscritoPorTallerRep.size(); k++) {
                                    listaInscritoPorTallerRep.get(k).setTaller(listaDetTalleres.get(j).getTaller());
                                    listaInscritoPorTallerRep.get(k).setMontoPagadoPorTaller(listaDetTalleres.get(j).getMontoPagadoPorTaller());
                                }
                            }
                            listaDetTalleres.get(j).setListaDetalleInscritosPorCursoTaller(listaInscritoPorTallerRep);
                            listaCabeceraCursoTaller.get(0).setListaDetalleTalleres(listaDetTalleres);
                        }
                    }
                }
            } else {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCabeceraCursoTaller);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            out = response.getOutputStream();
            out.write(bytes);
            out.flush();

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception ettt) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), ettt);
            }
        }
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        FacesContext.getCurrentInstance().responseComplete();
    }

    //Cambio
    //Metodos Getter y Setter
    public CursoTallerBean getCursoTallerBean() {
        if (cursoTallerBean == null) {
            cursoTallerBean = new CursoTallerBean();
        }
        return cursoTallerBean;
    }

    public void setCursoTallerBean(CursoTallerBean cursoTallerBean) {
        this.cursoTallerBean = cursoTallerBean;
    }

    public List<CursoTallerBean> getListaCursoTallerBean() {
        if (listaCursoTallerBean == null) {
            listaCursoTallerBean = new ArrayList<>();
        }
        return listaCursoTallerBean;
    }

    public void setListaCursoTallerBean(List<CursoTallerBean> listaCursoTallerBean) {
        this.listaCursoTallerBean = listaCursoTallerBean;
    }

    public PersonaBean getPersonaBean() {
        if (personaBean == null) {
            personaBean = new PersonaBean();
        }
        return personaBean;
    }

    public void setPersonaBean(PersonaBean personaBean) {
        this.personaBean = personaBean;
    }

    public ProgramacionBean getProgramacionBean() {
        if (programacionBean == null) {
            programacionBean = new ProgramacionBean();
        }
        return programacionBean;
    }

    public void setProgramacionBean(ProgramacionBean programacionBean) {
        this.programacionBean = programacionBean;
    }

    public boolean isComodin() {
        return comodin;
    }

    public void setComodin(boolean comodin) {
        this.comodin = comodin;
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

    public List<PersonaBean> getListaFiltroBean() {
        if (listaFiltroBean == null) {
            listaFiltroBean = new ArrayList<>();
        }
        return listaFiltroBean;
    }

    public void setListaFiltroBean(List<PersonaBean> listaFiltroBean) {
        this.listaFiltroBean = listaFiltroBean;
    }

    public PersonaBean getPersonaFiltroBean() {
        if (personaFiltroBean == null) {
            personaFiltroBean = new PersonaBean();
        }
        return personaFiltroBean;
    }

    public void setPersonaFiltroBean(PersonaBean personaFiltroBean) {
        this.personaFiltroBean = personaFiltroBean;
    }

    public EstudianteBean getEstudianteFiltroBean() {
        if (estudianteFiltroBean == null) {
            estudianteFiltroBean = new EstudianteBean();
        }
        return estudianteFiltroBean;
    }

    public void setEstudianteFiltroBean(EstudianteBean estudianteFiltroBean) {
        this.estudianteFiltroBean = estudianteFiltroBean;
    }

    public List<EstudianteBean> getListaEstudianteBean() {
        if (listaEstudianteBean == null) {
            listaEstudianteBean = new ArrayList<>();
        }
        return listaEstudianteBean;
    }

    public void setListaEstudianteBean(List<EstudianteBean> listaEstudianteBean) {
        this.listaEstudianteBean = listaEstudianteBean;
    }

    public CursoTallerBean getCursoTallerFiltroBean() {
        if (cursoTallerFiltroBean == null) {
            cursoTallerFiltroBean = new CursoTallerBean();
        }
        return cursoTallerFiltroBean;
    }

    public void setCursoTallerFiltroBean(CursoTallerBean cursoTallerFiltroBean) {
        this.cursoTallerFiltroBean = cursoTallerFiltroBean;
    }

    public List<Integer> getListaAnioBean() {
        if (listaAnioBean == null) {
            listaAnioBean = new ArrayList<>();
        }
        return listaAnioBean;
    }

    public void setListaAnioBean(List<Integer> listaAnioBean) {
        this.listaAnioBean = listaAnioBean;
    }

    public List<DetCursoTallerRepBean> getListaCursoTallerRepBean() {
        if (listaCursoTallerRepBean == null) {
            listaCursoTallerRepBean = new ArrayList<>();
        }
        return listaCursoTallerRepBean;
    }

    public void setListaCursoTallerRepBean(List<DetCursoTallerRepBean> listaCursoTallerRepBean) {
        this.listaCursoTallerRepBean = listaCursoTallerRepBean;
    }

    public List<Integer> getListaIdProgramacion() {
        if (listaIdProgramacion == null) {
            listaIdProgramacion = new ArrayList<>();
        }
        return listaIdProgramacion;
    }

    public void setListaIdProgramacion(List<Integer> listaIdProgramacion) {
        this.listaIdProgramacion = listaIdProgramacion;
    }

    public Boolean getValAdmTodos() {
        return valAdmTodos;
    }

    public void setValAdmTodos(Boolean valAdmTodos) {
        this.valAdmTodos = valAdmTodos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getStatusProgramacion() {
        return statusProgramacion;
    }

    public void setStatusProgramacion(Integer statusProgramacion) {
        this.statusProgramacion = statusProgramacion;
    }

}

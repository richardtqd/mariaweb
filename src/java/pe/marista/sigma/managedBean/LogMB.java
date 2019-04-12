package pe.marista.sigma.managedBean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import pe.marista.sigma.bean.LogBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.LogService;
import pe.marista.sigma.service.UsuarioService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;


@ManagedBean
@ViewScoped
public class LogMB extends BaseMB implements Serializable {

    // Construtores
    public LogMB() {
        try {
            UsuarioService usuarioService = BeanFactory.getUsuarioService();
            listaUsuarioBean = usuarioService.obtenerTodos();
            listaUsuarioFiltro = new LinkedHashMap<String, Integer>();
            listaUsuarioFiltro.put(MensajesBackEnd.getValueOfKey("comboSeleccionarTodosLabel", null),
                    new Integer(MensajesBackEnd.getValueOfKey("comboSeleccionarTodosValue", null)));
//            for (UsuarioBean usuarioBean : listaUsuarioBean) {
//                listaUsuarioFiltro.put(usuarioBean.getNomReal() + " " + usuarioBean.getApeUsuario(), new Integer(usuarioBean.getCodUsuario()));
//            }

        } catch (Exception ex) {
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    // Variables
    private List<LogBean> listaLog;
//    private LogFiltroBean logFiltroBean;
    private List<UsuarioBean> listaUsuarioBean;
    private Map<String, Integer> listaUsuarioFiltro;
    private Map<String, String> listaTablaFiltro;
    private Map<String, Integer> listaTipOpe;
    private Integer scrollerPage = 1;
    private int numRows = 15;

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public Map<String, Integer> getListaTipOpe() {
        return listaTipOpe;
    }

    public void setListaTipOpe(Map<String, Integer> listaTipOpe) {
        this.listaTipOpe = listaTipOpe;
    }

    public Integer getScrollerPage() {
        return scrollerPage;
    }

    public void setScrollerPage(Integer scrollerPage) {
        this.scrollerPage = scrollerPage;
    }

    public Map<String, String> getListaTablaFiltro() {
        return listaTablaFiltro;
    }

    public void setListaTablaFiltro(Map<String, String> listaTablaFiltro) {
        this.listaTablaFiltro = listaTablaFiltro;
    }

    public List<UsuarioBean> getListaUsuarioBean() {
        return listaUsuarioBean;
    }

    public void setListaUsuarioBean(List<UsuarioBean> listaUsuarioBean) {
        this.listaUsuarioBean = listaUsuarioBean;
    }

    public Map<String, Integer> getListaUsuarioFiltro() {
        return listaUsuarioFiltro;
    }

    public void setListaUsuarioFiltro(Map<String, Integer> listaUsuarioFiltro) {
        this.listaUsuarioFiltro = listaUsuarioFiltro;
    }

    public List<LogBean> getListaLog() {
        return listaLog;
    }

    public void setListaLog(List<LogBean> listaLog) {
        this.listaLog = listaLog;
    }

    // Metodos
//    public void obtenerPorFiltro() {
//        try {
//
//            if ((logFiltroBean.getFechaInicio() != null && logFiltroBean.getFechaFin() != null)
//                    && (!logFiltroBean.getFechaInicio().before(logFiltroBean.getFechaFin()))
//                    && logFiltroBean.getFechaInicio().getDate() != logFiltroBean.getFechaFin().getDate()) {
//                new FAPUtils().requestColocarObjeto("operacionIncorrecta", true);
//            } else {
//                try {
//                    LogService logService = BeanFactory.getLogService();
//                    if (logFiltroBean.getFechaInicio() != null && logFiltroBean.getFechaFin() != null) {
//                        Timestamp t = new Timestamp(logFiltroBean.getFechaInicio().getTime());
//                        Timestamp u = new Timestamp(logFiltroBean.getFechaFin().getTime());
//                        t.setHours(0); t.setMinutes(0); t.setSeconds(0);
//                        u.setHours(23); u.setMinutes(59); u.setSeconds(59);
//                        logFiltroBean.setFechaInicio(t);
//                        logFiltroBean.setFechaFin(u);
//                        System.out.println("Fecha Inicio = "+t);
//                        System.out.println("Fecha Final = "+u);
//                    }
//                    listaLog = logService.obtenerPorFiltro(logFiltroBean);
//                } catch (Exception ex) {
//                    new MensajePrime().addErrorGeneralMessage();
//                    GLTLog.writeError(this.getClass(), ex);
//                }
//            }
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }

    public void obtenerTodos() {
        try {
            LogService logService = BeanFactory.getLogService();
            listaLog = logService.obtenerTodos();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }
    
//    public void imprimir() {
//        ServletOutputStream out = null;
//        try {
//            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
//                    getResponse();
//            
//            String archivoJasper = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().
//                    getRequest()).getServletContext().getRealPath("/reportes/logOperaciones.jasper");
//            String archivoLogo = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().
//                    getRequest()).getServletContext().getRealPath(MaristaConstantes.RUTA_LOGO);
//                    
//            File file = new File(archivoJasper);
//
//            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
//            
//            Map<String, Object> params = new HashMap<String, Object>();
//            params.put("parTituloSistema", MensajesBackEnd.getValueOfKey("appTitulo", null));
//            params.put("parLogo", archivoLogo);
//            StringBuffer filtros = new StringBuffer("Filtros");
//            
//            filtros.append("\nFecha Inicio: ").append(new SimpleDateFormat(
//                    MaristaConstantes.FORMATO_FECHA).format(logFiltroBean.getFechaInicio()));
//            filtros.append("\nFecha Final: ").append(new SimpleDateFormat(
//                    MaristaConstantes.FORMATO_FECHA).format(logFiltroBean.getFechaFin()));
//            params.put("parFiltros", filtros);
//            
//            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaLog);
//            byte[] bytes = JasperRunManager.runReportToPdf(reporte, params, jrbc);
//
//            response.reset();
//            response.setContentType("application/pdf");
//            response.setContentLength(bytes.length);
//            out = response.getOutputStream();
//            out.write(bytes);
//            out.flush();
//
//        } catch (Exception err) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), err);
//        } finally {
//            try {
//                if (out != null) {
//                    out.close();
//                }
//            } catch (Exception eww) {
//                new MensajePrime().addErrorGeneralMessage();
//                GLTLog.writeError(this.getClass(), eww);
//            }
//        }
//        // Inform JSF that it doesn't need to handle response.
//        // This is very important, otherwise you will get the following exception in the logs:
//        // java.lang.IllegalStateException: Cannot forward after response has been committed.
//        FacesContext.getCurrentInstance().responseComplete();
//    }

}

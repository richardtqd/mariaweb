package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.ConceptoBean;
import pe.marista.sigma.bean.Contenedor;
import pe.marista.sigma.bean.ProcesoBancoBean;
import pe.marista.sigma.bean.ProcesoEnvioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.bean.RecEnvBean;
import pe.marista.sigma.bean.TipoConceptoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.IngresoCajaPensionesRepBean;
import pe.marista.sigma.service.ConceptoService;
import pe.marista.sigma.service.ProcesoBancoService;
import pe.marista.sigma.service.ProcesoFilesService;
import pe.marista.sigma.service.RecEnvService;
import pe.marista.sigma.service.TipoConceptoService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

public class RecEnvMB implements Serializable {

    @PostConstruct
    public void RecEnvMB() {
        try {
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            TipoConceptoService tipoConceptoService = BeanFactory.getTipoConceptoService();
            getListaTipoConceptoBean();
            listaTipoConceptoBean = tipoConceptoService.obtenerTipoConceptoCurso();
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            Integer idProcesoBanco = procesoBancoService.obtenerMaxIdProcesoBanco(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            String ruc = obtenerRucBanco(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            GregorianCalendar fechaActual = new GregorianCalendar();
            getProcesoEnvioBean().setFechaIni(fechaActual.getTime());
            getProcesoEnvioBean().setFechaFin(fechaActual.getTime());
            procesoEnvioBean.setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            procesoEnvioBean.setIdProcesoBanco(idProcesoBanco);
            procesoEnvioBean.setRuc(ruc);
            setFechaInicio(fechaActual.getTime());
            setFechaFin(fechaActual.getTime());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    private RecEnvBean recEnvBean;
    private List<RecEnvBean> listaRecEnvBean;
    private UsuarioBean usuarioLoginBean;
    private StreamedContent content;
    private ProcesoEnvioBean procesoEnvioBean;

    //AYUDA
    private List<ConceptoBean> listaconceptoBean;
    private List<TipoConceptoBean> listaTipoConceptoBean;
    private List<IngresoCajaPensionesRepBean> listaIngresoCajaPensionesRepBean;

    //CONTENEDOR
    private List<Contenedor> listaBancoSigma;

    //NUMERO DE FILAS EN ARCHIVO
    private Integer filas = 0;
    private Integer filasCab = 0;
    private Integer filasInt = 0;

    //excel
    private Date fechaInicio;
    private Date fechaFin;

    public RecEnvBean getRecEnvBean() {
        if (recEnvBean == null) {
            recEnvBean = new RecEnvBean();
        }
        return recEnvBean;
    }

    public void setRecEnvBean(RecEnvBean recEnvBean) {
        this.recEnvBean = recEnvBean;
    }

    public List<RecEnvBean> getListaRecEnvBean() {
        if (listaRecEnvBean == null) {
            listaRecEnvBean = new ArrayList<>();
        }
        return listaRecEnvBean;
    }

    public void setListaRecEnvBean(List<RecEnvBean> listaRecEnvBean) {
        this.listaRecEnvBean = listaRecEnvBean;
    }

    public String obtenerRucBanco(String uniNeg) {
        String ruc = "";
        try {
            switch (uniNeg) {
                case MaristaConstantes.UNI_NEG_CHAMPS:
                    ruc = "20100043140";
                    break;
                case MaristaConstantes.UNI_NEG_SANJOC:
                    ruc = "20100053455";
                    break;
                case MaristaConstantes.UNI_NEG_SANLUI:
                    ruc = "";
                    break;
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return ruc;
    }

    public void buscarProcesoRecEnv() {
        try {
            RecEnvService recEnvService = BeanFactory.getRecEnvService();
            listaRecEnvBean = recEnvService.buscarProcesoRecEnv(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), recEnvBean.getFechaProceso());
            if (listaRecEnvBean.isEmpty()) {
                System.out.println("vacio");
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void filtrarOperaciones() {
        try {
            Integer res = 0;
            if (procesoEnvioBean.getFechaIni() != null) {
                procesoEnvioBean.setFechaIni(procesoEnvioBean.getFechaIni());
                res = 1;
            }
            if (procesoEnvioBean.getFechaFin() != null) {
                procesoEnvioBean.setFechaFin(procesoEnvioBean.getFechaFin());
                res = 1;
            }
            if (procesoEnvioBean.getIdTipoConcepto() != null
                    && !procesoEnvioBean.getIdTipoConcepto().equals(0)) {
                procesoEnvioBean.setIdTipoConcepto(procesoEnvioBean.getIdTipoConcepto());
                res = 1;
            }
            if (procesoEnvioBean.getIdConcepto() != null
                    && !procesoEnvioBean.getIdConcepto().equals(0)) {
                procesoEnvioBean.setIdConcepto(procesoEnvioBean.getIdConcepto());
                res = 1;
            }
            if (!procesoEnvioBean.getNombres().equals("")
                    && procesoEnvioBean.getNombres() != null) {
                procesoEnvioBean.setNombres(procesoEnvioBean.getNombres());
                res = 1;
            }
            if (!procesoEnvioBean.getCodigo().equals("")
                    && procesoEnvioBean.getCodigo() != null) {
                procesoEnvioBean.setCodigo(procesoEnvioBean.getCodigo());
                res = 1;
            }
            if (!procesoEnvioBean.getIdEstudiante().equals("")
                    && procesoEnvioBean.getIdEstudiante() != null) {
                procesoEnvioBean.setIdEstudiante(procesoEnvioBean.getIdEstudiante());
                res = 1;
            }
            if (res.equals(1)) {
                RecEnvService recEnvService = BeanFactory.getRecEnvService();
                listaBancoSigma = recEnvService.obtenerFiltroOperaciones(procesoEnvioBean);
                if (listaBancoSigma.isEmpty()) {
                    System.out.println(">>>> nada");
                } else {
                    ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
                    filas = procesoFilesService.obtenerNumFiles(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 1, MaristaConstantes.FileDetalle, procesoEnvioBean.getRuc());
                    filasCab = procesoFilesService.obtenerNumFiles(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 1, MaristaConstantes.FileCabecera, procesoEnvioBean.getRuc());
                    filasInt = procesoFilesService.obtenerNumFiles(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), 1, MaristaConstantes.FileIntermedio, procesoEnvioBean.getRuc());
                    System.out.println(">>>>" + listaBancoSigma.size() + " // " + filas + " // " + filasInt + " // " + filasCab);
                }
            } else {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarFiltroRecEnv() {
        try {
            procesoEnvioBean = new ProcesoEnvioBean();
            GregorianCalendar fechaActual = new GregorianCalendar();
            getProcesoEnvioBean().setFechaIni(fechaActual.getTime());
            getProcesoEnvioBean().setFechaFin(fechaActual.getTime());
            getProcesoEnvioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            Integer idProcesoBanco = procesoBancoService.obtenerMaxIdProcesoBanco(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            String ruc = obtenerRucBanco(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getProcesoEnvioBean().setIdProcesoBanco(idProcesoBanco);
            getProcesoEnvioBean().setRuc(ruc);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarFiltroPensionesEnCaja() {
        try {
            listaIngresoCajaPensionesRepBean = new ArrayList<>();
            GregorianCalendar fechaActual = new GregorianCalendar();
            fechaInicio = (fechaActual.getTime());
            fechaFin = (fechaActual.getTime());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerFile() {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            File archivo = new File("Envio.txt");
            fichero = new FileWriter(archivo);
            pw = new PrintWriter(archivo);
            System.out.println(listaRecEnvBean.size());
            if (!listaRecEnvBean.isEmpty()) {
                for (RecEnvBean rec : listaRecEnvBean) {
                    pw.print(rec.getCodigoCliente() + "  ");
                    pw.print(rec.getNombreAlumno() + "  ");
                    pw.print(rec.getCodigoCuota());
                    pw.print(rec.getImporte());
                    pw.print(rec.getMora());
                    pw.print(rec.getFechaPago());
                    pw.print(rec.getFiller());
                    pw.print(rec.getFormaPago());
                    pw.print(rec.getFiller());
                    pw.print(rec.getOficinaPago());
                    pw.print(rec.getFiller());
                    pw.print(rec.getNroTerminal());
                    pw.print(rec.getFiller());
                    pw.print(rec.getOficinaAbono());
                    pw.print(rec.getFiller());
                    pw.print(rec.getCtaAbono());
                    pw.print(rec.getFiller());
                    pw.print(rec.getMoneda());
                    pw.print(rec.getFiller());
                    pw.print(rec.getFechaVenc());
                    pw.print(rec.getDato());
                    pw.print(rec.getFiller().concat("         "));
                    pw.print(rec.getTipoServicio());
                    pw.println();
                }
                pw.close();
            }
            content = new DefaultStreamedContent(new FileInputStream(archivo), "txt", "SIGMA");
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception err) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), err);
            }
        }
    }

    public void descargaSigmaBanco() {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            File archivo = new File("Envio.txt");
            fichero = new FileWriter(archivo);
            pw = new PrintWriter(archivo);
            if (!listaBancoSigma.isEmpty()) {
                for (int i = 0; i < listaBancoSigma.size(); i++) {
                    for (int j = 0; j < listaBancoSigma.get(i).getListaContenedor().size(); j++) {
                        if (j < (listaBancoSigma.get(i).getListaContenedor().size() - 1)) {
                            pw.print(listaBancoSigma.get(i).getListaContenedor().get(j).getValor());
                        } else {
                            if (j == (listaBancoSigma.get(i).getListaContenedor().size() - 1)) {
                                pw.print(listaBancoSigma.get(i).getListaContenedor().get(j).getValor());
                                pw.println();
                            }
                        }
                    }
                }
            }
            pw.close();
            String nombre = obtenerNombreArchivoBcoSigma(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoEnvioBean.getFechaFin());
            content = new DefaultStreamedContent(new FileInputStream(archivo), ".txt", nombre);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception err) {
                new MensajePrime().addErrorGeneralMessage();
                GLTLog.writeError(this.getClass(), err);
            }
        }
    }

    public void obtenerFiltroPensionesEnCajaPorFecha() {
        try {

            RecEnvService recEnvService = BeanFactory.getRecEnvService();
            listaIngresoCajaPensionesRepBean = new ArrayList<>();
            listaIngresoCajaPensionesRepBean = recEnvService.buscarPensionesEnCajaPorFecha(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), fechaInicio, fechaFin);
            if (listaIngresoCajaPensionesRepBean.isEmpty()) {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");

            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorTipo() {
        try {
            ConceptoService conceptoService = BeanFactory.getConceptoService();
            getListaconceptoBean();
            TipoConceptoBean tipoConceptoBean = new TipoConceptoBean();
            tipoConceptoBean.setIdTipoConcepto(procesoEnvioBean.getIdTipoConcepto());
            listaconceptoBean = conceptoService.obtenerPorTipo(tipoConceptoBean);
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public String obtenerNombreArchivo(String uniNeg, Date fecha) {
        String nombre = "";
        try {
            String fechaProceso = "";
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            ProcesoBancoBean procesoBancoBean = new ProcesoBancoBean();
            procesoBancoBean.getUnidadNegocioBean().setUniNeg(uniNeg);
            procesoBancoBean.setFecha(fecha);
            if (uniNeg.equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                fechaProceso = procesoBancoService.obtenerFechaProceso(procesoBancoBean);
                nombre = "MP".concat(fechaProceso).concat("S.txt");
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return nombre;
    }

    public String obtenerNombreArchivoBcoSigma(String uniNeg, Date fecha) {
        String nombre = "";
        try {
            String fechaProceso = "";
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            ProcesoBancoBean procesoBancoBean = new ProcesoBancoBean();
            procesoBancoBean.getUnidadNegocioBean().setUniNeg(uniNeg);
            procesoBancoBean.setFecha(fecha);
            if (uniNeg.equals(MaristaConstantes.UNI_NEG_CHAMPS)) {
                fechaProceso = procesoBancoService.obtenerFechaProcesoBcoSigma(procesoBancoBean);
                nombre = "MP".concat(fechaProceso).concat("S.txt");
            }
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
        return nombre;
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

    public StreamedContent getContent() {
        return content;
    }

    public void setContent(StreamedContent content) {
        this.content = content;
    }

    public ProcesoEnvioBean getProcesoEnvioBean() {
        if (procesoEnvioBean == null) {
            procesoEnvioBean = new ProcesoEnvioBean();
        }
        return procesoEnvioBean;
    }

    public void setProcesoEnvioBean(ProcesoEnvioBean procesoEnvioBean) {
        this.procesoEnvioBean = procesoEnvioBean;
    }

    public List<ConceptoBean> getListaconceptoBean() {
        if (listaconceptoBean == null) {
            listaconceptoBean = new ArrayList<>();
        }
        return listaconceptoBean;
    }

    public void setListaconceptoBean(List<ConceptoBean> listaconceptoBean) {
        this.listaconceptoBean = listaconceptoBean;
    }

    public List<TipoConceptoBean> getListaTipoConceptoBean() {
        if (listaTipoConceptoBean == null) {
            listaTipoConceptoBean = new ArrayList<>();
        }
        return listaTipoConceptoBean;
    }

    public void setListaTipoConceptoBean(List<TipoConceptoBean> listaTipoConceptoBean) {
        this.listaTipoConceptoBean = listaTipoConceptoBean;
    }

    public List<Contenedor> getListaBancoSigma() {
        if (listaBancoSigma == null) {
            listaBancoSigma = new ArrayList<>();
        }
        return listaBancoSigma;
    }

    public void setListaBancoSigma(List<Contenedor> listaBancoSigma) {
        this.listaBancoSigma = listaBancoSigma;
    }

    public Integer getFilas() {
        return filas;
    }

    public void setFilas(Integer filas) {
        this.filas = filas;
    }

    public Integer getFilasCab() {
        return filasCab;
    }

    public void setFilasCab(Integer filasCab) {
        this.filasCab = filasCab;
    }

    public Integer getFilasInt() {
        return filasInt;
    }

    public void setFilasInt(Integer filasInt) {
        this.filasInt = filasInt;
    }

    public List<IngresoCajaPensionesRepBean> getListaIngresoCajaPensionesRepBean() {
        if (listaIngresoCajaPensionesRepBean == null) {
            listaIngresoCajaPensionesRepBean = new ArrayList<>();
        }
        return listaIngresoCajaPensionesRepBean;
    }

    public void setListaIngresoCajaPensionesRepBean(List<IngresoCajaPensionesRepBean> listaIngresoCajaPensionesRepBean) {
        this.listaIngresoCajaPensionesRepBean = listaIngresoCajaPensionesRepBean;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

}

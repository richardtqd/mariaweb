/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.EnvioBcoBean;
import pe.marista.sigma.bean.MesBean;
import pe.marista.sigma.bean.ProcesoBancoBean;
import pe.marista.sigma.bean.ProcesoEnvioBean;
import pe.marista.sigma.bean.ProcesoFilesBean;
import pe.marista.sigma.bean.TipoEnvioUniNegBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.EnvioBcoService;
import pe.marista.sigma.service.ProcesoBancoService;
import pe.marista.sigma.service.ProcesoFilesService;
import pe.marista.sigma.service.TipoEnvioUniNegService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

/**
 *
 * @author MS-001
 */
public class EnvioBcoMB extends BaseMB implements Serializable {

    /**
     * Creates a new instance of EnvioBcoMB
     */
    @PostConstruct
    public void EnvioBcoMB() {

        try {
            usuarioSessionBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getListaEnvioBcoBean();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    private UsuarioBean usuarioSessionBean;
    private ProcesoEnvioBean procesoEnvioBean;
    private ProcesoBancoBean procesoBancoBean;
    private List<ProcesoBancoBean> listaProcesoBancoBean;
    private List<ProcesoFilesBean> listaProcesoFilesDetalleBean;
    private List<EnvioBcoBean> listaEnvioBcoBean;
    private List<Object> listaData;
    private Map<String, String> map; // +getter
    private StreamedContent content;
    private List<TipoEnvioUniNegBean> listaTipoEnvioUniNegBean;
    private TipoEnvioUniNegBean tipoEnvioUniNegBean;

    //rangos de fecha
    private String renderedFecha;
    private Date fechaInicio;
    private Date fechaFin;
    private Map<String, Integer> listaMeses;
    //private String tipoEnvio;
    private Integer periodoInicio;
    private Integer periodoFin;
    private List<MesBean> listaMesAll;
    private Integer[] selectedMesesNoConsiderar = new Integer[12];

    public void obtenerFiltroProceso() {
        try {
            procesoBancoBean.setUnidadNegocioBean(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean());
            procesoBancoBean.setFlgProceso(1);
            Integer res = 0;
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            if (procesoBancoBean.getFlgProceso() != null) {
                procesoBancoBean.setFlgProceso(procesoBancoBean.getFlgProceso());
                res = 1;
            }
            if (procesoBancoBean.getNombre() != null) {
                procesoBancoBean.setNombre(procesoBancoBean.getNombre());
                res = 1;
            }
            if (procesoBancoBean.getEntidadBean().getRuc() != null) {
                procesoBancoBean.getEntidadBean().setRuc(procesoBancoBean.getEntidadBean().getRuc());
                res = 1;
            }
            if (procesoBancoBean.getAnio() != null) {
                procesoBancoBean.setAnio(procesoBancoBean.getAnio());
                res = 1;
            }
            if (procesoBancoBean.getMes() != null) {
                procesoBancoBean.setMes(procesoBancoBean.getMes());
                res = 1;
            }
            if (procesoBancoBean.getDia() != null) {
                procesoBancoBean.setDia(procesoBancoBean.getDia());
                res = 1;
            }
            if (procesoBancoBean.getFechaInicio() != null) {
                procesoBancoBean.setFechaInicio(procesoBancoBean.getFechaInicio());
                res = 1;
            }
            if (procesoBancoBean.getFechaFin() != null) {
                procesoBancoBean.setFechaFin(procesoBancoBean.getFechaFin());
                res = 1;
            }
            if (res == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaProcesoBancoBean = new ArrayList<>();
            } else if (res == 1) {
                listaProcesoBancoBean = procesoBancoService.filtrarProceso(procesoBancoBean);
                if (listaProcesoBancoBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaProcesoBancoBean = new ArrayList<>();
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarFiltroBanco() {
        try {
            procesoBancoBean = new ProcesoBancoBean();
            listaProcesoBancoBean = new ArrayList<>();

            getProcesoBancoBean().getUnidadNegocioBean().setUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerProcesoEnvioBcoPorId(Object object) {
        try {
            procesoBancoBean = (ProcesoBancoBean) object;
            ProcesoBancoService procesoBancoService = BeanFactory.getProcesoBancoService();
            procesoBancoService.obtenerPorId(procesoBancoBean.getIdProcesoBanco());
            getProcesoEnvioBean().setIdProcesoBanco(procesoBancoBean.getIdProcesoBanco());
            System.out.println(">>>>" + procesoBancoBean.getIdProcesoBanco());
            mostrarFilesEnvioPorId(procesoBancoBean.getIdProcesoBanco());
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void mostrarFilesEnvio() {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            File archivo = new File("Envio.txt");
            fichero = new FileWriter(archivo);
            pw = new PrintWriter(archivo);
            if (!listaEnvioBcoBean.isEmpty()) {
                EnvioBcoService envioBcoService = BeanFactory.getEnvioBcoService();
                //cabecera
                ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
                List<ProcesoFilesBean> listCabecera = procesoFilesService.obtenerFileProcesoVer2(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), null, 2, MaristaConstantes.FileCabecera);
                if (listCabecera != null) {
                    if (listCabecera.size() > 0) {

                        String cabecera = "";
                        cabecera = envioBcoService.obtenerCabeceraRegPorId(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getIdProcesoBanco());
                        pw.print(cabecera);
                        pw.println();
                    }
                }
                //intermedio
                List<ProcesoFilesBean> listIntermedio = procesoFilesService.obtenerFileProcesoVer2(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), null, 2, MaristaConstantes.FileIntermedio);
                if (listIntermedio != null) {
                    if (listIntermedio.size() > 0) {
                        List<EnvioBcoBean> intermedio = new ArrayList<>();
                        intermedio = envioBcoService.obtenerListaIntermedioRegPorId(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getIdProcesoBanco());
                        if (!intermedio.isEmpty()) {
                            for (EnvioBcoBean lista : intermedio) {
                                pw.print(lista.getConcatenado());
                                pw.println();
                            }
                        }
                    }
                }

                System.out.println(">>> ENTRO DET");
                for (int i = 0; i < listaEnvioBcoBean.size(); i++) {
                    pw.print(listaEnvioBcoBean.get(i).getConcatenado());
                    pw.println();
                }
                pw.close();
            }
            if (procesoBancoBean.getNombre() == null) {
                procesoBancoBean.setNombre("archivo");
            }
            content = new DefaultStreamedContent(new FileInputStream(archivo), "txt", procesoBancoBean.getNombre() + ".txt");
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

    public void mostrarFilesEnvioPorId(Integer id) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            File archivo = new File("Envio.txt");
            fichero = new FileWriter(archivo);
            pw = new PrintWriter(archivo);
            EnvioBcoService envioBcoService = BeanFactory.getEnvioBcoService();
            //cabecera
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            List<ProcesoFilesBean> listCabecera = procesoFilesService.obtenerFileProcesoVer2(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), null, 2, MaristaConstantes.FileCabecera);
            if (listCabecera != null) {
                if (listCabecera.size() > 0) {

                    String cabecera = "";
                    cabecera = envioBcoService.obtenerCabeceraRegPorId(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getIdProcesoBanco());
                    pw.print(cabecera);
                    pw.println();
                }
            }
            //intermedio
            List<ProcesoFilesBean> listIntermedio = procesoFilesService.obtenerFileProcesoVer2(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), null, 2, MaristaConstantes.FileIntermedio);
            if (listIntermedio != null) {
                if (listIntermedio.size() > 0) {
                    List<EnvioBcoBean> intermedio = new ArrayList<>();
                    intermedio = envioBcoService.obtenerListaIntermedioRegPorId(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), procesoBancoBean.getIdProcesoBanco());
                    if (!intermedio.isEmpty()) {
                        for (EnvioBcoBean lista : intermedio) {
                            pw.print(lista.getConcatenado());
                            pw.println();
                        }
                    }
                }
            }
            listaEnvioBcoBean = new ArrayList<>();
            listaEnvioBcoBean = envioBcoService.obtenerListaEnvioBcoPorId(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), id);
            if (!listaEnvioBcoBean.isEmpty()) {
                System.out.println(">>> ENTRO DET");
                for (int i = 0; i < listaEnvioBcoBean.size(); i++) {
                    pw.print(listaEnvioBcoBean.get(i).getConcatenado());
                    pw.println();
                }
                pw.close();
            }
            if (procesoBancoBean.getNombre() == null) {
                procesoBancoBean.setNombre("archivo");
            }

            content = new DefaultStreamedContent(new FileInputStream(archivo), "txt", procesoBancoBean.getNombre() + ".txt");
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

    public void obtenerEnvioBco() {
        try {
            if (this.tipoEnvioUniNegBean.getFlgFechaReqStr().equals("false")) {
                this.fechaFin = null;
                this.fechaInicio = null;
            }

            List<Integer> listAnios = new ArrayList<>();
            for (int i = periodoInicio; i <= periodoFin; i++) {
                listAnios.add(i);
            }
            List<Integer> listMeses = new ArrayList<>();
            for (int i = 0; i < selectedMesesNoConsiderar.length; i++) {
                listMeses.add(selectedMesesNoConsiderar[i]);
            }

            EnvioBcoService envioBcoService = BeanFactory.getEnvioBcoService();

            Integer count = 0;

            count = envioBcoService.countCURSOR_insertarLogEnvioBco(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                    fechaInicio, fechaFin, this.tipoEnvioUniNegBean.getIdTipoStatusCtaCte(), listAnios, listMeses);

            if (count > 0) {
                envioBcoService.CURSOR_insertarLogEnvioBco(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                        fechaInicio, fechaFin, this.tipoEnvioUniNegBean, listAnios, listMeses, usuarioSessionBean.getUsuario(), getProcesoBancoBean());
                Integer idProcesoBcoInst = procesoBancoBean.getIdProcesoBanco();
                listaEnvioBcoBean = envioBcoService.obtenerListaEnvioBcoPorId(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), idProcesoBcoInst);
                System.out.println("listaEnvioBcoBean:" + listaEnvioBcoBean.size());

            } else {
                new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
            }

        } catch (Exception e) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), e);
        }
    }

    public void cargarDatos() {
        try {
            ProcesoFilesService procesoFilesService = BeanFactory.getProcesoFilesService();
            listaProcesoFilesDetalleBean = procesoFilesService.obtenerFileProcesoVer2(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), null, 2, MaristaConstantes.FileDetalle);
            System.out.println("size listaProcesoFilesDetalleBean:" + listaProcesoFilesDetalleBean.size());
            listaData = new ArrayList<>();
            String data = "";
            String valor = "";
            map = new TreeMap<String, String>();
            data = "";
            valor = "";
            for (int i = 0; i < listaProcesoFilesDetalleBean.size(); i++) {
                Integer c = i + 1;
                data = "data" + c.toString();
                System.out.println("data " + i + ":" + data);
                valor = listaProcesoFilesDetalleBean.get(i).getNombre();
                map.put(data, valor);
            }

            getTipoEnvioUniNegBean();

            ///////////////////////////
            listaMesesConsiderar();
            obtenerAnios();

            Date fecha = new Date();
            DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd");
            String date = fechaHora.format(fecha.getTime());
            Date date1 = fechaHora.parse(date);

            fechaInicio = date1;
            fechaFin = date1;

            TipoEnvioUniNegService tipoEnvioUniNegService = BeanFactory.getTipoEnvioUniNegService();
            listaTipoEnvioUniNegBean = tipoEnvioUniNegService.obtenerTipoEnvioUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

            if (!listaTipoEnvioUniNegBean.isEmpty()) {
                if (listaTipoEnvioUniNegBean.size() > 1) {
                    for (TipoEnvioUniNegBean list : listaTipoEnvioUniNegBean) {
                        if (list.getFlgDefault() != null) {
                            if (list.getFlgDefault().equals(Boolean.TRUE)) {
                                System.out.println("list..." + list.getNombreTipoEnvio());
                                System.out.println("list2..." + list.getFlgFechaReqStr());
                                this.tipoEnvioUniNegBean = list;
                                obtenerRenderedFecha(list);
                                break;
                            }
                        }
                    }
                } else {
                    this.tipoEnvioUniNegBean = listaTipoEnvioUniNegBean.get(0);
                    obtenerRenderedFecha(listaTipoEnvioUniNegBean.get(0));
                }
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void listaMesesConsiderar() {
        getListaMesAll();
        listaMesAll = new ArrayList<>();
//        listaMesAll.add(new MesBean(1, MaristaConstantes.ENERO));
        listaMesAll.add(new MesBean(2, MaristaConstantes.MES_FEBRERO));
        listaMesAll.add(new MesBean(3, MaristaConstantes.MARZO));
        listaMesAll.add(new MesBean(4, MaristaConstantes.ABRIL));
        listaMesAll.add(new MesBean(5, MaristaConstantes.MAYO));
        listaMesAll.add(new MesBean(6, MaristaConstantes.JUNIO));
        listaMesAll.add(new MesBean(7, MaristaConstantes.JULIO));
        listaMesAll.add(new MesBean(8, MaristaConstantes.AGOSTO));
        listaMesAll.add(new MesBean(9, MaristaConstantes.SETIEMBRE));
        listaMesAll.add(new MesBean(10, MaristaConstantes.OCTUBRE));
        listaMesAll.add(new MesBean(11, MaristaConstantes.NOVIEMBRE));
        listaMesAll.add(new MesBean(12, MaristaConstantes.DICIEMBRE));
        obtenerMeses();
    }

    public void obtenerMeses() {
        selectedMesesNoConsiderar = new Integer[13];
        for (int i = 2; i <= 12; i++) {
            System.out.print("xxx " + i);
            selectedMesesNoConsiderar[i] = i;
        }
    }

    public void obtenerAnios() {
        periodoInicio = 2015;
        DateFormat fecha = new SimpleDateFormat("yyyy");
        String year = fecha.format(new Date());
        periodoFin = new Integer(year);
        for (int i = periodoInicio; i <= periodoFin + 1; i++) {
            System.out.print("xxx " + i);
        }
        periodoInicio = new Integer(year);
    }

    public void obtenerRenderedFecha(TipoEnvioUniNegBean tipo) {
        try {
            System.out.println("flg:" + tipo.getFlgFechaReq());
            this.renderedFecha = tipo.getFlgFechaReqStr();
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void obtenerRenderedFecha2() {
        try {
            System.out.println("flg:" + this.getTipoEnvioUniNegBean().getIdTipoEnvioUniNeg());
            if (!listaTipoEnvioUniNegBean.isEmpty()) {
                if (listaTipoEnvioUniNegBean.size() > 0) {
                    TipoEnvioUniNegService tipoEnvioUniNegService = BeanFactory.getTipoEnvioUniNegService();
                    System.out.println("list.getIdTipoEnvioUniNeg():" + tipoEnvioUniNegBean.getIdTipoEnvioUniNeg());
                    this.tipoEnvioUniNegBean = tipoEnvioUniNegService.obtenerTipoEnvioPorId(this.tipoEnvioUniNegBean.getIdTipoEnvioUniNeg(), usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    System.out.println("list.getFlgFechaReq():" + tipoEnvioUniNegBean.getFlgFechaReq());
                    System.out.println("list.getFlgFechaReqStr():" + tipoEnvioUniNegBean.getFlgFechaReqStr());
                    this.renderedFecha = tipoEnvioUniNegBean.getFlgFechaReqStr();
                    System.out.println("renderedFecha:" + renderedFecha);
                }

                TipoEnvioUniNegService tipoEnvioUniNegService = BeanFactory.getTipoEnvioUniNegService();
                listaTipoEnvioUniNegBean = new ArrayList<>();
                listaTipoEnvioUniNegBean = tipoEnvioUniNegService.obtenerTipoEnvioUniNeg(usuarioSessionBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());

                if (!listaTipoEnvioUniNegBean.isEmpty()) {
                    if (listaTipoEnvioUniNegBean.size() > 1) {
                        for (TipoEnvioUniNegBean list : listaTipoEnvioUniNegBean) {
                            if (list.getIdTipoEnvioUniNeg().equals(this.tipoEnvioUniNegBean.getIdTipoEnvioUniNeg())) {
                                this.tipoEnvioUniNegBean = list;
                                obtenerRenderedFecha(list);
                                break;
                            }
                        }
                    } else {
                        this.tipoEnvioUniNegBean = listaTipoEnvioUniNegBean.get(0);
                        obtenerRenderedFecha(listaTipoEnvioUniNegBean.get(0));
                    }
                }
            }

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void limpiarEnvio() {
        try {
            procesoEnvioBean = new ProcesoEnvioBean();
            listaEnvioBcoBean = new ArrayList<>();
            cargarDatos();
            Date fecha = new Date();
            DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd");
            String date = fechaHora.format(fecha.getTime());
            Date date1 = fechaHora.parse(date);

            fechaInicio = date1;
            fechaFin = date1;

        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    //getter and setter 
    public List<EnvioBcoBean> getListaEnvioBcoBean() {
        if (listaEnvioBcoBean == null) {
            listaEnvioBcoBean = new ArrayList<>();
        }
        return listaEnvioBcoBean;
    }

    public void setListaEnvioBcoBean(List<EnvioBcoBean> listaEnvioBcoBean) {
        this.listaEnvioBcoBean = listaEnvioBcoBean;
    }

    public List<Object> getListaData() {
        return listaData;
    }

    public void setListaData(List<Object> listaData) {
        this.listaData = listaData;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public List<ProcesoFilesBean> getListaProcesoFilesDetalleBean() {
        if (listaProcesoFilesDetalleBean == null) {
            listaProcesoFilesDetalleBean = new ArrayList<>();
        }
        return listaProcesoFilesDetalleBean;
    }

    public void setListaProcesoFilesDetalleBean(List<ProcesoFilesBean> listaProcesoFilesDetalleBean) {
        this.listaProcesoFilesDetalleBean = listaProcesoFilesDetalleBean;
    }

    public StreamedContent getContent() {
        return content;
    }

    public void setContent(StreamedContent content) {
        this.content = content;
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

    public Map<String, Integer> getListaMeses() {
        return listaMeses;
    }

    public void setListaMeses(Map<String, Integer> listaMeses) {
        this.listaMeses = listaMeses;
    }

    public Integer getPeriodoInicio() {
        return periodoInicio;
    }

    public void setPeriodoInicio(Integer periodoInicio) {
        this.periodoInicio = periodoInicio;
    }

    public Integer getPeriodoFin() {
        return periodoFin;
    }

    public void setPeriodoFin(Integer periodoFin) {
        this.periodoFin = periodoFin;
    }

    public Integer[] getSelectedMesesNoConsiderar() {
        return selectedMesesNoConsiderar;
    }

    public void setSelectedMesesNoConsiderar(Integer[] selectedMesesNoConsiderar) {
        this.selectedMesesNoConsiderar = selectedMesesNoConsiderar;
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

    public ProcesoBancoBean getProcesoBancoBean() {
        if (procesoBancoBean == null) {
            procesoBancoBean = new ProcesoBancoBean();
        }
        return procesoBancoBean;
    }

    public void setProcesoBancoBean(ProcesoBancoBean procesoBancoBean) {
        this.procesoBancoBean = procesoBancoBean;
    }

    public List<MesBean> getListaMesAll() {
        if (listaMesAll == null) {
            listaMesAll = new ArrayList<>();
        }
        return listaMesAll;
    }

    public void setListaMesAll(List<MesBean> listaMesAll) {
        this.listaMesAll = listaMesAll;
    }

    public List<TipoEnvioUniNegBean> getListaTipoEnvioUniNegBean() {
        if (listaTipoEnvioUniNegBean == null) {
            listaTipoEnvioUniNegBean = new ArrayList<>();

        }
        return listaTipoEnvioUniNegBean;
    }

    public void setListaTipoEnvioUniNegBean(List<TipoEnvioUniNegBean> listaTipoEnvioUniNegBean) {
        this.listaTipoEnvioUniNegBean = listaTipoEnvioUniNegBean;
    }

    public String getRenderedFecha() {
        return renderedFecha;
    }

    public void setRenderedFecha(String renderedFecha) {
        this.renderedFecha = renderedFecha;
    }

    public TipoEnvioUniNegBean getTipoEnvioUniNegBean() {
        if (tipoEnvioUniNegBean == null) {
            tipoEnvioUniNegBean = new TipoEnvioUniNegBean();
        }
        return tipoEnvioUniNegBean;
    }

    public void setTipoEnvioUniNegBean(TipoEnvioUniNegBean tipoEnvioUniNegBean) {
        this.tipoEnvioUniNegBean = tipoEnvioUniNegBean;
    }

    public List<ProcesoBancoBean> getListaProcesoBancoBean() {
        if (listaProcesoBancoBean == null) {
            listaProcesoBancoBean = new ArrayList<>();
        }
        return listaProcesoBancoBean;
    }

    public void setListaProcesoBancoBean(List<ProcesoBancoBean> listaProcesoBancoBean) {
        this.listaProcesoBancoBean = listaProcesoBancoBean;
    }

}

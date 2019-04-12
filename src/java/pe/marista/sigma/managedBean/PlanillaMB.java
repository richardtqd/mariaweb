package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import static org.apache.poi.hssf.usermodel.HeaderFooter.file;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.jfree.util.HashNMap;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.AsientoBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.MesBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.PlanillaBean;
import pe.marista.sigma.bean.PlanillaCtsBean;
import pe.marista.sigma.bean.PlanillaCtsNoProcesadosBean;
import pe.marista.sigma.bean.PlanillaNoProcesadoBean;
import pe.marista.sigma.bean.TemporalBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.ConceptoPlanillaRepBean;
import pe.marista.sigma.bean.reporte.PlanillaCtsReapBean;
import pe.marista.sigma.bean.reporte.PlanillaPersonalCRRepBean;
import pe.marista.sigma.dao.AsientoDAO;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.AsientoService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.PersonalService;
import pe.marista.sigma.service.PlanillaService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;
import pe.marista.sigma.util.MensajesBackEnd;

public class PlanillaMB extends BaseMB implements Serializable {

    @PostConstruct
    public void PlanillaMB() {
        try {
            //sesión del usuario
            usuarioLoginBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            getListaMesAll();
            obtenerListaMeses();
            getListaMesAllPlanilla();
            obtenerListaMesesPlanilla();

            //CARGA DE PLANILLA
            getPlanillaBean();
            getPlanillaBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            getPlanillaBean().setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco));

            //PLANILLA FILTRO
            getPlanillaFiltroBean();
            getPlanillaFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
//            getPlanillaFiltroBean().setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco));
            Calendar miCalendario = Calendar.getInstance();
            getPlanillaBean().setAnio(miCalendario.get(Calendar.YEAR));

            getPlanillaFiltroBean().setAnio(miCalendario.get(Calendar.YEAR));
            getPlanillaCtsBean();
            getPlanillaCtsBean().setAnio(miCalendario.get(Calendar.YEAR));
            setAnio(miCalendario.get(Calendar.YEAR));
            listaReporte();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //CLASES
    private UsuarioBean usuarioLoginBean;
    private PlanillaBean planillaBean;
    private PlanillaCtsBean planillaCtsBean;
    private PlanillaBean planillaFiltroBean;
    private List<List<Object>> listaObjectPlanilla;

    //LISTAS
    private List<MesBean> listaMesAllPlanilla;
    private List<MesBean> listaMesAll;
    private List<PlanillaBean> listaPlanillaBean;
    private List<PlanillaNoProcesadoBean> listaPlanillaNoProcesadoBean;

    //VARIABLES
    private Integer row = 0;
    private Integer col = 0;
    private Integer anio;

    private Integer cantidadProsesado = 0;
    private Integer cantidadSinPro;
    private Integer cantidadTotalExcel;
    private Double cantidadPlanillaDinero = 0.0;

    //ayuda Vista
    private Integer cantidadProcesadosVista = 0;

    private String fechaSeleccionada;
    private AsientoBean asientoBean;
    private List<PlanillaCtsBean> listaPlanillaCtsBean;
    private List<PlanillaCtsBean> listaPlanillaCtsNoProcesadoBean;
    private List<PlanillaCtsNoProcesadosBean> listaPlanillaCtsNoProcesadoBean2;
    private Map<String, Integer> listaReporte;
    private Integer orden;
    private Integer[] selectedIdTipoAcceso;
    private List<CodigoBean> listaNivelIns;
    private Integer mes;

    public void cargarDatosTipoNiveles() {
        try {
            List<Integer> listaTipoAccesoUsu = new ArrayList<>();
            CodigoService codigoService = BeanFactory.getCodigoService();
            if (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg().equals(MaristaConstantes.UNI_NEG_SECTOR)) {
                getListaNivelIns();
                listaNivelIns = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_NivelCong));
            } else {
                getListaNivelIns();
                listaNivelIns = codigoService.obtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_NivelColegio));
            }
            Integer size = listaTipoAccesoUsu.size();
            selectedIdTipoAcceso = new Integer[size];
            Integer count = 0;
            for (Integer lis : listaTipoAccesoUsu) {
                selectedIdTipoAcceso[count] = lis;
                count = count + 1;
            }
            Calendar miCalendario = Calendar.getInstance();
            setAnio(miCalendario.get(Calendar.YEAR));
            getListaMesAll();
            obtenerListaMeses();
            listaReporte();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerListaMeses() {
        try {
            listaMesAll.add(new MesBean(1, MaristaConstantes.ENERO));
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
            listaMesAll.add(new MesBean(13, MaristaConstantes.JULIO_GRATI));
            listaMesAll.add(new MesBean(14, MaristaConstantes.DICIEMBRE_GRATI));
            //CTS
            listaMesAll.add(new MesBean(15, MaristaConstantes.CTS_MAYO));
            listaMesAll.add(new MesBean(16, MaristaConstantes.CTS_NOVIEMBRE));
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void listaReporte() {
        listaReporte = new LinkedHashMap<>();
        listaReporte.put(MensajesBackEnd.getValueOfKey("etiquetaReportePersonalAc", null), 1);
        listaReporte.put(MensajesBackEnd.getValueOfKey("etiquetaReportePersonalBa", null), 2);
        listaReporte.put(MensajesBackEnd.getValueOfKey("etiquetaPlanillaCuentas", null), 3);
        listaReporte.put(MensajesBackEnd.getValueOfKey("etiquetaReporteTotalesTipo", null), 4);
        listaReporte.put(MensajesBackEnd.getValueOfKey("etiquetaTipoPlaniCR", null), 5);
        listaReporte.put(MensajesBackEnd.getValueOfKey("etiquetaTrabajadorCr", null), 6);
        listaReporte.put(MensajesBackEnd.getValueOfKey("etiquetaReportePlanillaCts", null), 7);
        listaReporte = Collections.unmodifiableMap(listaReporte);
    }

    public void obtenerReporte() {
        try {
            System.out.println("reporte: " + orden);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerListaMesesPlanilla() {
        try {
            listaMesAllPlanilla.add(new MesBean(1, MaristaConstantes.ENERO));
            listaMesAllPlanilla.add(new MesBean(2, MaristaConstantes.MES_FEBRERO));
            listaMesAllPlanilla.add(new MesBean(3, MaristaConstantes.MARZO));
            listaMesAllPlanilla.add(new MesBean(4, MaristaConstantes.ABRIL));
            listaMesAllPlanilla.add(new MesBean(5, MaristaConstantes.MAYO));
            listaMesAllPlanilla.add(new MesBean(6, MaristaConstantes.JUNIO));
            listaMesAllPlanilla.add(new MesBean(7, MaristaConstantes.JULIO));
            listaMesAllPlanilla.add(new MesBean(8, MaristaConstantes.AGOSTO));
            listaMesAllPlanilla.add(new MesBean(9, MaristaConstantes.SETIEMBRE));
            listaMesAllPlanilla.add(new MesBean(10, MaristaConstantes.OCTUBRE));
            listaMesAllPlanilla.add(new MesBean(11, MaristaConstantes.NOVIEMBRE));
            listaMesAllPlanilla.add(new MesBean(12, MaristaConstantes.DICIEMBRE));
            listaMesAllPlanilla.add(new MesBean(13, MaristaConstantes.JULIO_GRATI));
            listaMesAllPlanilla.add(new MesBean(14, MaristaConstantes.DICIEMBRE_GRATI));
        } catch (Exception err) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), err);
        }
    }

    public void cargarPlanilla(FileUploadEvent event) {
        List listaData = new ArrayList();
        try {
            InputStream miArchivo = null;
            UploadedFile file = event.getFile();
            miArchivo = file.getInputstream();
            XSSFWorkbook workbook = new XSSFWorkbook(miArchivo);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator rowIterator = sheet.rowIterator();
            Integer n = -1;
            while (rowIterator.hasNext()) {
                XSSFRow xSSFRow = (XSSFRow) rowIterator.next();
                Iterator iterator = xSSFRow.cellIterator();
                List cellList = new ArrayList();
                while (iterator.hasNext()) {
                    XSSFCell xSSFCell = (XSSFCell) iterator.next();
                    if (!xSSFCell.getStringCellValue().isEmpty()) {
                        cellList.add(xSSFCell);
                    } else {
                        cellList.add("0");
                    }
                }
                listaData.add(cellList);
            }
            System.out.println(">>>>" + listaData.size());
            listaObjectPlanilla = listaMapa(listaData);

            //RECORRIENDO LISTA DE EXCEL
            Integer personal = 1, id = 0;
            for (int i = 0; i < row; i++) {
                TemporalBean temporalBean = new TemporalBean();
                temporalBean.setPersonal(personal);
                temporalBean.setColumna1(listaObjectPlanilla.get(i).get(0).toString());
                temporalBean.setColumna2(listaObjectPlanilla.get(i).get(1).toString());
                temporalBean.setColumna3(listaObjectPlanilla.get(i).get(2).toString());
                temporalBean.setColumna4(listaObjectPlanilla.get(i).get(3).toString());
                temporalBean.setColumna5(listaObjectPlanilla.get(i).get(4).toString());
                temporalBean.setColumna6(Integer.parseInt(listaObjectPlanilla.get(i).get(5).toString()));
                temporalBean.setColumna7(listaObjectPlanilla.get(i).get(6).toString());
                temporalBean.setColumna8(Double.parseDouble(listaObjectPlanilla.get(i).get(7).toString()));
                temporalBean.setColumna9(listaObjectPlanilla.get(i).get(8).toString());
                temporalBean.setColumna10(Double.parseDouble(listaObjectPlanilla.get(i).get(9).toString()));
                temporalBean.setColumna11(listaObjectPlanilla.get(i).get(10).toString());
                temporalBean.setColumna12(Double.parseDouble(listaObjectPlanilla.get(i).get(11).toString()));
                temporalBean.setColumna13(listaObjectPlanilla.get(i).get(12).toString());
                temporalBean.setColumna14(Double.parseDouble(listaObjectPlanilla.get(i).get(13).toString()));
                temporalBean.setColumna15(listaObjectPlanilla.get(i).get(14).toString());
                temporalBean.setColumna16(Double.parseDouble(listaObjectPlanilla.get(i).get(15).toString()));
                temporalBean.setColumna17(listaObjectPlanilla.get(i).get(16).toString());
                temporalBean.setColumna18(Double.parseDouble(listaObjectPlanilla.get(i).get(17).toString()));
                temporalBean.setColumna19(listaObjectPlanilla.get(i).get(18).toString());
                temporalBean.setColumna20(Double.parseDouble(listaObjectPlanilla.get(i).get(19).toString()));
                temporalBean.setColumna21(listaObjectPlanilla.get(i).get(20).toString());
                temporalBean.setColumna22(Double.parseDouble(listaObjectPlanilla.get(i).get(21).toString()));
                temporalBean.setColumna23(listaObjectPlanilla.get(i).get(22).toString());
                temporalBean.setColumna24(Double.parseDouble(listaObjectPlanilla.get(i).get(23).toString()));
                temporalBean.setColumna25(listaObjectPlanilla.get(i).get(24).toString());
                temporalBean.setColumna26(Double.parseDouble(listaObjectPlanilla.get(i).get(25).toString()));
                temporalBean.setColumna27(listaObjectPlanilla.get(i).get(26).toString());
                temporalBean.setColumna28(Double.parseDouble(listaObjectPlanilla.get(i).get(27).toString()));
                temporalBean.setColumna29(listaObjectPlanilla.get(i).get(28).toString());
                temporalBean.setColumna30(Double.parseDouble(listaObjectPlanilla.get(i).get(29).toString()));
                temporalBean.setColumna31(listaObjectPlanilla.get(i).get(30).toString());
                temporalBean.setColumna32(Double.parseDouble(listaObjectPlanilla.get(i).get(31).toString()));
                temporalBean.setColumna33(listaObjectPlanilla.get(i).get(32).toString());
                temporalBean.setColumna34(Double.parseDouble(listaObjectPlanilla.get(i).get(33).toString()));
                temporalBean.setColumna35(listaObjectPlanilla.get(i).get(34).toString());
                temporalBean.setColumna36(Double.parseDouble(listaObjectPlanilla.get(i).get(35).toString()));
                temporalBean.setColumna37(listaObjectPlanilla.get(i).get(36).toString());
                temporalBean.setColumna38(Double.parseDouble(listaObjectPlanilla.get(i).get(37).toString()));
                if (row % 4 == 0) {
                    personal++;
                }
                PlanillaService planillaService = BeanFactory.getPlanillaService();
                planillaService.insertarTemporal(temporalBean);
                RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void filtroAlSubirExcel(FileUploadEvent event) {
        try {
            PersonalService personalService = BeanFactory.getPersonalService();
            PlanillaService planillaService = BeanFactory.getPlanillaService();
            List<Integer> listaPersonal = new ArrayList<>();
            PersonalBean personal = new PersonalBean();
            if (planillaBean.getFechaSubida() != null && planillaBean.getMes() != null && !getPlanillaBean().getMes().equals(0)) {
                String ultimoDiaMes = planillaService.obtenerUltimoDiaDelMes(planillaBean.getAnio(), getPlanillaBean().getMes());
                if (planillaBean.getFechaSubida() != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    fechaSeleccionada = sdf.format(planillaBean.getFechaSubida());
                    if (fechaSeleccionada.equals(ultimoDiaMes)) {
                        System.out.println("entro");
                        if (getPlanillaBean().getMes() != null && !getPlanillaBean().getMes().equals(0)) {
                            String uniNeg = (usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                            listaPersonal = personalService.filtrarPersonalConCentros(uniNeg);
                            if (listaPersonal.size() > 0) {
                                for (Integer status : listaPersonal) {
                                    if (status.equals(1)) {
                                        List<PersonalBean> listaPerDuplicados = new ArrayList<>();
                                        listaPerDuplicados = personalService.duplicadosPersonal();
                                        if (listaPerDuplicados.size() == 0) {
//                                            new MensajePrime().addInformativeMessagePer("Subiendo Archivo . . .");  
                                            cargarPlanillaXlsx(event);
                                        } else {
                                            new MensajePrime().addInformativeMessagePer("Hay personal duplicados activos, cambiar de estado");
                                        }
                                    } else {
                                        System.out.println("1");
                                        new MensajePrime().addInformativeMessagePer("Personal No tiene porcentajes en los Centros de responsabilidad");
                                    }
                                }
                            } else {
                                System.out.println("2");
                                new MensajePrime().addInformativeMessagePer("Personal No tiene porcentajes en los Centros de responsabilidad");
                            }
                        } else {
                            new MensajePrime().addInformativeMessagePer("Ingresar el mes");
                        }
                    } else {
                        if (planillaBean.getMes() == 13 || planillaBean.getMes() == 14) {
                            new MensajePrime().addInformativeMessagePer("La fecha seleccionada no es quincena del mes");
                        } else {
                            new MensajePrime().addInformativeMessagePer("La fecha seleccionada no es el último dia del mes");
                        }
                    }
                } else {
                    new MensajePrime().addInformativeMessagePer("Seleccionar fecha de planilla");
                }
            } else {
                new MensajePrime().addInformativeMessagePer("Seleccionar fecha y mes de planilla");
            }
            System.out.println("gfds");
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarPlanillaXlsx(FileUploadEvent event) {
        new MensajePrime().addInformativeMessagePer("Subiendo Archivo . . .");
        List listaData = new ArrayList();
        try {
            InputStream miArchivo = null;
            UploadedFile file = event.getFile();
            miArchivo = file.getInputstream();
            XSSFWorkbook workbook = new XSSFWorkbook(miArchivo);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Row rowXls;
            for (Iterator<Row> it = sheet.iterator(); it.hasNext();) {
                rowXls = it.next();
                List cellList = new ArrayList();
                for (Integer cn = 0; cn < rowXls.getLastCellNum(); cn++) {
                    Cell cell = rowXls.getCell(cn, Row.CREATE_NULL_AS_BLANK);
                    if (cell.toString().equals("")) {
                        cellList.add("0");
                    } else {
                        cellList.add(cell.toString());
                    }
                }
                listaData.add(cellList);
            }
            System.out.println(">>>>" + listaData.size());
            listaObjectPlanilla = listaMapa(listaData);

            PlanillaService planillaService = BeanFactory.getPlanillaService();
            Integer idPlanilla = planillaService.obtenerPlanillaUltimoId();
            System.out.println("so: " + idPlanilla);

            //RECORRIENDO LISTA DE EXCEL
            Integer personal = 1, id = 0;
            for (int i = 11; i < row; i++) {
                Integer filas = listaObjectPlanilla.get(i).size();
                TemporalBean temporalBean = new TemporalBean();
                temporalBean.setPersonal(personal);
                if (0 < filas) {
                    temporalBean.setColumna1(listaObjectPlanilla.get(i).get(0).toString());
                } else if (0 > filas) {
                    temporalBean.setColumna1(null);
                }
                if (1 < filas) {
                    temporalBean.setColumna2(listaObjectPlanilla.get(i).get(1).toString());
                } else if (1 > filas) {
                    temporalBean.setColumna2(null);
                }
                if (2 < filas) {
                    temporalBean.setColumna3(listaObjectPlanilla.get(i).get(2).toString());
                } else if (2 > filas) {
                    temporalBean.setColumna3(null);
                }
                if (3 < filas) {
                    temporalBean.setColumna4(listaObjectPlanilla.get(i).get(3).toString());
                } else if (3 > filas) {
                    temporalBean.setColumna4(null);
                }
                if (4 < filas) {
                    temporalBean.setColumna5(listaObjectPlanilla.get(i).get(4).toString());
                } else if (4 > filas) {
                    temporalBean.setColumna5(null);
                }
                if (5 < filas) {
                    if (listaObjectPlanilla.get(i).get(5).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        double d = Double.parseDouble(listaObjectPlanilla.get(i).get(5).toString().replace(")", ""));
                        int valor = (int) d;
                        temporalBean.setColumna6(valor);
                    } else {
                        temporalBean.setColumna6(null);
                    }
                } else if (5 > filas) {
                    temporalBean.setColumna6(null);
                }
                if (6 < filas) {
                    temporalBean.setColumna7(listaObjectPlanilla.get(i).get(6).toString());
                } else if (6 > filas) {
                    temporalBean.setColumna7(null);
                }
                if (7 < filas) {
                    if (listaObjectPlanilla.get(i).get(7).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        temporalBean.setColumna8(Double.parseDouble(listaObjectPlanilla.get(i).get(7).toString().replace(")", "")));
                    } else {
                        temporalBean.setColumna8(null);
                    }
                } else if (7 > filas) {
                    temporalBean.setColumna8(null);
                }
                if (8 < filas) {
                    temporalBean.setColumna9(listaObjectPlanilla.get(i).get(8).toString());
                } else if (8 > filas) {
                    temporalBean.setColumna9(null);
                }
                if (9 < filas) {
                    if (listaObjectPlanilla.get(i).get(9).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        temporalBean.setColumna10(Double.parseDouble(listaObjectPlanilla.get(i).get(9).toString().replace(")", "")));
                    } else {
                        temporalBean.setColumna10(null);
                    }
                } else if (9 > filas) {
                    temporalBean.setColumna10(null);
                }
                if (10 < filas) {
                    temporalBean.setColumna11(listaObjectPlanilla.get(i).get(10).toString());
                } else if (10 > filas) {
                    temporalBean.setColumna11(null);
                }
                if (11 < filas) {
                    if (listaObjectPlanilla.get(i).get(11).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        temporalBean.setColumna12(Double.parseDouble(listaObjectPlanilla.get(i).get(11).toString().replace(")", "")));
                    } else {
                        temporalBean.setColumna12(null);
                    }
                } else if (11 > filas) {
                    temporalBean.setColumna12(null);
                }
                if (12 < filas) {
                    temporalBean.setColumna13(listaObjectPlanilla.get(i).get(12).toString());
                } else if (12 > filas) {
                    temporalBean.setColumna13(null);
                }
                if (13 < filas) {
                    if (listaObjectPlanilla.get(i).get(13).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        temporalBean.setColumna14(Double.parseDouble(listaObjectPlanilla.get(i).get(13).toString().replace(")", "")));
                    } else {
                        temporalBean.setColumna14(null);
                    }
                } else if (13 > filas) {
                    temporalBean.setColumna14(null);
                }
                if (14 < filas) {
                    temporalBean.setColumna15(listaObjectPlanilla.get(i).get(14).toString());
                } else if (14 > filas) {
                    temporalBean.setColumna15(null);
                }
                if (15 < filas) {
                    if (listaObjectPlanilla.get(i).get(15).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        temporalBean.setColumna16(Double.parseDouble(listaObjectPlanilla.get(i).get(15).toString().replace(")", "")));
                    } else {
                        temporalBean.setColumna16(null);
                    }
                } else if (15 > filas) {
                    temporalBean.setColumna16(null);
                }
                if (16 < filas) {
                    temporalBean.setColumna17(listaObjectPlanilla.get(i).get(16).toString());
                } else if (16 > filas) {
                    temporalBean.setColumna17(null);
                }
                if (17 < filas) {
                    if (listaObjectPlanilla.get(i).get(17).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        temporalBean.setColumna18(Double.parseDouble(listaObjectPlanilla.get(i).get(17).toString().replace(")", "")));
                    } else {
                        temporalBean.setColumna18(null);
                    }
                } else if (17 > filas) {
                    temporalBean.setColumna18(null);
                }
                if (18 < filas) {
                    temporalBean.setColumna19(listaObjectPlanilla.get(i).get(18).toString());
                } else if (18 > filas) {
                    temporalBean.setColumna19(null);
                }
                if (19 < filas) {
                    if (listaObjectPlanilla.get(i).get(19).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        temporalBean.setColumna20(Double.parseDouble(listaObjectPlanilla.get(i).get(19).toString().replace(")", "")));
                    } else {
                        temporalBean.setColumna20(null);
                    }
                } else if (19 > filas) {
                    temporalBean.setColumna20(null);
                }
                if (20 < filas) {
                    temporalBean.setColumna21(listaObjectPlanilla.get(i).get(20).toString());
                } else if (20 > filas) {
                    temporalBean.setColumna21(null);
                }
                if (21 < filas) {
                    if (listaObjectPlanilla.get(i).get(21).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        temporalBean.setColumna22(Double.parseDouble(listaObjectPlanilla.get(i).get(21).toString().replace(")", "")));
                    } else {
                        temporalBean.setColumna22(null);
                    }
                } else if (21 > filas) {
                    temporalBean.setColumna22(null);
                }
                if (22 < filas) {
                    temporalBean.setColumna23(listaObjectPlanilla.get(i).get(22).toString());
                } else if (22 > filas) {
                    temporalBean.setColumna23(null);
                }
                if (23 < filas) {
                    if (listaObjectPlanilla.get(i).get(23).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        temporalBean.setColumna24(Double.parseDouble(listaObjectPlanilla.get(i).get(23).toString().replace(")", "")));
                    } else {
                        temporalBean.setColumna24(null);
                    }
                } else if (23 > filas) {
                    temporalBean.setColumna24(null);
                }
                if (24 < filas) {
                    temporalBean.setColumna25(listaObjectPlanilla.get(i).get(24).toString());
                } else if (24 > filas) {
                    temporalBean.setColumna25(null);
                }
                if (25 < filas) {
                    if (listaObjectPlanilla.get(i).get(25).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        temporalBean.setColumna26(Double.parseDouble(listaObjectPlanilla.get(i).get(25).toString().replace(")", "")));
                    } else {
                        temporalBean.setColumna26(null);
                    }
                } else if (25 > filas) {
                    temporalBean.setColumna26(null);
                }
                if (26 < filas) {
                    temporalBean.setColumna27(listaObjectPlanilla.get(i).get(26).toString());
                } else if (26 > filas) {
                    temporalBean.setColumna27(null);
                }
                if (27 < filas) {
                    if (listaObjectPlanilla.get(i).get(27).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        temporalBean.setColumna28(Double.parseDouble(listaObjectPlanilla.get(i).get(27).toString().replace(")", "")));
                    } else {
                        temporalBean.setColumna28(null);
                    }
                } else if (27 > filas) {
                    temporalBean.setColumna28(null);
                }
                if (28 < filas) {
                    temporalBean.setColumna29(listaObjectPlanilla.get(i).get(28).toString());
                } else if (28 > filas) {
                    temporalBean.setColumna29(null);
                }
                if (29 < filas) {
                    if (listaObjectPlanilla.get(i).get(29).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        temporalBean.setColumna30(Double.parseDouble(listaObjectPlanilla.get(i).get(29).toString().replace(")", "")));
                    } else {
                        temporalBean.setColumna30(null);
                    }
                } else if (29 > filas) {
                    temporalBean.setColumna30(null);
                }
                if (30 < filas) {
                    temporalBean.setColumna31(listaObjectPlanilla.get(i).get(30).toString());
                } else if (30 > filas) {
                    temporalBean.setColumna31(null);
                }
                if (31 < filas) {
                    if (listaObjectPlanilla.get(i).get(31).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        temporalBean.setColumna32(Double.parseDouble(listaObjectPlanilla.get(i).get(31).toString().replace(")", "")));
                    } else {
                        temporalBean.setColumna32(null);
                    }
                } else if (31 > filas) {
                    temporalBean.setColumna32(null);
                }
                if (32 < filas) {
                    temporalBean.setColumna33(listaObjectPlanilla.get(i).get(32).toString());
                } else if (32 > filas) {
                    temporalBean.setColumna33(null);
                }
                if (33 < filas) {
                    if (listaObjectPlanilla.get(i).get(33).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        temporalBean.setColumna34(Double.parseDouble(listaObjectPlanilla.get(i).get(33).toString().replace(")", "")));
                    } else {
                        temporalBean.setColumna34(null);
                    }
                } else if (33 > filas) {
                    temporalBean.setColumna34(null);
                }
                if (34 < filas) {
                    temporalBean.setColumna35(listaObjectPlanilla.get(i).get(34).toString());
                } else if (34 > filas) {
                    temporalBean.setColumna35(null);
                }
                if (35 < filas) {
                    if (listaObjectPlanilla.get(i).get(35).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        temporalBean.setColumna36(Double.parseDouble(listaObjectPlanilla.get(i).get(35).toString().replace(")", "")));
                    } else {
                        temporalBean.setColumna36(null);
                    }
                } else if (35 > filas) {
                    temporalBean.setColumna36(null);
                }
                if (36 < filas) {
                    temporalBean.setColumna37(listaObjectPlanilla.get(i).get(36).toString());
                } else if (36 > filas) {
                    temporalBean.setColumna37(null);
                }
                if (37 < filas) {
                    if (listaObjectPlanilla.get(i).get(37).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        temporalBean.setColumna38(Double.parseDouble(listaObjectPlanilla.get(i).get(37).toString().replace(")", "")));
                    } else {
                        temporalBean.setColumna38(null);
                    }
                } else if (37 > filas) {
                    temporalBean.setColumna38(null);
                }
                if (row % 4 == 0) {
                    personal++;
                }
                planillaService.insertarTemporal(temporalBean); 
            }
                  
            getPlanillaBean();
            getPlanillaBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            System.out.println("anio: " + planillaBean.getAnio());
            getPlanillaBean().setAnio(planillaBean.getAnio());
            getPlanillaBean().setMes(getPlanillaBean().getMes());
            getPlanillaBean().setCreaPor(usuarioLoginBean.getUsuario());

            getPlanillaBean().setFechaAyuda(fechaSeleccionada);
            System.out.println("aaa: " + fechaSeleccionada);
            System.out.println("bbb: " + planillaBean.getFechaAyuda());
            /* EJECUTANDO PLANILLA */
            planillaService.proPlanilla(getPlanillaBean());
            //INSERTANDO
            if (getPlanillaBean().getMes() == 3) {
                PersonalBean personalCodPer = new PersonalBean();
                PersonalService personalService = BeanFactory.getPersonalService();
                List<PlanillaBean> temList = new ArrayList<>();
                temList = planillaService.consultarPlanillaConjunto(getPlanillaBean().getAnio(), getPlanillaBean().getMes());
                for (PlanillaBean tem : temList) {
                    personalCodPer.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                    personalCodPer.getTipoPlanillaColegio().setIdCodigo(31801);
                    personalCodPer.setCodPer(tem.getCodigo());
                    personalCodPer.setBasico(tem.getRemuneracion());
                    personalCodPer.setNroHoras(tem.getHoras());
                    personalCodPer.setBoniCargoTotal(tem.getBoniCargo());
//                    personalCodPer = personalService.buscarPorCodPer(tem.getCodigo());
                    //modifica basico, jornada y tipo de planilla: privada, estatal, mixta
                    personalService.modificarSueldoBasico(personalCodPer);
                }
            }else{
                System.out.println("es otro mes diferente a marzo");   
            }      
            /* EJECUTANDO ASIENTO */
            //planillaService.proPlanillaAsiento(planillaBean);
//            limpiarPopPlanilla();
            limpiarPlanillaUpload();
            System.out.println("id: " + idPlanilla);
            cantidadProsesado = planillaService.obtenerPlanillaCantidadesInsertadas(idPlanilla);
            System.out.println("2: " + cantidadProsesado);
            PlanillaBean planilla = new PlanillaBean();
            planilla.setIdPlanilla(idPlanilla);
            planilla.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaPlanillaBean = planillaService.obtenerPlanillaListaProcesados(planilla);
            String uniNeg = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            Integer anio = planillaBean.getAnio();
            Integer mes = getPlanillaBean().getMes();

            if (!listaPlanillaBean.isEmpty()) {
                List<String> listaArriiba = new ArrayList<>();
//                String codigo = listaPlanillaBean.get(j).getCodigo();
                for (int j = 0; j < listaPlanillaBean.size(); j++) {
//                    PlanillaNoProcesadoBean planNo = new PlanillaNoProcesadoBean();
                    for (PlanillaBean pla2 : listaPlanillaBean) {
                        listaArriiba.add(pla2.getCodigo());
                    }
                    Double totalRem = 0.0;
                    Double totalSalud = 0.0;
                    Double totalNoRemu = 0.0;
                    totalRem = listaPlanillaBean.get(j).getTotRem().doubleValue();
                    totalSalud = listaPlanillaBean.get(j).getSalud().doubleValue();
                    totalNoRemu = listaPlanillaBean.get(j).getNoRemu().doubleValue();
                    if (totalRem == null || totalRem.equals("")) {
                        totalRem = 0.0;
                    }
                    if (totalSalud == null || totalSalud.equals("")) {
                        totalSalud = 0.0;
                    }
                    if (totalNoRemu == null || totalNoRemu.equals("")) {
                        totalNoRemu = 0.0;
                    }
                    cantidadPlanillaDinero = cantidadPlanillaDinero + (totalRem + totalSalud + totalNoRemu);
                    listaPlanillaBean.get(j).setTotalPersonal(totalRem + totalSalud + totalNoRemu);
                }
                listaPlanillaNoProcesadoBean = planillaService.obtenerPlanillaListaNoProcesados(listaArriiba, uniNeg, anio, mes);
            } else {
                listaPlanillaNoProcesadoBean = planillaService.obtenerPlanillaListaNoProcesados2(uniNeg, anio, mes);
            }
            cantidadTotalExcel = listaPlanillaBean.size() + listaPlanillaNoProcesadoBean.size();
            Integer cantidadAyuda = cantidadTotalExcel - listaPlanillaBean.size();
            if (cantidadAyuda < 0) {
                cantidadSinPro = cantidadAyuda * -1;
                System.out.println("entro");
            } else {
                System.out.println("entro1");
                cantidadSinPro = cantidadAyuda;
            }
            
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarCTSXlsx(FileUploadEvent event) {
        PlanillaService planillaService = BeanFactory.getPlanillaService();
        listaPlanillaCtsBean = new ArrayList<>();
        listaPlanillaCtsNoProcesadoBean = new ArrayList<>();
        PlanillaCtsBean cts = new PlanillaCtsBean();
        new MensajePrime().addInformativeMessagePer("Subiendo Archivo . . .");
        List listaData = new ArrayList();
        getPlanillaCtsBean();
        try {
            InputStream miArchivo = null;
            UploadedFile file = event.getFile();
            miArchivo = file.getInputstream();
            XSSFWorkbook workbook = new XSSFWorkbook(miArchivo);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Row rowXls;
            for (Iterator<Row> it = sheet.iterator(); it.hasNext();) {
                rowXls = it.next();
                List cellList = new ArrayList();
                for (Integer cn = 0; cn < rowXls.getLastCellNum(); cn++) {
                    Cell cell = rowXls.getCell(cn, Row.CREATE_NULL_AS_BLANK);
                    if (cell.toString().equals("")) {
                        cellList.add("0");
                    } else {
                        cellList.add(cell.toString());
                    }
                }
                listaData.add(cellList);
            }
            System.out.println(">>>>" + listaData.size());
            listaObjectPlanilla = listaMapa(listaData);

            //RECORRIENDO LISTA DE EXCEL
            Integer personal = 1, id = 0;

            for (int i = 7; i < row; i++) {
                System.out.println("anio: " + planillaCtsBean.getAnio());
                System.out.println("mes: " + planillaCtsBean.getParte());
                Integer filas = listaObjectPlanilla.get(i).size();
                PlanillaCtsBean planillaCts = new PlanillaCtsBean();
                planillaCts.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                planillaCts.setAnio(planillaCtsBean.getAnio());
                planillaCts.setParte(planillaCtsBean.getParte());
                planillaCts.setCreaPor(usuarioLoginBean.getUsuario());
                String fechaVista = "";
                if (planillaCtsBean.getParte() == 5) {
                    fechaVista = "15-May-" + planillaCts.getAnio();
                } else if (planillaCtsBean.getParte() == 11) {
                    fechaVista = "15-Nov-" + planillaCts.getAnio();
                }
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                Date date = formatter.parse(fechaVista);
                planillaCts.setFechaSubida(date);
                System.out.println("date. " + planillaCts.getFechaSubida());
                if (1 < filas) {
                    planillaCts.setCodigo(listaObjectPlanilla.get(i).get(1).toString());
                } else if (1 > filas) {
                    planillaCts.setCodigo(null);
                }
                if (2 < filas) {
                    planillaCts.setEmpleado(listaObjectPlanilla.get(i).get(2).toString());
                } else if (2 > filas) {
                    planillaCts.setEmpleado(null);
                }
                if (6 < filas) {
                    planillaCts.setFechaIngreso(listaObjectPlanilla.get(i).get(6).toString());
                } else if (6 > filas) {
                    planillaCts.setFechaIngreso(null);
                }
                if (7 < filas) {
                    planillaCts.setFechaInicio(listaObjectPlanilla.get(i).get(7).toString());
                } else if (7 > filas) {
                    planillaCts.setFechaInicio(null);
                }
                if (8 < filas) {
                    planillaCts.setFechaFin(listaObjectPlanilla.get(i).get(8).toString());
                } else if (8 > filas) {
                    planillaCts.setFechaFin(null);
                }
                if (9 < filas) {
                    planillaCts.setCantidadMeses(listaObjectPlanilla.get(i).get(9).toString());
                } else if (9 > filas) {
                    planillaCts.setCantidadMeses(null);
                }
                if (10 < filas) {
                    planillaCts.setCantidadDias(listaObjectPlanilla.get(i).get(10).toString());
                } else if (10 > filas) {
                    planillaCts.setCantidadDias(null);
                }
                if (11 < filas) {
                    if (listaObjectPlanilla.get(i).get(11).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        planillaCts.setCol1(Double.parseDouble(listaObjectPlanilla.get(i).get(11).toString().replace(")", "")));
                    } else {
                        planillaCts.setCol1(null);
                    }
                } else if (11 > filas) {
                    planillaCts.setCol1(null);
                }
                if (12 < filas) {
                    if (listaObjectPlanilla.get(i).get(12).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        planillaCts.setCol2(Double.parseDouble(listaObjectPlanilla.get(i).get(12).toString().replace(")", "")));
                    } else {
                        planillaCts.setCol2(null);
                    }
                } else if (12 > filas) {
                    planillaCts.setCol2(null);
                }
                if (13 < filas) {
                    if (listaObjectPlanilla.get(i).get(13).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        planillaCts.setCol3(Double.parseDouble(listaObjectPlanilla.get(i).get(13).toString().replace(")", "")));
                    } else {
                        planillaCts.setCol3(null);
                    }
                } else if (13 > filas) {
                    planillaCts.setCol3(null);
                }
                if (14 < filas) {
                    if (listaObjectPlanilla.get(i).get(14).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        planillaCts.setCol4(Double.parseDouble(listaObjectPlanilla.get(i).get(14).toString().replace(")", "")));
                    } else {
                        planillaCts.setCol4(null);
                    }
                } else if (14 > filas) {
                    planillaCts.setCol4(null);
                }
                if (15 < filas) {
                    if (listaObjectPlanilla.get(i).get(15).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        planillaCts.setCol5(Double.parseDouble(listaObjectPlanilla.get(i).get(15).toString().replace(")", "")));
                    } else {
                        planillaCts.setCol5(null);
                    }
                } else if (15 > filas) {
                    planillaCts.setCol5(null);
                }
                if (16 < filas) {
                    if (listaObjectPlanilla.get(i).get(16).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        planillaCts.setCol6(Double.parseDouble(listaObjectPlanilla.get(i).get(16).toString().replace(")", "")));
                    } else {
                        planillaCts.setCol6(null);
                    }
                } else if (16 > filas) {
                    planillaCts.setCol6(null);
                }
                if (17 < filas) {
                    planillaCts.setCol7(listaObjectPlanilla.get(i).get(17).toString());
                } else if (17 > filas) {
                    planillaCts.setCol7(null);
                }
                if (18 < filas) {
                    if (listaObjectPlanilla.get(i).get(18).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        planillaCts.setCol8(Double.parseDouble(listaObjectPlanilla.get(i).get(18).toString().replace(")", "")));
                    } else {
                        planillaCts.setCol8(null);
                    }
                } else if (18 > filas) {
                    planillaCts.setCol8(null);
                }
                if (19 < filas) {
                    if (listaObjectPlanilla.get(i).get(19).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        planillaCts.setCol9(Double.parseDouble(listaObjectPlanilla.get(i).get(19).toString().replace(")", "")));
                    } else {
                        planillaCts.setCol9(null);
                    }
                } else if (19 > filas) {
                    planillaCts.setCol9(null);
                }
                if (20 < filas) {
                    planillaCts.setCol10(listaObjectPlanilla.get(i).get(20).toString());
                } else if (20 > filas) {
                    planillaCts.setCol10(null);
                }
                if (21 < filas) {
                    if (listaObjectPlanilla.get(i).get(21).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        planillaCts.setCol11(Double.parseDouble(listaObjectPlanilla.get(i).get(21).toString().replace(")", "")));
                    } else {
                        planillaCts.setCol11(null);
                    }
                } else if (21 > filas) {
                    planillaCts.setCol11(null);
                }
                if (22 < filas) {
                    if (listaObjectPlanilla.get(i).get(22).toString().matches("[+-]?\\d*(\\.\\d+)?")) {
                        planillaCts.setCol12(Double.parseDouble(listaObjectPlanilla.get(i).get(22).toString().replace(")", "")));
                    } else {
                        planillaCts.setCol12(null);
                    }
                } else if (22 > filas) {
                    planillaCts.setCol12(null);
                }
                if (23 < filas) {
                    planillaCts.setCol13(listaObjectPlanilla.get(i).get(23).toString());
                } else if (23 > filas) {
                    planillaCts.setCol13(null);
                }
                if (24 < filas) {
                    planillaCts.setCol14(listaObjectPlanilla.get(i).get(24).toString());
                } else if (24 > filas) {
                    planillaCts.setCol14(null);
                }
                System.out.println("codigo: " + listaObjectPlanilla.get(i).get(1).toString());
                String insertados = planillaService.obtenerListaTrabajadoresPorMes(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                        planillaCtsBean.getAnio(), planillaCtsBean.getParte(), listaObjectPlanilla.get(i).get(1).toString());
                String insertadosNoProc = planillaService.obtenerListaTrabajadoresPorMesNoProc(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(),
                        planillaCtsBean.getAnio(), planillaCtsBean.getParte(), listaObjectPlanilla.get(i).get(1).toString());

                if (!listaObjectPlanilla.get(i).get(1).toString().equals("0")) {
                    if (insertadosNoProc == null) {
                        planillaService.insertarPlanillaCTSNoProcesados(planillaCts);
                        listaPlanillaCtsNoProcesadoBean.add(planillaCts);
                    } else if (!insertadosNoProc.equals(listaObjectPlanilla.get(i).get(1).toString())) {
                        planillaService.insertarPlanillaCTSNoProcesados(planillaCts);
                        listaPlanillaCtsNoProcesadoBean.add(planillaCts);
                    }
                } else {
                    System.out.println("tiene 0");
                }
                Integer datosCompletos = planillaService.obtenerListaTrabajadoresCRparCts(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), listaObjectPlanilla.get(i).get(1).toString());
                if (!listaObjectPlanilla.get(i).get(1).toString().equals("0")) {
                    if (insertados == null) {
                        if (datosCompletos != null) {
                            if (datosCompletos.equals(1)) {
                                planillaService.insertarPlanillaCTS(planillaCts);
                                setPlanillaCtsBean(planillaCts);
                                cts.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                cts.setAnio(planillaCtsBean.getAnio());
                                cts.setParte(getPlanillaCtsBean().getParte());
                                cts.setCreaPor(usuarioLoginBean.getUsuario());
                                cts.setFechaSubida(planillaCts.getFechaSubida());
                                planillaService.sp_ed_obtenerListaTrabajadoresCRparCts(cts);
                            } else if (datosCompletos.equals(0)) {
                                System.out.println("no tiene todos los datos");
                            }
                        } else {
                            System.out.println("El personal no existe o no tiene codigo LPM");
                        }
                    } else if (!insertados.equals(listaObjectPlanilla.get(i).get(1).toString())) {
                        if (datosCompletos != null) {
                            if (datosCompletos.equals(1)) {
                                planillaService.insertarPlanillaCTS(planillaCts);
                                listaPlanillaCtsBean.add(planillaCts);
                                cts.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                                cts.setAnio(planillaCtsBean.getAnio());
                                cts.setParte(getPlanillaCtsBean().getParte());
                                cts.setCreaPor(usuarioLoginBean.getUsuario());
                                cts.setFechaSubida(planillaCts.getFechaSubida());
                                planillaService.sp_ed_obtenerListaTrabajadoresCRparCts(cts);
                            } else if (datosCompletos.equals(0)) {
                                System.out.println("no tiene todos los datos");
                            }
                        } else {
                            System.out.println("El personal no existe o no tiene codigo LPM");
                        }
                    }

                } else {
                    System.out.println("tiene 0");
                }
            }

            limpiarPlanillaUpload();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarPlanillaXls(FileUploadEvent event) {

        try {
            InputStream ExcelFileToRead = new FileInputStream("D:\\planilla_modelo_modi_122017.xlsx");
            UploadedFile file = event.getFile();
            ExcelFileToRead = file.getInputstream();
            XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
            XSSFSheet sheet = wb.getSheetAt(0);

            HSSFRow row;
            HSSFCell cell;

            Iterator rows = sheet.rowIterator();

            while (rows.hasNext()) {
                row = (HSSFRow) rows.next();
                Iterator cells = row.cellIterator();

                while (cells.hasNext()) {
                    cell = (HSSFCell) cells.next();

                    if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        System.out.print(cell.getStringCellValue() + " ");
                    } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                        System.out.print(cell.getNumericCellValue() + " ");
                    } else {
                        //U Can Handel Boolean, Formula, Errors
                    }
                }
                System.out.println();
            }

            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void imprimirPlanillaCts(Integer mes) {

        ServletOutputStream out = null;
        try {
            getPlanillaCtsBean();
            PlanillaService planillaService = BeanFactory.getPlanillaService();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reportePlanillaCts.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);

            List<PlanillaCtsReapBean> listaCabecera = new ArrayList<>();
            listaCabecera = planillaService.obtenerReporteCabecera(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, mes);
            if (!listaCabecera.isEmpty()) {
                for (int j = 0; j < listaCabecera.size(); j++) {
                    List<PlanillaCtsReapBean> listaDetalle = new ArrayList<>();
                    listaDetalle = planillaService.obtenerReporteDetalle(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio, mes, listaCabecera.get(j).getIdCodigo());
                    listaCabecera.get(j).setListaDetalle(listaDetalle);
                    if (!listaDetalle.isEmpty()) {
                        for (int k = 0; k < listaDetalle.size(); k++) {
                            List<PlanillaCtsReapBean> listaSubDetalle = new ArrayList<>();
                            listaSubDetalle = planillaService.obtenerReporteSubDetalle(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), anio,
                                    mes, listaCabecera.get(j).getIdCodigo(), listaDetalle.get(k).getIdPlanillaCts());
                            listaDetalle.get(k).setListaSubDetalle(listaSubDetalle);
                            listaCabecera.get(j).setListaDetalle(listaDetalle);
                        }
                    }
                }
            }

            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCabecera);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);

            byte[] bytes = JasperRunManager.runReportToPdf(reporte, parametros, jrbc);
            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            response.setHeader("Content-Disposition", "inline; filename=SaldoPensiones_" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + ".pdf");
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
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void cargar(FileUploadEvent event) throws FileNotFoundException, IOException {
        try {
            FileInputStream file = new FileInputStream(new File("D:\\planilla_modelo_modi_122017.xlsx"));

            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row;
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                Cell celda;
                while (cellIterator.hasNext()) {
                    celda = cellIterator.next();

                    switch (celda.getCellType()) {
                        case Cell.CELL_TYPE_NUMERIC:
                            if (DateUtil.isCellDateFormatted(celda)) {
                                System.out.println(celda.getDateCellValue());
                            } else {
                                System.out.println(celda.getNumericCellValue());
                            }
                            break;
                        case Cell.CELL_TYPE_STRING:
                            System.out.println(celda.getStringCellValue());
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            System.out.println(celda.getBooleanCellValue());
                            break;
                    }
                }
            }
            // cerramos el libro excel1
            //  workbook.close();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public List<List<Object>> listaMapa(List listData) {
        List<List<Object>> listaGenGeo = new ArrayList<List<Object>>();
        try {
            List<Object> listaGeo = new ArrayList<>();
            for (int i = 0; i < listData.size(); i++) {
                row++;
                List cellTemp = (List) listData.get(i);
                listaGeo = new ArrayList<Object>();
                for (int j = 0; j < cellTemp.size(); j++) {
                    col++;
//                    XSSFCell xSSFCell = (XSSFCell) cellTemp.get(j);
                    String cellValue = cellTemp.get(j).toString();
                    listaGeo.add(cellValue);
                }
                listaGenGeo.add(listaGeo);
            }
            System.out.println(">>>>" + listaGenGeo.size());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return listaGenGeo;
    }

    public void cargarPlanillaXls() {
        try {
            List listaData = new ArrayList();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarPlanillaUpload() {
        try {
            listaObjectPlanilla = new ArrayList<>();
            row = 0;
            col = 0;
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarMes() {
        try {
            getPlanillaBean().setMes(getPlanillaBean().getMes());
            System.out.println(">>>" + getPlanillaBean().getMes());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarFecha() {
        try {
            System.out.println(">>>" + getPlanillaBean().getFechaSubida());
            System.out.println(">>>" + getPlanillaCtsBean().getFechaSubida());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarAnio() {
        try {
            getPlanillaBean().setAnio(getPlanillaBean().getAnio());
            System.out.println(">>>" + getPlanillaBean().getAnio());
            getPlanillaCtsBean().setParte(getPlanillaCtsBean().getParte());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPlanilla() {
        try {
            PlanillaService planillaService = BeanFactory.getPlanillaService();
            planillaBean.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaPlanillaBean = planillaService.obtenerPlanilla(planillaBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void filtrarPlanilla() {
        System.out.println("entrosss");
        try {
            System.out.println("entroddd");
            Integer res = 0;
            PlanillaService planillaService = BeanFactory.getPlanillaService();
            if (!getPlanillaFiltroBean().getCodigo().equals("") && getPlanillaFiltroBean().getCodigo() != null) {
                getPlanillaFiltroBean().setCodigo(getPlanillaFiltroBean().getCodigo());
                res = 1;
            }
            if (!getPlanillaFiltroBean().getEmpleado().equals("") && getPlanillaFiltroBean().getEmpleado() != null) {
                getPlanillaFiltroBean().setEmpleado(getPlanillaFiltroBean().getEmpleado());
                res = 1;
            }
            if (getPlanillaFiltroBean().getAnio() != null) {
                getPlanillaFiltroBean().setAnio(getPlanillaFiltroBean().getAnio());
                res = 1;
            }
            if (getPlanillaFiltroBean().getMes() != null) {
                getPlanillaFiltroBean().setMes(getPlanillaFiltroBean().getMes());
                res = 1;
            }
            if (res == 1) {
                if (getPlanillaFiltroBean().getMes() != null && !getPlanillaFiltroBean().getMes().equals(0)) {
                    if (planillaFiltroBean.getMes() == 15 || planillaFiltroBean.getMes() == 16) {
                        listaPlanillaBean = new ArrayList<>();
                        Integer parte = 0;
                        if (planillaFiltroBean.getMes() == 15) {
                            parte = 5;
                        } else {
                            parte = 11;
                        }
                        PlanillaCtsBean plaCts = new PlanillaCtsBean();
                        plaCts.setCodigo(getPlanillaFiltroBean().getCodigo());
                        plaCts.setEmpleado(getPlanillaFiltroBean().getEmpleado());
                        plaCts.setAnio(getPlanillaFiltroBean().getAnio());
                        plaCts.setParte(parte);
                        plaCts.getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
                        listaPlanillaCtsBean = planillaService.filtrarPlanillacts(plaCts);
                        listaPlanillaCtsNoProcesadoBean = planillaService.obtenerPlanillaCTSListaNoProcesados2(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getPlanillaFiltroBean().getAnio(), parte);

                        Integer cantidadAyuda = listaPlanillaCtsBean.size() - listaPlanillaCtsNoProcesadoBean.size();
                        cantidadPlanillaDinero = planillaService.obtenerCantidadDeProcesadosCts(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getPlanillaFiltroBean().getAnio(), parte);
                        System.out.println("resultado cts: " + cantidadAyuda);
                        if (cantidadAyuda < 0) {
                            cantidadSinPro = cantidadAyuda * -1;
                            System.out.println("entro cts");
                        } else {
                            System.out.println("entro1 cts");
                            cantidadSinPro = cantidadAyuda;
                        }
                        System.out.println("entro cts");
                        if (listaPlanillaCtsBean.isEmpty()) {
                            System.out.println("entro2 cts");
                            new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                            listaPlanillaCtsBean = new ArrayList<>();
                        }
                    } else {
                        listaPlanillaCtsBean = new ArrayList<>();
                        listaPlanillaBean = planillaService.filtrarPlanilla(getPlanillaFiltroBean());
                        listaPlanillaNoProcesadoBean = planillaService.obtenerPlanillaListaNoProcesados2(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getPlanillaFiltroBean().getAnio(), getPlanillaFiltroBean().getMes());
                        Integer cantidadAyuda = listaPlanillaBean.size() - listaPlanillaNoProcesadoBean.size();
                        cantidadPlanillaDinero = planillaService.obtenerCantidadDeProcesados(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getPlanillaFiltroBean().getAnio(), getPlanillaFiltroBean().getMes());
                        System.out.println("resultado: " + cantidadAyuda);
                        if (cantidadAyuda < 0) {
                            cantidadSinPro = cantidadAyuda * -1;
                            System.out.println("entro");
                        } else {
                            System.out.println("entro1");
                            cantidadSinPro = cantidadAyuda;
                        }
                        System.out.println("entro");
                        if (listaPlanillaBean.isEmpty()) {
                            System.out.println("entro2");
                            new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                            listaPlanillaBean = new ArrayList<>();
                        }
                    }
                } else {
                    new MensajePrime().addInformativeMessagePer("Ingresar el mes");
                }
            } else if (res == 0) {
                System.out.println("No entro");
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaPlanillaBean = new ArrayList<>();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void noProcesados() {
        try {
            PlanillaService planillaService = BeanFactory.getPlanillaService();
            String uniNeg = usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            Integer anio = planillaFiltroBean.getAnio();
            Integer mes = getPlanillaFiltroBean().getMes();
            Integer parte = 0;
            if (planillaFiltroBean.getMes() == 15) {
                parte = 5;
            } else {
                parte = 11;
            }
            if (planillaFiltroBean.getMes() == 15 || planillaFiltroBean.getMes() == 16) {
                if (!listaPlanillaCtsBean.isEmpty()) {
                    List<String> listaArriiba = new ArrayList<>();
                    for (int j = 0; j < listaPlanillaCtsBean.size(); j++) {
                        for (PlanillaCtsBean pla2 : listaPlanillaCtsBean) {
                            listaArriiba.add(pla2.getCodigo());
                        }
                    }
                    listaPlanillaCtsNoProcesadoBean2 = planillaService.obtenerPlanillaCTSListaNoProcesados(listaArriiba, uniNeg, anio, parte);

                } else {
                    listaPlanillaCtsNoProcesadoBean = planillaService.obtenerPlanillaCTSListaNoProcesados2(uniNeg, anio, parte);
                }
            } else {
                if (!listaPlanillaBean.isEmpty()) {
                    List<String> listaArriiba = new ArrayList<>();
                    for (int j = 0; j < listaPlanillaBean.size(); j++) {
                        for (PlanillaBean pla2 : listaPlanillaBean) {
                            listaArriiba.add(pla2.getCodigo());
                            Double totalRem = listaPlanillaBean.get(j).getTotRem().doubleValue();
                            Double totalSalud = listaPlanillaBean.get(j).getSalud().doubleValue();
                            Double totalNoRemu = listaPlanillaBean.get(j).getNoRemu().doubleValue();
                            cantidadPlanillaDinero = cantidadPlanillaDinero + (totalRem + totalSalud + totalNoRemu);
                            listaPlanillaBean.get(j).setTotalPersonal(totalRem + totalSalud + totalNoRemu);
                        }
                        listaPlanillaNoProcesadoBean = planillaService.obtenerPlanillaListaNoProcesados(listaArriiba, uniNeg, anio, mes);
                    }
                } else {
                    listaPlanillaNoProcesadoBean = planillaService.obtenerPlanillaListaNoProcesados2(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg(), getPlanillaFiltroBean().getAnio(), mes);
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarPlanilla() {
        try {
            planillaFiltroBean = new PlanillaBean();
            listaPlanillaBean = new ArrayList<>();
            getPlanillaFiltroBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getPlanillaFiltroBean().setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco));
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarPopPlanilla() {
        try {
            planillaBean = new PlanillaBean();
            getPlanillaBean().getUnidadNegocioBean().setUniNeg(usuarioLoginBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            getPlanillaBean().setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco));
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //REPORTES
    public void imprimirTodosPdfActivos(Integer status) {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            PersonalService personalService = BeanFactory.getPersonalService();
            String uniNeg = usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reportePersonalCRActivos.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            List<Integer> listaTipoAccesoConsiderar = new ArrayList<>();
            for (int i = 0; i < selectedIdTipoAcceso.length; i++) {
                listaTipoAccesoConsiderar.add(selectedIdTipoAcceso[i]);
                System.out.print(selectedIdTipoAcceso[i] + "\t");
                System.out.print("cantidad Tipo Planilla" + listaTipoAccesoConsiderar.size());
            }
            List<PlanillaPersonalCRRepBean> listaCRPlanillaCabecera = new ArrayList<>();
            List<PlanillaPersonalCRRepBean> listaCRPlanillaCabecera2 = new ArrayList<>();

            for (int j = 0; j < listaTipoAccesoConsiderar.size(); j++) {
                listaCRPlanillaCabecera = personalService.obtenerPlanillaPersonalCR(uniNeg, status, listaTipoAccesoConsiderar.get(j));

                for (PlanillaPersonalCRRepBean listaCR : listaCRPlanillaCabecera) {
                    listaCRPlanillaCabecera2.add(listaCR);
                    System.out.println("lista: " + listaCRPlanillaCabecera2.size());

                }
            }
            if (!listaCRPlanillaCabecera2.isEmpty()) {
                for (int j = 0; j < listaCRPlanillaCabecera2.size(); j++) {
                    List<PlanillaPersonalCRRepBean> listaCRPlanilla = new ArrayList<>();
                    listaCRPlanilla = personalService.obtenerPlanillaPersonalCRDetalle(uniNeg, status, listaCRPlanillaCabecera2.get(j).getId());
                    listaCRPlanillaCabecera2.get(j).setListaDetPlanilla(listaCRPlanilla);

                    List<PlanillaPersonalCRRepBean> listaCRActivosSinNada = new ArrayList<>();
                    listaCRActivosSinNada = personalService.obtenerPlanillaPersonalCRDetalleSinNada(uniNeg, status, listaCRPlanillaCabecera2.get(j).getId());
                    listaCRPlanillaCabecera2.get(j).setListaSinNada(listaCRActivosSinNada);

                    List<PlanillaPersonalCRRepBean> listaTotales = new ArrayList<>();
                    List<PlanillaPersonalCRRepBean> listaTotalesAyuda = new ArrayList<>();
                    for (int k = 0; k < listaCRPlanillaCabecera2.size(); k++) {
                        listaTotalesAyuda = personalService.obtenerPlanillaPersonalCRTotales(uniNeg, status, listaCRPlanillaCabecera2.get(k).getId());
                        for (PlanillaPersonalCRRepBean ayuda : listaTotalesAyuda) {
                            listaTotales.add(ayuda);
                        }
                        listaCRPlanillaCabecera2.get(j).setListaTotales(listaTotales);
                    }
                }
            }

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCRPlanillaCabecera2);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            parametros.put("USUARIO", ub.getUsuario());

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

        FacesContext.getCurrentInstance()
                .responseComplete();
    }

    public void imprimirTodosPdfRemuneracion() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            PersonalService personalService = BeanFactory.getPersonalService();
            String uniNeg = usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteLpmRemuneraciones.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);
            List<ConceptoPlanillaRepBean> listaCabecera = new ArrayList<>();
            listaCabecera = personalService.obtenerPlanillaRemuneracionesCabecera(uniNeg, mes, anio);
            System.out.println("anio " + anio);
            if (!listaCabecera.isEmpty()) {
                for (int m = 0; m < listaCabecera.size(); m++) {
                    List<ConceptoPlanillaRepBean> listaNiveles = new ArrayList<>();
                    listaNiveles = personalService.obtenerPlanillaRemuneracionesId(uniNeg, mes, listaCabecera.get(m).getIdTipoNivel(), anio);
                    listaCabecera.get(m).setListaPersonal(listaNiveles);
                    if (!listaNiveles.isEmpty()) {
                        for (int s = 0; s < listaNiveles.size(); s++) {
                            System.out.println("numero: " + listaNiveles.size());
                            List<ConceptoPlanillaRepBean> listaDetalle = new ArrayList<>();
                            listaDetalle = personalService.obtenerPlanillaRemuneracionesDetalle(uniNeg, listaNiveles.get(s).getIdTipoNivel(), listaNiveles.get(s).getIdObjeto(), mes, anio);
                            listaNiveles.get(s).setListaDetallePersonal(listaDetalle);
                            listaCabecera.get(m).setListaPersonal(listaNiveles);
                        }
                    }
                }
            }

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCabecera);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            parametros.put("USUARIO", ub.getUsuario());

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

        FacesContext.getCurrentInstance()
                .responseComplete();
    }

    public void imprimirPdfCuentas() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            PersonalService personalService = BeanFactory.getPersonalService();
            String uniNeg = usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteTipoPlanillaCR.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            List<Integer> listaTipoAccesoConsiderar = new ArrayList<>();
            for (int i = 0; i < selectedIdTipoAcceso.length; i++) {
                listaTipoAccesoConsiderar.add(selectedIdTipoAcceso[i]);
                System.out.print(selectedIdTipoAcceso[i] + "\t");
                System.out.print("cantidad Tipo Planilla" + listaTipoAccesoConsiderar.size());
            }
            List<ConceptoPlanillaRepBean> listaCRPlanillaCR = new ArrayList<>();
            List<ConceptoPlanillaRepBean> listaCRPlanillaCR2 = new ArrayList<>();
            for (int j = 0; j < listaTipoAccesoConsiderar.size(); j++) {
                listaCRPlanillaCR = personalService.obtenerPlanillaCuenta(uniNeg, listaTipoAccesoConsiderar.get(j), mes, anio);

                for (ConceptoPlanillaRepBean listaCR : listaCRPlanillaCR) {
                    listaCRPlanillaCR2.add(listaCR);
                    System.out.println("lista: " + listaCRPlanillaCR2.size());
                }
            }
            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCRPlanillaCR2);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            parametros.put("USUARIO", ub.getUsuario());

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

        FacesContext.getCurrentInstance()
                .responseComplete();
    }

    public void imprimirPdfCrPlanilla() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            PersonalService personalService = BeanFactory.getPersonalService();
            String uniNeg = usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteCRPlanilla.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            List<Integer> listaTipoAccesoConsiderar = new ArrayList<>();
            for (int i = 0; i < selectedIdTipoAcceso.length; i++) {
                listaTipoAccesoConsiderar.add(selectedIdTipoAcceso[i]);
                System.out.print(selectedIdTipoAcceso[i] + "\t");
                System.out.print("cantidad Tipo Planilla" + listaTipoAccesoConsiderar.size());
            }
            List<ConceptoPlanillaRepBean> listaCRPlanillaCR = new ArrayList<>();
            List<ConceptoPlanillaRepBean> listaCRPlanillaCR2 = new ArrayList<>();
            for (int j = 0; j < listaTipoAccesoConsiderar.size(); j++) {
                listaCRPlanillaCR = personalService.obtenerPlanillaCRCabecera(uniNeg, mes, listaTipoAccesoConsiderar.get(j), anio);

                for (ConceptoPlanillaRepBean listaCR : listaCRPlanillaCR) {
                    listaCRPlanillaCR2.add(listaCR);
                    System.out.println("lista: " + listaCRPlanillaCR2.size());
                }
            }

            if (!listaCRPlanillaCR2.isEmpty()) {
                for (int m = 0; m < listaCRPlanillaCR2.size(); m++) {
                    List<ConceptoPlanillaRepBean> listaDetalle = new ArrayList<>();
                    listaDetalle = personalService.obtenerPlanillaCRDetalle(uniNeg, mes, listaCRPlanillaCR2.get(m).getIdTipoNivel(), anio);
                    listaCRPlanillaCR2.get(m).setListaPersonal(listaDetalle);
                    if (!listaDetalle.isEmpty()) {
                        for (int s = 0; s < listaDetalle.size(); s++) {
                            List<ConceptoPlanillaRepBean> listaSubDetalle = new ArrayList<>();
                            listaSubDetalle = personalService.obtenerPlanillaCRSubDetalle(uniNeg, mes, listaDetalle.get(s).getIdTipoNivel(), anio, listaDetalle.get(s).getCr());
                            listaDetalle.get(s).setListaDetallePersonal(listaSubDetalle);
                            listaCRPlanillaCR2.get(m).setListaPersonal(listaDetalle);
                        }
                    }
                }
            }

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCRPlanillaCR2);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            parametros.put("USUARIO", ub.getUsuario());

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

        FacesContext.getCurrentInstance()
                .responseComplete();
    }

    public void imprimirPdfCrTrabajador() {
        ServletOutputStream out = null;
        Integer id = 0;
        try {
            UsuarioBean usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            PersonalService personalService = BeanFactory.getPersonalService();
            String uniNeg = usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                    getResponse();
            String archivoJasper = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().
                    getRequest()).getServletContext().getRealPath("/reportes/reporteTrabajadorCR.jasper");
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            String absoluteWebPath = externalContext.getRealPath("/");
            File file = new File(archivoJasper);

            List<ConceptoPlanillaRepBean> listaCRPlanillaCR = new ArrayList<>();
            listaCRPlanillaCR = personalService.obtenerTrabajadorCRCabecera(uniNeg, mes, anio);
            if (!listaCRPlanillaCR.isEmpty()) {
                for (int m = 0; m < listaCRPlanillaCR.size(); m++) {
                    List<ConceptoPlanillaRepBean> listaDetalle = new ArrayList<>();
                    listaDetalle = personalService.obtenerTrabajadorCRDetalle(uniNeg, mes, anio, listaCRPlanillaCR.get(m).getIdObjeto());
                    listaCRPlanillaCR.get(m).setListaPersonal(listaDetalle);
                    if (!listaDetalle.isEmpty()) {
                        for (int s = 0; s < listaDetalle.size(); s++) {
                            List<ConceptoPlanillaRepBean> listaSubDetalle = new ArrayList<>();
                            listaSubDetalle = personalService.obtenerTrabajadorCRSubDetalle(uniNeg, mes, anio, listaDetalle.get(s).getCr(), listaDetalle.get(s).getIdObjeto());
                            listaDetalle.get(s).setListaDetallePersonal(listaSubDetalle);
                            listaCRPlanillaCR.get(m).setListaPersonal(listaDetalle);
                        }
                    }
                }
            }

            JasperReport reporte = (JasperReport) JRLoader.loadObject(file);
            JRBeanCollectionDataSource jrbc = new JRBeanCollectionDataSource(listaCRPlanillaCR);
            Map<String, Object> parametros = new HashMap<>();
            String ruta = absoluteWebPath + "reportes\\";
            parametros.put("REPORT_LOCALE", new java.util.Locale("en", "US"));//Para los puntos decimales
            parametros.put("rutaImagen", absoluteWebPath);//Para los puntos decimales
            parametros.put("SUBREPORT_DIR", ruta);
            UsuarioBean ub = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            parametros.put("USUARIO", ub.getUsuario());

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

        FacesContext.getCurrentInstance()
                .responseComplete();
    }

    //GET Y SET
    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
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

    public List<List<Object>> getListaObjectPlanilla() {
        if (listaObjectPlanilla == null) {
            listaObjectPlanilla = new ArrayList<>();
        }
        return listaObjectPlanilla;
    }

    public void setListaObjectPlanilla(List<List<Object>> listaObjectPlanilla) {
        this.listaObjectPlanilla = listaObjectPlanilla;
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

    public PlanillaBean getPlanillaBean() {
        if (planillaBean == null) {
            planillaBean = new PlanillaBean();
        }
        return planillaBean;
    }

    public void setPlanillaBean(PlanillaBean planillaBean) {
        this.planillaBean = planillaBean;
    }

    public List<PlanillaBean> getListaPlanillaBean() {
        return listaPlanillaBean;
    }

    public void setListaPlanillaBean(List<PlanillaBean> listaPlanillaBean) {
        this.listaPlanillaBean = listaPlanillaBean;
    }

    public PlanillaBean getPlanillaFiltroBean() {
        if (planillaFiltroBean == null) {
            planillaFiltroBean = new PlanillaBean();
        }
        return planillaFiltroBean;
    }

    public void setPlanillaFiltroBean(PlanillaBean planillaFiltroBean) {
        this.planillaFiltroBean = planillaFiltroBean;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getCantidadProsesado() {
        return cantidadProsesado;
    }

    public void setCantidadProsesado(Integer cantidadProsesado) {
        this.cantidadProsesado = cantidadProsesado;
    }

    public Integer getCantidadSinPro() {
        return cantidadSinPro;
    }

    public void setCantidadSinPro(Integer cantidadSinPro) {
        this.cantidadSinPro = cantidadSinPro;
    }

    public List<PlanillaNoProcesadoBean> getListaPlanillaNoProcesadoBean() {
        if (listaPlanillaNoProcesadoBean == null) {
            listaPlanillaNoProcesadoBean = new ArrayList<>();
        }
        return listaPlanillaNoProcesadoBean;
    }

    public void setListaPlanillaNoProcesadoBean(List<PlanillaNoProcesadoBean> listaPlanillaNoProcesadoBean) {
        this.listaPlanillaNoProcesadoBean = listaPlanillaNoProcesadoBean;
    }

    public Integer getCantidadTotalExcel() {
        return cantidadTotalExcel;
    }

    public void setCantidadTotalExcel(Integer cantidadTotalExcel) {
        this.cantidadTotalExcel = cantidadTotalExcel;
    }

    public Double getCantidadPlanillaDinero() {
        return cantidadPlanillaDinero;
    }

    public void setCantidadPlanillaDinero(Double cantidadPlanillaDinero) {
        this.cantidadPlanillaDinero = cantidadPlanillaDinero;
    }

    public Integer getCantidadProcesadosVista() {
        return cantidadProcesadosVista;
    }

    public void setCantidadProcesadosVista(Integer cantidadProcesadosVista) {
        this.cantidadProcesadosVista = cantidadProcesadosVista;
    }

    public String getFechaSeleccionada() {
        return fechaSeleccionada;
    }

    public void setFechaSeleccionada(String fechaSeleccionada) {
        this.fechaSeleccionada = fechaSeleccionada;
    }

    public PlanillaCtsBean getPlanillaCtsBean() {
        if (planillaCtsBean == null) {
            planillaCtsBean = new PlanillaCtsBean();
        }
        return planillaCtsBean;
    }

    public void setPlanillaCtsBean(PlanillaCtsBean planillaCtsBean) {
        this.planillaCtsBean = planillaCtsBean;
    }

    public AsientoBean getAsientoBean() {
        if (asientoBean == null) {
            asientoBean = new AsientoBean();
        }
        return asientoBean;
    }

    public void setAsientoBean(AsientoBean asientoBean) {
        this.asientoBean = asientoBean;
    }

    public List<PlanillaCtsBean> getListaPlanillaCtsBean() {
        if (listaPlanillaCtsBean == null) {
            listaPlanillaCtsBean = new ArrayList<>();
        }
        return listaPlanillaCtsBean;
    }

    public void setListaPlanillaCtsBean(List<PlanillaCtsBean> listaPlanillaCtsBean) {
        this.listaPlanillaCtsBean = listaPlanillaCtsBean;
    }

    public List<PlanillaCtsBean> getListaPlanillaCtsNoProcesadoBean() {
        if (listaPlanillaCtsNoProcesadoBean == null) {
            listaPlanillaCtsNoProcesadoBean = new ArrayList<>();
        }
        return listaPlanillaCtsNoProcesadoBean;
    }

    public void setListaPlanillaCtsNoProcesadoBean(List<PlanillaCtsBean> listaPlanillaCtsNoProcesadoBean) {
        this.listaPlanillaCtsNoProcesadoBean = listaPlanillaCtsNoProcesadoBean;
    }

    public List<PlanillaCtsNoProcesadosBean> getListaPlanillaCtsNoProcesadoBean2() {
        if (listaPlanillaCtsNoProcesadoBean2 == null) {
            listaPlanillaCtsNoProcesadoBean2 = new ArrayList<>();
        }
        return listaPlanillaCtsNoProcesadoBean2;
    }

    public void setListaPlanillaCtsNoProcesadoBean2(List<PlanillaCtsNoProcesadosBean> listaPlanillaCtsNoProcesadoBean2) {
        this.listaPlanillaCtsNoProcesadoBean2 = listaPlanillaCtsNoProcesadoBean2;
    }

    public List<MesBean> getListaMesAllPlanilla() {
        if (listaMesAllPlanilla == null) {
            listaMesAllPlanilla = new ArrayList<>();
        }
        return listaMesAllPlanilla;
    }

    public void setListaMesAllPlanilla(List<MesBean> listaMesAllPlanilla) {
        this.listaMesAllPlanilla = listaMesAllPlanilla;
    }

    public Map<String, Integer> getListaReporte() {
        return listaReporte;
    }

    public void setListaReporte(Map<String, Integer> listaReporte) {
        this.listaReporte = listaReporte;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer[] getSelectedIdTipoAcceso() {
        return selectedIdTipoAcceso;
    }

    public void setSelectedIdTipoAcceso(Integer[] selectedIdTipoAcceso) {
        this.selectedIdTipoAcceso = selectedIdTipoAcceso;
    }

    public List<CodigoBean> getListaNivelIns() {
        if (listaNivelIns == null) {
            listaNivelIns = new ArrayList<>();
        }
        return listaNivelIns;
    }

    public void setListaNivelIns(List<CodigoBean> listaNivelIns) {
        this.listaNivelIns = listaNivelIns;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }
}

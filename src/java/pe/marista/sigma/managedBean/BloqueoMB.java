package pe.marista.sigma.managedBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.BloqueoBean;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.EstudianteBloqueoBean;
import pe.marista.sigma.bean.PersonalBean;
import pe.marista.sigma.bean.TipoCodigoBean;
import pe.marista.sigma.bean.UniNegUniOrgBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.factory.BeanFactory;
import pe.marista.sigma.service.BloqueoService;
import pe.marista.sigma.service.CodigoService;
import pe.marista.sigma.service.EstudianteBloqueoService;
import pe.marista.sigma.service.EstudianteService;
import pe.marista.sigma.service.LegajoService;
import pe.marista.sigma.service.PersonalService;
import pe.marista.sigma.util.GLTLog;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajePrime;

public class BloqueoMB implements Serializable {

    @PostConstruct
    public void BloqueoMB() {
        try {
            getBloqueoBean();
            getBloqueoUploadBean();
            getPersonalFiltroBean();
            getEstudianteBloqueoBean();
            usuarioBean = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
            bloqueoUploadBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            bloqueoUploadBean.setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco));
            estudianteBloqueoBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            EstudianteBloqueoService estudianteBloqueoService = BeanFactory.getEstudianteBloqueoService();
            listaEstudianteBloqueoBean = estudianteBloqueoService.obtenerBloqueoFiltro(estudianteBloqueoBean);
            getTipoBloqueo();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //CLASES BLOQUEO
    private UsuarioBean usuarioBean;
    private BloqueoBean bloqueoBean;
    private PersonalBean personalFiltroBean;
    private EstudianteBloqueoBean estudianteBloqueoBean;

    //CLASES UPLOAD BLOQUEO
    private BloqueoBean bloqueoUploadBean;
    private List<List<Object>> listaObjectPlanilla;
    private List<PersonalBean> listaPersonalBean;

    //VARIABLES UPLOAD BLOQUEO
    private Integer row = 0;
    private Integer col = 0;
    private Integer tipoBloqueo;

    //LISTAS
    private List<BloqueoBean> listaBloqueoBean;
    private List<CodigoBean> listaTipoEstadoEstudiante;
    private List<UniNegUniOrgBean> listaUniNegUniOrgBean;
    private List<EstudianteBloqueoBean> listaEstudianteBloqueoBean;

    //CONFIGURACION DE BLOQUEO =============================================================================================
    public void cargarDatos() {
        try {
            bloqueoBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            bloqueoBean.setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco));
            CodigoService codigoService = BeanFactory.getCodigoService();
            getListaTipoEstadoEstudiante();
            listaTipoEstadoEstudiante = codigoService.funcionObtenerPorTipo(new TipoCodigoBean(MaristaConstantes.TIP_STATUS_BLOQUEO_EST));
            getPersonalFiltroBean();
            personalFiltroBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            obtener();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtener() {
        try {
            BloqueoService bloqueoService = BeanFactory.getBloqueoService();
            listaBloqueoBean = bloqueoService.obtener(bloqueoBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void obtenerPorId(Object object) {
        try {
            bloqueoBean = (BloqueoBean) object;
            BloqueoService bloqueoService = BeanFactory.getBloqueoService();
            bloqueoBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            bloqueoBean = bloqueoService.obtenerPorId(bloqueoBean);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void guardar() {
        try {
            if (bloqueoBean.getIdBloqueo() == null) {
                insertar();
            } else {
                modificar();
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void insertar() {
        try {
            BloqueoService bloqueoService = BeanFactory.getBloqueoService();
            bloqueoBean.setCreapor(usuarioBean.getUsuario());
            bloqueoService.insertar(bloqueoBean);
            obtener();
            limpiarBloqueo();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void modificar() {
        try {
            BloqueoService bloqueoService = BeanFactory.getBloqueoService();
            bloqueoBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            bloqueoBean.setModiPor(usuarioBean.getUsuario());
            bloqueoService.modificar(bloqueoBean);
            obtener();
            limpiarBloqueo();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void eliminar() {
        try {
            BloqueoService bloqueoService = BeanFactory.getBloqueoService();
            bloqueoBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            bloqueoService.eliminar(bloqueoBean);
            obtener();
            limpiarBloqueo();
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarBloqueo() {
        try {
            bloqueoBean = new BloqueoBean();
            cargarDatos();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    /* FILTRO PERSONAL */
    public void obtenerFiltroPersonal() {
        try {
            int estado = 0;
            LegajoService legajoService = BeanFactory.getLegajoService();
            if (personalFiltroBean.getCodPer() != null && !personalFiltroBean.getCodPer().equals("")) {
                personalFiltroBean.setCodPer(personalFiltroBean.getCodPer().toUpperCase().trim());
                estado = 1;
            }
            if (personalFiltroBean.getApepat() != null && !personalFiltroBean.getApepat().equals("")) {
                personalFiltroBean.setApepat(personalFiltroBean.getApepat().toUpperCase().trim());
                estado = 1;
            }
            if (personalFiltroBean.getApemat() != null && !personalFiltroBean.getApemat().equals("")) {
                personalFiltroBean.setApemat(personalFiltroBean.getApemat().toUpperCase().trim());
                estado = 1;
            }
            if (personalFiltroBean.getNombre() != null && !personalFiltroBean.getNombre().equals("")) {
                personalFiltroBean.setNombre(personalFiltroBean.getNombre().toUpperCase().trim());
                estado = 1;
            }
            if (personalFiltroBean.getUnidadOrganicaBean().getIdUniOrg() != null && !personalFiltroBean.getUnidadOrganicaBean().getIdUniOrg().equals(0)) {
                personalFiltroBean.getUnidadOrganicaBean().setIdUniOrg(personalFiltroBean.getUnidadOrganicaBean().getIdUniOrg());
                estado = 1;
            } else if (estado == 0) {
                new MensajePrime().addInformativeMessageFilterField("informacionEtiqueta");
                listaPersonalBean = new ArrayList<>();
            }
            if (estado == 1) {
                listaPersonalBean = legajoService.obtenerFiltroPersonal(personalFiltroBean);
                if (listaPersonalBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarPersonalFiltro() {
        try {
            personalFiltroBean = new PersonalBean();
            personalFiltroBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            listaPersonalBean = new ArrayList<>();
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerPersonal(Object object) {
        try {
            personalFiltroBean = (PersonalBean) object;
            PersonalService personalService = BeanFactory.getPersonalService();
            personalFiltroBean = personalService.buscarPorId(personalFiltroBean.getIdPersonal());
            bloqueoBean.getPersonalBean().setIdPersonal(personalFiltroBean.getIdPersonal());
            bloqueoBean.getPersonalBean().setNombreCompleto(personalFiltroBean.getNombreCompleto());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

//    public void actualizarTablaBloqueo(EstudianteBloqueoBean bloqueoBean, Integer n) {
//        try {
//            EstudianteBloqueoService estudianteBloqueoService = BeanFactory.getEstudianteBloqueoService();
//            CodigoService codigoService = BeanFactory.getCodigoService();
//            bloqueoBean.setUnidadNegocioBean(usuarioBean.getPersonalBean().getUnidadNegocioBean());
//            bloqueoBean.getEstudianteBean().setIdEstudiante(estudianteBean.getPersonaBean().getIdPersona());
//            Integer id = 0;
//            id = bloqueoBean.getTipoStatusBloqueoBean().getIdCodigo();
//            if (id == 20901) {
//                bloqueoBean.getTipoStatusBloqueoBean().setCodigo(MaristaConstantes.bloqueo_Activo);
//            }
//            if (id == 20902) {
//                bloqueoBean.getTipoStatusBloqueoBean().setCodigo(MaristaConstantes.bloqueo_Resuelto);
//            }
//            bloqueoBean.setModiPor(usuarioBean.getUsuario());
//            estudianteBloqueoService.actualizar(bloqueoBean, listaEstudianteBloqueoBean, estudianteBean);
//            listaEstudianteBloqueoBean = new ArrayList<>();
//            listaEstudianteBloqueoBean = estudianteBloqueoService.obtenerBloqueoPorEstudiantes(bloqueoBean);
//        } catch (Exception ex) {
//            new MensajePrime().addErrorGeneralMessage();
//            GLTLog.writeError(this.getClass(), ex);
//        }
//    }
    //===================================================================================================
    //UPLOAD BLOQUEO=====================================================================================
    public void cargarDatosUpload() {
        try {
            bloqueoUploadBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            bloqueoUploadBean.setAnio(Integer.parseInt(MaristaConstantes.Anios_Banco));
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void cargarCsv(FileUploadEvent event) {
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void copyFile(String fileName, InputStream in) {
        try {
//            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            BloqueoService bloqueoService = BeanFactory.getBloqueoService();
            String ip = bloqueoService.obtenerIpServer(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            String destination = "\\\\\\\\" + ip + "\\\\" + usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg() + "\\\\BLOQUEOS\\\\";
            OutputStream out = new FileOutputStream(new File(destination + fileName));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            System.out.println("destino >>>" + destination);
            System.out.println("El archivo se ha creado con éxito!");
            bloqueoUploadBean.setObjFile(fileName);
            if (getTipoBloqueo().equals(1)) {
                estudianteService.execProBloqueo(bloqueoUploadBean);
            } else if (getTipoBloqueo().equals(0)) {
                estudianteService.execProBloqueoLibera(bloqueoUploadBean);
            }
            RequestContext.getCurrentInstance().addCallbackParam("operacionCorrecta", true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void cargarBloqueos(FileUploadEvent event) {
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
            listaObjectPlanilla = listaMapa(listaData);
            System.out.println(">>>>" + listaObjectPlanilla.size());
            System.out.println(">>>>" + sheet.getRow(0).getPhysicalNumberOfCells());
            Integer personal = 1, id = 0, valor = 0;
            if (!listaObjectPlanilla.isEmpty()) {
                for (int i = 0; i < row; i++) {
                    if (valor <= sheet.getRow(0).getPhysicalNumberOfCells()) {
                        obtenerTipoColumna(valor, listaObjectPlanilla.get(i));
                        valor++;
                    }
                }
            } else {
                System.out.println(">>> vacio");
            }
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
                    String cellValue = cellTemp.get(j).toString();
                    listaGeo.add(cellValue);
                }
                listaGenGeo.add(listaGeo);
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
        return listaGenGeo;
    }

    public void obtenerTipoColumna(Integer posicion, List<Object> listaObject) {
        try {
            List<BloqueoBean> listaBloq = new ArrayList<>();
            BloqueoService bloqueoService = BeanFactory.getBloqueoService();
            listaBloq = bloqueoService.obtenerBloqueoAnio(bloqueoUploadBean);
            EstudianteService estudianteService = BeanFactory.getEstudianteService();
            EstudianteBean estudiante = new EstudianteBean();
            EstudianteBloqueoBean estudianteBloqueoBean = new EstudianteBloqueoBean();
            estudiante.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            estudiante.setCodigo(listaObject.get(posicion - 1).toString().replace(".0", ""));
            estudiante = estudianteService.obtenerEstPorCodigo(estudiante);
            estudianteBloqueoBean.getEstudianteBean().setIdEstudiante(estudiante.getIdEstudiante());
            estudianteBloqueoBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
            if (!listaBloq.isEmpty()) {
                for (int i = 0; i < listaBloq.size(); i++) {
                    if (listaBloq.get(i).getPosicion().equals(posicion + 1)) {
                        System.out.println(">>>>" + listaObject.get(posicion));
                    }
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void filtrarBloqueosEstudiante() {
        try {
            int res = 0;
            getEstudianteBloqueoBean();
            if (!getEstudianteBloqueoBean().getEstudianteBean().getCodigo().equals("") && getEstudianteBloqueoBean().getEstudianteBean().getCodigo() != null) {
                getEstudianteBloqueoBean().getEstudianteBean().setCodigo(getEstudianteBloqueoBean().getEstudianteBean().getCodigo());
                res = 1;
            }
            if (!getEstudianteBloqueoBean().getEstudianteBean().getIdEstudiante().equals("") && getEstudianteBloqueoBean().getEstudianteBean().getIdEstudiante() != null) {
                getEstudianteBloqueoBean().getEstudianteBean().setIdEstudiante(getEstudianteBloqueoBean().getEstudianteBean().getIdEstudiante());
                res = 1;
            }
            if (!getEstudianteBloqueoBean().getEstudianteBean().getPersonaBean().getNombreCompleto().equals("") && getEstudianteBloqueoBean().getEstudianteBean().getPersonaBean().getNombreCompleto() != null) {
                getEstudianteBloqueoBean().getEstudianteBean().getPersonaBean().setNombreCompleto(getEstudianteBloqueoBean().getEstudianteBean().getPersonaBean().getNombreCompleto());
                res = 1;
            }
            if (res == 1) {
                EstudianteBloqueoService estudianteBloqueoService = BeanFactory.getEstudianteBloqueoService();
                listaEstudianteBloqueoBean = estudianteBloqueoService.obtenerFiltroEstudianteMasivo(getEstudianteBloqueoBean());
                if (listaEstudianteBloqueoBean.isEmpty()) {
                    new MensajePrime().addInformativeMessageSearch("informacionEtiqueta");
                    listaEstudianteBloqueoBean = new ArrayList<>();
                }
            }
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void limpiarBloqueosEstudiante() {
        try {
            estudianteBloqueoBean = new EstudianteBloqueoBean();
            estudianteBloqueoBean.getUnidadNegocioBean().setUniNeg(usuarioBean.getPersonalBean().getUnidadNegocioBean().getUniNeg());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    public void ponerTipoBloqueo() {
        try {
            System.out.println(">>>" + getTipoBloqueo());
        } catch (Exception ex) {
            new MensajePrime().addErrorGeneralMessage();
            GLTLog.writeError(this.getClass(), ex);
        }
    }

    //===================================================================================================
    public UsuarioBean getUsuarioBean() {
        if (usuarioBean == null) {
            usuarioBean = new UsuarioBean();
        }
        return usuarioBean;
    }

    public void setUsuarioBean(UsuarioBean usuarioBean) {
        this.usuarioBean = usuarioBean;
    }

    public BloqueoBean getBloqueoBean() {
        if (bloqueoBean == null) {
            bloqueoBean = new BloqueoBean();
        }
        return bloqueoBean;
    }

    public void setBloqueoBean(BloqueoBean bloqueoBean) {
        this.bloqueoBean = bloqueoBean;
    }

    public List<BloqueoBean> getListaBloqueoBean() {
        if (listaBloqueoBean == null) {
            listaBloqueoBean = new ArrayList<>();
        }
        return listaBloqueoBean;
    }

    public void setListaBloqueoBean(List<BloqueoBean> listaBloqueoBean) {
        this.listaBloqueoBean = listaBloqueoBean;
    }

    public BloqueoBean getBloqueoUploadBean() {
        if (bloqueoUploadBean == null) {
            bloqueoUploadBean = new BloqueoBean();
        }
        return bloqueoUploadBean;
    }

    public void setBloqueoUploadBean(BloqueoBean bloqueoUploadBean) {
        this.bloqueoUploadBean = bloqueoUploadBean;
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

    public List<CodigoBean> getListaTipoEstadoEstudiante() {
        if (listaTipoEstadoEstudiante == null) {
            listaTipoEstadoEstudiante = new ArrayList<>();
        }
        return listaTipoEstadoEstudiante;
    }

    public void setListaTipoEstadoEstudiante(List<CodigoBean> listaTipoEstadoEstudiante) {
        this.listaTipoEstadoEstudiante = listaTipoEstadoEstudiante;
    }

    public PersonalBean getPersonalFiltroBean() {
        if (personalFiltroBean == null) {
            personalFiltroBean = new PersonalBean();
        }
        return personalFiltroBean;
    }

    public void setPersonalFiltroBean(PersonalBean personalFiltroBean) {
        this.personalFiltroBean = personalFiltroBean;
    }

    public List<UniNegUniOrgBean> getListaUniNegUniOrgBean() {
        if (listaUniNegUniOrgBean == null) {
            listaUniNegUniOrgBean = new ArrayList<>();
        }
        return listaUniNegUniOrgBean;
    }

    public void setListaUniNegUniOrgBean(List<UniNegUniOrgBean> listaUniNegUniOrgBean) {
        this.listaUniNegUniOrgBean = listaUniNegUniOrgBean;
    }

    public List<PersonalBean> getListaPersonalBean() {
        if (listaPersonalBean == null) {
            listaPersonalBean = new ArrayList<>();
        }
        return listaPersonalBean;
    }

    public void setListaPersonalBean(List<PersonalBean> listaPersonalBean) {
        this.listaPersonalBean = listaPersonalBean;
    }

    public EstudianteBloqueoBean getEstudianteBloqueoBean() {
        if (estudianteBloqueoBean == null) {
            estudianteBloqueoBean = new EstudianteBloqueoBean();
        }
        return estudianteBloqueoBean;
    }

    public void setEstudianteBloqueoBean(EstudianteBloqueoBean estudianteBloqueoBean) {
        this.estudianteBloqueoBean = estudianteBloqueoBean;
    }

    public List<EstudianteBloqueoBean> getListaEstudianteBloqueoBean() {
        if (listaEstudianteBloqueoBean == null) {
            listaEstudianteBloqueoBean = new ArrayList<>();
        }
        return listaEstudianteBloqueoBean;
    }

    public void setListaEstudianteBloqueoBean(List<EstudianteBloqueoBean> listaEstudianteBloqueoBean) {
        this.listaEstudianteBloqueoBean = listaEstudianteBloqueoBean;
    }

    public Integer getTipoBloqueo() {
        return tipoBloqueo;
    }

    public void setTipoBloqueo(Integer tipoBloqueo) {
        this.tipoBloqueo = tipoBloqueo;
    }

}

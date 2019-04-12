/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author MS002
 */
public class ProcesoFilesBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private EntidadBean entidadBean;
    private Integer idFile;
    private Integer idFilePadre;
    private CodigoBean tipoFile;
    private Integer posicionItem;
    private Integer posicionIni;
    private Integer posicionFin;
    private Integer longitud;
    private CodigoBean tipoDato;
    private String nombre;
    private String descripcion;
    private Integer estado;
    private Integer flgProceso;
    private Integer validate;
    private Integer idDefecto;
    private String creaPor;
    private String modiPor;
    private Date creaFecha;
    private String modiVer;

    private String nomDefecto;

    private String nomColumna;

    private Boolean ayudaBanco;

    //Columnas Nuevas
    private Integer posicionValor = 2;
    private Integer tipoValorEstado = 2;
    private String constante;
    private Boolean posicionValorItem = true;
    private Integer complemento = 2;
    private String valorComplemento;

    //Ayuda
    private String alineacion;
    private String valoracion;
    private String valorCampo;
    private String formula;
    private String valor;

    public ProcesoFilesBean(int i, int i0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //Ayuda
    private Boolean disabled;
    private String columna = "";
    private String columnaCab;
    private Integer dato;
    private Integer file;

    //new
    private Boolean flgConsiderarPunto = false;
    private String flgConsiderarPuntoVista;
    private Boolean flgRestarMora = false;
    private String flgRestarMoraVista;

    public ProcesoFilesBean() {
    }

    public ProcesoFilesBean(Integer posicionIni, Integer posicionFin) {
        this.posicionIni = posicionIni;
        this.posicionFin = posicionFin;
    }

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public EntidadBean getEntidadBean() {
        if (entidadBean == null) {
            entidadBean = new EntidadBean();
        }
        return entidadBean;
    }

    public void setEntidadBean(EntidadBean entidadBean) {
        this.entidadBean = entidadBean;
    }

    public Integer getIdFile() {
        return idFile;
    }

    public void setIdFile(Integer idFile) {
        this.idFile = idFile;
    }

    public Integer getIdFilePadre() {
        return idFilePadre;
    }

    public void setIdFilePadre(Integer idFilePadre) {
        this.idFilePadre = idFilePadre;
    }

    public Integer getPosicionIni() {
        return posicionIni;
    }

    public void setPosicionIni(Integer posicionIni) {
        this.posicionIni = posicionIni;
    }

    public Integer getPosicionFin() {
        return posicionFin;
    }

    public void setPosicionFin(Integer posicionFin) {
        this.posicionFin = posicionFin;
    }

    public Integer getLongitud() {
        return longitud;
    }

    public void setLongitud(Integer longitud) {
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public String getModiVer() {
        return modiVer;
    }

    public void setModiVer(String modiVer) {
        this.modiVer = modiVer;
    }

    public CodigoBean getTipoFile() {
        if (tipoFile == null) {
            tipoFile = new CodigoBean();
        }
        return tipoFile;
    }

    public void setTipoFile(CodigoBean tipoFile) {
        this.tipoFile = tipoFile;
    }

    public Integer getPosicionItem() {
        return posicionItem;
    }

    public void setPosicionItem(Integer posicionItem) {
        this.posicionItem = posicionItem;
    }

    public Integer getFlgProceso() {
        return flgProceso;
    }

    public void setFlgProceso(Integer flgProceso) {
        this.flgProceso = flgProceso;
    }

    public CodigoBean getTipoDato() {
        if (tipoDato == null) {
            tipoDato = new CodigoBean();
        }
        return tipoDato;
    }

    public void setTipoDato(CodigoBean tipoDato) {
        this.tipoDato = tipoDato;
    }

    public Integer getValidate() {
        return validate;
    }

    public void setValidate(Integer validate) {
        this.validate = validate;
    }

    public Boolean getDisabled() {
        if (validate != null) {
            if (validate == 1) {
                disabled = true;
            } else {
                if (validate == 0) {
                    disabled = false;
                }
            }
        }
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

    public String getColumnaCab() {
        return columnaCab;
    }

    public void setColumnaCab(String columnaCab) {
        this.columnaCab = columnaCab;
    }

    public Integer getDato() {
        return dato;
    }

    public void setDato(Integer dato) {
        this.dato = dato;
    }

    public Integer getIdDefecto() {
        return idDefecto;
    }

    public void setIdDefecto(Integer idDefecto) {
        this.idDefecto = idDefecto;
    }

    public Integer getFile() {
        return file;
    }

    public void setFile(Integer file) {
        this.file = file;
    }

    public String getNomDefecto() {
        return nomDefecto;
    }

    public void setNomDefecto(String nomDefecto) {
        this.nomDefecto = nomDefecto;
    }

    public String getNomColumna() {
        return nomColumna;
    }

    public void setNomColumna(String nomColumna) {
        this.nomColumna = nomColumna;
    }

    public Boolean getAyudaBanco() {
        return ayudaBanco;
    }

    public void setAyudaBanco(Boolean ayudaBanco) {
        this.ayudaBanco = ayudaBanco;
    }

    public Integer getPosicionValor() {
        return posicionValor;
    }

    public void setPosicionValor(Integer posicionValor) {
        this.posicionValor = posicionValor;
    }

    public Integer getTipoValorEstado() {
        return tipoValorEstado;
    }

    public void setTipoValorEstado(Integer tipoValorEstado) {
        this.tipoValorEstado = tipoValorEstado;
    }

    public String getConstante() {
        return constante;
    }

    public void setConstante(String constante) {
        this.constante = constante;
    }

    public Boolean getPosicionValorItem() {
        return posicionValorItem;
    }

    public void setPosicionValorItem(Boolean posicionValorItem) {
        this.posicionValorItem = posicionValorItem;
    }

    public String getAlineacion() {
        return alineacion;
    }

    public void setAlineacion(String alineacion) {
        this.alineacion = alineacion;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }

    public Integer getComplemento() {
        return complemento;
    }

    public void setComplemento(Integer complemento) {
        this.complemento = complemento;
    }

    public String getValorComplemento() {
        return valorComplemento;
    }

    public void setValorComplemento(String valorComplemento) {
        this.valorComplemento = valorComplemento;
    }

    public String getValorCampo() {
        return valorCampo;
    }

    public void setValorCampo(String valorCampo) {
        this.valorCampo = valorCampo;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Boolean getFlgConsiderarPunto() {
        return flgConsiderarPunto;
    }

    public void setFlgConsiderarPunto(Boolean flgConsiderarPunto) {
        this.flgConsiderarPunto = flgConsiderarPunto;
    }

    public String getFlgConsiderarPuntoVista() {
        return flgConsiderarPuntoVista;
    }

    public void setFlgConsiderarPuntoVista(String flgConsiderarPuntoVista) {
        this.flgConsiderarPuntoVista = flgConsiderarPuntoVista;
    }

    public Boolean getFlgRestarMora() {
        return flgRestarMora;
    }

    public void setFlgRestarMora(Boolean flgRestarMora) {
        this.flgRestarMora = flgRestarMora;
    }

    public String getFlgRestarMoraVista() {
        return flgRestarMoraVista;
    }

    public void setFlgRestarMoraVista(String flgRestarMoraVista) {
        this.flgRestarMoraVista = flgRestarMoraVista;
    }

}

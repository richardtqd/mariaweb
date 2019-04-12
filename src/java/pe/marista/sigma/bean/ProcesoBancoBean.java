/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author MS-005
 */
public class ProcesoBancoBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private Integer idProcesoBanco;
    private Integer anio;
    private String nombre;
    private Date fecha;
    private Integer regProc;
    private Integer regEnv;
    private Integer regError;
    private Float montoEnv;
    private Float montoRecup;
    private EntidadBean entidadBean;//ruc
    private Integer flgProceso;
    private String codUniNeg;//Integer
    private String numCuenta;
    private Boolean moneda;
    private String tipoArchivo;
//    private Time horaCorte;
    private Date horaCorte;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;
    private CuentaBancoBean cuentaBancoBean;
    private String ruc;

    //Ayuda
    private String idMoneda;
    private Integer codMoneda = 0;
    private String statusProceso;
    private String mes;
    private String dia;
    private String idMes;
    private Boolean flgEnvio;
    private String datoProceso;
    private String operacion;

    private List<Object> listaProcesoBanco;

    //Campo Nuevo
    private Integer tipoEnvio;
    private Integer mespro;
    private Integer diaPro;

    //Ayuda
    private Date fechaInicio;
    private Date fechaFin;
    private Integer estado;
    private String cuota;
    private Integer tipoMoneda;
    private Integer tipoOperacion;
    private String fileObjeto;

    private Date fechaVista;
    //PROCESOS
    private ProcesoEnvioBean procesoEnvioBean;

    //reporte 
    private Integer regProcesados;
    private String fechaOpBco;

    private Double totalMora;
    private Double totalRecaudado;

    public String getIdMes() {
        if (mes.equals("1")) {
            idMes = "Enero";
        }
        if (mes.equals("2")) {
            idMes = "Febrero";
        }
        if (mes.equals("3")) {
            idMes = "Marzo";
        }
        if (mes.equals("4")) {
            idMes = "Abril";
        }
        if (mes.equals("5")) {
            idMes = "Mayo";
        }
        if (mes.equals("6")) {
            idMes = "Junio";
        }
        if (mes.equals("7")) {
            idMes = "Julio";
        }
        if (mes.equals("8")) {
            idMes = "Agosto";
        }
        if (mes.equals("9")) {
            idMes = "Setiembre";
        }
        if (mes.equals("10")) {
            idMes = "Octubre";
        }
        if (mes.equals("11")) {
            idMes = "Noviembre";
        }
        if (mes.equals("12")) {
            idMes = "Diciembre";
        }
        return idMes;
    }

    public void setIdMes(String idMes) {
        this.idMes = idMes;
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

    public Integer getIdProcesoBanco() {
        return idProcesoBanco;
    }

    public void setIdProcesoBanco(Integer idProcesoBanco) {
        this.idProcesoBanco = idProcesoBanco;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getRegErrror() {
        return regError;
    }

    public void setRegErrror(Integer regError) {
        this.regError = regError;
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

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public String getModiVer() {
        return modiVer;
    }

    public void setModiVer(String modiVer) {
        this.modiVer = modiVer;
    }

    public Integer getRegProc() {
        return regProc;
    }

    public void setRegProc(Integer regProc) {
        this.regProc = regProc;
    }

    public Integer getRegError() {
        return regError;
    }

    public void setRegError(Integer regError) {
        this.regError = regError;
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public Integer getRegEnv() {
        return regEnv;
    }

    public void setRegEnv(Integer regEnv) {
        this.regEnv = regEnv;
    }

    public Float getMontoEnv() {
        return montoEnv;
    }

    public void setMontoEnv(Float montoEnv) {
        this.montoEnv = montoEnv;
    }

    public Float getMontoRecup() {
        return montoRecup;
    }

    public void setMontoRecup(Float montoRecup) {
        this.montoRecup = montoRecup;
    }

    public String getCodUniNeg() {
        return codUniNeg;
    }

    public void setCodUniNeg(String codUniNeg) {
        this.codUniNeg = codUniNeg;
    }

    public Boolean getMoneda() {
        if (moneda != null) {
            if (moneda.equals(true)) {
                codMoneda = 1;
            } else {
                codMoneda = 0;
            }
        }
        return moneda;
    }

    public void setMoneda(Boolean moneda) {
        this.moneda = moneda;
    }

//    public Time getHoraCorte() {
//        return horaCorte;
//    }
//
//    public void setHoraCorte(Time horaCorte) {
//        this.horaCorte = horaCorte;
//    }
    public String getIdMoneda() {
        if (moneda != null) {
            if (moneda == true) {
                idMoneda = "Dolares";
                return idMoneda;
            } else {
                idMoneda = "Soles";
                return idMoneda;
            }
        }
        return idMoneda;
    }

    public void setIdMoneda(String idMoneda) {
        this.idMoneda = idMoneda;
    }

    public String getStatusProceso() {
        if (flgProceso == 1) {
            statusProceso = "Activo";
        } else {
            statusProceso = "Inactivo";
        }
        return statusProceso;
    }

    public void setStatusProceso(String statusProceso) {
        this.statusProceso = statusProceso;
    }

    public CuentaBancoBean getCuentaBancoBean() {
        if (cuentaBancoBean == null) {
            cuentaBancoBean = new CuentaBancoBean();
        }
        return cuentaBancoBean;
    }

    public void setCuentaBancoBean(CuentaBancoBean cuentaBancoBean) {
        this.cuentaBancoBean = cuentaBancoBean;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Boolean getFlgEnvio() {
        return flgEnvio;
    }

    public void setFlgEnvio(Boolean flgEnvio) {
        this.flgEnvio = flgEnvio;
    }

    public String getDatoProceso() {
        if (datoProceso == null) {
            datoProceso = "";
            if (flgProceso == 1) {
                datoProceso = "Envio";
            } else {
                datoProceso = "Recuperación";
            }
        }
        return datoProceso;
    }

    public void setDatoProceso(String datoProceso) {
        this.datoProceso = datoProceso;
    }

    public Integer getFlgProceso() {
        return flgProceso;
    }

    public void setFlgProceso(Integer flgProceso) {
        this.flgProceso = flgProceso;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Integer getCodMoneda() {
        return codMoneda;
    }

    public void setCodMoneda(Integer codMoneda) {
        this.codMoneda = codMoneda;
    }

    public Date getHoraCorte() {
        return horaCorte;
    }

    public void setHoraCorte(Date horaCorte) {
        this.horaCorte = horaCorte;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }

    public List<Object> getListaProcesoBanco() {
        if (listaProcesoBanco == null) {
            listaProcesoBanco = new ArrayList<>();
//            listaProcesoBanco.add(this.getCodUniNeg());
//            listaProcesoBanco.add(this.getMoneda());
//            listaProcesoBanco.add(this.getNumCuenta());
//            listaProcesoBanco.add(this.getTipoArchivo());
//            listaProcesoBanco.add(this.getFecha());
//            listaProcesoBanco.add(this.getRegEnv());
//            listaProcesoBanco.add(this.getMontoRecup());
        }
        return listaProcesoBanco;
    }

    public void setListaProcesoBanco(List<Object> listaProcesoBanco) {
        this.listaProcesoBanco = listaProcesoBanco;
    }

    public Integer getTipoEnvio() {
        return tipoEnvio;
    }

    public void setTipoEnvio(Integer tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
    }

    public Integer getMespro() {
        return mespro;
    }

    public void setMespro(Integer mespro) {
        this.mespro = mespro;
    }

    public Integer getDiaPro() {
        return diaPro;
    }

    public void setDiaPro(Integer diaPro) {
        this.diaPro = diaPro;
    }

    public Date getFechaInicio() {
        if (fechaInicio == null) {
            fechaInicio = new Date();
        }
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        if (fechaFin == null) {
            fechaFin = new Date();
        }
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getCuota() {
        return cuota;
    }

    public void setCuota(String cuota) {
        this.cuota = cuota;
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

    public Integer getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(Integer tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public Integer getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(Integer tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public String getFileObjeto() {
        return fileObjeto;
    }

    public void setFileObjeto(String fileObjeto) {
        this.fileObjeto = fileObjeto;
    }

    public Date getFechaVista() {
        return fechaVista;
    }

    public void setFechaVista(Date fechaVista) {
        this.fechaVista = fechaVista;
    }

    public Integer getRegProcesados() {
        return regProcesados;
    }

    public void setRegProcesados(Integer regProcesados) {
        this.regProcesados = regProcesados;
    }

    public String getFechaOpBco() {
        return fechaOpBco;
    }

    public void setFechaOpBco(String fechaOpBco) {
        this.fechaOpBco = fechaOpBco;
    }

    public Double getTotalMora() {
        return totalMora;
    }

    public void setTotalMora(Double totalMora) {
        this.totalMora = totalMora;
    }

    public Double getTotalRecaudado() {
        return totalRecaudado;
    }

    public void setTotalRecaudado(Double totalRecaudado) {
        this.totalRecaudado = totalRecaudado;
    }

}

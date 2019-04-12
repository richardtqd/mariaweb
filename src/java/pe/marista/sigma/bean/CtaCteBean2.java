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
 * @author Administrador
 */
public class CtaCteBean2 implements Serializable{
    private String idCtaCte;
    private String nomEstudiante; //idEstudiante
    private String codigo = "9999999";
    private String seccion;
    private Date fechaPago;
    //
    private String beca;
    private String servicio;
    private String monto; 
    private String importe;
    private String mora1;
    
    private String pagoPar;
    private Date fechaCan;
    private String deuda;
    private String mora2;
    private String Total;
    
    private Date fechaVenc;
    private Date fecha;
    private String subTotal;  
    private String STdeuda;
    private String STmora;//lugar
    
    private String STtotal;
    private String condonacion;

    public CtaCteBean2() {
       
    }

    public String getIdCtaCte() {
        return idCtaCte;
    }

    public void setIdCtaCte(String idCtaCte) {
        this.idCtaCte = idCtaCte;
    }

    public String getNomEstudiante() {
        return nomEstudiante;
    }

    public void setNomEstudiante(String nomEstudiante) {
        this.nomEstudiante = nomEstudiante;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

  

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getMora1() {
        return mora1;
    }

    public void setMora1(String mora1) {
        this.mora1 = mora1;
    }

    public String getPagoPar() {
        return pagoPar;
    }

    public void setPagoPar(String pagoPar) {
        this.pagoPar = pagoPar;
    }

    public Date getFechaCan() {
        return fechaCan;
    }

    public void setFechaCan(Date fechaCan) {
        this.fechaCan = fechaCan;
    }

    public String getDeuda() {
        return deuda;
    }

    public void setDeuda(String deuda) {
        this.deuda = deuda;
    }

    public String getMora2() {
        return mora2;
    }

    public void setMora2(String mora2) {
        this.mora2 = mora2;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String Total) {
        this.Total = Total;
    }

    public Date getFechaVenc() {
        return fechaVenc;
    }

    public void setFechaVenc(Date fechaVenc) {
        this.fechaVenc = fechaVenc;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getSTdeuda() {
        return STdeuda;
    }

    public void setSTdeuda(String STdeuda) {
        this.STdeuda = STdeuda;
    }

    public String getSTmora() {
        return STmora;
    }

    public void setSTmora(String STmora) {
        this.STmora = STmora;
    }

    public String getSTtotal() {
        return STtotal;
    }

    public void setSTtotal(String STtotal) {
        this.STtotal = STtotal;
    }

    public CtaCteBean2(String idCtaCte, String nomEstudiante, String codigo, String seccion, Date fechaPago, String beca, String servicio, String importe, String monto, String STmora,String mora1,String condonacion,String deuda, String pagoPar, Date fechaCan, String mora2, Date fechaVenc, String subTotal) {
        this.idCtaCte = idCtaCte;
        this.nomEstudiante = nomEstudiante;
        this.codigo = codigo;
        this.seccion = seccion;
        this.fechaPago = fechaPago;
        this.beca = beca;
        this.servicio = servicio;
        this.importe = importe;
        this.monto = monto;
        this.STmora=STmora;
        this.mora1 = mora1;
        this.condonacion=condonacion;
        this.deuda=deuda;
        this.pagoPar = pagoPar;
        this.fechaCan = fechaCan;
        this.mora2 = mora2;
        this.fechaVenc = fechaVenc;
        this.subTotal = subTotal;
    }

     

    public String getBeca() {
        return beca;
    }

    public void setBeca(String beca) {
        this.beca = beca;
    }

    public CtaCteBean2(String idCtaCte, String nomEstudiante, String codigo, String seccion, String beca, String servicio, String monto, Date fechaVenc) {
        this.idCtaCte = idCtaCte;
        this.nomEstudiante = nomEstudiante;
        this.codigo = codigo;
        this.seccion = seccion;
        this.beca = beca;
        this.servicio = servicio;
        this.monto = monto;
        this.fechaVenc = fechaVenc;
    }

    public CtaCteBean2(String codigo, String nomEstudiante,  String seccion) {
       this.codigo = codigo;
        this.nomEstudiante = nomEstudiante;
        
        this.seccion = seccion;
    }

    public CtaCteBean2(String beca,String idCtaCte, String servicio, String monto, String pagoPar, String deuda, String mora2, String subTotal) {
        this.beca = beca;
        this.idCtaCte=idCtaCte;
        this.servicio = servicio;
        this.monto = monto;
        this.pagoPar = pagoPar;
        this.deuda = deuda;
        this.mora2 = mora2;
        this.subTotal = subTotal;
    }

    public String getCondonacion() {
        return condonacion;
    }

    public void setCondonacion(String condonacion) {
        this.condonacion = condonacion;
    }

    public CtaCteBean2(String idCtaCte, String codigo, String servicio, String monto) {
        this.idCtaCte = idCtaCte;
        this.codigo = codigo;
        this.servicio = servicio;
        this.monto = monto;
    }
 
    
    
    
    
    
 
}

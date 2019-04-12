/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;

/**
 *
 * @author MS001
 */
public class DetCobrosPensionesRepBean implements Serializable{ 
    private String label;
    private String codigo;
    private String nombre;
    private String importe;
    private String mora;
    private String total;
    private String sumImporte;
    private String sumMora;
    private String totalFecVenc;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getMora() {
        return mora;
    }

    public void setMora(String mora) {
        this.mora = mora;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSumImporte() {
        return sumImporte;
    }

    public void setSumImporte(String sumImporte) {
        this.sumImporte = sumImporte;
    }

    public String getSumMora() {
        return sumMora;
    }

    public void setSumMora(String sumMora) {
        this.sumMora = sumMora;
    }

    public String getTotalFecVenc() {
        return totalFecVenc;
    }

    public void setTotalFecVenc(String totalFecVenc) {
        this.totalFecVenc = totalFecVenc;
    }
}

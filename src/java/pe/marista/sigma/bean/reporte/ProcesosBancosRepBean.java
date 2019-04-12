/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class ProcesosBancosRepBean implements Serializable {

    private Integer idReporteBanco;
    private String nomUniNeg;
    private String codigo;
    private Integer idGradoAcademico;
    private String nombres;
    private String concepto;
    private String fechaPago;
    private Float monto;
    private Float mora;
    private Integer idTipoLugarPago;
    private String lugarPago;
    private Float totalMatricula;
    private Float totalMarzo;
    private Float totalAbril;
    private Float totalMayo;
    private Float totalJunio;
    private Float totalJulio;
    private Float totalAgosto;
    private Float totalSetiembre;
    private Float totalOctubre;
    private Float totalNoviembre;
    private Float totalDiciembre;

    private String gradoAcademico;
    private Integer idgradoacademico;
    private Integer idtipolugarpago;
    private String tipoLugar;
    private String anio;
    private String refFecha;
    private Float totalImporte;
    private Float totalMora;
    private Integer total;
    private String fechaImp;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getIdGradoAcademico() {
        return idGradoAcademico;
    }

    public void setIdGradoAcademico(Integer idGradoAcademico) {
        this.idGradoAcademico = idGradoAcademico;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public Float getMora() {
        return mora;
    }

    public void setMora(Float mora) {
        this.mora = mora;
    }

    public Integer getIdTipoLugarPago() {
        return idTipoLugarPago;
    }

    public void setIdTipoLugarPago(Integer idTipoLugarPago) {
        this.idTipoLugarPago = idTipoLugarPago;
    }

    public String getLugarPago() {
        return lugarPago;
    }

    public void setLugarPago(String lugarPago) {
        this.lugarPago = lugarPago;
    }

    public Float getTotalMatricula() {
        return totalMatricula;
    }

    public void setTotalMatricula(Float totalMatricula) {
        this.totalMatricula = totalMatricula;
    }

    public Float getTotalAbril() {
        return totalAbril;
    }

    public void setTotalAbril(Float totalAbril) {
        this.totalAbril = totalAbril;
    }

    public Float getTotalMayo() {
        return totalMayo;
    }

    public void setTotalMayo(Float totalMayo) {
        this.totalMayo = totalMayo;
    }

    public Float getTotalJunio() {
        return totalJunio;
    }

    public void setTotalJunio(Float totalJunio) {
        this.totalJunio = totalJunio;
    }

    public Float getTotalJulio() {
        return totalJulio;
    }

    public void setTotalJulio(Float totalJulio) {
        this.totalJulio = totalJulio;
    }

    public Float getTotalAgosto() {
        return totalAgosto;
    }

    public void setTotalAgosto(Float totalAgosto) {
        this.totalAgosto = totalAgosto;
    }

    public Float getTotalSetiembre() {
        return totalSetiembre;
    }

    public void setTotalSetiembre(Float totalSetiembre) {
        this.totalSetiembre = totalSetiembre;
    }

    public Float getTotalOctubre() {
        return totalOctubre;
    }

    public void setTotalOctubre(Float totalOctubre) {
        this.totalOctubre = totalOctubre;
    }

    public Float getTotalNoviembre() {
        return totalNoviembre;
    }

    public void setTotalNoviembre(Float totalNoviembre) {
        this.totalNoviembre = totalNoviembre;
    }

    public Float getTotalDiciembre() {
        return totalDiciembre;
    }

    public void setTotalDiciembre(Float totalDiciembre) {
        this.totalDiciembre = totalDiciembre;
    }

    public Integer getIdReporteBanco() {
        return idReporteBanco;
    }

    public void setIdReporteBanco(Integer idReporteBanco) {
        this.idReporteBanco = idReporteBanco;
    }

    public String getGradoAcademico() {
        return gradoAcademico;
    }

    public void setGradoAcademico(String gradoAcademico) {
        this.gradoAcademico = gradoAcademico;
    }

    public String getNomUniNeg() {
        return nomUniNeg;
    }

    public void setNomUniNeg(String nomUniNeg) {
        this.nomUniNeg = nomUniNeg;
    }

    public Integer getIdgradoacademico() {
        return idgradoacademico;
    }

    public void setIdgradoacademico(Integer idgradoacademico) {
        this.idgradoacademico = idgradoacademico;
    }

    public Integer getIdtipolugarpago() {
        return idtipolugarpago;
    }

    public void setIdtipolugarpago(Integer idtipolugarpago) {
        this.idtipolugarpago = idtipolugarpago;
    }

    public String getTipoLugar() {
        return tipoLugar;
    }

    public void setTipoLugar(String tipoLugar) {
        this.tipoLugar = tipoLugar;
    }

    public Float getTotalMarzo() {
        return totalMarzo;
    }

    public void setTotalMarzo(Float totalMarzo) {
        this.totalMarzo = totalMarzo;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getRefFecha() {
        return refFecha;
    }

    public void setRefFecha(String refFecha) {
        this.refFecha = refFecha;
    }

    public Float getTotalImporte() {
        return totalImporte;
    }

    public void setTotalImporte(Float totalImporte) {
        this.totalImporte = totalImporte;
    }

    public Float getTotalMora() {
        return totalMora;
    }

    public void setTotalMora(Float totalMora) {
        this.totalMora = totalMora;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getFechaImp() {
        return fechaImp;
    }

    public void setFechaImp(String fechaImp) {
        this.fechaImp = fechaImp;
    }

}

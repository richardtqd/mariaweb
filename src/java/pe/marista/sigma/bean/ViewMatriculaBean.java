/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean;

import java.io.Serializable;

/**
 *
 * @author Administrador
 */
public class ViewMatriculaBean implements Serializable {

    private String uniNeg;
    private Integer anio;
    private String nivel;
    private String grado;
    private String seccion;
    private Integer matriculados;
    private Integer pendientes;
    private Integer total;

    //ayuda
    private Integer totalIni;
    private Integer totalPri;
    private Integer totalSec;
    private Integer totalSecB;
    private Integer totalIniPriSec;
    private Integer totalTaller;
    //CR Multi
    private String nivelInicial;
    private String nivelPrimaria;
    private String nivelSecundaria;
    private String nivelBach4;
    private String nivelBach5;
    private Integer sumMatriTot=0;

    public ViewMatriculaBean() {
        this.totalIni = 0;
        this.totalPri = 0;
        this.totalSec = 0;
        this.totalSecB = 0;
        this.totalIniPriSec = 0;
    }

    public ViewMatriculaBean(String uniNeg, Integer anio, String nivelInicial, String nivelPrimaria, String nivelSecundaria, String nivelBach4, String nivelBach5) {
        this.uniNeg = uniNeg;
        this.anio = anio;
        this.nivelInicial = nivelInicial;
        this.nivelPrimaria = nivelPrimaria;
        this.nivelSecundaria = nivelSecundaria;
        this.nivelBach4 = nivelBach4;
        this.nivelBach5 = nivelBach5;
    }
    

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public Integer getMatriculados() {
        return matriculados;
    }

    public void setMatriculados(Integer matriculados) {
        this.matriculados = matriculados;
    }

    public Integer getPendientes() {
        return pendientes;
    }

    public void setPendientes(Integer pendientes) {
        this.pendientes = pendientes;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalIni() {
        return totalIni;
    }

    public void setTotalIni(Integer totalIni) {
        this.totalIni = totalIni;
    }

    public Integer getTotalPri() {
        return totalPri;
    }

    public void setTotalPri(Integer totalPri) {
        this.totalPri = totalPri;
    }

    public Integer getTotalSec() {
        return totalSec;
    }

    public void setTotalSec(Integer totalSec) {
        this.totalSec = totalSec;
    }

    public Integer getTotalIniPriSec() {
        return totalIniPriSec;
    }

    public void setTotalIniPriSec(Integer totalIniPriSec) {
        this.totalIniPriSec = totalIniPriSec;
    }

    public String getNivelInicial() {
        return nivelInicial;
    }

    public void setNivelInicial(String nivelInicial) {
        this.nivelInicial = nivelInicial;
    }

    public String getNivelPrimaria() {
        return nivelPrimaria;
    }

    public void setNivelPrimaria(String nivelPrimaria) {
        this.nivelPrimaria = nivelPrimaria;
    }

    public String getNivelSecundaria() {
        return nivelSecundaria;
    }

    public void setNivelSecundaria(String nivelSecundaria) {
        this.nivelSecundaria = nivelSecundaria;
    }

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public Integer getTotalSecB() {
        return totalSecB;
    }

    public void setTotalSecB(Integer totalSecB) {
        this.totalSecB = totalSecB;
    }

    public String getNivelBach4() {
        return nivelBach4;
    }

    public void setNivelBach4(String nivelBach4) {
        this.nivelBach4 = nivelBach4;
    }

    public String getNivelBach5() {
        return nivelBach5;
    }

    public void setNivelBach5(String nivelBach5) {
        this.nivelBach5 = nivelBach5;
    }

    public Integer getTotalTaller() {
        return totalTaller;
    }

    public void setTotalTaller(Integer totalTaller) {
        this.totalTaller = totalTaller;
    }

    public Integer getSumMatriTot() {
        if(totalIni!=null){
        sumMatriTot = sumMatriTot+totalIni;
        }
        if(totalPri!=null){
        sumMatriTot=sumMatriTot+totalPri;
        }
        if(totalSec!=null){
            sumMatriTot=sumMatriTot+totalSec;
        }
        if(totalSecB!=null){
            sumMatriTot=sumMatriTot+totalSecB;
        }
        return sumMatriTot;
    }

    public void setSumMatriTot(Integer sumMatriTot) {
        this.sumMatriTot = sumMatriTot;
    }
    
}

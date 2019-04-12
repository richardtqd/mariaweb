/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author MS001
 */
public class SaldoPensionesRepBean implements Serializable {

    private String titulo;
    private String identificador;
    private String nombre;
    private String matricula;
    private String marzo;
    private String abril;
    private String mayo;
    private String junio;
    private String julio;
    private String agosto;
    private String septiembre;
    private String octubre;
    private String noviembre;
    private String diciembre;
    private String deuda; 
    private String nro; 
    private String idEstudiante;
    private JRBeanCollectionDataSource listaEstudiantesSaldo; 
    private JRBeanCollectionDataSource listaEstudiantesSaldoLetra; 
    private String codigoEstudiante;
    private String nombreRespago;
    private String correoResp;
    private String direccionResp;
    private String telefonoResp;
    
    //pivot
    private Integer idnivelacademico;
    private Integer idgradoacademico;
    private String seccion; 
    private String nombreGrado;
    private String nombreNivel;
    private String idestudiante;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarzo() {
        return marzo;
    }

    public void setMarzo(String marzo) {
        this.marzo = marzo;
    }

    public String getAbril() {
        return abril;
    }

    public void setAbril(String abril) {
        this.abril = abril;
    }

    public String getMayo() {
        return mayo;
    }

    public void setMayo(String mayo) {
        this.mayo = mayo;
    }

    public String getJunio() {
        return junio;
    }

    public void setJunio(String junio) {
        this.junio = junio;
    }

    public String getJulio() {
        return julio;
    }

    public void setJulio(String julio) {
        this.julio = julio;
    }

    public String getAgosto() {
        return agosto;
    }

    public void setAgosto(String agosto) {
        this.agosto = agosto;
    }

    public String getSeptiembre() {
        return septiembre;
    }

    public void setSeptiembre(String septiembre) {
        this.septiembre = septiembre;
    }

    public String getOctubre() {
        return octubre;
    }

    public void setOctubre(String octubre) {
        this.octubre = octubre;
    }

    public String getNoviembre() {
        return noviembre;
    }

    public void setNoviembre(String noviembre) {
        this.noviembre = noviembre;
    }

    public String getDiciembre() {
        return diciembre;
    }

    public void setDiciembre(String diciembre) {
        this.diciembre = diciembre;
    }

    public String getDeuda() {
        return deuda;
    }

    public void setDeuda(String deuda) {
        this.deuda = deuda;
    }

    public JRBeanCollectionDataSource getListaEstudiantesSaldo() {
        return listaEstudiantesSaldo;
    }

    public void setListaEstudiantesSaldo(List<EstudianteSaldoRepBean> listaEstudiantesSaldo) {
        this.listaEstudiantesSaldo = new JRBeanCollectionDataSource(listaEstudiantesSaldo);
    }

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    } 
  public JRBeanCollectionDataSource getListaEstudiantesSaldoLetra() {
        return listaEstudiantesSaldoLetra;
    }

    public void setListaEstudiantesSaldoLetra(List<EstudianteSaldoRepBean> listaEstudiantesSaldoLetra) {
        this.listaEstudiantesSaldoLetra = new JRBeanCollectionDataSource(listaEstudiantesSaldoLetra);
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getCodigoEstudiante() {
        return codigoEstudiante;
    }

    public void setCodigoEstudiante(String codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
    }

    public String getNombreRespago() {
        return nombreRespago;
    }

    public void setNombreRespago(String nombreRespago) {
        this.nombreRespago = nombreRespago;
    }

    public String getCorreoResp() {
        return correoResp;
    }

    public void setCorreoResp(String correoResp) {
        this.correoResp = correoResp;
    }

    public String getDireccionResp() {
        return direccionResp;
    }

    public void setDireccionResp(String direccionResp) {
        this.direccionResp = direccionResp;
    }

    public String getTelefonoResp() {
        return telefonoResp;
    }

    public void setTelefonoResp(String telefonoResp) {
        this.telefonoResp = telefonoResp;
    }

    public Integer getIdnivelacademico() {
        return idnivelacademico;
    }

    public void setIdnivelacademico(Integer idnivelacademico) {
        this.idnivelacademico = idnivelacademico;
    }

    public Integer getIdgradoacademico() {
        return idgradoacademico;
    }

    public void setIdgradoacademico(Integer idgradoacademico) {
        this.idgradoacademico = idgradoacademico;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getNombreGrado() {
        return nombreGrado;
    }

    public void setNombreGrado(String nombreGrado) {
        this.nombreGrado = nombreGrado;
    }

    public String getNombreNivel() {
        return nombreNivel;
    }

    public void setNombreNivel(String nombreNivel) {
        this.nombreNivel = nombreNivel;
    }

    public String getIdestudiante() {
        return idestudiante;
    }

    public void setIdestudiante(String idestudiante) {
        this.idestudiante = idestudiante;
    }

}

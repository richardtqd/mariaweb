/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author MS001
 */
public class EstudianteSaldoRepBean implements Serializable {

    private String idestudiante;
    private Integer idnivelacademico;
    private Integer idgradoacademico;
    private String seccion;
    private String nombre;
    private String nro;
    private String nombreGrado;
    private String nombreNivel;
    private JRBeanCollectionDataSource listaDetalle;
    
    private JRBeanCollectionDataSource listaDetalleLetra;

    public JRBeanCollectionDataSource getListaDetalleLetra() {
        return listaDetalleLetra;
    }

    public void setListaDetalleLetra(List<SaldoPensionesRepBean> listaDetalleLetra) {
        this.listaDetalleLetra = new JRBeanCollectionDataSource(listaDetalleLetra);
    }
  
     public JRBeanCollectionDataSource getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<SaldoPensionesRepBean> listaDetalle) {
        this.listaDetalle = new JRBeanCollectionDataSource(listaDetalle);
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdestudiante() {
        return idestudiante;
    }

    public void setIdestudiante(String idestudiante) {
        this.idestudiante = idestudiante;
    }

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
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
}

package pe.marista.sigma.bean.reporte;

import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ProvicionPensionRepBean 
{
    private String nombreUniNeg;
    private Integer cantAlumnos;
    private String cantFacturado;
    private String cantPagantes;
    private String catDsctoBeca;
    private String titulo;
    private String mes;
    private Integer mesAyuda;
    private String anio;
    private JRBeanCollectionDataSource listaNiveles;

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public Integer getCantAlumnos() {
        return cantAlumnos;
    }

    public void setCantAlumnos(Integer cantAlumnos) {
        this.cantAlumnos = cantAlumnos;
    }

    public String getCantFacturado() {
        return cantFacturado;
    }

    public void setCantFacturado(String cantFacturado) {
        this.cantFacturado = cantFacturado;
    }

    public String getCantPagantes() {
        return cantPagantes;
    }

    public void setCantPagantes(String cantPagantes) {
        this.cantPagantes = cantPagantes;
    }

    public String getCatDsctoBeca() {
        return catDsctoBeca;
    }

    public void setCatDsctoBeca(String catDsctoBeca) {
        this.catDsctoBeca = catDsctoBeca;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public JRBeanCollectionDataSource getListaNiveles() {
        return listaNiveles;
    }

    public void setListaNiveles(List<ProvicionPensionNivelesRepBean> listaNiveles) {
        this.listaNiveles = new JRBeanCollectionDataSource(listaNiveles);
    }  

    public Integer getMesAyuda() {
        return mesAyuda;
    }

    public void setMesAyuda(Integer mesAyuda) {
        this.mesAyuda = mesAyuda;
    }
}


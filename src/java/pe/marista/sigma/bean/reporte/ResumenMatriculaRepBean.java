package pe.marista.sigma.bean.reporte;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ResumenMatriculaRepBean {
    private Integer anio;
    private String nombreUniNeg;
    private String titulo;
    private String fechaCorte;
    private String nivelAcademico;
    private String gradoAcademico;
    private Integer ingresantes;
    private Integer promovido;
    private Integer matriculados;
    private Integer cantidadPagados;
    private JRBeanCollectionDataSource listaDetalle; 
    private String uniNeg;

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFechaCorte() {
        return fechaCorte;
    }

    public void setFechaCorte(String fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    public String getNivelAcademico() {
        return nivelAcademico;
    }

    public void setNivelAcademico(String nivelAcademico) {
        this.nivelAcademico = nivelAcademico;
    }

    public String getGradoAcademico() {
        return gradoAcademico;
    }

    public void setGradoAcademico(String gradoAcademico) {
        this.gradoAcademico = gradoAcademico;
    }

    public Integer getIngresantes() {
        return ingresantes;
    }

    public void setIngresantes(Integer ingresantes) {
        this.ingresantes = ingresantes;
    }

    public Integer getPromovido() {
        return promovido;
    }

    public void setPromovido(Integer promovido) {
        this.promovido = promovido;
    }

    public Integer getMatriculados() {
        return matriculados;
    }

    public void setMatriculados(Integer matriculados) {
        this.matriculados = matriculados;
    }

    public Integer getCantidadPagados() {
        return cantidadPagados;
    }

    public void setCantidadPagados(Integer cantidadPagados) {
        this.cantidadPagados = cantidadPagados;
    }
    
    public JRBeanCollectionDataSource getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<ResumenMatriculaRepBean> listaDetalle) {
        this.listaDetalle = new JRBeanCollectionDataSource(listaDetalle);
    }

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    
}

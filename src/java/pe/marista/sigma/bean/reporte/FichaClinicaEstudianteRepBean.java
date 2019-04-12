package pe.marista.sigma.bean.reporte;

import java.io.Serializable;
import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class FichaClinicaEstudianteRepBean implements Serializable{
    private String uniNeg;
    private String alumno;
    private String gradoAcademico;
    private String lugarFecNac;
    private Integer edad;
    private String documento;
    private String domicilio;
    private String telefonoDomi;
    private String celularPadre;
    private String celularMadre;
    private String sanguineo;
    private String nombreSeguro;
    private String fechaHemoglobina;
    private Integer resultado;       
    private JRBeanCollectionDataSource listaAlergias;

    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public String getAlumno() {
        return alumno;
    }

    public void setAlumno(String alumno) {
        this.alumno = alumno;
    }

    public String getGradoAcademico() {
        return gradoAcademico;
    }

    public void setGradoAcademico(String gradoAcademico) {
        this.gradoAcademico = gradoAcademico;
    }

    public String getLugarFecNac() {
        return lugarFecNac;
    }

    public void setLugarFecNac(String lugarFecNac) {
        this.lugarFecNac = lugarFecNac;
    }

   
    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefonoDomi() {
        return telefonoDomi;
    }

    public void setTelefonoDomi(String telefonoDomi) {
        this.telefonoDomi = telefonoDomi;
    }

    public String getCelularPadre() {
        return celularPadre;
    }

    public void setCelularPadre(String celularPadre) {
        this.celularPadre = celularPadre;
    }

    public String getCelularMadre() {
        return celularMadre;
    }

    public void setCelularMadre(String celularMadre) {
        this.celularMadre = celularMadre;
    }

    public String getSanguineo() {
        return sanguineo;
    }

    public void setSanguineo(String sanguineo) {
        this.sanguineo = sanguineo;
    }

    public String getNombreSeguro() {
        return nombreSeguro;
    }

    public void setNombreSeguro(String nombreSeguro) {
        this.nombreSeguro = nombreSeguro;
    }

    public JRBeanCollectionDataSource getListaAlergias() {
        return listaAlergias;
    } 
    
    public void setListaAlergias(List<EstudianteAlergiasRepBean> listaAlergias) {
        this.listaAlergias = new JRBeanCollectionDataSource(listaAlergias);
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getFechaHemoglobina() {
        return fechaHemoglobina;
    }

    public void setFechaHemoglobina(String fechaHemoglobina) {
        this.fechaHemoglobina = fechaHemoglobina;
    }

    public Integer getResultado() {
        return resultado;
    }

    public void setResultado(Integer resultado) {
        this.resultado = resultado;
    }
    
}

package pe.marista.sigma.bean.reporte;

public class AdmisionEstudiantesRepBean {

    private String titulo;
    private String nombreGradoAcademico;
    private String codigoColegio;
    private String nombreCompleto;
    private String nombreUniNeg;
    private String rutaImagen;
    private String ruc;
    private Integer CANTIDAD;   

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNombreGradoAcademico() {
        return nombreGradoAcademico;
    }

    public void setNombreGradoAcademico(String nombreGradoAcademico) {
        this.nombreGradoAcademico = nombreGradoAcademico;
    }

    public String getCodigoColegio() {
        return codigoColegio;
    }

    public void setCodigoColegio(String codigoColegio) {
        this.codigoColegio = codigoColegio;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public Integer getCANTIDAD() {
        return CANTIDAD;
    }

    public void setCANTIDAD(Integer CANTIDAD) {
        this.CANTIDAD = CANTIDAD;
    }

}

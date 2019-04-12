package pe.marista.sigma.bean.reporte;

public class AlertasEvaluacionDesempenoRepBean {
    
    private String titulo;
    private String periodo;
    private String nombreUniNeg;
    private Integer idEvaluadoEvaluador;
    private String nombreEvaluador;
    private String cargoEvaluador;
    private String nombreEvaluado;
    private String cargoEvaluado;
    private Integer respuesta0;
    private Integer respuesta1;
    private Integer respuesta4;
    private String usuario;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getNombreUniNeg() {
        return nombreUniNeg;
    }

    public void setNombreUniNeg(String nombreUniNeg) {
        this.nombreUniNeg = nombreUniNeg;
    }

    public Integer getIdEvaluadoEvaluador() {
        return idEvaluadoEvaluador;
    }

    public void setIdEvaluadoEvaluador(Integer idEvaluadoEvaluador) {
        this.idEvaluadoEvaluador = idEvaluadoEvaluador;
    }

    public String getNombreEvaluador() {
        return nombreEvaluador;
    }

    public void setNombreEvaluador(String nombreEvaluador) {
        this.nombreEvaluador = nombreEvaluador;
    }

    public String getCargoEvaluador() {
        return cargoEvaluador;
    }

    public void setCargoEvaluador(String cargoEvaluador) {
        this.cargoEvaluador = cargoEvaluador;
    }

    public String getNombreEvaluado() {
        return nombreEvaluado;
    }

    public void setNombreEvaluado(String nombreEvaluado) {
        this.nombreEvaluado = nombreEvaluado;
    }

    public String getCargoEvaluado() {
        return cargoEvaluado;
    }

    public void setCargoEvaluado(String cargoEvaluado) {
        this.cargoEvaluado = cargoEvaluado;
    }

    public Integer getRespuesta0() {
        return respuesta0;
    }

    public void setRespuesta0(Integer respuesta0) {
        this.respuesta0 = respuesta0;
    }

    public Integer getRespuesta1() {
        return respuesta1;
    }

    public void setRespuesta1(Integer respuesta1) {
        this.respuesta1 = respuesta1;
    }

    public Integer getRespuesta4() {
        return respuesta4;
    }

    public void setRespuesta4(Integer respuesta4) {
        this.respuesta4 = respuesta4;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}

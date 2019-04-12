package pe.marista.sigma.bean;

import java.io.Serializable;

public class PersonalEDBean implements Serializable{
    private UnidadNegocioBean unidadNegocioBean;
    private String codigoPer;
    private String nombre;
    private String apepat;
    private String apemat;
    private String nombreCompleto;
    private CargoBean cargo1Bean;
    private CargoBean cargo2Bean;
    private CargoBean cargo3Bean;
    private CargoBean cargo4Bean;
    private Boolean status;
    private Integer idCodigo;
    
    //ayuda
    private String value;
//    private UnidadOrganicaBean unidadOrganicaBean;

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean== null) {
            unidadNegocioBean= new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public String getCodigoPer() {
        return codigoPer;
    }

    public void setCodigoPer(String codigoPer) {
        this.codigoPer = codigoPer;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApepat() {
        return apepat;
    }

    public void setApepat(String apepat) {
        this.apepat = apepat;
    }

    public String getApemat() {
        return apemat;
    }

    public void setApemat(String apemat) {
        this.apemat = apemat;
    }

//    public UnidadOrganicaBean getUnidadOrganicaBean() {
//        if (unidadOrganicaBean== null) {
//            unidadOrganicaBean= new UnidadOrganicaBean();
//        }
//        return unidadOrganicaBean;
//    }
//
//    public void setUnidadOrganicaBean(UnidadOrganicaBean unidadOrganicaBean) {
//        this.unidadOrganicaBean = unidadOrganicaBean;
//    }

    public CargoBean getCargo1Bean() {
        if (cargo1Bean==null) {
            cargo1Bean= new CargoBean();
        }
        return cargo1Bean;
    }

    public void setCargo1Bean(CargoBean cargo1Bean) {
        this.cargo1Bean = cargo1Bean;
    }

    public CargoBean getCargo2Bean() {
        if (cargo2Bean==null) {
            cargo2Bean= new CargoBean();
        }
        return cargo2Bean;
    }

    public void setCargo2Bean(CargoBean cargo2Bean) {
        this.cargo2Bean = cargo2Bean;
    }

    public CargoBean getCargo3Bean() {
        if (cargo3Bean==null) {
            cargo3Bean= new CargoBean();
        }
        return cargo3Bean;
    }

    public void setCargo3Bean(CargoBean cargo3Bean) {
        this.cargo3Bean = cargo3Bean;
    }

    public CargoBean getCargo4Bean() {
        if (cargo4Bean==null) {
            cargo4Bean= new CargoBean();
        }
        return cargo4Bean;
    }

    public void setCargo4Bean(CargoBean cargo4Bean) {
        this.cargo4Bean = cargo4Bean;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Integer getIdCodigo() {
        return idCodigo;
    }

    public void setIdCodigo(Integer idCodigo) {
        this.idCodigo = idCodigo;
    }

    public String getValue() {
         StringBuilder sb = new StringBuilder();
        if (codigoPer != null) {
            sb.append(codigoPer).append("@");
        }
        if (getCargo1Bean().getTipoGrupoOcupacionalBean().getCodigo() != null) {
            sb.append(getCargo1Bean().getTipoGrupoOcupacionalBean().getCodigo()).append("/");
        }
        if (getCargo1Bean().getIdCargo() != null) {
            sb.append(getCargo1Bean().getIdCargo());
        }
        value=sb.toString();
        System.out.println("value:"+value);
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}

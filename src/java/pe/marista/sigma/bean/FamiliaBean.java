package pe.marista.sigma.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class FamiliaBean implements Serializable {

    private UnidadNegocioBean unidadNegocioBean;
    private Integer idFamilia;
    private String nombre;
    private FamiliarBean padreBean;
    private FamiliarBean madreBean;
    private String vehiculo1;
    private String vehiculo2;
    private Boolean status = Boolean.TRUE;
    private String creaPor;
    private Date creaFecha;
    private String modiPor;
    private String modiVer;//Cambio
    private Integer idGrupoFamiliar;
    //Grupo Familiar Ayuda
    private int gpFlg = 0;
    private List<FamiliaBean> listaFamiliaDetalle;

    public Integer getIdFamilia() {
        return idFamilia;
    }

    public void setIdFamilia(Integer idFamilia) {
        this.idFamilia = idFamilia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public FamiliarBean getPadreBean() {
        if (padreBean == null) {
            padreBean = new FamiliarBean();
        }
        return padreBean;
    }

    public void setPadreBean(FamiliarBean padreBean) {
        this.padreBean = padreBean;
    }

    public FamiliarBean getMadreBean() {
        if (madreBean == null) {
            madreBean = new FamiliarBean();
        }
        return madreBean;
    }

    public void setMadreBean(FamiliarBean madreBean) {
        this.madreBean = madreBean;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public UnidadNegocioBean getUnidadNegocioBean() {
        if (unidadNegocioBean == null) {
            unidadNegocioBean = new UnidadNegocioBean();
        }
        return unidadNegocioBean;
    }

    public void setUnidadNegocioBean(UnidadNegocioBean unidadNegocioBean) {
        this.unidadNegocioBean = unidadNegocioBean;
    }

    public String getCreaPor() {
        return creaPor;
    }

    public void setCreaPor(String creaPor) {
        this.creaPor = creaPor;
    }

    public Date getCreaFecha() {
        return creaFecha;
    }

    public void setCreaFecha(Date creaFecha) {
        this.creaFecha = creaFecha;
    }

    public String getModiPor() {
        return modiPor;
    }

    public void setModiPor(String modiPor) {
        this.modiPor = modiPor;
    }

    public String getModiVer() {
        return modiVer;
    }

    public void setModiVer(String modiVer) {
        this.modiVer = modiVer;
    }

    public String getVehiculo1() {
        return vehiculo1;
    }

    public void setVehiculo1(String vehiculo1) {
        this.vehiculo1 = vehiculo1;
    }

    public String getVehiculo2() {
        return vehiculo2;
    }

    public void setVehiculo2(String vehiculo2) {
        this.vehiculo2 = vehiculo2;
    }

    public Integer getIdGrupoFamiliar() {
        return idGrupoFamiliar;
    }

    public void setIdGrupoFamiliar(Integer idGrupoFamiliar) {
        this.idGrupoFamiliar = idGrupoFamiliar;
    }

    public int getGpFlg() {
        return gpFlg;
    }

    public void setGpFlg(int gpFlg) {
        this.gpFlg = gpFlg;
    }

    public List<FamiliaBean> getListaFamiliaDetalle() {
        if(listaFamiliaDetalle==null){
            listaFamiliaDetalle = new ArrayList<>();
        }
        return listaFamiliaDetalle;
    }

    public void setListaFamiliaDetalle(List<FamiliaBean> listaFamiliaDetalle) {
        this.listaFamiliaDetalle = listaFamiliaDetalle;
    }

}

package pe.marista.sigma.bean.reporte;

import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class FichaEstudianteRepBean {

    private String uniNeg;
    private String familia;
    private String direccion;
    private String distrito;
    private String telefono1;
    private String telefono2;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private String paisNacimiento;
    private String departamentoNacimiento;
    private String distritoNacimiento;
    private String fechaNacimiento;
    private String documento;
    private String sexo;
    private String nacionalidad;
    private String viveConPadre;
    private String viveConMadre;
    private String correo;
    private Integer nroHermano;
    private String colegioProcedencia;
    private Integer nroHermanos;
    private String telefonoEmergencia;
    private JRBeanCollectionDataSource listaPadres;
  
    private String idFamiliar;
    private String tipoParentesco;
    private String idEstudiante;
 
    public String getUniNeg() {
        return uniNeg;
    }

    public void setUniNeg(String uniNeg) {
        this.uniNeg = uniNeg;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getPaisNacimiento() {
        return paisNacimiento;
    }

    public void setPaisNacimiento(String paisNacimiento) {
        this.paisNacimiento = paisNacimiento;
    }

    public String getDepartamentoNacimiento() {
        return departamentoNacimiento;
    }

    public void setDepartamentoNacimiento(String departamentoNacimiento) {
        this.departamentoNacimiento = departamentoNacimiento;
    }

    public String getDistritoNacimiento() {
        return distritoNacimiento;
    }

    public void setDistritoNacimiento(String distritoNacimiento) {
        this.distritoNacimiento = distritoNacimiento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getViveConPadre() {
        return viveConPadre;
    }

    public void setViveConPadre(String viveConPadre) {
        this.viveConPadre = viveConPadre;
    }

    public String getViveConMadre() {
        return viveConMadre;
    }

    public void setViveConMadre(String viveConMadre) {
        this.viveConMadre = viveConMadre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getNroHermano() {
        return nroHermano;
    }

    public void setNroHermano(Integer nroHermano) {
        this.nroHermano = nroHermano;
    }

    public String getColegioProcedencia() {
        return colegioProcedencia;
    }

    public void setColegioProcedencia(String colegioProcedencia) {
        this.colegioProcedencia = colegioProcedencia;
    }

    public Integer getNroHermanos() {
        return nroHermanos;
    }

    public void setNroHermanos(Integer nroHermanos) {
        this.nroHermanos = nroHermanos;
    }

    public String getTelefonoEmergencia() {
        return telefonoEmergencia;
    }

    public void setTelefonoEmergencia(String telefonoEmergencia) {
        this.telefonoEmergencia = telefonoEmergencia;
    }

    public JRBeanCollectionDataSource getListaPadres() {
        return listaPadres;
    }

    public void setListaPadres(List<FamiliarEstudianteRepBean> listaPadres) {
        this.listaPadres = new JRBeanCollectionDataSource(listaPadres);
    }
    
    public String getIdFamiliar() {
        return idFamiliar;
    }

    public void setIdFamiliar(String idFamiliar) {
        this.idFamiliar = idFamiliar;
    }

    public String getTipoParentesco() {
        return tipoParentesco;
    }

    public void setTipoParentesco(String tipoParentesco) {
        this.tipoParentesco = tipoParentesco;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
}

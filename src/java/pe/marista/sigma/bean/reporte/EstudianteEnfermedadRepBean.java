package pe.marista.sigma.bean.reporte;

import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class EstudianteEnfermedadRepBean {
    
    private String enfermedad;
    private String edadInicio;
    private String status;
    
    
    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    public String getEdadInicio() {
        return edadInicio;
    }

    public void setEdadInicio(String edadInicio) {
        this.edadInicio = edadInicio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    } 
    
}

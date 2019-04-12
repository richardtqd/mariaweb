package pe.marista.sigma.bean.reporte;

import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class EstudianteMedicamentosRepBean {

    private String medicamento;
    private String flgAutoriza;
    

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getFlgAutoriza() {
        return flgAutoriza;
    }

    public void setFlgAutoriza(String flgAutoriza) {
        this.flgAutoriza = flgAutoriza;
    }
 

}

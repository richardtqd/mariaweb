package pe.marista.sigma.bean.reporte;

import java.util.List;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class EstudianteEnfermedadPadresRepBean {

    private String DiabetesPadres;
    private String hipertensionPadres;
    private String tbcPadres;
    

    public String getDiabetesPadres() {
        return DiabetesPadres;
    }

    public void setDiabetesPadres(String DiabetesPadres) {
        this.DiabetesPadres = DiabetesPadres;
    }

    public String getHipertensionPadres() {
        return hipertensionPadres;
    }

    public void setHipertensionPadres(String hipertensionPadres) {
        this.hipertensionPadres = hipertensionPadres;
    }

    public String getTbcPadres() {
        return tbcPadres;
    }

    public void setTbcPadres(String tbcPadres) {
        this.tbcPadres = tbcPadres;
    }
 
}

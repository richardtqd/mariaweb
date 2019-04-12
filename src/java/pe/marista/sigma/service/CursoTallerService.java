package pe.marista.sigma.service;

import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.CursoTallerBean;
import pe.marista.sigma.bean.reporte.CursoTallerRepBean;
import pe.marista.sigma.bean.reporte.DetCursoTallerRepBean;
import pe.marista.sigma.bean.reporte.DetDetCursoTallerRepBean;
import pe.marista.sigma.dao.CursoTallerDAO;

/**
 *
 * @author Administrador
 */
public class CursoTallerService {

    private CursoTallerDAO cursoTallerDAO;

    //Logica de Negocio
    @Transactional
    public void insertarCursoTaller(CursoTallerBean cursoTallerBean) throws Exception {
        cursoTallerDAO.insertarCursoTaller(cursoTallerBean);
    }

    @Transactional
    public void modificarCursoTaller(CursoTallerBean cursoTallerBean) throws Exception {
        cursoTallerDAO.modificarCursoTaller(cursoTallerBean);
    }

    @Transactional
    public void eliminarCursoTaller(Integer idCursoTaller) throws Exception {
        cursoTallerDAO.eliminarCursoTaller(idCursoTaller);
    }

    public List<CursoTallerBean> obtenerCursoTaller() throws Exception {
        return cursoTallerDAO.obtenerCursoTaller();
    }

    public List<CursoTallerBean> obtenerFiltroCursoTaller(CursoTallerBean cursoTallerBean) throws Exception {
        return cursoTallerDAO.obtenerFiltroCursoTaller(cursoTallerBean);
    }

    public CursoTallerBean obtenerCursoTallerPorId(Integer idCursoTaller, String uniNeg) throws Exception {
        return cursoTallerDAO.obtenerCursoTallerPorId(idCursoTaller, uniNeg);
    }

    public List<CursoTallerRepBean> obtenerTalleresRep(String uniNeg, Date fechaIni, Date fechaFin, List<Integer> idprogramacion, Integer flg) throws Exception {
        return cursoTallerDAO.obtenerTalleresRep(uniNeg, fechaIni, fechaFin, idprogramacion, flg);
    }

    public List<DetCursoTallerRepBean> obtenerDetalleTallerRep(String uniNeg, Date fechaIni, Date fechaFin, List<Integer> idprogramacion, Integer flg) throws Exception {
        return cursoTallerDAO.obtenerDetalleTallerRep(uniNeg, fechaIni, fechaFin, idprogramacion, flg);
    }

//    public List<DetDetCursoTallerRepBean> obtenerInscritosTalleresRep(String uniNeg, Integer idProgramacion) throws Exception {
//        return cursoTallerDAO.obtenerInscritosTalleresRep(uniNeg, idProgramacion);
//    }
    public List<DetDetCursoTallerRepBean> obtenerInscritosTalleresRep(String uniNeg, Integer idProgramacion, Date fechaIni, Date fechaFin) throws Exception {
        return cursoTallerDAO.obtenerInscritosTalleresRep(uniNeg, idProgramacion, fechaIni, fechaFin);
    }

    public List<CursoTallerRepBean> obtenerTalleresRepId(String uniNeg, Date fechaIni, Date fechaFin, Integer idProgramacion) throws Exception {
        return cursoTallerDAO.obtenerTalleresRepId(uniNeg, fechaIni, fechaFin, idProgramacion);
    }

    public List<DetCursoTallerRepBean> obtenerDetalleTallerRepId(String uniNeg, Date fechaIni, Date fechaFin, Integer idProgramacion) throws Exception {
        return cursoTallerDAO.obtenerDetalleTallerRepId(uniNeg, fechaIni, fechaFin, idProgramacion);
    }

    public List<DetCursoTallerRepBean> obtenerDetalleTallerRepDesc(String uniNeg, Date fechaIni, Date fechaFin, List<Integer> idprogramacion, Integer flg, String descripcion) throws Exception {
        return cursoTallerDAO.obtenerDetalleTallerRepDesc(uniNeg, fechaIni, fechaFin, idprogramacion, flg, descripcion);
    }

    public List<DetCursoTallerRepBean> obtenerDetalleTallerWebRepDesc(String uniNeg, String fechaIni, String fechaFin, List<Integer> idprogramacion, Integer flg, String descripcion) throws Exception {
        return cursoTallerDAO.obtenerDetalleTallerWebRepDesc(uniNeg, fechaIni, fechaFin, idprogramacion, flg, descripcion);
    }

    public List<CursoTallerRepBean> obtenerTalleresRepWeb(String uniNeg, String fechaIni, String fechaFin, List<Integer> idprogramacion, Integer flg) throws Exception {
        return cursoTallerDAO.obtenerTalleresRepWeb(uniNeg, fechaIni, fechaFin, idprogramacion, flg);
    }

    public List<DetCursoTallerRepBean> obtenerDetalleTallerRepWeb(String uniNeg, String fechaIni, String fechaFin, List<Integer> idprogramacion, Integer flg) throws Exception {
        return cursoTallerDAO.obtenerDetalleTallerRepWeb(uniNeg, fechaIni, fechaFin, idprogramacion, flg);
    }

    public List<DetDetCursoTallerRepBean> obtenerInscritosTalleresRepWeb(String uniNeg, Integer idProgramacion, String fechaIni, String fechaFin) throws Exception {
        return cursoTallerDAO.obtenerInscritosTalleresRepWeb(uniNeg, idProgramacion, fechaIni, fechaFin);
    }

    public Integer obtenerNroIns(String uniNeg, String idPersona, Integer idProgramacion) throws Exception {
        return cursoTallerDAO.obtenerNroIns(uniNeg, idPersona, idProgramacion);
    }

    //Getter y Setter
    public CursoTallerDAO getCursoTallerDAO() {
        return cursoTallerDAO;
    }

    public void setCursoTallerDAO(CursoTallerDAO cursoTallerDAO) {
        this.cursoTallerDAO = cursoTallerDAO;
    }

}

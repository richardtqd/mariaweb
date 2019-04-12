package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.ProgramacionBean;
import pe.marista.sigma.dao.ProgramacionDAO;

/**
 *
 * @author Administrador
 */
public class ProgramacionService {

    private ProgramacionDAO programacionDAO;

    //Lógica de Negocio
    public List<ProgramacionBean> obtenerProgramacionPorUniNeg(ProgramacionBean programacionBean) throws Exception {
        return programacionDAO.obtenerProgramacion(programacionBean);
    }

    public List<ProgramacionBean> obtenerProgramacionActivos(ProgramacionBean programacionBean) throws Exception {
        return programacionDAO.obtenerProgramacionActivos(programacionBean);
    }

    public ProgramacionBean obtenerPrograPorId(ProgramacionBean programacionBean) throws Exception {
        return programacionDAO.obtenerPrograPorId(programacionBean);
    }

    public Integer obtenerCupoPrograPorId(ProgramacionBean programacionBean) throws Exception {
        return programacionDAO.obtenerCupoPrograPorId(programacionBean);
    }

    public List<ProgramacionBean> obtenerPrograPorTipo(String codigo, String uniNeg) throws Exception {
        return programacionDAO.obtenerPrograPorTipo(codigo, uniNeg);
    }

    public List<ProgramacionBean> obtenerProgPorTipoPorAnioPorUniNeg(String tipoCodigo, String codigo, String uniNeg, Integer anio) throws Exception {
        return programacionDAO.obtenerProgPorTipoPorAnioPorUniNeg(tipoCodigo, codigo, uniNeg, anio);
    }

    public List<ProgramacionBean> obtenerPrograAdmisionUniNeg(ProgramacionBean programacionBean) throws Exception {
        return programacionDAO.obtenerPrograAdmisionUniNeg(programacionBean);
    }

    public List<ProgramacionBean> obtenerProgPorTipoActivosPorConcepto(String uniNeg, Integer idConcepto) throws Exception {
        return programacionDAO.obtenerProgPorTipoActivosPorConcepto(uniNeg, idConcepto);
    }

    public List<ProgramacionBean> obtenerProgPorTipoActivos(String uniNeg) throws Exception {
        return programacionDAO.obtenerProgPorTipoActivos(uniNeg);
    }

    public List<ProgramacionBean> obtenerProgPorTipoActivosDocIngFor(String uniNeg, List<Integer> idProgramacion, Integer idConcepto) throws Exception {
        return programacionDAO.obtenerProgPorTipoActivosDocIngFor(uniNeg, idProgramacion, idConcepto);
    }

    public List<Integer> obtenerProgRegPorDiscente(String uniNeg, String idDiscente) throws Exception {
        return programacionDAO.obtenerProgRegPorDiscente(uniNeg, idDiscente);
    }

    @Transactional
    public void insertarProgramacion(ProgramacionBean programacionBean) throws Exception {
        programacionBean.getIdTipoMoneda().setIdCodigo(MaristaConstantes.COD_SOLES);
        programacionDAO.insertarProgramacion(programacionBean);
    }

    @Transactional
    public void modificarProgramacion(ProgramacionBean programacionBean) throws Exception {
        programacionDAO.modificarProgramacion(programacionBean);
    }

    @Transactional
    public void modificarProgramacionCupos(ProgramacionBean programacionBean) throws Exception {
        programacionDAO.modificarProgramacionCupos(programacionBean);
    }

    @Transactional
    public void eliminarProgramacion(ProgramacionBean programacionBean) throws Exception {
        programacionDAO.eliminarProgramacion(programacionBean);
    }

    @Transactional
    public void cambiarEstado(ProgramacionBean programacionBean) throws Exception {
        programacionDAO.cambiarEstado(programacionBean);
    }

    public Integer obtenerCuposPrograPorIdFor(String uniNeg, List<Integer> idProgramacion) throws Exception {
        return programacionDAO.obtenerCuposPrograPorIdFor(uniNeg, idProgramacion);
    }

    public List<ProgramacionBean> obtenerProgPorTipoActivosRef(ProgramacionBean programacionBean) throws Exception {
        return programacionDAO.obtenerProgPorTipoActivosRef(programacionBean);
    }

    //GEtter y Setter
    public ProgramacionDAO getProgramacionDAO() {
        return programacionDAO;
    }

    public void setProgramacionDAO(ProgramacionDAO programacionDAO) {
        this.programacionDAO = programacionDAO;
    }

    public List<ProgramacionBean> obtenerTodos() throws Exception {
        return programacionDAO.obtenerTodos();
    }

    public List<ProgramacionBean> obtenerProgramacionImput(String uniNeg, Integer idProgramacionDscto) throws Exception {
        return programacionDAO.obtenerProgramacionImput(uniNeg, idProgramacionDscto);
    }

    public List<ProgramacionBean> obtenerProgramacionOutput(String uniNeg, Integer idProgramacionDscto) throws Exception {
        return programacionDAO.obtenerProgramacionOutput(uniNeg, idProgramacionDscto);
    }

    

}

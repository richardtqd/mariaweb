package pe.marista.sigma.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.CodigoBean;
import pe.marista.sigma.bean.EstudianteBean;
import pe.marista.sigma.bean.FamiliaBean;
import pe.marista.sigma.bean.FamiliarBean;
import pe.marista.sigma.bean.FamiliarEstudianteBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.dao.EstudianteDAO;
import pe.marista.sigma.dao.FamiliaDAO;
import pe.marista.sigma.dao.FamiliarDAO;
import pe.marista.sigma.dao.PersonaDAO;
import pe.marista.sigma.util.MaristaUtils;

/**
 *
 * @author Administrador
 */
public class FamiliaService {

    private FamiliaDAO familiaDAO;
    private FamiliarDAO familiarDAO;
    private PersonaDAO personaDAO;
    private EstudianteDAO estudianteDAO;
    private final UsuarioBean usuarioLogin;

    public FamiliaService() throws Exception {
        usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
    }

    @Transactional
    public void insertarFamilia(FamiliaBean familiaBean, String idEstudiante) throws Exception {
        //Para Padre
        familiaBean.getPadreBean().getPersonaBean().setIdPersona(familiaBean.getPadreBean().getPersonaBean().getNroDoc());
        familiaBean.getPadreBean().getPersonaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
        familiaBean.getPadreBean().getPersonaBean().setCreaFecha(new Date());
        familiaBean.getPadreBean().getPersonaBean().setCreaPor(usuarioLogin.getUsuario());
        familiaBean.getPadreBean().setCreaFecha(new Date());
        familiaBean.getPadreBean().setCreaPor(usuarioLogin.getUsuario());

        FamiliarEstudianteBean familiarEstudiantePadre = new FamiliarEstudianteBean();

        familiarEstudiantePadre.getEstudianteBean().getPersonaBean().setIdPersona(idEstudiante);
        familiarEstudiantePadre.getEstudianteBean().getPersonaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
        familiarEstudiantePadre.setFamiliarBean(familiaBean.getPadreBean());
        familiarEstudiantePadre.setTipoParentescoBean(new CodigoBean(MaristaConstantes.CODIGO_PADRE));
        familiarEstudiantePadre.setCreaPor(usuarioLogin.getUsuario());
        familiarEstudiantePadre.setCreaFecha(new Date());
        PersonaBean personaPadre = personaDAO.obtenerPersPorId(familiaBean.getPadreBean().getPersonaBean());
        if (personaPadre == null) {
            personaDAO.insertarPersona(familiaBean.getPadreBean().getPersonaBean());
            familiarDAO.insertarFamiliar(familiaBean.getPadreBean());
            familiarDAO.insertarFamiliarEstudiante(familiarEstudiantePadre);
        } else {
            FamiliarBean familiarPadre = familiarDAO.obtenerFamiliarPorId(familiaBean.getPadreBean().getPersonaBean());
            if (familiarPadre == null) {
                familiarDAO.insertarFamiliar(familiaBean.getPadreBean());
            } else {
                FamiliarEstudianteBean familiarEstudianteP = familiarDAO.obtenerFamiliarEstPorId(familiarEstudiantePadre);
                if (familiarEstudianteP == null) {
                    familiarDAO.insertarFamiliarEstudiante(familiarEstudiantePadre);
                }
            }
        }
        //Para Madre
        familiaBean.getMadreBean().getPersonaBean().setIdPersona(familiaBean.getMadreBean().getPersonaBean().getNroDoc());
        familiaBean.getMadreBean().getPersonaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
        familiaBean.getMadreBean().getPersonaBean().setCreaFecha(new Date());
        familiaBean.getMadreBean().getPersonaBean().setCreaPor(usuarioLogin.getUsuario());
        familiaBean.getMadreBean().setCreaFecha(new Date());
        familiaBean.getMadreBean().setCreaPor(usuarioLogin.getUsuario());

        FamiliarEstudianteBean familiarEstudianteMadre = new FamiliarEstudianteBean();
        familiarEstudianteMadre.getEstudianteBean().getPersonaBean().setIdPersona(idEstudiante);
        familiarEstudianteMadre.getEstudianteBean().getPersonaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
        familiarEstudianteMadre.setFamiliarBean(familiaBean.getMadreBean());
        familiarEstudianteMadre.setTipoParentescoBean(new CodigoBean(MaristaConstantes.CODIGO_PADRE));
        familiarEstudianteMadre.setCreaPor(usuarioLogin.getUsuario());
        familiarEstudianteMadre.setCreaFecha(new Date());
        PersonaBean personaMadre = personaDAO.obtenerPersPorId(familiaBean.getMadreBean().getPersonaBean());
        if (personaMadre == null) {
            personaDAO.insertarPersona(familiaBean.getMadreBean().getPersonaBean());
            familiarDAO.insertarFamiliar(familiaBean.getMadreBean());
            familiarDAO.insertarFamiliarEstudiante(familiarEstudianteMadre);
        } else {
            FamiliarBean familiarMadre = familiarDAO.obtenerFamiliarPorId(familiaBean.getMadreBean().getPersonaBean());
            if (familiarMadre == null) {

                familiarDAO.insertarFamiliar(familiaBean.getMadreBean());
            } else {
                FamiliarEstudianteBean familiarEstudianteM = familiarDAO.obtenerFamiliarEstPorId(familiarEstudianteMadre);
                if (familiarEstudianteM == null) {
                    familiarDAO.insertarFamiliarEstudiante(familiarEstudianteMadre);
                }
            }
        }
        //Para Familia
        familiaBean.setCreaPor(usuarioLogin.getUsuario());
        familiaBean.setCreaFecha(new Date());
        familiaBean.setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
        familiaBean.setNombre(familiaBean.getPadreBean().getPersonaBean().getApepat() + " " + familiaBean.getMadreBean().getPersonaBean().getApepat());
        FamiliaBean fb = new FamiliaBean();
        fb = familiaDAO.obtenerFamiliaPorPaMa(familiaBean);
        if (fb == null) {
            familiaDAO.insertarFamilia(familiaBean);
        } else {
            familiaBean.setIdFamilia(fb.getIdFamilia());
        }
        EstudianteBean estudianteBean = new EstudianteBean();
        estudianteBean.getPersonaBean().setIdPersona(idEstudiante);
        estudianteBean.setModiPor(usuarioLogin.getUsuario());
        estudianteBean.setFamiliaBean(familiaBean);
        estudianteDAO.modificarEstFam(estudianteBean);
    }

    @Transactional
    public void modificarFamilia(FamiliaBean familiaBean, String idEstudiante) throws Exception {
        familiaBean.getPadreBean().getPersonaBean().setIdPersona(familiaBean.getPadreBean().getPersonaBean().getNroDoc());
        familiaBean.getMadreBean().getPersonaBean().setIdPersona(familiaBean.getMadreBean().getPersonaBean().getNroDoc());
//        familiaBean.getApoderadoBean().getPersonaBean().setIdPersona(familiaBean.getApoderadoBean().getPersonaBean().getNroDoc());
        //Para Padre
        personaDAO.modificarPersona(familiaBean.getPadreBean().getPersonaBean());
        familiaBean.getPadreBean().getPersonaBean().setIdPersona(familiaBean.getPadreBean().getPersonaBean().getNroDoc());
        familiarDAO.modificarFamiliar(familiaBean.getPadreBean());
        //Para Madre
        personaDAO.modificarPersona(familiaBean.getMadreBean().getPersonaBean());
        familiaBean.getMadreBean().getPersonaBean().setIdPersona(familiaBean.getMadreBean().getPersonaBean().getNroDoc());
        familiarDAO.modificarFamiliar(familiaBean.getMadreBean());
//        //Para Apoderado
//        personaDAO.modificarPersona(familiaBean.getApoderadoBean().getPersonaBean());
//        familiaBean.getApoderadoBean().getPersonaBean().setIdPersona(familiaBean.getApoderadoBean().getPersonaBean().getNroDoc());
//        familiarDAO.modificarFamiliar(familiaBean.getApoderadoBean());
        //Para Familia
        familiaBean.setNombre(familiaBean.getPadreBean().getPersonaBean().getApepat() + "-" + familiaBean.getMadreBean().getPersonaBean().getApepat());
        familiaDAO.modificarFamilia(familiaBean);
    }

    @Transactional
    public void modificarFamiliaRapido(FamiliaBean familiaBean) throws Exception {
//        familiaBean.getPadreBean().getPersonaBean().setIdPersona(familiaBean.getPadreBean().getPersonaBean().getNroDoc());
//        familiaBean.getMadreBean().getPersonaBean().setIdPersona(familiaBean.getMadreBean().getPersonaBean().getNroDoc());
        familiaDAO.modificarFamiliaRapido(familiaBean);
    }

    @Transactional
    public void eliminarFamilia(Integer idFamilia) throws Exception {
        familiaDAO.eliminarFamilia(idFamilia);
    }

    public List<FamiliaBean> obtenerFamilia() throws Exception {
        return familiaDAO.obtenerFamilia();
    }

    public List<FamiliaBean> obtenerFiltroFamilia(FamiliaBean familiaBean) throws Exception {
        List<FamiliaBean> lista = familiaDAO.obtenerFiltroFamilia(familiaBean);
        return lista;
    }

    public List<FamiliaBean> obtenerFiltroFamiliaPrep(FamiliaBean familiaBean) throws Exception {
        List<FamiliaBean> lista = new ArrayList<>();
        if (familiaBean.getGpFlg() == 3) {
            lista = familiaDAO.obtenerFiltroGrupoFamilia(familiaBean);
        } else {
            lista = familiaDAO.obtenerFiltroFamilia(familiaBean);
        }
        List<FamiliaBean> grupoFamiliaBean = new ArrayList<>();
        int cont = 0;
        FamiliaBean familia = new FamiliaBean();
        for (int i = 0; i < lista.size(); i++) {
            if (cont == 0) {
                cont = lista.get(i).getIdGrupoFamiliar();
            }
            if (cont == lista.get(i).getIdGrupoFamiliar()) {
                familia.setIdGrupoFamiliar(cont);
                familia.setUnidadNegocioBean(lista.get(i).getUnidadNegocioBean());
                familia.setNombre(lista.get(i).getNombre());
                familia.getListaFamiliaDetalle().add(lista.get(i));
            } else {
                grupoFamiliaBean.add(familia);
                cont = lista.get(i).getIdGrupoFamiliar();
                familia = new FamiliaBean();
                familia.setIdGrupoFamiliar(cont);
                familia.setUnidadNegocioBean(lista.get(i).getUnidadNegocioBean());
                familia.setNombre(lista.get(i).getNombre());
                familia.getListaFamiliaDetalle().add(lista.get(i));
//                grupoFamiliaBean.add(familia);
            }
            if (i + 1 == lista.size()) {
                grupoFamiliaBean.add(familia);
            }
        }
        return grupoFamiliaBean;
    }

    public FamiliaBean obtenerFamiliaPorId(FamiliaBean familiaBean) throws Exception {
        return familiaDAO.obtenerFamiliaPorId(familiaBean);
    }

    @Transactional
    public void guardarGrupoFamilia(FamiliaBean familiaBean, List<FamiliaBean> listaFamiliaBean, List<FamiliaBean> listaFamiliaTempBean) throws Exception {
        if (familiaBean.getIdGrupoFamiliar() == null) {
            familiaBean.setIdGrupoFamiliar(familiaDAO.generaCodigoGrupoFamiliar(familiaBean) + 1);
        }
        for (FamiliaBean bean1 : listaFamiliaBean) {
            bean1.setIdGrupoFamiliar(familiaBean.getIdGrupoFamiliar());
            familiaDAO.actualizaFamiliaPorGrupo(bean1);
        }
        for (int i = 0; i < listaFamiliaTempBean.size(); i++) {
            listaFamiliaTempBean.get(i).setIdGrupoFamiliar(null);
            familiaDAO.actualizaFamiliaPorGrupo(listaFamiliaTempBean.get(i));
        }
    }

    public List<FamiliaBean> obtenerGrupoFamiliirPorId(FamiliaBean familiaBean) throws Exception {
        return familiaDAO.obtenerGrupoFamiliirPorId(familiaBean);
    }

    public List<FamiliaBean> obtenerGrupoFamiliirPorIdInverso(FamiliaBean familiaBean) throws Exception {
        return familiaDAO.obtenerGrupoFamiliirPorIdInverso(familiaBean);
    }

//    public List<FamiliaBean> obtenerFiltroGrupoFamilia(FamiliaBean familiaBean) throws Exception {
//        List<FamiliaBean> lista = familiaDAO.obtenerFiltroGrupoFamilia(familiaBean);
//        return lista;
//    }
    public List<PersonaBean> obtenerEstudiantePorGrupoFam(FamiliaBean familiaBean) throws Exception {
        return familiaDAO.obtenerEstudiantePorGrupoFam(familiaBean);
    }

    //Getter y Setter

    public FamiliaDAO getFamiliaDAO() {
        return familiaDAO;
    }

    public void setFamiliaDAO(FamiliaDAO familiaDAO) {
        this.familiaDAO = familiaDAO;
    }

    public FamiliarDAO getFamiliarDAO() {
        return familiarDAO;
    }

    public void setFamiliarDAO(FamiliarDAO familiarDAO) {
        this.familiarDAO = familiarDAO;
    }

    public PersonaDAO getPersonaDAO() {
        return personaDAO;
    }

    public void setPersonaDAO(PersonaDAO personaDAO) {
        this.personaDAO = personaDAO;
    }

    public EstudianteDAO getEstudianteDAO() {
        return estudianteDAO;
    }

    public void setEstudianteDAO(EstudianteDAO estudianteDAO) {
        this.estudianteDAO = estudianteDAO;
    }

    public FamiliaBean obtenerFamiliaPorIdRapido(FamiliaBean familiaBean) throws Exception {
        return familiaDAO.obtenerFamiliaPorIdRapido(familiaBean);
    }

    public void insertarFamiliaRapido(FamiliaBean familiaBean) throws Exception {
        familiaDAO.insertarFamiliaRapido(familiaBean);
    }

    @Transactional
    public void modificarFamiliaRapidoPapa(FamiliaBean familiaBean) throws Exception {
        familiaDAO.modificarFamiliaRapidoPapa(familiaBean);
    }

    @Transactional
    public void modificarFamiliaRapidoMama(FamiliaBean familiaBean) throws Exception {
        familiaDAO.modificarFamiliaRapidoMama(familiaBean);
    }

    public Boolean obtenerFamiliaPorPadresFam(String idPadre, String idMadre,String uniNeg) throws Exception {
        return familiaDAO.obtenerFamiliaPorPadresFam(idPadre, idMadre,uniNeg);
    }

    public Integer obtenerFamiliaId(String idPadre, String idMadre, String uniNeg,Integer flgOpcion) throws Exception {
        return familiaDAO.obtenerFamiliaId(idPadre, idMadre, uniNeg,flgOpcion);
    }
    

}

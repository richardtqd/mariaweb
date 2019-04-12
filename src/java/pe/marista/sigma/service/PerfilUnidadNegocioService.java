package pe.marista.sigma.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.bean.ModuloBean;
import pe.marista.sigma.bean.PerfilBean;
import pe.marista.sigma.bean.PerfilUnidadNegocioBean;
import pe.marista.sigma.bean.UnidadNegocioBean;
import pe.marista.sigma.dao.PerfilUnidadNegocioDAO;

public class PerfilUnidadNegocioService {

    private PerfilUnidadNegocioDAO perfilUnidadNegocioDAO;

    //getter and setter
    public PerfilUnidadNegocioDAO getPerfilUnidadNegocioDAO() {
        return perfilUnidadNegocioDAO;
    }

    public void setPerfilUnidadNegocioDAO(PerfilUnidadNegocioDAO perfilUnidadNegocioDAO) {
        this.perfilUnidadNegocioDAO = perfilUnidadNegocioDAO;
    }

    //Metodos
    public List<PerfilUnidadNegocioBean> obtenerTodos() throws Exception {
        return perfilUnidadNegocioDAO.obtenerTodos();
    }

    public List<PerfilUnidadNegocioBean> obtenerPorFiltro(PerfilUnidadNegocioBean perfilUnidadNegocioBean) throws Exception {
        return perfilUnidadNegocioDAO.obtenerPorFiltro(perfilUnidadNegocioBean);
    }

    public PerfilUnidadNegocioBean obtenerPorId(PerfilUnidadNegocioBean perfilUnidadNegocioBean) throws Exception {
        return perfilUnidadNegocioDAO.obtenerPorId(perfilUnidadNegocioBean);
    }
    public List<PerfilUnidadNegocioBean> obtenerPorIdPerfil(PerfilUnidadNegocioBean perfilUnidadNegocioBean) throws Exception {
        return perfilUnidadNegocioDAO.obtenerPorIdPerfil(perfilUnidadNegocioBean);
    }

    @Transactional
    public void insertar(PerfilBean perfilBean, List<String> listaUnidadNegocioDest, List<UnidadNegocioBean> listaUnidadNegocioBean) throws Exception {
        perfilUnidadNegocioDAO.insertarPerfil(perfilBean);//Inserta el Perfil
        for (String string : listaUnidadNegocioDest) {
            for (UnidadNegocioBean unidadNegocioBean : listaUnidadNegocioBean) {
                if (unidadNegocioBean.getNombreUniNeg().equals(string)) {
                    PerfilUnidadNegocioBean perfilUnidadNegocioBean = new PerfilUnidadNegocioBean();
                    perfilUnidadNegocioBean.setPerfilBean(perfilBean);
                    perfilUnidadNegocioBean.setUnidadNegocioBean(unidadNegocioBean);
                    perfilUnidadNegocioBean.setUnidadNegocioBean(unidadNegocioBean);
                    perfilUnidadNegocioDAO.insertar(perfilUnidadNegocioBean);
                    break;
                }
            }
        }
//        perfilUnidadNegocioDAO.insertar(perfilUnidadNegocioBean);
//        UsuarioBean ejecutor = (UsuarioBean) (new FAPUtils().sesionObtenerObjeto("usuarioLogin"));
//        GCPLog.writeAction(getClass(), GCPLog.ACCION_INSERTAR, GCPLog.TABLA_PERFIL,
//                ejecutor.getCodUsuario(), FAPConstantes.MENSAJE_LOG_INSERTAR + "Perfil: " + perfilUsuarioBean.getNomPerfil());
    }

    @Transactional
    public void actualizar(PerfilBean perfilBean, List<String> listaUnidadNegocioDest, List<UnidadNegocioBean> listaUnidadNegocioBean) throws Exception {
        perfilUnidadNegocioDAO.actualizarPerfil(perfilBean);
        PerfilUnidadNegocioBean bean = new PerfilUnidadNegocioBean();
        bean.setPerfilBean(perfilBean);
        perfilUnidadNegocioDAO.eliminarTodos(bean);
        for (String string : listaUnidadNegocioDest) {
            for (UnidadNegocioBean unidadNegocioBean : listaUnidadNegocioBean) {
                if (unidadNegocioBean.getNombreUniNeg().equals(string)) {
                    PerfilUnidadNegocioBean perfilUnidadNegocioBean = new PerfilUnidadNegocioBean();
                    perfilUnidadNegocioBean.setPerfilBean(perfilBean);
                    perfilUnidadNegocioBean.setUnidadNegocioBean(unidadNegocioBean);
                    perfilUnidadNegocioBean.setUnidadNegocioBean(unidadNegocioBean);
                    perfilUnidadNegocioDAO.insertar(perfilUnidadNegocioBean);
                    break;
                }
            }
        }
//        UsuarioBean ejecutor = (UsuarioBean) (new FAPUtils().sesionObtenerObjeto("usuarioLogin"));
//        GCPLog.writeAction(getClass(), GCPLog.ACCION_MODIFICAR, GCPLog.TABLA_PERFIL,
//                ejecutor.getCodUsuario(), FAPConstantes.MENSAJE_LOG_MODIFICAR + "Perfil: " + perfilUsuarioBean.getNomPerfil());
    }

    @Transactional
    public void eliminarTodos(PerfilUnidadNegocioBean perfilUnidadNegocioBean) throws Exception {
        perfilUnidadNegocioDAO.eliminarTodos(perfilUnidadNegocioBean);
        eliminarPerfil(perfilUnidadNegocioBean.getPerfilBean());
//        UsuarioBean ejecutor = (UsuarioBean) (new FAPUtils().sesionObtenerObjeto("usuarioLogin"));
//        GCPLog.writeAction(getClass(), GCPLog.ACCION_ELIMINAR, GCPLog.TABLA_PERFIL,
//                ejecutor.getCodUsuario(), FAPConstantes.MENSAJE_LOG_ELIMINAR + "Perfil: " + codigo.toString());
    }
    //Perfil

    @Transactional
    public void insertarPerfil(PerfilBean perfilBean) throws Exception {
        perfilUnidadNegocioDAO.insertarPerfil(perfilBean);
    }

    @Transactional
    public void eliminarPerfil(PerfilBean perfilBean) throws Exception {
        perfilUnidadNegocioDAO.eliminarPerfil(perfilBean);
    }

    public List<PerfilBean> obtenerTodosPerfil() throws Exception {
        return perfilUnidadNegocioDAO.obtenerTodosPerfil();
    }

    public List<PerfilBean> obtenerTodosFiltroPerfil(PerfilBean perfilBean) throws Exception {
        System.out.println(perfilBean.getNombre());
        return perfilUnidadNegocioDAO.obtenerTodosFiltroPerfil(perfilBean);
    }
    
    //Modulo

    public List<ModuloBean> obtenerTodosMenu() throws Exception {
        return perfilUnidadNegocioDAO.obtenerTodosModulo();
    }
    //Unidad Negocio

    public List<UnidadNegocioBean> obtenerTodosUnidadNegocio() throws Exception {
        return perfilUnidadNegocioDAO.obtenerTodosUnidadNegocio();
    }
}

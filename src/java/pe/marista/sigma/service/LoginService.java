package pe.marista.sigma.service;

import java.util.List;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.VistaBean;
import pe.marista.sigma.dao.UsuarioDAO;
import pe.marista.sigma.util.MaristaCifra;

public class LoginService {

    private UsuarioDAO usuarioDAO;

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public UsuarioBean autenticarUsuario(UsuarioBean usuarioBean) throws Exception {
        usuarioBean.setClave(new MaristaCifra().encrypt(usuarioBean.getClave()));
        return usuarioDAO.autenticarUsuario(usuarioBean);
    }

    public List<VistaBean> obtenerVistaPorUsuario(UsuarioBean usuarioBean) throws Exception {
        return usuarioDAO.obtenerVistaPorUsuario(usuarioBean);
    }
}

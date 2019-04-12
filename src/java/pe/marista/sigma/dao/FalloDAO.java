/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.FalloBean;

/**
 *
 * @author JC
 */
public interface FalloDAO {

    public List<FalloBean> obtenerPorUniNeg(FalloBean falloBean) throws Exception;

    public List<FalloBean> filtrarFallo(FalloBean falloBean) throws Exception;

}

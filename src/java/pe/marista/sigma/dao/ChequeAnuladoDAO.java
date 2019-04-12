package pe.marista.sigma.dao;

import java.util.List;
import pe.marista.sigma.bean.ChequeAnuladoBean;

public interface ChequeAnuladoDAO {

    public void insertarChequeAnulado(ChequeAnuladoBean chequeAnulado) throws Exception;

    public void modificarChequeAnulado(ChequeAnuladoBean chequeAnulado) throws Exception;

    public void eliminarChequeAnulado(ChequeAnuladoBean chequeAnulado) throws Exception;

    public ChequeAnuladoBean obtenerChequeAnuladoPorId(ChequeAnuladoBean chequeAnulado) throws Exception;

    public List<ChequeAnuladoBean> obtenerChequeAnuladoPorFiltro(ChequeAnuladoBean chequeAnulado) throws Exception;

}

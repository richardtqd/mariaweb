package pe.marista.sigma.dao;

import pe.marista.sigma.bean.FacturaCompraBean;

public interface FacturaCompraDAO 
{
     public void insertar(FacturaCompraBean facturaCompraBean) throws Exception;

    public void modificar(FacturaCompraBean facturaCompraBean) throws Exception;
    
}

package pe.marista.sigma.util;


import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;

public class SpringWebApplicationContext 
{
	private FileSystemXmlApplicationContext contextoXML = null;
	
    private SpringWebApplicationContext()
	{
	}
	
	private static SpringWebApplicationContext unico = null;
	public static SpringWebApplicationContext getInstance() 
	{
		if (unico==null)
			unico = new SpringWebApplicationContext();
		return unico;
	}
	private WebApplicationContext webApplicationContext=null;

	public WebApplicationContext getWebApplicationContext()
	{
		return webApplicationContext;
	}

	public void setWebApplicationContext(WebApplicationContext context)
	{
		webApplicationContext = context;
	}
	
	public Object getBean(String beanId)
	{
		/*  Este m�todo busca el objeto pedido dentro de los contextos de Spring.
		 *  Spring puede guardar sus objetos en dos contextos.
		 *  El contexto principal es el WebApplicationContext; pero generalmente
		 *  en ambientes de prueba se usa el XMLContext
		 */
		 
		Object respuesta;
		
		if (webApplicationContext!=null)
			{
			respuesta = webApplicationContext.getBean(beanId);
			if (respuesta==null)
				throw new RuntimeException("Nombre de Bean incorrecto de objeto Spring");
			return respuesta;
			}
		
		//si el webApplicactionContext es null, se busca en el
		//XMLApplicationContext, si hubiera uno
		
		if (contextoXML==null)
			throw new RuntimeException("Contexto no ha sido inicializado correctamente");
			
		respuesta = contextoXML.getBean(beanId);
		
		if (respuesta==null)
			throw new RuntimeException("Nombre de Bean incorrecto de objeto Spring");			
		return respuesta;
	}
	
	public static void inicializaConArchivosXML(String[] arr)
	{
		/*   Este m�todo inicializa el contexto con archivos XML indicados
		 *   por par�metro. Esto m�todo se usa para poder crear un contexto SPRING
		 *   desde un simple programa que tenga un main() para poder hacer ejecuciones
		 *   r�pidas desde un ambiente de pruebas
		 */
		 unico = new SpringWebApplicationContext();
		 unico.contextoXML = new FileSystemXmlApplicationContext(arr);
	}
}

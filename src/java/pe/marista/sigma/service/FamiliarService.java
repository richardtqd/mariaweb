package pe.marista.sigma.service;

import com.google.common.io.ByteStreams;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import pe.marista.sigma.MaristaConstantes;
import pe.marista.sigma.bean.FamiliaBean;
import pe.marista.sigma.bean.FamiliarBean;
import pe.marista.sigma.bean.FamiliarEstudianteBean;
import pe.marista.sigma.bean.PersonaBean;
import pe.marista.sigma.bean.UsuarioBean;
import pe.marista.sigma.bean.reporte.FamiliarRepBean;
import pe.marista.sigma.dao.FamiliaDAO;
import pe.marista.sigma.dao.FamiliarDAO;
import pe.marista.sigma.dao.PersonaDAO;
import pe.marista.sigma.util.MaristaUtils;
import pe.marista.sigma.util.MensajesBackEnd;

/**
 *
 * @author Administrador
 */
public class FamiliarService {

    private FamiliarDAO familiarDAO;
    private final UsuarioBean usuarioLogin;
    private PersonaDAO personaDAO;
    private FamiliaDAO familiaDAO;

    public FamiliarService() throws Exception {
        usuarioLogin = (UsuarioBean) new MaristaUtils().sesionObtenerObjeto("usuarioLogin");
    }

    //Logica de Negocio
    @Transactional
    public void insertarFamiliar(FamiliarBean familiarBean) throws Exception {
        familiarDAO.insertarFamiliar(familiarBean);
    }

    @Transactional
    public void modificarFamiliar(FamiliarBean familiarBean) throws Exception {
        familiarDAO.modificarFamiliar(familiarBean);
    }

    @Transactional
    public void eliminarFamiliar(String idFamiliar) throws Exception {
        familiarDAO.eliminarFamiliar(idFamiliar);
    }

    public List<FamiliarBean> obtenerFamiliar() throws Exception {
        return familiarDAO.obtenerFamiliar();
    }

    public List<PersonaBean> obtenerFamiliarPersonaPorFiltro(FamiliarBean familiarBean) throws Exception {
        return familiarDAO.obtenerFamiliarPersonaPorFiltro(familiarBean);
    }

    public List<FamiliarBean> obtenerFamiliarPorFiltro(FamiliarBean familiarBean) throws Exception {
        return familiarDAO.obtenerFamiliarPorFiltro(familiarBean);
    }

    public FamiliarBean obtenerFamiliarPorId(PersonaBean personaBean) throws Exception {
        return familiarDAO.obtenerFamiliarPorId(personaBean);
    }

    public FamiliarBean obtenerResPagoPorId(PersonaBean personaBean) throws Exception {
        return familiarDAO.obtenerResPagoPorId(personaBean);
    }

    public List<FamiliarEstudianteBean> obtenerFamiliarEstPorEst(String idEstudiante) throws Exception {
        return familiarDAO.obtenerFamiliarEstPorEst(idEstudiante);
    }

    public List<FamiliarRepBean> obtenerFamiliarRep(String idEstudiante, String idFamiliar, String tipo, String uniNeg) throws Exception {
        return familiarDAO.obtenerFamiliarRep(idEstudiante, idFamiliar, tipo, uniNeg);
    }

    public List<FamiliarEstudianteBean> obtenerFamiliarEstPorEstSinPadres(String idEstudiante) throws Exception {
        return familiarDAO.obtenerFamiliarEstPorEstSinPadres(idEstudiante);
    }

    @Transactional
    public void guardarPadres(FamiliarEstudianteBean padreEstudianteBean, FamiliarEstudianteBean madreEstudianteBean) throws Exception {

        //Papa
        padreEstudianteBean.getFamiliarBean().getPersonaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
        padreEstudianteBean.getFamiliarBean().getPersonaBean().setCreaFecha(new Date());
        padreEstudianteBean.getFamiliarBean().getPersonaBean().setCreaPor(usuarioLogin.getUsuario());
        padreEstudianteBean.getFamiliarBean().setCreaFecha(new Date());
        padreEstudianteBean.getFamiliarBean().setCreaPor(usuarioLogin.getUsuario());
        padreEstudianteBean.setCreaFecha(new Date());
        padreEstudianteBean.setCreaPor(usuarioLogin.getUsuario());
        PersonaBean personaPadre = personaDAO.obtenerPersPorId(padreEstudianteBean.getFamiliarBean().getPersonaBean());
        madreEstudianteBean.getFamiliarBean().getPersonaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
        madreEstudianteBean.getFamiliarBean().getPersonaBean().setCreaFecha(new Date());
        madreEstudianteBean.getFamiliarBean().getPersonaBean().setCreaPor(usuarioLogin.getUsuario());
        madreEstudianteBean.getFamiliarBean().setCreaFecha(new Date());
        madreEstudianteBean.getFamiliarBean().setCreaPor(usuarioLogin.getUsuario());
        madreEstudianteBean.setCreaFecha(new Date());
        madreEstudianteBean.setCreaPor(usuarioLogin.getUsuario());
        PersonaBean personaMadre = personaDAO.obtenerPersPorId(madreEstudianteBean.getFamiliarBean().getPersonaBean());
        if (!padreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona().equals(madreEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona())) {
            if (personaPadre == null) {
                if (padreEstudianteBean.getFamiliarBean().getPersonaBean().getCreaFecha() != null) {
                    personaDAO.insertarPersona(padreEstudianteBean.getFamiliarBean().getPersonaBean());
                }
                familiarDAO.insertarFamiliar(padreEstudianteBean.getFamiliarBean());
                familiarDAO.insertarFamiliarEstudiante(padreEstudianteBean);
            } else {
                personaDAO.modificarPersona(padreEstudianteBean.getFamiliarBean().getPersonaBean());
            }
            FamiliarBean familiarPadre = familiarDAO.obtenerFamiliarPorId(padreEstudianteBean.getFamiliarBean().getPersonaBean());
            if (familiarPadre == null) {
                if (padreEstudianteBean.getFamiliarBean().getCreaFecha() != null) {
                    familiarDAO.insertarFamiliar(padreEstudianteBean.getFamiliarBean());
                }
            } else {
                System.out.println("ZZZ: " + padreEstudianteBean.getFamiliarBean().getDireccion());
                familiarDAO.modificarFamiliar(padreEstudianteBean.getFamiliarBean());
            }
            FamiliarEstudianteBean familiarEstudianteP = familiarDAO.obtenerFamiliarEstPorId(padreEstudianteBean);
            if (familiarEstudianteP == null) {
                if (padreEstudianteBean.getCreaFecha() != null) {
                    familiarDAO.insertarFamiliarEstudiante(padreEstudianteBean);
                }
            } else {
                familiarDAO.modificarFamiliarEstudiante(padreEstudianteBean);
            }

        } else {
            throw new Exception(MensajesBackEnd.getValueOfKey("errorDupliPadres", null));
        }
        //Mama
        if (personaMadre == null) {
            if (madreEstudianteBean.getFamiliarBean().getPersonaBean().getCreaFecha() != null) {
                personaDAO.insertarPersona(madreEstudianteBean.getFamiliarBean().getPersonaBean());
            } else {
                personaDAO.modificarPersona(madreEstudianteBean.getFamiliarBean().getPersonaBean());
            }
            familiarDAO.insertarFamiliar(madreEstudianteBean.getFamiliarBean());
            familiarDAO.insertarFamiliarEstudiante(madreEstudianteBean);
        } else {
            FamiliarBean familiarMadre = familiarDAO.obtenerFamiliarPorId(madreEstudianteBean.getFamiliarBean().getPersonaBean());
            if (familiarMadre == null) {
                if (madreEstudianteBean.getFamiliarBean().getCreaFecha() != null) {
                    familiarDAO.insertarFamiliar(madreEstudianteBean.getFamiliarBean());
                } else {
                    familiarDAO.modificarFamiliar(madreEstudianteBean.getFamiliarBean());
                }
            } else {
                FamiliarEstudianteBean familiarEstudianteM = familiarDAO.obtenerFamiliarEstPorId(madreEstudianteBean);
                if (familiarEstudianteM == null) {
                    if (madreEstudianteBean.getCreaFecha() != null) {
                        familiarDAO.insertarFamiliarEstudiante(madreEstudianteBean);
                    } else {
                        familiarDAO.modificarFamiliarEstudiante(madreEstudianteBean);
                    }
                }
            }
        }
    }

    //Familiar Estudiante
    @Transactional
    public void guardarFamiliarEstudianteRapido(FamiliarEstudianteBean familiarEstudianteBean) throws Exception {
        familiarEstudianteBean.getFamiliarBean().getPersonaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
        familiarEstudianteBean.getFamiliarBean().getPersonaBean().setCreaFecha(new Date());
        familiarEstudianteBean.getFamiliarBean().getPersonaBean().setCreaPor(usuarioLogin.getUsuario());
        familiarEstudianteBean.getFamiliarBean().setCreaFecha(new Date());
        familiarEstudianteBean.getFamiliarBean().setCreaPor(usuarioLogin.getUsuario());
        familiarEstudianteBean.setCreaFecha(new Date());
        familiarEstudianteBean.setCreaPor(usuarioLogin.getUsuario());
        PersonaBean persona = personaDAO.obtenerPersPorId(familiarEstudianteBean.getFamiliarBean().getPersonaBean());
        FamiliaBean familiaBean = new FamiliaBean();
        familiaBean.setIdFamilia(familiarEstudianteBean.getEstudianteBean().getFamiliaBean().getIdFamilia());
        familiaBean.getUnidadNegocioBean().setUniNeg(familiarEstudianteBean.getFamiliarBean().getPersonaBean().getUnidadNegocioBean().getUniNeg());
//        familiaBean = familiaDAO.obtenerFamiliaPorIdRapido(familiaBean);
        if (persona == null) {
            if (familiarEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersonaOld() == null) {
                personaDAO.insertarPersona(familiarEstudianteBean.getFamiliarBean().getPersonaBean());
                familiarDAO.insertarFamiliar(familiarEstudianteBean.getFamiliarBean());
                familiarDAO.insertarFamiliarEstudiante(familiarEstudianteBean);
//                familiaDAO.insertarFamilia(familiaBean);

            } else {
                personaDAO.modificarPersona(familiarEstudianteBean.getFamiliarBean().getPersonaBean());
                familiarDAO.modificarFamiliarRapido(familiarEstudianteBean.getFamiliarBean());
                familiarDAO.modificarFamiliarEstudianteRapido(familiarEstudianteBean);
//                familiaDAO.modificarFamilia(familiaBean);
            }

        } else {
            personaDAO.modificarPersona(familiarEstudianteBean.getFamiliarBean().getPersonaBean());
            FamiliarBean familiarPadre = familiarDAO.obtenerFamiliarPorId(familiarEstudianteBean.getFamiliarBean().getPersonaBean());
            if (familiarPadre == null) {
                if (familiarEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona() == null) {
                    familiarDAO.insertarFamiliar(familiarEstudianteBean.getFamiliarBean());
                } else {
                    familiarDAO.modificarFamiliarRapido(familiarEstudianteBean.getFamiliarBean());
                }
            } else {
                familiarDAO.modificarFamiliar(familiarEstudianteBean.getFamiliarBean());
                FamiliarEstudianteBean familiarEstudianteP = familiarDAO.obtenerFamiliarEstPorId(familiarEstudianteBean);
                if (familiarEstudianteP == null) {
                    if (familiarEstudianteBean.getCreaFecha() != null) {
                        familiarDAO.insertarFamiliarEstudiante(familiarEstudianteBean);
                    } else {
                        familiarDAO.modificarFamiliarEstudianteRapido(familiarEstudianteBean);
                    }
                } else {
                    familiarDAO.modificarFamiliarEstudianteRapido(familiarEstudianteBean);
                }
            }
        }
    }

    //Familiar Estudiante
    @Transactional
    public void guardarFamiliarEstudiante(FamiliarEstudianteBean familiarEstudianteBean) throws Exception {
        familiarEstudianteBean.getFamiliarBean().getPersonaBean().setUnidadNegocioBean(usuarioLogin.getPersonalBean().getUnidadNegocioBean());
        familiarEstudianteBean.getFamiliarBean().getPersonaBean().setCreaFecha(new Date());
        familiarEstudianteBean.getFamiliarBean().getPersonaBean().setCreaPor(usuarioLogin.getUsuario());
        familiarEstudianteBean.getFamiliarBean().setCreaFecha(new Date());
        familiarEstudianteBean.getFamiliarBean().setCreaPor(usuarioLogin.getUsuario());
        familiarEstudianteBean.setCreaFecha(new Date());
        familiarEstudianteBean.setCreaPor(usuarioLogin.getUsuario());
        familiarEstudianteBean.getFamiliarBean().setDniEstudiante(familiarEstudianteBean.getFamiliarBean().getPersonaBean().getNroDoc());
        PersonaBean persona = personaDAO.obtenerPersPorId(familiarEstudianteBean.getFamiliarBean().getPersonaBean());
        if (persona == null) {
            if (familiarEstudianteBean.getFamiliarBean().getPersonaBean().getCreaFecha() != null) {
                personaDAO.insertarPersona(familiarEstudianteBean.getFamiliarBean().getPersonaBean());
            } else {
                personaDAO.modificarPersona(familiarEstudianteBean.getFamiliarBean().getPersonaBean());
            }
            familiarDAO.insertarFamiliar(familiarEstudianteBean.getFamiliarBean());
            if (familiarEstudianteBean.getEstudianteBean().getPersonaBean().getIdPersona() != null) {
                familiarDAO.insertarFamiliarEstudiante(familiarEstudianteBean);
            }
           familiarDAO.eliminarFamiliar(familiarEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersonaOld());
        } else {
            personaDAO.modificarPersona(familiarEstudianteBean.getFamiliarBean().getPersonaBean());
            FamiliarBean familiarPadre = familiarDAO.obtenerFamiliarPorId(familiarEstudianteBean.getFamiliarBean().getPersonaBean());
            if (familiarPadre == null) {
                if (familiarEstudianteBean.getFamiliarBean().getCreaFecha() != null) {
                    familiarDAO.insertarFamiliar(familiarEstudianteBean.getFamiliarBean());
                } else {
                    familiarDAO.modificarFamiliar(familiarEstudianteBean.getFamiliarBean());
                }
            } else {
                familiarDAO.modificarFamiliar(familiarEstudianteBean.getFamiliarBean());
                FamiliarEstudianteBean familiarEstudianteP = familiarDAO.obtenerFamiliarEstPorId(familiarEstudianteBean);
                if (familiarEstudianteP == null) {
                    if (familiarEstudianteBean.getCreaFecha() != null) {
                        if (familiarEstudianteBean.getEstudianteBean().getPersonaBean().getIdPersona() != null) {
                            familiarDAO.insertarFamiliarEstudiante(familiarEstudianteBean);
                        }
                    } else {
                        if (familiarEstudianteBean.getEstudianteBean().getPersonaBean().getIdPersona() != null) {
                            familiarDAO.modificarFamiliarEstudiante(familiarEstudianteBean);
                        }
                    }
                } else {
                    if (familiarEstudianteBean.getEstudianteBean().getPersonaBean().getIdPersona() != null) {
                        familiarDAO.modificarFamiliarEstudiante(familiarEstudianteBean);
                    }
                }
            }
        }
    }

    @Transactional
    public void modificarFamiliarEstudiante(FamiliarEstudianteBean familiarEstudianteBean) throws Exception {
        familiarDAO.modificarFamiliar(familiarEstudianteBean.getFamiliarBean());
        familiarDAO.modificarFamiliarEstudiante(familiarEstudianteBean);
    }

    @Transactional
    public void eliminarFamiliarEst(FamiliarEstudianteBean familiarEstudianteBean) throws Exception {
//        eliminarArchivo(familiarEstudianteBean.getFamiliarBean());
//        familiarDAO.eliminarFamiliarCentroTraPorFam(familiarEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());
        familiarDAO.eliminarFamiliarEst(familiarEstudianteBean);
//        familiarDAO.eliminarFamiliar(familiarEstudianteBean.getFamiliarBean().getPersonaBean().getIdPersona());

    }

    @Transactional
    public void eliminarFamiliarEstudiantePorFam(String idFamiliar) throws Exception {
        familiarDAO.eliminarFamiliarEstudiantePorFam(idFamiliar);
    }

    public FamiliarEstudianteBean obtenerFamiliarPorParentesco(FamiliarEstudianteBean familiarEstudianteBean) throws Exception {
        return familiarDAO.obtenerFamiliarPorParentesco(familiarEstudianteBean);
    }

    public FamiliarEstudianteBean obtenerFamiliarEstPorId(FamiliarEstudianteBean familiarEstudianteBean) throws Exception {
        return familiarDAO.obtenerFamiliarEstPorId(familiarEstudianteBean);
    }

    public void guardarArchivo(FamiliarBean familiarBean) throws Exception {
        InputStream inputStream = familiarBean.getFile().getInputstream();
//        byte[] bytes = IOUtils.toByteArray(inputStream);
        byte[] bytes = ByteStreams.toByteArray(inputStream);
        StringBuilder rutaFoto = new StringBuilder();
        rutaFoto.append("\\").append(MaristaConstantes.RUTA_DOCU_FAM).append(familiarBean.getPersonaBean().getNroDoc()).append(".jpg");
        StringBuilder rutaGral = new StringBuilder();
        rutaGral.append(MaristaUtils.obtenerRealPath()).append(rutaFoto);
        File foto = new File(rutaGral.toString());
        FileOutputStream miArchivo = new FileOutputStream(foto);
        miArchivo.write(bytes);
        miArchivo.close();
        familiarBean.setFoto(rutaFoto.toString());
        String cadena = MaristaUtils.obtenerRealPath().toString().substring(0, MaristaUtils.obtenerRealPath().length() - 9);
        File foto2 = new File(cadena + "\\web\\" + MaristaConstantes.RUTA_DOCU_FAM + familiarBean.getPersonaBean().getNroDoc() + ".jpg");
        FileOutputStream miArchivo2 = new FileOutputStream(foto2);
        miArchivo2.write(bytes);
        miArchivo2.close();
        inputStream.close();
    }

    public void eliminarArchivo(FamiliarBean familiarBean) throws Exception {
//        InputStream inputStream = familiarBean.getFile().getInputstream();
//        byte[] bytes = IOUtils.toByteArray(inputStream);
//        byte[] bytes = ByteStreams.toByteArray(inputStream);
        StringBuilder rutaFoto = new StringBuilder();
        rutaFoto.append("\\").append(MaristaConstantes.RUTA_DOCU_FAM).append(familiarBean.getPersonaBean().getNroDoc()).append(".jpg");
        StringBuilder rutaGral = new StringBuilder();
        rutaGral.append(MaristaUtils.obtenerRealPath()).append(rutaFoto);
        File foto = new File(rutaGral.toString());
        foto.delete();
//        FileOutputStream miArchivo = new FileOutputStream(foto);
//        miArchivo.write(bytes);
//        miArchivo.close();
        familiarBean.setFoto(rutaFoto.toString());
        String cadena = MaristaUtils.obtenerRealPath().toString().substring(0, MaristaUtils.obtenerRealPath().length() - 9);
        File foto2 = new File(cadena + "\\web\\" + MaristaConstantes.RUTA_DOCU_FAM + familiarBean.getPersonaBean().getNroDoc() + ".jpg");
        foto2.delete();
//        FileOutputStream miArchivo2 = new FileOutputStream(foto2);
//        miArchivo2.write(bytes);
//        miArchivo2.close();
//        inputStream.close();        
    }
//Geter y Setter

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

    public FamiliaDAO getFamiliaDAO() {
        return familiaDAO;
    }

    public void setFamiliaDAO(FamiliaDAO familiaDAO) {
        this.familiaDAO = familiaDAO;
    }

    public void modificarFamiliarRapido(FamiliarBean familiarBean) throws Exception {
        familiarDAO.modificarFamiliarRapido(familiarBean);
    }

    public void modificarFamiliarEstudianteRapido(FamiliarEstudianteBean familiarEstudianteBean) throws Exception {
        familiarDAO.modificarFamiliarEstudianteRapido(familiarEstudianteBean);
    }

    public void modificarFamiliarEstudianteStatus(FamiliarEstudianteBean familiarEstudianteBean) throws Exception {
        familiarDAO.modificarFamiliarEstudianteStatus(familiarEstudianteBean);
    }

    public FamiliarEstudianteBean obtenerFamiliarEstPorFamiliar(String idEstudiante) throws Exception {
        return familiarDAO.obtenerFamiliarEstPorFamiliar(idEstudiante);
    }

    public List<FamiliarEstudianteBean> obtenerFamiliarEstPorEstRapido(String idEstudiante) throws Exception {
        return familiarDAO.obtenerFamiliarEstPorEstRapido(idEstudiante);
    }

    public void modificarDniEstudianteFamEst(FamiliarEstudianteBean familiarEstudianteBean) throws Exception {
        familiarDAO.modificarDniEstudianteFamEst(familiarEstudianteBean);
    }
 

}

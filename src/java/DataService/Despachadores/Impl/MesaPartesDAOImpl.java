/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataService.Despachadores.Impl;

import BusinessServices.Beans.BeanMesaPartes;
import BusinessServices.Beans.BeanMsgerr;
import DataService.Despachadores.MsgerrDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import DataService.Despachadores.MesaPartesDAO;

/**
 *
 * @author H-URBINA-M
 */
public class MesaPartesDAOImpl implements MesaPartesDAO {

    private final Connection objConnection;
    private List lista;
    private String sql;
    private ResultSet objResultSet;
    private BeanMesaPartes objBnMesaParte;
    private PreparedStatement objPreparedStatement;
    private MsgerrDAO objDsMsgerr;
    private BeanMsgerr objBnMsgerr;
    private int s = 0;

    public MesaPartesDAOImpl(Connection objConnection1) {
        objConnection = objConnection1;
    }

    @Override
    public List getListaMesaPartes(BeanMesaPartes objBeanMesaParte, String usuario) {
        lista = new LinkedList<>();
        sql = "SELECT LPAD(NMESA_PARTE_CODIGO,5,0) AS CODIGO, PK_UTIL.FUN_DOCUMENTO(NDOCUMENTO_CODIGO)||'-'||VMESA_PARTE_NUMERO AS NUMERO, "
                + "REPLACE(REGEXP_REPLACE(UPPER(VMESA_PARTE_ASUNTO),'''',''),'\n"
                + "', ' ') AS ASUNTO, PK_UTIL.FUN_PRIORIDAD(NPRIORIDAD_CODIGO) AS PRIORIDAD, "
                + "PK_UTIL.FUN_INSTITUCION(NINSTITUCION_CODIGO) AS INSTITUCION, TO_DATE(DMESA_PARTE_FECHA+1) AS FECHA_MESA_PARTE, "
                + "PK_UTIL.FUN_DESCRIPCION_ESTADO(CESTADO_CODIGO) AS ESTADO, "
                + "REPLACE(REGEXP_REPLACE(UPPER(VMESA_PARTE_POST_FIRMA),'''',''),'\n"
                + "', ' ') AS POST_FIRMA,  "
                + "NMESA_PARTE_LEGAJOS AS LEGAJO, NMESA_PARTE_FOLIOS AS FOLIO, "
                + "PK_UTIL.FUN_MESA_PARTE_USUARIO_RESPONS(CPERIODO_CODIGO, CMESA_PARTE_TIPO, NMESA_PARTE_CODIGO) USUARIO_RESPONSABLE, "
                + "VMESA_PARTE_DIGITAL AS MESA_PARTE "
                + "FROM OPREFA_MESA_PARTES WHERE "
                + "CPERIODO_CODIGO=? AND "
                + "CMES_CODIGO=? AND "
                + "CMESA_PARTE_TIPO=? AND "
                + "TO_CHAR(DMESA_PARTE_RECEPCION,'DD')=LPAD(?,2,0) "
                + "ORDER BY CODIGO DESC";
        try {
            objPreparedStatement = objConnection.prepareStatement(sql);
            objPreparedStatement.setString(1, objBeanMesaParte.getPeriodo());
            objPreparedStatement.setString(2, objBeanMesaParte.getMes());
            objPreparedStatement.setString(3, objBeanMesaParte.getTipo());
            objPreparedStatement.setString(4, objBeanMesaParte.getNumero());
            objResultSet = objPreparedStatement.executeQuery();
            while (objResultSet.next()) {
                objBnMesaParte = new BeanMesaPartes();
                objBnMesaParte.setNumero(objResultSet.getString("CODIGO"));
                objBnMesaParte.setNumeroDocumento(objResultSet.getString("NUMERO"));
                objBnMesaParte.setAsunto(objResultSet.getString("ASUNTO"));
                objBnMesaParte.setPrioridad(objResultSet.getString("PRIORIDAD"));
                objBnMesaParte.setInstitucion(objResultSet.getString("INSTITUCION"));
                objBnMesaParte.setFecha(objResultSet.getDate("FECHA_MESA_PARTE"));
                objBnMesaParte.setEstado(objResultSet.getString("ESTADO"));
                objBnMesaParte.setPostFirma(objResultSet.getString("POST_FIRMA"));
                objBnMesaParte.setLegajo(objResultSet.getInt("LEGAJO"));
                objBnMesaParte.setFolio(objResultSet.getInt("FOLIO"));
                objBnMesaParte.setUsuarioResponsable(objResultSet.getString("USUARIO_RESPONSABLE"));
                objBnMesaParte.setArchivo(objResultSet.getString("MESA_PARTE"));
                lista.add(objBnMesaParte);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener getListaMesaPartes(objBeanMesaParte) : " + e.getMessage());
        } finally {
            try {
                if (objResultSet != null) {
                    objResultSet.close();
                    objPreparedStatement.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return lista;
    }

    @Override
    public List getListaMesaPartesConsulta(BeanMesaPartes objBeanMesaParte, String usuario) {
        lista = new LinkedList<>();
        sql = "SELECT LPAD(NMESA_PARTE_CODIGO,5,0) AS CODIGO, PK_UTIL.FUN_DOCUMENTO(NDOCUMENTO_CODIGO)||'-'||VMESA_PARTE_NUMERO AS NUMERO, "
                + "REPLACE(REGEXP_REPLACE(UPPER(VMESA_PARTE_ASUNTO),'''',''),'\n"
                + "', ' ') AS ASUNTO, PK_UTIL.FUN_PRIORIDAD(NPRIORIDAD_CODIGO) AS PRIORIDAD, "
                + "PK_UTIL.FUN_INSTITUCION(NINSTITUCION_CODIGO) AS INSTITUCION, "
                + "TO_DATE(DMESA_PARTE_FECHA+1) AS FECHA_MESA_PARTE, TO_DATE(DMESA_PARTE_RECEPCION+1) AS FECHA_RECEPCION, "
                + "PK_UTIL.FUN_DESCRIPCION_ESTADO(CESTADO_CODIGO) AS ESTADO, "
                + "REPLACE(REGEXP_REPLACE(UPPER(VMESA_PARTE_POST_FIRMA),'''',''),'\n"
                + "', ' ') AS POST_FIRMA,  "
                + "NMESA_PARTE_LEGAJOS AS LEGAJO, NMESA_PARTE_FOLIOS AS FOLIO,"
                + "PK_UTIL.FUN_MESA_PARTE_USUARIO_RESPONS(CPERIODO_CODIGO, CMESA_PARTE_TIPO, NMESA_PARTE_CODIGO) AS USUARIO_RESPONABLE, "
                + "VMESA_PARTE_DIGITAL AS MESA_PARTE "
                + "FROM OPREFA_MESA_PARTES WHERE "
                + "CPERIODO_CODIGO=? AND "
                + "CMES_CODIGO LIKE ? AND "
                + "CMESA_PARTE_TIPO=? "
                + "ORDER BY CODIGO DESC";
        try {
            objPreparedStatement = objConnection.prepareStatement(sql);
            objPreparedStatement.setString(1, objBeanMesaParte.getPeriodo());
            objPreparedStatement.setString(2, objBeanMesaParte.getMes().replace("00", "%"));
            objPreparedStatement.setString(3, objBeanMesaParte.getTipo());
            objResultSet = objPreparedStatement.executeQuery();
            while (objResultSet.next()) {
                objBnMesaParte = new BeanMesaPartes();
                objBnMesaParte.setNumero(objResultSet.getString("CODIGO"));
                objBnMesaParte.setNumeroDocumento(objResultSet.getString("NUMERO"));
                objBnMesaParte.setAsunto(objResultSet.getString("ASUNTO"));
                objBnMesaParte.setInstitucion(objResultSet.getString("INSTITUCION"));
                objBnMesaParte.setPrioridad(objResultSet.getString("PRIORIDAD"));
                objBnMesaParte.setFecha(objResultSet.getDate("FECHA_MESA_PARTE"));
                objBnMesaParte.setEstado(objResultSet.getString("ESTADO"));
                objBnMesaParte.setFechaRecepcion(objResultSet.getDate("FECHA_RECEPCION"));
                objBnMesaParte.setPostFirma(objResultSet.getString("POST_FIRMA"));
                objBnMesaParte.setLegajo(objResultSet.getInt("LEGAJO"));
                objBnMesaParte.setFolio(objResultSet.getInt("FOLIO"));
                objBnMesaParte.setUsuarioResponsable(objResultSet.getString("USUARIO_RESPONABLE"));
                objBnMesaParte.setArchivo(objResultSet.getString("MESA_PARTE"));
                lista.add(objBnMesaParte);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener getListaMesaPartesConsulta(objBeanMesaParte) : " + e.getMessage());
        } finally {
            try {
                if (objResultSet != null) {
                    objResultSet.close();
                    objPreparedStatement.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return lista;
    }

    @Override
    public BeanMesaPartes getMesaParte(BeanMesaPartes objBeanMesaParte, String usuario) {
        sql = "SELECT LPAD(NMESA_PARTE_CODIGO,5,0) AS NUMERO, NINSTITUCION_CODIGO,"
                + "PK_UTIL.FUN_INSTITUCION(NINSTITUCION_CODIGO) AS INSTITUCION, "
                + "NPRIORIDAD_CODIGO, NDOCUMENTO_CODIGO, VMESA_PARTE_NUMERO, "
                + "NCLASIFICACION_DOCUMENTO_CODIG, DMESA_PARTE_FECHA, DMESA_PARTE_RECEPCION, "
                + "VMESA_PARTE_ASUNTO, VMESA_PARTE_POST_FIRMA, NMESA_PARTE_LEGAJOS, NMESA_PARTE_FOLIOS "
                + "FROM OPREFA_MESA_PARTES WHERE "
                + "CPERIODO_CODIGO=? AND  "
                + "CMES_CODIGO=? AND "
                + "CMESA_PARTE_TIPO=? AND  "
                + "NMESA_PARTE_CODIGO=? ";
        try {
            objPreparedStatement = objConnection.prepareStatement(sql);
            objPreparedStatement.setString(1, objBeanMesaParte.getPeriodo());
            objPreparedStatement.setString(2, objBeanMesaParte.getMes());
            objPreparedStatement.setString(3, objBeanMesaParte.getTipo());
            objPreparedStatement.setInt(4, Utiles.Utiles.checkNum(objBeanMesaParte.getNumero()));
            objResultSet = objPreparedStatement.executeQuery();
            if (objResultSet.next()) {
                objBeanMesaParte.setNumero(objResultSet.getString("NUMERO"));
                objBeanMesaParte.setInstitucion(objResultSet.getString("NINSTITUCION_CODIGO"));
                objBeanMesaParte.setReferencia(objResultSet.getString("INSTITUCION"));
                objBeanMesaParte.setPrioridad(objResultSet.getString("NPRIORIDAD_CODIGO"));
                objBeanMesaParte.setDocumento(objResultSet.getString("NDOCUMENTO_CODIGO"));
                objBeanMesaParte.setNumeroDocumento(objResultSet.getString("VMESA_PARTE_NUMERO"));
                objBeanMesaParte.setClasificacion(objResultSet.getString("NCLASIFICACION_DOCUMENTO_CODIG"));
                objBeanMesaParte.setFecha(objResultSet.getDate("DMESA_PARTE_FECHA"));
                objBeanMesaParte.setFechaRecepcion(objResultSet.getDate("DMESA_PARTE_RECEPCION"));
                objBeanMesaParte.setAsunto(objResultSet.getString("VMESA_PARTE_ASUNTO"));
                objBeanMesaParte.setPostFirma(objResultSet.getString("VMESA_PARTE_POST_FIRMA"));
                objBeanMesaParte.setLegajo(objResultSet.getInt("NMESA_PARTE_LEGAJOS"));
                objBeanMesaParte.setFolio(objResultSet.getInt("NMESA_PARTE_FOLIOS"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener getMesaParte(objBeanMesaParte) : " + e.getMessage());
        } finally {
            try {
                if (objResultSet != null) {
                    objResultSet.close();
                    objPreparedStatement.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return objBeanMesaParte;
    }

    @Override
    public String getNumeroMesaParte(BeanMesaPartes objBnMesaParte, String usuario) {
        String result = "00001";
        sql = "SELECT LPAD(NVL(MAX(NMESA_PARTE_CODIGO)+1,1),5,0) AS CODIGO "
                + "FROM OPREFA_MESA_PARTES WHERE "
                + "CPERIODO_CODIGO=? AND "
                + "CMESA_PARTE_TIPO=? ";
        try {
            objPreparedStatement = objConnection.prepareStatement(sql);
            objPreparedStatement.setString(1, objBnMesaParte.getPeriodo());
            objPreparedStatement.setString(2, objBnMesaParte.getTipo());
            objResultSet = objPreparedStatement.executeQuery();
            if (objResultSet.next()) {
                result = objResultSet.getString("CODIGO");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener getNumeroMesaParte(objBnMesaParte) : " + e.getMessage());
        } finally {
            try {
                if (objResultSet != null) {
                    objResultSet.close();
                    objPreparedStatement.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }

    @Override
    public String iduMesaParte(BeanMesaPartes objBeanMesaParte, String usuario) {
        String numero = "";
        sql = "{CALL SP_IDU_MESA_PARTES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        try (CallableStatement cs = objConnection.prepareCall(sql)) {
            cs.setString(1, objBeanMesaParte.getPeriodo());
            cs.setString(2, objBeanMesaParte.getTipo());
            cs.setString(3, objBeanMesaParte.getNumero());
            cs.setString(4, objBeanMesaParte.getMes());
            cs.setString(5, objBeanMesaParte.getInstitucion());
            cs.setString(6, objBeanMesaParte.getPrioridad());
            cs.setString(7, objBeanMesaParte.getDocumento());
            cs.setString(8, objBeanMesaParte.getNumeroDocumento());
            cs.setString(9, objBeanMesaParte.getClasificacion());
            cs.setDate(10, objBeanMesaParte.getFecha());
            cs.setString(11, objBeanMesaParte.getAsunto());
            cs.setString(12, objBeanMesaParte.getPostFirma());
            cs.setInt(13, objBeanMesaParte.getLegajo());
            cs.setInt(14, objBeanMesaParte.getFolio());
            cs.setString(15, objBeanMesaParte.getArchivo());
            cs.setString(16, objBeanMesaParte.getCorreo());
            cs.setString(17, usuario);
            cs.setString(18, objBeanMesaParte.getMode().toUpperCase());
            cs.registerOutParameter(19, java.sql.Types.NUMERIC);
            s = cs.executeUpdate();
            numero = "" + cs.getInt(19);
            cs.close();
        } catch (SQLException e) {
            System.out.println("Error al ejecutar iduMesaParte : " + e.getMessage());
            objDsMsgerr = new MsgerrDAOImpl(objConnection);
            objBnMsgerr = new BeanMsgerr();
            objBnMsgerr.setUsuario(usuario);
            objBnMsgerr.setTabla("OPREFA_MESA_PARTES");
            objBnMsgerr.setTipo(objBeanMesaParte.getMode().toUpperCase());
            objBnMsgerr.setDescripcion(e.getMessage());
            s = objDsMsgerr.iduMsgerr(objBnMsgerr);
            return "0";
        }
        return numero;
    }

    @Override
    public List getListaRemisionMesaParte(BeanMesaPartes objBeanMesaParte, String usuario) {
        lista = new LinkedList<>();
        sql = "SELECT CMESA_PARTE_NUMERO AS CODIGO, "
                + "PK_UTIL.FUN_NOMBRE_TIPO_MESA_PARTE(NTIPO_MESA_PARTE_CODIGO)||'-'||VMESA_PARTE_NUMERO AS NUMERO, "
                + "VMESA_PARTE_ASUNTO AS ASUNTO, PK_UTIL.FUN_NOMBRE_PRIORIDAD(NPRIORIDAD_CODIGO) AS PRIORIDAD, "
                + "PK_UTIL.FUN_INSTITUCION(NINSTITUCION_CODIGO) AS INSTITUCION, "
                + "DMESA_PARTE_FECHA AS FECHA_MESA_PARTE, PK_UTIL.FUN_DESCRIPCION_ESTADO(CESTADO_CODIGO) AS ESTADO,"
                + "VMESA_PARTE_POST_FIRMA AS FIRMA, NMESA_PARTE_LEGAJOS AS LEGAJO, NMESA_PARTE_FOLIOS AS FOLIO,"
                + "PK_UTIL.FUN_MESA_PARTE_RESPUESTA(CPERIODO_CODIGO, CMESA_PARTE_TIPO,CMESA_PARTE_NUMERO) AS DOC_RESPUESTA,"
                + "VMESA_PARTE_DIGITAL AS MESA_PARTE  "
                + "FROM OPREFA_MESA_PARTES WHERE "
                + "CPERIODO_CODIGO=? AND "
                + "CMES_CODIGO=? AND "
                + "CMESA_PARTE_TIPO=? AND "
                + "VUSUARIO_CREADOR=? AND "
                + "CESTADO_CODIGO NOT IN ('AN')"
                + "ORDER BY ESTADO DESC";
        try {
            objPreparedStatement = objConnection.prepareStatement(sql);
            objPreparedStatement.setString(1, objBeanMesaParte.getPeriodo());
            objPreparedStatement.setString(2, objBeanMesaParte.getMes());
            objPreparedStatement.setString(3, objBeanMesaParte.getTipo());
            objPreparedStatement.setString(4, usuario);
            objResultSet = objPreparedStatement.executeQuery();
            while (objResultSet.next()) {
                objBnMesaParte = new BeanMesaPartes();
                objBnMesaParte.setNumero(objResultSet.getString("CODIGO"));
                objBnMesaParte.setNumeroDocumento(objResultSet.getString("NUMERO"));
                objBnMesaParte.setAsunto(objResultSet.getString("ASUNTO"));
                objBnMesaParte.setPrioridad(objResultSet.getString("PRIORIDAD"));
                objBnMesaParte.setFecha(objResultSet.getDate("FECHA_MESA_PARTE"));
                objBnMesaParte.setEstado(objResultSet.getString("ESTADO"));
                objBnMesaParte.setPostFirma(objResultSet.getString("FIRMA"));
                objBnMesaParte.setLegajo(objResultSet.getInt("LEGAJO"));
                objBnMesaParte.setFolio(objResultSet.getInt("FOLIO"));
                objBnMesaParte.setReferencia(objResultSet.getString("DOC_RESPUESTA"));
                objBnMesaParte.setArchivo(objResultSet.getString("MESA_PARTE"));
                lista.add(objBnMesaParte);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener getListaRemisionMesaParte(objBeanMesaParte) : " + e.getMessage());
        } finally {
            try {
                if (objResultSet != null) {
                    objResultSet.close();
                    objPreparedStatement.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return lista;
    }

    @Override
    public String getNumeroMesaParteSalida(BeanMesaPartes objBnMesaParte, String usuario) {
        String result = "00001";
        sql = "SELECT LPAD(NVL(MAX(CNUMERO_MESA_PARTE)+1,1),5,0) AS CODIGO "
                + "FROM OPREFA_MESA_PARTES WHERE "
                + "CPERIODO_CODIGO = ? AND "
                + "NTIPO_MESA_PARTE_CODIGO = ? ";
        try {
            objPreparedStatement = objConnection.prepareStatement(sql);
            objPreparedStatement.setString(1, objBnMesaParte.getPeriodo());
            objPreparedStatement.setString(2, objBnMesaParte.getDocumento());
            objResultSet = objPreparedStatement.executeQuery();
            if (objResultSet.next()) {
                result = objResultSet.getString("CODIGO");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener getNumeroMesaParteSalida(objBnMesaParte) : " + e.getMessage());
        } finally {
            try {
                if (objResultSet != null) {
                    objResultSet.close();
                    objPreparedStatement.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }

}

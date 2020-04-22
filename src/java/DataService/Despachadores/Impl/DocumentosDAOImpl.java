/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataService.Despachadores.Impl;

import BusinessServices.Beans.BeanDocumentos;
import BusinessServices.Beans.BeanMsgerr;
import DataService.Despachadores.DocumentosDAO;
import DataService.Despachadores.MsgerrDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author H-URBINA-M
 */
public class DocumentosDAOImpl implements DocumentosDAO {

    private final Connection objConnection;
    private List lista;
    private String sql;
    private ResultSet objResultSet;
    private BeanDocumentos objBnDocumento;
    private PreparedStatement objPreparedStatement;
    private MsgerrDAO objDsMsgerr;
    private BeanMsgerr objBnMsgerr;
    private int s = 0;

    public DocumentosDAOImpl(Connection objConnection1) {
        objConnection = objConnection1;
    }

    @Override
    public List getListaDocumentos(BeanDocumentos objBeanDocumento, String usuario) {
        lista = new LinkedList<>();
        sql = "SELECT LPAD(NDOCUMENTO_CODIGO,5,0) AS CODIGO, PK_UTIL.FUN_TIPO_DOCUMENTO(NTIPO_DOCUMENTO_CODIGO)||'-'||VDOCUMENTO_NUMERO AS NUMERO, "
                + "REPLACE(REGEXP_REPLACE(UPPER(VDOCUMENTO_ASUNTO),'''',''),'\n"
                + "', ' ') AS ASUNTO, PK_UTIL.FUN_PRIORIDAD(NPRIORIDAD_CODIGO) AS PRIORIDAD, "
                + "PK_UTIL.FUN_INSTITUCION(NINSTITUCION_CODIGO) AS INSTITUCION, TO_DATE(DDOCUMENTO_FECHA+1) AS FECHA_DOCUMENTO, "
                + "PK_UTIL.FUN_DESCRIPCION_ESTADO(CESTADO_CODIGO) AS ESTADO, "
                + "REPLACE(REGEXP_REPLACE(UPPER(VDOCUMENTO_POST_FIRMA),'''',''),'\n"
                + "', ' ') AS POST_FIRMA,  "
                + "NDOCUMENTO_LEGAJOS AS LEGAJO, NDOCUMENTO_FOLIOS AS FOLIO, "
                + "PK_UTIL.FUN_DOCUMENTO_USUARIO_RESPONSA(CPERIODO_CODIGO, CDOCUMENTO_TIPO, NDOCUMENTO_CODIGO) USUARIO_RESPONSABLE, "
                + "VDOCUMENTO_DIGITAL AS DOCUMENTO "
                + "FROM OPREFA_DOCUMENTOS WHERE "
                + "CPERIODO_CODIGO=? AND "
                + "CMES_CODIGO=? AND "
                + "CDOCUMENTO_TIPO=? AND "
                + "TO_CHAR(DDOCUMENTO_RECEPCION,'DD')=LPAD(?,2,0) "
                + "ORDER BY CODIGO DESC";
        try {
            objPreparedStatement = objConnection.prepareStatement(sql);
            objPreparedStatement.setString(1, objBeanDocumento.getPeriodo());
            objPreparedStatement.setString(2, objBeanDocumento.getMes());
            objPreparedStatement.setString(3, objBeanDocumento.getTipo());
            objPreparedStatement.setString(4, objBeanDocumento.getNumero());
            objResultSet = objPreparedStatement.executeQuery();
            while (objResultSet.next()) {
                objBnDocumento = new BeanDocumentos();
                objBnDocumento.setNumero(objResultSet.getString("CODIGO"));
                objBnDocumento.setNumeroDocumento(objResultSet.getString("NUMERO"));
                objBnDocumento.setAsunto(objResultSet.getString("ASUNTO"));
                objBnDocumento.setPrioridad(objResultSet.getString("PRIORIDAD"));
                objBnDocumento.setInstitucion(objResultSet.getString("INSTITUCION"));
                objBnDocumento.setFecha(objResultSet.getDate("FECHA_DOCUMENTO"));
                objBnDocumento.setEstado(objResultSet.getString("ESTADO"));
                objBnDocumento.setPostFirma(objResultSet.getString("POST_FIRMA"));
                objBnDocumento.setLegajo(objResultSet.getInt("LEGAJO"));
                objBnDocumento.setFolio(objResultSet.getInt("FOLIO"));
                objBnDocumento.setUsuarioResponsable(objResultSet.getString("USUARIO_RESPONSABLE"));
                objBnDocumento.setArchivo(objResultSet.getString("DOCUMENTO"));
                lista.add(objBnDocumento);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener getListaDocumentos(objBeanDocumento) : " + e.getMessage());
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
    public List getListaDocumentosConsulta(BeanDocumentos objBeanDocumento, String usuario) {
        lista = new LinkedList<>();
        sql = "SELECT LPAD(NDOCUMENTO_CODIGO,5,0) AS CODIGO, PK_UTIL.FUN_TIPO_DOCUMENTO(NTIPO_DOCUMENTO_CODIGO)||'-'||VDOCUMENTO_NUMERO AS NUMERO, "
                + "REPLACE(REGEXP_REPLACE(UPPER(VDOCUMENTO_ASUNTO),'''',''),'\n"
                + "', ' ') AS ASUNTO, PK_UTIL.FUN_PRIORIDAD(NPRIORIDAD_CODIGO) AS PRIORIDAD, "
                + "PK_UTIL.FUN_INSTITUCION(NINSTITUCION_CODIGO) AS INSTITUCION, "
                + "TO_DATE(DDOCUMENTO_FECHA+1) AS FECHA_DOCUMENTO, TO_DATE(DDOCUMENTO_RECEPCION+1) AS FECHA_RECEPCION, "
                + "PK_UTIL.FUN_DESCRIPCION_ESTADO(CESTADO_CODIGO) AS ESTADO, "
                + "REPLACE(REGEXP_REPLACE(UPPER(VDOCUMENTO_POST_FIRMA),'''',''),'\n"
                + "', ' ') AS POST_FIRMA,  "
                + "NDOCUMENTO_LEGAJOS AS LEGAJO, NDOCUMENTO_FOLIOS AS FOLIO,"
                + "PK_UTIL.FUN_DOCUMENTO_USUARIO_RESPONSA(CPERIODO_CODIGO, CDOCUMENTO_TIPO, NDOCUMENTO_CODIGO) AS USUARIO_RESPONABLE, "
                + "VDOCUMENTO_DIGITAL AS DOCUMENTO "
                + "FROM OPREFA_DOCUMENTOS WHERE "
                + "CPERIODO_CODIGO=? AND "
                + "CMES_CODIGO LIKE ? AND "
                + "CDOCUMENTO_TIPO=? "
                + "ORDER BY CODIGO DESC";
        try {
            objPreparedStatement = objConnection.prepareStatement(sql);
            objPreparedStatement.setString(1, objBeanDocumento.getPeriodo());
            objPreparedStatement.setString(2, objBeanDocumento.getMes().replace("00", "%"));
            objPreparedStatement.setString(3, objBeanDocumento.getTipo());
            objResultSet = objPreparedStatement.executeQuery();
            while (objResultSet.next()) {
                objBnDocumento = new BeanDocumentos();
                objBnDocumento.setNumero(objResultSet.getString("CODIGO"));
                objBnDocumento.setNumeroDocumento(objResultSet.getString("NUMERO"));
                objBnDocumento.setAsunto(objResultSet.getString("ASUNTO"));
                objBnDocumento.setInstitucion(objResultSet.getString("INSTITUCION"));
                objBnDocumento.setPrioridad(objResultSet.getString("PRIORIDAD"));
                objBnDocumento.setFecha(objResultSet.getDate("FECHA_DOCUMENTO"));
                objBnDocumento.setEstado(objResultSet.getString("ESTADO"));
                objBnDocumento.setFechaRecepcion(objResultSet.getDate("FECHA_RECEPCION"));
                objBnDocumento.setPostFirma(objResultSet.getString("POST_FIRMA"));
                objBnDocumento.setLegajo(objResultSet.getInt("LEGAJO"));
                objBnDocumento.setFolio(objResultSet.getInt("FOLIO"));
                objBnDocumento.setUsuarioResponsable(objResultSet.getString("USUARIO_RESPONABLE"));
                objBnDocumento.setArchivo(objResultSet.getString("DOCUMENTO"));
                lista.add(objBnDocumento);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener getListaDocumentosConsulta(objBeanDocumento) : " + e.getMessage());
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
    public BeanDocumentos getDocumento(BeanDocumentos objBeanDocumento, String usuario) {
        sql = "SELECT LPAD(NDOCUMENTO_CODIGO,5,0) AS NUMERO, NINSTITUCION_CODIGO,"
                + "PK_UTIL.FUN_INSTITUCION(NINSTITUCION_CODIGO) AS INSTITUCION, "
                + "NPRIORIDAD_CODIGO, NTIPO_DOCUMENTO_CODIGO, VDOCUMENTO_NUMERO, "
                + "NCLASIFICACION_DOCUMENTO_CODIG, DDOCUMENTO_FECHA, DDOCUMENTO_RECEPCION, "
                + "VDOCUMENTO_ASUNTO, VDOCUMENTO_POST_FIRMA, NDOCUMENTO_LEGAJOS, NDOCUMENTO_FOLIOS "
                + "FROM OPREFA_DOCUMENTOS WHERE "
                + "CPERIODO_CODIGO=? AND  "
                + "CMES_CODIGO=? AND "
                + "CDOCUMENTO_TIPO=? AND  "
                + "NDOCUMENTO_CODIGO=? ";
        try {
            objPreparedStatement = objConnection.prepareStatement(sql);
            objPreparedStatement.setString(1, objBeanDocumento.getPeriodo());
            objPreparedStatement.setString(2, objBeanDocumento.getMes());
            objPreparedStatement.setString(3, objBeanDocumento.getTipo());
            objPreparedStatement.setInt(4, Utiles.Utiles.checkNum(objBeanDocumento.getNumero()));
            objResultSet = objPreparedStatement.executeQuery();
            if (objResultSet.next()) {
                objBeanDocumento.setNumero(objResultSet.getString("NUMERO"));
                objBeanDocumento.setInstitucion(objResultSet.getString("NINSTITUCION_CODIGO"));
                objBeanDocumento.setReferencia(objResultSet.getString("INSTITUCION"));
                objBeanDocumento.setPrioridad(objResultSet.getString("NPRIORIDAD_CODIGO"));
                objBeanDocumento.setTipoDocumento(objResultSet.getString("NTIPO_DOCUMENTO_CODIGO"));
                objBeanDocumento.setNumeroDocumento(objResultSet.getString("VDOCUMENTO_NUMERO"));
                objBeanDocumento.setClasificacion(objResultSet.getString("NCLASIFICACION_DOCUMENTO_CODIG"));
                objBeanDocumento.setFecha(objResultSet.getDate("DDOCUMENTO_FECHA"));
                objBeanDocumento.setFechaRecepcion(objResultSet.getDate("DDOCUMENTO_RECEPCION"));
                objBeanDocumento.setAsunto(objResultSet.getString("VDOCUMENTO_ASUNTO"));
                objBeanDocumento.setPostFirma(objResultSet.getString("VDOCUMENTO_POST_FIRMA"));
                objBeanDocumento.setLegajo(objResultSet.getInt("NDOCUMENTO_LEGAJOS"));
                objBeanDocumento.setFolio(objResultSet.getInt("NDOCUMENTO_FOLIOS"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener getDocumento(objBeanDocumento) : " + e.getMessage());
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
        return objBeanDocumento;
    }

    @Override
    public String getNumeroDocumento(BeanDocumentos objBnDocumento, String usuario) {
        String result = "00001";
        sql = "SELECT LPAD(NVL(MAX(NDOCUMENTO_CODIGO)+1,1),5,0) AS CODIGO "
                + "FROM OPREFA_DOCUMENTOS WHERE "
                + "CPERIODO_CODIGO=? AND "
                + "CDOCUMENTO_TIPO=? ";
        try {
            objPreparedStatement = objConnection.prepareStatement(sql);
            objPreparedStatement.setString(1, objBnDocumento.getPeriodo());
            objPreparedStatement.setString(2, objBnDocumento.getTipo());
            objResultSet = objPreparedStatement.executeQuery();
            if (objResultSet.next()) {
                result = objResultSet.getString("CODIGO");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener getNumeroDocumento(objBnDocumento) : " + e.getMessage());
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
    public int iduDocumento(BeanDocumentos objBeanDocumento, String usuario) {
        sql = "{CALL SP_IDU_DOCUMENTOS(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        try (CallableStatement cs = objConnection.prepareCall(sql)) {
            cs.setString(1, objBeanDocumento.getPeriodo());
            cs.setString(2, objBeanDocumento.getTipo());
            cs.setString(3, objBeanDocumento.getNumero());
            cs.setString(4, objBeanDocumento.getMes());
            cs.setString(5, objBeanDocumento.getInstitucion());
            cs.setString(6, objBeanDocumento.getPrioridad());
            cs.setString(7, objBeanDocumento.getTipoDocumento());
            cs.setString(8, objBeanDocumento.getNumeroDocumento());
            cs.setString(9, objBeanDocumento.getClasificacion());
            cs.setDate(10, objBeanDocumento.getFecha());
            cs.setString(11, objBeanDocumento.getAsunto());
            cs.setString(12, objBeanDocumento.getPostFirma());
            cs.setInt(13, objBeanDocumento.getLegajo());
            cs.setInt(14, objBeanDocumento.getFolio());
            cs.setString(15, objBeanDocumento.getArchivo());
            cs.setString(16, usuario);
            cs.setString(17, objBeanDocumento.getMode().toUpperCase());
            s = cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            System.out.println("Error al ejecutar iduDocumento : " + e.getMessage());
            objDsMsgerr = new MsgerrDAOImpl(objConnection);
            objBnMsgerr = new BeanMsgerr();
            objBnMsgerr.setUsuario(usuario);
            objBnMsgerr.setTabla("OPREFA_DOCUMENTOS");
            objBnMsgerr.setTipo(objBeanDocumento.getMode().toUpperCase());
            objBnMsgerr.setDescripcion(e.getMessage());
            s = objDsMsgerr.iduMsgerr(objBnMsgerr);
            return 0;
        }
        return s;
    }

    @Override
    public List getListaRemisionDocumento(BeanDocumentos objBeanDocumento, String usuario) {
        lista = new LinkedList<>();
        sql = "SELECT CDOCUMENTO_NUMERO AS CODIGO, "
                + "PK_UTIL.FUN_NOMBRE_TIPO_DOCUMENTO(NTIPO_DOCUMENTO_CODIGO)||'-'||VDOCUMENTO_NUMERO AS NUMERO, "
                + "VDOCUMENTO_ASUNTO AS ASUNTO, PK_UTIL.FUN_NOMBRE_PRIORIDAD(NPRIORIDAD_CODIGO) AS PRIORIDAD, "
                + "PK_UTIL.FUN_INSTITUCION(NINSTITUCION_CODIGO) AS INSTITUCION, "
                + "DDOCUMENTO_FECHA AS FECHA_DOCUMENTO, PK_UTIL.FUN_DESCRIPCION_ESTADO(CESTADO_CODIGO) AS ESTADO,"
                + "VDOCUMENTO_POST_FIRMA AS FIRMA, NDOCUMENTO_LEGAJOS AS LEGAJO, NDOCUMENTO_FOLIOS AS FOLIO,"
                + "PK_UTIL.FUN_DOCUMENTO_RESPUESTA(CPERIODO_CODIGO, CDOCUMENTO_TIPO,CDOCUMENTO_NUMERO) AS DOC_RESPUESTA,"
                + "VDOCUMENTO_DIGITAL AS DOCUMENTO  "
                + "FROM OPREFA_DOCUMENTOS WHERE "
                + "CPERIODO_CODIGO=? AND "
                + "CMES_CODIGO=? AND "
                + "CDOCUMENTO_TIPO=? AND "
                + "VUSUARIO_CREADOR=? AND "
                + "CESTADO_CODIGO NOT IN ('AN')"
                + "ORDER BY ESTADO DESC";
        try {
            objPreparedStatement = objConnection.prepareStatement(sql);
            objPreparedStatement.setString(1, objBeanDocumento.getPeriodo());
            objPreparedStatement.setString(2, objBeanDocumento.getMes());
            objPreparedStatement.setString(3, objBeanDocumento.getTipo());
            objPreparedStatement.setString(4, usuario);
            objResultSet = objPreparedStatement.executeQuery();
            while (objResultSet.next()) {
                objBnDocumento = new BeanDocumentos();
                objBnDocumento.setNumero(objResultSet.getString("CODIGO"));
                objBnDocumento.setNumeroDocumento(objResultSet.getString("NUMERO"));
                objBnDocumento.setAsunto(objResultSet.getString("ASUNTO"));
                objBnDocumento.setPrioridad(objResultSet.getString("PRIORIDAD"));
                objBnDocumento.setFecha(objResultSet.getDate("FECHA_DOCUMENTO"));
                objBnDocumento.setEstado(objResultSet.getString("ESTADO"));
                objBnDocumento.setPostFirma(objResultSet.getString("FIRMA"));
                objBnDocumento.setLegajo(objResultSet.getInt("LEGAJO"));
                objBnDocumento.setFolio(objResultSet.getInt("FOLIO"));
                objBnDocumento.setReferencia(objResultSet.getString("DOC_RESPUESTA"));
                objBnDocumento.setArchivo(objResultSet.getString("DOCUMENTO"));
                lista.add(objBnDocumento);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener getListaRemisionDocumento(objBeanDocumento) : " + e.getMessage());
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
    public String getNumeroDocumentoSalida(BeanDocumentos objBnDocumento, String usuario) {
        String result = "00001";
        sql = "SELECT LPAD(NVL(MAX(CNUMERO_DOCUMENTO)+1,1),5,0) AS CODIGO "
                + "FROM SIPE_CORRELATIVO_DOCUMENTO WHERE "
                + "CPERIODO_CODIGO = ? AND "
                + "NTIPO_DOCUMENTO_CODIGO = ? ";
        try {
            objPreparedStatement = objConnection.prepareStatement(sql);
            objPreparedStatement.setString(1, objBnDocumento.getPeriodo());
            objPreparedStatement.setString(2, objBnDocumento.getTipoDocumento());
            objResultSet = objPreparedStatement.executeQuery();
            if (objResultSet.next()) {
                result = objResultSet.getString("CODIGO");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener getNumeroDocumentoSalida(objBnDocumento) : " + e.getMessage());
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

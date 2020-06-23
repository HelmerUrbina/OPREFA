/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataService.Despachadores.Impl;

import BusinessServices.Beans.BeanMesaPartes;
import BusinessServices.Beans.BeanMsgerr;
import DataService.Despachadores.DecretosDAO;
import DataService.Despachadores.MsgerrDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author H-URBINA-M
 */
public class DecretosDAOImpl implements DecretosDAO {

    private final Connection objConnection;
    private List lista;
    private String sql;
    private ResultSet objResultSet;
    private BeanMesaPartes objBnDecreto;
    private PreparedStatement objPreparedStatement;
    private MsgerrDAO objDsMsgerr;
    private BeanMsgerr objBnMsgerr;
    private int s = 0;

    public DecretosDAOImpl(Connection objConnection1) {
        objConnection = objConnection1;
    }

    @Override
    public List getListaDocumentosPendientes(BeanMesaPartes objBeanDecreto, String usuario) {
        lista = new LinkedList<>();
        sql = "SELECT LPAD(NMESA_PARTE_CODIGO,5,0) AS CODIGO, "
                + "PK_UTIL.FUN_DOCUMENTO(NDOCUMENTO_CODIGO)||'-'||VMESA_PARTE_NUMERO AS NUMERO, "
                + "REPLACE(REGEXP_REPLACE(UPPER(VMESA_PARTE_ASUNTO),'''',''),'\n"
                + "', ' ') AS ASUNTO, PK_UTIL.FUN_PRIORIDAD(NPRIORIDAD_CODIGO) AS PRIORIDAD, "
                + "PK_UTIL.FUN_INSTITUCION(NINSTITUCION_CODIGO) AS INSTITUCION, "
                + "TO_DATE(DMESA_PARTE_FECHA+1) AS FECHA_MESA_PARTE, TO_DATE(DMESA_PARTE_RECEPCION+1) AS FECHA_RECEPCION, "
                + "REGEXP_REPLACE(UPPER(VMESA_PARTE_POST_FIRMA),'''','') AS FIRMA, "
                + "VMESA_PARTE_DIGITAL AS MESA_PARTE "
                + "FROM OPREFA_MESA_PARTES WHERE "
                + "CPERIODO_CODIGO=? AND "
                + "CMES_CODIGO=? AND "
                + "CMESA_PARTE_TIPO=? AND "
                + "CESTADO_CODIGO='PE'"
                + "ORDER BY CODIGO DESC";
        try {
            objPreparedStatement = objConnection.prepareStatement(sql);
            objPreparedStatement.setString(1, objBeanDecreto.getPeriodo());
            objPreparedStatement.setString(2, objBeanDecreto.getMes());
            objPreparedStatement.setString(3, objBeanDecreto.getTipo());
            objResultSet = objPreparedStatement.executeQuery();
            while (objResultSet.next()) {
                objBnDecreto = new BeanMesaPartes();
                objBnDecreto.setNumero(objResultSet.getString("CODIGO"));
                objBnDecreto.setNumeroDocumento(objResultSet.getString("NUMERO"));
                objBnDecreto.setAsunto(objResultSet.getString("ASUNTO"));
                objBnDecreto.setPrioridad(objResultSet.getString("PRIORIDAD"));
                objBnDecreto.setInstitucion(objResultSet.getString("INSTITUCION"));
                objBnDecreto.setFecha(objResultSet.getDate("FECHA_MESA_PARTE"));
                objBnDecreto.setFechaRecepcion(objResultSet.getDate("FECHA_RECEPCION"));
                objBnDecreto.setPostFirma(objResultSet.getString("FIRMA"));
                objBnDecreto.setArchivo(objResultSet.getString("MESA_PARTE"));
                lista.add(objBnDecreto);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener getListaDocumentosPendientes(objBnDecreto) : " + e.getMessage());
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
    public List getListaDocumentosDecretados(BeanMesaPartes objBeanDecreto, String usuario) {
        lista = new LinkedList<>();
        sql = "SELECT LPAD(DOC.NMESA_PARTE_CODIGO,5,0) AS CODIGO, DEC.NMESA_PARTE_DECRETO_CODIGO AS DECRETO, "
                + "PK_UTIL.FUN_DOCUMENTO(DOC.NDOCUMENTO_CODIGO)||'-'||DOC.VMESA_PARTE_NUMERO AS NUMERO, "
                + "REPLACE(REGEXP_REPLACE(UPPER(DOC.VMESA_PARTE_ASUNTO),'''',''),'\n"
                + "', ' ') AS ASUNTO, PK_UTIL.FUN_PRIORIDAD(DEC.NPRIORIDAD_CODIGO) AS PRIORIDAD, "
                + "PK_UTIL.FUN_INSTITUCION(DOC.NINSTITUCION_CODIGO) AS INSTITUCION, "
                + "TO_DATE(DOC.DMESA_PARTE_FECHA+1) AS FECHA_MESA_PARTE,  "
                + "REGEXP_REPLACE(UPPER(DEC.VMESA_PARTE_DECRETO_DESCRIPCIO),'''','') AS COMENTARIO, "
                + "PK_UTIL.FUN_USUARIO(DEC.VUSUARIO_RESPONSABLE) AS USUARIO_RESPONSABLE,"
                + "DOC.VMESA_PARTE_DIGITAL AS MESA_PARTE "
                + "FROM OPREFA_MESA_PARTES DOC INNER JOIN OPREFA_MESA_PARTES_DECRETOS DEC ON ("
                + "DOC.CPERIODO_CODIGO=DEC.CPERIODO_CODIGO AND "
                + "DOC.CMESA_PARTE_TIPO=DEC.CMESA_PARTE_TIPO AND "
                + "DOC.NMESA_PARTE_CODIGO=DEC.NMESA_PARTE_CODIGO) WHERE "
                + "DOC.CPERIODO_CODIGO=? AND "
                + "DOC.CMES_CODIGO=? AND "
                + "DOC.CMESA_PARTE_TIPO=? AND "
                + "DEC.CESTADO_CODIGO!='AN' "
                + "ORDER BY CODIGO DESC";
        try {
            objPreparedStatement = objConnection.prepareStatement(sql);
            objPreparedStatement.setString(1, objBeanDecreto.getPeriodo());
            objPreparedStatement.setString(2, objBeanDecreto.getMes());
            objPreparedStatement.setString(3, objBeanDecreto.getTipo());
            objResultSet = objPreparedStatement.executeQuery();
            while (objResultSet.next()) {
                objBnDecreto = new BeanMesaPartes();
                objBnDecreto.setNumero(objResultSet.getString("CODIGO"));
                objBnDecreto.setDecreto(objResultSet.getInt("DECRETO"));
                objBnDecreto.setNumeroDocumento(objResultSet.getString("NUMERO"));
                objBnDecreto.setAsunto(objResultSet.getString("ASUNTO"));
                objBnDecreto.setPrioridad(objResultSet.getString("PRIORIDAD"));
                objBnDecreto.setInstitucion(objResultSet.getString("INSTITUCION"));
                objBnDecreto.setFecha(objResultSet.getDate("FECHA_MESA_PARTE"));
                objBnDecreto.setPostFirma(objResultSet.getString("COMENTARIO"));
                objBnDecreto.setUsuarioResponsable(objResultSet.getString("USUARIO_RESPONSABLE"));
                objBnDecreto.setArchivo(objResultSet.getString("MESA_PARTE"));
                lista.add(objBnDecreto);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener getListaDocumentosDecretados(objBnDecreto) : " + e.getMessage());
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
    public List getListaDocumentosRespuesta(BeanMesaPartes objBeanDocumento, String usuario) {
        lista = new LinkedList<>();
        sql = "SELECT DOC.CPERIODO_CODIGO AS PERIODO, LPAD(DOC.NMESA_PARTE_CODIGO,5,0) AS NUMERO, "
                + "DEC.NMESA_PARTE_DECRETO_CODIGO AS CODIGO, "
                + "PK_UTIL.FUN_INSTITUCION(DOC.NINSTITUCION_CODIGO) AS INSTITUCION, "
                + "PK_UTIL.FUN_DOCUMENTO(DOC.NDOCUMENTO_CODIGO)||'-'||DOC.VMESA_PARTE_NUMERO AS MESA_PARTE, "
                + "REPLACE(REGEXP_REPLACE(UPPER(DOC.VMESA_PARTE_ASUNTO),'''',''),'\n"
                + "', ' ') AS ASUNTO, PK_UTIL.FUN_PRIORIDAD(DEC.NPRIORIDAD_CODIGO) AS PRIORIDAD, "
                + "TO_DATE(DOC.DMESA_PARTE_FECHA+1) AS FECHA_MESA_PARTE, "
                + "DOC.VMESA_PARTE_POST_FIRMA AS POST_FIRMA, DOC.NMESA_PARTE_LEGAJOS AS LEGAJO,"
                + "DOC.NMESA_PARTE_FOLIOS AS FOLIO,"
                + "DOC.VMESA_PARTE_DIGITAL AS ARCHIVO, "
                + "DEC.VMESA_PARTE_DECRETO_DESCRIPCIO AS COMENTARIO, "
                + "PK_UTIL.FUN_DESCRIPCION_ESTADO(DEC.CESTADO_CODIGO) AS ESTADO "
                + "FROM OPREFA_MESA_PARTES DOC JOIN OPREFA_MESA_PARTES_DECRETOS DEC ON ("
                + "DOC.CPERIODO_CODIGO=DEC.CPERIODO_CODIGO AND "
                + "DOC.CMESA_PARTE_TIPO=DEC.CMESA_PARTE_TIPO AND "
                + "DOC.NMESA_PARTE_CODIGO=DEC.NMESA_PARTE_CODIGO) WHERE "
                + "DOC.CPERIODO_CODIGO=? AND "
                + "DEC.VUSUARIO_RESPONSABLE=? AND "
                + "DEC.CESTADO_CODIGO IN ('AC','RE') AND "
                + "DOC.CMESA_PARTE_TIPO='E' "
                + "ORDER BY DEC.CESTADO_CODIGO, DOC.NMESA_PARTE_CODIGO DESC";
        try {
            objPreparedStatement = objConnection.prepareStatement(sql);
            objPreparedStatement.setString(1, objBeanDocumento.getPeriodo());
            objPreparedStatement.setString(2, objBeanDocumento.getUsuarioResponsable());
            objResultSet = objPreparedStatement.executeQuery();
            while (objResultSet.next()) {
                objBnDecreto = new BeanMesaPartes();
                objBnDecreto.setPeriodo(objResultSet.getString("PERIODO"));
                objBnDecreto.setNumero(objResultSet.getString("NUMERO"));
                objBnDecreto.setDecreto(objResultSet.getInt("CODIGO"));
                objBnDecreto.setInstitucion(objResultSet.getString("INSTITUCION"));
                objBnDecreto.setNumeroDocumento(objResultSet.getString("MESA_PARTE"));
                objBnDecreto.setAsunto(objResultSet.getString("ASUNTO"));
                objBnDecreto.setPrioridad(objResultSet.getString("PRIORIDAD"));
                objBnDecreto.setFecha(objResultSet.getDate("FECHA_MESA_PARTE"));
                objBnDecreto.setPostFirma(objResultSet.getString("POST_FIRMA"));
                objBnDecreto.setLegajo(objResultSet.getInt("LEGAJO"));
                objBnDecreto.setFolio(objResultSet.getInt("FOLIO"));
                objBnDecreto.setMes(objResultSet.getString("COMENTARIO"));
                objBnDecreto.setEstado(objResultSet.getString("ESTADO"));
                objBnDecreto.setArchivo(objResultSet.getString("ARCHIVO"));
                lista.add(objBnDecreto);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener getListaDocumentosRespuesta(objBeanDocumento) : " + e.getMessage());
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
    public int iduDecreto(BeanMesaPartes objBnDecreto, String usuario) {
        sql = "{CALL SP_IDU_MESA_PARTES_DECRETO(?,?,?,?,?,?,?,?,?,?,?,?)}";
        try (CallableStatement cs = objConnection.prepareCall(sql)) {
            cs.setString(1, objBnDecreto.getPeriodo());
            cs.setString(2, objBnDecreto.getTipo());
            cs.setString(3, objBnDecreto.getNumero());
            cs.setInt(4, objBnDecreto.getDecreto());
            cs.setString(5, objBnDecreto.getUsuario());
            cs.setString(6, objBnDecreto.getAsunto());
            cs.setString(7, objBnDecreto.getPrioridad());
            cs.setString(8, objBnDecreto.getArea());
            cs.setString(9, objBnDecreto.getUsuarioResponsable());
            cs.setString(10, usuario);
            cs.setString(11, objBnDecreto.getMode().toUpperCase());
            cs.registerOutParameter(12, java.sql.Types.VARCHAR);
            cs.executeUpdate();
            s = cs.getInt(12);
            cs.close();
        } catch (SQLException e) {
            System.out.println("Error al ejecutar iduDecreto : " + e.getMessage());
            objDsMsgerr = new MsgerrDAOImpl(objConnection);
            objBnMsgerr = new BeanMsgerr();
            objBnMsgerr.setUsuario(usuario);
            objBnMsgerr.setTabla("OPREFA_MESA_PARTES_DECRETOS");
            objBnMsgerr.setTipo(objBnDecreto.getMode().toUpperCase());
            objBnMsgerr.setDescripcion(e.getMessage());
            s = objDsMsgerr.iduMsgerr(objBnMsgerr);
            return 0;
        }
        return s;
    }

    @Override
    public int iduDecretarTipoDecreto(BeanMesaPartes objBnDecreto, String usuario) {
        sql = "{CALL SP_IDU_DECRETO_TIPO_DECRETO(?,?,?,?,?,?,?)}";
        try (CallableStatement cs = objConnection.prepareCall(sql)) {
            cs.setString(1, objBnDecreto.getPeriodo());
            cs.setString(2, objBnDecreto.getTipo());
            cs.setString(3, objBnDecreto.getNumero());
            cs.setInt(4, objBnDecreto.getDecreto());
            cs.setString(5, objBnDecreto.getDocumento());
            cs.setString(6, usuario);
            cs.setString(7, objBnDecreto.getMode().toUpperCase());
            s = cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            System.out.println("Error al ejecutar iduDecretar : " + e.getMessage());
            objDsMsgerr = new MsgerrDAOImpl(objConnection);
            objBnMsgerr = new BeanMsgerr();
            objBnMsgerr.setUsuario(usuario);
            objBnMsgerr.setTabla("OPREFA_MESA_PARTES_DECRETOS");
            objBnMsgerr.setTipo(objBnDecreto.getMode().toUpperCase());
            objBnMsgerr.setDescripcion(e.getMessage());
            s = objDsMsgerr.iduMsgerr(objBnMsgerr);
            return 0;
        }
        return s;
    }

    @Override
    public String getDocumentosPendientes(String usuario) {
        String result = "0";
        sql = "SELECT COUNT(*) AS MESA_PARTE "
                + "FROM OPREFA_MESA_PARTES_DECRETOS WHERE "
                + "VUSUARIO_RESPONSABLE=? AND "
                + "CESTADO_CODIGO='AC'";
        try {
            objPreparedStatement = objConnection.prepareStatement(sql);
            objPreparedStatement.setString(1, usuario);
            objResultSet = objPreparedStatement.executeQuery();
            if (objResultSet.next()) {
                result = objResultSet.getString("MESA_PARTE");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener getDocumentosPendientes(objBnDocumento) : " + e.getMessage());
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
    public BeanMesaPartes getDecreto(BeanMesaPartes objBeanDecreto, String usuario) {
        sql = "SELECT VUSUARIO_DECRETA, NPRIORIDAD_CODIGO, CAREA_LABORAL_CODIGO, "
                + "VUSUARIO_RESPONSABLE, VMESA_PARTE_DECRETO_DESCRIPCION "
                + "FROM OPREFA_MESA_PARTES_DECRETOS WHERE "
                + "CPERIODO_CODIGO=? AND "
                + "CMESA_PARTE_TIPO=? AND "
                + "NMESA_PARTE_CODIGO=? AND "
                + "NMESA_PARTE_DECRETO_CODIGO=? ";
        try {
            objPreparedStatement = objConnection.prepareStatement(sql);
            objPreparedStatement.setString(1, objBeanDecreto.getPeriodo());
            objPreparedStatement.setString(2, objBeanDecreto.getTipo());
            objPreparedStatement.setString(3, objBeanDecreto.getNumero());
            objPreparedStatement.setInt(4, objBeanDecreto.getDecreto());
            objResultSet = objPreparedStatement.executeQuery();
            if (objResultSet.next()) {
                objBeanDecreto.setUsuario(objResultSet.getString("VUSUARIO_DECRETA"));
                objBeanDecreto.setPrioridad(objResultSet.getString("NPRIORIDAD_CODIGO"));
                objBeanDecreto.setArea(objResultSet.getString("CAREA_LABORAL_CODIGO"));
                objBeanDecreto.setUsuarioResponsable(objResultSet.getString("VUSUARIO_RESPONSABLE"));
                objBeanDecreto.setAsunto(objResultSet.getString("VMESA_PARTE_DECRETO_DESCRIPCION"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener getDecreto(objBeanDecreto) : " + e.getMessage());
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
        return objBeanDecreto;
    }

    @Override
    public ArrayList getListaDecretoTipoDecretos(BeanMesaPartes objBeanDecreto, String usuario) {
        ArrayList<String> Arreglo = new ArrayList<>();
        sql = "SELECT NTIPO_DECRETO_CODIGO "
                + "FROM OPREFA_MESA_PARTES_DECRETOS_TI WHERE "
                + "CPERIODO_CODIGO=? AND "
                + "CMESA_PARTE_TIPO=? AND "
                + "NMESA_PARTE_CODIGO=? AND "
                + "NMESA_PARTE_DECRETO_CODIGO=? ";
        try {
            objPreparedStatement = objConnection.prepareStatement(sql);
            objPreparedStatement.setString(1, objBeanDecreto.getPeriodo());
            objPreparedStatement.setString(2, objBeanDecreto.getTipo());
            objPreparedStatement.setString(3, objBeanDecreto.getNumero());
            objPreparedStatement.setInt(4, objBeanDecreto.getDecreto());
            objResultSet = objPreparedStatement.executeQuery();
            while (objResultSet.next()) {
                Arreglo.add(objResultSet.getString("NTIPO_DECRETO_CODIGO"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener getListaDecretoTipoDecretos(objBnDecreto) : " + e.getMessage());
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
        return Arreglo;
    }

    @Override
    public ArrayList getListaDetalleDocumentoDecretado(BeanMesaPartes objBeanDecreto, String usuario) {
        ArrayList<String> Arreglo = new ArrayList<>();
        ArrayList<String> Filas = new ArrayList<>();
        sql = "SELECT NMESA_PARTE_DECRETO_CODIGO AS CODIGO, "
                + "PK_UTIL.FUN_USUARIO(VUSUARIO_DECRETA) AS DECRETADOR,"
                + "PK_UTIL.FUN_USUARIO(VUSUARIO_RESPONSABLE) AS USUARIO_RESPONSABLE,"
                + "PK_UTIL.FUN_PRIORIDAD(NPRIORIDAD_CODIGO) AS PRIORIDAD,"
                + "VMESA_PARTE_DECRETO_DESCRIPCIO AS COMENTARIO, "
                + "TO_CHAR(DMESA_PARTE_DECRETO_FECHA,'DD/MM/YYYY') AS FECHA_DECRETO,"
                + "TO_CHAR(DMESA_PARTE_DECRETO_RECEPCION,'DD/MM/YYYY') AS FECHA_RECEPCION,"
                + "PK_UTIL.FUN_DESCRIPCION_ESTADO(CESTADO_CODIGO) AS ESTADO "
                + "FROM OPREFA_MESA_PARTES_DECRETOS WHERE "
                + "CPERIODO_CODIGO=? AND "
                + "CMESA_PARTE_TIPO=? AND "
                + "NMESA_PARTE_CODIGO=? AND "
                + "CESTADO_CODIGO!='AN' "
                + "ORDER BY CODIGO ";
        try {
            objPreparedStatement = objConnection.prepareStatement(sql);
            objPreparedStatement.setString(1, objBeanDecreto.getPeriodo());
            objPreparedStatement.setString(2, objBeanDecreto.getTipo());
            objPreparedStatement.setString(3, objBeanDecreto.getNumero());
            objResultSet = objPreparedStatement.executeQuery();
            while (objResultSet.next()) {
                Filas.clear();
                String arreglo = objResultSet.getString("CODIGO") + "+++"
                        + objResultSet.getString("DECRETADOR") + "+++"
                        + objResultSet.getString("USUARIO_RESPONSABLE") + "+++"
                        + objResultSet.getString("PRIORIDAD") + "+++"
                        + objResultSet.getString("COMENTARIO") + "+++"
                        + objResultSet.getString("FECHA_DECRETO") + "+++"
                        + objResultSet.getString("FECHA_RECEPCION") + "+++"
                        + objResultSet.getString("ESTADO");
                Filas.add(arreglo);
                Arreglo.add("" + Filas);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener getListaDetalleDocumentoDecretado(objBnDecreto) : " + e.getMessage());
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
        return Arreglo;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataService.Despachadores.Impl;

import BusinessServices.Beans.BeanMsgerr;
import BusinessServices.Beans.BeanPlanilla;
import DataService.Despachadores.MsgerrDAO;
import DataService.Despachadores.PlanillaSIAFDAO;
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
public class PlanillaSIAFDAOImpl implements PlanillaSIAFDAO {

    private final Connection objConnection;
    private List lista;
    private String sql;
    private ResultSet objResultSet;
    private BeanPlanilla objBnPlanilla;
    private PreparedStatement objPreparedStatement;
    private int s = 0;
    private BeanMsgerr objBnMsgerr;
    private MsgerrDAO objDsMsgerr;

    public PlanillaSIAFDAOImpl(Connection objConnection1) {
        objConnection = objConnection1;
    }

    @Override
    public List getListaPlanillaSIAF(String busqueda) {
        lista = new LinkedList<>();

        sql = "SELECT ANO_PROCESO AS PERIODO, PK_UTIL.FUN_MES_ABREVIATURA(MES_PROCESO) AS MES, "
                + "PK_UTIL.FUN_TIPO_PLANILLA(TIPO_PLANILLA) AS TIPO_PLANILLA, "
                + "PK_UTIL.FUN_CLASE_PLANILLA(CLASE_PLANILLA) AS CLASE_PLANILLA, "
                + "CORRELATIVO_PLANILLA AS CORRELATIVO, "
                + "PK_UTIL.FUN_TIPO_DOCUMENTO(TIPO_DOCUMENTO) AS TIPO_DOCUMENTO, "
                + "NUMERO_DOCUMENTO AS NUMERO_DOCUMENTO, "
                + "APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS NOMBRES, "
                + "MONTO_NETO_CAB AS MONTO, PK_UTIL.FUN_BANCO_ABREVIATURA(COD_BANCO) AS BANCO, "
                + "CTA_BANCARIA AS CUENTA, PK_UTIL.FUN_FORMA_PAGO(FORMA_PAGO_MCPP) AS FORMA_PAGO "
                + "FROM SIAF_PLANILLA WHERE "
                + "(NUMERO_DOCUMENTO LIKE ? OR  "
                + "UPPER(APELLIDO_PATERNO||APELLIDO_MATERNO||NOMBRE) LIKE ? )"
                + "ORDER BY PERIODO, MES_PROCESO, TIPO_PLANILLA, CLASE_PLANILLA, CORRELATIVO";
        try {
            objPreparedStatement = objConnection.prepareStatement(sql);
            objPreparedStatement.setString(1, busqueda);
            objPreparedStatement.setString(2, "%" + busqueda.replaceAll(" ", "%").toUpperCase() + "%");
            objResultSet = objPreparedStatement.executeQuery();
            
            while (objResultSet.next()) {
                objBnPlanilla = new BeanPlanilla();
                objBnPlanilla.setPeriodo(objResultSet.getString("PERIODO"));
                objBnPlanilla.setMes(objResultSet.getString("MES"));
                objBnPlanilla.setTipoPlanilla(objResultSet.getString("TIPO_PLANILLA"));
                objBnPlanilla.setClasePlanilla(objResultSet.getString("CLASE_PLANILLA"));
                objBnPlanilla.setCorrelativo(objResultSet.getString("CORRELATIVO"));
                objBnPlanilla.setTipoDocumento(objResultSet.getString("TIPO_DOCUMENTO"));
                objBnPlanilla.setNumeroDocumento(objResultSet.getString("NUMERO_DOCUMENTO"));
                objBnPlanilla.setNombres(objResultSet.getString("NOMBRES"));
                objBnPlanilla.setMonto(objResultSet.getDouble("MONTO"));
                objBnPlanilla.setBanco(objResultSet.getString("BANCO"));
                objBnPlanilla.setCuentaBancaria(objResultSet.getString("CUENTA"));
                objBnPlanilla.setFormaPago(objResultSet.getString("FORMA_PAGO"));
                lista.add(objBnPlanilla);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener getListaPlanillaSIAF(" + busqueda + ") : " + e.getMessage());
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
}

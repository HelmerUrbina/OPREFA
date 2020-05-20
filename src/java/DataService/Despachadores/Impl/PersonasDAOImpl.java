/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataService.Despachadores.Impl;

import BusinessServices.Beans.BeanMsgerr;
import BusinessServices.Beans.BeanPersonas;
import DataService.Despachadores.MsgerrDAO;
import DataService.Despachadores.PersonasDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author helme
 */
public class PersonasDAOImpl implements PersonasDAO {

    private final Connection objConnection;
    private List lista;
    private String sql;
    private ResultSet objResultSet;
    private BeanPersonas objBnPersona;
    private PreparedStatement objPreparedStatement;
    private int s = 0;
    private BeanMsgerr objBnMsgerr;
    private MsgerrDAO objDsMsgerr;

    public PersonasDAOImpl(Connection objConnection1) {
        objConnection = objConnection1;
    }

    @Override
    public List getListaPersonas() {
        lista = new LinkedList<>();
        sql = "SELECT CPERIODO_CODIGO AS CODIGO, VPERIODO_NOMBRE AS DESCRIPCION, "
                + "NPERIODO_IGV AS IGV, "
                + "PK_UTIL.FUN_DESCRIPCION_ESTADO(CESTADO_CODIGO) AS ESTADO "
                + "FROM OPREFA_PERIODOS "
                + "ORDER BY CODIGO DESC";
        try {
            objPreparedStatement = objConnection.prepareStatement(sql);
            objResultSet = objPreparedStatement.executeQuery();
            while (objResultSet.next()) {
                objBnPersona = new BeanPersonas();
                ////objBnPersona.setCodigo(objResultSet.getString("CODIGO"));
                //objBnPersona.setDescripcion(objResultSet.getString("DESCRIPCION"));
                //objBnPersona.setImporte(objResultSet.getDouble("IGV"));
                //objBnPersona.setEstado(objResultSet.getString("ESTADO"));
                lista.add(objBnPersona);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener getListaPersonas() : " + e.getMessage());
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
    public BeanPersonas getPersona(BeanPersonas objBeanPersona) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int iduPersona(BeanPersonas objBeanPersona, String usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

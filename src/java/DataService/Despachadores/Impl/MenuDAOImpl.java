package DataService.Despachadores.Impl;

import BusinessServices.Beans.BeanMenu;
import BusinessServices.Beans.BeanMsgerr;
import DataService.Despachadores.MenuDAO;
import DataService.Despachadores.MsgerrDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MenuDAOImpl implements MenuDAO {

    private final Connection objConnection;
    private List lista;
    private String sql;
    private ResultSet objResultSet;
    private BeanMenu objBnMenu;
    private PreparedStatement objPreparedStatement;
    private int s = 0;
    private BeanMsgerr objBnMsgerr;
    private MsgerrDAO objDsMsgerr;

    public MenuDAOImpl(Connection objConnection1) {
        objConnection = objConnection1;
    }

    @Override
    public List getListaMenu() {
        lista = new LinkedList<>();
        sql = "SELECT OPREFA_MODULOS.CMODULO_CODIGO||'.'||OPREFA_MENU.CMENU_CODIGO AS CODIGO, "
                + "OPREFA_MODULOS.VMODULO_NOMBRE AS MODULO, OPREFA_MENU.VMENU_NOMBRE AS MENU, "
                + "OPREFA_MENU.VMENU_SERVLET AS SERVLET, OPREFA_MENU.VMENU_MODO AS MODO, "
                + "PK_UTIL.FUN_DESCRIPCION_ESTADO(OPREFA_MENU.CESTADO_CODIGO) AS ESTADO "
                + "FROM OPREFA_MODULOS INNER JOIN OPREFA_MENU ON ("
                + "OPREFA_MODULOS.CMODULO_CODIGO=OPREFA_MENU.CMODULO_CODIGO) "
                + "ORDER BY CODIGO";
        try {
            objPreparedStatement = objConnection.prepareStatement(sql);
            objResultSet = objPreparedStatement.executeQuery();
            while (objResultSet.next()) {
                objBnMenu = new BeanMenu();
                objBnMenu.setCodigo(objResultSet.getString("CODIGO"));
                objBnMenu.setModulo(objResultSet.getString("MODULO"));
                objBnMenu.setNombre(objResultSet.getString("MENU"));
                objBnMenu.setServlet(objResultSet.getString("SERVLET"));
                objBnMenu.setModo(objResultSet.getString("MODO"));
                objBnMenu.setEstado(objResultSet.getString("ESTADO"));
                lista.add(objBnMenu);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener getListaMenu() : " + e.getMessage());
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
    public BeanMenu getMenu(BeanMenu objBeanMenu) {
        sql = "SELECT CMODULO_CODIGO AS MODULO, CMENU_CODIGO AS MENU, VMENU_NOMBRE AS NOMBRE, "
                + "VMENU_SERVLET AS SERVLET, VMENU_MODO AS MODO, CESTADO_CODIGO AS ESTADO "
                + "FROM OPREFA_MENU WHERE "
                + "CMODULO_CODIGO||'.'||CMENU_CODIGO=? ";
        try {
            objPreparedStatement = objConnection.prepareStatement(sql);
            objPreparedStatement.setString(1, objBeanMenu.getCodigo());
            objResultSet = objPreparedStatement.executeQuery();
            if (objResultSet.next()) {
                objBeanMenu.setModulo(objResultSet.getString("MODULO"));
                objBeanMenu.setCodigo(objResultSet.getString("MENU"));
                objBeanMenu.setNombre(objResultSet.getString("NOMBRE"));
                objBeanMenu.setServlet(objResultSet.getString("SERVLET"));
                objBeanMenu.setModo(objResultSet.getString("MODO"));
                objBeanMenu.setEstado(objResultSet.getString("ESTADO"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener getMenu(objBeanMenu) : " + e.getMessage());
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
        return objBeanMenu;
    }

    @Override
    public int iduMenu(BeanMenu objBeanMenu, String usuario) {
        sql = "{CALL SP_IDU_MENU(?,?,?,?,?,?,?,?)}";
        try (CallableStatement cs = objConnection.prepareCall(sql)) {
            cs.setString(1, objBeanMenu.getModulo());
            cs.setString(2, objBeanMenu.getCodigo());
            cs.setString(3, objBeanMenu.getNombre());
            cs.setString(4, objBeanMenu.getServlet());
            cs.setString(5, objBeanMenu.getModo());
            cs.setString(6, objBeanMenu.getEstado());
            cs.setString(7, usuario);
            cs.setString(8, objBeanMenu.getMode());
            s = cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            System.out.println("Error al ejecutar iduMenu : " + e.getMessage());
            objDsMsgerr = new MsgerrDAOImpl(objConnection);
            objBnMsgerr = new BeanMsgerr();
            objBnMsgerr.setUsuario(usuario);
            objBnMsgerr.setTabla("OPREFA_MENU");
            objBnMsgerr.setTipo(objBeanMenu.getMode().toUpperCase());
            objBnMsgerr.setDescripcion(e.getMessage());
            s = objDsMsgerr.iduMsgerr(objBnMsgerr);
            return 0;
        }
        return s;
    }
}

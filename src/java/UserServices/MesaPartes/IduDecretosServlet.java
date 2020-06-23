/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserServices.MesaPartes;

import BusinessServices.Beans.BeanMesaPartes;
import BusinessServices.Beans.BeanMsgerr;
import BusinessServices.Beans.BeanUsuario;
import DataService.Despachadores.DecretosDAO;
import DataService.Despachadores.Impl.DecretosDAOImpl;
import DataService.Despachadores.Impl.MsgerrDAOImpl;
import DataService.Despachadores.MsgerrDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author H-URBINA-M
 */
@WebServlet(name = "IduDecretosServlet", urlPatterns = {"/IduDecretos"})
public class IduDecretosServlet extends HttpServlet {

    private ServletConfig config = null;
    private ServletContext context = null;
    private HttpSession session = null;
    private RequestDispatcher dispatcher = null;
    private BeanMesaPartes objBnDecreto;
    private Connection objConnection;
    private DecretosDAO objDsDecreto;
    private BeanMsgerr objBnMsgerr;
    private MsgerrDAO objDsMsgerr;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        config = this.getServletConfig();
        context = config.getServletContext();
        session = request.getSession(false);
        BeanUsuario objUsuario = (BeanUsuario) session.getAttribute("objUsuario" + session.getId());
        //VERIFICAMOS QUE LA SESSION SEA VALIDA
        if (objUsuario == null) {
            dispatcher = request.getRequestDispatcher("/FinSession.jsp");
            dispatcher.forward(request, response);
        }
        objConnection = (Connection) context.getAttribute("objConnection");
        String result = null;
        objBnDecreto = new BeanMesaPartes();
        objBnDecreto.setMode(request.getParameter("mode"));
        objBnDecreto.setPeriodo(request.getParameter("periodo"));
        objBnDecreto.setTipo(request.getParameter("tipo"));
        objBnDecreto.setNumero(request.getParameter("numero"));
        objBnDecreto.setDecreto(Utiles.Utiles.checkNum(request.getParameter("decreto")));
        objBnDecreto.setUsuario(request.getParameter("usuarioEmision"));
        objBnDecreto.setAsunto(request.getParameter("comentario"));
        objBnDecreto.setPrioridad(request.getParameter("prioridad"));
        objBnDecreto.setArea(request.getParameter("area"));
        objBnDecreto.setUsuarioResponsable(request.getParameter("usuario"));
        objDsDecreto = new DecretosDAOImpl(objConnection);
        // EJECUTAMOS EL PROCEDIMIENTO SEGUN EL MODO QUE SE ESTA TRABAJANDO        
        int k = objDsDecreto.iduDecreto(objBnDecreto, objUsuario.getUsuario());
        if (k != 0) {
            if (objBnDecreto.getMode().equals("I") || objBnDecreto.getMode().equals("U")) {
                objBnDecreto.setMode("D");
                objBnDecreto.setDecreto(k);
                k = objDsDecreto.iduDecretarTipoDecreto(objBnDecreto, objUsuario.getUsuario());
                String lista[][] = Utiles.Utiles.generaLista(request.getParameter("lista"), 1);
                for (String[] item : lista) {
                    objBnDecreto.setMode("I");
                    objBnDecreto.setDocumento(item[0].trim());
                    k = objDsDecreto.iduDecretarTipoDecreto(objBnDecreto, objUsuario.getUsuario());
                }
            }
        } else {
            // EN CASO DE HABER PROBLEMAS DESPACHAMOS UNA VENTANA DE ERROR, MOSTRANDO EL ERROR OCURRIDO.
            objBnMsgerr = new BeanMsgerr();
            objBnMsgerr.setUsuario(objUsuario.getUsuario());
            objBnMsgerr.setTabla("OPREFA_MESA_PARTES_DECRETOS");
            objBnMsgerr.setTipo(objBnDecreto.getMode());
            objDsMsgerr = new MsgerrDAOImpl(objConnection);
            objBnMsgerr = objDsMsgerr.getMsgerr(objBnMsgerr);
            result = objBnMsgerr.getDescripcion();
        }
        // EN CASO DE NO HABER PROBLEMAS RETORNAMOS UNA NUEVA CONSULTA CON TODOS LOS DATOS.
        if (result == null) {
            try (PrintWriter out = response.getWriter()) {
                out.print("GUARDO");
            }
        } else {
            //PROCEDEMOS A ELIMINAR TODO;
            try (PrintWriter out = response.getWriter()) {
                out.print(result);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

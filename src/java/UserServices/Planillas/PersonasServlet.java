/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserServices.Planillas;

import BusinessServices.Beans.BeanPersonas;
import BusinessServices.Beans.BeanUsuario;
import DataService.Despachadores.CombosDAO;
import DataService.Despachadores.Impl.CombosDAOImpl;
import DataService.Despachadores.Impl.PersonasDAOImpl;
import DataService.Despachadores.PersonasDAO;
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
@WebServlet(name = "PersonasServlet", urlPatterns = {"/Personas"})
public class PersonasServlet extends HttpServlet {

    private ServletConfig config = null;
    private ServletContext context = null;
    private HttpSession session = null;
    private RequestDispatcher dispatcher = null;
    private BeanPersonas objBnPersonas;
    private Connection objConnection;
    private PersonasDAO objDsPersonas;
    private CombosDAO objDsCombo;

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
        session = request.getSession();
        BeanUsuario objUsuario = (BeanUsuario) session.getAttribute("objUsuario" + session.getId());
        if (objUsuario == null) {
            dispatcher = request.getRequestDispatcher("/FinSession.jsp");
            dispatcher.forward(request, response);
        }
        objConnection = (Connection) context.getAttribute("objConnection");
        String result = null;
        objBnPersonas = new BeanPersonas();
        objBnPersonas.setMode(request.getParameter("mode"));
        objBnPersonas.setDNI(request.getParameter("codigo"));
        objDsPersonas = new PersonasDAOImpl(objConnection);
        // DE ACUERO AL MODO, OBTENEMOS LOS DATOS NECESARIOS.  
        if (objBnPersonas.getMode().equals("G")) {
            if (request.getAttribute("objPeriodos") != null) {
                request.removeAttribute("objPeriodos");
            }
            request.setAttribute("objPeriodos", objDsPersonas.getListaPersonas());
            objDsCombo = new CombosDAOImpl(objConnection);
            if (request.getAttribute("objPrioridades") != null) {
                request.removeAttribute("objPrioridades");
            }
            request.setAttribute("objPrioridades", objDsCombo.getPrioridades());
            if (request.getAttribute("objDocumentos") != null) {
                request.removeAttribute("objDocumentos");
            }
            request.setAttribute("objDocumentos", objDsCombo.getDocumentos());
            if (request.getAttribute("objClasificaciones") != null) {
                request.removeAttribute("objClasificaciones");
            }
            request.setAttribute("objClasificaciones", objDsCombo.getClasificacionDocumento());
            if (request.getAttribute("objAreaLaboral") != null) {
                request.removeAttribute("objAreaLaboral");
            }
            request.setAttribute("objAreaLaboral", objDsCombo.getAreaLaboral());
            if (request.getAttribute("objInstituciones") != null) {
                request.removeAttribute("objInstituciones");
            }
            request.setAttribute("objInstituciones", objDsCombo.getInstituciones());
        }
        if (objBnPersonas.getMode().equals("U")) {
            objBnPersonas = objDsPersonas.getPersona(objBnPersonas);
            result = objBnPersonas.getPaterno() + "+++"
                    + objBnPersonas.getMaterno();
        }
        //SE ENVIA DE ACUERDO AL MODO SELECCIONADO
        switch (request.getParameter("mode")) {
            case "personas":
                dispatcher = request.getRequestDispatcher("Planillas/Personas.jsp");
                break;
            case "G":
                dispatcher = request.getRequestDispatcher("Planillas/ListaPersonas.jsp");
                break;
            default:
                dispatcher = request.getRequestDispatcher("FinSession.jsp");
                break;
        }
        if (result != null) {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.print(result);
            }
        } else {
            dispatcher.forward(request, response);
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserServices.Planeamiento;

import BusinessServices.Beans.BeanPlaneamiento;
import BusinessServices.Beans.BeanUsuario;
import DataService.Despachadores.Impl.ObjetivosEstrategicosDAOImpl;
import DataService.Despachadores.ObjetivosEstrategicosDAO;
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
@WebServlet(name = "ObjetivosEstrategicosServlet", urlPatterns = {"/ObjetivosEstrategicos"})
public class ObjetivosEstrategicosServlet extends HttpServlet {

    private ServletConfig config = null;
    private ServletContext context = null;
    private HttpSession session = null;
    private RequestDispatcher dispatcher = null;
    private BeanPlaneamiento objBnObjetivos;
    private Connection objConnection;
    private ObjetivosEstrategicosDAO objDsObjetivos;

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
        if (objUsuario == null) {
            dispatcher = request.getRequestDispatcher("/FinSession.jsp");
            dispatcher.forward(request, response);
        }
        objConnection = (Connection) context.getAttribute("objConnection");
        String result = null;
        objBnObjetivos = new BeanPlaneamiento();
        objBnObjetivos.setMode(request.getParameter("mode"));
        objBnObjetivos.setPeriodo(request.getParameter("periodo"));
        objBnObjetivos.setCodigo(request.getParameter("codigo"));
        objDsObjetivos = new ObjetivosEstrategicosDAOImpl(objConnection);
        // DE ACUERO AL MODO, OBTENEMOS LOS DATOS NECESARIOS.  
        if (objBnObjetivos.getMode().equals("G")) {
            if (request.getAttribute("objObjetivosEstrategicos") != null) {
                request.removeAttribute("objObjetivosEstrategicos");
            }
            request.setAttribute("objObjetivosEstrategicos", objDsObjetivos.getListaObjetivosEstrategicos(objBnObjetivos.getPeriodo(), objUsuario.getUsuario()));
        }
        if (objBnObjetivos.getMode().equals("I")) {
            result = "" + objDsObjetivos.getPrioridadObjetivosEstrategicos(objBnObjetivos.getPeriodo(), objUsuario.getUsuario());
        }
        if (objBnObjetivos.getMode().equals("U")) {
            objBnObjetivos = objDsObjetivos.getObjetivosEstrategicos(objBnObjetivos, objUsuario.getUsuario());
            result = objBnObjetivos.getDescripcion() + "+++"
                    + objBnObjetivos.getAbreviatura() + "+++"
                    + objBnObjetivos.getPrioridad() + "+++"
                    + objBnObjetivos.getFechaInicio() + "+++"
                    + objBnObjetivos.getFechaFinal() + "+++"
                    + objBnObjetivos.getUnidadMedida() + "+++"
                    + objBnObjetivos.getEstado();
        }
        //SE ENVIA DE ACUERDO AL MODO SELECCIONADO
        switch (request.getParameter("mode")) {
            case "objetivosEstrategicos":
                dispatcher = request.getRequestDispatcher("Planeamiento/ObjetivosEstrategicos.jsp");
                break;
            case "G":
                dispatcher = request.getRequestDispatcher("Planeamiento/ListaObjetivosEstrategicos.jsp");
                break;
            default:
                dispatcher = request.getRequestDispatcher("error.jsp");
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserServices.MesaPartes;

import BusinessServices.Beans.BeanMesaPartes;
import BusinessServices.Beans.BeanUsuario;
import DataService.Despachadores.CombosDAO;
import DataService.Despachadores.Impl.CombosDAOImpl;
import DataService.Despachadores.Impl.MesaPartesDAOImpl;
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
import DataService.Despachadores.MesaPartesDAO;

/**
 *
 * @author H-URBINA-M
 */
@WebServlet(name = "MesaPartesServlet", urlPatterns = {"/MesaPartes"})
public class MesaPartesServlet extends HttpServlet {

    private ServletConfig config = null;
    private ServletContext context = null;
    private HttpSession session = null;
    private RequestDispatcher dispatcher = null;
    private BeanMesaPartes objBnDocumento;
    private Connection objConnection;
    private MesaPartesDAO objDsDocumento;
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
        session = request.getSession(false);
        BeanUsuario objUsuario = (BeanUsuario) session.getAttribute("objUsuario" + session.getId());
        //VERIFICAMOS LA SESSION DEL USUARIO
        if (objUsuario == null) {
            dispatcher = request.getRequestDispatcher("/FinSession.jsp");
            dispatcher.forward(request, response);
        }
        objConnection = (Connection) context.getAttribute("objConnection");
        String result = null;
        objBnDocumento = new BeanMesaPartes();
        objBnDocumento.setMode(request.getParameter("mode"));
        objBnDocumento.setPeriodo(request.getParameter("periodo"));
        objBnDocumento.setMes(request.getParameter("mes"));
        objBnDocumento.setTipo(request.getParameter("tipo"));
        objBnDocumento.setNumero(request.getParameter("codigo"));
        objDsDocumento = new MesaPartesDAOImpl(objConnection);
        if (objBnDocumento.getMode().equals("G")) {
            if (request.getAttribute("objMesaPartes") != null) {
                request.removeAttribute("objMesaPartes");
            }
            request.setAttribute("objMesaPartes", objDsDocumento.getListaMesaPartes(objBnDocumento, objUsuario.getUsuario()));
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
        if (objBnDocumento.getMode().equals("L")) {
            if (request.getAttribute("objMesaPartesConsulta") != null) {
                request.removeAttribute("objMesaPartesConsulta");
            }
            request.setAttribute("objMesaPartesConsulta", objDsDocumento.getListaMesaPartesConsulta(objBnDocumento, objUsuario.getUsuario()));
        }
        if (objBnDocumento.getMode().equals("I")) {
            result = objDsDocumento.getNumeroMesaParte(objBnDocumento, objUsuario.getUsuario());
        }
        if (objBnDocumento.getMode().equals("U")) {
            objBnDocumento = objDsDocumento.getMesaParte(objBnDocumento, objUsuario.getUsuario());
            result = objBnDocumento.getNumero() + "+++"
                    + objBnDocumento.getInstitucion() + "+++"
                    + objBnDocumento.getReferencia() + "+++"
                    + objBnDocumento.getPrioridad() + "+++"
                    + objBnDocumento.getDocumento() + "+++"
                    + objBnDocumento.getNumeroDocumento() + "+++"
                    + objBnDocumento.getClasificacion() + "+++"
                    + objBnDocumento.getFecha() + "+++"
                    + objBnDocumento.getFechaRecepcion() + "+++"
                    + objBnDocumento.getAsunto() + "+++"
                    + objBnDocumento.getPostFirma() + "+++"
                    + objBnDocumento.getLegajo() + "+++"
                    + objBnDocumento.getFolio();
        }
        //SE ENVIA DE ACUERDO AL MODO SELECCIONADO
        switch (request.getParameter("mode")) {
            case "mesaPartes":
                dispatcher = request.getRequestDispatcher("MesaPartes/MesaPartes.jsp");
                break;
            case "consultaMesaParte":
                dispatcher = request.getRequestDispatcher("MesaPartes/MesaPartesConsulta.jsp");
                break;
            case "G":
                dispatcher = request.getRequestDispatcher("MesaPartes/ListaMesaPartes.jsp");
                break;
            case "L":
                dispatcher = request.getRequestDispatcher("MesaPartes/ListaMesaPartesConsulta.jsp");
                break;
            default:
                dispatcher = request.getRequestDispatcher("FinSession.jsp");
                break;
        }
        if (result != null) {
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

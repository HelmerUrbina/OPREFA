/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserServices.MesaPartes;

import BusinessServices.Beans.BeanDocumentos;
import BusinessServices.Beans.BeanUsuario;
import DataService.Despachadores.CombosDAO;
import DataService.Despachadores.DecretosDAO;
import DataService.Despachadores.Impl.CombosDAOImpl;
import DataService.Despachadores.Impl.DecretosDAOImpl;
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
@WebServlet(name = "DecretosServlet", urlPatterns = {"/Decretos"})
public class DecretosServlet extends HttpServlet {

    private ServletConfig config = null;
    private ServletContext context = null;
    private HttpSession session = null;
    private RequestDispatcher dispatcher = null;
    private BeanDocumentos objBnDecreto;
    private Connection objConnection;
    private DecretosDAO objDsDecreto;
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
        objBnDecreto = new BeanDocumentos();
        objBnDecreto.setMode(request.getParameter("mode"));
        objBnDecreto.setPeriodo(request.getParameter("periodo"));
        objBnDecreto.setMes(request.getParameter("mes"));
        objBnDecreto.setTipo(request.getParameter("tipo"));
        objBnDecreto.setNumero(request.getParameter("numero"));
        objBnDecreto.setDecreto(Utiles.Utiles.checkNum(request.getParameter("codigo")));
        objBnDecreto.setUsuarioResponsable(objUsuario.getUsuario());
        objDsDecreto = new DecretosDAOImpl(objConnection);
        if (objBnDecreto.getMode().equals("G")) {
            if (request.getAttribute("objDocumentos") != null) {
                request.removeAttribute("objDocumentos");
            }
            request.setAttribute("objDocumentos", objDsDecreto.getListaDocumentosPendientes(objBnDecreto, objUsuario.getUsuario()));
        }
        if (objBnDecreto.getMode().equals("A")) {
            if (request.getAttribute("objDecretos") != null) {
                request.removeAttribute("objDecretos");
            }
            request.setAttribute("objDecretos", objDsDecreto.getListaDocumentosDecretados(objBnDecreto, objUsuario.getUsuario()));
        }
        if (objBnDecreto.getMode().equals("L")) {
            if (request.getAttribute("objDocumetosDecretados") != null) {
                request.removeAttribute("objDocumetosDecretados");
            }
            request.setAttribute("objDocumetosDecretados", objDsDecreto.getListaDocumentosRespuesta(objBnDecreto, objUsuario.getUsuario()));
        }
        if (objBnDecreto.getMode().equals("G") || objBnDecreto.getMode().equals("A") || objBnDecreto.getMode().equals("L")) {
            objDsCombo = new CombosDAOImpl(objConnection);
            if (request.getAttribute("objUsuarioJefatura") != null) {
                request.removeAttribute("objUsuarioJefatura");
            }
            request.setAttribute("objUsuarioJefatura", objDsCombo.getUsuarioJefatura());
            if (request.getAttribute("objAreaLaboral") != null) {
                request.removeAttribute("objAreaLaboral");
            }
            request.setAttribute("objAreaLaboral", objDsCombo.getAreaLaboral());
            if (request.getAttribute("objPrioridades") != null) {
                request.removeAttribute("objPrioridades");
            }
            request.setAttribute("objPrioridades", objDsCombo.getPrioridades());
            if (request.getAttribute("objTipoDecretos") != null) {
                request.removeAttribute("objTipoDecretos");
            }
            request.setAttribute("objTipoDecretos", objDsCombo.getTipoDecretos());
        }
        if (objBnDecreto.getMode().equals("U")) {
            objBnDecreto = objDsDecreto.getDecreto(objBnDecreto, objUsuario.getUsuario());
            result = objBnDecreto.getUsuario() + "+++"
                    + objBnDecreto.getPrioridad() + "+++"
                    + objBnDecreto.getArea() + "+++"
                    + objBnDecreto.getUsuarioResponsable() + "+++"
                    + objBnDecreto.getAsunto();
        }
        if (objBnDecreto.getMode().equals("T")) {
            result = "" + objDsDecreto.getListaDecretoTipoDecretos(objBnDecreto, objUsuario.getUsuario());
        }
        if (objBnDecreto.getMode().equals("S")) {
            result = "" + objDsDecreto.getListaDetalleDocumentoDecretado(objBnDecreto, objUsuario.getUsuario());
        }
        if (objBnDecreto.getMode().equals("C")) {
            result = objDsDecreto.getDocumentosPendientes(objUsuario.getUsuario());
        }
        //SE ENVIA DE ACUERDO AL MODO SELECCIONADO
        switch (request.getParameter("mode")) {
            case "decreto":
                dispatcher = request.getRequestDispatcher("MesaPartes/Decretos.jsp");
                break;
            case "documentoDecretado":
                dispatcher = request.getRequestDispatcher("MesaPartes/DocumentosDecretados.jsp");
                break;
            case "G":
                dispatcher = request.getRequestDispatcher("MesaPartes/ListaDecretosPendientes.jsp");
                break;
            case "A":
                dispatcher = request.getRequestDispatcher("MesaPartes/ListaDecretos.jsp");
                break;
            case "L":
                dispatcher = request.getRequestDispatcher("MesaPartes/ListaDocumentosDecretados.jsp");
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

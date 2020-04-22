/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserServices.MesaPartes;

import BusinessServices.Beans.BeanDocumentos;
import BusinessServices.Beans.BeanMsgerr;
import BusinessServices.Beans.BeanUsuario;
import DataService.Despachadores.DocumentosDAO;
import DataService.Despachadores.Impl.DocumentosDAOImpl;
import DataService.Despachadores.Impl.MsgerrDAOImpl;
import DataService.Despachadores.MsgerrDAO;
import Utiles.Utiles;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author H-URBINA-M
 */
@WebServlet(name = "IduDocumentosServlet", urlPatterns = {"/IduDocumentos"})
@MultipartConfig(location = "D:/OPREFA/MesaPartes/Documentos")
public class IduDocumentosServlet extends HttpServlet {

    private ServletConfig config = null;
    private ServletContext context = null;
    private HttpSession session = null;
    private RequestDispatcher dispatcher = null;
    private BeanDocumentos objBnDocumentos;
    private Connection objConnection;
    private DocumentosDAO objDsDocumentos;
    private BeanMsgerr objBnMsgerr = null;
    private MsgerrDAO objDsMsgerr;
    private static final long serialVersionUID = 1L;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.text.ParseException
     * @throws net.sf.jasperreports.engine.JRException
     * @throws java.awt.print.PrinterException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, JRException, JRException, PrinterException {
        config = this.getServletConfig();
        context = config.getServletContext();
        session = request.getSession(true);
        response.setContentType("text/html;charset=UTF-8");
        String result = null;
        int k = 0;
        String resulDetalle = null;
        BeanUsuario objUsuario = (BeanUsuario) session.getAttribute("objUsuario" + session.getId());
        if (objUsuario == null) {
            dispatcher = request.getRequestDispatcher("/FinSession.jsp");
            dispatcher.forward(request, response);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false); //No Complaciente en Fecha
        java.util.Date fecha_doc = sdf.parse(Utiles.checkFecha(request.getParameter("fechaDocumento")));
        objConnection = (Connection) context.getAttribute("objConnection");
        objBnDocumentos = new BeanDocumentos();
        objBnDocumentos.setMode(request.getParameter("mode"));
        objBnDocumentos.setPeriodo(request.getParameter("periodo"));
        objBnDocumentos.setTipo(request.getParameter("tipo"));
        objBnDocumentos.setNumero(request.getParameter("numero"));
        objBnDocumentos.setMes(request.getParameter("mes"));
        objBnDocumentos.setInstitucion(request.getParameter("institucion"));
        objBnDocumentos.setPrioridad(request.getParameter("prioridad"));
        objBnDocumentos.setTipoDocumento(request.getParameter("tipoDocumento"));
        objBnDocumentos.setNumeroDocumento(request.getParameter("numeroDocumento"));
        objBnDocumentos.setClasificacion(request.getParameter("clasificacion"));
        objBnDocumentos.setFecha(new java.sql.Date(fecha_doc.getTime()));
        objBnDocumentos.setAsunto(request.getParameter("asunto"));
        objBnDocumentos.setPostFirma(request.getParameter("postFirma"));
        objBnDocumentos.setLegajo(Utiles.checkNum(request.getParameter("legajos")));
        objBnDocumentos.setFolio(Utiles.checkNum(request.getParameter("folios")));
        objBnDocumentos.setArea(request.getParameter("area"));
        objBnDocumentos.setUsuarioResponsable(request.getParameter("usuario"));
        objBnDocumentos.setReferencia(request.getParameter("referencia"));
        objDsDocumentos = new DocumentosDAOImpl(objConnection);
        if (objBnDocumentos.getTipo().equals("E") && !objBnDocumentos.getMode().equals("D")) {
            Collection<Part> parts = request.getParts();
            for (Part part : parts) {
                if (null != Utiles.getFileName(part)) {
                    objBnDocumentos.setArchivo(Utiles.stripAccents(Utiles.getFileName(part)));
                    part.write(objBnDocumentos.getPeriodo() + "-" + objBnDocumentos.getTipo() + "-" + objBnDocumentos.getNumero() + "-" + objBnDocumentos.getArchivo());
                }
            }
        } else {
            if (objBnDocumentos.getMode().equals("C") || objBnDocumentos.getTipo().equals("S")) {
                Collection<Part> parts = request.getParts();
                for (Part part : parts) {
                    if (null != Utiles.getFileName(part)) {
                        objBnDocumentos.setArchivo(Utiles.stripAccents(Utiles.getFileName(part)));
                        part.write(objBnDocumentos.getPeriodo() + "-" + objBnDocumentos.getTipo() + "-" + objBnDocumentos.getNumero() + "-" + objBnDocumentos.getArchivo());
                    }
                }
            }
        }
        k = objDsDocumentos.iduDocumento(objBnDocumentos, objUsuario.getUsuario());
        if (k == 0) {
            // EN CASO DE HABER PROBLEMAS DESPACHAMOS UNA VENTANA DE ERROR, MOSTRANDO EL ERROR OCURRIDO.
            result = "ERROR";
            objBnMsgerr = new BeanMsgerr();
            objBnMsgerr.setUsuario(objUsuario.getUsuario());
            objBnMsgerr.setTabla("OPREFA_DOCUMENTOS");
            objBnMsgerr.setTipo(objBnDocumentos.getMode());
            objDsMsgerr = new MsgerrDAOImpl(objConnection);
            objBnMsgerr = objDsMsgerr.getMsgerr(objBnMsgerr);
            resulDetalle = objBnMsgerr.getDescripcion();
        }
        // EN CASO DE NO HABER PROBLEMAS RETORNAMOS UNA NUEVA CONSULTA CON TODOS LOS DATOS.
        if (result == null) {
            try (PrintWriter out = response.getWriter()) {
                out.print("GUARDO");
                if (objBnDocumentos.getMode().equals("I")) {
                    InputStream stream = context.getResourceAsStream("/Reportes/Documentos/MPA0003.jasper");
                    Map parametro = new HashMap();
                    parametro.put("REPORT_LOCALE", new Locale("en", "US"));
                    parametro.put("PERIODO", objBnDocumentos.getPeriodo());
                    parametro.put("CODIGO", objBnDocumentos.getNumero());
                    parametro.put("TIPO", objBnDocumentos.getTipo());
                    parametro.put("USUARIO", objUsuario.getUsuario());
                    parametro.put("SUBREPORT_DIR", "D:\\OPREFA\\Reportes");
                    JasperPrint reporte = JasperFillManager.fillReport(stream, parametro, objConnection);
                    Utiles u = new Utiles();
                    u.printTicket(reporte);
                }
            }
        } else {
            //PROCEDEMOS A ELIMINAR TODO;
            try (PrintWriter out = response.getWriter()) {
                out.print(resulDetalle);
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
        try {
            processRequest(request, response);
        } catch (ParseException | JRException | PrinterException ex) {
            Logger.getLogger(IduDocumentosServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ParseException | JRException | PrinterException ex) {
            Logger.getLogger(IduDocumentosServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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

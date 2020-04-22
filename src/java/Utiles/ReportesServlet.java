/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utiles;

import BusinessServices.Beans.BeanReporte;
import BusinessServices.Beans.BeanUsuario;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 *
 * @author H-URBINA-M
 */
@WebServlet(name = "ReportesServlet", urlPatterns = {"/Reportes"})
public class ReportesServlet extends HttpServlet {

    private HttpSession session = null;
    private ServletConfig config = null;
    private ServletContext context = null;
    private RequestDispatcher dispatcher;
    private Connection objConnection;
    private BeanReporte reporte;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws net.sf.jasperreports.engine.JRException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JRException {
        config = this.getServletConfig();
        context = config.getServletContext();
        session = request.getSession();
        //VERIFICAMOS QUE LA SESSION SEA VALIDA        
        BeanUsuario objUsuario = (BeanUsuario) session.getAttribute("objUsuario" + session.getId());
        if (objUsuario == null) {
            dispatcher = request.getRequestDispatcher("/FinSession.jsp");
            dispatcher.forward(request, response);
        }
        objConnection = (Connection) context.getAttribute("objConnection");
        reporte = new BeanReporte();
        reporte.setReporte(request.getParameter("reporte"));
        reporte.setPeriodo(request.getParameter("periodo"));
        reporte.setUnidad("" + session.getAttribute("unidad"));
        reporte.setPresupuesto(Utiles.checkNum(request.getParameter("presupuesto")));
        reporte.setTipo(request.getParameter("tipo"));
        reporte.setCodigo(request.getParameter("codigo"));
        reporte.setCodigo2(request.getParameter("codigo2"));
        reporte.setGenerica(request.getParameter("generica"));
        String nombre = "";
        
        if (reporte.getCodigo() != null) {
            if (reporte.getCodigo().equals("") || reporte.getCodigo().equals("null")) {
                reporte.setCodigo("%");
            }
        }
        if (reporte.getGenerica() != null) {
            if (reporte.getGenerica().equals("*") || reporte.getGenerica().equals("null")) {
                reporte.setGenerica("");
            }
        }
        switch (reporte.getReporte()) {
            //REPORTES DEL MODULO DE PLANEAMIENTO
            case "PLA0001":
                nombre = "Planeamiento/PLA0001.jasper";
                break;
            //REPORTES DEL MODULO DE PROGRAMACION
            case "PROG0001":
                nombre = "Programacion/PROG0001.jasper";
                break;
            case "PROG0002":
                nombre = "Programacion/PROG0002.jasper";
                break;
            case "PROG0003":
                nombre = "Programacion/PROG0003.jasper";
                break;
            case "PROG0004":
                nombre = "Programacion/PROG0004.jasper";
                break;
            case "PROG0005":
                nombre = "Programacion/PROG0005.jasper";
                break;
            case "PROG0006":
                nombre = "Programacion/PROG0006.jasper";
                break;
            case "PROG0007":
                nombre = "Programacion/PROG0007.jasper";
                break;
            case "PROG0008":
                nombre = "Programacion/PROG0008.jasper";
                break;
            //REPORTES DE EJECUCION PRESUPUESTAL
            case "EJE0001":
                nombre = "Ejecucion/EJE0001.jasper";
                break;
            case "EJE0002":
                nombre = "Ejecucion/EJE0002.jasper";
                break;
            case "EJE0003":
                nombre = "Ejecucion/EJE0003.jasper";
                break;
            case "EJE0004":
                nombre = "Ejecucion/EJE0004.jasper";
                break;
            case "EJE0005":
                nombre = "Ejecucion/EJE0005.jasper";
                break;
            case "EJE0006":
                nombre = "Ejecucion/EJE0006_1.jasper";
                if (Integer.valueOf(reporte.getPeriodo()) <= 2014) {
                    nombre = "Ejecucion/EJE0006_1.jasper";
                }
                break;
            case "EJE0007":
                nombre = "Ejecucion/EJE0007_1.jasper";
                if (Integer.valueOf(reporte.getPeriodo()) <= 2014) {
                    nombre = "Ejecucion/EJE0007_1.jasper";
                }
                break;
            case "EJE0008":
                nombre = "Ejecucion/EJE0008.jasper";
                break;
            case "EJE0009":
                nombre = "Ejecucion/EJE0009.jasper";
                break;
            case "EJE0010":
                nombre = "Ejecucion/EJE0010.jasper";
                break;
            case "EJE0011":
                nombre = "Ejecucion/EJE0011_1.jasper";
                if (Integer.valueOf(reporte.getPeriodo()) <= 2014) {
                    nombre = "Ejecucion/EJE0011_1.jasper";
                }
                break;
            case "EJE0012":
                nombre = "Ejecucion/EJE0012.jasper";
                break;
            case "EJE0013":
                nombre = "Ejecucion/EJE0013.jasper";
                break;
            case "EJE0014":
                nombre = "Ejecucion/EJE0014_1.jasper";
                if (Integer.valueOf(reporte.getPeriodo()) <= 2014) {
                    nombre = "Ejecucion/EJE0014_1.jasper";
                }
                break;
            //REPORTES DE LOGISTICA
            case "LOG0001":
                nombre = "Logistica/LOG0001.jasper";
                break;
            case "LOG0002":
                nombre = "Logistica/LOG0002.jasper";
                break;
            case "LOG0003":
                nombre = "Logistica/LOG0003.jasper";
                break;
            case "LOG0004":
                nombre = "Logistica/LOG0004.jasper";
                break;
            case "LOG0005":
                nombre = "Logistica/LOG0005.jasper";
                break;
            //REPORTES DE MESA DE PARTES
            case "MPA0001":
                nombre = "MesaPartes/MPA0001.jasper";
                break;
            case "MPA0002":
                nombre = "MesaPartes/MPA0002.jasper";
                break;
            //REPORTES SIAF
            case "SIAF0001":
                nombre = "SIAF/SIAF0001.jasper";
                break;
            //REPORTES PERSONAL
            case "PER0001":
                nombre = "Personal/PER0001.jasper";
                break;
            case "PER0002":
                nombre = "Personal/PER0002.jasper";
                break;
            case "PER0003":
                nombre = "Personal/PER0003.jasper";
                break;
            //REPORTES EVALUACION
            case "EVA0001":
                nombre = "Evaluacion/EVA0001.jasper";
                break;
            case "EVA0002":
                nombre = "Evaluacion/EVA0002.jasper";
                break;
            case "EVA0003":
                nombre = "Evaluacion/EVA0003.jasper";
                break;
            case "EVA0004":
                nombre = "Evaluacion/EVA0004.jasper";
                break;
            //MESA DE AYUDA
            case "MAY0001":
                nombre = "MesaAyuda/MAY0001.jasper";
                break;
            default:
                break;
        }
        InputStream stream = context.getResourceAsStream("/Reportes/" + nombre);
        if (stream == null) {
            throw new IllegalArgumentException("No se encuentra el reporte con nombre: " + nombre);
        }
        Map parameters = new HashMap();
        parameters.put("REPORT_LOCALE", new Locale("en", "US"));
        parameters.put("PERIODO", reporte.getPeriodo());
        parameters.put("PRESUPUESTO", reporte.getPresupuesto());
        parameters.put("TIPO", reporte.getTipo());
        parameters.put("CODIGO", reporte.getCodigo());
        parameters.put("CODIGO2", reporte.getCodigo2());
        parameters.put("USUARIO", objUsuario.getUsuario());
        parameters.put("GENERICA", reporte.getGenerica());
        parameters.put("SUBREPORT_DIR", "D:\\OPREFA\\Reportes");
        // parameters.put("SUBREPORT_DIR2", "F:\\Apache Tomcat\\webapps\\SIPRE\\web\\Reportes\\");
        parameters.put("SUBREPORT_DIR2", stream);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        JasperPrint jasperPrint = JasperFillManager.fillReport(stream, parameters, objConnection);
        response.setContentType("application/pdf");
        JRExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
        exporter.exportReport();
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
        } catch (JRException ex) {
            Logger.getLogger(ReportesServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (JRException ex) {
            Logger.getLogger(ReportesServlet.class.getName()).log(Level.SEVERE, null, ex);
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

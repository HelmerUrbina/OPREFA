/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserServices.MesaPartes;

import BusinessServices.Beans.BeanMesaPartes;
import BusinessServices.Beans.BeanMsgerr;
import DataService.Despachadores.Impl.MesaPartesDAOImpl;
import DataService.Despachadores.Impl.MsgerrDAOImpl;
import DataService.Despachadores.MesaPartesDAO;
import DataService.Despachadores.MsgerrDAO;
import Utiles.JavaMail;
import Utiles.Utiles;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

/**
 *
 * @author H-URBINA-M
 */
@WebServlet(name = "RegistrarMesaPartesServlet", urlPatterns = {"/RegistrarMesaPartes"})
@MultipartConfig(location = "D:/OPREFA/MesaPartes/Documentos")
public class RegistrarMesaPartesServlet extends HttpServlet {

    private ServletConfig config = null;
    private ServletContext context = null;
    private BeanMesaPartes objBnMesaParte;
    private Connection objConnection;
    private MesaPartesDAO objDsMesaParte;
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
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        config = this.getServletConfig();
        context = config.getServletContext();
        String result = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false); //No Complaciente en Fecha
        java.util.Date fecha_doc = sdf.parse(request.getParameter("fechaDocumento"));
        objConnection = (Connection) context.getAttribute("objConnection");
        objBnMesaParte = new BeanMesaPartes();
        objBnMesaParte.setMode("I");
        objBnMesaParte.setPeriodo(request.getParameter("periodo"));
        objBnMesaParte.setTipo(request.getParameter("tipo"));
        objBnMesaParte.setNumero("0");
        objBnMesaParte.setMes(request.getParameter("mes"));
        objBnMesaParte.setInstitucion(request.getParameter("institucion"));
        objBnMesaParte.setPrioridad(request.getParameter("prioridad"));
        objBnMesaParte.setDocumento(request.getParameter("documento"));
        objBnMesaParte.setNumeroDocumento(request.getParameter("numeroDocumento"));
        objBnMesaParte.setClasificacion(request.getParameter("clasificacion"));
        objBnMesaParte.setFecha(new java.sql.Date(fecha_doc.getTime()));
        objBnMesaParte.setAsunto(request.getParameter("asunto"));
        objBnMesaParte.setPostFirma(request.getParameter("postFirma"));
        objBnMesaParte.setLegajo(Utiles.checkNum(request.getParameter("legajos")));
        objBnMesaParte.setFolio(Utiles.checkNum(request.getParameter("folios")));
        objBnMesaParte.setCorreo(request.getParameter("correo"));
        objDsMesaParte = new MesaPartesDAOImpl(objConnection);
        Collection<Part> parts = request.getParts();
        for (Part part : parts) {
            if (null != Utiles.getFileName(part)) {
                objBnMesaParte.setArchivo(Utiles.getFileName(part));
                part.write(objBnMesaParte.getPeriodo() + "-" + objBnMesaParte.getTipo() + "-" + objBnMesaParte.getNumero() + "-" + objBnMesaParte.getArchivo());
            }
        }
        objBnMesaParte.setNumero(objDsMesaParte.iduMesaParte(objBnMesaParte, "0000"));
        if (objBnMesaParte.getNumero().equals("0")) {
            // EN CASO DE HABER PROBLEMAS DESPACHAMOS UNA VENTANA DE ERROR, MOSTRANDO EL ERROR OCURRIDO.
            objBnMsgerr = new BeanMsgerr();
            objBnMsgerr.setUsuario("0000");
            objBnMsgerr.setTabla("OPREFA_MESA_PARTES");
            objBnMsgerr.setTipo(objBnMesaParte.getMode());
            objDsMsgerr = new MsgerrDAOImpl(objConnection);
            objBnMsgerr = objDsMsgerr.getMsgerr(objBnMsgerr);
            result = objBnMsgerr.getDescripcion();
        }
        InputStream stream = request.getServletContext().getResourceAsStream("/Reportes/MesaPartes/MPA0004.jasper");
        if (stream == null) {
            System.out.println("No se encuentra el reporte");
        }
        Map parameters = new HashMap();
        parameters.put("REPORT_LOCALE", new Locale("en", "US"));
        parameters.put("PERIODO", objBnMesaParte.getPeriodo());
        parameters.put("TIPO", objBnMesaParte.getTipo());
        parameters.put("CODIGO", objBnMesaParte.getNumero());
        parameters.put("SUBREPORT_DIR", getServletContext().getRealPath("/Reportes"));
        String imagen = "";
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(stream, parameters, objConnection);
            imagen = extractPrintImage("D:\\OPREFA\\MesaPartes\\Documentos\\Registro\\" + objBnMesaParte.getPeriodo() + "-" + objBnMesaParte.getNumero() + ".jpg", jasperPrint);
        } catch (JRException ex) {
            System.out.println(ex.getMessage());
        }
        // EN CASO DE NO HABER PROBLEMAS RETORNAMOS UNA NUEVA CONSULTA CON TODOS LOS DATOS.
        if (result == null) {
            try (PrintWriter out = response.getWriter()) {
               JavaMail mail = new JavaMail(objBnMesaParte.getCorreo(), imagen);
                String aaaa = mail.SendMail();
                out.print("GUARDO");
            } catch (Exception e) {
                try (PrintWriter out2 = response.getWriter()) {
                    out2.print(e.getMessage());
                }
            }
        } else {
            //PROCEDEMOS A ELIMINAR TODO;
            try (PrintWriter out = response.getWriter()) {
                out.print(result);
            }
        }
    }

    private String extractPrintImage(String filePath, JasperPrint print) {
        File file = new File(filePath);
        OutputStream ouputStream = null;
        try {
            ouputStream = new FileOutputStream(file);
            DefaultJasperReportsContext.getInstance();
            JasperPrintManager printManager = JasperPrintManager.getInstance(DefaultJasperReportsContext.getInstance());
            BufferedImage rendered_image = null;
            rendered_image = (BufferedImage) printManager.printPageToImage(print, 0, 1.6f);
            ImageIO.write(rendered_image, "jpg", ouputStream);
        } catch (IOException | JRException e) {
            System.out.println(e.getMessage());
        }
        return "" + file;
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
        } catch (ParseException ex) {
            Logger.getLogger(RegistrarMesaPartesServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ParseException ex) {
            Logger.getLogger(RegistrarMesaPartesServlet.class.getName()).log(Level.SEVERE, null, ex);
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

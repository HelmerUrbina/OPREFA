/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author H-URBINA-M
 */
@WebServlet(name = "DescargaServlet", urlPatterns = {"/Descarga"})
public class DescargaServlet extends HttpServlet {

    private static final long serialVersionUID = 4440011247408877539L;
    private String filePath;

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
        String periodo = request.getParameter("periodo");
        String codigo = request.getParameter("codigo");
        String unidadOperativa = request.getParameter("unidadOperativa");
        String presupuesto = request.getParameter("presupuesto");
        String documento = request.getParameter("documento");
        String opcion = request.getParameter("opcion");
        filePath = "D:/OPREFA/";
        switch (opcion) {
            case "DemandaAdicional":
                filePath += "PROGRAMACION/DemandaAdicional/" + periodo + "-" + unidadOperativa + "-" + documento;
                break;
            case "MesaPartes":
                filePath += "MesaPartes/Documentos/" + periodo + "-" + codigo + "-" + documento;
                break;
            case "Firma":
                filePath += "DOCUMENTOS/Firmas/" + periodo + "-" + unidadOperativa + "-" + codigo + "-" + documento;
                break;
            case "CertificadoPresupuestal":
                filePath += "EJECUCION/CertificadoPresupuestal/" + periodo + "-" + unidadOperativa + "-" + presupuesto + "-" + codigo + "-" + documento;
                break;
            case "CompromisoAnual":
                filePath += "EJECUCION/CompromisoAnual/" + periodo + "-" + unidadOperativa + "-" + presupuesto + "-" + codigo + "-" + documento;
                break;
            case "DeclaracionJurada":
                filePath += "EJECUCION/DeclaracionJurada/" + periodo + "-" + unidadOperativa + "-" + presupuesto + "-" + codigo + "-" + documento;
                break;
            default:
                filePath = "";
        }
        File fileToDownload = new File(filePath);
        try (FileInputStream fileInputStream = new FileInputStream(fileToDownload);
                ServletOutputStream out = response.getOutputStream()) {
            String mimeType = new MimetypesFileTypeMap().getContentType(filePath);
            response.setContentType(mimeType);
            response.setContentLength(fileInputStream.available());
            response.setHeader("Content-Disposition", "attachment; filename=\""
                    + fileToDownload.getName() + "\"");
            int c;
            while ((c = fileInputStream.read()) != -1) {
                out.write(c);
            }
            out.flush();
        } catch (IOException e) {
            System.out.println("Error al leer Archivo" + e.getMessage());
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataService.Despachadores;

import BusinessServices.Beans.BeanDocumentos;
import java.util.List;

/**
 *
 * @author H-URBINA-M
 */
public interface DocumentosDAO {

    public List getListaDocumentos(BeanDocumentos objBeanDocumento, String usuario);

    public List getListaDocumentosConsulta(BeanDocumentos objBeanDocumento, String usuario);

    public BeanDocumentos getDocumento(BeanDocumentos objBeanDocumento, String usuario);

    public String getNumeroDocumento(BeanDocumentos objBnDocumento, String usuario);

    public int iduDocumento(BeanDocumentos objBeanDocumento, String usuario);

    
    
    
    public List getListaRemisionDocumento(BeanDocumentos objBeanDocumento, String usuario);

    public String getNumeroDocumentoSalida(BeanDocumentos objBnDocumento, String usuario);

}

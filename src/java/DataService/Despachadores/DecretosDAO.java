/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataService.Despachadores;

import BusinessServices.Beans.BeanDocumentos;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author H-URBINA-M
 */
public interface DecretosDAO {

    public List getListaDocumentosPendientes(BeanDocumentos objBnDecreto, String usuario);

    public List getListaDocumentosDecretados(BeanDocumentos objBnDecreto, String usuario);

    public List getListaDocumentosRespuesta(BeanDocumentos objDocumento, String usuario);

    public int iduDecreto(BeanDocumentos objBnDecreto, String usuario);

    public int iduDecretarTipoDecreto(BeanDocumentos objBnDecreto, String usuario);

    public String getDocumentosPendientes(String usuario);

    public BeanDocumentos getDecreto(BeanDocumentos objBeanDecreto, String usuario);

    public ArrayList getListaDecretoTipoDecretos(BeanDocumentos objBeanDecreto, String usuario);

    public ArrayList getListaDetalleDocumentoDecretado(BeanDocumentos objBeanDecreto, String usuario);

}

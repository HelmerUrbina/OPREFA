/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataService.Despachadores;

import BusinessServices.Beans.BeanMesaPartes;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author H-URBINA-M
 */
public interface DecretosDAO {

    public List getListaDocumentosPendientes(BeanMesaPartes objBnDecreto, String usuario);

    public List getListaDocumentosDecretados(BeanMesaPartes objBnDecreto, String usuario);

    public List getListaDocumentosRespuesta(BeanMesaPartes objDocumento, String usuario);

    public int iduDecreto(BeanMesaPartes objBnDecreto, String usuario);

    public int iduDecretarTipoDecreto(BeanMesaPartes objBnDecreto, String usuario);

    public String getDocumentosPendientes(String usuario);

    public BeanMesaPartes getDecreto(BeanMesaPartes objBeanDecreto, String usuario);

    public ArrayList getListaDecretoTipoDecretos(BeanMesaPartes objBeanDecreto, String usuario);

    public ArrayList getListaDetalleDocumentoDecretado(BeanMesaPartes objBeanDecreto, String usuario);

}

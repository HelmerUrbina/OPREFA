/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataService.Despachadores;

import BusinessServices.Beans.BeanMesaPartes;
import java.util.List;

/**
 *
 * @author H-URBINA-M
 */
public interface MesaPartesDAO {

    public List getListaMesaPartes(BeanMesaPartes objBeanMesaParte, String usuario);

    public List getListaMesaPartesConsulta(BeanMesaPartes objBeanMesaParte, String usuario);

    public BeanMesaPartes getMesaParte(BeanMesaPartes objBeanMesaParte, String usuario);

    public String getNumeroMesaParte(BeanMesaPartes objBnMesaParte, String usuario);

    public int iduMesaParte(BeanMesaPartes objBeanMesaParte, String usuario);

    public List getListaRemisionMesaParte(BeanMesaPartes objBeanMesaParte, String usuario);

    public String getNumeroMesaParteSalida(BeanMesaPartes objBnMesaParte, String usuario);

}

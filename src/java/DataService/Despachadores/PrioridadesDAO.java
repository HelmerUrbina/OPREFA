/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataService.Despachadores;

import BusinessServices.Beans.BeanComun;
import java.util.List;

/**
 *
 * @author helme
 */
public interface PrioridadesDAO {

    public List getListaPrioridades();

    public BeanComun getPrioridad(BeanComun objBeanComun);

    public int iduPrioridad(BeanComun objBeanComun, String usuario);

}

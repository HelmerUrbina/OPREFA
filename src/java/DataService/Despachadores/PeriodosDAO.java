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
 * @author H-URBINA-M
 */
public interface PeriodosDAO {

    public List getListaPeriodos();

    public BeanComun getPeriodo(BeanComun objBeanComun);

    public int iduPeriodo(BeanComun objBeanComun, String usuario);

}
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
public interface AreaLaboralDAO {

    public List getListaAreaLaboral();

    public BeanComun getAreaLaboral(BeanComun objBeanComun);

    public int iduAreaLaboral(BeanComun objBeanComun, String usuario);

}

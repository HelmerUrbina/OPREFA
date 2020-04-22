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
public interface TipoDecretosDAO {

    public List getListaTipoDecretos();

    public BeanComun getTipoDecreto(BeanComun objBeanComun);

    public int iduTipoDecreto(BeanComun objBeanComun, String usuario);

}

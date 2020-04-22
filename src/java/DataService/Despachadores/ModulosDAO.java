package DataService.Despachadores;

import BusinessServices.Beans.BeanComun;
import java.util.List;

public interface ModulosDAO {

    public List getListaModulos();

    public BeanComun getModulo(BeanComun objBeanComun);

    public int iduModulo(BeanComun objBeanComun, String usuario);
}

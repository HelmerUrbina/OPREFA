package DataService.Despachadores;

import BusinessServices.Beans.BeanMenu;
import java.util.List;

public interface MenuDAO {

    public List getListaMenu();

    public BeanMenu getMenu(BeanMenu objBeanComun);

    public int iduMenu(BeanMenu objBeanMenu, String usuario);

}
